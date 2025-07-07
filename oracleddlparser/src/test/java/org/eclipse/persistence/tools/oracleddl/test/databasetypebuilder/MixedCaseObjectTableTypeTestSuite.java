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

//JUnit4 imports
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.ObjectTableType;
import org.eclipse.persistence.tools.oracleddl.test.AllTests;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypeBuilder;

//testing imports
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_CREATE_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_DEBUG_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_DROP_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_CREATE;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_DEBUG;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_DROP;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.buildConnection;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.runDdl;

public class MixedCaseObjectTableTypeTestSuite {

    static final String MIXEDCASE_TYPE1 = "mixedCase1";
    static final String CREATE_MIXEDCASE_TYPE1 =
        "CREATE OR REPLACE TYPE " + MIXEDCASE_TYPE1 + " AS OBJECT (" +
            "\n\tempno  NUMBER(4)," +
            "\n\tename  VARCHAR2(10)" +
        ")";
    static final String MIXEDCASE_TYPE2 = "MIXEDCASE1TABLE";
    static final String CREATE_MIXEDCASE_TYPE2 =
        "CREATE OR REPLACE TYPE " + MIXEDCASE_TYPE2 + " as table of " + MIXEDCASE_TYPE1;
    static final String DROP_MIXEDCASE_TYPE1 =
        "DROP TYPE " + MIXEDCASE_TYPE1;
    static final String DROP_MIXEDCASE_TYPE2 =
        "DROP TYPE " + MIXEDCASE_TYPE2;

    //fixtures
    static DatabaseTypeBuilder dtBuilder = DatabaseTypeBuilderTestSuite.dtBuilder;
    static Connection conn = AllTests.conn;
    static ObjectTableType objectTableType = null;
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
            runDdl(conn, CREATE_MIXEDCASE_TYPE1, ddlDebug);
            runDdl(conn, CREATE_MIXEDCASE_TYPE2, ddlDebug);
        }
        dtBuilder = new DatabaseTypeBuilder();
        boolean worked = true;
        String msg = null;
        try {
            objectTableType = (ObjectTableType)dtBuilder.buildTypes(conn, null, MIXEDCASE_TYPE2).get(0);
        }
        catch (Exception e) {
            worked = false;
            msg = e.getMessage();
        }
        if (!worked) {
            fail(msg);
        }
    }

    @AfterClass
    static public void tearDown() {
        if (ddlDrop) {
            runDdl(conn, DROP_MIXEDCASE_TYPE2, ddlDebug);
            runDdl(conn, DROP_MIXEDCASE_TYPE1, ddlDebug);
        }
    }

    @Test
    public void testMixedCase() {
        assertEquals("incorrect object type name", MIXEDCASE_TYPE2, objectTableType.getTypeName());
    }

}
