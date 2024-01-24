/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banorte.contract.web;
import com.banorte.contract.business.CitiesRemote;
import com.banorte.contract.business.StatesRemote;
import com.banorte.contract.model.Cities;
import com.banorte.contract.model.States;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author cgomez
 */
public class CitiesMB {
    private static final String SUCCESS = "SUCCESS";
    private Cities city;
    private CitiesRemote citiesBean; 
    private StatesRemote statesBean;
    private SelectItem[] statesArray;
    
    
    public CitiesMB() {
        city = new Cities();
        if (citiesBean == null) {
            citiesBean = (CitiesRemote) EjbInstanceManager.getEJB(CitiesRemote.class);
        }         
        if ( statesBean == null) {
            statesBean = (StatesRemote) EjbInstanceManager.getEJB(StatesRemote.class);
        }
    }
    
    
     
    public String getCities_state() {
        return city.getId_state();
    }

    public void setCities_state(String cities_state) {
        city.setId_state(cities_state);
    }
    
    public String getCities_name() {
        return city.getName();
    }

    public void setCities_name(String cities_name) {
        city.setName(cities_name);
    }



     public String saveCity() {
            
           if ( city.getId()== null ) {
                citiesBean.save(city);
                //city = citiesBean.findById(getCities_id());
            } else {
                   Cities cityFind = citiesBean.findById(city.getId());
                  if (cityFind!=null){
                    city.setId(cityFind.getId());
                    citiesBean.update(city);
                    }
            }  
       
        return SUCCESS;
    }
     
     
      public void setEditForm() {        
        FacesContext fCtx = FacesContext.getCurrentInstance();
        Map<String, String> params = fCtx.getExternalContext().getRequestParameterMap();    
        Integer citiesId = Integer.parseInt(params.get("cities_id"));


        System.err.println("Entro a Edicion, cities_id: " + citiesId);

        Cities city_ = citiesBean.findById(citiesId);
        System.err.println("Entro a Edicion, city_.getName(): " + city_.getName());
        System.err.println("Entro a Edicion, city_.getId_state(): " + city_.getId_state());
        
        
        this.setCities_name(city_.getName());
        this.setCities_state(city_.getId_state());
        
        System.err.println("Entro a Edicion, this.setCities_name " + this.getCities_name());
        System.err.println("Entro a Edicion,  this.setCities_state " + this.getCities_state());
        
    }
    
     public String getEditForm() {       
        System.err.println("Entro a getEditForm");
        setEditForm();
        return "";
    }
     
   public void setResetForm() {
       System.err.println("Entro a setResetForm");
        city = new Cities();
    }

    public String getResetForm() {
        System.err.println("Entro a getResetForm");
        setResetForm();
        return "";
    }  
     

    public SelectItem[] getStatesArray() {
         if(this.statesArray==null){
            //Lista Estados
            List <States> states=statesBean.findAll();
                if(states!=null){
                   statesArray = new SelectItem[states.size()];
                    int i=0;
                    for (States sta:states){                        
                        //System.err.println("Estado: " +  sta.getName());
                        statesArray[i]=new SelectItem(sta.getName(),sta.getName());
                        i++;
                    }
                }
        }        
        return statesArray;
    }

    public void setStatesArray(SelectItem[] statesArray) {
        this.statesArray = statesArray;
    }       
    
}
