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
 *     David McCann - Aug.08, 2011 - 2.4 - Initial implementation
 ******************************************************************************/
package org.eclipse.persistence.tools.oracleddl.test.metadata;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import javax.wsdl.WSDLException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 * Validate generated documents;  WSDL, XSD, OR and OX projects.
 * This test suite makes use of the TABLETYPE database table.
 *
 */
public class ValidationTests extends TestHelper {
    
    @BeforeClass
    public static void setUp() throws WSDLException {
        DBWS_BUILDER_XML_USERNAME =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
          "<dbws-builder xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" +
            "<properties>" +
                "<property name=\"projectName\">tabletype</property>" +
                "<property name=\"logLevel\">off</property>" +
                "<property name=\"username\">";
        DBWS_BUILDER_XML_PASSWORD =
                "</property><property name=\"password\">";
        DBWS_BUILDER_XML_URL =
                "</property><property name=\"url\">";
        DBWS_BUILDER_XML_DRIVER =
                "</property><property name=\"driver\">";
        DBWS_BUILDER_XML_PLATFORM =
                "</property><property name=\"platformClassname\">";
        DBWS_BUILDER_XML_MAIN =
                "</property>" +
            "</properties>" +
            "<table " +
              "schemaPattern=\"%\" " +
              "tableNamePattern=\"TABLETYPE\" " +
            "/>" +
          "</dbws-builder>";

        TestHelper.setUp(".");
        
        //System.out.println(DBWS_WSDL_STREAM.toString());
        //System.out.println(DBWS_OX_STREAM.toString());
        //System.out.println(DBWS_OR_STREAM.toString());
        //System.out.println(DBWS_SCHEMA_STREAM.toString());
    }
    
    @Test
    public void validateSchema() {
        Document testDoc = xmlParser.parse(new StringReader(DBWS_SCHEMA_STREAM.toString()));
        Document controlDoc = xmlParser.parse(new StringReader(XSD));
        removeEmptyTextNodes(testDoc);
        removeEmptyTextNodes(controlDoc);
        assertTrue("Schema validation failed:\nExpected:" + XSD + "\nbut was:\n" + DBWS_SCHEMA_STREAM.toString(), comparer.isNodeEqual(controlDoc, testDoc));
    }
    
    @Test
    public void validateWSDL() {
        Document testDoc = xmlParser.parse(new StringReader(DBWS_WSDL_STREAM.toString()));
        Document controlDoc = xmlParser.parse(new StringReader(WSDL));
        removeEmptyTextNodes(testDoc);
        removeEmptyTextNodes(controlDoc);
        assertTrue("Schema validation failed:\nExpected:" + WSDL + "\nbut was:\n" + DBWS_WSDL_STREAM.toString(), comparer.isNodeEqual(controlDoc, testDoc));
    }

    @Test
    public void validateORProject() {
        Document testDoc = xmlParser.parse(new StringReader(DBWS_OR_STREAM.toString()));
        Document controlDoc = xmlParser.parse(new StringReader(OR_PROJECT));
        removeEmptyTextNodes(testDoc);
        removeEmptyTextNodes(controlDoc);
        assertTrue("Schema validation failed:\nExpected:" + OR_PROJECT + "\nbut was:\n" + DBWS_OR_STREAM.toString(), comparer.isNodeEqual(controlDoc, testDoc));
    }

    @Test
    public void validateOXProject() {
        Document testDoc = xmlParser.parse(new StringReader(DBWS_OX_STREAM.toString()));
        Document controlDoc = xmlParser.parse(new StringReader(OX_PROJECT));
        removeEmptyTextNodes(testDoc);
        removeEmptyTextNodes(controlDoc);
        assertTrue("Schema validation failed:\nExpected:" + OX_PROJECT + "\nbut was:\n" + DBWS_OX_STREAM.toString(), comparer.isNodeEqual(controlDoc, testDoc));
    }

