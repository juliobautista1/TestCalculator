package com.banorte.contract.web;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

import com.banorte.contract.model.Contract;
import com.banorte.contract.model.Maintance;
import com.banorte.contract.util.DirectaStatus;
import com.banorte.contract.util.EncryptBd;
import com.banorte.contract.util.Formatter;

public class OutboundReportMB extends ReportMB {

	private static Logger log = Logger.getLogger(OutboundReportMB.class
			.getName());

	private String searchDescription;
	private String searchoption;
	private static final String SEARCHOPTION_MERCHANTNUMBER = "MERCHANTNUMBER";
	private static final String SEARCHOPTION_SIC = "SIC";

	private Collection<Maintance> resultList;

    public void setBeginSearch() {
    	
//		FacesContext fCtx = FacesContext.getCurrentInstance();
//		Map<String, String> params = fCtx.getExternalContext()
//				.getRequestParameterMap();
//
//		String merchantNumber = Formatter.fixLenght(params.get("merchantNumber").trim(),8);
//
//    	this.searchoption = SEARCHOPTION_MERCHANTNUMBER;
//    	this.setSearchDescription(merchantNumber);

    	//this.getReportResult();
    }
    
    public String getBeginSearch() {
    	setBeginSearch();
        return "";
    }

	// searchDescription property
	public void setSearchDescription(String reference) {
		this.searchDescription = reference.toUpperCase();
	}

	public String getSearchDescription() {
		return searchDescription;
	}

	// ResultList property
	public void setResultList(Collection<Maintance> list) {
		this.resultList = list;
	}
	public Collection<Maintance> getResultList() {

		FacesContext fCtx = FacesContext.getCurrentInstance();
		Map<String, String> params = fCtx.getExternalContext()
				.getRequestParameterMap();

		//Verificar si vienen parametros de busqueda
		if(params.get("beginSearch")!= null && params.get("beginSearch").equals("true")){
	    	this.searchoption = SEARCHOPTION_MERCHANTNUMBER;
	    	this.searchDescription = params.get("merchantNumber");
	    	this.resultList= null; 
		}
		
		if (resultList == null)
			this.getReportResult();
		return this.resultList;
	}

	// searchoption property
	public void setSearchoption(String searchoption) {
		this.searchoption = searchoption.trim();
	}
	public String getSearchoption() {
		return this.searchoption;
	}

	public OutboundReportMB() {
		super();

		this.searchoption = SEARCHOPTION_MERCHANTNUMBER;
	}


	public String getReportResult() {
				
		ArrayList<Maintance> list = new ArrayList<Maintance>();

		if (this.searchDescription != null
				&& !this.searchDescription.equals("")) {
			if (this.searchoption.equals(SEARCHOPTION_MERCHANTNUMBER)) {
				try {

					String merchantNumber = Formatter.fixLenght(this.searchDescription.trim(),8);
					
					List<Maintance> result = maintanceBean.findByMerchantNumber(merchantNumber);

					if (result != null && result.size() > 0) {
						list.addAll(complementSearch(result));
					}
				} catch (NumberFormatException e) {
				}
			} else if (this.searchoption.equals(SEARCHOPTION_SIC)) {
				try {
					if (Formatter.isNumeric(this.searchDescription.trim())){
						
						Integer sic= Integer.parseInt(this.searchDescription.trim());
						List<Maintance> result = maintanceBean.findBySIC(sic);
						
						list.addAll(complementSearch(result));
						
					}
				} catch (NumberFormatException e) {
				
				}
			}
		}

		this.setResultList(list);

		return SEARCH;
	}

