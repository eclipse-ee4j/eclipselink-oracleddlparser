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

import java.util.List;

public class BaseDatabaseTypeVisitor implements DatabaseTypeVisitor {

	public void visit(ArgumentType databaseType) {
		DatabaseType dt = databaseType.getDataType();
		if (dt != null) {
			dt.accept(this);
		}
	}
	public void visit(BinaryType databaseType) {
	}
	public void visit(BlobType databaseType) {
	}
	public void visit(LongRawType databaseType) {
	}
	public void visit(RawType databaseType) {
	}
	public void visit(CharType databaseType) {
	}
	public void visit(ClobType databaseType) {
	}
	public void visit(DecimalType databaseType) {
	}
	public void visit(DoubleType databaseType) {
	}
	public void visit(FieldType databaseType) {
		DatabaseType dt = databaseType.getDataType();
		if (dt != null) {
			dt.accept(this);
		}
	}
	public void visit(FloatType databaseType) {
	}
	public void visit(NumericType databaseType) {
	}
	public void visit(PLSQLCursorType databaseType) {
		DatabaseType dt = databaseType.getDataType();
		if (dt != null) {
			dt.accept(this);
		}
	}
	public void visit(PLSQLPackageType databaseType) {
		//TODO
	}
	public void visit(PLSQLRecordType plsqlRecordType) {
		//TODO
	}
	public void visit(PLSQLCollectionType plsqlCollectionType) {
		//TODO
	}
	public void visit(ProcedureType databaseType) {
		//TODO
	}
	public void visit(FunctionType databaseType) {
		//TODO
	}
	public void visit(RealType databaseType) {
	}
	public void visit(ScalarDatabaseType databaseType) {
	}
	public void visit(TableType databaseType) {
		List<FieldType> columns = databaseType.getColumns();
		for (FieldType column : columns) {
			column.accept(this);
		}
	}
	public void visit(UnresolvedType databaseType) {
	}
	public void visit(UnresolvedSizedType databaseType) {
	}
	public void visit(URowIdType databaseType) {
	}
	public void visit(VarCharType databaseType) {
	}
	public void visit(VarChar2Type databaseType) {
	}
	public void visit(LongType databaseType) {
	}
}
