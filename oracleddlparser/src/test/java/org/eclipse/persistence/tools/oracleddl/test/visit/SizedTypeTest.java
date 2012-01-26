/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Mike Norman - June 10 2011, created DDL parser package
 *     David McCann - July 2011, visit tests
 ******************************************************************************/
package org.eclipse.persistence.tools.oracleddl.test.visit;

//JUnit4 imports
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.BinaryType;
import org.eclipse.persistence.tools.oracleddl.metadata.BlobType;
import org.eclipse.persistence.tools.oracleddl.metadata.CharType;
import org.eclipse.persistence.tools.oracleddl.metadata.ClobType;
import org.eclipse.persistence.tools.oracleddl.metadata.LongRawType;
import org.eclipse.persistence.tools.oracleddl.metadata.LongType;
import org.eclipse.persistence.tools.oracleddl.metadata.NCharType;
import org.eclipse.persistence.tools.oracleddl.metadata.NClobType;
import org.eclipse.persistence.tools.oracleddl.metadata.NVarChar2Type;
import org.eclipse.persistence.tools.oracleddl.metadata.RawType;
import org.eclipse.persistence.tools.oracleddl.metadata.SizedType;
import org.eclipse.persistence.tools.oracleddl.metadata.URowIdType;
import org.eclipse.persistence.tools.oracleddl.metadata.VarChar2Type;
import org.eclipse.persistence.tools.oracleddl.metadata.VarCharType;

/**
 * Test SizedType visit method chain.  Ensures that all required 
 * information can be retrieved via SizedType.accept().
 * 
 * This test covers:
 *  - BinaryType
 *  - BlobType
 *  - CharType
 *  - ClobType
 *  - LongRawType
 *  - LongType
 *  - NCharType
 *  - NClobType
 *  - NVarChar2Type
 *  - RawType
 *  - URowIdType
 *  - VarChar2Type
 *  - VarCharType
 *
 */
public class SizedTypeTest {
    protected static long L10 = 10L;
    protected static long L12 = 12L;
    protected static long L4001 = 4001L;

    protected static String BINARY = "BINARY";
    protected static String BINARY_10 = "BINARY(10)";
    protected static String BLOB = "BLOB";
    protected static String BLOB_10 = "BLOB(10)";
    protected static String LONG_RAW = "LONG RAW";
    protected static String LONG_RAW_10 = "LONG RAW(10)";
    protected static String RAW = "RAW";
    protected static String RAW_10 = "RAW(10)";
    protected static String CHAR = "CHAR";
    protected static String CHAR_12 = "CHAR(12)";
    protected static String NCHAR = "NCHAR";
    protected static String NCHAR_12 = "NCHAR(12)";
    protected static String CLOB = "CLOB";
    protected static String CLOB_10 = "CLOB(10)";
    protected static String NCLOB = "NCLOB";
    protected static String UROWID = "UROWID";
    protected static String UROWID_4001 = "UROWID(4001)";
    protected static String VARCHAR = "VARCHAR";
    protected static String VARCHAR_10 = "VARCHAR(10)";
    protected static String VARCHAR2 = "VARCHAR2";
    protected static String VARCHAR2_10 = "VARCHAR2(10)";
    protected static String LONG = "LONG";
    protected static String LONG_10 = "LONG(10)";
    protected static String NVARCHAR2 = "NVARCHAR2";
    protected static String NVARCHAR2_10 = "NVARCHAR2(10)";
    
    protected static SizedTypeVisitor visitor;

    @BeforeClass
    public static void setUp() {
        visitor = new SizedTypeVisitor();
    }
    
	@Test
	public void testBinaryType() {
        // test defaults: default size = 0L, type name = "BINARY"   
        SizedType binaryType = new BinaryType();
        binaryType.accept(visitor);
        assertEquals("BinaryType() test failed:\n", visitor.toString(), BINARY);
        // test setting the initial size
        binaryType = new BinaryType(L10);
        binaryType.accept(visitor);
        assertEquals("BinaryType(L10) test failed:\n", visitor.toString(), BINARY_10);
	}
	
    @Test
    public void testBlobType() {
        // test defaults: default size = 0L, type name = "BLOB"   
        SizedType blobType = new BlobType();
        blobType.accept(visitor);
        assertEquals("BlobType() test failed:\n", visitor.toString(), BLOB);
        // test setting the initial size
        blobType = new BlobType(L10);
        blobType.accept(visitor);
        assertEquals("BlobType(L10) test failed:\n", visitor.toString(), BLOB_10);
    }
    
    @Test
    public void testLongRawType() {
        // test defaults: default size = 0L, type name = "LONG RAW"   
        SizedType lrawType = new LongRawType();
        lrawType.accept(visitor);
        assertEquals("LongRawType() test failed:\n", visitor.toString(), LONG_RAW);
        // test setting the initial size
        lrawType = new LongRawType(L10);
        lrawType.accept(visitor);
        assertEquals("LongRawType(L10) test failed:\n", visitor.toString(), LONG_RAW_10);
    }
    
