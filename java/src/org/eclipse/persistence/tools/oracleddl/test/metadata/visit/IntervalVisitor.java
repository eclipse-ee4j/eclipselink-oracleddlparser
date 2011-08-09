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
 *     David McCann - July 22, 2011 - 2.4 - Initial implementation
 ******************************************************************************/
package org.eclipse.persistence.tools.oracleddl.test.metadata.visit;

import org.eclipse.persistence.tools.oracleddl.metadata.BaseDatabaseTypeVisitor;
import org.eclipse.persistence.tools.oracleddl.metadata.IntervalDayToSecond;
import org.eclipse.persistence.tools.oracleddl.metadata.IntervalYearToMonth;

/**
 * Visitor for use with IntervalDayToSecond and IntervalYearToMonth.
 * The visit methods simply gather all relevant information such 
 * that it can be returned as a String when visiting is complete. 
 */
public class IntervalVisitor extends BaseDatabaseTypeVisitor {
    //protected long dayPrecision;
    //protected long secondPrecision;
    //protected long yearPrecision;
    protected String typeName;
    
    public void visit(IntervalDayToSecond databaseType) {
        //dayPrecision = databaseType.getDayPrecision();
        //secondPrecision = databaseType.getSecondPrecision();
        typeName = databaseType.getTypeName();
    }
    
    public void visit(IntervalYearToMonth databaseType) {
        //yearPrecision = databaseType.getYearPrecision();
        typeName = databaseType.getTypeName();
    }
    
    public String toString() {
        return typeName;
    }
}
