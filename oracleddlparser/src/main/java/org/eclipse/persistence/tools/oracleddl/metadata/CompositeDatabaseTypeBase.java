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

public abstract class CompositeDatabaseTypeBase extends DatabaseTypeBase {

    public CompositeDatabaseTypeBase(String typeName) {
        super(typeName);
    }

    @Override
    public boolean isComposite() {
        return true;
    }

    public abstract DatabaseType getEnclosedType();

    public abstract void setEnclosedType(DatabaseType enclosedType);

}
