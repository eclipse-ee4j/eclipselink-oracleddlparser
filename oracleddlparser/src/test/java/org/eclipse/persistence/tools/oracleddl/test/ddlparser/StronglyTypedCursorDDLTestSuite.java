/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
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
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

//JUnit4 imports
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLPackageType;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.UnresolvedTypesVisitor;
import org.eclipse.persistence.tools.oracleddl.parser.DDLParser;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypesRepository;

public class StronglyTypedCursorDDLTestSuite {

    static final String CREATE_PACKAGE_PREFIX = "CREATE PACKAGE ";
    static final String CURSOR_PACKAGE = "STRONGLY_TYPED_REF_CURSOR_TEST";
    static final String CREATE_CURSOR_PACKAGE =
        CREATE_PACKAGE_PREFIX + CURSOR_PACKAGE + " AS" +
            "\nTYPE STRONGLY_TYPED_REF_CURSOR IS REF CURSOR RETURN STRC_TABLE%ROWTYPE;" +
            "\nPROCEDURE GET_EMS(P_EMS STRC_TABLE.NAME%TYPE, P_EMS_SET OUT STRONGLY_TYPED_REF_CURSOR);" +
        "\nEND " + CURSOR_PACKAGE + ";";

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

    @Test
    public void testCursorPackage() {
        parser.ReInit(new StringReader(CREATE_CURSOR_PACKAGE));
        boolean worked = true;
        @SuppressWarnings("unused") PLSQLPackageType packageType = null;
        try {
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertTrue("cursor package should parse", worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(packageType);
        assertTrue("cursor package should contain two unresolved datatypes",
            l.getUnresolvedTypes().size() == 2);
    }
}
