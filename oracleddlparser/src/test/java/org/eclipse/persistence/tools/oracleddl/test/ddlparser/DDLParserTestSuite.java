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

//JUnit4 imports
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    AnchoredTypesTestSuite.class,
    CaseSensitivePackageTestSuite.class,
    ExtraSchemaNameDDLTestSuite.class,
    FunctionDDLTestSuite.class,
    ProcedureDDLTestSuite.class,
    PackageDDLTestSuite.class,
    StronglyTypedCursorDDLTestSuite.class,
    TableDDLTestSuite.class,
    TypeDDLTestSuite.class
})
public class DDLParserTestSuite {

}