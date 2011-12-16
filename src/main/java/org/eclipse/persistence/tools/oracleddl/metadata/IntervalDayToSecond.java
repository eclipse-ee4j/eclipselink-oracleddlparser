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

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitable;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class IntervalDayToSecond extends ScalarDatabaseTypeBase implements ScalarDatabaseType, DatabaseTypeVisitable {

	static final String TYPENAME_DAYPART = "INTERVAL DAY";
	static final String TYPENAME_SECONDPART = "TO SECOND";
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
	
	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}
}