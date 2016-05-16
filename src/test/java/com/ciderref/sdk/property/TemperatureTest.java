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

import static com.ciderref.sdk.property.Temperature.Units.Celsius;
import static com.ciderref.sdk.property.Temperature.Units.Fahrenheit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Unit tests for {@link Temperature}.
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessivePublicCount", "PMD.GodClass"})
public class TemperatureTest {

    /** Temperature.Units.values() should return 2 enums */
    @Test
    public void testTwoValues() {
        assertEquals(2, Temperature.Units.values().length);
    }

    /** Temperature.Units.valueOf("Celsius") should return Celsius */
    @Test
    public void testValueOfCelsius() {
        assertEquals(Celsius, Temperature.Units.valueOf("Celsius"));
    }

    /** Temperature.Units.valueOf("Fahrenheit") should return Fahrenheit */
    @Test
    public void testValueOfFahrenheit() {
        assertEquals(Fahrenheit, Temperature.Units.valueOf("Fahrenheit"));
    }

    /** Constructing with null units of measurement produces an exception. */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullUnitsThrows() {
        new Temperature(212, null);
    }

    /** 0℃ should be 0℃ ±0. */
    @Test
    public void test0CelsiusIs0Celsius() {
        assertEquals(0, new Temperature(0, Celsius).getValue(Celsius), 0);
    }

    /** 50℃ should be 50℃ ±0. */
    @Test
    public void test50CelsiusIs50Celsius() {
        assertEquals(50, new Temperature(50, Celsius).getValue(Celsius), 0);
    }

    /** 100℃ should be 100℃ ±0. */
    @Test
    public void test100CelsiusIs100Celsius() {
        assertEquals(100, new Temperature(100, Celsius).getValue(Celsius), 0);
    }

    /** 0℃ should be 32℉ ±0. */
    @Test
    public void test0CelsiusIs32Fahrenheit() {
        assertEquals(32, new Temperature(0, Celsius).getValue(Fahrenheit), 0);
    }

    /** 50℃ should be 122℉ ±0. */
    @Test
    public void test50CelsiusIs122Fahrenheit() {
        assertEquals(122, new Temperature(50, Celsius).getValue(Fahrenheit), 0);
    }

    /** 100℃ should be 212℉ ±0. */
    @Test
    public void test100CelsiusIs212Fahrenheit() {
        assertEquals(212, new Temperature(100, Celsius).getValue(Fahrenheit), 0);
    }

    /** 0℉ should be 0℉ ±0. */
    @Test
    public void test0FahrenheitIs0Fahrenheit() {
        assertEquals(0, new Temperature(0, Fahrenheit).getValue(Fahrenheit), 0);
    }

    /** 50℉ should be 50℉ ±0. */
    @Test
    public void test50FahrenheitIs50Fahrenheit() {
        assertEquals(50, new Temperature(50, Fahrenheit).getValue(Fahrenheit), 0);
    }

    /** 212℉ should be 212℉ ±0. */
    @Test
    public void test212FahrenheitIt212Fahrenheit() {
        assertEquals(212, new Temperature(212, Fahrenheit).getValue(Fahrenheit), 0);
    }

    /** 32℉ should be 0℃ ±0. */
    @Test
    public void test32FahrenheitIs0Celsius() {
        assertEquals(0, new Temperature(32, Fahrenheit).getValue(Celsius), 0);
    }

    /** 122℉ should be 50℃ ±0. */
    @Test
    public void test122FahrenheitIs50Celsius() {
        assertEquals(50, new Temperature(122, Fahrenheit).getValue(Celsius), 0);
    }

    /** 212℉ should be 1000℃ ±0. */
    @Test
    public void test212FahrenheitIs100Fahrenheit() {
        assertEquals(100, new Temperature(212, Fahrenheit).getValue(Celsius), 0);
    }

    /** getValue(null) throws. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetValueWithNullUnitsThrows() {
        new Temperature(212, Fahrenheit).getValue(null);
    }

    /** 0℃ is just as cold as 0℃. */
    @Test
    public void test0CelsiusCompareTo0Celsius() {
        assertEquals(0, new Temperature(0, Celsius).compareTo(new Temperature(0, Celsius)));
    }

