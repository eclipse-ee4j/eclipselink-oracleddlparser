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
 ******************************************************************************/
package org.eclipse.persistence.tools.oracleddl.metadata;

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class FunctionType extends ProcedureType {

    protected DatabaseType returnArgument;
    
    public FunctionType(String procedureName) {
        super(procedureName);
    }

    @Override
    public boolean isFunction() {
        return true;
    }

    public DatabaseType getReturnArgument() {
        return returnArgument;
    }

    public void setReturnArgument(DatabaseType returnArgument) {
        this.returnArgument = returnArgument;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FUNCTION ");
        if (schema != null) {
            sb.append(schema);
            sb.append(".");
        }
        sb.append(procedureName);
        sb.append(" (");
        for (int i=0; i<arguments.size();) {
            ArgumentType arg = arguments.get(i);
            sb.append(arg.argumentName);
            if (arg.optional) {
                sb.append("(opt) ");
            } else {
                sb.append(" ");
            }
            sb.append(arg.getDirection());
            sb.append(" ");
            sb.append(arg.getDataType());
            if (++i < arguments.size()) {
                sb.append(", ");
            }
        }
        sb.append(") RETURN ");
        sb.append(returnArgument.getTypeName());
        return sb.toString();
    }

    @Override
    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }
}
