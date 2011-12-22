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
package org.eclipse.persistence.tools.oracleddl.metadata.visit;

//javase imports
import java.lang.reflect.Method;
import java.util.List;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.ArgumentType;
import org.eclipse.persistence.tools.oracleddl.metadata.BinaryType;
import org.eclipse.persistence.tools.oracleddl.metadata.BlobType;
import org.eclipse.persistence.tools.oracleddl.metadata.CharType;
import org.eclipse.persistence.tools.oracleddl.metadata.ClobType;
import org.eclipse.persistence.tools.oracleddl.metadata.CompositeDatabaseType;
import org.eclipse.persistence.tools.oracleddl.metadata.DatabaseType;
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
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLType;
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

public class BaseDatabaseTypeVisitor implements DatabaseTypeVisitor {

    //scalar visit callbacks
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
    public void visit(FloatType databaseType) {
    }
    public void visit(IntervalDayToSecond databaseType) {
    }
    public void visit(IntervalYearToMonth databaseType) {
    }
    public void visit(NClobType databaseType) {
    }
    public void visit(NumericType databaseType) {
    }
    public void visit(RealType databaseType) {
    }
    public void visit(ScalarDatabaseTypeEnum databaseType) {
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
    public void visit(TimeStampType databaseType) {
    }

    //composite visit callbacks
    public void visit(CompositeDatabaseType databaseType) {
        CompositeDatabaseTypeHandler.handle(this, databaseType);
    }

    public void beginVisit(ArgumentType databaseType) {
    }
    public void visit(ArgumentType databaseType) {
        beginVisit(databaseType);
        DatabaseType dt = databaseType.getDataType();
        if (dt != null) {
            dt.accept(this);
        }
        endVisit(databaseType);
    }
    public void endVisit(ArgumentType databaseType) {
    }

    public void beginVisit(FieldType databaseType) {
    }
    public void visit(FieldType databaseType) {
        beginVisit(databaseType);
        DatabaseType dt = databaseType.getDataType();
        if (dt != null) {
            dt.accept(this);
        }
        endVisit(databaseType);
    }
    public void endVisit(FieldType databaseType) {
    }

    public void beginVisit(PLSQLCursorType databaseType) {
    }
    public void visit(PLSQLCursorType databaseType) {
        beginVisit(databaseType);
        DatabaseType dt = databaseType.getDataType();
        if (dt != null) {
            dt.accept(this);
        }
        endVisit(databaseType);
    }
    public void endVisit(PLSQLCursorType databaseType) {
    }

    public void beginVisit(PLSQLPackageType databaseType) {
    }
    public void visit(PLSQLPackageType databaseType) {
        beginVisit(databaseType);
        if (databaseType.getTypes() != null) {
            for (PLSQLType plsqlType : databaseType.getTypes()) {
                plsqlType.accept(this);
            }
        }
        if (databaseType.getProcedures() != null) {
            for (ProcedureType procType : databaseType.getProcedures()) {
                procType.accept(this);
            }
        }
        endVisit(databaseType);
    }
    public void endVisit(PLSQLPackageType databaseType) {
    }

    public void beginVisit(PLSQLRecordType databaseType) {
    }
    public void visit(PLSQLRecordType databaseType) {
        beginVisit(databaseType);
        if (databaseType.getFields() != null) {
            for (FieldType fieldType : databaseType.getFields()) {
                fieldType.accept(this);
            }
        }
        endVisit(databaseType);
    }
    public void endVisit(PLSQLRecordType databaseType) {
    }

    public void beginVisit(PLSQLCollectionType databaseType) {
    }
    public void visit(PLSQLCollectionType databaseType) {
        beginVisit(databaseType);
        DatabaseType nestedType = databaseType.getNestedType();
        if (nestedType != null) {
            nestedType.accept(this);
        }
        endVisit(databaseType);
    }
    public void endVisit(PLSQLCollectionType databaseType) {
    }

    public void beginVisit(ProcedureType databaseType) {
    }
    public void visit(ProcedureType databaseType) {
        beginVisit(databaseType);
        List<ArgumentType> arguments = databaseType.getArguments();
        for (ArgumentType argument : arguments) {
            argument.accept(this);
        }
        endVisit(databaseType);
    }
    public void endVisit(ProcedureType databaseType) {
    }

    public void beginVisit(FunctionType databaseType) {
    }
    public void visit(FunctionType databaseType) {
        beginVisit(databaseType);
        List<ArgumentType> arguments = databaseType.getArguments();
        for (ArgumentType argument : arguments) {
            argument.accept(this);
        }
        databaseType.getReturnArgument().accept(this);
        endVisit(databaseType);
    }
    public void endVisit(FunctionType databaseType) {
    }

    public void beginVisit(TableType databaseType) {
    }
    public void visit(TableType databaseType) {
        beginVisit(databaseType);
        List<FieldType> columns = databaseType.getColumns();
        for (FieldType column : columns) {
            column.accept(this);
        }
        endVisit(databaseType);
    }
    public void endVisit(TableType databaseType) {
    }

    public void beginVisit(ObjectType databaseType) {
    }
    public void visit(ObjectType databaseType) {
        beginVisit(databaseType);
        for (FieldType field : databaseType.getFields()) {
            field.accept(this);
        }
        endVisit(databaseType);
    }
    public void endVisit(ObjectType databaseType) {
    }

    public void beginVisit(VArrayType databaseType) {
    }
    public void visit(VArrayType databaseType) {
        beginVisit(databaseType);
        DatabaseType dt = databaseType.getEnclosedType();
        if (dt != null) {
            dt.accept(this);
        }
        endVisit(databaseType);
    }
    public void endVisit(VArrayType databaseType) {
    }

    public void beginVisit(ObjectTableType databaseType) {
    }
    public void visit(ObjectTableType databaseType) {
        beginVisit(databaseType);
        DatabaseType dt = databaseType.getEnclosedType();
        if (dt != null) {
            dt.accept(this);
        }
        endVisit(databaseType);
    }
    public void endVisit(ObjectTableType databaseType) {
    }

    public void beginVisit(ROWTYPEType databaseType) {
    }
    public void visit(ROWTYPEType databaseType) {
        beginVisit(databaseType);
        DatabaseType dt = databaseType.getEnclosedType();
        if (dt != null) {
            dt.accept(this);
        }
        endVisit(databaseType);
    }
    public void endVisit(ROWTYPEType databaseType) {
    }

    public void beginVisit(TYPEType databaseType) {
    }
    public void visit(TYPEType databaseType) {
        beginVisit(databaseType);
        DatabaseType dt = databaseType.getEnclosedType();
        if (dt != null) {
            dt.accept(this);
        }
        endVisit(databaseType);
    }
    public void endVisit(TYPEType databaseType) {
    }

    //use reflection to avoid huge if-then-else tree of if (databaseType instanceof xxx)
    static CompositeDatabaseTypeHandler handler = new CompositeDatabaseTypeHandler();
    static class CompositeDatabaseTypeHandler {
        static Method[] methods = CompositeDatabaseTypeHandler.class.getMethods();
        public static void handle(DatabaseTypeVisitor visitor, Object o) {
            for (Method m : methods) {
                if (m.getParameterTypes()[1] == o.getClass()) {
                    try {
                        m.invoke(null, visitor, o);
                        return;
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            throw new RuntimeException("Can't handle");
        }
        public static void handle(DatabaseTypeVisitor visitor, ArgumentType databaseType){
            visitor.visit(databaseType);
        }
        public static void handle(DatabaseTypeVisitor visitor, FunctionType databaseType){
            visitor.visit(databaseType);
        }
        public static void handle(DatabaseTypeVisitor visitor, PLSQLPackageType databaseType){
            visitor.visit(databaseType);
        }
        public static void handle(DatabaseTypeVisitor visitor, ProcedureType databaseType){
            visitor.visit(databaseType);
        }
        public static void handle(DatabaseTypeVisitor visitor, TableType databaseType){
            visitor.visit(databaseType);
        }
        public static void handle(DatabaseTypeVisitor visitor, FieldType databaseType){
            visitor.visit(databaseType);
        }
        public static void handle(DatabaseTypeVisitor visitor, PLSQLCursorType databaseType){
            visitor.visit(databaseType);
        }
        public static void handle(DatabaseTypeVisitor visitor, PLSQLRecordType databaseType){
            visitor.visit(databaseType);
        }
        public static void handle(DatabaseTypeVisitor visitor, PLSQLCollectionType databaseType){
            visitor.visit(databaseType);
        }
        public static void handle(DatabaseTypeVisitor visitor, ObjectType databaseType){
            visitor.visit(databaseType);
        }
        public static void handle(DatabaseTypeVisitor visitor, VArrayType databaseType){
            visitor.visit(databaseType);
        }
        public static void handle(DatabaseTypeVisitor visitor, ObjectTableType databaseType){
            visitor.visit(databaseType);
        }
        public static void handle(DatabaseTypeVisitor visitor, ROWTYPEType databaseType){
            visitor.visit(databaseType);
        }
        public static void handle(DatabaseTypeVisitor visitor, TYPEType databaseType){
            visitor.visit(databaseType);
        }
    }
}