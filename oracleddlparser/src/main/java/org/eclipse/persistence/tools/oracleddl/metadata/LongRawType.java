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

public class LongRawType extends BlobType {

    public static final String TYPENAME = "LONG RAW";

    public LongRawType() {
        super(TYPENAME, BlobType.DEFAULT_SIZE);
    }
    public LongRawType(long size) {
        super(TYPENAME, size);
    }

    @Override
    public boolean isLongRawType() {
        return true;
    }

    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }

}
