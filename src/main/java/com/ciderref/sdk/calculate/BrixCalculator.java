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

package com.ciderref.sdk.calculate;

import com.ciderref.sdk.property.DegreesBrix;
import com.ciderref.sdk.property.IllegalPropertyValueException;
import com.ciderref.sdk.property.SpecificGravity;

/**
 * Calculates {@link DegreesBrix} given {@link SpecificGravity} and vice versa. Note that while Brix is technically
 * a measure of the sugar concentration in an aqueous solution, this calculator assumes that other solids in juice
 * behave similarly, and thus that Brix represents the ratio of the total mass of all dry extract (solids) in an
 * aqueous solution to the total mass of the solution, expressed as a percentage. This assumption is both useful and
 * sufficiently valid for apple juice.
 */
public class BrixCalculator {

    private static final double MAXIMUM_BRIX_FOR_SG_REGRESSION = 44.0;
    private static final double MINIMUM_SG_FOR_BRIX_REGRESSION = 1.0000185477666315;
    private static final double MAXIMUM_SG_FOR_BRIX_REGRESSION = 1.17875;

    /**
     * Determine the specific gravity given the degrees Brix. This method employs a regression formula from Claude
     * Jolicoeur's The New Cidermaker's Handbook, chapter 8. That formula is a third-order regression based on the NBS
     * C440 table. Accurate to within ±0.03°SG for Brix up to 44°Bx, where °SG are given by the formula
     * °SG = 1000 * (SG - 1).
     *
     * @param degreesBrix (not null) the ratio between the mass of solids in an aqueous solution and the mass of the
     *                    solution overall.
     * @return (not null) the specific gravity of the solution
     * @throws IllegalArgumentException if {@code degreesBrix} is null
     * @throws IllegalPropertyValueException if {@code degreesBrix} exceeds 44°Bx
     */
    public SpecificGravity getSpecificGravity(DegreesBrix degreesBrix) {
        if (degreesBrix == null) {
            throw new IllegalArgumentException("Specific gravity cannot be determined with knowledge of degrees Brix");
        }
        if (Double.compare(degreesBrix.getValue(), MAXIMUM_BRIX_FOR_SG_REGRESSION) > 0) {
            throw new IllegalPropertyValueException("This calculator is designed for Brix values not in excess of "
                    + "44°Bx");
        }
        return new SpecificGravity(1 + (3.8687 * degreesBrix.getValue() + 0.013048 * Math.pow(degreesBrix.getValue(), 2)
                + 0.0000487 * Math.pow(degreesBrix.getValue(), 3)) / 1000);
    }

    /**
     * Determine the degrees Brix given the specific gravity. This method employs a regression formula from
     * https://en.wikipedia.org/wiki/Brix, valid for specific gravities of less than 1.17875 (40 degrees Brix).
     * Accurate to within ±0.003°Bx for Brix up to 40°Bx.
     *
     * @param specificGravity (not null) the specific gravity of the solution
     * @return (not null) the ratio between the mass of solids in an aqueous solution and the mass of the
     *                    solution overall.
     * @throws IllegalArgumentException if {@code specificGravity} is null.
     * @throws IllegalPropertyValueException if the calculated Brix exceeds 40°Bx
     */
    public DegreesBrix getDegreesBrix(SpecificGravity specificGravity) {
        if (specificGravity == null) {
            throw new IllegalArgumentException("Specific gravity must be known to determine degrees Brix.");
        }
        if (Double.compare(specificGravity.getValue(), MAXIMUM_SG_FOR_BRIX_REGRESSION) > 0) {
            throw new IllegalPropertyValueException("This calculator is designed for specific gravities not in excess "
                    + "of 1.17875.");
        }
        if (Double.compare(specificGravity.getValue(), MINIMUM_SG_FOR_BRIX_REGRESSION) < 0) {
            // Below this value, the regression produces very small negative values, which of course isn't physically
            //  possible. Coerce these values to zero instead.
            return new DegreesBrix(0);
        }
        double brix = ((182.4601 * specificGravity.getValue() - 775.6821) * specificGravity.getValue() + 1262.7794)
                * specificGravity.getValue() - 669.5622;
        return new DegreesBrix(brix);
    }

}
