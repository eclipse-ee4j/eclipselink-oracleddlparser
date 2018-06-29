/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
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
//     Mike Norman - Feb 23 2012, created 'is-a' Testable interfaces
package org.eclipse.persistence.tools.oracleddl.metadata;

public abstract class DatabaseTypeTestableBase implements DatabaseTypeScalarTestable, DatabaseTypeCompositeTestable {

    // return false for all scalar and composite 'is-a' tests

    public boolean isScalar() {
        return false;
    }

    public boolean isComposite() {
        return false;
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
