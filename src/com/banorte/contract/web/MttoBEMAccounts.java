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
public class MttoBEMAccounts extends ContractAbstractMB{
	
	private Integer mttoType;
	private String bemnumber;
	private String quantityAccounts;
	private String accownnum_1;
	private String regimen_1;
	private String accownname_1;
	private String accownnum_2;
	private String regimen_2;
	private String accownname_2;
	private String accownnum_3;
	private String regimen_3;
	private String accownname_3;
	private String accownnum_4;
	private String regimen_4;
	private String accownname_4;
	private String accownnum_5;
	private String regimen_5;
	private String accownname_5;
	private String fiscalFullName;
	private String accownnum_6;
	private String regimen_6;
	private String accownname_6;
	private String accownnum_7;
	private String regimen_7;
	private String accownname_7;
	private String accownnum_8;
	private String regimen_8;
	private String accownname_8;	

    private String NAME_TEMPLATE ="nameTemplate";
    private String PATHTEMPLATE ="pathTemplate";
    
	private PdfTemplateBindingContract pdfTemplateBinding;
	private ArrayList<ContractMessageErrors> ordenErrorsList = new ArrayList();
	
	

	public MttoBEMAccounts() {
		super();
		this.bemnumber 				= ApplicationConstants.EMPTY_STRING;
		this.quantityAccounts 		= ApplicationConstants.EMPTY_STRING;
		this.accownnum_1 			= ApplicationConstants.EMPTY_STRING;
		this.accownname_1 			= ApplicationConstants.EMPTY_STRING;
		this.regimen_1 				= ApplicationConstants.EMPTY_STRING;
		this.accownnum_2 			= ApplicationConstants.EMPTY_STRING;
		this.accownname_2 			= ApplicationConstants.EMPTY_STRING;
		this.regimen_2 				= ApplicationConstants.EMPTY_STRING;
		this.accownnum_3 			= ApplicationConstants.EMPTY_STRING;
		this.accownname_3 			= ApplicationConstants.EMPTY_STRING;
		this.regimen_3 				= ApplicationConstants.EMPTY_STRING;
		this.accownnum_4 			= ApplicationConstants.EMPTY_STRING;
		this.accownname_4 			= ApplicationConstants.EMPTY_STRING;
		this.regimen_4 				= ApplicationConstants.EMPTY_STRING;
		this.accownnum_5 			= ApplicationConstants.EMPTY_STRING;
		this.accownname_5 			= ApplicationConstants.EMPTY_STRING;
		this.regimen_5 				= ApplicationConstants.EMPTY_STRING;
		this.fiscalFullName 		= ApplicationConstants.EMPTY_STRING;
		accownnum_6					= ApplicationConstants.EMPTY_STRING;
		accownname_6				= ApplicationConstants.EMPTY_STRING;
		regimen_6					= ApplicationConstants.EMPTY_STRING;
		accownnum_7					= ApplicationConstants.EMPTY_STRING;
		accownname_7				= ApplicationConstants.EMPTY_STRING;
		regimen_7					= ApplicationConstants.EMPTY_STRING;
		accownnum_8					= ApplicationConstants.EMPTY_STRING;
		accownname_8				= ApplicationConstants.EMPTY_STRING;
		regimen_8					= ApplicationConstants.EMPTY_STRING;
		
		this.mttoType = MttoType.ALTA_CUENTAS.getMttoTypeId();
		setStatusContract(statusBean.findById(new Integer(ApplicationConstants.DEFAULT_VERSION_CONTRACT)));
	}

