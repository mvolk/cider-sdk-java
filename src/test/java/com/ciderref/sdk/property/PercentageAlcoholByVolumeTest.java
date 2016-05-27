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
 * Unit tests for {@link PercentAlcoholByVolume}.
 */
@SuppressWarnings({"PMD.TooManyMethods"})
public class PercentageAlcoholByVolumeTest {

    /** Constructing with NaN produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNaNThrows() {
        new PercentAlcoholByVolume(Double.NaN);
    }

    /** Constructing with negative infinity produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeInfinityThrows() {
        new PercentAlcoholByVolume(Double.NEGATIVE_INFINITY);
    }

    /** Constructing with positive infinity produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPositiveInfinityThrows() {
        new PercentAlcoholByVolume(Double.POSITIVE_INFINITY);
    }

    /** Constructing with value less than zero produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeThrows() {
        // -0.0 is equivalent to 0.0 according to Double.compare, so go one ulp less to test the boundary
        new PercentAlcoholByVolume(-0.0 - Math.ulp(-0.0));
    }

    /** Constructing with value greater than 100 produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorOver100PercentThrows() {
        new PercentAlcoholByVolume(100 + Math.ulp(100));
    }

    /** Constructing with positive zero is valid. */
    @Test
    public void testConstructorZeroValid() {
        assertEquals(0, new PercentAlcoholByVolume(0).getValue(), 0);
    }

    /** Constructing with positive 100 is valid. */
    @Test
    public void testConstructor100Valid() {
        assertEquals(100, new PercentAlcoholByVolume(100).getValue(), 0);
    }

    /** Comparison to null yields a NPE. */
    @Test(expected = NullPointerException.class)
    public void testCompareToNullThrows() {
        //noinspection ConstantConditions
        new PercentAlcoholByVolume(50).compareTo(null);
    }

    /** 2.53 %ABV is 2.53 %ABV. */
    @Test
    public void testCompareSameValue() {
        assertEquals(0, new PercentAlcoholByVolume(2.53).compareTo(new PercentAlcoholByVolume(2.53)));
    }

    /** 2.525 %ABV is 2.53 %ABV. */
    @Test
    public void testCompareEquivalentValues() {
        assertEquals(0, new PercentAlcoholByVolume(2.525).compareTo(new PercentAlcoholByVolume(2.53)));
    }

    /** 5 %ABV is less than 10% ABV. */
    @Test
    public void testCompareLessThan() {
        assertEquals(-1, new PercentAlcoholByVolume(5).compareTo(new PercentAlcoholByVolume(10)));
    }

    /** 10 %ABV is more than 5% ABV. */
    @Test
    public void testCompareMoreThan() {
        assertEquals(1, new PercentAlcoholByVolume(10).compareTo(new PercentAlcoholByVolume(5)));
    }

    /** 5 %ABV is not equal to null. */
    @Test
    public void testNotEqualToNull() {
        //noinspection ObjectEqualsNull
        assertFalse(new PercentAlcoholByVolume(5).equals(null)); // NOPMD - equals(null) is the point of this test
    }

    /** 5 %ABV is not equal to "5 %ABV". */
    @Test
    public void testMassIsNotEqualToString() {
        //noinspection EqualsBetweenInconvertibleTypes
        assertFalse(new PercentAlcoholByVolume(5).equals("5 %ABV"));
    }

    /** 2.53 %ABV is 2.53 %ABV. */
    @Test
    public void testEqualsSameValue() {
        assertTrue(new PercentAlcoholByVolume(2.53).equals(new PercentAlcoholByVolume(2.53)));
    }

    /** 2.525 %ABV is 2.53 %ABV. */
    @Test
    public void testEqualsEquivalentValues() {
        assertTrue(new PercentAlcoholByVolume(2.525).equals(new PercentAlcoholByVolume(2.53)));
    }

    /** 2.5249... %ABV not equal 2.53% ABV. */
    @Test
    public void testNotEqualDifferentValue() {
        assertFalse(new PercentAlcoholByVolume(2.525 - Math.ulp(2.525)).equals(new PercentAlcoholByVolume(2.53)));
    }

    /** Equals itself. */
    @Test
    public void testEqualsItself() {
        PercentAlcoholByVolume abv = new PercentAlcoholByVolume(29);
        @SuppressWarnings("UnnecessaryLocalVariable")
        PercentAlcoholByVolume itself = abv;
        assertTrue(abv.equals(itself)); // NOPMD - exercising .equals(Object) is the point of this test
    }

    /** Java .equals(Object) contract is met. */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void equalsContract() {
        EqualsVerifier
                .forClass(PercentAlcoholByVolume.class)
                .withIgnoredFields("value")
                .suppress(Warning.NULL_FIELDS) // comparableValue should never be null
                .verify();
    }

    /** Hash code value is as expected. */
    @Test
    public void testHashCode() {
        assertEquals(new Long(10000).hashCode(), new PercentAlcoholByVolume(100).hashCode());
    }

}
