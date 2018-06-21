/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

package org.eclipse.persistence.tools.oracleddl.metadata;

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class VArrayType extends CompositeDatabaseTypeWithEnclosedType implements CompositeDatabaseType {

    protected String schema;
    protected long size;

    public VArrayType(String typeName) {
        super(typeName);
    }

    public String getSchema() {
        return schema;
    }
    public void setSchema(String schema) {
       this.schema = schema;
    }

    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public boolean isResolved() {
        // if enclosedType is unresolved, then this VArray is unresolved
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isResolved();
    }

    @Override
    public boolean isVArrayType() {
        return true;
    }

    @Override
    public String shortName() {
        return super.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" VARRAY(");
        if (size > 0) {
            sb.append(size);
        }
        sb.append(") OF ");
        sb.append(enclosedType.shortName());
        if (!enclosedType.isResolved()) {
            sb.append("[u]");
        }
        return sb.toString();
    }

    public void accept(DatabaseTypeVisitor visitor) {
    }
}
