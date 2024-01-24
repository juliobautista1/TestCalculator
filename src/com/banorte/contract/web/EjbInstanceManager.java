/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banorte.contract.web;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**  
 *
 * @author MRIOST
 */
public class EjbInstanceManager {

    private static Logger log = Logger.getLogger(EjbInstanceManager.class.getName());
    private static Hashtable<Class, String> ejbMap;


    static {
        ejbMap = new Hashtable();
        ejbMap.put(com.banorte.contract.business.StatusContractRemote.class, "StatusContract#com.banorte.contract.business.StatusContractRemote");
        ejbMap.put(com.banorte.contract.business.AttributeRemote.class, "Attribute#com.banorte.contract.business.AttributeRemote");
        ejbMap.put(com.banorte.contract.business.ClientRemote.class, "Client#com.banorte.contract.business.ClientRemote");
        ejbMap.put(com.banorte.contract.business.ProductRemote.class, "Product#com.banorte.contract.business.ProductRemote");
        ejbMap.put(com.banorte.contract.business.ContractAttributeRemote.class, "ContractAttribute#com.banorte.contract.business.ContractAttributeRemote");
        ejbMap.put(com.banorte.contract.business.ContractRemote.class, "Contract#com.banorte.contract.business.ContractRemote");
        ejbMap.put(com.banorte.contract.business.ReferenceRemote.class, "Reference#com.banorte.contract.business.ReferenceRemote");
        ejbMap.put(com.banorte.contract.business.ContactTypeRemote.class, "ContactType#com.banorte.contract.business.ContactTypeRemote");
        ejbMap.put(com.banorte.contract.business.TemplateRemote.class, "Template#com.banorte.contract.business.TemplateRemote");
        ejbMap.put(com.banorte.contract.business.PayrateRemote.class, "Payrate#com.banorte.contract.business.PayrateRemote");
        ejbMap.put(com.banorte.contract.business.AffiliationRemote.class, "Affiliation#com.banorte.contract.business.AffiliationRemote");        
        ejbMap.put(com.banorte.contract.business.StatesRemote.class, "States#com.banorte.contract.business.StatesRemote");
        ejbMap.put(com.banorte.contract.business.CategoriesRemote.class, "Categories#com.banorte.contract.business.CategoriesRemote");
        ejbMap.put(com.banorte.contract.business.CitiesRemote.class, "Cities#com.banorte.contract.business.CitiesRemote");
        ejbMap.put(com.banorte.contract.business.ExecutiveRemote.class, "Executive#com.banorte.contract.business.ExecutiveRemote");
        ejbMap.put(com.banorte.contract.business.ContractStatusHistoryRemote.class, "ContractStatusHistory#com.banorte.contract.business.ContractStatusHistoryRemote");
        //Joseles:Se agregó Employee
        ejbMap.put(com.banorte.contract.business.EmployeeRemote.class, "Employee#com.banorte.contract.business.EmployeeRemote");
        //Joseles:Se agregó Maintance
        ejbMap.put(com.banorte.contract.business.MaintanceRemote.class, "Maintance#com.banorte.contract.business.MaintanceRemote");
        //Joseles:Se agregó StatusDirecta
        ejbMap.put(com.banorte.contract.business.StatusDirectaRemote.class, "StatusDirecta#com.banorte.contract.business.StatusDirectaRemote");
        //Joseles:Se agregó DirectaLoad
        ejbMap.put(com.banorte.contract.business.DirectaLoadRemote.class, "DirectaLoad#com.banorte.contract.business.DirectaLoadRemote");
        //Joseles:Se agregó DirectaResult
        ejbMap.put(com.banorte.contract.business.DirectaResultRemote.class, "DirectaResult#com.banorte.contract.business.DirectaResultRemote");
        //Joseles:Se agregó Plan
        ejbMap.put(com.banorte.contract.business.PlanRemote.class, "Plan#com.banorte.contract.business.PlanRemote");
        //Joseles:Se agregó Equipment
        ejbMap.put(com.banorte.contract.business.EquipmentRemote.class, "Equipment#com.banorte.contract.business.EquipmentRemote");
        //Joseles:Se agregó CommisionPlan
        ejbMap.put(com.banorte.contract.business.CommisionPlanRemote.class, "CommisionPlan#com.banorte.contract.business.CommisionPlanRemote");
        //Joseles:Se agregó IncomePlan
        ejbMap.put(com.banorte.contract.business.IncomePlanRemote.class, "IncomePlan#com.banorte.contract.business.IncomePlanRemote");
        //Omar:Se agregó Branch
        ejbMap.put(com.banorte.contract.business.BranchRemote.class, "Branch#com.banorte.contract.business.BranchRemote");
      //Omar:Se agregó Executive Branch
        ejbMap.put(com.banorte.contract.business.ExecutiveBranchRemote.class, "ExecutiveBranch#com.banorte.contract.business.ExecutiveBranchRemote");
      //Omar:Se agregó Bitacora
        ejbMap.put(com.banorte.contract.business.BitacoraRemote.class, "Bitacora#com.banorte.contract.business.BitacoraRemote");
      //Omar:Se agregó Load File 
        ejbMap.put(com.banorte.contract.business.LoadFileRemote.class, "LoadFile#com.banorte.contract.business.LoadFileRemote");
      //Omar:Se agregó Load File 
        ejbMap.put(com.banorte.contract.business.FailReferenceRemote.class, "FailReference#com.banorte.contract.business.FailReferenceRemote");
      //Omar:Se agregó Recipent Mail 
        ejbMap.put(com.banorte.contract.business.RecipentMailRemote.class, "RecipentMail#com.banorte.contract.business.RecipentMailRemote");
        //gina
        ejbMap.put(com.banorte.contract.business.ProductTypeRemote.class, "ProductType#com.banorte.contract.business.ProductTypeRemote");
        ejbMap.put(com.banorte.contract.business.UserProfileRemote.class, "UserProfile#com.banorte.contract.business.UserProfileRemote");
        //Alta Emisora PyME
        ejbMap.put(com.banorte.contract.business.StatusRemote.class, "DMFiltroEmisoras#com.banorte.contract.business.StatusRemote");
    }

    public static synchronized Object getEJB(Class c) {
        try {
            Context ctx = new InitialContext();         
            Object o = ctx.lookup(ejbMap.get(c));
            return o;
        } catch (NamingException ex) {
            Logger.getLogger(StatusMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString() 
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
    
    
}
