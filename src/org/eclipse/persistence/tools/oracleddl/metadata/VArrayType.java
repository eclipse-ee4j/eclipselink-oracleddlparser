package org.eclipse.persistence.tools.oracleddl.metadata;


public class VArrayType extends CompositeDatabaseTypeBase implements CompositeDatabaseType {

    protected String schema;

    public VArrayType(String typeName) {
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
