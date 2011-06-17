package org.eclipse.persistence.tools.dbws.metadata;

public class PLSQLCollectionType extends PLSQLType implements DatabaseTypeVisitable {

	//TODO - fix up later
	public PLSQLCollectionType(String collectionName) {
		super(collectionName);
	}

	@Override
	public void addCompositeType(DatabaseType enclosedType) {
	}
	
	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}


}
