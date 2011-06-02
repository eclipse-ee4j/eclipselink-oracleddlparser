package org.eclipse.persistence.tools.dbws.metadata;

public class FieldType implements MetadataType {

    protected String name;
    protected MetadataType dataType;

    public FieldType(String name) {
        this.name = name;
    }

    public boolean isNested() {
        return false;
    }

    public String getName() {
        return name;
    }

    public MetadataType getDataType() {
        return dataType;
    }
    public void setDataType(MetadataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name);
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