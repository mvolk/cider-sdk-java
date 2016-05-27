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

/**
 * A measure of alcohol content. Expressed as 100 times the ratio of the partial volume of the pure ethanol in a
 * solution to the total volume of the solution at 20 degrees Celsius. Given as a percentage instead of a fraction.
 * This is a dimensionless value.
 */
public class PercentAlcoholByVolume implements Comparable<PercentAlcoholByVolume> {

    private final double value;
    private final Long comparableValue;

    /**
     * Constructor.
     *
     * @param abv % alcohol by volume
     * @throws IllegalPropertyValueException if the {@code abv} is less than {@code 0}, exceeds {@code 100}, ir
     *         infinite, or is {@link Double#NaN}.
     */
    public PercentAlcoholByVolume(double abv) {
        if (Double.isNaN(abv)) {
            throw new IllegalArgumentException("ABV cannot be represented without a number");
        }
        if (Double.isInfinite(abv) || Double.compare(abv, 0) < 0 || Double.compare(abv, 100) > 0) {
            throw new IllegalPropertyValueException("ABV cannot be less than 0% nor more than 100%");
        }
        this.value = abv;
        this.comparableValue = Math.round(abv * 100);
    }

    /**
     * The ABV.
     *
     * @return %ABV
     */
    public double getValue() {
        return value;
    }

    /**
     * Compares one %ABV to another in order from least alcohol to most alcohol per volume. Two ABVs are equivalent
     * if they are within 0.01 percentage points of each other.
     *
     * @param otherAbv the other alcohol by volume
     * @return the value {@code 0} if this abv is the same as {@code otherABV}; a value less
     *         than {@code 0} if this abv is lower than {@code otherABV}; and a value greater
     *         than {@code 0} if this abv is higher than {@code otherABV}.
     *
     * @throws NullPointerException if {@code otherABV} is null
     */
    @Override
    public int compareTo(PercentAlcoholByVolume otherAbv) {
        return comparableValue.compareTo(otherAbv.comparableValue);
    }

    /**
     * Determines whether this object is "equal" to another object.
     *
     * <p>This object is "equal" to another object if and only if:
     * <ul>
     *     <li>The other object is not null</li>
     *     <li>The other object is an {code instanceof} this class</li>
     *     <li>This object {@link #compareTo(PercentAlcoholByVolume)} the other object returns {@code 0}
     *     (equivalent)</li>
     * </ul>
     *
     * @param other (nullable) the object to compare with this object
     * @return true if the two objects are "equal" according to the criteria above.
     */
    @Override
    public final boolean equals(Object other) {
        return other instanceof PercentAlcoholByVolume
                && (other == this || this.compareTo((PercentAlcoholByVolume) other) == 0);
    }

    /**
     * A hash code for this ABV.
     *
     * @return a hash code value for this object
     */
    @Override
    public final int hashCode() {
        return comparableValue.hashCode();
    }

}
