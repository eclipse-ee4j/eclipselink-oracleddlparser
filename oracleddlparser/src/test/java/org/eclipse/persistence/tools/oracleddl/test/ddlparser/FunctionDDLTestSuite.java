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
package org.eclipse.persistence.tools.oracleddl.test.ddlparser;

//javase imports
import java.io.InputStream;
import java.io.StringReader;

//JUnit4 imports
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.ArgumentType;
import org.eclipse.persistence.tools.oracleddl.metadata.DatabaseType;
import org.eclipse.persistence.tools.oracleddl.metadata.DecimalType;
import org.eclipse.persistence.tools.oracleddl.metadata.FunctionType;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.UnresolvedTypesVisitor;
import org.eclipse.persistence.tools.oracleddl.parser.DDLParser;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypesRepository;
import static org.eclipse.persistence.tools.oracleddl.metadata.ArgumentTypeDirection.IN;

public class FunctionDDLTestSuite {

    static final String CREATE_FUNCTION_PREFIX = "CREATE FUNCTION ";
    //JUnit fixture(s)
    static DDLParser parser = null;

    @BeforeClass
    static public void setUp() {
        parser = new DDLParser(new InputStream() {
            public int read() {
                return 0;
            }
        });
        parser.setTypesRepository(new DatabaseTypesRepository());
    }

    static final String EMPTY_FUNCTION = "EMPTY_FUNCTION";
    static final String CREATE_EMPTY_FUNCTION =
        CREATE_FUNCTION_PREFIX + EMPTY_FUNCTION + " RETURN DECIMAL IS BEGIN RETURN 0; END";
    @Test
    public void testEmptyFunction() {
        parser.ReInit(new StringReader(CREATE_EMPTY_FUNCTION));
        boolean worked = true;
        @SuppressWarnings("unused") FunctionType functionType = null;
        try {
            functionType = parser.parseTopLevelFunction();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue("empty function should parse", worked);
        assertEquals("empty function wrong name", EMPTY_FUNCTION, functionType.getProcedureName());
        assertTrue("empty function should have no arguments", functionType.getArguments().isEmpty());
    }

    static final String SIMPLE_FUNCTION = "SIMPLE_FUNCTION";
    static final String SIMPLE_ARG = "DEPT";
    static final String CREATE_SIMPLE_FUNCTION =
        CREATE_FUNCTION_PREFIX + SIMPLE_FUNCTION + " (" + SIMPLE_ARG + " IN DECIMAL) RETURN DECIMAL AS " +
        "BEGIN " +
            "SELECT max(SAL) INTO MAXSAL FROM SIMPLESF WHERE DEPTNO = DEPT; " +
            "RETURN(MAXSAL); " +
        "END";

    @Test
    public void testSimpleFunction() {
        parser.ReInit(new StringReader(CREATE_SIMPLE_FUNCTION));
        boolean worked = true;
        String message = "";
        FunctionType functionType = null;
        try {
            functionType = parser.parseTopLevelFunction();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("simple function did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(functionType);
        assertTrue("simple function should not contain any unresolved datatypes",
            l.getUnresolvedTypes().isEmpty());
        assertEquals("simple function wrong name", SIMPLE_FUNCTION, functionType.getProcedureName());
        DatabaseType returnArg = functionType.getReturnArgument();
        assertEquals("simple function should have DECIMAL return type",
            new DecimalType().getTypeName(), returnArg.getTypeName());
        assertEquals("dummy procedure should have 1 argument", 1, functionType.getArguments().size());
        ArgumentType arg1 = functionType.getArguments().get(0);
        assertEquals("dummy procedure's argument wrong name", SIMPLE_ARG, arg1.getArgumentName());
        DatabaseType arg1Type = arg1.getEnclosedType();
        assertEquals("incorrect type for " + arg1.getArgumentName() + " type",
            new DecimalType().getTypeName(), arg1Type.getTypeName());
        assertSame("incorrect direction for " + arg1.getArgumentName(), IN, arg1.getDirection());
    }

    static final String FUNCTION_W_KEYWORDS = "KEYWORDSF";
    static final String IN_ARG1 = "OPERATOR";
    static final String IN_ARG2 = "ARRAY";
    static final String CREATE_FUNCTION_W_KEYWORDS =
        CREATE_FUNCTION_PREFIX + FUNCTION_W_KEYWORDS +
            " ( " + IN_ARG1 + " IN VARCHAR2, " +
                    IN_ARG2 + " IN DATE" +
            ") return Integer AS " +
        "BEGIN " +
            "return 1;" +
        "END";
    @Test
    public void testFunction_With_Keyword() {
        parser.ReInit(new StringReader(CREATE_FUNCTION_W_KEYWORDS));
        boolean worked = true;
        String message = "";
        FunctionType functionType = null;
        try {
            functionType = parser.parseTopLevelFunction();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("function with keywords did not parse:\n" + message, worked);
        assertEquals("incorrect function name " + FUNCTION_W_KEYWORDS,
            FUNCTION_W_KEYWORDS, functionType.getProcedureName());
    }

}
