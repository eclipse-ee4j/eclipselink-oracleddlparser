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
package org.eclipse.persistence.tools.oracleddl.test.databasetypebuilder;

//javase imports
import java.sql.Connection;
import java.util.List;

//JUnit4 imports
import org.junit.AfterClass;
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.ArgumentType;
import org.eclipse.persistence.tools.oracleddl.metadata.DatabaseType;
import org.eclipse.persistence.tools.oracleddl.metadata.FunctionType;
import org.eclipse.persistence.tools.oracleddl.metadata.ObjectType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLPackageType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLRecordType;
import org.eclipse.persistence.tools.oracleddl.metadata.ProcedureType;
import org.eclipse.persistence.tools.oracleddl.metadata.TableType;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.UnresolvedTypesVisitor;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;
import org.eclipse.persistence.tools.oracleddl.test.AllTests;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypeBuilder;

//testing imports
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_CREATE_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_CREATE;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_DEBUG_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_DEBUG;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_DDL_DROP_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_DDL_DROP;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.buildConnection;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.runDdl;

public class TypeResolutionTestSuite {

    static final String CREATE_DDLRESOLVTEST_EMP_TABLE =
        "CREATE TABLE DDLRESOLVTEST_EMP (" +
            "\nEMPNO NUMERIC(4)," +
            "\nENAME VARCHAR(10)," +
            "\nJOB VARCHAR(9)," +
            "\nMGR NUMERIC(4)," +
            "\nHIREDATE DATE," +
            "\nSAL DECIMAL(7,2)," +
            "\nCOMM NUMERIC(7,2)," +
            "\nDEPTNO NUMERIC(2)," +
            "\nPRIMARY KEY (EMPNO)" +
        "\n)";
    static final String CREATE_DDLRESOLVTEST_TYPE1 =
        "CREATE OR REPLACE TYPE DDLRESOLVTEST_TYPE1 AS OBJECT (" +
            "\n\tACCT\tNUMBER," +
            "\n\tCOUNTRY\tVARCHAR2(30)," +
            "\n\tADDR_DIVISION\tVARCHAR2(30)," +
            "\n\tSTATE\tVARCHAR2(30)" +
        ")";
    static final String CREATE_DDLRESOLVTEST_TYPE2 =
        "CREATE OR REPLACE TYPE DDLRESOLVTEST_TYPE2 AS OBJECT (" +
            "\n\tPNR\tNUMBER," +
            "\n\tCOMPANY\tVARCHAR2(30)," +
            "\n\tSE\tVARCHAR2(30)," +
            "\n\tSCRIP\tVARCHAR2(30)," +
            "\n\tTT\tDDLRESOLVTEST_TYPE1" +
        ")";
    static final String CREATE_DDLRESOLVTEST_TYPE3 =
        "CREATE OR REPLACE TYPE DDLRESOLVTEST_TYPE3 AS VARRAY(2) OF DDLRESOLVTEST_TYPE2";
    static final String CREATE_DDLRESOLVTEST_TYPE4 =
        "CREATE OR REPLACE TYPE DDLRESOLVTEST_TYPE4 AS OBJECT (" +
            "\n\tACCT\tNUMBER," +
            "\n\tSTATE\tVARCHAR2(30)," +
            "\nCONSTRUCTOR FUNCTION DDLRESOLVTEST_TYPE4(" +
            "\n\tACCT\tNUMBER," +
            "\n\tSTATE\tVARCHAR2) RETURN SELF AS RESULT" +
        ")";
    static final String CREATE_DDLRESOLVTEST_TYPE4_BODY =
        "CREATE OR REPLACE TYPE BODY DDLRESOLVTEST_TYPE4 AS " +
            "\nCONSTRUCTOR FUNCTION DDLRESOLVTEST_TYPE4(" +
            "\n\tACCT\tNUMBER," +
            "\n\tSTATE\tVARCHAR2) RETURN SELF AS RESULT IS" +
            "\n\tBEGIN" +
            "\n\t\tSELF.ACCT := ACCT;" +
            "\n\t\tSELF.STATE   := STATE;" +
            "\n\t\tRETURN;" +
            "\n\tEND;" +
            "\nEND;";
    static final String DDLRESOLVTEST_TABLE1 = "DDLRESOLVTEST_TABLE1";
    static final String CREATE_DDLRESOLVTEST_TABLE1 =
        "CREATE TABLE " + DDLRESOLVTEST_TABLE1 + " (" +
            "\n\tIDE\tNUMBER," +
            "\n\tIDTT\tDDLRESOLVTEST_TYPE1" +
        "\n)";
    static final String CREATE_DDLRESOLVTEST_TABLE2 =
        "CREATE TABLE DDLRESOLVTEST_TABLE2 (" +
            "\n\tSRNO\tNUMBER," +
            "\n\tDETAIL\tDDLRESOLVTEST_TYPE2" +
        "\n)";
    static final String CREATE_DDLRESOLVTEST_TABLE3 =
        "CREATE TABLE DDLRESOLVTEST_TABLE3 (" +
            "\n\tID\tINTEGER," +
            "\n\tTT3\tDDLRESOLVTEST_TYPE3" +
        "\n)";
    static final String CREATE_DDLRESOLVTEST_REGION =
        "CREATE OR REPLACE TYPE DDLRESOLVTEST_REGION AS OBJECT (" +
            "\n\tREG_ID\tNUMBER(5)," +
            "\n\tREG_NAME\tVARCHAR2(50)" +
        "\n)";
    static final String CREATE_DDLRESOLVTEST_EMP_ADDRESS =
        "CREATE OR REPLACE TYPE DDLRESOLVTEST_EMP_ADDRESS AS OBJECT (" +
            "\n\tSTREET\tVARCHAR2(100)," +
            "\n\tSUBURB\tVARCHAR2(50)," +
            "\n\tADDR_REGION\tDDLRESOLVTEST_REGION," +
            "\n\tPOSTCODE\tINTEGER" +
         "\n)";
    static final String EMP_OBJECT_TYPE = "DDLRESOLVTEST_EMP_OBJECT";
    static final String CREATE_EMP_OBJECT =
        "CREATE OR REPLACE TYPE " + EMP_OBJECT_TYPE + " AS OBJECT (" +
            "\n\tEMPLOYEE_ID\tNUMBER(8)," +
            "\n\tADDRESS\tDDLRESOLVTEST_EMP_ADDRESS," +
            "\n\tEMPLOYEE_NAME\tVARCHAR2(80)," +
            "\n\tDATE_OF_HIRE\tDATE" +
        "\n)";
    static final String DDLRESOLVTEST_PACKAGE = "DDLRESOLVTEST_PACKAGE";
    static final String CREATE_DDLRESOLVTEST_PACKAGE =
        "CREATE OR REPLACE PACKAGE " + DDLRESOLVTEST_PACKAGE + " AS" +
            "\n\tTYPE EMPREC IS RECORD ( " +
                "\n\tEMPNO DDLRESOLVTEST_EMP.EMPNO%TYPE," +
                "\n\tENAME DDLRESOLVTEST_EMP.ENAME%TYPE," +
                "\n\tJOB DDLRESOLVTEST_EMP.JOB%TYPE," +
                "\n\tMGR DDLRESOLVTEST_EMP.MGR%TYPE," +
                "\n\tHIREDATE DDLRESOLVTEST_EMP.HIREDATE%TYPE," +
                "\n\tSAL DDLRESOLVTEST_EMP.SAL%TYPE," +
                "\n\tCOMM DDLRESOLVTEST_EMP.COMM%TYPE," +
                "\n\tDEPTNO DDLRESOLVTEST_EMP.DEPTNO%TYPE" +
            "\n\t);" +
            "\n\tFUNCTION DDLRESOLVTESTFUNC17(PARAM1 IN INTEGER) RETURN DDLRESOLVTEST_TABLE2%ROWTYPE;" +
            "\n\tPROCEDURE DDLRESOLVTESTPROC17(PARAM1 IN INTEGER, REC OUT DDLRESOLVTEST_TABLE2%ROWTYPE);" +
            "\n\tPROCEDURE DDLRESOLVTESTPROC17b(OLDREC IN DDLRESOLVTEST_TABLE3%ROWTYPE, NEWREC OUT DDLRESOLVTEST_TABLE3%ROWTYPE);" +
            "\n\tPROCEDURE EMP_TEST(E1 IN EMPREC, NAME IN VARCHAR2);" +
            "\n\tPROCEDURE EMP_TEST2(NAME IN DDLRESOLVTEST_EMP.ENAME%TYPE);" +
            "\n\tFUNCTION ECHOREGION(AREGION IN DDLRESOLVTEST_REGION) RETURN DDLRESOLVTEST_REGION;" +
            "\n\tFUNCTION ECHOEMPADDRESS(ANEMPADDRESS IN DDLRESOLVTEST_EMP_ADDRESS) RETURN DDLRESOLVTEST_EMP_ADDRESS;" +
            "\n\tFUNCTION ECHOEMPOBJECT(ANEMPOBJECT IN DDLRESOLVTEST_EMP_OBJECT) RETURN DDLRESOLVTEST_EMP_OBJECT;" +
        "\nEND " + DDLRESOLVTEST_PACKAGE + ";";
    static final String CREATE_DDLRESOLVTEST_PACKAGE_BODY =
        "CREATE OR REPLACE PACKAGE BODY " + DDLRESOLVTEST_PACKAGE + " AS" +
            "\n\tFUNCTION DDLRESOLVTESTFUNC17(PARAM1 IN INTEGER) RETURN DDLRESOLVTEST_TABLE2%ROWTYPE AS" +
                "\n\tL_DATA1 DDLRESOLVTEST_TABLE2%ROWTYPE;" +
                "\n\tCURSOR C_EMP(PARAMTEMP IN INTEGER) IS SELECT * FROM DDLRESOLVTEST_TABLE2 TE WHERE TE.SRNO=PARAMTEMP;" +
            "\n\tBEGIN" +
                "\n\tOPEN C_EMP(PARAM1);" +
                "\n\tLOOP" +
                    "\n\tFETCH C_EMP INTO L_DATA1;" +
                    "\n\tEXIT WHEN C_EMP%NOTFOUND;" +
                "\n\tEND LOOP;" +
                "\n\tRETURN L_DATA1;" +
            "\n\tEND DDLRESOLVTESTFUNC17;" +
            "\n\tPROCEDURE DDLRESOLVTESTPROC17( PARAM1 IN INTEGER, REC OUT DDLRESOLVTEST_TABLE2%ROWTYPE) AS" +
            "\n\tBEGIN" +
                "\n\tREC := DDLRESOLVTESTFUNC17(PARAM1);" +
            "\n\tEND DDLRESOLVTESTPROC17;" +
            "\n\tPROCEDURE DDLRESOLVTESTPROC17b(OLDREC IN DDLRESOLVTEST_TABLE3%ROWTYPE, NEWREC OUT DDLRESOLVTEST_TABLE3%ROWTYPE) AS" +
            "\n\tBEGIN" +
                "\n\tNEWREC := OLDREC;" +
            "\n\tEND DDLRESOLVTESTPROC17b;" +
            "\n\tPROCEDURE EMP_TEST(E1 IN EMPREC, NAME IN VARCHAR2) AS" +
            "\n\tBEGIN" +
                "\n\tnull;" +
            "\n\tEND EMP_TEST;" +
            "\n\tPROCEDURE EMP_TEST2(NAME IN DDLRESOLVTEST_EMP.ENAME%TYPE) AS" +
            "\n\tBEGIN" +
                "\n\tnull;" +
            "\n\tEND EMP_TEST2;" +
            "\n\tFUNCTION ECHOREGION(AREGION IN DDLRESOLVTEST_REGION) RETURN DDLRESOLVTEST_REGION AS" +
            "\n\tBEGIN" +
                "\n\tRETURN AREGION;" +
            "\n\tEND ECHOREGION;" +
            "\n\tFUNCTION ECHOEMPADDRESS(ANEMPADDRESS IN DDLRESOLVTEST_EMP_ADDRESS) RETURN DDLRESOLVTEST_EMP_ADDRESS AS" +
            "\n\tBEGIN" +
                "\n\tRETURN ANEMPADDRESS;" +
            "\n\tEND ECHOEMPADDRESS;" +
            "\n\tFUNCTION ECHOEMPOBJECT(ANEMPOBJECT IN DDLRESOLVTEST_EMP_OBJECT) RETURN DDLRESOLVTEST_EMP_OBJECT AS" +
            "\n\tBEGIN" +
                "\n\tRETURN ANEMPOBJECT;" +
            "\n\tEND ECHOEMPOBJECT;" +
      "\nEND " + DDLRESOLVTEST_PACKAGE + ";";
    static final String OTHER_PACKAGE = "DDLRESOLVTEST_PACKAGE2";
    static final String CREATE_OTHER_PACKAGE =
        "CREATE OR REPLACE PACKAGE " + OTHER_PACKAGE + " AS" +
            "\n\tPROCEDURE SOMEPROC(E1 IN " + DDLRESOLVTEST_PACKAGE + "." + "EMPREC);" +
            "\n\tFUNCTION GET_JOB(p_job_id IN VARCHAR2) RETURN DDLRESOLVTEST_TYPE4;" +
        "\nEND " + OTHER_PACKAGE + ";";
    static final String CREATE_OTHER_PACKAGE_BODY =
        "CREATE OR REPLACE PACKAGE BODY " + OTHER_PACKAGE + " AS" +
            "\n\tPROCEDURE SOMEPROC(E1 IN " + DDLRESOLVTEST_PACKAGE + "." + "EMPREC) AS" +
            "\n\tBEGIN" +
                "\n\tnull;" +
            "\n\tEND SOMEPROC;" +
            "\n\tFUNCTION GET_JOB(p_job_id IN VARCHAR2) RETURN DDLRESOLVTEST_TYPE4 IS" +
            "\n\tresult DDLRESOLVTEST_TYPE4;" +
            "\n\tACCT\tNUMBER;" +
            "\n\tSTATE\tVARCHAR2(30);" +
            "\n\tBEGIN" +
            "\n\t\tRETURN NULL;" +
            "\n\tEND GET_JOB;" +
        "\nEND " + OTHER_PACKAGE + ";";

