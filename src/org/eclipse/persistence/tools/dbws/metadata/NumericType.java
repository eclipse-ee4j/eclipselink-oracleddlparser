package org.eclipse.persistence.tools.dbws.metadata;

public class NumericType extends PrecisionType {

	static long DEFAULT_PRECISON = 38l;
    public NumericType() {
        super("NUMERIC", DEFAULT_PRECISON);
    }
    
    public NumericType(long precision) {
        super("NUMERIC", precision);
    }
    
    public NumericType(long precision, long scale) {
        super("NUMERIC", precision, scale);
    }

	@Override
	public long getDefaultPrecision() {
		return DEFAULT_PRECISON;
	}

}