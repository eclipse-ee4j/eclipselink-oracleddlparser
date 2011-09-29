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
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLPackageType;
import org.eclipse.persistence.tools.oracleddl.parser.DDLParser;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypesRepository;


public class PackageDDLTestSuite {

    static final String CREATE_PACKAGE_PREFIX = "CREATE PACKAGE ";
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

    static final String EMPTY_PACKAGE = "EMPTY_PACKAGE";
    static final String CREATE_EMPTY_PACKAGE =
        CREATE_PACKAGE_PREFIX + EMPTY_PACKAGE + " AS END " + EMPTY_PACKAGE + ";";
    @Test
    public void testEmptyPackage() {
        parser.ReInit(new StringReader(CREATE_EMPTY_PACKAGE));
        boolean worked = true;
        @SuppressWarnings("unused") PLSQLPackageType packageType = null;
        try {
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue("empty package should parse", worked);
        assertEquals("empty package wrong name", packageType.getPackageName(), EMPTY_PACKAGE);
        assertNull("empty package should have no procedures", packageType.getProcedures());
        assertNull("empty package should have no types", packageType.getTypes());
    }

    static final String SIMPLE_PACKAGE = "SIMPLE_PACKAGE";
    static final String CREATE_SIMPLE_PACKAGE =
        CREATE_PACKAGE_PREFIX + SIMPLE_PACKAGE + " AS " +
            "\nTYPE EREC IS RECORD (" +
                "\nFLAG PLS_INTEGER," +
                "\nEMPNO NUMBER(4)," +
                "\nENAME VARCHAR2(10)," +
                "\nJOB VARCHAR2(9)" +
            "\n);" +
        "\nEND " + SIMPLE_PACKAGE + ";";
    @Test
    public void testSimplePackage() {
        parser.ReInit(new StringReader(CREATE_SIMPLE_PACKAGE));
        boolean worked = true;
        @SuppressWarnings("unused") PLSQLPackageType packageType = null;
        try {
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue("simple package should parse", worked);
    }
}