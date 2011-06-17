package org.eclipse.persistence.tools.dbws.metadata;

public class FunctionType extends ProcedureType {

	protected DatabaseType returnArgument;
	
	public FunctionType(String procedureName) {
		super(procedureName);
	}

	public DatabaseType getReturnArgument() {
		return returnArgument;
	}

	public void setReturnArgument(DatabaseType returnArgument) {
		this.returnArgument = returnArgument;
	}

	@Override
	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}

}
