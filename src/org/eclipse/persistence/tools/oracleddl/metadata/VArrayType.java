package org.eclipse.persistence.tools.oracleddl.metadata;

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class VArrayType extends CompositeDatabaseTypeBase implements CompositeDatabaseType {

    protected String schema;
    protected long size;
    protected DatabaseType enclosedType;

    public VArrayType(String typeName) {
        super(typeName);
    }

    public String getSchema() {
        return schema;
    }
    public void setSchema(String schema) {
       this.schema = schema;
    }

    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }

    public DatabaseType getEnclosedType() {
        return enclosedType;
    }
    public void addCompositeType(DatabaseType enclosedType) {
        this.enclosedType = enclosedType;
    }

    public void accept(DatabaseTypeVisitor visitor) {
    }

}