    static final String DROP_OTHER_PACKAGE =
        "DROP PACKAGE " + OTHER_PACKAGE;
    static final String DROP_DDLRESOLVTEST_PACKAGE =
        "DROP PACKAGE " + DDLRESOLVTEST_PACKAGE;
    static final String DROP_EMP_OBJECT = "DROP TYPE " + EMP_OBJECT_TYPE;
    static final String DROP_DDLRESOLVTEST_EMP_ADDRESS = "DROP TYPE DDLRESOLVTEST_EMP_ADDRESS";
    static final String DROP_DDLRESOLVTEST_REGION = "DROP TYPE DDLRESOLVTEST_REGION";
    static final String DROP_DDLRESOLVTEST_TABLE3 = "DROP TABLE DDLRESOLVTEST_TABLE3";
    static final String DROP_DDLRESOLVTEST_TABLE2 = "DROP TABLE DDLRESOLVTEST_TABLE2";
    static final String DROP_DDLRESOLVTEST_TABLE1 = "DROP TABLE DDLRESOLVTEST_TABLE1";
    static final String DROP_DDLRESOLVTEST_EMP_TABLE = "DROP TABLE DDLRESOLVTEST_EMP";
    static final String DROP_DDLRESOLVTEST_TYPE4 = "DROP TYPE DDLRESOLVTEST_TYPE4";
    static final String DROP_DDLRESOLVTEST_TYPE3 = "DROP TYPE DDLRESOLVTEST_TYPE3";
    static final String DROP_DDLRESOLVTEST_TYPE2 = "DROP TYPE DDLRESOLVTEST_TYPE2";
    static final String DROP_DDLRESOLVTEST_TYPE1 = "DROP TYPE DDLRESOLVTEST_TYPE1";

