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

import com.ciderref.sdk.math.IdentityFunction;
import org.junit.Test;

/**
 * Unit tests for {@link TableOfConversionFunctions}.
 */
public class TableOfConversionFunctionsTest {

    /** put(...) should throw if fromUnits is null. */
    @Test(expected = IllegalArgumentException.class)
    public void testPutNullFromUnitsThrows() {
        new TableOfConversionFunctions<>(UnitsOfMass.values())
                .put(null, UnitsOfMass.Grams, new IdentityFunction());
    }

    /** put(...) should throw if toUnits is null. */
    @Test(expected = IllegalArgumentException.class)
    public void testPutNullToUnitsThrows() {
        new TableOfConversionFunctions<>(UnitsOfMass.values())
                .put(UnitsOfMass.Grams, null, new IdentityFunction());
    }

    /** put(...) should throw if fromUnits and toUnits are null. */
    @Test(expected = IllegalArgumentException.class)
    public void testPutNullFromAndToUnitsThrows() {
        new TableOfConversionFunctions<>(UnitsOfMass.values())
                .put(null, null, new IdentityFunction());
    }

}
