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
* BufferTest.java                                                             *
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
public class BufferTest {
    
    /** Creates a new instance of BufferTest */
    public BufferTest() {
    }
    
    public static void StringBufferEffortAnalysis() {
        try {
            StringBuffer sb = new StringBuffer(10, 4, false);
            String t = null;
            for (int i = 0; i < sb.getLength(); i++) {
                sb.write(((String)("000"+i)).toCharArray());
            }
            
            sb.seek(0);
            for (int i = 0; i < sb.getLength(); i++) {
                t = new String(sb.read());
                System.out.println(t);
            }
            System.out.println("finished");
            System.out.println(t);
            
            System.out.println("start");
            sb.seekFrom(Buffer.END, -3);
            System.out.println(sb.read());
            sb.seekFrom(Buffer.CURRENT, -5);
            System.out.println(sb.read());
            System.out.println("finished");
            
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
            
    public static void IOBufferEffortAnalysis() {
        try {
            char[] string = new char[10];
            for (int i = 0; i < 10; i++) {
                string[i] = (char)(48+i);
            }
            IOBuffer ioBuffer = new IOBuffer(50, 0, true);
            System.out.println("now writting buffer");
            System.out.println("===================");
            System.out.println("read -> boolean true");
            System.out.println("read -> byte    100");
            System.out.println("read -> char    '\uFBE7' 64487");
            System.out.println("read -> short   30000");
            System.out.println("read -> int     1500000");
            System.out.println("read -> float   "+10/3);
            System.out.println("read -> long    1000500000");
            System.out.println("read -> double  "+(double)1000500000.123456);
            System.out.println("read -> string  0123456789");
            ioBuffer.writeBoolean(true);
            ioBuffer.writeByte((byte)100);
            ioBuffer.writeChar('Z');
            ioBuffer.writeShort((short)30000);
            ioBuffer.writeInteger(1500000);
            ioBuffer.writeFloat(10/3);
            ioBuffer.writeLong(1000500000);
            ioBuffer.writeDouble(1000500000.123456);
            ioBuffer.writeString(string);
            ioBuffer.seek(0);
            System.out.println("now reading buffer");
            System.out.println("==================");
            System.out.println("read -> boolean " + ioBuffer.readBoolean());
            System.out.println("read -> byte    " + ioBuffer.readByte());
            System.out.println("read -> char    " + ioBuffer.readChar());
            System.out.println("read -> short   " + ioBuffer.readShort());
            System.out.println("read -> int     " + ioBuffer.readInteger());
            System.out.println("read -> float   " + ioBuffer.readFloat());
            //ioBuffer.seekFrom(Buffer.CURRENT, -4);
            //System.out.println(10/3 == ioBuffer.readFloat());
            System.out.println("read -> long    " + ioBuffer.readLong());
            System.out.println("read -> double  " + ioBuffer.readDouble());
            //ioBuffer.seekFrom(Buffer.CURRENT, -8);
            //System.out.println(1000500000.123456 == ioBuffer.readDouble());
            System.out.println("read -> string  " + new String(ioBuffer.readString(string)));
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
    
    public static void effortAnalysis() {
        try {
            //allocat byte buffer with size
            //of long array of 100 cell
            byte[] b1 = new byte[800];
            long realTime = 0;
            long t2, t1;
            int o1, o2;
            //System.out.println();
            System.out.println("writing original byte buffer");
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

            //System.out.println("Instantiating new LongBuffer");
            LongBuffer buf = new LongBuffer(100, 8, 1, true);
            buf.seek(0);

            System.out.println("writing LongBuffer long data");
            System.out.println("============================");
            t1 = System.currentTimeMillis();
            for(int a = 0; a < 1000000; a++) {
                //8127
            buf.seek(0);
            for (int i = 0; i < 800; i+=8) {
                o1 = (b1[i + 0] << 24 & 0xFF000000) + (b1[i + 1] << 16 & 0x00FF0000) + (b1[i + 2] << 8 & 0x0000FF00) + (b1[i + 3] << 0 & 0x000000FF);
                o2 = (b1[i + 4] << 24 & 0xFF000000) + (b1[i + 5] << 16 & 0x00FF0000) + (b1[i + 6] << 8 & 0x0000FF00) + (b1[i + 7] << 0 & 0x000000FF);
                buf.write(((long)o1 << 32 & 0xFFFFFFFF00000000L) + ((long)o2 << 0 & 0x00000000FFFFFFFFL));
            }
            }
            
            buf.seek(0);
//            System.out.println("reading LongBuffer long data");
  //          System.out.println("============================");
            for (int i = 0; i < 100; i++) {
                System.out.println(buf.read());
            }
            t2 = System.currentTimeMillis();
            System.out.println("time = " + (t2-t1));
            System.out.println("finished");
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        StringBufferEffortAnalysis();
        //IOBufferEffortAnalysis();
        //effortAnalysis();
        /*
        try {
            //System.out.println();
            System.out.println("starting test...");
            System.out.println("allocating buffer");
            //ByteBuffer buf = new ByteBuffer(100, true);
            LongBuffer buf = new LongBuffer(100, 8, 1, false);
            System.out.println("writting buffer data");
            System.out.println("====================");
            buf.seek(0);
            for (int i = 0; i < buf.getLength(); i++) {
                buf.write((byte)i);
            }
            System.out.println("reading buffer data");
            System.out.println("====================");
            //buf.seek(0);
            //buf.seekFrom(Buffer.BEGIN, 0);
            buf.seek(50);
            //buf.seekFrom(Buffer.CURRENT, -10);
            //buf.seekFrom(Buffer.END, 0);
            for (int i = 0; i < buf.getLength() - 50; i++) {
                System.out.print(buf.read());
                System.out.println(" "+buf.getPosition() + "      i = " + i);
            }
            System.out.println();
            System.out.println("========================================");
            System.out.println("sending buffer to garbage collect");
            buf = null;
            System.out.println("garbage collecting buffer");
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