    @Test
    public void testRawType() {
        // test defaults: default size = 0L, type name = "RAW"   
        SizedType rawType = new RawType();
        rawType.accept(visitor);
        assertEquals("RawType() test failed:\n", visitor.toString(), RAW);
        // test setting the initial size
        rawType = new RawType(L10);
        rawType.accept(visitor);
        assertEquals("RawType(L10) test failed:\n", visitor.toString(), RAW_10);
    }

    @Test
    public void testCharType() {
        // test defaults: default size = 1L, type name = "CHAR"   
        SizedType charType = new CharType();
        charType.accept(visitor);
        assertEquals("CharType() test failed:\n", visitor.toString(), CHAR);
        // test setting the initial size
        charType = new CharType(L12);
        charType.accept(visitor);
        assertEquals("CharType(L12) test failed:\n", visitor.toString(), CHAR_12);
    }

    @Test
    public void testNCharType() {
        // test defaults: default size = 1L, type name = "NCHAR"   
        SizedType nCharType = new NCharType();
        nCharType.accept(visitor);
        assertEquals("NCharType() test failed:\n", visitor.toString(), NCHAR);
        // test setting the initial size
        nCharType = new NCharType(L12);
        nCharType.accept(visitor);
        assertEquals("NCharType(L12) test failed:\n", visitor.toString(), NCHAR_12);
    }
    
    @Test
    public void testClobType() {
        // test defaults: default size = 0L, type name = "CLOB"   
        SizedType clobType = new ClobType();
        clobType.accept(visitor);
        assertEquals("ClobType() test failed:\n", visitor.toString(), CLOB);
        // test setting the initial size
        clobType = new ClobType(L10);
        clobType.accept(visitor);
        assertEquals("ClobType(L10) test failed:\n", visitor.toString(), CLOB_10);
    }
    
    @Test
    public void testNClobType() {
        // test defaults: default size = 0L, type name = "NCLOB"   
        SizedType nClobType = new NClobType();
        nClobType.accept(visitor);
        assertEquals("NClobType() test failed:\n", visitor.toString(), NCLOB);
        // test setting the initial size
        // TODO:  verify that setting initial size of NClob is not allowed
        //nClobType = new NClobType(LONG_10);
        //nClobType.accept(visitor);
        //assertEquals("NClobType(LONG_10) test failed:\n", visitor.toString(), nClobType.toString());
    }
    
    @Test
    public void testURowIdType() {
        // test defaults: default size = 4000L, type name = "UROWID"   
        SizedType uRowIdType = new URowIdType();
        uRowIdType.accept(visitor);
        assertEquals("URowIdType() test failed:\n", visitor.toString(), UROWID);
        // test setting the initial size
        uRowIdType = new URowIdType(L4001);
        uRowIdType.accept(visitor);
        assertEquals("URowIdType(L4001) test failed:\n", visitor.toString(), UROWID_4001);
    }
    
    @Test
    public void testVarCharType() {
        // test defaults: default size = 1L, type name = "VARCHAR"   
        SizedType varcharType = new VarCharType();
        varcharType.accept(visitor);
        assertEquals("VarCharType() test failed:\n", visitor.toString(), VARCHAR);
        // test setting the initial size
        varcharType = new VarCharType(L10);
        varcharType.accept(visitor);
        assertEquals("VarCharType(L10) test failed:\n", visitor.toString(), VARCHAR_10);
    }
    
    @Test
    public void testVarChar2Type() {
        // test defaults: default size = 1L, type name = "VARCHAR2"   
        SizedType varchar2Type = new VarChar2Type();
        varchar2Type.accept(visitor);
        assertEquals("VarChar2Type() test failed:\n", visitor.toString(), VARCHAR2);
        // test setting the initial size
        varchar2Type = new VarChar2Type(L10);
        varchar2Type.accept(visitor);
        assertEquals("VarChar2Type(L10) test failed:\n", visitor.toString(), VARCHAR2_10);
    }
    
    @Test
    public void testLongType() {
        // test defaults: default size = 1L, type name = "LONG"   
        SizedType longType = new LongType();
        longType.accept(visitor);
        assertEquals("LongType() test failed:\n", visitor.toString(), LONG);
        // test setting the initial size
        longType = new LongType(L10);
        longType.accept(visitor);
        assertEquals("LongType(L10) test failed:\n", visitor.toString(), LONG_10);
    }
    
    @Test
    public void testNVarChar2Type() {
        // test defaults: default size = 1L, type name = "NVARCHAR2"   
        SizedType nVarchar2Type = new NVarChar2Type();
        nVarchar2Type.accept(visitor);
        assertEquals("NVarChar2Type() test failed:\n", visitor.toString(), NVARCHAR2);
        // test setting the initial size
        nVarchar2Type = new NVarChar2Type(L10);
        nVarchar2Type.accept(visitor);
        assertEquals("NVarChar2Type(L10) test failed:\n", visitor.toString(), NVARCHAR2_10);
    }
}