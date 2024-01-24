/**
 * 
 */
package com.banorte.contract.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.banorte.contract.business.ClientRemote;
import com.banorte.contract.business.PayrateRemote;
import com.banorte.contract.model.Payrate;
import com.banorte.contract.web.EjbInstanceManager;

/**
 * @author omar
 *
 */
public class ContractCDUtil {
	
	
	public PayrateRemote payrateBean;
	

	public ContractCDUtil() {
		if (payrateBean == null) {
			payrateBean = (PayrateRemote) EjbInstanceManager.getEJB(PayrateRemote.class);
		}
		
	}
	
	public ArrayList<Payrate> LoadComissionBatch(){
		ArrayList<Payrate> result = new ArrayList<Payrate>();
		result = (ArrayList<Payrate>) payrateBean.getComisionCDBatch();
		
		return result;
	}
	
	
	public ArrayList<Payrate> LoadComissionOnLine(){
		ArrayList<Payrate> result = new ArrayList<Payrate>();
		result = (ArrayList<Payrate>) payrateBean.getComisionCDOnLine();
		
		return result;
	}
	
	public HashMap<String,Payrate> loadHashComission(ArrayList<Payrate> listComission){
		
		HashMap<String,Payrate> result = new HashMap<String,Payrate>();
		
		for(Payrate payrate:listComission){
			result.put(payrate.getCode(), payrate);
		}
		
		return result;
		
	}
	
	
	public String subStringUtil(String cadena){
		String resultado = ApplicationConstants.EMPTY_STRING;
		
		if(cadena.length()>ApplicationConstants.END_SUBSTRING){
			resultado = cadena.substring(ApplicationConstants.BEGIN_SUBSTRING, ApplicationConstants.END_SUBSTRING);
		}else {
			resultado = cadena;
		}
		
			
		return resultado;
	}
	
	public String fillWhitZero(String cadena, int tamanioMaximo){
		String resultado 	= ApplicationConstants.EMPTY_STRING;
		int tamanioCadena 	= cadena.length();
		
		
		for(int iterador = 0 ; tamanioCadena < tamanioMaximo ; iterador++){
			resultado= resultado +ApplicationConstants.ZERO_STRING;
		}
		
		resultado = resultado +cadena;
		
		return resultado;
		
	}
	
	public String transformSN(String cadena){
		String resultado 	= 	ApplicationConstants.EMPTY_STRING;
		
		if( Boolean.parseBoolean(cadena)){
			resultado = ApplicationConstants.BOOLEAN_STRING_S;
		}else {
			resultado = ApplicationConstants.BOOLEAN_STRING_N;
		}
		
		return resultado;
	}
	
	

}
