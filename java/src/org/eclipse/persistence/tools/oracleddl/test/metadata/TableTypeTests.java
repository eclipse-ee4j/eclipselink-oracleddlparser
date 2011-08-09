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
 * Tests TableType where the database table contains all relevant scalar
 * types except UROWID, INTERVALDAYTOSECOND, and INTERVALYEARTOMINTH.
 *
 */
public class TableTypeTests extends TestHelper {
    
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
    public void findByPrimaryKeyTest() {
        Invocation invocation = new Invocation("findByPrimaryKey_tabletypeType");
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
        Invocation invocation = new Invocation("findAll_tabletypeType");
        Operation op = xrService.getOperation(invocation.getName());
        Object result = op.invoke(xrService, invocation);
        assertNotNull("result is null", result);
        Document doc = xmlPlatform.createDocument();
        XMLMarshaller marshaller = xrService.getXMLContext().createMarshaller();
        Element ec = doc.createElement("tabletype-collection");
        doc.appendChild(ec);
        for (Object r : (Vector)result) {
            marshaller.marshal(r, ec);
        }
        Document controlDoc = xmlParser.parse(new StringReader(ALL_PEOPLE_XML));
        assertTrue("control document not same as instance document", comparer.isNodeEqual(controlDoc, doc));
    }

    protected static final String ONE_PERSON_XML =
        "<?xml version = '1.0' encoding = 'UTF-8'?>" +
        "<tabletypeType xmlns=\"urn:tabletype\">" +
          "<id>1</id>" +
          "<name>mike</name>" +
          "<deptno>99</deptno>" +
          "<deptname>sales</deptname>" +
          "<section>a</section>" +
          "<cubical>1442</cubical>" +
          "<sal>100000.8</sal>" +
          "<commission>450.8</commission>" +
          "<sales>10000.8</sales>" +
          "<binid>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAACEBA=</binid>" +
          "<b>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAAPAQEBAQEBAQEBAQEBAQEB</b>" +
          "<c>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</c>" +
          "<nc>nclobaaaaaaaaaaaaaaaaaaaaaaaaa</nc>" +
          "<r>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAADAQEB</r>" +
          "<lr>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAAJAQEBAQEBAQEB</lr>" +
        "</tabletypeType>";
    
    protected static final String ALL_PEOPLE_XML =
        "<?xml version = '1.0' encoding = 'UTF-8'?>" +
        "<tabletype-collection>" +
          "<tabletypeType xmlns=\"urn:tabletype\">" +
            "<id>1</id>" +
            "<name>mike</name>" +
            "<deptno>99</deptno>" +
            "<deptname>sales</deptname>" +
            "<section>a</section>" +
            "<cubical>1442</cubical>" +
            "<sal>100000.8</sal>" +
            "<commission>450.8</commission>" +
            "<sales>10000.8</sales>" +
            "<binid>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAACEBA=</binid>" +
            "<b>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAAPAQEBAQEBAQEBAQEBAQEB</b>" +
            "<c>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</c>" +
            "<nc>nclobaaaaaaaaaaaaaaaaaaaaaaaaa</nc>" +
            "<r>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAADAQEB</r>" +
            "<lr>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAAJAQEBAQEBAQEB</lr>" +
          "</tabletypeType>" +
          "<tabletypeType xmlns=\"urn:tabletype\">" +
            "<id>2</id>" +
            "<name>merrick</name>" +
            "<deptno>98</deptno>" +
            "<deptname>delivery</deptname>" +
            "<section>f</section>" +
            "<cubical>1222</cubical>" +
            "<sal>20000.0</sal>" +
            "<commission>0.0</commission>" +
            "<sales>0.0</sales>" +
            "<binid>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAACAQE=</binid>" +
            "<b>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAAPAgICAgICAgICAgICAgIC</b>" +
            "<c>bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb</c>" +
            "<nc>nclobbbbbbbbbbbbbbbbbbbbbbbbbb</nc>" +
            "<r>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAADAgIC</r>" +
            "<lr>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAAJAgICAgICAgIC</lr>" +
          "</tabletypeType>" +
          "<tabletypeType xmlns=\"urn:tabletype\">" +
            "<id>3</id>" +
            "<name>rick</name>" +
            "<deptno>99</deptno>" +
            "<deptname>sales</deptname>" +
            "<section>b</section>" +
            "<cubical>1414</cubical>" +
            "<sal>98000.2</sal>" +
            "<commission>150.2</commission>" +
            "<sales>2000.2</sales>" +
            "<binid>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAACERA=</binid>" +
            "<b>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAAPAwMDAwMDAwMDAwMDAwMD</b>" +
            "<c>cccccccccccccccccccccccccccccc</c>" +
            "<nc>nclobccccccccccccccccccccccccc</nc>" +
            "<r>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAADAwMD</r>" +
            "<lr>rO0ABXVyAAJbQqzzF/gGCFTgAgAAeHAAAAAJAwMDAwMDAwMD</lr>" +
          "</tabletypeType>" +
        "</tabletype-collection>";
}
