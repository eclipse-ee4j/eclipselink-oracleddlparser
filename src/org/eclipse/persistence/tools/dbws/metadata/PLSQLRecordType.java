package org.eclipse.persistence.tools.dbws.metadata;

public class PLSQLRecordType extends PLSQLType implements DatabaseTypeVisitable {

	//TODO - fix up later
	public PLSQLRecordType(String recordName) {
		super(recordName);
	}
	
	@Override
	public void addCompositeType(DatabaseType enclosedType) {
	}

	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}


}
