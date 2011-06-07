package org.eclipse.persistence.tools.dbws.metadata;

public class RealType extends PrecisionType {
    
    public RealType() {
        super("REAL", 18);
    }

    public RealType(long precision) {
        super("REAL", precision);
    }

    public RealType(long precision, long scale) {
        super("REAL", precision, 0);
    }

}