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

//DDL parser imports
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitable;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;
import static org.eclipse.persistence.tools.oracleddl.metadata.ArgumentTypeDirection.RETURN;

public class ArgumentType extends DatabaseTypeTestableBase implements CompositeDatabaseType, DatabaseTypeVisitable {

    protected String argumentName;
    protected DatabaseType enclosedType;
    protected ArgumentTypeDirection direction;
    protected boolean optional = false;

    public ArgumentType(String argumentName) {
        this.argumentName = argumentName;
    }

    public String getArgumentName() {
        return argumentName;
    }

    public DatabaseType getEnclosedType() {
        return enclosedType;
    }
    public void setEnclosedType(DatabaseType enclosedType) {
        this.enclosedType = enclosedType;
    }

	public ArgumentTypeDirection getDirection() {
		return direction;
	}
	public void setDirection(ArgumentTypeDirection direction) {
		this.direction = direction;
	}

	public boolean isResolved() {
        // if dataType is unresolved, then this argument is unresolved
		if (enclosedType == null) {
			return false;
		}
		return enclosedType.isResolved();
	}

	public boolean isComposite() {
		if (enclosedType == null) {
			// by default, an argument is 'simple' until otherwise configured 'composite'
			return false;
		}
		return enclosedType.isComposite();
	}

    @Override
    public boolean isArgumentType() {
        return true;
    }

    public String getTypeName() {
		if (enclosedType == null) {
			return null;
		}
		return enclosedType.getTypeName();
    }

    public boolean optional() {
    	return optional;
    }
    public void setOptional() {
    	optional = true;
    }
    public void unsetOptional() {
    	optional = false;
    }

    public String shortName() {
        StringBuilder sb = new StringBuilder();
        if (argumentName != null) {
            sb.append(argumentName);
        }
        else {
            if (direction == RETURN) {
                sb.append(RETURN);
            }
        }
        sb.append(" ");
        if (direction != RETURN) {
            sb.append(direction.toString());
            sb.append(" ");
        }
        if (enclosedType == null) {
            sb.append("<null/>");
        }
        else {
            sb.append(enclosedType.shortName());
        }
        return sb.toString();
    }

    //for all DatabaseTypeCompositeTestable 'is-a' tests, delegate to enclosed dataType

    public boolean isObjectTableType() {
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isObjectTableType();
    }

    public boolean isObjectType() {
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isObjectType();
    }

    public boolean isPLSQLCollectionType() {
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isPLSQLCollectionType();
    }

    public boolean isPLSQLCursorType() {
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isPLSQLCursorType();
    }

    public boolean isPLSQLRecordType() {
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isPLSQLRecordType();
    }

    public boolean isPLSQLSubType() {
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isPLSQLSubType();
    }

    public boolean isTableType() {
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isTableType();
    }

    public boolean isDbTableType() {
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isDbTableType();
    }

    public boolean isVArrayType() {
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isVArrayType();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (argumentName != null) {
            sb.append(argumentName);
        }
        else {
            if (direction == RETURN) {
                sb.append(RETURN);
            }
        }
        sb.append(" ");
        if (direction != RETURN) {
            sb.append(direction.toString());
            sb.append(" ");
        }
        if (enclosedType == null) {
            sb.append("<null/>");
        }
        else {
            sb.append(enclosedType.toString());
        }
        if (optional) {
            sb.append(" (opt)");
        }
        return sb.toString();
    }

	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
    }
}