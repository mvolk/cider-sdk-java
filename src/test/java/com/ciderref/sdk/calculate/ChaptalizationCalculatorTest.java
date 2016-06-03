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

import static org.junit.Assert.assertEquals;

import com.ciderref.sdk.property.IllegalPropertyValueException;
import com.ciderref.sdk.property.Mass;
import com.ciderref.sdk.property.MassConcentration;
import com.ciderref.sdk.property.PercentAlcoholByVolume;
import com.ciderref.sdk.property.SpecificGravity;
import com.ciderref.sdk.property.Volume;
import com.ciderref.sdk.property.units.UnitsOfMass;
import com.ciderref.sdk.property.units.UnitsOfVolume;
import com.ciderref.sdk.substance.AmericanAppleJuice;
import com.ciderref.sdk.substance.AppleJuice;
import com.ciderref.sdk.substance.AqueousSolution;
import org.junit.Test;

/**
 * Unit tests for {@link ChaptalizationCalculator}.
 */
public class ChaptalizationCalculatorTest {

    private final AqueousSolution solution35DegSg;
    private final AqueousSolution solution40DegSg;

    /** Constructor. */
    public ChaptalizationCalculatorTest() {
        SpecificGravity specificGravity = new SpecificGravity(1.035);
        MassConcentration sugar = new MassConcentration(new Mass(74.5, UnitsOfMass.Grams), Volume.ONE_LITER);
        MassConcentration solids = new MassConcentration(new Mass(90.7, UnitsOfMass.Grams), Volume.ONE_LITER);
        solution35DegSg = new AqueousSolution(specificGravity, solids, sugar, Volume.ONE_LITER);

        specificGravity = new SpecificGravity(1.040);
        sugar = new MassConcentration(new Mass(85.2, UnitsOfMass.Grams), Volume.ONE_LITER);
        solids = new MassConcentration(new Mass(103.7, UnitsOfMass.Grams), Volume.ONE_LITER);
        solution40DegSg = new AqueousSolution(specificGravity, solids, sugar, Volume.ONE_LITER);
    }

    /**
     * Confirm that the example given in chapter 8.3 of Claude Jolicoeur's The New Cidermaker's Handbook, from whence
     * the formulae used to generate the solution was gleaned, is the same answer, within a quarter of a gram per
     * Liter, as the answer given by the calculator.
     */
    @Test
    public void testGetAmountOfSugarToAddJolicoeurExampleOneLiter() {
        // Close inspection reveals that the calculation is rounded to 7.3%. Based on the formulae in the book,
        //  the actual %ABV is 122.2 x 0.06  = 7.332%. This rounding error translates to almost half a gram of sugar
        //  per liter - not significant for the book, but significant for this test.
        PercentAlcoholByVolume targetAbv = new PercentAlcoholByVolume(7.332);
        double result = new ChaptalizationCalculator()
                .getAmountOfSugarToAdd(solution40DegSg, targetAbv)
                .getValue(UnitsOfMass.Grams);
        assertEquals(40, result, 0.025);
    }

    /**
     * Confirm that the example given in chapter 8.3 of Claude Jolicoeur's The New Cidermaker's Handbook, from whence
     * the formulae used to generate the solution was gleaned, is appropriately scaled up by the calculator.
     */
    @Test
    public void testGetAmountOfSugarToAddJolicoeurExampleTwentyLiters() {
        SpecificGravity specificGravity = new SpecificGravity(1.040);
        MassConcentration sugar = new MassConcentration(new Mass(85.2, UnitsOfMass.Grams), Volume.ONE_LITER);
        MassConcentration solids = new MassConcentration(new Mass(103.7, UnitsOfMass.Grams), Volume.ONE_LITER);
        Volume twentyLiters = new Volume(20, UnitsOfVolume.Liters);
        AqueousSolution solution = new AqueousSolution(specificGravity, solids, sugar, twentyLiters);
        PercentAlcoholByVolume targetAbv = new PercentAlcoholByVolume(7.332);
        double result = new ChaptalizationCalculator()
                .getAmountOfSugarToAdd(solution, targetAbv)
                .getValue(UnitsOfMass.Grams);
        assertEquals(800, result, 0.5);
    }

