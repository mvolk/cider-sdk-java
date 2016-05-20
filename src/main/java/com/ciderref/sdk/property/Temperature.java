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
 * Represents a temperature to the nearest hundredth of a degree Celsius. Calculations (such are unit conversions) are
 * performed with full available precision. Immutable and thread-safe.
 */
public class Temperature implements Comparable<Temperature> {

    public enum Units { Celsius, Fahrenheit }

    private final double degreesC;

    /**
     * Constructor.
     *
     * @param temperature the temperature
     * @param units (not null) the unit of measurement in which {@code temperature} is expressed
     *
     * @throws NullPointerException if {@code units} is null
     */
    public Temperature(double temperature, Units units) {
        if (units == null) {
            throw new IllegalArgumentException("Temperature cannot be represented without units of measurement.");
        }
        if (Double.isNaN(temperature)) {
            throw new IllegalArgumentException("The magnitude of a temperature must be represented by a number.");
        }
        if (Double.isInfinite(temperature)) {
            throw new IllegalArgumentException("This implementation does not support representation of infinite "
                    + "temperature.");
        }
        if (units == Units.Celsius) {
            this.degreesC = temperature;
        } else {
            this.degreesC = (temperature - 32.0) * 5.0 / 9.0;
        }
    }

    /**
     * This temperature expressed in a specific unit of measurement.
     *
     * @param units (not null) the unit of measurement in which to return this temperature
     * @return this temperature expressed in the given unit of measurement
     *
     * @throws IllegalArgumentException if {@code units} is null
     */
    public double getValue(Units units) {
        if (units == null) {
            throw new IllegalArgumentException("Temperature cannot be represented without units of measurement.");
        } else if (units == Units.Celsius) {
            return degreesC;
        } else {
            return degreesC * 9.0 / 5.0 + 32.0;
        }
    }

    /**
     * Compares one temperature to another in order from coldest to warmest. Note that values are rounded to the
     * nearest hundredth of a degree Celsius using the "half up" rounding strategy prior to comparison.
     *
     * @param otherTemperature the other temperature
     * @return the value {@code 0} if this temperature is the same as {@code otherTemperature}; a value less than
     *         {@code 0} if this temperature is cooler than {@code otherTemperature}; and a value greater than {@code 0}
     *         if this temperature is warmer than {@code otherTemperature}.
     *
     * @throws NullPointerException if {@code otherTemperature} is null
     */
    @Override
    public int compareTo(Temperature otherTemperature) {
        return Long.compare(getComparableValue(), otherTemperature.getComparableValue());
    }

    /**
     * Compares this temperature to another temperature.
     *
     * @param otherTemperature the other temperature
     * @return {@code true} if this temperature is warmer than {@code otherTemperature}; {@code false} otherwise.
     *
     * @throws NullPointerException if {@code otherTemperature} is null
     */
    public boolean isWarmerThan(Temperature otherTemperature) {
        return compareTo(otherTemperature) > 0;
    }

    /**
     * Compares this temperature to another temperature.
     *
     * @param otherTemperature the other temperature
     * @return {@code true} if this temperature is colder than {@code otherTemperature}; {@code false} otherwise.
     *
     * @throws NullPointerException if {@code otherTemperature} is null
     */
    public boolean isCoolerThan(Temperature otherTemperature) {
        return compareTo(otherTemperature) < 0;
    }

    /**
     * Determines whether this object is "equal" to another object.
     *
     * <p>This object is "equal" to another object if and only if:
     * <ul>
     *     <li>The other object is not {@code null}</li>
     *     <li>The other object is an {@code instanceof} this class</li>
     *     <li>This object {@link #compareTo(Temperature)} the other object returns {@code 0} (equivalent)</li>
     * </ul>
     *
     * @param other (nullable) the object to compare with this object
     * @return true if the two objects are "equal" according to the criteria above.
     */
    @Override
    public final boolean equals(Object other) {
        return other instanceof Temperature && (other == this || this.compareTo((Temperature) other) == 0);
    }

    /**
     * A units-agnostic hash code for this temperature.
     *
     * @return a hash code value for this object
     */
    @Override
    public final int hashCode() {
        return ((Long) getComparableValue()).hashCode();
    }

    // Returns temperature in hundredths of a degree Celsius. Used to sidestep floating point comparison issues.
    private long getComparableValue() {
        return Math.round(degreesC * 100);
    }

}