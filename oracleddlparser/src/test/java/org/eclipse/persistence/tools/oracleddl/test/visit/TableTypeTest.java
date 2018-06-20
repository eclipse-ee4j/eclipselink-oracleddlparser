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
package org.eclipse.persistence.tools.oracleddl.test.visit;

//JUnit4 imports
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.FieldType;
import org.eclipse.persistence.tools.oracleddl.metadata.NumericType;
import org.eclipse.persistence.tools.oracleddl.metadata.TableType;
import org.eclipse.persistence.tools.oracleddl.metadata.VarChar2Type;
import org.eclipse.persistence.tools.oracleddl.metadata.VarCharType;
import org.eclipse.persistence.tools.oracleddl.test.visit.TableTypeVisitor;

/**
 * Test TableType visit method chain.  Ensures that all required
 * information can be retrieved via TableType.accept().
 *
 * This test covers:
 *  - TableType
 *  - FieldType
 *  - DatabaseType
 *
 */
public class TableTypeTest {

    //JUnit fixture(s)
    static TableType table = null;
    @BeforeClass
    public static void setUp() {
        // setup TableType
        table = new TableType("EMPLOYEE");
        table.setSchema("TLUSER");
        FieldType col = new FieldType("ID");
        col.setEnclosedType(new VarCharType());
        col.setPk();
        table.addColumn(col);
        col = new FieldType("NAME");
        col.setEnclosedType(new VarChar2Type());
        col.setNotNull();
        table.addColumn(col);
        col = new FieldType("DEPT");
        col.setEnclosedType(new NumericType());
        table.addColumn(col);
    }

    static String TABLE =
        "TABLE TLUSER.EMPLOYEE (\n" +
            "\tID\tVARCHAR\n" +
            "\tNAME\tVARCHAR2 (NOT NULL)\n" +
            "\tDEPT\tNUMERIC\n" +
            "\tPRIMARY KEY (ID)\n" +
        ")";

    @Test
    public void testTableType() {
        // visit
        TableTypeVisitor visitor = new TableTypeVisitor();
        table.accept(visitor);
        assertEquals("TableTypeVisitor test failed:\n", visitor.toString(), TABLE);
    }
}