    protected static final String XSD = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<xsd:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" targetNamespace=\"urn:tabletype\" xmlns=\"urn:tabletype\" elementFormDefault=\"qualified\">\n" +
           "<xsd:complexType name=\"tabletypeType\">\n" +
              "<xsd:sequence>\n" +
                 "<xsd:element name=\"id\" type=\"xsd:double\"/>\n" +
                 "<xsd:element name=\"name\" type=\"xsd:string\" minOccurs=\"0\" nillable=\"true\"/>\n" +
                 "<xsd:element name=\"deptno\" type=\"xsd:decimal\" minOccurs=\"0\" nillable=\"true\"/>\n" +
                 "<xsd:element name=\"deptname\" type=\"xsd:string\" minOccurs=\"0\" nillable=\"true\"/>\n" +
                 "<xsd:element name=\"section\" type=\"xsd:string\" minOccurs=\"0\" nillable=\"true\"/>\n" +
                 "<xsd:element name=\"cubical\" type=\"xsd:string\" minOccurs=\"0\" nillable=\"true\"/>\n" +
                 "<xsd:element name=\"sal\" type=\"xsd:double\" minOccurs=\"0\" nillable=\"true\"/>\n" +
                 "<xsd:element name=\"commission\" type=\"xsd:double\" minOccurs=\"0\" nillable=\"true\"/>\n" +
                 "<xsd:element name=\"sales\" type=\"xsd:double\" minOccurs=\"0\" nillable=\"true\"/>\n" +
                 "<xsd:element name=\"binid\" type=\"xsd:base64Binary\" minOccurs=\"0\" nillable=\"true\"/>\n" +
                 "<xsd:element name=\"b\" type=\"xsd:base64Binary\" minOccurs=\"0\" nillable=\"true\"/>\n" +
                 "<xsd:element name=\"c\" type=\"xsd:string\" minOccurs=\"0\" nillable=\"true\"/>\n" +
                 "<xsd:element name=\"nc\" type=\"xsd:string\" minOccurs=\"0\" nillable=\"true\"/>\n" +
                 "<xsd:element name=\"r\" type=\"xsd:base64Binary\" minOccurs=\"0\" nillable=\"true\"/>\n" +
                 "<xsd:element name=\"lr\" type=\"xsd:base64Binary\" minOccurs=\"0\" nillable=\"true\"/>\n" +
              "</xsd:sequence>\n" +
           "</xsd:complexType>\n" +
           "<xsd:element name=\"tabletypeType\" type=\"tabletypeType\"/>\n" +
        "</xsd:schema>";                    

