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
 *     David McCann - July 2011, visit tests
 ******************************************************************************/
package org.eclipse.persistence.tools.oracleddl.test.visit;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.DecimalType;
import org.eclipse.persistence.tools.oracleddl.metadata.DoubleType;
import org.eclipse.persistence.tools.oracleddl.metadata.FloatType;
import org.eclipse.persistence.tools.oracleddl.metadata.NumericType;
import org.eclipse.persistence.tools.oracleddl.metadata.PrecisionType;
import org.eclipse.persistence.tools.oracleddl.metadata.RealType;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.BaseDatabaseTypeVisitor;

class PrecisionTypeVisitor extends BaseDatabaseTypeVisitor {

    protected String typeName;
    protected long precision;
    protected long defaultPrecision;
    protected long scale;
    
    public void visit(PrecisionType databaseType) {
        typeName = databaseType.getTypeName();
        precision = databaseType.getPrecision();
        defaultPrecision = databaseType.getDefaultPrecision();
        scale = databaseType.getScale();
    }
    
    public void visit(DecimalType databaseType) {
        visit((PrecisionType)databaseType);
    }
    
    public void visit(DoubleType databaseType) {
        visit((PrecisionType)databaseType);
    }
    
    public void visit(FloatType databaseType) {
        visit((PrecisionType)databaseType);
    }
    
    public void visit(NumericType databaseType) {
        visit((PrecisionType)databaseType);
    }
    
    public void visit(RealType databaseType) {
        visit((PrecisionType)databaseType);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder(typeName);
        if (precision != defaultPrecision) {
            sb.append('(');
            sb.append(precision);
            if (scale != 0) {
                sb.append(',');
                sb.append(scale);
            }
            sb.append(')');
        }
        return sb.toString();
    }
}