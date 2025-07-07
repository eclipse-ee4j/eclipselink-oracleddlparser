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
            public int read() {
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
        assertEquals("empty procedure wrong name", EMPTY_PROCEDURE, procedureType.getProcedureName());
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
        assertEquals("dummy procedure wrong name", DUMMY_PROCEDURE, procedureType.getProcedureName());
        assertEquals("dummy procedure should have 1 argument", 1, procedureType.getArguments().size());
        ArgumentType arg1 = procedureType.getArguments().get(0);
        assertEquals("dummy procedure's argument wrong name", DUMMY_ARG, arg1.getArgumentName());
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
        assertEquals("dummy procedure wrong schema", DUMMY_PROCEDURE_SCHEMA, procedureType.getSchema());
        assertEquals("dummy procedure wrong name", DUMMY_PROCEDURE, procedureType.getProcedureName());
        assertEquals("dummy procedure should have 1 argument", 1, procedureType.getArguments().size());
        ArgumentType arg1 = procedureType.getArguments().get(0);
        assertEquals("dummy procedure's argument wrong name", DUMMY_ARG, arg1.getArgumentName());
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
        assertEquals("procedure with in and out args wrong name", PROCEDURE_W_IN_OUT_ARGS,
                procedureType.getProcedureName());
        assertEquals("procedure with in and out args should have 3 arguments", 3, procedureType.getArguments().size());
        ArgumentType arg1 = procedureType.getArguments().get(0);
        assertEquals("procedure with in and out args - arg1 wrong name", IN_ARG,
                arg1.getArgumentName());
        DatabaseType arg1Type = arg1.getEnclosedType();
        assertEquals("incorrect type for " + arg1.getArgumentName() + " type",
            new VarChar2Type().getTypeName(), arg1Type.getTypeName());
        assertSame("incorrect direction for " + arg1.getArgumentName(), IN, arg1.getDirection());
        ArgumentType arg2 = procedureType.getArguments().get(1);
        assertEquals("procedure with in and out args - arg2 wrong name", OUT_ARG,
                arg2.getArgumentName());
        DatabaseType arg2Type = arg2.getEnclosedType();
        assertEquals("incorrect type for " + arg2.getArgumentName() + " type",
            new VarChar2Type().getTypeName(), arg2Type.getTypeName());
        assertSame("incorrect direction for " + arg2.getArgumentName(), OUT, arg2.getDirection());
        ArgumentType arg3 = procedureType.getArguments().get(2);
        assertEquals("procedure with in and out args - arg3 wrong name", OUT_ARG2,
                arg3.getArgumentName());
        DatabaseType arg3Type = arg3.getEnclosedType();
        assertEquals("incorrect type for " + arg3.getArgumentName() + " type",
            new NumericType().getTypeName(), arg3Type.getTypeName());
        assertSame("incorrect direction for " + arg3.getArgumentName(), OUT, arg3.getDirection());
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
        assertEquals("procedure with SYS_REFCURSOR wrong name", PROCEDURE_W_SYS_REFCURSOR,
                procedureType.getProcedureName());
        assertEquals("procedure with SYS_REFCURSOR should have 1 argument", 1, procedureType.getArguments().size());
        ArgumentType arg1 = procedureType.getArguments().get(0);
        assertEquals("procedure with SYS_REFCURSOR - arg1 wrong name", SYS_REF_OUT_ARG,
                arg1.getArgumentName());
        DatabaseType arg1Type = arg1.getEnclosedType();
        assertEquals("incorrect type for " + arg1.getArgumentName() + " type",
            SYS_REFCURSOR_TYPE.getTypeName(), arg1Type.getTypeName());
        assertSame("incorrect direction for " + arg1.getArgumentName(), OUT, arg1.getDirection());
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
        assertEquals("procedure with INOUT arg wrong name", PROCEDURE_W_INOUT_ARG,
                procedureType.getProcedureName());
        assertEquals("procedure with INOUT arg should have 1 argument", 1, procedureType.getArguments().size());
        ArgumentType arg1 = procedureType.getArguments().get(0);
        assertEquals("procedure with INOUT arg - arg1 wrong name", INOUT_ARG,
                arg1.getArgumentName());
        DatabaseType arg1Type = arg1.getEnclosedType();
        assertEquals("incorrect type for " + arg1.getArgumentName() + " type",
            new NumericType().getTypeName(), arg1Type.getTypeName());
        assertSame("incorrect direction for " + arg1.getArgumentName(), INOUT, arg1.getDirection());
    }

    static final String PROCEDURE_W_KEYWORDS = "KEYWORDSP";
    static final String IN_ARG1 = "OPERATOR";
    static final String IN_ARG2 = "TIMESTAMP";
    static final String CREATE_PROCEDURE_W_KEYWORDS =
        CREATE_PROCEDURE_PREFIX + PROCEDURE_W_KEYWORDS +
            " ( " + IN_ARG1 + " IN VARCHAR2, " +
                    IN_ARG2 + " IN DATE" +
            ") AS " +
        "BEGIN " +
            "null;" +
        "END";
    @Test
    public void testProcedure_With_Keyword() {
        parser.ReInit(new StringReader(CREATE_PROCEDURE_W_KEYWORDS));
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
        assertTrue("procedure with keywords did not parse:\n" + message, worked);
        assertEquals("incorrect procedure name " + PROCEDURE_W_KEYWORDS,
            PROCEDURE_W_KEYWORDS, procedureType.getProcedureName());
    }

}
