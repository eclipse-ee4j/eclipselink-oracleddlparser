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
 *     Mike Norman - June 10 2010, created DDL parser package
 ******************************************************************************/
package org.eclipse.persistence.tools.dbws.metadata;

//javase imports
import java.util.ArrayList;
import java.util.List;

public class TableType extends ComplexDatabaseTypeBase implements ComplexDatabaseType, DatabaseTypeVisitable {

    protected String tableName;
    protected String schema;
    protected List<FieldType> columns = new ArrayList<FieldType>();
    
    public TableType(String tableName) {
		super(null);
		setTableName(tableName);
	}

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
        super.typeName = "TABLE " + tableName;
    }

    public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
       this.schema = schema;
    }

    public void addEnclosedType(DatabaseType enclosedType) {
        columns.add((FieldType)enclosedType);
    }
    
    public List<FieldType> getColumns() {
    	return columns;
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
    public String toString() {
        StringBuilder sb = new StringBuilder("TABLE ");
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
        return sb.toString();
    }

	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}
}