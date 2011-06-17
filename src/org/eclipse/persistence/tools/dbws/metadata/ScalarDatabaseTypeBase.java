package org.eclipse.persistence.tools.dbws.metadata;

public abstract class ScalarDatabaseTypeBase extends DatabaseTypeBase {

	public ScalarDatabaseTypeBase(String typeName) {
		super(typeName);
	}

	@Override
	public boolean isComposite() {
		return false;
	}
}