    protected static final String WSDL =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<wsdl:definitions\n" + 
             "name=\"tabletypeService\"\n" + 
             "targetNamespace=\"urn:tabletypeService\"\n" + 
             "xmlns:ns1=\"urn:tabletype\"\n" + 
             "xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\"\n" + 
             "xmlns:tns=\"urn:tabletypeService\"\n" + 
             "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" + 
             "xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\"\n" +
             ">\n" +
             "<wsdl:types>\n" +
                "<xsd:schema elementFormDefault=\"qualified\" targetNamespace=\"urn:tabletypeService\" xmlns:tns=\"urn:tabletypeService\"\n" + 
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "<xsd:import namespace=\"urn:tabletype\" schemaLocation=\"eclipselink-dbws-schema.xsd\"/>\n" +
                "<xsd:complexType name=\"update_tabletypeTypeRequestType\">\n" +
                   "<xsd:sequence>\n" +
                      "<xsd:element name=\"theInstance\">\n" +
                         "<xsd:complexType>\n" +
                            "<xsd:sequence>\n" +
                               "<xsd:element ref=\"ns1:tabletypeType\"/>\n" +
                            "</xsd:sequence>\n" +
                         "</xsd:complexType>\n" +
                      "</xsd:element>\n" +
                   "</xsd:sequence>\n" +
                "</xsd:complexType>\n" +
                "<xsd:complexType name=\"findAll_tabletypeTypeResponseType\">\n" +
                   "<xsd:sequence>\n" +
                      "<xsd:element name=\"result\">\n" +
                         "<xsd:complexType>\n" +
                            "<xsd:sequence>\n" +
                               "<xsd:element maxOccurs=\"unbounded\" minOccurs=\"0\" ref=\"ns1:tabletypeType\"/>\n" +
                            "</xsd:sequence>\n" +
                         "</xsd:complexType>\n" +
                      "</xsd:element>\n" +
                   "</xsd:sequence>\n" +
                "</xsd:complexType>\n" +
                "<xsd:complexType name=\"delete_tabletypeTypeRequestType\">\n" +
                   "<xsd:sequence>\n" +
                      "<xsd:element name=\"id\" type=\"xsd:double\"/>\n" +
                   "</xsd:sequence>\n" +
                "</xsd:complexType>\n" +
                "<xsd:complexType name=\"findByPrimaryKey_tabletypeTypeResponseType\">\n" +
                   "<xsd:sequence>\n" +
                      "<xsd:element name=\"result\">\n" +
                         "<xsd:complexType>\n" +
                            "<xsd:sequence>\n" +
                               "<xsd:element minOccurs=\"0\" ref=\"ns1:tabletypeType\"/>\n" +
                            "</xsd:sequence>\n" +
                         "</xsd:complexType>\n" +
                      "</xsd:element>\n" +
                   "</xsd:sequence>\n" +
                "</xsd:complexType>\n" +
                "<xsd:complexType name=\"findAll_tabletypeTypeRequestType\"/>\n" +
                "<xsd:complexType name=\"findByPrimaryKey_tabletypeTypeRequestType\">\n" +
                   "<xsd:sequence>\n" +
                      "<xsd:element name=\"id\" type=\"xsd:double\"/>\n" +
                   "</xsd:sequence>\n" +
                "</xsd:complexType>\n" +
                "<xsd:complexType name=\"create_tabletypeTypeRequestType\">\n" +
                   "<xsd:sequence>\n" +
                      "<xsd:element name=\"theInstance\">\n" +
                         "<xsd:complexType>\n" +
                            "<xsd:sequence>\n" +
                               "<xsd:element ref=\"ns1:tabletypeType\"/>\n" +
                            "</xsd:sequence>\n" +
                         "</xsd:complexType>\n" +
                      "</xsd:element>\n" +
                   "</xsd:sequence>\n" +
                "</xsd:complexType>\n" +
                "<xsd:element name=\"update_tabletypeType\" type=\"tns:update_tabletypeTypeRequestType\"/>\n" +
                "<xsd:element name=\"create_tabletypeType\" type=\"tns:create_tabletypeTypeRequestType\"/>\n" +
                "<xsd:element name=\"findAll_tabletypeTypeResponse\" type=\"tns:findAll_tabletypeTypeResponseType\"/>\n" +
                "<xsd:element name=\"findAll_tabletypeType\" type=\"tns:findAll_tabletypeTypeRequestType\"/>\n" +
                "<xsd:element name=\"findByPrimaryKey_tabletypeTypeResponse\" type=\"tns:findByPrimaryKey_tabletypeTypeResponseType\"/>\n" +
                "<xsd:element name=\"findByPrimaryKey_tabletypeType\" type=\"tns:findByPrimaryKey_tabletypeTypeRequestType\"/>\n" +
                "<xsd:element name=\"FaultType\">\n" +
                   "<xsd:complexType>\n" +
                      "<xsd:sequence>\n" +
                         "<xsd:element name=\"faultCode\" type=\"xsd:string\"/>\n" +
                         "<xsd:element name=\"faultString\" type=\"xsd:string\"/>\n" +
                      "</xsd:sequence>\n" +
                   "</xsd:complexType>\n" +
                "</xsd:element>\n" +
                "<xsd:element name=\"EmptyResponse\">\n" +
                   "<xsd:complexType/>\n" +
                "</xsd:element>\n" +
                "<xsd:element name=\"delete_tabletypeType\" type=\"tns:delete_tabletypeTypeRequestType\"/>\n" +
                 "</xsd:schema>\n" +
              "</wsdl:types>\n" +
              "<wsdl:message name=\"FaultType\">\n" +
                 "<wsdl:part name=\"fault\" element=\"tns:FaultType\">\n" +"</wsdl:part>\n" +
              "</wsdl:message>\n" +
              "<wsdl:message name=\"findByPrimaryKey_tabletypeTypeResponse\">\n" +
                 "<wsdl:part name=\"findByPrimaryKey_tabletypeTypeResponse\" element=\"tns:findByPrimaryKey_tabletypeTypeResponse\">\n" +"</wsdl:part>\n" +
              "</wsdl:message>\n" +
              "<wsdl:message name=\"findByPrimaryKey_tabletypeTypeRequest\">\n" +
                 "<wsdl:part name=\"findByPrimaryKey_tabletypeTypeRequest\" element=\"tns:findByPrimaryKey_tabletypeType\">\n" +"</wsdl:part>\n" +
              "</wsdl:message>\n" +
              "<wsdl:message name=\"update_tabletypeTypeRequest\">\n" +
                 "<wsdl:part name=\"update_tabletypeTypeRequest\" element=\"tns:update_tabletypeType\">\n" +"</wsdl:part>\n" +
              "</wsdl:message>\n" +
              "<wsdl:message name=\"EmptyResponse\">\n" +
                 "<wsdl:part name=\"emptyResponse\" element=\"tns:EmptyResponse\">\n" +"</wsdl:part>\n" +
              "</wsdl:message>\n" +
              "<wsdl:message name=\"create_tabletypeTypeRequest\">\n" +
                 "<wsdl:part name=\"create_tabletypeTypeRequest\" element=\"tns:create_tabletypeType\">\n" +"</wsdl:part>\n" +
              "</wsdl:message>\n" +
              "<wsdl:message name=\"findAll_tabletypeTypeRequest\">\n" +
                 "<wsdl:part name=\"findAll_tabletypeTypeRequest\" element=\"tns:findAll_tabletypeType\">\n" +"</wsdl:part>\n" +
              "</wsdl:message>\n" +
              "<wsdl:message name=\"delete_tabletypeTypeRequest\">\n" +
                 "<wsdl:part name=\"delete_tabletypeTypeRequest\" element=\"tns:delete_tabletypeType\">\n" +"</wsdl:part>\n" +
              "</wsdl:message>\n" +
              "<wsdl:message name=\"findAll_tabletypeTypeResponse\">\n" +
                 "<wsdl:part name=\"findAll_tabletypeTypeResponse\" element=\"tns:findAll_tabletypeTypeResponse\">\n" +"</wsdl:part>\n" +
              "</wsdl:message>\n" +
              "<wsdl:portType name=\"tabletypeService_Interface\">\n" +
                 "<wsdl:operation name=\"update_tabletypeType\">\n" +
                    "<wsdl:input message=\"tns:update_tabletypeTypeRequest\">\n" +"</wsdl:input>\n" +
                    "<wsdl:output name=\"update_tabletypeTypeEmptyResponse\" message=\"tns:EmptyResponse\">\n" +"</wsdl:output>\n" +
                    "<wsdl:fault name=\"FaultException\" message=\"tns:FaultType\">\n" +"</wsdl:fault>\n" +
                "</wsdl:operation>\n" +
                "<wsdl:operation name=\"create_tabletypeType\">\n" +
                   "<wsdl:input message=\"tns:create_tabletypeTypeRequest\">\n" +"</wsdl:input>\n" +
                   "<wsdl:output name=\"create_tabletypeTypeEmptyResponse\" message=\"tns:EmptyResponse\">\n" +"</wsdl:output>\n" +
                   "<wsdl:fault name=\"FaultException\" message=\"tns:FaultType\">\n" +"</wsdl:fault>\n" +
                "</wsdl:operation>\n" +
                "<wsdl:operation name=\"findAll_tabletypeType\">\n" +
                   "<wsdl:input message=\"tns:findAll_tabletypeTypeRequest\">\n" +"</wsdl:input>\n" +
                   "<wsdl:output message=\"tns:findAll_tabletypeTypeResponse\">\n" +"</wsdl:output>\n" +
                "</wsdl:operation>\n" +
                "<wsdl:operation name=\"findByPrimaryKey_tabletypeType\">\n" +
                   "<wsdl:input message=\"tns:findByPrimaryKey_tabletypeTypeRequest\">\n" +"</wsdl:input>\n" +
                   "<wsdl:output message=\"tns:findByPrimaryKey_tabletypeTypeResponse\">\n" +"</wsdl:output>\n" +
                "</wsdl:operation>\n" +
                "<wsdl:operation name=\"delete_tabletypeType\">\n" +
                   "<wsdl:input message=\"tns:delete_tabletypeTypeRequest\">\n" +"</wsdl:input>\n" +
                   "<wsdl:output name=\"delete_tabletypeTypeEmptyResponse\" message=\"tns:EmptyResponse\">\n" +"</wsdl:output>\n" +
                   "<wsdl:fault name=\"FaultException\" message=\"tns:FaultType\">\n" +"</wsdl:fault>\n" +
                "</wsdl:operation>\n" +
             "</wsdl:portType>\n" +
             "<wsdl:binding name=\"tabletypeService_SOAP_HTTP\" type=\"tns:tabletypeService_Interface\">\n" +
                "<soap:binding style=\"document\" transport=\"http://schemas.xmlsoap.org/soap/http\"/>\n" +
                "<wsdl:operation name=\"update_tabletypeType\">\n" +
                   "<soap:operation soapAction=\"urn:tabletypeService:update_tabletypeType\"/>\n" +
                   "<wsdl:input>\n" +
                      "<soap:body use=\"literal\"/>\n" +
                   "</wsdl:input>\n" +
                   "<wsdl:output>\n" +
                      "<soap:body use=\"literal\"/>\n" +
                   "</wsdl:output>\n" +
                   "<wsdl:fault name=\"FaultException\">\n" +
                       "<soap:fault name=\"FaultException\" use=\"literal\"/>\n" +
                   "</wsdl:fault>\n" +
                "</wsdl:operation>\n" +
                "<wsdl:operation name=\"create_tabletypeType\">\n" +
                   "<soap:operation soapAction=\"urn:tabletypeService:create_tabletypeType\"/>\n" +
                   "<wsdl:input>\n" +
                      "<soap:body use=\"literal\"/>\n" +
                   "</wsdl:input>\n" +
                   "<wsdl:output>\n" +
                      "<soap:body use=\"literal\"/>\n" +
                   "</wsdl:output>\n" +
                   "<wsdl:fault name=\"FaultException\">\n" +
                      "<soap:fault name=\"FaultException\" use=\"literal\"/>\n" +
                   "</wsdl:fault>\n" +
                "</wsdl:operation>\n" +
                "<wsdl:operation name=\"findAll_tabletypeType\">\n" +
                   "<soap:operation soapAction=\"urn:tabletypeService:findAll_tabletypeType\"/>\n" +
                   "<wsdl:input>\n" +
                      "<soap:body use=\"literal\"/>\n" +
                   "</wsdl:input>\n" +
                   "<wsdl:output>\n" +
                      "<soap:body use=\"literal\"/>\n" +
                   "</wsdl:output>\n" +
                "</wsdl:operation>\n" +
                "<wsdl:operation name=\"findByPrimaryKey_tabletypeType\">\n" +
                   "<soap:operation soapAction=\"urn:tabletypeService:findByPrimaryKey_tabletypeType\"/>\n" +
                   "<wsdl:input>\n" +
                      "<soap:body use=\"literal\"/>\n" +
                   "</wsdl:input>\n" +
                   "<wsdl:output>\n" +
                      "<soap:body use=\"literal\"/>\n" +
                   "</wsdl:output>\n" +
                "</wsdl:operation>\n" +
                "<wsdl:operation name=\"delete_tabletypeType\">\n" +
                   "<soap:operation soapAction=\"urn:tabletypeService:delete_tabletypeType\"/>\n" +
                   "<wsdl:input>\n" +
                      "<soap:body use=\"literal\"/>\n" +
                   "</wsdl:input>\n" +
                   "<wsdl:output>\n" +
                      "<soap:body use=\"literal\"/>\n" +
                   "</wsdl:output>\n" +
                   "<wsdl:fault name=\"FaultException\">\n" +
                      "<soap:fault name=\"FaultException\" use=\"literal\"/>\n" +
                   "</wsdl:fault>\n" +
                "</wsdl:operation>\n" +
             "</wsdl:binding>\n" +
             "<wsdl:service name=\"tabletypeService\">\n" +
                "<wsdl:port name=\"tabletypeServicePort\" binding=\"tns:tabletypeService_SOAP_HTTP\">\n" +
                   "<soap:address location=\"REPLACE_WITH_ENDPOINT_ADDRESS\"/>\n" +
                "</wsdl:port>\n" +
             "</wsdl:service>\n" +
        "</wsdl:definitions>\n";
    