	@Override
	public void setResetForm() {
		this.bemnumber 				= ApplicationConstants.EMPTY_STRING;
		this.quantityAccounts 		= ApplicationConstants.EMPTY_STRING;
		this.accownnum_1 			= ApplicationConstants.EMPTY_STRING;
		this.accownname_1 			= ApplicationConstants.EMPTY_STRING;
		this.regimen_1 				= ApplicationConstants.EMPTY_STRING;
		this.accownnum_2 			= ApplicationConstants.EMPTY_STRING;
		this.accownname_2 			= ApplicationConstants.EMPTY_STRING;
		this.regimen_2 				= ApplicationConstants.EMPTY_STRING;
		this.accownnum_3 			= ApplicationConstants.EMPTY_STRING;
		this.accownname_3 			= ApplicationConstants.EMPTY_STRING;
		this.regimen_3 				= ApplicationConstants.EMPTY_STRING;
		this.accownnum_4 			= ApplicationConstants.EMPTY_STRING;
		this.accownname_4 			= ApplicationConstants.EMPTY_STRING;
		this.regimen_4 				= ApplicationConstants.EMPTY_STRING;
		this.accownnum_5 			= ApplicationConstants.EMPTY_STRING;
		this.accownname_5 			= ApplicationConstants.EMPTY_STRING;
		this.regimen_5 				= ApplicationConstants.EMPTY_STRING;
		this.fiscalFullName 		= ApplicationConstants.EMPTY_STRING;
		accownnum_6					= ApplicationConstants.EMPTY_STRING;
		accownname_6				= ApplicationConstants.EMPTY_STRING;
		regimen_6					= ApplicationConstants.EMPTY_STRING;
		accownnum_7					= ApplicationConstants.EMPTY_STRING;
		accownname_7				= ApplicationConstants.EMPTY_STRING;
		regimen_7					= ApplicationConstants.EMPTY_STRING;
		accownnum_8					= ApplicationConstants.EMPTY_STRING;
		accownname_8				= ApplicationConstants.EMPTY_STRING;
		regimen_8					= ApplicationConstants.EMPTY_STRING;
		
		clearFields();
		setProduct(productBean.findById(new Integer(ProductType.MTTO_ALTA_CUENTAS.value()))); 
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
				AttrConstants.ACCOUNT_NUMBER_1,AttrConstants.ACCOUNT_REGIMEN_1,AttrConstants.ACCOUNT_NAME_1,
				AttrConstants.ACCOUNT_NUMBER_2,AttrConstants.ACCOUNT_REGIMEN_2,AttrConstants.ACCOUNT_NAME_2,
				AttrConstants.ACCOUNT_NUMBER_3,AttrConstants.ACCOUNT_REGIMEN_3,AttrConstants.ACCOUNT_NAME_3,
				AttrConstants.ACCOUNT_NUMBER_4,AttrConstants.ACCOUNT_REGIMEN_4,AttrConstants.ACCOUNT_NAME_4,
				AttrConstants.ACCOUNT_NUMBER_5,AttrConstants.ACCOUNT_REGIMEN_5,AttrConstants.ACCOUNT_NAME_5,
				AttrConstants.OFFICER_REP_FIRMNUMBER_1,AttrConstants.OFFICER_REP_FIRMNUMBER_2,
				AttrConstants.OFFICER_EBANKING_FIRMNUMBER,AttrConstants.QTY_ACCOUNTS,AttrConstants.CLIENT_FISCALNAME,
				AttrConstants.CLIENT_SIC,
				AttrConstants.ACCOUNT_NUMBER_6, AttrConstants.ACCOUNT_REGIMEN_6, AttrConstants.ACCOUNT_NAME_6,
				AttrConstants.ACCOUNT_NUMBER_7, AttrConstants.ACCOUNT_REGIMEN_7, AttrConstants.ACCOUNT_NAME_7,
				AttrConstants.ACCOUNT_NUMBER_8, AttrConstants.ACCOUNT_REGIMEN_8, AttrConstants.ACCOUNT_NAME_8,
							};
		
