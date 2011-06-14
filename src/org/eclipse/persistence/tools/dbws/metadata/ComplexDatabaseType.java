package org.eclipse.persistence.tools.dbws.metadata;

public interface ComplexDatabaseType extends DatabaseType {
	
	public void addEnclosedType(DatabaseType enclosedType);
}