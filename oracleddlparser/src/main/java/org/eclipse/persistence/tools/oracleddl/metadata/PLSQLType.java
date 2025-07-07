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

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class PLSQLType extends CompositeDatabaseTypeWithEnclosedType implements CompositeDatabaseType {

    protected PLSQLPackageType parentType;

    public PLSQLType(String typeName) {
        super(typeName);
    }

    public PLSQLPackageType getParentType() {
        return parentType;
    }
    public void setParentType(PLSQLPackageType parentType) {
        this.parentType = parentType;
    }

    public boolean isComposite() {
        return true;
    }

    @Override
    public void accept(DatabaseTypeVisitor visitor) {
    }

    @Override
    public boolean isPLSQLType() {
        return true;
    }

}
