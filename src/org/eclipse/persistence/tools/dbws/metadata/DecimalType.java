package org.eclipse.persistence.tools.dbws.metadata;

public class DecimalType extends PrecisionType {

	static long DEFAULT_PRECISON = 5l;
    public DecimalType() {
        super("DECIMAL", DEFAULT_PRECISON);
    }

    public DecimalType(long precision) {
        super("DECIMAL", precision);
    }

    public DecimalType(long precision, long scale) {
        super("DECIMAL", precision, scale);
    }

	@Override
	public long getDefaultPrecision() {
		return DEFAULT_PRECISON;
	}
}