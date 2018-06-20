/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

// Contributors:
//     Mike Norman - June 10 2011, created DDL parser package
package org.eclipse.persistence.tools.oracleddl.metadata;

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitable;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class PLSQLCursorType extends CompositeDatabaseTypeWithEnclosedType implements CompositeDatabaseType, DatabaseTypeVisitable {

    static final String REF_CURSOR = "REF CURSOR";
    protected String cursorName;
    protected PLSQLPackageType parentType;

    public PLSQLCursorType(String cursorName) {
        super(null);
        this.cursorName = cursorName;
    }

    @Override
    public String getTypeName() {
        if (enclosedType == null) {
            return REF_CURSOR + "(" + cursorName + ")";
        }
        return enclosedType.getTypeName();
    }

    public String getCursorName() {
        return cursorName;
    }

    public boolean isWeaklyTyped() {
        return enclosedType == null;
    }

    public PLSQLPackageType getParentType() {
        return parentType;
    }

    public void setParentType(PLSQLPackageType parentType) {
        this.parentType = parentType;
    }

    public boolean isResolved() {
        // if the dataType is unresolved, then this PLSQLCursor is a weakly-typed REF CURSOR
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isResolved();
    }

    public boolean isPLSQLCursorType() {
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
        StringBuilder sb = new StringBuilder(cursorName);
        sb.append(" IS ");
        sb.append(REF_CURSOR);
        if (enclosedType != null) {
            sb.append(" RETURN ");
            sb.append(enclosedType.getTypeName());
            if (!enclosedType.isResolved()) {
                sb.append("[u]");
            }
        }
        return sb.toString();
    }

}
