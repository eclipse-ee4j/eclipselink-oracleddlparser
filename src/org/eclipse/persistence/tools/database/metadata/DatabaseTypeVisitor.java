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

public interface DatabaseTypeVisitor {

	//scalar visit callbacks
	public void visit(BinaryType databaseType);
	public void visit(BlobType databaseType);
	public void visit(LongRawType databaseType);
	public void visit(RawType databaseType);
	public void visit(CharType databaseType);
	public void visit(ClobType databaseType);
	public void visit(DecimalType databaseType);
	public void visit(DoubleType databaseType);
	public void visit(FloatType databaseType);
	public void visit(IntervalDayToSecond databaseType);
	public void visit(IntervalYearToMonth databaseType);
	public void visit(NClobType databaseType);
	public void visit(NumericType databaseType);
	public void visit(RealType databaseType);
	public void visit(ScalarDatabaseTypeEnum databaseType);
	public void visit(UnresolvedType databaseType);
	public void visit(UnresolvedSizedType databaseType);
	public void visit(URowIdType databaseType);
	public void visit(VarCharType databaseType);
	public void visit(VarChar2Type databaseType);
	public void visit(LongType databaseType) ;

	//composite visit callbacks
	public void beginVisit(ArgumentType databaseType);
	public void visit(ArgumentType databaseType);
	public void endVisit(ArgumentType databaseType);

	public void beginVisit(FieldType databaseType);
	public void visit(FieldType databaseType);
	public void endVisit(FieldType databaseType);

	public void beginVisit(PLSQLCursorType databaseType);
	public void visit(PLSQLCursorType databaseType);
	public void endVisit(PLSQLCursorType databaseType);

	public void beginVisit(PLSQLPackageType databaseType);
	public void visit(PLSQLPackageType databaseType);
	public void endVisit(PLSQLPackageType databaseType);

	public void beginVisit(PLSQLRecordType databaseType);
	public void visit(PLSQLRecordType plsqlRecordType);
	public void endVisit(PLSQLRecordType databaseType);

	public void beginVisit(PLSQLCollectionType databaseType);
	public void visit(PLSQLCollectionType plsqlCollectionType);
	public void endVisit(PLSQLCollectionType databaseType);

	public void beginVisit(ProcedureType databaseType);
	public void visit(ProcedureType databaseType);
	public void endVisit(ProcedureType databaseType);

	public void beginVisit(FunctionType databaseType);
	public void visit(FunctionType databaseType);
	public void endVisit(FunctionType databaseType);

	public void beginVisit(TableType databaseType);
	public void visit(TableType databaseType);
	public void endVisit(TableType databaseType);
}