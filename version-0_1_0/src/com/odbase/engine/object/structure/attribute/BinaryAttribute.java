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
* BinaryAttribute.java                                                        *
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
public class BinaryAttribute extends com.odbase.engine.object.structure.attribute.BasicAttribute {
    private com.odbase.engine.object.structure.domain.StreamDomain   binaryDomain;
    private byte[]                                 value;
    /** Creates a new instance of StringAttribute */
    protected BinaryAttribute(byte attributeType, int attributeLength, int attributeSize, boolean clean) {
        super(attributeType, attributeLength, attributeSize, clean);
        attributeDomain = binaryDomain = new com.odbase.engine.object.structure.domain.StreamDomain(attributeLength, attributeSize, clean);
        value = new byte[binaryDomain.getUnitSize()];
    }
    
    public byte[] readBinary()
    throws IOException {
        return value;
    }

    public void writeBinary(byte[] data)
    throws IOException {
        // not: valu.length = unitSize of binaryDomain
        for (int i = 0; i < value.length; i++) {
            value[i] = data[i];
        }
        attributeModified = true;
    }
    
    public void commiteWrite()
    throws IOException {
        if (attributeModified) {
            binaryDomain.save(value);
            attributeModified = false;
        }
    }
    
    public void moveFirst()
    throws IOException {
        attributeDomain.seekToBegin();
    }
    
    public void moveNext()
    throws IOException {
        value = binaryDomain.readForward();
    }
    
    public void movePrevious()
    throws IOException {
        value = binaryDomain.readBackward();
    }
    
    public void moveLast()
    throws IOException {
        binaryDomain.seekToEnd();
    }
}