		Contract contract_ = contractBean.findById(idContract);
		if (contract_.getContractAttributeCollection() != null) {
			Map<String, String> map = this.getContractAttributeFills(contract_,CD_FILLS);
			
			this.setClient_sic( map.get(AttrConstants.CLIENT_SIC));
			this.setBemnumber( map.get(AttrConstants.BEM_NUMBER) );
			this.setCrnumber( map.get(AttrConstants.CR_NUMBER) );
			this.setQuantityAccounts( map.get(AttrConstants.QTY_ACCOUNTS));
			this.setAccownnum_1( map.get(AttrConstants.ACCOUNT_NUMBER_1));
			this.setAccownname_1( map.get(AttrConstants.ACCOUNT_NAME_1) );
			this.setRegimen_1( map.get(AttrConstants.ACCOUNT_REGIMEN_1));
			this.setAccownnum_2( map.get(AttrConstants.ACCOUNT_NUMBER_2));
			this.setAccownname_2( map.get(AttrConstants.ACCOUNT_NAME_2) );
			this.setRegimen_2( map.get(AttrConstants.ACCOUNT_REGIMEN_2));
			this.setAccownnum_3( map.get(AttrConstants.ACCOUNT_NUMBER_3));
			this.setAccownname_3( map.get(AttrConstants.ACCOUNT_NAME_3) );
			this.setRegimen_3( map.get(AttrConstants.ACCOUNT_REGIMEN_3));
			this.setAccownnum_4( map.get(AttrConstants.ACCOUNT_NUMBER_4));
			this.setAccownname_4( map.get(AttrConstants.ACCOUNT_NAME_4) );
			this.setRegimen_4( map.get(AttrConstants.ACCOUNT_REGIMEN_4));
			this.setAccownnum_5( map.get(AttrConstants.ACCOUNT_NUMBER_5));
			this.setAccownname_5( map.get(AttrConstants.ACCOUNT_NAME_5) );
			this.setRegimen_5( map.get(AttrConstants.ACCOUNT_REGIMEN_5));
			this.setFiscalFullName( map.get(AttrConstants.CLIENT_FISCALNAME) );
			accownnum_6 = (map.get(AttrConstants.ACCOUNT_NUMBER_6));
			accownname_6 = (map.get(AttrConstants.ACCOUNT_NAME_6));
			regimen_6 = (map.get(AttrConstants.ACCOUNT_REGIMEN_6));
			accownnum_7 = (map.get(AttrConstants.ACCOUNT_NUMBER_7));
			accownname_7 = (map.get(AttrConstants.ACCOUNT_NAME_7));
			regimen_7 = (map.get(AttrConstants.ACCOUNT_REGIMEN_7));
			accownnum_8 = (map.get(AttrConstants.ACCOUNT_NUMBER_8));
			accownname_8 = (map.get(AttrConstants.ACCOUNT_NAME_8));
			regimen_8 = (map.get(AttrConstants.ACCOUNT_REGIMEN_8));
			
			// DATOS CLIENTE 1 y 2
			this.loadToEditLegalrepresentative1(map);
			this.loadToEditLegalrepresentative2(map);
			
			//DATOS DEL FUNCIONARIO QUE CAPTURO LA SOLICITUD DE MANTENIMIENTO
			this.loadToEditOfficerInfo(map);
			
			//DATOS DE LOS FUNCIONARIOS BANORTE
			this.loadToEditOfficerRep1Info(map);
			this.setOfficerrepfirmnumber_1(map.get( AttrConstants.OFFICER_REP_FIRMNUMBER_1));
			this.loadToEditOfficerRep2Info(map);
			this.setOfficerrepfirmnumber_2(map.get( AttrConstants.OFFICER_REP_FIRMNUMBER_2));
			
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
		Collection<Template> templateCollection = getTemplateOption(this.getProduct().getProductid());
		Collection<Template> templateColl2 = new ArrayList();
		//nueva pagina de cuentas propias
		for(Template t: templateCollection){
//			if((t.getName().equals(ApplicationConstants.TEMPLATE_MTTO_ACCOUNTS2))){
//				if(Integer.parseInt(quantityAccounts)>=5){
//					templateColl2.add(t);
//				}
//            }else {
    	        templateColl2.add(t);	
//            }
		}
		
		Template[] templateArray = new Template[templateColl2.size()];
		return templateColl2.toArray(templateArray); 
	}

	@Override
	public String getProductPrefix() {
		return ApplicationConstants.PREFIJO_MTTOS;
	}

	@Override
	public void getProductIdDetail() {
		setProduct(productBean.findById(new Integer(ProductType.MTTO_ALTA_CUENTAS.value())));
	}

