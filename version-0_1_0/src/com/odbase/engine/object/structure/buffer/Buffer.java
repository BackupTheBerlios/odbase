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
* Buffer.java                                                                 *
* Created on 2003-01-01, 1:00 AM                                              *
*-----------------------------------------------------------------------------*/

package com.odbase.engine.object.structure.buffer;

import java.io.*;

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
public abstract class Buffer {
    public static final byte BEGIN   = 0;
    public static final byte CURRENT = 1;
    public static final byte END     = 2;
    /** number of cells in array*/
    protected int       length;
    /** position of reading or writing cursor*/
    protected int       cursor;
    /** cell size in buffer*/
    protected int       unitSize;
    /** shall zero buffer befor finalizing*/
    protected boolean   zeroOnFinalize;
    
    /** constructor
     * allocates the required buffer size
     * @params length number of cells to be allocated
     * @params zeroOnFinalize shall zero array befor finalizing
     */
    public Buffer(int length, int unitSize, boolean zeroOnFinalize) {
        this.zeroOnFinalize = zeroOnFinalize;
        this.unitSize       = unitSize;
        this.length         = length;
        allocateLength();
    }

    /**
     * gets memory size, without virtual cells.
     */
    public final int getSize() {
        return unitSize * length;
    }
    
    /**
     * gets length of rows in Buffer
     */
    public final int getLength() {
        return length;
    }
    
    public final int getUnitSize() {
        return unitSize;
    }
    
    /**
     * allocates required amount of memory.
     * @params length number of cells to be allocated
     */
    protected abstract void allocateLength();
    
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
        if (position > length) {
            throw new IOException("can't seek after end of buffer");
        } else if (position < 0) {
            throw new IOException("can't seek before begin of buffer");
        }
        cursor = position;
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
                length = Math.abs(length);
                if (length > this.length) {
                    throw new IOException("can't seek after end of buffer");
                } else if (length < 0) {
                    throw new IOException("can't seek before begin of buffer");
                }
                cursor = length;
                break;
            case CURRENT:
                if ((cursor + length) > this.length) {
                    throw new IOException("can't seek after end of buffer");
                } else if ((cursor + length) < 0) {
                    throw new IOException("can't seek before begin of buffer");
                }
                cursor += length;
                break;
            case END:
                length = Math.abs(length);
                if (length > this.length) {
                    throw new IOException("can't seek before begin of buffer");
                }
                cursor = this.length - length;
                break;
        }
    }
    
    /**
     * @returns the cursor cell position in the buffer buffer
     */
    public final int getPosition() {
        return cursor;
    }
    
    /**
     * if asked, cleans the Buffer by felling it with zeros.
     */
    protected abstract void clean();

    public void finalize()
    throws Throwable {
        System.out.println("starting system finalization");
        if (zeroOnFinalize) {
            clean();
            System.out.println("buffer zeroed");
        }
        System.out.println("system finalization finished");
    }
}
