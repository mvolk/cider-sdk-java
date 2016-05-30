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

import com.ciderref.sdk.property.units.ConversionFunctions;
import com.ciderref.sdk.property.units.UnitsOfVolume;
import com.ciderref.sdk.property.units.VolumeConversionFunctions;

/**
 * Represents a volume to the nearest microliter. Calculations (such are unit conversions) are performed with full
 * available precision. Immutable and thread-safe.
 */
public class Volume implements Comparable<Volume> {

    /** One liter. */
    public static final Volume ONE_LITER = new Volume(1.0, UnitsOfVolume.Liters);

    private final double magnitude;
    private final UnitsOfVolume units;
    private final Long comparableValue;
    private final VolumeConversionFunctions conversion;

    /**
     * Constructor.
     *
     * @param value the size of the volume.
     * @param units (not null) the units of measurement in which the size of the volume is expressed.
     *
     * @throws IllegalArgumentException if {@code units} is {@code null}
     */
    public Volume(double value, UnitsOfVolume units) {
        if (units == null) {
            throw new IllegalArgumentException("Volume cannot be represented without units of measurement.");
        }
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("The magnitude of a volume must be represented by a number.");
        }
        if (Double.compare(value, 0.0) < 0) {
            throw new IllegalArgumentException("The magnitude of a volume cannot be less than zero.");
        }
        if (Double.isInfinite(value)) {
            throw new IllegalArgumentException("This implementation does not support representation of infinite "
                    + "volume.");
        }
        this.magnitude = value;
        this.units = units;
        this.conversion = ConversionFunctions.getForUnitsOfVolume();
        this.comparableValue = Math.round(getValue(UnitsOfVolume.Milliliters) * 1000);
    }

    /**
     * This volume expressed in a specific unit of measurement.
     *
     * @param units (not null) the unit of measurement in which to return this volume
     * @return this volume expressed in the given unit of measurement
     *
     * @throws IllegalArgumentException if {@code units} is null
     */
    public final double getValue(UnitsOfVolume units) {
        if (units == null) {
            throw new IllegalArgumentException("Volume cannot be represented without units of measurement.");
        }
        return conversion.getFunction(this.units, units).applyTo(magnitude);
    }

    /**
     * Compares this volume to another volume. Note that values are rounded to the nearest microliter using the
     * "half up" rounding strategy prior to comparison.
     *
     * @param otherVolume the other volume
     * @return the value {@code 0} if this volume is the same as {@code otherVolume}; a value less than
     *         {@code 0} if this volume is smaller than {@code otherVolume}; and a value greater than {@code 0}
     *         if this volume is larger than {@code otherVolume}.
     *
     * @throws NullPointerException if {@code otherTemperature} is null
     */
    @Override
    public int compareTo(Volume otherVolume) {
        return comparableValue.compareTo(otherVolume.comparableValue);
    }

    /**
     * Determines whether this object is "equal" to another object.
     *
     * <p>This object is "equal" to another object if and only if:
     * <ul>
     *     <li>The other object is not {@code null}</li>
     *     <li>The other object is an {@code instanceof} this class</li>
     *     <li>This object {@link #compareTo(Volume)} the other object returns {@code 0} (equivalent)</li>
     * </ul>
     *
     * @param other (nullable) the object to compare with this object
     * @return true if the two objects are "equal" according to the criteria above.
     */
    @Override
    public final boolean equals(Object other) {
        return other instanceof Volume && (other == this || this.compareTo((Volume) other) == 0);
    }

    /**
     * A units-agnostic hash code for this volume.
     *
     * @return a hash code value for this object
     */
    @Override
    public final int hashCode() {
        return comparableValue.hashCode();
    }

}
