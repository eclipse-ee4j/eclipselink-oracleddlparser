package org.eclipse.persistence.tools.dbws.plsqlparser.test;

//javase imports
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

//JUnit4 imports
import org.junit.BeforeClass;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

//EclipseLink imports
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
	
	static final String TEST_PACKAGE = 
	    "CREATE OR REPLACE PACKAGE \"SCOTT\".\"CURSOR_TEST\" AS\n" +
	        "TYPE EREC IS RECORD (\n" +
    	        "FLAG PLS_INTEGER,\n" +
    	        "EMPNO NUMBER(4),\n" +
    	        "ENAME VARCHAR2(10),\n" +
    	        "JOB VARCHAR2(9)\n" +
    	    ");\n" +
    	    "TYPE EREC_CURSOR IS REF CURSOR RETURN EREC;\n" +
    	    "PROCEDURE EPROC(PARM1 IN VARCHAR2 DEFAULT 'bbb', PARM2 IN EREC, PARM3 OUT INTEGER, PARM4 OUT EREC_CURSOR);\n" +
    	    "FUNCTION EFUNC(PARM1 IN VARCHAR2 := 'bbb') RETURN EREC_CURSOR;\n" +
	    "END CURSOR_TEST;";
	@Test
	public void packageTest() throws ParseException  {
	    parser.ReInit(new StringReader(TEST_PACKAGE));
		SimpleNode plsqlPackageNode = null;
		boolean worked = true;
        String message = "";
        try {
            plsqlPackageNode = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("test package did not parse correctly:\n" + message, worked);
		if (worked) {
		    plsqlPackageNode.dump(">");
		}
	}
}