package org.eclipse.persistence.tools.dbws.metadata;

//javase imports
import java.util.ArrayList;
import java.util.List;

public class TableType implements ComplexDatabaseType {

    protected String name;
    protected String schema;
    protected List<FieldType> columns = new ArrayList<FieldType>();
    
    public TableType(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return "TABLE";
    }

    public boolean isSimple() {
        return false;
    }
    public boolean isComplex() {
        return true;
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
        StringBuilder sb = new StringBuilder("table ");
        if (schema != null) {
            sb.append(schema);
            sb.append(".");
        }
        sb.append(name);
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
}
