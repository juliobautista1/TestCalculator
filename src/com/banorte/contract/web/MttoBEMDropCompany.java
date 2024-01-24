/**
 * 
 */
package com.banorte.contract.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.Template;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.AttrConstants;
import com.banorte.contract.util.MttoType;
import com.banorte.contract.util.MttoUtil;
import com.banorte.contract.util.ProductType;
import com.banorte.contract.util.pdf.PdfTemplateBinding;
import com.banorte.contract.util.pdf.PdfTemplateBindingContract;

/**
 * @author omar
 *
 */
public class MttoBEMDropCompany  extends ContractAbstractMB {
	
	private Integer mttoType;
	private String bemnumber;
	private String dropMotiveSelected;
	private String fiscalFullName;
	private String legalRepresentativeFullName;   //	AttrConstants.LEGALREPRESENTATIVE_NAME_1
	private String comments;
	
	private String dropMotiveOption1;
	private String dropMotiveOption2;
	private String dropMotiveOption3;
	private String dropMotiveOption4;
	private String dropMotiveOption5;
	private String dropMotiveOption6;
	private String dropMotiveOption7;
	private String dropMotiveOption8;
	private String dropMotiveOption9;
	private String dropMotiveOption10;
	private String dropMotiveOption11;

    private String NAME_TEMPLATE ="nameTemplate";
    private String PATHTEMPLATE ="pathTemplate";
	
	private PdfTemplateBindingContract pdfTemplateBinding;
	private SelectItem[] motive_array;  
	
	

	public MttoBEMDropCompany() {
		super();
		this.mttoType = MttoType.BAJA_EMPRESA.getMttoTypeId();
		this.setClient_sic(ApplicationConstants.ZERO_STRING);
		setStatusContract(statusBean.findById(new Integer(ApplicationConstants.DEFAULT_VERSION_CONTRACT)));	
	}

	@Override
	public void setResetForm() {
		this.bemnumber 						= ApplicationConstants.EMPTY_STRING;
		this.dropMotiveSelected				= ApplicationConstants.EMPTY_STRING;
		this.legalRepresentativeFullName	= ApplicationConstants.EMPTY_STRING;
		this.comments 						= ApplicationConstants.EMPTY_STRING;
		this.fiscalFullName					= ApplicationConstants.EMPTY_STRING;	
		
		
		clearFields();
		setProduct(productBean.findById(new Integer(ProductType.MTTO_DROP_COMPANY.value()))); 
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
				AttrConstants.DROP_MOTIVE,AttrConstants.COMMENTS,
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
			this.setDropMotiveSelected(map.get(AttrConstants.DROP_MOTIVE));
			this.setComments( map.get(AttrConstants.COMMENTS));
			
			
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
		setProduct(productBean.findById(new Integer(ProductType.MTTO_DROP_COMPANY.value())));
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
		addToList(list,AttrConstants.DROP_MOTIVE, getDropMotiveSelected());
		addToList(list,AttrConstants.DROP_MOTIVE_OPTION_1, getDropMotiveOption1());
		addToList(list,AttrConstants.DROP_MOTIVE_OPTION_2, getDropMotiveOption2());
		addToList(list,AttrConstants.DROP_MOTIVE_OPTION_3, getDropMotiveOption3());
		addToList(list,AttrConstants.DROP_MOTIVE_OPTION_4, getDropMotiveOption4());
		addToList(list,AttrConstants.DROP_MOTIVE_OPTION_5, getDropMotiveOption5());
		addToList(list,AttrConstants.DROP_MOTIVE_OPTION_6, getDropMotiveOption6());
		addToList(list,AttrConstants.DROP_MOTIVE_OPTION_7, getDropMotiveOption7());
		addToList(list,AttrConstants.DROP_MOTIVE_OPTION_8, getDropMotiveOption8());
		addToList(list,AttrConstants.DROP_MOTIVE_OPTION_9, getDropMotiveOption9());
		addToList(list,AttrConstants.DROP_MOTIVE_OPTION_10, getDropMotiveOption10());
		addToList(list,AttrConstants.DROP_MOTIVE_OPTION_11, getDropMotiveOption11());
		
		addToList(list,AttrConstants.COMMENTS, getComments());
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
		this.setValidationMtto( Boolean.FALSE);
		this.setValidationEDO(Boolean.FALSE);
		generalInfoErrorsList.clear();
		processSelectedMotive();
		return saveCompleteMtto();
	}
	
