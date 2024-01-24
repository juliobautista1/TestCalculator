/**
 * 
 */
package com.banorte.contract.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import com.banorte.contract.model.Cities;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.ContractMessageErrors;
import com.banorte.contract.model.Payrate;
import com.banorte.contract.model.Template;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.AttrConstants;
import com.banorte.contract.util.ContractCDUtil;
import com.banorte.contract.util.ProductType;
import com.banorte.contract.util.pdf.PdfTemplateBinding;
import com.banorte.contract.util.pdf.PdfTemplateBindingContract;

/**
 * @author omar
 * 
 */
public class ContractCdMB extends ContractAbstractMB {

	private String bemnumber;
	private String affiliation_accountnumbermn;
	private String affiliation_Through; // medioAfiliacion;
	private String affiliation_formatLayout;
	private String affiliation_payworksClabe;
	private String affiliation_autoRegister; // registroAutomatico;
	private String affiliation_retries;
	private String affiliation_maxAmount;
	private String affiliation_internalcredithistory;
	private String affiliation_externalcredithistory;
	private String affiliation_havedepositcompany;
	private String affiliation_depositcompany;
	private String affiliation_depositamount;
	private String affiliation_duedate;
	private String affiliation_officerdepositexent;
	private String referenceName;
	private String referenceLength;
	private String referenceType;
	private String referenceDV;
	private String referenceModuleType;
	private String referenceRequired;
	private String batchCommission;
	private String batchCommissionType;
	private String banorteOE;
	private String banorteONE;
	private String otrosOE;
	private String otrosONE;
	private String onLineCommission;
	private String onLineCommissionType;
	private String onLineBanorteOE;
	private String onLineBanorteONE;
	private String branchTransmission;
	private String comissionBatchCode;
	private String comissionOnlineCode;
	private String comissionBatchCodeSelected;
	private String comissionOnlineCodeSelected;
	private String monthlyAmount;

	// atributos para "Afiliacion Banorte O Emisor"  Select
	private String affiliation_SelectedBanorte;
	private String affiliation_NoselectedBanorte;
	private String affiliation_SelectedEmisor;
	private String affiliation_NoselectedEmisor;
	 
	// atributos para Format Layout Select
	private String formatLayoutSelectedSiga;
	
	// atributos para Payworks Clabe Select
	private String payworksClabeSelectSi;
	private String payworksClabeSelectNo;
	
	// atributos para ModuleType Select
	private String moduleTypeSelect10;
	private String moduleTypeSelect11;
	private String moduleTypeSelect97;
	private String moduleTypeSelectOtro;
	
	// atributos para Tipo de Proceso
	private String batchCommissionSelect;
	private String onlineCommissionSelect;
	
	private SelectItem[] affiliationThroughArray;
	private SelectItem[] formatLayoutArray;
	private SelectItem[] buroInternoArray;
	private SelectItem[] buroNacionalArray;
	private SelectItem[] referenceTypeArray;
	private SelectItem[] referenceModuleArray;
	private SelectItem[] batchProcessArray;
	private SelectItem[] onlineProcessArray;
	private SelectItem[] batchCommisionsArray;
	private SelectItem[] onlineCommisionsArray;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList<ContractMessageErrors> ordenErrorsList = new ArrayList();
	private HashMap<String, Payrate> comissionBatchHash;
	private HashMap<String, Payrate> comissionOnLineHash;
	private ArrayList<Payrate> batchList;
	private ArrayList<Payrate> onlineList;

	private PdfTemplateBindingContract pdfTemplateBinding;
	
	private String cdemitter; //para numero de emisora
	
	//PayworksClabe
	private String issuerName;
	private String issuerAccount;
	private String issuerAddress;
	private String issuerStreet;
	private String issuerExtNum;
	private String issuerIntNum;
	private String issuerColony;
	private String issuerZipCode;
	private String issuerState;
	private String issuerCity;
	private String issuerContactEmail;
	private String issuerContactName;
	private String issuerContactAreaCode;
	private String issuerContactPhone;
	private String issuerCategoryCode;
	private SelectItem[] issuerCategoriesArray;
	private SelectItem[] issuerCitiesArray;
	private boolean isChangeFianza;

    private String NAME_TEMPLATE ="nameTemplate";
    private String PATHTEMPLATE ="pathTemplate";
    
    //F-113829 Actualización Formatos Cobranza / abril 2022
    private String creditoActivoRetrasoSi;//RF002
    private String creditoActivoRetrasoNo;//RF002
    private String creditoHistoricoRetrasoSi;//RF002
    private String creditoHistoricoRetrasoNo;//RF002
    private String nombreDelTerritorioDirector;
    //private String rfcRepresentanteLegal1;
    //private String rfcRepresentanteLegal2;
    private String nivelRiesgoCD;
	
	@Override
	public void setResetForm() {
		setIsChangeFianza(false);
		
		bemnumber 							= ApplicationConstants.EMPTY_STRING;
		affiliation_accountnumbermn 		= ApplicationConstants.EMPTY_STRING;
		affiliation_Through 				= ApplicationConstants.EMPTY_STRING;
		affiliation_formatLayout 			= ApplicationConstants.EMPTY_STRING;
		affiliation_payworksClabe 			= ApplicationConstants.EMPTY_STRING;
		affiliation_autoRegister 			= ApplicationConstants.EMPTY_STRING;
		affiliation_retries 				= ApplicationConstants.ZERO_STRING;
		affiliation_maxAmount 				= ApplicationConstants.EMPTY_STRING;
		affiliation_internalcredithistory 	= ApplicationConstants.EMPTY_STRING;
		affiliation_externalcredithistory 	= ApplicationConstants.EMPTY_STRING;
		affiliation_havedepositcompany 		= ApplicationConstants.EMPTY_STRING;
		affiliation_depositcompany 			= ApplicationConstants.EMPTY_STRING;
		affiliation_depositamount 			= ApplicationConstants.EMPTY_STRING;
		affiliation_duedate 				= ApplicationConstants.EMPTY_STRING;
		referenceName 						= ApplicationConstants.EMPTY_STRING;
		referenceLength 					= ApplicationConstants.EMPTY_STRING;
		referenceType 						= ApplicationConstants.EMPTY_STRING;
		referenceDV 						= ApplicationConstants.EMPTY_STRING;
		referenceRequired 					= ApplicationConstants.EMPTY_STRING;
		referenceModuleType 				= ApplicationConstants.EMPTY_STRING;
		batchCommissionType 				= ApplicationConstants.EMPTY_STRING;
		banorteOE 							= ApplicationConstants.BATCH_BANORTE_DEFAULT;
		banorteONE 							= ApplicationConstants.BATCH_BANORTE_DEFAULT;
		otrosOE 							= ApplicationConstants.BATCH_OTROS_DEFAULT;
		otrosONE 							= ApplicationConstants.BATCH_OTROS_DEFAULT;
		onLineCommissionType 				= ApplicationConstants.EMPTY_STRING;
		onLineBanorteOE 					= ApplicationConstants.ONLINE_BANORTE_DEFAULT;
		onLineBanorteONE 					= ApplicationConstants.ONLINE_BANORTE_DEFAULT;
		comissionBatchCodeSelected 			= ApplicationConstants.EMPTY_STRING;
		comissionOnlineCodeSelected 		= ApplicationConstants.EMPTY_STRING;
		branchTransmission 					= ApplicationConstants.TRANSMITIR_SUCURSAL;
		comissionBatchCode 					= ApplicationConstants.COMISSION_BATCH_CODE_DEFAULT;
		onLineCommission 					= Boolean.FALSE.toString();
		affiliation_officerdepositexent 	= ApplicationConstants.EMPTY_STRING;
		monthlyAmount						= ApplicationConstants.EMPTY_STRING;
		issuerName=ApplicationConstants.EMPTY_STRING;
		issuerAccount=ApplicationConstants.EMPTY_STRING;
		issuerStreet=ApplicationConstants.EMPTY_STRING;
		issuerExtNum=ApplicationConstants.EMPTY_STRING;
		issuerIntNum=ApplicationConstants.EMPTY_STRING;
		issuerColony=ApplicationConstants.EMPTY_STRING;
		issuerZipCode=ApplicationConstants.EMPTY_STRING;
		issuerState=ApplicationConstants.EMPTY_STRING;
		issuerCity=ApplicationConstants.EMPTY_STRING;
		issuerContactEmail=ApplicationConstants.EMPTY_STRING;
		issuerContactName=ApplicationConstants.EMPTY_STRING;
		issuerContactAreaCode=ApplicationConstants.EMPTY_STRING;
		issuerContactPhone=ApplicationConstants.EMPTY_STRING;
		issuerCategoryCode=ApplicationConstants.EMPTY_STRING;
		setBranchDirectorName(ApplicationConstants.EMPTY_STRING);
		setBranchDirectorLastName(ApplicationConstants.EMPTY_STRING);
		setBranchDirectorMothersLn(ApplicationConstants.EMPTY_STRING);
		setBranchDirectorNumber(ApplicationConstants.EMPTY_STRING);
		setBranchDirectorposition(ApplicationConstants.EMPTY_STRING);
		loadInfoBatchComission();
		clearFields();
		setProduct(productBean.findById(new Integer(ProductType.COBRANZA_DOMICILIADA.value()))); 
		setStatusContract(statusBean.findById(new Integer(1))); // Status Nuevo
		
		//F-113829 Actualización Formatos Cobranza / abril 2022
		creditoActivoRetrasoSi 							= ApplicationConstants.EMPTY_STRING;
		creditoActivoRetrasoNo 							= ApplicationConstants.EMPTY_STRING;
		creditoHistoricoRetrasoSi 						= ApplicationConstants.EMPTY_STRING;
		creditoHistoricoRetrasoNo 						= ApplicationConstants.EMPTY_STRING;
		nombreDelTerritorioDirector 					= ApplicationConstants.EMPTY_STRING;
		//rfcRepresentanteLegal1 							= ApplicationConstants.EMPTY_STRING;
		//rfcRepresentanteLegal2 							= ApplicationConstants.EMPTY_STRING;
		nivelRiesgoCD 					= ApplicationConstants.EMPTY_STRING;
	}

	public ContractCdMB() {
		super();
		initInfo();
		setStatusContract(statusBean.findById(new Integer(ApplicationConstants.DEFAULT_VERSION_CONTRACT)));

	}
	
