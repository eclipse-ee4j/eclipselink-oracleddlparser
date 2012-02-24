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

//javase imports
import java.util.ArrayList;
import java.util.List;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.ArgumentType;
import org.eclipse.persistence.tools.oracleddl.metadata.ProcedureType;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.BaseDatabaseTypeVisitor;

/**
 * Visitor for use with ProcedureType.  The visit methods
 * simply gather all relevant information such that it
 * can be returned as a String when visiting is complete.
 */
class ProcedureTypeVisitor extends BaseDatabaseTypeVisitor {
    public String procName;
    public String schema;
    public List<String> argData = new ArrayList<String>();

    public void beginVisit(ProcedureType procType) {
        procName = procType.getProcedureName();
        schema = procType.getSchema();
    }

    public void beginVisit(ArgumentType argType) {
        if (argType.optional()) {
            argData.add(argType.getArgumentName() + "(opt) " + argType.getDirection() + " " + argType.getEnclosedType());
        } else {
            argData.add(argType.getArgumentName() + " " + argType.getDirection() + " " + argType.getEnclosedType());
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