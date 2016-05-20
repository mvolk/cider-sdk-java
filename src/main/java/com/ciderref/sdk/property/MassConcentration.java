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
 * Represents the mass of a constituent substance in a mixture divided by the volume of the mixture. Immutable and
 * thread-safe.
 */
public class MassConcentration implements Comparable<MassConcentration> {

    private static final Volume ZERO_VOLUME = new Volume(0, Volume.Units.Milliliters);

    private final Mass mass;
    private final Volume volume;

    /**
     * Constructor.
     *
     * @param mass (not null) the amount of mass in the {@code volume}.
     * @param volume (not null) the size of the volume.
     *
     * @throws IllegalArgumentException if either {@code mass} or {@code volume} is {@code null} or if the
     *         {@code volume} is zero.
     */
    public MassConcentration(Mass mass, Volume volume) {
        if (mass == null || volume == null) {
            throw new IllegalArgumentException("MassConcentration requires knowledge of both mass and volume.");
        }
        if (ZERO_VOLUME.equals(volume)) {
            throw new IllegalArgumentException("MassConcentration is undefined when the mass does not occupy "
                    + "any volume.");
        }
        this.mass = mass;
        this.volume = volume;
    }

    /**
     * This mass concentration expressed in specific units of measurement.
     *
     * @param massUnits (not null) the mass unit of measurement in which to return this volume; e.g. the unit of
     *                  measurement in the numerator in the unit expression.
     * @param volumeUnits (not null) the volume unit of measurement in which to return this volume; e.g. the
     *                    unit of measurement in the denominator in the unit expression.
     * @return this mass concentration expressed in {@code massUnits} per {@code volumeUnits}.
     *
     * @throws IllegalArgumentException if either {@code massUnits} or {@code volumeUnits} is {@code null}
     */
    public double getValue(Mass.Units massUnits, Volume.Units volumeUnits) {
        if (massUnits == null || volumeUnits == null) {
            throw new IllegalArgumentException("MassConcentration cannot be represented without units of measurement "
                    + "for both mass and volume.");
        }
        return mass.getValue(massUnits) / volume.getValue(volumeUnits);
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
        return Long.compare(getComparableValue(), otherMassConcentration.getComparableValue());
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
        return ((Long) getComparableValue()).hashCode();
    }

    // Returns temperature in hundredths of a gram per liter. Used to sidestep floating point comparison issues.
    private long getComparableValue() {
        return  Math.round(getValue(Mass.Units.Grams, Volume.Units.Liters) * 100);
    }

}
