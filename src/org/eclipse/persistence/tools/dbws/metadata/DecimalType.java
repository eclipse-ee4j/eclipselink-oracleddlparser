package org.eclipse.persistence.tools.dbws.metadata;

public class DecimalType extends PrecisionType {
    
    public DecimalType() {
        super("DECIMAL", 5);
    }

    public DecimalType(long precision) {
        super("DECIMAL", precision);
    }

    public DecimalType(long precision, long scale) {
        super("DECIMAL", precision, scale);
    }

}