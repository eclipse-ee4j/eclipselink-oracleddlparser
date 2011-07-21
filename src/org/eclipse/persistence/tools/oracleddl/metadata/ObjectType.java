package org.eclipse.persistence.tools.oracleddl.metadata;


public class ObjectType extends CompositeDatabaseTypeBase implements CompositeDatabaseType {

    protected String schema;
    
    public ObjectType(String typeName) {
        super(typeName);
    }

    public String getSchema() {
        return schema;
    }
    public void setSchema(String schema) {
       this.schema = schema;
    }

    public void accept(DatabaseTypeVisitor visitor) {
    }

    public void addCompositeType(DatabaseType enclosedType) {
    }

}
