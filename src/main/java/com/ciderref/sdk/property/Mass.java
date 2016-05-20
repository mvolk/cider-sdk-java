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
 * Represents a mass. Conversion factors from http://www.nist.gov/pml/wmd/pubs/upload/AppC-12-hb44-final.pdf.
 * Immutable and thread-safe.
 */
@SuppressWarnings("PMD.ShortClassName")
public class Mass implements Comparable<Mass> {

    public enum Units { Grams, Kilograms, Ounces, Pounds }

    private final double grams;

    /**
     * Constructor.
     *
     * @param value the amount of mass.
     * @param units (not null) the units of measurement in which the amount of mass is expressed. For units which
     *              represent weight, and not mass, standard Earth gravity is assumed.
     *
     * @throws IllegalArgumentException if {@code units} is {@code null}
     */
    public Mass(double value, Units units) {
        if (units == null) {
            throw new IllegalArgumentException("Mass cannot be represented without units of measurement.");
        }
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("The magnitude of a mass must be represented by a number.");
        }
        if (Double.compare(value, 0.0) < 0) {
            throw new IllegalArgumentException("The magnitude of a mass cannot be less than zero.");
        }
        if (Double.isInfinite(value)) {
            throw new IllegalArgumentException("This implementation does not support representation of infinite "
                    + "mass.");
        }
        switch (units) {
            case Kilograms:
                this.grams = value * 1000.0;
                break;
            case Ounces:
                this.grams = value * 28.349523125;
                break;
            case Pounds:
                this.grams = value * 453.59237;
                break;
            default: // Grams
                this.grams = value;
                break;
        }
    }

    /**
     * This mass expressed in a specific unit of measurement.
     *
     * @param units (not null) the unit of measurement in which to return this mass
     * @return this mass expressed in the given unit of measurement
     *
     * @throws IllegalArgumentException if {@code units} is null
     */
    public double getValue(Units units) {
        if (units == null) {
            throw new IllegalArgumentException("Mass cannot be represented without units of measurement.");
        }
        switch (units) {
            case Kilograms:
                return grams / 1000;
            case Ounces:
                return grams / 28.349523125;
            case Pounds:
                return grams / 453.59237;
            default: // Grams
                return grams;
        }
    }

    /**
     * Compares this mass to another mass. Note that values that are within 1/100th of a gram of each other
     * are considered equivalent.
     *
     * @param otherMass the other mass
     * @return {@code true} if this mass is larger than {@code otherMass}; {@code false} otherwise.
     *
     * @throws NullPointerException if {@code otherMass} is null
     */
    @Override
    public int compareTo(Mass otherMass) {
        return Long.compare(getComparableValue(), otherMass.getComparableValue());
    }

    /**
     * Determines whether this object is "equal" to another object.
     *
     * <p>This object is "equal" to another object if and only if:
     * <ul>
     *     <li>The other object is not {@code null}</li>
     *     <li>The other object is an {@code instanceof} this class</li>
     *     <li>This object {@link #compareTo(Mass)} the other object returns {@code 0} (equivalent)</li>
     * </ul>
     *
     * @param other (nullable) the object to compare with this object
     * @return true if the two objects are "equal" according to the criteria above.
     */
    @Override
    public final boolean equals(Object other) {
        return other instanceof Mass && (other == this || this.compareTo((Mass) other) == 0);
    }

    /**
     * A units-agnostic hash code for this mass.
     *
     * @return a hash code value for this object
     */
    @Override
    public final int hashCode() {
        return ((Long) getComparableValue()).hashCode();
    }

    // Returns temperature in hundredths of a gram. Used to sidestep floating point comparison issues.
    private long getComparableValue() {
        return Math.round(grams * 100);
    }

}
