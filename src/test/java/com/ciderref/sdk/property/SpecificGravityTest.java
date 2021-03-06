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

import com.ciderref.sdk.property.units.UnitsOfTemperature;
import com.ciderref.sdk.substance.Water;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Units tests for {@link SpecificGravity}. Expected corrected values are drawn from The New Ciderist's Handbook by
 * Claude Jolicoeur, published in 2013 by Chelsea Green Publishing in White River Junction, Vermont.
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessivePublicCount"})
public class SpecificGravityTest {

    private static final Temperature FREEZING = Water.STANDARD_FREEZING_POINT;
    private static final Temperature ROOM_TEMPERATURE = new Temperature(68, UnitsOfTemperature.Fahrenheit);
    private static final Temperature BOILING = Water.STANDARD_BOILING_POINT;

    /** Constructor throws if the specific gravity is NaN. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNaN() {
        new SpecificGravity(Double.NaN);
    }

    /** Constructor throws if the specific gravity is positive infinity. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorPositiveInfinity() {
        new SpecificGravity(Double.POSITIVE_INFINITY);
    }

    /** Constructor throws if the specific gravity is negative infinity. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeInfinity() {
        new SpecificGravity(Double.NEGATIVE_INFINITY);
    }

    /** Constructor throws if the specific gravity is zero. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorZero() {
        new SpecificGravity(0.0);
    }

    /** Constructor accepts specific gravity that is minimally more than zero. */
    @Test
    public void testConstructorZeroPlusUlp() {
        assertEquals(0.0 + Math.ulp(0.0), new SpecificGravity(0.0 + Math.ulp(0.0)).getValue(), 0);
    }

    /** Constructor correctly handles routine value. */
    @Test
    public void testConstructor() {
        assertEquals(1.040, new SpecificGravity(1.040).getValue(), 0);
    }

    /** Correcting constructor throws if the specific gravity is NaN. */
    @Test(expected = IllegalArgumentException.class)
    public void testCorrectingConstructorNaN() {
        new SpecificGravity(Double.NaN, ROOM_TEMPERATURE, ROOM_TEMPERATURE);
    }

    /** Correcting constructor throws if the specific gravity is positive infinity. */
    @Test(expected = IllegalArgumentException.class)
    public void testCorrectingConstructorPositiveInfinity() {
        new SpecificGravity(Double.POSITIVE_INFINITY, ROOM_TEMPERATURE, ROOM_TEMPERATURE);
    }

    /** Correcting constructor throws if the specific gravity is negative infinity. */
    @Test(expected = IllegalArgumentException.class)
    public void testCorrectingConstructorNegativeInfinity() {
        new SpecificGravity(Double.NEGATIVE_INFINITY, ROOM_TEMPERATURE, ROOM_TEMPERATURE);
    }

    /** Correcting constructor throws if the specific gravity is zero. */
    @Test(expected = IllegalArgumentException.class)
    public void testCorrectingConstructorZero() {
        new SpecificGravity(0.0, ROOM_TEMPERATURE, ROOM_TEMPERATURE);
    }

    /** Correcting constructor accepts specific gravity that is minimally more than zero. */
    @Test
    public void testCorrectingConstructorZeroPlusUlp() {
        assertEquals(0.0 + Math.ulp(0.0),
                new SpecificGravity(0.0 + Math.ulp(0.0), ROOM_TEMPERATURE, ROOM_TEMPERATURE).getValue(), 0);
    }

    /** Correcting constructor correctly handles measurement at the hydrometer's calibration temperature. */
    @Test
    public void testCorrectingConstructorValueLowLimit() {
        SpecificGravity sg = new SpecificGravity(1.015, ROOM_TEMPERATURE, ROOM_TEMPERATURE);
        assertEquals(1.015, sg.getValue(), 0);
    }

    /** Correcting constructor throws if the measured temperature is too high. */
    @Test(expected = IllegalPropertyValueException.class)
    public void testCorrectingConstructorMeasuredTemperatureTooHigh() {
        new SpecificGravity(1.0, new Temperature(100.01, UnitsOfTemperature.Celsius), ROOM_TEMPERATURE);
    }

