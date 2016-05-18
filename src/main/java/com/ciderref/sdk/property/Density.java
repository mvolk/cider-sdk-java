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
 * Represents volumic mass. Immutable and thread-safe.
 */
public class Density implements Comparable<Density> {

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
    public Density(Mass mass, Volume volume) {
        if (mass == null || volume == null) {
            throw new IllegalArgumentException("Density requires knowledge of both mass and volume.");
        }
        if (ZERO_VOLUME.equals(volume)) {
            throw new IllegalArgumentException("Density is undefined when the mass does not occupy any volume.");
        }
        this.mass = mass;
        this.volume = volume;
    }

    /**
     * This density expressed in specific units of measurement.
     *
     * @param massUnits (not null) the mass unit of measurement in which to return this volume; e.g. the unit of
     *                  measurement in the numerator in the unit expression.
     * @param volumeUnits (not null) the volume unit of measurement in which to return this volume; e.g. the
     *                    unit of measurement in the denominator in the unit expression.
     * @return this density expressed in {@code massUnits} per {@code volumeUnits}.
     *
     * @throws IllegalArgumentException if either {@code massUnits} or {@code volumeUnits} is {@code null}
     */
    public double getValue(Mass.Units massUnits, Volume.Units volumeUnits) {
        if (massUnits == null || volumeUnits == null) {
            throw new IllegalArgumentException("Density cannot be represented without units of measurement for both "
                    + "mass and volume.");
        }
        return mass.getValue(massUnits) / volume.getValue(volumeUnits);
    }

    /**
     * Compares this density to another density. Note that values that are within 1/100th of a gram per liter of each
     * other are considered equivalent.
     *
     * @param otherDensity the other density
     * @return {@code true} if this density is higher than {@code otherDensity}; {@code false} otherwise.
     *
     * @throws NullPointerException if {@code otherDensity} is null
     */
    @Override
    public int compareTo(Density otherDensity) {
        double thisDensityNormalized = getValue(Mass.Units.Grams, Volume.Units.Liters);
        double otherDensityNormalized = otherDensity.getValue(Mass.Units.Grams, Volume.Units.Liters);
        if (Math.abs(thisDensityNormalized - otherDensityNormalized) <= 0.01) {
            return 0;
        } else {
            return Double.compare(thisDensityNormalized, otherDensityNormalized);
        }
    }

    /**
     * Determines whether this object is "equal" to another object.
     *
     * <p>This object is "equal" to another object if and only if:
     * <ul>
     *     <li>The other object is not {@code null}</li>
     *     <li>The other object is an {@code instanceof} this class</li>
     *     <li>This object {@link #compareTo(Density)} the other object returns {@code 0} (equivalent)</li>
     * </ul>
     *
     * @param other (nullable) the object to compare with this object
     * @return true if the two objects are "equal" according to the criteria above.
     */
    @Override
    public final boolean equals(Object other) {
        return other instanceof Density && (other == this || this.compareTo((Density) other) == 0);
    }

    /**
     * A units-agnostic hash code for this density.
     *
     * @return a hash code value for this object
     */
    @Override
    public final int hashCode() {
        return mass.hashCode() ^ volume.hashCode();
    }

}
