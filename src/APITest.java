import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class APITest {
    public static void main(String[] args) throws Exception {
               
        getDataFromAPI("BusRouteInfoInqireService", "getCtyCodeList", "%2FINPAsm7NTY0H7pQwDLNdW5dFd%2FhZxqvngMPEUKPW2de5TVRU2fhgI6x6CsUpkhjJYmH5tG4vYCahsntFWxJ%2Bg%3D%3D",
        		"numOfRows=999", "pageNo=1");
        
    }
    
    public static void getDataFromAPI(String serviceName, String operationName, String serviceKey, String... parameterDic) throws Exception {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.tago.go.kr/openapi/service/"); /*URL*/
        urlBuilder.append(serviceName);
        urlBuilder.append("/");
        urlBuilder.append(operationName);
        urlBuilder.append("?ServiceKey=");
        urlBuilder.append(serviceKey);
        for(String parameter : parameterDic) {
            urlBuilder.append("&");
        	urlBuilder.append(parameter);
        }
        
        System.out.println(urlBuilder);
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
        	Document doc = parseXML(conn.getInputStream());
	        XPath xpath = XPathFactory.newInstance().newXPath();
	        NodeList nodeList = (NodeList)xpath.evaluate("/response/body/items/item", doc, XPathConstants.NODESET);
	        for(int i = 0; i < nodeList.getLength(); i++) {
	        	NodeList node = nodeList.item(i).getChildNodes();
	        	for(int j = 0; j < node.getLength(); j++) {
	        		System.out.println(node.item(j).getTextContent());
	        	}
	        }
        }
        
        /*BufferedReader rd;
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = rd.readLine()) != null) {
            	System.out.println(line);
            }
            rd.close();
        }*/
        conn.disconnect();
    }
    
    public static Document parseXML(InputStream stream) {
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	DocumentBuilder db = null;
    	Document doc = null;
		try {
			db = dbf.newDocumentBuilder();
	    	doc = db.parse(stream);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return doc;
    }
}