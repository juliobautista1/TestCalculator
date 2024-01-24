/**
 * 
 */
package com.banorte.contract.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.ContractMessageErrors;
import com.banorte.contract.model.Template;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.AttrConstants;
import com.banorte.contract.util.MttoType;
import com.banorte.contract.util.ProductType;
import com.banorte.contract.util.pdf.PdfTemplateBinding;
import com.banorte.contract.util.pdf.PdfTemplateBindingContract;

/**
 * @author omar
 *
 */
public class MttoBEMDropToken extends ContractAbstractMB {
	
	private Integer mttoType;
	private String bemnumber;
	private String fiscalFullName;
	private String legalRepresentativeFullName;   	//	AttrConstants.LEGALREPRESENTATIVE_NAME_1
	private String comments;
	
	private String quantityTokens;					//	AttrConstants.QTY_ACCOUNTS
	private String tokenNumber_1;
	private String tokenNumber_2;
	private String tokenNumber_3;
	private String tokenNumber_4;
	private String tokenNumber_5;
	private String tokenNumber_6;
	private String tokenNumber_7;
	private String tokenNumber_8;
	private String tokenNumber_9;
	private String tokenNumber_10;

    private String NAME_TEMPLATE ="nameTemplate";
    private String PATHTEMPLATE ="pathTemplate";
	
	private String dropTokensTemplate;
	
	private PdfTemplateBindingContract pdfTemplateBinding;
	
	public MttoBEMDropToken() {
		super();
		this.setDropTokensTemplate( ApplicationConstants.OPCION_SELECTED);
		this.mttoType = MttoType.BAJA_TOKENS.getMttoTypeId();
		setStatusContract(statusBean.findById(new Integer(ApplicationConstants.DEFAULT_VERSION_CONTRACT)));
	}
	
	@Override
	public void setResetForm() {
		this.bemnumber 						= ApplicationConstants.EMPTY_STRING;
		this.legalRepresentativeFullName	= ApplicationConstants.EMPTY_STRING;
		this.comments 						= ApplicationConstants.EMPTY_STRING;
		this.fiscalFullName					= ApplicationConstants.EMPTY_STRING;		
		this.quantityTokens 				= ApplicationConstants.EMPTY_STRING;
		this.tokenNumber_1 					= ApplicationConstants.EMPTY_STRING;
		this.tokenNumber_2 					= ApplicationConstants.EMPTY_STRING;
		this.tokenNumber_3 					= ApplicationConstants.EMPTY_STRING;
		this.tokenNumber_4 					= ApplicationConstants.EMPTY_STRING;
		this.tokenNumber_5 					= ApplicationConstants.EMPTY_STRING;
		this.tokenNumber_6 					= ApplicationConstants.EMPTY_STRING;
		this.tokenNumber_7 					= ApplicationConstants.EMPTY_STRING;
		this.tokenNumber_8 					= ApplicationConstants.EMPTY_STRING;
		this.tokenNumber_9 					= ApplicationConstants.EMPTY_STRING;
		this.tokenNumber_10 				= ApplicationConstants.EMPTY_STRING;
		
		clearFields();
		setProduct(productBean.findById(new Integer(ProductType.MTTO_DROP_TOKENS.value()))); 
		setStatusContract(statusBean.findById(new Integer(1))); // Status Nuevo
	}
	
	public String getResetForm() {
		setResetForm();
		return "";
	}
	
