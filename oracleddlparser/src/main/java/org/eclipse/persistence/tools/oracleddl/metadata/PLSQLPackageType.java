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
 *     Mike Norman - June 10 2011, created DDL parser package
 ******************************************************************************/
package org.eclipse.persistence.tools.oracleddl.metadata;

//javase imports
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitable;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class PLSQLPackageType extends DatabaseTypeTestableBase implements CompositeDatabaseType, DatabaseTypeVisitable {

	protected String packageName;
    protected String schema;
	protected List<PLSQLType> types;
	protected List<PLSQLCursorType> cursors;
	protected List<ProcedureType> procedures;
	protected List<FieldType> localVariables;

    public PLSQLPackageType() {
    }

    @Override
    public String getTypeName() {
        return "PACKAGE " + packageName;
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
    //actually setting packageName
    public void setTypeName(String typeName) {
        setPackageName(typeName);
    }

    /**
     * Return the schema name for this package.
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Set the schema name for this package.
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    public List<PLSQLType> getTypes() {
        if (types == null) {
            types = new ArrayList<PLSQLType>();
        }
		return types;
	}
    public void addType(PLSQLType type) {
        if (types == null) {
            types = new ArrayList<PLSQLType>();
        }
        if (!types.contains(type)) {
            types.add(type);
        }
    }

	public List<PLSQLCursorType> getCursors() {
        if (cursors == null) {
            cursors = new ArrayList<PLSQLCursorType>();
        }
		return cursors;
	}

	public List<ProcedureType> getProcedures() {
        if (procedures == null) {
            procedures = new ArrayList<ProcedureType>();
        }
		return procedures;
	}

    public List<FieldType> getLocalVariables() {
        if (localVariables == null) {
            localVariables = new ArrayList<FieldType>();
        }
        return localVariables;
    }

    public void addCursor(PLSQLCursorType cursorType) {
        if (getCursors() == null) {
            cursors = new ArrayList<PLSQLCursorType>();
        }
        if (!cursors.contains(cursorType)) {
            cursors.add(cursorType);
        }
    }

	public void addProcedure(ProcedureType procedureType) {
        if (getProcedures() == null) {
            procedures = new ArrayList<ProcedureType>();
        }
        if (!procedures.contains(procedureType)) {
            procedures.add(procedureType);
        }
    }

    public void addLocalVariable(FieldType var) {
        if (getLocalVariables() == null) {
            localVariables = new ArrayList<FieldType>();
        }
        if (!localVariables.contains(var)) {
            localVariables.add(var);
        }
    }

    public DatabaseType getEnclosedType() {
        return null;
    }
    public void setEnclosedType(DatabaseType enclosedType) {
        //no-op
    }

	public boolean isComposite() {
		return true;
	}

	public boolean isResolved() {
		// if any of the enclosed types are unresolved, then this package is unresolved

	    if (types != null) {
    		for (PLSQLType type : types) {
    			if (!type.isResolved()) {
    				return false;
    			}
    		}
	    }
        if (cursors != null) {
    		for (PLSQLCursorType cursor : cursors) {
    			if (!cursor.isResolved()) {
    				return false;
    			}
    		}
        }
        if (procedures != null) {
    		for (ProcedureType procedure : procedures) {
    			if (!procedure.isResolved()) {
    				return false;
    			}
    		}
        }
		return true;
	}

	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}

    public String shortName() {
        return toString();
    }

    @Override
    public String toString() {
        return getTypeName();
    }

}