/*
 * Copyright (c) 2018, 2025 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

package org.eclipse.persistence.tools.oracleddl.metadata;

//javase imports
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//DDL parser imports
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class ObjectType extends CompositeDatabaseTypeBase implements CompositeDatabaseType {

    protected String schema;
    protected List<FieldType> fields = new ArrayList<>();

    public ObjectType(String typeName) {
        super(typeName);
    }

    public String getSchema() {
        return schema;
    }
    public void setSchema(String schema) {
       this.schema = schema;
    }

    public DatabaseType getEnclosedType() {
        return null;
    }
    public void setEnclosedType(DatabaseType enclosedType) {
        //no-op
    }

    /**
     * Returns the list of FieldType instances.
     */
    public List<FieldType> getFields() {
        return fields;
    }
    public void addField(FieldType field) {
        fields.add(field);
    }

    @Override
    public boolean isResolved() {
        // if any of the field types are unresolved, then this objectType is unresolved
        for (FieldType fType : fields) {
            if (!fType.isResolved()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isObjectType() {
        return true;
    }

    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String shortName() {
        return typeName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" (");
        for (Iterator<FieldType> iterator = fields.iterator(); iterator.hasNext(); ) {
            FieldType f = iterator.next();
            sb.append("\n\t");
            sb.append(f.toString());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("\n)");
        return sb.toString();
    }

}
