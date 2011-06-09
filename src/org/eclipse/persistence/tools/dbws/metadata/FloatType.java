package org.eclipse.persistence.tools.dbws.metadata;

public class FloatType extends PrecisionType {

	static long DEFAULT_PRECISON = 38l;
    public FloatType() {
        super("FLOAT", DEFAULT_PRECISON);
    }

    public FloatType(long precision) {
        super("FLOAT", precision);
    }

    public FloatType(long precision, long scale) {
        super("FLOAT", precision, 0);
    }

	@Override
	public long getDefaultPrecision() {
		return DEFAULT_PRECISON;
	}
}