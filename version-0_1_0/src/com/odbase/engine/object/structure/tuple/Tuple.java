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
* Tuple.java                                                                  *
* Created on 2003-01-01, 1:00 AM                                              *
*-----------------------------------------------------------------------------*/

package com.odbase.engine.object.structure.tuple;

import java.io.*;
import java.util.*;
import com.odbase.engine.object.structure.attribute.*;

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
public class Tuple {
    /**
     * a data structure that will store feilds Objects.
     */
    private Object[]    attributes;
    
    /**
     * must be checked befor performing any work
     * on tuple's feilds.
     */
    private boolean     attributesInstanciated;
    
    /**
     * number of tuples stored in that Tuple
     */
    private int         degree;
    
    /**
     * number of attribute's cells in attribute domain
     */
    private int         cardinality;
    
    /**
     * types of attributes stored in that tuple
     */
    private int[]       types;
    
    /**
     * size of single line of tuple in memory
     * size * cardinality returns the number of bytes
     * in tuple domain
     */
    private int         size;
    
    /**
     * checks whether the tuple was written to or not
     */
    private boolean     modified;
    
    /**
     * checks if the tuple is read only tupleread only
     * or read write tuple.
     * read only  = true.
     * read write = false.
     */
    private boolean     readOnly;

    /**
     * a temporary attribute instead of creating and grbage collecting
     * new 4 bytes each time we need some thing from Interface Attribute
     */
    private Attribute       attribute;
    
    /**
     * used for bushing the attributes[] by n
     * to ensure that system attributes will
     * be stored on the first n cells;
     */
    private static byte SYSTEM_PUSH = 1;

    /** Creates new Tuple */
    public Tuple(int degree, int cardinality, boolean readOnly) {
        this.degree         = degree;
        this.cardinality    = cardinality;
        attributes             = new Object[this.degree + SYSTEM_PUSH];
        types               = new int[this.cardinality];
        this.readOnly       = readOnly;
        try {
            attributes[0]          = BasicAttribute.createAttribute(Attribute.NULL,
                                    this.cardinality, degree / 8 + 1, false);
        } catch (UnsupportedAttributeException x) {
            x.printStackTrace();
        }
        size                = degree / 8 + 1;
    }
    
    public void createAttributeInstance(int attributeIndex, byte attributeType,
                                    int dynamicAttributeUnitSize,
                                    boolean cleanOnFinalize)
    throws ArrayIndexOutOfBoundsException, UnsupportedAttributeException {
        /*
         * to push index by one for:
         * 1- NullAttribute Object;
         */
        attributeIndex += SYSTEM_PUSH;
        /** 
         * attributeUnitSize
         * just presented for
         * StringAttribute, or Complex Number
         * but it is fixed for other Attribute Types
         */
        /**
         * check for types array boundaries
         */
        if (attributeIndex > degree
            || attributeIndex < 0) {
                throw new ArrayIndexOutOfBoundsException("code 000000 0000000\n cause:\n1-\n2-\n3-");
        } else {
            types[attributeIndex] = attributeType;
        }

        /* 
         * switch type to receive the appropriate attribute instance in 
         * attributes Vector.
         */
        switch (types[attributeIndex]) {
            case Attribute.BOOLEAN:
                attributes[attributeIndex] = BasicAttribute.createAttribute(Attribute.BOOLEAN,
                    cardinality, Attribute.BOOLEAN_SIZE, cleanOnFinalize);
                size += Attribute.BOOLEAN_SIZE;
                break;
            case Attribute.BYTE:
                attributes[attributeIndex] = BasicAttribute.createAttribute(Attribute.BYTE,
                    cardinality, Attribute.BYTE_SIZE, cleanOnFinalize);
                size += Attribute.BYTE_SIZE;
                break;
            case Attribute.CHARACTER:
                attributes[attributeIndex] = BasicAttribute.createAttribute(Attribute.CHARACTER,
                    cardinality, Attribute.CHARACTER_SIZE, cleanOnFinalize);
                size += Attribute.CHARACTER_SIZE;
                break;
            case Attribute.SHORT:
                attributes[attributeIndex] = BasicAttribute.createAttribute(Attribute.SHORT,
                    cardinality, Attribute.SHORT_SIZE, cleanOnFinalize);
                size += Attribute.SHORT_SIZE;
                break;
            case Attribute.INTEGER:
                attributes[attributeIndex] = BasicAttribute.createAttribute(Attribute.INTEGER,
                    cardinality, Attribute.INTEGER_SIZE, cleanOnFinalize);
                size += Attribute.INTEGER_SIZE;
                break;
            case Attribute.FLOAT:
                attributes[attributeIndex] = BasicAttribute.createAttribute(Attribute.FLOAT,
                    cardinality, Attribute.FLOAT_SIZE, cleanOnFinalize);
                size += Attribute.FLOAT_SIZE;
                break;
            case Attribute.LONG:
                attributes[attributeIndex] = BasicAttribute.createAttribute(Attribute.LONG,
                    cardinality, Attribute.LONG_SIZE, cleanOnFinalize);
                size += Attribute.LONG_SIZE;
                break;
            case Attribute.DOUBLE:
                attributes[attributeIndex] = BasicAttribute.createAttribute(Attribute.DOUBLE,
                    cardinality, Attribute.DOUBLE_SIZE, cleanOnFinalize);
                size += Attribute.DOUBLE_SIZE;
                break;
            case Attribute.STRING:
                attributes[attributeIndex] = BasicAttribute.createAttribute(Attribute.STRING,
                    cardinality, dynamicAttributeUnitSize, cleanOnFinalize);
                size += dynamicAttributeUnitSize;
                break;
        }
    }
    
