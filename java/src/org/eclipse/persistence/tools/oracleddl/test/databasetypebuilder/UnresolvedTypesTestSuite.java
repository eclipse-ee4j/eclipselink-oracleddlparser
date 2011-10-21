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
import java.util.List;

//JUnit4 imports
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLPackageType;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;
import org.eclipse.persistence.tools.oracleddl.test.AllTests;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypeBuilder;

import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.buildConnection;

public class UnresolvedTypesTestSuite {

    //JUnit fixture(s)
    static DatabaseTypeBuilder dtBuilder = DatabaseTypeBuilderTestSuite.dtBuilder;
    static Connection conn = AllTests.conn;

	@BeforeClass
	static public void setUp() throws ClassNotFoundException, SQLException {
        conn = buildConnection();
        dtBuilder = new DatabaseTypeBuilder();
	}

	static final String TESMANPACK_PACKAGE = "TESMANPACK";
    @Test
    public void testUnresolvedTypeResolution() throws ParseException {
        List<PLSQLPackageType> packages = dtBuilder.buildPackages(conn, null, TESMANPACK_PACKAGE);
        for (PLSQLPackageType p : packages) {
            System.out.println(p.toString() + (p.isResolved() ? " is resolved." : " is not resolved."));
        }
    }
}