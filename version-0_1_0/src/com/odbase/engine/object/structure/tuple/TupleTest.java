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
* TupleTest.java                                                              *
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
public class TupleTest {

    /** Creates new TupleTest */
    public TupleTest() {
    }

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) {
        long t1 ,t2;
        t1 = System.currentTimeMillis();
        Tuple tuple = new Tuple(9, 21, false);
        //1- instantiate attributes.
        //System.out.println("1- instantiate tuple attributes.");
        try {
            tuple.createAttributeInstance(0, Attribute.BOOLEAN, 0, true);
            tuple.createAttributeInstance(1, Attribute.BYTE, 0, true);
            tuple.createAttributeInstance(2, Attribute.CHARACTER, 0, true);
            tuple.createAttributeInstance(3, Attribute.SHORT, 0, true);
            tuple.createAttributeInstance(4, Attribute.INTEGER, 0, true);
            tuple.createAttributeInstance(5, Attribute.FLOAT, 0, true);
            tuple.createAttributeInstance(6, Attribute.LONG, 0, true);
            tuple.createAttributeInstance(7, Attribute.DOUBLE, 0, true);
            tuple.createAttributeInstance(8, Attribute.STRING, 14, true);
        } catch (UnsupportedAttributeException x) {
                x.printStackTrace();
        }
        //2- write data to attributes.
        try {
            int z = 0;
            t1 = System.currentTimeMillis();
            tuple.moveFirst();
            for (int x = 0; x < 21; x++) {
                if (!tuple.isEndOfTuple()) {
                    tuple.moveNext();
                } else break;
                tuple.writeBoolean(true, 0);
                tuple.writeByte((byte)(10 + x), 1);
                tuple.writeChar((char)(65 + x), 2);
                tuple.writeShort((short)(30000 + x), 3);
                tuple.writeInteger(645950847 + x, 4);
                tuple.writeFloat((float)1.3 + (float)x, 5);
                tuple.writeLong(456789123 + x, 6);
                tuple.writeDouble(456789123.456 + x, 7);
                tuple.writeString(("Allah Akbar 00").toCharArray(), 8);
                tuple.commiteWrite();
                
               /* if (x == 20) {
                    tuple.moveFirst();
                    x = -1;
                    z++;
                    if (z == 10000000) {
                        x = 21;
                    }
                }
                */
            }
            t2 = System.currentTimeMillis();
            //System.out.println("Writin of " + 10000000 * 21 + " Tuples in " + (t2-t1) + " Milisecond");
        } catch (IOException x) {
                x.printStackTrace();
        } catch (TupleWriteException x) {
            x.printStackTrace();
        }
        
        //3- read data from attributes.
        try {
            tuple.moveFirst();
            for (int x = 0; x < 21; x++) {
                if (!tuple.isEndOfTuple()) {
                    tuple.moveNext();
                } else break;
                tuple.readBoolean(0);
                tuple.readByte(1);
                tuple.readChar(2);
                tuple.readShort(3);
                tuple.readInteger(4);
                tuple.readFloat(5);
                tuple.readLong(6);
                tuple.readDouble(7);
                tuple.readString(8);
            }
        } catch (IOException x) {
                x.printStackTrace();
        }
        
        //4- read data from attributes backward.
        try {
            int z = 0;
            t1 = System.currentTimeMillis();
            for (int x = 20; x > -1; x--) {
                if (!tuple.isBeginOfTuple()) {
                    tuple.movePrevious();
                } else break;
                tuple.readBoolean(0);
                tuple.readByte(1);
                tuple.readChar(2);
                tuple.readShort(3);
                tuple.readInteger(4);
                tuple.readFloat(5);
                tuple.readLong(6);
                tuple.readDouble(7);
                tuple.readString(8);
                
                /*if (x == 0) {
                    tuple.moveFirst();
                    x = 20;
                    z++;
                    if (z == 10000000) {
                        x = -1;
                    }
                }
                 */
            }
            //t2 = System.currentTimeMillis();
            //System.out.println("Reading of " + 10000000 * 21 + " Tuples in " + (t2-t1) + " Milisecond");
        } catch (IOException x) {
                x.printStackTrace();
        }
        
        //System.out.println("Test Finished");
        //System.out.println("tuple = null");
        //tuple = null;
        //System.out.println("System.gc()");
        //System.gc();
        //System.out.println("System.runFinalization();");
        //System.runFinalization();
        //System.out.println("exit");
        t2 = System.currentTimeMillis();
        System.out.println("time = " + (t2-t1));
    }
}
