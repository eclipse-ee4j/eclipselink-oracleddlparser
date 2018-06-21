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

public class IntervalDayToSecond extends ScalarDatabaseTypeBase implements ScalarDatabaseType, DatabaseTypeVisitable {

    public static final String TYPENAME_DAYPART = "INTERVAL DAY";
    public static final String TYPENAME_SECONDPART = "TO SECOND";
    static final long DEFAULT_DAY_PRECISION = 2L;
    static final long DEFAULT_SECOND_PRECISION = 6L;

    protected long dayPrecision;
    protected long secondPrecision;

    public IntervalDayToSecond() {
        super(null);
        this.dayPrecision = DEFAULT_DAY_PRECISION;
        this.secondPrecision = DEFAULT_SECOND_PRECISION;
        this.typeName = TYPENAME_DAYPART + " " + TYPENAME_SECONDPART;
    }
    public IntervalDayToSecond(long dayPrecision) {
        super(null);
        this.dayPrecision = dayPrecision;
        this.secondPrecision = DEFAULT_SECOND_PRECISION;
        this.typeName = TYPENAME_DAYPART + "(" + dayPrecision + ") " + TYPENAME_SECONDPART;
    }
    public IntervalDayToSecond(long dayPrecision, long secondPrecision) {
        super(null);
        this.dayPrecision = dayPrecision;
        this.secondPrecision = secondPrecision;
        this.typeName = TYPENAME_DAYPART + "(" + dayPrecision + ") " + TYPENAME_SECONDPART +
            "(" + secondPrecision + ")";
    }

    public long getDayPrecision() {
        return dayPrecision;
    }
    public long getSecondPrecision() {
        return secondPrecision;
    }

    @Override
    public boolean isIntervalDayToSecond() {
        return true;
    }

    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }
}
