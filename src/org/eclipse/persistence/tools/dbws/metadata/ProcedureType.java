package org.eclipse.persistence.tools.dbws.metadata;

//javase imports
import java.util.ArrayList;
import java.util.List;

public class ProcedureType extends CompositeDatabaseTypeBase implements CompositeDatabaseType, DatabaseTypeVisitable {

    protected String procedureName;
    protected String schema;
    protected List<ArgumentType> arguments = new ArrayList<ArgumentType>();

    public ProcedureType(String procedureName) {
		super(null);
		setProcedureName(procedureName);
	}

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
        super.typeName = "PROCEDURE " + procedureName;
    }

    public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
       this.schema = schema;
    }

	public List<ArgumentType> getArguments() {
		return arguments;
	}

	@Override
	public void addCompositeType(DatabaseType enclosedType) {
		//TODO
	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PROCEDURE ");
        if (schema != null) {
            sb.append(schema);
            sb.append(".");
        }
        sb.append(procedureName);
        sb.append(" (\n");
        
        return sb.toString();
    }

	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}
}