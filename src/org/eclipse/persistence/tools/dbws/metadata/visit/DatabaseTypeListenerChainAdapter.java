package org.eclipse.persistence.tools.dbws.metadata.visit;

//javase imports
import java.util.ArrayList;
import java.util.List;

public class DatabaseTypeListenerChainAdapter implements DatabaseTypeListener {

    protected List<DatabaseTypeListener> chain = new ArrayList<DatabaseTypeListener>();

    public DatabaseTypeListenerChainAdapter() {
    }
    
    public DatabaseTypeListenerChainAdapter(List<DatabaseTypeListener> chain) {
        this();
        this.chain.addAll(chain);
    }

    public void addListener(DatabaseTypeListener listener) {
        chain.add(listener);
    }
	
	public void handleArrayType(String typeName, long size) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleArrayType(typeName, size);
        }
	}

	public void handleBinaryType(long size) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleBinaryType(size);
        }
	}

	public void handleBlobType(long size) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleBlobType(size);
        }
	}

	public void handleCharType(long size) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleBlobType(size);
        }
	}

	public void handleClobType(long size) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleClobType(size);
        }
	}

	public void handleDecimalType(long precision, long scale) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleDecimalType(precision, scale);
        }
	}

	public void handleDoubleType(long precision, long scale) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleDoubleType(precision, scale);
        }
	}

	public void handleFieldType(String fieldName, String typeName,
			boolean notNull, boolean pk) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleFieldType(fieldName, typeName, notNull, pk);
        }
	}

	@Override
	public void handleFloatType(long precision, long scale) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleFloatType(precision, scale);
        }
	}

	@Override
	public void handleLongRawType(long size) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleLongRawType(size);
        }
	}

	@Override
	public void handleLongType(long size) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleLongType(size);
        }
	}

	@Override
	public void handleNumericType(long precision, long scale) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleNumericType(precision, scale);
        }
	}

	@Override
	public void beginPLSQLPackageType(String packageName) {
        for (DatabaseTypeListener listener : chain) {
            listener.beginPLSQLPackageType(packageName);
        }
	}

	@Override
	public void endPLSQLPackageType() {
        for (DatabaseTypeListener listener : chain) {
            listener.endPLSQLPackageType();
        }
	}

	@Override
	public void handleRawType(long size) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleRawType(size);
        }
	}

	@Override
	public void handleRealType(long precision, long scale) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleRealType(precision, scale);
        }
	}

	@Override
	public void handleUnresolvedSizedType(String unresolvedTypeName, long size) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleUnresolvedSizedType(unresolvedTypeName, size);
        }
	}

	@Override
	public void handleUnresolvedType(String unresolvedTypeName) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleUnresolvedType(unresolvedTypeName);
        }
	}

	@Override
	public void beginTableType(String tableName) {
        for (DatabaseTypeListener listener : chain) {
            listener.beginTableType(tableName);
        }
	}

	@Override
	public void endTableType() {
        for (DatabaseTypeListener listener : chain) {
            listener.endTableType();
        }
	}

	@Override
	public void handleURowIdType(long size) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleURowIdType(size);
        }
	}

	@Override
	public void handleVarCharType(long size) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleVarCharType(size);
        }
	}

	@Override
	public void handleVarChar2Type(long size) {
        for (DatabaseTypeListener listener : chain) {
            listener.handleVarChar2Type(size);
        }
	}

}