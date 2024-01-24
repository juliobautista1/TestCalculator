/**
 * 
 */
package com.banorte.contract.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
public class MttoBEMAddToken  extends ContractAbstractMB {
	
	private Integer mttoType;
	private String bemnumber;
	private String quantityAccounts;
	
	private String newTokenNumber_1;			//AttrConstants.MTTO_NEW_TOKEN_NUMBER_1    NUEVO TOKEN 
	private String newTokenNumber_2;			//AttrConstants.MTTO_NEW_TOKEN_NUMBER_2
	private String newTokenNumber_3;			//AttrConstants.MTTO_NEW_TOKEN_NUMBER_3
	private String newTokenNumber_4;			//AttrConstants.MTTO_NEW_TOKEN_NUMBER_4
	private String newTokenNumber_5;			//AttrConstants.MTTO_NEW_TOKEN_NUMBER_5
	
	private String folioPsd_1;
	private String folioPsd_2;
	private String folioPsd_3;
	private String folioPsd_4;
	private String folioPsd_5;
	
    private String NAME_TEMPLATE ="nameTemplate";
    private String PATHTEMPLATE ="pathTemplate";
    
	// DATOS EMPRESA 
	private String bem_colony;
	private String bem_address;
	private String bem_cp;
	private String fiscalFullName;
	private String legalRepresentativeFullName;   //	AttrConstants.LEGALREPRESENTATIVE_NAME_1
	// ************
	
	private PdfTemplateBindingContract pdfTemplateBinding;
	private String templateSolicitud_addToken;

	/**
	 * 
	 */
	public MttoBEMAddToken() {
		super();
		this.mttoType = MttoType.TOKENS_ADICIONALES.getMttoTypeId();
		setStatusContract(statusBean.findById(new Integer(ApplicationConstants.DEFAULT_VERSION_CONTRACT)));
	}

