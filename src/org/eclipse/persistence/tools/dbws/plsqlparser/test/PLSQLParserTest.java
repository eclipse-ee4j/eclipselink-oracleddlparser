package org.eclipse.persistence.tools.dbws.plsqlparser.test;

//javase imports
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

//JUnit4 imports
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

//EclipseLink imports
import org.eclipse.persistence.tools.dbws.plsqlparser.PLSQLNode;
import org.eclipse.persistence.tools.dbws.plsqlparser.PLSQLPackageNode;
import org.eclipse.persistence.tools.dbws.plsqlparser.ParseException;
import org.eclipse.persistence.tools.dbws.plsqlparser.PLSQLParser;
import org.eclipse.persistence.tools.dbws.plsqlparser.SimpleNode;

public class PLSQLParserTest {

	//fixture
	static PLSQLParser parser = null;
	@BeforeClass
	static public void setUp() {
        parser = new PLSQLParser(new InputStream() {
            public int read() throws IOException {
                return 0;
            }
        });
	}

    static final String PACKAGE_NAME = "CURSOR_TEST";
    static final String DOT_NAME = "SCOTT.CURSOR_TEST";
    static final String QUOTED_DOT_NAME = "\"SCOTT\".\"CURSOR_TEST\"";
    static final String EMPTY_PACKAGE_PREFIX = 
        "CREATE OR REPLACE PACKAGE ";
    static final String EMPTY_PACKAGE_BODY = " AS \n";
    static final String EMPTY_PACKAGE_SUFFIX =
        "END CURSOR_TEST;";
    @Ignore
    //@Test
    public void testEmptyPackage() {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + EMPTY_PACKAGE_SUFFIX));
        PLSQLNode rootNode = null;
        boolean worked = true;
        String message = "";
        try {
            rootNode = (PLSQLNode)parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("empty package did not parse correctly:\n" + message, worked);
        PLSQLPackageNode packageNode = rootNode.getPackageNode();
        assertEquals("incorrect package name", packageNode.getPackageName(), PACKAGE_NAME);
    }

    @Ignore
    //@Test
	public void testEmptyPackageDN() {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + DOT_NAME +
            EMPTY_PACKAGE_BODY + EMPTY_PACKAGE_SUFFIX));
		PLSQLNode rootNode = null;
		boolean worked = true;
        String message = "";
        try {
            rootNode = (PLSQLNode)parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("empty package DN did not parse correctly:\n" + message, worked);
        PLSQLPackageNode packageNode = rootNode.getPackageNode();
        assertEquals("incorrect package name", packageNode.getPackageName(), DOT_NAME);
	}

    @Ignore
    //@Test
    public void testEmptyPackageQDN()  {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + QUOTED_DOT_NAME +
            EMPTY_PACKAGE_BODY + EMPTY_PACKAGE_SUFFIX));
        PLSQLNode rootNode = null;
        boolean worked = true;
        String message = "";
        try {
            rootNode = (PLSQLNode)parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("empty package QDN did not parse correctly:\n" + message, worked);
        PLSQLPackageNode packageNode = rootNode.getPackageNode();
        //NB: PLSQLParser strips-off the quotes
        assertEquals("incorrect package name", packageNode.getPackageName(), DOT_NAME);
    }

    static final String VARIABLE_DECLARATION =
        "urban_legend  CONSTANT BOOLEAN := FALSE; -- PL/SQL-only data type\n";
    @Ignore
    //@Test
    public void testVariableDeclaration() {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + VARIABLE_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        boolean worked = true;
        String message = "";
        try {
            parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("package with variable declaration did not parse correctly:\n" + message, worked);
    }

    static final String SIMPLE_RECORD_DECLARATION =
        "TYPE EREC IS RECORD (\n" +
            "FLAG PLS_INTEGER,\n" +
            "EMPNO NUMBER(4),\n" +
            "ENAME VARCHAR2(10),\n" +
            "JOB VARCHAR2(9)\n" +
        ");";
    @Ignore
    //@Test
    public void testSimpleRecordDeclaration() {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + SIMPLE_RECORD_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        boolean worked = true;
        String message = "";
        try {
            parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("package with simple record declaration did not parse correctly:\n" + message, worked);
    }

    static final String COMPLEX_RECORD_DECLARATION =
        "TYPE EREC IS RECORD (\n" +
            "FLAG PLS_INTEGER,\n" +
            "EMPNO EMPNO%TYPE,\n" +
            "ENAME SCOTT.ENAME%TYPE,\n" +
            "JOB VARCHAR2(9)\n" +
        ");";
    @Ignore
    //@Test
    public void testComplexRecordDeclaration() {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + COMPLEX_RECORD_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        boolean worked = true;
        String message = "";
        try {
            parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("package with complex record declaration did not parse correctly:\n" + message, worked);
    }
    
    static final String NESTED_RECORD_DECLARATION =
        "TYPE DREC IS RECORD (\n" +
            "DEPT NUMBER(4),\n" +
            "EMP EREC\n" +
        ");";
    @Ignore
    //@Test
    public void testNestedRecordDeclaration() {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + NESTED_RECORD_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        boolean worked = true;
        String message = "";
        try {
            parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("package with complex nested record declaration did not parse correctly:\n" + message, worked);
    }
    
    static final String WEAK_REF_CURSOR_DECLARATION =
        "TYPE rcursor IS REF CURSOR;";
    @Ignore
    //@Test
    public void testWeakRefCursorDeclaration() {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + WEAK_REF_CURSOR_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        boolean worked = true;
        String message = "";
        try {
            parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("package with weak ref cursor declaration did not parse correctly:\n" + message, worked);
    }
    
    static final String TYPED_REF_CURSOR_DECLARATION =
        "TYPE EREC IS RECORD (\n" +
            "FLAG PLS_INTEGER,\n" +
            "EMPNO NUMBER(4),\n" +
            "ENAME VARCHAR2(10),\n" +
            "JOB VARCHAR2(9)\n" +
        ");\n" +
        "TYPE EREC_CURSOR IS REF CURSOR RETURN EREC;";
    //@Ignore
    @Test
    public void testTypedRefCursorDeclaration() {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + TYPED_REF_CURSOR_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        boolean worked = true;
        String message = "";
        SimpleNode parseNode = null;
        try {
            parseNode = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("package with typed ref cursor declaration did not parse correctly:\n" + message, worked);
        parseNode.dump(">");
    }

}