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
* DomainTest.java                                                             *
* Created on 2003-01-01, 1:00 AM                                              *
*-----------------------------------------------------------------------------*/

package com.odbase.engine.object.structure.domain;

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
public class DomainTest {
    
    /** Creates a new instance of DomainTest */
    public DomainTest() {
    }
    
    public static void StringDomainEffortAnalysis() {
        try {
            System.out.println("starting test...");
            System.out.println("allocating domin");
            //ByteDomain domain = new ByteDomain(100, true);
            StringDomain domain = new StringDomain(10, 4, true);
            char[] a = new char[4];
            //StringDomain domain = new StreamDomain(10, 4, true);
            //byte[] a = new byte[4];
            a[0] = (char)48;
            a[1] = (char)48;
            a[2] = (char)48;
            System.out.println("writting domin data");
            System.out.println("====================");
            domain.seek(-1);
            for (int i = 0; i < domain.getCardinality(); i++) {
                a[3] = (char)(48 + i);
                domain.readForward();
                domain.save(a);
            }
            System.out.println("readForwarding domin data");
            System.out.println("====================");
            //domain.seek(0);
            //domain.seekFrom(Domain.BEGIN, 0);
            //domain.seek(-1);
            domain.seekToBegin();
            //domain.seekFrom(Domain.CURRENT, -10);
            //domain.seekFrom(Domain.END, 0);
            for (int i = 0; i < domain.getCardinality(); i++) {
                System.out.print(domain.readForward());
                System.out.println(" "+domain.getPosition() + "      i = " + i);
            }
            
            System.out.println("\n\n\nTesting using while (!isEndOfDomain)");
            System.out.println("writting domin data");
            System.out.println("====================");
            domain.seekToBegin();
            int i = 0;
            while (!domain.isEndOfDomain()) {
                a[3] = (char)(48 + i);
                domain.readForward();
                domain.save(a);
                i++;
            }
            
            System.out.println("readBackward domin data");
            System.out.println("====================");

            domain.seekToEnd();
            while (!domain.isBeginOfDomain()) {
                System.out.print(domain.readBackward());
                System.out.println(" "+domain.getPosition() + "      i = " + i);
                i--;
            }
            
            domain.seekToBegin();
            System.out.println("readForward domin data");
            System.out.println("====================");
            i = 0;
            while (!domain.isEndOfDomain()) {
                System.out.print(domain.readForward());
                System.out.println(" "+domain.getPosition() + "      i = " + i);
                i++;
            }
            
            System.out.println();
            System.out.println("========================================");
            System.out.println("sending domin to garbage collect");
            domain = null;
            System.out.println("garbage collecting domin");
            System.gc();
            System.out.println("runing system finalizer");
            System.runFinalization();
            System.out.println("testing finished...");
        } catch (OutOfMemoryError x) {
            x.printStackTrace();
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
            
    public static void IODomainEffortAnalysis() {
        try {
            char[] string = new char[10];
            for (int i = 0; i < 10; i++) {
                string[i] = (char)(48+i);
            }
            IODomain ioDomain = new IODomain(50, 0, true);
            System.out.println("now writting domin");
            System.out.println("===================");
            System.out.println("readForward -> boolean true");
            System.out.println("readForward -> byte    100");
            System.out.println("readForward -> char    Z");
            System.out.println("readForward -> short   30000");
            System.out.println("readForward -> int     1500000");
            System.out.println("readForward -> float   "+(float)10/3);
            System.out.println("readForward -> long    1000500000");
            System.out.println("readForward -> double  "+(double)1000500000.123456);
            System.out.println("readForward -> string  0123456789");
            ioDomain.writeBoolean(true);
            ioDomain.writeByte((byte)100);
            ioDomain.writeChar('Z');
            ioDomain.writeShort((short)30000);
            ioDomain.writeInteger(1500000);
            ioDomain.writeFloat((float)10/3);
            ioDomain.writeLong(1000500000);
            ioDomain.writeDouble(1000500000.123456);
            ioDomain.writeString(string);
            ioDomain.seek(-1);
            System.out.println("now readForwarding domin");
            System.out.println("==================");
            System.out.println("readForward -> boolean " + ioDomain.readBoolean());
            System.out.println("readForward -> byte    " + ioDomain.readByte());
            System.out.println("readForward -> char    " + ioDomain.readChar());
            System.out.println("readForward -> short   " + ioDomain.readShort());
            System.out.println("readForward -> int     " + ioDomain.readInteger());
            System.out.println("readForward -> float   " + ioDomain.readFloat());
            //ioDomain.seekFrom(Domain.CURRENT, -4);
            //System.out.println(10/3 == ioDomain.readForwardFloat());
            System.out.println("readForward -> long    " + ioDomain.readLong());
            System.out.println("readForward -> double  " + ioDomain.readDouble());
            //ioDomain.seekFrom(Domain.CURRENT, -8);
            //System.out.println(1000500000.123456 == ioDomain.readForwardDouble());
            System.out.println("readForward -> string  " + new String(ioDomain.readString(string)));
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
    
    public static void effortAnalysis() {
        try {
            //allocat byte domin with size
            //of long array of 100 cell
            byte[] b1 = new byte[800];
            long realTime = 0;
            long t2, t1;
            int o1, o2;
            //System.out.println();
            System.out.println("writing original byte domin");
            t1 = System.currentTimeMillis();
            for (int q = 0; q < 1000000; q++) {
            for (int i = 0; i < 800; i+=8) {
                realTime = i / 8;
                b1[i + 0] = (byte)(realTime >>> 56);
                b1[i + 1] = (byte)(realTime >>> 48);
                b1[i + 2] = (byte)(realTime >>> 40);
                b1[i + 3] = (byte)(realTime >>> 32);
                b1[i + 4] = (byte)(realTime >>> 24);
                b1[i + 5] = (byte)(realTime >>> 16);
                b1[i + 6] = (byte)(realTime >>> 8);
                b1[i + 7] = (byte)(realTime >>> 0);
                o1 = (b1[i + 0] << 24 & 0xFF000000) + (b1[i + 1] << 16 & 0x00FF0000) + (b1[i + 2] << 8 & 0x0000FF00) + (b1[i + 3] << 0 & 0x000000FF);
                o2 = (b1[i + 4] << 24 & 0xFF000000) + (b1[i + 5] << 16 & 0x00FF0000) + (b1[i + 6] << 8 & 0x0000FF00) + (b1[i + 7] << 0 & 0x000000FF);
                realTime = ((long)o1 << 32 & 0xFFFFFFFF00000000L) + ((long)o2 << 0 & 0x00000000FFFFFFFFL);
            }
            }
            t2 = System.currentTimeMillis();
            System.out.println("time = " + (t2-t1));

            //System.out.println("Instantiating new LongDomain");
            LongDomain domain = new LongDomain(100, 8, true);
            domain.seek(-1);

            System.out.println("writing LongDomain long data");
            System.out.println("============================");
            t1 = System.currentTimeMillis();
            for(int a = 0; a < 1000000; a++) {
                //8127
            domain.seek(-1);
            for (int i = 0; i < 800; i+=8) {
                o1 = (b1[i + 0] << 24 & 0xFF000000) + (b1[i + 1] << 16 & 0x00FF0000) + (b1[i + 2] << 8 & 0x0000FF00) + (b1[i + 3] << 0 & 0x000000FF);
                o2 = (b1[i + 4] << 24 & 0xFF000000) + (b1[i + 5] << 16 & 0x00FF0000) + (b1[i + 6] << 8 & 0x0000FF00) + (b1[i + 7] << 0 & 0x000000FF);
                domain.save(((long)o1 << 32 & 0xFFFFFFFF00000000L) + ((long)o2 << 0 & 0x00000000FFFFFFFFL));
            }
            }
            
            domain.seek(-1);
//            System.out.println("readForwarding LongDomain long data");
  //          System.out.println("============================");
            for (int i = 0; i < 100; i++) {
                System.out.println(domain.readForward());
            }
            t2 = System.currentTimeMillis();
            System.out.println("time = " + (t2-t1));
            System.out.println("finished");
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
    
    public static void plusEffortAnalysis() {
        byte[] domin = new byte[8];
        int cursor = 0;
        int convertDomain01, convertDomain02;
        long t1,t2;
        for (int i = 0; i < 1000000000; i++) {
        }
        t1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            /*cursor = 0;
            cursor += 1;*/
            cursor = 8;
            //convertDomain01 = domin[cursor - 8] << 24;
            convertDomain01 = (domin[cursor - 8] << 24) + (domin[cursor - 7] << 16 & 0x00FF0000) + (domin[cursor - 6] << 8 & 0x0000FF00) + (domin[cursor - 5] << 0 & 0x000000FF);
            convertDomain02 = (domin[cursor - 4] << 24) + (domin[cursor - 3] << 16 & 0x00FF0000) + (domin[cursor - 2] << 8 & 0x0000FF00) + (domin[cursor - 1] << 0 & 0x000000FF);
        }
        t2 = System.currentTimeMillis();
        System.out.println("time = " + (t2-t1));
        
        t1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            /*cursor = 0;
            cursor += 1;*/
            cursor = 8;
            //convertDomain01 = domin[cursor - 8] << 24;
            convertDomain01 = (domin[cursor - 8] << 24) + (domin[cursor - 7] << 16 & 0x00FF0000) + (domin[cursor - 6] << 8 & 0x0000FF00) + (domin[cursor - 5] << 0 & 0x000000FF);
            convertDomain02 = (domin[cursor - 4] << 24) + (domin[cursor - 3] << 16 & 0x00FF0000) + (domin[cursor - 2] << 8 & 0x0000FF00) + (domin[cursor - 1] << 0 & 0x000000FF);
        }
        t2 = System.currentTimeMillis();
        System.out.println("time = " + (t2-t1));
        
        t1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            /*cursor = 0;
            cursor++;*/
            cursor = -1;
            //convertDomain01 = domin[++cursor] << 24;
            convertDomain01 = (domin[++cursor] << 24) + (domin[++cursor] << 16 & 0x00FF0000) + (domin[++cursor] << 8 & 0x0000FF00) + (domin[++cursor] << 0 & 0x000000FF);
            convertDomain02 = (domin[++cursor] << 24) + (domin[++cursor] << 16 & 0x00FF0000) + (domin[++cursor] << 8 & 0x0000FF00) + (domin[++cursor] << 0 & 0x000000FF);
        }
        t2 = System.currentTimeMillis();
        System.out.println("time = " + (t2-t1));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //StringDomainEffortAnalysis();
        IODomainEffortAnalysis();
        //effortAnalysis();
        //plusEffortAnalysis();
        /*
        try {
            //System.out.println();
            System.out.println("starting test...");
            System.out.println("allocating domin");
            //ByteDomain domain = new ByteDomain(10, 1, true);
            LongDomain domain = new LongDomain(10, 8, true);

            int i = 0;
            System.out.println("\nTesting using while (!isEndOfDomain)");
            System.out.println("writting domin data");
            System.out.println("====================");
            domain.seek(-1);
            while (!domain.isEndOfDomain()) {
                domain.readForward();
                domain.save((byte)i);
                i++;
            }
            
            System.out.println("writing backward");
            System.out.println("====================");
            domain.seekToEnd();
            i = 9;
            while (!domain.isBeginOfDomain()) {
                domain.readBackward();
                domain.save((byte)i);
                i--;
            }
            i++;
            
            domain.seekToBegin();
            System.out.println("readForward domin data");
            System.out.println("====================");
            //i = 0;
            while (!domain.isEndOfDomain()) {
                System.out.print(domain.readForward());
                System.out.println(" "+domain.getPosition() + "      i = " + i);
                i++;
            }
            
            System.out.println("readBackward domin data");
            System.out.println("====================");

            domain.seekToEnd();
            while (!domain.isBeginOfDomain()) {
                System.out.print(domain.readBackward());
                System.out.println(" "+domain.getPosition() + "      i = " + i);
                i--;
            }
            
            System.out.println();
            System.out.println("========================================");
            System.out.println("sending domin to garbage collect");
            domain = null;
            System.out.println("garbage collecting domin");
            System.gc();
            System.out.println("runing system finalizer");
            System.runFinalization();
            System.out.println("testing finished...");
        } catch (OutOfMemoryError x) {
            x.printStackTrace();
        } catch (IOException x) {
            x.printStackTrace();
        }
         */
    }
}