	private void processSelectedMotive(){
		deleteSelectedMotive();
		String opcion = this.getDropMotiveSelected();
		if( opcion.equals(ApplicationConstants.MOTIVE_DROP_1)){
			this.setDropMotiveOption1( ApplicationConstants.OPCION_SELECTED);
		}else if( opcion.equals(ApplicationConstants.MOTIVE_DROP_2)){
			this.setDropMotiveOption2( ApplicationConstants.OPCION_SELECTED);
		} else if( opcion.equals(ApplicationConstants.MOTIVE_DROP_3)){
			this.setDropMotiveOption3( ApplicationConstants.OPCION_SELECTED);
		}else if( opcion.equals(ApplicationConstants.MOTIVE_DROP_4)){
			this.setDropMotiveOption4( ApplicationConstants.OPCION_SELECTED);
		}else if( opcion.equals(ApplicationConstants.MOTIVE_DROP_5)){
			this.setDropMotiveOption5( ApplicationConstants.OPCION_SELECTED);
		}else if( opcion.equals(ApplicationConstants.MOTIVE_DROP_6)){
			this.setDropMotiveOption6( ApplicationConstants.OPCION_SELECTED);
		}else if( opcion.equals(ApplicationConstants.MOTIVE_DROP_7)){
			this.setDropMotiveOption7( ApplicationConstants.OPCION_SELECTED);
		}else if( opcion.equals(ApplicationConstants.MOTIVE_DROP_8)){
			this.setDropMotiveOption8( ApplicationConstants.OPCION_SELECTED);
		}else if( opcion.equals(ApplicationConstants.MOTIVE_DROP_9)){
			this.setDropMotiveOption9( ApplicationConstants.OPCION_SELECTED);
		}else if( opcion.equals(ApplicationConstants.MOTIVE_DROP_10)){
			this.setDropMotiveOption10( ApplicationConstants.OPCION_SELECTED);
		}else if( opcion.equals(ApplicationConstants.MOTIVE_DROP_11)){
			this.setDropMotiveOption11( ApplicationConstants.OPCION_SELECTED);
		}
	}
	
	private void deleteSelectedMotive(){
		
		this.dropMotiveOption1  	= ApplicationConstants.EMPTY_STRING;
		this.dropMotiveOption2  	= ApplicationConstants.EMPTY_STRING;
		this.dropMotiveOption3  	= ApplicationConstants.EMPTY_STRING;
		this.dropMotiveOption4  	= ApplicationConstants.EMPTY_STRING;
		this.dropMotiveOption5  	= ApplicationConstants.EMPTY_STRING;
		this.dropMotiveOption6  	= ApplicationConstants.EMPTY_STRING;
		this.dropMotiveOption7  	= ApplicationConstants.EMPTY_STRING;
		this.dropMotiveOption8  	= ApplicationConstants.EMPTY_STRING;
		this.dropMotiveOption9  	= ApplicationConstants.EMPTY_STRING;
		this.dropMotiveOption10  	= ApplicationConstants.EMPTY_STRING;
		this.dropMotiveOption11 	= ApplicationConstants.EMPTY_STRING;
		
	}
	
	

	//Gettet's and Setter's

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
	 * @return the dropMotiveSelected
	 */
	public String getDropMotiveSelected() {
		return dropMotiveSelected;
	}

	/**
	 * @param dropMotiveSelected the dropMotiveSelected to set
	 */
	public void setDropMotiveSelected(String dropMotiveSelected) {
		this.dropMotiveSelected = dropMotiveSelected;
	}

	/**
	 * @return the legalRepresentativeName
	 */
	public String getLegalRepresentativeFullName() {
		return legalRepresentativeFullName;
	}

	/**
	 * @param legalRepresentativeName the legalRepresentativeName to set
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
	 * @return the motive_list
	 */
	public SelectItem[] getMotive_array() {
		if (this.motive_array == null) {
        	this.motive_array = new MttoUtil().getattributeOptionArray(AttrConstants.DROP_MOTIVE);
        }
        
        return this.motive_array;
	}

