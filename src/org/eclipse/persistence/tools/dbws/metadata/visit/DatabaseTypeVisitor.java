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

//DDL parser imports
import org.eclipse.persistence.tools.dbws.metadata.ArrayType;
import org.eclipse.persistence.tools.dbws.metadata.BinaryType;
import org.eclipse.persistence.tools.dbws.metadata.BlobType;
import org.eclipse.persistence.tools.dbws.metadata.CharType;
import org.eclipse.persistence.tools.dbws.metadata.ClobType;
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

public interface DatabaseTypeVisitor {
	
    public void visit(ArrayType arrayType);
    
    public void visit(BinaryType binaryType);
    
    public void visit(BlobType blobType);
    
    public void visit(CharType charType);
    
    public void visit(ClobType clobType);
    
    public void visit(DecimalType decimalType);
    
    public void visit(DoubleType doubleType);
    
    public void visit(FieldType fieldType);
    
    public void visit(FloatType floatType);
    
    public void visit(LongRawType longRawType);
    
    public void visit(LongType longType);
    
    public void visit(NumericType numericType);
    
    public void visit(PLSQLPackageType plsqlPackageType);
    
    public void visit(RawType rawType);
    
    public void visit(RealType realType);
    
    public void visit(ScalarType scalarType);
    
    public void visit(TableType tableType);
    
    public void visit(UnresolvedSizedType unresolvedSizedType);
    
    public void visit(UnresolvedType unresolvedType);
    
    public void visit(URowIdType urowIdType);
    
    public void visit(VarCharType varCharType);
    
    public void visit(VarChar2Type varChar2Type);

}