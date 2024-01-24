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
public class MttoBEMDropAccount extends ContractAbstractMB{
	
	private Integer mttoType;
	private String bemnumber;
	private String fiscalFullName;
	private String legalRepresentativeFullName;   //	AttrConstants.LEGALREPRESENTATIVE_NAME_1
	private String comments;
	
	private String quantityAccounts;
	private String accountNumber_1;
	private String accountNumber_2;
	private String accountNumber_3;
	private String accountNumber_4;
	private String accountNumber_5;
	private String accountNumber_6;
	private String accountNumber_7;
	private String accountNumber_8;
	private String accountNumber_9;
	private String accountNumber_10;
	
    private String NAME_TEMPLATE ="nameTemplate";
    private String PATHTEMPLATE ="pathTemplate";
	
	private String dropAccountsTemplate;
	
	private PdfTemplateBindingContract pdfTemplateBinding;
	

	public MttoBEMDropAccount() {
		super();
		this.bemnumber 						= ApplicationConstants.EMPTY_STRING;
		this.legalRepresentativeFullName	= ApplicationConstants.EMPTY_STRING;
		this.comments 						= ApplicationConstants.EMPTY_STRING;
		this.fiscalFullName					= ApplicationConstants.EMPTY_STRING;		
		this.quantityAccounts 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_1 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_2 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_3 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_4 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_5 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_6 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_7 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_8 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_9 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_10 				= ApplicationConstants.EMPTY_STRING;
		this.setDropAccountsTemplate( ApplicationConstants.OPCION_SELECTED);
		this.mttoType = MttoType.BAJA_CUENTAS.getMttoTypeId();
		setStatusContract(statusBean.findById(new Integer(ApplicationConstants.DEFAULT_VERSION_CONTRACT)));
	}

