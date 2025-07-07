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

public abstract class SizedType extends ScalarDatabaseTypeBase implements ScalarDatabaseType {

    protected long size;

    public SizedType(String typeName, long size) {
        super(typeName);
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public abstract long getDefaultSize();

    @Override
    public boolean isSizedType() {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (size != getDefaultSize()) {
            sb.append('(');
            sb.append(size);
            sb.append(')');
        }
        return sb.toString();
    }

}