    /** 0℃ is not cooler than 0℃. */
    @Test
    public void test0CelsiusIsNotCoolerThan0Celsius() {
        assertFalse(new Temperature(0, Celsius).isCoolerThan(new Temperature(0, Celsius)));
    }

    /** 0℃ is not warmer than 0℃. */
    @Test
    public void test0CelsiusIsNoWarmerThan0Celsius() {
        assertFalse(new Temperature(0, Celsius).isWarmerThan(new Temperature(0, Celsius)));
    }

    /** -5℃ is colder than 1℃. */
    @Test
    public void testNegative5CelsiusCompareTo1Celsius() {
        assertEquals(-1, new Temperature(-5, Celsius).compareTo(new Temperature(1, Celsius)));
    }

    /** -5℃ is colder than 1℃. */
    @Test
    public void testNegative5CelsiusIsColderThan1Celsius() {
        assertTrue(new Temperature(-5, Celsius).isCoolerThan(new Temperature(1, Celsius)));
    }

    /** -5℃ is not warmer than 1℃. */
    @Test
    public void testNegative5CelsiusIsNotWarmerThan1Celsius() {
        assertFalse(new Temperature(-5, Celsius).isWarmerThan(new Temperature(1, Celsius)));
    }

    /** 1℃ is warmer than -5℃. */
    @Test
    public void test1CelsiusIsWarmerThanNegative5Celsius() {
        assertTrue(new Temperature(1, Celsius).isWarmerThan(new Temperature(-5, Celsius)));
    }

    /** 1℃ is not cooler than -5℃. */
    @Test
    public void test1CelsiusIsNotCoolerThanNegative5Celsius() {
        assertFalse(new Temperature(1, Celsius).isCoolerThan(new Temperature(-5, Celsius)));
    }

    /** 50℃ is warmer than 8℃. */
    @Test
    public void test50CelsiusCompareTo8Celsius() {
        assertEquals(1, new Temperature(50, Celsius).compareTo(new Temperature(8, Celsius)));
    }

    /** 50℃ is warmer than 8℃. */
    @Test
    public void test50CelsiusIsWarmerThan8Celsius() {
        assertTrue(new Temperature(50, Celsius).isWarmerThan(new Temperature(8, Celsius)));
    }

    /** 50℃ is not cooler than 8℃. */
    @Test
    public void test50CelsiusIsNotCoolerThan8Celsius() {
        assertFalse(new Temperature(50, Celsius).isCoolerThan(new Temperature(8, Celsius)));
    }

    /** 8℃ is cooler than 50℃. */
    @Test
    public void test8CelsiusIsCoolerThan50Celsius() {
        assertTrue(new Temperature(8, Celsius).isCoolerThan(new Temperature(50, Celsius)));
    }

    /** 8℃ is not warmer than 50℃. */
    @Test
    public void test8CelsiusIsNotWarmerThan50Celsius() {
        assertFalse(new Temperature(8, Celsius).isWarmerThan(new Temperature(50, Celsius)));
    }

    /** 34℉ is just as cold as 34℉. */
    @Test
    public void test34FahrenheitCompareTo34Fahrenheit() {
        assertEquals(0, new Temperature(34, Fahrenheit).compareTo(new Temperature(34, Fahrenheit)));
    }

    /** 34℉ is not warmer than 34℉. */
    @Test
    public void test34FahrenheitIsNotWarmerThan34Fahrenheit() {
        assertFalse(new Temperature(34, Fahrenheit).isWarmerThan(new Temperature(34, Fahrenheit)));
    }

    /** 34℉ is not colder than 34℉. */
    @Test
    public void test34FahrenheitIsNotColderThan34Fahrenheit() {
        assertFalse(new Temperature(34, Fahrenheit).isCoolerThan(new Temperature(34, Fahrenheit)));
    }

    /** -60℉ is colder than 0℉. */
    @Test
    public void testNegative60FahrenheitCompareTo0Fahrenheit() {
        assertEquals(-1, new Temperature(-60, Fahrenheit).compareTo(new Temperature(0, Fahrenheit)));
    }

