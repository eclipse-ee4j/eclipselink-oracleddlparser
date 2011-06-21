package org.eclipse.persistence.tools.database.parser.test;

//javase imports
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//JUnit4 imports
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.fail;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

//DDL parser imports
import org.eclipse.persistence.tools.database.metadata.FunctionType;
import org.eclipse.persistence.tools.database.metadata.PLSQLPackageType;
import org.eclipse.persistence.tools.database.metadata.ProcedureType;
import org.eclipse.persistence.tools.database.metadata.TableType;
import org.eclipse.persistence.tools.database.metadata.parser.DDLParser;
import org.eclipse.persistence.tools.database.metadata.parser.ParseException;
import org.eclipse.persistence.tools.database.metadata.parser.SimpleNode;
import org.eclipse.persistence.tools.database.metadata.util.DatabaseTypesRepository;
import org.eclipse.persistence.tools.database.metadata.util.UnresolvedTypesVisitor;

public class DDLParserTest {

    static final String DATABASE_USERNAME_KEY = "db.user";
    static final String DATABASE_PASSWORD_KEY = "db.pwd";
    static final String DATABASE_URL_KEY = "db.url";
    static final String DATABASE_DRIVER_KEY = "db.driver";
    static final String DEFAULT_DATABASE_USERNAME = "scott";
    static final String DEFAULT_DATABASE_PASSWORD = "tiger";
    static final String DEFAULT_DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
    static final String DEFAULT_DATABASE_DRIVER = "oracle.jdbc.OracleDriver";

    static final String DBMS_METADATA_DDL_STMT_SUFFIX =
        "', SYS_CONTEXT('USERENV', 'CURRENT_USER')) AS RESULT FROM DUAL";
    static final String DBMS_METADATA_SESSION_TRANSFORM_STMT =
        "BEGIN " +
            "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'PRETTY',TRUE); " +
            "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'SQLTERMINATOR',TRUE); " +
            "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'CONSTRAINTS', TRUE); " +
            "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'CONSTRAINTS_AS_ALTER', TRUE); " +
            "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'REF_CONSTRAINTS',FALSE); " +
            "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'SEGMENT_ATTRIBUTES',FALSE); " +
            "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'STORAGE',FALSE); " +
            "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'TABLESPACE',FALSE); " +
            "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'SPECIFICATION',TRUE); " +
            "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'BODY',FALSE); " +
        "END;";

	//fixtures
    static String username;
    static String password;
    static String url;
    static String driver;
    static Connection conn;
	static DDLParser parser = null;
	
	@BeforeClass
	static public void setUp() throws ClassNotFoundException, SQLException {
        username = System.getProperty(DATABASE_USERNAME_KEY, DEFAULT_DATABASE_USERNAME);
        password = System.getProperty(DATABASE_PASSWORD_KEY, DEFAULT_DATABASE_PASSWORD);
        url = System.getProperty(DATABASE_URL_KEY, DEFAULT_DATABASE_URL);
        driver = System.getProperty(DATABASE_DRIVER_KEY, DEFAULT_DATABASE_DRIVER);
        Class.forName(driver);
        conn = DriverManager.getConnection(url, username, password);
        CallableStatement callableStatement = conn.prepareCall(DBMS_METADATA_SESSION_TRANSFORM_STMT);
        boolean worked = true;
        String msg = "";
        try {
            callableStatement.execute();
        }
        catch (SQLException e) {
           worked = false;
           msg = e.getMessage();
        }
        if (!worked) {
            fail(msg);
        }
        parser = new DDLParser(new InputStream() {
            public int read() throws IOException {
                return 0;
            }
        });
        parser.setTypesRepository(new DatabaseTypesRepository());
	}

