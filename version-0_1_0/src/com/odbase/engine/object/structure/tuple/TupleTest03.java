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
* TupleTest03.java                                                            *
* Created on 2003-01-01, 1:00 AM                                              *
*-----------------------------------------------------------------------------*/

package com.odbase.engine.object.structure.tuple;

import java.io.*;
import java.util.*;
import com.odbase.engine.object.structure.attribute.*;
import com.odbase.engine.object.structure.tuple.Tuple;

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
public class TupleTest03 {
    private static byte[]   BIT;
    static {
        BIT     = new byte[16];
        BIT[0]  = (byte)0x80;//10000000
        BIT[1]  = (byte)0x40;//01000000
        BIT[2]  = (byte)0x20;//00100000
        BIT[3]  = (byte)0x10;//00010000
        BIT[4]  = (byte)0x08;//00001000
        BIT[5]  = (byte)0x04;//00000100
        BIT[6]  = (byte)0x02;//00000010
        BIT[7]  = (byte)0x01;//00000001
        
        BIT[8]  = (byte)0x7F;//01111111
        BIT[9]  = (byte)0xBF;//10111111
        BIT[10] = (byte)0xDF;//11011111
        BIT[11] = (byte)0xEF;//11101111
        BIT[12] = (byte)0xF7;//11110111
        BIT[13] = (byte)0xFB;//11111011
        BIT[14] = (byte)0xFD;//11111101
        BIT[15] = (byte)0xFE;//11111110
    }

    /** Creates new TupleTest03 */
    public TupleTest03() {
        int a = 500;
        assert a == 0;
    }
    
    public static void setBit(byte b[], int bit, boolean value) {
        if (value) {
            b[bit / 8] |= BIT[bit % 8];
        } else {
            b[bit / 8] &= BIT[(bit % 8) + 8];
        }
    }

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) {
        String S;
        long t1 ,t2;
        t1 = System.currentTimeMillis();
        Tuple tuple = new Tuple(9, 21, false);
        //1- instantiate attributes.
        System.out.println("1- instantiate tuple attributes.");
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
        
        System.out.println("2- write null attribute.");
        try {
            //tuple.moveFirst();
            byte[] nullAttribute = new byte[2];
            while (tuple.moveNext()) {
                for (int i = 0; i < 9; i++) {
                    setBit(nullAttribute, 4, true);
                }
                tuple.writeNull(nullAttribute);
                tuple.commiteWrite();
            }
        } catch (IOException x) {
                x.printStackTrace();
        } catch (TupleWriteException x) {
            x.printStackTrace();
        }/* catch (AttributeNotExistException x) {
            x.printStackTrace();
        }*/
        
        System.out.println("2- write data to attributes.");
        try {
            tuple.moveFirst();
            int x = 0;
            while (tuple.moveNext()) {
                //tuple.setNullAttribute(x % 9, true);
                S = "";
                if (!tuple.isNullAttribute(0)) {
                    tuple.writeBoolean(true, 0);
                    S += true;
                } else {
                    S += null;
                }
                S += "   ";
                
                if (!tuple.isNullAttribute(1)) {
                    tuple.writeByte((byte)(10 + x), 1);
                    S += (byte)(10 + x);
                } else {
                    S += null;
                }
                S += "   ";
                
                if (!tuple.isNullAttribute(2)) {
                    tuple.writeChar((char)(65 + x), 2);
                    S += (char)(65 + x);
                } else {
                    S += null;
                }
                S += "   ";
                
                if (!tuple.isNullAttribute(3)) {
                    tuple.writeShort((short)(30000 + x), 3);
                    S += (short)(30000 + x);
                } else {
                    S += null;
                }
                S += "   ";
                
                if (!tuple.isNullAttribute(4)) {
                    tuple.writeInteger(645950847 + x, 4);
                    S += 645950847 + x;
                } else {
                    S += null;
                }
                S += "   ";
                
                if (!tuple.isNullAttribute(5)) {
                    tuple.writeFloat((float)1.3 + (float)x, 5);
                    S += (float)1.3 + (float)x;
                } else {
                    S += null;
                }
                S += "   ";
                
                if (!tuple.isNullAttribute(6)) {
                    tuple.writeLong(456789123 + x, 6);
                    S += 456789123 + x;
                } else {
                    S += null;
                }
                S += "   ";

                if (!tuple.isNullAttribute(7)) {
                    tuple.writeDouble(456789123.456 + x, 7);
                    S += 456789123.456 + x;
                } else {
                    S += null;
                }
                S += "   ";
                
                if (!tuple.isNullAttribute(8)) {
                    tuple.writeString(("Allah Akbar 00").toCharArray(), 8);
                    S += "Allah Akbar 00";
                } else {
                    S += null;
                }
                S += "   ";
                
                System.out.println(S);
                    
                tuple.commiteWrite();
                x++;
            }
        } catch (IOException x) {
                x.printStackTrace();
        } catch (TupleWriteException x) {
            x.printStackTrace();
        } catch (AttributeNotExistException x) {
            x.printStackTrace();
        }

        System.out.println("3- read data from attributes, forward.");
        try {
            tuple.moveFirst();
            int x = 0;
            while (tuple.moveNext()) {
                S = "";
                if (!tuple.isNullAttribute(0)) {
                    S += tuple.readBoolean(0);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(1)) {
                    S += tuple.readByte(1);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(2)) {
                    S += tuple.readChar(2);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(3)) {
                    S += tuple.readShort(3);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(4)) {
                    S += tuple.readInteger(4);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(5)) {
                    S += tuple.readFloat(5);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(6)) {
                    S += tuple.readLong(6);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(7)) {
                    S += tuple.readDouble(7);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(8)) {
                    S += new String(tuple.readString(8));
                } else {
                    S += null;
                }
                S += "   ";
                
                System.out.println(S);
                x++;
            }
        } catch (IOException x) {
                x.printStackTrace();
        } catch (AttributeNotExistException x) {
            x.printStackTrace();
        }
        
        System.out.println("4- read data from attributes, backward.");
        try {
            /*tuple.moveFirst();
            tuple.movePrevious();
            tuple.moveLast();
            tuple.moveFirst();
            tuple.moveNext();*/
            tuple.moveLast();
            int x = 0;
            while (tuple.movePrevious()) {
                S = "";
                if (!tuple.isNullAttribute(0)) {
                    S += tuple.readBoolean(0);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(1)) {
                    S += tuple.readByte(1);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(2)) {
                    S += tuple.readChar(2);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(3)) {
                    S += tuple.readShort(3);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(4)) {
                    S += tuple.readInteger(4);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(5)) {
                    S += tuple.readFloat(5);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(6)) {
                    S += tuple.readLong(6);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(7)) {
                    S += tuple.readDouble(7);
                } else {
                    S += null;
                }
                S += "   ";
                if (!tuple.isNullAttribute(8)) {
                    S += new String(tuple.readString(8));
                } else {
                    S += null;
                }
                S += "   ";
                
                System.out.println(S);
                x++;
            }
        } catch (IOException x) {
                x.printStackTrace();
        } catch (AttributeNotExistException x) {
            x.printStackTrace();
        }
        
        t2 = System.currentTimeMillis();
        System.out.println("time" + (t1-t2));
        
        //System.out.println("Test Finished");
        //System.out.println("tuple = null");
        //tuple = null;
        //System.out.println("System.gc()");
        //System.gc();
        //System.out.println("System.runFinalization();");
        //System.runFinalization();
        //System.out.println("exit");
    }
}
