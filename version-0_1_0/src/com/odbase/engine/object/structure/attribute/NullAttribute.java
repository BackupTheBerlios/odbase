/*-----------------------------------------------------------------------------*
*                            ODBASE(TM) SOFTWARE                              *
*          SOURCE CODE FILES/SYSTEM BINARY FILES/PROPRIETARY LICENSE          *
*           COPYRIGHT(C) 2002-2003, SINAI UNBREAKABLE SYSTEMS, INC.           *
*                             ALL RIGHTS RESERVED                             *
*                                                                             *
* = Mohammad Nabil Mostafa odbase2003@yahoo.com                               *
*                                                                             *
* Redistribution and use in source and binary forms, with or without          *
* modification, are permitted provided that the following conditions are met: *
*                                                                             *
* 1- Redistributions of source code must retain the above copyright notice,   *
*    this list of conditions and the following disclaimer.                    *
* 2- Redistributions in binary form must reproduce the above copyright        *
*    notice, this list of conditions and the following disclaimer in the      *
*    documentation and/or other materials provided with the distribution.     *
* 3- Neither the name of the [SINAI UNBREAKABLE SYSTEMS, INC] nor the names   *
*    of its contributors may be used to endorse or promote products derived   *
*    from this software without specific prior written permission.            *
*                                                                             *
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" *
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE   *
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE  *
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE    *
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR         *
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF        *
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS    *
* INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN     *
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)     *
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE  *
* POSSIBILITY OF SUCH DAMAGE.                                                 *
*-----------------------------------------------------------------------------*
* NullAttribute.java                                                          *
* Created on 2003-01-01, 1:00 AM                                              *
*-----------------------------------------------------------------------------*/

package com.odbase.engine.object.structure.attribute;

import java.io.*;
import java.util.*;

/** 
 *
 * <b>Description:</b><br>
 *
 * <b>Scenario:</b><br>
 *
 * @author Mohammad Nabil Mostafa
 * @version 0.1 <a href="#License">Netmate(TM) Software SOURCE CODE FILES/SYSTEM BINARY FILES/PROPRIETARY License</a>
 * @since 0.1
 *
 */
public class NullAttribute extends com.odbase.engine.object.structure.attribute.BasicAttribute {
    private com.odbase.engine.object.structure.domain.StreamDomain   nullDomain;
    /**
     * a byte attribute that holds informations about whether if attribute
     * is null or not, attributeIndex indicates the number of bit that
     * record should test, from right, to see if it is:
     * 1 so attribute is null
     * 0 so attribute is not null
     */
    private byte[]                                  value;
    
    /**
     * a bit mask for setting bit n to be 1, using one of first 8 masks
     * also
     * a bit mask for unsetting bit n to be 0, using one of last 8 masks
     */
    private static byte[] BIT_MASK
        =   {(byte)0x80/*10000000*/, (byte)0x40,/*01000000*/
            (byte)0x20/*00100000*/, (byte)0x10,/*00010000*/
            (byte)0x08/*00001000*/, (byte)0x04,/*00000100*/
            (byte)0x02/*00000010*/, (byte)0x01,/*00000001*/
            (byte)0x7F/*01111111*/, (byte)0xBF,/*10111111*/
            (byte)0xDF/*11011111*/, (byte)0xEF,/*11101111*/
            (byte)0xF7/*11110111*/, (byte)0xFB,/*11111011*/
            (byte)0xFD/*11111101*/, (byte)0xFE/*11111110*/};
    /** Creates a new instance of NullAttribute */
            protected NullAttribute(byte attributeType, int attributeLength, int attributeSize, boolean clean) {
        super(attributeType, attributeLength, attributeSize, clean);
        attributeDomain = nullDomain = new com.odbase.engine.object.structure.domain.StreamDomain(attributeLength, attributeSize, clean);
        value = new byte[nullDomain.getUnitSize()];
    }

    public void setNullAttribute(int attributeIndex, boolean setNull) {
        /*
         * value == true  -> set to null
         * value == false -> set to not null
         */
        if (setNull) {
            /*
             * set bit -> 1
             * it is null
             */
            value[attributeIndex / 8] |= BIT_MASK[attributeIndex % 8];
        } else {
            /*
             * unset bit -> 0
             * it is not null
             */
            value[attributeIndex / 8] &= BIT_MASK[(attributeIndex % 8) + 8];
        }
        attributeModified = true;
    }
    
    public boolean isNullAttribute(int attributeIndex) {
        /*
         * true  == null -> bit was set   1.
         * false != null -> bit was unset 0.
         */
        return (value[attributeIndex / 8] | BIT_MASK[attributeIndex % 8]) == value[attributeIndex / 8];
    }
    
    public byte[] readNull()
    throws IOException {
        return value;
    }

    public void writeNull(byte[] data)
    throws IOException {
        // not: valu.length = unitSize of StreamDomain
        for (int i = 0; i < value.length; i++) {
            value[i] = data[i];
        }
        attributeModified = true;
    }
    
    public void commiteWrite()
    throws IOException {
        if (attributeModified) {
            nullDomain.save(value);
            attributeModified = false;
        }
    }
    
    public void moveNext()
    throws IOException {
        value = nullDomain.readForward();
    }
    
    public void movePrevious()
    throws IOException {
        value = nullDomain.readBackward();
    }
}
