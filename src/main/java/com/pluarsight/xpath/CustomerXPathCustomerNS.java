package com.pluarsight.xpath;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.NamespaceContext;
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

public class CustomerXPathCustomerNS {

	private static XPathFactory XPATH_FACTORY = XPathFactory.newInstance();
	private static XPath XPATH = XPATH_FACTORY.newXPath();

	static {
		NamespaceContext context = new NamespaceContext() {

			@Override
			public Iterator getPrefixes(String namespaceURI) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getPrefix(String namespaceURI) {
				String prefix = "";
				if("www.its.com".equals(namespaceURI)) {
					prefix = "tbc";
				} else {
					prefix = "tba";
				}
				return prefix;
			}

			@Override
			public String getNamespaceURI(String prefix) {
				String nameSpace = "";
				if ("tbc".equalsIgnoreCase(prefix)) {
					nameSpace = "www.its.com";
				} else {
					nameSpace = "www.pluarsight.com";
				}
				return nameSpace;
			}
		};
		
		XPATH.setNamespaceContext(context);
	}

	public static void main(String[] args)
			throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {

		// 1. find the document.
		try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("./customer-namespace.xml")) {

			// 2. Document Builder Factory
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			// 3. Document Builder
			DocumentBuilder builder = factory.newDocumentBuilder();

			// 4. get the document throw parsing
			Document document = builder.parse(inputStream);

			// 6. get the expression
			javax.xml.xpath.XPathExpression expression = XPATH.compile("/tbc:customers/tbc:customer");

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
		javax.xml.xpath.XPathExpression firstNameExpr = XPATH.compile("tbc:firstName");
		javax.xml.xpath.XPathExpression lastNameExpr = XPATH.compile("tbc:lastName");
		XPathExpression emailExpression = XPATH.compile("tbc:email");
		XPathExpression addressesExpressions = XPATH.compile("tba:addresses");

		// set the values
		customer.setId((Long) idExpression.evaluate(customerNode, XPathConstants.STRING));
		customer.setFirstName(firstNameExpr.evaluate(customerNode, XPathConstants.STRING).toString());
		customer.setLastName(lastNameExpr.evaluate(customerNode, XPathConstants.STRING).toString());
		customer.setEmailAddress(emailExpression.evaluate(customerNode, XPathConstants.STRING).toString());

		// get the addresses
		Node addressNode = (Node) addressesExpressions.evaluate(customerNode, XPathConstants.NODE);
		if (addressNode != null) {
			NodeList addressChildNodes = addressNode.getChildNodes();
			for (int i = 0; i < addressChildNodes.getLength(); i++) {
				Node currentAddressNode = addressChildNodes.item(i);
				if (currentAddressNode instanceof Element && "address".equals(currentAddressNode.getLocalName())) {
					customer.getAddress().add(buildAddressFromNode(currentAddressNode));
				}
			}
		}

		return customer;
	}

	private static Address buildAddressFromNode(Node currentAddressNode) throws XPathExpressionException {
		Address address = new Address();

		XPathExpression typeExpression = XPATH.compile("tba:type");
		XPathExpression streetExpression = XPATH.compile("tba:street");
		XPathExpression stateExpression = XPATH.compile("tba:state");
		XPathExpression cityExpression = XPATH.compile("tba:city");

		// set the values
		address.setAddressType(typeExpression.evaluate(currentAddressNode, XPathConstants.STRING).toString());
		address.setState(streetExpression.evaluate(currentAddressNode, XPathConstants.STRING).toString());
		address.setCity(cityExpression.evaluate(currentAddressNode, XPathConstants.STRING).toString());
		address.setState(stateExpression.evaluate(currentAddressNode, XPathConstants.STRING).toString());
		return address;
	}

}
