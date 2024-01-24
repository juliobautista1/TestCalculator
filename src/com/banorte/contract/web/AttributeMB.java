/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banorte.contract.web;

import com.banorte.contract.business.AttributeRemote;
import com.banorte.contract.model.Attribute;

import java.util.LinkedHashMap;
import java.util.List;  
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.ejb.EJB;

/** 
 *
 * @author MRIOS
 */

public class AttributeMB {
    private static Logger log = Logger.getLogger(AttributeMB.class.getName());
    //@EJB
    private AttributeRemote attribute;
    private List<Attribute> listaAtributos;
    private Map<String,Attribute> map;

    /** Creates a new instance of Atributo */
    public AttributeMB() {
        if(attribute==null){
            attribute = (AttributeRemote)EjbInstanceManager.getEJB(AttributeRemote.class);
        }
        if(map==null){
            //map = new TreeMap();
        	map = new LinkedHashMap();
        }
        for(Attribute a:getListaAtributos()){
            map.put(a.getFieldname(), a);
        }
    }

    public Attribute getByFieldname(String fieldname){
        return map.get(fieldname);
    }

    public List<Attribute> getListaAtributos(){
        if(listaAtributos==null){
            listaAtributos = attribute.findAll();
        }
        
        return listaAtributos;
    }
    
}
