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
import org.eclipse.persistence.tools.oracleddl.metadata.FunctionType;
import org.eclipse.persistence.tools.oracleddl.metadata.VarCharType;

/**
 * Test FunctionType visit method chain.  Ensures that all required
 * information can be retrieved via FunctionType.accept().
 *
 * This test covers:
 *  - FunctionType
 *  - DatabaseType
 *  - ProcedureType
 *  - ArgumentType
 *  - ArgumentTypeDirection
 *
 */
public class FunctionTypeTest {

    protected static String FUNCTION = "FUNCTION TLUSER.GET_EMP_SALARY (EMP_ID IN VARCHAR) RETURN FLOAT";

    @Test
	public void testFunctionType() {
        // setup FunctionType
        FunctionType function = new FunctionType("GET_EMP_SALARY");
        function.setSchema("TLUSER");
        // add arg "EMP_ID"
        ArgumentType arg = new ArgumentType("EMP_ID");
        arg.setDirection(ArgumentTypeDirection.IN);
        arg.setEnclosedType(new VarCharType());
        function.addArgument(arg);
        // set return argument
        arg = new ArgumentType("");
        arg.setDirection(ArgumentTypeDirection.RETURN);
        arg.setEnclosedType(new FloatType());
        function.setReturnArgument(arg);

        // visit
        FunctionTypeVisitor visitor = new FunctionTypeVisitor();
		function.accept(visitor);
		assertEquals("FunctionType test failed:\n" + visitor.toString(), visitor.toString(), FUNCTION);
	}

}