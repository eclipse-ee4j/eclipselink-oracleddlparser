/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

// Contributors:
//     Mike Norman - June 10 2011, created DDL parser package
//     David McCann - July 2011, visit tests
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
