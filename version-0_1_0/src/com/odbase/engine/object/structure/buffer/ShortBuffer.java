/*
 * ShortBuffer.java
 *
 * Created on April 30, 2002, 3:21 PM
 */

package com.odbase.engine.object.structure.buffer;

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
public class ShortBuffer extends Buffer {
    protected short[]      buffer;
    
    /** constructor
     * allocates the required buffer size
     * @params length number of cells to be allocated
     * @params zeroOnFinalize shall zero array befor finalizing
     */
    public ShortBuffer(int length, int unitSize, boolean zeroOnFinalize) {
        super(length, unitSize, zeroOnFinalize);
    }
    
    /**
     * allocates required amount of memory.
     */
    protected void allocateLength()
    throws OutOfMemoryError {
        buffer = new short[length];
    }

    /**
     * if asked, cleans the Buffer by felling it with zeros.
     */
    protected void clean() {
        Arrays.fill(buffer, (short)0);
    }
    
    public short read()
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        return buffer[cursor++];
    }
    
    public void write(short data)
    throws IOException {
        if (cursor == length) {
            throw new IOException("can't pass end of buffer");
        }
        buffer[cursor++] = data;
    }   
}
