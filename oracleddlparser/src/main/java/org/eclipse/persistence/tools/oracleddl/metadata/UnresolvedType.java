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

public class UnresolvedType extends DatabaseTypeBase implements DatabaseType, DatabaseTypeVisitable {

    protected CompositeDatabaseType owningType;

    public UnresolvedType(String unresolvedTypeName) {
        super(unresolvedTypeName);
    }

    public CompositeDatabaseType getOwningType() {
        return owningType;
    }
    public void setOwningType(CompositeDatabaseType owningType) {
        this.owningType = owningType;
    }

    public String getUnresolvedTypeName() {
        return getTypeName();
    }

    public boolean isResolved() {
        return false;
    }

    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }

}
