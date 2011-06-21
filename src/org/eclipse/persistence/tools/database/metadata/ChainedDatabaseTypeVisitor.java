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
 *     Mike Norman - June 10 2011, created DDL parser package
 ******************************************************************************/
package org.eclipse.persistence.tools.database.metadata;

import java.util.ArrayList;
import java.util.List;

public class ChainedDatabaseTypeVisitor implements DatabaseTypeVisitor {

    protected List<DatabaseTypeVisitor> chain = new ArrayList<DatabaseTypeVisitor>();

    public ChainedDatabaseTypeVisitor() {
        // possibly some init code required
    }
    public ChainedDatabaseTypeVisitor(List<DatabaseTypeVisitor> chain) {
        this();
        this.chain.addAll(chain);
    }

    public void addListener(DatabaseTypeVisitor listener) {
        chain.add(listener);
    }

	//scalar visit callbacks
	public void visit(BinaryType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(BlobType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(LongRawType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(RawType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(CharType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(ClobType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(DecimalType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(DoubleType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(FloatType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(IntervalDayToSecond databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(IntervalYearToMonth databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(NClobType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(NumericType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(RealType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(ScalarDatabaseTypeEnum databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(UnresolvedType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(UnresolvedSizedType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(URowIdType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(VarCharType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(VarChar2Type databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void visit(LongType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}

	//composite visit callbacks
	public void beginVisit(ArgumentType databaseType) {
	}
	public void visit(ArgumentType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void endVisit(ArgumentType databaseType) {
	}
	
	public void beginVisit(FieldType databaseType) {
	}
	public void visit(FieldType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void endVisit(FieldType databaseType) {
	}

	public void beginVisit(PLSQLCursorType databaseType) {
	}
	public void visit(PLSQLCursorType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void endVisit(PLSQLCursorType databaseType) {
	}

	public void beginVisit(PLSQLPackageType databaseType) {
	}
	public void visit(PLSQLPackageType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void endVisit(PLSQLPackageType databaseType) {
	}

	public void beginVisit(PLSQLRecordType databaseType) {
	}
	public void visit(PLSQLRecordType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void endVisit(PLSQLRecordType databaseType) {
	}

	public void beginVisit(PLSQLCollectionType databaseType) {
	}
	public void visit(PLSQLCollectionType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void endVisit(PLSQLCollectionType databaseType) {
	}

	public void beginVisit(ProcedureType databaseType) {
	}
	public void visit(ProcedureType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void endVisit(ProcedureType databaseType) {
	}

	public void beginVisit(FunctionType databaseType) {
	}
	public void visit(FunctionType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void endVisit(FunctionType databaseType) {
	}

	public void beginVisit(TableType databaseType) {
	}
	public void visit(TableType databaseType) {
        for (DatabaseTypeVisitor listener : chain) {
            listener.visit(databaseType);
        }
	}
	public void endVisit(TableType databaseType) {
	}
}