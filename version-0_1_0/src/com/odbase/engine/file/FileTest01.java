/*
 * FileTest01.java
 *
 * Created on May 6, 2002, 2:18 PM
 */

package com.odbase.engine.file;

import java.io.*;
import java.util.*;

/**
 *
 * @author  Family
 * @version 
 */
public class FileTest01 {

    /** Creates new FileTest01 */
    public FileTest01() {
    }
    
    public native void seek(long pos) throws IOException;

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) {
        try {
            byte b[] = new byte[10];
            byte b01[] = new byte[10];
            byte b02[] = new byte[3];
            File file = new File("D:\\Java File Testing.txt");
            //file.createNewFile();
            FileOutputStream os01 = new FileOutputStream(file);
            FileInputStream  is01 = new FileInputStream(file);
            FileOutputStream os02 = new FileOutputStream(file);
            FileInputStream  is02 = new FileInputStream(file);
            os01.getChannel().lock(6, 5, false);
            for (int i = 65; i < 72; i++) {
                b01[i - 65] = (byte)i;
            }
            os01.write(b01);
            System.out.println("write");
            //os01.getChannel().lock();
            //is01.getChannel().lock(6, 5, false);
            System.out.println("write");
            is01.read(b02);
            for (int i = 0; i < 10; i++) {
                System.out.print((char)b01[i]);
            }
            System.out.println("write");
            //System.out.println(is01.getChannel()");
            //os01.getChannel().lock();
            //os01.getChannel().lock();
            //is01.getChannel().lock();
            Thread.currentThread().sleep(60000);
/*
            is02.read(b02);
            for (int i = 0; i < 10; i++) {
                System.out.print((char)b02[i]);
            }
            System.out.println();

            System.out.println("Finished");
            Thread.currentThread().sleep(60000);
            os01.close();
            //Runtime.getRuntime().availableProcessors();
 */
        } catch (IOException x) {
            x.printStackTrace();
        } catch (InterruptedException x) {
            x.printStackTrace();
        }
    }

}