    protected static final String OR_PROJECT = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<object-persistence xmlns=\"http://www.eclipse.org/eclipselink/xsds/persistence\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:eclipselink=\"http://www.eclipse.org/eclipselink/xsds/persistence\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"Eclipse Persistence Services - @VERSION@.@QUALIFIER@\">\n" +
           "<name>tabletype-dbws-or</name>\n" +
           "<class-mapping-descriptors>\n" +
              "<class-mapping-descriptor xsi:type=\"relational-class-mapping-descriptor\">\n" +
                 "<class>tabletype.Tabletype</class>\n" +
                 "<alias>tabletypeType</alias>\n" +
                 "<primary-key>\n" +
                    "<field table=\"TABLETYPE\" name=\"ID\" sql-typecode=\"2\" xsi:type=\"column\"/>\n" +
                 "</primary-key>\n" +
                 "<events/>\n" +
                 "<querying>\n" +
                    "<queries>\n" +
                       "<query name=\"findByPrimaryKey\" xsi:type=\"read-object-query\">\n" +
                          "<criteria operator=\"equal\" xsi:type=\"relation-expression\">\n" +
                             "<left xsi:type=\"field-expression\">\n" +
                                "<field table=\"TABLETYPE\" name=\"ID\" sql-typecode=\"2\" xsi:type=\"column\"/>\n" +
                                "<base xsi:type=\"base-expression\"/>\n" +
                             "</left>\n" +
                             "<right xsi:type=\"parameter-expression\">\n" +
                                "<parameter name=\"id\" xsi:type=\"column\"/>\n" +
                             "</right>\n" +
                          "</criteria>\n" +
                          "<arguments>\n" +
                             "<argument name=\"id\">\n" +
                                "<type>java.lang.Object</type>\n" +
                             "</argument>\n" +
                          "</arguments>\n" +
                          "<reference-class>tabletype.Tabletype</reference-class>\n" +
                       "</query>\n" +
                       "<query name=\"findAll\" xsi:type=\"read-all-query\">\n" +
                          "<reference-class>tabletype.Tabletype</reference-class>\n" +
                          "<container xsi:type=\"list-container-policy\">\n" +
                             "<collection-type>java.util.Vector</collection-type>\n" +
                          "</container>\n" +
                       "</query>\n" +
                    "</queries>\n" +
                 "</querying>\n" +
                 "<attribute-mappings>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>id</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"ID\" sql-typecode=\"2\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>java.math.BigInteger</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>name</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"NAME\" sql-typecode=\"12\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>java.lang.String</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>deptno</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"DEPTNO\" sql-typecode=\"3\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>java.math.BigDecimal</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>deptname</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"DEPTNAME\" sql-typecode=\"12\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>java.lang.String</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>section</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"SECTION\" sql-typecode=\"1\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>java.lang.Character</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>cubical</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"CUBICAL\" sql-typecode=\"-15\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>java.lang.Object</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>sal</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"SAL\" sql-typecode=\"6\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>java.lang.Float</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>commission</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"COMMISSION\" sql-typecode=\"7\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>java.lang.Float</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>sales</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"SALES\" sql-typecode=\"8\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>java.lang.Double</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>binid</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"BINID\" sql-typecode=\"-2\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>[B</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>b</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"B\" sql-typecode=\"2004\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>[B</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>c</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"C\" sql-typecode=\"2005\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>[Ljava.lang.Character;</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>nc</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"NC\" sql-typecode=\"2011\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>java.lang.Object</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>r</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"R\" sql-typecode=\"-3\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>[B</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"direct-mapping\">\n" +
                       "<attribute-name>lr</attribute-name>\n" +
                       "<field table=\"TABLETYPE\" name=\"LR\" sql-typecode=\"-4\" xsi:type=\"column\"/>\n" +
                       "<attribute-classification>[B</attribute-classification>\n" +
                    "</attribute-mapping>\n" +
                 "</attribute-mappings>\n" +
                 "<descriptor-type>independent</descriptor-type>\n" +
                 "<caching>\n" +
                    "<cache-type>weak-reference</cache-type>\n" +
                    "<cache-size>-1</cache-size>\n" +
                 "</caching>\n" +
                 "<remote-caching>\n" +
                    "<cache-type>weak-reference</cache-type>\n" +
                    "<cache-size>-1</cache-size>\n" +
                 "</remote-caching>\n" +
                 "<instantiation/>\n" +
                 "<copying xsi:type=\"instantiation-copy-policy\"/>\n" +
                 "<tables>\n" +
                    "<table name=\"TABLETYPE\"/>\n" +
                 "</tables>\n" +
              "</class-mapping-descriptor>\n" +
           "</class-mapping-descriptors>\n" +
           "<login xsi:type=\"database-login\">\n" +
              "<platform-class>org.eclipse.persistence.platform.database.DatabasePlatform</platform-class>\n" +
              "<connection-url>\n" +"</connection-url>\n" +
           "</login>\n" +
        "</object-persistence>\n";

