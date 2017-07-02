/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dmachacek.mq.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author dmachace
 */
public class XMLMessageHandler extends MessageHandler {
    protected static final Logger logger = LoggerFactory.getLogger(XMLMessageHandler.class);

    Document doc;
    public List<String> getXmlElement(String docContent, String elementName) throws ParserConfigurationException, SAXException, IOException {

        List<String> elementList = new LinkedList<>();
        Document doc = getDocument(docContent);
        Element docElement = doc.getDocumentElement();

        String elementRoot = "element";
        if (docContent.contains("sip_message")) {
            elementRoot = "AppMsg";
        }

        NodeList nodeList = docElement.getElementsByTagName(elementName);
        logger.info("Found {} element(s) total. Root element: {}", nodeList.getLength(), elementRoot);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elementTemp = (Element) nNode;
                logger.info("{}. element's content: {}. Parent: {}", i+1, elementTemp.getTextContent(),elementTemp.getParentNode().getNodeName());
                elementList.add(elementTemp.getTextContent());

            }
        }
        return elementList;
    }


    public Document getDocument (String docContent) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(docContent.getBytes(StandardCharsets.UTF_8));
        doc = dBuilder.parse(stream);
        doc.getDocumentElement().normalize();

        return doc;
    }
}



