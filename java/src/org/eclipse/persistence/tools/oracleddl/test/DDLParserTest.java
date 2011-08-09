package org.eclipse.persistence.tools.oracleddl.test;

//javase imports
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

//JUnit4 imports
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

//DDL parser imports
import org.eclipse.persistence.tools.oracleddl.metadata.FieldType;
import org.eclipse.persistence.tools.oracleddl.metadata.NumericType;
import org.eclipse.persistence.tools.oracleddl.metadata.TableType;
import org.eclipse.persistence.tools.oracleddl.metadata.VarChar2Type;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.UnresolvedTypesVisitor;
import org.eclipse.persistence.tools.oracleddl.parser.DDLParser;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypesRepository;

import static org.junit.Assert.assertEquals;

public class DDLParserTest {

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

    static final String CREATE_TABLE_PREFIX = "CREATE TABLE ";
	static final String EMPTY_TABLE = CREATE_TABLE_PREFIX + "EMPTY_TABLE();";
    @Test
    public void testEmptyTable() {
        parser.ReInit(new StringReader(EMPTY_TABLE));
        boolean worked = true;
        @SuppressWarnings("unused") TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            worked = false;
        }
        assertFalse("empty table should not parse", worked);
    }

    static final String DUMMY = "DUMMY";
    static final String DUMMY_TABLE = DUMMY + "_TABLE ( " + DUMMY + " VARCHAR2(1) );";
    @Test
    public void testDummyTable() {
        parser.ReInit(new StringReader(CREATE_TABLE_PREFIX + DUMMY_TABLE));
        boolean worked = true;
        String message = "";
        TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("dummy table did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(tableType);
        assertTrue("dummy table should not contain any unresolved column datatypes",
            l.getUnresolvedTypes().isEmpty());
        List<FieldType> columns = tableType.getColumns();
        assertEquals("dummy table has wrong number of columns", 1, columns.size());
        FieldType col1 = columns.get(0);
        assertEquals("incorrect name for " + DUMMY + " column",
            DUMMY, col1.getFieldName());
        assertEquals("incorrect type for " + DUMMY + " column",
            new VarChar2Type().getTypeName(), col1.getTypeName());
    }

    static final String DUMMY_TABLE_SCHEMA = "SCOTT.";
    @Test
    public void testDummyTable_WithSchema() {
        parser.ReInit(new StringReader(CREATE_TABLE_PREFIX + DUMMY_TABLE_SCHEMA +
            DUMMY_TABLE));
        boolean worked = true;
        String message = "";
        TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("dummy table did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(tableType);
        assertTrue("dummy table should not contain any unresolved column datatypes",
            l.getUnresolvedTypes().isEmpty());
        assertEquals("dummy table wrong schema",
            (String)DUMMY_TABLE_SCHEMA.subSequence(0, DUMMY_TABLE_SCHEMA.length()-1),
            (String)tableType.getSchema());
    }

    static final String DUMMY_TABLE_QUOTED_SCHEMA = "\"SCOTT\".";
    @Test
    public void testDummyTable_WithQuotedSchema() {
        parser.ReInit(new StringReader(CREATE_TABLE_PREFIX + DUMMY_TABLE_QUOTED_SCHEMA +
            DUMMY_TABLE));
        boolean worked = true;
        String message = "";
        TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("dummy table did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(tableType);
        assertTrue("dummy table should not contain any unresolved column datatypes",
            l.getUnresolvedTypes().isEmpty());
        assertEquals("dummy table wrong schema",
            (String)DUMMY_TABLE_SCHEMA.subSequence(0, DUMMY_TABLE_SCHEMA.length()-1),
            (String)tableType.getSchema());
    }

    static final String NORMAL = "NORMAL";
    static final String ENAME = "ENAME";
    static final String JOB = "JOB";
    static final String SAL = "SAL";
    static final String COMM = "COMM";
    static final String NORMAL_TABLE =
        NORMAL + " (\n" +
            ENAME + " VARCHAR2(10),\n" +
            JOB + " VARCHAR2(9),\n" +
            SAL + " NUMBER,\n" +
            COMM + " NUMBER,\n" +
            "CONSTRAINT \"PK_BONUS\" PRIMARY KEY (\"" + ENAME + "\", \"" + JOB + "\") ENABLE\n" +
        ");";
    @Test
    public void testNormalTable() {
        parser.ReInit(new StringReader(CREATE_TABLE_PREFIX + NORMAL_TABLE));
        boolean worked = true;
        String message = "";
        TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(NORMAL + " table did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(tableType);
        assertTrue(NORMAL + " table should not contain any unresolved column datatypes",
            l.getUnresolvedTypes().isEmpty());
        List<FieldType> columns = tableType.getColumns();
        assertEquals(NORMAL + " table has wrong number of columns", 4, columns.size());
        FieldType col1 = columns.get(0);
        assertEquals("incorrect name for " + ENAME + " column",
            ENAME, col1.getFieldName());
        assertEquals("incorrect type for " + ENAME + " column",
            new VarChar2Type().getTypeName(), col1.getTypeName());
        FieldType col2 = columns.get(1);
        assertEquals("incorrect name for " + JOB + " column",
            JOB, col2.getFieldName());
        assertEquals("incorrect type for " + JOB + " column",
            new VarChar2Type().getTypeName(), col2.getTypeName());
        FieldType col3 = columns.get(2);
        assertEquals("incorrect name for " + SAL + " column",
            SAL, col3.getFieldName());
        assertEquals("incorrect type for " + SAL + " column",
            new NumericType().getTypeName(), col3.getTypeName());
        FieldType col4 = columns.get(3);
        assertEquals("incorrect name for " + COMM + " column",
            COMM, col4.getFieldName());
        assertEquals("incorrect type for " + COMM + " column",
            new NumericType().getTypeName(), col4.getTypeName());
    }
    
    static final String IOT_TABLE =
        " FOO_IOT (\n" +
            ENAME + " VARCHAR2(10),\n" +
            JOB + " VARCHAR2(9),\n" +
            SAL + " NUMBER,\n" +
            COMM + " NUMBER,\n" +
            "CONSTRAINT \"PK_BONUS\" PRIMARY KEY (\"" + ENAME + "\", \"" + JOB + "\") ENABLE\n" +
        ") ORGANIZATION INDEX;";
    @Test
    public void testIOTTable() {
        parser.ReInit(new StringReader(CREATE_TABLE_PREFIX + IOT_TABLE));
        boolean worked = true;
        String message = "";
        TableType tableType = null;
        try {
            tableType = parser.parseTable();
        }
        catch (ParseException pe) {
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("iot table did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(tableType);
        assertTrue("iot table should not contain any unresolved column datatypes",
            l.getUnresolvedTypes().isEmpty());
        List<FieldType> columns = tableType.getColumns();
        assertEquals(NORMAL + " table has wrong number of columns", 4, columns.size());
        FieldType col1 = columns.get(0);
        assertEquals("incorrect name for " + ENAME + " column",
            ENAME, col1.getFieldName());
        assertEquals("incorrect type for " + ENAME + " column",
            new VarChar2Type().getTypeName(), col1.getTypeName());
        FieldType col2 = columns.get(1);
        assertEquals("incorrect name for " + JOB + " column",
            JOB, col2.getFieldName());
        assertEquals("incorrect type for " + JOB + " column",
            new VarChar2Type().getTypeName(), col2.getTypeName());
        FieldType col3 = columns.get(2);
        assertEquals("incorrect name for " + SAL + " column",
            SAL, col3.getFieldName());
        assertEquals("incorrect type for " + SAL + " column",
            new NumericType().getTypeName(), col3.getTypeName());
        FieldType col4 = columns.get(3);
        assertEquals("incorrect name for " + COMM + " column",
            COMM, col4.getFieldName());
        assertEquals("incorrect type for " + COMM + " column",
            new NumericType().getTypeName(), col4.getTypeName());
    }

}