	public String getEditForm() {
		setEditForm();
		return "";
	}
	@Override
	public void setEditForm() {
		FacesContext fCtx 			= FacesContext.getCurrentInstance();
		Map<String, String> params 	= fCtx.getExternalContext().getRequestParameterMap();
		Integer idContract 			= Integer.parseInt(params.get(AttrConstants.CONTRACT_ID));
		
		String CD_FILLS[] = { AttrConstants.BEM_NUMBER,AttrConstants.CR_NUMBER,
				AttrConstants.LEGALREPRESENTATIVE_NAME_1,AttrConstants.CLIENT_FISCALNAME,
				AttrConstants.COMMENTS,AttrConstants.QTY_ACCOUNTS,AttrConstants.MTTO_TOKEN_NUMBER_1,
				AttrConstants.MTTO_TOKEN_NUMBER_2,AttrConstants.MTTO_TOKEN_NUMBER_3,AttrConstants.MTTO_TOKEN_NUMBER_4,
				AttrConstants.MTTO_TOKEN_NUMBER_5,AttrConstants.MTTO_TOKEN_NUMBER_6,AttrConstants.MTTO_TOKEN_NUMBER_7,
				AttrConstants.MTTO_TOKEN_NUMBER_8,AttrConstants.MTTO_TOKEN_NUMBER_9,AttrConstants.MTTO_TOKEN_NUMBER_10,
				AttrConstants.DROP_TOKEN_TEMPLATE_OPTION,AttrConstants.QTY_TOKENS,
				AttrConstants.OFFICER_REP_FIRMNUMBER_1,AttrConstants.OFFICER_EBANKING_FIRMNUMBER,AttrConstants.CLIENT_SIC};
		
		Contract contract_ = contractBean.findById(idContract);
		if (contract_.getContractAttributeCollection() != null) {
			Map<String, String> map = this.getContractAttributeFills(contract_,CD_FILLS);

			this.setClient_sic( map.get(AttrConstants.CLIENT_SIC));
			this.setBemnumber( map.get(AttrConstants.BEM_NUMBER) );
			this.setCrnumber( map.get(AttrConstants.CR_NUMBER) );
			this.setLegalRepresentativeFullName( map.get(AttrConstants.LEGALREPRESENTATIVE_NAME_1));
			this.setClient_fiscalname( map.get(AttrConstants.CLIENT_FISCALNAME));
			this.setFiscalFullName( map.get(AttrConstants.CLIENT_FISCALNAME) );
			this.setTokenNumber_1( map.get(AttrConstants.MTTO_TOKEN_NUMBER_1) );
			this.setTokenNumber_2( map.get(AttrConstants.MTTO_TOKEN_NUMBER_2) );
			this.setTokenNumber_3( map.get(AttrConstants.MTTO_TOKEN_NUMBER_3) );
			this.setTokenNumber_4( map.get(AttrConstants.MTTO_TOKEN_NUMBER_4) );
			this.setTokenNumber_5( map.get(AttrConstants.MTTO_TOKEN_NUMBER_5) );
			this.setTokenNumber_6( map.get(AttrConstants.MTTO_TOKEN_NUMBER_6) );
			this.setTokenNumber_7( map.get(AttrConstants.MTTO_TOKEN_NUMBER_7) );
			this.setTokenNumber_8( map.get(AttrConstants.MTTO_TOKEN_NUMBER_8) );
			this.setTokenNumber_9( map.get(AttrConstants.MTTO_TOKEN_NUMBER_9) );
			this.setTokenNumber_10( map.get(AttrConstants.MTTO_TOKEN_NUMBER_10) );
			this.setDropTokensTemplate( map.get(AttrConstants.DROP_TOKEN_TEMPLATE_OPTION));
			this.setComments( map.get(AttrConstants.COMMENTS));
			quantityTokens=map.get(AttrConstants.QTY_TOKENS);//gina
			
			
			//DATOS DEL FUNCIONARIO QUE CAPTURO LA SOLICITUD DE MANTENIMIENTO
			this.loadToEditOfficerInfo(map);
			
			//DATOS DE LOS FUNCIONARIOS BANORTE
			this.loadToEditOfficerRep1Info(map);
			this.setOfficerrepfirmnumber_1(map.get( AttrConstants.OFFICER_REP_FIRMNUMBER_1));
			
			//DATOS FUNCIONARIO EBANKING
			this.loadToEditEbankingInfo(map);
			this.setOfficerebankingnumfirm( map.get( AttrConstants.OFFICER_EBANKING_FIRMNUMBER) );
		}
		this.setContract(contract_);
		
	}
	
	
	@Override
	public PdfTemplateBinding getPdfTemplateBinding() {
		return pdfTemplateBinding;
	}
	
	
	@Override
	public Template[] getFormatList() {
		this.setToPrint(false);
		Collection<Template> templateCollection 	= getTemplateOption(this.getProduct().getProductid());
		
		Template[] templateArray = new Template[templateCollection.size()];
		return templateCollection.toArray(templateArray);
	}
	
	
	@Override
	public String getProductPrefix() {
		return ApplicationConstants.PREFIJO_MTTOS;
	}
	
	
	@Override
	public void getProductIdDetail() {
		setProduct(productBean.findById(new Integer(ProductType.MTTO_DROP_TOKENS.value())));
		
	}
	
	
	@Override
	public boolean savePartialContract() {
		Contract contract = getContract();
		ArrayList<ContractAttribute> list = new ArrayList<ContractAttribute>();

		// Mantenimientos Baja de Cuentas
		addToList(list,AttrConstants.CLIENT_SIC, getClient_sic());
		addToList(list,AttrConstants.MTTO_TYPE, this.mttoType.toString());
		addToList(list,AttrConstants.CELEBRATION_DATE, getCelebrationdate());
		addToList(list,AttrConstants.BEM_NUMBER, getBemnumber());
		addToList(list,AttrConstants.CR_NUMBER, getCrnumber());
		addToList(list,AttrConstants.LEGALREPRESENTATIVE_NAME_1,getLegalRepresentativeFullName());
		addToList(list,AttrConstants.CLIENT_FISCALNAME, getFiscalFullName());
		addToList(list,AttrConstants.COMMENTS, getComments());
		addToList(list,AttrConstants.QTY_TOKENS, getQuantityTokens());
		addToList(list,AttrConstants.MTTO_TOKEN_NUMBER_1, getTokenNumber_1());
		addToList(list,AttrConstants.MTTO_TOKEN_NUMBER_2, getTokenNumber_2());
		addToList(list,AttrConstants.MTTO_TOKEN_NUMBER_3, getTokenNumber_3());
		addToList(list,AttrConstants.MTTO_TOKEN_NUMBER_4, getTokenNumber_4());
		addToList(list,AttrConstants.MTTO_TOKEN_NUMBER_5, getTokenNumber_5());
		addToList(list,AttrConstants.MTTO_TOKEN_NUMBER_6, getTokenNumber_6());
		addToList(list,AttrConstants.MTTO_TOKEN_NUMBER_7, getTokenNumber_7());
		addToList(list,AttrConstants.MTTO_TOKEN_NUMBER_8, getTokenNumber_8());
		addToList(list,AttrConstants.MTTO_TOKEN_NUMBER_9, getTokenNumber_9());
		addToList(list,AttrConstants.MTTO_TOKEN_NUMBER_10, getTokenNumber_10());
		addToList(list,AttrConstants.DROP_TOKEN_TEMPLATE_OPTION, getDropTokensTemplate());
		addToList(list,AttrConstants.OPERATION_COMMENTS, this.getComments().length()<250?this.getComments():this.getComments().substring(0, 249));
		
		addToList(list,"contract_reference", contract.getReference());
		
		//DATOS DEL FUNCIONARIO QUE CAPTURO LA SOLICITUD DE MANTENIMIENTO
		this.loadToSaveOfficer(list);
		
		//DATOS DE LOS FUNCIONARIOS BANORTE
		this.loadToSaveOfficerRep1(list);
		addToList(list,AttrConstants.OFFICER_REP_FIRMNUMBER_1,getOfficerrepfirmnumber_1());
		
		//DATOS FUNCIONARIO EBANKING
		this.loadToSaveOfficerEbanking(list);
		addToList(list,AttrConstants.OFFICER_EBANKING_FIRMNUMBER,(getOfficerebankingnumfirm()==null?"":getOfficerebankingnumfirm()));
		
		this.loadToSaveInfoDatosCompleteMtto(list);
		
		contract.setContractAttributeCollection(list);
		contractBean.update(contract);

		pdfTemplateBinding = new PdfTemplateBindingContract();
		pdfTemplateBinding.setContract(contract);

		return true;
	}
	
	
	public void createPDF() {
   		FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fCtx.getExternalContext().getRequest();
		   	
	   	   String  nameTemplate = request.getParameter(NAME_TEMPLATE);
	       String  pathTemplate = request.getParameter(PATHTEMPLATE);
	       Integer flagTemplate = Integer.parseInt(request.getParameter("flagTemplate"));//Integer.parseInt(params.get("flagTemplate"));
			pdfTemplateBinding.setAffiliationId(1);
			createPdfOrXLSResponse(getPath() + pathTemplate+nameTemplate,nameTemplate,true, flagTemplate.intValue() == 1 ? true : false);
/*		FacesContext fCtx 				= FacesContext.getCurrentInstance();
		Map<String, String> params 		= fCtx.getExternalContext().getRequestParameterMap();
		String nameTemplate 			= params.get("nameTemplate");
		String pathTemplate 			= params.get("pathTemplate");
		Integer flagTemplate 			= Integer.parseInt(params.get("flagTemplate"));

		pdfTemplateBinding.setAffiliationId(1);
		createPdfOrXLSResponse(getPath() + pathTemplate + nameTemplate,
				nameTemplate, true, flagTemplate.intValue() == 1 ? true : false);*/
	}
	
	
	public String processInfo() {
		this.setValidationEDO(Boolean.FALSE);
		this.setValidationMtto( Boolean.FALSE);  				// para no validar los 2 representantes de Banorte
		this.setInfoTokens();
		validateTokens();
		return saveCompleteMtto();
	}
	