    /** -60℉ is colder than 0℉. */
    @Test
    public void testNegative60FahrenheitIsCoolerThan0Fahrenheit() {
        assertTrue(new Temperature(-60, Fahrenheit).isCoolerThan(new Temperature(0, Fahrenheit)));
    }

    /** -60℉ is not warmer than 0℉. */
    @Test
    public void testNegative60FahrenheitIsNotWarmerThan0Fahrenheit() {
        assertFalse(new Temperature(-60, Fahrenheit).isWarmerThan(new Temperature(0, Fahrenheit)));
    }

    /** 0℉ is warmer than -60℉. */
    @Test
    public void test0FahrenheitIsWarmerThanNegative60Fahrenheit() {
        assertTrue(new Temperature(0, Fahrenheit).isWarmerThan(new Temperature(-60, Fahrenheit)));
    }

    /** 0℉ is not cooler than -60℉. */
    @Test
    public void test0FahrenheitIsNotCoolerThanNegative60Fahrenheit() {
        assertFalse(new Temperature(0, Fahrenheit).isCoolerThan(new Temperature(-60, Fahrenheit)));
    }

    /** 1℉ is warmer than -5℉. */
    @Test
    public void test1FahrenheitCompareToNegative5Fahrenheit() {
        assertEquals(1, new Temperature(1, Fahrenheit).compareTo(new Temperature(-5, Fahrenheit)));
    }

    /** 1℉ is warmer than -5℉. */
    @Test
    public void test1FahrenheitIsWarmerThanNegative5Fahrenheit() {
        assertTrue(new Temperature(1, Fahrenheit).isWarmerThan(new Temperature(-5, Fahrenheit)));
    }

    /** 1℉ is not cooler than -5℉. */
    @Test
    public void test1FahrenheitIsNotCoolerThanNegative5Fahrenheit() {
        assertFalse(new Temperature(1, Fahrenheit).isCoolerThan(new Temperature(-5, Fahrenheit)));
    }

    /** -5℉ is cooler than 1℉. */
    @Test
    public void testNegative5FahrenheitIsCoolerThan1Fahrenheit() {
        assertTrue(new Temperature(-5, Fahrenheit).isCoolerThan(new Temperature(1, Fahrenheit)));
    }

    /** -5℉ is not warmer than 1℉. */
    @Test
    public void testNegative5FahrenheitIsNotWarmerThan1Fahrenheit() {
        assertFalse(new Temperature(-5, Fahrenheit).isWarmerThan(new Temperature(1, Fahrenheit)));
    }

    /** 0℃ is just as cold as 32℉. */
    @Test
    public void test0CelsiusCompareTo0Fahrenheit() {
        assertEquals(0, new Temperature(0, Celsius).compareTo(new Temperature(32, Fahrenheit)));
    }

    /** 0℃ is not warmer than 32℉. */
    @Test
    public void test0CelsiusIsNotWarmerThan0Fahrenheit() {
        assertFalse(new Temperature(0, Celsius).isWarmerThan(new Temperature(32, Fahrenheit)));
    }

    /** 0℃ is not cooler than 32℉. */
    @Test
    public void test0CelsiusIsNotCoolerThan0Fahrenheit() {
        assertFalse(new Temperature(0, Celsius).isCoolerThan(new Temperature(32, Fahrenheit)));
    }

    /** 30℉ is colder than 0℃. */
    @Test
    public void test30FahrenheitCompareTo0Celsius() {
        assertEquals(-1, new Temperature(30, Fahrenheit).compareTo(new Temperature(0, Celsius)));
    }

    /** 30℉ is colder than 0℃. */
    @Test
    public void test30FahrenheitIsCooler0Celsius() {
        assertTrue(new Temperature(30, Fahrenheit).isCoolerThan(new Temperature(0, Celsius)));
    }

    /** 30℉ is not warmer than 0℃. */
    @Test
    public void test30FahrenheitIsNotWarmerThan0Celsius() {
        assertFalse(new Temperature(30, Fahrenheit).isWarmerThan(new Temperature(0, Celsius)));
    }

