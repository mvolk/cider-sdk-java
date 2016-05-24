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
 * BRIX scale measure of sugar content in a fluid.
 */
@SuppressWarnings("PMD.ShortClassName")
public class Brix implements Comparable<Brix> {

    private final double value;
    private final Long comparableBrix;

    /**
     * Constructor.
     *
     * @param value degrees Brix.
     * @throws IllegalArgumentException if {@code value} is less than zero, exceeds 25, or is not a number.
     */
    public Brix(double value) {
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("A Brix value must be represented by a number.");
        }
        if (Double.compare(value, 0) < 0) {
            throw new IllegalArgumentException("A Brix value of less than zero is not possible.");
        }
        if (Double.compare(value, 25) > 0) {
            throw new IllegalArgumentException("Brix values in excess of 25 are very unlikely to be encountered in "
                    + "cidermaking and are not supported here.");
        }
        this.value = value;
        this.comparableBrix = Math.round(value * 100000);
    }

    /**
     * Obtain the numeric value of this Brix.
     *
     * @return degrees Brix.
     */
    public double getValue() {
        return value;
    }


    /**
     * Compares this Brix to another Brix. Note that consistent with the 5 decimal place accuracy of the Brix scale,
     * values are compared after rounding them half-up to five decimal places.
     *
     * @param otherBrix the other Brix
     * @return the value {@code 0} if this Brix is the same as {@code otherBrix}; a value less than
     *         {@code 0} if this Brix is less than {@code otherBrix}; and a value greater than {@code 0}
     *         if this Brix is larger than {@code otherBrix}.
     *
     * @throws NullPointerException if {@code otherBrix} is null
     */
    @Override
    public int compareTo(Brix otherBrix) {
        return comparableBrix.compareTo(otherBrix.comparableBrix);
    }

    /**
     * Determines whether this object is "equal" to another object.
     *
     * <p>This object is "equal" to another object if and only if:
     * <ul>
     *     <li>The other object is not {@code null}</li>
     *     <li>The other object is an {@code instanceof} this class</li>
     *     <li>This object {@link #compareTo(Brix)} the other object returns {@code 0} (equivalent)</li>
     * </ul>
     *
     * @param other (nullable) the object to compare with this object
     * @return true if the two objects are "equal" according to the criteria above.
     */
    @Override
    public final boolean equals(Object other) {
        return other instanceof Brix && (other == this || this.compareTo((Brix) other) == 0);
    }

    /**
     * A units-agnostic hash code for this Brix.
     *
     * @return a hash code value for this object
     */
    @Override
    public final int hashCode() {
        return comparableBrix.hashCode();
    }

}
