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

import static com.ciderref.sdk.property.Volume.Units.Liters;
import static com.ciderref.sdk.property.Volume.Units.Milliliters;
import static com.ciderref.sdk.property.Volume.Units.USGallons;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Unit tests for {@link Volume}.
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessivePublicCount", "PMD.TooManyStaticImports"})
public class VolumeTest {

    /** Volume.Units.values() should return 3 values. */
    @Test
    public void testCountOfUnitValues() {
        assertEquals(3, Volume.Units.values().length);
    }

    /** Volume.Units.valueOf("Milliliters") should return Milliliters. */
    @Test
    public void testUnitsIncludeMilliliters() {
        assertEquals(Milliliters, Volume.Units.valueOf("Milliliters"));
    }

    /** Volume.Units.valueOf("Liters") should return Liters. */
    @Test
    public void testUnitsIncludeLiters() {
        assertEquals(Liters, Volume.Units.valueOf("Liters"));
    }

    /** Volume.Units.valueOf("USGallons") should return USGallons. */
    @Test
    public void testUnitsIncludeUsGallons() {
        assertEquals(USGallons, Volume.Units.valueOf("USGallons"));
    }

    /** Constructing with null units of measurement produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullUnitsThrows() {
        new Volume(5, null);
    }

    /** Constructor handles milliliters correctly. */
    @Test
    public void testConstructorMilliliters() {
        assertEquals(5, new Volume(5, Milliliters).getValue(Milliliters), 0);
    }

    /** Constructor handles liters correctly. */
    @Test
    public void testConstructorLiters() {
        assertEquals(5, new Volume(5, Liters).getValue(Liters), 0);
    }

    /** Constructor handles US Gallons correctly. */
    @Test
    public void testConstructorUsGallons() {
        assertEquals(5, new Volume(5, USGallons).getValue(USGallons), 0);
    }

    /** getValue throws if null units passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetValueWithNullArgumentThrows() {
        new Volume(5000, Milliliters).getValue(null);
    }

    /** Converts Milliliters to Liters correctly. */
    @Test
    public void testGetMillilitersAsLiters() {
        assertEquals(5, new Volume(5000, Milliliters).getValue(Liters), 0);
    }

    /** Converts Milliliters to US Gallons correctly. */
    @Test
    public void testGetMillilitersAsUsGallons() {
        assertEquals(1.32086, new Volume(5000, Milliliters).getValue(USGallons), 0.000001);
    }

    /** Converts Liters to Milliliters correctly. */
    @Test
    public void testGetLitersAsMilliliters() {
        assertEquals(5000, new Volume(5, Liters).getValue(Milliliters), 0);
    }

    /** Converts Liters to US Gallons correctly. */
    @Test
    public void testGetLitersAsUsGallons() {
        assertEquals(1.32086, new Volume(5, Liters).getValue(USGallons), 0.000001);
    }

    /** Converts US Gallons to Milliliters correctly. */
    @Test
    public void testGetUsGallonsAsMilliliters() {
        assertEquals(5000, new Volume(1.32086, USGallons).getValue(Milliliters), 0.01);
    }

    /** Converts Liters to US Gallons correctly. */
    @Test
    public void testGetUsGallonsAsLiters() {
        assertEquals(5, new Volume(1.32086, USGallons).getValue(Liters), 0.000001);
    }

    /** Comparison to null yields a NPE. */
    @Test(expected = NullPointerException.class)
    public void testCompareToNullThrows() {
        //noinspection ConstantConditions
        new Volume(50, Liters).compareTo(null);
    }

    /**
     * 1.00 mL is the same as 1.009999999999 mL. Note that comparison to 1.01 doesn't work since 1 - 1.01 in
     * double-precision floating point arithmetic yields a tiny fraction more than 0.01.
     */
    @Test
    public void testCompareValuesSeparatedByLessThanEpsilon() {
        assertEquals(0, new Volume(1, Milliliters).compareTo(new Volume(1.009999999999, Milliliters)));
    }

    /** 1.000 mL is less than 1.01000000001 mL. */
    @Test
    public void testCompareValuesSeparatedByMoreThanEpsilon() {
        assertEquals(-1, new Volume(1, Milliliters).compareTo(new Volume(1.01000000001, Milliliters)));
    }

    /** 5L is more than 1 US Gallon. */
    @Test
    public void testCompare5LitersTo1UsGallon() {
        assertEquals(1, new Volume(5, Liters).compareTo(new Volume(1, USGallons)));
    }

    /** 1 US Gallon is less than 5 Liters. */
    @Test
    public void testCompare1UsGallonTo5Liters() {
        assertEquals(-1, new Volume(1, USGallons).compareTo(new Volume(5, Liters)));
    }

    /** 1 Liter is 1000 Milliliters. */
    @Test
    public void testCompare1LiterTo1000Milliliters() {
        assertEquals(0, new Volume(1, Liters).compareTo(new Volume(1000, Milliliters)));
    }

    /** 1000 Milliliters is 1 Liter. */
    @Test
    public void testCompare1000MillilitersTo1Liter() {
        assertEquals(0, new Volume(1000, Milliliters).compareTo(new Volume(1, Liters)));
    }

    /** 20 L is not equal to null. */
    @Test
    public void testVolumeIsNotEqualToNull() {
        //noinspection ObjectEqualsNull
        assertFalse(new Volume(20, Liters).equals(null)); // NOPMD - equals(null) is the point of this test
    }

    /** 50L is not equal to "50L". */
    @Test
    public void testVolumeIsNotEqualToString() {
        //noinspection EqualsBetweenInconvertibleTypes
        assertFalse(new Volume(50, Liters).equals("50L"));
    }

    /** 0L is equal to 0 US Gallons. */
    @Test
    public void test0Equals0() {
        assertTrue(new Volume(0, Liters).equals(new Volume(0, USGallons)));
    }

    /** 7L is not equal to 7mL. */
    @Test
    public void test7LitersIsNotEqualTo7Milliliters() {
        assertFalse(new Volume(7, Liters).equals(new Volume(7, Milliliters)));
    }

    /** 1L is equal to 1000mL. */
    @Test
    public void test1LiterIsEqualTo1000Milliliters() {
        assertTrue(new Volume(1, Liters).equals(new Volume(1000, Milliliters)));
    }

    /** Volume equals itself. */
    @Test
    public void testVolumeEqualsItself() {
        Volume volume = new Volume(2, Milliliters);
        @SuppressWarnings("UnnecessaryLocalVariable")
        Volume itself = volume;
        assertTrue(volume.equals(itself)); // NOPMD - exercising .equals(Object) is the point of this test
    }

    /** Java .equals(Object) contract is met. */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void equalsContract() {
        EqualsVerifier.forClass(Volume.class).verify();
    }

}
