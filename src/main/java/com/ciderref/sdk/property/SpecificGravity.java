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

import com.ciderref.sdk.substance.Water;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Represents the ratio of the density of some liquid (typically apple juice, must or hard cider in this context) to
 * the density of pure water. This is a dimensionless value. Immutable and thread-safe.
 */
public class SpecificGravity implements Comparable<SpecificGravity> {

    /** The lowest specific gravity that the CiderRef SDK supports. */
    public static final double MINIMUM_SUPPORTED_VALUE = 0.990;

    /** The highest specific gravity that the CiderRef SDK supports. */
    public static final double MAXIMUM_SUPPORTED_VALUE = 1.100;

    private static final NumberFormat FORMAT = new DecimalFormat("0.000");

    private final double value;

    /**
     * Constructor for values that are already corrected for temperature.
     *
     * @param actualSpecificGravity actual specific gravity, after correcting for temperature
     * @throws IllegalPropertyValueException if the {@code value} is less than {@link #MINIMUM_SUPPORTED_VALUE} or
     *         exceeds {@link #MAXIMUM_SUPPORTED_VALUE}.
     */
    public SpecificGravity(double actualSpecificGravity) {
        checkSpecificGravityValidity(actualSpecificGravity, "specific gravity");
        this.value = actualSpecificGravity;
    }

    /**
     * Constructor for values that are not already corrected for temperature.
     *
     * @param measuredSpecificGravity measured specific gravity of the fluid, before correcting for temperature
     * @param measuredTemperature (not null) measured temperature of the fluid
     * @param calibrationTemperature (not null) the calibration temperature of the hydrometer used to measure the
     *                               specific gravity
     * @throws IllegalArgumentException if {@code measuredTemperature} or {@code calibrationTemperature} is
     *         {@code null}.
     * @throws IllegalPropertyValueException if the {@code measuredTemperature} or {@code calibrationTemperature} is
     *         not between {@link Water#STANDARD_FREEZING_POINT} and {@link Water#STANDARD_BOILING_POINT}, inclusive.
     * @throws IllegalPropertyValueException if the measured or corrected specific gravity is not between
     *         {@link #MINIMUM_SUPPORTED_VALUE} and {@link #MAXIMUM_SUPPORTED_VALUE}, inclusive.
     */
    public SpecificGravity(double measuredSpecificGravity,
                           Temperature measuredTemperature,
                           Temperature calibrationTemperature) {

        checkSpecificGravityValidity(measuredSpecificGravity, "measured specific gravity");

        Water water = new Water();
        double densityOfWaterAtCalibrationTemperature =
                water.getDensity(calibrationTemperature).getValueInGramsPerLiter();
        double densityOfWaterAtActualTemperature =
                water.getDensity(measuredTemperature).getValueInGramsPerLiter();
        double correctionFactor = densityOfWaterAtCalibrationTemperature / densityOfWaterAtActualTemperature;
        this.value = correctionFactor * measuredSpecificGravity;

        checkSpecificGravityValidity(this.value, "corrected specific gravity");
    }

    private void checkSpecificGravityValidity(double specificGravity, String name) {
        if (specificGravity < MINIMUM_SUPPORTED_VALUE || specificGravity > MAXIMUM_SUPPORTED_VALUE) {
            throw new IllegalPropertyValueException("The " + name + ", " + FORMAT.format(specificGravity)
                    + ", is not within the range of supported specific gravity values. Specific gravities between "
                    + FORMAT.format(MINIMUM_SUPPORTED_VALUE) + " and " + FORMAT.format(MAXIMUM_SUPPORTED_VALUE)
                    + ", inclusive, are supported.");
        }
    }

    /**
     * The actual (corrected) specific gravity.
     *
     * @return actual specific gravity.
     */
    public double getValue() {
        return value;
    }

    /**
     * Compares one specific gravity to another in order from least dense to most dense.
     *
     * @param otherSpecificGravity the other specificGravity
     * @return the value {@code 0} if this specific gravity is the same as {@code otherSpecificGravity}; a value less
     *         than {@code 0} if this specific gravity is lower than {@code otherSpecificGravity}; and a value greater
     *         than {@code 0} if this specific gravity is higher than {@code otherSpecificGravity}.
     *
     * @throws NullPointerException if {@code otherSpecificGravity} is null
     */
    @Override
    public int compareTo(SpecificGravity otherSpecificGravity) {
        return Double.compare(value, otherSpecificGravity.value);
    }

    /**
     * Determines whether this object is "equal" to another object.
     *
     * <p>This object is "equal" to another object if and only if:
     * <ul>
     *     <li>The other object is not null</li>
     *     <li>The other object is an {code instanceof} this class</li>
     *     <li>This object {@link #compareTo(SpecificGravity)} the other object returns {@code 0} (equivalent)</li>
     * </ul>
     *
     * @param other (nullable) the object to compare with this object
     * @return true if the two objects are "equal" according to the criteria above.
     */
    @Override
    public final boolean equals(Object other) {
        return other instanceof SpecificGravity && (other == this || this.compareTo((SpecificGravity) other) == 0);
    }

    /**
     * A hash code for this specific gravity.
     *
     * @return a hash code value for this object
     */
    @Override
    public final int hashCode() {
        return ((Double) value).hashCode();
    }

    /**
     * The standard "0.000"-format representation of this specific gravity.
     *
     * @return (not null) textual representation of this specific gravity
     */
    @Override
    public String toString() {
        return FORMAT.format(value);
    }

}
