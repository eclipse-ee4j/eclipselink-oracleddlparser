/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
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
package org.eclipse.persistence.tools.oracleddl.metadata;

//javase imports
import java.util.ArrayList;
import java.util.List;

//DDL parser imports
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitable;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class ProcedureType extends CompositeDatabaseTypeBase implements CompositeDatabaseType, DatabaseTypeVisitable {

    protected String catalogName; // for Oracle catalogName == packageName
    protected String procedureName;
    protected String schema;
    protected int overload; // Oracle support overloading - which number is this procedure
    protected PLSQLPackageType parentType =  null; //procedure can be TOPLEVEL or in a package
    protected List<ArgumentType> arguments = new ArrayList<ArgumentType>();

    public ProcedureType(String procedureName) {
        super(null);
        setProcedureName(procedureName);
    }

    public String getProcedureName() {
        return procedureName;
    }
    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
        super.typeName = "PROCEDURE " + procedureName;
    }

    public String getSchema() {
        if (parentType != null && schema == null) {
            schema = parentType.getSchema();
        }
        return schema;
    }
    public void setSchema(String schema) {
       this.schema = schema;
    }

    public String getCatalogName() {
        return catalogName;
    }
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public int getOverload() {
        return overload;
    }
    public void setOverload(int overload) {
        this.overload = overload;
    }

    public PLSQLPackageType getParentType() {
        return parentType;
    }
    public void setParentType(PLSQLPackageType parentType) {
        this.parentType = parentType;
    }

    public DatabaseType getEnclosedType() {
        return null;
    }
    public void setEnclosedType(DatabaseType enclosedType) {
        //no-op
    }

    public List<ArgumentType> getArguments() {
        return arguments;
    }
    public void addArgument(ArgumentType arg) {
        arguments.add(arg);
    }

    @Override
    public boolean isResolved() {
        // if any of the arguments are unresolved, then this ProcedureType is unresolved
        for (ArgumentType argType : arguments) {
            if (!argType.isResolved()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isProcedureType() {
        return true;
    }

    @Override
    public boolean isFunctionType() {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PROCEDURE ");
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
        sb.append(")");
        return sb.toString();
    }

    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }
}
