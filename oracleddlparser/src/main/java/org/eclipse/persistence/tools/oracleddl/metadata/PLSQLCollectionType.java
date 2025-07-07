/*
 * Copyright (c) 2011, 2025 Oracle and/or its affiliates. All rights reserved.
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

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitable;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class PLSQLCollectionType extends PLSQLType implements DatabaseTypeVisitable {

    protected DatabaseType indexType;
    protected boolean indexed = false;

    public PLSQLCollectionType(String collectionName) {
        super(collectionName);
    }

    public boolean isIndexed() {
        return indexed;
    }
    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }

    public DatabaseType getIndexType() {
        return indexType;
    }
    public void setIndexType(DatabaseType indexType) {
        this.indexType = indexType;
    }

    @Override
    public DatabaseType getEnclosedType() {
        return enclosedType;
    }
    @Override
    public void setEnclosedType(DatabaseType enclosedType) {
        this.enclosedType = enclosedType;
    }

    @Override
    public boolean isResolved() {
        // if the nestedType is unresolved, then this PLSQLCollection is unresolved
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isResolved();
    }

    @Override
    public boolean isPLSQLCollectionType() {
        return true;
    }

    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }
}
