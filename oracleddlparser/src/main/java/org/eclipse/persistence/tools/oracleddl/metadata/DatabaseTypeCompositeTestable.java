/*
 * Copyright (c) 2012, 2023 Oracle and/or its affiliates. All rights reserved.
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

    public boolean isComposite();

    /**
     * Indicates FieldType instance
     */
    public boolean isFieldType();

    /**
     * Indicates ArgumentType instance
     */
    public boolean isArgumentType();

    /**
     * Indicates ROWTYPEType instance
     */
    public boolean isROWTYPEType();
    /**
     * Indicates TYPEType instance
     */
    public boolean isTYPEType();

    /**
     * Indicates ObjectTableType instance
     */
    public boolean isObjectTableType();

    /**
     * Indicates ObjectType instance
     */
    public boolean isObjectType();

    /**
     * Indicates PLSQLType instance
     */
    public boolean isPLSQLType();

    /**
     * Indicates PLSQLCollectionType instance
     */
    public boolean isPLSQLCollectionType();

    /**
     * Indicates PLSQLPLSQLCursorType instance
     */
    public boolean isPLSQLCursorType();

    /**
     * Indicates PLSQLRecordType instance
     */
    public boolean isPLSQLRecordType();

    /**
     * Indicates PLSQLSubType instance
     */
    public boolean isPLSQLSubType();

    /**
     * Indicates TableType instance
     */
    public boolean isTableType();

    /**
     * Indicates DbTableType instance
     */
    public boolean isDbTableType();

    /**
     * Indicates VArrayType instance
     */
    public boolean isVArrayType();

    //following 'is-a' tests don't really belong here, but
    //they definitely don't belong on DatabaseTypeScalarTestable
    public boolean isProcedureType();

    public boolean isFunctionType();
}
