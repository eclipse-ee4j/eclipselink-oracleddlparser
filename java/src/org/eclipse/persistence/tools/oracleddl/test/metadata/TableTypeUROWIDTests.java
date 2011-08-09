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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.Vector;

import javax.wsdl.WSDLException;

import org.eclipse.persistence.internal.xr.Invocation;
import org.eclipse.persistence.internal.xr.Operation;
import org.eclipse.persistence.oxm.XMLMarshaller;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Tests TableType where the database table is indexed and contains
 * a UROWID type.
 * 
 */
public class TableTypeUROWIDTests extends TestHelper {
    
    @BeforeClass
    public static void setUp() throws WSDLException {
        DBWS_BUILDER_XML_USERNAME =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
          "<dbws-builder xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" +
            "<properties>" +
                "<property name=\"projectName\">tabletypeurowid</property>" +
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
              "tableNamePattern=\"INDEXEDTABLETYPE\" " +
            "/>" +
          "</dbws-builder>";

        TestHelper.setUp(".");
        
        //System.out.println(DBWS_WSDL_STREAM.toString());
        //System.out.println(DBWS_OX_STREAM.toString());
        //System.out.println(DBWS_OR_STREAM.toString());
        //System.out.println(DBWS_SCHEMA_STREAM.toString());

    }
    
    @Test
    public void findByPrimaryKeyTest() {
        Invocation invocation = new Invocation("findByPrimaryKey_indexedtabletypeType");
        invocation.setParameter("id", 1);
        Operation op = xrService.getOperation(invocation.getName());
        Object result = op.invoke(xrService, invocation);
        assertNotNull("result is null", result);
        Document doc = xmlPlatform.createDocument();
        XMLMarshaller marshaller = xrService.getXMLContext().createMarshaller();
        marshaller.marshal(result, doc);
        //marshaller.marshal(result, System.out);
        Document controlDoc = xmlParser.parse(new StringReader(ONE_PERSON_XML));
        assertTrue("control document not same as instance document", comparer.isNodeEqual(controlDoc, doc));
    }
    
    @SuppressWarnings("rawtypes")
    @Test
    public void findAllTest() {
        Invocation invocation = new Invocation("findAll_indexedtabletypeType");
        Operation op = xrService.getOperation(invocation.getName());
        Object result = op.invoke(xrService, invocation);
        assertNotNull("result is null", result);
        Document doc = xmlPlatform.createDocument();
        XMLMarshaller marshaller = xrService.getXMLContext().createMarshaller();
        Element ec = doc.createElement("tabletypeurowid-collection");
        doc.appendChild(ec);
        for (Object r : (Vector)result) {
            marshaller.marshal(r, ec);
        }
        Document controlDoc = xmlParser.parse(new StringReader(ALL_PEOPLE_XML));
        assertTrue("control document not same as instance document", comparer.isNodeEqual(controlDoc, doc));
    }

    public static final String ONE_PERSON_XML =
        "<?xml version = '1.0' encoding = 'UTF-8'?>" +
        "<indexedtabletypeType xmlns=\"urn:tabletypeurowid\">" +
          "<id>1</id>" +
          "<name>mike</name>" +
          "<rid>*EcLiPseLiNk1</rid>" +
        "</indexedtabletypeType>";
    
    public static final String ALL_PEOPLE_XML =
        "<?xml version = '1.0' encoding = 'UTF-8'?>" +
        "<tabletypeurowid-collection>" +
          "<indexedtabletypeType xmlns=\"urn:tabletypeurowid\">" +
            "<id>1</id>" +
            "<name>mike</name>" +
            "<rid>*EcLiPseLiNk1</rid>" +
          "</indexedtabletypeType>" +
          "<indexedtabletypeType xmlns=\"urn:tabletypeurowid\">" +
            "<id>2</id>" +
            "<name>merrick</name>" +
            "<rid>*EcLiPseLiNk2</rid>" +
          "</indexedtabletypeType>" +
          "<indexedtabletypeType xmlns=\"urn:tabletypeurowid\">" +
            "<id>3</id>" +
            "<name>rick</name>" +
            "<rid>*EcLiPseLiNk3</rid>" +
          "</indexedtabletypeType>" +
        "</tabletypeurowid-collection>";
}
