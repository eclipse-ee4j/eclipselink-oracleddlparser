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

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitable;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class PLSQLSubType extends PLSQLType implements DatabaseTypeVisitable {

    protected boolean notNull = false;
    protected boolean hasRange = false;
    protected long rangeStart;
    protected long rangeEnd;

    public PLSQLSubType(String typeName) {
        super(typeName);
    }

    public boolean isResolved() {
        // if enclosedType is unresolved, then this argument is unresolved
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isResolved();
    }

    public boolean isNotNull() {
        return notNull;
    }
    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public boolean hasRange() {
        return hasRange;
    }
    public void setHasRange(boolean hasRange) {
        this.hasRange = hasRange;
    }

    public long getRangeStart() {
        return rangeStart;
    }
    public void setRangeStart(long rangeStart) {
        this.rangeStart = rangeStart;
    }

    public long getRangeEnd() {
        return rangeEnd;
    }
    public void setRangeEnd(long rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    @Override
    public boolean isPLSQLSubType() {
        return true;
    }

    public String shortName() {
        StringBuilder sb = new StringBuilder(typeName);
        if (!enclosedType.isResolved()) {
            sb.append("[u]");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (typeName != null) {
            sb.append(typeName);
        }
        if (enclosedType == null) {
            sb.append("<null/>");
        }
        else if (!enclosedType.isResolved()) {
            sb.append("[u]");
        }
        return sb.toString();
    }

    @Override
    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }
}
