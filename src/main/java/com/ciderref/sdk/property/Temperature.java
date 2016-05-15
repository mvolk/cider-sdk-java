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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a temperature. Provides methods for converting between units of temperature measurement. Immutable and
 * thread-safe.
 */
public class Temperature implements Comparable<Temperature> {

    private final double degreesC;

    /**
     * Constructor.
     *
     * @param temperature the temperature
     * @param units (not null) the unit of measurement in which {code temperature} is expressed
     */
    public Temperature(double temperature, @NotNull TemperatureUnits units) {
        this.degreesC = units.convert(temperature, TemperatureUnits.Celsius);
    }

    /**
     * This temperature expressed in a specific unit of measurement.
     *
     * @param units the unit of measurement in which to return this temperature
     * @return this temperature expressed in the given unit of measurement
     */
    public double getValue(@NotNull TemperatureUnits units) {
        return TemperatureUnits.Celsius.convert(degreesC, units);
    }

    /**
     * Compares one temperature to another in order from coldest to warmest.
     *
     * @param otherTemperature the other temperature
     * @return the value {@code 0} if this temperature is the same as {@code t}; a value less than
     *         {@code 0} if this temperature is cooler than {@code t}; and a value greater than {@code 0}
     *         if this temperature is warmer than {@code t}.
     */
    @Override
    public int compareTo(@NotNull Temperature otherTemperature) {
        return Double.compare(degreesC, otherTemperature.degreesC);
    }

    /**
     * Determines whether this object is "equal" to another object.
     *
     * <p>This object is "equal" to another object if and only if:
     * <ul>
     *     <li>The other object is not null</li>
     *     <li>The other object is an {code instanceof} this class</li>
     *     <li>This object {@link #compareTo(Temperature)} the other object returns {@code 0} (equivalent)</li>
     * </ul>
     *
     * @param other (nullable) the object to compare with this object
     * @return true if the two objects are "equal" according to the criteria above.
     */
    @Override
    public final boolean equals(@Nullable Object other) {
        return other instanceof Temperature && (other == this || this.compareTo((Temperature) other) == 0);
    }

    /**
     * A units-agnostic hash code for this temperature.
     *
     * @return a hash code value for this object
     */
    @Override
    public final int hashCode() {
        return ((Double) degreesC).hashCode();
    }

}