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
package org.eclipse.persistence.tools.oracleddl.test;

//javase imports
import java.util.Properties;
import java.util.TreeSet;

//JUnit4 imports
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

//Oracleddl imports
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypeBuilder;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypeBuilderException;

public class DatabaseTypeBuilderTestSuite {

    //fixtures
    static DatabaseTypeBuilder dtBuilder = null;
    static TreeSet<Object> expectedTransformsKeys = null;
    @BeforeClass
    public static void setUp() throws Exception {
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
        //TODO - need to send DDL down to database
    }
    
    @AfterClass
    public static void tearDown() {
        //TODO - cleanup DDL
    }

    @Test
    public void testTransforms() {
        boolean worked = true;
        Properties transformProperties = null;
        String msg = "";
        try {
            transformProperties = dtBuilder.getTransformProperties();
        }
        catch (DatabaseTypeBuilderException dtbe) {
            worked = false;
            msg = dtbe.getMessage();
        }
        assertTrue("did not get transforms properties\n\t" + msg, worked);
        assertNotNull("transforms properties should never be null", transformProperties);
        assertFalse("transforms properties should never be empty", transformProperties.isEmpty());

        TreeSet<Object> transformsKeySet = new TreeSet<Object>(expectedTransformsKeys.comparator());
        transformsKeySet.addAll(transformProperties.keySet());  
        assertTrue(expectedTransformsKeys.equals(transformsKeySet));
    }
    
    @Ignore
    public void testDDLforTable() {
        //TODO - get actual DDL from database: 
    }

}