	public void validateTokens(){
		this.getGeneralInfoErrorsList().clear();
		
		if (this.getErrorsList() != null) {
			this.getErrorsList().clear();
		}
//		ContractMessageErrors errors = new ContractMessageErrors();
		int tokens = Integer.parseInt(quantityTokens);
		
			if(tokenNumber_1.length()!=9){
				ContractMessageErrors errors = new ContractMessageErrors();
				errors.setMessage("El numero de token 1 debe ser de 9 posiciones");
				this.getGeneralInfoErrorsList().add(errors);
			}
			if(tokenNumber_2.length()!=9 && tokens>=2){
				ContractMessageErrors errors = new ContractMessageErrors();
				errors.setMessage("El numero de token 2 debe ser de 9 posiciones");
				this.getGeneralInfoErrorsList().add(errors);
			}
			if(tokenNumber_3.length()!=9 && tokens >=3){
				ContractMessageErrors errors = new ContractMessageErrors();
				errors.setMessage("El numero de token 3 debe ser de 9 posiciones");
				this.getGeneralInfoErrorsList().add(errors);
			}
			if(tokenNumber_4.length()!=9 && tokens>=4){
				ContractMessageErrors errors = new ContractMessageErrors();
				errors.setMessage("El numero de token 4 debe ser de 9 posiciones");
				this.getGeneralInfoErrorsList().add(errors);
			}
			if(tokenNumber_5.length()!=9 && tokens>=5){
				ContractMessageErrors errors = new ContractMessageErrors();
				errors.setMessage("El numero de token 5 debe ser de 9 posiciones");
				this.getGeneralInfoErrorsList().add(errors);
			}
			if(tokenNumber_6.length()!=9 && tokens >=6){
				ContractMessageErrors errors = new ContractMessageErrors();
				errors.setMessage("El numero de token 6 debe ser de 9 posiciones");
				this.getGeneralInfoErrorsList().add(errors);
			}
			if(tokenNumber_7.length()!=9 && tokens>=7){
				ContractMessageErrors errors = new ContractMessageErrors();
				errors.setMessage("El numero de token 7 debe ser de 9 posiciones");
				this.getGeneralInfoErrorsList().add(errors);
			}
			if(tokenNumber_8.length()!=9 && tokens>=8){
				ContractMessageErrors errors = new ContractMessageErrors();
				errors.setMessage("El numero de token 8 debe ser de 9 posiciones");
				this.getGeneralInfoErrorsList().add(errors);
			}
			if(tokenNumber_9.length()!=9 && tokens >=9){
				ContractMessageErrors errors = new ContractMessageErrors();
				errors.setMessage("El numero de token 9 debe ser de 9 posiciones");
				this.getGeneralInfoErrorsList().add(errors);
			}
			if(tokenNumber_10.length()!=9 && tokens>=10){
				ContractMessageErrors errors = new ContractMessageErrors();
				errors.setMessage("El numero de token 10 debe ser de 9 posiciones");
				this.getGeneralInfoErrorsList().add(errors);
			}

		this.setErrorsList(this.getGeneralInfoErrorsList());
		
		
	}
	
