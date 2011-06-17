/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Mike Norman - June 10 2010, created DDL parser package
 ******************************************************************************/
package org.eclipse.persistence.tools.dbws.metadata;

//javase imports
import java.util.List;

public class PLSQLPackageType implements CompositeDatabaseType, DatabaseTypeVisitable {

	protected String packageName;
	protected List<PLSQLType> types;
	protected List<PLSQLCursorType> cursors;
	protected List<ProcedureType> procedures;

    public PLSQLPackageType() {
    }
    
    public PLSQLPackageType(String packageName) {
		setPackageName(packageName);
	}

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<PLSQLType> getTypes() {
		return types;
	}

	public List<PLSQLCursorType> getCursors() {
		return cursors;
	}

	public List<ProcedureType> getProcedures() {
		return procedures;
	}

	public void addCompositeType(DatabaseType enclosedType) {
    	// TODO
    }

	public String getTypeName() {
		return "PACKAGE " + packageName;
	}

	public boolean isComposite() {
		return true;
	}

	public boolean isResolved() {
		// if any of the enclosed types are unresolved, then this package is unresolved
		for (PLSQLType type : types) {
			if (!type.isResolved()) {
				return false;
			}
		}
		for (PLSQLCursorType cursor : cursors) {
			if (!cursor.isResolved()) {
				return false;
			}
		}
		for (ProcedureType procedure : procedures) {
			if (!procedure.isResolved()) {
				return false;
			}
		}
		return true;
	}

	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}

}