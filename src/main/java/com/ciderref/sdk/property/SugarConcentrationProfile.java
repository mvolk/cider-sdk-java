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

package com.ciderref.sdk.property;

import com.ciderref.sdk.property.units.UnitsOfMass;

/**
 * Represent a standard distribution of sugar concentration as a function of specific gravity in a class of fluid.
 */
public class SugarConcentrationProfile {

    private final double averageCoefficient;
    private final double standardDeviation;

    /**
     * Constructor.
     *
     * @param averageCoefficient the coefficient that, when multiplied by the number of degrees of specific gravity,
     *                           yields the mass concentration of sugar in an average sample of a class of fluid.
     * @param standardDeviation the statistical standard deviation found in coefficients measured in samples of a
     *                          class of fluid.
     * @throws IllegalArgumentException if either {@code averageCoefficient} or {@code standardDeviation} are
     *         {@link Double#isNaN(double)}, {@link Double#isInfinite(double)}, or less than zero.
     */
    public SugarConcentrationProfile(double averageCoefficient, double standardDeviation) {
        if (Double.isNaN(averageCoefficient) || Double.isNaN(standardDeviation)) {
            throw new IllegalArgumentException("Must have numbers for sugar coefficient and standard deviation");
        }
        if (Double.isInfinite(averageCoefficient) || Double.isInfinite(standardDeviation)) {
            throw new IllegalArgumentException("Sugar coefficient and standard deviation must be finite");
        }
        if (Double.compare(averageCoefficient, 0) < 0 || Double.compare(standardDeviation, 0) < 0) {
            throw new IllegalArgumentException("Sugar coefficient and standard deviation cannot be less than zero");
        }
        this.averageCoefficient = averageCoefficient;
        this.standardDeviation = standardDeviation;
    }

    /**
     * The average sugar content of apple juice of the given density.
     *
     * @param specificGravity (not null) actual specific gravity.
     * @return (not null) the average amount of sugar in apple juice of the given specific gravity.
     * @throws IllegalArgumentException if {@code specificGravity} is null.
     */
    public MassConcentration getAverageSugarConcentration(SpecificGravity specificGravity) {
        requireSpecificGravity(specificGravity);
        double sg = specificGravity.getValue();
        double sc = averageCoefficient;
        return new MassConcentration(new Mass(sc * (sg - 1), UnitsOfMass.Grams), Volume.ONE_LITER);
    }

    /**
     * The minimum sugar content of apple juice of the given density. "Minimum" in this context means two standard
     * deviations less than average.
     *
     * @param specificGravity (not null) actual specific gravity.
     * @return (not null) the minimum amount of sugar in apple juice of the given specific gravity.
     * @throws IllegalArgumentException if {@code specificGravity} is null.
     */
    public MassConcentration getMinimumSugarConcentration(SpecificGravity specificGravity) {
        requireSpecificGravity(specificGravity);
        double sg = specificGravity.getValue();
        double sc = averageCoefficient - 2 * standardDeviation;
        return new MassConcentration(new Mass(sc * (sg - 1), UnitsOfMass.Grams), Volume.ONE_LITER);
    }

    /**
     * The maximum sugar content of apple juice of the given density. "Maximum" in this context means two standard
     * deviations more than average.
     *
     * @param specificGravity (not null) actual specific gravity.
     * @return (not null) the maximum amount of sugar in apple juice of the given specific gravity.
     * @throws IllegalArgumentException if {@code specificGravity} is null.
     */
    public MassConcentration getMaximumSugarConcentration(SpecificGravity specificGravity) {
        requireSpecificGravity(specificGravity);
        double sg = specificGravity.getValue();
        double sc = averageCoefficient + 2 * standardDeviation;
        return new MassConcentration(new Mass(sc * (sg - 1), UnitsOfMass.Grams), Volume.ONE_LITER);
    }

    private void requireSpecificGravity(SpecificGravity specificGravity) {
        if (specificGravity == null) {
            throw new IllegalArgumentException("Specific gravity is required.");
        }
    }

    /**
     * Determines whether this object is "equal" to another object.
     *
     * <p>This object is "equal" to another object if and only if:
     * <ul>
     *     <li>The other object is not {@code null}</li>
     *     <li>The other object is an {@code instanceof} this class</li>
     *     <li>This object has the same {@code averageCoefficient} and {@code standardDeviation} as the other
     *     object</li>
     * </ul>
     *
     * @param other (nullable) the object to compare with this object
     * @return true if the two objects are "equal" according to the criteria above.
     */
    @Override
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SugarConcentrationProfile)) {
            return false;
        }
        SugarConcentrationProfile that = (SugarConcentrationProfile) other;
        return Double.compare(that.averageCoefficient, averageCoefficient) == 0
                && Double.compare(that.standardDeviation, standardDeviation) == 0;
    }

    /**
     * A hash code for this sugar concentration profile.
     *
     * @return a hash code value for this object
     */
    @Override
    public final int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(averageCoefficient);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(standardDeviation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}
