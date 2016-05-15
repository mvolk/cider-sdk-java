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

/**
 * Converts dimensioned values to and from the reference (base) units of measurement selected by the implementation.
 */
interface ConversionLogic {

    /**
     * Converts a dimensioned value to the reference unit of measurement selected by the implementation.
     *
     * @param value a dimensioned value
     * @return a dimensioned value expressed in the reference unit of measurement selected by the implementation
     *         and semantically equivalent to {@code value}.
     */
    double fromReferenceUnits(double value);

    /**
     * Converts a dimensioned value from the reference unit of measurement selected by the implementation to the
     * implementation's native unit of measurement.
     *
     * @param value a dimensioned value expressed in the reference unit of measurement selected by the implementation.
     * @return a dimensioned value expressed in the implementation's native unit of measurement and semantically
     *         equivalent to {@code value}.
     */
    double toReferenceUnits(double value);

}