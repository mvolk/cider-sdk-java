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

package com.ciderref.sdk.substance;

import static org.junit.Assert.assertEquals;

import com.ciderref.sdk.property.IllegalPropertyValueException;
import com.ciderref.sdk.property.Temperature;
import com.ciderref.sdk.property.TemperatureUnits;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link Water}. Test examples drawn from
 * http://www.engineeringtoolbox.com/water-density-specific-weight-d_595.html,
 * http://www.engineeringtoolbox.com/water-thermal-properties-d_162.html,
 * and The New Cidermaker's Handbook by Claude Jolicoeur, published in 2013 by Chelsea Green Publishing in White
 * River Junction, Vermont.
 */
@SuppressWarnings("PMD.TooManyMethods")
public class WaterTest {

    private Water water;

    /**
     * Instantiate a fresh instance of {@link Water} before each test case.
     */
    @Before
    public void setUp() {
        water = new Water();
    }

    /** Freezing point is correct. */
    @Test
    public void testStandardFreezingPoint() {
        assertEquals(0, Water.STANDARD_FREEZING_POINT.getValue(TemperatureUnits.Celsius), 0);
    }

    /** Boiling point is correct. */
    @Test
    public void testStandardBoilingPoint() {
        assertEquals(100, Water.STANDARD_BOILING_POINT.getValue(TemperatureUnits.Celsius), 0);
    }

    /** getDensity throws if temperature is below freezing. */
    @Test(expected = IllegalPropertyValueException.class)
    public void testGetDensityOfIce() {
        water.getDensity(new Temperature(31.99, TemperatureUnits.Fahrenheit));
    }

    /** getDensity throws if temperature is above boiling. */
    @Test(expected = IllegalPropertyValueException.class)
    public void testGetDensityOfWaterVapor() {
        water.getDensity(new Temperature(212.01, TemperatureUnits.Fahrenheit));
    }

    /** getDensity returns 999.8 g/L ±0.10 at 0℃. */
    @Test
    public void testGetDensityOfWaterAt0Celsius() {
        assertEquals(999.8, water.getDensity(new Temperature(0, TemperatureUnits.Celsius)), 0.1);
    }

    /** getDensity returns 1000.0 g/L ±0.1 at 4℃. */
    @Test
    public void testGetDensityOfWaterAt4Celsius() {
        assertEquals(1000.0, water.getDensity(new Temperature(4, TemperatureUnits.Celsius)), 0.1);
    }

    /** getDensity returns 1000.0 g/L ±0.1 at 5℃. */
    @Test
    public void testGetDensityOfWaterAt5Celsius() {
        assertEquals(1000.0, water.getDensity(new Temperature(5, TemperatureUnits.Celsius)), 0.1);
    }

    /** getDensity returns 999.7 g/L ±0.05 at 10℃. */
    @Test
    public void testGetDensityOfWaterAt10Celsius() {
        assertEquals(999.7, water.getDensity(new Temperature(10, TemperatureUnits.Celsius)), 0.05);
    }

    /** getDensity returns 999.0 g/L ±0.05 at 60℉. */
    @Test
    public void testGetDensityOfWaterAt60Fahrenheit() {
        assertEquals(999.0, water.getDensity(new Temperature(60, TemperatureUnits.Fahrenheit)), 0.05);
    }

    /** getDensity returns 998.2 g/L ±0.05 at 20℃. */
    @Test
    public void testGetDensityOfWaterAt15Celsius() {
        assertEquals(998.2, water.getDensity(new Temperature(20, TemperatureUnits.Celsius)), 0.05);
    }

    /** getDensity returns 997.0 g/L ±0.1 at 25℃. */
    @Test
    public void testGetDensityOfWaterAt25Celsius() {
        assertEquals(997.0, water.getDensity(new Temperature(25, TemperatureUnits.Celsius)), 0.1);
    }

    /** getDensity returns 995.7 g/L ±0.1 at 30℃. */
    @Test
    public void testGetDensityOfWaterAt30Celsius() {
        assertEquals(995.7, water.getDensity(new Temperature(30, TemperatureUnits.Celsius)), 0.1);
    }

    /** getDensity returns 994.1 g/L ±0.1 at 35℃. */
    @Test
    public void testGetDensityOfWaterAt35Celsius() {
        assertEquals(994.1, water.getDensity(new Temperature(35, TemperatureUnits.Celsius)), 0.1);
    }

    /** getDensity returns 992.2 g/L ±0.1 at 40℃. */
    @Test
    public void testGetDensityOfWaterAt40Celsius() {
        assertEquals(992.2, water.getDensity(new Temperature(40, TemperatureUnits.Celsius)), 0.1);
    }

    /** getDensity returns 990.2 g/L ±0.1 at 45℃. */
    @Test
    public void testGetDensityOfWaterAt45Celsius() {
        assertEquals(990.2, water.getDensity(new Temperature(45, TemperatureUnits.Celsius)), 0.1);
    }

    /** getDensity returns 988.1 g/L ±0.1 at 50℃. */
    @Test
    public void testGetDensityOfWaterAt50Celsius() {
        assertEquals(988.1, water.getDensity(new Temperature(50, TemperatureUnits.Celsius)), 0.1);
    }

    /** getDensity returns 983.2 g/L ±0.1 at 60℃. */
    @Test
    public void testGetDensityOfWaterAt60Celsius() {
        assertEquals(983.2, water.getDensity(new Temperature(60, TemperatureUnits.Celsius)), 0.1);
    }

    /** getDensity returns 977.8 g/L ±0.1 at 70℃. */
    @Test
    public void testGetDensityOfWaterAt70Celsius() {
        assertEquals(977.8, water.getDensity(new Temperature(70, TemperatureUnits.Celsius)), 0.1);
    }

    /** getDensity returns 971.8 g/L ±0.1 at 80℃. */
    @Test
    public void testGetDensityOfWaterAt80Celsius() {
        assertEquals(971.8, water.getDensity(new Temperature(80, TemperatureUnits.Celsius)), 0.1);
    }

    /** getDensity returns 965.3 g/L ±0.1 at 90℃. */
    @Test
    public void testGetDensityOfWaterAt90Celsius() {
        assertEquals(965.3, water.getDensity(new Temperature(90, TemperatureUnits.Celsius)), 0.1);
    }

    /** getDensity returns 958.6 g/L ±0.1 at 100℃. */
    @Test
    public void testGetDensityOfWaterAt100Celsius() {
        assertEquals(958.6, water.getDensity(new Temperature(100, TemperatureUnits.Celsius)), 0.1);
    }

}