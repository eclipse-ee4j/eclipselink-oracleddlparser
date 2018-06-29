/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
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

public class CharType extends SizedType implements DatabaseTypeVisitable {

    public static final String TYPENAME = "CHAR";
    static final long DEFAULT_SIZE = 1l;

    public CharType() {
        super(TYPENAME, DEFAULT_SIZE);
    }
    public CharType(long size) {
        super(TYPENAME, size);
    }
    public CharType(String typename, long size) {
        super(typename, size);
    }

    @Override
    public boolean isCharType() {
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
