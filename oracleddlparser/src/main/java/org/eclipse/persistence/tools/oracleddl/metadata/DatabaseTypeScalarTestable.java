/*
 * Copyright (c) 2012, 2025 Oracle and/or its affiliates. All rights reserved.
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

    boolean isScalar(); // refers to singleton scalars from ScalarDatabaseTypeEnum

    /**
     * Indicates IntervalDayToSecond instance
     */
    boolean isIntervalDayToSecond();

    /**
     * Indicates IntervalYearToMonth instance
     */
    boolean isIntervalYearToMonth();

    /**
     * Indicates PrecisionType instance
     */
    boolean isPrecisionType();

    /**
     * Indicates DecimalType instance
     */
    boolean isDecimalType();

    /**
     * Indicates DoubleType instance
     */
    boolean isDoubleType();

    /**
     * Indicates FloatType instance
     */
    boolean isFloatType();

    /**
     * Indicates NumericType instance
     */
    boolean isNumericType();

    /**
     * Indicates RealType instance
     */
    boolean isRealType();

    /**
     * Indicates SizedType instance
     */
    boolean isSizedType();

    /**
     * Indicates BinaryType instance
     */
    boolean isBinaryType();

    /**
     * Indicates BlobType instance
     */
    boolean isBlobType();

    /**
     * Indicates LongRawType instance
     */
    boolean isLongRawType();

    /**
     * Indicates RawType instance
     */
    boolean isRawType();

    /**
     * Indicates CharType instance
     */
    boolean isCharType();

    /**
     * Indicates NCharType instance
     */
    boolean isNCharType();

    /**
     * Indicates ClobType instance
     */
    boolean isClobType();

    /**
     * Indicates NClobType instance
     */
    boolean isNClobType();

    /**
     * Indicates TimeStampType instance
     */
    boolean isTimeStampType();

    /**
     * Indicates URowIdType instance
     */
    boolean isURowIdType();

    /**
     * Indicates VarCharType instance
     */
    boolean isVarCharType();

    /**
     * Indicates VarChar2Type instance
     */
    boolean isVarChar2Type();

    /**
     * Indicates Long instance
     */
    boolean isLongType();

    /**
     * Indicates NVarChar2Type instance
     */
    boolean isNVarChar2Type();
}
