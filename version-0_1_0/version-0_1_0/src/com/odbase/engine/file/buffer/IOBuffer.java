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
* IOBuffer.java                                                               *
* Created on 2003-01-01, 1:00 AM                                              *
*-----------------------------------------------------------------------------*/

package com.odbase.engine.file.buffer;

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
public class IOBuffer extends ByteBuffer {
    /* insted of allocating:
     * 2 int in "long read()".
     * we allocate them here while they will be aproximately
     * used by 25% to 50% in working code.
     */
    private int  convertBuffer01, convertBuffer02;
    private long convertBuffer03;
    
    /** Creates a new instance of IOBuffer */
    public IOBuffer(int length, int unitSize, boolean zeroOnFinalize) {
        super(length, 1, zeroOnFinalize);
    }
    
    /*
     * now create helper methods that will read next needed length
     * of bytes as: 
     *  1- boolean   -> 1 byte  read.
     *  2- byte      -> 1 byte  read.
     *  3- char      -> 2 bytes read.
     *  4- short     -> 2 bytes read.
     *  5- integer   -> 4 bytes read.
     *  6- float     -> 4 bytes read.
     *  7- long      -> 8 bytes read.
     *  8- double    -> 8 bytes read.
     *  9- String    -> n bytes read.
     * 10- Stream    -> n bytes read.
     * note: "byte read()" method is allready exist in parent
     * class "ByteBuffer" but we will just but its code in
     * method "byte readByte()" to be like other mthods.
     */
    
