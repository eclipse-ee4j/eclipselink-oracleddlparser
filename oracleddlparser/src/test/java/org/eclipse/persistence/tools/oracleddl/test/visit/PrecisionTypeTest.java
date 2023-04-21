/*
 * Copyright (c) 2011, 2023 Oracle and/or its affiliates. All rights reserved.
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
import org.eclipse.persistence.tools.oracleddl.metadata.DecimalType;
import org.eclipse.persistence.tools.oracleddl.metadata.DoubleType;
import org.eclipse.persistence.tools.oracleddl.metadata.FloatType;
import org.eclipse.persistence.tools.oracleddl.metadata.NumericType;
import org.eclipse.persistence.tools.oracleddl.metadata.PrecisionType;
import org.eclipse.persistence.tools.oracleddl.metadata.RealType;

/**
 * Test SizedType visit method chain.  Ensures that all required 
 * information can be retrieved via SizedType.accept().
 * 
 * This test covers:
 *  - DecimalType
 *  - DoubleType
 *  - FloatType
 *  - NumericType
 *  - RealType
 *  
 */
public class PrecisionTypeTest {

    protected static long LONG_1 = 1L;
    protected static long LONG_6 = 6L;
    protected static long LONG_39 = 39L;
    protected static long LONG_19 = 19L;

    protected static String DECIMAL = "DECIMAL";
    protected static String DECIMAL_6 = "DECIMAL(6)";
    protected static String DECIMAL_6_1 = "DECIMAL(6,1)";
    protected static String DOUBLE = "DOUBLE";
    protected static String DOUBLE_39 = "DOUBLE(39)";
    protected static String FLOAT = "FLOAT";
    protected static String FLOAT_39 = "FLOAT(39)";
    protected static String NUMERIC = "NUMERIC";
    protected static String NUMERIC_39 = "NUMERIC(39)";
    protected static String NUMERIC_39_1 = "NUMERIC(39,1)";
    protected static String REAL = "REAL";
    protected static String REAL_19 = "REAL(19)";

    protected static PrecisionTypeVisitor visitor;

    @BeforeClass
    public static void setUp() {
        visitor = new PrecisionTypeVisitor();
    }
    
    @Test
    public void testDecimalType() {
        // test defaults
        PrecisionType decimalType = new DecimalType();
        decimalType.accept(visitor);
        assertEquals("DecimalType() test failed:\n", visitor.toString(), DECIMAL);
        // test setting precision
        decimalType = new DecimalType(LONG_6);
        decimalType.accept(visitor);
        assertEquals("DecimalType(LONG_6) test failed:\n", visitor.toString(), DECIMAL_6);
        // test setting precision and scale
        decimalType = new DecimalType(LONG_6, LONG_1);
        decimalType.accept(visitor);
        assertEquals("DecimalType(LONG_6, LONG_1) test failed:\n", visitor.toString(), DECIMAL_6_1);
    }
    
    @Test
    public void testDoubleType() {
        // test defaults
        PrecisionType doubleType = new DoubleType();
        doubleType.accept(visitor);
        assertEquals("DoubleType() test failed:\n", visitor.toString(), DOUBLE);
        // test setting precision
        doubleType = new DoubleType(LONG_39);
        doubleType.accept(visitor);
        assertEquals("DoubleType(LONG_39) test failed:\n", visitor.toString(), DOUBLE_39);
        // TODO:  verify that setting scale should have no affect
        // test setting precision and scale
        doubleType = new DoubleType(LONG_39, LONG_1);
        doubleType.accept(visitor);
        assertEquals("DoubleType(LONG_39, LONG_1) test failed:\n", visitor.toString(), DOUBLE_39);
    }

    @Test
    public void testFloatType() {
        // test defaults
        PrecisionType floatType = new FloatType();
        floatType.accept(visitor);
        assertEquals("FloatType() test failed:\n", visitor.toString(), FLOAT);
        // test setting precision
        floatType = new FloatType(LONG_39);
        floatType.accept(visitor);
        assertEquals("FloatType(LONG_39) test failed:\n", visitor.toString(), FLOAT_39);
        // TODO:  verify that setting scale should have no affect
        // test setting precision and scale
        floatType = new FloatType(LONG_39, LONG_1);
        floatType.accept(visitor);
        assertEquals("FloatType(LONG_39, LONG_1) test failed:\n", visitor.toString(), FLOAT_39);
    }

    @Test
    public void testNumericType() {
        // test defaults
        PrecisionType numericType = new NumericType();
        numericType.accept(visitor);
        assertEquals("NumericType() test failed:\n", visitor.toString(), NUMERIC);
        // test setting precision
        numericType = new NumericType(LONG_39);
        numericType.accept(visitor);
        assertEquals("NumericType(LONG_39) test failed:\n", visitor.toString(), NUMERIC_39);
        // test setting precision and scale
        numericType = new NumericType(LONG_39, LONG_1);
        numericType.accept(visitor);
        assertEquals("NumericType(LONG_39, LONG_1) test failed:\n", visitor.toString(), NUMERIC_39_1);
    }

    @Test
    public void testRealType() {
        // test defaults
        PrecisionType realType = new RealType();
        realType.accept(visitor);
        assertEquals("RealType() test failed:\n", visitor.toString(), REAL);
        // test setting precision
        realType = new RealType(LONG_19);
        realType.accept(visitor);
        assertEquals("RealType(LONG_19) test failed:\n", visitor.toString(), REAL_19);
        // TODO:  verify that setting scale should have no affect
        // test setting precision and scale
        realType = new RealType(LONG_19, LONG_1);
        realType.accept(visitor);
        assertEquals("RealType(LONG_19, LONG_1) test failed:\n", visitor.toString(), REAL_19);
    }

}
