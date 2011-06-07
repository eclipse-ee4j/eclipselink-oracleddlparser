package org.eclipse.persistence.tools.dbws.metadata;

public class FieldType implements DatabaseType {

    protected String fieldName;
    protected DatabaseType dataType;

    public FieldType(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getTypeName() {
        return dataType.getTypeName();
    }

    public boolean isSimple() {
        return dataType.isSimple();
    }
    public boolean isComplex() {
        return dataType.isComplex();
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