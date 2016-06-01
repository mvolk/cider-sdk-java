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
 * The sugar content in a aqueous solution. One degree Brix is equivalent to one gram of sucrose per 100 grams of pure
 * water.
 */
public class DegreesBrix implements Comparable<DegreesBrix> {

    private final double value;
    private final Long comparableBrix;

    /**
     * Constructor.
     *
     * @param value degrees DegreesBrix.
     * @throws IllegalArgumentException if {@code value} is less than zero, is infinite or is not a number.
     */
    public DegreesBrix(double value) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("A Brix value must be represented by a number.");
        }
        if (Double.isInfinite(value)) {
            throw new IllegalArgumentException("A Brix value must be finite.");
        }
        if (Double.compare(value, 0) < 0) {
            throw new IllegalArgumentException("A Brix value of less than zero is not possible.");
        }
        this.value = value;
        this.comparableBrix = Math.round(value * 100000);
    }

    /**
     * Obtain the numeric value of this DegreesBrix.
     *
     * @return degrees DegreesBrix.
     */
    public double getValue() {
        return value;
    }


    /**
     * Compares this object to another {@link DegreesBrix}. Note that consistent with the 5 decimal place accuracy of
     * the Brix scale, values are compared after rounding them half-up to five decimal places.
     *
     * @param otherDegreesBrix the other Brix
     * @return the value {@code 0} if this Brix value is the same as {@code otherDegreesBrix}; a value less than
     *         {@code 0} if this Brix value is less than {@code otherDegreesBrix}; and a value greater than {@code 0}
     *         if this Brix value is larger than {@code otherDegreesBrix}.
     *
     * @throws NullPointerException if {@code otherDegreesBrix} is null
     */
    @Override
    public int compareTo(DegreesBrix otherDegreesBrix) {
        return comparableBrix.compareTo(otherDegreesBrix.comparableBrix);
    }

    /**
     * Determines whether this object is "equal" to another object.
     *
     * <p>This object is "equal" to another object if and only if:
     * <ul>
     *     <li>The other object is not {@code null}</li>
     *     <li>The other object is an {@code instanceof} this class</li>
     *     <li>This object {@link #compareTo(DegreesBrix)} the other object returns {@code 0} (equivalent)</li>
     * </ul>
     *
     * @param other (nullable) the object to compare with this object
     * @return true if the two objects are "equal" according to the criteria above.
     */
    @Override
    public final boolean equals(Object other) {
        return other instanceof DegreesBrix && (other == this || this.compareTo((DegreesBrix) other) == 0);
    }

    /**
     * A hash code for this Brix value.
     *
     * @return a hash code value for this object
     */
    @Override
    public final int hashCode() {
        return comparableBrix.hashCode();
    }

}
