/**
 * 
 */
package com.banorte.contract.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.banorte.contract.business.AffiliationRemote;
import com.banorte.contract.business.ClientRemote;
import com.banorte.contract.business.ExecutiveBranchRemote;
import com.banorte.contract.business.ExecutiveRemote;
import com.banorte.contract.business.ProductRemote;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.Executive;
import com.banorte.contract.model.ExecutiveBranch;
import com.banorte.contract.web.EjbInstanceManager;

/**
 * @author omar
 *
 */
public class ReportUtil {
	
	protected ExecutiveBranchRemote executiveBranchBean;
	protected ExecutiveRemote executiveBean;
	
	
	
	
	/**
	 * 
	 */
	public ReportUtil() {
		super();
		
		if (executiveBranchBean == null) {
			executiveBranchBean = (ExecutiveBranchRemote) EjbInstanceManager.getEJB(ExecutiveBranchRemote.class);
		}
		if (executiveBean == null) {
			executiveBean = (ExecutiveRemote) EjbInstanceManager.getEJB(ExecutiveRemote.class);
		}
	}

	public boolean isContractBEM(String referencia){
		boolean resultado = Boolean.FALSE;
	
		if(referencia.contains(ApplicationConstants.PREFIJO_BEM)){
			resultado = Boolean.TRUE;
		}else if(referencia.contains(ApplicationConstants.PREFIJO_MTTOS)){
			resultado = Boolean.TRUE;
		}
		
		return resultado;
	}
	
	public boolean getLicenseBEM(String userId,String referencia,Contract contract){
		
		boolean resultado 					= Boolean.FALSE;
		boolean isBEM 						= Boolean.FALSE;
		String creationoffempnum 			= contract.getCreationoffempnum();
		String authoffempnum1 				= contract.getAuthoffempnum1();
		String authoffempnum2 				= contract.getAuthoffempnum2();
		String cr_str 						= ApplicationConstants.EMPTY_STRING;
		String idTerritorio					= ApplicationConstants.EMPTY_STRING;
		String idRegion						= ApplicationConstants.EMPTY_STRING;
		Integer cr 							= ApplicationConstants.INIT_CONT_CERO;
		ExecutiveBranch executiveBranch 	= null;
		List<Executive> executiveList 		= new ArrayList<Executive>();
		
		isBEM = this.isContractBEM(referencia);
		
		if(!isBEM){
			return Boolean.FALSE;
		}
		
		if(userId.equals( creationoffempnum )){
			resultado = Boolean.TRUE;
			return resultado;
		}else if(userId.equals( authoffempnum1 )){
			resultado = Boolean.TRUE;
			return resultado;
		} else if(userId.equals( authoffempnum2 )){
			resultado = Boolean.TRUE;
			return resultado;
		}
		
		cr_str = getAttributeValueById(contract.getContractAttributeCollection(), ApplicationConstants.ID_ATTRIBUTE_CR);
		if(cr_str.equals(ApplicationConstants.EMPTY_STRING)){
			return Boolean.FALSE;
		}
		cr = Integer.valueOf(cr_str);
		executiveBranch = executiveBranchBean.findExecutiveBranchByCR(cr);
		
		//Verifica que el usuario sea Ejecutivo de la Region y territorio
		if(executiveBranch != null){
			idTerritorio = String.valueOf( executiveBranch.getIdTerritorio() );
			idRegion = String.valueOf( executiveBranch.getIdRegion() );
			executiveList = executiveBean.findExecutiveByTerritorioAndRegion(idTerritorio, idRegion);
			
			for(Executive executive : executiveList ){
				if(userId.equals( executive.getUserid())){
					resultado = Boolean.TRUE;
					return resultado;
				}
			}
			
			executiveList = executiveBean.findExecutiveCoordinatorByTerritory(idTerritorio, Boolean.TRUE);
			
			for(Executive executive : executiveList ){
				if(userId.equals( executive.getUserid())){
					resultado = Boolean.TRUE;
					return resultado;
				}
			}
			
		}
		
		
		
		return resultado;
		
		
	}
	
	public String getAttributeValueById(Collection<ContractAttribute> ContractAttributeCollection,Integer idAttribute){
		String resultado = ApplicationConstants.EMPTY_STRING ;
		
		if(ContractAttributeCollection == null){
			return resultado;
		}
		
		for (ContractAttribute contractAttribute : ContractAttributeCollection){
			if ( contractAttribute.getAttribute().getAttributeid().equals(idAttribute) ){
				resultado = contractAttribute.getValue();
				break;
			}
			
		}
		
		return resultado;
		
	}
	
	
	public boolean isContractMtto(String reference){
		
		return reference.contains(ApplicationConstants.PREFIJO_MTTOS);		
	}

}
