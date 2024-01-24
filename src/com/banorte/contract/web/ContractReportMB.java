
package com.banorte.contract.web;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banorte.contract.business.AttributeRemote;
import com.banorte.contract.business.ContractAttributeRemote;
import com.banorte.contract.business.ExecutiveRemote;
import com.banorte.contract.business.PlanRemote;
import com.banorte.contract.business.StatusContractRemote;
import com.banorte.contract.business.TemplateRemote;
import com.banorte.contract.layout.LayoutTempleteContract;
import com.banorte.contract.model.Affiliation;
import com.banorte.contract.model.Attribute;
import com.banorte.contract.model.Bitacora;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.ContractSearch;
import com.banorte.contract.model.ContractStatusHistory;
import com.banorte.contract.model.Executive;
import com.banorte.contract.model.Maintance;
import com.banorte.contract.model.Plan;
import com.banorte.contract.model.StatusContract;
import com.banorte.contract.model.Template;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.BitacoraType;
import com.banorte.contract.util.BitacoraUtil;
import com.banorte.contract.util.ContractAttributeComparator;
import com.banorte.contract.util.ContractStatusHistoryComparator;
import com.banorte.contract.util.DirectaStatus;
import com.banorte.contract.util.EncryptBd;
import com.banorte.contract.util.Formatter;
import com.banorte.contract.util.ProductType;
import com.banorte.contract.util.ReportUtil;
import com.banorte.contract.util.pdf.PdfBuilder;
import com.banorte.contract.util.pdf.PdfTemplateBindingContract;
import com.banorte.contract.util.pdf.PdfTemplateReader;


public class ContractReportMB extends ReportMB {

    private static Logger log = Logger.getLogger(ContractReportMB.class.getName());
    private PdfTemplateBindingContract pdfTemplateBinding;
    protected ContractAttributeRemote contractAttributeBean;    
    protected StatusContractRemote statusBean;
    protected ExecutiveRemote executiveBean;
    protected AttributeRemote attributeBean;

    private String NAME_TEMPLATE ="nameTemplate";
    private String PATHTEMPLATE ="pathTemplate";
    
//    protected AffiliationRemote affiliationBean;
    private TemplateRemote templateBean;

    
    private AttributeMB attributeMB;
    private String user;
    private String reference;
    private Collection<ContractSearch> contractList;
    private boolean toPrint;
    private Contract contract;
    private String searchoption;
    private String currentVersionFiscalName;
    private Integer authorization;
    private static final Integer STATUSID_FORMALIZADO 		= 3;
    private static final Integer STATUSID_COFORMALIZADO 	= 4;
    private static final Integer STATUSID_CANCELADO 		= 8;
    private static final Integer STATUSID_AUTORIZACIONPTE 	=9;
    private static final Integer STATUSID_ACTIVACIONPTE 	=10;
    private boolean pyme	 								= false;
    private boolean isOIP;
    private boolean licenseBEM 								= Boolean.FALSE;
    private boolean bem 									= Boolean.FALSE;
    private boolean contractMtto							= Boolean.FALSE;
    
    //Propiedades para Support
    private Contract selectedContract;
    private String currentPage ="";
    
    //private static final String FORMALIZE = "FORMALIZE";

    //Para tabla de afiliacion
    public String plan;
	public String noCtaDlls;
	public String noCtaPesos;
	public PlanRemote planBean;
    
	public ContractReportMB() {
        super();
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpSession session = (HttpSession) (facesContext.getExternalContext().getSession(true));
        Map<String, String> userSession = (HashMap) session.getAttribute(ApplicationConstants.PROFILE);
        this.user = userSession.get(ApplicationConstants.UID);
        
        this.currentPage = request.getRequestURI();
        
        if ( templateBean == null) {
            templateBean = (TemplateRemote) EjbInstanceManager.getEJB(TemplateRemote.class);
        }
        
        if ( statusBean == null ) {
            statusBean = (StatusContractRemote) EjbInstanceManager.getEJB(StatusContractRemote.class);
        }
        
        if (contractAttributeBean == null){
            contractAttributeBean = (ContractAttributeRemote) EjbInstanceManager.getEJB(ContractAttributeRemote.class);
        }
        
        if (executiveBean == null){
            executiveBean = (ExecutiveRemote) EjbInstanceManager.getEJB(ExecutiveRemote.class);
        }     
        if(attributeBean == null){
        	attributeBean = (AttributeRemote) EjbInstanceManager.getEJB(AttributeRemote.class);
        }
        if(planBean == null){
        	planBean = (PlanRemote) EjbInstanceManager.getEJB(PlanRemote.class);
        }

        this.searchoption = "Folio";
        this.licenseBEM = Boolean.FALSE;
    }
    
    protected Collection<Template> getTemplateOption(Integer productId) {
        Collection<Template> templates = templateBean.findAll();        
        Collection<Template> result = new ArrayList();
        for ( Template template : templates) {            
            if ( template.getProduct().getProductid().equals(productId)) 
                result.add(template);                
        }        
        
        return result;
    }
    
    
    public void setResetForm() {
    	contract = new Contract();
    	this.searchoption = "Folio";
    	this.setReference("");
    	contractList = null;
    	this.selectedContract = null;
    }
    
    public String getResetForm() {
    	
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	
    	if(!this.currentPage.equals(request.getRequestURI())){

    		this.currentPage = request.getRequestURI();
    		
    		setResetForm();
    	}
        return "";
    }
    
    public AttributeMB getAttributeMB() {
        return attributeMB;
    }

