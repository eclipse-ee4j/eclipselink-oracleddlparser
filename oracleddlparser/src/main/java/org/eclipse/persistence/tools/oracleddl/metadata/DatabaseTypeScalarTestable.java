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
//     Mike Norman - Mike Norman - Feb 23 2012, created 'is-a' Testable interfaces
package org.eclipse.persistence.tools.oracleddl.metadata;

/**
 * There are quite a few places where we have 'if (x instanceof YYY)'.
 * Consolidate all scalar 'is-a' test APIs
 *
 * @author mnorman
 */
public interface DatabaseTypeScalarTestable {

    //scalar 'is-a' tests

    public boolean isScalar(); // refers to singleton scalars from ScalarDatabaseTypeEnum

    /**
     * Indicates IntervalDayToSecond instance
     */
    public boolean isIntervalDayToSecond();

    /**
     * Indicates IntervalYearToMonth instance
     */
    public boolean isIntervalYearToMonth();

    /**
     * Indicates PrecisionType instance
     */
    public boolean isPrecisionType();

    /**
     * Indicates DecimalType instance
     */
    public boolean isDecimalType();

    /**
     * Indicates DoubleType instance
     */
    public boolean isDoubleType();

    /**
     * Indicates FloatType instance
     */
    public boolean isFloatType();

    /**
     * Indicates NumericType instance
     */
    public boolean isNumericType();

    /**
     * Indicates RealType instance
     */
    public boolean isRealType();

    /**
     * Indicates SizedType instance
     */
    public boolean isSizedType();

    /**
     * Indicates BinaryType instance
     */
    public boolean isBinaryType();

    /**
     * Indicates BlobType instance
     */
    public boolean isBlobType();

    /**
     * Indicates LongRawType instance
     */
    public boolean isLongRawType();

    /**
     * Indicates RawType instance
     */
    public boolean isRawType();

    /**
     * Indicates CharType instance
     */
    public boolean isCharType();

    /**
     * Indicates NCharType instance
     */
    public boolean isNCharType();

    /**
     * Indicates ClobType instance
     */
    public boolean isClobType();

    /**
     * Indicates NClobType instance
     */
    public boolean isNClobType();

    /**
     * Indicates TimeStampType instance
     */
    public boolean isTimeStampType();

    /**
     * Indicates URowIdType instance
     */
    public boolean isURowIdType();

    /**
     * Indicates VarCharType instance
     */
    public boolean isVarCharType();

    /**
     * Indicates VarChar2Type instance
     */
    public boolean isVarChar2Type();

    /**
     * Indicates Long instance
     */
    public boolean isLongType();

    /**
     * Indicates NVarChar2Type instance
     */
    public boolean isNVarChar2Type();
}