    /** Correcting constructor throws if the measured temperature is too low. */
    @Test(expected = IllegalPropertyValueException.class)
    public void testCorrectingConstructorMeasuredTemperatureTooLow() {
        new SpecificGravity(1.0, new Temperature(-0.01, UnitsOfTemperature.Celsius), ROOM_TEMPERATURE);
    }

    /** Correcting constructor accepts a measured temperature at the maximum supported temperature. */
    @Test
    public void testCorrectingConstructorMeasuredTemperatureAtHighLimit() {
        SpecificGravity sg = new SpecificGravity(1.0, BOILING, ROOM_TEMPERATURE);
        assertEquals(1.04135, sg.getValue(), 0.0001);
    }

    /** Correcting constructor accepts a measured temperature at the minimum supported temperature. */
    @Test
    public void testCorrectingConstructorMeasuredTemperatureAtLowLimit() {
        SpecificGravity sg =  new SpecificGravity(1.0, FREEZING, ROOM_TEMPERATURE);
        assertEquals(0.99836, sg.getValue(), 0.00001);
    }

    /** Correcting constructor throws if the measured temperature is null. */
    @Test(expected = IllegalArgumentException.class)
    public void testCorrectingConstructorNullMeasuredTemperature() {
        new SpecificGravity(1.0, null, ROOM_TEMPERATURE);
    }

    /** Correcting constructor throws if the calibration temperature is too high. */
    @Test(expected = IllegalPropertyValueException.class)
    public void testCorrectingConstructorCalibrationTemperatureTooHigh() {
        new SpecificGravity(1.0, ROOM_TEMPERATURE, new Temperature(100.01, UnitsOfTemperature.Celsius));
    }

    /** Correcting constructor throws if the calibration temperature is too low. */
    @Test(expected = IllegalPropertyValueException.class)
    public void testCorrectingConstructorCalibrationTemperatureTooLow() {
        new SpecificGravity(1.0, ROOM_TEMPERATURE, new Temperature(-0.01, UnitsOfTemperature.Celsius));
    }

    /** Correcting constructor accepts a calibration temperature at the maximum limit. */
    @Test
    public void testCorrectingConstructorCalibrationTemperatureAtHighLimit() {
        SpecificGravity sg = new SpecificGravity(1.1, ROOM_TEMPERATURE, BOILING);
        assertEquals(1.05632, sg.getValue(), 0.00001);
    }

    /** Correcting constructor accepts a calibration temperature at the minimum limit. */
    @Test
    public void testCorrectingConstructorCalibrationTemperatureAtLowLimit() {
        SpecificGravity sg = new SpecificGravity(1.0, ROOM_TEMPERATURE, FREEZING);
        assertEquals(1.00163, sg.getValue(), 0.00001);
    }

    /** Correcting constructor throws when passed a null calibration temperature. */
    @Test(expected = IllegalArgumentException.class)
    public void testCorrectingConstructorNullCalibrationTemperature() {
        new SpecificGravity(1.0, ROOM_TEMPERATURE, null);
    }

    /** Correcting constructor correctly corrects 1.020 at 43℉ with hydrometer calibrated at 60℉. */
    @Test
    public void testCorrectingConstructorCorrectAt43Fahrenheit() {
        Temperature measuredTemperature = new Temperature(43, UnitsOfTemperature.Fahrenheit);
        Temperature calibrationTemperature = new Temperature(60, UnitsOfTemperature.Fahrenheit);
        SpecificGravity correctedSg = new SpecificGravity(1.020, measuredTemperature, calibrationTemperature);
        assertEquals(1.0190, correctedSg.getValue(), 0.0001);
    }

    /** Correcting constructor correctly corrects 1.020 at 50℉ with hydrometer calibrated at 60℉. */
    @Test
    public void testCorrectingConstructorCorrectAt50Fahrenheit() {
        Temperature measuredTemperature = new Temperature(50, UnitsOfTemperature.Fahrenheit);
        Temperature calibrationTemperature = new Temperature(60, UnitsOfTemperature.Fahrenheit);
        SpecificGravity correctedSg = new SpecificGravity(1.020, measuredTemperature, calibrationTemperature);
        assertEquals(1.0193, correctedSg.getValue(), 0.0001);
    }

