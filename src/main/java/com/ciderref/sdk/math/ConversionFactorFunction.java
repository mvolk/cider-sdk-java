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

package com.ciderref.sdk.math;

/**
 * Converts one value to another via multiplication by a factor or ratio. Immutable and thread-safe.
 */
public final class ConversionFactorFunction implements Function {
    private final double numerator;
    private final double denominator;

    /**
     * Construct a simple multiplicative conversion factor function.
     *
     * @param factor the factor by which to multiply a value to convert it to another value
     */
    public ConversionFactorFunction(double factor) {
        this.numerator = factor;
        this.denominator = 1.0;
    }

    /**
     * Construct a ratio-based conversion factor function.
     *
     * @param numerator the numerator of the ratio
     * @param denominator the denominator of the ratio
     * @throws IllegalArgumentException if denominator is zero.
     */
    public ConversionFactorFunction(double numerator, double denominator) {
        if (Double.compare(denominator, 0) == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero (divide by zero)");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Apply the conversion factor or ratio to a value.
     *
     * @param value an input value
     * @return the converted value
     */
    public double applyTo(double value) {
        return numerator * value / denominator;
    }

    /**
     * Get the inverse of this function.
     *
     * @return (not null) the inverse of this function
     */
    public ConversionFactorFunction getInverse() {
        if (Double.compare(numerator, 0.0) == 0) {
            return new ConversionFactorFunction(0.0);
        } else {
            return new ConversionFactorFunction(denominator, numerator);
        }
    }

    /**
     * Determine whether this function is equivalent to another function.
     *
     * @param obj (nullable) the object to compare
     * @return {@code true} if the two objects are equivalent; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ConversionFactorFunction that = (ConversionFactorFunction) obj;
        return Double.compare(that.numerator, numerator) == 0 && Double.compare(that.denominator, denominator) == 0;
    }

    /**
     * Hash code for this object.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(numerator);
        int result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(denominator);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Get the factor or ratio's numerator.
     *
     * @return numerator
     */
    public double getNumerator() {
        return numerator;
    }

    /**
     * Get the factor or ratio's denominator.
     *
     * @return denominator
     */
    public double getDenominator() {
        return denominator;
    }

}
