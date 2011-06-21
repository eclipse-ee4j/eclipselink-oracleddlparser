package org.eclipse.persistence.tools.dbws.metadata;

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