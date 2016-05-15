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

import org.junit.Test;

/**
 * Unit tests for {@link TemperatureUnits}.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class TemperatureUnitsTest {

    /** TemperatureUnits.values() should return 2 enums */
    @Test
    public void testTwoValues() {
        assertEquals(2, TemperatureUnits.values().length);
    }

    /** TemperatureUnits.valueOf("Celsius") should return TemperatureUnits.Celsius */
    @Test
    public void testValueOfCelsius() {
        assertEquals(TemperatureUnits.Celsius, TemperatureUnits.valueOf("Celsius"));
    }

    /** TemperatureUnits.valueOf("Fahrenheit") should return TemperatureUnits.Fahrenheit */
    @Test
    public void testValueOfFahrenheit() {
        assertEquals(TemperatureUnits.Fahrenheit, TemperatureUnits.valueOf("Fahrenheit"));
    }

    /** TemperatureUnits.Celsius.getName() should return "Celsius" */
    @Test
    public void testCelsiusName() {
        assertEquals("Celsius", TemperatureUnits.Celsius.getName());
    }

    /** TemperatureUnits.Celsius.getSymbol() should return "℃" */
    @Test
    public void testCelsiusSymbol() {
        assertEquals("℃", TemperatureUnits.Celsius.getSymbol());
    }

    /** Converting 0℃ to ℃ should yield 0. */
    @Test
    public void test0CelsiusToCelsius() {
        assertEquals(0, TemperatureUnits.Celsius.convert(0, TemperatureUnits.Celsius), 0);
    }

    /** Converting 32℃ to ℃ should yield 32. */
    @Test
    public void test32CelsiusToCelsius() {
        assertEquals(32, TemperatureUnits.Celsius.convert(32, TemperatureUnits.Celsius), 0);
    }

    /** Converting 100℃ to ℃ should yield 100. */
    @Test
    public void test100CelsiusToCelsius() {
        assertEquals(100, TemperatureUnits.Celsius.convert(100, TemperatureUnits.Celsius), 0);
    }

    /** TemperatureUnits.Fahrenheit.getName() should return "Fahrenheit" */
    @Test
    public void testFahrenheitName() {
        assertEquals("Fahrenheit", TemperatureUnits.Fahrenheit.getName());
    }

    /** TemperatureUnits.Fahrenheit.getSymbol() should return "℉" */
    @Test
    public void testFahrenheitSymbol() {
        assertEquals("℉", TemperatureUnits.Fahrenheit.getSymbol());
    }

    /** Converting 0℉ to ℃ should yield -17.7777 ±0.00001. */
    @Test
    public void test0FahrenheitToCelsius() {
        assertEquals(-17.7777, TemperatureUnits.Fahrenheit.convert(0, TemperatureUnits.Celsius), 0.0001);
    }

    /** Converting 32℉ to ℃ should yield 0 ±0. */
    @Test
    public void test32FahrenheitToCelsius() {
        assertEquals(0, TemperatureUnits.Fahrenheit.convert(32, TemperatureUnits.Celsius), 0);
    }

    /** Converting 212℉ to ℃ should yield 100 ±0. */
    @Test
    public void test212FahrenheitToCelsius() {
        assertEquals(100, TemperatureUnits.Fahrenheit.convert(212, TemperatureUnits.Celsius), 0);
    }

    /** Converting 0℃ to ℉ should yield 32 ±0. */
    @Test
    public void test0CelsiusToFahrenheit() {
        assertEquals(32.0, TemperatureUnits.Celsius.convert(0, TemperatureUnits.Fahrenheit), 0);
    }

    /** Converting 32℃ to ℉ should yield 89.6 ±0. */
    @Test
    public void test32CelsiusToFahrenheit() {
        assertEquals(89.6, TemperatureUnits.Celsius.convert(32, TemperatureUnits.Fahrenheit), 0);
    }

    /** Converting 100℃ to ℉ should yield 212 ±0. */
    @Test
    public void test100CelsiusToFahrenheit() {
        assertEquals(212, TemperatureUnits.Celsius.convert(100, TemperatureUnits.Fahrenheit), 0);
    }

}