    /**
     * returns the number of attributes in this tuple
     * @retuen the number of attributes in this tuple
     */
    public int getdegree() {
        return degree;
    }
    
    /**
     * returns the number of memory bytes occupied by single tuple
     * @return the number of memory bytes occupied by single tuple
     */
    public int getSize() {
        return size;
    }
    
    /**
     * returns the number of tuple lines occupied by tuple in memory
     * @return the number of tuple lines occupied by tuple in memory
     */
    public int getBufferCount() {
        return cardinality;
    }
    
    public byte getAttributeType(int attributeIndex) {
        return ((Attribute)attributes[attributeIndex + SYSTEM_PUSH]).getType();
    }

    public void commiteWrite()
    throws IOException {
        for (int attributeIndex = 0; attributeIndex < degree + SYSTEM_PUSH; attributeIndex++) {
            ((Attribute)attributes[attributeIndex]).commiteWrite();
        }
    }
    
    /**
     * points tuple cursor to position of first tuple
     */
    public void moveFirst()
    throws IOException {
        for (int attributeIndex = 0; attributeIndex < degree + SYSTEM_PUSH; attributeIndex++) {
            ((Attribute)attributes[attributeIndex]).moveFirst();
        }
    }

    /**
     * test attributes contents to see it it is possible to scroll forward
     * and retrieve one new tuple at a time for every single call.
     * @return true if there is at least on availabel tuple to be read
     * false if there is no more tuples that could be read.
     */
    public boolean moveNext()
    throws IOException {
        if (((Attribute)attributes[0]).isEndOfAttribute()) {
            return false;
        }
        for (int attributeIndex = 0; attributeIndex < degree + SYSTEM_PUSH; attributeIndex++) {
            ((Attribute)attributes[attributeIndex]).moveNext();
        }
        return true;
    }
    
    /**
     * test attributes contents to see it it is possible to scroll backward
     * and retrieve one new tuple at a time for every single call.
     * @return true if there is at least on availabel tuple to be read
     * false if there is no more tuples that could be read.
     */
    public boolean movePrevious()
    throws IOException {
        if (((Attribute)attributes[0]).isBeginOfAttribute()) {
            return false;
        }
        for (int attributeIndex = 0; attributeIndex < degree + SYSTEM_PUSH; attributeIndex++) {
            ((Attribute)attributes[attributeIndex]).movePrevious();
        }
        return true;
    }
    
    /**
     * points tuple cursor to position of last tuple
     */
    public void moveLast()
    throws IOException {
        for (int attributeIndex = 0; attributeIndex < degree + SYSTEM_PUSH; attributeIndex++) {
            ((Attribute)attributes[attributeIndex]).moveLast();
        }
    }
    
    /**
     * to know if shall i update before moving next or not
     */
    public boolean isModified() {
        return modified;
    }
    
    /**
     * to know if the tuple is read only tuple
     * or read write tuple.
     * @return read only return true, read write returns false
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    public void setNullAttribute(int attributeIndex, boolean value)
    throws AttributeNotExistException {
        if (attributeIndex >= degree) {
            throw new AttributeNotExistException("attempting to set a non-existing attribute to null");
        }
        ((NullAttribute)attributes[0]).setNullAttribute(attributeIndex, value);
        modified = true;
    }
    
    public boolean isNullAttribute(int attributeIndex)
    throws AttributeNotExistException {
        if (attributeIndex >= degree) {
            throw new AttributeNotExistException("attempting to perform null test on a non-existing attribute");
        }
        return ((NullAttribute)attributes[0]).isNullAttribute(attributeIndex);
    }
    
    /**
     * checkes whether Begin of Attribute reached or not
     */
    public boolean isBeginOfTuple() {
        /*
         * tests the first natural attribute in
         * attributes[] after system attributes
         */
        return ((Attribute)attributes[SYSTEM_PUSH]).isBeginOfAttribute();
    }
    
