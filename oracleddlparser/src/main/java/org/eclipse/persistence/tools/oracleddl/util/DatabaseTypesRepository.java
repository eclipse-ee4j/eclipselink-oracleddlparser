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

//javase imports
import java.util.HashMap;
import java.util.Map;

import org.eclipse.persistence.tools.oracleddl.metadata.DatabaseType;
import org.eclipse.persistence.tools.oracleddl.parser.DDLParser;

/**
 * A utility class to help {@link DDLParser} keep track of {@link DatabaseType}'s.
 *
 * @author mnorman
 */
public class DatabaseTypesRepository {

    // hold on to MetadataTypes that have already been built
    protected Map<String, DatabaseType> repository = new HashMap<String, DatabaseType>();

    public Map<String, DatabaseType> setDatabaseType(String namespaceKey, DatabaseType databaseType) {
        repository.put(namespaceKey, databaseType);
        return repository;
    }

    public DatabaseType getDatabaseType(String namespaceKey) {
        return repository.get(namespaceKey);
    }
}
