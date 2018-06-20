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

public abstract class DatabaseTypeBase extends DatabaseTypeTestableBase implements Cloneable {

    protected String typeName;

    public DatabaseTypeBase(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isResolved() {
        return true;
    }

    public String shortName() {
        return toString();
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            //shouldn't ever happen
            return null;
        }
    }

    @Override
    public String toString() {
        return typeName;
    }
}
