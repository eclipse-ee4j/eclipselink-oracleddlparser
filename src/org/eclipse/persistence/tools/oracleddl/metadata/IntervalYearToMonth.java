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

public class IntervalYearToMonth extends ScalarDatabaseTypeBase implements ScalarDatabaseType, DatabaseTypeVisitable {

	static final String TYPENAME_YEARPART = "INTERVAL YEAR";
	static final String TYPENAME_MONTHPART = "TO MONTH";
	static final long DEFAULT_YEAR_PRECISION = 2L;
	
	protected long yearPrecision;

	public IntervalYearToMonth() {	
		super(null);
		this.yearPrecision = DEFAULT_YEAR_PRECISION;
		this.typeName = TYPENAME_YEARPART + " " + TYPENAME_MONTHPART;
	}
	public IntervalYearToMonth(long yearPrecision) {
		super(null);
		this.yearPrecision = yearPrecision;
		this.typeName = TYPENAME_YEARPART + "(" + yearPrecision + ") " + TYPENAME_MONTHPART;
	}

	public long getYearPrecision() {
		return yearPrecision;
	}
	
	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}
}