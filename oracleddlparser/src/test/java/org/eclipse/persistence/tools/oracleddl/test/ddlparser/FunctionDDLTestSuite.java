/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Mike Norman - June 10 2011, created DDL parser package
 *     David McCann - July 2011, visit tests
 ******************************************************************************/
package org.eclipse.persistence.tools.oracleddl.test.ddlparser;

//javase imports
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

//JUnit4 imports
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
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
            public int read() throws IOException {
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
        assertEquals("empty function wrong name", functionType.getProcedureName(), EMPTY_FUNCTION);
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
        assertEquals("simple function wrong name", functionType.getProcedureName(), SIMPLE_FUNCTION);
        DatabaseType returnArg = functionType.getReturnArgument();
        assertEquals("simple function should have DECIMAL return type",
            new DecimalType().getTypeName(), returnArg.getTypeName());
        assertTrue("dummy procedure should have 1 argument",
            functionType.getArguments().size() == 1);
        ArgumentType arg1 = functionType.getArguments().get(0);
        assertEquals("dummy procedure's argument wrong name", arg1.getArgumentName(), SIMPLE_ARG);
        DatabaseType arg1Type = arg1.getEnclosedType();
        assertEquals("incorrect type for " + arg1.getArgumentName() + " type",
            new DecimalType().getTypeName(), arg1Type.getTypeName());
        assertTrue("incorrect direction for " + arg1.getArgumentName(),
            arg1.getDirection() == IN);
    }

/*
    static final String PROCEDURE_W_SYS_REFCURSOR = "GETALL";
    static final String SYS_REF_OUT_ARG = "SIMPL";
    static final String CREATE_PROCEDURE_W_SYS_REFCURSOR =
        CREATE_FUNCTION_PREFIX + PROCEDURE_W_SYS_REFCURSOR +
            " ( " + SYS_REF_OUT_ARG + " OUT SYS_REFCURSOR) AS " +
        "BEGIN " +
          "OPEN SIMPL FOR SELECT * FROM simplesp;" +
        "END";
    @Test
    public void testProcedure_With_Sys_Refcursor() {
        parser.ReInit(new StringReader(CREATE_PROCEDURE_W_SYS_REFCURSOR));
        boolean worked = true;
        String message = "";
        ProcedureType procedureType = null;
        try {
            procedureType = parser.parseTopLevelProcedure();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("procedure with SYS_REFCURSOR did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(procedureType);
        assertTrue("procedure with SYS_REFCURSOR should not contain any unresolved datatypes",
            l.getUnresolvedTypes().isEmpty());
        assertEquals("procedure with SYS_REFCURSOR wrong name", procedureType.getProcedureName(),
            PROCEDURE_W_SYS_REFCURSOR);
        assertTrue("procedure with SYS_REFCURSOR should have 1 argument",
            procedureType.getArguments().size() == 1);
        ArgumentType arg1 = procedureType.getArguments().get(0);
        assertEquals("procedure with SYS_REFCURSOR - arg1 wrong name", arg1.getArgumentName(),
            SYS_REF_OUT_ARG);
        DatabaseType arg1Type = arg1.getDataType();
        assertEquals("incorrect type for " + arg1.getArgumentName() + " type",
            SYS_REFCURSOR_TYPE.getTypeName(), arg1Type.getTypeName());
        assertTrue("incorrect direction for " + arg1.getArgumentName(),
            arg1.getDirection() == OUT);
    }

    static final String PROCEDURE_W_INOUT_ARG = "INOUTARGSP";
    static final String INOUT_ARG = "X";
    static final String CREATE_PROCEDURE_W_INOUT_ARG =
        CREATE_FUNCTION_PREFIX + PROCEDURE_W_INOUT_ARG +
            " ( " + INOUT_ARG + " IN OUT NUMERIC) AS " +
        "BEGIN " +
            "null;" +
        "END";
    @Test
    public void testProcedure_With_InOut_Arg() {
        parser.ReInit(new StringReader(CREATE_PROCEDURE_W_INOUT_ARG));
        boolean worked = true;
        String message = "";
        ProcedureType procedureType = null;
        try {
            procedureType = parser.parseTopLevelProcedure();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("procedure with INOUT arg did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(procedureType);
        assertTrue("procedure with INOUT arg should not contain any unresolved datatypes",
            l.getUnresolvedTypes().isEmpty());
        assertEquals("procedure with INOUT arg wrong name", procedureType.getProcedureName(),
            PROCEDURE_W_INOUT_ARG);
        assertTrue("procedure with INOUT arg should have 1 argument",
            procedureType.getArguments().size() == 1);
        ArgumentType arg1 = procedureType.getArguments().get(0);
        assertEquals("procedure with INOUT arg - arg1 wrong name", arg1.getArgumentName(),
            INOUT_ARG);
        DatabaseType arg1Type = arg1.getDataType();
        assertEquals("incorrect type for " + arg1.getArgumentName() + " type",
            new NumericType().getTypeName(), arg1Type.getTypeName());
        assertTrue("incorrect direction for " + arg1.getArgumentName(),
            arg1.getDirection() == INOUT);
    }
*/
}