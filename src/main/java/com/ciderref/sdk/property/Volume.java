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
 * Represents a volume to the nearest microliter. Calculations (such are unit conversions) are performed with full
 * available precision. Immutable and thread-safe.
 */
public class Volume implements Comparable<Volume> {

    public enum Units {
        Milliliters, Liters, USGallons
    }

    private final double milliliters;

    /**
     * Constructor.
     *
     * @param value the size of the volume.
     * @param units (not null) the units of measurement in which the size of the volume is expressed.
     *
     * @throws IllegalArgumentException if {@code units} is {@code null}
     */
    public Volume(double value, Units units) {
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
        switch (units) {
            case Liters:
                this.milliliters = value * 1000.0;
                break;
            case USGallons:
                this.milliliters = value * 3785.41178;
                break;
            default: // Milliliters
                this.milliliters = value;
                break;
        }
    }

    /**
     * This volume expressed in a specific unit of measurement.
     *
     * @param units (not null) the unit of measurement in which to return this volume
     * @return this volume expressed in the given unit of measurement
     *
     * @throws IllegalArgumentException if {@code units} is null
     */
    public double getValue(Units units) {
        if (units == null) {
            throw new IllegalArgumentException("Volume cannot be represented without units of measurement.");
        }
        switch (units) {
            case Liters:
                return milliliters / 1000.0;
            case USGallons:
                return milliliters / 3785.411784;
            default: // Milliliters
                return milliliters;
        }
    }

    /**
     * Compares this volume to another volume. Note that values are rounded to the nearest microliter using the
     * "half up" rounding strategy prior to comparison.
     *
     * @param otherVolume the other volume
     * @return {@code true} if this volume is larger than {@code otherVolume}; {@code false} otherwise.
     *
     * @throws NullPointerException if {@code otherVolume} is null
     */
    @Override
    public int compareTo(Volume otherVolume) {
        return Long.compare(getComparableValue(), otherVolume.getComparableValue());
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
        return ((Long) getComparableValue()).hashCode();
    }

    // Returns volume in microliters. Used to sidestep floating point comparison issues.
    private long getComparableValue() {
        return Math.round(milliliters * 1000);
    }

}
