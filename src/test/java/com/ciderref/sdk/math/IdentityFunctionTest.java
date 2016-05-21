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

package com.ciderref.sdk.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * Unit tests for {@link IdentityFunction}.
 */
public class IdentityFunctionTest {

    /** Confirm that when passed a value the applyTo(double) function returns that argument. */
    @Test
    public void testApplyToWithZero() {
        assertEquals(0, new IdentityFunction().applyTo(0), 0);
    }

    /** Confirm that when passed a value the applyTo(double) function returns that argument. */
    @Test
    public void testApplyToWithNegative() {
        assertEquals(-5.2129, new IdentityFunction().applyTo(-5.2129), 0);
    }

    /** Confirm that when passed a value the applyTo(double) function returns that argument. */
    @Test
    public void testApplyToWithPositive() {
        assertEquals(912348.492, new IdentityFunction().applyTo(912348.492), 0);
    }

    /** Confirm that the inverse of the identity function is the identity function. */
    @Test
    public void testInverseIsSelf() {
        Function identityFunction = new IdentityFunction();
        assertSame(identityFunction.getInverse(), identityFunction);
    }

}