	private void setInfoTokens(){
		if(getQuantityTokens().equals( ApplicationConstants.NUMBER_1)){
			setEmptyInfo2();
			setEmptyInfo3();
			setEmptyInfo4();
			setEmptyInfo5();
			setEmptyInfo6();
			setEmptyInfo7();
			setEmptyInfo8();
			setEmptyInfo9();
			setEmptyInfo10();
		}else if(getQuantityTokens().equals( ApplicationConstants.NUMBER_2)){
			setEmptyInfo3();
			setEmptyInfo4();
			setEmptyInfo5();
			setEmptyInfo6();
			setEmptyInfo7();
			setEmptyInfo8();
			setEmptyInfo9();
			setEmptyInfo10();
		}else if(getQuantityTokens().equals( ApplicationConstants.NUMBER_3)){
			setEmptyInfo4();
			setEmptyInfo5();
			setEmptyInfo6();
			setEmptyInfo7();
			setEmptyInfo8();
			setEmptyInfo9();
			setEmptyInfo10();
		}else if(getQuantityTokens().equals( ApplicationConstants.NUMBER_4)){
			setEmptyInfo5();
			setEmptyInfo6();
			setEmptyInfo7();
			setEmptyInfo8();
			setEmptyInfo9();
			setEmptyInfo10();
		}else if(getQuantityTokens().equals( ApplicationConstants.NUMBER_5)){
			setEmptyInfo6();
			setEmptyInfo7();
			setEmptyInfo8();
			setEmptyInfo9();
			setEmptyInfo10();
		}else if(getQuantityTokens().equals( ApplicationConstants.NUMBER_6)){
			setEmptyInfo7();
			setEmptyInfo8();
			setEmptyInfo9();
			setEmptyInfo10();
		}else if(getQuantityTokens().equals( ApplicationConstants.NUMBER_7)){
			setEmptyInfo8();
			setEmptyInfo9();
			setEmptyInfo10();
		}else if(getQuantityTokens().equals( ApplicationConstants.NUMBER_8)){
			setEmptyInfo9();
			setEmptyInfo10();
		}else if(getQuantityTokens().equals( ApplicationConstants.NUMBER_9)){
			setEmptyInfo10();
		}
		
	}
	
