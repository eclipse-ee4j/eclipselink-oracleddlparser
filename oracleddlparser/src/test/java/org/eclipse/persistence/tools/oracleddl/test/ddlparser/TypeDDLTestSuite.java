/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 */

// Contributors:
//     Mike Norman - June 10 2011, created DDL parser package
//     David McCann - July 2011, visit tests
package org.eclipse.persistence.tools.oracleddl.test.ddlparser;

//javase imports
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

//JUnit4 imports
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.FieldType;
import org.eclipse.persistence.tools.oracleddl.metadata.ObjectTableType;
import org.eclipse.persistence.tools.oracleddl.metadata.ObjectType;
import org.eclipse.persistence.tools.oracleddl.metadata.UnresolvedType;
import org.eclipse.persistence.tools.oracleddl.metadata.VArrayType;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.UnresolvedTypesVisitor;
import org.eclipse.persistence.tools.oracleddl.parser.DDLParser;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypesRepository;

public class TypeDDLTestSuite {

    static final String CREATE_TYPE_PREFIX  = "CREATE OR REPLACE TYPE ";
    //JUnit fixture(s)
    static DDLParser parser = null;

    @BeforeClass
    static public void setUp() {
        parser = new DDLParser(new InputStream() {
            public int read() throws IOException {
                return 0;
            }
        });
        parser.setTypesRepository(new DatabaseTypesRepository());
    }

