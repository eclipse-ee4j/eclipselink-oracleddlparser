/*******************************************************************************
 * Copyright (c) 2011, 2014 Oracle. All rights reserved.
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

    protected ArgumentType returnArgument;

    public FunctionType(String procedureName) {
        super(procedureName);
    }

    @Override
    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
        super.typeName = "FUNCTION " + procedureName;
    }

    public ArgumentType getReturnArgument() {
        return returnArgument;
    }

    public void setReturnArgument(ArgumentType returnArgument) {
        this.returnArgument = returnArgument;
    }

    @Override
    public boolean isResolved() {
        // if the returnArg is unresolved, then this function is unresolved
        if (returnArgument == null) {
            return false;
        }
        if (!returnArgument.isResolved()) {
            return false;
        }
        // use ProcedureType's isResolved()
        return super.isResolved();
    }

    @Override
    public boolean isFunctionType() {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FUNCTION ");
        if (schema != null) {
            sb.append(schema);
            sb.append(".");
        }
        sb.append(procedureName);
        sb.append("(");
        for (int i = 0; i < arguments.size();) {
            ArgumentType arg = arguments.get(i);
            sb.append(arg.toString());
            if (++i < arguments.size()) {
                sb.append(", ");
            }
        }
        sb.append(") ");
        sb.append(returnArgument != null ? returnArgument.toString() : "<NULL>");
        return sb.toString();
    }

    @Override
    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }
}
