package org.eclipse.persistence.tools.dbws.metadata;

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