	private void initInfo(){
		ContractCDUtil cdUtil 		= new ContractCDUtil();
		batchList 					= cdUtil.LoadComissionBatch();
		onlineList 					= cdUtil.LoadComissionOnLine();
		comissionBatchHash 			= cdUtil.loadHashComission(batchList);
		comissionOnLineHash 		= cdUtil.loadHashComission(onlineList);
		batchCommisionsArray		= this.convertPayrateToSelectItem(batchList, Boolean.FALSE);
		onlineCommisionsArray		= this.convertPayrateToSelectItem(onlineList, Boolean.TRUE);
		errorsList.clear();
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
		setIsChangeFianza(false);
		initInfo();
		FacesContext fCtx 			= FacesContext.getCurrentInstance();
		Map<String, String> params 	= fCtx.getExternalContext().getRequestParameterMap();
		Integer idContract 			= Integer.parseInt(params.get("contractId"));
		
		String CD_FILLS[] = { AttrConstants.BEM_NUMBER,AttrConstants.AFFILIATION_ACCOUNT_NUMBER_MN,AttrConstants.AFFILIATION_THROUGH,
				AttrConstants.AFFILIATION_FORMATLAYOUT,AttrConstants.AFFILIATION_PAYWORKSCLABE,AttrConstants.AFFILIATION_AUTOREG,
				AttrConstants.AFFILIATION_RETRIES,AttrConstants.AFFILIATION_MAXAMOUNT,AttrConstants.AFFILIATION_INTERNALCREDITHIST,
				AttrConstants.AFFILIATION_EXTERNALCREDITHIST,AttrConstants.AFFILIATION_HAVEDEPOSITCOMP,AttrConstants.AFFILIATION_DEPOSITCOMPANY,
				AttrConstants.AFFILIATION_DEPOSITAMOUNT,AttrConstants.AFFILIATION_DUEDATE,AttrConstants.REFERENCE_NAME,
				AttrConstants.REFERENCE_LENGTH,AttrConstants.REFERENCE_TYPE,AttrConstants.REFERENCE_DV,
				AttrConstants.REFERENCE_MODULETYPE,AttrConstants.REFERENCE_REQUIRED,AttrConstants.BATCH_COMISSIONTYPE,
				AttrConstants.BANORTE_OE,AttrConstants.BANORTE_ONE,AttrConstants.OTHER_OE,
				AttrConstants.OTHER_ONE,AttrConstants.ONLINE_COMISSIONTYPE,AttrConstants.ONLINE_BANORTE_OE,
				AttrConstants.ONLINE_BANORTE_ONE,AttrConstants.BRANCH_TRANSMISSION,AttrConstants.BATCH_COMISSION_CODE,
				AttrConstants.ONLINE_COMISSION_CODE, AttrConstants.AFFILIATION_OFFICER_DEPOSIT_EXNT,AttrConstants.MONTHLY_AMOUNT,
				AttrConstants.SELECTED_OPTION_NO_BANORTE,AttrConstants.SELECTED_OPTION_BANORTE,
				AttrConstants.SELECTED_OPTION_EMISOR,AttrConstants.SELECTED_OPTION_NO_EMISOR,
				AttrConstants.RED,"issuerName","issuerAccount","issuerStreet","issuerExtNum","issuerIntNum","issuerColony","issuerZipCode",
				"issuerState","issuerCity","issuerContactEmail","issuerContactName","issuerContactAreaCode","issuerContactPhone",
				"issuerCategoryCode",AttrConstants.BRANCH_DIRECTOR_NUMBER,AttrConstants.BRANCH_DIRECTOR_NAME,AttrConstants.BRANCH_DIRECTOR_LASTNAME,
				AttrConstants.BRANCH_DIRECTOR_MOTHERSLN,AttrConstants.BRANCH_DIRECTOR_POSITION,
				"creditoActivoRetrasoSi",
				"creditoActivoRetrasoNo",
				"creditoHistoricoRetrasoSi",
				"creditoHistoricoRetrasoNo",
				"nombreDelTerritorioDirector",
				//"rfcRepresentanteLegal1",
				//"rfcRepresentanteLegal2",
				"nivelRiesgoCD",
		};

		Contract contract_ = contractBean.findById(idContract);
		if (contract_.getContractAttributeCollection() != null) {
			Map<String, String> map = this.getContractAttributeFills(contract_,CD_FILLS);

			this.setBemnumber( map.get(AttrConstants.BEM_NUMBER) );
			this.setAffiliation_accountnumbermn( map.get(AttrConstants.AFFILIATION_ACCOUNT_NUMBER_MN) );
			this.setAffiliation_Through( map.get( AttrConstants.AFFILIATION_THROUGH ) );
			this.setAffiliation_formatLayout( map.get( AttrConstants.AFFILIATION_FORMATLAYOUT ) );
			this.setAffiliation_payworksClabe( map.get( AttrConstants.AFFILIATION_PAYWORKSCLABE ) );
			this.setAffiliation_autoRegister( map.get( AttrConstants.AFFILIATION_AUTOREG ) );
			this.setAffiliation_retries( map.get( AttrConstants.AFFILIATION_RETRIES ) );
			this.setAffiliation_maxAmount( map.get( AttrConstants.AFFILIATION_MAXAMOUNT ) );
			this.setAffiliation_internalcredithistory( map.get( AttrConstants.AFFILIATION_INTERNALCREDITHIST ) );
			this.setAffiliation_externalcredithistory( map.get( AttrConstants.AFFILIATION_EXTERNALCREDITHIST ) );
			this.setAffiliation_havedepositcompany( map.get( AttrConstants.AFFILIATION_HAVEDEPOSITCOMP ) );
			this.setAffiliation_depositcompany( map.get( AttrConstants.AFFILIATION_DEPOSITCOMPANY ) );
			this.setAffiliation_depositamount( map.get( AttrConstants.AFFILIATION_DEPOSITAMOUNT));
			this.setAffiliation_duedate( map.get( AttrConstants.AFFILIATION_DUEDATE ) );
			this.setAffiliation_officerdepositexent( map.get(AttrConstants.AFFILIATION_OFFICER_DEPOSIT_EXNT) );
			this.setReferenceName( map.get( AttrConstants.REFERENCE_NAME ) );
			this.setReferenceLength( map.get( AttrConstants.REFERENCE_LENGTH ) );
			this.setReferenceType( map.get( AttrConstants.REFERENCE_TYPE ) );
			this.setReferenceDV( map.get( AttrConstants.REFERENCE_DV ) );
			this.setReferenceModuleType( map.get( AttrConstants.REFERENCE_MODULETYPE ) );
			this.setBatchCommissionType( map.get( AttrConstants.BATCH_COMISSIONTYPE ) );
			this.setBanorteOE( map.get( AttrConstants.BANORTE_OE ) );
			this.setBanorteONE( map.get( AttrConstants.BANORTE_ONE ) );
			this.setOtrosOE( map.get( AttrConstants.OTHER_OE ) );
			this.setOtrosONE( map.get( AttrConstants.ONLINE_BANORTE_ONE ) );
			this.setOnLineCommissionType( map.get( AttrConstants.ONLINE_COMISSIONTYPE ) );
			this.setOnLineBanorteOE( map.get( AttrConstants.ONLINE_BANORTE_OE ) );
			this.setOnLineBanorteONE( map.get( AttrConstants.ONLINE_BANORTE_ONE ) );
			this.setBranchTransmission( map.get( AttrConstants.BRANCH_TRANSMISSION ) );
			this.setComissionBatchCode( map.get( AttrConstants.BATCH_COMISSION_CODE ) );
			this.setComissionOnlineCode( map.get( AttrConstants.ONLINE_COMISSION_CODE ) );
			this.setClient_sic(map.get("client_sic"));
			this.setMonthlyAmount( map.get( AttrConstants.MONTHLY_AMOUNT ));
			
			//Datos del Representante Legal o Apoderados
			this.loadToEditLegalrepresentative1(map);
			this.loadToEditLegalrepresentative2(map);
	
			//Firmas Adicionales Facultadas
			this.setClientcontact_name1(map.get(AttrConstants.CLIENTCONTACT_NAME_1));
			this.setClientcontact_lastname1(map.get(AttrConstants.CLIENTCONTACT_LASTNAME_1));
			this.setClientcontact_mothersln1(map.get(AttrConstants.CLIENTCONTACT_MOTHERSNAME_1));
			this.setClientcontact_position1(map.get(AttrConstants.CLIENTCONTACT_POSITION_1));
			
			this.setClientcontact_name2(map.get(AttrConstants.CLIENTCONTACT_NAME_2));
			this.setClientcontact_lastname2(map.get(AttrConstants.CLIENTCONTACT_LASTNAME_2));
			this.setClientcontact_mothersln2(map.get(AttrConstants.CLIENTCONTACT_MOTHERSNAME_2));
			this.setClientcontact_position2(map.get(AttrConstants.CLIENTCONTACT_POSITION_2));
			
			this.setClientcontact_name3(map.get(AttrConstants.CLIENTCONTACT_NAME_3));
			this.setClientcontact_lastname3(map.get(AttrConstants.CLIENTCONTACT_LASTNAME_3));
			this.setClientcontact_mothersln3(map.get(AttrConstants.CLIENTCONTACT_MOTHERSNAME_3));
			this.setClientcontact_position3(map.get(AttrConstants.CLIENTCONTACT_POSITION_3));
			
			this.setClientcontact_name4(map.get(AttrConstants.CLIENTCONTACT_NAME_4));
			this.setClientcontact_lastname4(map.get(AttrConstants.CLIENTCONTACT_LASTNAME_4));
			this.setClientcontact_mothersln4(map.get(AttrConstants.CLIENTCONTACT_MOTHERSNAME_4));
			this.setClientcontact_position4(map.get(AttrConstants.CLIENTCONTACT_POSITION_4));
			
			// DATOS EMPLEADO EBANKING ESPECIALIZADO
			
			this.setOfficerebankingEspEmpnumber( map.get(AttrConstants.OFFICER_EBANKING_ESP_NUMBER));
			this.setOfficerebankingEspName( map.get(AttrConstants.OFFICER_EBANKING_ESP_NAME));
			this.setOfficerebankingEspLastname( map.get(AttrConstants.OFFICER_EBANKING_ESP_LASTNAME));
			this.setOfficerebankingEspMothersln( map.get(AttrConstants.OFFICER_EBANKING_ESP_MOTHERSNAME));
			this.setOfficerebankingEspPosition( map.get(AttrConstants.OFFICER_EBANKING_ESP_POSITION) );
			
			// DATOS EMPLEADO EBANKING TERRITORIAL
			
			this.setOfficerebankingTempnumber( map.get(AttrConstants.OFFICER_EBANKING_T_NUMBER));
			this.setOfficerebankingTname( map.get(AttrConstants.OFFICER_EBANKING_T_NAME));
			this.setOfficerebankingTlastname( map.get(AttrConstants.OFFICER_EBANKING_T_LASTNAME));
			this.setOfficerebankingTmothersln( map.get(AttrConstants.OFFICER_EBANKING_T_MOTHERSNAME));
			this.setOfficerebankingTposition( map.get(AttrConstants.OFFICER_EBANKING_T_POSITION));
			
			//DATOS DIRECTOR SUCURSAL
			this.setBranchDirectorNumber( map.get(AttrConstants.BRANCH_DIRECTOR_NUMBER));
			this.setBranchDirectorName(map.get(AttrConstants.BRANCH_DIRECTOR_NAME));
			this.setBranchDirectorLastName(map.get(AttrConstants.BRANCH_DIRECTOR_LASTNAME));
			this.setBranchDirectorMothersLn(map.get(AttrConstants.BRANCH_DIRECTOR_MOTHERSLN));
			this.setBranchDirectorposition(map.get(AttrConstants.BRANCH_DIRECTOR_POSITION));
			

			this.loadToEditGeneralInfo(map);
			this.loadToEditBranchInfo(map);
			this.loadToEditEbankingInfo(map);
			this.loadToEditOfficerInfo(map);
			this.loadToEditOfficerRep1Info(map);
			this.loadToEditOfficerRep2Info(map);
			
			red=(map.get(AttrConstants.RED)!=null?map.get(AttrConstants.RED):"0");//los contratos sin red por default son "Banorte"
			//payworksClabe
			issuerName=map.get("issuerName");
			issuerAccount=map.get("issuerAccount");
			issuerStreet=map.get("issuerStreet");
			issuerExtNum=map.get("issuerExtNum");
			issuerIntNum=map.get("issuerIntNum");
			issuerColony=map.get("issuerColony");
			issuerZipCode=map.get("issuerZipCode");
			issuerState=map.get("issuerState");
			issuerCity=map.get("issuerCity");
			issuerContactEmail=map.get("issuerContactEmail");
			issuerContactName=map.get("issuerContactName");
			issuerContactAreaCode=map.get("issuerContactAreaCode");
			issuerContactPhone=map.get("issuerContactPhone");
			issuerCategoryCode=map.get("issuerCategoryCode");
			
			this.setCreditoActivoRetrasoNo(map.get("creditoActivoRetrasoNo")!=null && "X".equalsIgnoreCase(map.get("creditoActivoRetrasoNo"))?"true":"false");
			this.setCreditoActivoRetrasoSi(map.get("creditoActivoRetrasoSi")!=null && "X".equalsIgnoreCase(map.get("creditoActivoRetrasoSi"))?"true":"false");
			
			this.setCreditoHistoricoRetrasoNo(map.get("creditoHistoricoRetrasoNo")!=null && "X".equalsIgnoreCase(map.get("creditoHistoricoRetrasoNo"))?"true":"false");
			this.setCreditoHistoricoRetrasoSi(map.get("creditoHistoricoRetrasoSi")!=null && "X".equalsIgnoreCase(map.get("creditoHistoricoRetrasoSi"))?"true":"false");
			
			this.setNombreDelTerritorioDirector(map.get("nombreDelTerritorioDirector"));
			
			//this.setRfcRepresentanteLegal1(map.get("rfcRepresentanteLegal1"));
			//this.setRfcRepresentanteLegal2(map.get("rfcRepresentanteLegal2"));
			this.setLegalrepresentative_rfc_1(map.get("legalrepresentative_rfc_1"));
	        this.setLegalrepresentative_rfc_2(map.get("legalrepresentative_rfc_2"));
	         
			this.setNivelRiesgoCD(map.get("nivelRiesgoCD"));
			
		}//fin atributos

		this.setContract(contract_);
	}



