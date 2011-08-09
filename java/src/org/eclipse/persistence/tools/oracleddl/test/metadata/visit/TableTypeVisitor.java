/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     David McCann - July 22, 2011 - 2.4 - Initial implementation
 ******************************************************************************/
package org.eclipse.persistence.tools.oracleddl.test.metadata.visit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.tools.oracleddl.metadata.BaseDatabaseTypeVisitor;
import org.eclipse.persistence.tools.oracleddl.metadata.FieldType;
import org.eclipse.persistence.tools.oracleddl.metadata.TableType;

/**
 * Visitor for use with TableType.  The visit methods simply
 * gather all relevant information such that it can be 
 * returned as a String when visiting is complete. 
 */
public class TableTypeVisitor extends BaseDatabaseTypeVisitor {
    public String tableName;
    public String schema;
    public List<String> columnData = new ArrayList<String>();
    public List<String> pkColumns = new ArrayList<String>();
    
    public void beginVisit(TableType databaseType) {
        tableName = databaseType.getTableName();
        schema = databaseType.getSchema();
    }
    
    public void beginVisit(FieldType databaseType) {
        if (databaseType.notNull()) {
            columnData.add(databaseType.getFieldName() + "\t" + databaseType.getTypeName() + " (NOT NULL)");
        } else {
            columnData.add(databaseType.getFieldName() + "\t" + databaseType.getTypeName());
        }
        if (databaseType.pk()) {
            pkColumns.add(databaseType.getFieldName());
        }
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder("TABLE ");
        if (schema != null) {
            sb.append(schema);
            sb.append(".");
        }
        sb.append(tableName);
        sb.append(" (\n");
        for (String col : columnData) {
            sb.append("\t");
            sb.append(col);
            sb.append("\n");
        }
        if (pkColumns.size() > 0) {
            sb.append("\t");
            sb.append("PRIMARY KEY (");
            for (int i = 0; i < pkColumns.size();) {
                sb.append(pkColumns.get(i));
                if (++i < pkColumns.size()) {
                    sb.append(',');
                }
            }
            sb.append(")\n");
        }
        sb.append(")");
        return sb.toString();
    }
}