	private List<Maintance> complementSearch(List<Maintance> list){
		
		EncryptBd decrypt = new EncryptBd();
		
		for(Maintance entity : list){
			
			if(entity.getStatus().getId().intValue() == DirectaStatus.CE_CUENTAS_NO_CONFIRMADAS.value().intValue())
			{
				String invalidAccounts = "Cuenta Invalidas: ";
				String comments = "";
				boolean containAccount = false;
				
		    	if(this.isInvalidaAccount(entity.getAccountNumber1(), entity.getIsValidAccountNumber1())){
		    		invalidAccounts += Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber1()));
		    		containAccount =true;
		    	}
		    	if(this.isInvalidaAccount(entity.getAccountNumber2(), entity.getIsValidAccountNumber2())){
		    		invalidAccounts += containAccount ? "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber2())): Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber2()));
		    		containAccount =true;
		    	}
		    	if(this.isInvalidaAccount(entity.getAccountNumber3(), entity.getIsValidAccountNumber3())){
		    		invalidAccounts += containAccount? "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber3())): Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber3()));
		    		containAccount =true;
		    	}
		    	if(this.isInvalidaAccount(entity.getAccountNumber4(), entity.getIsValidAccountNumber4())){
		    		invalidAccounts += containAccount ? "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber4())): Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber4()));
		    		containAccount =true;
		    	}
		    	if(this.isInvalidaAccount(entity.getAccountNumber5(), entity.getIsValidAccountNumber5())){
		    		invalidAccounts += containAccount ? "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber5())): Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber5()));
		    		containAccount =true;
		    	}
		    	if(this.isInvalidaAccount(entity.getAccountNumber6(), entity.getIsValidAccountNumber6())){
		    		invalidAccounts += containAccount ? "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber6())): Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber6()));
		    		containAccount =true;
		    	}
		    	if(this.isInvalidaAccount(entity.getAccountNumber7(), entity.getIsValidAccountNumber7())){
		    		invalidAccounts += containAccount ? "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber7())): Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber7()));
		    		containAccount =true;
		    	}
		    	if(this.isInvalidaAccount(entity.getAccountNumber8(), entity.getIsValidAccountNumber8())){
		    		invalidAccounts += containAccount ? "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber8())): Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber8()));
		    		containAccount =true;
		    	}
		    	if(this.isInvalidaAccount(entity.getAccountNumber9(), entity.getIsValidAccountNumber9())){
		    		invalidAccounts += containAccount ? "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber9())): Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber9()));
		    		containAccount =true;
		    	}
		    	if(this.isInvalidaAccount(entity.getAccountNumber10(), entity.getIsValidAccountNumber10())){
		    		invalidAccounts += containAccount ? "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber10())): Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber10()));
		    		containAccount =true;
		    	}
		    	if(this.isInvalidaAccount(entity.getAccountNumber11(), entity.getIsValidAccountNumber11())){
		    		invalidAccounts += containAccount ? "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber11())): Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber11()));
		    		containAccount =true;
		    	}
		    	if(this.isInvalidaAccount(entity.getAccountNumber12(), entity.getIsValidAccountNumber12())){
		    		invalidAccounts += containAccount ? "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber12())): Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber12()));
		    		containAccount =true;
		    	}
		    	if(this.isInvalidaAccount(entity.getAccountNumber13(), entity.getIsValidAccountNumber13())){
		    		invalidAccounts += containAccount ? "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber13())): Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber13()));
		    		containAccount =true;
		    	}
		    	if(this.isInvalidaAccount(entity.getAccountNumber14(), entity.getIsValidAccountNumber14())){
		    		invalidAccounts += containAccount ? "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber14())): Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber14()));
		    		containAccount =true;
		    	}
		    	if(this.isInvalidaAccount(entity.getAccountNumber15(), entity.getIsValidAccountNumber15())){
		    		invalidAccounts += containAccount ? "," + Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber15())): Formatter.maskAccount(decrypt.decrypt(entity.getAccountNumber15()));
		    		containAccount =true;
		    	}
			
		    	if 	(containAccount)
		    		invalidAccounts += ". ";
	    		
		    		
		    	if(entity.getComments() != null)
		    		comments = invalidAccounts + entity.getComments();
		    	else
		    		comments = invalidAccounts;
		    	
		    	entity.setComments(comments);
			
			}			
		}
		
		return list;
		
	}
	
    private boolean isInvalidaAccount(String account,Integer directaValidation){
    	
    	boolean isInvalid = false;
    	
    	//Verificar cada cuenta con el resultado de la campaña para verificar si alguna fue inavalida.
    	if (account !=null && !account.equals(""))
    		if(directaValidation == 1)
    			isInvalid =true;
    	
    	return isInvalid;
    }

}