	@Override
	public void setResetForm() {
		this.bemnumber 						= ApplicationConstants.EMPTY_STRING;
		this.quantityAccounts				= ApplicationConstants.EMPTY_STRING;
		this.newTokenNumber_1				= ApplicationConstants.EMPTY_STRING;
		this.newTokenNumber_2				= ApplicationConstants.EMPTY_STRING;
		this.newTokenNumber_3				= ApplicationConstants.EMPTY_STRING;
		this.newTokenNumber_4				= ApplicationConstants.EMPTY_STRING;
		this.newTokenNumber_5				= ApplicationConstants.EMPTY_STRING;
		this.folioPsd_1						= ApplicationConstants.EMPTY_STRING;
		this.folioPsd_2						= ApplicationConstants.EMPTY_STRING;
		this.folioPsd_3						= ApplicationConstants.EMPTY_STRING;
		this.folioPsd_4						= ApplicationConstants.EMPTY_STRING;
		this.folioPsd_5						= ApplicationConstants.EMPTY_STRING;
		this.bem_colony						= ApplicationConstants.EMPTY_STRING;
		this.bem_address					= ApplicationConstants.EMPTY_STRING;
		this.bem_cp							= ApplicationConstants.EMPTY_STRING;		
		this.fiscalFullName					= ApplicationConstants.EMPTY_STRING;
		this.legalRepresentativeFullName	= ApplicationConstants.EMPTY_STRING;
		
		clearFields();
		setProduct(productBean.findById(new Integer(ProductType.MTTO_ADD_TOKEN.value()))); 
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
		
		String FILLS[] = { AttrConstants.BEM_NUMBER,AttrConstants.CR_NUMBER,AttrConstants.CLIENT_SIC,
				AttrConstants.LEGALREPRESENTATIVE_NAME_1,AttrConstants.CLIENT_FISCALNAME,
				AttrConstants.QTY_ACCOUNTS,AttrConstants.MTTO_NEW_TOKEN_NUMBER_1,
				AttrConstants.MTTO_NEW_TOKEN_NUMBER_2,AttrConstants.MTTO_NEW_TOKEN_NUMBER_3,AttrConstants.MTTO_NEW_TOKEN_NUMBER_4,
				AttrConstants.MTTO_NEW_TOKEN_NUMBER_5,AttrConstants.MTTO_FOLIO_PSD_1,
				AttrConstants.MTTO_FOLIO_PSD_2,AttrConstants.MTTO_FOLIO_PSD_3,AttrConstants.MTTO_FOLIO_PSD_4,
				AttrConstants.MTTO_FOLIO_PSD_5,AttrConstants.BEM_COLONY,AttrConstants.BEM_ADDRESS,
				AttrConstants.BEM_CP,AttrConstants.BEM_STATE,AttrConstants.BEM_POPULATION,
				AttrConstants.OFFICER_REP_FIRMNUMBER_1,AttrConstants.OFFICER_EBANKING_FIRMNUMBER};
		

		Contract contract_ = contractBean.findById(idContract);
		if (contract_.getContractAttributeCollection() != null) {
			Map<String, String> map = this.getContractAttributeFills(contract_,FILLS);

			this.setClient_sic( map.get(AttrConstants.CLIENT_SIC));
			this.setBemnumber( map.get(AttrConstants.BEM_NUMBER) );
			this.setCrnumber( map.get(AttrConstants.CR_NUMBER) );
			this.setNewTokenNumber_1( map.get(AttrConstants.MTTO_NEW_TOKEN_NUMBER_1) );
			this.setNewTokenNumber_2( map.get(AttrConstants.MTTO_NEW_TOKEN_NUMBER_2) );
			this.setNewTokenNumber_3( map.get(AttrConstants.MTTO_NEW_TOKEN_NUMBER_3) );
			this.setNewTokenNumber_4( map.get(AttrConstants.MTTO_NEW_TOKEN_NUMBER_4) );
			this.setNewTokenNumber_5( map.get(AttrConstants.MTTO_NEW_TOKEN_NUMBER_5) );
			this.setFolioPsd_1( map.get(AttrConstants.MTTO_FOLIO_PSD_1));
			this.setFolioPsd_2( map.get(AttrConstants.MTTO_FOLIO_PSD_2));
			this.setFolioPsd_3( map.get(AttrConstants.MTTO_FOLIO_PSD_3));
			this.setFolioPsd_4( map.get(AttrConstants.MTTO_FOLIO_PSD_4));
			this.setFolioPsd_5( map.get(AttrConstants.MTTO_FOLIO_PSD_5));
			this.setBem_colony( map.get(AttrConstants.BEM_COLONY));
			this.setBem_address( map.get(AttrConstants.BEM_ADDRESS));
			this.setBem_cp( map.get(AttrConstants.BEM_CP));
			this.setCelebrationplace( map.get(AttrConstants.BEM_POPULATION) );
			this.setCelebrationstate(  map.get(AttrConstants.BEM_STATE) );
			this.setLegalRepresentativeFullName( map.get(AttrConstants.LEGALREPRESENTATIVE_NAME_1));
			this.setFiscalFullName( map.get(AttrConstants.CLIENT_FISCALNAME) );
			quantityAccounts= map.get(AttrConstants.QTY_ACCOUNTS);//gina
			
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
		setProduct(productBean.findById(new Integer(ProductType.MTTO_ADD_TOKEN.value())));
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
		addToList(list,AttrConstants.QTY_ACCOUNTS, getQuantityAccounts());
		addToList(list,AttrConstants.QTY_TOKENS, getQuantityAccounts());
		addToList(list,AttrConstants.QTY_PASS, getQuantityAccounts());
		addToList(list,AttrConstants.MTTO_NEW_TOKEN_NUMBER_1, getNewTokenNumber_1());
		addToList(list,AttrConstants.MTTO_NEW_TOKEN_NUMBER_2, getNewTokenNumber_2());
		addToList(list,AttrConstants.MTTO_NEW_TOKEN_NUMBER_3, getNewTokenNumber_3());
		addToList(list,AttrConstants.MTTO_NEW_TOKEN_NUMBER_4, getNewTokenNumber_4());
		addToList(list,AttrConstants.MTTO_NEW_TOKEN_NUMBER_5, getNewTokenNumber_5());
		addToList(list,AttrConstants.MTTO_FOLIO_PSD_1, getFolioPsd_1());
		addToList(list,AttrConstants.MTTO_FOLIO_PSD_2, getFolioPsd_2());
		addToList(list,AttrConstants.MTTO_FOLIO_PSD_3, getFolioPsd_3());
		addToList(list,AttrConstants.MTTO_FOLIO_PSD_4, getFolioPsd_4());
		addToList(list,AttrConstants.MTTO_FOLIO_PSD_5, getFolioPsd_5());
		addToList(list,AttrConstants.BEM_COLONY,getBem_colony());
		addToList(list,AttrConstants.BEM_ADDRESS, getBem_address());
		addToList(list,AttrConstants.BEM_CP,getBem_cp());
		addToList(list,AttrConstants.BEM_STATE, getCelebrationstate());
		addToList(list,AttrConstants.BEM_POPULATION, getCelebrationplace());
		addToList(list,AttrConstants.LEGALREPRESENTATIVE_NAME_1,getLegalRepresentativeFullName());
		addToList(list,AttrConstants.CLIENT_FISCALNAME, getFiscalFullName());
		addToList(list,AttrConstants.MTTO_TEMPLATE_SOLICITUD_ADD_TOKEN, getTemplateSolicitud_addToken());
		addToList(list,AttrConstants.QTY_ENVELOPE, getQuantityAccounts());
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
	}
	
	
	public String processInfo() {
		this.getGeneralInfoErrorsList().clear();
		//Validacion Apoderado Legal o Administrador Designado
		if(! validateLegalRepresentativeFullName()){
			return "FAILED";
		}
		//validar de 9 a 10 caracteres en serie de nuevos tokens
		if(validarSerialToken()){
			return"FAILED";
		}
		
		this.setValidationEDO(Boolean.TRUE);
		this.setValidationMtto( Boolean.FALSE);  				// para no validar los 2 representantes de Banorte
		this.setTemplateSolicitud_addToken(ApplicationConstants.OPCION_SELECTED); 
		generalInfoErrorsList.clear();
		setInfoAccounts();
		
		return saveCompleteMtto();
	}
	
	public boolean validarSerialToken(){
		List<String> tokensNuevos = new ArrayList<String>();
		ContractMessageErrors errors;
		Boolean errorResult=false;
		ArrayList<ContractMessageErrors> errorsList= new ArrayList<ContractMessageErrors>();
		
		int tokenSolicitado=Integer.parseInt(quantityAccounts);
		tokensNuevos.add(newTokenNumber_1);
		tokensNuevos.add(newTokenNumber_2);	
		tokensNuevos.add(newTokenNumber_3);
		tokensNuevos.add(newTokenNumber_4);
		tokensNuevos.add(newTokenNumber_5);

		for(int i=0;i<=(tokenSolicitado-1);i++){
			if(tokensNuevos.get(i).trim().length()!=9){
				errors=new ContractMessageErrors();
				errors.setMessage("El token "+(i+1)+" debe ser de 9 caracteres");
				errorsList.add(errors);
				this.setErrorsList(errorsList);
				errorResult=true;
			}
		}
		
		return errorResult;
	}
	
	private void setInfoAccounts(){
		if(getQuantityAccounts().equals( ApplicationConstants.NUMBER_1)){
			setEmptyInfo2();
			setEmptyInfo3();
			setEmptyInfo4();
			setEmptyInfo5();
		}else if(getQuantityAccounts().equals( ApplicationConstants.NUMBER_2)){
			setEmptyInfo3();
			setEmptyInfo4();
			setEmptyInfo5();
		}else if(getQuantityAccounts().equals( ApplicationConstants.NUMBER_3)){
			setEmptyInfo4();
			setEmptyInfo5();
		}else if(getQuantityAccounts().equals( ApplicationConstants.NUMBER_4)){
			setEmptyInfo5();
		}
		
	}
	
	private void setEmptyInfo2(){
		this.setFolioPsd_2(ApplicationConstants.EMPTY_STRING);
		this.setNewTokenNumber_2(ApplicationConstants.EMPTY_STRING);
	}
	
	private void setEmptyInfo3(){
		this.setFolioPsd_3(ApplicationConstants.EMPTY_STRING);
		this.setNewTokenNumber_3(ApplicationConstants.EMPTY_STRING);
	}
	
	private void setEmptyInfo4(){
		this.setFolioPsd_4(ApplicationConstants.EMPTY_STRING);
		this.setNewTokenNumber_4(ApplicationConstants.EMPTY_STRING);
	}
	
	private void setEmptyInfo5(){
		this.setFolioPsd_5(ApplicationConstants.EMPTY_STRING);
		this.setNewTokenNumber_5(ApplicationConstants.EMPTY_STRING);
	}
	
	
	//Validacion Legal Representative Full Name
	private boolean validateLegalRepresentativeFullName(){
		this.getGeneralInfoErrorsList().clear();
		ContractMessageErrors errors;
		boolean result 										= Boolean.TRUE;
		ArrayList<ContractMessageErrors> errorsList 		= new ArrayList();
		
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
	
	
	// GETTER'S AND SETTER'S
	public Integer getMttoType() {
		return mttoType;
	}

	public void setMttoType(Integer mttoType) {
		this.mttoType = mttoType;
	}

	public String getBemnumber() {
		return bemnumber;
	}

	public void setBemnumber(String bemnumber) {
		this.bemnumber = bemnumber;
	}
	
	public String getQuantityAccounts() {
		return quantityAccounts;
	}

	public void setQuantityAccounts(String quantityAccounts) {
		this.quantityAccounts = quantityAccounts;
	}

	public String getNewTokenNumber_1() {
		return newTokenNumber_1;
	}

	public void setNewTokenNumber_1(String newTokenNumber_1) {
		this.newTokenNumber_1 = newTokenNumber_1;
	}

	public String getNewTokenNumber_2() {
		return newTokenNumber_2;
	}

	public void setNewTokenNumber_2(String newTokenNumber_2) {
		this.newTokenNumber_2 = newTokenNumber_2;
	}

	public String getNewTokenNumber_3() {
		return newTokenNumber_3;
	}

	public void setNewTokenNumber_3(String newTokenNumber_3) {
		this.newTokenNumber_3 = newTokenNumber_3;
	}

	public String getNewTokenNumber_4() {
		return newTokenNumber_4;
	}

	public void setNewTokenNumber_4(String newTokenNumber_4) {
		this.newTokenNumber_4 = newTokenNumber_4;
	}

	public String getNewTokenNumber_5() {
		return newTokenNumber_5;
	}

	public void setNewTokenNumber_5(String newTokenNumber_5) {
		this.newTokenNumber_5 = newTokenNumber_5;
	}

	public String getFolioPsd_1() {
		return folioPsd_1;
	}

	public void setFolioPsd_1(String folioPsd_1) {
		this.folioPsd_1 = folioPsd_1;
	}

	public String getFolioPsd_2() {
		return folioPsd_2;
	}

	public void setFolioPsd_2(String folioPsd_2) {
		this.folioPsd_2 = folioPsd_2;
	}

	public String getFolioPsd_3() {
		return folioPsd_3;
	}

	public void setFolioPsd_3(String folioPsd_3) {
		this.folioPsd_3 = folioPsd_3;
	}

	public String getFolioPsd_4() {
		return folioPsd_4;
	}

	public void setFolioPsd_4(String folioPsd_4) {
		this.folioPsd_4 = folioPsd_4;
	}

	public String getFolioPsd_5() {
		return folioPsd_5;
	}

	public void setFolioPsd_5(String folioPsd_5) {
		this.folioPsd_5 = folioPsd_5;
	}

	public String getBem_colony() {
		return bem_colony;
	}

	public void setBem_colony(String bem_colony) {
		this.bem_colony = bem_colony;
	}
	
	public String getBem_address() {
		return bem_address;
	}

	public void setBem_address(String bem_address) {
		this.bem_address = bem_address;
	}

	public String getBem_cp() {
		return bem_cp;
	}

	public void setBem_cp(String bem_cp) {
		this.bem_cp = bem_cp;
	}

	public String getFiscalFullName() {
		return fiscalFullName;
	}

	public void setFiscalFullName(String fiscalFullName) {
		this.fiscalFullName = fiscalFullName;
	}

	public String getLegalRepresentativeFullName() {
		return legalRepresentativeFullName;
	}

	public void setLegalRepresentativeFullName(String legalRepresentativeFullName) {
		this.legalRepresentativeFullName = legalRepresentativeFullName;
	}

	public String getTemplateSolicitud_addToken() {
		return templateSolicitud_addToken;
	}

	public void setTemplateSolicitud_addToken(String templateSolicitud_addToken) {
		this.templateSolicitud_addToken = templateSolicitud_addToken;
	}
	
	public void metodoParaHacerMasPeso7(){
		System.out.println("metodo dummy para hacer peso para harvest");
	}	
}
