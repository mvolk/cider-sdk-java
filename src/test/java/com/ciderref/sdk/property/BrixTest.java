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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * Unit tests for {@link Brix}.
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessivePublicCount", "PMD.TooManyStaticImports"})
public class BrixTest {

    /** Constructing with value less than zero produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeThrows() {
        // -0.0 is equivalent to 0.0 according to Double.compare, so go one ulp less to test the boundary
        new Brix(-0.0 - Math.ulp(-0.0));
    }

    /** Constructing with positive zero is valid. */
    @Test
    public void testConstructorZeroValid() {
        assertEquals(0, new Brix(+0).getValue(), 0);
    }

    /** Constructing with 25 is valid. */
    @Test
    public void testConstructor25Valid() {
        assertEquals(25, new Brix(25).getValue(), 0);
    }

    /** Constructing with more than 25 is not valid. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOver25Valid() {
        new Brix(25 + Math.ulp(25));
    }

    /**
     * Constructing with {@link Double#NaN} produces an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNaNThrows() {
        new Brix(Double.NaN);
    }

    /** Constructing with negative infinity produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeInfinityThrows() {
        new Brix(Double.NEGATIVE_INFINITY);
    }

    /** Constructing with positive infinity produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPositiveInfinityThrows() {
        new Brix(Double.POSITIVE_INFINITY);
    }

    /** Comparison to null yields a NPE. */
    @Test(expected = NullPointerException.class)
    public void testCompareToNullThrows() {
        //noinspection ConstantConditions
        new Brix(10).compareTo(null);
    }

    /** 8 degrees Brix is less than 8.00001 degrees Brix. */
    @Test
    public void testCompareMinimumResolution() {
        assertEquals(-1, new Brix(8).compareTo(new Brix(8.00001)));
    }

    /** 8.00001 degrees Brix is more than 8 degrees Brix. */
    @Test
    public void testCompareMinimumResolutionInverse() {
        assertEquals(1, new Brix(8.00001).compareTo(new Brix(8)));
    }

    /** 8 degrees Brix equals 8.0000049999 degrees Brix. */
    @Test
    public void testCompareWithinMinimumResolution() {
        assertEquals(0, new Brix(8).compareTo(new Brix(8.000005 - Math.ulp(8.000005))));
    }

    /** 8 degrees Brix equals 8 degrees Brix. */
    @Test
    public void testCompare8BrixIs8Brix() {
        assertEquals(0, new Brix(8).compareTo(new Brix(8)));
    }

    /** Not equal to null. */
    @Test
    public void testBrixIsNotEqualToNull() {
        //noinspection ObjectEqualsNull
        assertFalse(new Brix(20).equals(null)); // NOPMD - equals(null) is the point of this test
    }

    /** Not equal to a String. */
    @Test
    public void testBrixIsNotEqualToString() {
        //noinspection EqualsBetweenInconvertibleTypes
        assertFalse(new Brix(15).equals("15"));
    }

    /** 0 degrees Brix equals 0 degrees Brix. */
    @Test
    public void test0Equals0() {
        assertTrue(new Brix(0).equals(new Brix(0)));
    }

    /** 3 degrees Brix is not equal to 3.00001 degrees Brix. */
    @Test
    public void testMinimumResolution() {
        assertFalse(new Brix(3).equals(new Brix(3.00001)));
    }

    /** Brix equals itself. */
    @Test
    public void testBrixEqualsItself() {
        Brix mass = new Brix(2);
        @SuppressWarnings("UnnecessaryLocalVariable")
        Brix itself = mass;
        assertTrue(mass.equals(itself)); // NOPMD - exercising .equals(Object) is the point of this test
    }

    /** Java .equals(Object) contract is met. */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void equalsContract() {
        EqualsVerifier
                .forClass(Brix.class)
                .withIgnoredFields("value")
                .suppress(Warning.NULL_FIELDS) // comparableValue should never be null
                .verify();
    }

    /** Hash code value is as expected. */
    @Test
    public void testHashCode() {
        assertEquals(new Long(100000).hashCode(), new Brix(1).hashCode());
    }

}
