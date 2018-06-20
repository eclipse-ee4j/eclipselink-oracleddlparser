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
import java.io.StringReader;
import java.util.ArrayList;

//JUnit4 imports
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.ArgumentType;
import org.eclipse.persistence.tools.oracleddl.metadata.DatabaseType;
import org.eclipse.persistence.tools.oracleddl.metadata.FunctionType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLPackageType;
import org.eclipse.persistence.tools.oracleddl.metadata.ProcedureType;
import org.eclipse.persistence.tools.oracleddl.parser.DDLParser;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypesRepository;

public class ExtraSchemaNameDDLTestSuite {

    static final String CREATE_EXTRA_SCHEMANAME_TEST_PACKAGE1 =
        "CREATE OR REPLACE PACKAGE EXTRA_SCHEMANAME_TEST_PACKAGE AS"+
            "\nFUNCTION test(PARAM1 IN SCOTT.EMP%ROWTYPE) RETURN NUMBER;"+
        "END EXTRA_SCHEMANAME_TEST_PACKAGE;";

    @Test
    public void testExtraSchemaNames() {
        DDLParser parser = new DDLParser(new StringReader(CREATE_EXTRA_SCHEMANAME_TEST_PACKAGE1));
        parser.setTypesRepository(new DatabaseTypesRepository());
        boolean worked = true;
        @SuppressWarnings("unused") PLSQLPackageType packageType = null;
        try {
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue("EXTRA_SCHEMANAME_TEST_PACKAGE should parse", worked);
    }

    static final String CREATE_EXTRA_SCHEMANAME_TEST_PACKAGE2 =
        "CREATE OR REPLACE PACKAGE EXTRA_SCHEMANAME_TEST_PACKAGE AS"+
            "\nFUNCTION test(PARAM1 IN NUMBER) RETURN SCOTT.EMP%ROWTYPE;"+
        "END EXTRA_SCHEMANAME_TEST_PACKAGE;";
    @Test
    public void testSetSchemaNames() {
        DDLParser parser = new DDLParser(new StringReader(CREATE_EXTRA_SCHEMANAME_TEST_PACKAGE2));
        parser.setTypesRepository(new DatabaseTypesRepository());
        ArrayList<String> schemaPatterns = new ArrayList<String>();
        schemaPatterns.add("SCOTT");
        parser.setSchemaPatterns(schemaPatterns);
        boolean worked = true;
        @SuppressWarnings("unused") PLSQLPackageType packageType = null;
        try {
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue("EXTRA_SCHEMANAME_TEST_PACKAGE should parse", worked);
        ProcedureType procedureType = packageType.getProcedures().get(0);
        if (procedureType.isFunctionType()) {
            FunctionType functionType = (FunctionType)procedureType;
            ArgumentType returnArgument = functionType.getReturnArgument();
            DatabaseType returnArgumentDataType = returnArgument.getEnclosedType();
            String returnArgumentDataTypeName = returnArgumentDataType.getTypeName();
            assertEquals("EMP%ROWTYPE", returnArgumentDataTypeName);
        }
    }

    static final String CREATE_EXTRA_SCHEMANAME_TEST_PACKAGE3 =
        "CREATE OR REPLACE PACKAGE SCOTT.EXTRA_SCHEMANAME_TEST_PACKAGE AS"+
            "\nFUNCTION test(PARAM1 IN SCOTT.EMP%ROWTYPE) RETURN SCOTT.EMP%ROWTYPE;"+
        "END EXTRA_SCHEMANAME_TEST_PACKAGE;";
    @Test
    public void testSameROWTYPEType() {
        DDLParser parser = new DDLParser(new StringReader(CREATE_EXTRA_SCHEMANAME_TEST_PACKAGE3));
        parser.setTypesRepository(new DatabaseTypesRepository());
        ArrayList<String> schemaPatterns = new ArrayList<String>();
        schemaPatterns.add("SCOTT");
        parser.setSchemaPatterns(schemaPatterns);
        boolean worked = true;
        @SuppressWarnings("unused") PLSQLPackageType packageType = null;
        try {
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue("EXTRA_SCHEMANAME_TEST_PACKAGE should parse", worked);
        ProcedureType procedureType = packageType.getProcedures().get(0);
        if (procedureType.isFunctionType()) {
            FunctionType functionType = (FunctionType)procedureType;
            DatabaseType parm1DataType = functionType.getArguments().get(0).getEnclosedType();
            ArgumentType returnArgument = functionType.getReturnArgument();
            DatabaseType returnArgumentDataType = returnArgument.getEnclosedType();
            String returnArgumentDataTypeName = returnArgumentDataType.getTypeName();
            assertEquals("EMP%ROWTYPE", returnArgumentDataTypeName);
            assertSame(parm1DataType, returnArgumentDataType);
        }
        
    }
}
