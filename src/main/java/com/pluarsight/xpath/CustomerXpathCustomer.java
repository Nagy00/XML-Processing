package com.pluarsight.xpath;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CustomerXpathCustomer {
	
	private static XPathFactory XPATH_FACTORY = XPathFactory.newInstance();
	private static XPath XPATH = XPATH_FACTORY.newXPath();

	public static void main(String[] args)
			throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {

		// 1. find the document.
		try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("./customers.xml")) {

			// 2. Document Builder Factory
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			// 3. Document Builder
			DocumentBuilder builder = factory.newDocumentBuilder();

			// 4. get the document throw parsing
			Document document = builder.parse(inputStream);
			
			// 6. get the expression
			javax.xml.xpath.XPathExpression expression = XPATH.compile("/customers/customer");

			NodeList customersNodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
			/*
			 * expression.evaluate(input node <doc , node , String> , output type <node,
			 * nodeList, String , ..>))
			 */
			System.out.println(customersNodeList.getLength());
			List<Customer> customerList = new ArrayList<>();

			for (int i = 0; i < customersNodeList.getLength(); i++) {

				Node currentNode = customersNodeList.item(i);
				customerList.add(buildCustomerUsinXpath(currentNode));

			}

		} catch (XPathExpressionException xpath) {
			throw new XPathExpressionException("Xpath Expression Exception");

		} catch (ParserConfigurationException parser) {
			throw new ParserConfigurationException("Parse Configuration Exception");

		} catch (IOException io) {
			throw new IOException("IO Exception");

		} catch (SAXException sax) {
			throw new SAXException("SAX Exception");
		}
	}
	
	private static Customer buildCustomerUsinXpath(Node customerNode) throws XPathExpressionException {
		Customer customer = new Customer();
		javax.xml.xpath.XPathExpression idExpression = XPATH.compile("@id"); // @ added cause it's an attr
		javax.xml.xpath.XPathExpression firstNameExpr = XPATH.compile("firstName");
		javax.xml.xpath.XPathExpression lastNameExpr = XPATH.compile("lastName");
	    XPathExpression emailExpression = XPATH.compile("email");
	    XPathExpression addressesExpressions = XPATH.compile("addresses");
		

		// set the values
		 customer.setId((Long)idExpression.evaluate(customerNode, XPathConstants.STRING));
		 customer.setFirstName(firstNameExpr.evaluate(customerNode, XPathConstants.STRING).toString());
		 customer.setLastName(lastNameExpr.evaluate(customerNode, XPathConstants.STRING).toString());
		 customer.setEmailAddress(emailExpression.evaluate(customerNode, XPathConstants.STRING).toString());
		 
		 // get the addresses
		 Node addressNode = (Node) addressesExpressions.evaluate(customerNode, XPathConstants.NODE);
		 if(addressNode != null) {
			 NodeList addressChildNodes = addressNode.getChildNodes();
			 for (int i  = 0; i < addressChildNodes.getLength(); i++) {
				 Node currentAddressNode = addressChildNodes.item(i);
				 if(currentAddressNode instanceof Element && "address".equals(currentAddressNode.getLocalName())) {
					 customer.getAddress().add(buildAddressFromNode(currentAddressNode));
				 }
			 }			 
		 }
		 
		return customer;
	}

	private static Address buildAddressFromNode(Node currentAddressNode) throws XPathExpressionException {
		Address address =  new Address();
		
		XPathExpression typeExpression = XPATH.compile("type");
		XPathExpression streetExpression = XPATH.compile("street");
		XPathExpression stateExpression = XPATH.compile("state");
		XPathExpression cityExpression = XPATH.compile("city");
		
		// set the values
		address.setAddressType(typeExpression.evaluate(currentAddressNode,XPathConstants.STRING).toString());
		address.setState(streetExpression.evaluate(currentAddressNode,XPathConstants.STRING).toString());
		address.setCity(cityExpression.evaluate(currentAddressNode,XPathConstants.STRING).toString());
		address.setState(stateExpression.evaluate(currentAddressNode,XPathConstants.STRING).toString());
		return address;
	}
}