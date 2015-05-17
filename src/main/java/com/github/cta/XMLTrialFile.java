package com.github.cta;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;

/**
 * @author Krzysztof Langner on 17.05.15.
 */
public class XMLTrialFile {

    private static final String CRITERIA_PATH = "/clinical_study/eligibility/criteria/textblock";
    private String eligibilityCriteria;


    public XMLTrialFile(String file){
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(file);
            Node node = (Node) xpath.evaluate(CRITERIA_PATH, document, XPathConstants.NODE);
            eligibilityCriteria = (node != null) ? node.getTextContent() : "";
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getEligibilityCriteria() {
        return eligibilityCriteria;
    }
}