    /**
     * checkes whether End of Attribute reached or not
     */
    public boolean isEndOfTuple() {
        /*
         * tests the first natural attribute in
         * attributes[] after system attributes
         */
        return ((Attribute)attributes[SYSTEM_PUSH]).isEndOfAttribute();
    }
    
    /**
     * resposible on formatting attribute contents using a standard formatting
     * protocol, if and unsupported formatting instruction passed to
     * <code>getFormat</cod> an UnsupportedFormatException object will be thrown
     * @param format contains the required format for attribute content.
     * @return returns a formated attribute string as specified by format.
     */
    public String getformat(String format)
    throws UnsupportedFormatException {
        throw new UnsupportedFormatException("not emplimented yet");
    }
    
    /*
     * now create helper methods that will read needed
     * Attributes:
     * boolean, byte, char, short, integer, float, long, double.
     */
    /**
     * will be used only by Tuple Loader
     */
    public byte[] readNull()
    throws IOException {
        attribute = (Attribute)attributes[0];
        return attribute.readNull();
    }
    
    public boolean readBoolean(int attributeIndex)
    throws IOException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        return attribute.readBoolean();
    }
    
    public byte readByte(int attributeIndex)
    throws IOException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        return attribute.readByte();
    }
    
    public char readChar(int attributeIndex)
    throws IOException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        return attribute.readChar();
    }
    
    public short readShort(int attributeIndex)
    throws IOException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        return attribute.readShort();
    }
    
    public int readInteger(int attributeIndex)
    throws IOException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        return attribute.readInteger();
    }
    
    public float readFloat(int attributeIndex)
    throws IOException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        return attribute.readFloat();
    }
    
    public long readLong(int attributeIndex)
    throws IOException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        return attribute.readLong();
    }
    
    public double readDouble(int attributeIndex)
    throws IOException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        return attribute.readDouble();
    }
    
    public char[] readString(int attributeIndex)
    throws IOException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        return attribute.readString();
    }

    /*
     * now create helper methods that will write needed
     * Attributes:
     * boolean, byte, char, short, integer, float, long, double.
     */
    /**
     * will be used only by Tuple Loader
     */
    public void writeNull(byte[] data)
    throws IOException, TupleWriteException {
        attribute = (Attribute)attributes[0];
        if (readOnly) {
            throw new TupleWriteException("can't write to a tuple, tuple is read only");
        }
        attribute.writeNull(data);
        modified = true;
    }
    
    public void writeBoolean(boolean data, int attributeIndex)
    throws IOException, TupleWriteException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        if (readOnly) {
            throw new TupleWriteException("can't write to a tuple, tuple is read only");
        }
        attribute.writeBoolean(data);
        modified = true;
    }
    
    public void writeByte(byte data, int attributeIndex)
    throws IOException, TupleWriteException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        if (readOnly) {
            throw new TupleWriteException("can't write to a tuple, tuple is read only");
        }
        attribute.writeByte(data);
        modified = true;
    }
    
    public void writeChar(char data, int attributeIndex)
    throws IOException, TupleWriteException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        if (readOnly) {
            throw new TupleWriteException("can't write to a tuple, tuple is read only");
        }
        attribute.writeChar(data);
        modified = true;
    }
    
    public void writeShort(short data, int attributeIndex)
    throws IOException, TupleWriteException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        if (readOnly) {
            throw new TupleWriteException("can't write to a tuple, tuple is read only");
        }
        attribute.writeShort(data);
        modified = true;
    }
    
    public void writeInteger(int data, int attributeIndex)
    throws IOException, TupleWriteException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        if (readOnly) {
            throw new TupleWriteException("can't write to a tuple, tuple is read only");
        }
        attribute.writeInteger(data);
        modified = true;
    }
    
    public void writeFloat(float data, int attributeIndex)
    throws IOException, TupleWriteException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        if (readOnly) {
            throw new TupleWriteException("can't write to a tuple, tuple is read only");
        }
        attribute.writeFloat(data);
        modified = true;
    }
    
    public void writeLong(long data, int attributeIndex)
    throws IOException, TupleWriteException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        if (readOnly) {
            throw new TupleWriteException("can't write to a tuple, tuple is read only");
        }
        attribute.writeLong(data);
        modified = true;
    }
    
    public void writeDouble(double data, int attributeIndex)
    throws IOException, TupleWriteException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        if (readOnly) {
            throw new TupleWriteException("can't write to a tuple, tuple is read only");
        }
        attribute.writeDouble(data);
        modified = true;
    }
    
    public void writeString(char[] data, int attributeIndex)
    throws IOException, TupleWriteException {
        attribute = (Attribute)attributes[attributeIndex + SYSTEM_PUSH];
        if (readOnly) {
            throw new TupleWriteException("can't write to a tuple, tuple is read only");
        }
        attribute.writeString(data);
        modified = true;
    }
}