    public boolean readBoolean()
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        return (buffer[cursor++] == 1);
    }
    
    public byte readByte()
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        return buffer[cursor++];
    }
    
    public char readChar()
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        cursor += 2;
        return (char)((buffer[cursor - 2] << 8) + (buffer[cursor - 1] << 0 & 0x00FF));
    }
    public short readShort()
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        cursor += 2;
        return (short)((buffer[cursor - 2] << 8) + (buffer[cursor - 1] << 0 & 0x00FF));
    }
    
    public int readInteger()
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        cursor += 4;
        return (buffer[cursor - 4] << 24) + (buffer[cursor - 3] << 16 & 0x00FF0000) + (buffer[cursor - 2] << 8 & 0x0000FF00) + (buffer[cursor - 1] << 0 & 0x000000FF);
    }
    
    public float readFloat()
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        cursor += 4;
        return Float.intBitsToFloat((buffer[cursor - 4] << 24) + (buffer[cursor - 3] << 16 & 0x00FF0000) + (buffer[cursor - 2] << 8 & 0x0000FF00) + (buffer[cursor - 1] << 0 & 0x000000FF));
    }
    
    public long readLong()
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        cursor += 8;
        convertBuffer01 = (buffer[cursor - 8] << 24) + (buffer[cursor - 7] << 16 & 0x00FF0000) + (buffer[cursor - 6] << 8 & 0x0000FF00) + (buffer[cursor - 5] << 0 & 0x000000FF);
        convertBuffer02 = (buffer[cursor - 4] << 24) + (buffer[cursor - 3] << 16 & 0x00FF0000) + (buffer[cursor - 2] << 8 & 0x0000FF00) + (buffer[cursor - 1] << 0 & 0x000000FF);
        return (((long)convertBuffer01) << 32) + (((long)convertBuffer02 << 0 & 0x00000000FFFFFFFFL));
    }
    
    public double readDouble()
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        cursor += 8;
        convertBuffer01 = (buffer[cursor - 8] << 24) + (buffer[cursor - 7] << 16 & 0x00FF0000) + (buffer[cursor - 6] << 8 & 0x0000FF00) + (buffer[cursor - 5] << 0 & 0x000000FF);
        convertBuffer02 = (buffer[cursor - 4] << 24) + (buffer[cursor - 3] << 16 & 0x00FF0000) + (buffer[cursor - 2] << 8 & 0x0000FF00) + (buffer[cursor - 1] << 0 & 0x000000FF);
        return Double.longBitsToDouble((((long)convertBuffer01) << 32) + (((long)convertBuffer02 << 0 & 0x00000000FFFFFFFFL)));
    }
    
    public char[] readString(char[] receiver)
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        for (int i = 0; i < receiver.length; i++) {
            cursor += 2;
            receiver[i] = (char)((buffer[cursor - 2] << 8) + (buffer[cursor - 1] << 0 & 0x00FF));
        }
        return receiver;
    }
    
    public byte[] readStream(byte[] receiver)
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        for (int i = 0; i < receiver.length; i++) {
            receiver[i] = (byte)buffer[cursor++];
        }
        return receiver;
    }
    
    /*
     * now create helper methods that will write next needed length
     * of bytes as:
     *  1- boolean   -> 1 byte  write.
     *  2- byte      -> 1 byte  write.
     *  3- char      -> 2 bytes write.
     *  4- short     -> 2 bytes write.
     *  5- integer   -> 4 bytes write.
     *  6- float     -> 4 bytes write.
     *  7- long      -> 8 bytes write.
     *  8- double    -> 8 bytes write.
     *  9- String    -> n bytes write.
     * 10- Stream    -> n bytes write.
     * note: "byte write()" method is allready exist in parent
     * class "ByteBuffer" but we will just but its code in
     * method "byte writeByte()" to be like other mthods.
     */
    
    public void writeBoolean(boolean data)
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        buffer[cursor++] = (data == true ? (byte)1 : (byte)0);
    }
    
    public void writeByte(byte data)
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        buffer[cursor++] = data;
    }
    
    public void writeChar(char data)
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        cursor += 2;
        buffer[cursor - 2] = (byte)(data >>> 8);
        buffer[cursor - 1] = (byte)(data >>> 0);
    }
    public void writeShort(short data)
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        cursor += 2;
        buffer[cursor - 2] = (byte)(data >>> 8);
        buffer[cursor - 1] = (byte)(data >>> 0);
    }
    
    public void writeInteger(int data)
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        cursor += 4;
        buffer[cursor - 4] = (byte)(data >>> 24);
        buffer[cursor - 3] = (byte)(data >>> 16);
        buffer[cursor - 2] = (byte)(data >>> 8);
        buffer[cursor - 1] = (byte)(data >>> 0);
    }
    
    public void writeFloat(float data)
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        cursor += 4;
        convertBuffer02 = Float.floatToIntBits(data);
        buffer[cursor - 4] = (byte)(convertBuffer02 >>> 24);
        buffer[cursor - 3] = (byte)(convertBuffer02 >>> 16);
        buffer[cursor - 2] = (byte)(convertBuffer02 >>> 8);
        buffer[cursor - 1] = (byte)(convertBuffer02 >>> 0);
    }
    
    public void writeLong(long data)
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        cursor += 8;
        buffer[cursor - 8] = (byte)(data >>> 56);
        buffer[cursor - 7] = (byte)(data >>> 48);
        buffer[cursor - 6] = (byte)(data >>> 40);
        buffer[cursor - 5] = (byte)(data >>> 32);
        buffer[cursor - 4] = (byte)(data >>> 24);
        buffer[cursor - 3] = (byte)(data >>> 16);
        buffer[cursor - 2] = (byte)(data >>> 8);
        buffer[cursor - 1] = (byte)(data >>> 0);
    }
    
    public void writeDouble(double data)
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        cursor += 8;
        convertBuffer03 = Double.doubleToLongBits(data);
        buffer[cursor - 8] = (byte)(convertBuffer03 >>> 56);
        buffer[cursor - 7] = (byte)(convertBuffer03 >>> 48);
        buffer[cursor - 6] = (byte)(convertBuffer03 >>> 40);
        buffer[cursor - 5] = (byte)(convertBuffer03 >>> 32);
        buffer[cursor - 4] = (byte)(convertBuffer03 >>> 24);
        buffer[cursor - 3] = (byte)(convertBuffer03 >>> 16);
        buffer[cursor - 2] = (byte)(convertBuffer03 >>> 8);
        buffer[cursor - 1] = (byte)(convertBuffer03 >>> 0);
    }
    
    public void writeString(char[] data)
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        for (int i = 0; i < data.length; i++) {
            cursor += 2;
            buffer[cursor - 2] = (byte)(data[i] >>> 8);
            buffer[cursor - 1] = (byte)(data[i] >>> 0);
        }
    }
    
    public void writeStream(byte[] data)
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        for (int i = 0; i < data.length; i++) {
            buffer[cursor++] = data[i];
        }
    }
}
