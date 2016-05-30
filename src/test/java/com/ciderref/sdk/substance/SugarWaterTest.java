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

import com.ciderref.sdk.property.DegreesBrix;
import com.ciderref.sdk.property.SpecificGravity;
import org.junit.Test;

/**
 * Unit tests for {@link SugarWater}.
 */
public class SugarWaterTest {

    /** Throws if getBrix is passed null. */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBrixNull() {
        new SugarWater().getBrix(null);
    }

    /**
     * Confirm getBrix against NIST table, available online at:
     * http://www.boulder.nist.gov/div838/SelectedPubs/Circular%20440%20Table%20114.pdf.
     * Regression fit within ±0.0015 degrees Brix considered passing.
     */
    @Test
    public void testGetBrix() {
        testGetBrix(1.00000,  0.0);
        testGetBrix(1.00390,  1.0);
        testGetBrix(1.00780,  2.0);
        testGetBrix(1.01173,  3.0);
        testGetBrix(1.01569,  4.0);
        testGetBrix(1.01968,  5.0);
        testGetBrix(1.02369,  6.0);
        testGetBrix(1.02773,  7.0);
        testGetBrix(1.03180,  8.0);
        testGetBrix(1.03590,  9.0);
        testGetBrix(1.04003, 10.0);
        testGetBrix(1.04418, 11.0);
        testGetBrix(1.04837, 12.0);
        testGetBrix(1.05259, 13.0);
        testGetBrix(1.05683, 14.0);
        testGetBrix(1.06111, 15.0);
        testGetBrix(1.06542, 16.0);
        testGetBrix(1.06976, 17.0);
        testGetBrix(1.07413, 18.0);
        testGetBrix(1.07853, 19.0);
        testGetBrix(1.08297, 20.0);
        testGetBrix(1.08744, 21.0);
        testGetBrix(1.09194, 22.0);
        testGetBrix(1.09647, 23.0);
    }

    private void testGetBrix(double sgValue, double expectedBrixValue) {
        SpecificGravity specificGravity = new SpecificGravity(sgValue);
        DegreesBrix actualDegreesBrix = new SugarWater().getBrix(specificGravity);
        assertEquals(expectedBrixValue, actualDegreesBrix.getValue(), 0.0015);
    }

}
