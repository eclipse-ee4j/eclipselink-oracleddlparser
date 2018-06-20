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

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class LongType extends VarChar2Type {

    public static final String TYPENAME = "LONG";

    public LongType() {
        super(TYPENAME, VarCharType.DEFAULT_SIZE);
    }
    public LongType(long size) {
        super(TYPENAME, size);
    }

    @Override
    public boolean isLongType() {
        return true;
    }

    @Override
    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }
}
