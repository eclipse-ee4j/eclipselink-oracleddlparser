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

public enum ScalarDatabaseTypeEnum implements ScalarDatabaseType, DatabaseTypeVisitable {

    BFILE_TYPE("BFILE"),
    BIGINT_TYPE("BIGINT"), //not a native Oracle datatype (only used for JDBC conversion)
    BINARY_DOUBLE_TYPE("BINARY_DOUBLE"),
    BINARY_FLOAT_TYPE("BINARY_FLOAT"),
    BINARY_INTEGER_TYPE("BINARY_INTEGER"),
    BIT_TYPE("BIT"), //not a native Oracle datatype (only used for JDBC conversion)
    BOOLEAN_TYPE("BOOLEAN"),
    DATALINK_TYPE("DATALINK"),
    DATE_TYPE("DATE"),
    INTEGER_TYPE("INTEGER"),
    LONGVARBINARY_TYPE("LONGVARBINARY"), //not a native Oracle datatype (only used for JDBC conversion)
    LONGVARCHAR_TYPE("LONGVARCHAR"), //not a native Oracle datatype (only used for JDBC conversion)
    MLSLABEL_TYPE("MLSLABEL"),
    NATURAL_TYPE("NATURAL"),
    PLS_INTEGER_TYPE("PLS_INTEGER"),
    POSITIVE_TYPE("POSITIVE"),
    ROWID_TYPE("ROWID"),
    SIGN_TYPE("SIGNTYPE"),
    SIMPLE_INTEGER_TYPE("SIMPLE_INTEGER"),
    SIMPLE_DOUBLE_TYPE("SIMPLE_DOUBLE"),
    SIMPLE_FLOAT_TYPE("SIMPLE_FLOAT"),
    SYS_REFCURSOR_TYPE("SYS_REFCURSOR"),
    SMALLINT_TYPE("SMALLINT"),
    TIME_TYPE("TIME"), //not a native Oracle datatype (only used for JDBC conversion)
    NULL_TYPE("NULL"),
    ;

    private final String typeName;

    ScalarDatabaseTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        //no-op: cant change name of enums
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

    public String shortName() {
        return toString();
    }

    public boolean isScalar() {
        //of all the implementors of ScalarDatabaseType,
        //this is actually the only class that returns true
        return true;
    }

    public boolean isIntervalDayToSecond() {
        return false;
    }

    public boolean isIntervalYearToMonth() {
        return false;
    }

    public boolean isPrecisionType() {
        return false;
    }

    public boolean isDecimalType() {
        return false;
    }

    public boolean isDoubleType() {
        return false;
    }

    public boolean isFloatType() {
        return false;
    }

    public boolean isNumericType() {
        return false;
    }

    public boolean isRealType() {
        return false;
    }

    public boolean isSizedType() {
        return false;
    }

    public boolean isBinaryType() {
        return false;
    }

    public boolean isBlobType() {
        return false;
    }

    public boolean isLongRawType() {
        return false;
    }

    public boolean isRawType() {
        return false;
    }

    public boolean isCharType() {
        return false;
    }

    public boolean isNCharType() {
        return false;
    }

    public boolean isClobType() {
        return false;
    }

    public boolean isNClobType() {
        return false;
    }

    public boolean isTimeStampType() {
        return false;
    }

    public boolean isURowIdType() {
        return false;
    }

    public boolean isVarCharType() {
        return false;
    }

    public boolean isVarChar2Type() {
        return false;
    }

    public boolean isLongType() {
        return false;
    }

    public boolean isNVarChar2Type() {
        return false;
    }

    public boolean isFieldType() {
        return false;
    }

    public boolean isArgumentType() {
        return false;
    }

    public boolean isROWTYPEType() {
        return false;
    }

    public boolean isTYPEType() {
        return false;
    }

    public boolean isObjectTableType() {
        return false;
    }

    public boolean isObjectType() {
        return false;
    }

    public boolean isPLSQLType() {
        return false;
    }

    public boolean isPLSQLCollectionType() {
        return false;
    }

    public boolean isPLSQLCursorType() {
        return false;
    }

    public boolean isPLSQLRecordType() {
        return false;
    }

    public boolean isPLSQLSubType() {
        return false;
    }

    public boolean isTableType() {
        return false;
    }

    public boolean isDbTableType() {
        return false;
    }

    public boolean isVArrayType() {
        return false;
    }

    public boolean isProcedureType() {
        return false;
    }

    public boolean isFunctionType() {
        return false;
    }

}