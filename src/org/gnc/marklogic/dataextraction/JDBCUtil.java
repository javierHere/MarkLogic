/*
 * Copyright (c)2016 General Networks Corporation 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 * 
 * The use of the Apache License does not indicate that this project is 
 * affiliated with the Apache Software Foundation. 
 */

package org.gnc.marklogic.dataextraction;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.*;
import java.util.*;

/**
*
* @author Javier Lizarraga, jlizarraga@gennet.com This class is used to transform pipe-delimited data into xml format.
*         Note that we are currently removing the root tag to support a camel requirement. 
*         In the future we can support Aggregate xml - THE CLASS FILE IS CURRENTLY NOT BEING USED.
*/

public class JDBCUtil {
	
	public static Document toDocument(ResultSet rs)
			   throws ParserConfigurationException, SQLException, Exception 
			{
			   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			   DocumentBuilder builder        = factory.newDocumentBuilder();
			   Document doc                   = builder.newDocument();
			   System.out.println("here");
			   Element results = doc.createElement("Results");
			   doc.appendChild(results);

			   ResultSetMetaData rsmd = rs.getMetaData();
			   int colCount           = rsmd.getColumnCount();
			   while (rs.next())
			   {
			      Element row = doc.createElement("Row");
			      results.appendChild(row);

			      for (int i = 1; i <= colCount; i++)
			      {
			         String columnName = rsmd.getColumnName(i);
			         System.out.println("here count" + columnName);
			         Object value      = rs.getObject(i);

			         Element node      = doc.createElement(columnName);
			         node.appendChild(doc.createTextNode(value.toString()));
			         row.appendChild(node);
			      }
			   }
			   
			    DOMSource domSource = new DOMSource(doc);
			    TransformerFactory tf = TransformerFactory.newInstance();
			    Transformer transformer = tf.newTransformer();
			    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			    transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			    StringWriter sw = new StringWriter();
			    StreamResult sr = new StreamResult(sw);
			    transformer.transform(domSource, sr);

			    System.out.println(sw.toString());
			   return doc;
			}
	
	public static String getXMLRecord(String tblColumnStr, String tblRec)
			   throws ParserConfigurationException, SQLException, Exception 
			{
			   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			   DocumentBuilder builder        = factory.newDocumentBuilder();
			   Document doc                   = builder.newDocument();
			   Element results = doc.createElement("Results");
			   tblColumnStr = ExtractHelper.removeSingleQuotes(tblColumnStr); //Need to remove single quotes on on demand field
			   doc.appendChild(results);
			   StringTokenizer columnTokenizer = new StringTokenizer(tblColumnStr, "|");

               String [] dbValues = tblRec.split("\\|");  // Spliting DB values - Need to use because of null values
               int tokenCount = dbValues.length;
    
               int myCnt = 0;
               int tokenizerCnt = columnTokenizer.countTokens();
               boolean nullAtEnd = true;
               if (tokenizerCnt == tokenCount)
            	   nullAtEnd = false;
			   Element row = doc.createElement("Row");
			   results.appendChild(row);
			   int countMe = 0;
               while(columnTokenizer.hasMoreElements())
               {
    			   Element node      = doc.createElement(columnTokenizer.nextToken());		   
    			   countMe++;
    			   if (nullAtEnd && tokenizerCnt == countMe)
    				   node.appendChild(doc.createTextNode(""));
    			   else
    				   node.appendChild(doc.createTextNode(dbValues[myCnt++]));
    		       row.appendChild(node);        
               }
                
	   
			    DOMConfiguration domConfig = doc.getDomConfig();
			    domConfig.setParameter("well-formed", Boolean.FALSE);
			    doc .setXmlStandalone(true);
			    DOMSource domSource = new DOMSource(doc);

			    TransformerFactory tf = TransformerFactory.newInstance();
			    Transformer transformer = tf.newTransformer();
			    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			    transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			    StringWriter sw = new StringWriter();
			    StreamResult sr = new StreamResult(sw);
			    transformer.transform(domSource, sr);
				    
			   return peel(sw.toString());

	}
	
    static String peel(String xmlString) {
    StringWriter writer = new StringWriter();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try {
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(
                xmlString)));
        NodeList nodes = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            Document d = builder.newDocument();
            Node newNode = d.importNode(n, true);
            d.insertBefore(newNode, null);
            writeOutDOM(d, writer);
        }
    } catch (ParserConfigurationException e) {
        e.printStackTrace();
    } catch (SAXException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (TransformerFactoryConfigurationError e) {
        e.printStackTrace();
    } catch (TransformerException e) {
        e.printStackTrace();
    }
    return writer.toString();
}
	
	static void writeOutDOM(Document doc, Writer writer) 
		     throws TransformerFactoryConfigurationError, TransformerException {
		    Result result = new StreamResult(writer);
		    DOMSource domSource = new DOMSource(doc);
		    Transformer transformer = TransformerFactory.newInstance()
		            .newTransformer();
		    transformer.setOutputProperty("omit-xml-declaration", "yes");
		    transformer.transform(domSource, result);
		}

}

