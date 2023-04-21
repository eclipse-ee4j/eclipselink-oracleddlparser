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
import java.util.List;

//JUnit4 imports
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.ArgumentType;
import org.eclipse.persistence.tools.oracleddl.metadata.ProcedureType;
import org.eclipse.persistence.tools.oracleddl.metadata.VarCharType;
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

public class ProcedureDDLTestSuite {

    static final String SIMPLEPROC = "DTB_SIMPLEPROC";
    static final String CREATE_SIMPLEPROC =
        "CREATE PROCEDURE " + SIMPLEPROC + "(X IN VARCHAR) AS\n" +
        "BEGIN\n" +
            "null;\n" +
        "END;";
    static final String DROP_SIMPLEPROC =
        "DROP PROCEDURE " + SIMPLEPROC;

    //fixtures
    static DatabaseTypeBuilder dtBuilder = DatabaseTypeBuilderTestSuite.dtBuilder;
    static Connection conn = AllTests.conn;
    static ProcedureType procedureType = null;

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
            runDdl(conn, CREATE_SIMPLEPROC, ddlDebug);
        }
        boolean worked = true;
        String msg = null;
        try {
            String schema = System.getProperty(DATABASE_USERNAME_KEY, DEFAULT_DATABASE_USERNAME);
            procedureType = dtBuilder.buildProcedures(conn, schema, SIMPLEPROC).get(0);
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
    public static void tearDown() {
        if (ddlDrop) {
            runDdl(conn, DROP_SIMPLEPROC, ddlDebug);
        }
    }

    @Test
    public void testProcedureName() {
        assertEquals("incorrect procedure name", SIMPLEPROC , procedureType.getProcedureName());
    }

    @Test
    public void testNumberOfArgs() {
        List<ArgumentType> args = procedureType.getArguments();
        assertTrue("incorrect number of arguments", args.size() ==  1);
    }

    @Test
    public void testArgumentNames() {
        List<ArgumentType> args = procedureType.getArguments();
        ArgumentType argFirst = args.get(0);
        assertEquals("incorrect argument name", "X" , argFirst.getArgumentName());
    }

    @Test
    public void testArgumentTypes() {
        List<ArgumentType> args = procedureType.getArguments();
        ArgumentType argFirst = args.get(0);
        assertEquals("incorrect type for args  " + argFirst.getArgumentName(),
            new VarCharType().getTypeName(), argFirst.getEnclosedType().getTypeName());
    }
}
