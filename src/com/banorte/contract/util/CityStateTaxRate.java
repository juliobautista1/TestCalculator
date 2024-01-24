/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banorte.contract.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Darvy Arch
 */
public class CityStateTaxRate {
    
    private static Logger logger = Logger.getLogger(CityStateTaxRate.class.getName());
    private static CityStateTaxRate instance = null;
    private static Document doc = null;
    private BufferedInputStream bis = null;
    
    private CityStateTaxRate() {        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder parser = factory.newDocumentBuilder();
            bis = new BufferedInputStream(this.getClass().getResourceAsStream("/com/banorte/contract/resources/taxexception.xml"));
            doc = parser.parse(bis);
        } catch ( ParserConfigurationException e ){
            logger.log(Level.SEVERE, "Error al  momento del parseo", e);
        } catch ( SAXException e ) {
            logger.log(Level.SEVERE, "Error al  momento de sax", e);
        } catch ( IOException e ) {
            logger.log(Level.SEVERE, "Error al  momento de la lectura del archivo", e);
        }finally{
        	if (bis != null) {
        		safeClose(bis);
        	}
        }
    }
    
    public static synchronized CityStateTaxRate getInstance() {
        if ( instance == null)
            instance = new CityStateTaxRate();
        return instance;
    }
    
    public static boolean findInList(String city, String state) {
        
        NodeList states = doc.getElementsByTagName("State");
        
        for ( int i  = 0; i < states.getLength(); i++) {
            if ( state.equalsIgnoreCase( states.item(i).getAttributes().getNamedItem("name").getNodeValue().toUpperCase() ) ) {

                NodeList citys = states.item(i).getChildNodes();
                
                for ( int j = 1; j < citys.getLength(); j++ ) {
                    if ( city.equalsIgnoreCase(citys.item(j).getAttributes().getNamedItem("name").getNodeValue().toUpperCase() ) ) {
                        return true;                    
                    }
                    j++;
                }
            }
        }
        
        return false;
    }
    
    public static void safeClose(BufferedInputStream bis) {
    	if (bis != null) {
    		try {
    			bis.close();
    		} catch (IOException e) {
    			logger.log(Level.SEVERE, "Error al  momento de la lectura del archivo", e);
    		}
    	}
    }
}
