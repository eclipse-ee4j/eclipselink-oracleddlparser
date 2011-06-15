package org.eclipse.persistence.tools.dbws.parser.test;

//javase imports
import java.util.ArrayList;
import java.util.List;

//DDL parser imports
import org.eclipse.persistence.tools.dbws.metadata.visit.DatabaseTypeDefaultListener;

public class UnresolvedTypesListener extends DatabaseTypeDefaultListener {

	protected List<String> unresolvedTypes = new ArrayList<String>();
	
	public List<String> getUnresolvedTypes() {
		return unresolvedTypes;
	}

	@Override
	public void handleUnresolvedSizedType(String unresolvedTypeName, long size) {
		unresolvedTypes.add(unresolvedTypeName);
	}

	@Override
	public void handleUnresolvedType(String unresolvedTypeName) {
		unresolvedTypes.add(unresolvedTypeName);
	}
}