package org.eclipse.persistence.tools.dbws.metadata;

public class RealType extends PrecisionType {

	static long DEFAULT_PRECISON = 18;
    public RealType() {
        super("REAL", 18);
    }

    public RealType(long precision) {
        super("REAL", precision);
    }

    public RealType(long precision, long scale) {
        super("REAL", precision, 0);
    }

	@Override
	public long getDefaultPrecision() {
		return DEFAULT_PRECISON;
	}

}