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
* Attribute.java                                                              *
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
public interface Attribute {
                             //storage type                     - JDBC
    public byte NULL        =  0;//encapsualtes BooleanDomain
    public byte BOOLEAN     =  1;//encapsualtes BooleanDomain   - BIT
    public byte BYTE        =  2;//encapsualtes ByteDomain      - TINYINT
    public byte CHARACTER   =  3;//encapsualtes CharDomain      - CHAR
    public byte SHORT       =  4;//encapsualtes ShortDomain     - SMALLINT
    public byte INTEGER     =  5;//encapsualtes IntegerDomain   - INTEGER
    public byte FLOAT       =  6;//encapsualtes FloatDomain     - REAL
    public byte LONG        =  7;//encapsualtes LongDomain      - BIGINT - DECIMAL - NUMERIC
    public byte DOUBLE      =  8;//encapsualtes DoubleDomain    - FLOAT - DOUBLE
    public byte STRING      =  9;//encapsualtes StringDomain    - VARCHAR - LONGVARCHAR
    public byte TIME        = 10;//encapsualtes LongDomain      - DATE - TIME - TIMESTAMP 
    public byte BINARY      = 11;//encapsualtes StringDomain    - BINARY - VARBINARY - LONGVARBINARY
                                    //wont be loaded into / from memory
                                    //sent to / receive from file
                                    //or show description
                                    //binary data to user.
    public byte BOOLEAN_SIZE     =  1;
    public byte BYTE_SIZE        =  1;
    public byte CHARACTER_SIZE   =  2;
    public byte SHORT_SIZE       =  2;
    public byte INTEGER_SIZE     =  4;
    public byte FLOAT_SIZE       =  4;
    public byte LONG_SIZE        =  8;
    public byte DOUBLE_SIZE      =  8;
    
    public String[] attributeTypeName = {"null", "boolean", "byte", "char", "short",
                                    "integer", "float", "long", "double",
                                    "String", "Time", "Binary"};

    /**
     * this method is useful in putting it in switch(case){} block
     * to simplify the fetshing operation.
     * @return returns the byte value that declars Attribute type.
     */
    public byte getType();
    
    /**
     * @return returns attribute type name.
     */
    public String getTypeName();
    
    /**
     * this method is usful in putting it in
     * for (; < Attribute.getCardinality();) {} block
     * to simplify the fetshing operation.
     * @return returns the number of elements in selected Attribute
     */
    public int getCardinality();
    
    /**
     * gets the total amount of btes allocated in memory for this attribute.
     * @return returns attribute size in momory in bytes.
     */
    public int getSize();
    
    public void moveFirst() throws IOException;
    public void moveNext() throws IOException;
    public void movePrevious() throws IOException;
    public void moveLast() throws IOException;
    
    /**
     * to know if shall i update before moving next or not
     */
    public boolean isModified();
    
    /**
     * checkes whether Begin of Attribute reached or not
     */
    public boolean isBeginOfAttribute();
    
    /**
     * checkes whether End of Attribute reached or not
     */
    public boolean isEndOfAttribute();

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
    
    public byte[] readNull() throws IOException;
    public boolean readBoolean() throws IOException;
    public byte readByte() throws IOException;
    public char readChar() throws IOException;
    public short readShort() throws IOException;
    public int readInteger() throws IOException;
    public float readFloat() throws IOException;
    public long readLong() throws IOException;
    public double readDouble() throws IOException;
    public char[] readString() throws IOException;
    public byte[] readBinary() throws IOException;
    
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
    
    public void writeNull(byte[] data) throws IOException;
    public void writeBoolean(boolean data) throws IOException;
    public void writeByte(byte data) throws IOException;
    public void writeChar(char data) throws IOException;
    public void writeShort(short data) throws IOException;
    public void writeInteger(int data) throws IOException;
    public void writeFloat(float data) throws IOException;
    public void writeLong(long data) throws IOException;
    public void writeDouble(double data) throws IOException;
    public void writeString(char[] data) throws IOException;
    public void writeBinary(byte[] data) throws IOException;
    
    public void commiteWrite() throws IOException;
}

