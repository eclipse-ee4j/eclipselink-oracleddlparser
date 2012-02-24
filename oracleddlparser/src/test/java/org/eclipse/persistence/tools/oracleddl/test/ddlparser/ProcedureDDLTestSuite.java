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
import org.eclipse.persistence.tools.oracleddl.metadata.NumericType;
import org.eclipse.persistence.tools.oracleddl.metadata.ProcedureType;
import org.eclipse.persistence.tools.oracleddl.metadata.VarChar2Type;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.UnresolvedTypesVisitor;
import org.eclipse.persistence.tools.oracleddl.parser.DDLParser;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypesRepository;
import static org.eclipse.persistence.tools.oracleddl.metadata.ScalarDatabaseTypeEnum.SYS_REFCURSOR_TYPE;
import static org.eclipse.persistence.tools.oracleddl.metadata.ArgumentTypeDirection.IN;
import static org.eclipse.persistence.tools.oracleddl.metadata.ArgumentTypeDirection.INOUT;
import static org.eclipse.persistence.tools.oracleddl.metadata.ArgumentTypeDirection.OUT;

public class ProcedureDDLTestSuite {

    static final String CREATE_PROCEDURE_PREFIX = "CREATE PROCEDURE ";
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


    static final String EMPTY_PROCEDURE = "EMPTY_PROCEDURE";
	static final String CREATE_EMPTY_PROCEDURE =
	    CREATE_PROCEDURE_PREFIX + EMPTY_PROCEDURE + " IS BEGIN null; END";
    @Test
    public void testEmptyProcedure() {
        parser.ReInit(new StringReader(CREATE_EMPTY_PROCEDURE));
        boolean worked = true;
        @SuppressWarnings("unused") ProcedureType procedureType = null;
        try {
            procedureType = parser.parseTopLevelProcedure();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue("empty procedure should parse", worked);
        assertEquals("empty procedure wrong name", procedureType.getProcedureName(), EMPTY_PROCEDURE);
        assertTrue("empty procedure should have no arguments", procedureType.getArguments().isEmpty());
    }

    static final String DUMMY_PROCEDURE = "DUMMY_PROCEDURE";
    static final String DUMMY_ARG = "DUM";
    static final String CREATE_DUMMY_PROCEDURE =
        CREATE_PROCEDURE_PREFIX + DUMMY_PROCEDURE + " (" + DUMMY_ARG + " IN VARCHAR2) AS " +
    	"BEGIN " +
    	  "null;" +
    	"END";
    @Test
    public void testDummyProcedure() {
        parser.ReInit(new StringReader(CREATE_DUMMY_PROCEDURE));
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
        assertTrue("dummy procedure did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(procedureType);
        assertTrue("dummy procedure should not contain any unresolved datatypes",
            l.getUnresolvedTypes().isEmpty());
        assertEquals("dummy procedure wrong name", procedureType.getProcedureName(), DUMMY_PROCEDURE);
        assertTrue("dummy procedure should have 1 argument",
            procedureType.getArguments().size() == 1);
        ArgumentType arg1 = procedureType.getArguments().get(0);
        assertEquals("dummy procedure's argument wrong name", arg1.getArgumentName(), DUMMY_ARG);
    }

    static final String DUMMY_PROCEDURE_SCHEMA = "SCOTT";
    static final String CREATE_DUMMY_PROCEDURE_SCHEMA =
        CREATE_PROCEDURE_PREFIX + DUMMY_PROCEDURE_SCHEMA + "." + DUMMY_PROCEDURE + " (" + DUMMY_ARG + " IN VARCHAR2) AS " +
        "BEGIN " +
          "null;" +
        "END";
    @Test
    public void testDummyProcedure_WithSchema() {
        parser.ReInit(new StringReader(CREATE_DUMMY_PROCEDURE_SCHEMA));
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
        assertTrue("dummy procedure(with schema) did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(procedureType);
        assertTrue("dummy procedure(with schema) should not contain any unresolved datatypes",
            l.getUnresolvedTypes().isEmpty());
        assertEquals("dummy procedure wrong schema", procedureType.getSchema(), DUMMY_PROCEDURE_SCHEMA);
        assertEquals("dummy procedure wrong name", procedureType.getProcedureName(), DUMMY_PROCEDURE);
        assertTrue("dummy procedure should have 1 argument",
            procedureType.getArguments().size() == 1);
        ArgumentType arg1 = procedureType.getArguments().get(0);
        assertEquals("dummy procedure's argument wrong name", arg1.getArgumentName(), DUMMY_ARG);
    }

    static final String PROCEDURE_W_IN_OUT_ARGS = "INOUTARGSSP";
    static final String IN_ARG = "T";
    static final String OUT_ARG = "U";
    static final String OUT_ARG2 = "V";
    static final String CREATE_PROCEDURE_W_IN_OUT_ARGS =
        CREATE_PROCEDURE_PREFIX + PROCEDURE_W_IN_OUT_ARGS +
            " ( " + IN_ARG + " IN VARCHAR2, " + OUT_ARG + " OUT VARCHAR2, " + OUT_ARG2 + " OUT NUMERIC) AS " +
        "BEGIN " +
          "null;" +
        "END";
    @Test
    public void testProcedure_With_In_Out_Args() {
        parser.ReInit(new StringReader(CREATE_PROCEDURE_W_IN_OUT_ARGS));
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
        assertTrue("procedure with in and out args did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(procedureType);
        assertTrue("procedure with in and out args should not contain any unresolved datatypes",
            l.getUnresolvedTypes().isEmpty());
        assertEquals("procedure with in and out args wrong name", procedureType.getProcedureName(),
            PROCEDURE_W_IN_OUT_ARGS);
        assertTrue("procedure with in and out args should have 3 arguments",
            procedureType.getArguments().size() == 3);
        ArgumentType arg1 = procedureType.getArguments().get(0);
        assertEquals("procedure with in and out args - arg1 wrong name", arg1.getArgumentName(),
            IN_ARG);
        DatabaseType arg1Type = arg1.getEnclosedType();
        assertEquals("incorrect type for " + arg1.getArgumentName() + " type",
            new VarChar2Type().getTypeName(), arg1Type.getTypeName());
        assertTrue("incorrect direction for " + arg1.getArgumentName(),
            arg1.getDirection() == IN);
        ArgumentType arg2 = procedureType.getArguments().get(1);
        assertEquals("procedure with in and out args - arg2 wrong name", arg2.getArgumentName(),
            OUT_ARG);
        DatabaseType arg2Type = arg2.getEnclosedType();
        assertEquals("incorrect type for " + arg2.getArgumentName() + " type",
            new VarChar2Type().getTypeName(), arg2Type.getTypeName());
        assertTrue("incorrect direction for " + arg2.getArgumentName(),
            arg2.getDirection() == OUT);
        ArgumentType arg3 = procedureType.getArguments().get(2);
        assertEquals("procedure with in and out args - arg3 wrong name", arg3.getArgumentName(),
            OUT_ARG2);
        DatabaseType arg3Type = arg3.getEnclosedType();
        assertEquals("incorrect type for " + arg3.getArgumentName() + " type",
            new NumericType().getTypeName(), arg3Type.getTypeName());
        assertTrue("incorrect direction for " + arg3.getArgumentName(),
            arg3.getDirection() == OUT);
    }

    static final String PROCEDURE_W_SYS_REFCURSOR = "GETALL";
    static final String SYS_REF_OUT_ARG = "SIMPL";
    static final String CREATE_PROCEDURE_W_SYS_REFCURSOR =
        CREATE_PROCEDURE_PREFIX + PROCEDURE_W_SYS_REFCURSOR +
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
        DatabaseType arg1Type = arg1.getEnclosedType();
        assertEquals("incorrect type for " + arg1.getArgumentName() + " type",
            SYS_REFCURSOR_TYPE.getTypeName(), arg1Type.getTypeName());
        assertTrue("incorrect direction for " + arg1.getArgumentName(),
            arg1.getDirection() == OUT);
    }

    static final String PROCEDURE_W_INOUT_ARG = "INOUTARGSP";
    static final String INOUT_ARG = "X";
    static final String CREATE_PROCEDURE_W_INOUT_ARG =
        CREATE_PROCEDURE_PREFIX + PROCEDURE_W_INOUT_ARG +
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
        DatabaseType arg1Type = arg1.getEnclosedType();
        assertEquals("incorrect type for " + arg1.getArgumentName() + " type",
            new NumericType().getTypeName(), arg1Type.getTypeName());
        assertTrue("incorrect direction for " + arg1.getArgumentName(),
            arg1.getDirection() == INOUT);
    }

}