package org.eclipse.persistence.tools.dbws.plsqlparser.test;

//javase imports
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

//JUnit4 imports
import org.junit.BeforeClass;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

//EclipseLink imports
import org.eclipse.persistence.tools.dbws.plsqlparser.PLSQLNode;
import org.eclipse.persistence.tools.dbws.plsqlparser.PLSQLPackageNode;
import org.eclipse.persistence.tools.dbws.plsqlparser.ParseException;
import org.eclipse.persistence.tools.dbws.plsqlparser.PLSQLParser;

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
    static final String EMPTY_PACKAGE_SUFFIX = " AS\n" +
        "END CURSOR_TEST;";
    @Test
    public void testEmptyPackage() throws ParseException  {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_SUFFIX));
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
	
	@Test
	public void testEmptyPackageDN() throws ParseException  {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + DOT_NAME +
            EMPTY_PACKAGE_SUFFIX));
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
        assertEquals("incorrect package name", packageNode.getPackageName(), DOT_NAME);
	}
    
    @Test
    public void testEmptyPackageQDN() throws ParseException  {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + QUOTED_DOT_NAME +
            EMPTY_PACKAGE_SUFFIX));
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
        //NB: PLSQLParser strips-off the quotes
        assertEquals("incorrect package name", packageNode.getPackageName(), DOT_NAME);
    }
}