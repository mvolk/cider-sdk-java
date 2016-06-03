/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Michael Volk (michael@volksys.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ciderref.sdk.calculate;

import com.ciderref.sdk.property.DegreesBrix;
import com.ciderref.sdk.property.IllegalPropertyValueException;
import com.ciderref.sdk.property.Mass;
import com.ciderref.sdk.property.MassConcentration;
import com.ciderref.sdk.property.PercentAlcoholByVolume;
import com.ciderref.sdk.property.SpecificGravity;
import com.ciderref.sdk.property.Volume;
import com.ciderref.sdk.property.units.UnitsOfMass;
import com.ciderref.sdk.property.units.UnitsOfVolume;
import com.ciderref.sdk.substance.AqueousSolution;
import com.ciderref.sdk.substance.Water;

/**
 * Computes the amount of sugar that must be added to attain a target ABV and the properties of a juice after
 * chaptalization with a given amount of sugar.
 */
public class ChaptalizationCalculator {

    private static final double MINIMUM_SPECIFIC_GRAVITY = 1.035;
    private static final double MAXIMUM_TARGET_ABV = 15.0;

    private final PotentialAlcoholCalculator potentialAlcoholCalculator = new PotentialAlcoholCalculator();
    private final BrixCalculator brixCalculator = new BrixCalculator();

    /**
     * Calculate that amount of sucrose that needs to be added to the must (juice) in order to obtain the desired
     * potential alcohol by volume.
     *
     * @param targetAbv (not null) the desired potential alcohol by volume
     * @param solution (not null) the aqueous solution (typically apple juice) to amend
     * @return (not null) the amount of sucrose that must be added to attain the desired {@code targetAbv}; zero if
     *         the juice's potential ABV is already at or above the {@code targetAbv}
     * @throws IllegalArgumentException if any one or more of the parameters are null
     * @throws IllegalPropertyValueException if {@code specificGravity} is less than 1.035 or {@code targetAbv} is
     *         over 15% ABV
     */
    public Mass getAmountOfSugarToAdd(AqueousSolution solution, PercentAlcoholByVolume targetAbv) {

        if (targetAbv == null || solution == null) {
            throw new IllegalArgumentException("Arguments must not be null");
        }

        if (Double.compare(solution.getSpecificGravity().getValue(), MINIMUM_SPECIFIC_GRAVITY) < 0) {
            throw new IllegalPropertyValueException("This calculator is not designed to work for juices with an "
                    + "original gravity below 1.035");
        }

        if (Double.compare(MAXIMUM_TARGET_ABV, targetAbv.getValue()) < 0) {
            throw new IllegalPropertyValueException("This calculator is not designed to work for target potential "
                    + "alcohol in excess of 15% ABV");
        }

        if (potentialAlcoholCalculator.getPotentialAlcohol(solution.getSugar()).compareTo(targetAbv) >= 0) {
            return new Mass(0, UnitsOfMass.Grams);
        }

        // While it is relatively easy to solve for the resultant potential ABV given a sugar addition of known
        //  quantity, it is quite challenging - perhaps impossible? - to solve for the sugar addition given a known
        //  resultant potential ABV. Until such time as a solution is found, this method employs a simple bounded
        //  binary search for a solution that is close enough (within 1/100th of a gram per liter).
        // The bounds are initialized based on the naive assumption that the volume of the solution doesn't change
        //  with the addition of sugar.

        // Calculate the mass of sugar in one liter of the juice now
        double currentSugarPerLiter = solution.getSugar().getValueInGramsPerLiter();

        // Calculate the amount of sugar that needs to be in one liter of the amended juice to produce the target
        //  potential ABV.
        double targetSugarPerLiter =
                potentialAlcoholCalculator.getSugarConcentration(targetAbv).getValueInGramsPerLiter();

        // If we assume the volume doesn't change with the addition of sugar, then the amount of sugar that we need
        //  to add (per liter of juice) is the difference between the juice's current sugar concentration and the
        //  sugar concentration necessary to obtain the target potential ABV. However, since the volume of the final
        //  solution is actually greater than the original volume, the naive value will underestimate the actual value,
        //  since the sugar to be added will be targetSugarPerLiter * (1 + delta) - currentSugarPerLiter * 1, where
        //  delta will be a small positive value. Thus, the naive estimate provides our low bound, and a liberal
        //  multiple of the naive estimate provides a reasonable high bound.
        double naiveSugarDeficitPerLiter = targetSugarPerLiter - currentSugarPerLiter;
        double lowBound = 1.0 * naiveSugarDeficitPerLiter;
        double highBound = 1.5 * naiveSugarDeficitPerLiter;

        // Now we're ready to test our hypothesis. For each iteration, the hypothesis is that the average of the low and
        //  high bounds is the right amount of sugar to add to obtain the desired potential ABV. We test the hypothesis
        //  by calculating the actual potential ABV that results from the addition of this much sugar. If it turns out
        //  that this amount of sugar is too much, then our high bound can be reduced to this value. If this amount of
        //  sugar is insufficient, the our low bound can be increased to this value. If by freak chance this is
        //  exactly the right amount, our low bound and high bound can be set to this value. Regardless, the magnitude
        //  of the distance between the bounds is reduced by half with each iteration, and we rapidly converge on a
        //  solution. When the bounds are within 1/100ths of a gram per liter, we accept the average of the bounds as
        //  the solution.
        while (Double.compare(highBound - lowBound, 0.01) > 0) {
            double meanAddedSugar = (highBound + lowBound) / 2;
            AqueousSolution amendedSolution =
                    getChaptalizationResult(solution, new Mass(meanAddedSugar, UnitsOfMass.Grams)); // NOPMD
            double meanAbv = potentialAlcoholCalculator.getPotentialAlcohol(amendedSolution.getSugar()).getValue();
            if (Double.compare(meanAbv, targetAbv.getValue()) < 0) {
                lowBound = meanAddedSugar;
            } else {
                highBound = meanAddedSugar;
            }
        }

        double sugarAdditionPerLiter = (highBound + lowBound) / 2;
        return new Mass(sugarAdditionPerLiter * solution.getVolume().getValue(UnitsOfVolume.Liters), UnitsOfMass.Grams);
    }

    /**
     * Calculate the properties of the solution produced by adding sugar to an existing solution.
     *
     * @param originalSolution (not null) properties of the solution prior to chaptalization
     * @param sugarAdded (not null) amount of sugar to be added to the solution
     * @return (not null) properties of the resulting solution
     * @throws NullPointerException if either of the arguments are null
     * @throws IllegalPropertyValueException if the Brix of the resulting solution exceeds 44°Bx
     */
    public AqueousSolution getChaptalizationResult(AqueousSolution originalSolution, Mass sugarAdded) {
        double totalSugarPerOriginalLiter =
                originalSolution.getSugar().getValueInGramsPerLiter() + sugarAdded.getValue(UnitsOfMass.Grams);
        double totalSolidsPerOriginalLiter =
                originalSolution.getSolids().getValueInGramsPerLiter() + sugarAdded.getValue(UnitsOfMass.Grams);
        double densityOfWater = Water.DENSITY_AT_20_DEGREES_CELSIUS.getValueInGramsPerLiter();
        // SG = density of solution / density of water, so density of solution = SG * density of water
        double originalTotalMass = originalSolution.getSpecificGravity().getValue() * densityOfWater;
        double totalSolutionMassPerOriginalLiter = originalTotalMass + sugarAdded.getValue(UnitsOfMass.Grams);
        DegreesBrix finalDegreesBrix =
                new DegreesBrix((totalSolidsPerOriginalLiter / totalSolutionMassPerOriginalLiter) * 100);
        SpecificGravity finalSpecificGravity = brixCalculator.getSpecificGravity(finalDegreesBrix);
        double finalVolumeOfOriginalLiterInLiters =
                totalSolutionMassPerOriginalLiter / (finalSpecificGravity.getValue() * densityOfWater);
        double totalSugarPerFinalLiter = totalSugarPerOriginalLiter / finalVolumeOfOriginalLiterInLiters;
        MassConcentration finalSugarConcentration =
                new MassConcentration(new Mass(totalSugarPerFinalLiter, UnitsOfMass.Grams), Volume.ONE_LITER);
        double totalSolidsPerFinalLiter = totalSolidsPerOriginalLiter / finalVolumeOfOriginalLiterInLiters;
        MassConcentration finalSolidsConcentration =
                new MassConcentration(new Mass(totalSolidsPerFinalLiter, UnitsOfMass.Grams), Volume.ONE_LITER);
        double finalVolumeInLiters =
                originalSolution.getVolume().getValue(UnitsOfVolume.Liters) * finalVolumeOfOriginalLiterInLiters;
        Volume finalVolume = new Volume(finalVolumeInLiters, UnitsOfVolume.Liters);
        return new AqueousSolution(finalSpecificGravity, finalSolidsConcentration, finalSugarConcentration,
                finalVolume);
    }

}