    /** 0℃ is warmer than 30℉. */
    @Test
    public void test0CelsiusIsWarmerThan30Fahrenheit() {
        assertTrue(new Temperature(0, Celsius).isWarmerThan(new Temperature(30, Fahrenheit)));
    }

    /** 0℃ is not cooler than 30℉. */
    @Test
    public void test0CelsiusIsNotCoolerThan30Fahrenheit() {
        assertFalse(new Temperature(0, Celsius).isCoolerThan(new Temperature(30, Fahrenheit)));
    }

    /** 50℃ is warmer than 50℉. */
    @Test
    public void test50CelsiusCompareTo50Fahrenheit() {
        assertEquals(1, new Temperature(50, Celsius).compareTo(new Temperature(50, Fahrenheit)));
    }

    /** 50℃ is warmer than 50℉. */
    @Test
    public void test50CelsiusIsWarmerThan50Fahrenheit() {
        assertTrue(new Temperature(50, Celsius).isWarmerThan(new Temperature(50, Fahrenheit)));
    }

    /** 50℃ is not cooler than 50℉. */
    @Test
    public void test50CelsiusIsNotCoolerThan50Fahrenheit() {
        assertFalse(new Temperature(50, Celsius).isCoolerThan(new Temperature(50, Fahrenheit)));
    }

    /** 50℉ is not warmer than 50℃. */
    @Test
    public void test50FahrenheitIsNotWarmerThan50Celsius() {
        assertFalse(new Temperature(50, Fahrenheit).isWarmerThan(new Temperature(50, Celsius)));
    }

    /** 50℉ is cooler than 50℃. */
    @Test
    public void test50FahrenheitIsCoolerThan50Celsius() {
        assertTrue(new Temperature(50, Fahrenheit).isCoolerThan(new Temperature(50, Celsius)));
    }

    /** Comparison to null yields a NPE. */
    @Test(expected = NullPointerException.class)
    public void test50CelsiusCompareToNull() {
        //noinspection ConstantConditions
        new Temperature(50, Celsius).compareTo(null);
    }

    /** Comparison to null yields a NPE. */
    @Test(expected = NullPointerException.class)
    public void test50CelsiusIsWarmerThanNull() {
        //noinspection ConstantConditions
        new Temperature(50, Celsius).isWarmerThan(null);
    }

    /** Comparison to null yields a NPE. */
    @Test(expected = NullPointerException.class)
    public void test50CelsiusIsCoolerThanNull() {
        //noinspection ConstantConditions
        new Temperature(50, Celsius).isCoolerThan(null);
    }

    /** 50℃ is not equal to null. */
    @Test
    public void testTemperatureIsNotEqualToNull() {
        //noinspection ObjectEqualsNull
        assertFalse(new Temperature(50, Celsius).equals(null)); // NOPMD - equals(null) is the point of this test
    }

    /** 50℃ is not equal to "50℃". */
    @Test
    public void testTemperatureIsNotEqualToString() {
        //noinspection EqualsBetweenInconvertibleTypes
        assertFalse(new Temperature(50, Celsius).equals("50℃"));
    }

    /** 50℃ is not equal to 50℉. */
    @Test
    public void test50CelsiusIsNotEqualTo50Fahrenheit() {
        assertFalse(new Temperature(50, Celsius).equals(new Temperature(50, Fahrenheit)));
    }

    /** 0℃ is equal to 32℉. */
    @Test
    public void test0CelsiusIsEqualTo32Fahrenheit() {
        assertTrue(new Temperature(0, Celsius).equals(new Temperature(32, Fahrenheit)));
    }

    /** Temperature equals itself. */
    @Test
    public void testTemperatureEqualsItself() {
        Temperature temperature = new Temperature(0, Celsius);
        @SuppressWarnings("UnnecessaryLocalVariable")
        Temperature itself = temperature;
        assertTrue(temperature.equals(itself)); // NOPMD - exercising .equals(Object) is the point of this test
    }

    /** Java .equals(Object) contract is met. */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    public void equalsContract() {
        EqualsVerifier.forClass(Temperature.class).verify();
    }

}
