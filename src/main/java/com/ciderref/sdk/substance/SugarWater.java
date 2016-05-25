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

import com.ciderref.sdk.property.Brix;
import com.ciderref.sdk.property.SpecificGravity;

/**
 * Properties of a solution of pure water and sucrose.
 */
public class SugarWater {

    /**
     * Determine the Brix given the specific gravity.
     *
     * @param specificGravity (not null) the specific gravity of the solution
     * @return (not null) the Brix of the solution
     * @throws IllegalArgumentException if {@code specificGravity} is null.
     */
    public Brix getBrix(SpecificGravity specificGravity) {
        if (specificGravity == null) {
            throw new IllegalArgumentException("Specific gravity must be known to determine Brix.");
        }
        // From https://en.wikipedia.org/wiki/Brix, valid for specific gravities of less than 1.17874 (40 degrees Brix)
        double value = ((182.4601 * specificGravity.getValue() - 775.6821) * specificGravity.getValue() + 1262.7794)
                * specificGravity.getValue() - 669.5622;
        if (Double.compare(value, 0) < 0) {
            // Brix less than zero (meaning a negative sucrose mass concentration) is not physically possible, but
            //  can be produced by the regression fit equation above. Simply force such mis-fits to zero, since
            //  effectively what this means is that there is no sugar in the water.
            value = 0;
        }
        return new Brix(value);
    }

}