    //JUnit fixture(s)
    static DatabaseTypeBuilder dtBuilder = DatabaseTypeBuilderTestSuite.dtBuilder;
    static Connection conn = AllTests.conn;
    static PLSQLPackageType ddlresolvtestPackage = null;

    static boolean ddlCreate = false;
    static boolean ddlDrop = false;
    static boolean ddlDebug = false;

    @BeforeClass
    static public void setUp() {
        try {
            conn = buildConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
            runDdl(conn, CREATE_DDLRESOLVTEST_EMP_TABLE, ddlDebug);
            runDdl(conn, CREATE_DDLRESOLVTEST_TYPE1, ddlDebug);
            runDdl(conn, CREATE_DDLRESOLVTEST_TYPE2, ddlDebug);
            runDdl(conn, CREATE_DDLRESOLVTEST_TYPE3, ddlDebug);
            runDdl(conn, CREATE_DDLRESOLVTEST_TYPE4, ddlDebug);
            runDdl(conn, CREATE_DDLRESOLVTEST_TYPE4_BODY, ddlDebug);
            runDdl(conn, CREATE_DDLRESOLVTEST_TABLE1, ddlDebug);
            runDdl(conn, CREATE_DDLRESOLVTEST_TABLE2, ddlDebug);
            runDdl(conn, CREATE_DDLRESOLVTEST_TABLE3, ddlDebug);
            runDdl(conn, CREATE_DDLRESOLVTEST_REGION, ddlDebug);
            runDdl(conn, CREATE_DDLRESOLVTEST_EMP_ADDRESS, ddlDebug);
            runDdl(conn, CREATE_EMP_OBJECT, ddlDebug);
            runDdl(conn, CREATE_DDLRESOLVTEST_PACKAGE, ddlDebug);
            runDdl(conn, CREATE_DDLRESOLVTEST_PACKAGE_BODY, ddlDebug);
            runDdl(conn, CREATE_OTHER_PACKAGE, ddlDebug);
            runDdl(conn, CREATE_OTHER_PACKAGE_BODY, ddlDebug);
        }
        dtBuilder = new DatabaseTypeBuilder();
        boolean worked = true;
        String msg = null;
        try {
            ddlresolvtestPackage = dtBuilder.buildPackages(conn, null, DDLRESOLVTEST_PACKAGE).get(0);
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
            runDdl(conn, DROP_OTHER_PACKAGE, ddlDebug);
            runDdl(conn, DROP_DDLRESOLVTEST_PACKAGE, ddlDebug);
            runDdl(conn, DROP_EMP_OBJECT, ddlDebug);
            runDdl(conn, DROP_DDLRESOLVTEST_EMP_ADDRESS, ddlDebug);
            runDdl(conn, DROP_DDLRESOLVTEST_REGION, ddlDebug);
            runDdl(conn, DROP_DDLRESOLVTEST_TABLE3, ddlDebug);
            runDdl(conn, DROP_DDLRESOLVTEST_TABLE2, ddlDebug);
            runDdl(conn, DROP_DDLRESOLVTEST_TABLE1, ddlDebug);
            runDdl(conn, DROP_DDLRESOLVTEST_EMP_TABLE, ddlDebug);
            runDdl(conn, DROP_DDLRESOLVTEST_TYPE4, ddlDebug);
            runDdl(conn, DROP_DDLRESOLVTEST_TYPE3, ddlDebug);
            runDdl(conn, DROP_DDLRESOLVTEST_TYPE2, ddlDebug);
            runDdl(conn, DROP_DDLRESOLVTEST_TYPE1, ddlDebug);
        }

    }

