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
* StringBuffer.java                                                           *
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
public class StringBuffer extends Buffer {
    protected char[]      buffer;
    protected char[]      bufferLine;
    
    /** constructor
     * allocates the required buffer size
     * @params length number of cells to be allocated
     * @params zeroOnFinalize shall zero array befor finalizing
     */
    public StringBuffer(int length, int unitSize, boolean zeroOnFinalize) {
        super(length, unitSize, zeroOnFinalize);
    }
    
    /**
     * allocates required amount of memory.
     */
    protected void allocateLength()
    throws OutOfMemoryError {
        buffer      = new char[length * unitSize];
        bufferLine  = new char[unitSize];
    }

    /**
     * if asked, cleans the Buffer by felling it with zeros.
     */
    protected void clean() {
        Arrays.fill(buffer, '\u0000');
        Arrays.fill(bufferLine, '\u0000');
    }
    
    public char[] read()
    throws IOException {
        if (cursor == (length * unitSize)) {
            throw new IOException("can't pass end of buffer");
        }
        cursor += unitSize;
        for (int i = 0; i < unitSize; i++) {
            bufferLine[i] = buffer[cursor - unitSize + i];
        }
        return bufferLine;
    }
    
    public void write(char[] data)
    throws IOException {
        if (cursor == (length * unitSize)) {
            throw new IOException("can't pass end of buffer");
        }
        cursor += unitSize;
        for (int i = 0; i < unitSize; i++) {
            buffer[cursor - unitSize + i] = data[i];
        }
    }
    /**
     * used to recieve StringBuffer temporary operations line buffer
     * to be used to receive data from IOBuffer
     * IOBuffer = new IOBuffer();
     * StringBuffer.readString(IOBuffer.readString(StringBuffer.getBufferLine()));
     */
    public char[] getStringLine() {
        return bufferLine;
    }
    
    /**
     * moves cursor to seek position
     * this method was overriden from Buffer class
     * in classes:
     * 1- StringBuffer.
     * 2- StreamBuffer.
     * @params position the valid position within buffer
     */
    public void seek(int position)
    throws IOException {
        if (position > (length * unitSize)) {
            throw new IOException("can't seek after end of buffer");
        } else if (position < 0) {
            throw new IOException("can't seek before begin of buffer");
        }
        cursor = position * unitSize;
    }
    
    /**
     * moves cursor to seek position
     * this method was overriden from Buffer class
     * in classes:
     * 1- StringBuffer.
     * 2- StreamBuffer.
     * @params position the valid position within buffer
     */
    public void seekFrom(byte location, int length)
    throws IOException {
        switch (location) {
            case BEGIN:
                cursor = length * unitSize;
                break;
            case CURRENT:
                if ((cursor + (length * unitSize)) > (this.length * unitSize)) {
                    throw new IOException("can't seek after end of buffer");
                }
                cursor += length * unitSize;
                break;
            case END:
                if ((cursor + (length * unitSize)) < 0) {
                    throw new IOException("can't seek before begin of buffer");
                }
                cursor = (this.length * unitSize) - Math.abs(length * unitSize);
                break;
        }
    }
}
