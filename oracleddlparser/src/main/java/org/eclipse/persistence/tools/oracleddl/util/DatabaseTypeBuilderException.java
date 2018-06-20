/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

// Contributors:
//     Mike Norman - June 10 2011, created DDL parser package
package org.eclipse.persistence.tools.oracleddl.util;

/**
 * <p>
 * <b>Purpose</b>: Any exception raised by DatabaseTypeBuilder should be this exception class.
 */
public class DatabaseTypeBuilderException extends RuntimeException {

    protected Throwable internalException;

    //exceptions are only built via the public static methods

    protected DatabaseTypeBuilderException(String message) {
        super(message);
    }

    // no DBMSMetadataSessionTransforms in META-INF/services
    static final String NO_TRANSFORMS = "no TRANSFORMS_FACTORY found";
    public static DatabaseTypeBuilderException noTransformsFactories() {
        String message = NO_TRANSFORMS;  // TODO i18n-ify
        DatabaseTypeBuilderException dtbe = new DatabaseTypeBuilderException(message);
        return dtbe;
    }

    // DBMSMetadataSessionTransforms found in META-INF/services, but no properties returned
    static final String NO_TRANSFORMS_PROPERTIES =
        "TRANSFORMS_FACTORY found but no transform properties returned";
    public static DatabaseTypeBuilderException noTransformsProperties() {
        String message = NO_TRANSFORMS_PROPERTIES;  // TODO i18n-ify
        DatabaseTypeBuilderException dtbe = new DatabaseTypeBuilderException(message);
        return dtbe;
    }

    /**
     * PUBLIC:
     * Return the internal exception.
     * DatabaseTypeBuilder captures JDBC exceptions
     * The internal exception can be accessed if required.
     */
    public Throwable getInternalException() {
        return internalException;
    }

    /**
     * Used to specify the internal exception.
     */
    protected void setInternalException(Throwable t) {
        internalException = t;
        if (getCause() == null) {
            initCause(t);
        }
    }
}
