/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Mike Norman - June 10 2011, created DDL parser package
 ******************************************************************************/
package org.eclipse.persistence.tools.oracleddl.metadata;

public class RealType extends PrecisionType implements DatabaseTypeVisitable {

	static final String TYPENAME = "REAL";
	static final long DEFAULT_PRECISON = 18;
	
    public RealType() {
        super(TYPENAME, 18);
    }

    public RealType(long precision) {
        super(TYPENAME, precision);
    }

    public RealType(long precision, long scale) {
        super(TYPENAME, precision, 0);
    }

	@Override
	public long getDefaultPrecision() {
		return DEFAULT_PRECISON;
	}
	
	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}
}