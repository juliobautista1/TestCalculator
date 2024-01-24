package com.banorte.contract.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.banorte.contract.model.Contract;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.AttrConstants;
import com.banorte.contract.util.ContractCDUtil;
import com.banorte.contract.util.FillConstants;
import com.banorte.contract.util.HeaderConstants;
import com.banorte.contract.util.MttoType;

public class MttoBEMLayout extends LayoutOperationsTemplate implements Layout{
	
	private final Integer PRODUCTTYPEID = 5;	
	
	private final String FILLS[] = {AttrConstants.CLIENT_FISCALNAME,AttrConstants.MTTO_TYPE,AttrConstants.CHANGE_FISCAL_NAME_INFO,
			AttrConstants.CHANGE_RFC_INFO,AttrConstants.CLIENT_SIC,AttrConstants.CHANGE_STATE_INFO,AttrConstants.CHANGE_CITY_INFO,
			AttrConstants.CHANGE_COLONY_INFO,AttrConstants.CHANGE_ADDRESS_INFO,AttrConstants.CHANGE_CP_INFO,AttrConstants.CR_NUMBER,
			AttrConstants.COMPLETE_REPRESENTATIVE_NAME_1,AttrConstants.COMPLETE_REPRESENTATIVE_NAME_2,AttrConstants.CHANGE_EMAIL_INFO,
			AttrConstants.CHANGE_PHONE_INFO,AttrConstants.CHANGE_CHARGE_CHECK,AttrConstants.CHANGE_ACCOUNT_CHARGE_INFO,
			AttrConstants.MTTO_TOKEN_NUMBER_1,AttrConstants.MTTO_FOLIO_PSD_1,AttrConstants.MTTO_TOKEN_NUMBER_2,
			AttrConstants.MTTO_FOLIO_PSD_2,AttrConstants.MTTO_TOKEN_NUMBER_3,AttrConstants.MTTO_FOLIO_PSD_3,AttrConstants.MTTO_TOKEN_NUMBER_4,
			AttrConstants.MTTO_FOLIO_PSD_4,AttrConstants.MTTO_TOKEN_NUMBER_5,AttrConstants.MTTO_FOLIO_PSD_5,
			AttrConstants.MTTO_TOKEN_NUMBER_6,AttrConstants.MTTO_TOKEN_NUMBER_7,AttrConstants.MTTO_TOKEN_NUMBER_8,
			AttrConstants.MTTO_TOKEN_NUMBER_9,AttrConstants.MTTO_TOKEN_NUMBER_10,AttrConstants.LEGALREPRESENTATIVE_NAME_1,
			AttrConstants.LEGALREPRESENTATIVE_NAME_2,AttrConstants.LEGALREPRESENTATIVE_NAME_3,
			AttrConstants.ACCOUNT_NUMBER_1,AttrConstants.ACCOUNT_NUMBER_2,AttrConstants.ACCOUNT_NUMBER_3,
			AttrConstants.ACCOUNT_NUMBER_4,AttrConstants.ACCOUNT_NUMBER_5,AttrConstants.DROP_MOTIVE,
			AttrConstants.OFFICER_EBANKING_NUMBER,AttrConstants.COMPLETE_OFFICER_EBANKING_NAME,
			AttrConstants.MTTO_ACCOUNT_NUMBER_1,AttrConstants.MTTO_ACCOUNT_NUMBER_2,AttrConstants.MTTO_ACCOUNT_NUMBER_3,
			AttrConstants.MTTO_ACCOUNT_NUMBER_4,AttrConstants.MTTO_ACCOUNT_NUMBER_5,AttrConstants.MTTO_ACCOUNT_NUMBER_6,
			AttrConstants.MTTO_ACCOUNT_NUMBER_7,AttrConstants.MTTO_ACCOUNT_NUMBER_8,AttrConstants.MTTO_ACCOUNT_NUMBER_9,
			AttrConstants.MTTO_ACCOUNT_NUMBER_10,AttrConstants.BEM_NUMBER,AttrConstants.MTTO_NEW_TOKEN_NUMBER_1,
			AttrConstants.MTTO_NEW_TOKEN_NUMBER_2,AttrConstants.MTTO_NEW_TOKEN_NUMBER_3,
			AttrConstants.MTTO_NEW_TOKEN_NUMBER_4,AttrConstants.MTTO_NEW_TOKEN_NUMBER_5,
			AttrConstants.CHANGE_ACCOUNT_CENTRAL_CHECK,AttrConstants.CHANGE_ORIGIN_TRANSACTION_CHECK,
			AttrConstants.CHANGE_PLAN_INFO
			};

	
	public MttoBEMLayout(List<Contract> search) {
        for ( Contract record : search ) {
           if( record.getProduct().getProductTypeid().getProductTypeid().intValue() ==  PRODUCTTYPEID) {
               this.setContracts(record);
           }
       }
   }
	