    public void setAttributeMB(AttributeMB attributeMB) {
        this.attributeMB = attributeMB;
    }
    
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference.toUpperCase();
    }

    public String getCurrentVersionFiscalName() {
        ContractAttribute contractAttribute =contractAttributeBean.findByContractidAndAttributeid(this.getContract().getContractId(), 178);
        if(contractAttribute!=null)
        	currentVersionFiscalName = contractAttribute.getValue()!=null?contractAttribute.getValue():"";
        else
        	currentVersionFiscalName="";
        
        return currentVersionFiscalName;
    }

    public void setCurrentVersionFiscalName(String currentVersionFiscalName) {
        this.currentVersionFiscalName = currentVersionFiscalName;
    }
       
    public void setContractList(Collection<ContractSearch> list) {
        this.contractList = list;
    }

    public Collection<ContractSearch> getContractList() {
    	
    	this.getResetForm();
    	
        if(contractList==null)
            this.getReportResult();
        
        return this.contractList;
    }
    
    //Propiedad: selectedContract
    public void setSelectedContract(Contract item) {
        this.selectedContract = item;
    }
    public Contract getSelectedContract() {
        
        return this.selectedContract;
    }
    
    
    public void setSearchoption(String searchoption) {
        this.searchoption = searchoption.trim();
    }
    
    public String getSearchoption() {
        return this.searchoption;
    }

    public Integer getAuthorizacion () {
        return this.authorization;
    }
    public String getFormatedDate(Date date){
        Formatter format = new Formatter();
        return format.formatDateToString(date);
    }
    
    public String getReportResult() {
    	
        ArrayList<ContractSearch> result 	= new ArrayList<ContractSearch>();
        ContractSearch search 				= new ContractSearch();
        List<String> listUsersToken 		= new ArrayList<String>();
        ReportUtil reportUtil 				= new ReportUtil();
        FacesContext fCtx 					= FacesContext.getCurrentInstance();
        String userNumber 					= BitacoraUtil.getUserNumberSession(fCtx);
        String folio 						= ApplicationConstants.EMPTY_STRING;
        String folioVersion					= ApplicationConstants.EMPTY_STRING;
        
        if ( this.reference != null && !this.reference.equals("") ) {
            if ( this.searchoption.equals(ApplicationConstants.OPTION_FOLIO) ) {
                try {
                	setContractMtto( reportUtil.isContractMtto(reference) );
                   Contract cont=contractBean.findByRefLastVersion(this.reference); 
                   if (cont != null){
                	   this.licenseBEM = reportUtil.getLicenseBEM(this.user	, this.reference, cont);
                       this.bem = reportUtil.isContractBEM(this.reference);
                   
	                    if (this.licenseBEM ){
	                    	listUsersToken = contractBean.findUsersToken(cont.getContractId());   	
	                    	cont.setListUsersToken( listUsersToken  );
	                    }
	                    result.add(complementSearch(cont, search));
	                    BitacoraUtil.getInstance().saveBitacoraFolio(BitacoraType.SEARCH_CONTRACT_NEW_FOLIO, userNumber, reference, folioVersion);
                   }
                } catch ( NumberFormatException e ) {
                }
            } else if ( this.searchoption.equals(ApplicationConstants.OPTION_SIC) ) {              
                try {
					List<Contract> contract_ = contractBean.findBySicLastVersion(Long.parseLong(this.reference));

					for (Contract cont : contract_) {
						String referencia = cont.getReference();
						String sic = cont.getClient().getSic().toString();
						if (!this.licenseBEM) {
							if (!this.bem) {
								this.licenseBEM = reportUtil.getLicenseBEM(this.user, referencia, cont);
								this.bem = reportUtil.isContractBEM(referencia);
							}
						}
						listUsersToken = contractBean.findUsersToken(cont.getContractId());
						cont.setListUsersToken(listUsersToken);
						result.add(complementSearch(cont, search));
						BitacoraUtil.getInstance().saveBitacoraSIC(BitacoraType.SEARCH_CONTRACT_NEW_SIC,userNumber, sic, folio, folioVersion);
					}
					
                } catch ( NumberFormatException e ) {
                }
            }
        }

        this.setContractList(result);

        return SEARCH;
    }
    

    public void getSupportReportResult() {

    	ArrayList<ContractSearch> result 	= new ArrayList<ContractSearch>();
        contractList 						= new ArrayList<ContractSearch>();
        FacesContext fCtx 					= FacesContext.getCurrentInstance();
        String userNumber 					= BitacoraUtil.getUserNumberSession(fCtx);
        String folio 						= ApplicationConstants.EMPTY_STRING;
        String folioVersion					= ApplicationConstants.EMPTY_STRING;

    	if ( this.reference != null && !this.reference.equals("") ) {
        	ContractSearch search;
        	try {

        		List<Contract> contractList	=contractBean.findByReference(this.reference);         
        		
        		for(Contract contract : contractList){
	        		search = new ContractSearch();
	        	
	        		//Buscar especificamente el contrato para obtener los atributos
	        		contract = contractBean.findById(contract.getContractId());
	        		
	        		search.setContract(contract);
	        		result.add(search);
	        		folio = contract.getReference();
	        		folioVersion = String.valueOf( contract.getVersion());
	        	}
        	
	           
	        } catch ( NumberFormatException e ) {
	        }
        }

        this.reference ="";
        this.setContractList(result);
        this.selectedContract =null;
        BitacoraUtil.getInstance().saveBitacoraFolio(BitacoraType.SUPPORT, userNumber, folio, folioVersion);
    }

	/**
	 * Metodo que obtiene el detalle de la tabla ContractStatusHistory en base al
	 * contractId enviado como parametro.
	 */
    public void getContractDetail() {

    	// Para recuperar el parámetro enviado por el JSP
		String key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("contractId");		
		if( key != null){
			Integer contracId = Integer.parseInt(key);
			for(ContractSearch entity : this.contractList){
				if(entity.getContract().getContractId().equals(contracId)){
		            this.selectedContract =  entity.getContract();
		            //gina: para ordenar la lista
		            Collection<ContractStatusHistory> coleccion=selectedContract.getContractStatusHistoryCollection();
		            List<ContractStatusHistory> lista=(List<ContractStatusHistory>)coleccion;
					Collections.sort(lista, new ContractStatusHistoryComparator());
		            selectedContract.setContractStatusHistoryCollection(lista);
		            Collection<ContractAttribute> coll = selectedContract.getContractAttributeCollection();
		            List<ContractAttribute> list = (List<ContractAttribute>)coll;
		            Collections.sort(list, new ContractAttributeComparator());
		            selectedContract.setContractAttributeCollection(list);
					break;
				}
			}
		}
		showAffiliationCollection();
    }
	
    private ContractSearch complementSearch(Contract cont, ContractSearch search){    	
        search = new ContractSearch();
        search.setContract(cont);
        if(cont.getContractStatusHistoryCollection()!=null){
            for(ContractStatusHistory cs:cont.getContractStatusHistoryCollection()){
                if(cs.getStatusContract().getStatusid()==4){
                    search.setFormalDate(cs.getModifydate());
                }
            }
        } 
        //Comentarios
        ContractAttribute contractAttribute =contractAttributeBean.findByContractidAndAttributeid(cont.getContractId(), 297);
        if (contractAttribute != null)
            search.setOperationComments(contractAttribute.getValue());
        else
            search.setOperationComments(" ");
        
        //Razon Social
        contractAttribute =contractAttributeBean.findByContractidAndAttributeid(cont.getContractId(), 178);
        if (contractAttribute != null)
            search.setFiscalName(contractAttribute.getValue());
        else
            search.setFiscalName(" ");
        
        //Fecha Instalacion
        StringBuffer statusDetail =  new StringBuffer(cont.getStatus().getDescription()); 
        ContractAttribute installDateAtt = contractAttributeBean.findByContractidAndAttributeid(cont.getContractId(), 417);
        if(installDateAtt!=null && installDateAtt.getValue()!=null && !installDateAtt.getValue().equals("")){
        	statusDetail.append("\n" + installDateAtt.getValue());
        }
        search.setStatusDetail(statusDetail.toString());
        //Emisora o Afiliacion, Poducto y Liga
        if(cont.getProduct().getProductid()==1){//BEM
            search.setNavigationUrl("/contract/ordenBEM.jsf");
            search.setProduct(cont.getProduct().getName());
            contractAttribute =contractAttributeBean.findByContractidAndAttributeid(cont.getContractId(), 21);
              if (contractAttribute != null)
                search.setEmitter(contractAttribute.getValue());
              else
                search.setEmitter(" ");
              
             //buscar respuesta de directa 
              List<Maintance> maintanceList = maintanceBean.findByContractId(search.getContract().getContractId());

              if(maintanceList.size() > 0)
              {
            	  //obtener el ultimo registro enviado a directa relacionado a este contrato
            	  Maintance lastDirectaSent = maintanceList.get(0);
            	  
            	  if (lastDirectaSent.getStatus()!=null && 
            			  lastDirectaSent.getStatus().getId().intValue()!= DirectaStatus.HEB_PROCESADO.value().intValue())
            	  {
            		  search.setToOutboundReport(true);
            		  search.setOutboundReportUrl("/contract/outboundReport.jsf");
            	  }
              }
              
              
        }else if(cont.getProduct().getProductid()==2){//Nomina
                search.setNavigationUrl("/contract/ordenNomina.jsf");
                search.setProduct(cont.getProduct().getName());
                contractAttribute =contractAttributeBean.findByContractidAndAttributeid(cont.getContractId(), 344); //EMISORA NOMINA
                if (contractAttribute != null)
                    search.setEmitter(contractAttribute.getValue());
               else
                    search.setEmitter(" ");
        }
        
        else if(cont.getProduct().getProductid()== ProductType.COBRANZA_DOMICILIADA.value()){
        	search.setNavigationUrl("/contract/ordenCD.jsf");
            search.setProduct(cont.getProduct().getName());
            //buscar el atributo para sacarle el id (por si no estan igual en la BD)
            Attribute att = attributeBean.findByFieldname("cdemitter");
            contractAttribute =contractAttributeBean.findByContractidAndAttributeid(cont.getContractId(), att.getAttributeid()); //cdemitter
            if (contractAttribute != null)
                search.setEmitter(contractAttribute.getValue());
           else
                search.setEmitter(" ");
        }
        
        else if(cont.getProduct().getProductid()== ProductType.MTTO_ALTA_CUENTAS.value()){
        	search.setNavigationUrl("/contract/ordenMttoBEMAccounts.jsf");
            search.setProduct(cont.getProduct().getName());
        }
        
        else if(cont.getProduct().getProductid()== ProductType.MTTO_DROP_COMPANY.value()){
        	search.setNavigationUrl("/contract/ordenMttoBEMDropCompany.jsf");
            search.setProduct(cont.getProduct().getName());
        }
        
        else if(cont.getProduct().getProductid()== ProductType.MTTO_NUEVA_CONTRASENA.value()){
        	search.setNavigationUrl("/contract/ordenMttoBEMNewPassword.jsf");
            search.setProduct(cont.getProduct().getName());
        }
        
        else if(cont.getProduct().getProductid()== ProductType.MTTO_REPOSICION_TOKEN.value()){
        	search.setNavigationUrl("/contract/ordenMttoBEMRenovToken.jsf");
            search.setProduct(cont.getProduct().getName());
        }
        
        else if(cont.getProduct().getProductid()== ProductType.MTTO_REPOSICION_TOKEN.value()){
        	search.setNavigationUrl("/contract/ordenMttoBEMRenovToken.jsf");
            search.setProduct(cont.getProduct().getName());
        }
        
        else if(cont.getProduct().getProductid()== ProductType.MTTO_CONVENIO_MODIFICATORIO.value()){
        	search.setNavigationUrl("/contract/ordenMttoBEMUpdateAgree.jsf");
            search.setProduct(cont.getProduct().getName());
        }
        
        else if(cont.getProduct().getProductid()== ProductType.MTTO_DROP_TOKENS.value()){
        	search.setNavigationUrl("/contract/ordenMttoBEMDropToken.jsf");
            search.setProduct(cont.getProduct().getName());
        }
        
        else if(cont.getProduct().getProductid()== ProductType.MTTO_DROP_ACCOUNT.value()){
        	search.setNavigationUrl("/contract/ordenMttoBEMDropAccount.jsf");
            search.setProduct(cont.getProduct().getName());
        }
        
        else if(cont.getProduct().getProductid()== ProductType.MTTO_ADD_TOKEN.value()){
        	search.setNavigationUrl("/contract/ordenMttoBEMAddToken.jsf");
            search.setProduct(cont.getProduct().getName());
        }
        
        else{//Adquirente
        	if(cont.getProduct().getProductid()==6){ //SIP
        		search.setNavigationUrl("/contract/ordenAfiliacionOIP.jsf");
        	}else{
        		search.setNavigationUrl("/contract/ordenAfiliacionAdquirente.jsf");
        	}
        	for (Affiliation affi : cont.getAffiliationCollection()) {
            	if (affi != null){
                	    if (affi.getNumaffilmn()!=null&&affi.getNumaffildlls()!=null){
                            search.setEmitter("Af. Pesos: " + affi.getNumaffilmn() + " Af. Dolares: " + affi.getNumaffildlls());
                        }else if(affi.getNumaffilmn()!=null){
                        	search.setEmitter("Af. Pesos: " + affi.getNumaffilmn());
                        }else if(affi.getNumaffildlls()!=null){
                        search.setEmitter("Af. Dolares: " + affi.getNumaffildlls());
                        }else {
                        		search.setEmitter(" ");
                            }
                        
                        if(cont.getProduct().getProductid()==5){
                            search.setProduct(affi.getProductdesc());
                        }else{
                            search.setProduct(affi.getProductdesc() + " " + affi.getSoluciontype());
                            }
                }
            }

        }
       
        search.setEditContract(getEditOption(cont));
        search.setCancelContract(getCancelOption(cont));
        if(contractAttributeBean.findByContractidAndAttributeid(cont.getContractId(), 415)==null){ //quien autoriza cuentas 1
        	search = this.getActivateOption(search, this.getToActivate(cont),1);
        }else if(contractAttributeBean.findByContractidAndAttributeid(cont.getContractId(), 416)==null){	
        	search = this.getActivateOption(search, this.getToActivate(cont),2); //quien autoriza cuentas 2
        }
        search = this.getAuthOption(search, this.getToAuthorize(cont));		// Formalizacion de contrato
        return search;
    }

    
    //Activar / Desactivar Opcion Para Editar
    private boolean getEditOption(Contract cont) {
        boolean editOption=false;
        if (cont.getStatus().getStatusid()!=339 && //en propuesta
        		(cont.getStatus().getStatusid()<4 || //formalizado por segundo usuario. Para que no se pueda editar si se envia a operaciones
        				cont.getStatus().getStatusid()==7 || //rechazado
        				cont.getStatus().getStatusid()>100)){//rechazado (motivos)
            editOption=true; 
        }
        return editOption;
        
    }  
    
    //Activar / Desactivar Opcion Para Cancelar
    private boolean getCancelOption(Contract cont) {        
        boolean cancelOption=false;
        if (cont.getStatus().getStatusid()!=5 && cont.getStatus().getStatusid()!=8 && cont.getStatus().getStatusid()!=11){
            cancelOption=true; 
        }
        return cancelOption;
        
    }  
    //Activar/Desactivar Opcion Para Formalizar    
    private ContractSearch getAuthOption(ContractSearch search, Integer option) {      
        if ( option.intValue() == 1 ) {
            search.setEnable(true);
        }else {
            search.setEnable(false);
        }
        return search;
    }
    
    //Activar/Desactivar Opcion Para Activar Cuentas BEM    
    private ContractSearch getActivateOption(ContractSearch search, Integer option, Integer user) {        
        if ( option.intValue() == 1 ) {
        	if(user==1)
        		search.setActivate(true);
        	else 
        		search.setActivate2user(true);
        } else {
        	if(user==1)
        		search.setActivate(false);
        	else 
        		search.setActivate2user(false);
        }
        return search;
    }
    
    
    // Formalizar / Coformalizar un contrato
    private Integer getToAuthorize(Contract contract) {
        Integer result = 0; //si es 0 no muestra la liga, si es 1 si la muestra
        if ( contract != null ) {
        	String auth1 = contract.getAuthoffempnum1() != null ? contract.getAuthoffempnum1().toUpperCase() : "";
            String auth2 = contract.getAuthoffempnum2() != null ? contract.getAuthoffempnum2().toUpperCase() : "";
          
            Executive exec=executiveBean.findByUserid(this.user.toUpperCase());            
            if((contract.getStatus()!=null) && ((contract.getStatus().getStatusid())<4)){ // si el estatus es menor a 4
            	if (auth1.equalsIgnoreCase("") && auth2.equalsIgnoreCase("")) { // Ninguna Vez formalizado - Activar el poder formalizaren ambos casos
    				result = 1;
    			} else if (!auth1.equalsIgnoreCase("") && !auth2.equalsIgnoreCase("")) {  // Completamente Formalizado - Esta lleno no debemos de mostrar nada
    				//result=0
    			} else if (contract.getProduct().getProductid() != 6) { // Si el producto no es OIP uno de los dos autorizadores debe ser eBanking
    				if (exec != null) {// Validar que el actual usuario a formalizar es ebanking
    					//String usuarioUpper = this.user.toUpperCase();
    					if (!this.user.equalsIgnoreCase(auth1)) { // Validar que no es el mismo usuario que formalizo la primera vez, aunque sea ebanking
    						result = 1;
    					}
    				}
    			} else {//=OIP
    				result = 1;
    			}
            }
        }
        return result;
    }
    
    // Autorizacion para Activacion de cuentas BEM
    private Integer getToActivate(Contract contract) {
        Integer result = 0;
        if ( contract != null && contract.getProduct().getProductid()==1 && (contract.getStatus().getStatusid()==6 || contract.getStatus().getStatusid()==9)) {
            String auth1 = contractAttributeBean.findByContractidAndAttributeid(contract.getContractId(), 415) != null ?contractAttributeBean.findByContractidAndAttributeid(contract.getContractId(), 415).getValue() : "";
            String auth2 = contractAttributeBean.findByContractidAndAttributeid(contract.getContractId(), 416) != null ?contractAttributeBean.findByContractidAndAttributeid(contract.getContractId(), 416).getValue() : "";
             
//            Executive exec=executiveBean.findByUserid(this.user.toUpperCase());            
//          	if (exec!=null){//Validar que el actual usuario a formalizar es ebanking     
          		  
      		if ( auth1.equals("") && auth2.equals("") ) {//Ninguna Vez formalizado para su activacion => Activar la opcion de formalizar
      			result = 1;
      		//} else  if ( auth1.equals("") || auth2.equals("") && (!auth1.equals(this.user) && !auth2.equals(this.user))) { // Al menos sin coformalizacion  => Activar la opcion de formalizar
      		} else  if ( !auth1.equals("") && auth2.equals("") && (!auth1.equals(this.user) && !auth2.equals(this.user))) { // Al menos sin coformalizacion  => Activar la opcion de formalizar
      			Executive exec=executiveBean.findByUserid(this.user.toUpperCase());            
  
      			if (exec!=null){//Validar que el actual usuario a formalizar es ebanking     
               		result = 1;
               	}
            }  
//            }            
        }
        return result;
    }
    
    
    
    public Collection<Contract> getContractByUser() {
        return contractBean.findByUser(this.user);
    }
    
    public void createPDF() {
        FacesContext fCtx = FacesContext.getCurrentInstance();   
        HttpServletRequest request = (HttpServletRequest) fCtx.getExternalContext().getRequest();
		   	
	   	   String  nameTemplate = request.getParameter(NAME_TEMPLATE);
	       String  pathTemplate = request.getParameter(PATHTEMPLATE);
	       Integer flagTemplate = Integer.parseInt(request.getParameter("flagTemplate"));
        BitacoraUtil.getInstance().saveBitacoraFolio(ApplicationConstants.BITACORA_DESCRIPTION_PDF+" "+nameTemplate, user, reference, contract.getVersion().toString());
        //log.info("******** Muestra Documento " + nameTemplate + "... " + flagTemplate.intValue());
        pdfTemplateBinding = new PdfTemplateBindingContract();
        pdfTemplateBinding.setContract(this.getContract());
        pdfTemplateBinding.setAffiliationId(1);
        createPdfOrXLSResponse(getPath() + pathTemplate+nameTemplate, nameTemplate, true, flagTemplate.intValue() == 1 ? true : false);
    }
    
    protected void createPdfOrXLSResponse(String template, String fileNameResult, boolean pdforxls, boolean edit) {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        FileInputStream fis = null;
        try {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            if ( edit && pdforxls) {
                PdfBuilder builder = new PdfBuilder(byteArray);
                PdfTemplateReader reader = new PdfTemplateReader(template);
                builder.setReader(reader);
                builder.setBinding(this.pdfTemplateBinding);
                builder.createDocument();
            } else {
            	fis = new FileInputStream(template);		

                int theByte = 0;
                while ((theByte = fis.read()) != -1){
                  byteArray.write(theByte);
                }
                fis.close();
            }
            byte[] pdf = byteArray.toByteArray();
            byteArray.close();
            HttpServletResponse response = (HttpServletResponse) fCtx.getExternalContext().getResponse();
            if ( pdforxls )
                response.setContentType("application/pdf");
            else
                response.setContentType("application/vnd.ms-excel");
            response.setContentLength(pdf.length);
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileNameResult + "\"");
            ServletOutputStream out = response.getOutputStream();
            out.write(pdf);
        } catch (IOException ioEx) {
            log.severe(ioEx.getMessage());
        } finally {
        	safeClose(fis);
            fCtx.responseComplete();
        }

    }
    
    public Template[] getFormatListMtto() {
		this.setToPrint(false);
		Collection<Template> templateCollection 	= getTemplateOption(contract.getProduct().getProductid());
		Collection<Template> templateColl2 = new ArrayList();
		String[] fills = {"quantityAccounts"};
		Map<String, String> map = new LayoutTempleteContract().bindFrom(contract, fills);
		//nueva pagina de cuentas propias
		for(Template t: templateCollection){
//			if((t.getName().equals(ApplicationConstants.TEMPLATE_MTTO_ACCOUNTS2))){
//				if(Integer.parseInt(map.get("quantityAccounts"))>=5){
//					templateColl2.add(t);
//				}
//            }else {
    	        templateColl2.add(t);	
//            }
		}
	
		Template[] templateArray = new Template[templateColl2.size()];
		return templateColl2.toArray(templateArray);
	}
    
    public Template[] getFormatList() {
        this.setToPrint(false);
        Collection<Template> templateCollection = getTemplateOption(contract.getProduct().getProductid());
        Collection<Template> templateCollection2 = new ArrayList();
        
        if ( contract.getProduct().getProductTypeid().getProductTypeid().intValue() == 1 ) {
            
            String[] fills = {"selectedbemplan", "accownnum_1","accownnum_2","accownnum_3","accownnum_4","accownnum_5",
                              "accownnum_6","accownnum_7","accownnum_8","accownnum_9","accownnum_10","accownnum_11",
                              "accownnum_12","accownnum_13","accownnum_14","accownnum_15","accothersnum_1","serialtoken_7",};
            
            Map<String, String> map = new LayoutTempleteContract().bindFrom(contract, fills);
            
            for (Template template : templateCollection ) {
//                if ( template.getEditable().intValue() == 0 ) { //se hace editable para folio
                if ( template.getName().equals(ApplicationConstants.BEM_ANEXO_TARIFAS_PLAN1) || 
                     template.getName().equals(ApplicationConstants.BEM_ANEXO_TARIFAS_PLAN2) ||
                     template.getName().equals(ApplicationConstants.BEM_ANEXO_TARIFAS_PLAN4) || 
//                         template.getName().equals(ApplicationConstants.BEM_ANEXO_TARIFAS_PLAN20)|| //se da de baja plan 20 150203
                     template.getName().equals(ApplicationConstants.BEM_ANEXO_TARIFAS_PLAN1200)) 
                {
                	String planSeleccionado=map.get("selectedbemplan").replaceAll(" ", "");
                	String formato=template.getName().replaceAll("BEMAnexoTarifas", "").replaceAll(".pdf", "");
                    if(formato.equalsIgnoreCase(planSeleccionado)){
                    	templateCollection2.add(template);
                    }
//                } 
//                    else {
//                        templateCollection2.add(template);
//                    }
                } else {
                    // Revisar para agregar los teceros
                if ( template.getName().equals(ApplicationConstants.BEM_CUENTAS_TERCEROS) ) {
                    if ( map.get("accothersnum_1") != null && !map.get("accothersnum_1").equals("") ){
                        templateCollection2.add(template);
                    }
                } else{
                    if(template.getName().equals(ApplicationConstants.BEM_TOKENS_ADICIONALES)){
                        if( map.get("serialtoken_7") != null && !map.get("serialtoken_7").equals("") ){
                        	templateCollection2.add(template);
                        }
                    }else{
                        if(template.getName().equals(ApplicationConstants.BEM_CUENTAS_AUTORIZADAS1)){
                            if(map.get("accownnum_1") != null && !map.get("accownnum_1").equals("") ||
                               map.get("accownnum_2") != null && !map.get("accownnum_2").equals("") ||
                               map.get("accownnum_3") != null && !map.get("accownnum_3").equals("") ||
                               map.get("accownnum_4") != null && !map.get("accownnum_4").equals("") ||
                               map.get("accownnum_5") != null && !map.get("accownnum_5").equals("") )
                            {
                                templateCollection2.add(template);
                            }
                        }else{
                            if(template.getName().equals(ApplicationConstants.BEM_CUENTAS_AUTORIZADAS2)){
                               if( map.get("accownnum_6") != null && !map.get("accownnum_6").equals("") ||
                                   map.get("accownnum_7") != null && !map.get("accownnum_7").equals("") ||
                                   map.get("accownnum_8") != null && !map.get("accownnum_8").equals("") ||
                                   map.get("accownnum_9") != null && !map.get("accownnum_9").equals("") ||
                                   map.get("accownnum_10") != null && !map.get("accownnum_10").equals("") ){
                                   	templateCollection2.add(template);
                                }
                            }else{
                                if(template.getName().equals(ApplicationConstants.BEM_CUENTAS_AUTORIZADAS3)){
                                    if(map.get("accownnum_11") != null && !map.get("accownnum_11").equals("") ||
                                       map.get("accownnum_12") != null && !map.get("accownnum_12").equals("") ||
                                       map.get("accownnum_13") != null && !map.get("accownnum_13").equals("") ||
                                       map.get("accownnum_14") != null && !map.get("accownnum_14").equals("") ||
                                       map.get("accownnum_15") != null && !map.get("accownnum_15").equals("") ){
                                    	templateCollection2.add(template);
                                    }
                                }else{
                                    templateCollection2.add(template);
                                }
                            }
                        }
                        }
                    }
                }
            }//fin for
            
        } else if ( contract.getProduct().getProductTypeid().getProductTypeid().intValue() == 2 ) {
            
            String[] fills = {"payrolltype"};
  
            Map<String, String> map = new LayoutTempleteContract().bindFrom(contract, fills);
  
            for (Template template : templateCollection ) {
            	if(map.get("payrolltype").equals("Impulso Nomina")){

            		if (template.getTemplateid()!= 11)
            			templateCollection2.add(template);
            	}else{
            		
            		if (template.getTemplateid()!= 48)
            			templateCollection2.add(template);
            	}
            			
            }
            
        }
        
        else if(contract.getProduct().getName().equals( ApplicationConstants.PREFIJO_CD)){
        	
        	String[] fills 				= {"affiliation_havedepositcompany"};
        	Boolean optionFianza		= Boolean.FALSE;
            Map<String, String> map 	= new LayoutTempleteContract().bindFrom(contract, fills);
        	
        	if(map.get("affiliation_havedepositcompany").equals( ApplicationConstants.OPTION_HAVE_DEPOSIT )){
    			optionFianza = Boolean.TRUE;
    		}
    		
    		for(Template template :templateCollection){
    			if(!template.getTemplateid().equals( ApplicationConstants.ID_LAYOUT_CD_EXCEPCION_FIANZA) ){
    				templateCollection2.add(template);
    			}else if(!optionFianza){
    				templateCollection2.add(template);
    			}
    		}
        }
       
        else if ( contract.getProduct().getProductTypeid().getProductTypeid().intValue() == 3 || //---Adquirente
                    contract.getProduct().getProductTypeid().getProductTypeid().intValue() == 4 ||
                    contract.getProduct().getProductTypeid().getProductTypeid().intValue() == 5) { 
                    
            for (Template template : templateCollection ) {
                Collection<Affiliation> affiliation= contract.getAffiliationCollection();    // affiliation
                Affiliation affi = null;
                Attribute attribute = null;
                ContractAttribute contractAttribute = null;
                //System.out.println("lista de template adquiriente: "+template.getName());// MGBC
                if (affiliation.size() > 0) {
                    affi = affiliation.iterator().next();              
                    attribute = attributeBean.findByFieldname("red");
                    contractAttribute = contractAttributeBean.findByContractidAndAttributeid(contract.getContractId(), attribute.getAttributeid());
                    String red = contractAttribute!=null?contractAttribute.getValue():"0";
                    if(red.equalsIgnoreCase("0")){
                    	if(template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_ANEXO_A_BANORTE)){
                    		templateCollection2.add(template);
                    	}
                    	if(template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_ANEXO_C_BANORTE)){
                    		templateCollection2.add(template);
                    	}
                    	if(template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_ANEXO_E_BANORTE)){
                    		/*//codigo anterior
                    		 * attribute = attributeBean.findByFieldname("alliance");
                        	contractAttribute = contractAttributeBean.findByContractidAndAttributeid(contract.getContractId(), attribute.getAttributeid());
                        	String alianza = contractAttribute!=null?contractAttribute.getValue():null;
                        	if(alianza!=null && alianza.equalsIgnoreCase("Agregador")){
                        		templateCollection2.add(template);
                        	}*/
                        	//Garantia liquida 
                        	attribute = attributeBean.findByFieldname("aff_garantiaLiquida");
                        	contractAttribute = contractAttributeBean.findByContractidAndAttributeid(contract.getContractId(), attribute.getAttributeid());
                        	String garantiaLiquida = contractAttribute!=null?contractAttribute.getValue():null;                        	
                        	if(garantiaLiquida!=null && garantiaLiquida.equalsIgnoreCase("true")){
                        		attribute = attributeBean.findByFieldname("affiliationismn");
                            	contractAttribute = contractAttributeBean.findByContractidAndAttributeid(contract.getContractId(), attribute.getAttributeid());
                            	String affiliationismn = contractAttribute!=null?contractAttribute.getValue():null;                        	
                        		if(affiliationismn != null && "X".equalsIgnoreCase(affiliationismn)){
                        			templateCollection2.add(template);
                        		}
                        	}
                    	}
                    	if(template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_ANEXO_E_BANORTE_DOLARES)){
                    		//Garantia liquida: si es seleccionada garantia liquida y la divisa dolares o ambas se agrega el anexo E Dolares a la lista 
                        	attribute = attributeBean.findByFieldname("aff_garantiaLiquida");
                        	contractAttribute = contractAttributeBean.findByContractidAndAttributeid(contract.getContractId(), attribute.getAttributeid());
                        	String garantiaLiquida = contractAttribute!=null?contractAttribute.getValue():null;                        	
                        	if(garantiaLiquida!=null && garantiaLiquida.equalsIgnoreCase("true")){
                        		/*attribute = attributeBean.findByFieldname("ventasEstimadasMensualesDolares");
                            	contractAttribute = contractAttributeBean.findByContractidAndAttributeid(contract.getContractId(), attribute.getAttributeid());
                            	String ventasEstimadasMensualesDolares = contractAttribute!=null?contractAttribute.getValue():null;                        	
                        		if(ventasEstimadasMensualesDolares != null && !ventasEstimadasMensualesDolares.trim().equals("")){
                        			templateCollection2.add(template);
                        		}*/
                        		attribute = attributeBean.findByFieldname("affiliationisdlls");
                            	contractAttribute = contractAttributeBean.findByContractidAndAttributeid(contract.getContractId(), attribute.getAttributeid());
                            	String affiliationisdlls = contractAttribute!=null?contractAttribute.getValue():null;                        	
                        		if(affiliationisdlls != null && "X".equalsIgnoreCase(affiliationisdlls)){
                        			templateCollection2.add(template);
                        		}
                        	}
                    	}
                    	if(template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_CARTA_DISMINUCION_EXENCION_GL)){
                    		//Garantia liquida 
                        	attribute = attributeBean.findByFieldname("aff_porcentajeGL");
                        	contractAttribute = contractAttributeBean.findByContractidAndAttributeid(contract.getContractId(), attribute.getAttributeid());
                        	String porcentajeGL = contractAttribute!=null?contractAttribute.getValue():null;
                        	//if(porcentajeGL!=null && (porcentajeGL.equalsIgnoreCase("excepcion")||(porcentajeGL.equalsIgnoreCase("disminucion"))||(porcentajeGL.equalsIgnoreCase("8")))){
                        	if(porcentajeGL!=null && (porcentajeGL.equalsIgnoreCase("excepcion")||(porcentajeGL.equalsIgnoreCase("disminucion"))||porcentajeGL.equalsIgnoreCase("8")||(porcentajeGL.equalsIgnoreCase("5")))){
                        		templateCollection2.add(template);
                        	}
                    	}
                    	if(template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_ANEXO_MUJER_PYME_BANORTE)){
                    		//Mujer PyME 
                        	attribute = attributeBean.findByFieldname("mujerPyME");
                        	contractAttribute = contractAttributeBean.findByContractidAndAttributeid(contract.getContractId(), attribute.getAttributeid());
                        	String mujerPyME = contractAttribute!=null?contractAttribute.getValue():null;
                        	if("true".equalsIgnoreCase(mujerPyME)){
                        		templateCollection2.add(template);
                        	}
                    	}
                    	if(template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_CARATULA_BANORTE)){
                    		templateCollection2.add(template);
                    	}
                    	if(template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_CLAUSULADO)){
                    		templateCollection2.add(template);
                    	}
                    	if(contract.getProduct().getProductid()==6){
                    		if(template.getName().equalsIgnoreCase(ApplicationConstants.ADENDUM_OIP)){
                    			templateCollection2.add(template);	
                        	}
                    		if(template.getName().equalsIgnoreCase(ApplicationConstants.SIP_ANEXO_COMISIONES)){
                    			templateCollection2.add(template);	
                    		}
                    		if(template.getName().equalsIgnoreCase(ApplicationConstants.SIP_CARATULA)){
                    			templateCollection2.add(template);	
                    		}
                    		if(template.getName().equalsIgnoreCase(ApplicationConstants.SIP_PARAMETRICO)){
                    			templateCollection2.add(template);	
                    		}
                		}
                    	if(template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_PAGA_RETIRA_EFECTIVO)){
                    		attribute = attributeBean.findByFieldname("aff_cashback");
                        	contractAttribute = contractAttributeBean.findByContractidAndAttributeid(contract.getContractId(), attribute.getAttributeid());
                        	String cashback = contractAttribute!=null?contractAttribute.getValue():null;
                        	if(cashback!=null && cashback.equalsIgnoreCase("X")){
                        		templateCollection2.add(template);
                        	}
                    	}
                    	
                    }else if(red.equalsIgnoreCase("1")){
                    	if(template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_ANEXO_A_IXE)){
                    		templateCollection2.add(template);
                    	}
                    	if(template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_ANEXO_C_IXE)){
                    		templateCollection2.add(template);
                    	}
                    	if(template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_CARATULA_IXE)){
                    		templateCollection2.add(template);
                    	}
                    	if(template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_CLAUSULADO)){
                    		templateCollection2.add(template);
                    	}
                    }
                }
            }
        }

        Template[] templateArray = new Template[templateCollection2.size()];
        return templateCollection2.toArray(templateArray);
    }
    
    public String toShow() {
        FacesContext fCtx = FacesContext.getCurrentInstance();  
        Map<String, String> params = fCtx.getExternalContext().getRequestParameterMap();  
        Integer idContract = Integer.parseInt(params.get("contractId"));

        this.setToPrint(true);
        this.setContract(contractBean.findById(idContract));
        
        return SUCCESS;
    }
    
    public String toShowMtto() {
        FacesContext fCtx = FacesContext.getCurrentInstance();  
        Map<String, String> params = fCtx.getExternalContext().getRequestParameterMap();  
        Integer idContract = Integer.parseInt(params.get("contractId"));

        this.setToPrint(true);
        this.setContract(contractBean.findById(idContract));
        
        
        return ApplicationConstants.SUCCESSMTTO;
    }
    
    public String toAuthorize() {
        FacesContext fCtx = FacesContext.getCurrentInstance();  
        Map<String, String> params = fCtx.getExternalContext().getRequestParameterMap();  
        Integer idContract = Integer.parseInt(params.get("contractId"));
        Contract contract_ = contractBean.findById(idContract);
        String emp1 = contract_.getAuthoffempnum1() != null ? contract_.getAuthoffempnum1() : "";
        String emp2 = contract_.getAuthoffempnum2() != null ? contract_.getAuthoffempnum2() : "";
        StatusContract status = null;
        ContractStatusHistory statusHistory = new ContractStatusHistory();
        String userNumber= BitacoraUtil.getUserNumberSession(fCtx);
        String folio=contract_.getReference();
        String folioVersion= String.valueOf( contract_.getVersion());
        Integer statusId = contract_.getStatus().getStatusid();
        
        if(emp1.equalsIgnoreCase("")&&emp2.equalsIgnoreCase("")){//si los dos autorizadores están vacíos, se FORMALIZA
        	if(contract_.getStatus().getStatusid()<3){ //si el contrato es nuevo o editado
        		contract_.setAuthoffempnum1(user);//se formaliza
        		status = statusBean.findById(STATUSID_FORMALIZADO);
        		statusHistory.setCommentary("APPSSTB contrato formalizado, pendiente de CoFormalizacion.");
                BitacoraUtil.getInstance().saveBitacoraFolio(BitacoraType.FORMALIZAR_FOLIO, userNumber, folio, folioVersion);	
        	}else{
        		BitacoraUtil.getInstance().saveBitacoraFolio("Se intento formalizar un folio con estatus mayor o igual 3", userNumber, folio, folioVersion);	
        	}
        }else if(!emp1.equalsIgnoreCase("")&&emp2.equalsIgnoreCase("")){//si el primer autorizador esta lleno y el segundo vacio se COFORMALIZA
        	if(statusId<4){ //contrato nuevo, editado o formalizado
        		if(!user.equalsIgnoreCase(emp1)){//si el usuario es diferente al que formalizo
        			contract_.setAuthoffempnum2(user);//se coformaliza
            		status = statusBean.findById(STATUSID_COFORMALIZADO);
                    statusHistory.setCommentary("APPSSTB contrato coformalizado, listo para su envio a operaciones.");
                    BitacoraUtil.getInstance().saveBitacoraFolio(BitacoraType.COFORMALIZAR_FOLIO, userNumber, folio, folioVersion);		
        		}
        	}else{
        		BitacoraUtil.getInstance().saveBitacoraFolio("Se intento coFormalizar un folio con estatus mayor o igual a 4", userNumber, folio, folioVersion);
        	}
        }
        
        if(status!=null ){
        	contract_.setStatus(status);
            statusHistory.setContract(contract_);
            statusHistory.setStatusContract(status);
            statusHistory.setOffempnum("appsstb");
            statusHistory.setModifydate(new Date());

            contract_.add(statusHistory);
            
            contractBean.update(contract_);	
        }
        return getReportResult();        
    }


    public String toActivate() {
        FacesContext fCtx = FacesContext.getCurrentInstance();  
        Map<String, String> params = fCtx.getExternalContext().getRequestParameterMap();  
        Integer idContract = Integer.parseInt(params.get("contractId"));
        
        Contract contract_ = contractBean.findById(idContract);
        
        String emp1 = contractAttributeBean.findByContractidAndAttributeid(contract_.getContractId(), 415) != null ?contractAttributeBean.findByContractidAndAttributeid(contract_.getContractId(), 415).getValue() : null;
        String emp2 = contractAttributeBean.findByContractidAndAttributeid(contract_.getContractId(), 416) != null ?contractAttributeBean.findByContractidAndAttributeid(contract_.getContractId(), 416).getValue() : null;
        Collection<ContractAttribute> atts = contract_.getContractAttributeCollection();     
        ContractAttribute newAtt = new ContractAttribute();

        if(emp1==null || emp1.equals("")){
        	emp1=this.user;
        	newAtt.setAttribute(this.getAttributeMB().getByFieldname("authactivateoffempnum1"));
         	newAtt.setValue(emp1);
        	atts.add(newAtt);
        	
        } else if(emp2==null || emp2.equals("")){
        	emp2=this.user;
            newAtt.setAttribute(this.getAttributeMB().getByFieldname("authactivateoffempnum2"));
        	newAtt.setValue(emp2);
        	atts.add(newAtt);
        }
        contract_.setContractAttributeCollection(atts);
        contractBean.update(contract_);
  
        StatusContract status = null;
        ContractStatusHistory statusHistory = new ContractStatusHistory();

        if ( emp1!=null && emp2!=null) {
            status = statusBean.findById(STATUSID_ACTIVACIONPTE);
            statusHistory.setCommentary("APPSSTB contrato listo para su envio a operaciones, para su activacion.");
        } else {
        	status = statusBean.findById(STATUSID_AUTORIZACIONPTE);
            statusHistory.setCommentary("APPSSTB contrato pendiente de 2a autorizacion para su activacion.");
        }
        contract_.setStatus(status);
        statusHistory.setContract(contract_);
        statusHistory.setStatusContract(status);
        statusHistory.setOffempnum("appsstb");
        statusHistory.setModifydate(new Date());

        contract_.add(statusHistory);
        
        contractBean.update(contract_);
        
        return getReportResult();        
    }

    public String toCancel() {
        FacesContext fCtx 			= FacesContext.getCurrentInstance();  
        Map<String, String> params 	= fCtx.getExternalContext().getRequestParameterMap();  
        Integer idContract 			= Integer.parseInt(params.get("contractId"));
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        String folio 				= ApplicationConstants.EMPTY_STRING;
        String folioVersion 		= ApplicationConstants.EMPTY_STRING;
        
        Contract contract_ = contractBean.findById(idContract);   
        contract_.setAuthoffempnum1(this.user);
        contract_.setAuthoffempnum2(this.user);

        StatusContract status = null;
        ContractStatusHistory statusHistory = new ContractStatusHistory();
        status = statusBean.findById(STATUSID_CANCELADO);            
        statusHistory.setCommentary("APPSSTB contrato cancelado.");

        contract_.setStatus(status);
        statusHistory.setContract(contract_);
        statusHistory.setStatusContract(status);
        statusHistory.setOffempnum("appsstb");
        statusHistory.setModifydate(new Date());

        contract_.add(statusHistory);        
        contractBean.update(contract_);
        
        
        folio 				= contract_.getReference();
        folioVersion 		= String.valueOf( contract_.getVersion());
        
        BitacoraUtil.getInstance().saveBitacoraFolio(BitacoraType.CANCEL_CONTRATO, userNumber, folio, folioVersion);
        
        
        return getReportResult();        
    }
    
    
    
    /**
     * Metodo para darle el valor correcto a las variables
     */
    public void showAffiliationCollection(){
    	EncryptBd decrypt = new EncryptBd();
    	Collection<Affiliation> afiliacion = selectedContract.getAffiliationCollection();
    	if(afiliacion!=null && afiliacion.size()>0){
    		for(Affiliation af: afiliacion){
        		String id=af.getCommercialplan()!=null?af.getCommercialplan():"0";
        		Integer planid=0;
        		try {
        			planid = Integer.parseInt(id);
    			} catch (Exception e) {
    				
    			}
        		Plan planContrato = planBean.findById(planid); 
        		if(planContrato!=null){
        			plan=planContrato.getName()!=null?planContrato.getName():"";
        		}else{
        			plan=id;
        		}
        		String ctaPesos=af.getAccountnumbermn()!=null?af.getAccountnumbermn():"";
        		String ctaDlls=af.getAccountnumberdlls()!=null?af.getAccountnumberdlls():"";
        		if(ctaPesos.trim().length()>10){
        			noCtaPesos=decrypt.decrypt(ctaPesos);
        		}else{
        			noCtaPesos=ctaPesos;
        		}
        		if(ctaDlls.trim().length()>10){
        			noCtaDlls=decrypt.decrypt(ctaDlls);
        		}else{
        			noCtaDlls=ctaDlls;
        		}
        	}
    	}
    }
    
    public static void safeClose(FileInputStream fis) {
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				log.severe(e.getMessage());
			}
		}
	}
    
    
    
    //setters getters
    public Contract getContract() {
        return this.contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
    
    //  Utilizados como Banderas para mostrar el Template Final
    public boolean getToPrint() {
        return toPrint;
    }

    public void setToPrint(boolean toPrint) {
        this.toPrint = toPrint;        
    }
    
    public String getPath() {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath(".");
		if (realPath == null) {
			//realPath = "C:/temp";
			//realPath = "C:/Users/liliana.rivera/Documents/Proyecto/contracts/contract"; 
			//realPath = "C:/Users/miguel.borbolla.STKBANORTE/Desktop/EbankingSoft/eBankingVersionActual/contract";
			//realPath= "C:/Users/celso.balbuena/Documents/WEBank/contract/web";
			//realPath = "C:/Users/miguel.borbolla.STKBANORTE/Desktop/EbankingSoft/eBankingVersionActual/tmp_fuentes_y_docs/APPEBANKING_Celso/contract/web";
			//realPath = "C:/hbocm870/eBankingVersionParaPRD_mujerPyME/APPEBANKING/contract/web";
			//realPath = "C:/hbocm870/eBankingVersionParaPRD_reporteHeB/APPEBANKING/contract/web";
			realPath = "C:/Users/daarevalo/eclipse-workspace/APPEBANKING/contract/web";
		}
        return realPath;
    }

	/**
	 * @return the licenseBEM
	 */
	public boolean isLicenseBEM() {
		return licenseBEM;
	}

	/**
	 * @param licenseBEM the licenseBEM to set
	 */
	public void setLicenseBEM(boolean licenseBEM) {
		this.licenseBEM = licenseBEM;
	}

	/**
	 * @return the bem
	 */
	public boolean isBem() {
		return bem;
	}

	/**
	 * @param bem the bem to set
	 */
	public void setBem(boolean bem) {
		this.bem = bem;
	}

	/**
	 * @return the contractMtto
	 */
	public boolean isContractMtto() {
		return contractMtto;
	}

	/**
	 * @param contractMtto the contractMtto to set
	 */
	public void setContractMtto(boolean contractMtto) {
		this.contractMtto = contractMtto;
	}
	
	public void pruebaMetodo(){
		System.out.println("metodo prueba para crear peso en harvest");
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getNoCtaDlls() {
		return noCtaDlls;
	}

	public void setNoCtaDlls(String noCtaDlls) {
		this.noCtaDlls = noCtaDlls;
	}

	public String getNoCtaPesos() {
		return noCtaPesos;
	}

	public void setNoCtaPesos(String noCtaPesos) {
		this.noCtaPesos = noCtaPesos;
	}
	
}
