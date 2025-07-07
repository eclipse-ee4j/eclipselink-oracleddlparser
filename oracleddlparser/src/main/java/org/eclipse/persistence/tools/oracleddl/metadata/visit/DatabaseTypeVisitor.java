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
    void visit(BinaryType databaseType);
    void visit(BlobType databaseType);
    void visit(LongRawType databaseType);
    void visit(RawType databaseType);
    void visit(CharType databaseType);
    void visit(ClobType databaseType);
    void visit(DecimalType databaseType);
    void visit(DoubleType databaseType);
    void visit(FloatType databaseType);
    void visit(IntervalDayToSecond databaseType);
    void visit(IntervalYearToMonth databaseType);
    void visit(NClobType databaseType);
    void visit(NumericType databaseType);
    void visit(RealType databaseType);
    void visit(ScalarDatabaseTypeEnum databaseType);
    void visit(UnresolvedType databaseType);
    void visit(UnresolvedSizedType databaseType);
    void visit(URowIdType databaseType);
    void visit(VarCharType databaseType);
    void visit(VarChar2Type databaseType);
    void visit(LongType databaseType);
    void visit(TimeStampType databaseType);

    void beginVisit(ArgumentType databaseType);
    void visit(ArgumentType databaseType);
    void endVisit(ArgumentType databaseType);

    void beginVisit(FieldType databaseType);
    void visit(FieldType databaseType);
    void endVisit(FieldType databaseType);

    void beginVisit(PLSQLCursorType databaseType);
    void visit(PLSQLCursorType databaseType);
    void endVisit(PLSQLCursorType databaseType);

    void beginVisit(PLSQLPackageType databaseType);
    void visit(PLSQLPackageType databaseType);
    void endVisit(PLSQLPackageType databaseType);

    void beginVisit(PLSQLRecordType databaseType);
    void visit(PLSQLRecordType plsqlRecordType);
    void endVisit(PLSQLRecordType databaseType);

    void beginVisit(PLSQLCollectionType databaseType);
    void visit(PLSQLCollectionType plsqlCollectionType);
    void endVisit(PLSQLCollectionType databaseType);

    void beginVisit(ProcedureType databaseType);
    void visit(ProcedureType databaseType);
    void endVisit(ProcedureType databaseType);

    void beginVisit(FunctionType databaseType);
    void visit(FunctionType databaseType);
    void endVisit(FunctionType databaseType);

    void beginVisit(TableType databaseType);
    void visit(TableType databaseType);
    void endVisit(TableType databaseType);

    void beginVisit(ObjectType databaseType);
    void visit(ObjectType databaseType);
    void endVisit(ObjectType databaseType);

    void beginVisit(VArrayType databaseType);
    void visit(VArrayType databaseType);
    void endVisit(VArrayType databaseType);

    void beginVisit(ObjectTableType databaseType);
    void visit(ObjectTableType databaseType);
    void endVisit(ObjectTableType databaseType);

    void beginVisit(ROWTYPEType databaseType);
    void visit(ROWTYPEType databaseType);
    void endVisit(ROWTYPEType databaseType);

    void beginVisit(TYPEType databaseType);
    void visit(TYPEType databaseType);
    void endVisit(TYPEType databaseType);

    void beginVisit(PLSQLSubType databaseType);
    void visit(PLSQLSubType plsqlSubType);
    void endVisit(PLSQLSubType databaseType);

}
