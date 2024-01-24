/**
 * 
 */
package com.banorte.contract.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import com.banorte.contract.model.Cities;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.ContractMessageErrors;
import com.banorte.contract.model.States;
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
public class MttoBEMUpdateAgreement extends ContractAbstractMB{
	
	private Integer mttoType;
	private String bemnumber;
	
	// DATOS EMPRESA 
	private String fiscalFullName;
	private String legalRepresentativeFullName;   //	AttrConstants.LEGALREPRESENTATIVE_NAME_1
	// ************
	
	//private String changeClientInfoCheck;
	private String changeFiscalNameCheck;
	private String changeLegalRepCheck;
	private String changeAddressCheck;
	private String changeRFCCheck;
	private String changeColonyCheck;
	private String changeCPCheck;
	private String changeStateCheck;
	//private String changeCityCheck;
	private String changePhoneNumberCheck;
	private String changeEmailCheck;
	
	private String changeFiscalNameInfo;
	private String changeLegalRepInfo;
	private String changeAddressInfo;
	private String changeRFCInfo;
	private String changeColonyInfo;
	private String changeCPCInfo;
	private String changeStateInfo;
	private String changeCityInfo;
	private String changePhoneNumberInfo;
	private String changeEmailInfo;
	
	
	//private String changeContractCheck;
	private String changeAccountChargeCheck;
	private String changePlanCheck;
	private String changeChargeCheck;
	private String changeAccountCentralCheck;
	private String changeOriginTransactionCheck;
	
	private String changeAccountChargeInfo;
	private String changePlanInfo;
	
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private String optionE;
	private String optionF;
	private String optionG;
	private String optionH;
	private String optionI;
	private String optionJ;
	private String optionK;
	private String optionL;
	private String optionM;
	private String quantityAttr;
	private String cityState;
	private String optionCuentaCentralizada;
	private String optionOrigenTransaccion;
	private String placeDateConvenio;

    private String NAME_TEMPLATE ="nameTemplate";
    private String PATHTEMPLATE ="pathTemplate";
	
	private PdfTemplateBindingContract pdfTemplateBinding;
	
	private SelectItem[] statesChangeArray;
	private SelectItem[] citiesChangeArray;
	
	
	

	/**
	 * 
	 */
	public MttoBEMUpdateAgreement() {
		super();
		this.mttoType = MttoType.CONVENIO_MODIFICATORIO.getMttoTypeId();
		setStatusContract(statusBean.findById(new Integer(ApplicationConstants.DEFAULT_VERSION_CONTRACT)));
	}

