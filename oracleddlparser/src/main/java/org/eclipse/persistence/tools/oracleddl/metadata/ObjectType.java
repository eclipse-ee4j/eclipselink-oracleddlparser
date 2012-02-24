package org.eclipse.persistence.tools.oracleddl.metadata;

//javase imports
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//DDL parser imports
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class ObjectType extends CompositeDatabaseTypeBase implements CompositeDatabaseType {

    protected String schema;
    protected List<FieldType> fields = new ArrayList<FieldType>();

    public ObjectType(String typeName) {
        super(typeName);
    }

    public String getSchema() {
        return schema;
    }
    public void setSchema(String schema) {
       this.schema = schema;
    }

    /**
     * Returns the list of FieldType instances.
     */
    public List<FieldType> getFields() {
        return fields;
    }

    @Override
    public void addCompositeType(DatabaseType enclosedType) {
        fields.add((FieldType)enclosedType);
    }

    @Override
    public boolean isResolved() {
        // if any of the field types are unresolved, then this objectType is unresolved
        for (FieldType fType : fields) {
            if (!fType.isResolved()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isObjectType() {
        return true;
    }

    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String shortName() {
        return typeName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" (");
        for (Iterator<FieldType> iterator = fields.iterator(); iterator.hasNext(); ) {
            FieldType f = iterator.next();
            sb.append("\n\t");
            sb.append(f.toString());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("\n)");
        return sb.toString();
    }

}