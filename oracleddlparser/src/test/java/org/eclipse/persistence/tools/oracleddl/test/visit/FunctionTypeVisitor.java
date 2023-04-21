/*
 * Copyright (c) 2011, 2023 Oracle and/or its affiliates. All rights reserved.
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
package org.eclipse.persistence.tools.oracleddl.test.visit;

//javase imports
import java.util.ArrayList;
import java.util.List;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.ArgumentType;
import org.eclipse.persistence.tools.oracleddl.metadata.FunctionType;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.BaseDatabaseTypeVisitor;
import static org.eclipse.persistence.tools.oracleddl.metadata.ArgumentTypeDirection.RETURN;

class FunctionTypeVisitor extends BaseDatabaseTypeVisitor {

    public String funcName;
    public String schema;
    public List<String> argData = new ArrayList<String>();
    public String returnArg;

    public void beginVisit(FunctionType funcType) {
        funcName = funcType.getProcedureName();
        schema = funcType.getSchema();
        returnArg = funcType.getReturnArgument().getTypeName();
    }

    public void beginVisit(ArgumentType argType) {
        if (argType.optional()) {
            argData.add(argType.getArgumentName() + "(opt) " + argType.getDirection() + " " +
                argType.getEnclosedType());
        }
        else if (argType.getDirection() != RETURN) {
            argData.add(argType.getArgumentName() + " " + argType.getDirection() + " " +
                argType.getEnclosedType());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("FUNCTION ");
        if (schema != null) {
            sb.append(schema);
            sb.append(".");
        }
        sb.append(funcName);
        sb.append(" (");
        for(int i=0; i<argData.size();) {
            String arg = argData.get(i);
            sb.append(arg);
            if (++i < argData.size()) {
                sb.append(", ");
            }
        }
        sb.append(") RETURN ");
        sb.append(returnArg);
        return sb.toString();
    }
}
