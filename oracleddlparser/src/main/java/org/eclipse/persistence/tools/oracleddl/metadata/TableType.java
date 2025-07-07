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
package org.eclipse.persistence.tools.oracleddl.metadata;

//javase imports
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitable;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.DatabaseTypeVisitor;

public class TableType extends CompositeDatabaseTypeBase implements CompositeDatabaseType, DatabaseTypeVisitable {

    public static final String TYPENAME = "TABLE";
    protected String tableName;
    protected String schema;
    protected List<FieldType> columns = new ArrayList<>();
    protected boolean iot = false;

    public TableType() {
        super(null);
    }

    public TableType(String tableName) {
        super(null);
        setTableName(tableName);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
        super.typeName = TYPENAME + " " + tableName;
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

    public List<FieldType> getColumns() {
        return columns;
    }
    public void addColumn(DatabaseType databaseType) {
        if (databaseType.isFieldType()) {
            columns.add((FieldType)databaseType);
        } else {
            // if not a FieldType instance we may need to update
            // an existing column with a resolved type
            for (FieldType col : columns) {
                if (col.getEnclosedType().getTypeName().equals(databaseType.getTypeName())) {
                    col.setEnclosedType(databaseType);
                }
            }
        }
    }

    public void setIOT(boolean iot) {
        this.iot = iot;
    }

    public boolean iot() {
       return iot;
    }

    public int numberOfPKColumns() {
        int pkColumns = 0;
        for (FieldType col : columns) {
            if (col.pk()) {
                pkColumns++;
            }
        }
        return pkColumns;
    }

    @Override
    public boolean isResolved() {
        // if any of the columns types are unresolved, then this table is unresolved
        for (FieldType fType : columns) {
            if (!fType.isResolved()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isTableType() {
        return true;
    }

    @Override
    public String shortName() {
        return typeName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(TYPENAME);
        sb.append(" ");
        if (schema != null) {
            sb.append(schema);
            sb.append(".");
        }
        sb.append(tableName);
        sb.append(" (\n");
        for (FieldType col : columns) {
            sb.append("\t");
            sb.append(col.toString());
            sb.append("\n");
        }
        int numPkCols = numberOfPKColumns();
        if (numPkCols > 0) {
            sb.append("\t");
            sb.append("PRIMARY KEY (");
            for (int i = 0, pkColCount = 0, len = columns.size(); i < len; i++) {
                FieldType col = columns.get(i);
                if (col.pk) {
                    sb.append(col.fieldName);
                    pkColCount++;
                    if (pkColCount < numPkCols) {
                        sb.append(',');
                    }
                }
            }
            sb.append(")\n");
        }
        sb.append(")");
        if (iot) {
            sb.append(" ORGANIZATION INDEX");
        }
        return sb.toString();
    }

    public void accept(DatabaseTypeVisitor visitor) {
        visitor.visit(this);
    }
}
