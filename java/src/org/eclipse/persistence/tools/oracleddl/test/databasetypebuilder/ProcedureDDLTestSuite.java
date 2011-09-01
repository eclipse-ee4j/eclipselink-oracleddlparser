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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.ArgumentType;
import org.eclipse.persistence.tools.oracleddl.metadata.ProcedureType;
import org.eclipse.persistence.tools.oracleddl.metadata.VarCharType;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypeBuilder;

//testing imports
import org.eclipse.persistence.tools.oracleddl.test.AllTests;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DATABASE_USERNAME_KEY;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.DEFAULT_DATABASE_USERNAME;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.buildConnection;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.createDbArtifact;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.dropDbArtifact;

public class ProcedureDDLTestSuite {

    static final String SIMPLEPROC = "DTB_SIMPLEPROC";
    static final String CREATE_SIMPLEPROC = 
        "CREATE PROCEDURE " + SIMPLEPROC + "(X IN VARCHAR) (\n" +
        "BEGIN\n" +
            "null;\n" +  
        "END";  
    static final String DROP_SIMPLEPROC =
        "DROP PROCEDURE " + SIMPLEPROC;
    
    //fixtures
    static DatabaseTypeBuilder dtBuilder = DatabaseTypeBuilderTestSuite.dtBuilder;
    static Connection conn = AllTests.conn;
    static ProcedureType procedureType = null;
    static List<String> expectedFieldNames = new ArrayList<String>();
    static List<String> expectedPKFieldNames = new ArrayList<String>();
    @BeforeClass
    public static void setUp() throws SQLException, ClassNotFoundException {
        conn = buildConnection();
        dtBuilder = new DatabaseTypeBuilder();
        //send DDL to database
        createDbArtifact(conn, CREATE_SIMPLEPROC);
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
            new VarCharType().getTypeName(), argFirst.getDataType().getTypeName());
    }

    @AfterClass
    public static void tearDown() {
        dropDbArtifact(conn, DROP_SIMPLEPROC);
    }

}