	@Override
	public PdfTemplateBinding getPdfTemplateBinding() {
		return pdfTemplateBinding;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Template[] getFormatList() {
		this.setToPrint(false);
		Collection<Template> templateCollection 	= getTemplateOption(this.getProduct().getProductid());
		Collection<Template> templateCollection2 	= new ArrayList();
		Boolean optionFianza 						= Boolean.FALSE;
		if(this.getAffiliation_havedepositcompany()!=null){
			if(this.getAffiliation_havedepositcompany().equals( ApplicationConstants.OPTION_HAVE_DEPOSIT )){
				optionFianza = Boolean.TRUE;
			}
		}
		for(Template template :templateCollection){
			if(!template.getTemplateid().equals( ApplicationConstants.ID_LAYOUT_CD_EXCEPCION_FIANZA) ){
				templateCollection2.add(template);
			}else if(!optionFianza){
				templateCollection2.add(template);
			}
		}
		Template[] templateArray = new Template[templateCollection2.size()];
		return templateCollection2.toArray(templateArray);
	}

	@Override
	public String getProductPrefix() {
		return ApplicationConstants.PREFIJO_CD;
	}

	@Override
	public void getProductIdDetail() {
		setProduct(productBean.findById(new Integer(ProductType.COBRANZA_DOMICILIADA.value())));
	}

	@Override
	public boolean savePartialContract() {
		Contract contract = getContract();
		ArrayList<ContractAttribute> list = new ArrayList<ContractAttribute>();
		// ORDEN COBRANZA DOMICILIADA
		addToList(list,AttrConstants.BEM_NUMBER, getBemnumber());
		// ORDEN AFILIACION
		addToList(list,AttrConstants.AFFILIATION_ACCOUNT_NUMBER_MN,getAffiliation_accountnumbermn());
		addToList(list,AttrConstants.AFFILIATION_THROUGH,getAffiliation_Through());
		addToList(list,AttrConstants.AFFILIATION_FORMATLAYOUT,getAffiliation_formatLayout());
		addToList(list,AttrConstants.AFFILIATION_PAYWORKSCLABE,getAffiliation_payworksClabe());
		addToList(list,AttrConstants.AFFILIATION_AUTOREG,getAffiliation_autoRegister());
		addToList(list,AttrConstants.AFFILIATION_RETRIES,getAffiliation_retries());
		addToList(list,AttrConstants.AFFILIATION_MAXAMOUNT,getAffiliation_maxAmount());
		addToList(list,AttrConstants.AFFILIATION_INTERNALCREDITHIST,getAffiliation_internalcredithistory());
		addToList(list,AttrConstants.AFFILIATION_EXTERNALCREDITHIST,getAffiliation_externalcredithistory());
		addToList(list,AttrConstants.AFFILIATION_HAVEDEPOSITCOMP,getAffiliation_havedepositcompany());
		addToList(list,AttrConstants.AFFILIATION_OFFICER_DEPOSIT_EXNT, getAffiliation_havedepositcompany().equals("S") ? "" : getAffiliation_officerdepositexent());
		addToList(list,AttrConstants.MONTHLY_AMOUNT,getMonthlyAmount());

		// ORDEN DEPOSIT
		addToList(list,AttrConstants.AFFILIATION_DEPOSITCOMPANY, getAffiliation_havedepositcompany().equals("N") ? "" : getAffiliation_depositcompany());
		addToList(list,AttrConstants.AFFILIATION_DEPOSITAMOUNT, getAffiliation_havedepositcompany().equals("N") ? "" : getAffiliation_depositamount());
		addToList(list,AttrConstants.AFFILIATION_DUEDATE, getAffiliation_havedepositcompany().equals("N") ? "" : getAffiliation_duedate());

		// ORDEN REFERENCE
		addToList(list,AttrConstants.REFERENCE_NAME,getReferenceName());
		addToList(list,AttrConstants.REFERENCE_LENGTH,getReferenceLength());
		addToList(list,AttrConstants.REFERENCE_TYPE,getReferenceType());
		addToList(list,AttrConstants.REFERENCE_DV,getReferenceDV());
		addToList(list,AttrConstants.REFERENCE_MODULETYPE,getReferenceModuleType());
		addToList(list,AttrConstants.REFERENCE_REQUIRED, getReferenceRequired());

		// ORDEN COMISSIONS
		addToList(list,AttrConstants.BATCH_COMISSIONTYPE,getBatchCommissionType());
		addToList(list,AttrConstants.BANORTE_OE, getBanorteOE());
		addToList(list,AttrConstants.BANORTE_ONE,getBanorteONE());
		addToList(list,AttrConstants.OTHER_OE, getOtrosOE());
		addToList(list,AttrConstants.OTHER_ONE, getOtrosONE());
		addToList(list,AttrConstants.ONLINE_COMISSIONTYPE,getOnLineCommissionType());
		addToList(list,AttrConstants.ONLINE_BANORTE_OE,getOnLineBanorteOE());
		addToList(list,AttrConstants.ONLINE_BANORTE_ONE,getOnLineBanorteONE());
		addToList(list,AttrConstants.BRANCH_TRANSMISSION,getBranchTransmission());
		addToList(list,AttrConstants.BATCH_COMISSION_CODE,getComissionBatchCodeSelected());
		addToList(list,AttrConstants.ONLINE_COMISSION_CODE,getComissionOnlineCodeSelected());
		addToList(list,AttrConstants.COMMENTS, getComments());
		addToList(list,AttrConstants.OPERATION_COMMENTS, this.getComments().length()<250?this.getComments():this.getComments().substring(0, 249));

		// ORDER INFO GENERAL//
		// DATOS DE LA CELEBRACION
		this.loadToSaveCelebrationInfo(list);

		// DATOS CONTRATANTE
		this.loadToSaveGeneralClientInfo(list);
		addToList(list,AttrConstants.CLIENT_FAX,( getClient_fax()!= null)? getClient_fax().toString() : "" );
		addToList(list,AttrConstants.CLIENT_FAXEXT,( getClient_faxext()!= null)? getClient_faxext().toString() : "" );
		addToList(list,AttrConstants.CLIENT_CONSTITUTIONDATE, getClient_constitutiondate() != null ? getCelebrationdate(): "");
		addToList(list,AttrConstants.CLIENT_CATEGORYCODE,getClient_categorycode());

		// DATOS APODERADO LEGAL
		this.loadToSaveLegalRepresentative1(list);
		addToList(list,AttrConstants.LEGALREPRESENTATIVE_POSITION_1,getLegalrepresentative_position_1());
		this.loadToSaveLegalRepresentative2(list);
		addToList(list,AttrConstants.LEGALREPRESENTATIVE_POSITION_2,getLegalrepresentative_position_2());

		// DATOS FIRMAS ADICIONALES FACULTADAS
		this.loadToSaveClientContact1(list);
		this.loadToSaveClientContact2(list);
		this.loadToSaveClientContact3(list);
		this.loadToSaveClientContact4(list);

		// DATOS FUNCIONARIO COLOCO PRODUCTO
		this.loadToSaveOfficer(list);

		// DATOS FUNCIONARIO EBANKING
		this.loadToSaveOfficerEbanking(list);

		// DATOS DE LA SUCURSAL
		this.loadToSaveBranch(list);

		// DATOS DEL APODERADO
		this.loadToSaveOfficerRep1(list);
		addToList(list,AttrConstants.OFFICER_REP_FIRMNUMBER_1,getOfficerrepfirmnumber_1());
		this.loadToSaveOfficerRep2(list);
		addToList(list,AttrConstants.OFFICER_REP_FIRMNUMBER_2,getOfficerrepfirmnumber_2());
		
		//DATOS EMPLEADO EBANKING ESPECIALIZADO 
		addToList(list,AttrConstants.OFFICER_EBANKING_ESP_NUMBER,getOfficerebankingEspEmpnumber());
		addToList(list,AttrConstants.OFFICER_EBANKING_ESP_NAME,getOfficerebankingEspName());
		addToList(list,AttrConstants.OFFICER_EBANKING_ESP_LASTNAME,getOfficerebankingEspLastname());
		addToList(list,AttrConstants.OFFICER_EBANKING_ESP_MOTHERSNAME,getOfficerebankingEspMothersln());
		addToList(list,AttrConstants.OFFICER_EBANKING_ESP_POSITION,getOfficerebankingEspPosition());
		
		//DATOS EMPLEADO EBANKING TERRITORIAL
		addToList(list,AttrConstants.OFFICER_EBANKING_T_NUMBER,getOfficerebankingTempnumber());
		addToList(list,AttrConstants.OFFICER_EBANKING_T_NAME,getOfficerebankingTname());
		addToList(list,AttrConstants.OFFICER_EBANKING_T_LASTNAME,getOfficerebankingTlastname());
		addToList(list,AttrConstants.OFFICER_EBANKING_T_MOTHERSNAME,getOfficerebankingTmothersln());
		addToList(list,AttrConstants.OFFICER_EBANKING_T_POSITION,getOfficerebankingTposition());
		
		//DATOS DIRECTOR SUCURSAL
		addToList(list,AttrConstants.BRANCH_DIRECTOR_NUMBER,getBranchDirectorNumber());
		addToList(list,AttrConstants.BRANCH_DIRECTOR_NAME,getBranchDirectorName());
		addToList(list,AttrConstants.BRANCH_DIRECTOR_LASTNAME,getBranchDirectorLastName());
		addToList(list,AttrConstants.BRANCH_DIRECTOR_MOTHERSLN,getBranchDirectorMothersLn());
		addToList(list,AttrConstants.BRANCH_DIRECTOR_POSITION,getBranchDirectorposition());
		
		
		// DATOS OPCION MULTIPLE SELECCIONADOS
		addToList(list,AttrConstants.SELECTED_OPTION_BANORTE,getAffiliation_SelectedBanorte());
		addToList(list,AttrConstants.SELECTED_OPTION_NO_BANORTE,getAffiliation_NoselectedBanorte());
		addToList(list,AttrConstants.SELECTED_OPTION_EMISOR,getAffiliation_SelectedEmisor());
		addToList(list,AttrConstants.SELECTED_OPTION_NO_EMISOR,getAffiliation_NoselectedEmisor());
		addToList(list,AttrConstants.SELECTED_OPTION_SIGA,getFormatLayoutSelectedSiga());
		addToList(list,AttrConstants.SELECTED_OPTION_PAYWORK_SI,getPayworksClabeSelectSi());
		addToList(list,AttrConstants.SELECTED_OPTION_PAYWORK_NO,getPayworksClabeSelectNo());
		addToList(list,AttrConstants.SELECTED_OPTION_MODULE_10,getModuleTypeSelect10());
		addToList(list,AttrConstants.SELECTED_OPTION_MODULE_11,getModuleTypeSelect11());
		addToList(list,AttrConstants.SELECTED_OPTION_MODULE_97,getModuleTypeSelect97());
		addToList(list,AttrConstants.SELECTED_OPTION_MODULE_OTRO,getModuleTypeSelectOtro());
		addToList(list,AttrConstants.SELECTED_OPTION_BATCH,getBatchCommissionSelect());
		addToList(list,AttrConstants.SELECTED_OPTION_ONLINE,getOnlineCommissionSelect());
		addToList(list,AttrConstants.CONTRACT_REFERENCE, this.getContract().getReference());
//		addToList(list,"cdemitter", "");//guarda atributo para emisora
		// DATOS COMPLETOS
		this.loadToSaveInfoDatosComplete(list);
		addToList(list,AttrConstants.CONTRACT_TYPE,ApplicationConstants.CONTRACT_TYPE_ALTA);
		
		addToList(list, AttrConstants.RED, red);
		//PayworksClabe
		addToList(list, "issuerName", issuerName);
		addToList(list, "issuerAccount", issuerAccount);
		addToList(list, "issuerStreet", issuerStreet);
		addToList(list, "issuerExtNum", issuerExtNum);
		addToList(list, "issuerIntNum", issuerIntNum);
		addToList(list, "issuerColony", issuerColony);
		addToList(list, "issuerZipCode", issuerZipCode);
		addToList(list, "issuerState", issuerState);
		addToList(list, "issuerCity", issuerCity);
		if("X".equalsIgnoreCase(payworksClabeSelectSi)){//si seleccionï¿½ payworks clabe
			issuerAddress=issuerStreet+" No. "+issuerExtNum+" Int. "+issuerColony+", CP. "+issuerZipCode+"; "+issuerCity+", "+issuerState;
			addToList(list, "issuerAddress", issuerAddress);
		}
		addToList(list, "issuerContactEmail", issuerContactEmail);
		addToList(list, "issuerContactName", issuerContactName);
		addToList(list, "issuerContactAreaCode", issuerContactAreaCode);
		addToList(list, "issuerContactPhone", issuerContactPhone);
		addToList(list, "issuerCategoryCode", issuerCategoryCode);
		
       	addToList(list,"creditoActivoRetrasoSi", ("true".equalsIgnoreCase(creditoActivoRetrasoSi)?"X":""));
       	addToList(list,"creditoActivoRetrasoNo", ("true".equalsIgnoreCase(creditoActivoRetrasoNo)?"X":""));
       	
       	addToList(list,"creditoHistoricoRetrasoSi", ("true".equalsIgnoreCase(creditoHistoricoRetrasoSi)?"X":""));
       	addToList(list,"creditoHistoricoRetrasoNo", ("true".equalsIgnoreCase(creditoHistoricoRetrasoNo)?"X":""));
       	
       	addToList(list, "nombreDelTerritorioDirector", nombreDelTerritorioDirector);
       	
       	//addToList(list, "rfcRepresentanteLegal1", getRfcRepresentanteLegal1());
       	//addToList(list, "rfcRepresentanteLegal2", getRfcRepresentanteLegal2());
       	addToList(list,"legalrepresentative_rfc_1", getLegalrepresentative_rfc_1());
       	addToList(list,"legalrepresentative_rfc_2", getLegalrepresentative_rfc_2());
        
       	
       	addToList(list, "nivelRiesgoCD", nivelRiesgoCD);
       	
       	addToList(list,"celebrationdateday",this.formatDate.getDayDate(getCelebrationdate()));       
        addToList(list,"celebrationdatemonth",this.formatDate.getMonthDesc(getCelebrationdate()));        
        addToList(list,"celebrationdateyear",this.formatDate.getYearDate(getCelebrationdate()));
        addToList(list,"celebrationlargedate",this.formatDate.getLargeDate(getCelebrationdate()));
        
        addToList(list,"celebrationComplete2",getCelebrationplace() + ", " + getCelebrationstate() + ", " + this.formatDate.getDayDate(getCelebrationdate()) + " de " + this.formatDate.getMonthDesc(getCelebrationdate()) + " del " + this.formatDate.getYearDate(getCelebrationdate()));
        addToList(list,"payworksClabeSiNo",("true".equalsIgnoreCase(getAffiliation_payworksClabe())?"Si":"No"));
        addToList(list,"clientSicFiscalname",("("+ getClient_sic()+")"+" "+getClient_fiscalname()));
       	
		contract.setContractAttributeCollection(list);
		contractBean.update(contract);

		pdfTemplateBinding = new PdfTemplateBindingContract();
		pdfTemplateBinding.setContract(contract);

		return true;
	}

	public void loadInfoBatchComission() {
		Payrate payrate = this.comissionBatchHash.get(this.comissionBatchCode);
		this.setBanorteOE(payrate.getCorrectb().toString());
		this.setBanorteONE(payrate.getErrorb().toString());
		this.setOtrosOE(payrate.getCorrectob().toString());
		this.setOtrosONE(payrate.getErrorob().toString());
		this.setComissionBatchCodeSelected(this.comissionBatchCode);
	}

	public void loadInfoOnlineComission() {
		Payrate payrate = this.comissionOnLineHash.get(this.comissionOnlineCode);
		this.setOnLineBanorteOE(payrate.getCorrectb().toString());
		this.setOnLineBanorteONE(payrate.getErrorb().toString());
		this.setComissionOnlineCodeSelected(this.comissionOnlineCode);

	}

	private SelectItem[] convertPayrateToSelectItem(List<Payrate> comissions, boolean isOnline) {
		SelectItem[] commisionsArray 	= null;
		String label 					= ApplicationConstants.EMPTY_STRING;
		if (comissions != null) {
			commisionsArray = new SelectItem[comissions.size()];
			int i = 0;
			for (Payrate comission : comissions) {
				if(isOnline){
					label = comission.printInfoOnline();
				}else {
					label = comission.printInfo();
				}
				commisionsArray[i] = new SelectItem(comission.getCode(),label);
				i++;
			}
		}
		return commisionsArray;
	}


	public void createPDF() {
		FacesContext fCtx 			= FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fCtx.getExternalContext().getRequest();
		   	
	   	   String  nameTemplate = request.getParameter(NAME_TEMPLATE);
	       String  pathTemplate = request.getParameter(PATHTEMPLATE);
	       Integer flagTemplate = Integer.parseInt(request.getParameter("flagTemplate"));
		pdfTemplateBinding.setAffiliationId(1);
		createPdfOrXLSResponse(getPath() + pathTemplate + nameTemplate,
				nameTemplate, true, flagTemplate.intValue() == 1 ? true : false);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String processInfo() {
		String newGiro 				= this.getClient_categorycode();
		String response 			= ApplicationConstants.EMPTY_STRING;
		boolean opcionEmisor 		= Boolean.FALSE;
		
//		this.getGeneralInfoErrorsList().clear();
		errorsList.clear();
		if(!validationComissionsBatch()){
//			ArrayList<ContractMessageErrors> errorsList 	= new ArrayList();
			ContractMessageErrors errors 					= new ContractMessageErrors();

			errors.setMessage("Favor de Capturar una Tarifa Valida ");
			errorsList.add(errors);
//			this.setErrorsList(errorsList);
			
			return "FAILED";
		}
//			else {
//			if(this.getErrorsList() !=null){
//				this.getErrorsList().clear();
//			}
//		}
		
		if(!validationComissionsOnline()){
//			ArrayList<ContractMessageErrors> errorsList 	= new ArrayList();
			ContractMessageErrors errors 					= new ContractMessageErrors();

			errors.setMessage("Favor de Capturar una Tarifa Valida ");
			errorsList.add(errors);
//			this.setErrorsList(errorsList);
			return "FAILED";
		}
//		else {
//			if(this.getErrorsList() !=null){
//				this.getErrorsList().clear();
//			}
//		}
		//validar datos banca especializada
		if(!validateOfficer()){
//			ArrayList<ContractMessageErrors> errorsList 	= new ArrayList();
			ContractMessageErrors errors 					= new ContractMessageErrors();

			errors.setMessage("Favor de Capturar los Datos Ejecutivo Banca Especializada completos");
			errorsList.add(errors);
//			this.setErrorsList(errorsList);
			return "FAILED";
		}
		
		//valida director de sucursal  validateBranchDirector
		if(!validateBranchDirector()){
//			ArrayList<ContractMessageErrors> errorsList 	= new ArrayList();
			ContractMessageErrors errors 					= new ContractMessageErrors();

			errors.setMessage("Favor de Capturar los Datos Director de Sucursal completos");
			errorsList.add(errors);
//			this.setErrorsList(errorsList);
			return "FAILED";
		}
//		else {
//			if(this.getErrorsList() !=null){
//				this.getErrorsList().clear();
//			}
//		}
		if(!validatePayworksClabeInfo()){
			return "FAILED";
		}
		if(red==null){
			ContractMessageErrors error = new ContractMessageErrors();
			error.setMessage("Favor de Seleccionar red: Banorte o Ixe");
			errorsList.add(error);
			return "FAILED";
		}
		
		if(this.getAffiliation_Through().equals(ApplicationConstants.THROUGH_OPCION_EMISOR)){
			opcionEmisor = Boolean.TRUE;
		}
		
		if(this.getAffiliation_formatLayout().equals(ApplicationConstants.FORMAT_LAYOUT_SIGA)){
			this.formatLayoutSelectedSiga = ApplicationConstants.OPCION_SELECTED;
		}
		
		if(this.getAffiliation_payworksClabe().equals(ApplicationConstants.VALUE_TRUE)){
			this.payworksClabeSelectSi = ApplicationConstants.OPCION_SELECTED;
			this.setAffiliation_autoRegister(ApplicationConstants.VALUE_TRUE);
		}else {
			this.payworksClabeSelectNo = ApplicationConstants.OPCION_SELECTED;
			this.setAffiliation_autoRegister(ApplicationConstants.VALUE_FALSE);
		}
		
		if(!opcionEmisor){
			if(this.getReferenceModuleType().equals(ApplicationConstants.MODULE_OPCION_10)){
				this.moduleTypeSelect10 = ApplicationConstants.OPCION_SELECTED;
				this.moduleTypeSelect11 = ApplicationConstants.EMPTY_STRING;
				this.moduleTypeSelect97 = ApplicationConstants.EMPTY_STRING;
				this.moduleTypeSelectOtro = ApplicationConstants.EMPTY_STRING;
			}else if( this.getReferenceModuleType().equals(ApplicationConstants.MODULE_OPCION_11) ){
				this.moduleTypeSelect10 = ApplicationConstants.EMPTY_STRING;
				this.moduleTypeSelect11 = ApplicationConstants.OPCION_SELECTED;
				this.moduleTypeSelect97 = ApplicationConstants.EMPTY_STRING;
				this.moduleTypeSelectOtro = ApplicationConstants.EMPTY_STRING;
			}else if( this.getReferenceModuleType().equals(ApplicationConstants.MODULE_OPCION_97) ){
				this.moduleTypeSelect10 = ApplicationConstants.EMPTY_STRING;
				this.moduleTypeSelect11 = ApplicationConstants.EMPTY_STRING;
				this.moduleTypeSelect97 = ApplicationConstants.OPCION_SELECTED;
				this.moduleTypeSelectOtro = ApplicationConstants.EMPTY_STRING;
			}else if( this.getReferenceModuleType().equals(ApplicationConstants.MODULE_OPCION_OTROS) ){
				this.moduleTypeSelect10 = ApplicationConstants.EMPTY_STRING;
				this.moduleTypeSelect11 = ApplicationConstants.EMPTY_STRING;
				this.moduleTypeSelect97 = ApplicationConstants.EMPTY_STRING;
				this.moduleTypeSelectOtro = ApplicationConstants.OPCION_SELECTED;
			}
		}
		
		if(this.getAffiliation_Through().equals(ApplicationConstants.THROUGH_OPCION_EMISOR)){
			this.affiliation_SelectedBanorte = ApplicationConstants.EMPTY_STRING; 
			this.affiliation_NoselectedBanorte = ApplicationConstants.OPCION_SELECTED; 
			this.affiliation_SelectedEmisor = ApplicationConstants.OPCION_SELECTED; 
			this.affiliation_NoselectedEmisor = ApplicationConstants.EMPTY_STRING; 
		}else if (this.getAffiliation_Through().equals(ApplicationConstants.THROUGH_OPCION_BANORTE)) {
			this.affiliation_SelectedBanorte = ApplicationConstants.OPCION_SELECTED; 
			this.affiliation_NoselectedBanorte = ApplicationConstants.EMPTY_STRING; 
			this.affiliation_SelectedEmisor = ApplicationConstants.EMPTY_STRING; 
			this.affiliation_NoselectedEmisor = ApplicationConstants.OPCION_SELECTED; 
		}else if (this.getAffiliation_Through().equals(ApplicationConstants.THROUGH_OPCION_AMBOS)) {
			this.affiliation_SelectedBanorte = ApplicationConstants.OPCION_SELECTED; 
			this.affiliation_NoselectedBanorte = ApplicationConstants.EMPTY_STRING; 
			this.affiliation_SelectedEmisor = ApplicationConstants.OPCION_SELECTED; 
			this.affiliation_NoselectedEmisor = ApplicationConstants.EMPTY_STRING;
		}
		
		this.batchCommissionSelect = ApplicationConstants.OPCION_SELECTED; 
		
		
		/*if(this.getOnLineCommission().equals(ApplicationConstants.VALUE_TRUE)){
			this.onlineCommissionSelect = ApplicationConstants.OPCION_SELECTED; 
		}else{
			this.onlineCommissionSelect = ApplicationConstants.EMPTY_STRING;
		}*/
		
		if(this.onLineBanorteOE != null){
			this.onlineCommissionSelect = ApplicationConstants.OPCION_SELECTED; 
		}else{
			this.onlineCommissionSelect = ApplicationConstants.EMPTY_STRING;
		}
		
		
		if( Boolean.parseBoolean(this.getReferenceRequired() ) ){
			this.setReferenceRequired(ApplicationConstants.OPTION_TRUE_1);
		}else{
			this.setReferenceRequired(ApplicationConstants.OPTION_FALSE_0);
		}
		
		if( Boolean.parseBoolean(this.getReferenceDV() ) ){
			this.setReferenceDV( ApplicationConstants.OPTION_TRUE_1);
		}else{
			this.setReferenceDV( ApplicationConstants.OPTION_FALSE_0);
		}
		
		isNivelBajo();
		response =  continueToGeneralInfo();
		this.setClient_categorycode(newGiro);
		
		return response;
	}
	
	/**
	 * Valida que esten completos los Datos Ejecutivo Banca Especializada
	 * @return
	 */
	private boolean validateOfficer(){
		boolean valid = true;
		//Si tiene fianza no se valida
		if(! ApplicationConstants.OPTION_HAVE_DEPOSIT.equalsIgnoreCase(affiliation_havedepositcompany)){
			String numero = getOfficerebankingEspEmpnumber();
			String nombre = getOfficerebankingEspName();
			String materno = getOfficerebankingEspLastname();
			String paterno = getOfficerebankingEspMothersln();
			String puesto = getOfficerebankingEspPosition();
			if("".equalsIgnoreCase(numero)||
				"".equalsIgnoreCase(nombre)||
				"".equalsIgnoreCase(paterno)||
				"".equalsIgnoreCase(materno)||
				"".equalsIgnoreCase(puesto)
			){
				valid=false;
			}
		}
		return valid;
	}
	//validacion datos director sucursal
	private boolean validateBranchDirector(){
		boolean valid = true;
		
			String numero = getBranchDirectorNumber();
			String nombre = getBranchDirectorName();
			String materno = getBranchDirectorLastName();
			String paterno = getBranchDirectorMothersLn();
			String puesto = getBranchDirectorposition();
			if("".equalsIgnoreCase(numero)||
				"".equalsIgnoreCase(nombre)||
				"".equalsIgnoreCase(paterno)||
				"".equalsIgnoreCase(materno)||
				"".equalsIgnoreCase(puesto)
			){
				valid=false;
			}
		
		return valid;
	}
	
	private boolean validationComissionsBatch(){
		boolean resultado 								= Boolean.FALSE;
		Double operacionbanorte 						= Double.valueOf( this.banorteOE );
		Double operacionOtros 							= Double.valueOf( this.otrosOE);
		
		
		for(Payrate payrate: this.batchList){
			if(operacionbanorte.equals(payrate.getCorrectb())){
				if(operacionOtros.equals(payrate.getCorrectob())){
					resultado = Boolean.TRUE;
					this.banorteONE = String.valueOf( payrate.getErrorb() );
					this.otrosONE = String.valueOf( payrate.getErrorob() );
					this.comissionBatchCodeSelected = payrate.getCode();
					break;
				}
			}
		}
		
		return resultado;
		
	}

	private boolean validationComissionsOnline(){
		boolean resultado = Boolean.FALSE;
		
		if(this.onLineBanorteOE.equals(null) || this.onLineBanorteOE.equals(ApplicationConstants.EMPTY_STRING)){
			return Boolean.TRUE;
		}
		
		Double operacionbanorte = Double.valueOf( this.onLineBanorteOE );
		
		for(Payrate payrate: this.onlineList){
			if(operacionbanorte.equals(payrate.getCorrectb())){
				resultado = Boolean.TRUE;
				this.onLineBanorteONE = String.valueOf( payrate.getErrorb() );
				this.comissionOnlineCodeSelected = payrate.getCode();
			}
		}
		
		return resultado;
		
	}
	
	private void isNivelBajo(){
		if(this.affiliation_internalcredithistory.equals(ApplicationConstants.SIN_COLOR)){
			if(this.affiliation_externalcredithistory.equals(ApplicationConstants.SIN_REGISTRO_BUENO)){
				String giroNumber = this.getClient_categorycode().substring(0,6);
				if(giroNumber.equals(ApplicationConstants.GIRO_ESPECIAL_CD_062000) || giroNumber.equals(ApplicationConstants.GIRO_ESPECIAL_CD_063000) || 
						giroNumber.equals(ApplicationConstants.GIRO_ESPECIAL_CD_121200) || giroNumber.equals(ApplicationConstants.GIRO_ESPECIAL_CD_172000) || 
						giroNumber.equals(ApplicationConstants.GIRO_ESPECIAL_CD_173000) || giroNumber.equals(ApplicationConstants.GIRO_ESPECIAL_CD_174000) || 
						giroNumber.equals(ApplicationConstants.GIRO_ESPECIAL_CD_181000) || giroNumber.equals(ApplicationConstants.GIRO_ESPECIAL_CD_182000)){
					this.setAffiliation_havedepositcompany(ApplicationConstants.BOOLEAN_STRING_N);
				}
			}
		}
	}
	
	private boolean validatePayworksClabeInfo(){
		String[]camposPayworks=new String[]{issuerName,issuerAccount,issuerStreet,issuerExtNum,
				issuerColony,issuerZipCode,issuerState,issuerCity,issuerContactEmail,issuerContactName,issuerContactAreaCode,
				issuerContactPhone,issuerCategoryCode};
		boolean vacio=false;
		if("true".equalsIgnoreCase(affiliation_payworksClabe)){
			for(String campo:camposPayworks){
				if(campo==null||campo.trim().isEmpty()){
					vacio=true;
				}
			}
			if(vacio){
				ContractMessageErrors error = new ContractMessageErrors();
				error.setMessage("Favor de capturar todos los campos de la secciï¿½n: - Datos de la emisora.");
				errorsList.add(error);
			}
			if(errorsList.size()>0){
				return false;
			}
		}else{
			clearPayworksData();
		}
		return true;
	}
	
	public void findIssuerCities(ActionEvent actionEvent) {
		HtmlCommandLink link=(HtmlCommandLink)actionEvent.getSource();
		//llena las ciudades
		getIssuerCitiesArray();
		// Para recuperar el parï¿½metro enviado por el JSP
		String anchor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("anchor" + link.getId().toString());
		redirectMtto(anchor);
	}
	
	public SelectItem[] getIssuerCitiesArray() {
		if(issuerState==null){
			issuerState="";
		}
		if (issuerState != null) {
			List<Cities> cities = citiesBean.findByState(issuerState);
			if (cities != null) {
				issuerCitiesArray = new SelectItem[cities.size()];
				int i = 0;
				for (Cities cit : cities) {
					issuerCitiesArray[i] = new SelectItem(cit.getName(),cit.getName());
					i++;
				}
			}
		}
		return issuerCitiesArray;
	}
	
	public SelectItem[] getIssuerCategoriesArray(){
		String[]categoriaComercios=new String[]{
				"Agricultura ",
				"Minerï¿½a ",
				"Construcciï¿½n ",
				"Industrias manufactureras ",
				"Comercio al por mayor ",
				"Comercio al por menor ",
				"Transportes ",
				"Correos y almacenamiento ",
				"Informaciï¿½n en medios masivos ",
				"Servicios financieros y de seguros ",
				"Servicios inmobiliarios ",
				"Servicios profesionales cientï¿½ficos y tï¿½cnicos ",
				"Corporativos ",
				"Servicios educativos ",
				"Servicios de salud ",
				"Servicios de esparcimientos culturales y deportivos y otros servicios recreativos",
		};
		issuerCategoriesArray=new SelectItem[categoriaComercios.length];
		for(int i=0; i<categoriaComercios.length;i++){
			issuerCategoriesArray[i]=new SelectItem(categoriaComercios[i], categoriaComercios[i]);
		}
		return issuerCategoriesArray;
	}
	
	public void clearPayworksData(){
		issuerAccount=ApplicationConstants.EMPTY_STRING;
		issuerName=ApplicationConstants.EMPTY_STRING;
		issuerStreet=ApplicationConstants.EMPTY_STRING;
		issuerExtNum=ApplicationConstants.EMPTY_STRING;
		issuerIntNum=ApplicationConstants.EMPTY_STRING;
		issuerCity=ApplicationConstants.EMPTY_STRING;
		issuerState=ApplicationConstants.EMPTY_STRING;
		issuerColony=ApplicationConstants.EMPTY_STRING;
		issuerZipCode=ApplicationConstants.EMPTY_STRING;
		issuerAddress=ApplicationConstants.EMPTY_STRING;
		issuerCategoryCode=ApplicationConstants.EMPTY_STRING;
		issuerContactAreaCode=ApplicationConstants.EMPTY_STRING;
		issuerContactEmail=ApplicationConstants.EMPTY_STRING;
		issuerContactName=ApplicationConstants.EMPTY_STRING;
		issuerContactPhone=ApplicationConstants.EMPTY_STRING;
	}

	/**
	 * GETTER & SETTERS
	 */

	/**
	 * @return the bemNumber
	 */
	public String getBemnumber() {
		return bemnumber;
	}

	/**
	 * @param bemNumber
	 *            the bemNumber to set
	 */
	public void setBemnumber(String bemnumber) {
		this.bemnumber = bemnumber;
	}

	/**
	 * @return the affiliation_havedepositcompany
	 */
	public String getAffiliation_havedepositcompany() {
		return affiliation_havedepositcompany;
	}

	/**
	 * @param affiliation_havedepositcompany
	 *            the affiliation_havedepositcompany to set
	 */
	public void setAffiliation_havedepositcompany(
			String affiliation_havedepositcompany) {
		this.affiliation_havedepositcompany = affiliation_havedepositcompany;
	}

	/**
	 * @return the affiliation_depositcompany
	 */
	public String getAffiliation_depositcompany() {
		return affiliation_depositcompany;
	}

	/**
	 * @param affiliation_depositcompany
	 *            the affiliation_depositcompany to set
	 */
	public void setAffiliation_depositcompany(String affiliation_depositcompany) {
		this.affiliation_depositcompany = affiliation_depositcompany;
	}

	/**
	 * @return the affiliation_depositamount
	 */
	public String getAffiliation_depositamount() {
		return affiliation_depositamount;
	}

	/**
	 * @param affiliation_depositamount
	 *            the affiliation_depositamount to set
	 */
	public void setAffiliation_depositamount(String affiliation_depositamount) {
		this.affiliation_depositamount = affiliation_depositamount;
	}

	/**
	 * @return the affiliation_duedate
	 */
	public String getAffiliation_duedate() {
		return affiliation_duedate;
	}

	/**
	 * @param affiliation_duedate
	 *            the affiliation_duedate to set
	 */
	public void setAffiliation_duedate(String affiliation_duedate) {
		this.affiliation_duedate = affiliation_duedate;
	}

	/**
	 * @return the referenceName
	 */
	public String getReferenceName() {
		return referenceName;
	}

	/**
	 * @param referenceName
	 *            the referenceName to set
	 */
	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	/**
	 * @return the referenceLength
	 */
	public String getReferenceLength() {
		return referenceLength;
	}

	/**
	 * @param referenceLength
	 *            the referenceLength to set
	 */
	public void setReferenceLength(String referenceLength) {
		this.referenceLength = referenceLength;
	}

	/**
	 * @return the referenceType
	 */
	public String getReferenceType() {
		return referenceType;
	}

	/**
	 * @param referenceType
	 *            the referenceType to set
	 */
	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	/**
	 * @return the referenceDV
	 */
	public String getReferenceDV() {
		return referenceDV;
	}

	/**
	 * @param referenceDV
	 *            the referenceDV to set
	 */
	public void setReferenceDV(String referenceDV) {
		this.referenceDV = referenceDV;
	}

	/**
	 * @return the referenceModuleType
	 */
	public String getReferenceModuleType() {
		return referenceModuleType;
	}

	/**
	 * @param referenceModuleType
	 *            the referenceModuleType to set
	 */
	public void setReferenceModuleType(String referenceModuleType) {
		this.referenceModuleType = referenceModuleType;
	}

	/**
	 * @return the batchCommissionType
	 */
	public String getBatchCommissionType() {
		return batchCommissionType;
	}

	/**
	 * @param batchCommissionType
	 *            the batchCommissionType to set
	 */
	public void setBatchCommissionType(String batchCommissionType) {
		this.batchCommissionType = batchCommissionType;
	}

	/**
	 * @return the banorteOE
	 */
	public String getBanorteOE() {
		return banorteOE;
	}

	/**
	 * @param banorteOE
	 *            the banorteOE to set
	 */
	public void setBanorteOE(String banorteOE) {
		this.banorteOE = banorteOE;
	}

	/**
	 * @return the banorteONE
	 */
	public String getBanorteONE() {
		return banorteONE;
	}

	/**
	 * @param banorteONE
	 *            the banorteONE to set
	 */
	public void setBanorteONE(String banorteONE) {
		this.banorteONE = banorteONE;
	}

	/**
	 * @return the otrosOE
	 */
	public String getOtrosOE() {
		return otrosOE;
	}

	/**
	 * @param otrosOE
	 *            the otrosOE to set
	 */
	public void setOtrosOE(String otrosOE) {
		this.otrosOE = otrosOE;
	}

	/**
	 * @return the otrosONE
	 */
	public String getOtrosONE() {
		return otrosONE;
	}

	/**
	 * @param otrosONE
	 *            the otrosONE to set
	 */
	public void setOtrosONE(String otrosONE) {
		this.otrosONE = otrosONE;
	}

	/**
	 * @return the onLineCommissionType
	 */
	public String getOnLineCommissionType() {
		return onLineCommissionType;
	}

	/**
	 * @param onLineCommissionType
	 *            the onLineCommissionType to set
	 */
	public void setOnLineCommissionType(String onLineCommissionType) {
		this.onLineCommissionType = onLineCommissionType;
	}

	/**
	 * @return the onLineBanorteOE
	 */
	public String getOnLineBanorteOE() {
		return onLineBanorteOE;
	}

	/**
	 * @param onLineBanorteOE
	 *            the onLineBanorteOE to set
	 */
	public void setOnLineBanorteOE(String onLineBanorteOE) {
		this.onLineBanorteOE = onLineBanorteOE;
	}

	/**
	 * @return the onLineBanorteONE
	 */
	public String getOnLineBanorteONE() {
		return onLineBanorteONE;
	}

	/**
	 * @param onLineBanorteONE
	 *            the onLineBanorteONE to set
	 */
	public void setOnLineBanorteONE(String onLineBanorteONE) {
		this.onLineBanorteONE = onLineBanorteONE;
	}

	/**
	 * @return the branchTransmission
	 */
	public String getBranchTransmission() {
		return branchTransmission;
	}

	/**
	 * @param branchTransmission
	 *            the branchTransmission to set
	 */
	public void setBranchTransmission(String branchTransmission) {
		this.branchTransmission = branchTransmission;
	}

	/**
	 * @return the affiliationThroughArray
	 */
	public SelectItem[] getAffiliationThroughArray() {
		return affiliationThroughArray;
	}

	/**
	 * @param affiliationThroughArray
	 *            the affiliationThroughArray to set
	 */
	public void setAffiliationThroughArray(SelectItem[] affiliationThroughArray) {
		this.affiliationThroughArray = affiliationThroughArray;
	}

	/**
	 * @return the formatLayoutArray
	 */
	public SelectItem[] getFormatLayoutArray() {
		return formatLayoutArray;
	}

	/**
	 * @param formatLayoutArray
	 *            the formatLayoutArray to set
	 */
	public void setFormatLayoutArray(SelectItem[] formatLayoutArray) {
		this.formatLayoutArray = formatLayoutArray;
	}

	/**
	 * @return the buroInternoArray
	 */
	public SelectItem[] getBuroInternoArray() {
		return buroInternoArray;
	}

	/**
	 * @param buroInternoArray
	 *            the buroInternoArray to set
	 */
	public void setBuroInternoArray(SelectItem[] buroInternoArray) {
		this.buroInternoArray = buroInternoArray;
	}

	/**
	 * @return the buroNacionalArray
	 */
	public SelectItem[] getBuroNacionalArray() {
		return buroNacionalArray;
	}

	/**
	 * @param buroNacionalArray
	 *            the buroNacionalArray to set
	 */
	public void setBuroNacionalArray(SelectItem[] buroNacionalArray) {
		this.buroNacionalArray = buroNacionalArray;
	}

	/**
	 * @return the referenceTypeArray
	 */
	public SelectItem[] getReferenceTypeArray() {
		return referenceTypeArray;
	}

	/**
	 * @param referenceTypeArray
	 *            the referenceTypeArray to set
	 */
	public void setReferenceTypeArray(SelectItem[] referenceTypeArray) {
		this.referenceTypeArray = referenceTypeArray;
	}

	/**
	 * @return the referenceModuleArray
	 */
	public SelectItem[] getReferenceModuleArray() {
		return referenceModuleArray;
	}

	/**
	 * @param referenceModuleArray
	 *            the referenceModuleArray to set
	 */
	public void setReferenceModuleArray(SelectItem[] referenceModuleArray) {
		this.referenceModuleArray = referenceModuleArray;
	}

	/**
	 * @return the batchProcessArray
	 */
	public SelectItem[] getBatchProcessArray() {
		return batchProcessArray;
	}

	/**
	 * @param batchProcessArray
	 *            the batchProcessArray to set
	 */
	public void setBatchProcessArray(SelectItem[] batchProcessArray) {
		this.batchProcessArray = batchProcessArray;
	}

	/**
	 * @return the onlineProcessArray
	 */
	public SelectItem[] getOnlineProcessArray() {
		return onlineProcessArray;
	}

	/**
	 * @param onlineProcessArray
	 *            the onlineProcessArray to set
	 */
	public void setOnlineProcessArray(SelectItem[] onlineProcessArray) {
		this.onlineProcessArray = onlineProcessArray;
	}

	/**
	 * @return the ordenErrorsList
	 */
	public ArrayList<ContractMessageErrors> getOrdenErrorsList() {
		return ordenErrorsList;
	}

	/**
	 * @param ordenErrorsList
	 *            the ordenErrorsList to set
	 */
	public void setOrdenErrorsList(
			ArrayList<ContractMessageErrors> ordenErrorsList) {
		this.ordenErrorsList = ordenErrorsList;
	}

	/**
	 * @return the affiliation_accountnumbermn
	 */
	public String getAffiliation_accountnumbermn() {
		return affiliation_accountnumbermn;
	}

	/**
	 * @param affiliation_accountnumbermn
	 *            the affiliation_accountnumbermn to set
	 */
	public void setAffiliation_accountnumbermn(
			String affiliation_accountnumbermn) {
		this.affiliation_accountnumbermn = affiliation_accountnumbermn;
	}

	/**
	 * @return the affiliation_Through
	 */
	public String getAffiliation_Through() {
		return affiliation_Through;
	}

	/**
	 * @param affiliation_Through
	 *            the affiliation_Through to set
	 */
	public void setAffiliation_Through(String affiliation_Through) {
		this.affiliation_Through = affiliation_Through;
	}

	/**
	 * @return the affiliation_formatLayout
	 */
	public String getAffiliation_formatLayout() {
		return affiliation_formatLayout;
	}

	/**
	 * @param affiliation_formatLayout
	 *            the affiliation_formatLayout to set
	 */
	public void setAffiliation_formatLayout(String affiliation_formatLayout) {
		this.affiliation_formatLayout = affiliation_formatLayout;
	}

	/**
	 * @return the affiliation_payworksClabe
	 */
	public String getAffiliation_payworksClabe() {
		return affiliation_payworksClabe;
	}

	/**
	 * @param affiliation_payworksClabe
	 *            the affiliation_payworksClabe to set
	 */
	public void setAffiliation_payworksClabe(String affiliation_payworksClabe) {
		this.affiliation_payworksClabe = affiliation_payworksClabe;
	}

	/**
	 * @return the affiliation_autoRegister
	 */
	public String getAffiliation_autoRegister() {
		return affiliation_autoRegister;
	}

	/**
	 * @param affiliation_autoRegister
	 *            the affiliation_autoRegister to set
	 */
	public void setAffiliation_autoRegister(String affiliation_autoRegister) {
		this.affiliation_autoRegister = affiliation_autoRegister;
	}

	/**
	 * @return the affiliation_retries
	 */
	public String getAffiliation_retries() {
		return affiliation_retries;
	}

	/**
	 * @param affiliation_retries
	 *            the affiliation_retries to set
	 */
	public void setAffiliation_retries(String affiliation_retries) {
		this.affiliation_retries = affiliation_retries;
	}

	/**
	 * @return the affiliation_maxAmount
	 */
	public String getAffiliation_maxAmount() {
		return affiliation_maxAmount;
	}

	/**
	 * @param affiliation_maxAmount
	 *            the affiliation_maxAmount to set
	 */
	public void setAffiliation_maxAmount(String affiliation_maxAmount) {
		this.affiliation_maxAmount = affiliation_maxAmount;
	}

	/**
	 * @return the affiliation_internalcredithistory
	 */
	public String getAffiliation_internalcredithistory() {
		return affiliation_internalcredithistory;
	}

	/**
	 * @param affiliation_internalcredithistory
	 *            the affiliation_internalcredithistory to set
	 */
	public void setAffiliation_internalcredithistory(
			String affiliation_internalcredithistory) {
		this.affiliation_internalcredithistory = affiliation_internalcredithistory;
	}

	/**
	 * @return the affiliation_externalcredithistory
	 */
	public String getAffiliation_externalcredithistory() {
		return affiliation_externalcredithistory;
	}

	/**
	 * @param affiliation_externalcredithistory
	 *            the affiliation_externalcredithistory to set
	 */
	public void setAffiliation_externalcredithistory(
			String affiliation_externalcredithistory) {
		this.affiliation_externalcredithistory = affiliation_externalcredithistory;
	}

	/**
	 * @return the batchCommission
	 */
	public String getBatchCommission() {
		return batchCommission;
	}

	/**
	 * @param batchCommission
	 *            the batchCommission to set
	 */
	public void setBatchCommission(String batchCommission) {
		this.batchCommission = batchCommission;
	}

	/**
	 * @return the onLineCommission
	 */
	public String getOnLineCommission() {
		return onLineCommission;
	}

	/**
	 * @param onLineCommission
	 *            the onLineCommission to set
	 */
	public void setOnLineCommission(String onLineCommission) {
		this.onLineCommission = onLineCommission;
	}

	/**
	 * @return the batchCommisionsArray
	 */
	public SelectItem[] getBatchCommisionsArray() {
		return batchCommisionsArray;
	}

	/**
	 * @param batchCommisionsArray
	 *            the batchCommisionsArray to set
	 */
	public void setBatchCommisionsArray(SelectItem[] batchCommisionsArray) {
		this.batchCommisionsArray = batchCommisionsArray;
	}

	/**
	 * @return the onlineCommisionsArray
	 */
	public SelectItem[] getOnlineCommisionsArray() {
		return onlineCommisionsArray;
	}

	/**
	 * @param onlineCommisionsArray
	 *            the onlineCommisionsArray to set
	 */
	public void setOnlineCommisionsArray(SelectItem[] onlineCommisionsArray) {
		this.onlineCommisionsArray = onlineCommisionsArray;
	}

	/**
	 * @return the comissionBatchCode
	 */
	public String getComissionBatchCode() {
		return comissionBatchCode;
	}

	/**
	 * @param comissionBatchCode
	 *            the comissionBatchCode to set
	 */
	public void setComissionBatchCode(String comissionBatchCode) {
		this.comissionBatchCode = comissionBatchCode;
	}

	/**
	 * @return the comissionOnlineCode
	 */
	public String getComissionOnlineCode() {
		return comissionOnlineCode;
	}

	/**
	 * @param comissionOnlineCode
	 *            the comissionOnlineCode to set
	 */
	public void setComissionOnlineCode(String comissionOnlineCode) {
		this.comissionOnlineCode = comissionOnlineCode;
	}

	/**
	 * @return the comissionOnlineCodeSelected
	 */
	public String getComissionOnlineCodeSelected() {
		return comissionOnlineCodeSelected;
	}

	/**
	 * @param comissionOnlineCodeSelected
	 *            the comissionOnlineCodeSelected to set
	 */
	public void setComissionOnlineCodeSelected(
			String comissionOnlineCodeSelected) {
		this.comissionOnlineCodeSelected = comissionOnlineCodeSelected;
	}

	/**
	 * @return the comissionBatchCodeSelected
	 */
	public String getComissionBatchCodeSelected() {
		return comissionBatchCodeSelected;
	}

	/**
	 * @param comissionBatchCodeSelected
	 *            the comissionBatchCodeSelected to set
	 */
	public void setComissionBatchCodeSelected(String comissionBatchCodeSelected) {
		this.comissionBatchCodeSelected = comissionBatchCodeSelected;
	}

	/**
	 * @return the referenceRequired
	 */
	public String getReferenceRequired() {
		return referenceRequired;
	}

	/**
	 * @param referenceRequired the referenceRequired to set
	 */
	public void setReferenceRequired(String referenceRequired) {
		this.referenceRequired = referenceRequired;
	}


	/**
	 * @return the affiliation_SelectedBanorte
	 */
	public String getAffiliation_SelectedBanorte() {
		return affiliation_SelectedBanorte;
	}

	/**
	 * @param affiliation_SelectedBanorte the affiliation_SelectedBanorte to set
	 */
	public void setAffiliation_SelectedBanorte(String affiliation_SelectedBanorte) {
		this.affiliation_SelectedBanorte = affiliation_SelectedBanorte;
	}

	/**
	 * @return the affiliation_NoselectedBanorte
	 */
	public String getAffiliation_NoselectedBanorte() {
		return affiliation_NoselectedBanorte;
	}

	/**
	 * @param affiliation_NoselectedBanorte the affiliation_NoselectedBanorte to set
	 */
	public void setAffiliation_NoselectedBanorte(
			String affiliation_NoselectedBanorte) {
		this.affiliation_NoselectedBanorte = affiliation_NoselectedBanorte;
	}

	/**
	 * @return the affiliation_SelectedEmisor
	 */
	public String getAffiliation_SelectedEmisor() {
		return affiliation_SelectedEmisor;
	}

	/**
	 * @param affiliation_SelectedEmisor the affiliation_SelectedEmisor to set
	 */
	public void setAffiliation_SelectedEmisor(String affiliation_SelectedEmisor) {
		this.affiliation_SelectedEmisor = affiliation_SelectedEmisor;
	}

	/**
	 * @return the affiliation_NoselectedEmisor
	 */
	public String getAffiliation_NoselectedEmisor() {
		return affiliation_NoselectedEmisor;
	}

	/**
	 * @param affiliation_NoselectedEmisor the affiliation_NoselectedEmisor to set
	 */
	public void setAffiliation_NoselectedEmisor(String affiliation_NoselectedEmisor) {
		this.affiliation_NoselectedEmisor = affiliation_NoselectedEmisor;
	}

	/**
	 * @return the formatLayoutSelectedSiga
	 */
	public String getFormatLayoutSelectedSiga() {
		return formatLayoutSelectedSiga;
	}

	/**
	 * @param formatLayoutSelectedSiga the formatLayoutSelectedSiga to set
	 */
	public void setFormatLayoutSelectedSiga(String formatLayoutSelectedSiga) {
		this.formatLayoutSelectedSiga = formatLayoutSelectedSiga;
	}

	/**
	 * @return the payworksClabeSelectSi
	 */
	public String getPayworksClabeSelectSi() {
		return payworksClabeSelectSi;
	}

	/**
	 * @param payworksClabeSelectSi the payworksClabeSelectSi to set
	 */
	public void setPayworksClabeSelectSi(String payworksClabeSelectSi) {
		this.payworksClabeSelectSi = payworksClabeSelectSi;
	}

	/**
	 * @return the payworksClabeSelectNo
	 */
	public String getPayworksClabeSelectNo() {
		return payworksClabeSelectNo;
	}

	/**
	 * @param payworksClabeSelectNo the payworksClabeSelectNo to set
	 */
	public void setPayworksClabeSelectNo(String payworksClabeSelectNo) {
		this.payworksClabeSelectNo = payworksClabeSelectNo;
	}

	/**
	 * @return the moduleTypeSelect10
	 */
	public String getModuleTypeSelect10() {
		return moduleTypeSelect10;
	}

	/**
	 * @param moduleTypeSelect10 the moduleTypeSelect10 to set
	 */
	public void setModuleTypeSelect10(String moduleTypeSelect10) {
		this.moduleTypeSelect10 = moduleTypeSelect10;
	}

	/**
	 * @return the moduleTypeSelect11
	 */
	public String getModuleTypeSelect11() {
		return moduleTypeSelect11;
	}

	/**
	 * @param moduleTypeSelect11 the moduleTypeSelect11 to set
	 */
	public void setModuleTypeSelect11(String moduleTypeSelect11) {
		this.moduleTypeSelect11 = moduleTypeSelect11;
	}

	/**
	 * @return the moduleTypeSelect97
	 */
	public String getModuleTypeSelect97() {
		return moduleTypeSelect97;
	}

	/**
	 * @param moduleTypeSelect97 the moduleTypeSelect97 to set
	 */
	public void setModuleTypeSelect97(String moduleTypeSelect97) {
		this.moduleTypeSelect97 = moduleTypeSelect97;
	}

	/**
	 * @return the moduleTypeSelectOtro
	 */
	public String getModuleTypeSelectOtro() {
		return moduleTypeSelectOtro;
	}

	/**
	 * @param moduleTypeSelectOtro the moduleTypeSelectOtro to set
	 */
	public void setModuleTypeSelectOtro(String moduleTypeSelectOtro) {
		this.moduleTypeSelectOtro = moduleTypeSelectOtro;
	}

	/**
	 * @return the batchCommissionSelect
	 */
	public String getBatchCommissionSelect() {
		return batchCommissionSelect;
	}

	/**
	 * @param batchCommissionSelect the batchCommissionSelect to set
	 */
	public void setBatchCommissionSelect(String batchCommissionSelect) {
		this.batchCommissionSelect = batchCommissionSelect;
	}

	/**
	 * @return the onlineCommissionSelect
	 */
	public String getOnlineCommissionSelect() {
		return onlineCommissionSelect;
	}

	/**
	 * @param onlineCommissionSelect the onlineCommissionSelect to set
	 */
	public void setOnlineCommissionSelect(String onlineCommissionSelect) {
		this.onlineCommissionSelect = onlineCommissionSelect;
	}

	/**
	 * @return the comissionOnLineHash
	 */
	public HashMap<String, Payrate> getComissionOnLineHash() {
		return comissionOnLineHash;
	}

	/**
	 * @param comissionOnLineHash the comissionOnLineHash to set
	 */
	public void setComissionOnLineHash(HashMap<String, Payrate> comissionOnLineHash) {
		this.comissionOnLineHash = comissionOnLineHash;
	}

	/**
	 * @return the affiliation_officerdepositexent
	 */
	public String getAffiliation_officerdepositexent() {
		return affiliation_officerdepositexent;
	}

	/**
	 * @param affiliation_officerdepositexent the affiliation_officerdepositexent to set
	 */
	public void setAffiliation_officerdepositexent(
			String affiliation_officerdepositexent) {
		this.affiliation_officerdepositexent = affiliation_officerdepositexent;
	}

	/**
	 * @return the monthlyAmount
	 */
	public String getMonthlyAmount() {
		return monthlyAmount;
	}

	/**
	 * @param monthlyAmount the monthlyAmount to set
	 */
	public void setMonthlyAmount(String monthlyAmount) {
		this.monthlyAmount = monthlyAmount;
	}

	public String getCdemitter() {
		return cdemitter;
	}

	public void setCdemitter(String cdemitter) {
		this.cdemitter = cdemitter;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public String getIssuerAccount() {
		return issuerAccount;
	}

	public void setIssuerAccount(String issuerAccount) {
		this.issuerAccount = issuerAccount;
	}

	public String getIssuerAddress() {
		return issuerAddress;
	}

	public void setIssuerAddress(String issuerAddress) {
		this.issuerAddress = issuerAddress;
	}

	public String getIssuerStreet() {
		return issuerStreet;
	}

	public void setIssuerStreet(String issuerStreet) {
		this.issuerStreet = issuerStreet;
	}

	public String getIssuerExtNum() {
		return issuerExtNum;
	}

	public void setIssuerExtNum(String issuerExtNum) {
		this.issuerExtNum = issuerExtNum;
	}

	public String getIssuerIntNum() {
		return issuerIntNum;
	}

	public void setIssuerIntNum(String issuerIntNum) {
		this.issuerIntNum = issuerIntNum;
	}

	public String getIssuerColony() {
		return issuerColony;
	}

	public void setIssuerColony(String issuerColony) {
		this.issuerColony = issuerColony;
	}

	public String getIssuerZipCode() {
		return issuerZipCode;
	}

	public void setIssuerZipCode(String issuerZipCode) {
		this.issuerZipCode = issuerZipCode;
	}

	public String getIssuerState() {
		return issuerState;
	}

	public void setIssuerState(String issuerState) {
		this.issuerState = issuerState;
	}

	public String getIssuerCity() {
		return issuerCity;
	}

	public void setIssuerCity(String issuerCity) {
		this.issuerCity = issuerCity;
	}
	
	public String getIssuerContactEmail() {
		return issuerContactEmail;
	}

	public void setIssuerContactEmail(String issuerContactEmail) {
		this.issuerContactEmail = issuerContactEmail;
	}

	public String getIssuerContactName() {
		return issuerContactName;
	}

	public void setIssuerContactName(String issuerContactName) {
		this.issuerContactName = issuerContactName;
	}

	public String getIssuerContactAreaCode() {
		return issuerContactAreaCode;
	}

	public void setIssuerContactAreaCode(String issuerContactAreaCode) {
		this.issuerContactAreaCode = issuerContactAreaCode;
	}

	public String getIssuerContactPhone() {
		return issuerContactPhone;
	}

	public void setIssuerContactPhone(String issuerContactPhone) {
		this.issuerContactPhone = issuerContactPhone;
	}

	public String getIssuerCategoryCode() {
		return issuerCategoryCode;
	}

	public void setIssuerCategoryCode(String issuerCategoryCode) {
		this.issuerCategoryCode = issuerCategoryCode;
	}
// El get se hace por medio de un metodo para llenar las categorias
//	public SelectItem[] getIssuerCategoriesArray() {
//		return issuerCategoriesArray;
//	} 

	public void setIssuerCategoriesArray(SelectItem[] issuerCategoriesArray) {
		this.issuerCategoriesArray = issuerCategoriesArray;
	}

	public void setIssuerCitiesArray(SelectItem[] issuerCitiesArray) {
		this.issuerCitiesArray = issuerCitiesArray;
	}

	public boolean getIsChangeFianza() {
		return isChangeFianza;
	}

	public void setIsChangeFianza(boolean isChangeFianza) {
		this.isChangeFianza = isChangeFianza;
	}

	public String getCreditoActivoRetrasoSi() {
		return creditoActivoRetrasoSi;
	}

	public void setCreditoActivoRetrasoSi(String creditoActivoRetrasoSi) {
		this.creditoActivoRetrasoSi = creditoActivoRetrasoSi;
	}

	public String getCreditoActivoRetrasoNo() {
		return creditoActivoRetrasoNo;
	}

	public void setCreditoActivoRetrasoNo(String creditoActivoRetrasoNo) {
		this.creditoActivoRetrasoNo = creditoActivoRetrasoNo;
	}

	public String getCreditoHistoricoRetrasoSi() {
		return creditoHistoricoRetrasoSi;
	}

	public void setCreditoHistoricoRetrasoSi(String creditoHistoricoRetrasoSi) {
		this.creditoHistoricoRetrasoSi = creditoHistoricoRetrasoSi;
	}

	public String getCreditoHistoricoRetrasoNo() {
		return creditoHistoricoRetrasoNo;
	}

	public void setCreditoHistoricoRetrasoNo(String creditoHistoricoRetrasoNo) {
		this.creditoHistoricoRetrasoNo = creditoHistoricoRetrasoNo;
	}

	public String getNombreDelTerritorioDirector() {
		return nombreDelTerritorioDirector;
	}

	public void setNombreDelTerritorioDirector(String nombreDelTerritorioDirector) {
		this.nombreDelTerritorioDirector = nombreDelTerritorioDirector;
	}

	public String getNivelRiesgoCD() {
		return nivelRiesgoCD;
	}

	public void setNivelRiesgoCD(String nivelRiesgoCD) {
		this.nivelRiesgoCD = nivelRiesgoCD;
	}

	/*public String getRfcRepresentanteLegal1() {
		return rfcRepresentanteLegal1;
	}

	public void setRfcRepresentanteLegal1(String rfcRepresentanteLegal1) {
		this.rfcRepresentanteLegal1 = rfcRepresentanteLegal1;
	}

	public String getRfcRepresentanteLegal2() {
		return rfcRepresentanteLegal2;
	}

	public void setRfcRepresentanteLegal2(String rfcRepresentanteLegal2) {
		this.rfcRepresentanteLegal2 = rfcRepresentanteLegal2;
	}*/
}
