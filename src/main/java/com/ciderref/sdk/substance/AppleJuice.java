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

import com.ciderref.sdk.property.MassConcentration;
import com.ciderref.sdk.property.SpecificGravity;
import com.ciderref.sdk.property.SugarConcentrationProfile;

/**
 * Properties of apple juice.
 */
public interface AppleJuice {

    /**
     * The sugar concentration profile for this juice.
     *
     * @return (not null) the sugar concentration profile for apple juice of this type.
     */
    SugarConcentrationProfile getSugarConcentrationProfile();

    /**
     * The total solids in apple juice of the given density. Total solids includes both sugar and sugar-free dry
     * extract (SDFE). To derive SDFE, subtract sugar content from total solids.
     *
     * @param specificGravity (not null) actual specific gravity.
     * @return (not null) the total solids in apple juice of the given specific gravity.
     * @throws IllegalArgumentException if {@code specificGravity} is null.
     */
    MassConcentration getTotalSolidsConcentration(SpecificGravity specificGravity);

}