    @Test
    public void testUnresolvedTypeResolution() throws ParseException {
        assertEquals("incorrect procedure name", DDLRESOLVTEST_PACKAGE , ddlresolvtestPackage.getPackageName());
        UnresolvedTypesVisitor visitor = new UnresolvedTypesVisitor();
        visitor.visit(ddlresolvtestPackage);
        assertEquals(DDLRESOLVTEST_PACKAGE + " should not have any unresolved types",
            0, visitor.getUnresolvedTypes().size());
    }

    @Test
    public void testSame_DDLRESOLVTEST_TABLE2_ROWTYPE() {
        FunctionType func1 = (FunctionType)ddlresolvtestPackage.getProcedures().get(0);
        DatabaseType tesmanfunc17ReturnType = func1.getReturnArgument().getEnclosedType();
        ProcedureType proc2 = ddlresolvtestPackage.getProcedures().get(1);
        DatabaseType tesmanproc17OutArgType = proc2.getArguments().get(1).getEnclosedType();
        assertSame(tesmanfunc17ReturnType, tesmanproc17OutArgType);
    }

    @Test
    public void testSame_DDLRESOLVTEST_TABLE3_ROWTYPE() {
        ProcedureType proc3 = ddlresolvtestPackage.getProcedures().get(2);
        List<ArgumentType> proc3Args = proc3.getArguments();
        DatabaseType oldrecDatabaseType = proc3Args.get(0).getEnclosedType();
        DatabaseType newrecDatabaseType = proc3Args.get(1).getEnclosedType();
        assertSame(oldrecDatabaseType, newrecDatabaseType);
    }

