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
* AttributeTest.java                                                          *
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
public class AttributeTest {

    /** Creates new AttributeTest */
    public AttributeTest() {
    }

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) {
        Vector attributes = new Vector();
        Attribute attribute = null;
        //1- instantiate attributes.
        System.out.println("1- instantiate attributes.");
        try {
            for (int i = 0; i < 10; i++) {
                switch (i) {
                    case Attribute.NULL:
                        attributes.add(i, BasicAttribute.createAttribute(Attribute.NULL, 21, 1, true));
                        break;
                    case Attribute.BOOLEAN:
                        attributes.add(i, BasicAttribute.createAttribute(Attribute.BOOLEAN, 21, 1, true));
                        break;
                    case Attribute.BYTE:
                        attributes.add(i, BasicAttribute.createAttribute(Attribute.BYTE, 21, 1, true));
                        break;
                    case Attribute.CHARACTER:
                        attributes.add(i, BasicAttribute.createAttribute(Attribute.CHARACTER, 21, 2, true));
                        break;
                    case Attribute.SHORT:
                        attributes.add(i, BasicAttribute.createAttribute(Attribute.SHORT, 21, 2, true));
                        break;
                    case Attribute.INTEGER:
                        attributes.add(i, BasicAttribute.createAttribute(Attribute.INTEGER, 21, 4, true));
                        break;
                    case Attribute.FLOAT:
                        attributes.add(i, BasicAttribute.createAttribute(Attribute.FLOAT, 21, 4, true));
                        break;
                    case Attribute.LONG:
                        attributes.add(i, BasicAttribute.createAttribute(Attribute.LONG, 21, 8, true));
                        break;
                    case Attribute.DOUBLE:
                        attributes.add(i, BasicAttribute.createAttribute(Attribute.DOUBLE, 21, 8, true));
                        break;
                    case Attribute.STRING:
                        attributes.add(i, BasicAttribute.createAttribute(Attribute.STRING, 21, 14, true));
                        break;
                }
            }
        } catch (UnsupportedAttributeException x) {
                x.printStackTrace();
        }
        //2- write data to attributes.
        System.out.println("2- write data to attributes.");
        try {
            for (int i = 0; i < 10; i++) {
                attribute = (Attribute)attributes.get(i);
                attribute.moveFirst();
                attribute.moveNext();
            }
            for (int x = 0; x < 21; x++) {
                for (int i = 0; i < 10; i++) {
                    attribute = (Attribute)attributes.get(i);
                    switch (attribute.getType()) {
                        case Attribute.BOOLEAN:
                            if (x % 2 == 1) {
                                attribute.writeBoolean(true);
                                System.out.print(true);
                            } else {
                                attribute.writeBoolean(false);
                                System.out.print(false);
                            }
                            System.out.print("     ");
                            break;
                        case Attribute.BYTE:
                            attribute.writeByte((byte)(10 + x));
                            System.out.print((byte)(10 + x));
                            System.out.print("     ");
                            break;
                        case Attribute.CHARACTER:
                            attribute.writeChar((char)(65 + x));
                            System.out.print((char)(65 + x));
                            System.out.print("     ");
                            break;
                        case Attribute.SHORT:
                            attribute.writeShort((short)(30000 + x));
                            System.out.print((short)(30000 + x));
                            System.out.print("     ");
                            break;
                        case Attribute.INTEGER:
                            attribute.writeInteger(645950847 + x);
                            System.out.print(645950847 + x);
                            System.out.print("     ");
                            break;
                        case Attribute.FLOAT:
                            attribute.writeFloat((float)1.3 + (float)x);
                            System.out.print((float)1.3 + (float)x);
                            System.out.print("     ");
                            break;
                        case Attribute.LONG:
                            attribute.writeLong(456789123 + x);
                            System.out.print(456789123 + x);
                            System.out.print("     ");
                            break;
                        case Attribute.DOUBLE:
                            attribute.writeDouble(456789123.456 + x);
                            System.out.print(456789123.456 + x);
                            System.out.print("     ");
                            break;
                        case Attribute.STRING:
                            if(x < 10) {
                                attribute.writeString(("Allah Akbar 0" + x).toCharArray());
                                System.out.println(("Allah Akbar 0" + x).toCharArray());
                            } else {
                                attribute.writeString(("Allah Akbar " + x).toCharArray());
                                System.out.println(("Allah Akbar " + x).toCharArray());
                            }
                            break;
                    }
                    attribute.commiteWrite();
                    if (!attribute.isEndOfAttribute()) {
                        attribute.moveNext();
                    }
                }
            }
        } catch (IOException x) {
                x.printStackTrace();
        }
        //3- read data from attributes.
        System.out.println("3- read data from attributes.");
        try {
            for (int i = 0; i < 10; i++) {
                attribute = (Attribute)attributes.get(i);
                attribute.moveFirst();
            }
            for (int x = 0; x < 21; x++) {
                System.out.print("" + x +" ");
                for (int i = 0; i < 10; i++) {
                    attribute = (Attribute)attributes.get(i);
                    switch (attribute.getType()) {
                        case Attribute.BOOLEAN:
                            System.out.print(attribute.readBoolean());
                            System.out.print("     ");
                            break;
                        case Attribute.BYTE:
                            System.out.print(attribute.readByte());
                            System.out.print("     ");
                            break;
                        case Attribute.CHARACTER:
                            System.out.print(attribute.readChar());
                            System.out.print("     ");
                            break;
                        case Attribute.SHORT:
                            System.out.print(attribute.readShort());
                            System.out.print("     ");
                            break;
                        case Attribute.INTEGER:
                            System.out.print(attribute.readInteger());
                            System.out.print("     ");
                            break;
                        case Attribute.FLOAT:
                            System.out.print(attribute.readFloat());
                            System.out.print("     ");
                            break;
                        case Attribute.LONG:
                            System.out.print(attribute.readLong());
                            System.out.print("     ");
                            break;
                        case Attribute.DOUBLE:
                            System.out.print(attribute.readDouble());
                            System.out.print("     ");
                            break;
                        case Attribute.STRING:
                            System.out.println(attribute.readString());
                            break;
                    }
                    if (!attribute.isEndOfAttribute()) {
                        attribute.moveNext();
                    }
                }
            }
        } catch (IOException x) {
                x.printStackTrace();
        }
        
        //3- read data from attributes.
        System.out.println("4- read data from attributes backward.");
        try {
            for (int x = 20; x > -1; x--) {
                System.out.print("" + x +" ");
                for (int i = 0; i < 10; i++) {
                    attribute = (Attribute)attributes.get(i);
                    switch (attribute.getType()) {
                        case Attribute.BOOLEAN:
                            System.out.print(attribute.readBoolean());
                            System.out.print("     ");
                            break;
                        case Attribute.BYTE:
                            System.out.print(attribute.readByte());
                            System.out.print("     ");
                            break;
                        case Attribute.CHARACTER:
                            System.out.print(attribute.readChar());
                            System.out.print("     ");
                            break;
                        case Attribute.SHORT:
                            System.out.print(attribute.readShort());
                            System.out.print("     ");
                            break;
                        case Attribute.INTEGER:
                            System.out.print(attribute.readInteger());
                            System.out.print("     ");
                            break;
                        case Attribute.FLOAT:
                            System.out.print(attribute.readFloat());
                            System.out.print("     ");
                            break;
                        case Attribute.LONG:
                            System.out.print(attribute.readLong());
                            System.out.print("     ");
                            break;
                        case Attribute.DOUBLE:
                            System.out.print(attribute.readDouble());
                            System.out.print("     ");
                            break;
                        case Attribute.STRING:
                            System.out.println(attribute.readString());
                            break;
                    }
                    if (!attribute.isBeginOfAttribute()) {
                        attribute.movePrevious();
                    }
                }
            }
        } catch (IOException x) {
                x.printStackTrace();
        }
        System.out.println("Test Finished");
    }

}
