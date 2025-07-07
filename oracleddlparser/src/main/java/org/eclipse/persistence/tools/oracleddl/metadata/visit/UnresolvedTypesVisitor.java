/*
 * Copyright (c) 2011, 2025 Oracle and/or its affiliates. All rights reserved.
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
//     Mike Norman - June 10 2011, created DDL parser package
package org.eclipse.persistence.tools.oracleddl.metadata.visit;

//javase imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//DDL parser imports
import org.eclipse.persistence.tools.oracleddl.metadata.UnresolvedSizedType;
import org.eclipse.persistence.tools.oracleddl.metadata.UnresolvedType;

public class UnresolvedTypesVisitor extends BaseDatabaseTypeVisitor {

    protected List<UnresolvedType> unresolvedTypes = new ArrayList<>();
    protected Map<String, List<UnresolvedType>> uniq = new HashMap<>();

    public UnresolvedTypesVisitor() {
    }

    public List<UnresolvedType> getUnresolvedTypes() {
        return unresolvedTypes;
    }

    public void visit(UnresolvedType unresolvedType) {
        String typeName = unresolvedType.getTypeName();
        List<UnresolvedType> similarUnresolvedTypes = uniq.computeIfAbsent(typeName, k -> new ArrayList<>());
        boolean addToUnresolvedTypes = false;
        for (UnresolvedType similarUnresolvedType : similarUnresolvedTypes) {
            if (unresolvedType.getOwningType() != similarUnresolvedType.getOwningType()) {
                addToUnresolvedTypes = true;
                break;
            }
        }
        if (addToUnresolvedTypes || similarUnresolvedTypes.isEmpty()) {
            similarUnresolvedTypes.add(unresolvedType);
            unresolvedTypes.add(unresolvedType);
        }
    }

    public void visit(UnresolvedSizedType unresolvedType) {
        visit((UnresolvedType)unresolvedType);
    }
}
