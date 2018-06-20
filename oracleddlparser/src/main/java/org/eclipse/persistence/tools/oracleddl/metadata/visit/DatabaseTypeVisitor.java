/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

// Contributors:
//     Mike Norman - June 10 2011, created DDL parser package
package org.eclipse.persistence.tools.oracleddl.metadata.visit;

import org.eclipse.persistence.tools.oracleddl.metadata.ArgumentType;
import org.eclipse.persistence.tools.oracleddl.metadata.BinaryType;
import org.eclipse.persistence.tools.oracleddl.metadata.BlobType;
import org.eclipse.persistence.tools.oracleddl.metadata.CharType;
import org.eclipse.persistence.tools.oracleddl.metadata.ClobType;
import org.eclipse.persistence.tools.oracleddl.metadata.DecimalType;
import org.eclipse.persistence.tools.oracleddl.metadata.DoubleType;
import org.eclipse.persistence.tools.oracleddl.metadata.FieldType;
import org.eclipse.persistence.tools.oracleddl.metadata.FloatType;
import org.eclipse.persistence.tools.oracleddl.metadata.FunctionType;
import org.eclipse.persistence.tools.oracleddl.metadata.IntervalDayToSecond;
import org.eclipse.persistence.tools.oracleddl.metadata.IntervalYearToMonth;
import org.eclipse.persistence.tools.oracleddl.metadata.LongRawType;
import org.eclipse.persistence.tools.oracleddl.metadata.LongType;
import org.eclipse.persistence.tools.oracleddl.metadata.NClobType;
import org.eclipse.persistence.tools.oracleddl.metadata.ObjectTableType;
import org.eclipse.persistence.tools.oracleddl.metadata.NumericType;
import org.eclipse.persistence.tools.oracleddl.metadata.ObjectType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLCollectionType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLCursorType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLPackageType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLRecordType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLSubType;
import org.eclipse.persistence.tools.oracleddl.metadata.ProcedureType;
import org.eclipse.persistence.tools.oracleddl.metadata.ROWTYPEType;
import org.eclipse.persistence.tools.oracleddl.metadata.RawType;
import org.eclipse.persistence.tools.oracleddl.metadata.RealType;
import org.eclipse.persistence.tools.oracleddl.metadata.ScalarDatabaseTypeEnum;
import org.eclipse.persistence.tools.oracleddl.metadata.TYPEType;
import org.eclipse.persistence.tools.oracleddl.metadata.TableType;
import org.eclipse.persistence.tools.oracleddl.metadata.TimeStampType;
import org.eclipse.persistence.tools.oracleddl.metadata.URowIdType;
import org.eclipse.persistence.tools.oracleddl.metadata.UnresolvedSizedType;
import org.eclipse.persistence.tools.oracleddl.metadata.UnresolvedType;
import org.eclipse.persistence.tools.oracleddl.metadata.VArrayType;
import org.eclipse.persistence.tools.oracleddl.metadata.VarChar2Type;
import org.eclipse.persistence.tools.oracleddl.metadata.VarCharType;

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
    public void visit(LongType databaseType);
    public void visit(TimeStampType databaseType);

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

    public void beginVisit(ObjectType databaseType);
    public void visit(ObjectType databaseType);
    public void endVisit(ObjectType databaseType);

    public void beginVisit(VArrayType databaseType);
    public void visit(VArrayType databaseType);
    public void endVisit(VArrayType databaseType);

    public void beginVisit(ObjectTableType databaseType);
    public void visit(ObjectTableType databaseType);
    public void endVisit(ObjectTableType databaseType);

    public void beginVisit(ROWTYPEType databaseType);
    public void visit(ROWTYPEType databaseType);
    public void endVisit(ROWTYPEType databaseType);

    public void beginVisit(TYPEType databaseType);
    public void visit(TYPEType databaseType);
    public void endVisit(TYPEType databaseType);

    public void beginVisit(PLSQLSubType databaseType);
    public void visit(PLSQLSubType plsqlSubType);
    public void endVisit(PLSQLSubType databaseType);

}
