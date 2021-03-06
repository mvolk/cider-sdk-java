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
import com.ciderref.sdk.property.units.UnitsOfVolume;

/**
 * Represents the mass of a constituent substance in a mixture divided by the volume of the mixture. Immutable and
 * thread-safe.
 */
public class MassConcentration implements Comparable<MassConcentration> {

    private static final Volume ZERO_VOLUME = new Volume(0, UnitsOfVolume.Milliliters);

    private final Mass mass;
    private final Volume volume;
    private final Long comparableValue;

    /**
     * Constructor.
     *
     * @param mass (not null) the mass of the substance of interest in the {@code volume}.
     * @param volume (not null) the size of the volume.
     *
     * @throws IllegalArgumentException if either {@code mass} or {@code volume} is {@code null} or if the
     *         {@code volume} is zero.
     */
    public MassConcentration(Mass mass, Volume volume) {
        if (mass == null || volume == null) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + " requires knowledge of both mass and volume.");
        }
        if (ZERO_VOLUME.equals(volume)) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + " is undefined when the mass does not occupy any volume.");
        }
        this.mass = mass;
        this.volume = volume;
        this.comparableValue = Math.round(getValueInGramsPerLiter() * 100);
    }

    /**
     * This mass concentration expressed in specific units of measurement.
     *
     * @param unitsOfMass (not null) the mass unit of measurement in which to return this volume; e.g. the unit of
     *                  measurement in the numerator in the unit expression.
     * @param unitsOfVolume (not null) the volume unit of measurement in which to return this volume; e.g. the
     *                    unit of measurement in the denominator in the unit expression.
     * @return this mass concentration expressed in {@code unitsOfMass} per {@code unitsOfVolume}.
     *
     * @throws IllegalArgumentException if either {@code unitsOfMass} or {@code unitsOfVolume} is {@code null}
     */
    public final double getValue(UnitsOfMass unitsOfMass, UnitsOfVolume unitsOfVolume) {
        if (unitsOfMass == null || unitsOfVolume == null) {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + " cannot be represented without units of measurement for both mass and volume.");
        }
        return mass.getValue(unitsOfMass) / volume.getValue(unitsOfVolume);
    }

    /**
     * Convenience method for obtaining mass concentration expressed in grams per liter.
     *
     * @return this mass concentration expressed in {@link UnitsOfMass#Grams} per {@link UnitsOfVolume#Liters}.
     */
    public final double getValueInGramsPerLiter() {
        return getValue(UnitsOfMass.Grams, UnitsOfVolume.Liters);
    }

    /**
     * Compares this mass concentration to another mass concentration. Note that values that are within 1/100th of a
     * gram per liter of each other are considered equivalent.
     *
     * @param otherMassConcentration the other mass concentration
     * @return the value {@code 0} if this mass concentration is the same as {@code otherMassConcentration}; a value
     *         less than {@code 0} if this massConcentration is lower than {@code otherMassConcentration}; and a value
     *         greater than {@code 0} if this mass concentration is higher than {@code otherMassConcentration}.
     *
     * @throws NullPointerException if {@code otherMassConcentration} is null
     */
    @Override
    public int compareTo(MassConcentration otherMassConcentration) {
        return comparableValue.compareTo(otherMassConcentration.comparableValue);
    }

    /**
     * Determines whether this object is "equal" to another object.
     *
     * <p>This object is "equal" to another object if and only if:
     * <ul>
     *     <li>The other object is not {@code null}</li>
     *     <li>The other object is an {@code instanceof} this class</li>
     *     <li>This object {@link #compareTo(MassConcentration)} the other object returns {@code 0} (equivalent)</li>
     * </ul>
     *
     * @param other (nullable) the object to compare with this object
     * @return true if the two objects are "equal" according to the criteria above.
     */
    @Override
    public final boolean equals(Object other) {
        return other instanceof MassConcentration && (other == this || this.compareTo((MassConcentration) other) == 0);
    }

    /**
     * A units-agnostic hash code for this mass concentration.
     *
     * @return a hash code value for this object
     */
    @Override
    public final int hashCode() {
        return comparableValue.hashCode();
    }

}
