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
package org.eclipse.persistence.tools.oracleddl.metadata.visitors;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.tools.oracleddl.metadata.BaseDatabaseTypeVisitor;
import org.eclipse.persistence.tools.oracleddl.metadata.FieldType;
import org.eclipse.persistence.tools.oracleddl.metadata.PrecisionType;
import org.eclipse.persistence.tools.oracleddl.metadata.TableType;

/**
 * Visitor for use with TableType. 
 */
public class TableTypeVisitor extends BaseDatabaseTypeVisitor {
    protected String tableName;
    protected String schema;
    protected List<ColumnData> columnData = new ArrayList<ColumnData>();
    
    public String getTableName() { 
        return tableName;
    }
    public String getSchema() { 
        return schema; 
    }
    public List<ColumnData> getColumnData() { 
        return columnData;
    }

    public void beginVisit(TableType databaseType) {
        tableName = databaseType.getTableName();
        schema = databaseType.getSchema();
    }
    
    public void beginVisit(FieldType databaseType) {
        if (databaseType.getDataType() instanceof PrecisionType) {
            PrecisionType pType = (PrecisionType) databaseType.getDataType();
            columnData.add(new ColumnData(databaseType.getFieldName(), databaseType.getTypeName(), databaseType.notNull(), databaseType.pk(), pType.getPrecision(), pType.getScale()));
        } else {
            columnData.add(new ColumnData(databaseType.getFieldName(), databaseType.getTypeName(), databaseType.notNull(), databaseType.pk()));
        }
    }
    
    /**
     * Inner class used to capture table column information.
     *  
     */
    public class ColumnData {
        private String fieldName;
        private String typeName;
        private boolean nullable;
        private boolean pk;
        private long precision;
        private long scale;
        
        ColumnData(String fieldName, String typeName, boolean nullable, boolean pk) {
            this(fieldName, typeName, nullable, pk, 0, 0);
        }
        
        ColumnData(String fieldName, String typeName, boolean nullable, boolean pk, long precision, long scale) {
            this.fieldName = fieldName;
            this.typeName = typeName;
            this.nullable = nullable;
            this.pk = pk;
            this.precision = precision;
            this.scale = scale;
        }
        
        public String getFieldName() { return fieldName; }
        public String getTypeName() { return typeName; }
        public boolean isNullable() { return nullable; }
        public boolean isPk() { return pk; }        
        public long getPrecision() { return precision; }
        public long getScale() { return scale; }
        
        // TODO:  support JDBC type via visit
        public int getJDBCType() {
            if (typeName.equals("NUMERIC")) {
                return Types.NUMERIC;
            }
            if (typeName.equals("VARCHAR")) {
                return Types.VARCHAR;
            }
            if (typeName.equals("VARCHAR2")) {
                return Types.VARCHAR;
            }
            if (typeName.equals("DECIMAL")) {
                return Types.DECIMAL;
            }
            if (typeName.equals("CHAR")) {
                return Types.CHAR;
            }
            if (typeName.equals("NCHAR")) {
                return Types.NCHAR;
            }
            if (typeName.equals("FLOAT")) {
                return Types.FLOAT;
            }
            if (typeName.equals("REAL")) {
                return Types.REAL;
            }
            if (typeName.equals("DOUBLE")) {
                return Types.DOUBLE;
            }
            if (typeName.equals("BINARY")) {
                return Types.BINARY;
            }
            if (typeName.equals("BLOB")) {
                return Types.BLOB;
            }
            if (typeName.equals("CLOB")) {
                return Types.CLOB;
            }
            if (typeName.equals("NCLOB")) {
                return Types.NCLOB;
            }
            if (typeName.equals("RAW")) {
                // TODO:  verify this
                return Types.VARBINARY;
            }
            if (typeName.equals("LONG RAW")) {
                // TODO:  verify this
                return Types.LONGVARBINARY;
            }
            if (typeName.equals("UROWID")) {
                // TODO:  verify this (if set to 'ROWID', the toString of the ROWID
                //        class is marshalled, i.e. 'oracle.sql.ROWID@1cbfa42'
                return Types.VARCHAR;
            }
            return Types.OTHER;
        }
    }
}