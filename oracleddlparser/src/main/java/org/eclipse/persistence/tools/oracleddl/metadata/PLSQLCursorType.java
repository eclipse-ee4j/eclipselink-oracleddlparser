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

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitable;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class PLSQLCursorType extends DatabaseTypeTestableBase implements CompositeDatabaseType, DatabaseTypeVisitable {

    static final String REF_CURSOR = "REF CURSOR";
    protected String cursorName;
    protected DatabaseType dataType;

    public PLSQLCursorType(String cursorName) {
		this.cursorName = cursorName;
    }

    public String getCursorName() {
        return cursorName;
    }

    public DatabaseType getDataType() {
        return dataType;
    }
    public void setDataType(DatabaseType dataType) {
        this.dataType = dataType;
    }

	public void addCompositeType(DatabaseType enclosedType) {
		setDataType(enclosedType);
	}

	public boolean isWeaklyTyped() {
		return dataType == null;
	}

	public boolean isResolved() {
        // if the dataType is unresolved, then this PLSQLCursor is a weakly-typed REF CURSOR
		if (dataType == null) {
			return false;
		}
		return dataType.isResolved();
	}

	public boolean isComposite() {
		if (dataType == null) {
			// by default, a Field is 'simple' until otherwise configured 'composite'
			return false;
		}
		return dataType.isComposite();
	}

    //for all DatabaseTypeCompositeTestable 'is-a' tests, delegate to enclosed dataType

    public boolean isObjectTableType() {
        if (dataType == null) {
            return false;
        }
        return dataType.isObjectTableType();
    }

    public boolean isObjectType() {
        if (dataType == null) {
            return false;
        }
        return dataType.isObjectType();
    }

    public boolean isPLSQLCollectionType() {
        if (dataType == null) {
            return false;
        }
        return dataType.isPLSQLCollectionType();
    }

    public boolean isPLSQLCursorType() {
        if (dataType == null) {
            return false;
        }
        return dataType.isPLSQLCursorType();
    }

    public boolean isPLSQLRecordType() {
        if (dataType == null) {
            return false;
        }
        return dataType.isPLSQLRecordType();
    }

    public boolean isPLSQLSubType() {
        if (dataType == null) {
            return false;
        }
        return dataType.isPLSQLSubType();
    }

    public boolean isTableType() {
        if (dataType == null) {
            return false;
        }
        return dataType.isTableType();
    }

    public boolean isDbTableType() {
        if (dataType == null) {
            return false;
        }
        return dataType.isDbTableType();
    }

    public boolean isVArrayType() {
        if (dataType == null) {
            return false;
        }
        return dataType.isVArrayType();
    }

    public String getTypeName() {
		if (dataType == null) {
			return REF_CURSOR + "(" + cursorName + ")";
		}
		return dataType.getTypeName();
    }

	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}

    public String shortName() {
        return toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(cursorName);
        sb.append(" IS ");
        sb.append(REF_CURSOR);
        if (dataType != null) {
            sb.append(" RETURN ");
            sb.append(dataType.getTypeName());
            if (!dataType.isResolved()) {
                sb.append("[u]");
            }
        }
        return sb.toString();
    }

}