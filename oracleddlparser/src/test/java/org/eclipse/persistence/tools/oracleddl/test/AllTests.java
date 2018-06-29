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
package org.eclipse.persistence.tools.oracleddl.test;

//javase imports
import java.sql.Connection;
import java.sql.SQLException;

//JUnit4 imports
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

//testing imports
import org.eclipse.persistence.tools.oracleddl.test.databasetypebuilder.DatabaseTypeBuilderTestSuite;
import org.eclipse.persistence.tools.oracleddl.test.ddlparser.DDLParserTestSuite;
import org.eclipse.persistence.tools.oracleddl.test.visit.VisitorsTestSuite;
import static org.eclipse.persistence.tools.oracleddl.test.TestHelper.buildConnection;

@RunWith(Suite.class)
@SuiteClasses({
    DDLParserTestSuite.class,
    VisitorsTestSuite.class,
    DatabaseTypeBuilderTestSuite.class
  }
)
public class AllTests {

    //shared JUnit fixtures
    public static Connection conn = null;
    
    @BeforeClass
    public static void setUp() throws ClassNotFoundException, SQLException {
        conn = buildConnection();
    }

}
