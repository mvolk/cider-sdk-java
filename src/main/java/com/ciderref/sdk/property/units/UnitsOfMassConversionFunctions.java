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

import com.ciderref.sdk.math.ConversionFactorFunction;
import com.ciderref.sdk.math.Function;
import com.ciderref.sdk.math.IdentityFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts measurements in one unit of mass to another unit of mass. Conversion factors are drawn from the
 * 2012  NIST Handbook 44, Appendix C: General Tables of Units and Measurement, available online at
 * http://www.nist.gov/pml/wmd/pubs/upload/AppC-12-hb44-final.pdf.
 */
public class UnitsOfMassConversionFunctions {

    // The following conversion factors are exact
    protected static final double KILOGRAMS_PER_POUND = 0.45359237;
    protected static final double KILOGRAMS_PER_OUNCE = 0.028349523125;
    protected static final double GRAMS_PER_KILOGRAM = 1000.0;
    protected static final double GRAMS_PER_POUND = 453.59237;
    protected static final double GRAMS_PER_OUNCE = 28.349523125;
    protected static final double OUNCES_PER_POUND = 16.0;

    protected static final Function IDENTITY_FUNCTION = new IdentityFunction();

    protected static final Function KILOGRAMS_TO_GRAMS = new ConversionFactorFunction(GRAMS_PER_KILOGRAM);
    protected static final Function POUNDS_TO_KILOGRAMS = new ConversionFactorFunction(KILOGRAMS_PER_POUND);
    protected static final Function POUNDS_TO_GRAMS = new ConversionFactorFunction(GRAMS_PER_POUND);
    protected static final Function POUNDS_TO_OUNCES = new ConversionFactorFunction(OUNCES_PER_POUND);
    protected static final Function OUNCES_TO_KILOGRAMS = new ConversionFactorFunction(KILOGRAMS_PER_OUNCE);
    protected static final Function OUNCES_TO_GRAMS = new ConversionFactorFunction(GRAMS_PER_OUNCE);

    private final Map<UnitsOfMass, Map<UnitsOfMass, Function>> functionMap;

    /** Constructor. */
    public UnitsOfMassConversionFunctions() {
        functionMap = new HashMap<>();
        for (UnitsOfMass unitsOfMass : UnitsOfMass.values()) {
            functionMap.put(unitsOfMass, new HashMap<UnitsOfMass, Function>()); // NOPMD
        }

        functionMap.get(UnitsOfMass.Kilograms).put(UnitsOfMass.Kilograms, IDENTITY_FUNCTION);
        functionMap.get(UnitsOfMass.Kilograms).put(UnitsOfMass.Grams, KILOGRAMS_TO_GRAMS);
        functionMap.get(UnitsOfMass.Kilograms).put(UnitsOfMass.Pounds, POUNDS_TO_KILOGRAMS.getInverse());
        functionMap.get(UnitsOfMass.Kilograms).put(UnitsOfMass.Ounces, OUNCES_TO_KILOGRAMS.getInverse());

        functionMap.get(UnitsOfMass.Grams).put(UnitsOfMass.Kilograms, KILOGRAMS_TO_GRAMS.getInverse());
        functionMap.get(UnitsOfMass.Grams).put(UnitsOfMass.Grams, IDENTITY_FUNCTION);
        functionMap.get(UnitsOfMass.Grams).put(UnitsOfMass.Pounds, POUNDS_TO_GRAMS.getInverse());
        functionMap.get(UnitsOfMass.Grams).put(UnitsOfMass.Ounces, OUNCES_TO_GRAMS.getInverse());

        functionMap.get(UnitsOfMass.Pounds).put(UnitsOfMass.Kilograms, POUNDS_TO_KILOGRAMS);
        functionMap.get(UnitsOfMass.Pounds).put(UnitsOfMass.Grams, POUNDS_TO_GRAMS);
        functionMap.get(UnitsOfMass.Pounds).put(UnitsOfMass.Pounds, IDENTITY_FUNCTION);
        functionMap.get(UnitsOfMass.Pounds).put(UnitsOfMass.Ounces, POUNDS_TO_OUNCES);

        functionMap.get(UnitsOfMass.Ounces).put(UnitsOfMass.Kilograms, OUNCES_TO_KILOGRAMS);
        functionMap.get(UnitsOfMass.Ounces).put(UnitsOfMass.Grams, OUNCES_TO_GRAMS);
        functionMap.get(UnitsOfMass.Ounces).put(UnitsOfMass.Pounds, POUNDS_TO_OUNCES.getInverse());
        functionMap.get(UnitsOfMass.Ounces).put(UnitsOfMass.Ounces, IDENTITY_FUNCTION);
    }

    /**
     * Obtain a function for converting a measurement expressed in terms of {@code fromUnits} to a measurement
     * expressed in terms of {@code toUnits}.
     *
     * @param fromUnits (not null) the units that the function needs to convert from
     * @param toUnits (not null) the units that the function needs to convert to
     * @return (not null) the conversion function
     * @throws IllegalArgumentException if {@code fromUnits} or {@code toUnits} is {@code null}.
     */
    public Function getFunction(UnitsOfMass fromUnits, UnitsOfMass toUnits) {
        if (fromUnits == null || toUnits == null) {
            throw new IllegalArgumentException("Conversion to or from null units is not possible");
        }
        return functionMap.get(fromUnits).get(toUnits);
    }

}
