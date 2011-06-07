package org.eclipse.persistence.tools.dbws.metadata;

public class DoubleType extends PrecisionType {
    
    public DoubleType() {
        super("DOUBLE", 38);
    }

    public DoubleType(long precision) {
        super("DOUBLE", precision);
    }

    public DoubleType(long precision, long scale) {
        super("DOUBLE", precision, 0);
    }

}