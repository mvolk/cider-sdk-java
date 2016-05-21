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

package com.ciderref.sdk.property.units;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.ciderref.sdk.math.Function;
import org.junit.Test;

/**
 * Unit tests for {@link CelsiusToFahrenheitFunction}.
 */
public class CelsiusToFahrenheitFunctionTest {

    /** getInverse() should be the inverse function with tolerance for floating-pound rounding errors. */
    @Test
    public void testInverse() {
        double degreesC = 18.25;
        Function function = new CelsiusToFahrenheitFunction();
        Function inverse = function.getInverse();
        assertEquals(degreesC, inverse.applyTo(function.applyTo(degreesC)), Math.ulp(degreesC));
    }

    /** f.getInverse().getInverse() should return f. */
    @Test
    public void testInverseOfInverse() {
        Function function = new CelsiusToFahrenheitFunction();
        assertSame(function, function.getInverse().getInverse());
    }

}
