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
* TupleTest02.java                                                            *
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
public class TupleTest02 {

    /** Creates new TupleTest02 */
    public TupleTest02() {
    }

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) {
        //System.out.println((int)Math.ceil(9 / (double)8));
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
        //2- write data to attributes.
        System.out.println("2- write data to attributes.");
        try {
            tuple.moveFirst();
            int x = 0;
            while (tuple.moveNext()) {
                if (x % 2 == 1) {
                    tuple.writeBoolean(true, 0);
                    System.out.print(true);
                } else {
                    tuple.writeBoolean(false, 0);
                    System.out.print(false);
                }
                System.out.print("     ");
                tuple.writeByte((byte)(10 + x), 1);
                System.out.print((byte)(10 + x));
                System.out.print("     ");
                tuple.writeChar((char)(65 + x), 2);
                System.out.print((char)(65 + x));
                System.out.print("     ");
                tuple.writeShort((short)(30000 + x), 3);
                System.out.print((short)(30000 + x));
                System.out.print("     ");
                tuple.writeInteger(645950847 + x, 4);
                System.out.print(645950847 + x);
                System.out.print("     ");
                tuple.writeFloat((float)1.3 + (float)x, 5);
                System.out.print((float)1.3 + (float)x);
                System.out.print("     ");
                tuple.writeLong(456789123 + x, 6);
                System.out.print(456789123 + x);
                System.out.print("     ");
                tuple.writeDouble(456789123.456 + x, 7);
                System.out.print(456789123.456 + x);
                System.out.print("     ");
                if(x < 10) {
                    tuple.writeString(("Allah Akbar 0" + x).toCharArray(), 8);
                    System.out.println(("Allah Akbar 0" + x).toCharArray());
                } else {
                    tuple.writeString(("Allah Akbar " + x).toCharArray(), 8);
                    System.out.println(("Allah Akbar " + x).toCharArray());
                }

                tuple.commiteWrite();
                x++;
            }
        } catch (IOException x) {
                x.printStackTrace();
        } catch (TupleWriteException x) {
            x.printStackTrace();
        }
        
        //3- read data from attributes.
        System.out.println("3- read data from attributes.");
        try {
            tuple.moveFirst();
            int x = 0;
            while (!tuple.isEndOfTuple()) {
                tuple.moveNext();
                tuple.readBoolean(0);
                System.out.print("" + x +" ");
                System.out.print(tuple.readBoolean(0));
                System.out.print("     ");
                System.out.print(tuple.readByte(1));
                System.out.print("     ");
                System.out.print(tuple.readChar(2));
                System.out.print("     ");
                System.out.print(tuple.readShort(3));
                System.out.print("     ");
                System.out.print(tuple.readInteger(4));
                System.out.print("     ");
                System.out.print(tuple.readFloat(5));
                System.out.print("     ");
                System.out.print(tuple.readLong(6));
                System.out.print("     ");
                System.out.print(tuple.readDouble(7));
                System.out.print("     ");
                System.out.println(tuple.readString(8));
                x++;
            }
        } catch (IOException x) {
            x.printStackTrace();
        }/* catch (AttributeNotExistException x) {
            x.printStackTrace();
        }*/
        
        //4- read data from attributes backward.
        System.out.println("4- read data from attributes backward.");
        try {
            int x = 20;
            while (!tuple.isBeginOfTuple()) {
                tuple.movePrevious();
                System.out.print("" + x +" ");
                System.out.print(tuple.readBoolean(0));
                System.out.print("     ");
                System.out.print(tuple.readByte(1));
                System.out.print("     ");
                System.out.print(tuple.readChar(2));
                System.out.print("     ");
                System.out.print(tuple.readShort(3));
                System.out.print("     ");
                System.out.print(tuple.readInteger(4));
                System.out.print("     ");
                System.out.print(tuple.readFloat(5));
                System.out.print("     ");
                System.out.print(tuple.readLong(6));
                System.out.print("     ");
                System.out.print(tuple.readDouble(7));
                System.out.print("     ");
                System.out.println(tuple.readString(8));
                x--;
            }
        } catch (IOException x) {
                x.printStackTrace();
        }
        
        //5- read data from attributes forward with while(!tuple.isEndOfTuple()).
        System.out.println("5- read data from attributes forward with while(!tuple.isEndOfTuple()).");
        try {
            tuple.moveFirst();
            while (!tuple.isEndOfTuple()) {
                tuple.moveNext();
                System.out.print(tuple.readBoolean(0));
                System.out.print("     ");
                System.out.print(tuple.readByte(1));
                System.out.print("     ");
                System.out.print(tuple.readChar(2));
                System.out.print("     ");
                System.out.print(tuple.readShort(3));
                System.out.print("     ");
                System.out.print(tuple.readInteger(4));
                System.out.print("     ");
                System.out.print(tuple.readFloat(5));
                System.out.print("     ");
                System.out.print(tuple.readLong(6));
                System.out.print("     ");
                System.out.print(tuple.readDouble(7));
                System.out.print("     ");
                System.out.println(tuple.readString(8));
            }
        } catch (IOException x) {
                x.printStackTrace();
        }
        
        System.out.println("Test Finished");
        System.out.println("tuple = null");
        tuple = null;
        System.out.println("System.gc()");
        System.gc();
        System.out.println("System.runFinalization();");
        System.runFinalization();
        System.out.println("exit");
        t2 = System.currentTimeMillis();
        System.out.println("time = " + (t2-t1));
    }

}
