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
 * Consolidate all composite 'is-a' test APIs
 *
 * @author mnorman
 */
public interface DatabaseTypeCompositeTestable {

    //composite 'is-a' tests

    boolean isComposite();

    /**
     * Indicates FieldType instance
     */
    boolean isFieldType();

    /**
     * Indicates ArgumentType instance
     */
    boolean isArgumentType();

    /**
     * Indicates ROWTYPEType instance
     */
    boolean isROWTYPEType();
    /**
     * Indicates TYPEType instance
     */
    boolean isTYPEType();

    /**
     * Indicates ObjectTableType instance
     */
    boolean isObjectTableType();

    /**
     * Indicates ObjectType instance
     */
    boolean isObjectType();

    /**
     * Indicates PLSQLType instance
     */
    boolean isPLSQLType();

    /**
     * Indicates PLSQLCollectionType instance
     */
    boolean isPLSQLCollectionType();

    /**
     * Indicates PLSQLPLSQLCursorType instance
     */
    boolean isPLSQLCursorType();

    /**
     * Indicates PLSQLRecordType instance
     */
    boolean isPLSQLRecordType();

    /**
     * Indicates PLSQLSubType instance
     */
    boolean isPLSQLSubType();

    /**
     * Indicates TableType instance
     */
    boolean isTableType();

    /**
     * Indicates DbTableType instance
     */
    boolean isDbTableType();

    /**
     * Indicates VArrayType instance
     */
    boolean isVArrayType();

    //following 'is-a' tests don't really belong here, but
    //they definitely don't belong on DatabaseTypeScalarTestable
    boolean isProcedureType();

    boolean isFunctionType();
}
