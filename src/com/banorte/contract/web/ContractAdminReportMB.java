/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banorte.contract.web;


import com.banorte.contract.business.CitiesRemote;
import com.banorte.contract.business.ExecutiveRemote;
import com.banorte.contract.model.Cities;
import java.util.Collection;
import java.util.List;



/**
 *
 * @author cgomez
 */
public class ContractAdminReportMB {
    
     private String searchoption;
     private String reference;
     protected ExecutiveRemote executiveBean;
     protected CitiesRemote citiesBean;
     private static final String SUCCESS = "SUCCESS";
     private static final String SEARCH = "SEARCH";   
     private Collection<Cities> citiesList;
    
    
     public ContractAdminReportMB() {
        
                
        if (executiveBean == null){
            executiveBean = (ExecutiveRemote) EjbInstanceManager.getEJB(ExecutiveRemote.class);
        }
        
        if (citiesBean == null) {
            citiesBean = (CitiesRemote) EjbInstanceManager.getEJB(CitiesRemote.class);
        } 
        
        this.searchoption = "Poblacion";
    }
    
     
   public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference.toUpperCase();
    }  
   
     public void setSearchoption(String searchoption) {
        this.searchoption = searchoption.trim();
    }
    
    public String getSearchoption() {
        return this.searchoption;
    }

    public Collection<Cities> getCitiesList() {
         if(citiesList==null)
            this.getReportResult();
        
        return citiesList;
    }

    public void setCitiesList(Collection<Cities> citiesList) {
        this.citiesList = citiesList;
    }
    
    
    public String getReportResult() {
       
        if ( this.reference != null && !this.reference.equals("") ) {

                if ( this.searchoption.equals("Poblacion") ) {
                    try{
                        List<Cities> cities_=citiesBean.findByName(this.reference);                         
                        this.setCitiesList(cities_);
                       
                        
                    } catch ( NumberFormatException e ) {}
                }
                 else{
                    try{
                        List<Cities> cities_=citiesBean.findByState(this.reference);
                        this.setCitiesList(cities_);
                    }catch ( NumberFormatException e ) { }
                 }     
                  
                    
        
        }

        return SEARCH;
    }
    
    
    
    

}
