/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

// Contributors:
//     Mike Norman - June 10 2011, created DDL parser package
//     David McCann - July 2011, visit tests
package org.eclipse.persistence.tools.oracleddl.test.ddlparser;

//javase imports
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

//JUnit4 imports
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.FieldType;
import org.eclipse.persistence.tools.oracleddl.metadata.NumericType;
import org.eclipse.persistence.tools.oracleddl.metadata.TableType;
import org.eclipse.persistence.tools.oracleddl.metadata.VarChar2Type;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.UnresolvedTypesVisitor;
import org.eclipse.persistence.tools.oracleddl.parser.DDLParser;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypesRepository;

public class TableDDLTestSuite {

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

    static final String CREATE_TABLE_PREFIX = "CREATE TABLE ";
    static final String EMPTY_TABLE = CREATE_TABLE_PREFIX + "EMPTY_TABLE();";
    @Test
    public void testEmptyTable() {
        parser.ReInit(new StringReader(EMPTY_TABLE));
        boolean worked = true;
        @SuppressWarnings("unused") TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertFalse("empty table should not parse", worked);
    }

    static final String DUMMY = "DUMMY";
    static final String DUMMY_TABLE = DUMMY + "_TABLE ( " + DUMMY + " VARCHAR2(1) );";
    @Test
    public void testDummyTable() {
        parser.ReInit(new StringReader(CREATE_TABLE_PREFIX + DUMMY_TABLE));
        boolean worked = true;
        String message = "";
        TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("dummy table did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(tableType);
        assertTrue("dummy table should not contain any unresolved column datatypes",
            l.getUnresolvedTypes().isEmpty());
        List<FieldType> columns = tableType.getColumns();
        assertEquals("dummy table has wrong number of columns", 1, columns.size());
        FieldType col1 = columns.get(0);
        assertEquals("incorrect name for " + DUMMY + " column",
            DUMMY, col1.getFieldName());
        assertEquals("incorrect type for " + DUMMY + " column",
            new VarChar2Type().getTypeName(), col1.getTypeName());
    }

    static final String DUMMY_TABLE_SCHEMA = "SCOTT.";
    @Test
    public void testDummyTable_WithSchema() {
        parser.ReInit(new StringReader(CREATE_TABLE_PREFIX + DUMMY_TABLE_SCHEMA +
            DUMMY_TABLE));
        boolean worked = true;
        String message = "";
        TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("dummy table did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(tableType);
        assertTrue("dummy table should not contain any unresolved column datatypes",
            l.getUnresolvedTypes().isEmpty());
        assertEquals("dummy table wrong schema",
            DUMMY_TABLE_SCHEMA.subSequence(0, DUMMY_TABLE_SCHEMA.length()-1),
            tableType.getSchema());
    }

    static final String DUMMY_TABLE_QUOTED_SCHEMA = "\"SCOTT\".";
    @Test
    public void testDummyTable_WithQuotedSchema() {
        parser.ReInit(new StringReader(CREATE_TABLE_PREFIX + DUMMY_TABLE_QUOTED_SCHEMA +
            DUMMY_TABLE));
        boolean worked = true;
        String message = "";
        TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("dummy table did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(tableType);
        assertTrue("dummy table should not contain any unresolved column datatypes",
            l.getUnresolvedTypes().isEmpty());
        assertEquals("dummy table wrong schema",
            DUMMY_TABLE_SCHEMA.subSequence(0, DUMMY_TABLE_SCHEMA.length()-1),
            tableType.getSchema());
    }

    static final String NORMAL = "NORMAL";
    static final String ENAME = "ENAME";
    static final String JOB = "JOB";
    static final String SAL = "SAL";
    static final String COMM = "COMM";
    static final String NORMAL_TABLE =
        NORMAL + " (\n" +
            ENAME + " VARCHAR2(10),\n" +
            JOB + " VARCHAR2(9),\n" +
            SAL + " NUMBER,\n" +
            COMM + " NUMBER,\n" +
            "CONSTRAINT \"PK_BONUS\" PRIMARY KEY (\"" + ENAME + "\", \"" + JOB + "\") ENABLE\n" +
        ");";
    @Test
    public void testNormalTable() {
        parser.ReInit(new StringReader(CREATE_TABLE_PREFIX + NORMAL_TABLE));
        boolean worked = true;
        String message = "";
        TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(NORMAL + " table did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(tableType);
        assertTrue(NORMAL + " table should not contain any unresolved column datatypes",
            l.getUnresolvedTypes().isEmpty());
        List<FieldType> columns = tableType.getColumns();
        assertEquals(NORMAL + " table has wrong number of columns", 4, columns.size());
        FieldType col1 = columns.get(0);
        assertEquals("incorrect name for " + ENAME + " column",
            ENAME, col1.getFieldName());
        assertEquals("incorrect type for " + ENAME + " column",
            new VarChar2Type().getTypeName(), col1.getTypeName());
        FieldType col2 = columns.get(1);
        assertEquals("incorrect name for " + JOB + " column",
            JOB, col2.getFieldName());
        assertEquals("incorrect type for " + JOB + " column",
            new VarChar2Type().getTypeName(), col2.getTypeName());
        FieldType col3 = columns.get(2);
        assertEquals("incorrect name for " + SAL + " column",
            SAL, col3.getFieldName());
        assertEquals("incorrect type for " + SAL + " column",
            new NumericType().getTypeName(), col3.getTypeName());
        FieldType col4 = columns.get(3);
        assertEquals("incorrect name for " + COMM + " column",
            COMM, col4.getFieldName());
        assertEquals("incorrect type for " + COMM + " column",
            new NumericType().getTypeName(), col4.getTypeName());
    }

    static final String IOT_TABLE =
        " FOO_IOT (\n" +
            ENAME + " VARCHAR2(10),\n" +
            JOB + " VARCHAR2(9),\n" +
            SAL + " NUMBER,\n" +
            COMM + " NUMBER,\n" +
            "CONSTRAINT \"PK_BONUS\" PRIMARY KEY (\"" + ENAME + "\", \"" + JOB + "\") ENABLE\n" +
        ") ORGANIZATION INDEX;";
    @Test
    public void testIOTTable() {
        parser.ReInit(new StringReader(CREATE_TABLE_PREFIX + IOT_TABLE));
        boolean worked = true;
        String message = "";
        TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("iot table did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(tableType);
        assertTrue("iot table should not contain any unresolved column datatypes",
            l.getUnresolvedTypes().isEmpty());
        List<FieldType> columns = tableType.getColumns();
        assertEquals(NORMAL + " table has wrong number of columns", 4, columns.size());
        FieldType col1 = columns.get(0);
        assertEquals("incorrect name for " + ENAME + " column",
            ENAME, col1.getFieldName());
        assertEquals("incorrect type for " + ENAME + " column",
            new VarChar2Type().getTypeName(), col1.getTypeName());
        FieldType col2 = columns.get(1);
        assertEquals("incorrect name for " + JOB + " column",
            JOB, col2.getFieldName());
        assertEquals("incorrect type for " + JOB + " column",
            new VarChar2Type().getTypeName(), col2.getTypeName());
        FieldType col3 = columns.get(2);
        assertEquals("incorrect name for " + SAL + " column",
            SAL, col3.getFieldName());
        assertEquals("incorrect type for " + SAL + " column",
            new NumericType().getTypeName(), col3.getTypeName());
        FieldType col4 = columns.get(3);
        assertEquals("incorrect name for " + COMM + " column",
            COMM, col4.getFieldName());
        assertEquals("incorrect type for " + COMM + " column",
            new NumericType().getTypeName(), col4.getTypeName());
    }

    static final String TIMESTAMP_TABLE = "TIMESTAMP_TABLE (" +
            "\nID NUMBER(10,0) NOT NULL ENABLE," +
            "\nRUN_DATE TIMESTAMP (6)," +
            "\nRAW_RESULT BLOB," +
            "\nANALYSIS_ID NUMBER(10,0)," +
            "\nPRIMARY KEY (ID) ENABLE\n" +
        ");";
    @Test
    public void testTimestampTable() {
        parser.ReInit(new StringReader(CREATE_TABLE_PREFIX + TIMESTAMP_TABLE));
        boolean worked = true;
        String message = "";
        @SuppressWarnings("unused")
        TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("timestamp table did not parse:\n" + message, worked);
    }

    static final String CONSTRAINTs_TABLE = "CONSTRAINT_TABLE (" +
            "\nEMPLOYEE_ID NUMBER(6) NOT NULL," +
            "\nSALARY NUMBER(8,2)," +
            "\nEMAIL VARCHAR2(25) CONSTRAINT \"EMP_EMAIL_NN\" NOT NULL ENABLE," +
            "\nCONSTRAINT \"EMP_EMP_ID_PK\" PRIMARY KEY (EMPLOYEE_ID) ENABLE,\n" +
            "\nCONSTRAINT \"EMP_EMAIL_UK\" UNIQUE (EMAIL) ENABLE," +
            "\nCONSTRAINT \"EMP_SALARY_MIN\" CHECK (SALARY > 0) ENABLE" + 
        ");";
    
    @Test
    public void testAdditionalTableConstrainst() {
        parser.ReInit(new StringReader(CREATE_TABLE_PREFIX + CONSTRAINTs_TABLE));
        boolean worked = true;
        String message = "";
        @SuppressWarnings("unused")
        TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("constraints table did not parse:\n" + message, worked);
    }

    static final String TABLE_W_KEYWORDS = "KEYWORD_TABLE";
    static final String CREATE_TABLE_W_KEYWORDS =
        CREATE_TABLE_PREFIX + TABLE_W_KEYWORDS + " ( " + 
            "\nID NUMBER(10,0) NOT NULL ENABLE," +
            "\nTIMESTAMP TIMESTAMP (6)," +
            "\nRAW_RESULT BLOB," +
            "\nANALYSIS_ID NUMBER(10,0)" +
        ");";
    @Test
    public void testKeywordTable() {
        parser.ReInit(new StringReader(CREATE_TABLE_W_KEYWORDS));
        boolean worked = true;
        String message = "";
        TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("keyword table did not parse:\n" + message, worked);
        assertEquals("incorrect table name for " + TABLE_W_KEYWORDS,
            TABLE_W_KEYWORDS, tableType.getTableName());
    }
}
