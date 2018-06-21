/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

// Contributors:
//     Mike Norman - Feb 1 2012, created anchored types (%TYPE, %ROWTYPE) tests
package org.eclipse.persistence.tools.oracleddl.test.ddlparser;

//javase imports
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

//JUnit4 imports
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

//Oracle DDL parser imports
import org.eclipse.persistence.tools.oracleddl.metadata.DatabaseType;
import org.eclipse.persistence.tools.oracleddl.metadata.FieldType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLCursorType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLPackageType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLSubType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLType;
import org.eclipse.persistence.tools.oracleddl.parser.DDLParser;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypesRepository;

public class AnchoredTypesTestSuite {

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

    static final String ANCHORED_TYPES_TEST1_PACKAGE = "ANCHORED_TYPES_TEST1";
    static final String ANCHORED_TYPES_TABLE = "ANCHORED_TYPES_TABLE";
    static final String ANCHORED_TYPES_TABLE_FIELD = "NAME";
    static final String ANCHORED_TYPE1_NAME = "W" + ANCHORED_TYPES_TABLE_FIELD;
    static final String ANCHORED_TYPE1 =
        ANCHORED_TYPES_TABLE + "." + ANCHORED_TYPES_TABLE_FIELD + "%TYPE";
    static final String CREATE_ANCHORED_TYPES_TEST1_PACKAGE =
        CREATE_PACKAGE_PREFIX + ANCHORED_TYPES_TEST1_PACKAGE + " AS" +
            "\nSUBTYPE " + ANCHORED_TYPE1_NAME + " IS " + ANCHORED_TYPE1 + ";" +
        "\nEND " + ANCHORED_TYPES_TEST1_PACKAGE + ";";
    @Test
    public void anchoredTypesTest1() {
        parser.ReInit(new StringReader(CREATE_ANCHORED_TYPES_TEST1_PACKAGE));
        boolean worked = true;
        @SuppressWarnings("unused") PLSQLPackageType packageType = null;
        try {
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue(ANCHORED_TYPES_TEST1_PACKAGE + " package should parse", worked);
        assertEquals(ANCHORED_TYPES_TEST1_PACKAGE + " package wrong name",
            packageType.getPackageName(), ANCHORED_TYPES_TEST1_PACKAGE);
        assertNotNull(ANCHORED_TYPES_TEST1_PACKAGE + " package should have types",
            packageType.getTypes());
        assertEquals(ANCHORED_TYPES_TEST1_PACKAGE + " package should have exactly 1 type", 1,
            packageType.getTypes().size());
        PLSQLType type = packageType.getTypes().get(0);
        assertEquals("type has wrong name", ANCHORED_TYPE1_NAME, type.getTypeName());
        assertTrue(ANCHORED_TYPE1_NAME+ " is not SUBTYPE", type.isPLSQLSubType());
        PLSQLSubType subType = (PLSQLSubType)type;
        assertFalse(ANCHORED_TYPE1_NAME+ " is not supposed to have a NOT NULL constraint",
            subType.isNotNull());
        assertFalse(ANCHORED_TYPE1_NAME+ " is not supposed to have a RANGE constraint",
            subType.hasRange());
        DatabaseType enclosedType = subType.getEnclosedType();
        assertFalse(ANCHORED_TYPE1_NAME+ "'s enclosedType is not supposed to be resolved",
            enclosedType.isResolved());
        assertEquals(ANCHORED_TYPE1_NAME + "'s enclosedType is incorrect", ANCHORED_TYPE1,
            enclosedType.getTypeName());
    }

    static final String ANCHORED_TYPES_TEST2_PACKAGE = "ANCHORED_TYPES_TEST2";
    static final String ANCHORED_TYPE2_NAME = "WREC";
    static final String ANCHORED_TYPE2 = ANCHORED_TYPES_TABLE + "%ROWTYPE";
    static final String CREATE_ANCHORED_TYPES_TEST2_PACKAGE =
        CREATE_PACKAGE_PREFIX + ANCHORED_TYPES_TEST2_PACKAGE + " AS" +
            "\nSUBTYPE " + ANCHORED_TYPE1_NAME + " IS " + ANCHORED_TYPE1 + ";" +
            "\nSUBTYPE " + ANCHORED_TYPE2_NAME + " IS " + ANCHORED_TYPE2 + ";" +
            "\nTYPE " + ANCHORED_TYPE2_NAME + "_CURSOR IS REF CURSOR RETURN " + ANCHORED_TYPE2_NAME + ";" +
        "\nEND " + ANCHORED_TYPES_TEST2_PACKAGE + ";";
    @Test
    public void anchoredTypesTest2() {
        parser.ReInit(new StringReader(CREATE_ANCHORED_TYPES_TEST2_PACKAGE));
        boolean worked = true;
        @SuppressWarnings("unused") PLSQLPackageType packageType = null;
        try {
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue(ANCHORED_TYPES_TEST2_PACKAGE + " package should parse", worked);
        assertEquals(ANCHORED_TYPES_TEST2_PACKAGE + " package wrong name",
            packageType.getPackageName(), ANCHORED_TYPES_TEST2_PACKAGE);
        assertNotNull(ANCHORED_TYPES_TEST2_PACKAGE + " package should have types",
            packageType.getTypes());
        assertEquals(ANCHORED_TYPES_TEST2_PACKAGE + " package should have exactly 2 types", 2,
            packageType.getTypes().size());
        PLSQLType type1 = packageType.getTypes().get(0);
        assertEquals("type has wrong name", ANCHORED_TYPE1_NAME, type1.getTypeName());
        assertTrue(ANCHORED_TYPE1_NAME + " is not SUBTYPE", type1.isPLSQLSubType());
        PLSQLSubType subType = (PLSQLSubType)type1;
        assertFalse(ANCHORED_TYPE1_NAME+ " is not supposed to have a NOT NULL constraint",
            subType.isNotNull());
        assertFalse(ANCHORED_TYPE1_NAME+ " is not supposed to have a RANGE constraint",
            subType.hasRange());
        DatabaseType enclosedType = subType.getEnclosedType();
        assertFalse(ANCHORED_TYPE1_NAME+ "'s enclosedType is not supposed to be resolved",
            enclosedType.isResolved());
        assertEquals(ANCHORED_TYPE1_NAME + "'s enclosedType is incorrect", ANCHORED_TYPE1,
            enclosedType.getTypeName());
        PLSQLType type2 = packageType.getTypes().get(1);
        assertEquals("type has wrong name", ANCHORED_TYPE2_NAME, type2.getTypeName());
        assertTrue(ANCHORED_TYPE2_NAME + " is not SUBTYPE", type2.isPLSQLSubType());
        PLSQLSubType subType2 = (PLSQLSubType)type2;
        assertFalse(ANCHORED_TYPE2_NAME+ " is not supposed to have a NOT NULL constraint",
            subType2.isNotNull());
        assertFalse(ANCHORED_TYPE2_NAME+ " is not supposed to have a RANGE constraint",
            subType2.hasRange());
        DatabaseType enclosedType2 = subType2.getEnclosedType();
        assertFalse(ANCHORED_TYPE2_NAME+ "'s enclosedType is not supposed to be resolved",
            enclosedType2.isResolved());
        assertEquals(ANCHORED_TYPE2_NAME + "'s enclosedType is incorrect", ANCHORED_TYPE2,
            enclosedType2.getTypeName());
        PLSQLCursorType cursor = packageType.getCursors().get(0);
        assertEquals("cursor has wrong name", ANCHORED_TYPE2_NAME + "_CURSOR",
            cursor.getCursorName());
        assertFalse(ANCHORED_TYPE2_NAME + "_CURSOR is not supposed to be weakly typed",
            cursor.isWeaklyTyped());
        assertEquals(ANCHORED_TYPE2_NAME + "_CURSOR returns wrong type", ANCHORED_TYPE2_NAME,
            cursor.getEnclosedType().getTypeName());
        assertFalse(ANCHORED_TYPE2_NAME + "_CURSOR's return type is not supposed to be resolved",
            cursor.getEnclosedType().isResolved());
    }

    /*
    CREATE OR REPLACE PACKAGE ANCHORED_TYPES_TEST3 AS
        WREC WTRC_TABLE%ROWTYPE;
        SUBTYPE WNAME IS WREC.NAME%TYPE;
        TYPE WREC_CURSOR IS REF CURSOR RETURN WREC%TYPE;
        END ANCHORED_TYPES_TEST3;
     */
    static final String ANCHORED_TYPES_TEST3_PACKAGE = "ANCHORED_TYPES_TEST3";
    static final String VAR_NAME = "WREC";
    static final String CREATE_ANCHORED_TYPES_TEST3_PACKAGE =
        CREATE_PACKAGE_PREFIX + ANCHORED_TYPES_TEST3_PACKAGE + " AS" +
            "\n" + VAR_NAME + " " + ANCHORED_TYPE2 + ";" +
            "\nSUBTYPE " + ANCHORED_TYPE1_NAME + " IS " + VAR_NAME + "." +  ANCHORED_TYPES_TABLE_FIELD  + "%TYPE;" +
            "\nTYPE " + ANCHORED_TYPE2_NAME + "_CURSOR IS REF CURSOR RETURN " + ANCHORED_TYPE2_NAME + "%TYPE;" +
        "\nEND " + ANCHORED_TYPES_TEST3_PACKAGE + ";";
    @Test
    public void anchoredTypesTest3() {
        parser.ReInit(new StringReader(CREATE_ANCHORED_TYPES_TEST3_PACKAGE));
        boolean worked = true;
        @SuppressWarnings("unused") PLSQLPackageType packageType = null;
        try {
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue(ANCHORED_TYPES_TEST3_PACKAGE + " package should parse", worked);
        assertEquals(ANCHORED_TYPES_TEST3_PACKAGE + " package wrong name",
            packageType.getPackageName(), ANCHORED_TYPES_TEST3_PACKAGE);
        assertNotNull(ANCHORED_TYPES_TEST3_PACKAGE + " package should have variables",
            packageType.getLocalVariables());
        assertEquals(ANCHORED_TYPES_TEST3_PACKAGE + " package should have exactly 1 variables", 1,
            packageType.getLocalVariables().size());
        FieldType var1 = packageType.getLocalVariables().get(0);
        assertEquals(VAR_NAME + " wrong name", VAR_NAME, var1.getFieldName());
        assertEquals(VAR_NAME + " wrong datatype", ANCHORED_TYPE2, var1.getEnclosedType().getTypeName());
        assertEquals(ANCHORED_TYPES_TEST3_PACKAGE + " package should have exactly 1 type", 1,
            packageType.getTypes().size());
        PLSQLType type1 = packageType.getTypes().get(0);
        assertEquals("type has wrong name", ANCHORED_TYPE1_NAME, type1.getTypeName());
        assertTrue(ANCHORED_TYPE1_NAME + " is not SUBTYPE", type1.isPLSQLSubType());
        PLSQLSubType subType = (PLSQLSubType)type1;
        assertFalse(ANCHORED_TYPE1_NAME+ " is not supposed to have a NOT NULL constraint",
            subType.isNotNull());
        assertFalse(ANCHORED_TYPE1_NAME+ " is not supposed to have a RANGE constraint",
            subType.hasRange());
        DatabaseType enclosedType = subType.getEnclosedType();
        assertFalse(ANCHORED_TYPE1_NAME+ "'s enclosedType is not supposed to be resolved",
            enclosedType.isResolved());
        assertEquals(ANCHORED_TYPE1_NAME + "'s enclosedType is incorrect",
            VAR_NAME + "." +  ANCHORED_TYPES_TABLE_FIELD  + "%TYPE", enclosedType.getTypeName());
        PLSQLCursorType cursor = packageType.getCursors().get(0);
        assertEquals("cursor has wrong name", ANCHORED_TYPE2_NAME + "_CURSOR",
            cursor.getCursorName());
        assertFalse(ANCHORED_TYPE2_NAME + "_CURSOR is not supposed to be weakly typed",
            cursor.isWeaklyTyped());
        assertEquals(ANCHORED_TYPE2_NAME + "_CURSOR returns wrong type", ANCHORED_TYPE2_NAME + "%TYPE",
            cursor.getEnclosedType().getTypeName());
        assertFalse(ANCHORED_TYPE2_NAME + "_CURSOR's return type is not supposed to be resolved",
            cursor.getEnclosedType().isResolved());
    }
}
