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
package org.eclipse.persistence.tools.oracleddl.test.visit;

//JUnit4 imports
import org.junit.Test;
import static org.junit.Assert.assertEquals;

//DDL parser imports
import org.eclipse.persistence.tools.oracleddl.metadata.ArgumentType;
import org.eclipse.persistence.tools.oracleddl.metadata.ArgumentTypeDirection;
import org.eclipse.persistence.tools.oracleddl.metadata.FloatType;
import org.eclipse.persistence.tools.oracleddl.metadata.ProcedureType;
import org.eclipse.persistence.tools.oracleddl.metadata.VarChar2Type;
import org.eclipse.persistence.tools.oracleddl.metadata.VarCharType;

/**
 * Test ProcedureType visit method chain.  Ensures that all required 
 * information can be retrieved via ProcedureType.accept().
 * 
 * This test covers:
 *  - ProcedureType
 *  - ArgumentType
 *  - ArgumentTypeDirection
 *  - DatabaseType
 *
 */
public class ProcedureTypeTest {
    
    protected static String PROCEDURE = 
        "PROCEDURE TLUSER.UPDATE_EMP_SALARY (EMP_ID IN VARCHAR, AMOUNT INOUT FLOAT, " +
            "NOTES(opt) IN VARCHAR2)";

	@Test
	public void testProcedureType() {
        // setup ProcedureType
        ProcedureType procedure = new ProcedureType("UPDATE_EMP_SALARY");
        procedure.setSchema("TLUSER");
        // add args "EMP_ID", "AMOUNT" and "NOTES"
        ArgumentType arg = new ArgumentType("EMP_ID");
        arg.setDirection(ArgumentTypeDirection.IN);
        arg.setDataType(new VarCharType());
        procedure.addCompositeType(arg);
        arg = new ArgumentType("AMOUNT");
        arg.setDirection(ArgumentTypeDirection.INOUT);
        arg.setDataType(new FloatType());
        procedure.addCompositeType(arg);
        arg = new ArgumentType("NOTES");
        arg.setDirection(ArgumentTypeDirection.IN);
        arg.setDataType(new VarChar2Type());
        arg.setOptional();
        procedure.addCompositeType(arg);

        // visit
        ProcedureTypeVisitor visitor = new ProcedureTypeVisitor();
		procedure.accept(visitor);
		assertEquals("ProcedureTypeVisitor test failed:\n", visitor.toString(), PROCEDURE);
		//System.out.print(procedure.toString());
	}

}