	@Override
	public void setResetForm() {
		this.bemnumber 						= ApplicationConstants.EMPTY_STRING;
		this.fiscalFullName					= ApplicationConstants.EMPTY_STRING;
		this.legalRepresentativeFullName	= ApplicationConstants.EMPTY_STRING;
		this.changeFiscalNameCheck 			= ApplicationConstants.EMPTY_STRING;
		this.changeLegalRepCheck 			= ApplicationConstants.EMPTY_STRING;
		this.changeAddressCheck 			= ApplicationConstants.EMPTY_STRING;
		this.changeRFCCheck 				= ApplicationConstants.EMPTY_STRING;
		this.changeColonyCheck				= ApplicationConstants.EMPTY_STRING;
		this.changeCPCheck					= ApplicationConstants.EMPTY_STRING;
		this.changeStateCheck 				= ApplicationConstants.EMPTY_STRING;
		this.changePhoneNumberCheck 		= ApplicationConstants.EMPTY_STRING;
		this.changeEmailCheck 				= ApplicationConstants.EMPTY_STRING;
		this.changeFiscalNameInfo 			= ApplicationConstants.EMPTY_STRING;
		this.changeLegalRepInfo 			= ApplicationConstants.EMPTY_STRING;
		this.changeAddressInfo 				= ApplicationConstants.EMPTY_STRING;
		this.changeRFCInfo 					= ApplicationConstants.EMPTY_STRING;
		this.changeColonyInfo 				= ApplicationConstants.EMPTY_STRING;
		this.changeCPCInfo 					= ApplicationConstants.EMPTY_STRING;
		this.changeStateInfo 				= ApplicationConstants.EMPTY_STRING;
		this.changeCityInfo 				= ApplicationConstants.EMPTY_STRING;
		this.changePhoneNumberInfo 			= ApplicationConstants.EMPTY_STRING;
		this.changeEmailInfo 				= ApplicationConstants.EMPTY_STRING;
		this.changeAccountChargeCheck 		= ApplicationConstants.EMPTY_STRING;
		this.changePlanCheck 				= ApplicationConstants.EMPTY_STRING;
		this.changeChargeCheck 				= ApplicationConstants.EMPTY_STRING;
		this.changeAccountCentralCheck 		= ApplicationConstants.EMPTY_STRING;
		this.changeOriginTransactionCheck 	= ApplicationConstants.EMPTY_STRING;
		this.changeAccountChargeInfo 		= ApplicationConstants.EMPTY_STRING;
		this.changePlanInfo 				= ApplicationConstants.EMPTY_STRING;
		this.optionA						= ApplicationConstants.EMPTY_STRING;
		this.optionB						= ApplicationConstants.EMPTY_STRING;
		this.optionC						= ApplicationConstants.EMPTY_STRING;
		this.optionD						= ApplicationConstants.EMPTY_STRING;
		this.optionE						= ApplicationConstants.EMPTY_STRING;
		this.optionF						= ApplicationConstants.EMPTY_STRING;
		this.optionG						= ApplicationConstants.EMPTY_STRING;
		this.optionH						= ApplicationConstants.EMPTY_STRING;
		this.optionI						= ApplicationConstants.EMPTY_STRING;
		this.optionJ						= ApplicationConstants.EMPTY_STRING;
		this.optionK						= ApplicationConstants.EMPTY_STRING;
		this.optionL						= ApplicationConstants.EMPTY_STRING;
		this.optionM						= ApplicationConstants.EMPTY_STRING;
		this.quantityAttr					= ApplicationConstants.EMPTY_STRING;
		this.cityState					= ApplicationConstants.EMPTY_STRING;
		
		clearFields();
		setProduct(productBean.findById(new Integer(ProductType.MTTO_CONVENIO_MODIFICATORIO.value()))); 
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
		
		String FILLS[] = { AttrConstants.BEM_NUMBER,AttrConstants.CR_NUMBER, AttrConstants.CLIENT_SIC,
				AttrConstants.LEGALREPRESENTATIVE_NAME_1,AttrConstants.CLIENT_FISCALNAME,
				AttrConstants.BEM_STATE,AttrConstants.BEM_POPULATION,
				AttrConstants.CHANGE_CLIENT_CHECK,AttrConstants.CHANGE_FISCAL_NAME_CHECK,
				AttrConstants.CHANGE_ADDRESS_CHECK,AttrConstants.CHANGE_RFC_CHECK,AttrConstants.CHANGE_COLONY_CHECK,
				AttrConstants.CHANGE_CP_CHECK,AttrConstants.CHANGE_STATE_CHECK,
				AttrConstants.CHANGE_CITY_CHECK,AttrConstants.CHANGE_PHONE_NUMBER_CHECK,AttrConstants.CHANGE_EMAIL_CHECK,
				AttrConstants.CHANGE_FISCAL_NAME_INFO,AttrConstants.CHANGE_ADDRESS_INFO,AttrConstants.CHANGE_RFC_INFO,
				AttrConstants.CHANGE_COLONY_INFO,AttrConstants.CHANGE_CP_INFO,AttrConstants.CHANGE_STATE_INFO,
				AttrConstants.CHANGE_CITY_INFO,AttrConstants.CHANGE_PHONE_INFO,AttrConstants.CHANGE_EMAIL_INFO,
				AttrConstants.CHANGE_CONTRACT_CHECK,AttrConstants.CHANGE_ACCOUNT_CHARGE_CHECK,AttrConstants.CHANGE_PLAN_CHECK,
				AttrConstants.CHANGE_CHARGE_CHECK,AttrConstants.CHANGE_ACCOUNT_CENTRAL_CHECK,AttrConstants.CHANGE_ORIGIN_TRANSACTION_CHECK,
				AttrConstants.CHANGE_ACCOUNT_CHARGE_INFO,AttrConstants.CHANGE_PLAN_INFO,
				AttrConstants.OFFICER_REP_FIRMNUMBER_1,AttrConstants.OFFICER_EBANKING_FIRMNUMBER};
		
		
		Contract contract_ = contractBean.findById(idContract);
		if (contract_.getContractAttributeCollection() != null) {
			Map<String, String> map = this.getContractAttributeFills(contract_,FILLS);

			this.setBemnumber( map.get(AttrConstants.BEM_NUMBER) );
			this.setCrnumber( map.get(AttrConstants.CR_NUMBER) );
			this.setClient_sic( map.get(AttrConstants.CLIENT_SIC));

			this.setCelebrationplace( map.get(AttrConstants.BEM_POPULATION) );
			this.setCelebrationstate( map.get(AttrConstants.BEM_STATE) );
			this.setLegalRepresentativeFullName( map.get(AttrConstants.LEGALREPRESENTATIVE_NAME_1));
			this.setFiscalFullName( map.get(AttrConstants.CLIENT_FISCALNAME) );
			this.setChangeFiscalNameCheck(map.get(AttrConstants.CHANGE_FISCAL_NAME_CHECK));
			this.setChangeLegalRepCheck( map.get(AttrConstants.CHANGE_LEGAL_REP_CHECK));
			this.setChangeAddressCheck(map.get(AttrConstants.CHANGE_ADDRESS_CHECK));
			this.setChangeRFCCheck(map.get(AttrConstants.CHANGE_RFC_CHECK));
			this.setChangeColonyCheck(map.get(AttrConstants.CHANGE_COLONY_CHECK));
			this.setChangeCPCheck(map.get(AttrConstants.CHANGE_CP_CHECK));
			this.setChangeStateCheck(map.get(AttrConstants.CHANGE_STATE_CHECK));
			this.setChangePhoneNumberCheck( map.get(AttrConstants.CHANGE_PHONE_NUMBER_CHECK));
			this.setChangeEmailCheck( map.get(AttrConstants.CHANGE_EMAIL_CHECK));
			this.setChangeFiscalNameInfo( map.get(AttrConstants.CHANGE_FISCAL_NAME_INFO));
			this.setChangeLegalRepInfo( map.get(AttrConstants.CHANGE_LEGAL_REP_INFO) );
			this.setChangeAddressInfo( map.get(AttrConstants.CHANGE_ADDRESS_INFO));
			this.setChangeRFCInfo( map.get(AttrConstants.CHANGE_RFC_INFO));
			this.setChangeColonyInfo( map.get(AttrConstants.CHANGE_COLONY_INFO));
			this.setChangeCPCInfo( map.get(AttrConstants.CHANGE_CP_INFO));
			this.setChangeStateInfo( map.get(AttrConstants.CHANGE_STATE_INFO));
			this.setChangeCityInfo( map.get(AttrConstants.CHANGE_CITY_INFO));
			this.setChangePhoneNumberInfo( map.get(AttrConstants.CHANGE_PHONE_INFO));
			this.setChangeEmailInfo(map.get(AttrConstants.CHANGE_EMAIL_INFO));
			this.setChangeAccountChargeCheck(map.get(AttrConstants.CHANGE_ACCOUNT_CHARGE_CHECK));
			this.setChangePlanCheck(map.get(AttrConstants.CHANGE_PLAN_CHECK));
			this.setChangeChargeCheck(map.get(AttrConstants.CHANGE_CHARGE_CHECK));
			this.setChangeAccountCentralCheck(map.get(AttrConstants.CHANGE_ACCOUNT_CENTRAL_CHECK));
			this.setChangeOriginTransactionCheck(map.get(AttrConstants.CHANGE_ORIGIN_TRANSACTION_CHECK));
			this.setChangeAccountChargeInfo(map.get(AttrConstants.CHANGE_ACCOUNT_CHARGE_INFO));
			this.setChangePlanInfo(map.get(AttrConstants.CHANGE_PLAN_INFO));
			
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
		setProduct(productBean.findById(new Integer(ProductType.MTTO_CONVENIO_MODIFICATORIO.value())));
	}

	@Override
	public boolean savePartialContract() {
		Contract contract = getContract();
		ArrayList<ContractAttribute> list = new ArrayList<ContractAttribute>();

		// Mantenimientos Baja de Cuentas
		addToList(list,AttrConstants.MTTO_TYPE, this.mttoType.toString());
		addToList(list,AttrConstants.CELEBRATION_DATE, getCelebrationdate());
		addToList(list,AttrConstants.CLIENT_SIC, getClient_sic());
		addToList(list,AttrConstants.BEM_NUMBER, getBemnumber());
		addToList(list,AttrConstants.CR_NUMBER, getCrnumber());
		addToList(list,AttrConstants.BEM_STATE, getCelebrationstate());
		addToList(list,AttrConstants.BEM_POPULATION, getCelebrationplace());
		addToList(list,AttrConstants.OPERATION_COMMENTS, this.getComments().length()<250?this.getComments():this.getComments().substring(0, 249));
		
		addToList(list,AttrConstants.CHANGE_FISCAL_NAME_CHECK, getChangeFiscalNameCheck());
		addToList(list,AttrConstants.CHANGE_LEGAL_REP_CHECK, getChangeLegalRepCheck());
		addToList(list,AttrConstants.CHANGE_ADDRESS_CHECK, getChangeAddressCheck());
		addToList(list,AttrConstants.CHANGE_RFC_CHECK, getChangeRFCCheck());
		addToList(list,AttrConstants.CHANGE_COLONY_CHECK, getChangeColonyCheck());
		addToList(list,AttrConstants.CHANGE_CP_CHECK, getChangeCPCheck());
		addToList(list,AttrConstants.CHANGE_STATE_CHECK, getChangeStateCheck());
		addToList(list,AttrConstants.CHANGE_PHONE_NUMBER_CHECK, getChangePhoneNumberCheck());
		addToList(list,AttrConstants.CHANGE_EMAIL_CHECK, getChangeEmailCheck());
		
		addToList(list,AttrConstants.CHANGE_FISCAL_NAME_INFO, getChangeFiscalNameInfo());
		addToList(list,AttrConstants.CHANGE_LEGAL_REP_INFO, getChangeLegalRepInfo());
		addToList(list,AttrConstants.CHANGE_ADDRESS_INFO, getChangeAddressInfo());
		addToList(list,AttrConstants.CHANGE_RFC_INFO, getChangeRFCInfo());
		addToList(list,AttrConstants.CHANGE_COLONY_INFO, getChangeColonyInfo());
		addToList(list,AttrConstants.CHANGE_CP_INFO, getChangeCPCInfo());
		addToList(list,AttrConstants.CHANGE_STATE_INFO, getChangeStateInfo());
		addToList(list,AttrConstants.CHANGE_CITY_INFO, getChangeCityInfo());
		addToList(list,AttrConstants.CHANGE_PHONE_INFO, getChangePhoneNumberInfo());
		addToList(list,AttrConstants.CHANGE_EMAIL_INFO, getChangeEmailInfo());
		
		addToList(list,AttrConstants.CHANGE_ACCOUNT_CHARGE_CHECK, getChangeAccountChargeCheck());
		addToList(list,AttrConstants.CHANGE_PLAN_CHECK, getChangePlanCheck());
		addToList(list,AttrConstants.CHANGE_CHARGE_CHECK, getChangeChargeCheck());
		addToList(list,AttrConstants.CHANGE_ACCOUNT_CENTRAL_CHECK, getChangeAccountCentralCheck());
		addToList(list,AttrConstants.CHANGE_ORIGIN_TRANSACTION_CHECK, getChangeOriginTransactionCheck());
		
		addToList(list,AttrConstants.CHANGE_ACCOUNT_CHARGE_INFO, getChangeAccountChargeInfo());
		addToList(list,AttrConstants.CHANGE_PLAN_INFO, getChangePlanInfo());
		
		addToList(list,AttrConstants.LEGALREPRESENTATIVE_NAME_1,getLegalRepresentativeFullName());
		addToList(list,AttrConstants.CLIENT_FISCALNAME, getFiscalFullName());
		
		addToList(list,AttrConstants.OPTION_A, getOptionA());
		addToList(list,AttrConstants.OPTION_B, getOptionB());
		addToList(list,AttrConstants.OPTION_C, getOptionC());
		addToList(list,AttrConstants.OPTION_D, getOptionD());
		addToList(list,AttrConstants.OPTION_E, getOptionE());
		addToList(list,AttrConstants.OPTION_F, getOptionF());
		addToList(list,AttrConstants.OPTION_G, getOptionG());
		addToList(list,AttrConstants.OPTION_H, getOptionH());
		addToList(list,AttrConstants.OPTION_I, getOptionI());
		addToList(list,AttrConstants.OPTION_J, getOptionJ());
		addToList(list,AttrConstants.OPTION_K, getOptionK());
		addToList(list,AttrConstants.OPTION_L, getOptionL());
		addToList(list,AttrConstants.OPTION_M, getOptionM());
		addToList(list,AttrConstants.QUANTITY_ATTR, getQuantityAttr());
		addToList(list,AttrConstants.CITY_STATE, getChangeCityInfo() + "," + getChangeStateInfo());
		addToList(list,AttrConstants.PLACE_DATE_CONVENIO,getCelebrationplace() + "," + getCelebrationstate()+ " - " + getCelebrationdate());
		addToList(list,AttrConstants.OPTION_ACOUNT_CENTRAL, getOptionCuentaCentralizada());
		addToList(list,AttrConstants.OPTION_ORIGIN_TRANS, getOptionOrigenTransaccion());
			
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
//		Validacion Apoderado Legal o Administrador Designado
		if(! validateLegalRepresentativeFullName()){
			return "FAILED";
		}
		
		this.setValidationEDO(Boolean.TRUE);
		this.setValidationMtto( Boolean.FALSE);  				// para no validar los 2 representantes de Banorte
		this.setSelectedOptions();
		this.setEmptyInfo();
		generalInfoErrorsList.clear();
		return saveCompleteMtto();
	}
	
	
	//Validacion Legal Representative Full Name
	private boolean validateLegalRepresentativeFullName(){
		this.getGeneralInfoErrorsList().clear();
		ContractMessageErrors errors;
		boolean result 										= Boolean.TRUE;
		ArrayList<ContractMessageErrors> errorsList 		= new ArrayList();
		
//		System.out.println("Entro a la validacion de validateLegalRepresentativeFullName" + getLegalRepresentativeFullName());
		
		if (getLegalRepresentativeFullName() == null
				|| getLegalRepresentativeFullName().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos de la Empresa - Apoderado Legal o Administrador Designado.");
			errorsList.add(errors);
			this.setErrorsList(errorsList);
			result = Boolean.FALSE;
		}
		
		return result;
	}
	
	
	private void setEmptyInfo(){
		
	
		if(!getChangeFiscalNameCheck().equals(ApplicationConstants.VALUE_TRUE)){
			this.setChangeFiscalNameInfo(ApplicationConstants.EMPTY_STRING);
		}
		
		/*if(!getChangeLegalRepCheck().equals(ApplicationConstants.VALUE_TRUE)){
			this.setChangeLegalRepInfo(ApplicationConstants.EMPTY_STRING);
		}*/
		
		if(!getChangeAddressCheck().equals(ApplicationConstants.VALUE_TRUE)){
			this.setChangeAddressInfo(ApplicationConstants.EMPTY_STRING);
		}
		
		if(!getChangeRFCCheck().equals(ApplicationConstants.VALUE_TRUE)){
			this.setChangeRFCInfo(ApplicationConstants.EMPTY_STRING);
		}
		
		if(!getChangeColonyCheck().equals(ApplicationConstants.VALUE_TRUE)){
			this.setChangeColonyInfo(ApplicationConstants.EMPTY_STRING);
		}
		
		if(!getChangeCPCheck().equals(ApplicationConstants.VALUE_TRUE)){
			this.setChangeCPCInfo(ApplicationConstants.EMPTY_STRING);
		}
		
		if(!getChangeStateCheck().equals(ApplicationConstants.VALUE_TRUE)){
			this.setChangeStateInfo(ApplicationConstants.EMPTY_STRING);
		}
		
		if(!getChangePhoneNumberCheck().equals(ApplicationConstants.VALUE_TRUE)){
			this.setChangePhoneNumberInfo(ApplicationConstants.EMPTY_STRING);
		}
		
		if(!getChangeEmailCheck().equals(ApplicationConstants.VALUE_TRUE)){
			this.setChangeEmailInfo(ApplicationConstants.EMPTY_STRING);
		}

		if(!getChangeAccountChargeCheck().equals(ApplicationConstants.VALUE_TRUE)){
			this.setChangeAccountChargeInfo(ApplicationConstants.EMPTY_STRING);
		}
		
		if(!getChangePlanCheck().equals(ApplicationConstants.VALUE_TRUE)){
			this.setChangePlanInfo(ApplicationConstants.EMPTY_STRING);
		}
		
		if(!getChangeChargeCheck().equals(ApplicationConstants.VALUE_TRUE)){
			this.setChangeAccountCentralCheck(ApplicationConstants.VALUE_FALSE);
			this.setChangeOriginTransactionCheck(ApplicationConstants.VALUE_FALSE);
		}
	}
	
	
	private void setSelectedOptions(){
		int countAttr = 0;
		
		if(getChangeFiscalNameCheck().equals(ApplicationConstants.VALUE_TRUE)){
			setOptionA(ApplicationConstants.OPCION_SELECTED);
			countAttr +=1;
		}else {
			setOptionA(ApplicationConstants.EMPTY_STRING);
		}
		
		/*if(getChangeLegalRepCheck().equals(ApplicationConstants.VALUE_TRUE)){
			setOptionB(ApplicationConstants.OPCION_SELECTED);
			countAttr +=1;
		}*/
		
		if(getChangeAddressCheck().equals(ApplicationConstants.VALUE_TRUE)){
			setOptionC(ApplicationConstants.OPCION_SELECTED);
			countAttr +=1;
		}else {
			setOptionC(ApplicationConstants.EMPTY_STRING);
		}
		
		if(getChangeRFCCheck().equals(ApplicationConstants.VALUE_TRUE)){
			setOptionD(ApplicationConstants.OPCION_SELECTED);
			countAttr +=1;
		}else {
			setOptionD(ApplicationConstants.EMPTY_STRING);
		}
		
		if(getChangeColonyCheck().equals(ApplicationConstants.VALUE_TRUE)){
			setOptionE(ApplicationConstants.OPCION_SELECTED);
			countAttr +=1;
		}else {
			setOptionE(ApplicationConstants.EMPTY_STRING);
		}
		
		if(getChangeCPCheck().equals(ApplicationConstants.VALUE_TRUE)){
			setOptionF(ApplicationConstants.OPCION_SELECTED);
			countAttr +=1;
		}else {
			setOptionF(ApplicationConstants.EMPTY_STRING);
		}
		
		if(getChangeStateCheck().equals(ApplicationConstants.VALUE_TRUE)){
			setOptionG(ApplicationConstants.OPCION_SELECTED);
			countAttr +=1;
		}else {
			setOptionG(ApplicationConstants.EMPTY_STRING);
		}
		
		if(getChangePhoneNumberCheck().equals(ApplicationConstants.VALUE_TRUE)){
			setOptionH(ApplicationConstants.OPCION_SELECTED);
			countAttr +=1;
		}else {
			setOptionH(ApplicationConstants.EMPTY_STRING);
		}
		
		if(getChangeEmailCheck().equals(ApplicationConstants.VALUE_TRUE)){
			setOptionI(ApplicationConstants.OPCION_SELECTED);
			countAttr +=1;
		}else {
			setOptionI(ApplicationConstants.EMPTY_STRING);
		}
		
		if(getChangeAccountChargeCheck().equals(ApplicationConstants.VALUE_TRUE)){
			setOptionK(ApplicationConstants.OPCION_SELECTED);
			countAttr +=1;
		}else {
			setOptionK(ApplicationConstants.EMPTY_STRING);
		}
		
		if(getChangePlanCheck().equals(ApplicationConstants.VALUE_TRUE)){
			setOptionL(ApplicationConstants.OPCION_SELECTED);
			countAttr +=1;
		}else {
			setOptionL(ApplicationConstants.EMPTY_STRING);
		}
		
		
		if(getChangeChargeCheck().equals(ApplicationConstants.VALUE_TRUE)){
			countAttr +=1;
			setOptionM(ApplicationConstants.OPCION_SELECTED);
			
			if(getChangeAccountCentralCheck().equals(ApplicationConstants.VALUE_TRUE)){
				setOptionCuentaCentralizada(ApplicationConstants.OPCION_SELECTED);
			}else {
				setOptionCuentaCentralizada(ApplicationConstants.EMPTY_STRING);
			}
			
			if(getChangeOriginTransactionCheck().equals(ApplicationConstants.VALUE_TRUE)){
				setOptionOrigenTransaccion(ApplicationConstants.OPCION_SELECTED);
			}else {
				setOptionOrigenTransaccion(ApplicationConstants.EMPTY_STRING);
			}
		}

		this.setQuantityAttr(String.valueOf( countAttr ) );
	
	}
	
	
	
	
	
	// GETTER'S AND SETTER'S

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
	 * @return the changeFiscalNameCheck
	 */
	public String getChangeFiscalNameCheck() {
		return changeFiscalNameCheck;
	}

	/**
	 * @param changeFiscalNameCheck the changeFiscalNameCheck to set
	 */
	public void setChangeFiscalNameCheck(String changeFiscalNameCheck) {
		this.changeFiscalNameCheck = changeFiscalNameCheck;
	}

	/**
	 * @return the changeAddressCheck
	 */
	public String getChangeAddressCheck() {
		return changeAddressCheck;
	}

	/**
	 * @param changeAddressCheck the changeAddressCheck to set
	 */
	public void setChangeAddressCheck(String changeAddressCheck) {
		this.changeAddressCheck = changeAddressCheck;
	}

	/**
	 * @return the changeRFCCheck
	 */
	public String getChangeRFCCheck() {
		return changeRFCCheck;
	}

	/**
	 * @param changeRFCCheck the changeRFCCheck to set
	 */
	public void setChangeRFCCheck(String changeRFCCheck) {
		this.changeRFCCheck = changeRFCCheck;
	}

	/**
	 * @return the changeColonyCheck
	 */
	public String getChangeColonyCheck() {
		return changeColonyCheck;
	}

	/**
	 * @param changeColonyCheck the changeColonyCheck to set
	 */
	public void setChangeColonyCheck(String changeColonyCheck) {
		this.changeColonyCheck = changeColonyCheck;
	}

	/**
	 * @return the changeCPCheck
	 */
	public String getChangeCPCheck() {
		return changeCPCheck;
	}

	/**
	 * @param changeCPCheck the changeCPCheck to set
	 */
	public void setChangeCPCheck(String changeCPCheck) {
		this.changeCPCheck = changeCPCheck;
	}

	/**
	 * @return the changeStateCheck
	 */
	public String getChangeStateCheck() {
		return changeStateCheck;
	}

	/**
	 * @param changeStateCheck the changeStateCheck to set
	 */
	public void setChangeStateCheck(String changeStateCheck) {
		this.changeStateCheck = changeStateCheck;
	}


	/**
	 * @return the changePhoneNumberCheck
	 */
	public String getChangePhoneNumberCheck() {
		return changePhoneNumberCheck;
	}

	/**
	 * @param changePhoneNumberCheck the changePhoneNumberCheck to set
	 */
	public void setChangePhoneNumberCheck(String changePhoneNumberCheck) {
		this.changePhoneNumberCheck = changePhoneNumberCheck;
	}

	/**
	 * @return the changeEmailCheck
	 */
	public String getChangeEmailCheck() {
		return changeEmailCheck;
	}

	/**
	 * @param changeEmailCheck the changeEmailCheck to set
	 */
	public void setChangeEmailCheck(String changeEmailCheck) {
		this.changeEmailCheck = changeEmailCheck;
	}

	/**
	 * @return the changeFiscalNameInfo
	 */
	public String getChangeFiscalNameInfo() {
		return changeFiscalNameInfo;
	}

	/**
	 * @param changeFiscalNameInfo the changeFiscalNameInfo to set
	 */
	public void setChangeFiscalNameInfo(String changeFiscalNameInfo) {
		this.changeFiscalNameInfo = changeFiscalNameInfo;
	}

	/**
	 * @return the changeAddressInfo
	 */
	public String getChangeAddressInfo() {
		return changeAddressInfo;
	}

	/**
	 * @param changeAddressInfo the changeAddressInfo to set
	 */
	public void setChangeAddressInfo(String changeAddressInfo) {
		this.changeAddressInfo = changeAddressInfo;
	}

	/**
	 * @return the changeRFCInfo
	 */
	public String getChangeRFCInfo() {
		return changeRFCInfo;
	}

	/**
	 * @param changeRFCInfo the changeRFCInfo to set
	 */
	public void setChangeRFCInfo(String changeRFCInfo) {
		this.changeRFCInfo = changeRFCInfo;
	}

	/**
	 * @return the changeColonyInfo
	 */
	public String getChangeColonyInfo() {
		return changeColonyInfo;
	}

	/**
	 * @param changeColonyInfo the changeColonyInfo to set
	 */
	public void setChangeColonyInfo(String changeColonyInfo) {
		this.changeColonyInfo = changeColonyInfo;
	}

	/**
	 * @return the changeCPCInfo
	 */
	public String getChangeCPCInfo() {
		return changeCPCInfo;
	}

	/**
	 * @param changeCPCInfo the changeCPCInfo to set
	 */
	public void setChangeCPCInfo(String changeCPCInfo) {
		this.changeCPCInfo = changeCPCInfo;
	}

	/**
	 * @return the changeStateInfo
	 */
	public String getChangeStateInfo() {
		if(this.changeStateInfo == null){
			return "";
		}
		return changeStateInfo;
	}

	/**
	 * @param changeStateInfo the changeStateInfo to set
	 */
	public void setChangeStateInfo(String changeStateInfo) {
		this.changeStateInfo = changeStateInfo;
	}

	/**
	 * @return the changeCityInfo
	 */
	public String getChangeCityInfo() {
		if(this.changeCityInfo == null){
			return "";
		}
		return changeCityInfo;
	}

	/**
	 * @param changeCityInfo the changeCityInfo to set
	 */
	public void setChangeCityInfo(String changeCityInfo) {
		this.changeCityInfo = changeCityInfo;
	}

	/**
	 * @return the changePhoneNumberInfo
	 */
	public String getChangePhoneNumberInfo() {
		return changePhoneNumberInfo;
	}

	/**
	 * @param changePhoneNumberInfo the changePhoneNumberInfo to set
	 */
	public void setChangePhoneNumberInfo(String changePhoneNumberInfo) {
		this.changePhoneNumberInfo = changePhoneNumberInfo;
	}

	/**
	 * @return the changeEmailInfo
	 */
	public String getChangeEmailInfo() {
		return changeEmailInfo;
	}

	/**
	 * @param changeEmailInfo the changeEmailInfo to set
	 */
	public void setChangeEmailInfo(String changeEmailInfo) {
		this.changeEmailInfo = changeEmailInfo;
	}

	/**
	 * @return the changeAccountChargeCheck
	 */
	public String getChangeAccountChargeCheck() {
		return changeAccountChargeCheck;
	}

	/**
	 * @param changeAccountChargeCheck the changeAccountChargeCheck to set
	 */
	public void setChangeAccountChargeCheck(String changeAccountChargeCheck) {
		this.changeAccountChargeCheck = changeAccountChargeCheck;
	}

	/**
	 * @return the changePlanCheck
	 */
	public String getChangePlanCheck() {
		return changePlanCheck;
	}

	/**
	 * @param changePlanCheck the changePlanCheck to set
	 */
	public void setChangePlanCheck(String changePlanCheck) {
		this.changePlanCheck = changePlanCheck;
	}

	/**
	 * @return the changeChargeCheck
	 */
	public String getChangeChargeCheck() {
		return changeChargeCheck;
	}

	/**
	 * @param changeChargeCheck the changeChargeCheck to set
	 */
	public void setChangeChargeCheck(String changeChargeCheck) {
		this.changeChargeCheck = changeChargeCheck;
	}

	/**
	 * @return the changeAccountCentralCheck
	 */
	public String getChangeAccountCentralCheck() {
		return changeAccountCentralCheck;
	}

	/**
	 * @param changeAccountCentralCheck the changeAccountCentralCheck to set
	 */
	public void setChangeAccountCentralCheck(String changeAccountCentralCheck) {
		this.changeAccountCentralCheck = changeAccountCentralCheck;
	}

	/**
	 * @return the changeOriginTransactionCheck
	 */
	public String getChangeOriginTransactionCheck() {
		return changeOriginTransactionCheck;
	}

	/**
	 * @param changeOriginTransactionCheck the changeOriginTransactionCheck to set
	 */
	public void setChangeOriginTransactionCheck(String changeOriginTransactionCheck) {
		this.changeOriginTransactionCheck = changeOriginTransactionCheck;
	}

	/**
	 * @return the changeAccountChargeInfo
	 */
	public String getChangeAccountChargeInfo() {
		return changeAccountChargeInfo;
	}

	/**
	 * @param changeAccountChargeInfo the changeAccountChargeInfo to set
	 */
	public void setChangeAccountChargeInfo(String changeAccountChargeInfo) {
		this.changeAccountChargeInfo = changeAccountChargeInfo;
	}

	/**
	 * @return the changePlanInfo
	 */
	public String getChangePlanInfo() {
		return changePlanInfo;
	}

	/**
	 * @param changePlanInfo the changePlanInfo to set
	 */
	public void setChangePlanInfo(String changePlanInfo) {
		this.changePlanInfo = changePlanInfo;
	}

	public SelectItem[] getStatesChangeArray() {
		if (this.statesChangeArray == null) {
			// Lista Estados
			List<States> states = statesBean.findAll();
			if (states != null) {
				statesChangeArray = new SelectItem[states.size()];
				int i = 0;
				for (States sta : states) {
					statesChangeArray[i] = new SelectItem(sta.getName(), sta.getName());
					i++;
				}
			}
		}
		return statesChangeArray;
	}
	
	/**
	 * @param statesChangeArray the statesChangeArray to set
	 */
	public void setStatesChangeArray(SelectItem[] statesChangeArray) {
		this.statesChangeArray = statesChangeArray;
	}


	
	public SelectItem[] getCitiesChangeArray() {
		if(getChangeStateInfo() == null){
			setChangeStateInfo(ApplicationConstants.EMPTY_STRING);
		}
		
		if (getChangeStateInfo() != null) {
			List<Cities> cities = citiesBean.findByState(getChangeStateInfo());
			if (cities != null) {
				citiesChangeArray = new SelectItem[cities.size()];
				int i = 0;
				for (Cities cit : cities) {
					// System.err.println("Ciudad: " + cit.getName());
					citiesChangeArray[i] = new SelectItem(cit.getName(),
							cit.getName());
					i++;
				}
			}
		}
		
		return citiesChangeArray;
	}
	
	public void setCitiesChangeArray(SelectItem[] citiesChangeArray) {
		this.citiesChangeArray = citiesChangeArray;
	}

	/**
	 * @return the optionA
	 */
	public String getOptionA() {
		return optionA;
	}

	/**
	 * @param optionA the optionA to set
	 */
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	/**
	 * @return the optionB
	 */
	public String getOptionB() {
		return optionB;
	}

	/**
	 * @param optionB the optionB to set
	 */
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	/**
	 * @return the optionC
	 */
	public String getOptionC() {
		return optionC;
	}

	/**
	 * @param optionC the optionC to set
	 */
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	/**
	 * @return the optionD
	 */
	public String getOptionD() {
		return optionD;
	}

	/**
	 * @param optionD the optionD to set
	 */
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	/**
	 * @return the optionE
	 */
	public String getOptionE() {
		return optionE;
	}

	/**
	 * @param optionE the optionE to set
	 */
	public void setOptionE(String optionE) {
		this.optionE = optionE;
	}

	/**
	 * @return the optionF
	 */
	public String getOptionF() {
		return optionF;
	}

	/**
	 * @param optionF the optionF to set
	 */
	public void setOptionF(String optionF) {
		this.optionF = optionF;
	}

	/**
	 * @return the optionG
	 */
	public String getOptionG() {
		return optionG;
	}

	/**
	 * @param optionG the optionG to set
	 */
	public void setOptionG(String optionG) {
		this.optionG = optionG;
	}

	/**
	 * @return the optionH
	 */
	public String getOptionH() {
		return optionH;
	}

	/**
	 * @param optionH the optionH to set
	 */
	public void setOptionH(String optionH) {
		this.optionH = optionH;
	}

	/**
	 * @return the optionI
	 */
	public String getOptionI() {
		return optionI;
	}

	/**
	 * @param optionI the optionI to set
	 */
	public void setOptionI(String optionI) {
		this.optionI = optionI;
	}

	/**
	 * @return the optionJ
	 */
	public String getOptionJ() {
		return optionJ;
	}

	/**
	 * @param optionJ the optionJ to set
	 */
	public void setOptionJ(String optionJ) {
		this.optionJ = optionJ;
	}

	/**
	 * @return the optionK
	 */
	public String getOptionK() {
		return optionK;
	}

	/**
	 * @param optionK the optionK to set
	 */
	public void setOptionK(String optionK) {
		this.optionK = optionK;
	}

	/**
	 * @return the optionL
	 */
	public String getOptionL() {
		return optionL;
	}

	/**
	 * @param optionL the optionL to set
	 */
	public void setOptionL(String optionL) {
		this.optionL = optionL;
	}

	/**
	 * @return the optionM
	 */
	public String getOptionM() {
		return optionM;
	}

	/**
	 * @param optionM the optionM to set
	 */
	public void setOptionM(String optionM) {
		this.optionM = optionM;
	}

	/**
	 * @return the quantityAttr
	 */
	public String getQuantityAttr() {
		return quantityAttr;
	}

	/**
	 * @param quantityAttr the quantityAttr to set
	 */
	public void setQuantityAttr(String quantityAttr) {
		this.quantityAttr = quantityAttr;
	}

	/**
	 * @return the changeLegalRepCheck
	 */
	public String getChangeLegalRepCheck() {
		return changeLegalRepCheck;
	}

	/**
	 * @param changeLegalRepCheck the changeLegalRepCheck to set
	 */
	public void setChangeLegalRepCheck(String changeLegalRepCheck) {
		this.changeLegalRepCheck = changeLegalRepCheck;
	}

	/**
	 * @return the changeLegalRepInfo
	 */
	public String getChangeLegalRepInfo() {
		return changeLegalRepInfo;
	}

	/**
	 * @param changeLegalRepInfo the changeLegalRepInfo to set
	 */
	public void setChangeLegalRepInfo(String changeLegalRepInfo) {
		this.changeLegalRepInfo = changeLegalRepInfo;
	}

	/**
	 * @return the cityState
	 */
	public String getCityState() {
		return cityState;
	}

	/**
	 * @param cityState the cityState to set
	 */
	public void setCityState(String cityState) {
		this.cityState = cityState;
	}

	/**
	 * @return the optionCuentaCentralizada
	 */
	public String getOptionCuentaCentralizada() {
		return optionCuentaCentralizada;
	}

	/**
	 * @param optionCuentaCentralizada the optionCuentaCentralizada to set
	 */
	public void setOptionCuentaCentralizada(String optionCuentaCentralizada) {
		this.optionCuentaCentralizada = optionCuentaCentralizada;
	}

	/**
	 * @return the optionOrigenTransaccion
	 */
	public String getOptionOrigenTransaccion() {
		return optionOrigenTransaccion;
	}

	/**
	 * @param optionOrigenTransaccion the optionOrigenTransaccion to set
	 */
	public void setOptionOrigenTransaccion(String optionOrigenTransaccion) {
		this.optionOrigenTransaccion = optionOrigenTransaccion;
	}

	/**
	 * @return the placeDateConvenio
	 */
	public String getPlaceDateConvenio() {
		return placeDateConvenio;
	}

	/**
	 * @param placeDateConvenio the placeDateConvenio to set
	 */
	public void setPlaceDateConvenio(String placeDateConvenio) {
		this.placeDateConvenio = placeDateConvenio;
	}
	
	public void metodoParaHacerMasPeso(){
		System.out.println("metodo dummy para hacer peso para harvest");
	}

	
}
