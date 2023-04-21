/*
 * Copyright (c) 2020, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

module org.eclipse.persistence.oracleddlparser {

    requires java.sql;

    exports org.eclipse.persistence.tools.oracleddl.metadata;
    exports org.eclipse.persistence.tools.oracleddl.metadata.visit;
    exports org.eclipse.persistence.tools.oracleddl.parser;
    exports org.eclipse.persistence.tools.oracleddl.util;

    uses org.eclipse.persistence.tools.oracleddl.util.DBMSMetadataSessionTransforms;

    provides org.eclipse.persistence.tools.oracleddl.util.DBMSMetadataSessionTransforms
            with org.eclipse.persistence.tools.oracleddl.util.DBMSMetadataSessionTransformsFactory;
}
