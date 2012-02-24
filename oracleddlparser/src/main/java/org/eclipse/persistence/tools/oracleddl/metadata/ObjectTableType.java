package org.eclipse.persistence.tools.oracleddl.metadata;

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class ObjectTableType extends CompositeDatabaseTypeWithEnclosedType implements CompositeDatabaseType {

    protected String schema;

    public ObjectTableType(String typeName) {
        super(typeName);
    }

    public String getSchema() {
        return schema;
    }
    public void setSchema(String schema) {
       this.schema = schema;
    }

    @Override
    public boolean isResolved() {
        // if enclosedType is unresolved, then this type is unresolved
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isResolved();
    }

    @Override
    public boolean isObjectTableType() {
        return true;
    }

    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }
}