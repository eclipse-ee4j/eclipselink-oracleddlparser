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
//     David McCann - July 2011, visit tests
package org.eclipse.persistence.tools.oracleddl.test.visit;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.IntervalDayToSecond;
import org.eclipse.persistence.tools.oracleddl.metadata.IntervalYearToMonth;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.BaseDatabaseTypeVisitor;

/**
 * Visitor for use with IntervalDayToSecond and IntervalYearToMonth.
 * The visit methods simply gather all relevant information such 
 * that it can be returned as a String when visiting is complete. 
 */
class IntervalVisitor extends BaseDatabaseTypeVisitor {
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
