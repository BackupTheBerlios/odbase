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
* DataObject.java                                                             *
* Created on 2003-02-16, 1:00 AM                                              *
*-----------------------------------------------------------------------------*/


package com.odbase.engine.object;

import com.odbase.engine.object.structure.attribute.Attribute;
import com.odbase.engine.object.structure.attribute.BasicAttribute;

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
public abstract class DataObject {
    private long    oid;
    private String  className;
    private String  instanceName;
    private int     pageSize;
    private int     pagesCount;
    private int     uniteSize;
    private boolean clean;
    
    protected Attribute attribute;
    
    /* when ever we add a new record we increment this counter or opposit*/
    private int     recordsCount;
    
    public static String BOOLEAN_CLASS_NAME    = "Boolean";
    public static String BYTE_CLASS_NAME       = "Byte";
    public static String CHARACTER_CLASS_NAME  = "Character";
    public static String SHORT_CLASS_NAME      = "Short";
    public static String INTEGER_CLASS_NAME    = "Integer";
    public static String FLOAT_CLASS_NAME      = "Float";
    public static String LONG_CLASS_NAME       = "Long";
    public static String DOUBLE_CLASS_NAME     = "Double";
    public static String TIME_CLASS_NAME       = "Time";
    public static String STRING_CLASS_NAME     = "String";
    public static String STREAM_CLASS_NAME     = "Stream";
    
    /** Creates a new instance of DataObject */
    public DataObject(long oid, String className, String instanceName,
        int pageSize, int pagesCount, int uniteSize, boolean clean) {
        this.oid            = oid;
        this.className      = className;
        this.instanceName   = instanceName;
        this.pageSize       = pageSize;
        this.uniteSize      = uniteSize;
        this.pagesCount     = pagesCount;
        this.clean          = clean;
        
        try {
            if (className.equals(BOOLEAN_CLASS_NAME)) {
                attribute = BasicAttribute.createAttribute(
                                Attribute.BOOLEAN, this.pageSize, Attribute.BOOLEAN_SIZE, this.clean);
            } else if (className.equals(CHARACTER_CLASS_NAME)) {
                attribute = BasicAttribute.createAttribute(
                                    Attribute.CHARACTER, this.pageSize, Attribute.CHARACTER_SIZE, this.clean);
            } else if (className.equals(BYTE_CLASS_NAME)) {
                attribute = BasicAttribute.createAttribute(
                                Attribute.BYTE, this.pageSize, Attribute.BYTE_SIZE, this.clean);
            } else if (className.equals(SHORT_CLASS_NAME)) {
                attribute = BasicAttribute.createAttribute(
                                Attribute.SHORT, this.pageSize, Attribute.SHORT_SIZE, this.clean);
            } else if (className.equals(INTEGER_CLASS_NAME)) {
                attribute = BasicAttribute.createAttribute(
                                Attribute.INTEGER, this.pageSize, Attribute.INTEGER_SIZE, this.clean);
            } else if (className.equals(FLOAT_CLASS_NAME)) {
                attribute = BasicAttribute.createAttribute(
                                Attribute.FLOAT, this.pageSize, Attribute.FLOAT_SIZE, this.clean);
            } else if (className.equals(LONG_CLASS_NAME)) {
                attribute = BasicAttribute.createAttribute(
                                Attribute.LONG, this.pageSize, Attribute.LONG_SIZE, this.clean);
            } else if (className.equals(DOUBLE_CLASS_NAME)) {
                attribute = BasicAttribute.createAttribute(
                            Attribute.DOUBLE, this.pageSize, Attribute.DOUBLE_SIZE, this.clean);
            } else if (className.equals(STRING_CLASS_NAME)) {
                attribute = BasicAttribute.createAttribute(
                            Attribute.STRING, this.pageSize, Attribute.STRING_SIZE, this.clean);
            } else if (className.equals(TIME_CLASS_NAME)) {
                attribute = BasicAttribute.createAttribute(
                            Attribute.TIME, this.pageSize, Attribute.TIME_SIZE, this.clean);
            } else if (className.equals(BINARY_CLASS_NAME)) {
                attribute = BasicAttribute.createAttribute(
                            Attribute.BINARY, this.pageSize, Attribute.BINARY_SIZE, this.clean);
            } else {
                throw new UnsupportedAttributeException(
                "can't instanciate unsupported Attribute type " + attributeType);
            }
        } catch (UnsupportedAttributeException x) {
            x.printStackTrace();
        }
    }
    
    public long getOID() {
        return oid;
    }
    
    public String getClassName() {
        return className;
    }
    
    public String getInstanceName() {
        return instanceName;
    }
    
    public long getSize() {
        // the integer (pageSize / 8) is the number of bytes on top of
        // each page that represents the null elements in Data-Page-Set
        // if we have 24 elements of data in a page then there is
        // (24 / 8) = 3 bytes for storing the null flags of page elements
        return pageSize * uniteSize * pagesCount * (pageSize / 8);
    }
    
    public long getRecordsCount() {
        return recordsCount;
    }
    
    public String toString() {
        return  "Object ID = " + oid
                + " $ Class Name = "+ className
                + " $ Instance Name = " + instanceName ;
    }

    public abstract Object read(int index);
    public abstract void write(int index, Object value);

    public abstract void moveFirst();
    public abstract void moveNext();
    public abstract void movePrevious();
    public abstract void moveLast();
}
