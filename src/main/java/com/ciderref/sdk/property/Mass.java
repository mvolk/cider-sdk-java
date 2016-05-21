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
import com.ciderref.sdk.property.units.UnitsOfMass;
import com.ciderref.sdk.property.units.UnitsOfMassConversionFunctions;

/**
 * Represents a mass. Conversion factors from http://www.nist.gov/pml/wmd/pubs/upload/AppC-12-hb44-final.pdf.
 * Immutable and thread-safe.
 */
@SuppressWarnings("PMD.ShortClassName")
public class Mass implements Comparable<Mass> {

    private final double magnitude;
    private final UnitsOfMass units;
    private final Long comparableMass;
    private final UnitsOfMassConversionFunctions conversion;

    /**
     * Constructor.
     *
     * @param magnitude the amount of mass.
     * @param units (not null) the units of measurement in which the amount of mass is expressed. For units which
     *              represent weight, and not mass, standard Earth gravity is assumed.
     *
     * @throws IllegalArgumentException if {@code units} is {@code null}
     */
    public Mass(double magnitude, UnitsOfMass units) {
        if (units == null) {
            throw new IllegalArgumentException("Mass cannot be represented without units of measurement.");
        }
        if (Double.isNaN(magnitude)) {
            throw new IllegalArgumentException("The magnitude of a mass must be represented by a number.");
        }
        if (Double.compare(magnitude, 0.0) < 0) {
            throw new IllegalArgumentException("The magnitude of a mass cannot be less than zero.");
        }
        if (Double.isInfinite(magnitude)) {
            throw new IllegalArgumentException("This implementation does not support representation of infinite "
                    + "mass.");
        }
        this.conversion = ConversionFunctions.getForUnitsOfMass();
        this.magnitude = magnitude;
        this.units = units;
        this.comparableMass = Math.round(conversion.getFunction(units, UnitsOfMass.Grams).applyTo(magnitude) * 100);
    }

    /**
     * This mass expressed in a specific unit of measurement.
     *
     * @param units (not null) the unit of measurement in which to return this mass
     * @return this mass expressed in the given unit of measurement
     *
     * @throws IllegalArgumentException if {@code units} is null
     * @throws com.ciderref.sdk.property.units.UnsupportedConversionException if unable to convert to the given units.
     */
    public double getValue(UnitsOfMass units) {
        if (units == null) {
            throw new IllegalArgumentException("Mass cannot be represented without units of measurement.");
        }
        return conversion.getFunction(this.units, units).applyTo(magnitude);
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
        return comparableMass.compareTo(otherMass.comparableMass);
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
        return comparableMass.hashCode();
    }

}
