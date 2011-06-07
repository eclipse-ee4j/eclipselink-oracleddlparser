package org.eclipse.persistence.tools.dbws.metadata;

public class NumericType extends PrecisionType {

    public NumericType() {
        super("NUMERIC", 38);
    }
    
    public NumericType(long precision) {
        super("NUMERIC", precision);
    }
    
    public NumericType(long precision, long scale) {
        super("NUMERIC", precision, scale);
    }

}