	@Override
	public void setResetForm() {
		this.bemnumber 						= ApplicationConstants.EMPTY_STRING;
		this.legalRepresentativeFullName	= ApplicationConstants.EMPTY_STRING;
		this.comments 						= ApplicationConstants.EMPTY_STRING;
		this.fiscalFullName					= ApplicationConstants.EMPTY_STRING;		
		this.quantityAccounts 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_1 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_2 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_3 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_4 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_5 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_6 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_7 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_8 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_9 				= ApplicationConstants.EMPTY_STRING;
		this.accountNumber_10 				= ApplicationConstants.EMPTY_STRING;
		
		clearFields();
		setProduct(productBean.findById(new Integer(ProductType.MTTO_DROP_ACCOUNT.value()))); 
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
				AttrConstants.COMMENTS,AttrConstants.QTY_ACCOUNTS,AttrConstants.MTTO_ACCOUNT_NUMBER_1,
				AttrConstants.MTTO_ACCOUNT_NUMBER_2,AttrConstants.MTTO_ACCOUNT_NUMBER_3,AttrConstants.MTTO_ACCOUNT_NUMBER_4,
				AttrConstants.MTTO_ACCOUNT_NUMBER_5,AttrConstants.MTTO_ACCOUNT_NUMBER_6,AttrConstants.MTTO_ACCOUNT_NUMBER_7,
				AttrConstants.MTTO_ACCOUNT_NUMBER_8,AttrConstants.MTTO_ACCOUNT_NUMBER_9,AttrConstants.MTTO_ACCOUNT_NUMBER_10,
				AttrConstants.DROP_ACCOUNT_TEMPLATE_OPTION,
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
			this.setAccountNumber_1( map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_1) );
			this.setAccountNumber_2( map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_2) );
			this.setAccountNumber_3( map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_3) );
			this.setAccountNumber_4( map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_4) );
			this.setAccountNumber_5( map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_5) );
			this.setAccountNumber_6( map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_6) );
			this.setAccountNumber_7( map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_7) );
			this.setAccountNumber_8( map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_8) );
			this.setAccountNumber_9( map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_9) );
			this.setAccountNumber_10( map.get(AttrConstants.MTTO_ACCOUNT_NUMBER_10) );
			this.setDropAccountsTemplate( map.get(AttrConstants.DROP_ACCOUNT_TEMPLATE_OPTION));
			this.setComments( map.get(AttrConstants.COMMENTS));
			quantityAccounts=map.get(AttrConstants.QTY_ACCOUNTS);//gina
			
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
		setProduct(productBean.findById(new Integer(ProductType.MTTO_DROP_ACCOUNT.value())));
		
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
		addToList(list,AttrConstants.QTY_ACCOUNTS, getQuantityAccounts());
		addToList(list,AttrConstants.MTTO_ACCOUNT_NUMBER_1, getAccountNumber_1());
		addToList(list,AttrConstants.MTTO_ACCOUNT_NUMBER_2, (2>Integer.parseInt(getQuantityAccounts()))?"":getAccountNumber_2());
		addToList(list,AttrConstants.MTTO_ACCOUNT_NUMBER_3, (3>Integer.parseInt(getQuantityAccounts()))?"":getAccountNumber_3());
		addToList(list,AttrConstants.MTTO_ACCOUNT_NUMBER_4, (4>Integer.parseInt(getQuantityAccounts()))?"":getAccountNumber_4());
		addToList(list,AttrConstants.MTTO_ACCOUNT_NUMBER_5, (5>Integer.parseInt(getQuantityAccounts()))?"":getAccountNumber_5());
		addToList(list,AttrConstants.MTTO_ACCOUNT_NUMBER_6, (6>Integer.parseInt(getQuantityAccounts()))?"":getAccountNumber_6());
		addToList(list,AttrConstants.MTTO_ACCOUNT_NUMBER_7, (7>Integer.parseInt(getQuantityAccounts()))?"":getAccountNumber_7());
		addToList(list,AttrConstants.MTTO_ACCOUNT_NUMBER_8, (8>Integer.parseInt(getQuantityAccounts()))?"":getAccountNumber_8());
		addToList(list,AttrConstants.MTTO_ACCOUNT_NUMBER_9, (9>Integer.parseInt(getQuantityAccounts()))?"":getAccountNumber_9());
		addToList(list,AttrConstants.MTTO_ACCOUNT_NUMBER_10, (10>Integer.parseInt(getQuantityAccounts()))?"":getAccountNumber_10());
		
