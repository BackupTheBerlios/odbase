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
* BasicAttribute.java                                                         *
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
public abstract class BasicAttribute implements com.odbase.engine.object.structure.attribute.Attribute {
    protected byte                                  attributeType;
    protected com.odbase.engine.object.structure.domain.Domain        attributeDomain;
    protected boolean                               attributeModified;

    protected BasicAttribute(byte attributeType, int attributeLength, int attributeSize, boolean clean) {
        this.attributeType      = attributeType;
        attributeModified       = false;
    }
    
    public static Attribute createAttribute(byte attributeType, int attributeLength, int attributeSize, boolean clean)
    throws UnsupportedAttributeException{
        switch(attributeType) {
            case NULL:
                return new NullAttribute(attributeType, attributeLength, attributeSize, clean);
            case BOOLEAN:
                return new BooleanAttribute(attributeType, attributeLength, BOOLEAN_SIZE, clean);
            case CHARACTER:
                return new CharAttribute(attributeType, attributeLength, CHARACTER_SIZE, clean);
            case BYTE:
                return new ByteAttribute(attributeType, attributeLength, BYTE_SIZE, clean);
            case SHORT:
                return new ShortAttribute(attributeType, attributeLength, SHORT_SIZE, clean);
            case INTEGER:
                return new IntegerAttribute(attributeType, attributeLength, INTEGER_SIZE, clean);
            case FLOAT:
                return new FloatAttribute(attributeType, attributeLength, FLOAT_SIZE, clean);
            case LONG:
                return new LongAttribute(attributeType, attributeLength, LONG_SIZE, clean);
            case DOUBLE:
                return new DoubleAttribute(attributeType, attributeLength, DOUBLE_SIZE, clean);
            case STRING:
                return new StringAttribute(attributeType, attributeLength, attributeSize, clean);
            case TIME:
                //return new TimeAttribute(attributeType, attributeLength, attributeSize, clean);
            case BINARY:
                //return new BinaryAttribute(attributeType, attributeLength, attributeSize, clean);
            default:
                throw new UnsupportedAttributeException(
                "can't instanciate unsupported Attribute type " + attributeType);
        }
    }
    
    /**
     * this method is useful in putting it in switch(case){} block
     * to simplify the fetshing operation.
     * @return returns the byte value that declars Attribute type.
     */
    public final byte getType(){
        return attributeType;
    }
    
    /**
     * @return returns attribute type name.
     */
    public final String getTypeName() {
        return attributeTypeName[attributeType];
    }
    
    public static String getTypeName(byte attributeType) {
        return attributeTypeName[attributeType];
    }

    /**
     * this method is usful in putting it in
     * for (; < Attribute.getCardinality();) {} block
     * to simplify the fetshing operation.
     * @return returns the number of elements in selected Attribute
     */
    public final int getCardinality() {
        return attributeDomain.getCardinality();
    }
    
    /**
     * gets the total amount of btes allocated in memory for this attribute.
     * @return returns attribute size in momory in bytes.
     */
    public final int getSize() {
        return attributeDomain.getSize();
    }
    
    public void moveFirst()
    throws IOException {
        attributeDomain.seekToBegin();
    }
    
    public abstract void moveNext() throws IOException;
    public abstract void movePrevious() throws IOException;
    
    public void moveLast()
    throws IOException {
        attributeDomain.seekToEnd();
   }
    
    /**
     * to know if shall i update before moving next or not
     */
    public boolean isModified() {
        return attributeModified;
    }
    
    /**
     * checkes whether Begin of Attribute reached or not
     */
    public final boolean isBeginOfAttribute() {
        return attributeDomain.isBeginOfDomain();
    }
    
    /**
     * checkes whether End of Attribute reached or not
     */
    public final boolean isEndOfAttribute() {
        return attributeDomain.isEndOfDomain();
    }

    /*
     * now create helper methods that will read needed
     * Attribute: 
     *  0- null      -> n bytes read.
     *  1- boolean   -> 1 byte  read.
     *  2- byte      -> 1 byte  read.
     *  3- char      -> 2 bytes read.
     *  4- short     -> 2 bytes read.
     *  5- integer   -> 4 bytes read.
     *  6- float     -> 4 bytes read.
     *  7- long      -> 8 bytes read.
     *  8- double    -> 8 bytes read.
     *  9- string    -> n bytes read.
     * 10- binary    -> n bytes read.
     */
    
    public byte[] readNull()
    throws IOException {
        throw new IOException("unsupported operation, can't read null from "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public boolean readBoolean()
    throws IOException {
        throw new IOException("unsupported operation, can't read boolean from "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public byte readByte()
    throws IOException {
        throw new IOException("unsupported operation, can't read byte from "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public char readChar()
    throws IOException {
        throw new IOException("unsupported operation, can't read char from "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public short readShort()
    throws IOException {
        throw new IOException("unsupported operation, can't read short from "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public int readInteger()
    throws IOException {
        throw new IOException("unsupported operation, can't read integer from "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public float readFloat()
    throws IOException {
        throw new IOException("unsupported operation, can't read float from "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public long readLong()
    throws IOException {
        throw new IOException("unsupported operation, can't read integer from "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public double readDouble()
    throws IOException {
        throw new IOException("unsupported operation, can't read double from "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public char[] readString()
    throws IOException {
        throw new IOException("unsupported operation, can't read string from "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public byte[] readBinary()
    throws IOException {
        throw new IOException("unsupported operation, can't read string from "
            + attributeTypeName[attributeType] + " Attribute");
    }

    /*
     * now create helper methods that will write needed
     * Attribute: 
     *  0- null      -> n bytes write.
     *  1- boolean   -> 1 byte  write.
     *  2- byte      -> 1 byte  write.
     *  3- char      -> 2 bytes write.
     *  4- short     -> 2 bytes write.
     *  5- integer   -> 4 bytes write.
     *  6- float     -> 4 bytes write.
     *  7- long      -> 8 bytes write.
     *  8- double    -> 8 bytes write.
     *  9- string    -> n bytes write.
     * 10- binary    -> n bytes write.
     */
    
    public void writeNull(byte[] data)
    throws IOException {
        throw new IOException("unsupported operation, can't write null to "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public void writeBoolean(boolean data)
    throws IOException {
        throw new IOException("unsupported operation, can't write boolean to "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public void writeByte(byte data)
    throws IOException {
        throw new IOException("unsupported operation, can't write byte to "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public void writeChar(char data)
    throws IOException {
        throw new IOException("unsupported operation, can't write char to "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public void writeShort(short data)
    throws IOException {
        throw new IOException("unsupported operation, can't write short to "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public void writeInteger(int data)
    throws IOException {
        throw new IOException("unsupported operation, can't write integer to "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public void writeFloat(float data)
    throws IOException {
        throw new IOException("unsupported operation, can't write float to "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public void writeLong(long data)
    throws IOException {
        throw new IOException("unsupported operation, can't write long to "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public void writeDouble(double data)
    throws IOException {
        throw new IOException("unsupported operation, can't write double to "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public void writeString(char[] data)
    throws IOException {
        throw new IOException("unsupported operation, can't write string to "
            + attributeTypeName[attributeType] + " Attribute");
    }
    
    public void writeBinary(byte[] data)
    throws IOException {
        throw new IOException("unsupported operation, can't write binary to "
            + attributeTypeName[attributeType] + " Attribute");
    }
}
