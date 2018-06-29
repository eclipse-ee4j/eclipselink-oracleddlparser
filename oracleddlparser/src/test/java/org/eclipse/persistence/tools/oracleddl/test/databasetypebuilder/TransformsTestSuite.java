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
package org.eclipse.persistence.tools.oracleddl.test.databasetypebuilder;

//javase imports
import java.util.Properties;
import java.util.TreeSet;

//JUnit4 imports
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypeBuilder;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypeBuilderException;

public class TransformsTestSuite {
    
    //fixtures
    static DatabaseTypeBuilder dtBuilder = DatabaseTypeBuilderTestSuite.dtBuilder;
    static Properties transformProperties = null;
    static TreeSet<Object> expectedTransformsKeys = null;
    @BeforeClass
    public static void setUp() {
        dtBuilder = new DatabaseTypeBuilder();
        expectedTransformsKeys = new TreeSet<Object>();
        expectedTransformsKeys.add("BODY");
        expectedTransformsKeys.add("CONSTRAINTS");
        expectedTransformsKeys.add("CONSTRAINTS_AS_ALTER");
        expectedTransformsKeys.add("PRETTY");
        expectedTransformsKeys.add("REF_CONSTRAINTS");
        expectedTransformsKeys.add("SEGMENT_ATTRIBUTES");
        expectedTransformsKeys.add("SPECIFICATION");
        expectedTransformsKeys.add("SQLTERMINATOR");
        expectedTransformsKeys.add("STORAGE");
        expectedTransformsKeys.add("TABLESPACE");

        boolean worked = true;
        String msg = "";
        try {
            transformProperties = dtBuilder.getTransformProperties();
        }
        catch (DatabaseTypeBuilderException dtbe) {
            worked = false;
            msg = dtbe.getMessage();
        }
        if (!worked) {
            fail(msg);
        }
    }
    
    @Test
    public void testGetTransforms() {
        assertNotNull("transforms properties should never be null", transformProperties);
    }
    
    @Test
    public void testTransformsNotEmpty() {
        assertFalse("transforms properties should never be empty", transformProperties.isEmpty());
    }
    
    @Test
    public void testTransformsKeys() {
        TreeSet<Object> transformsKeySet = new TreeSet<Object>(expectedTransformsKeys.comparator());
        transformsKeySet.addAll(transformProperties.keySet());  
        assertTrue(expectedTransformsKeys.equals(transformsKeySet));
    }

}