//		list.add(getContractAttribute(AttrConstants.MTTO_ACCOUNT_NUMBER_3, getAccountNumber_3()));
//		list.add(getContractAttribute(AttrConstants.MTTO_ACCOUNT_NUMBER_4, getAccountNumber_4()));
//		list.add(getContractAttribute(AttrConstants.MTTO_ACCOUNT_NUMBER_5, getAccountNumber_5()));
//		list.add(getContractAttribute(AttrConstants.MTTO_ACCOUNT_NUMBER_6, getAccountNumber_6()));
//		list.add(getContractAttribute(AttrConstants.MTTO_ACCOUNT_NUMBER_7, getAccountNumber_7()));
//		list.add(getContractAttribute(AttrConstants.MTTO_ACCOUNT_NUMBER_8, getAccountNumber_8()));
//		list.add(getContractAttribute(AttrConstants.MTTO_ACCOUNT_NUMBER_9, getAccountNumber_9()));
//		list.add(getContractAttribute(AttrConstants.MTTO_ACCOUNT_NUMBER_10, getAccountNumber_10()));
		addToList(list,AttrConstants.DROP_ACCOUNT_TEMPLATE_OPTION, getDropAccountsTemplate());
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
	       Integer flagTemplate = Integer.parseInt(request.getParameter("flagTemplate"));
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
		
		if(!validateAccounts() ){
			return "FAILED";
		}
		generalInfoErrorsList.clear();
		return saveCompleteMtto();
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean validateAccounts(){
		this.getGeneralInfoErrorsList().clear();
		
		if (this.getErrorsList() != null) {
			this.getErrorsList().clear();
		}
		ContractMessageErrors errors;
		
		
		if (accountNumber_1.length() < 10  ) {
			if(quantityAccounts.equals(ApplicationConstants.NUMBER_1) || quantityAccounts.equals(ApplicationConstants.NUMBER_2)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_3) || quantityAccounts.equals(ApplicationConstants.NUMBER_4)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_5) || quantityAccounts.equals(ApplicationConstants.NUMBER_6)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_7) || quantityAccounts.equals(ApplicationConstants.NUMBER_8)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_9) || quantityAccounts.equals(ApplicationConstants.NUMBER_10)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 1 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
				
			}
				
		} 
		
		if (accountNumber_2.length() < 10 ) {
			if( quantityAccounts.equals(ApplicationConstants.NUMBER_2)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_3) || quantityAccounts.equals(ApplicationConstants.NUMBER_4)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_5) || quantityAccounts.equals(ApplicationConstants.NUMBER_6)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_7) || quantityAccounts.equals(ApplicationConstants.NUMBER_8)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_9) || quantityAccounts.equals(ApplicationConstants.NUMBER_10)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 2 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
		} 
		
		if (accountNumber_3.length() < 10 ) {
			if(quantityAccounts.equals(ApplicationConstants.NUMBER_3) || quantityAccounts.equals(ApplicationConstants.NUMBER_4)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_5) || quantityAccounts.equals(ApplicationConstants.NUMBER_6)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_7) || quantityAccounts.equals(ApplicationConstants.NUMBER_8)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_9) || quantityAccounts.equals(ApplicationConstants.NUMBER_10)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 3 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
				
			}
			
		} 
		
		if (accountNumber_4.length() < 10 ) {
			if(quantityAccounts.equals(ApplicationConstants.NUMBER_4)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_5) || quantityAccounts.equals(ApplicationConstants.NUMBER_6)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_7) || quantityAccounts.equals(ApplicationConstants.NUMBER_8)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_9) || quantityAccounts.equals(ApplicationConstants.NUMBER_10)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 4 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
		} 
		
		if (accountNumber_5.length() < 10 ) {
			if( quantityAccounts.equals(ApplicationConstants.NUMBER_5) || quantityAccounts.equals(ApplicationConstants.NUMBER_6)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_7) || quantityAccounts.equals(ApplicationConstants.NUMBER_8)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_9) || quantityAccounts.equals(ApplicationConstants.NUMBER_10)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 5 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
			
		} 
		
		if (accountNumber_6.length() < 10 ) {
			if( quantityAccounts.equals(ApplicationConstants.NUMBER_6)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_7) || quantityAccounts.equals(ApplicationConstants.NUMBER_8)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_9) || quantityAccounts.equals(ApplicationConstants.NUMBER_10)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 6 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
			
		} 
		
		if (accountNumber_7.length() < 10 ) {
			if( quantityAccounts.equals(ApplicationConstants.NUMBER_7) || quantityAccounts.equals(ApplicationConstants.NUMBER_8)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_9) || quantityAccounts.equals(ApplicationConstants.NUMBER_10)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 7 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
			
		} 
		
		if (accountNumber_8.length() < 10 ) {
			if( quantityAccounts.equals(ApplicationConstants.NUMBER_8)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_9) || quantityAccounts.equals(ApplicationConstants.NUMBER_10)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 8 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
			
		} 
		
		if (accountNumber_9.length() < 10 ) {
			if( quantityAccounts.equals(ApplicationConstants.NUMBER_9) || quantityAccounts.equals(ApplicationConstants.NUMBER_10)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 9 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
			
		} 
		
		if (accountNumber_10.length() < 10 ) {
			if( quantityAccounts.equals(ApplicationConstants.NUMBER_10)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 10 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
			
		} 

		this.setErrorsList(this.getGeneralInfoErrorsList());

		if (this.getGeneralInfoErrorsList().isEmpty()) {
			return true;
		} else {
			return false;
		}
		
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
	 * @return the quantityAccounts
	 */
	public String getQuantityAccounts() {
		return quantityAccounts;
	}

	/**
	 * @param quantityAccounts the quantityAccounts to set
	 */
	public void setQuantityAccounts(String quantityAccounts) {
		this.quantityAccounts = quantityAccounts;
	}

	/**
	 * @return the accountNumber_1
	 */
	public String getAccountNumber_1() {
		return accountNumber_1;
	}

	/**
	 * @param accountNumber_1 the accountNumber_1 to set
	 */
	public void setAccountNumber_1(String accountNumber_1) {
		this.accountNumber_1 = accountNumber_1;
	}

	/**
	 * @return the accountNumber_2
	 */
	public String getAccountNumber_2() {
		return accountNumber_2;
	}

	/**
	 * @param accountNumber_2 the accountNumber_2 to set
	 */
	public void setAccountNumber_2(String accountNumber_2) {
		this.accountNumber_2 = accountNumber_2;
	}

	/**
	 * @return the accountNumber_3
	 */
	public String getAccountNumber_3() {
		return accountNumber_3;
	}

	/**
	 * @param accountNumber_3 the accountNumber_3 to set
	 */
	public void setAccountNumber_3(String accountNumber_3) {
		this.accountNumber_3 = accountNumber_3;
	}

	/**
	 * @return the accountNumber_4
	 */
	public String getAccountNumber_4() {
		return accountNumber_4;
	}

	/**
	 * @param accountNumber_4 the accountNumber_4 to set
	 */
	public void setAccountNumber_4(String accountNumber_4) {
		this.accountNumber_4 = accountNumber_4;
	}

	/**
	 * @return the accountNumber_5
	 */
	public String getAccountNumber_5() {
		return accountNumber_5;
	}

	/**
	 * @param accountNumber_5 the accountNumber_5 to set
	 */
	public void setAccountNumber_5(String accountNumber_5) {
		this.accountNumber_5 = accountNumber_5;
	}

	/**
	 * @return the accountNumber_6
	 */
	public String getAccountNumber_6() {
		return accountNumber_6;
	}

	/**
	 * @param accountNumber_6 the accountNumber_6 to set
	 */
	public void setAccountNumber_6(String accountNumber_6) {
		this.accountNumber_6 = accountNumber_6;
	}

	/**
	 * @return the accountNumber_7
	 */
	public String getAccountNumber_7() {
		return accountNumber_7;
	}

	/**
	 * @param accountNumber_7 the accountNumber_7 to set
	 */
	public void setAccountNumber_7(String accountNumber_7) {
		this.accountNumber_7 = accountNumber_7;
	}

	/**
	 * @return the accountNumber_8
	 */
	public String getAccountNumber_8() {
		return accountNumber_8;
	}

	/**
	 * @param accountNumber_8 the accountNumber_8 to set
	 */
	public void setAccountNumber_8(String accountNumber_8) {
		this.accountNumber_8 = accountNumber_8;
	}

	/**
	 * @return the accountNumber_9
	 */
	public String getAccountNumber_9() {
		return accountNumber_9;
	}

	/**
	 * @param accountNumber_9 the accountNumber_9 to set
	 */
	public void setAccountNumber_9(String accountNumber_9) {
		this.accountNumber_9 = accountNumber_9;
	}

	/**
	 * @return the accountNumber_10
	 */
	public String getAccountNumber_10() {
		return accountNumber_10;
	}

	/**
	 * @param accountNumber_10 the accountNumber_10 to set
	 */
	public void setAccountNumber_10(String accountNumber_10) {
		this.accountNumber_10 = accountNumber_10;
	}

	/**
	 * @return the dropAccountsTemplate
	 */
	public String getDropAccountsTemplate() {
		return dropAccountsTemplate;
	}

	/**
	 * @param dropAccountsTemplate the dropAccountsTemplate to set
	 */
	public void setDropAccountsTemplate(String dropAccountsTemplate) {
		this.dropAccountsTemplate = dropAccountsTemplate;
	}
	
	
	
	public void metodoParaHacerMasPeso6(){
		System.out.println("metodo dummy para hacer peso para harvest");
	}
}
