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

/**
 * Converts measurements in one unit of mass to another unit of mass. Conversion factors are drawn from the
 * 2012  NIST Handbook 44, Appendix C: General Tables of Units and Measurement, available online at
 * http://www.nist.gov/pml/wmd/pubs/upload/AppC-12-hb44-final.pdf.
 */
public class MassConversionFunctions extends TableOfConversionFunctions<UnitsOfMass> {

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

    /** Constructor. */
    public MassConversionFunctions() {
        super(UnitsOfMass.values());
        
        put(UnitsOfMass.Kilograms, UnitsOfMass.Kilograms, IDENTITY_FUNCTION);
        put(UnitsOfMass.Kilograms, UnitsOfMass.Grams, KILOGRAMS_TO_GRAMS);
        put(UnitsOfMass.Kilograms, UnitsOfMass.Pounds, POUNDS_TO_KILOGRAMS.getInverse());
        put(UnitsOfMass.Kilograms, UnitsOfMass.Ounces, OUNCES_TO_KILOGRAMS.getInverse());

        put(UnitsOfMass.Grams, UnitsOfMass.Kilograms, KILOGRAMS_TO_GRAMS.getInverse());
        put(UnitsOfMass.Grams, UnitsOfMass.Grams, IDENTITY_FUNCTION);
        put(UnitsOfMass.Grams, UnitsOfMass.Pounds, POUNDS_TO_GRAMS.getInverse());
        put(UnitsOfMass.Grams, UnitsOfMass.Ounces, OUNCES_TO_GRAMS.getInverse());

        put(UnitsOfMass.Pounds, UnitsOfMass.Kilograms, POUNDS_TO_KILOGRAMS);
        put(UnitsOfMass.Pounds, UnitsOfMass.Grams, POUNDS_TO_GRAMS);
        put(UnitsOfMass.Pounds, UnitsOfMass.Pounds, IDENTITY_FUNCTION);
        put(UnitsOfMass.Pounds, UnitsOfMass.Ounces, POUNDS_TO_OUNCES);

        put(UnitsOfMass.Ounces, UnitsOfMass.Kilograms, OUNCES_TO_KILOGRAMS);
        put(UnitsOfMass.Ounces, UnitsOfMass.Grams, OUNCES_TO_GRAMS);
        put(UnitsOfMass.Ounces, UnitsOfMass.Pounds, POUNDS_TO_OUNCES.getInverse());
        put(UnitsOfMass.Ounces, UnitsOfMass.Ounces, IDENTITY_FUNCTION);
    }

}