    static String getDDL(String psSpec) {
        String ddl = null;
        try {
            PreparedStatement ps = conn.prepareStatement(psSpec);
            ResultSet rs = ps.executeQuery();
            rs.next();
            ddl = rs.getString("RESULT").trim();
            try {
                rs.close();
                ps.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        if (ddl.endsWith("/")) {
            ddl = (String)ddl.subSequence(0, ddl.length()-1);
        }
        return ddl;
    }
    
    static final String DBMS_METADATA_GET_PACKAGE_DDL_STMT_PREFIX =
        "SELECT DBMS_METADATA.GET_DDL('PACKAGE_SPEC', '";
	static String getDDLForPackage(String packageName) {
	    return getDDL(DBMS_METADATA_GET_PACKAGE_DDL_STMT_PREFIX + packageName +
            DBMS_METADATA_DDL_STMT_SUFFIX);
	}

    static final String DBMS_METADATA_GET_PROCEDURE_DDL_STMT_PREFIX =
        "SELECT DBMS_METADATA.GET_DDL('PROCEDURE', '";
    static String getDDLForProcedure(String procedureName) {
        return getDDL(DBMS_METADATA_GET_PROCEDURE_DDL_STMT_PREFIX + procedureName +
            DBMS_METADATA_DDL_STMT_SUFFIX);
    }

    static final String DBMS_METADATA_GET_FUNCTION_DDL_STMT_PREFIX =
        "SELECT DBMS_METADATA.GET_DDL('FUNCTION', '";
    static String getDDLForFunction(String functionName) {
        return getDDL(DBMS_METADATA_GET_FUNCTION_DDL_STMT_PREFIX + functionName +
            DBMS_METADATA_DDL_STMT_SUFFIX);
    }

    static final String DBMS_METADATA_GET_TABLE_DDL_STMT_PREFIX =
        "SELECT DBMS_METADATA.GET_DDL('TABLE', '";
    static String getDDLForTable(String tableName) {
        String ddl = getDDL(DBMS_METADATA_GET_TABLE_DDL_STMT_PREFIX + tableName +
            DBMS_METADATA_DDL_STMT_SUFFIX);
        return ddl;
    }

    static final String DBMS_METADATA_GET_TYPE_DDL_STMT_PREFIX =
        "SELECT DBMS_METADATA.GET_DDL('TYPE_SPEC', '";
    static String getDDLForType(String typeName) {
        return getDDL(DBMS_METADATA_GET_TYPE_DDL_STMT_PREFIX + typeName +
            DBMS_METADATA_DDL_STMT_SUFFIX);
    }
    
    static PLSQLPackageType parsePackage(String expectedPackageName) {
        boolean worked = true;
        String message = "";
        PLSQLPackageType packageType = null;
        try {
            parser.setTypesRepository(new DatabaseTypesRepository());
            packageType = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(expectedPackageName + " did not parse correctly:\n" + message, worked);
        return packageType;
    }

    static final String PACKAGE_NAME = "CURSOR_TEST";
    static final String DOT_NAME = "SCOTT.CURSOR_TEST";
    static final String QUOTED_DOT_NAME = "\"SCOTT\".\"CURSOR_TEST\"";
    static final String EMPTY_PACKAGE_PREFIX = 
        "CREATE OR REPLACE PACKAGE ";
    static final String EMPTY_PACKAGE_BODY = " AS \n";
    static final String EMPTY_PACKAGE_SUFFIX =
        "END CURSOR_TEST;"; 
    //@Ignore
    @Test
    public void testEmptyPackage() {
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + EMPTY_PACKAGE_SUFFIX));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(PACKAGE_NAME);
    }

    //@Ignore
    @Test
	public void testEmptyPackageDN() {
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + DOT_NAME +
            EMPTY_PACKAGE_BODY + EMPTY_PACKAGE_SUFFIX));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(DOT_NAME);
	}

    //@Ignore
    @Test
    public void testEmptyPackageQDN()  {
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + QUOTED_DOT_NAME +
            EMPTY_PACKAGE_BODY + EMPTY_PACKAGE_SUFFIX));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(DOT_NAME);
    }

    static final String VARIABLE_DECLARATION =
        "urban_legend  CONSTANT BOOLEAN := FALSE; -- PL/SQL-only data type\n"; 
    //@Ignore
    @Test
    public void testVariableDeclaration() {
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + VARIABLE_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(PACKAGE_NAME);
    }

    static final String SIMPLE_RECORD_DECLARATION =
        "TYPE EREC IS RECORD (\n" +
            "FLAG PLS_INTEGER,\n" +
            "EMPNO NUMBER(4),\n" +
            "ENAME VARCHAR2(10),\n" +
            "JOB VARCHAR2(9)\n" +
        ");"; 
    //@Ignore
    @Test
    public void testSimpleRecordDeclaration() {
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + SIMPLE_RECORD_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(PACKAGE_NAME);
    }

    static final String COMPLEX_RECORD_DECLARATION =
        "TYPE EREC IS RECORD (\n" +
            "FLAG PLS_INTEGER,\n" +
            "EMPNO EMPNO%TYPE,\n" +
            "ENAME SCOTT.ENAME%TYPE,\n" +
            "JOB VARCHAR2(9)\n" +
        ");";
    //@Ignore
    @Test
    public void testComplexRecordDeclaration() {
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + COMPLEX_RECORD_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(PACKAGE_NAME);
    }
    
    static final String NESTED_RECORD_DECLARATION =
        "TYPE DREC IS RECORD (\n" +
            "DEPT NUMBER(4),\n" +
            "EMP EREC\n" +
        ");"; 
    //@Ignore
    @Test
    public void testNestedRecordDeclaration() {
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + NESTED_RECORD_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(PACKAGE_NAME);
    }
    
    static final String WEAK_REF_CURSOR_DECLARATION =
        "TYPE rcursor IS REF CURSOR;"; 
    //@Ignore
    @Test
    public void testWeakRefCursorDeclaration() {
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + WEAK_REF_CURSOR_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(PACKAGE_NAME);
    }
    
    static final String TYPED_REF_CURSOR_DECLARATION =
        "TYPE EREC IS RECORD (\n" +
            "FLAG PLS_INTEGER,\n" +
            "EMPNO NUMBER(4),\n" +
            "ENAME EMP.ENAME%TYPE,\n" +
            "JOB VARCHAR2(9)\n" +
        ");\n" +
        "TYPE EREC_CURSOR IS REF CURSOR RETURN EREC;"; 
    //@Ignore
    @Test
    public void testTypedRefCursorDeclaration() {
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + TYPED_REF_CURSOR_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(PACKAGE_NAME);
    }

    static final String QUALCOMM_PACKAGE = "yms_pkg";
    static final String QUALCOMM_DECLARATION =
        "CREATE OR REPLACE PACKAGE " + QUALCOMM_PACKAGE + "\n" +
        "AS\n" +
        "\n" +
        "   TYPE rMetadataRecord IS RECORD (\n" +
        "      lot_id                    CLASSIFIED_TEST_DATA.lot_id%TYPE,\n" +
        "      source_lot                CLASSIFIED_TEST_DATA.source_lot_id%TYPE,\n" +
        "      document_id               DATA_FILE_LOG.document_id%TYPE,\n" +
        "      supplier                  TRADING_PARTNER.TP_NAME%TYPE,\n" +
        "      fab                       CLASSIFIED_TEST_DATA.fab%TYPE,\n" +
        "      test_program              CLASSIFIED_TEST_DATA.test_program%TYPE,\n" +
        "      program_class             CLASSIFIED_TEST_DATA.program_class%TYPE,\n" +
        "      lot_type                  CLASSIFIED_TEST_DATA.lot_type%TYPE,\n" +
        "      mpn                       CLASSIFIED_TEST_DATA.mpn%TYPE,\n" +
        "      mcn                       CLASSIFIED_TEST_DATA.mcn%TYPE,\n" +
        "      orig_start_timestamp      CLASSIFIED_TEST_DATA.orig_start_timestamp%TYPE,\n" +
        "      orig_end_timestamp        CLASSIFIED_TEST_DATA.orig_end_timestamp%TYPE,\n" +
        "      orig_publish_timestamp    CLASSIFIED_TEST_DATA.orig_publish_timestamp%TYPE,\n" +
        "      received_timestamp        DATA_FILE_LOG.received_timestamp%TYPE,\n" +
        "      data_type_name            DATA_TYPE.data_type_name%TYPE,\n" +
        "      file_format_name          FILE_FORMAT.file_format_name%TYPE,\n" +
        "      retest_code               CLASSIFIED_TEST_DATA.retest_code%TYPE,\n" +
        "      test_location             CLASSIFIED_TEST_DATA.test_location%TYPE,\n" +
        "      product                   CLASSIFIED_TEST_DATA.product%TYPE,\n" +
        "      family                    CLASSIFIED_TEST_DATA.family%TYPE,\n" +
        "      foundry                   CLASSIFIED_TEST_DATA.foundry%TYPE,\n" +
        "      project                   CLASSIFIED_TEST_DATA.project%TYPE,\n" +
        "      process                   CLASSIFIED_TEST_DATA.process%TYPE,\n" +
        "      technology                CLASSIFIED_TEST_DATA.technology%TYPE,\n" +
        "      gdpw                      CLASSIFIED_TEST_DATA.gdpw%TYPE,\n" +
        "      checksum                  CLASSIFIED_TEST_DATA.checksum%TYPE,\n" +
        "      operation             CLASSIFIED_TEST_DATA.operation%TYPE,\n" +
        "      isReconciledData              VARCHAR2(8),\n" +
        "      package_count         CLASSIFIED_TEST_DATA.package_count%TYPE,\n" +
        "      total_bin_count           CLASSIFIED_TEST_DATA.total_bin_count%TYPE,\n" +
        "      overall_quantity_in       CLASSIFIED_TEST_DATA.overall_quantity_in%TYPE,\n" +
        "      overall_quantity_out      CLASSIFIED_TEST_DATA.overall_quantity_out%TYPE,\n" +
        "      tp_number             TRADING_PARTNER.tp_number%TYPE,\n" +
        "      equipment_type        CLASSIFIED_TEST_DATA.equipment_type%TYPE,\n" +
        "      tester_name           CLASSIFIED_TEST_DATA.tester_name%TYPE,\n" +
        "      tis_id                CLASSIFIED_TEST_DATA.tis_id%TYPE,\n" +
        "      testing_mode                CLASSIFIED_TEST_DATA.testing_mode%TYPE,\n" +
        "      data_type_for_classification   DATA_FLOW.data_type%TYPE,\n" +
        "      family_number   CLASSIFIED_TEST_DATA.family_number%TYPE,\n" +
        "      ttr_rule_guid       TTR_RULE_DEFINITION.ttr_rule_guid%TYPE,\n" +
        "      ttr_rule_version       TTR_RULE_DEFINITION.ttr_rule_version%TYPE,\n" +
        "      ttr_rule_name       TTR_RULE_DEFINITION.ttr_rule_name%TYPE,\n" +
        "      ttr_rule_creation_date CLASSIFIED_TTR_RULE_DATA.ttr_rule_creation_date%TYPE,\n" +
        "      otmessage_type_name OTMESSAGE_TYPE.otmessage_type_name%TYPE,\n" +
        "      otmessage_action_name OTMESSAGE_ACTION.otmessage_action_name%TYPE,\n" +
        "      first_pass_total_good   CLASSIFIED_MES_DATA.first_pass_total_good%TYPE,\n" +
        "      first_pass_total_bad   CLASSIFIED_MES_DATA.first_pass_total_bad%TYPE,\n" +
        "      last_pass_total_good   CLASSIFIED_MES_DATA.last_pass_total_good%TYPE,\n" +
        "      last_pass_total_bad   CLASSIFIED_MES_DATA.last_pass_total_bad%TYPE,\n" +
        "      calculated_yield   CLASSIFIED_MES_DATA.calculated_yield%TYPE,\n" +
        "      ot_software_version   ot_version.ot_software_version%TYPE,\n" +
        "      ot_tester_version   ot_version.ot_tester_version%TYPE,\n" +
        "      ot_tester_type_name   ot_tester_type.ot_tester_type_name%TYPE,\n" +
        "      specification_version   schema_specification.specification_version%TYPE,\n" +
        "      schema   schema_specification.schema%TYPE,\n" +
        "      tp_type_name  trading_partner_type.tp_type_name%TYPE\n" +
        "   );\n" +
        "\n" +
        "   TYPE rcmetadatacursor IS REF CURSOR\n" +
        "      RETURN rmetadatarecord;\n" +
        "\n" +
        "   FUNCTION get_metadata (\n" +
        "      inprocessinstanceid   data_file_log.process_instance_id%TYPE\n" +
        "   )\n" +
        "      RETURN rcmetadatacursor;\n" +
        "\n" +
        "   FUNCTION is_reconciled_data (indataflowid data_flow.data_flow_id%TYPE := 3)\n" +
        "      RETURN VARCHAR2;\n" +
        "\n" +
        "END yms_pkg;";  
    //@Ignore
    @Test
    public void testQualcommPackage() {
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(QUALCOMM_DECLARATION));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(QUALCOMM_PACKAGE);
    }

    //@Ignore
    @Test
    public void testCursorTestPackageFromDatabase() {
        String ddlForPackage = getDDLForPackage(PACKAGE_NAME);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddlForPackage));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(DOT_NAME);
    }

    static final String SOME_PACKAGE = "SOMEPACKAGE";
    //@Ignore
    @Test
    public void testSomePackageFromDatabase() {
        String ddlForPackage = getDDLForPackage(SOME_PACKAGE);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddlForPackage));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(username.toUpperCase() + "." + SOME_PACKAGE);
    }

    static final String ANOTHER_ADVANCED_DEMO_PACKAGE = "ANOTHER_ADVANCED_DEMO";
    //@Ignore
    @Test
    public void testAnotherAdvancedDemoPackageFromDatabase() {
        String ddlForPackage = getDDLForPackage(ANOTHER_ADVANCED_DEMO_PACKAGE);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddlForPackage));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(username.toUpperCase() + "." + ANOTHER_ADVANCED_DEMO_PACKAGE);
    }

    static final String ADVANCED_OBJECT_DEMO_PACKAGE = "ADVANCED_OBJECT_DEMO";
    //@Ignore
    @Test
    public void testAdvancedObjectDemoPackageFromDatabase() {
        String ddlForPackage = getDDLForPackage(ADVANCED_OBJECT_DEMO_PACKAGE);
        parser.ReInit(new StringReader(ddlForPackage));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(username.toUpperCase() + "." + ADVANCED_OBJECT_DEMO_PACKAGE);
    }
    
    static final String TEST_TYPES_PACKAGE = "TEST_TYPES";
    //@Ignore
    @Test
    public void testTestTypesPackageFromDatabase() {
        String ddlForPackage = getDDLForPackage(TEST_TYPES_PACKAGE);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddlForPackage));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(username.toUpperCase() + "." + TEST_TYPES_PACKAGE);
    }

    static final String LTBL_PACKAGE = "LTBL_PKG";
    //@Ignore
    @Test
    public void testLTBLPackageFromDatabase() {
        String ddlForPackage = getDDLForPackage(LTBL_PACKAGE);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddlForPackage));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(username.toUpperCase() + "." + LTBL_PACKAGE);
    }
    
    static final String TESMAN_PACKAGE = "TESMANPACK";
    //@Ignore
    @Test
    public void testTesmanFromDatabase() {
        String ddlForPackage = getDDLForPackage(TESMAN_PACKAGE);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddlForPackage));
        @SuppressWarnings("unused") PLSQLPackageType packageType = parsePackage(username.toUpperCase() + "." + TESMAN_PACKAGE);
    }
    
    static final String TOPLEVEL_PROCEDURE_BOOL_IN_TEST = "BOOL_IN_TEST";
    //@Ignore
    @Test
    public void testTopLevelProcedure_BoolInTest() {
        String ddl = getDDLForProcedure(TOPLEVEL_PROCEDURE_BOOL_IN_TEST);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddl));
        boolean worked = true;
        String message = "";
        @SuppressWarnings("unused")
        ProcedureType procedureType = null;
        try {
            procedureType = parser.parseTopLevelProcedure();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(TOPLEVEL_PROCEDURE_BOOL_IN_TEST + " did not parse correctly:\n" + message, worked);
    }
    
    static final String TOPLEVEL_FUNCTION_BUILDTBL2 = "BUILDTBL2";
    //@Ignore
    @Test
    public void testTopLevelFunction_BUILDTBL2() {
        String ddl = getDDLForFunction(TOPLEVEL_FUNCTION_BUILDTBL2);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddl));
        boolean worked = true;
        String message = "";
        @SuppressWarnings("unused") FunctionType functionType = null;
        try {
            functionType = parser.parseTopLevelFunction();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(TOPLEVEL_FUNCTION_BUILDTBL2 + " did not parse correctly:\n" + message, worked);
    }
    
    static final String TYPE_EMP_INFO = "EMP_INFO";
    //@Ignore
    @Test
    public void testType_EMP_INFO() {
        String ddl = getDDLForType(TYPE_EMP_INFO);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddl));
        boolean worked = true;
        String message = "";
        SimpleNode parseNode = null;
        try {
            parseNode = parser.parseType();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(TYPE_EMP_INFO + " did not parse correctly:\n" + message, worked);
        parseNode.dump("");
    }

    static final String TYPE_SOMEPACKAGE_TBL1 = "SOMEPACKAGE_TBL1";
    //@Ignore
    @Test
    public void testType_SOMEPACKAGE_TBL1() {
        String ddl = getDDLForType(TYPE_SOMEPACKAGE_TBL1);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddl));
        boolean worked = true;
        String message = "";
        SimpleNode parseNode = null;
        try {
            parseNode = parser.parseType();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(TYPE_SOMEPACKAGE_TBL1 + " did not parse correctly:\n" + message, worked);
        parseNode.dump("");
    }
    
    static final String TABLE_BONUS = "BONUS";
    //@Ignore
    @Test
    public void testTable_Bonus() {
        String ddl = getDDLForTable(TABLE_BONUS);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddl));
        boolean worked = true;
        String message = "";
        TableType table = null;
        try {
            table = (TableType)parser.parseTable();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(TABLE_BONUS + " did not parse correctly:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(table);
        assertTrue(TABLE_BONUS + " table should not contain any unresolved column datatypes",
        	l.getUnresolvedTypes().isEmpty());
        System.out.println(table.toString());
    }
    
    static final String TEMP_TABLE = "TAXABLE_EMP";
    //@Ignore
    @Test
    public void testTempTable_TaxableEmp() {
        String ddl = getDDLForTable(TEMP_TABLE);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddl));
        boolean worked = true;
        String message = "";
        TableType table = null;
        try {
            table = (TableType)parser.parseTable();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(TEMP_TABLE + " did not parse correctly:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(table);
        assertTrue(TEMP_TABLE + " table should not contain any unresolved column datatypes",
            l.getUnresolvedTypes().isEmpty());
        System.out.println(table.toString());
    }
    
    static final String TABLE_XR_VEE_ARRAY_EMP = "XR_VEE_ARRAY_EMP";
    //@Ignore
    @Test
    public void testTable_XR_VEE_ARRAY_EMP() {
        String ddl = getDDLForTable(TABLE_XR_VEE_ARRAY_EMP);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddl));
        boolean worked = true;
        String message = "";
        TableType table = null;
        try {
            table = (TableType)parser.parseTable();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(TABLE_XR_VEE_ARRAY_EMP + " did not parse correctly:\n" + message, worked);
        UnresolvedTypesVisitor l = new UnresolvedTypesVisitor();
        l.visit(table);
        assertFalse(TABLE_XR_VEE_ARRAY_EMP + " table should contain unresolved column datatypes",
            l.getUnresolvedTypes().isEmpty());
        System.out.println(table.toString());
    }
    
    static final String TABLE_SUBIMAGEINFO = "SUBIMAGEINFO";
    //@Ignore
    @Test
    public void testTable_SUBIMAGEINFO() {
        String ddl = getDDLForTable(TABLE_SUBIMAGEINFO);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddl));
        boolean worked = true;
        String message = "";
        TableType table = null;
        try {
            table = (TableType)parser.parseTable();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(TABLE_SUBIMAGEINFO + " did not parse correctly:\n" + message, worked);
        System.out.println(table.toString());
    }

    static final String NESTED_TABLE_LTBL = "LTBL_PKG_LTBL_TAB";
    //@Ignore
    @Test
    public void testNestedTable_LTBL_PKG_LTBL_TAB() {
        String ddl = getDDLForType(NESTED_TABLE_LTBL);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddl));
        boolean worked = true;
        String message = "";
        SimpleNode parseNode = null;
        try {
            parseNode = parser.parseType();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(NESTED_TABLE_LTBL + " did not parse correctly:\n" + message, worked);
        parseNode.dump("");
    }

    static final String VARRAY_TYPE = "EMP_INFO_ARRAY";
    //@Ignore
    @Test
    public void testVarray_EMP_INFO_ARRAY() {
        String ddl = getDDLForType(VARRAY_TYPE);
        parser.setTypesRepository(new DatabaseTypesRepository());
        parser.ReInit(new StringReader(ddl));
        boolean worked = true;
        String message = "";
        SimpleNode parseNode = null;
        try {
            parseNode = parser.parseType();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue(VARRAY_TYPE + " did not parse correctly:\n" + message, worked);
        parseNode.dump("");
    }
}