	@Override
	public boolean hasElements() {
        if( this.getContracts().size() > 0 ) {
            return true;
        } else
            return false;
        
	}

	@Override
	public List<String> getContent(Contract contract) {
		List<String> list 			= new ArrayList();        
        Map<String, String> map 	= new LayoutTempleteContract().bindFrom(contract, FILLS);
        String mttoTypeStr 			= map.get(AttrConstants.MTTO_TYPE);
        //int mttoTypeId 				= Integer.getInteger( mttoTypeStr  ); 
        String cobroComisiontype 	= ApplicationConstants.EMPTY_STRING;
        String cobroComisionStr 	= ApplicationConstants.EMPTY_STRING;
        
        list.add(contract.getReference());    //folio  
        list.add(map.get(AttrConstants.BEM_NUMBER));//numero de empresa
        list.add(map.get(AttrConstants.MTTO_TYPE)); //tipo de mantenimiento
        
        if(mttoTypeStr.equals( MttoType.CONVENIO_MODIFICATORIO.getMttoTypeIdStr() ) ){
        	list.add(map.get(AttrConstants.CHANGE_FISCAL_NAME_INFO)); 
        	list.add(map.get(AttrConstants.CLIENT_FISCALNAME)); 
        }else {
        	list.add(map.get(AttrConstants.CLIENT_FISCALNAME));
        	list.add(FillConstants.STATIC_COLUMN_EMPTY);  		// Razon social Anterior
        }
        
        list.add(map.get(AttrConstants.CHANGE_RFC_INFO));
        
        /*if(mttoTypeStr.equals( MttoType.CONVENIO_MODIFICATORIO.getMttoTypeIdStr()) || mttoTypeStr.equals(MttoType.ALTA_CUENTAS.getMttoTypeIdStr()) ){
        	list.add(map.get(AttrConstants.CLIENT_SIC)); 
        }else {
        	list.add(FillConstants.STATIC_COLUMN_EMPTY);  		// SIC
        }*/
        
        list.add(map.get(AttrConstants.CLIENT_SIC));
        
        if(mttoTypeStr.equals(MttoType.CONVENIO_MODIFICATORIO.getMttoTypeIdStr()) ){
        	list.add(map.get(FillConstants.STATIC_MEXICO));
        }else {
        	list.add(FillConstants.STATIC_COLUMN_EMPTY); 	//	pais
        }
        
        list.add(map.get(AttrConstants.CHANGE_STATE_INFO)); 
    	list.add(map.get(AttrConstants.CHANGE_CITY_INFO));
    	list.add(map.get(AttrConstants.CHANGE_COLONY_INFO)); 
    	list.add(map.get(AttrConstants.CHANGE_ADDRESS_INFO)); 
    	list.add(map.get(AttrConstants.CHANGE_CP_INFO)); 
        list.add(map.get(AttrConstants.CR_NUMBER)); 
        
        if(mttoTypeStr.equals( MttoType.ALTA_CUENTAS.getMttoTypeIdStr() ) ){
        	list.add(map.get(AttrConstants.COMPLETE_REPRESENTATIVE_NAME_1));
            list.add(map.get(AttrConstants.COMPLETE_REPRESENTATIVE_NAME_2));
        }else {
        	 list.add(map.get(AttrConstants.LEGALREPRESENTATIVE_NAME_1));
             list.add(map.get(AttrConstants.LEGALREPRESENTATIVE_NAME_2));
        }
        
         
        
        
        list.add(map.get(AttrConstants.CHANGE_EMAIL_INFO));
        list.add(map.get(AttrConstants.CHANGE_PHONE_INFO));
        list.add(map.get(AttrConstants.CHANGE_PLAN_INFO));
        
        
        cobroComisiontype = map.get(AttrConstants.CHANGE_CHARGE_CHECK);
        
        if(mttoTypeStr.equals( MttoType.CONVENIO_MODIFICATORIO.getMttoTypeIdStr() ) ){
        	if( cobroComisiontype.equals(ApplicationConstants.VALUE_TRUE)){
        		String accountCentral = ( map.get(AttrConstants.CHANGE_ACCOUNT_CENTRAL_CHECK) !=null)?  map.get(AttrConstants.CHANGE_ACCOUNT_CENTRAL_CHECK) : ApplicationConstants.EMPTY_STRING;
        		String transaction = ( map.get(AttrConstants.CHANGE_ORIGIN_TRANSACTION_CHECK) !=null)?  map.get(AttrConstants.CHANGE_ORIGIN_TRANSACTION_CHECK) : ApplicationConstants.EMPTY_STRING;

        		
        		if(accountCentral.equals(ApplicationConstants.VALUE_TRUE)){
        			cobroComisionStr ="Cuenta Centralizada";
        		}
        		else if(transaction.equals(ApplicationConstants.VALUE_TRUE)){
        			cobroComisionStr ="Cuenta de Origen de la Transaccion";
        		}
            }
        }else {
        	cobroComisionStr = FillConstants.STATIC_COLUMN_EMPTY;
        }
        
        
        list.add(cobroComisionStr);
        list.add(map.get(AttrConstants.CHANGE_ACCOUNT_CHARGE_INFO)); 	 	//CONVENIO MODIFICATORIO    	
        
        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_1));
        list.add(map.get(AttrConstants.MTTO_NEW_TOKEN_NUMBER_1));
        list.add(map.get(AttrConstants.MTTO_FOLIO_PSD_1)); 
        
        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_2));
        list.add(map.get(AttrConstants.MTTO_NEW_TOKEN_NUMBER_2));
        list.add(map.get(AttrConstants.MTTO_FOLIO_PSD_2)); 
        
        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_3));
        list.add(map.get(AttrConstants.MTTO_NEW_TOKEN_NUMBER_3));
        list.add(map.get(AttrConstants.MTTO_FOLIO_PSD_3)); 
        
        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_4));
        list.add(map.get(AttrConstants.MTTO_NEW_TOKEN_NUMBER_4));
        list.add(map.get(AttrConstants.MTTO_FOLIO_PSD_4)); 
        
        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_5));
        list.add(map.get(AttrConstants.MTTO_NEW_TOKEN_NUMBER_5));
        list.add(map.get(AttrConstants.MTTO_FOLIO_PSD_5)); //
        
        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_6));
        list.add(FillConstants.STATIC_COLUMN_EMPTY); 
        list.add(FillConstants.STATIC_COLUMN_EMPTY);
        
        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_7));
        list.add(FillConstants.STATIC_COLUMN_EMPTY); 
        list.add(FillConstants.STATIC_COLUMN_EMPTY);
        
        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_8));
        list.add(FillConstants.STATIC_COLUMN_EMPTY); 
        list.add(FillConstants.STATIC_COLUMN_EMPTY);
        
        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_9));
        list.add(FillConstants.STATIC_COLUMN_EMPTY); 
        list.add(FillConstants.STATIC_COLUMN_EMPTY);
        
        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_10));
        list.add(FillConstants.STATIC_COLUMN_EMPTY); 
        list.add(FillConstants.STATIC_COLUMN_EMPTY);
        
        if(validateLegalRepresentative(mttoTypeStr)){
            list.add(FillConstants.STATIC_COLUMN_EMPTY); 	// ADMINISTRADOR 1
            list.add(FillConstants.STATIC_COLUMN_EMPTY); 	// ADMINISTRADOR 1
            list.add(FillConstants.STATIC_COLUMN_EMPTY); 	// ADMINISTRADOR 1
        }else{
        	list.add(FillConstants.STATIC_COLUMN_EMPTY); 
            list.add(FillConstants.STATIC_COLUMN_EMPTY); 
            list.add(FillConstants.STATIC_COLUMN_EMPTY); 
        }
        list.add(FillConstants.STATIC_COLUMN_EMPTY);  			// Mancumunado
        
        if(mttoTypeStr.equals( MttoType.BAJA_CUENTAS.getMttoTypeIdStr() ) ){//Para baja de cuentas
        	list.add(FillConstants.STATIC_COLUMN_EMPTY); 
            list.add(FillConstants.STATIC_COLUMN_EMPTY); 
            list.add(FillConstants.STATIC_COLUMN_EMPTY); 
            list.add(FillConstants.STATIC_COLUMN_EMPTY); 
            list.add(FillConstants.STATIC_COLUMN_EMPTY);
            list.add(FillConstants.STATIC_COLUMN_EMPTY); 
            list.add(FillConstants.STATIC_COLUMN_EMPTY); 
            list.add(FillConstants.STATIC_COLUMN_EMPTY); 
            list.add(FillConstants.STATIC_COLUMN_EMPTY); 
            list.add(FillConstants.STATIC_COLUMN_EMPTY);
        }else{
        	list.add(map.get(AttrConstants.ACCOUNT_NUMBER_1));
            list.add(map.get(AttrConstants.ACCOUNT_NUMBER_2));
            list.add(map.get(AttrConstants.ACCOUNT_NUMBER_3));
            list.add(map.get(AttrConstants.ACCOUNT_NUMBER_4));
            list.add(map.get(AttrConstants.ACCOUNT_NUMBER_5));
            list.add(map.get(AttrConstants.ACCOUNT_NUMBER_6));
            list.add(map.get(AttrConstants.ACCOUNT_NUMBER_7));
            list.add(map.get(AttrConstants.ACCOUNT_NUMBER_8));
            list.add(FillConstants.STATIC_COLUMN_EMPTY); 
            list.add(FillConstants.STATIC_COLUMN_EMPTY);
        }
        