    @Test
    public void testSame_EMPREC() {
        PLSQLRecordType empRecType = (PLSQLRecordType)ddlresolvtestPackage.getTypes().get(0);
        PLSQLRecordType empRecType2 = (PLSQLRecordType)ddlresolvtestPackage.getProcedures().get(3).
            getArguments().get(0).getEnclosedType();
        assertSame(empRecType, empRecType2);
    }

    @Test
    public void testSame_EMPdotEMPNO_TYPE() {
        PLSQLRecordType empRecType = (PLSQLRecordType)ddlresolvtestPackage.getTypes().get(0);
        DatabaseType empDotEnamePcentTYPE1 = empRecType.getFields().get(1).getEnclosedType();
        ArgumentType nameArg = ddlresolvtestPackage.getProcedures().get(4).getArguments().get(0);
        DatabaseType empDotEnamePcentTYPE2 = nameArg.getEnclosedType();
        assertSame(empDotEnamePcentTYPE1, empDotEnamePcentTYPE2);
    }

    @Test
    public void testPackageRefersToGlobalTypes() {
        FunctionType echoRegionProc = (FunctionType)ddlresolvtestPackage.getProcedures().get(5);
        ArgumentType aRegion = echoRegionProc.getArguments().get(0);
        ArgumentType returnRegion = echoRegionProc.getReturnArgument();
        assertSame(aRegion.getEnclosedType(), returnRegion.getEnclosedType());
    }

