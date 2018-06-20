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

public abstract class PrecisionType extends ScalarDatabaseTypeBase implements ScalarDatabaseType {

    protected long precision;
    protected long scale;

    public PrecisionType(String typeName, long precision) {
        super(typeName);
        this.precision = precision;
        this.scale = 0;
    }

    public PrecisionType(String typeName, long precision, long scale) {
        this(typeName, precision);
        this.scale = scale;
    }

    public long getPrecision() {
        return precision;
    }
    public long getScale() {
        return scale;
    }

    public abstract long getDefaultPrecision();

    public boolean isSimple() {
        return true;
    }

    @Override
    public boolean isPrecisionType() {
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (precision != getDefaultPrecision()) {
            sb.append('(');
            sb.append(precision);
            if (scale != 0) {
                sb.append(',');
                sb.append(scale);
            }
            sb.append(')');
        }
        return sb.toString();
    }
}
