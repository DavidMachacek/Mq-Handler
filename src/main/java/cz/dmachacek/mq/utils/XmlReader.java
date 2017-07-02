/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dmachacek.mq.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import cz.dmachacek.mq.exception.CommonException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author dmachace
 */
public class XmlReader {
    Document  doc;

    public NodeList getNodeList(String content) throws CommonException, IOException, SAXException, ParserConfigurationException {
        doc=getDocument(content);
        Element docElement = doc.getDocumentElement();
        String element = "element"; //dictionary element tag
        if (content.contains("sip_message")) {
            element="AppMsg";
        }
        NodeList nodeList = docElement.getElementsByTagName(element);
        return nodeList;
    }

    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }
        return textVal;
    }

    public Document getDocument (String docContent) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(docContent.getBytes(StandardCharsets.UTF_8));
        doc = dBuilder.parse(stream);
        doc.getDocumentElement().normalize();

        return doc;
    }

    public String[][] getArrayFromXML(String content) throws IOException, ParserConfigurationException, SAXException {

        NodeList nodeList = getNodeList(content);
        String[][] array = new String[nodeList.getLength()][3];

        if (nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {

                Element element = (Element) nodeList.item(i);

                String name = getTextValue(element, "Name");
                String posFrom = getTextValue(element, "PosFrom");
                String posTo = getTextValue(element, "PosTo");

                array[i][0] = name;
                array[i][1] = posFrom;
                array[i][2] = posTo;
            }
        }
        return array;
    }
}
