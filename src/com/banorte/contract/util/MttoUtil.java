/**
 * 
 */
package com.banorte.contract.util;

import java.util.Collection;

import javax.faces.model.SelectItem;

import com.banorte.contract.model.Attribute;
import com.banorte.contract.model.AttributeOption;
import com.banorte.contract.web.AttributeMB;

/**
 * @author omar
 *
 */
public class MttoUtil {
	
	private AttributeMB attributeMB;
	
	
	
	public MttoUtil() {
		super();
		attributeMB = new AttributeMB();
	}



	/**
	 * Develve un Array de SelecItems cuando el valor de los Atributos en su Valor y Descripcion son Cadenas 
	 * @param attributeFieldName
	 * @return
	 */
	public SelectItem[] getattributeOptionArray(String attributeFieldName){
    	
		SelectItem[] itemArray 	= null;
		int iterator 			= 0;
	    Attribute att 			= this.getAttributeMB().getByFieldname(attributeFieldName);
	    
	    if (att != null) {
	        Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
	        itemArray = new SelectItem[attOptCollection.size()];
	
	        
	        for (AttributeOption attOpt : attOptCollection) {
	        	itemArray[iterator] = new SelectItem(attOpt.getValue(), attOpt.getDescription());
	        	++iterator;
	        }            
  
	    }
		
		return itemArray;
    }



	/**
	 * @return the attributeMB
	 */
	public AttributeMB getAttributeMB() {
		return attributeMB;
	}



	/**
	 * @param attributeMB the attributeMB to set
	 */
	public void setAttributeMB(AttributeMB attributeMB) {
		this.attributeMB = attributeMB;
	}
	

}