    /** Correcting constructor correctly corrects 1.020 at 60℉ with hydrometer calibrated at 60℉. */
    @Test
    public void testCorrectingConstructorCorrectAt60Fahrenheit() {
        Temperature measuredTemperature = new Temperature(60, UnitsOfTemperature.Fahrenheit);
        Temperature calibrationTemperature = new Temperature(60, UnitsOfTemperature.Fahrenheit);
        SpecificGravity correctedSg = new SpecificGravity(1.020, measuredTemperature, calibrationTemperature);
        assertEquals(1.020, correctedSg.getValue(), 0.0001);
    }

    /** Correcting constructor correctly corrects 1.020 at 65℉ with hydrometer calibrated at 60℉. */
    @Test
    public void testCorrectingConstructorCorrectAt65Fahrenheit() {
        Temperature measuredTemperature = new Temperature(65, UnitsOfTemperature.Fahrenheit);
        Temperature calibrationTemperature = new Temperature(60, UnitsOfTemperature.Fahrenheit);
        SpecificGravity correctedSg = new SpecificGravity(1.020, measuredTemperature, calibrationTemperature);
        assertEquals(1.0205, correctedSg.getValue(), 0.0001);
    }

    /** Correcting constructor correctly corrects 1.020 at 70℉ with hydrometer calibrated at 60℉. */
    @Test
    public void testCorrectingConstructorCorrectAt70Fahrenheit() {
        Temperature measuredTemperature = new Temperature(70, UnitsOfTemperature.Fahrenheit);
        Temperature calibrationTemperature = new Temperature(60, UnitsOfTemperature.Fahrenheit);
        SpecificGravity correctedSg = new SpecificGravity(1.020, measuredTemperature, calibrationTemperature);
        assertEquals(1.021, correctedSg.getValue(), 0.0001);
    }

    /** Correcting constructor correctly corrects 1.020 at 77℉ with hydrometer calibrated at 60℉. */
    @Test
    public void testCorrectingConstructorCorrectAt77Fahrenheit() {
        Temperature measuredTemperature = new Temperature(77, UnitsOfTemperature.Fahrenheit);
        Temperature calibrationTemperature = new Temperature(60, UnitsOfTemperature.Fahrenheit);
        SpecificGravity correctedSg = new SpecificGravity(1.020, measuredTemperature, calibrationTemperature);
        assertEquals(1.022, correctedSg.getValue(), 0.0001);
    }

    /** Correcting constructor correctly corrects 1.020 at 84℉ with hydrometer calibrated at 60℉. */
    @Test
    public void testCorrectingConstructorCorrectAt84Fahrenheit() {
        Temperature measuredTemperature = new Temperature(84, UnitsOfTemperature.Fahrenheit);
        Temperature calibrationTemperature = new Temperature(60, UnitsOfTemperature.Fahrenheit);
        SpecificGravity correctedSg = new SpecificGravity(1.020, measuredTemperature, calibrationTemperature);
        assertEquals(1.023, correctedSg.getValue(), 0.0001);
    }

    /** 1.020 is the same as 1.020 */
    @Test
    public void testCompareToSameValue() {
        assertEquals(0, new SpecificGravity(1.020).compareTo(new SpecificGravity(1.020)));
    }

    /** 1.020 is more than 1.010 */
    @Test
    public void testCompareToSmallerValue() {
        assertEquals(1, new SpecificGravity(1.020).compareTo(new SpecificGravity(1.010)));
    }

    /** 1.020 is more than 1.030 */
    @Test
    public void testCompareToLargerValue() {
        assertEquals(-1, new SpecificGravity(1.020).compareTo(new SpecificGravity(1.030)));
    }

    /** Comparison with null throws NPE. */
    @Test(expected = NullPointerException.class)
    public void testCompareToNullValue() {
        new SpecificGravity(1.020).compareTo(null);
    }

    /** Java .equals(Object) contract is met. */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void testEqualsContract() {
        EqualsVerifier.forClass(SpecificGravity.class).verify();
    }

    /** toString correctly represents specific gravities higher than 1. */
    @Test
    public void testToStringAbove1() {
        assertEquals("1.010", new SpecificGravity(1.01).toString());
    }

    /** toString correctly represents specific gravities less than 1. */
    @Test
    public void testToStringBelow1() {
        assertEquals("0.990", new SpecificGravity(.99).toString());
    }

}
