package org.eclipse.persistence.tools.dbws.metadata;

public class FloatType extends PrecisionType {
    
    public FloatType() {
        super("FLOAT", 38);
    }

    public FloatType(long precision) {
        super("FLOAT", precision);
    }

    public FloatType(long precision, long scale) {
        super("FLOAT", precision, 0);
    }

}