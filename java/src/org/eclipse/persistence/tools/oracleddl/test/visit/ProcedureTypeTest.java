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
package org.eclipse.persistence.tools.oracleddl.test.visit;

//javase imports
import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.persistence.tools.oracleddl.metadata.visit.BaseDatabaseTypeVisitor;

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
	

    /**
     * Visitor for use with ProcedureType.  The visit methods
     * simply gather all relevant information such that it
     * can be returned as a String when visiting is complete. 
     */
	static class ProcedureTypeVisitor extends BaseDatabaseTypeVisitor {
	    public String procName;
	    public String schema;
	    public List<String> argData = new ArrayList<String>();
	    
	    public void beginVisit(ProcedureType procType) {
	        procName = procType.getProcedureName();
	        schema = procType.getSchema();
	    }
	    
	    public void beginVisit(ArgumentType argType) {
	        if (argType.optional()) {
	            argData.add(argType.getArgumentName() + "(opt) " + argType.getDirection() + " " + argType.getDataType());
	        } else {
	            argData.add(argType.getArgumentName() + " " + argType.getDirection() + " " + argType.getDataType());
	        }
	    }
	   
	    public String toString() {
	        StringBuilder sb = new StringBuilder("PROCEDURE ");
	        if (schema != null) {
	            sb.append(schema);
	            sb.append(".");
	        }
	        sb.append(procName);
	        sb.append(" (");
	        for (int i=0; i<argData.size();) {
	            String arg = argData.get(i);
	            sb.append(arg);
	            if (++i < argData.size()) {
	                sb.append(", ");
	            }
	        }
	        sb.append(")");
	        return sb.toString();
	    }
	}

}