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

public class PLSQLCursorType implements CompositeDatabaseType, DatabaseTypeVisitable {

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

	public boolean isWeaklyTypes() {
		return dataType == null;
	}

	public boolean isResolved() {
        // if the dataType is unresolved, then this PLSQLCursor is unresolved
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

    public String getTypeName() {
		if (dataType == null) {
			return null;
		}
		return dataType.getTypeName();
    }

	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}

}