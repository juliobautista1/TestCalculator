/**
 * 
 */
package com.banorte.contract.util;

/**
 * @author omar
 *
 */
public class LayoutOperationResponseUtil {
	
	
	public Integer getProductTypeByFileName(String fileName){
		Integer productType 	= ApplicationConstants.VALUE_0;
		String sigla 			= ApplicationConstants.EMPTY_STRING;
		
		sigla = fileName.substring(ApplicationConstants.FILE_SUBSTR_BEGIN, ApplicationConstants.FILE_SUBSTR_END);
		
		if(sigla.equals(ApplicationConstants.FILE_BEM)){
			productType = ApplicationConstants.PT_BEM;
		}else if (sigla.equals(ApplicationConstants.FILE_NOMINA)){
			productType = ApplicationConstants.PT_NOMINA;
		}else if (sigla.equals(ApplicationConstants.FILE_ADQ)){
			productType = ApplicationConstants.PT_ADQ;
		}else if (sigla.equals(ApplicationConstants.FILE_MB)){
			productType = ApplicationConstants.PT_MB;
		}else if (sigla.equals(ApplicationConstants.FILE_CD)){
			productType = ApplicationConstants.PT_CD;
		}else if (sigla.equals(ApplicationConstants.FILE_ALTA)){
			productType = ApplicationConstants.PT_BEM;
		}
		
		
		return productType;
	}
	
	
	public int getIdUserTokenAttr(int posicion){
		int idAttr = 0;
		
		switch (posicion){
		case 1: idAttr= ApplicationConstants.ATTR_ID_USER_TOKEN_1;
			break;
		case 2: idAttr= ApplicationConstants.ATTR_ID_USER_TOKEN_2;
			break;
		case 3: idAttr= ApplicationConstants.ATTR_ID_USER_TOKEN_3;
			break;
		case 4: idAttr= ApplicationConstants.ATTR_ID_USER_TOKEN_4;
			break;
		case 5: idAttr= ApplicationConstants.ATTR_ID_USER_TOKEN_5;
			break;
		case 6: idAttr= ApplicationConstants.ATTR_ID_USER_TOKEN_6;
			break;
		case 7: idAttr= ApplicationConstants.ATTR_ID_USER_TOKEN_7;
			break;
		case 8: idAttr= ApplicationConstants.ATTR_ID_USER_TOKEN_8;
			break;
		case 9: idAttr= ApplicationConstants.ATTR_ID_USER_TOKEN_9;
			break;
		case 10: idAttr= ApplicationConstants.ATTR_ID_USER_TOKEN_10;
			break;
		case 11: idAttr= ApplicationConstants.ATTR_ID_USER_TOKEN_11;
			break;
		case 12: idAttr= ApplicationConstants.ATTR_ID_USER_TOKEN_12;
			break;
		}
		
		return idAttr;
	}

}
