/*
 * Copyright (c) 2011, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

// Contributors:
//     Mike Norman - June 10 2011, created DDL parser package
package org.eclipse.persistence.tools.oracleddl.metadata;

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitable;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class FieldType extends DatabaseTypeTestableBase implements Cloneable,
    CompositeDatabaseType, DatabaseTypeVisitable {

    protected String fieldName;
    protected DatabaseType enclosedType;
    protected boolean notNull = false;
    protected boolean pk = false;

    public FieldType(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
    //actually setting fieldName
    public void setTypeName(String typeName) {
        this.fieldName = typeName;
    }

    public DatabaseType getEnclosedType() {
        return enclosedType;
    }
    public void setEnclosedType(DatabaseType enclosedType) {
        this.enclosedType = enclosedType;
    }

    public boolean isResolved() {
        // if dataType is unresolved, then this field is unresolved
        if (enclosedType == null) {
            return false;
        }
        return enclosedType.isResolved();
    }

    public boolean isComposite() {
        if (enclosedType == null) {
            // by default, a Field is 'simple' until otherwise configured 'composite'
            return false;
        }
        return enclosedType.isComposite();
    }

    public String getTypeName() {
        if (enclosedType == null) {
            return null;
        }
        return enclosedType.getTypeName();
    }

    public boolean notNull() {
        return notNull;
    }
    public void setNotNull() {
        this.notNull = true;
    }
    public void unSetNotNull() {
        this.notNull = false;
    }

    public boolean pk() {
        return pk;
    }
    public void setPk() {
        this.pk = true;
    }
    public void unSetPk() {
        this.pk = false;
    }

    public String shortName() {
        return toString();
    }

    public boolean isFieldType() {
        return true;
    }

    //for the following DatabaseTypeCompositeTestable 'is-a' tests, delegate to enclosed dataType

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
    public FieldType clone() {
        try {
            return (FieldType)super.clone();
        }
        catch (CloneNotSupportedException e){
            return null;

        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(fieldName);
        sb.append(" ");
        if (enclosedType == null) {
            sb.append("<null/>");
        }
        else {
            sb.append(enclosedType.shortName());
        }
        if (notNull) {
            sb.append(" (NOT NULL)");
        }
        return sb.toString();
    }

    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }
}