    protected static final String OX_PROJECT = 
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<object-persistence xmlns=\"http://www.eclipse.org/eclipselink/xsds/persistence\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:eclipselink=\"http://www.eclipse.org/eclipselink/xsds/persistence\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"Eclipse Persistence Services - @VERSION@.@QUALIFIER@\">\n" +
           "<name>tabletype-dbws-ox</name>\n" +
           "<class-mapping-descriptors>\n" +
              "<class-mapping-descriptor xsi:type=\"xml-class-mapping-descriptor\">\n" +
                 "<class>tabletype.Tabletype</class>\n" +
                 "<alias>tabletypeType</alias>\n" +
                 "<events/>\n" +
                 "<querying/>\n" +
                 "<attribute-mappings>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>id</attribute-name>\n" +
                       "<field name=\"id/text()\" is-required=\"true\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}double</schema-type>\n" +
                       "</field>\n" +
                       "<attribute-classification>java.math.BigInteger</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<empty-node-represents-null>true</empty-node-represents-null>\n" +
                          "<null-representation-for-xml>ABSENT_NODE</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>name</attribute-name>\n" +
                       "<field name=\"name/text()\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}string</schema-type>\n" +
                       "</field>\n" +
                       "<attribute-classification>java.lang.String</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<xsi-nil-represents-null>true</xsi-nil-represents-null>\n" +
                          "<null-representation-for-xml>XSI_NIL</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>deptno</attribute-name>\n" +
                       "<field name=\"deptno/text()\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}decimal</schema-type>\n" +
                       "</field>\n" +
                       "<attribute-classification>java.math.BigDecimal</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<xsi-nil-represents-null>true</xsi-nil-represents-null>\n" +
                          "<null-representation-for-xml>XSI_NIL</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>deptname</attribute-name>\n" +
                       "<field name=\"deptname/text()\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}string</schema-type>\n" +
                       "</field>\n" +
                       "<attribute-classification>java.lang.String</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<xsi-nil-represents-null>true</xsi-nil-represents-null>\n" +
                          "<null-representation-for-xml>XSI_NIL</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>section</attribute-name>\n" +
                       "<field name=\"section/text()\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}string</schema-type>\n" +
                       "</field>\n" +
                       "<attribute-classification>java.lang.Character</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<xsi-nil-represents-null>true</xsi-nil-represents-null>\n" +
                          "<null-representation-for-xml>XSI_NIL</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>cubical</attribute-name>\n" +
                       "<field name=\"cubical/text()\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}string</schema-type>\n" +
                       "</field>\n" +
                       "<attribute-classification>java.lang.Object</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<xsi-nil-represents-null>true</xsi-nil-represents-null>\n" +
                          "<null-representation-for-xml>XSI_NIL</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>sal</attribute-name>\n" +
                       "<field name=\"sal/text()\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}double</schema-type>\n" +
                       "</field>\n" +
                       "<attribute-classification>java.lang.Float</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<xsi-nil-represents-null>true</xsi-nil-represents-null>\n" +
                          "<null-representation-for-xml>XSI_NIL</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>commission</attribute-name>\n" +
                       "<field name=\"commission/text()\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}double</schema-type>\n" +
                       "</field>\n" +
                       "<attribute-classification>java.lang.Float</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<xsi-nil-represents-null>true</xsi-nil-represents-null>\n" +
                          "<null-representation-for-xml>XSI_NIL</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>sales</attribute-name>\n" +
                       "<field name=\"sales/text()\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}double</schema-type>\n" +
                       "</field>\n" +
                       "<attribute-classification>java.lang.Double</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<xsi-nil-represents-null>true</xsi-nil-represents-null>\n" +
                          "<null-representation-for-xml>XSI_NIL</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>binid</attribute-name>\n" +
                       "<field name=\"binid/text()\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}base64Binary</schema-type>\n" +
                       "</field>\n" +
                       "<converter xsi:type=\"serialized-object-converter\"/>\n" +
                       "<attribute-classification>[B</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<xsi-nil-represents-null>true</xsi-nil-represents-null>\n" +
                          "<null-representation-for-xml>XSI_NIL</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>b</attribute-name>\n" +
                       "<field name=\"b/text()\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}base64Binary</schema-type>\n" +
                       "</field>\n" +
                       "<converter xsi:type=\"serialized-object-converter\"/>\n" +
                       "<attribute-classification>[B</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<xsi-nil-represents-null>true</xsi-nil-represents-null>\n" +
                          "<null-representation-for-xml>XSI_NIL</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>c</attribute-name>\n" +
                       "<field name=\"c/text()\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}string</schema-type>\n" +
                       "</field>\n" +
                       "<attribute-classification>[Ljava.lang.Character;</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<xsi-nil-represents-null>true</xsi-nil-represents-null>\n" +
                          "<null-representation-for-xml>XSI_NIL</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>nc</attribute-name>\n" +
                       "<field name=\"nc/text()\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}string</schema-type>\n" +
                       "</field>\n" +
                       "<attribute-classification>java.lang.Object</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<xsi-nil-represents-null>true</xsi-nil-represents-null>\n" +
                          "<null-representation-for-xml>XSI_NIL</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>r</attribute-name>\n" +
                       "<field name=\"r/text()\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}base64Binary</schema-type>\n" +
                       "</field>\n" +
                       "<converter xsi:type=\"serialized-object-converter\"/>\n" +
                       "<attribute-classification>[B</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<xsi-nil-represents-null>true</xsi-nil-represents-null>\n" +
                          "<null-representation-for-xml>XSI_NIL</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                    "<attribute-mapping xsi:type=\"xml-direct-mapping\">\n" +
                       "<attribute-name>lr</attribute-name>\n" +
                       "<field name=\"lr/text()\" xsi:type=\"node\">\n" +
                          "<schema-type>{http://www.w3.org/2001/XMLSchema}base64Binary</schema-type>\n" +
                       "</field>\n" +
                       "<converter xsi:type=\"serialized-object-converter\"/>\n" +
                       "<attribute-classification>[B</attribute-classification>\n" +
                       "<null-policy xsi:type=\"null-policy\">\n" +
                          "<xsi-nil-represents-null>true</xsi-nil-represents-null>\n" +
                          "<null-representation-for-xml>XSI_NIL</null-representation-for-xml>\n" +
                       "</null-policy>\n" +
                    "</attribute-mapping>\n" +
                 "</attribute-mappings>\n" +
                 "<descriptor-type>aggregate</descriptor-type>\n" +
                 "<caching>\n" +
                    "<cache-size>-1</cache-size>\n" +
                 "</caching>\n" +
                 "<remote-caching>\n" +
                    "<cache-size>-1</cache-size>\n" +
                 "</remote-caching>\n" +
                 "<instantiation/>\n" +
                 "<copying xsi:type=\"instantiation-copy-policy\"/>\n" +
                 "<default-root-element>tabletypeType</default-root-element>\n" +
                 "<default-root-element-field name=\"tabletypeType\"/>\n" +
                 "<namespace-resolver>\n" +
                    "<default-namespace-uri>urn:tabletype</default-namespace-uri>\n" +
                 "</namespace-resolver>\n" +
                 "<schema xsi:type=\"schema-url-reference\">\n" +
                    "<resource></resource>\n" +
                    "<schema-context>/tabletypeType</schema-context>\n" +
                    "<node-type>complex-type</node-type>\n" +
                 "</schema>\n" +
              "</class-mapping-descriptor>\n" +
           "</class-mapping-descriptors>\n" +
           "<login xsi:type=\"xml-login\">\n" +
              "<platform-class>org.eclipse.persistence.oxm.platform.DOMPlatform</platform-class>\n" +
           "</login>\n" +
        "</object-persistence>";

}
