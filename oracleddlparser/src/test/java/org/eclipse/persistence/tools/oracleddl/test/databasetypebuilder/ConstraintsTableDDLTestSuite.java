/*
 * Copyright (c) 2011, 2023 Oracle and/or its affiliates. All rights reserved.
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
//     Mike Norman - June 10 2011, created DDL parser package
//     David McCann - July 2011, visit tests
package org.eclipse.persistence.tools.oracleddl.test.databasetypebuilder;

//javase imports
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//JUnit4 imports
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.DatabaseType;
import org.eclipse.persistence.tools.oracleddl.metadata.FieldType;
import org.eclipse.persistence.tools.oracleddl.metadata.NumericType;
import org.eclipse.persistence.tools.oracleddl.metadata.PrecisionType;
import org.eclipse.persistence.tools.oracleddl.metadata.SizedType;
import org.eclipse.persistence.tools.oracleddl.metadata.TableType;
import org.eclipse.persistence.tools.oracleddl.metadata.VarChar2Type;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypeBuilder;

//testing imports
import org.eclipse.persistence.tools.oracleddl.test.AllTests;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_CREATE_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_DEBUG_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_DROP_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_CREATE;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_DEBUG;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_DROP;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.buildConnection;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.runDdl;

public class ConstraintsTableDDLTestSuite {

    static final String CONSTRAINTS_TABLE = "CONSTRAINT_TABLE";
    static final String CONSTRAINTS_TABLE_FIELD1 =
        "EMPLOYEE_ID";
    static final String CONSTRAINTS_TABLE_FIELD2 =
        "SALARY";
    static final String CONSTRAINTS_TABLE_FIELD3 =
        "EMAIL";
    static final String CREATE_CONSTRAINTS_TABLE =
        "CREATE TABLE " + CONSTRAINTS_TABLE + " (" +
            "\n" + CONSTRAINTS_TABLE_FIELD1 + " NUMBER(6) NOT NULL," +
            "\n" + CONSTRAINTS_TABLE_FIELD2 + " NUMBER(8,2)," +
            "\n" + CONSTRAINTS_TABLE_FIELD3 + " VARCHAR2(25) CONSTRAINT \"EMP_EMAIL_NN\" NOT NULL ENABLE," +
            "\nCONSTRAINT \"EMP_EMP_ID_PK\" PRIMARY KEY (" + CONSTRAINTS_TABLE_FIELD1 + ") ENABLE," +
            "\nCONSTRAINT \"EMP_EMAIL_UK\" UNIQUE (" + CONSTRAINTS_TABLE_FIELD3 + ") ENABLE," +
            "\nCONSTRAINT \"EMP_SALARY_MIN\" CHECK (" + CONSTRAINTS_TABLE_FIELD2+ " > 0) ENABLE" + 
            
        ")";
    static final String DROP_CONSTRAINTS_TABLE =
        "DROP TABLE " + CONSTRAINTS_TABLE;

    //fixtures
    static DatabaseTypeBuilder dtBuilder = DatabaseTypeBuilderTestSuite.dtBuilder;
    static Connection conn = AllTests.conn;
    static TableType tableType = null;
    static List<String> expectedFieldNames = new ArrayList<String>();
    static List<String> expectedPKFieldNames = new ArrayList<String>();

    static boolean ddlCreate = false;
    static boolean ddlDrop = false;
    static boolean ddlDebug = false;

    @BeforeClass
    public static void setUp() throws SQLException, ClassNotFoundException {
        conn = buildConnection();
        dtBuilder = new DatabaseTypeBuilder();
        String ddlCreateProp = System.getProperty(DATABASE_DDL_CREATE_KEY, DEFAULT_DATABASE_DDL_CREATE);
        if ("true".equalsIgnoreCase(ddlCreateProp)) {
            ddlCreate = true;
        }
        String ddlDropProp = System.getProperty(DATABASE_DDL_DROP_KEY, DEFAULT_DATABASE_DDL_DROP);
        if ("true".equalsIgnoreCase(ddlDropProp)) {
            ddlDrop = true;
        }
        String ddlDebugProp = System.getProperty(DATABASE_DDL_DEBUG_KEY, DEFAULT_DATABASE_DDL_DEBUG);
        if ("true".equalsIgnoreCase(ddlDebugProp)) {
            ddlDebug = true;
        }
        if (ddlCreate) {
            runDdl(conn, CREATE_CONSTRAINTS_TABLE, ddlDebug);
        }
        boolean worked = true;
        String msg = null;
        try {
            tableType = dtBuilder.buildTables(conn, "", CONSTRAINTS_TABLE).get(0);
        }
        catch (Exception e) {
            worked = false;
            msg = e.getMessage();
        }
        if (!worked) {
            fail(msg);
        }
        expectedPKFieldNames.add(CONSTRAINTS_TABLE_FIELD1);
        expectedFieldNames.add(CONSTRAINTS_TABLE_FIELD1);
        expectedFieldNames.add(CONSTRAINTS_TABLE_FIELD2);
        expectedFieldNames.add(CONSTRAINTS_TABLE_FIELD3);
    }

    @AfterClass
    static public void tearDown() {
        if (ddlDrop) {
            runDdl(conn, DROP_CONSTRAINTS_TABLE, ddlDebug);
        }
    }

    @Test
    public void testTableName() {
        assertEquals("incorrect table name", CONSTRAINTS_TABLE , tableType.getTableName());
    }

    @Test
    public void testNumberOfColumns() {
        List<FieldType> columns = tableType.getColumns();
        assertTrue("incorrect number of columns", columns.size() ==  3);
    }

    @Test
    public void testPrimaryKeys() {
        List<FieldType> columns = tableType.getColumns();
        List<String> pkFieldNames = new ArrayList<String>();
        for (FieldType field : columns) {
            if ((field.pk())) {
                pkFieldNames.add(field.getFieldName());
            }
        }
        assertEquals("incorrect PK column names", expectedPKFieldNames, pkFieldNames);
    }

    @Test
    public void testColumnNames() {
        List<FieldType> columns = tableType.getColumns();
        List<String> fieldNames = new ArrayList<String>();
        for (FieldType field : columns) {
            fieldNames.add(field.getFieldName());
        }
        assertEquals("incorrect column names", expectedFieldNames, fieldNames);
    }

    @Test
    public void testColumnTypes() {
        List<FieldType> columns = tableType.getColumns();
        FieldType field1 = columns.get(0);
        DatabaseType col1Type = field1.getEnclosedType();
        assertEquals("incorrect type for column [" + CONSTRAINTS_TABLE_FIELD1 + "]",
            new NumericType().getTypeName(), col1Type.getTypeName());
        assertTrue("incorrect NULL constraint for column [" + CONSTRAINTS_TABLE_FIELD1 + "]",
            field1.notNull());

        FieldType field2 = columns.get(1);
        DatabaseType col2Type = field2.getEnclosedType();
        assertEquals("incorrect type for column [" + CONSTRAINTS_TABLE_FIELD2 + "]",
            new NumericType().getTypeName(), col2Type.getTypeName());
        assertFalse("incorrect NULL constraint for column [" + CONSTRAINTS_TABLE_FIELD2 + "]",
            field2.notNull());
        assertTrue("incorrect precision for column [" + CONSTRAINTS_TABLE_FIELD2 + "]",
            ((PrecisionType)col2Type).getPrecision() == 8);
        assertTrue("incorrect scale for column [" + CONSTRAINTS_TABLE_FIELD2 + "]",
            ((PrecisionType)col2Type).getScale() == 2);

        FieldType field3 = columns.get(2);
        DatabaseType col3Type = field3.getEnclosedType();
        assertEquals("incorrect type for column [" + CONSTRAINTS_TABLE_FIELD3 + "]",
            new VarChar2Type().getTypeName(), col3Type.getTypeName());
        assertTrue("incorrect size for column [" + CONSTRAINTS_TABLE_FIELD3 + "]",
            ((SizedType)col3Type).getSize() == 25);
    }
}
