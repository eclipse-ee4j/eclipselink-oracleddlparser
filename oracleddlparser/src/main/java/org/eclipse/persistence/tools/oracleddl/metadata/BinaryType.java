/*
 * Copyright (c) 2011, 2023 Oracle and/or its affiliates. All rights reserved.
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

public class BinaryType extends SizedType implements DatabaseTypeVisitable {

    public static final String TYPENAME = "BINARY";
    static final long DEFAULT_SIZE = 0l;

    public BinaryType() {
        super(TYPENAME, DEFAULT_SIZE);
    }
    public BinaryType(long size) {
        super(TYPENAME, size);
    }

    @Override
    public boolean isBinaryType() {
        return true;
    }

    @Override
    public long getDefaultSize() {
        return DEFAULT_SIZE;
    }

    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }
}
