package org.eclipse.persistence.tools.dbws.metadata;

public abstract class SizedType implements SimpleDatabaseType {

    protected String typeName;
    protected long size;
    
    public SizedType(String typeName, long size) {
        super();
        this.typeName = typeName;
        this.size = size;
    }

    public String getTypeName() {
        return typeName;
    }

    public long getSize() {
        return size;
    }

    public boolean isSimple() {
        return true;
    }

    public boolean isComplex() {
        return false;
    }

}