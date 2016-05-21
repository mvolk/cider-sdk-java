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
import static org.junit.Assert.assertTrue;

import com.ciderref.sdk.property.units.UnitsOfMass;
import com.ciderref.sdk.property.units.UnitsOfVolume;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

/**
 * Unit tests for {@link MassConcentration}.
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessivePublicCount", "PMD.TooManyStaticImports"})
public class MassConcentrationTest {

    /** Constructing with null mass produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullMassThrows() {
        new MassConcentration(null, new Volume(1, UnitsOfVolume.USGallons));
    }

    /** Constructing with null volume produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullVolumeUnitsThrows() {
        new MassConcentration(new Mass(4, UnitsOfMass.Grams), null);
    }

    /** Constructing with null mass and volume produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorBothArgsNullThrows() {
        new MassConcentration(null, null);
    }

    /** Constructing with zero volume produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorZeroVolumeThrows() {
        new MassConcentration(new Mass(4, UnitsOfMass.Grams), new Volume(0, UnitsOfVolume.USGallons));
    }

    /** Constructor handles args correctly. */
    @Test
    public void testConstructor() {
        assertEquals(2, new MassConcentration(new Mass(4, UnitsOfMass.Grams),
                new Volume(2, UnitsOfVolume.Liters)).getValue(UnitsOfMass.Grams, UnitsOfVolume.Liters), 0);
    }

    /** getValue with null mass units throws. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetValueNullMassUnitsThrows() {
        new MassConcentration(new Mass(4, UnitsOfMass.Grams),
                new Volume(2, UnitsOfVolume.Liters)).getValue(null, UnitsOfVolume.Liters);
    }

    /** getValue with null volume units throws. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetValueNullVolumeUnitsThrows() {
        new MassConcentration(new Mass(4, UnitsOfMass.Grams),
                new Volume(2, UnitsOfVolume.Liters)).getValue(UnitsOfMass.Grams, null);
    }

    /** getValue with both args null throws. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetValueBothArgsNullThrows() {
        new MassConcentration(new Mass(4, UnitsOfMass.Grams),
                new Volume(2, UnitsOfVolume.Liters)).getValue(null, null);
    }

    /** Comparison to null yields a NPE. */
    @Test(expected = NullPointerException.class)
    public void testCompareToNullThrows() {
        //noinspection ConstantConditions
        new MassConcentration(new Mass(4, UnitsOfMass.Grams), new Volume(100, UnitsOfVolume.Milliliters))
                .compareTo(null);
    }

    /** 5g/L is less than 1 oz/us gallon. */
    @Test
    public void testCompare5GramsPerLiterTo1OuncePerUsGallon() {
        assertEquals(-1,
                new MassConcentration(new Mass(5, UnitsOfMass.Grams), new Volume(1, UnitsOfVolume.Liters)).compareTo(
                new MassConcentration(new Mass(1, UnitsOfMass.Ounces), new Volume(1, UnitsOfVolume.USGallons))));
    }

    /** 1 oz/us gallon is more than 5g/L. */
    @Test
    public void testCompare1OuncePerUsGallonTo5GramsPerLiter() {
        assertEquals(1,
                new MassConcentration(new Mass(1, UnitsOfMass.Ounces), new Volume(1, UnitsOfVolume.USGallons))
                        .compareTo(
                new MassConcentration(new Mass(5, UnitsOfMass.Grams), new Volume(1, UnitsOfVolume.Liters))));
    }

    /** 5g/L is equal to 5g/L. */
    @Test
    public void testCompare1EquivalentMassConcentrations() {
        assertEquals(0,
                new MassConcentration(new Mass(5, UnitsOfMass.Grams), new Volume(1, UnitsOfVolume.Liters)).compareTo(
                new MassConcentration(new Mass(5, UnitsOfMass.Grams), new Volume(1, UnitsOfVolume.Liters))));
    }

    /** MassConcentration equals itself. */
    @Test
    public void testMassConcentrationEqualsItself() {
        MassConcentration massConcentration =
                new MassConcentration(new Mass(5, UnitsOfMass.Grams), new Volume(100, UnitsOfVolume.Milliliters));
        @SuppressWarnings("UnnecessaryLocalVariable")
        MassConcentration itself = massConcentration;
        assertTrue(massConcentration.equals(itself)); // NOPMD - exercising .equals(Object) is the point of this test
    }

    /** Java .equals(Object) contract is met. */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void equalsContract() {
        // Null fields warning is suppressed because our constructor prevents construction of MassConcentration with
        //  null mass or volume.
        EqualsVerifier
                .forClass(MassConcentration.class)
                .withIgnoredFields("mass", "volume")
                .suppress(Warning.NULL_FIELDS) // comparableValue should never be null
                .verify();
    }

    /** Hash code value is as expected. */
    @Test
    public void testHashCode() {
        assertEquals(new Long(100).hashCode(),
                new MassConcentration(new Mass(1, UnitsOfMass.Grams), new Volume(1, UnitsOfVolume.Liters)).hashCode());
    }

}
