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

import static org.junit.Assert.assertEquals;

import org.eclipse.persistence.tools.oracleddl.metadata.IntervalDayToSecond;
import org.eclipse.persistence.tools.oracleddl.metadata.IntervalYearToMonth;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test Interval visit method chain.  Ensures that all required 
 * information can be retrieved via accept().
 * 
 * This test covers:
 *  - IntervalDayToSecond
 *  - IntervalYearToMonth
 *
 */
public class IntervalTest {
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