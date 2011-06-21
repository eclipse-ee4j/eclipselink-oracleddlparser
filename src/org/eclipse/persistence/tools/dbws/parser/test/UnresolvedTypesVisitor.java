package org.eclipse.persistence.tools.dbws.parser.test;

//javase imports
import java.util.ArrayList;
import java.util.List;

//DDL parser imports
import org.eclipse.persistence.tools.dbws.metadata.BaseDatabaseTypeVisitor;
import org.eclipse.persistence.tools.dbws.metadata.UnresolvedSizedType;
import org.eclipse.persistence.tools.dbws.metadata.UnresolvedType;

public class UnresolvedTypesVisitor extends BaseDatabaseTypeVisitor {

	protected List<String> unresolvedTypes = new ArrayList<String>();
	
	public List<String> getUnresolvedTypes() {
		return unresolvedTypes;
	}

	public void visit(UnresolvedType unresolvedType) {
		unresolvedTypes.add(unresolvedType.getTypeName());
	}

	public void visit(UnresolvedSizedType unresolvedType) {
		unresolvedTypes.add(unresolvedType.getTypeName());
	}
	
}