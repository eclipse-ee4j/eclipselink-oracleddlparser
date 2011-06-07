package org.eclipse.persistence.tools.dbws.metadata;

public class FieldType implements ComplexDatabaseType {

    protected String fieldName;
    protected DatabaseType dataType;

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
        return sb.toString();
    }
}