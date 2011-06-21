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

public enum ScalarDatabaseTypeEnum implements ScalarDatabaseType, DatabaseTypeVisitable {

    BFILE_TYPE("BFILE"),
    BIGINT_TYPE("BIGINT"),
    BINARY_DOUBLE_TYPE("BINARY_DOUBLE"),
    BINARY_FLOAT_TYPE("BINARY_FLOAT"),
    BINARY_INTEGER_TYPE("BINARY_INTEGER"),
    BIT_TYPE("BIT"),
    BOOLEAN_TYPE("BOOLEAN"),
    DATALINK_TYPE("DATALINK"),
    DATE_TYPE("DATE"),
    INTEGER_TYPE("INTEGER"),
    LONGVARBINARY_TYPE("LONGVARBINARY"),
    LONGVARCHAR_TYPE("LONGVARCHAR"),
    MLSLABEL_TYPE("MLSLABEL"),
    NATURAL_TYPE("NATURAL"),
    PLS_INTEGER_TYPE("PLS_INTEGER"),
    POSITIVE_TYPE("POSITIVE"),
    ROWID_TYPE("ROWID"),
    SIGN_TYPE("SIGNTYPE"),
    SIMPLE_INTEGER_TYPE("SIMPLE INTEGER"),
    SMALLINT_TYPE("SMALLINT"),
    TIME_TYPE("TIME"),
    TIMESTAMP_TYPE("TIMESTAMP"),
    NULL_TYPE("NULL"),
    ;

    private final String typeName;
    
    ScalarDatabaseTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public boolean isComposite() {
        return false;
    }

	public boolean isResolved() {
		return true;
	}

	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}

}