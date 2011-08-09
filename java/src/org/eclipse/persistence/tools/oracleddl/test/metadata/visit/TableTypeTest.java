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
 *     David McCann - July 22, 2011 - 2.4 - Initial implementation
 ******************************************************************************/
package org.eclipse.persistence.tools.oracleddl.test.metadata.visit;

import static org.junit.Assert.assertEquals;

import org.eclipse.persistence.tools.oracleddl.metadata.FieldType;
import org.eclipse.persistence.tools.oracleddl.metadata.NumericType;
import org.eclipse.persistence.tools.oracleddl.metadata.TableType;
import org.eclipse.persistence.tools.oracleddl.metadata.VarChar2Type;
import org.eclipse.persistence.tools.oracleddl.metadata.VarCharType;
import org.junit.Test;

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
    protected static String TABLE =
            "TABLE TLUSER.EMPLOYEE (\n" +
                    "\tID\tVARCHAR\n" +
                    "\tNAME\tVARCHAR2 (NOT NULL)\n" +
                    "\tDEPT\tNUMERIC\n" +
                    "\tPRIMARY KEY (ID)\n" +
                ")";

	@Test
	public void testTableType() {
        // setup TableType
	    TableType table = new TableType("EMPLOYEE");
        table.setSchema("TLUSER");
        
        FieldType col = new FieldType("ID");
        col.setDataType(new VarCharType());
        col.setPk();
        table.addCompositeType(col);
        col = new FieldType("NAME");
        col.setDataType(new VarChar2Type());
        col.setNotNull();
        table.addCompositeType(col);
        col = new FieldType("DEPT");
        col.setDataType(new NumericType());
        table.addCompositeType(col);
        
        // visit
        TableTypeVisitor visitor = new TableTypeVisitor();
		table.accept(visitor);
		assertEquals("TableTypeVisitor test failed:\n", visitor.toString(), TABLE);
	}
}