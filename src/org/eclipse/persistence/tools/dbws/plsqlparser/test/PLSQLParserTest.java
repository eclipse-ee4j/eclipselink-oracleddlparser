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
    //@Ignore
    @Test
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

    //@Ignore
    @Test
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

    //@Ignore
    @Test
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
    //@Ignore
    @Test
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
    //@Ignore
    @Test
    public void testSimpleRecordDeclaration() {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + SIMPLE_RECORD_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        boolean worked = true;
        String message = "";
        PLSQLNode parseNode = null;
        try {
            parseNode = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("package with simple record declaration did not parse correctly:\n" + message, worked);
        parseNode.dump("");
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
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + COMPLEX_RECORD_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        boolean worked = true;
        String message = "";
        PLSQLNode parseNode = null;
        try {
            parseNode = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("package with complex record declaration did not parse correctly:\n" + message, worked);
        parseNode.dump("");
    }
    
    static final String NESTED_RECORD_DECLARATION =
        "TYPE DREC IS RECORD (\n" +
            "DEPT NUMBER(4),\n" +
            "EMP EREC\n" +
        ");";
    //@Ignore
    @Test
    public void testNestedRecordDeclaration() {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + NESTED_RECORD_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        boolean worked = true;
        String message = "";
        PLSQLNode parseNode = null;
        try {
            parseNode = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("package with complex nested record declaration did not parse correctly:\n" + message, worked);
        parseNode.dump("");
    }
    
    static final String WEAK_REF_CURSOR_DECLARATION =
        "TYPE rcursor IS REF CURSOR;";
    //@Ignore
    @Test
    public void testWeakRefCursorDeclaration() {
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + WEAK_REF_CURSOR_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        boolean worked = true;
        String message = "";
        PLSQLNode parseNode = null;
        try {
            parseNode = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("package with weak ref cursor declaration did not parse correctly:\n" + message, worked);
        parseNode.dump("");
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
        parser.ReInit(new StringReader(EMPTY_PACKAGE_PREFIX + PACKAGE_NAME +
            EMPTY_PACKAGE_BODY + TYPED_REF_CURSOR_DECLARATION + EMPTY_PACKAGE_SUFFIX));
        boolean worked = true;
        String message = "";
        PLSQLNode parseNode = null;
        try {
            parseNode = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("package with typed ref cursor declaration did not parse correctly:\n" + message, worked);
        parseNode.dump("");
    }
    
    static final String YMS_DECLARATION =
        "CREATE OR REPLACE PACKAGE yms_pkg\n" +
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
    public void testYMSDeclaration() {
        parser.ReInit(new StringReader(YMS_DECLARATION));
        boolean worked = true;

        String message = "";
        PLSQLNode parseNode = null;
        try {
            parseNode = parser.parsePLSQLPackage();
        }
        catch (ParseException pe) {
            //pe.printStackTrace();
            message = pe.getMessage();
            worked = false;
        }
        assertTrue("YMS package did not parse correctly:\n" + message, worked);
        parseNode.dump("");
    }
}