    @Test
    public void testObjectTypeRefersToGlobalTypes() {
        boolean worked = true;
        String msg = null;
        ObjectType objectType = null;
        try {
            objectType = (ObjectType)dtBuilder.buildTypes(conn, null, EMP_OBJECT_TYPE).get(0);
        }
        catch (Exception e) {
            worked = false;
            msg = e.getMessage();
        }
        assertTrue(msg,worked);
        UnresolvedTypesVisitor visitor = new UnresolvedTypesVisitor();
        visitor.visit(objectType);
        assertEquals(EMP_OBJECT_TYPE + " should not have any unresolved types",
            0, visitor.getUnresolvedTypes().size());
    }

    @Test
    public void testTableTypeRefersToGlobalTypes() {
        boolean worked = true;
        String msg = null;
        TableType tableType = null;
        try {
            tableType = dtBuilder.buildTables(conn, null, DDLRESOLVTEST_TABLE1).get(0);
        }
        catch (Exception e) {
            worked = false;
            msg = e.getMessage();
        }
        assertTrue(msg,worked);
        UnresolvedTypesVisitor visitor = new UnresolvedTypesVisitor();
        visitor.visit(tableType);
        assertEquals(DDLRESOLVTEST_TABLE1 + " should not have any unresolved types",
            0, visitor.getUnresolvedTypes().size());
    }

    @Test
    public void testPLSQLRecordTypeRefersToDifferentPackage() {
        boolean worked = true;
        String msg = null;
        PLSQLPackageType otherPackage = null;
        try {
            otherPackage = dtBuilder.buildPackages(conn, null, OTHER_PACKAGE).get(0);
        }
        catch (Exception e) {
            worked = false;
            msg = e.getMessage();
        }
        assertTrue(msg,worked);
        UnresolvedTypesVisitor visitor = new UnresolvedTypesVisitor();
        visitor.visit(otherPackage);
        assertEquals(OTHER_PACKAGE + " should not have any unresolved types",
            0, visitor.getUnresolvedTypes().size());
    }
}
