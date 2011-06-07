package org.eclipse.persistence.tools.dbws.metadata;

public abstract class PrecisionType implements SimpleDatabaseType {

    protected String typeName;
    protected long precision;
    protected long scale;
    
    public PrecisionType(String typeName, long precision) {
        this.typeName = typeName;
        this.precision = precision;
        this.scale = 0;
    }
    
    public PrecisionType(String typeName, long precision, long scale) {
        this(typeName, precision);
        this.scale = scale;
    }
    
    public String getTypeName() {
        return typeName;
    }

    public long getPrecision() {
        return precision;
    }

    public long getScale() {
        return scale;
    }
    public boolean isSimple() {
        return true;
    }
    public boolean isComplex() {
        return false;
    }

}