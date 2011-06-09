package org.eclipse.persistence.tools.dbws.metadata;

public class DoubleType extends PrecisionType {

	static long DEFAULT_PRECISON = 38l;
    public DoubleType() {
        super("DOUBLE", DEFAULT_PRECISON);
    }

    public DoubleType(long precision) {
        super("DOUBLE", precision);
    }

    public DoubleType(long precision, long scale) {
        super("DOUBLE", precision, 0);
    }

	@Override
	public long getDefaultPrecision() {
		return DEFAULT_PRECISON;
	}
}