/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

// Contributors:
//     Mike Norman - Feb 23 2012, created 'is-a' Testable interfaces
package org.eclipse.persistence.tools.oracleddl.metadata;

public class CompositeDatabaseTypeWithEnclosedType extends CompositeDatabaseTypeBase {

    public CompositeDatabaseTypeWithEnclosedType(String typeName) {
        super(typeName);
    }

    protected DatabaseType enclosedType;

    public DatabaseType getEnclosedType() {
        return enclosedType;
    }

    public void setEnclosedType(DatabaseType enclosedType) {
        this.enclosedType = enclosedType;
    }

}
