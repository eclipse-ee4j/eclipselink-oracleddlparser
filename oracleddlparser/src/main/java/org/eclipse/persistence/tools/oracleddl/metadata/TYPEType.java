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

//DDL parser imports
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitable;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class TYPEType extends CompositeDatabaseTypeWithEnclosedType implements CompositeDatabaseType, DatabaseTypeVisitable {

    public TYPEType(String typeName) {
        super(typeName);
    }

    public String getTypeName() {
        if (isResolved()) {
            return enclosedType.getTypeName();
        }
        return typeName;
    }

    public boolean isResolved() {
        // if enclosedType is unresolved, then this argument is unresolved
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isResolved();
    }

    @Override
    public boolean isTYPEType() {
        return true;
    }

    public String shortName() {
        StringBuilder sb = new StringBuilder(typeName);
        if (!enclosedType.isResolved()) {
            sb.append("[u]");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (typeName != null) {
            sb.append(typeName);
        }
        if (enclosedType == null) {
            sb.append("<null/>");
        }
        else if (!enclosedType.isResolved()) {
            sb.append("[u]");
        }
        return sb.toString();
    }

    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }
}
