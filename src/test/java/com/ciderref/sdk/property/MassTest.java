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

import static com.ciderref.sdk.property.Mass.Units.Grams;
import static com.ciderref.sdk.property.Mass.Units.Kilograms;
import static com.ciderref.sdk.property.Mass.Units.Ounces;
import static com.ciderref.sdk.property.Mass.Units.Pounds;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Unit tests for {@link Mass}.
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessivePublicCount", "PMD.TooManyStaticImports"})
public class MassTest {

    private static final double TOLERANCE_IN_GRAMS = 0.01;
    private static final double TOLERANCE_IN_KILOGRAMS = 1E-5;
    private static final double TOLERANCE_IN_OUNCES = 3.527E-4;
    private static final double TOLERANCE_IN_POUNDS = 2.205E-5;

    /** Mass.Units.values() should return 4 values. */
    @Test
    public void testCountOfUnitValues() {
        assertEquals(4, Mass.Units.values().length);
    }

    /** Mass.Units.valueOf("Grams") should return Grams. */
    @Test
    public void testUnitsIncludeMilligrams() {
        assertEquals(Grams, Mass.Units.valueOf("Grams"));
    }

    /** Mass.Units.valueOf("Kilograms") should return Kilograms. */
    @Test
    public void testUnitsIncludeGrams() {
        assertEquals(Kilograms, Mass.Units.valueOf("Kilograms"));
    }

    /** Mass.Units.valueOf("Ounces") should return Ounces. */
    @Test
    public void testUnitsIncludeOunces() {
        assertEquals(Ounces, Mass.Units.valueOf("Ounces"));
    }

    /** Mass.Units.valueOf("Pounds") should return Pounds. */
    @Test
    public void testUnitsIncludePounds() {
        assertEquals(Pounds, Mass.Units.valueOf("Pounds"));
    }

    /** Constructing with null units of measurement produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullUnitsThrows() {
        new Mass(5, null);
    }

    /** Constructing with NaN produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNaNThrows() {
        new Mass(Double.NaN, Grams);
    }

    /** Constructing with value less than zero produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeThrows() {
        // -0.0 is equivalent to 0.0 according to Double.compare, so go one ulp less to test the boundary
        new Mass(-0.0 - Math.ulp(-0.0), Grams);
    }

    /** Constructing with positive zero is valid. */
    @Test
    public void testConstructorZeroValid() {
        assertEquals(0, new Mass(+0, Grams).getValue(Grams), 0);
    }

    /** Constructing with negative infinity produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeInfinitThrows() {
        new Mass(Double.NEGATIVE_INFINITY, Grams);
    }

    /** Constructing with positive infinity produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPositiveInfinitThrows() {
        new Mass(Double.POSITIVE_INFINITY, Grams);
    }

    /** Constructor handles milligrams correctly. */
    @Test
    public void testConstructorMilligrams() {
        assertEquals(5, new Mass(5, Grams).getValue(Grams), 0);
    }

    /** Constructor handles grams correctly. */
    @Test
    public void testConstructorGrams() {
        assertEquals(5, new Mass(5, Kilograms).getValue(Kilograms), 0);
    }

    /** Constructor handles ounces correctly. */
    @Test
    public void testConstructorOunces() {
        assertEquals(5, new Mass(5, Ounces).getValue(Ounces), 0);
    }

    /** Constructor handles Pounds correctly. */
    @Test
    public void testConstructorPounds() {
        assertEquals(5, new Mass(5, Pounds).getValue(Pounds), 0);
    }

    /** getValue throws if null units passed in. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetValueWithNullArgumentThrows() {
        new Mass(5000, Grams).getValue(null);
    }

    /** Converts Grams to Kilograms correctly. */
    @Test
    public void testGetGramsAsKilograms() {
        assertEquals(5, new Mass(5000, Grams).getValue(Kilograms), TOLERANCE_IN_KILOGRAMS);
    }

    /** Converts Grams to Ounces correctly. */
    @Test
    public void testGetGramsAsOunces() {
        assertEquals(1.763698097479021, new Mass(50, Grams).getValue(Ounces), TOLERANCE_IN_OUNCES);
    }

    /** Converts Grams to Pounds correctly. */
    @Test
    public void testGetGramsAsPounds() {
        assertEquals(11.02311310924388, new Mass(5000, Grams).getValue(Pounds), TOLERANCE_IN_POUNDS);
    }