//        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_6));
//        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_7));
//        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_8));
//        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_9));
//        list.add(map.get(AttrConstants.MTTO_TOKEN_NUMBER_10));
        
        list.add(map.get(AttrConstants.DROP_MOTIVE));//motivo de baja
        list.add(map.get(AttrConstants.OFFICER_EBANKING_NUMBER));
        list.add(map.get(AttrConstants.COMPLETE_OFFICER_EBANKING_NAME));
        
        if(mttoTypeStr.equals( MttoType.BAJA_CUENTAS.getMttoTypeIdStr() ) ){
        	list.add(map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_1));
            list.add(map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_2));
            list.add(map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_3));
            list.add(map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_4));
            list.add(map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_5));
            list.add(map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_6));
            list.add(map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_7));
            list.add(map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_8));
            list.add(map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_9));
            list.add(map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_10));
        }else {
        	for(int iterador=1;iterador <=HeaderConstants.MAX_CUENTAS ; iterador++){
        		list.add(map.get(FillConstants.STATIC_COLUMN_EMPTY)); 	//	vacio		
    		}
        	
        }
        
        return list;
	}

	@Override
	public List<String> getHeader() {
		List<String> list = new ArrayList();
        
		list.add(HeaderConstants.FOLIO);
		list.add(HeaderConstants.NUMERO_EMPRESA);
		list.add(HeaderConstants.TIPO_MTTO);
		list.add(HeaderConstants.RAZON_SOCIAL);
		list.add(HeaderConstants.RAZON_SOCIAL_ANTERIOR);
		list.add(HeaderConstants.RFC);
		list.add(HeaderConstants.SIC);
		list.add(HeaderConstants.PAIS);
		list.add(HeaderConstants.ESTADO);
		list.add(HeaderConstants.CIUDAD);
		list.add(HeaderConstants.COLONIA);
		list.add(HeaderConstants.CALLE);
		list.add(HeaderConstants.CP);
		list.add(HeaderConstants.CR_MTTO);
		list.add(HeaderConstants.APODERADO_1);
		list.add(HeaderConstants.APODERADO_2);
		list.add(HeaderConstants.EMAIL);
		list.add(HeaderConstants.TELEFONO);
		list.add(HeaderConstants.PLAN);
		list.add(HeaderConstants.TIPO_COBRO_COMISION);
		list.add(HeaderConstants.CUENTA);
		
		for(int iterador=1;iterador <=HeaderConstants.MAX_FOLIOS ; iterador++){
			list.add(HeaderConstants.TOKEN + " " + iterador);
			list.add(HeaderConstants.TOKEN_NUEVO + " " + iterador);
			list.add(HeaderConstants.FOLIO + " " + iterador);
		}
		
		for(int iterador=1;iterador <=HeaderConstants.MAX_ADMINISTRADOR ; iterador++){
			list.add(HeaderConstants.ADMINISTRADOR + " " + iterador);			
		}
		
		list.add(HeaderConstants.MANCOMUNADO);
		
		for(int iterador=1;iterador <=HeaderConstants.MAX_CUENTAS ; iterador++){
			list.add(HeaderConstants.CUENTA + " " + iterador);			
		}
		
		list.add(HeaderConstants.MOTIVO_BAJA);
		list.add(HeaderConstants.NUM_EMP_EBANKING);
		list.add(HeaderConstants.NOMBRE_EMP_EBANKING);
		
		for(int iterador=1;iterador <=HeaderConstants.MAX_CUENTAS ; iterador++){
			list.add(HeaderConstants.BAJA_CUENTA + " " + iterador);			
		}
		
        return list;
	}
	
	private boolean validateLegalRepresentative(String mttoTypeStr){
		
		boolean result = Boolean.FALSE;
		if(mttoTypeStr.equals( MttoType.NUEVA_CONTRASENA.getMttoTypeIdStr() ) ){
			result = Boolean.TRUE;
        }
		
		if(mttoTypeStr.equals( MttoType.REPOSICION_TOKENS.getMttoTypeIdStr() ) ){
			result = Boolean.TRUE;
        }
		
		if(mttoTypeStr.equals( MttoType.TOKENS_ADICIONALES.getMttoTypeIdStr() ) ){
			result = Boolean.TRUE;
        }
		
		if(mttoTypeStr.equals( MttoType.CONVENIO_MODIFICATORIO.getMttoTypeIdStr() ) ){
			result = Boolean.TRUE;
        }
		
		if(mttoTypeStr.equals( MttoType.BAJA_TOKENS.getMttoTypeIdStr() ) ){
			result = Boolean.TRUE;
        }
		
		if(mttoTypeStr.equals( MttoType.BAJA_CUENTAS.getMttoTypeIdStr() ) ){
			result = Boolean.TRUE;
        }
		
		return result;
	}

}
