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
package org.eclipse.persistence.tools.oracleddl.test.ddlparser;

//javase imports
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

//JUnit4 imports
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.FieldType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLPackageType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLRecordType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLType;
import org.eclipse.persistence.tools.oracleddl.parser.DDLParser;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypesRepository;


public class PackageDDLTestSuite {

    static final String CREATE_PACKAGE_PREFIX = "CREATE PACKAGE ";
    //JUnit fixture(s)
    static DDLParser parser = null;

    @BeforeClass
    static public void setUp() {
        parser = new DDLParser(new InputStream() {
            public int read() throws IOException {
                return 0;
            }
        });
        parser.setTypesRepository(new DatabaseTypesRepository());
    }

    static final String EMPTY_PACKAGE = "EMPTY_PACKAGE";
    static final String CREATE_EMPTY_PACKAGE =
        CREATE_PACKAGE_PREFIX + EMPTY_PACKAGE + " AS END " + EMPTY_PACKAGE + ";";
    @Test
    public void testEmptyPackage() {
        parser.ReInit(new StringReader(CREATE_EMPTY_PACKAGE));
        boolean worked = true;
        @SuppressWarnings("unused") PLSQLPackageType packageType = null;
        try {
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue("empty package should parse", worked);
        assertEquals("empty package wrong name", packageType.getPackageName(), EMPTY_PACKAGE);
        assertNull("empty package should have no procedures", packageType.getProcedures());
        assertNull("empty package should have no types", packageType.getTypes());
    }

    /*
    TYPE EREC IS RECORD(
        FLAG    PLS_INTEGER,
        EMPNO   NUMERIC,
        ENAME   VARCHAR2,
        JOB     VARCHAR2)
      );
    */
    static final String SIMPLE_PACKAGE = "SIMPLE_PACKAGE";
    static final String SIMPLE_TYPE = "EREC";
    static final String FIELD1_NAME = "FLAG";
    static final String FIELD1_TYPE = "PLS_INTEGER";
    static final String FIELD2_NAME = "EMPNO";
    static final String FIELD2_TYPE = "NUMERIC";
    static final String FIELD3_NAME = "ENAME";
    static final String FIELD3_TYPE = "VARCHAR2";
    static final String FIELD4_NAME = "JOB";
    static final String FIELD4_TYPE = "VARCHAR2";
    static final String CREATE_SIMPLE_PACKAGE =
        CREATE_PACKAGE_PREFIX + SIMPLE_PACKAGE + " AS " +
            "\nTYPE " + SIMPLE_TYPE + " IS RECORD (" +
                "\n" + FIELD1_NAME + " " + FIELD1_TYPE + "," +
                "\n" + FIELD2_NAME + " " + FIELD2_TYPE + "," +
                "\n" + FIELD3_NAME + " " + FIELD3_TYPE + "," +
                "\n" + FIELD4_NAME + " " + FIELD4_TYPE +
            "\n);" +
        "\nEND " + SIMPLE_PACKAGE + ";";
    @Test
    public void testSimplePackage() {
        parser.ReInit(new StringReader(CREATE_SIMPLE_PACKAGE));
        boolean worked = true;
        @SuppressWarnings("unused") PLSQLPackageType packageType = null;
        try {
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue("simple package should parse", worked);
        assertEquals("simple package has wrong name", packageType.getPackageName(), SIMPLE_PACKAGE);
        assertNull("simple package should have no procedures", packageType.getProcedures());
        assertNotNull("simple package should have types", packageType.getTypes());
        assertEquals("simple package should have exactly 1 type", 1, packageType.getTypes().size());
        PLSQLType type = packageType.getTypes().get(0);
        assertEquals("type has wrong name", SIMPLE_TYPE, type.getTypeName());
        List<FieldType> fields = ((PLSQLRecordType)type).getFields();
        assertEquals("type field1 has wrong name", FIELD1_NAME, fields.get(0).getFieldName());
        assertEquals("type field1 has wrong type", FIELD1_TYPE, fields.get(0).getTypeName());
        assertEquals("type field2 has wrong name", FIELD2_NAME, fields.get(1).getFieldName());
        assertEquals("type field1 has wrong type", FIELD2_TYPE, fields.get(1).getTypeName());
        assertEquals("type field3 has wrong name", FIELD3_NAME, fields.get(2).getFieldName());
        assertEquals("type field1 has wrong type", FIELD3_TYPE, fields.get(2).getTypeName());
        assertEquals("type field4 has wrong name", FIELD4_NAME, fields.get(3).getFieldName());
        assertEquals("type field4 has wrong type", FIELD4_TYPE, fields.get(3).getTypeName());
    }

    /*
    TYPE ADDRESS IS RECORD(
        HOUSE_NUMBER VARCHAR2(6),
        STREET       VARCHAR2(50),
        PHONE        VARCHAR2(15),
        REGION       VARCHAR2(10),
        POSTAL_CODE  VARCHAR2(10),
        COUNTRY      VARCHAR2(25)
      );
      TYPE CONTACT IS RECORD(
        HOME     ADDRESS,
        BUSINESS ADDRESS
      );
    */
    static final String NPACKAGE = "PACKAGE_WITH_NESTED_RECORDS";
    static final String NTYPE1 = "ADDRESS";
    static final String NT1_FIELD_TYPE = "VARCHAR2";
    static final String NT1_FIELD1_NAME = "HOUSE_NUMBER";
    static final String NT1_FIELD2_NAME = "STREET";
    static final String NT1_FIELD3_NAME = "PHONE";
    static final String NT1_FIELD4_NAME = "REGION";
    static final String NT1_FIELD5_NAME = "POSTAL_CODE";
    static final String NT1_FIELD6_NAME = "COUNTRY";
    static final String NTYPE2 = "CONTACT";
    static final String NT2_FIELD_TYPE = NTYPE1;
    static final String NT2_FIELD1_NAME = "HOME";
    static final String NT2_FIELD2_NAME = "BUSINESS";
    static final String CREATE_NPACKAGE =
        CREATE_PACKAGE_PREFIX + NPACKAGE + " AS " +
            "\nTYPE " + NTYPE1 + " IS RECORD (" +
                "\n" + NT1_FIELD1_NAME + " " + NT1_FIELD_TYPE + "," +
                "\n" + NT1_FIELD2_NAME + " " + NT1_FIELD_TYPE + "," +
                "\n" + NT1_FIELD3_NAME + " " + NT1_FIELD_TYPE + "," +
                "\n" + NT1_FIELD4_NAME + " " + NT1_FIELD_TYPE + "," +
                "\n" + NT1_FIELD5_NAME + " " + NT1_FIELD_TYPE + "," +
                "\n" + NT1_FIELD6_NAME + " " + NT1_FIELD_TYPE +
            "\n);" +
            "\nTYPE " + NTYPE2 + " IS RECORD (" +
                "\n" + NT2_FIELD1_NAME + " " + NT2_FIELD_TYPE + "," +
                "\n" + NT2_FIELD2_NAME + " " + NT2_FIELD_TYPE +
            "\n);" +
        "\nEND " + NPACKAGE + ";";
    @Test
    public void testPackageWithNestedRecords() {
        parser.ReInit(new StringReader(CREATE_NPACKAGE));
        boolean worked = true;
        @SuppressWarnings("unused") PLSQLPackageType packageType = null;
        try {
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue("npackage should parse", worked);
        assertEquals("npackage has wrong name", packageType.getPackageName(), NPACKAGE);
        assertNull("npackage should have no procedures", packageType.getProcedures());
        assertNotNull("npackage should have types", packageType.getTypes());
        assertEquals("npackage should have exactly 2 types", 2, packageType.getTypes().size());
        PLSQLType ntype1 = packageType.getTypes().get(0);
        assertEquals("ntype1 has wrong name", NTYPE1, ntype1.getTypeName());
        List<FieldType> fields = ((PLSQLRecordType)ntype1).getFields();
        assertEquals("ntype1 field1 has wrong name", NT1_FIELD1_NAME, fields.get(0).getFieldName());
        assertEquals("ntype1 field1 has wrong type", NT1_FIELD_TYPE, fields.get(0).getTypeName());
        assertEquals("ntype1 field2 has wrong name", NT1_FIELD2_NAME, fields.get(1).getFieldName());
        assertEquals("ntype1 field1 has wrong type", NT1_FIELD_TYPE, fields.get(1).getTypeName());
        assertEquals("ntype1 field3 has wrong name", NT1_FIELD3_NAME, fields.get(2).getFieldName());
        assertEquals("ntype1 field1 has wrong type", NT1_FIELD_TYPE, fields.get(2).getTypeName());
        assertEquals("ntype1 field4 has wrong name", NT1_FIELD4_NAME, fields.get(3).getFieldName());
        assertEquals("ntype1 field4 has wrong type", NT1_FIELD_TYPE, fields.get(3).getTypeName());
        PLSQLType ntype2 = packageType.getTypes().get(1);
        assertEquals("ntype2 has wrong name", NTYPE2, ntype2.getTypeName());
        fields = ((PLSQLRecordType)ntype2).getFields();
        assertEquals("ntype2 field1 has wrong name", NT2_FIELD1_NAME, fields.get(0).getFieldName());
        assertEquals("ntype2 field1 has wrong type", NT2_FIELD_TYPE, fields.get(0).getTypeName());
        assertEquals("ntype2 field2 has wrong name", NT2_FIELD2_NAME, fields.get(1).getFieldName());
        assertEquals("ntype2 field1 has wrong type", NT2_FIELD_TYPE, fields.get(1).getTypeName());
    }

    //TYPE VCHAR_ARRAY IS TABLE OF VARCHAR2(20) INDEX BY BINARY_INTEGER;
    static final String PACKAGE_WCOLLECTION = "PACKAGE_WCOLLECTION";
    static final String PACKAGE_WCOLLECTION_NAME = "VCHAR_ARRAY";
    static final String PACKAGE_WCOLLECTION_TYPE = "VARCHAR2(20)";
    static final String PACKAGE_WCOLLECTION_INDEXBY = "BINARY_INTEGER";
    static final String CREATE_PACKAGE_WCOLLECTION =
        CREATE_PACKAGE_PREFIX + PACKAGE_WCOLLECTION + " AS " +
            "\nTYPE " + PACKAGE_WCOLLECTION_NAME + " IS TABLE OF " +
                PACKAGE_WCOLLECTION_TYPE + " INDEX BY " + PACKAGE_WCOLLECTION_INDEXBY + ";" +
        "\nEND " + PACKAGE_WCOLLECTION + ";";
    @Test
    public void testPackageWithCollection() {
        parser.ReInit(new StringReader(CREATE_PACKAGE_WCOLLECTION));
        boolean worked = true;
        @SuppressWarnings("unused") PLSQLPackageType packageType = null;
        try {
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue("package with collection should parse", worked);
        assertEquals("package with collection has wrong name", packageType.getPackageName(),
            PACKAGE_WCOLLECTION);
        assertNull("package with collection should have no procedures", packageType.getProcedures());
        assertNotNull("package with collection should have types", packageType.getTypes());
        assertEquals("package with collection should have exactly 1 type", 1,
            packageType.getTypes().size());
        PLSQLType t1 = packageType.getTypes().get(0);
        assertEquals("collection type1 has wrong name", PACKAGE_WCOLLECTION_NAME, t1.getTypeName());
    }

    /*
    TYPE BOOK_TYPE IS RECORD (
        ISBN          VARCHAR2(20)
        ,TITLE        VARCHAR2(2000)
        ,AUTHOR       VARCHAR2(2000)
        ,PUBLISHER    VARCHAR2(120)
        ,PUBLISHED_ON DATE
    );
    TYPE BOOKS_TAB IS TABLE OF BOOK_TYPE;
     */
    static final String PACKAGE_WCOLLECTION_WRECORD = "PACKAGE_WCOLLECTION_WRECORD";
    static final String PACKAGE_WCOLLECTION_WRECORD_TYPE1 = "BOOK_TYPE";
    static final String PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD1_NAME = "ISBN";
    static final String PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD1_TYPE = "VARCHAR2(20)";
    static final String PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD2_NAME = "TITLE";
    static final String PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD2_TYPE = "VARCHAR2(2000)";
    static final String PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD3_NAME = "AUTHOR";
    static final String PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD3_TYPE = "VARCHAR2(2000)";
    static final String PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD4_NAME = "PUBLISHER";
    static final String PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD4_TYPE = "VARCHAR2";
    static final String PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD5_NAME = "PUBLISHED_ON";
    static final String PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD5_TYPE = "DATE";
    static final String PACKAGE_WCOLLECTION_WRECORD_NAME = "BOOKS_TAB";
    static final String CREATE_PACKAGE_WCOLLECTION_WRECORD =
        CREATE_PACKAGE_PREFIX + PACKAGE_WCOLLECTION_WRECORD + " AS " +
            "\nTYPE " + PACKAGE_WCOLLECTION_WRECORD_TYPE1 + " IS RECORD (" +
                "\n" + PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD1_NAME + " " + PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD1_TYPE + "," +
                "\n" + PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD2_NAME + " " + PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD2_TYPE + "," +
                "\n" + PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD3_NAME + " " + PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD3_TYPE + "," +
                "\n" + PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD4_NAME + " " + PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD4_TYPE + "," +
                "\n" + PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD5_NAME + " " + PACKAGE_WCOLLECTION_WRECORD_TYPE1_FIELD5_TYPE +
            "\n);" +
            "\nTYPE " + PACKAGE_WCOLLECTION_WRECORD_NAME + " IS TABLE OF " +
                PACKAGE_WCOLLECTION_WRECORD_TYPE1 + ";" +
        "\nEND " + PACKAGE_WCOLLECTION_WRECORD + ";";
    @Test
    public void testPackageWithCollectionWithNestedRecord() {
        parser.ReInit(new StringReader(CREATE_PACKAGE_WCOLLECTION_WRECORD));
        boolean worked = true;
        @SuppressWarnings("unused") PLSQLPackageType packageType = null;
        try {
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue("package with collection with record should parse", worked);

    }
}