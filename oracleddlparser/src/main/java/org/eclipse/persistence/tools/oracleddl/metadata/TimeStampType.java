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

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitable;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class TimeStampType extends SizedType implements DatabaseTypeVisitable {

    public static final String TYPENAME = "TIMESTAMP";
    static final long DEFAULT_SIZE = 6l;

    protected boolean withTimeZone = false;
    protected boolean withLocalTimeZone = false;

    public TimeStampType() {
        super(TYPENAME, DEFAULT_SIZE);
    }
    public TimeStampType(long size) {
        super(TYPENAME, size);
    }

    @Override
    public long getDefaultSize() {
        return DEFAULT_SIZE;
    }

    public boolean isWithTimeZone() {
        return withTimeZone;
    }
    public void setWithTimeZone() {
        withTimeZone = true;
    }

    public boolean isWithLocalTimeZone() {
        return withLocalTimeZone;
    }
    public void setWithLocalTimeZone() {
        withLocalTimeZone = true;
    }

    @Override
    public boolean isTimeStampType() {
        return true;
    }
    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }
}