    /**
     * Confirm a large-magnitude chaptalization adjustment produces a result that is in keeping with Jolicoeuer's table.
     */
    @Test
    public void testGetAmountOfSugarToAddLargeAdjustment() {
        PercentAlcoholByVolume targetAbv = new PercentAlcoholByVolume(10.86);
        double result = new ChaptalizationCalculator()
                .getAmountOfSugarToAdd(solution35DegSg, targetAbv)
                .getValue(UnitsOfMass.Grams);
        assertEquals(120, result, 0.25);
    }

    /** Confirm that a maximum magnitude chaptalization adjustment produces a result. */
    @Test
    public void testGetAmountOfSugarToAddMaximumAdjustment() {
        PercentAlcoholByVolume targetAbv = new PercentAlcoholByVolume(15.0);
        double result = new ChaptalizationCalculator()
                .getAmountOfSugarToAdd(solution35DegSg, targetAbv)
                .getValue(UnitsOfMass.Grams);

        assertEquals(207.7, result, 0.25);
    }

    /** Confirm that a minimum magnitude chaptalization to very sweet juice produces a result. */
    @Test
    public void testGetAmountOfSugarToAddTopEndAdjustment() {
        SpecificGravity specificGravity = new SpecificGravity(1.110095);
        AppleJuice juice = new AmericanAppleJuice();
        MassConcentration solids = juice.getTotalSolidsConcentration(specificGravity);
        MassConcentration sugar = juice.getSugarConcentrationProfile().getMaximumSugarConcentration(specificGravity);
        AqueousSolution solution = new AqueousSolution(specificGravity, solids, sugar, Volume.ONE_LITER);
        PercentAlcoholByVolume targetAbv = new PercentAlcoholByVolume(15.0);
        double result = new ChaptalizationCalculator()
                .getAmountOfSugarToAdd(solution, targetAbv)
                .getValue(UnitsOfMass.Grams);

        assertEquals(0.10, result, 0.003);
    }

    /** Confirm that zero is returned if no chaptalization is required. */
    @Test
    public void testGetAmountOfSugarToAddNoAdjustmentNecessary() {
        SpecificGravity specificGravity = new SpecificGravity(1.035);
        MassConcentration solids = new MassConcentration(new Mass(100, UnitsOfMass.Grams), Volume.ONE_LITER);
        MassConcentration sugar =
                new MassConcentration(new Mass(87.55238850911459, UnitsOfMass.Grams), Volume.ONE_LITER);
        AqueousSolution solution = new AqueousSolution(specificGravity, solids, sugar, Volume.ONE_LITER);
        PercentAlcoholByVolume targetAbv = new PercentAlcoholByVolume(5.0);
        double result = new ChaptalizationCalculator()
                .getAmountOfSugarToAdd(solution, targetAbv)
                .getValue(UnitsOfMass.Grams);

        assertEquals(0.0, result, 0.0);
    }

    /** Confirm that targeting an ABV above 15% throws. */
    @Test(expected = IllegalPropertyValueException.class)
    public void testGetAmountOfSugarToAddTargetAbvTooHigh() {
        PercentAlcoholByVolume targetAbv = new PercentAlcoholByVolume(15.0 + Math.ulp(15.0));
        new ChaptalizationCalculator().getAmountOfSugarToAdd(solution35DegSg, targetAbv);
    }

    /** Confirm that starting with an SG below 1.035 throws. */
    @Test(expected = IllegalPropertyValueException.class)
    public void testGetAmountOfSugarToAddSgTooLow() {
        SpecificGravity specificGravity = new SpecificGravity(1.035 - Math.ulp(1.035));
        MassConcentration sugar = new MassConcentration(new Mass(74.5, UnitsOfMass.Grams), Volume.ONE_LITER);
        MassConcentration solids = new MassConcentration(new Mass(90.7, UnitsOfMass.Grams), Volume.ONE_LITER);
        AqueousSolution solution = new AqueousSolution(specificGravity, solids, sugar, Volume.ONE_LITER);
        PercentAlcoholByVolume targetAbv = new PercentAlcoholByVolume(6.0);
        new ChaptalizationCalculator().getAmountOfSugarToAdd(solution, targetAbv);
    }

    /** Confirm that null solution throws. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAmountOfSugarToAddNullSolution() {
        PercentAlcoholByVolume targetAbv = new PercentAlcoholByVolume(15.0 + Math.ulp(15.0));
        new ChaptalizationCalculator().getAmountOfSugarToAdd(null, targetAbv);
    }

    /** Confirm that null target ABV throws. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAmountOfSugarToAddNullTargetAbv() {
        new ChaptalizationCalculator().getAmountOfSugarToAdd(solution35DegSg, null);
    }

}
