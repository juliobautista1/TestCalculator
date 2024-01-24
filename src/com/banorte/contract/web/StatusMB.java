/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  
package com.banorte.contract.web;

import com.banorte.contract.business.StatusContractRemote;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
 
/**    
 *
 * @author MRIOS
 */

public class StatusMB {
    private static final Logger log = Logger.getLogger(StatusMB.class.getName());
    //@EJB
    private StatusContractRemote statusBean; 
    private List listaEstatus;

    /** Creates a new instance of StatusMB */
    public StatusMB() {
        if(statusBean==null){
            statusBean = (StatusContractRemote)EjbInstanceManager.getEJB(StatusContractRemote.class);
        }
    }

    public List getListaEstatus(){
        if(listaEstatus==null && statusBean!=null){ 
            listaEstatus = statusBean.findAll();
           log.info("Total de Estatus:"+listaEstatus.size());
        }
        if(listaEstatus==null){ 
            log.info("Lista es NULL");
            return new java.util.ArrayList();
        }else{
           log.info("Total de Estatus:"+listaEstatus.size());            
        }
     
        return listaEstatus;
    }

}
