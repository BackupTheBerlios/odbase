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
* StreamDomain.java                                                           *
* Created on 2003-01-01, 1:00 AM                                              *
*-----------------------------------------------------------------------------*/

package com.odbase.engine.object.structure.domain;

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
public class StreamDomain extends Domain {
    protected byte[]      domin;
    protected byte[]      dominLine;
    
    /** constructor
     * allocates the required domin size
     * @params cardinality number of cells to be allocated
     * @params zeroOnFinalize shall zero array befor finalizing
     */
    public StreamDomain(int cardinality, int unitSize, boolean zeroOnFinalize) {
        super(cardinality, unitSize, zeroOnFinalize);
    }
    
    /**
     * allocates required amount of memory.
     */
    protected void allocateCardinality()
    throws OutOfMemoryError {
        domin      = new byte[cardinality * unitSize];
        dominLine  = new byte[unitSize];
        cursor     = -unitSize;
    }

    /**
     * if asked, cleans the Domain by felling it with zeros.
     */
    protected void clean() {
        Arrays.fill(domin, (byte)0);
        Arrays.fill(dominLine, (byte)0);
    }
    
    public byte[] readForward()
    throws IOException {
        if (cursor == cardinality * unitSize) {
            throw new IOException("can't read after end of domin");
        }
        cursor += unitSize;
        for (int i = 0; i < unitSize; i++) {
            dominLine[i] = domin[cursor + i];
        }
        return dominLine;
    }
    
    public byte[] readBackward()
    throws IOException {
        if (cursor == 0) {
            throw new IOException("can't read before begin of domin");
        }
        cursor -= unitSize;
        for (int i = 0; i < unitSize; i++) {
            dominLine[i] = domin[cursor + i];
        }
        return dominLine;
    }

    public void save(byte[] data)
    throws IOException {
        if (cursor == -1) {
            throw new IOException("can't write before begine of domin");
        } else if (cursor == (cardinality * unitSize)) {
            throw new IOException("can't write after end of domin");
        }
        for (int i = 0; i < unitSize; i++) {
            domin[cursor + i] = data[i];
        }
    }
    
    /**
     * used to recieve StreamDomain temporary operations line domin
     * to be used to receive data from IODomain
     * IODomain = new IODomain();
     * StreamDomain.readString(IODomain.readString(StreamDomain.getDomainLine()));
     */
    public byte[] getStringLine() {
        return dominLine;
    }
    
    /**
     * moves cursor to seek position
     * this method was overriden from Domain class
     * in classes:
     * 1- StreamDomain.
     * 2- StreamDomain.
     * @params position the valid position within domin
     */
    public void seek(int position)
    throws IOException {
        if (position >= (cardinality * unitSize)) {
            throw new IOException("can't seek after end of domin");
        } else if (position < -1) {
            throw new IOException("can't seek before begin of domin");
        }
        cursor = position * unitSize;
    }
    
    /**
     * moves cursor to seek position
     * this method was overriden from Domain class
     * in classes:
     * 1- StreamDomain.
     * 2- StreamDomain.
     * @params position the valid position within domin
     */
    public void seekFrom(byte location, int cardinality)
    throws IOException {
        switch (location) {
            case BEGIN:
                cardinality *= cardinality < 0 ? -1 : 1;
                cursor = cardinality * unitSize;
                break;
            case CURRENT:
                if ((cursor + (cardinality * unitSize)) > (this.cardinality * unitSize)) {
                    throw new IOException("can't seek after end of domin");
                }
                cursor += cardinality * unitSize;
                break;
            case END:
                cardinality *= cardinality < 0 ? -1 : 1;
                if ((cursor + (cardinality * unitSize)) < 0) {
                    throw new IOException("can't seek before begin of domin");
                }
                cursor = (this.cardinality * unitSize) - (cardinality * unitSize);
                break;
        }
    }
    
    public void seekToBegin() {
        cursor = -unitSize;
    }
    
    public void seekToEnd() {
        cursor = cardinality * unitSize;
    }
    
    /**
     * checkes whether Begin of Domain reached or not
     * this method was overriden from Domain class
     * in classes:
     * 1- StreamDomain.
     * 2- StreamDomain.
     */
    public boolean isBeginOfDomain() {
        return cursor == 0;
    }
    
    /**
     * checkes whether End of Domain reached or not
     * this method was overriden from Domain class
     * in classes:
     * 1- StreamDomain.
     * 2- StreamDomain.
     */
    public boolean isEndOfDomain() {
        return cursor == (cardinality - 1) * unitSize;
    }
    
    public int getPosition() {
        return cursor / unitSize;
    }
}