	/**
	 * @param motive_list the motive_list to set
	 */
	public void setMotive_array(SelectItem[] motive_array) {
		this.motive_array = motive_array;
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
	 * @return the dropMotiveOption1
	 */
	public String getDropMotiveOption1() {
		return dropMotiveOption1;
	}

	/**
	 * @param dropMotiveOption1 the dropMotiveOption1 to set
	 */
	public void setDropMotiveOption1(String dropMotiveOption1) {
		this.dropMotiveOption1 = dropMotiveOption1;
	}

	/**
	 * @return the dropMotiveOption2
	 */
	public String getDropMotiveOption2() {
		return dropMotiveOption2;
	}

	/**
	 * @param dropMotiveOption2 the dropMotiveOption2 to set
	 */
	public void setDropMotiveOption2(String dropMotiveOption2) {
		this.dropMotiveOption2 = dropMotiveOption2;
	}

	/**
	 * @return the dropMotiveOption3
	 */
	public String getDropMotiveOption3() {
		return dropMotiveOption3;
	}

	/**
	 * @param dropMotiveOption3 the dropMotiveOption3 to set
	 */
	public void setDropMotiveOption3(String dropMotiveOption3) {
		this.dropMotiveOption3 = dropMotiveOption3;
	}

	/**
	 * @return the dropMotiveOption4
	 */
	public String getDropMotiveOption4() {
		return dropMotiveOption4;
	}

	/**
	 * @param dropMotiveOption4 the dropMotiveOption4 to set
	 */
	public void setDropMotiveOption4(String dropMotiveOption4) {
		this.dropMotiveOption4 = dropMotiveOption4;
	}

	/**
	 * @return the dropMotiveOption5
	 */
	public String getDropMotiveOption5() {
		return dropMotiveOption5;
	}

	/**
	 * @param dropMotiveOption5 the dropMotiveOption5 to set
	 */
	public void setDropMotiveOption5(String dropMotiveOption5) {
		this.dropMotiveOption5 = dropMotiveOption5;
	}

	/**
	 * @return the dropMotiveOption6
	 */
	public String getDropMotiveOption6() {
		return dropMotiveOption6;
	}

	/**
	 * @param dropMotiveOption6 the dropMotiveOption6 to set
	 */
	public void setDropMotiveOption6(String dropMotiveOption6) {
		this.dropMotiveOption6 = dropMotiveOption6;
	}

	/**
	 * @return the dropMotiveOption7
	 */
	public String getDropMotiveOption7() {
		return dropMotiveOption7;
	}

	/**
	 * @param dropMotiveOption7 the dropMotiveOption7 to set
	 */
	public void setDropMotiveOption7(String dropMotiveOption7) {
		this.dropMotiveOption7 = dropMotiveOption7;
	}

	/**
	 * @return the dropMotiveOption8
	 */
	public String getDropMotiveOption8() {
		return dropMotiveOption8;
	}

	/**
	 * @param dropMotiveOption8 the dropMotiveOption8 to set
	 */
	public void setDropMotiveOption8(String dropMotiveOption8) {
		this.dropMotiveOption8 = dropMotiveOption8;
	}

	/**
	 * @return the dropMotiveOption9
	 */
	public String getDropMotiveOption9() {
		return dropMotiveOption9;
	}

	/**
	 * @param dropMotiveOption9 the dropMotiveOption9 to set
	 */
	public void setDropMotiveOption9(String dropMotiveOption9) {
		this.dropMotiveOption9 = dropMotiveOption9;
	}

	/**
	 * @return the dropMotiveOption10
	 */
	public String getDropMotiveOption10() {
		return dropMotiveOption10;
	}

	/**
	 * @param dropMotiveOption10 the dropMotiveOption10 to set
	 */
	public void setDropMotiveOption10(String dropMotiveOption10) {
		this.dropMotiveOption10 = dropMotiveOption10;
	}

	/**
	 * @return the dropMotiveOption11
	 */
	public String getDropMotiveOption11() {
		return dropMotiveOption11;
	}

	/**
	 * @param dropMotiveOption11 the dropMotiveOption11 to set
	 */
	public void setDropMotiveOption11(String dropMotiveOption11) {
		this.dropMotiveOption11 = dropMotiveOption11;
	}
	
	
	
	public void metodoParaHacerMasPeso5(){
		System.out.println("metodo dummy para hacer peso para harvest");
	}	
}
