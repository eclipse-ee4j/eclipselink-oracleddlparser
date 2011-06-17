package org.eclipse.persistence.tools.dbws.metadata;

public class ArgumentType implements ComplexDatabaseType, DatabaseTypeVisitable {

    protected String argumentName;
    protected DatabaseType dataType;
    protected ArgumentTypeDirection direction;
    protected boolean optional = false;
    
    public ArgumentType(String argumentName) {
        this.argumentName = argumentName;
    }

    public String getArgumentName() {
        return argumentName;
    }

    public DatabaseType getDataType() {
        return dataType;
    }
    public void setDataType(DatabaseType dataType) {
        this.dataType = dataType;
    }

	public ArgumentTypeDirection getDirection() {
		return direction;
	}
	public void setDirection(ArgumentTypeDirection direction) {
		this.direction = direction;
	}

	public boolean isResolved() {
		if (dataType == null) {
			return false;
		}
		return dataType.isResolved();
	}

	public boolean isComplex() {
		if (dataType == null) {
			// by default, an argument is 'simple' until otherwise configured 'complex'
			return false;
		}
		return dataType.isComplex();
	}

    public String getTypeName() {
		if (dataType == null) {
			return null;
		}
		return dataType.getTypeName();
    }
    
    public boolean optional() {
    	return optional;
    }
    public void setOptional() {
    	optional = true;
    }
    public void unsetOptional() {
    	optional = false;
    }

	public void addEnclosedType(DatabaseType enclosedType) {
		setDataType(enclosedType);
	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(argumentName);
        sb.append("\t");
        if (dataType == null) {
            sb.append("unknown datatype");
        }
        else {
            sb.append(dataType.toString());
        }
        if (optional) {
            sb.append(" (OPTIONAL)");
        }
        return sb.toString();
    }

	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}
}