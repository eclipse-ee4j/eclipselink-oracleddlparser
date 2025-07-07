/*
 * Copyright (c) 2011, 2025 Oracle and/or its affiliates. All rights reserved.
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
import org.eclipse.persistence.tools.oracleddl.metadata.ScalarDatabaseTypeEnum;
import org.eclipse.persistence.tools.oracleddl.metadata.SizedType;
import org.eclipse.persistence.tools.oracleddl.metadata.TableType;
import org.eclipse.persistence.tools.oracleddl.metadata.VarChar2Type;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypeBuilder;

//testing imports
import org.eclipse.persistence.tools.oracleddl.test.AllTests;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_CREATE_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_DEBUG_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_DROP_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_USERNAME_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_CREATE;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_DEBUG;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_DROP;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_USERNAME;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.buildConnection;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.runDdl;

public class TableDDLTestSuite {

    static final String SIMPLETABLE = "DTB_SIMPLETABLE";
    static final String SIMPLETABLE_FIELD1 =
        "ID";
    static final String SIMPLETABLE_FIELD2 =
        "NAME";
    static final String SIMPLETABLE_FIELD3 =
        "SINCE";
    static final String CREATE_SIMPLETABLE =
        "CREATE TABLE " + SIMPLETABLE + " (\n" +
            SIMPLETABLE_FIELD1 + " INTEGER NOT NULL,\n" +
            SIMPLETABLE_FIELD2 + " VARCHAR2(25),\n" +
            SIMPLETABLE_FIELD3 + " DATE,\n" +
            "PRIMARY KEY (" + SIMPLETABLE_FIELD1 + "," + SIMPLETABLE_FIELD2 + ")\n" +
        ")";
    static final String DROP_SIMPLETABLE =
        "DROP TABLE " + SIMPLETABLE;

    //fixtures
    static DatabaseTypeBuilder dtBuilder = DatabaseTypeBuilderTestSuite.dtBuilder;
    static Connection conn = AllTests.conn;
    static TableType tableType = null;
    static List<String> expectedFieldNames = new ArrayList<>();
    static List<String> expectedPKFieldNames = new ArrayList<>();

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
            runDdl(conn, CREATE_SIMPLETABLE, ddlDebug);
        }
        boolean worked = true;
        String msg = null;
        try {
            String schema = System.getProperty(DATABASE_USERNAME_KEY, DEFAULT_DATABASE_USERNAME);
            tableType = dtBuilder.buildTables(conn, schema, SIMPLETABLE).get(0);
        }
        catch (Exception e) {
            worked = false;
            msg = e.getMessage();
        }
        if (!worked) {
            fail(msg);
        }
        expectedPKFieldNames.add(SIMPLETABLE_FIELD1);
        expectedPKFieldNames.add(SIMPLETABLE_FIELD2);
        expectedFieldNames.add(SIMPLETABLE_FIELD1);
        expectedFieldNames.add(SIMPLETABLE_FIELD2);
        expectedFieldNames.add(SIMPLETABLE_FIELD3);
    }

    @AfterClass
    public static void tearDown() {
        if (ddlDrop) {
            runDdl(conn, DROP_SIMPLETABLE, ddlDebug);
        }
    }

    @Test
    public void testTableName() {
        assertEquals("incorrect table name", SIMPLETABLE , tableType.getTableName());
    }

    @Test
    public void testNumberOfColumns() {
        List<FieldType> columns = tableType.getColumns();
        assertEquals("incorrect number of columns", 3, columns.size());
    }

    @Test
    public void testPrimaryKeys() {
        List<FieldType> columns = tableType.getColumns();
        List<String> pkFieldNames = new ArrayList<>();
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
        List<String> fieldNames = new ArrayList<>();
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
        assertEquals("incorrect type for column [" + SIMPLETABLE_FIELD1 + "]",
            ScalarDatabaseTypeEnum.INTEGER_TYPE.getTypeName(), col1Type.getTypeName());
        assertTrue("incorrect NULL constraint for column [" + SIMPLETABLE_FIELD1 + "]",
            field1.notNull());

        FieldType field2 = columns.get(1);
        DatabaseType col2Type = field2.getEnclosedType();
        assertEquals("incorrect type for column [" + SIMPLETABLE_FIELD2 + "]",
            new VarChar2Type().getTypeName(), col2Type.getTypeName());
        assertFalse("incorrect NULL constraint for column [" + SIMPLETABLE_FIELD2 + "]",
            field2.notNull());
        assertEquals("incorrect size for column [" + SIMPLETABLE_FIELD2 + "]", 25, ((SizedType) col2Type).getSize());

        FieldType field3 = columns.get(2);
        DatabaseType col3Type = field3.getEnclosedType();
        assertEquals("incorrect type for column [" + SIMPLETABLE_FIELD3 + "]",
            ScalarDatabaseTypeEnum.DATE_TYPE.getTypeName(), col3Type.getTypeName());
        assertFalse("incorrect NULL constraint for column [" + SIMPLETABLE_FIELD3 + "]",
            field3.notNull());
    }
}
