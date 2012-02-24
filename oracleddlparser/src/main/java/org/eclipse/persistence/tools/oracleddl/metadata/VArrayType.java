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

    @Override
    public boolean isResolved() {
        // if enclosedType is unresolved, then this VArray is unresolved
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isResolved();
    }

    @Override
    public boolean isVArrayType() {
        return true;
    }

    @Override
    public String shortName() {
        return super.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" VARRAY(");
        if (size > 0) {
            sb.append(size);
        }
        sb.append(") OF ");
        sb.append(enclosedType.shortName());
        if (!enclosedType.isResolved()) {
            sb.append("[u]");
        }
        return sb.toString();
    }

    public void accept(DatabaseTypeVisitor visitor) {
    }

}