    /** Converts Kilograms to Grams correctly. */
    @Test
    public void testGetKilogramsAsGrams() {
        assertEquals(5000, new Mass(5, Kilograms).getValue(Grams), TOLERANCE_IN_GRAMS);
    }

    /** Converts Kilograms to Ounces correctly. */
    @Test
    public void testGetKilogramsAsOunces() {
        assertEquals(1.763698097479021, new Mass(0.05, Kilograms).getValue(Ounces), TOLERANCE_IN_OUNCES);
    }

    /** Converts Kilograms to Pounds correctly. */
    @Test
    public void testGetKilogramsAsPounds() {
        assertEquals(11.02311310924388, new Mass(5, Kilograms).getValue(Pounds), TOLERANCE_IN_POUNDS);
    }

    /** Converts Pounds to Grams correctly. */
    @Test
    public void testGetPoundsAsGrams() {
        assertEquals(5000, new Mass(11.02311310924388, Pounds).getValue(Grams), TOLERANCE_IN_GRAMS);
    }

    /** Converts Pounds to Ounces correctly. */
    @Test
    public void testGetPoundsAsOunces() {
        assertEquals(16, new Mass(1, Pounds).getValue(Ounces), TOLERANCE_IN_OUNCES);
    }

    /** Converts Pounds to Kilograms correctly. */
    @Test
    public void testGetPoundsAsKilograms() {
        assertEquals(5, new Mass(11.02311310924388, Pounds).getValue(Kilograms), TOLERANCE_IN_KILOGRAMS);
    }

    /** Comparison to null yields a NPE. */
    @Test(expected = NullPointerException.class)
    public void testCompareToNullThrows() {
        //noinspection ConstantConditions
        new Mass(50, Kilograms).compareTo(null);
    }

    /** 5kg is more than 5lb. */
    @Test
    public void testCompare5KilogramsTo5Pounds() {
        assertEquals(1, new Mass(5, Kilograms).compareTo(new Mass(5, Pounds)));
    }

    /** 5lb is less than 5kg. */
    @Test
    public void testCompare5PoundsTo5Kilograms() {
        assertEquals(-1, new Mass(5, Pounds).compareTo(new Mass(5, Kilograms)));
    }

    /** 1kg is 1000g. */
    @Test
    public void testCompare1KilogramTo1000Grams() {
        assertEquals(0, new Mass(1, Kilograms).compareTo(new Mass(1000, Grams)));
    }

    /** 1000g is 1kg. */
    @Test
    public void testCompare1000GramsTo1Kilogram() {
        assertEquals(0, new Mass(1000, Grams).compareTo(new Mass(1, Kilograms)));
    }

    /** 20kg is not equal to null. */
    @Test
    public void testMassIsNotEqualToNull() {
        //noinspection ObjectEqualsNull
        assertFalse(new Mass(20, Kilograms).equals(null)); // NOPMD - equals(null) is the point of this test
    }

    /** 50kg is not equal to "50kg". */
    @Test
    public void testMassIsNotEqualToString() {
        //noinspection EqualsBetweenInconvertibleTypes
        assertFalse(new Mass(50, Kilograms).equals("50kg"));
    }

    /** 0L is equal to 0 Pounds. */
    @Test
    public void test0Equals0() {
        assertTrue(new Mass(0, Kilograms).equals(new Mass(0, Pounds)));
    }

    /** 7kg is not equal to 7g. */
    @Test
    public void test7KilogramsIsNotEqualTo7Grams() {
        assertFalse(new Mass(7, Kilograms).equals(new Mass(7, Grams)));
    }

    /** 1kg is equal to 1000g. */
    @Test
    public void test1KilogramIsEqualTo1000Grams() {
        assertTrue(new Mass(1, Kilograms).equals(new Mass(1000, Grams)));
    }

    /** Mass equals itself. */
    @Test
    public void testMassEqualsItself() {
        Mass mass = new Mass(2, Grams);
        @SuppressWarnings("UnnecessaryLocalVariable")
        Mass itself = mass;
        assertTrue(mass.equals(itself)); // NOPMD - exercising .equals(Object) is the point of this test
    }

    /** Java .equals(Object) contract is met. */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void equalsContract() {
        EqualsVerifier.forClass(Mass.class).verify();
    }

    /** Hash code value is as expected. */
    @Test
    public void testHashCode() {
        assertEquals(new Long(100).hashCode(), new Mass(1, Grams).hashCode());
    }

}
