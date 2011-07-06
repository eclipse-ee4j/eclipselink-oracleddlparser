package org.eclipse.persistence.tools.database.metadata;


public class NestedTableType extends CompositeDatabaseTypeBase implements CompositeDatabaseType {

    protected String schema;

    public NestedTableType(String typeName) {
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
