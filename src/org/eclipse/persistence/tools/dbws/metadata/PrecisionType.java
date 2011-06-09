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
    
    public abstract long getDefaultPrecision();

    public long getScale() {
        return scale;
    }
    public boolean isSimple() {
        return true;
    }
    public boolean isComplex() {
        return false;
    }

	public String toString() {
		StringBuilder sb = new StringBuilder(typeName);
		if (precision != getDefaultPrecision()) {
			sb.append('(');
			sb.append(precision);
			if (scale != 0) {
				sb.append(',');
				sb.append(scale);
			}
			sb.append(')');
		}
		return sb.toString();
	}

}