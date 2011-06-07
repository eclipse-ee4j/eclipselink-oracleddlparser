package org.eclipse.persistence.tools.dbws.metadata;

public enum UnsizedType implements SimpleDatabaseType {

    BIGINT_TYPE("BIGINT"),
    BINARY_DOUBLE_TYPE("BINARY_DOUBLE"),
    BINARY_FLOAT_TYPE("BINARY_FLOAT"),
    BINARY_INTEGER_TYPE("BINARY_INTEGER"),
    BIT_TYPE("BIT"),
    BOOLEAN_TYPE("BOOLEAN"),
    DATALINK_TYPE("DATALINK"),
    DATE_TYPE("DATE"),
    INTEGER_TYPE("INTEGER"),
    LONGVARBINARY_TYPE("LONGVARBINARY"),
    LONGVARCHAR_TYPE("LONGVARCHAR"),
    MLSLABEL_TYPE("MLSLABEL"),
    NATURAL_TYPE("NATURAL"),
    PLS_INTEGER_TYPE("PLS_INTEGER"),
    POSITIVE_TYPE("POSITIVE"),
    ROWID_TYPE("ROWID"),
    SIGN_TYPE("SIGNTYPE"),
    SMALLINT_TYPE("SMALLINT"),
    TIME_TYPE("TIME"),
    TIMESTAMP_TYPE("TIMESTAMP"),
    NULL_TYPE("NULL"),
    ;

    private final String typeName;
    
    UnsizedType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public boolean isSimple() {
        return true;
    }

    public boolean isComplex() {
        return false;
    }

}