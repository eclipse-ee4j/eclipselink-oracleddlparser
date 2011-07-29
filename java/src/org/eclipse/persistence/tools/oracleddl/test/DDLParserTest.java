package org.eclipse.persistence.tools.oracleddl.test;

//javase imports
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

//JUnit4 imports
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

//DDL parser imports
import org.eclipse.persistence.tools.oracleddl.metadata.TableType;
import org.eclipse.persistence.tools.oracleddl.parser.DDLParser;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;
import org.eclipse.persistence.tools.oracleddl.util.DatabaseTypesRepository;
import org.eclipse.persistence.tools.oracleddl.util.UnresolvedTypesVisitor;

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

	static final String EMPTY_TABLE = "CREATE TABLE EMPTY_TABLE();";
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

    static final String CREATE_TABLE_PREFIX = "CREATE TABLE ";
    static final String DUMMY_TABLE = "DUMMY_TABLE ( DUMMY VARCHAR2(1) );";
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
        assertEquals("", DUMMY_TABLE_SCHEMA.subSequence(0, DUMMY_TABLE_SCHEMA.length()-1),
            tableType.getSchema());
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
        assertEquals("", DUMMY_TABLE_SCHEMA.subSequence(0, DUMMY_TABLE_SCHEMA.length()-1),
            tableType.getSchema());
    }
    
    static final String NORMAL_TABLE =
        " BONUS (\n" +
            "ENAME VARCHAR2(10),\n" +
            "JOB VARCHAR2(9),\n" +
            "SAL NUMBER,\n" +
            "COMM NUMBER\n" +
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
        assertTrue("normal table did not parse:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(tableType);
        assertTrue("normal table should not contain any unresolved column datatypes",
            l.getUnresolvedTypes().isEmpty());
        //TODO - check each column's name, type
    }

}