    /*
      CREATE OR REPLACE TYPE EMP_INFO AS OBJECT (
        ID      NUMERIC(5),
        NAME    VARCHAR2(50)
      );
     */
    static final String SIMPLE_TYPE = "EMP_INFO";
    static final String SIMPLE_TYPE_FIELD1_NAME = "ID";
    static final String SIMPLE_TYPE_FIELD1_TYPE = "NUMERIC(5)";
    static final String SIMPLE_TYPE_FIELD2_NAME = "NAME";
    static final String SIMPLE_TYPE_FIELD2_TYPE = "VARCHAR2(50)";
    static final String CREATE_SIMPLE_TYPE =
        CREATE_TYPE_PREFIX + SIMPLE_TYPE + " IS OBJECT (" +
            "\n" + SIMPLE_TYPE_FIELD1_NAME + " " + SIMPLE_TYPE_FIELD1_TYPE + "," +
            "\n" + SIMPLE_TYPE_FIELD2_NAME + " " + SIMPLE_TYPE_FIELD2_TYPE +
        "\n);";
    @Test
    public void testSimpleType() {
        parser.ReInit(new StringReader(CREATE_SIMPLE_TYPE));
        boolean worked = true;
        String message = "";
        ObjectType simpleType = null;
        try {
            simpleType = (ObjectType)parser.parseType();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(SIMPLE_TYPE + " type did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(simpleType);
        assertTrue(SIMPLE_TYPE + " type should not contain unresolved column datatypes",
            l.getUnresolvedTypes().isEmpty());
        List<FieldType> fields = simpleType.getFields();
        assertEquals(SIMPLE_TYPE + " type should contain 2 column fields",
            2, fields.size());
        FieldType field1 = fields.get(0);
        assertEquals("incorrect name for " + SIMPLE_TYPE_FIELD1_NAME + " field ",
            SIMPLE_TYPE_FIELD1_NAME, field1.getFieldName());
        assertEquals("incorrect type for " + SIMPLE_TYPE_FIELD1_NAME + " field ",
            SIMPLE_TYPE_FIELD1_TYPE, field1.getEnclosedType().toString());
        FieldType field2 = fields.get(1);
        assertEquals("incorrect name for " + SIMPLE_TYPE_FIELD2_NAME + " field ",
            SIMPLE_TYPE_FIELD2_NAME, field2.getFieldName());
        assertEquals("incorrect type for " + SIMPLE_TYPE_FIELD2_NAME + " field ",
            SIMPLE_TYPE_FIELD2_TYPE, field2.getEnclosedType().toString());
    }

    /*
      CREATE OR REPLACE TYPE GUID_PACKAGE_GUID_ARRAY AS TABLE OF VARCHAR2(20);
    */
    static final String GUID_TABLE_TYPE = "GUID_PACKAGE_GUID_ARRAY";
    static final String CREATE_GUID_TABLE_TYPE =
        CREATE_TYPE_PREFIX + GUID_TABLE_TYPE + " AS TABLE OF VARCHAR2(20);";
    @Test
    public void testObjectTableType() {
        parser.ReInit(new StringReader(CREATE_GUID_TABLE_TYPE));
        boolean worked = true;
        String message = "";
        ObjectTableType objectTableType = null;
        try {
            objectTableType = (ObjectTableType)parser.parseType();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(GUID_TABLE_TYPE + " type did not parse:\n" + message, worked);
        assertEquals(GUID_TABLE_TYPE + " type wrong name", GUID_TABLE_TYPE,
            objectTableType.getTypeName());
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(objectTableType);
        List<UnresolvedType> unresolvedTypes = l.getUnresolvedTypes();
        assertTrue(GUID_TABLE_TYPE + " type should not contain unresolved column datatypes",
            unresolvedTypes.isEmpty());
        assertEquals("incorrect table type for " + GUID_TABLE_TYPE,
            "VARCHAR2(20)", objectTableType.getEnclosedType().toString());
    }

    /*
      CREATE OR REPLACE TYPE VCARRAY AS VARRAY(4) OF VARCHAR2(20);
    */
    static final String VCARRAY_VARRAY_TYPE = "VCARRAY";
    static final String CREATE_VCARRAY_VARRAY_TYPE =
        CREATE_TYPE_PREFIX + VCARRAY_VARRAY_TYPE + " AS VARRAY(4) OF VARCHAR2(20);";
    @Test
    public void testVArrayType() {
        parser.ReInit(new StringReader(CREATE_VCARRAY_VARRAY_TYPE));
        boolean worked = true;
        String message = "";
        VArrayType varrayType = null;
        try {
            varrayType = (VArrayType)parser.parseType();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(VCARRAY_VARRAY_TYPE + " type did not parse:\n" + message, worked);
        assertEquals(VCARRAY_VARRAY_TYPE + " type wrong name", VCARRAY_VARRAY_TYPE,
            varrayType.getTypeName());
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(varrayType);
        List<UnresolvedType> unresolvedTypes = l.getUnresolvedTypes();
        assertTrue(VCARRAY_VARRAY_TYPE + " type should not contain unresolved column datatypes",
            unresolvedTypes.isEmpty());
        assertEquals(VCARRAY_VARRAY_TYPE + "'s size be 4", 4L, varrayType.getSize());
        assertEquals("incorrect enclosed type for " + VCARRAY_VARRAY_TYPE,
            "VARCHAR2(20)", varrayType.getEnclosedType().toString());
    }

    /*
      CREATE OR REPLACE TYPE EMPLOYEE_CONTACT IS OBJECT (
        EMP_NUMBER      NUMERIC,
        EMP_NAME        VARCHAR2(50),
        HOME_CONTACT    HR.CONTACT,
        WORK_CONTACT    HR.CONTACT
      );
    */
    static final String TYPE_WITH_UNRESOLVED_TYPE = "EMPLOYEE_CONTACT";
    static final String TWUT_FIELD1_NAME = "EMP_NUMBER";
    static final String TWUT_FIELD1_TYPE = "NUMERIC";
    static final String TWUT_FIELD2_NAME = "EMP_NAME";
    static final String TWUT_FIELD2_TYPE = "VARCHAR2(50)";
    static final String TWUT_FIELD3_NAME = "HOME_CONTACT";
    static final String TWUT_FIELD3_TYPE = "HR.CONTACT";
    static final String TWUT_FIELD4_NAME = "WORK_CONTACT";
    static final String TWUT_FIELD4_TYPE = TWUT_FIELD3_TYPE;
    static final String CREATE_TYPE_WITH_UNRESOLVED_TYPE =
        CREATE_TYPE_PREFIX + TYPE_WITH_UNRESOLVED_TYPE + " IS OBJECT (" +
            "\n" + TWUT_FIELD1_NAME + " " + TWUT_FIELD1_TYPE + "," +
            "\n" + TWUT_FIELD2_NAME + " " + TWUT_FIELD2_TYPE + "," +
            "\n" + TWUT_FIELD3_NAME + " " + TWUT_FIELD3_TYPE + "," +
            "\n" + TWUT_FIELD4_NAME + " " + TWUT_FIELD4_TYPE +
        "\n);";
    @Test
    public void testTypeWithUnresolvedType() {
        parser.ReInit(new StringReader(CREATE_TYPE_WITH_UNRESOLVED_TYPE));
        boolean worked = true;
        String message = "";
        ObjectType typeWithUnresolvedType = null;
        try {
            typeWithUnresolvedType = (ObjectType)parser.parseType();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(TYPE_WITH_UNRESOLVED_TYPE + " type did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(typeWithUnresolvedType);
        List<UnresolvedType> unresolvedTypes = l.getUnresolvedTypes();
        assertFalse(TYPE_WITH_UNRESOLVED_TYPE + " type should contain unresolved column datatypes",
            unresolvedTypes.isEmpty());
        assertEquals(TYPE_WITH_UNRESOLVED_TYPE + " type should contain 2 unresolved column datatypes",
            2, unresolvedTypes.size());
        List<FieldType> fields = typeWithUnresolvedType.getFields();
        assertEquals(TYPE_WITH_UNRESOLVED_TYPE + " type should contain 4 column fields",
            4, fields.size());
        FieldType field1 = fields.get(0);
        assertEquals("incorrect name for " + TWUT_FIELD1_NAME + " field ",
            TWUT_FIELD1_NAME, field1.getFieldName());
        assertEquals("incorrect type for " + TWUT_FIELD1_NAME + " field ",
            TWUT_FIELD1_TYPE, field1.getEnclosedType().toString());
        FieldType field2 = fields.get(1);
        assertEquals("incorrect name for " + TWUT_FIELD2_NAME + " field ",
            TWUT_FIELD2_NAME, field2.getFieldName());
        assertEquals("incorrect type for " + TWUT_FIELD2_NAME + " field ",
            TWUT_FIELD2_TYPE, field2.getEnclosedType().toString());
        FieldType field3 = fields.get(2);
        assertEquals("incorrect name for " + TWUT_FIELD3_NAME + " field ",
            TWUT_FIELD3_NAME, field3.getFieldName());
        assertEquals("incorrect type for " + TWUT_FIELD3_NAME + " field ",
            TWUT_FIELD3_TYPE, field3.getEnclosedType().toString());
        FieldType field4 = fields.get(3);
        assertEquals("incorrect name for " + TWUT_FIELD4_NAME + " field ",
            TWUT_FIELD4_NAME, field4.getFieldName());
        assertEquals("incorrect type for " + TWUT_FIELD4_NAME + " field ",
            TWUT_FIELD4_TYPE, field4.getEnclosedType().toString());
    }

    static final String TYPE_W_KEYWORDS = "KEYWORD_TYPE";
    static final String TWKW_FIELD1_NAME = "OID";
    static final String TWKW_FIELD1_TYPE = "VARCHAR2(50)";
    static final String TWKW_FIELD2_NAME = "CODE";
    static final String TWKW_FIELD2_TYPE = "VARCHAR2 (2)";
    static final String CREATE_TYPE_WITH_KEYWORD =
        CREATE_TYPE_PREFIX + TYPE_W_KEYWORDS + " IS OBJECT (" +
            "\n" + TWKW_FIELD1_NAME + " " + TWKW_FIELD1_TYPE + "," +
            "\n" + TWKW_FIELD2_NAME + " " + TWKW_FIELD2_TYPE +
        "\n);";
    @Test
    public void testKeywordType() {
        parser.ReInit(new StringReader(CREATE_TYPE_WITH_KEYWORD));
        boolean worked = true;
        String message = "";
        ObjectType typeWithKeyword = null;
        try {
            typeWithKeyword = (ObjectType)parser.parseType();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("type with keyword did not parse:\n" + message, worked);
        assertEquals("incorrect type name " + TYPE_W_KEYWORDS,
            TYPE_W_KEYWORDS, typeWithKeyword.getTypeName());
    }

    static final String TYPE_NAME = "CUSTOM_CONS_TYPE";
    static final String CREATE_TYPE_WITH_MULTIPLE_CONSTRUCTORS = CREATE_TYPE_PREFIX + TYPE_NAME + " AS OBJECT(" +
            "\n    status varchar2(5)," +
            "\n    orauser varchar2(40)," +
            "\n    comments varchar2(1000)," +
            "\nconstructor function " + TYPE_NAME + "(i_status in boolean, i_orauser in varchar2, i_comments in varchar2) return self as result," +
            "\nconstructor function " + TYPE_NAME + "(i_status in varchar2, i_comments in varchar2) return self as result," +
            "\nconstructor function " + TYPE_NAME + "(i_status in varchar2) return self as result)";
    @Test
    public void testTypeWithMultipleConstructors() {
        parser.ReInit(new StringReader(CREATE_TYPE_WITH_MULTIPLE_CONSTRUCTORS));
        boolean worked = true;
        String message = "";
        ObjectType typeWithMultipleConstructors = null;
        try {
            typeWithMultipleConstructors = (ObjectType)parser.parseType();
        } catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("type with multiple constructors did not parse:\n" + message, worked);
        assertEquals("incorrect type name " + TYPE_NAME, TYPE_NAME, typeWithMultipleConstructors.getTypeName());
    }

    static final String MEMBER_FUNCTION_TYPE_NAME = "MEMBER_FUNCTION_TYPE";
    static final String CREATE_MEMBER_FUNCTION_TYPE = CREATE_TYPE_PREFIX + MEMBER_FUNCTION_TYPE_NAME + " AS OBJECT(" +
            "\n    status1 varchar2(5)," +
            "\n    status2 varchar2(5)," +
            "\n    status3 varchar2(5)," +
            "\n    member function my_function1 return varchar2," +
            "\n    member function my_function2 return varchar2," +
            "\n    static member function my_function3 return varchar2," +
            "\n    member function my_function4 return varchar2," +
            "\n    static member function my_function5 return varchar2," +
            "\n    MEMBER PROCEDURE display1 (SELF IN OUT NOCOPY solid_typ)," +
            "\n    MEMBER PROCEDURE display2 (SELF IN OUT NOCOPY solid_typ)," +
            "\n    static MEMBER PROCEDURE display3 (SELF IN OUT NOCOPY solid_typ)," +
            "\n    static MEMBER PROCEDURE display4 (SELF IN OUT NOCOPY solid_typ)," +
            "\n    member function my_function6 return varchar2," +
            "\n    static member function my_function7 return varchar2" +
            "\n)";

    @Test
    public void testTypeWithMemberFunctions() {
        parser.ReInit(new StringReader(CREATE_MEMBER_FUNCTION_TYPE));
        boolean worked = true;
        String message = "";
        ObjectType typeWithMemberFunctions = null;
        try {
            typeWithMemberFunctions = (ObjectType)parser.parseType();
        } catch (ParseException pe) {
            pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("Parse error:\n" + message, worked);
        assertNotNull("Type should not be null", typeWithMemberFunctions);
        assertEquals("Incorrect type name: " + MEMBER_FUNCTION_TYPE_NAME, MEMBER_FUNCTION_TYPE_NAME, typeWithMemberFunctions.getTypeName());
        assertEquals("Type should have three fields", 3, typeWithMemberFunctions.getFields().size());
    }

    static final String MEMBER_FUNCTION_CONS_TYPE_NAME = "MEMBER_FUNCTION_CONS_TYPE";
    static final String CREATE_MEMBER_FUNCTION_CONS_TYPE =
        "CREATE OR REPLACE TYPE " + MEMBER_FUNCTION_CONS_TYPE_NAME + " AS OBJECT(" +
        "\n    status1 varchar2(5)," +
        "\n    status2 varchar2(5)," +
        "\n    status3 varchar2(5)," +
        "\n    constructor function my_type (" +
        "\n       i_status        in varchar2," +
        "\n       i_comments      in varchar2 := null" +
        "\n    ) return self as result," +
        "\n    constructor function my_type (" +
        "\n       i_status        in boolean," +
        "\n       i_orauser       in varchar2," +
        "\n       i_comments      in varchar2" +
        "\n    ) return self as result," +
        "\n    member function my_function1 return varchar2," +
        "\n    member function my_function2 return varchar2," +
        "\n    static member function my_function3 return varchar2," +
        "\n    member function my_function4 return varchar2," +
        "\n    static member function my_function5 return varchar2," +
        "\n    MEMBER PROCEDURE display1 (SELF IN OUT NOCOPY solid_typ)," +
        "\n    MEMBER PROCEDURE display2 (SELF IN OUT NOCOPY solid_typ)," +
        "\n    static MEMBER PROCEDURE display3 (SELF IN OUT NOCOPY solid_typ)," +
        "\n    static MEMBER PROCEDURE display4 (SELF IN OUT NOCOPY solid_typ)," +
        "\n    member function my_function6 return varchar2," +
        "\n    static member function my_function7 return varchar2" +
        "\n)";

    @Test
    public void testTypeWithMemberFunctionsAndConstructors() {
        parser.ReInit(new StringReader(CREATE_MEMBER_FUNCTION_CONS_TYPE));
        boolean worked = true;
        String message = "";
        ObjectType typeWithMemberFunctions = null;
        try {
            typeWithMemberFunctions = (ObjectType)parser.parseType();
        } catch (ParseException pe) {
            pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("Parse error:\n" + message, worked);
        assertNotNull("Type should not be null", typeWithMemberFunctions);
        assertEquals("incorrect type name: " + MEMBER_FUNCTION_CONS_TYPE_NAME, MEMBER_FUNCTION_CONS_TYPE_NAME,
                typeWithMemberFunctions.getTypeName());
        assertEquals("Type should have three fields", 3, typeWithMemberFunctions.getFields().size());
    }

}
