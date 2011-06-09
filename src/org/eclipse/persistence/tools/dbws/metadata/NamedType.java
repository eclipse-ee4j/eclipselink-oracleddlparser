package org.eclipse.persistence.tools.dbws.metadata;

public class NamedType implements ComplexDatabaseType {

    protected String typeName;
    protected DatabaseType nestedType;
    
	public NamedType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}
	public DatabaseType getNestedType() {
		return nestedType;
	}

	public boolean isSimple() {
		return false;
	}
	public boolean isComplex() {
		return true;
	}

	public void addEnclosedType(DatabaseType enclosedType) {
		nestedType = enclosedType;
	}
	
    public String toString() {
        return typeName;
    }

}