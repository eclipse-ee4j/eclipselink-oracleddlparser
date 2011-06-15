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
package org.eclipse.persistence.tools.dbws.metadata.visit;

//javase imports
import java.util.List;

//DDL parser imports
import org.eclipse.persistence.tools.dbws.metadata.ArrayType;
import org.eclipse.persistence.tools.dbws.metadata.BinaryType;
import org.eclipse.persistence.tools.dbws.metadata.BlobType;
import org.eclipse.persistence.tools.dbws.metadata.CharType;
import org.eclipse.persistence.tools.dbws.metadata.ClobType;
import org.eclipse.persistence.tools.dbws.metadata.DatabaseType;
import org.eclipse.persistence.tools.dbws.metadata.DecimalType;
import org.eclipse.persistence.tools.dbws.metadata.DoubleType;
import org.eclipse.persistence.tools.dbws.metadata.FieldType;
import org.eclipse.persistence.tools.dbws.metadata.FloatType;
import org.eclipse.persistence.tools.dbws.metadata.LongRawType;
import org.eclipse.persistence.tools.dbws.metadata.LongType;
import org.eclipse.persistence.tools.dbws.metadata.NumericType;
import org.eclipse.persistence.tools.dbws.metadata.PLSQLPackageType;
import org.eclipse.persistence.tools.dbws.metadata.RawType;
import org.eclipse.persistence.tools.dbws.metadata.RealType;
import org.eclipse.persistence.tools.dbws.metadata.ScalarType;
import org.eclipse.persistence.tools.dbws.metadata.TableType;
import org.eclipse.persistence.tools.dbws.metadata.URowIdType;
import org.eclipse.persistence.tools.dbws.metadata.UnresolvedSizedType;
import org.eclipse.persistence.tools.dbws.metadata.UnresolvedType;
import org.eclipse.persistence.tools.dbws.metadata.VarChar2Type;
import org.eclipse.persistence.tools.dbws.metadata.VarCharType;

public class DatabaseTypeWalker extends DatabaseTypeDefaultVisitor implements DatabaseTypeVisitor {

    protected DatabaseTypeListener listener;

    public DatabaseTypeWalker(DatabaseTypeListener listener) {
        this.listener = listener;
    }

    public DatabaseTypeListener getListener() {
        return listener;
    }

	@Override
	public void visit(ArrayType arrayType) {
		listener.handleArrayType(arrayType.getTypeName(), arrayType.getSize());
	}

	@Override
	public void visit(BinaryType binaryType) {
		listener.handleBinaryType(binaryType.getSize());
	}

	@Override
	public void visit(BlobType blobType) {
		listener.handleBlobType(blobType.getSize());
	}

	@Override
	public void visit(CharType charType) {
		listener.handleCharType(charType.getSize());
	}

	@Override
	public void visit(ClobType clobType) {
		listener.handleClobType(clobType.getSize());
	}

	@Override
	public void visit(DecimalType decimalType) {
		listener.handleDecimalType(decimalType.getPrecision(), decimalType.getScale());
	}

	@Override
	public void visit(DoubleType doubleType) {
		listener.handleDoubleType(doubleType.getPrecision(), doubleType.getScale());
	}

	@Override
	public void visit(FieldType fieldType) {
		DatabaseType dt = fieldType.getDataType();
		listener.handleFieldType(fieldType.getFieldName(), dt.getTypeName(), fieldType.notNull(), fieldType.pk());
		if (dt.isComplex() || !dt.isResolved()) {
			dt.accept(this);
		}
	}

	@Override
	public void visit(FloatType floatType) {
		listener.handleFloatType(floatType.getPrecision(), floatType.getScale());
	}

	@Override
	public void visit(LongRawType longRawType) {
		listener.handleLongRawType(longRawType.getSize());
	}

	@Override
	public void visit(LongType longType) {
		listener.handleLongType(longType.getSize());
	}

	@Override
	public void visit(NumericType numericType) {
		listener.handleNumericType(numericType.getPrecision(), numericType.getScale());
	}

	@Override
	public void visit(PLSQLPackageType plsqlPackageType) {
        listener.beginPLSQLPackageType(plsqlPackageType.getPackageName());
        /*
        List<PLSQLType> types = plsqlPackageType.getTypes();
        for (PLSQLType t : types) {
            t.accept(this);
        }
        List<RefCursorTypes> cursors = plsqlPackageType.getRefCursorTypes();
        for (RefCursorTypes c : cursors) {
            c.accept(this);
        }
        List<ProcedureMethodType> methods = plsqlPackageType.getMethods();
        for (ProcedureMethodType m : methods) {
            m.accept(this);
        }
        */
        listener.endPLSQLPackageType();
	}

	@Override
	public void visit(RawType rawType) {
		listener.handleRawType(rawType.getSize());
	}

	@Override
	public void visit(RealType realType) {
		listener.handleRealType(realType.getPrecision(), realType.getScale());
	}

	@Override
	public void visit(ScalarType scalarType) {
		super.visit(scalarType);
	}

	@Override
	public void visit(TableType tableType) {
        listener.beginTableType(tableType.getTableName());
        List<FieldType> fields = tableType.getColumns();
        for (FieldType field : fields) {
        	field.accept(this);
        }
        listener.endTableType();
	}

	@Override
	public void visit(UnresolvedSizedType unresolvedSizedType) {
		listener.handleUnresolvedSizedType(unresolvedSizedType.getUnresolvedTypeName(), unresolvedSizedType.getSize());
	}

	@Override
	public void visit(UnresolvedType unresolvedType) {
		listener.handleUnresolvedType(unresolvedType.getUnresolvedTypeName());
	}

	@Override
	public void visit(URowIdType urowIdType) {
		listener.handleURowIdType(urowIdType.getSize());
	}

	@Override
	public void visit(VarCharType varCharType) {
		listener.handleVarCharType(varCharType.getSize());
	}

	@Override
	public void visit(VarChar2Type varChar2Type) {
		listener.handleVarChar2Type(varChar2Type.getSize());
	}
}