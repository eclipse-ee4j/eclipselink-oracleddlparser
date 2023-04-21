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
package org.eclipse.persistence.tools.oracleddl.metadata;

//javase imports
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitable;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class PLSQLPackageType extends DatabaseTypeTestableBase implements CompositeDatabaseType, DatabaseTypeVisitable {

    protected String packageName;
    protected String schema;
    protected List<PLSQLType> types;
    protected List<PLSQLCursorType> cursors;
    protected List<ProcedureType> procedures;
    protected List<FieldType> localVariables;

    public PLSQLPackageType() {
    }

    @Override
    public String getTypeName() {
        return "PACKAGE " + packageName;
    }

    public PLSQLPackageType(String packageName) {
        setPackageName(packageName);
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    //actually setting packageName
    public void setTypeName(String typeName) {
        setPackageName(typeName);
    }

    /**
     * Return the schema name for this package.
     *
     * @return the schema name for this package
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Set the schema name for this package.
     *
     * @param schema the schema name for this package
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    public List<PLSQLType> getTypes() {
        if (types == null) {
            types = new ArrayList<PLSQLType>();
        }
        return types;
    }

    public void addType(PLSQLType type) {
        if (types == null) {
            types = new ArrayList<PLSQLType>();
        }
        if (!types.contains(type)) {
            types.add(type);
        }
    }

    public List<PLSQLCursorType> getCursors() {
        if (cursors == null) {
            cursors = new ArrayList<PLSQLCursorType>();
        }
        return cursors;
    }

    public List<ProcedureType> getProcedures() {
        if (procedures == null) {
            procedures = new ArrayList<ProcedureType>();
        }
        return procedures;
    }

    public List<FieldType> getLocalVariables() {
        if (localVariables == null) {
            localVariables = new ArrayList<FieldType>();
        }
        return localVariables;
    }

    public void addCursor(PLSQLCursorType cursorType) {
        List<PLSQLCursorType> curs = getCursors();
        if (!curs.contains(cursorType)) {
            curs.add(cursorType);
        }
    }

    public void addProcedure(ProcedureType procedureType) {
        List<ProcedureType> proc = getProcedures();
        if (!proc.contains(procedureType)) {
            proc.add(procedureType);
        }
    }

    public void addLocalVariable(FieldType var) {
        List<FieldType> vars = getLocalVariables();
        if (!vars.contains(var)) {
            vars.add(var);
        }
    }

    public DatabaseType getEnclosedType() {
        return null;
    }

    public void setEnclosedType(DatabaseType enclosedType) {
        //no-op
    }

    @Override
    public boolean isComposite() {
        return true;
    }

    public boolean isResolved() {
        // if any of the enclosed types are unresolved, then this package is unresolved

        if (types != null) {
            for (PLSQLType type : types) {
                if (!type.isResolved()) {
                    return false;
                }
            }
        }
        if (cursors != null) {
            for (PLSQLCursorType cursor : cursors) {
                if (!cursor.isResolved()) {
                    return false;
                }
            }
        }
        if (procedures != null) {
            for (ProcedureType procedure : procedures) {
                if (!procedure.isResolved()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }

    public String shortName() {
        return toString();
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}
