/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
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
package org.eclipse.persistence.tools.oracleddl.metadata;

//javase imports
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//DDL parser imports
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitable;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class PLSQLRecordType extends PLSQLType implements DatabaseTypeVisitable {

    protected List<FieldType> fields = new ArrayList<FieldType>();

    public PLSQLRecordType(String recordName) {
        super(recordName);
    }

    /**
     * Returns the list of FieldType instances.
     *
     * @return the list of FieldType instances
     */
    public List<FieldType> getFields() {
        return fields;
    }

    public void addField(DatabaseType databaseType) {
        if (databaseType.isFieldType()) {
            fields.add((FieldType)databaseType);
        } else {
            // if not a FieldType instance we may need to update
            // an existing field with a resolved type
            for (FieldType fld : fields) {
                if (fld.getEnclosedType().getTypeName().equals(databaseType.getTypeName())) {
                    fld.setEnclosedType(databaseType);
                }
            }
        }
    }

    @Override
    public DatabaseType getEnclosedType() {
        return null;
    }
    @Override
    public void setEnclosedType(DatabaseType enclosedType) {
        //no-op
    }

    @Override
    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isResolved() {
        // if any of the field types are unresolved, then this record is unresolved
        for (FieldType fType : fields) {
            if (!fType.isResolved()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isPLSQLRecordType() {
        return true;
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