	private void setEmptyInfo2(){
		this.setTokenNumber_2(ApplicationConstants.EMPTY_STRING);
	}
	
	private void setEmptyInfo3(){
		this.setTokenNumber_3(ApplicationConstants.EMPTY_STRING);
	}
	
	private void setEmptyInfo4(){
		this.setTokenNumber_4(ApplicationConstants.EMPTY_STRING);
	}
	
	private void setEmptyInfo5(){
		this.setTokenNumber_5(ApplicationConstants.EMPTY_STRING);
	}
	
	private void setEmptyInfo6(){
		this.setTokenNumber_6(ApplicationConstants.EMPTY_STRING);
	}
	
	private void setEmptyInfo7(){
		this.setTokenNumber_7(ApplicationConstants.EMPTY_STRING);
	}
	
	private void setEmptyInfo8(){
		this.setTokenNumber_8(ApplicationConstants.EMPTY_STRING);
	}
	
	private void setEmptyInfo9(){
		this.setTokenNumber_9(ApplicationConstants.EMPTY_STRING);
	}
	
	private void setEmptyInfo10(){
		this.setTokenNumber_10(ApplicationConstants.EMPTY_STRING);
	}
	
	
	
	// GETTERS & SETTERS 
	
	/**
	 * @return the mttoType
	 */
	public Integer getMttoType() {
		return mttoType;
	}
	/**
	 * @param mttoType the mttoType to set
	 */
	public void setMttoType(Integer mttoType) {
		this.mttoType = mttoType;
	}
	/**
	 * @return the bemnumber
	 */
	public String getBemnumber() {
		return bemnumber;
	}
	/**
	 * @param bemnumber the bemnumber to set
	 */
	public void setBemnumber(String bemnumber) {
		this.bemnumber = bemnumber;
	}
	/**
	 * @return the fiscalFullName
	 */
	public String getFiscalFullName() {
		return fiscalFullName;
	}
	/**
	 * @param fiscalFullName the fiscalFullName to set
	 */
	public void setFiscalFullName(String fiscalFullName) {
		this.fiscalFullName = fiscalFullName;
	}
	/**
	 * @return the legalRepresentativeFullName
	 */
	public String getLegalRepresentativeFullName() {
		return legalRepresentativeFullName;
	}
	/**
	 * @param legalRepresentativeFullName the legalRepresentativeFullName to set
	 */
	public void setLegalRepresentativeFullName(String legalRepresentativeFullName) {
		this.legalRepresentativeFullName = legalRepresentativeFullName;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return the quantityTokens
	 */
	public String getQuantityTokens() {
		return quantityTokens;
	}
	/**
	 * @param quantityTokens the quantityTokens to set
	 */
	public void setQuantityTokens(String quantityTokens) {
		this.quantityTokens = quantityTokens;
	}
	/**
	 * @return the tokenNumber_1
	 */
	public String getTokenNumber_1() {
		return tokenNumber_1;
	}
	/**
	 * @param tokenNumber_1 the tokenNumber_1 to set
	 */
	public void setTokenNumber_1(String tokenNumber_1) {
		this.tokenNumber_1 = tokenNumber_1;
	}
	/**
	 * @return the tokenNumber_2
	 */
	public String getTokenNumber_2() {
		return tokenNumber_2;
	}
	/**
	 * @param tokenNumber_2 the tokenNumber_2 to set
	 */
	public void setTokenNumber_2(String tokenNumber_2) {
		this.tokenNumber_2 = tokenNumber_2;
	}
	/**
	 * @return the tokenNumber_3
	 */
	public String getTokenNumber_3() {
		return tokenNumber_3;
	}
	/**
	 * @param tokenNumber_3 the tokenNumber_3 to set
	 */
	public void setTokenNumber_3(String tokenNumber_3) {
		this.tokenNumber_3 = tokenNumber_3;
	}
	/**
	 * @return the tokenNumber_4
	 */
	public String getTokenNumber_4() {
		return tokenNumber_4;
	}
	/**
	 * @param tokenNumber_4 the tokenNumber_4 to set
	 */
	public void setTokenNumber_4(String tokenNumber_4) {
		this.tokenNumber_4 = tokenNumber_4;
	}
	/**
	 * @return the tokenNumber_5
	 */
	public String getTokenNumber_5() {
		return tokenNumber_5;
	}
	/**
	 * @param tokenNumber_5 the tokenNumber_5 to set
	 */
	public void setTokenNumber_5(String tokenNumber_5) {
		this.tokenNumber_5 = tokenNumber_5;
	}
	/**
	 * @return the tokenNumber_6
	 */
	public String getTokenNumber_6() {
		return tokenNumber_6;
	}
	/**
	 * @param tokenNumber_6 the tokenNumber_6 to set
	 */
	public void setTokenNumber_6(String tokenNumber_6) {
		this.tokenNumber_6 = tokenNumber_6;
	}
	/**
	 * @return the tokenNumber_7
	 */
	public String getTokenNumber_7() {
		return tokenNumber_7;
	}
	/**
	 * @param tokenNumber_7 the tokenNumber_7 to set
	 */
	public void setTokenNumber_7(String tokenNumber_7) {
		this.tokenNumber_7 = tokenNumber_7;
	}
	/**
	 * @return the tokenNumber_8
	 */
	public String getTokenNumber_8() {
		return tokenNumber_8;
	}
	/**
	 * @param tokenNumber_8 the tokenNumber_8 to set
	 */
	public void setTokenNumber_8(String tokenNumber_8) {
		this.tokenNumber_8 = tokenNumber_8;
	}
	/**
	 * @return the tokenNumber_9
	 */
	public String getTokenNumber_9() {
		return tokenNumber_9;
	}
	/**
	 * @param tokenNumber_9 the tokenNumber_9 to set
	 */
	public void setTokenNumber_9(String tokenNumber_9) {
		this.tokenNumber_9 = tokenNumber_9;
	}
	/**
	 * @return the tokenNumber_10
	 */
	public String getTokenNumber_10() {
		return tokenNumber_10;
	}
	/**
	 * @param tokenNumber_10 the tokenNumber_10 to set
	 */
	public void setTokenNumber_10(String tokenNumber_10) {
		this.tokenNumber_10 = tokenNumber_10;
	}


	/**
	 * @return the dropTokensTemplate
	 */
	public String getDropTokensTemplate() {
		return dropTokensTemplate;
	}


	/**
	 * @param dropTokensTemplate the dropTokensTemplate to set
	 */
	public void setDropTokensTemplate(String dropTokensTemplate) {
		this.dropTokensTemplate = dropTokensTemplate;
	}
	
	
	public void metodoParaHacerMasPeso4(){
		System.out.println("metodo dummy para hacer peso para harvest");
	}	
}
