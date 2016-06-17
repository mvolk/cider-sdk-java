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

import static org.junit.Assert.assertEquals;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Units tests for {@link ConversionFactorFunction}.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class ConversionFactorFunctionTest {

    /** Factor constructor sets numerator correctly. */
    @Test
    public void testFactorConstructorSetsNumeratorCorrectly() {
        double numerator = 2948.29;
        ConversionFactorFunction function = new ConversionFactorFunction(numerator);
        assertEquals(numerator, function.getNumerator(), 0);
    }

    /** Factor constructor sets denominator correctly. */
    @Test
    public void testFactorConstructorSetsDenominatorCorrectly() {
        double numerator = 2948.29;
        ConversionFactorFunction function = new ConversionFactorFunction(numerator);
        assertEquals(1.0, function.getDenominator(), 0);
    }

    /** Ratio constructor sets numerator correctly. */
    @Test
    public void testRatiorConstructorSetsNumeratorCorrectly() {
        double numerator = 65308.39862;
        double denominator = 2948.29;
        ConversionFactorFunction function = new ConversionFactorFunction(numerator, denominator);
        assertEquals(numerator, function.getNumerator(), 0);
    }

    /** Ratio constructor sets denominator correctly. */
    @Test
    public void testRatioConstructorSetsDenominatorCorrectly() {
        double numerator = 65308.39862;
        double denominator = 2948.29;
        ConversionFactorFunction function = new ConversionFactorFunction(numerator, denominator);
        assertEquals(denominator, function.getDenominator(), 0);
    }

    /** Ratio constructor throws if denominator is zero. */
    @Test(expected = IllegalArgumentException.class)
    public void testRatioConstructorThrowsOnZeroDenominator() {
        double numerator = 65308.39862;
        double denominator = 0.0;
        new ConversionFactorFunction(numerator, denominator);
    }

    /** getInverse() sets numerator correctly. */
    @Test
    public void testInverseSetsNumeratorCorrectly() {
        double numerator = 65308.39862;
        double denominator = 2948.29;
        ConversionFactorFunction function = new ConversionFactorFunction(numerator, denominator);
        ConversionFactorFunction inverse = function.getInverse();
        assertEquals(function.getNumerator(), inverse.getDenominator(), 0);
    }

    /** getInverse() sets denominator correctly. */
    @Test
    public void testInverseSetsDenominatorCorrectly() {
        double numerator = 2948.29;
        double denominator = 65308.39862;
        ConversionFactorFunction function = new ConversionFactorFunction(numerator, denominator);
        ConversionFactorFunction inverse = function.getInverse();
        assertEquals(function.getDenominator(), inverse.getNumerator(), 0);
    }

    /** getInverse() of zero is not possible. */
    @Test(expected = NotInvertibleException.class)
    public void testInverseOfZero() {
        new ConversionFactorFunction(0.0).getInverse();
    }

    /** Inverse is true inverse for values exactly representable in double precision floating point. */
    @Test
    public void testInverseTrueInverse() {
        double numerator = 2;
        double denominator = 4;
        double value = 8;
        ConversionFactorFunction function = new ConversionFactorFunction(numerator, denominator);
        assertEquals(value, function.getInverse().applyTo(function.applyTo(value)), 0);
    }

    /** applyTo(0) returns 0. */
    @Test
    public void testApplyToZero() {
        assertEquals(0, new ConversionFactorFunction(832.249275, 25824.204).applyTo(0), 0);
    }

    /** applyTo(double) returns expected value. */
    @Test
    public void testApplyTo() {
        double numerator = 832.249275;
        double denominator = 25824.204;
        double inputValue = 47292.3;
        double expectedResult = inputValue * numerator / denominator;
        double actualResult = new ConversionFactorFunction(numerator, denominator).applyTo(inputValue);
        assertEquals(expectedResult, actualResult, 0);
    }

    /** Java .equals(Object) contract is met. */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void equalsContract() {
        EqualsVerifier
                .forClass(ConversionFactorFunction.class)
                .verify();
    }

    /** hashCode returns expected value. */
    @Test
    public void testHashCode() {
        double numerator = 832.249275;
        assertEquals(-364485169, new ConversionFactorFunction(numerator).hashCode());
    }

}
