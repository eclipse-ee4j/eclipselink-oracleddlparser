/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Mike Norman - Feb 1 2012, created anchored types (%TYPE, %ROWTYPE) tests
 ******************************************************************************/
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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypeBuilder;

//testing imports
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLCursorType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLPackageType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLType;
import org.eclipse.persistence.tools.oracleddl.test.AllTests;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_CREATE_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_DEBUG_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_DROP_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_CREATE;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_DEBUG;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_DROP;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.buildConnection;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.runDdl;

public class AnchoredTypesResolutionTestSuite {

    static final String CREATE_TABLE_PREFIX = "CREATE TABLE ";
    static final String CREATE_PACKAGE_PREFIX = "CREATE PACKAGE ";
    static final String ANCHORED_TYPES_TEST4_PACKAGE = "ANCHORED_TYPES_TEST4";
    static final String ANCHORED_TYPES_TABLE2 = "ANCHORED_TYPES_TABLE2";
    static final String CREATE_ANCHORED_TYPES_TABLE2 =
        CREATE_TABLE_PREFIX + ANCHORED_TYPES_TABLE2 + " (" +
            "\nID NUMBER NOT NULL," +
            "\nNAME VARCHAR2(80)," +
            "\nSINCE DATE," +
            "\nPRIMARY KEY (ID)" +
        "\n)";
    static final String ANCHORED_TYPES_TABLE_FIELD = "NAME";
    static final String ANCHORED_TYPE1_NAME = "W" + ANCHORED_TYPES_TABLE_FIELD;
    static final String ANCHORED_TYPE1 =
        ANCHORED_TYPES_TABLE2 + "." + ANCHORED_TYPES_TABLE_FIELD + "%TYPE";
    static final String ANCHORED_TYPE2_NAME = "ANCHORED_TYPE2";
    static final String ANCHORED_TYPE2 = ANCHORED_TYPES_TABLE2 + "%ROWTYPE";
    static final String ANCHORED_TYPE3_NAME = "ANCHORED_CURSOR";
    static final String CREATE_ANCHORED_TYPES_TEST1_PACKAGE =
        CREATE_PACKAGE_PREFIX + ANCHORED_TYPES_TEST4_PACKAGE + " AS" +
            "\nSUBTYPE " + ANCHORED_TYPE1_NAME + " IS " + ANCHORED_TYPE1 + ";" +
            "\nSUBTYPE " + ANCHORED_TYPE2_NAME + " IS " + ANCHORED_TYPE2 + ";" +
            "\nTYPE " + ANCHORED_TYPE3_NAME + " IS REF CURSOR RETURN " + ANCHORED_TYPE2_NAME + ";" +
        "\nEND " + ANCHORED_TYPES_TEST4_PACKAGE + ";";

    static final String DROP_ANCHORED_TYPES_TABLE2 =
        "DROP TABLE " + ANCHORED_TYPES_TABLE2;
    static final String DROP_ANCHORED_TYPES_TEST1_PACKAGE =
        "DROP PACKAGE " + ANCHORED_TYPES_TEST4_PACKAGE;

    //JUnit fixture(s)
    static DatabaseTypeBuilder dtBuilder = DatabaseTypeBuilderTestSuite.dtBuilder;
    static Connection conn = AllTests.conn;
    static List<String> expectedFieldNames = new ArrayList<String>();
    static List<String> expectedPKFieldNames = new ArrayList<String>();

    static boolean ddlCreate = false;
    static boolean ddlDrop = false;
    static boolean ddlDebug = false;

    @BeforeClass
    static public void setUp() throws ClassNotFoundException, SQLException {
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
            runDdl(conn, CREATE_ANCHORED_TYPES_TABLE2, ddlDebug);
            runDdl(conn, CREATE_ANCHORED_TYPES_TEST1_PACKAGE, ddlDebug);
        }
    }

    @AfterClass
    static public void tearDown() {
        if (ddlDrop) {
            runDdl(conn, DROP_ANCHORED_TYPES_TABLE2, ddlDebug);
            runDdl(conn, DROP_ANCHORED_TYPES_TEST1_PACKAGE, ddlDebug);
        }
    }

    @Test
    public void anchoredTypesTest1() {
        boolean worked = true;
        PLSQLPackageType plsqlPackageType = null;
        try {
            plsqlPackageType = dtBuilder.buildPackages(conn, "", ANCHORED_TYPES_TEST4_PACKAGE).get(0);
        }
        catch (Exception e) {
            worked = false;
        }
        assertTrue(ANCHORED_TYPES_TEST4_PACKAGE + " should parse", worked);
        PLSQLType plsqlType1 = plsqlPackageType.getTypes().get(0);
        assertTrue(ANCHORED_TYPE1_NAME+ "'s enclosedType is supposed to be resolved",
            plsqlType1.isResolved());
        PLSQLType plsqlType2 = plsqlPackageType.getTypes().get(1);
        assertTrue(ANCHORED_TYPE2_NAME + "'s enclosedType is supposed to be resolved",
            plsqlType2.isResolved());
        PLSQLCursorType cursorType = plsqlPackageType.getCursors().get(0);
        assertTrue(ANCHORED_TYPE3_NAME + " is supposed to be resolved",
            cursorType.isResolved());
        assertSame(ANCHORED_TYPE2_NAME + " is supposed to be identical to " +
            ANCHORED_TYPE3_NAME + "'s return type", plsqlType2, cursorType.getDataType());
    }

}