/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
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

//JUnit4 imports
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.IntervalDayToSecond;
import org.eclipse.persistence.tools.oracleddl.metadata.IntervalYearToMonth;

/**
 * Test Interval visit method chain.  Ensures that all required 
 * information can be retrieved via accept().
 * 
 * This test covers:
 *  - IntervalDayToSecond
 *  - IntervalYearToMonth
 *
 */
public class IntervalTypeTest {
    protected static String IDTS = "INTERVAL DAY TO SECOND";
    protected static String IDTS_1 = "INTERVAL DAY(1) TO SECOND";
    protected static String IDTS_1_2 = "INTERVAL DAY(1) TO SECOND(2)";
    protected static String IYTM = "INTERVAL YEAR TO MONTH";
    protected static String IYTM_1 = "INTERVAL YEAR(1) TO MONTH";
    
    protected static IntervalVisitor visitor;

    @BeforeClass
    public static void setUp() {
        visitor = new IntervalVisitor();
    }
    
    @Test
    public void testIntervalDayToSecond() {
        IntervalDayToSecond dayToSec = new IntervalDayToSecond();
        dayToSec.accept(visitor);
        assertEquals("IntervalDayToSecond() test failed:\n", visitor.toString(), IDTS);
        dayToSec = new IntervalDayToSecond(1L);
        dayToSec.accept(visitor);
        assertEquals("IntervalDayToSecond(1L) test failed:\n", visitor.toString(), IDTS_1);
        dayToSec = new IntervalDayToSecond(1L, 2L);
        dayToSec.accept(visitor);
        assertEquals("IntervalDayToSecond(1L, 2L) test failed:\n", visitor.toString(), IDTS_1_2);
    }
    
    @Test
    public void testIntervalYearToMonth() {
        IntervalYearToMonth yearToMonth = new IntervalYearToMonth();
        yearToMonth.accept(visitor);
        assertEquals("IntervalYearToMonth() test failed:\n", visitor.toString(), IYTM);
        yearToMonth = new IntervalYearToMonth(1L);
        yearToMonth.accept(visitor);
        assertEquals("IntervalYearToMonth(1L) test failed:\n", visitor.toString(), IYTM_1);
    }
}