	@Override
	public boolean savePartialContract() {
		Contract contract = getContract();
		ArrayList<ContractAttribute> list = new ArrayList<ContractAttribute>();

		// Mantenimientos Alta de Cuentas 
		addToList(list,AttrConstants.MTTO_TYPE, this.mttoType.toString());
		addToList(list,AttrConstants.CELEBRATION_DATE, getCelebrationdate());
		addToList(list,AttrConstants.CLIENT_SIC, getClient_sic());
		addToList(list,AttrConstants.BEM_NUMBER, getBemnumber());
		addToList(list,AttrConstants.CR_NUMBER, getCrnumber());
		addToList(list,AttrConstants.QTY_ACCOUNTS, getQuantityAccounts());
		addToList(list,AttrConstants.CLIENT_FISCALNAME, getFiscalFullName());
		addToList(list,AttrConstants.OPERATION_COMMENTS, this.getComments().length()<250?this.getComments():this.getComments().substring(0, 249));
		
		//ALTA DE CUENTAS
//		if(quantityAccounts.equals(ApplicationConstants.NUMBER_1) || quantityAccounts.equals(ApplicationConstants.NUMBER_2)
//				|| quantityAccounts.equals(ApplicationConstants.NUMBER_3) || quantityAccounts.equals(ApplicationConstants.NUMBER_4)
//				|| quantityAccounts.equals(ApplicationConstants.NUMBER_5) || quantityAccounts.equals(ApplicationConstants.NUMBER_6)
//				|| quantityAccounts.equals(ApplicationConstants.NUMBER_7) || quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NUMBER_1, getAccownnum_1()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_REGIMEN_1, getRegimen_1()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NAME_1, getAccownname_1()));
//		}
//		
//		if(quantityAccounts.equals(ApplicationConstants.NUMBER_2)||quantityAccounts.equals(ApplicationConstants.NUMBER_3) 
//				|| quantityAccounts.equals(ApplicationConstants.NUMBER_4) || quantityAccounts.equals(ApplicationConstants.NUMBER_5)
//				|| quantityAccounts.equals(ApplicationConstants.NUMBER_6) || quantityAccounts.equals(ApplicationConstants.NUMBER_7) 
//				|| quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NUMBER_2, getAccownnum_2()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_REGIMEN_2, getRegimen_2()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NAME_2, getAccownname_2()));
//		}
//		
//		
//		if(quantityAccounts.equals(ApplicationConstants.NUMBER_3) || quantityAccounts.equals(ApplicationConstants.NUMBER_4)
//				|| quantityAccounts.equals(ApplicationConstants.NUMBER_5) || quantityAccounts.equals(ApplicationConstants.NUMBER_6)
//				|| quantityAccounts.equals(ApplicationConstants.NUMBER_7) || quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NUMBER_3, getAccownnum_3()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_REGIMEN_3, getRegimen_3()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NAME_3, getAccownname_3()));
//		}
//		
//		if(quantityAccounts.equals(ApplicationConstants.NUMBER_4)|| quantityAccounts.equals(ApplicationConstants.NUMBER_5)
//				|| quantityAccounts.equals(ApplicationConstants.NUMBER_6)|| quantityAccounts.equals(ApplicationConstants.NUMBER_7) 
//				|| quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NUMBER_4, getAccownnum_4()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_REGIMEN_4, getRegimen_4()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NAME_4, getAccownname_4()));
//		}
//		
//		if(quantityAccounts.equals(ApplicationConstants.NUMBER_5)||quantityAccounts.equals(ApplicationConstants.NUMBER_6)
//				|| quantityAccounts.equals(ApplicationConstants.NUMBER_7) || quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NUMBER_5, getAccownnum_5()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_REGIMEN_5, getRegimen_5()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NAME_5, getAccownname_5()));
//		}
//		if(quantityAccounts.equals(ApplicationConstants.NUMBER_6)|| quantityAccounts.equals(ApplicationConstants.NUMBER_7) 
//				|| quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NUMBER_6, getAccownnum_6()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_REGIMEN_6, getRegimen_6()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NAME_6, getAccownname_6()));
//		}
//		if(quantityAccounts.equals(ApplicationConstants.NUMBER_7) || quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NUMBER_7, getAccownnum_7()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_REGIMEN_7, getRegimen_7()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NAME_7, getAccownname_7()));
//		}
//		if(quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NUMBER_8, getAccownnum_8()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_REGIMEN_8, getRegimen_8()));
//			list.add(getContractAttribute(AttrConstants.ACCOUNT_NAME_8, getAccownname_8()));
//		}
	//gina Agregar numero cuenta	
			addToList(list,AttrConstants.ACCOUNT_NUMBER_1, (1>Integer.parseInt(quantityAccounts)?"":getAccownnum_1()));
			addToList(list,AttrConstants.ACCOUNT_NUMBER_2, (2>Integer.parseInt(quantityAccounts)?"":getAccownnum_2()));
			addToList(list,AttrConstants.ACCOUNT_NUMBER_3, (3>Integer.parseInt(quantityAccounts)?"":getAccownnum_3()));
			addToList(list,AttrConstants.ACCOUNT_NUMBER_4, (4>Integer.parseInt(quantityAccounts)?"":getAccownnum_4()));
			addToList(list,AttrConstants.ACCOUNT_NUMBER_5, (5>Integer.parseInt(quantityAccounts)?"":getAccownnum_5()));
			addToList(list,AttrConstants.ACCOUNT_NUMBER_6, (6>Integer.parseInt(quantityAccounts)?"":getAccownnum_6()));
			addToList(list,AttrConstants.ACCOUNT_NUMBER_7, (7>Integer.parseInt(quantityAccounts)?"":getAccownnum_7()));
			addToList(list,AttrConstants.ACCOUNT_NUMBER_8, (8>Integer.parseInt(quantityAccounts)?"":getAccownnum_8()));
	//gina Agregar Regimen	
			addToList(list,AttrConstants.ACCOUNT_NAME_1, (1>Integer.parseInt(quantityAccounts)?"":getAccownname_1()));
			addToList(list,AttrConstants.ACCOUNT_NAME_2, (2>Integer.parseInt(quantityAccounts)?"":getAccownname_2()));
			addToList(list,AttrConstants.ACCOUNT_NAME_3, (3>Integer.parseInt(quantityAccounts)?"":getAccownname_3()));
			addToList(list,AttrConstants.ACCOUNT_NAME_4, (4>Integer.parseInt(quantityAccounts)?"":getAccownname_4()));
			addToList(list,AttrConstants.ACCOUNT_NAME_5, (5>Integer.parseInt(quantityAccounts)?"":getAccownname_5()));
			addToList(list,AttrConstants.ACCOUNT_NAME_6, (6>Integer.parseInt(quantityAccounts)?"":getAccownname_6()));
			addToList(list,AttrConstants.ACCOUNT_NAME_7, (7>Integer.parseInt(quantityAccounts)?"":getAccownname_7()));
			addToList(list,AttrConstants.ACCOUNT_NAME_8, (8>Integer.parseInt(quantityAccounts)?"":getAccownname_8()));

	//gina Agregar NombresCuentas
			addToList(list,AttrConstants.ACCOUNT_REGIMEN_1, (1>Integer.parseInt(quantityAccounts)?"":getRegimen_1()));
			addToList(list,AttrConstants.ACCOUNT_REGIMEN_2, (2>Integer.parseInt(quantityAccounts)?"":getRegimen_2()));
			addToList(list,AttrConstants.ACCOUNT_REGIMEN_3, (3>Integer.parseInt(quantityAccounts)?"":getRegimen_3()));
			addToList(list,AttrConstants.ACCOUNT_REGIMEN_4, (4>Integer.parseInt(quantityAccounts)?"":getRegimen_4()));
			addToList(list,AttrConstants.ACCOUNT_REGIMEN_5, (5>Integer.parseInt(quantityAccounts)?"":getRegimen_5()));
			addToList(list,AttrConstants.ACCOUNT_REGIMEN_6, (6>Integer.parseInt(quantityAccounts)?"":getRegimen_6()));
			addToList(list,AttrConstants.ACCOUNT_REGIMEN_7, (7>Integer.parseInt(quantityAccounts)?"":getRegimen_7()));
			addToList(list,AttrConstants.ACCOUNT_REGIMEN_8, (8>Integer.parseInt(quantityAccounts)?"":getRegimen_8()));
			
			addToList(list,"contract_reference", contract.getReference());
		
		
		// DATOS CLIENTE 1 y 2
		this.loadToSaveLegalRepresentative1(list);
		this.loadToSaveLegalRepresentative2(list);
		
		//DATOS DEL FUNCIONARIO QUE CAPTURO LA SOLICITUD DE MANTENIMIENTO
		this.loadToSaveOfficer(list);
		
		//DATOS DE LOS FUNCIONARIOS BANORTE
		this.loadToSaveOfficerRep1(list);
		addToList(list,AttrConstants.OFFICER_REP_FIRMNUMBER_1,getOfficerrepfirmnumber_1());
		this.loadToSaveOfficerRep2(list);
		addToList(list,AttrConstants.OFFICER_REP_FIRMNUMBER_2,getOfficerrepfirmnumber_2());
		
		//DATOS FUNCIONARIO EBANKING (gina... nuevo: Funcionario Facultado
		this.loadToSaveOfficerEbanking(list);
		addToList(list,AttrConstants.OFFICER_EBANKING_FIRMNUMBER,getOfficerebankingnumfirm());
		
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
		
		this.setValidationEDO(Boolean.FALSE);
		this.setValidationMtto( Boolean.FALSE);  				// para no validar los 2 representantes de Banorte
		
		generalInfoErrorsList.clear();
		
		if(!validateAccounts() ){
			return "FAILED";
		}
		
		return saveCompleteMtto();
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean validateAccounts(){
		this.getGeneralInfoErrorsList().clear();
		
		if (this.getErrorsList() != null) {
			this.getErrorsList().clear();
		}
		ContractMessageErrors errors;
		
		accownnum_1=(accownnum_1==null?"":accownnum_1);
		if (accownnum_1.length()<10) {
			if(quantityAccounts.equals(ApplicationConstants.NUMBER_1) || quantityAccounts.equals(ApplicationConstants.NUMBER_2)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_3) || quantityAccounts.equals(ApplicationConstants.NUMBER_4)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_5)|| quantityAccounts.equals(ApplicationConstants.NUMBER_6)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_7)|| quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 1 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
		} else if(!(getRegimen_1()==null)){
			errors = validarRegimen(this.getRegimen_1(),this.getAccownname_1(),'1');
			if(errors !=null){
				this.getGeneralInfoErrorsList().add(errors);
			}
		}
		accownnum_2=(accownnum_2==null?"":accownnum_2);
		if (accownnum_2.length() < 10 ) {
			if(quantityAccounts.equals(ApplicationConstants.NUMBER_2)||quantityAccounts.equals(ApplicationConstants.NUMBER_3) 
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_4) || quantityAccounts.equals(ApplicationConstants.NUMBER_5)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_6) || quantityAccounts.equals(ApplicationConstants.NUMBER_7)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 2 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
		} else if(!(getRegimen_2()==null)){
			errors = validarRegimen(this.getRegimen_2(),this.getAccownname_2(),'2');
			if(errors !=null){
				this.getGeneralInfoErrorsList().add(errors);
			}
		}
		accownnum_3=(accownnum_3==null?"":accownnum_3);
		if (accownnum_3.length() < 10 ) {
			if(quantityAccounts.equals(ApplicationConstants.NUMBER_3) || quantityAccounts.equals(ApplicationConstants.NUMBER_4)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_5)|| quantityAccounts.equals(ApplicationConstants.NUMBER_6)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_7)|| quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 3 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
		} else if(!(getRegimen_3()==null)){
			errors = validarRegimen(this.getRegimen_3(),this.getAccownname_3(),'3');
			if(errors !=null){
				this.getGeneralInfoErrorsList().add(errors);
			}
		}
		accownnum_4=(accownnum_4==null?"":accownnum_4);
		if (accownnum_4.length() < 10 ) {
			if(quantityAccounts.equals(ApplicationConstants.NUMBER_4)|| quantityAccounts.equals(ApplicationConstants.NUMBER_5)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_6)|| quantityAccounts.equals(ApplicationConstants.NUMBER_7)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 4 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
		} else if(!(getRegimen_4()==null)){
			errors = validarRegimen(this.getRegimen_4(),this.getAccownname_4(),'4');
			if(errors !=null){
				this.getGeneralInfoErrorsList().add(errors);
			}
		}
		accownnum_5=(accownnum_5==null?"":accownnum_5);
		if (accownnum_5.length() < 10 ) {
			if(quantityAccounts.equals(ApplicationConstants.NUMBER_5)|| quantityAccounts.equals(ApplicationConstants.NUMBER_6)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_7)|| quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 5 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
		} else if(!(getRegimen_5()==null)){
			errors = validarRegimen(this.getRegimen_5(),this.getAccownname_5(),'5');
			if(errors !=null){
				this.getGeneralInfoErrorsList().add(errors);
			}
		}
		accownnum_6=(accownnum_6==null?"":accownnum_6);
		if (accownnum_6.length() < 10 ) {
			if(quantityAccounts.equals(ApplicationConstants.NUMBER_6)|| quantityAccounts.equals(ApplicationConstants.NUMBER_7)
					|| quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 6 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
		} else if(!(getRegimen_6()==null)){
			errors = validarRegimen(this.getRegimen_6(),this.getAccownname_6(),'6');
			if(errors !=null){
				this.getGeneralInfoErrorsList().add(errors);
			}
		}
		accownnum_7=(accownnum_7==null?"":accownnum_7);
		if (accownnum_7.length() < 10 ) {
			if(quantityAccounts.equals(ApplicationConstants.NUMBER_7)||quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 7 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
		} else if(!(getRegimen_7()==null)){
			errors = validarRegimen(this.getRegimen_7(),this.getAccownname_7(),'7');
			if(errors !=null){
				this.getGeneralInfoErrorsList().add(errors);
			}
		}
		accownnum_8=(accownnum_8==null?"":accownnum_8);
		if (accownnum_8.length() < 10 ) {
			if(quantityAccounts.equals(ApplicationConstants.NUMBER_8)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar un numero de cuenta 8 de 10 digitos");
				this.getGeneralInfoErrorsList().add(errors);
			}
		} else if(!(getRegimen_8()==null)){
			errors = validarRegimen(this.getRegimen_8(),this.getAccownname_8(),'8');
			if(errors !=null){
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
	
	
	
	 private ContractMessageErrors validarRegimen(String regimen, String accownName, char regimenFieldId){
	    	
	    	ContractMessageErrors error=null;
	    	
	    	if(regimen.equalsIgnoreCase("MANCOMUNADO")){
	    		
	    		String[] names = accownName.split(",");
	    		
	    		if(names.length < 2){
	    			error = new ContractMessageErrors();
	    			error.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas " + regimenFieldId +" - El Regimen Mancomunado, requiere la captura de al menos dos Personas Autorizadas");
	    		}
	    	}
	    	
	    	return error;
	    };
	
	
	
	// GETTER'S & SETTER'S
	

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
		if(this.getContract() != null){
			this.getContract().setMerchantNumber(bemnumber);
		}
		this.bemnumber = bemnumber;
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
	 * @return the accownnum_1
	 */
	public String getAccownnum_1() {
		return accownnum_1;
	}

	/**
	 * @param accownnum_1 the accownnum_1 to set
	 */
	public void setAccownnum_1(String accownnum_1) {
		this.accownnum_1 = accownnum_1;
	}

	/**
	 * @return the regimen_1
	 */
	public String getRegimen_1() {
		return regimen_1;
	}

	/**
	 * @param regimen_1 the regimen_1 to set
	 */
	public void setRegimen_1(String regimen_1) {
		this.regimen_1 = regimen_1;
	}

	/**
	 * @return the accownname_1
	 */
	public String getAccownname_1() {
		return accownname_1;
	}

	/**
	 * @param accownname_1 the accownname_1 to set
	 */
	public void setAccownname_1(String accownname_1) {
		this.accownname_1 = accownname_1;
	}

	/**
	 * @return the accownnum_2
	 */
	public String getAccownnum_2() {
		return accownnum_2;
	}

	/**
	 * @param accownnum_2 the accownnum_2 to set
	 */
	public void setAccownnum_2(String accownnum_2) {
		this.accownnum_2 = accownnum_2;
	}

	/**
	 * @return the regimen_2
	 */
	public String getRegimen_2() {
		return regimen_2;
	}

	/**
	 * @param regimen_2 the regimen_2 to set
	 */
	public void setRegimen_2(String regimen_2) {
		this.regimen_2 = regimen_2;
	}

	/**
	 * @return the accownname_2
	 */
	public String getAccownname_2() {
		return accownname_2;
	}

	/**
	 * @param accownname_2 the accownname_2 to set
	 */
	public void setAccownname_2(String accownname_2) {
		this.accownname_2 = accownname_2;
	}

	/**
	 * @return the accownnum_3
	 */
	public String getAccownnum_3() {
		return accownnum_3;
	}

	/**
	 * @param accownnum_3 the accownnum_3 to set
	 */
	public void setAccownnum_3(String accownnum_3) {
		this.accownnum_3 = accownnum_3;
	}

	/**
	 * @return the regimen_3
	 */
	public String getRegimen_3() {
		return regimen_3;
	}

	/**
	 * @param regimen_3 the regimen_3 to set
	 */
	public void setRegimen_3(String regimen_3) {
		this.regimen_3 = regimen_3;
	}

	/**
	 * @return the accownname_3
	 */
	public String getAccownname_3() {
		return accownname_3;
	}

	/**
	 * @param accownname_3 the accownname_3 to set
	 */
	public void setAccownname_3(String accownname_3) {
		this.accownname_3 = accownname_3;
	}

	/**
	 * @return the accownnum_4
	 */
	public String getAccownnum_4() {
		return accownnum_4;
	}

	/**
	 * @param accownnum_4 the accownnum_4 to set
	 */
	public void setAccownnum_4(String accownnum_4) {
		this.accownnum_4 = accownnum_4;
	}

	/**
	 * @return the regimen_4
	 */
	public String getRegimen_4() {
		return regimen_4;
	}

	/**
	 * @param regimen_4 the regimen_4 to set
	 */
	public void setRegimen_4(String regimen_4) {
		this.regimen_4 = regimen_4;
	}

	/**
	 * @return the accownname_4
	 */
	public String getAccownname_4() {
		return accownname_4;
	}

	/**
	 * @param accownname_4 the accownname_4 to set
	 */
	public void setAccownname_4(String accownname_4) {
		this.accownname_4 = accownname_4;
	}

	/**
	 * @return the accownnum_5
	 */
	public String getAccownnum_5() {
		return accownnum_5;
	}

	/**
	 * @param accownnum_5 the accownnum_5 to set
	 */
	public void setAccownnum_5(String accownnum_5) {
		this.accownnum_5 = accownnum_5;
	}

	/**
	 * @return the regimen_5
	 */
	public String getRegimen_5() {
		return regimen_5;
	}

	/**
	 * @param regimen_5 the regimen_5 to set
	 */
	public void setRegimen_5(String regimen_5) {
		this.regimen_5 = regimen_5;
	}

	/**
	 * @return the accownname_5
	 */
	public String getAccownname_5() {
		return accownname_5;
	}

	/**
	 * @param accownname_5 the accownname_5 to set
	 */
	public void setAccownname_5(String accownname_5) {
		this.accownname_5 = accownname_5;
	}

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
	 * @return the ordenErrorsList
	 */
	public ArrayList<ContractMessageErrors> getOrdenErrorsList() {
		return ordenErrorsList;
	}



	/**
	 * @param ordenErrorsList the ordenErrorsList to set
	 */
	public void setOrdenErrorsList(ArrayList<ContractMessageErrors> ordenErrorsList) {
		this.ordenErrorsList = ordenErrorsList;
	}



	/**
	 * @param pdfTemplateBinding the pdfTemplateBinding to set
	 */
	public void setPdfTemplateBinding(PdfTemplateBindingContract pdfTemplateBinding) {
		this.pdfTemplateBinding = pdfTemplateBinding;
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



	public String getAccownnum_6() {
		return accownnum_6;
	}



	public void setAccownnum_6(String accownnum_6) {
		this.accownnum_6 = accownnum_6;
	}



	public String getRegimen_6() {
		return regimen_6;
	}



	public void setRegimen_6(String regimen_6) {
		this.regimen_6 = regimen_6;
	}



	public String getAccownname_6() {
		return accownname_6;
	}



	public void setAccownname_6(String accownname_6) {
		this.accownname_6 = accownname_6;
	}



	public String getAccownnum_7() {
		return accownnum_7;
	}



	public void setAccownnum_7(String accownnum_7) {
		this.accownnum_7 = accownnum_7;
	}



	public String getRegimen_7() {
		return regimen_7;
	}



	public void setRegimen_7(String regimen_7) {
		this.regimen_7 = regimen_7;
	}



	public String getAccownname_7() {
		return accownname_7;
	}



	public void setAccownname_7(String accownname_7) {
		this.accownname_7 = accownname_7;
	}



	public String getAccownnum_8() {
		return accownnum_8;
	}



	public void setAccownnum_8(String accownnum_8) {
		this.accownnum_8 = accownnum_8;
	}



	public String getRegimen_8() {
		return regimen_8;
	}



	public void setRegimen_8(String regimen_8) {
		this.regimen_8 = regimen_8;
	}



	public String getAccownname_8() {
		return accownname_8;
	}



	public void setAccownname_8(String accownname_8) {
		this.accownname_8 = accownname_8;
	}
	
	
	public void metodoParaHacerMasPeso8(){
		System.out.println("metodo dummy para hacer peso para harvest");
	}	
	
}
