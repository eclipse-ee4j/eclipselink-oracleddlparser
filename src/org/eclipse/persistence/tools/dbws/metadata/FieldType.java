package org.eclipse.persistence.tools.dbws.metadata;

public class FieldType implements ComplexDatabaseType {

    protected String fieldName;
    protected DatabaseType dataType;
    protected boolean notNull = false;
    protected boolean pk = false;

    public FieldType(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getTypeName() {
        return dataType.getTypeName();
    }

    public boolean isSimple() {
    	if (dataType != null) {
    		return dataType.isSimple();
    	}
    	return false;
    }
    public boolean isComplex() {
    	if (dataType != null) {
    		return dataType.isComplex();
    	}
    	return true;
    }
    
    public boolean notNull() {
    	return notNull;
    }
    public void setNotNull() {
    	this.notNull = true;
    }
    public void setAllowNull() {
    	this.notNull = false;
    }
    
    public boolean pk() {
    	return pk;
    }
    public void setPk() {
    	this.pk = true;
    }
    public void unSetPk() {
    	this.pk = false;
    }

	public void addEnclosedType(DatabaseType enclosedType) {
		setDataType(enclosedType);
	}

    public String getFieldName() {
        return fieldName;
    }

    public DatabaseType getDataType() {
        return dataType;
    }
    public void setDataType(DatabaseType dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(fieldName);
        sb.append("\t");
        if (dataType == null) {
            sb.append("unknown datatype");
        }
        else {
            sb.append(dataType.toString());
        }
        if (notNull) {
            sb.append(" (NOT NULL)");
        }
        return sb.toString();
    }
}