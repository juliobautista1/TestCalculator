/*

 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banorte.contract.web;

import java.beans.IntrospectionException;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banorte.contract.business.AffiliationRemote;
import com.banorte.contract.business.AttributeRemote;
import com.banorte.contract.business.BitacoraRemote;
import com.banorte.contract.business.BranchRemote;
import com.banorte.contract.business.CitiesRemote;
import com.banorte.contract.business.ClientRemote;
import com.banorte.contract.business.ContactTypeRemote;
import com.banorte.contract.business.ContractAttributeRemote;
import com.banorte.contract.business.ContractRemote;
import com.banorte.contract.business.EmployeeRemote;
import com.banorte.contract.business.ExecutiveBranchRemote;
import com.banorte.contract.business.ExecutiveRemote;
import com.banorte.contract.business.ProductRemote;
import com.banorte.contract.business.ReferenceRemote;
import com.banorte.contract.business.StatesRemote;
import com.banorte.contract.business.StatusContractRemote;
import com.banorte.contract.business.TemplateRemote;
import com.banorte.contract.layout.LayoutOperations;
import com.banorte.contract.layout.LayoutOperationsResponse;
import com.banorte.contract.model.Attribute;
import com.banorte.contract.model.Bitacora;
import com.banorte.contract.model.Branch;
import com.banorte.contract.model.Cities;
import com.banorte.contract.model.Client;
import com.banorte.contract.model.ClientContact;
import com.banorte.contract.model.ContactType;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.ContractMessageErrors;
import com.banorte.contract.model.ContractStatusHistory;
import com.banorte.contract.model.Employee;
import com.banorte.contract.model.Executive;
import com.banorte.contract.model.ExecutiveBranch;
import com.banorte.contract.model.LegalRepresentative;
import com.banorte.contract.model.Product;
import com.banorte.contract.model.States;
import com.banorte.contract.model.StatusContract;
import com.banorte.contract.model.Template;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.AttrConstants;
import com.banorte.contract.util.BitacoraComparator;
import com.banorte.contract.util.BitacoraType;
import com.banorte.contract.util.BitacoraUtil;
import com.banorte.contract.util.ContractUtil;
import com.banorte.contract.util.Formatter;
import com.banorte.contract.util.ProductType;
import com.banorte.contract.util.pdf.PdfBuilder;
import com.banorte.contract.util.pdf.PdfTemplateBinding;
import com.banorte.contract.util.pdf.PdfTemplateReader;

/**
 * 
 * @author MRIOST
 */
public abstract class ContractAbstractMB {

	private static Logger log = Logger.getLogger(ContractAbstractMB.class.getName());
	private static final String SUCCESS = "SUCCESS";
	private static final String FAILED = "FAILED";
	private static final String DOUBLE_CLICK = "DOUBLE_CLICK";
	private static final Integer STATUSID_FORMALIZADO = 3;
	private AttributeMB attributeMB;
	private Client client;
	private Contract contract; 
	private LegalRepresentative legalRep1;
	private LegalRepresentative legalRep2;
	private ClientContact clientContact1;
	protected ClientContact clientContact2;
	protected ClientContact clientContact3;
	private ClientContact clientContact4;
	private ClientContact clientContact5;
	private ClientContact clientContact6;
	private ClientContact clientContact7;
	protected ClientRemote clientBean;
	protected ContactTypeRemote contactTypeBean;
	protected ProductRemote productBean;
	protected ContractRemote contractBean;
	protected StatusContractRemote statusBean;
	protected ContractAttributeRemote contractAttributeBean;
	protected AffiliationRemote contractAffiliationBean;
	protected StatesRemote statesBean;
	protected CitiesRemote citiesBean;
	protected ExecutiveRemote executiveBean;
	protected BitacoraRemote bitacoraBean;
	protected ExecutiveBranchRemote executiveBranchBean;
	protected AttributeRemote attributeBean;
	
	protected ReferenceRemote referenceBean;
	private String celebrationplace;
	private String celebrationstate;
	private String celebrationdate; 
	private String applicationdate;
	public Formatter formatDate;
	public TemplateRemote templateBean;
	public EmployeeRemote employeeBean;
	public BranchRemote branchBean;
	// atributos pasa "Datos del funcionario que colocï¿½ el producto"
	private String officername;
	private String officerlastname;
	private String officermothersln;
	private String officerempnumber;
	private String officerposition;
//--nuevos campos para layout (contrato unico IXE)	
	private String officerTerritory;
	private String officerRegion;
	
	//Director de Sucursal
	private String branchDirectorNumber;
	private String branchDirectorName;
	private String branchDirectorLastName;
	private String branchDirectorMothersLn;
	private String branchDirectorposition;

	// atributos pasa "Datos del funcionario E-banking"
	private String officerebankingname;
	private String officerebankinglastname;
	private String officerebankingmothersln;
	private String officerebankingempnumber;
	private String officerebankingposition;
	private String officerebankingnumfirm;

	// atributos pasa "Datos de la sucursal"
	private String crnumber;
	private String branchname;
	private String branchstreet;
	private String branchcolony;
	private String branchcounty;
	private String branchcity;
	private String branchstate;
	private String branchphone;
	private String branchfax;
	private String bankingsector;

	private String officerrepname_1;
	private String officerreplastname_1;
	private String officerrepmothersln_1;
	private String officerrepempnumber_1;
	private String officerrepposition_1;
	private String officerrepname_2;
	private String officerreplastname_2;
	private String officerrepmothersln_2;
	private String officerrepempnumber_2;
	private String officerrepposition_2;
	private String comments;
	
	private String officerrepfirmnumber_1;
	private String officerrepfirmnumber_2;

	protected ArrayList<ContractMessageErrors> generalInfoErrorsList = new ArrayList();
	protected Collection<ContractMessageErrors> errorsList = new ArrayList<ContractMessageErrors>();
	private SelectItem[] statesArray;
	private SelectItem[] citiesCelebrationArray;
	private SelectItem[] citiesClientArray;
	private SelectItem[] citiesBranchArray;

	private boolean toPrint;
	private boolean toAuthorize;
	private boolean toEdit; 
	protected boolean isOIP;
	protected boolean pyme 				=  Boolean.FALSE;
	protected boolean foundBranch 		=  Boolean.FALSE;
	protected boolean validationMtto 	=  Boolean.TRUE;
	protected boolean validationEDO 	=  Boolean.TRUE;

	//Variables para Mitigacion de Riesgos
	private String startMaintanceProcessDate;
	private String processDays;
	
	//Variables para Reportes
	private String startReportDate;
	private String endReportDate;
	private String dateReportFilter; //seleccion de filtro de fecha
	private String statusReportFilter;//seleccion de filtro de estatus
	private Integer process;
	private Integer productId;
	private boolean procesoEnvio;
	private boolean procesoLectura;
	boolean procesoCargaCuentasMujerPyME;
	
	//Bitacora
	private ArrayList<Bitacora> bitacoraList;
	private String startBitacoraDate;
	private String endBitacoraDate;
	
	private String userNumberBitacora;
	private String referenceNumberBitacora;
	private String nombreUsuarioBitacora;
	
	// atributos para "Datos del funcionario Banca Especializada"
	private String officerebankingEspName;
	private String officerebankingEspLastname;
	private String officerebankingEspMothersln;
	private String officerebankingEspEmpnumber;
	private String officerebankingEspPosition;

	// atributos para "Datos del funcionario Director Territorial"
	private String officerebankingTname;
	private String officerebankingTlastname;
	private String officerebankingTmothersln;
	private String officerebankingTempnumber;
	private String officerebankingTposition;
	
	protected String red;
	
	private List<ExecutiveBranch> executiveBranchList;
	private List<Executive> executiveList;
	
	private String statusGarantiaLiquida;
	private boolean isSeleccionClienteNuevo;
	
	//F-113829 Actualización Formatos Cobranza / abril 2022
	//private String rfcRepresentanteLegal1;
    //private String rfcRepresentanteLegal2;
	
	// private <string,boolean> e = new List<string,boolean>;

	public ContractAbstractMB() {
		if (clientBean == null) {
			clientBean = (ClientRemote) EjbInstanceManager.getEJB(ClientRemote.class);
		}
		if (productBean == null) {
			productBean = (ProductRemote) EjbInstanceManager.getEJB(ProductRemote.class);
		}
		if (statusBean == null) {
			statusBean = (StatusContractRemote) EjbInstanceManager.getEJB(StatusContractRemote.class);
		}
		if (contractBean == null) {
			contractBean = (ContractRemote) EjbInstanceManager.getEJB(ContractRemote.class);
		}
		if (referenceBean == null) {
			referenceBean = (ReferenceRemote) EjbInstanceManager.getEJB(ReferenceRemote.class);
		}
		if (contactTypeBean == null) {
			contactTypeBean = (ContactTypeRemote) EjbInstanceManager.getEJB(ContactTypeRemote.class);
		}

		if (templateBean == null) {
			templateBean = (TemplateRemote) EjbInstanceManager.getEJB(TemplateRemote.class);
		}

		if (statesBean == null) {
			statesBean = (StatesRemote) EjbInstanceManager.getEJB(StatesRemote.class);
		}

		if (citiesBean == null) {
			citiesBean = (CitiesRemote) EjbInstanceManager.getEJB(CitiesRemote.class);
		}

		if (contractAttributeBean == null) {
			contractAttributeBean = (ContractAttributeRemote) EjbInstanceManager.getEJB(ContractAttributeRemote.class);
		}

		if (employeeBean == null) {
			employeeBean = (EmployeeRemote) EjbInstanceManager.getEJB(EmployeeRemote.class);
		}

		if (executiveBean == null) {
			executiveBean = (ExecutiveRemote) EjbInstanceManager.getEJB(ExecutiveRemote.class);
		}
		
		if (branchBean == null) {
			branchBean = (BranchRemote) EjbInstanceManager.getEJB(BranchRemote.class);
		}
		
		if (bitacoraBean == null) {
			bitacoraBean = (BitacoraRemote) EjbInstanceManager.getEJB(BitacoraRemote.class);
		}
		
		if(executiveBranchBean == null){
			executiveBranchBean = (ExecutiveBranchRemote) EjbInstanceManager.getEJB(ExecutiveBranchRemote.class);
		}
		if(attributeBean == null){
			attributeBean = (AttributeRemote) EjbInstanceManager.getEJB(AttributeRemote.class);
		}

		client 			= new Client();
		contract 		= new Contract();
		legalRep1 		= new LegalRepresentative();
		legalRep2 		= new LegalRepresentative();
		clientContact1 	= new ClientContact();
		clientContact2 	= new ClientContact();
		clientContact3 	= new ClientContact();
		clientContact4 	= new ClientContact();
		clientContact5 	= new ClientContact();
		clientContact6 	= new ClientContact();
		clientContact7 	= new ClientContact();
		formatDate 		= new Formatter();
		toPrint 		= false;
		citiesBranchArray = new SelectItem[ApplicationConstants.EMPTY_LIST];
		// employee = new Employee();
	}

	public abstract void setResetForm();
	public abstract void setEditForm();

	public void setPyme() {
		this.pyme = true;
		this.isOIP = true;
	}

	public String getPyme() {
		setPyme();
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();
		try {
			response.sendRedirect("ordenAfiliacionOIP.jsf?resetForm=true");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}

	public void setIsOIP(String oip) {
		if (oip.equals("true"))
			this.isOIP = true;
	}

	public String getIsOIP() {

		if (this.isOIP)
			return "true";
		else
			return "";
	}

	public Map<String, String> getContractAttributeFills(Contract contract,
			String[] fills) {
		Map<String, String> map = new HashMap<String, String>();
		String GENERAL_FILLS[] = { "celebrationplace", "celebrationstate",
				"celebrationdate", "client_categorycode", "comments",
				"crnumber", "branchname", "branchstreet", "branchcolony",
				"branchphone", "branchfax", "bankingsector", "branchcity",
				"branchstate", "officername", "officerlastname",
				"officermothersln", "officerempnumber", "officerposition",
				AttrConstants.OFFICER_EBANKING_NAME, AttrConstants.OFFICER_EBANKING_LASTNAME,
				AttrConstants.OFFICER_EBANKING_MOTHERSNAME, AttrConstants.OFFICER_EBANKING_NUMBER,
				AttrConstants.OFFICER_EBANKING_POSITION,AttrConstants.OFFICER_EBANKING_FIRMNUMBER,
				"officerrepname_1", "officerreplastname_1",
				"officerrepmothersln_1", "officerrepempnumber_1",
				"officerrepposition_1","officerrepfirmnumber_1", "officerrepname_2",
				"officerreplastname_2", "officerrepmothersln_2",
				"officerrepempnumber_2", "officerrepposition_2", "officerrepfirmnumber_2",
				"client_merchantname", "client_phone", "client_phoneext",
				"client_fax", "client_faxext", AttrConstants.CLIENT_SIC, "client_street",
				"client_numext", "client_numint", "client_colony",
				"client_county", "client_zipcode", "client_city",
				"client_state", "client_email", "client_fiscalname",
				"client_fiscaltype", "client_rfc", "client_areacode",
				"client_emailname", "client_addressComplete",
				"client_faxComplete", "client_site",
				"legalrepresentative_position_1",
				"legalrepresentative_position_2",
				"legalrepresentative_mothersln_1", "legalrepresentative_rfc_1",
				"legalrepresentative_mothersln_2",
				"legalrepresentative_email_2", "legalrepresentative_name_1",
				"legalrepresentative_email_1", 
				"legalrepresentative_lastname_1",
				"legalrepresentative_lastname_2", "legalrepresentative_rfc_2",
				"legalrepresentative_name_2", "clientcontact_email1",
				"clientcontact_email2", "clientcontact_email3",
				"clientcontact_email4", "clientcontact_email5",
				"clientcontact_email6", "clientcontact_email7",
				"clientcontact_lastname1", "clientcontact_lastname2",
				"clientcontact_lastname3", "clientcontact_lastname4",
				"clientcontact_lastname5", "clientcontact_lastname6",
				"clientcontact_lastname7", "clientcontact_mothersln1",
				"clientcontact_mothersln2", "clientcontact_mothersln3",
				"clientcontact_mothersln4", "clientcontact_mothersln5",
				"clientcontact_mothersln6", "clientcontact_mothersln7",
				"clientcontact_name1", "clientcontact_name2",
				"clientcontact_name3", "clientcontact_name4",
				"clientcontact_name5", "clientcontact_name6",
				"clientcontact_name7", "clientcontact_phone1",
				"clientcontact_phone2", "clientcontact_phone3",
				"clientcontact_phone4", "clientcontact_phone5",
				"clientcontact_phone6", "clientcontact_phone7",
				"clientcontact_phoneext1", "clientcontact_phoneext2",
				"clientcontact_phoneext3", "clientcontact_phoneext4",
				"clientcontact_phoneext5", "clientcontact_phoneext6",
				"clientcontact_phoneext7", "clientcontact_position1",
				"clientcontact_position2", "clientcontact_position3",
				"clientcontact_position4", "clientcontact_position5",
				"clientcontact_position6", "clientcontact_position7",
				AttrConstants.OFFICER_EBANKING_ESP_NUMBER,AttrConstants.OFFICER_EBANKING_ESP_NAME,
				AttrConstants.OFFICER_EBANKING_ESP_LASTNAME,AttrConstants.OFFICER_EBANKING_ESP_MOTHERSNAME,
				AttrConstants.OFFICER_EBANKING_ESP_POSITION,
				AttrConstants.OFFICER_EBANKING_T_NUMBER,AttrConstants.OFFICER_EBANKING_T_NAME,
				AttrConstants.OFFICER_EBANKING_T_LASTNAME,AttrConstants.OFFICER_EBANKING_T_MOTHERSNAME,
				AttrConstants.OFFICER_EBANKING_T_POSITION,
				//"rfcRepresentanteLegal1",
				//"rfcRepresentanteLegal2"
				};


		String value = "";
		this.contract = contract;

		for (int i = 0; i < GENERAL_FILLS.length; i++) {
			Collection<ContractAttribute> atts = contract
					.getContractAttributeCollection();
			for (ContractAttribute att : atts) {
				value = "";
				if (att.getAttribute().getFieldname().equals(GENERAL_FILLS[i])) {
					value = att.getValue() != null ? att.getValue() : "";
					break;
				}
			}
			map.put(GENERAL_FILLS[i], value);
			// System.err.println("campo: "+ GENERAL_FILLS[i]+ " valor: " +
			// value);
		}

		for (int i = 0; i < fills.length; i++) {
			Collection<ContractAttribute> atts = contract
					.getContractAttributeCollection();
			for (ContractAttribute att : atts) {
				value = "";
				if (att.getAttribute().getFieldname().equals(fills[i])) {
					value = att.getValue() != null ? att.getValue() : "";
					break;
				}
			}
			map.put(fills[i], value);
			// System.err.println("campo: "+ fills[i]+ " valor: " + value);
		}

		return map;
	}

	public void clearFields() {
		celebrationplace 			= ApplicationConstants.EMPTY_STRING;
		celebrationstate 			= ApplicationConstants.EMPTY_STRING;
		celebrationdate 			= ApplicationConstants.EMPTY_STRING;
		applicationdate 			= ApplicationConstants.EMPTY_STRING;
		officername 				= ApplicationConstants.EMPTY_STRING;
		officerlastname 			= ApplicationConstants.EMPTY_STRING;
		officermothersln 			= ApplicationConstants.EMPTY_STRING;
		officerempnumber 			= ApplicationConstants.EMPTY_STRING;
		officerebankingname 		= ApplicationConstants.EMPTY_STRING;
		officerebankinglastname 	= ApplicationConstants.EMPTY_STRING;
		officerebankingmothersln 	= ApplicationConstants.EMPTY_STRING;
		officerebankingempnumber 	= ApplicationConstants.EMPTY_STRING;
		officerebankingposition		= ApplicationConstants.EMPTY_STRING;
		officerebankingnumfirm		= ApplicationConstants.EMPTY_STRING;
		officerposition 			= ApplicationConstants.EMPTY_STRING;
		crnumber 					= ApplicationConstants.EMPTY_STRING;
		branchname 					= ApplicationConstants.EMPTY_STRING;
		branchstreet 				= ApplicationConstants.EMPTY_STRING;
		branchcolony 				= ApplicationConstants.EMPTY_STRING;
		branchcounty 				= ApplicationConstants.EMPTY_STRING;
		branchcity 					= ApplicationConstants.EMPTY_STRING;
		branchstate 				= ApplicationConstants.EMPTY_STRING;
		branchphone 				= ApplicationConstants.EMPTY_STRING;
		branchfax 					= ApplicationConstants.EMPTY_STRING;
		bankingsector 				= ApplicationConstants.EMPTY_STRING;
		officerrepname_1 			= ApplicationConstants.EMPTY_STRING;
		officerreplastname_1 		= ApplicationConstants.EMPTY_STRING;
		officerrepmothersln_1 		= ApplicationConstants.EMPTY_STRING;
		officerrepempnumber_1 		= ApplicationConstants.EMPTY_STRING;
		officerrepposition_1 		= ApplicationConstants.EMPTY_STRING;
		officerrepfirmnumber_1 		= ApplicationConstants.EMPTY_STRING;
		officerrepname_2 			= ApplicationConstants.EMPTY_STRING;
		officerreplastname_2 		= ApplicationConstants.EMPTY_STRING;
		officerrepmothersln_2 		= ApplicationConstants.EMPTY_STRING;
		officerrepempnumber_2 		= ApplicationConstants.EMPTY_STRING;
		officerrepposition_2 		= ApplicationConstants.EMPTY_STRING;
		officerrepfirmnumber_2 		= ApplicationConstants.EMPTY_STRING;
		comments 					= ApplicationConstants.EMPTY_STRING;
		client 						= new Client();
		contract 					= new Contract();
		legalRep1 					= new LegalRepresentative();
		legalRep2 					= new LegalRepresentative();
		clientContact1 				= new ClientContact();
		clientContact2 				= new ClientContact();
		clientContact3 				= new ClientContact();
		clientContact4 				= new ClientContact();
		clientContact5 				= new ClientContact();
		clientContact6 				= new ClientContact();
		clientContact7 				= new ClientContact();
		officerebankingEspName 		= ApplicationConstants.EMPTY_STRING;
		officerebankingEspLastname 	= ApplicationConstants.EMPTY_STRING;
		officerebankingEspMothersln = ApplicationConstants.EMPTY_STRING;
		officerebankingEspEmpnumber = ApplicationConstants.EMPTY_STRING;
		officerebankingEspPosition	= ApplicationConstants.EMPTY_STRING;
		officerebankingTname 		= ApplicationConstants.EMPTY_STRING;
		officerebankingTlastname 	= ApplicationConstants.EMPTY_STRING;
		officerebankingTmothersln 	= ApplicationConstants.EMPTY_STRING;
		officerebankingTempnumber 	= ApplicationConstants.EMPTY_STRING;
		officerebankingTposition	= ApplicationConstants.EMPTY_STRING;
		red="";
		//rfcRepresentanteLegal1 		= ApplicationConstants.EMPTY_STRING;
		//rfcRepresentanteLegal2 		= ApplicationConstants.EMPTY_STRING;
		this.setFoundBranch(Boolean.FALSE);
		
		generalInfoErrorsList.clear();
		if (this.getErrorsList() != null) {
			this.getErrorsList().clear();
		}

	}

	public ContractAttribute getContractAttribute(String fieldName, String value) {
		Attribute att = attributeMB.getByFieldname(fieldName);
		ContractAttribute cAtt = new ContractAttribute();
		cAtt.setAttribute(att);
		cAtt.setValue(value);
		//System.out.println("ID: "+att.getAttributeid()+ " Descripcion: "+att.getDescription() +"  Campo : "+fieldName + " Valor: "+ value );
		return cAtt;
	}

	public String getPath() {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext();
		String realPath = ctx.getRealPath(".");
		if (realPath == null) {
			//realPath = "C:/temp";//Produccion
			//realPath = "C:/Users/miguel.borbolla.STKBANORTE/Desktop/EbankingSoft/eBankingVersionActual/contract";//local
			//realPath = "C:/Users/miguel.borbolla.STKBANORTE/Desktop/EbankingSoft/eBankingVersionActual/tmp_fuentes_y_docs/APPEBANKING_Celso/contract/web";
			//realPath = "C:/hbocm870/eBankingVersionParaPRD_mujerPyME/APPEBANKING/contract/web";
			//realPath = "C:/hbocm870/eBankingVersionParaPRD_reporteHeB/APPEBANKING/contract/web";
			realPath = "C:/Users/daarevalo/eclipse-workspace/APPEBANKING/contract/web";
		}
		return realPath;
	}

	protected void createPdfOrXLSResponse(String template,String fileNameResult, boolean pdforxls, boolean edit) { 
		FacesContext fCtx = FacesContext.getCurrentInstance();
		FileInputStream fis = null;
		try {
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			if (edit && pdforxls) {
				PdfBuilder builder 			= new PdfBuilder(byteArray);
				PdfTemplateReader reader 	= new PdfTemplateReader(template);
				
				builder.setReader(reader);
				builder.setBinding(getPdfTemplateBinding());
				
				builder.createDocument();
			} else {
				fis = new FileInputStream(template); 
				int theByte = 0;
				while ((theByte = fis.read()) != -1) {
					byteArray.write(theByte);
				}
				fis.close();
			}
			byte[] pdf = byteArray.toByteArray();
			byteArray.close();
			HttpServletResponse response = (HttpServletResponse) fCtx.getExternalContext().getResponse();
			if (pdforxls)
				response.setContentType("application/pdf");
			else
				response.setContentType("application/vnd.ms-excel");
			response.setContentLength(pdf.length);
			response.setHeader("Content-disposition", "attachment; filename=\"" + fileNameResult + "\""); 
			ServletOutputStream out = response.getOutputStream();
			out.write(pdf);
			
			String userNumber 		= BitacoraUtil.getInstance().getUserNumberSession(fCtx);
			String folio 			= this.getContract().getReference();
			String folioVersion 	= String.valueOf( this.getContract().getVersion() );
			String description 		= ApplicationConstants.BITACORA_DESCRIPTION_PDF + fileNameResult; 
			
			BitacoraUtil.getInstance().saveBitacoraFolio(description, userNumber, folio, folioVersion);
		} catch (IOException ioEx) {
			log.severe(ioEx.getMessage());
		} finally {
			safeClose(fis);
			fCtx.responseComplete();
		}

	}

	public String getTemplate() {
		if (this.isOIP)
			return "./contractTemplateOIP.xhtml";
		else
			return "./contractTemplate.xhtml";
	}

	protected Collection<Template> getTemplateOption(Integer productId) {
		Collection<Template> templates 	= templateBean.findAll();
		Collection<Template> result 	= new ArrayList();
		
		for (Template template : templates) {
			if (template.getProduct().getProductid().equals(productId))
				result.add(template);
		}

		return result;
	}

	public abstract PdfTemplateBinding getPdfTemplateBinding();

	public abstract Template[] getFormatList();

	public Product getProduct() {
		return contract.getProduct();
	}

	public void setProduct(Product product) {
		contract.setProduct(product);
	}

	public StatusContract getStatusContract() {
		return contract.getStatus();
	}

	public void setStatusContract(StatusContract status) {
		contract.setStatus(status);
	}

	public AttributeMB getAttributeMB() {
		return attributeMB;
	}

	public void setAttributeMB(AttributeMB attributeMB) {
		this.attributeMB = attributeMB;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	private void clearClientFields() {
		if (client != null) {
			client.setMessageservice("");
			client.setSite("");
			client.setFscity("");
			client.setFscounty("");
			client.setFsemail("");
			client.setFsfax(null);
			client.setFsfaxext(null);
			client.setFsname("");
			client.setFsnum("");
			client.setFsphone(null);
			client.setFsphoneext(null);
			client.setFsrfc("");
			client.setFsstate("");
			client.setFsstreet("");
			client.setFszipcode(null);
			client.setFscolony("");
		}

	}

	public boolean findClientBySic() {
		try {
			this.getProductIdDetail();
			String formSIC = getClient_sic().trim();
			Client clientBySic = clientBean.findBySic(new Long(formSIC));
			// para validar si el cliente es nuevo o actual y que aparezca la seleccion en la carta GL
			if(clientBySic != null){
				setIsSeleccionClienteNuevo(false);
			}else{
				setIsSeleccionClienteNuevo(true);
			}
			
			if (client != null && client.getFiscalname() != null && clientBySic != null) {
				client.setClientid(clientBySic.getClientid());
				
				//No borrar los campos para adquirente
				if(contract.getProduct().getProductid()<=2){
					clearClientFields();
				}
				
				return true;
			}
			this.setClient(clientBySic);
			clearClientFields();
			// client = clientBean.findBySic(new Long(getClient_sic().trim()));
			if (client != null) {
				ArrayList<LegalRepresentative> legalRepList = new ArrayList<LegalRepresentative>();
				legalRepList = (ArrayList<LegalRepresentative>) client.getLegalRepresentativeCollection();
				if (legalRepList != null && legalRepList.size() > 0) {
					if (legalRepList.get(0) != null) {
						legalRep1 = legalRepList.get(0);
						if (legalRepList.size() > 1 && legalRepList.get(1) != null) {
							legalRep2 = legalRepList.get(1);
						}
					}
				}
				ArrayList<ClientContact> clientContactList = new ArrayList<ClientContact>();
				clientContactList = (ArrayList<ClientContact>) client.getClientContactCollection();

				for (ClientContact clientContact : clientContactList) {
					if (contract.getProduct().getProductid().intValue() == clientContact.getClientContactPK().getProductid().intValue()) {
						if (clientContact.getClientContactPK().getContactid().intValue() == 1) {
							clientContact1 = clientContact;
						} else if (clientContact.getClientContactPK().getContactid().intValue() == 2) {
							clientContact2 = clientContact;
						} else if (clientContact.getClientContactPK().getContactid().intValue() == 3) {
							clientContact3 = clientContact;
						} else if (clientContact.getClientContactPK().getContactid().intValue() == 4) {
							clientContact4 = clientContact;
						} else if (clientContact.getClientContactPK().getContactid().intValue() == 5) {
							clientContact5 = clientContact;
						} else if (clientContact.getClientContactPK().getContactid().intValue() == 6) {
							clientContact6 = clientContact;
						} else if (clientContact.getClientContactPK().getContactid().intValue() == 7) {
							clientContact7 = clientContact;
						}
					}
				}
			} else {
				client = new Client();
				client.setSic(new Long(formSIC));
			}

		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}

		return true;
	}
	
	/**
	 * Se Comento ï¿½para que no validara y dejara pasar siempre, hasta que autorizaran la validacion de que exista el cliente
	 * en la bd de Altamira
	 * @return
	 */
	public boolean validationClientBySic() {
		try {
			this.getProductIdDetail();
			String formSIC = getClient_sic().trim();
			Client clientBySic = clientBean.findBySic(new Long(formSIC));
			
			if(clientBySic !=null){
				this.setClient(clientBySic);
				clearClientFields();
				return true;
			}else {
				return true;
				//return false;
			}
		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.getMessage(), ex);
			return true;
			//return false;
		}
	}

	private void assignLegalRepresentative(LegalRepresentative legal) {
		if (!legal.getName().trim().equals("")
				&& !legal.getLastname().trim().equals("")
				&& !legal.getMothersln().trim().equals("")) {
			client.add(legal);
		}
	}

	private void assignClientContact(ClientContact contact, Integer subtype) {
		if (!contact.getName().trim().equals("")
				&& !contact.getLastname().trim().equals("")
				&& !contact.getMothersln().trim().equals("")) {
			ContactType contactType = new ContactType();
			if (contract.getProduct().getProductid() != null) {
				if (contract.getProduct().getProductid() == 1) {
					contactType = contactTypeBean.findById(new Integer(
							ApplicationConstants.ADMINISTRADOR));
				} else if (contract.getProduct().getProductid() == 2) {
					contactType = contactTypeBean.findById(new Integer(
							ApplicationConstants.FIRMAFACULTADA));
				} else {
					if (subtype == 1) {
						contactType = contactTypeBean.findById(new Integer(
								ApplicationConstants.CONTACTOHERRAMIENTA));
					} else {
						contactType = contactTypeBean.findById(new Integer(
								ApplicationConstants.DESAROLLADOR));
					}
				}

				contact.setContacttype(contactType);
				client.add(contact);
			}
		}
	}

	private boolean saveGeneralInfo() {
		client.setLastcontractproduct(getProduct().getProductid());
		if (client.getClientid() == null) // && contract.getReference() == null
		// // SI EL CLIENTE VIENE VACIO
		// (CONTRATO NUEVO)
		{
			client.getClientContactCollection().clear();
			client.getLegalRepresentativeCollection().clear();

			clientBean.save(client);
			client = clientBean.findBySic(new Long(getClient_sic().trim()));
		} else if (client.getClientid() != null)// CONTRATO EDITADO
		{
			Client clientFind = clientBean.findBySic(new Long(getClient_sic()
					.trim()));
			if (clientFind == null) { // SI EL SIC CAPTURADO NO EXISTE EN LA
				// TABLA CLIENT CREA UN REGISTRO NUEVO
				client.setClientid(null);
				clientBean.save(client);
				client = clientBean.findBySic(new Long(getClient_sic().trim()));

			} else // SI EL SIC CAPTURADO SI EXISTE EN CLIENT ACTUALIZA
			{
				client.setClientid(clientFind.getClientid());
				clientBean.update(client);
			}
		}
		client.getLegalRepresentativeCollection().clear();
		assignLegalRepresentative(legalRep1);
		assignLegalRepresentative(legalRep2);
		client.getClientContactCollection().clear();
		assignClientContact(clientContact1, 1);
		assignClientContact(clientContact2, 0);
		assignClientContact(clientContact3, 0);
		assignClientContact(clientContact4, 0);
		assignClientContact(clientContact5, 0);
		assignClientContact(clientContact6, 0);
		assignClientContact(clientContact7, 0);
		try {
			clientBean.update(client);
		} catch (Exception e) {
			System.out.println("ocurrio un error al guardar");
		}
		return true;
	}
	
	
	
	

	/**
	 * Metodo que actualiza la informaciï¿½n de los campos relacionados al
	 * empleado
	 */
	private void fillEmployeeFields(Employee employee, String officerType) {
		
		if(employee==null){
			ArrayList<ContractMessageErrors> errorsList 	= new ArrayList();
			ContractMessageErrors errors 					= new ContractMessageErrors();

			errors.setMessage("No se encontro Informacion para el Numero de Empleado Proporcionado");
			errorsList.add(errors);
			this.setErrorsList(errorsList);
		}else{
			if(this.getErrorsList()!=null){
				this.getErrorsList().clear();
			}
		}

		if (officerType.equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER) || 
				officerType.equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_MTTO)) {

			if (employee != null) {
				this.setOfficerempnumber(employee.getEmployeeId());
				this.setOfficername(employee.getName());
				this.setOfficerlastname(employee.getFirstName());
				this.setOfficermothersln(employee.getSecondName());
				this.setOfficerposition(employee.getPosition());
			} else {
				this.setOfficerempnumber("");
				this.setOfficername("");
				this.setOfficerlastname("");
				this.setOfficermothersln("");
				this.setOfficerposition("");
			}

		} else if (officerType.equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_BANKING) ||
				officerType.equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_BANKING_MTTO)) {

			if (employee != null) {

				this.setOfficerebankingempnumber(employee.getEmployeeId());
				this.setOfficerebankingname(employee.getName());
				this.setOfficerebankinglastname(employee.getFirstName());
				this.setOfficerebankingmothersln(employee.getSecondName());
				this.setOfficerebankingposition(employee.getPosition());
			} else {
				this.setOfficerebankingempnumber(ApplicationConstants.EMPTY_STRING);
				this.setOfficerebankingname(ApplicationConstants.EMPTY_STRING);
				this.setOfficerebankinglastname(ApplicationConstants.EMPTY_STRING);
				this.setOfficerebankingmothersln(ApplicationConstants.EMPTY_STRING);
				this.setOfficerebankingposition(ApplicationConstants.EMPTY_STRING);
			}

		} else if (officerType.equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_REP_EMP_1) ||
				officerType.equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_REP_EMP_1_MTTO)) {

			employee = this.findEmployeeById(this.getOfficerrepempnumber_1());

			if (employee != null) {
				this.setOfficerrepempnumber_1(employee.getEmployeeId());
				this.setOfficerrepname_1(employee.getName());
				this.setOfficerreplastname_1(employee.getFirstName());
				this.setOfficerrepmothersln_1(employee.getSecondName());
				this.setOfficerrepposition_1(employee.getPosition());
			} else {
				this.setOfficerrepempnumber_1("");
				this.setOfficerrepname_1("");
				this.setOfficerreplastname_1("");
				this.setOfficerrepmothersln_1("");
				this.setOfficerrepposition_1("");
			}

		} else if (officerType.equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_REP_EMP_2) ||
				officerType.equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_REP_EMP_2_MTTO)) {

			employee = this.findEmployeeById(this.getOfficerrepempnumber_2());

			if (employee != null) {

				this.setOfficerrepempnumber_2(employee.getEmployeeId());
				this.setOfficerrepname_2(employee.getName());
				this.setOfficerreplastname_2(employee.getFirstName());
				this.setOfficerrepmothersln_2(employee.getSecondName());
				this.setOfficerrepposition_2(employee.getPosition());
			} else {
				this.setOfficerrepempnumber_2("");
				this.setOfficerrepname_2("");
				this.setOfficerreplastname_2("");
				this.setOfficerrepmothersln_2("");
				this.setOfficerrepposition_2("");
			}
		}else if (officerType.equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_BANKING_ESP)) {

			if (employee != null) {

				this.setOfficerebankingEspEmpnumber(employee.getEmployeeId());
				this.setOfficerebankingEspName(employee.getName());
				this.setOfficerebankingEspLastname(employee.getFirstName());
				this.setOfficerebankingEspMothersln(employee.getSecondName());
				this.setOfficerebankingEspPosition(employee.getPosition());
			} else {
				this.setOfficerebankingEspEmpnumber(ApplicationConstants.EMPTY_STRING);
				this.setOfficerebankingEspName(ApplicationConstants.EMPTY_STRING);
				this.setOfficerebankingEspLastname(ApplicationConstants.EMPTY_STRING);
				this.setOfficerebankingEspMothersln(ApplicationConstants.EMPTY_STRING);
				this.setOfficerebankingEspPosition(ApplicationConstants.EMPTY_STRING);
				
			}

		} else if (officerType.equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_BANKING_T)) {

			if (employee != null) {

				this.setOfficerebankingTempnumber(employee.getEmployeeId());
				this.setOfficerebankingTname(employee.getName());
				this.setOfficerebankingTlastname(employee.getFirstName());
				this.setOfficerebankingTmothersln(employee.getSecondName());
				this.setOfficerebankingTposition(employee.getPosition());
			} else {
				this.setOfficerebankingTempnumber(ApplicationConstants.EMPTY_STRING);
				this.setOfficerebankingTname(ApplicationConstants.EMPTY_STRING);
				this.setOfficerebankingTlastname(ApplicationConstants.EMPTY_STRING);
				this.setOfficerebankingTmothersln(ApplicationConstants.EMPTY_STRING);
				this.setOfficerebankingTposition(ApplicationConstants.EMPTY_STRING);
			}

		}else if (officerType.equals(ApplicationConstants.ID_ACTEVENT_SEARCH_BRANCH_DIRECTOR)) {
			 
			if (employee != null) {
                this.setBranchDirectorNumber(employee.getEmployeeId());
                this.setBranchDirectorName(employee.getName());
                this.setBranchDirectorLastName(employee.getFirstName());
                this.setBranchDirectorMothersLn(employee.getSecondName());
                this.setBranchDirectorposition(employee.getPosition());
			} else {
                this.setBranchDirectorNumber(ApplicationConstants.EMPTY_STRING);
                this.setBranchDirectorName(ApplicationConstants.EMPTY_STRING);
                this.setBranchDirectorLastName(ApplicationConstants.EMPTY_STRING);
                this.setBranchDirectorMothersLn(ApplicationConstants.EMPTY_STRING);
                this.setBranchDirectorposition(ApplicationConstants.EMPTY_STRING);
            }

		}

	}
	
	
	private void fillBranchFields(Branch branch) {

			if (branch != null) {
				this.setBranchname(branch.getName());
				this.setBranchstreet(branch.getAddress());
				this.setBranchcolony(branch.getSuburb());
				this.setBranchstate(branch.getState());
				this.setBranchcity(branch.getPlaza());
				this.setBranchphone(branch.getPhoneFormat());
				this.setFoundBranch(Boolean.TRUE);
			} else {
				this.setBranchname("");
				this.setBranchstreet("");
				this.setBranchcolony("");
				this.setBranchstate("");
				this.setBranchcity("");
				this.setBranchphone("");
				this.setFoundBranch(Boolean.FALSE);
			}
	}


	/**
	 * Metodo que obtiene la informaciï¿½n del empleado en base a su Nï¿½mero de
	 * Empledo(Id)
	 * 
	 * @return Employee
	 */
	private Employee findEmployeeById(String id) {
		Employee employee = new Employee();
		try {
			employee = employeeBean.findByNumEmpleado(id);
		} catch (Exception ex) {log.log(Level.SEVERE, "Error en ContractAbstractMB.findEmployeeById: " + ex.getMessage(), ex);
			return null;
		}
		return employee;
	}
	
	private Branch findBranchByCR(String cr) {

		Branch branch = new Branch();
		int param = Integer.parseInt(cr);
		try {

			branch = branchBean.findByCR(param);

		} catch (Exception ex) {
			log.log(Level.SEVERE,"Error en ContractAbstractMB.findBranchByCR: "+ ex.getMessage(), ex);
			return null;
		}

		return branch;
	}


	/**
	 * Metodo que identifica y busca la informaciï¿½n del empleado en base al
	 * control que lanzï¿½ el evento
	 * @throws ServletException 
	 * @throws IOException 
	 */
	public void findCitiesByStateId(ActionEvent actionEvent) throws ServletException, IOException {
		
		//String anchor = actionEvent.getComponent().getId();
		
		// Para recuperar el parï¿½metro enviado por el JSP
		//String componentId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("anchorSearchCities");		
		String componentId="anchorSearchCities";
		this.redirectGeneralInfo(componentId); // this.redirectGeneralInfo("anchor"+componentId);
		
	}	
//	public void findCitiesByStateId(ValueChangeEvent event) {
//		// Para recuperar el parï¿½metro enviado por el JSP
//		String componentId = FacesContext.getCurrentInstance().getExternalContext()
//				.getRequestParameterMap().get(
//						"anchorSearchCities");		
//		
//		this.redirectGeneralInfo("anchor"+componentId);
//	}
	
	public void updateProperties(ValueChangeEvent event) throws ServletException, IOException {
	
		String componentId = event.getComponent().getId();
		if(componentId.equals("celebrationstate")){
			this.setCelebrationplace(event.getNewValue().toString());
			//this.redirectGeneralInfo("anchor"+componentId);
		}else if(componentId.equals("client_state")){
			this.setClient_city(event.getNewValue().toString());
		}else if(componentId.equals("branchstate")){
			this.setBranchcity(event.getNewValue().toString());
		}
		
		
		FacesContext context = FacesContext.getCurrentInstance();
				
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
	
		String anchor = "anchor"+componentId;
		
		//Redirect
		this.redirectGeneralInfo(anchor);
//		try {
//			
//			response.sendRedirect("generalInfoNomina.jsf#anchor"+componentId);
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
	
	/**
	 * Metodo que identifica y busca la informaciï¿½n del empleado en base al
	 * control que lanzï¿½ el evento
	 */
	public void findOfficerInfo(ActionEvent actionEvent)  throws ServletException, IOException {

		HtmlCommandLink link 	= (HtmlCommandLink) actionEvent.getSource();
		Employee employee 		= null;
		Branch branch 			= null;
		boolean redirect 		= Boolean.TRUE;
		boolean samePage = false;

		if (link.getId().equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER)) {
			employee = this.findEmployeeById(this.getOfficerempnumber());
		} else if (link.getId().equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_BANKING)) {
			employee = this.findEmployeeById(this.getOfficerebankingempnumber());
		} else if (link.getId().equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_REP_EMP_1)) {
			employee = this.findEmployeeById(this.getOfficerrepempnumber_1());
		} else if (link.getId().equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_REP_EMP_2)) {
			employee = this.findEmployeeById(this.getOfficerrepempnumber_2());
		}else if (link.getId().equals(ApplicationConstants.ID_ACTEVENT_SEARCH_BRANCH)) {
			branch = this.findBranchByCR(this.getCrnumber());
		}else if (link.getId().equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_BANKING_ESP)) {
			employee = this.findEmployeeById(this.getOfficerebankingEspEmpnumber());
			redirect = Boolean.FALSE;
			samePage=true;
		}else if (link.getId().equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_BANKING_T)) {
			employee = this.findEmployeeById(this.getOfficerebankingTempnumber());
			redirect = Boolean.FALSE;
			samePage=true;
		}else if (link.getId().equals(ApplicationConstants.ID_ACTEVENT_SEARCH_BRANCH_DIRECTOR)) {//SearchOfficerMtto
			employee = this.findEmployeeById(this.getBranchDirectorNumber());
            redirect = Boolean.FALSE;
            samePage=true;
		} else if (link.getId().equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_MTTO)) {//SearchOfficerMtto
			employee = this.findEmployeeById(this.getOfficerempnumber());
			redirect = Boolean.FALSE;
			samePage=true;//para el anchor de mantenimientos
		} else if (link.getId().equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_BANKING_MTTO)) {//SearchOfficerBankingMtto
			employee = this.findEmployeeById(this.getOfficerebankingempnumber());
			redirect = Boolean.FALSE;
			samePage=true;//para el anchor de mantenimientos
		} else if (link.getId().equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_REP_EMP_1_MTTO)) {//SearchOfficerRepEmp1Mtto
			employee = this.findEmployeeById(this.getOfficerrepempnumber_1());
			redirect = Boolean.FALSE;
			samePage=true;//para el anchor de mantenimientos
		} else if (link.getId().equals(ApplicationConstants.ID_ACTEVENT_SEARCH_OFFICER_REP_EMP_2_MTTO)) {//SearchOfficerRepEmp2Mtto
			employee = this.findEmployeeById(this.getOfficerrepempnumber_2());
			redirect = Boolean.FALSE;
			samePage=true;//para el anchor de mantenimientos
		}
		
		if(link.getId().equals(ApplicationConstants.ID_ACTEVENT_SEARCH_BRANCH)){
			//llena la info de la sucursal
			this.fillBranchFields(branch);
		}else{
			// Llenar la informacion del empleado
			this.fillEmployeeFields(employee, link.getId());
		}
		
		// Para recuperar el parï¿½metro enviado por el JSP
		String anchor="officeInfo"; 
		if(redirect){//Redirect a general info de cada producto
			this.redirectGeneralInfo(anchor);
		}else if(samePage==true){//redirect a la misma pagina de mantenimientos
			this.redirectMtto(anchor);
		}
	}

	protected void redirectGeneralInfo(String anchor)  throws ServletException, IOException{		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.setContentType("text/html");
		
		try{
			String url ="";
			if (this.getProduct().getProductid() == ProductType.BEM.value())
				url = "generalInfoBem.jsf#";
			else if (this.getProduct().getProductid() == ProductType.NOMINA.value())
				url = "generalInfoNomina.jsf#"; 
			else if (this.getProduct().getProductid() == ProductType.INTERNET.value() || 
					this.getProduct().getProductid() == ProductType.TRADICIONAL.value() ||
					this.getProduct().getProductid() == ProductType.COMERCIOELECTRONICO.value())
				url = "generalInfoAdquirente.jsf#"; 
			else if ( this.getProduct().getProductid() == ProductType.COBRANZA_DOMICILIADA.value() ){
				url = "generalInfoCD.jsf#";
			}
			else
				url = "generalInfoAdquirenteOIP.jsf#";
			
			//if(!"".equals(anchor))
			if(null != anchor && (anchor.length()>0)) //se agrego
				url = url+anchor;
				response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();}
	}
	
	/**
	 * Metodo para sujetar la pagina en la seccion donde se mando llamar a buscar los datos de empleado
	 * @param anchor
	 */
	protected void redirectMtto(String anchor){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		
		try{
			String url ="";
			if (this.getProduct().getProductid() == ProductType.MTTO_ALTA_CUENTAS.value())
				url = "ordenMttoBEMAccounts.jsf#";
			else if (this.getProduct().getProductid() == ProductType.MTTO_NUEVA_CONTRASENA.value())
				url = "ordenMttoBEMNewPassword.jsf#";
			else if (this.getProduct().getProductid() == ProductType.MTTO_ADD_TOKEN.value())
				url = "ordenMttoBEMAddToken.jsf#";
			else if ( this.getProduct().getProductid() == ProductType.MTTO_REPOSICION_TOKEN.value()){
				url = "ordenMttoBEMRenovToken.jsf#";
			}else if(this.getProduct().getProductid()==ProductType.MTTO_CONVENIO_MODIFICATORIO.value()){
				url="ordenMttoBEMUpdateAgree.jsf#";
			}else if(this.getProduct().getProductid()==ProductType.MTTO_DROP_COMPANY.value()){
				url="ordenMttoBEMDropCompany.jsf#";
			}else if(this.getProduct().getProductid()==ProductType.MTTO_DROP_TOKENS.value()){
				url="ordenMttoBEMDropToken.jsf#";
			}else if(this.getProduct().getProductid()==ProductType.MTTO_DROP_ACCOUNT.value()){
				url="ordenMttoBEMDropAccount.jsf#";
			}else if(this.getProduct().getProductid()==ProductType.COBRANZA_DOMICILIADA.value()){
				url="ordenCD.jsf#";
			}
			url = url+anchor;
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();}
	}
	
	public abstract String getProductPrefix();

	public abstract void getProductIdDetail();

	private String getContractReference() {
		String ref = "";
		toEdit = false;
		if (contract.getReference() != null) {
			ref = contract.getReference();
			toEdit = true;
		} else {
			Integer referenceid = referenceBean.getReference().getReferenceid();
			String refStr = "000" + referenceid;
			SimpleDateFormat formater = new SimpleDateFormat("ddMMyy");
			String refDate = formater.format(new java.util.Date(System.currentTimeMillis()));
			ref = getProductPrefix() + refDate + refStr.substring(refStr.length() - 4, refStr.length());
		}
		return ref;

	}

	@SuppressWarnings("unchecked")
	private boolean saveContract() {
		// UNA VEZ GUARDADO EL CLIENTE SE GUARDA EL CONTRACT, LLENAR EL OBJ.
		// CONTRACT, ASIGNARLE EL OBJ. CLIENT QUE YA PERSISTIMOS, EL OBJ.
		// PRODUCTO Y EL OBJ. STATUS CONTRACT, PARA ASIGNAR EL CAMPO REFERENCE,
		// HACER UNA UTILERIA PARA LA GENERACION DE FOLIOS DE CONTRATO
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) (facesContext.getExternalContext().getSession(true));
		Map<String, String> userSession = (HashMap) session.getAttribute(ApplicationConstants.PROFILE);

		String contractReference = getContractReference();

		Contract existingContract = contractBean.findByRefLastVersion(contractReference);

		if (existingContract != null) {

			contract.setAuthoffempnum1("");
			contract.setAuthoffempnum2("");

			if (existingContract.getStatus().getStatusid() > 6) { 
				// Cuando es una version Nueva guardar un nuevo registro
				// //
				// correccion de folios desaparecidos....
				//debe tomar el estatus del ultimo contrato que existe en la bd y no del que viene en session..
				
				contract.setClient(client);
				contract.setVersion(existingContract.getVersion() + 1); // correccion
				// de folios desaparecidos....debe tomar la siguiente version del ultimo que existe en la bd 
				// y no del que viene en session..
				contract.setContractId(null);
				contract.setStatus(statusBean.findById(new Integer(2)));
				contract.getContractAttributeCollection().clear();
				contract.getAffiliationCollection().clear();
				contract.getContractStatusHistoryCollection().clear();
				contractBean.save(contract);
			} else {
				contract.setClient(client);
				contract.setStatus(statusBean.findById(new Integer(2)));
				contractBean.update(contract);
			}
		} else {
			contract.setVersion(1);
			contract.setClient(client);
			contract.setReference(contractReference);
			contract.setCreationdate(new java.util.Date(System.currentTimeMillis()));
			contract.setCreationoffempnum(userSession.get(ApplicationConstants.UID));
			contract.setCreationoffname(userSession.get(ApplicationConstants.UNAME));
			contractBean.save(contract);
		}

		contract = contractBean.findByRefLastVersion(contractReference);
		return true;
	}

	@SuppressWarnings("static-access")
	public String saveCompleteContract() {
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		this.getContract().setContractTypeId( ApplicationConstants.ID_CONTRACT_TYPE_ALTA);
		// SE REALIZAN LAS INVOCACIONES PARA EL GUARDADO EN CASCADA, PRIMERO
		// DATOS GENERALES, LUEGO EL CONTRATO Y AL FINAL LOS ATRIBUTOS O DATOS
		// DE AFILIACION PARA EL CASO DE ADQUIRENTE
		if (validateGeneralInfo()) {// Validaciones GeneralInfo
			if (saveGeneralInfo()) {
				if (saveContract()) {
					removeContractAttributes(this.getContract()); //eliminamos la lista de atributos del contrato
					if (savePartialContract()) {
						String userNumber 		= BitacoraUtil.getInstance().getUserNumberSession(ctx);
						String folio 			= this.getContract().getReference();
						String folioVersion 	= String.valueOf( this.getContract().getVersion() );
						
						setStatusHistory();
						toPrint = true;
						toAuthorize = true;
						if(folioVersion!=null && Integer.parseInt(folioVersion)>1){ //si el folio es mayor a 1 es una edicion.
							BitacoraUtil.getInstance().saveBitacoraFolio(BitacoraType.EDITION_CONTRATO, userNumber, folio, folioVersion);
						}else{
							BitacoraUtil.getInstance().saveBitacoraFolio(BitacoraType.CAPTURA_CONTRATO, userNumber, folio, folioVersion);
						}
						return SUCCESS;
					} else {
						return FAILED;
					}

				}
			}
		}
		return FAILED;
	}
	
	public void removeContractAttributes(Contract contract){
		Collection<ContractAttribute> listaAtributos=null;
		if(contract!=null){
			listaAtributos=contract.getContractAttributeCollection();
			if(listaAtributos!=null && listaAtributos.size()>0)
			for(ContractAttribute atributo:listaAtributos){
				contractAttributeBean.delete(atributo);
			}	
		}
	}
	
	/**
	 * Metodo para guardar un contrato cuando es Mtto
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String saveCompleteMtto() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		Date day = new Date();
		SimpleDateFormat formatter= new SimpleDateFormat ("dd-MMM-yy");
    
		this.setCelebrationdate( formatter.format( day ) );
		this.getContract().setContractTypeId( ApplicationConstants.ID_CONTRACT_TYPE_MTTO);
		
		
		if( !validationClientBySic() ){
			ContractMessageErrors errors = new ContractMessageErrors();
			errors.setMessage("Numero de Cliente en altamira (SIC) No Valido.");
			generalInfoErrorsList.add(errors);

			this.setErrorsList(generalInfoErrorsList);
			return FAILED;
		}
		
		
		// SE REALIZAN LAS INVOCACIONES PARA EL GUARDADO EN CASCADA, PRIMERO
		// DATOS GENERALES, LUEGO EL CONTRATO Y AL FINAL LOS ATRIBUTOS O DATOS
		// DE AFILIACION PARA EL CASO DE ADQUIRENTE
		if (validateGeneralInfoMtto()) {// Validaciones GeneralInfo
			if (saveGeneralInfo()) {
				if (saveContract()) {
					removeContractAttributes(this.getContract()); //para eliminar la lista de atributos de mantenimientos antes de guardar nuevos
					if (savePartialContract()) {
						
						String userNumber 		= BitacoraUtil.getInstance().getUserNumberSession(ctx);
						String folio 			= this.getContract().getReference();
						String folioVersion 	= String.valueOf( this.getContract().getVersion() );
						
						setStatusHistory();
						toPrint = true;
						toAuthorize = true;
						if(folioVersion!=null && Integer.parseInt(folioVersion)>1){ //si el folio es mayor a 1 es una edicion.
							BitacoraUtil.getInstance().saveBitacoraFolio(BitacoraType.EDITION_CONTRATO, userNumber, folio, folioVersion);
						}else{
							BitacoraUtil.getInstance().saveBitacoraFolio(BitacoraType.CAPTURA_CONTRATO, userNumber, folio, folioVersion);
						}
						return SUCCESS;
					} else {
						return FAILED;
					}
				}
				}
			
		}
		return FAILED;
	}

	private void setStatusHistory() { 
		StatusContract status = null;
		if (!toEdit)
			status = statusBean.findById(1);
		else
			status = statusBean.findById(contract.getStatus().getStatusid());

		contract.setStatus(status);

		ContractStatusHistory statusHistory = new ContractStatusHistory();
		statusHistory.setContract(contract);
		statusHistory.setStatusContract(status);
		statusHistory.setModifydate(new Date());
		statusHistory.setOffempnum("appsstb");
		statusHistory.setCommentary(toEdit ? "APPSSTB contrato editado en espera de formalizacion." : "APPSSTB contrato capturado en espera de formalizacion.");

		contract.add(statusHistory);

		contractBean.update(contract);
	}

	public String continueToGeneralInfo() {
		
		if (contract.getReference() == null) {
			if (getClient_sic() != null && !getClient_sic().trim().equals("")&& !findClientBySic()) {
				// "] !!! ", "No se encontro el cliente con SIC [" +
			}
		} else { // SI EL CONTRATO YA EXISTE (EDICION)
			// BUSCAR EL CLIENTE POR SIC Y SI EXISTE COPIARLE LA INFO QUE SE TRAE EN SESSION
			Client clientExist = client;
			if (getClient_sic() != null && !getClient_sic().trim().equals("")&& !findClientBySic()) {
				// " No se encontro el cliente con SIC [" + getClient_sic() +
			} else {
				Integer clientId = client.getClientid();
				client = clientExist;
				client.setClientid(clientId);
			}
		}
		this.setFoundBranch(Boolean.FALSE);
		//this.celebrationstate = ApplicationConstants.EMPTY_STRING;
		return SUCCESS;
	}

	public String toAuthorizeEmp1() {
		FacesContext facesContext 		= FacesContext.getCurrentInstance();
		HttpSession session 			= (HttpSession) (facesContext.getExternalContext().getSession(true));
		Map<String, String> userSession = (HashMap) session.getAttribute(ApplicationConstants.PROFILE);
		Contract contract_ = contractBean.findById(this.getContract().getContractId());
		String userNumber 				= userSession.get(ApplicationConstants.UID);
		String folio 					= contract_.getReference();
		String folioVersion 			= String.valueOf( contract_.getVersion() );

		String emp1 = contract_.getAuthoffempnum1() != null ? contract_.getAuthoffempnum1() : "";
		String emp2 = contract_.getAuthoffempnum2() != null ? contract_.getAuthoffempnum2() : "";
		if (emp1.equals("")) {
			contract_.setAuthoffempnum1(userSession.get(ApplicationConstants.UID));
			emp1 = userSession.get(ApplicationConstants.UID);
		}

		StatusContract status 					= null; 
		ContractStatusHistory statusHistory 	= new ContractStatusHistory();

		if (!emp1.equals("") && emp2.equals("")) {
			status = statusBean.findById(STATUSID_FORMALIZADO);//formalizado
			statusHistory.setCommentary("APPSSTB contrato formalizado, pendiente de CoFormalizacion.");
		}
		contract_.setStatus(status);
		statusHistory.setContract(contract_);
		statusHistory.setStatusContract(status);
		statusHistory.setOffempnum("appsstb");
		statusHistory.setModifydate(new Date());

		contract_.add(statusHistory);

		contractBean.update(contract_);
		this.setToAuthorize(false);
		this.setToPrint(true);

		BitacoraUtil.getInstance().saveBitacoraFolio(BitacoraType.FORMALIZAR_FOLIO, userNumber, folio, folioVersion);
		return SUCCESS;
	}
	public void ejecutarProceso(){
		if(procesoEnvio && !procesoLectura){
			generateLayout();
		}else if (procesoEnvio && procesoLectura){
			createLayout();
		}else if(!procesoEnvio && procesoLectura){
			readLayout();
		}else if(!procesoEnvio && !procesoLectura && procesoCargaCuentasMujerPyME){
			readCuentasMujerPyME();
		}
	}
	public void createLayout() { //Ejecutar Envio y Lectura Layouts
		
		LayoutOperations layOut 	= LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        System.out.println("Ejecucion Manual Envio y Lectura Layouts...");
        
        bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.GENERATE_READ_LAYOUTS, userNumber);
		layOut.setProducto((productId==99?null:productId)); //en base al producto elegido, si es todos los productos manda nulo
        layOut.startSend(false, true,false);
		layOut.startRecipe(false,false);
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
	}

	public void readLayout() { //Ejecutar Lectura Layouts
		LayoutOperations layOut 	= LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
		System.out.println("Ejecucion Manual Lectura Layouts...");
		
		bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.READ_LAYOUTS, userNumber);
		layOut.startRecipe(false,false);
		System.out.println("Cargando las Instalaciones de TPV..." + (new java.sql.Timestamp(System.currentTimeMillis())));
		layOut.startRecipeInstall(false); //FIXME gina descomentar para prod  
		System.out.println("Fin de Layouts Manual de Instalacion..."+ (new java.sql.Timestamp(System.currentTimeMillis())));
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
	}
	
	public void generateLayout(){ //Ejecutar Envio de Layouts
		LayoutOperations layOut 	= LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        
		System.out.println("Ejecucion Manual Generacion de Layouts...");
		bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.GENERATE_LAYOUTS, userNumber);
		layOut.setProducto((productId==99?null:productId));
		layOut.startSend(false, true,false);
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
	}
	
	
	public void readCuentasMujerPyME(){ //Ejecutar Lectura de lista de altamira de cuentas con MujerPyme
		LayoutOperations layOut 	= LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        
		System.out.println("Ejecucion Manual Lectura de cuentas Mujer PyME..."+ (new java.sql.Timestamp(System.currentTimeMillis())));
		bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.READ_CUENTAS_MUJERPYME, userNumber);
		layOut.startLecturaCuentasMujerPyME(false, false);
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
		System.out.println("Fin de Lectura Manual de cuentas Mujer PyME..."+ (new java.sql.Timestamp(System.currentTimeMillis())));
	}

	
	public void completeMaintanceProcess() {
		
		LayoutOperations layout 	= LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        
        bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.RELIEF_RISK, userNumber);
		System.out.println("Ejecucion Proceso Completo de Mitigación Riesgos BEM...");
		/*Pruebas:
		 *Quitarcomentartios para pruebas de carga de mantenimientos por un numero de dias
		 */
		Calendar startProcessDay = new Formatter().formatDate(this.startMaintanceProcessDate);
		
		Integer processDays = Integer.parseInt(this.getProcessDays());
		
		for (Integer i=0; i < processDays; i++){
			
			//Procesar las altas y mantenimientos del dï¿½a
			layout.startMaintanceProcess(startProcessDay.getTime());

			startProcessDay.roll(Calendar.DAY_OF_MONTH, true);
		}

		//Enviar a directa y a tralix la informacion de las altas y mantenimientos 
		layout.startMaintanceSend();

		
//		Calendar proccesDate =new Formatter().formatDate(Calendar.getInstance().getTime());
//		//Procesar las altas y mantenimientos del dï¿½a
//		layout.startMaintanceProcess(proccesDate.getTime());
//		//Enviar a directa y a tralix la informacion de las altas y mantenimientos 
//		layout.startMaintanceSend( proccesDate.getTime());
		
		clearFieldsMaintanceProcess();
		System.out.println("Fin Proceso Completo de Mitigación Riesgos BEM...");
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
	}
	
	
	
	public void startMaintanceProcess() {
		
		LayoutOperations layout 	= LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        
        
		System.out.println("Ejecutar Recopilacion de Mantenimientos y Activaciones...");
		bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.COMPILATION_MANTENIMIENTOS_ACT, userNumber);
		Calendar proccesDate =new Formatter().formatDate(this.getStartMaintanceProcessDate());

		Integer processDays = Integer.parseInt(this.getProcessDays());
		
		for (Integer i=0; i < processDays; i++){
			//Procesar las altas y mantenimientos del dï¿½a
			layout.startMaintanceProcess(proccesDate.getTime());		
			proccesDate.roll(Calendar.DAY_OF_MONTH, true);
		}

		clearFieldsMaintanceProcess();
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
		System.out.println("Fin Ejecutar Recopilacion de Mantenimientos y Activaciones...");
	}
	
	public void startMaintanceSend() {
		
		LayoutOperations layout 	= LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        
		System.out.println("Ejecutar Envio de Mantenimientos a Tralix y Directa...");
		bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.SEND_MANTENIMIENTOS_TRALIX_DIRECTA, userNumber);
		//Enviar a directa y a tralix la informacion de las altas y mantenimientos 
		layout.startMaintanceSend();
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
		System.out.println("Fin Ejecutar Envio de Mantenimientos a Tralix y Directa...");
	}
	
	private  void clearFieldsMaintanceProcess(){
		
		this.startMaintanceProcessDate ="";
		this.processDays ="";
		
	}
	
	public void readLayoutDirecta() {
		
		LayoutOperations layout 	= LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        
		System.out.println("Ejecutar Proceso de Obtención de Resultados de Directa...");
		bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.RESULTS_DIRECTA, userNumber);
		
		//Quitar comentarios para pruebas
		Calendar startOperationDay = new Formatter().formatDate(this.getStartMaintanceProcessDate());
		Calendar endOperationDay = new Formatter().formatDate(this.getStartMaintanceProcessDate());
		endOperationDay.add(Calendar.DAY_OF_MONTH, Integer.parseInt(this.getProcessDays()));

		//Iniciar el envio de informacion sobre mantenimientos a Directa
		layout.startRecipeDirecta( new Formatter().formatDate(startOperationDay.getTime()).getTime(), 
				 new Formatter().formatDate(endOperationDay.getTime()).getTime());

		clearFieldsMaintanceProcess();
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
		System.out.println("Fin Ejecutar Proceso de Obtención de Resultados de Directa..."+ bitacora.getEndDate());
	}

	public void getAllProductsDetailReport() {
		
		LayoutOperations layout = LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        
		System.out.println("Inicio de la ejecucion Reporte General...");
		bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.REPORT_ALL_PRODUCTS, userNumber);
		
		Calendar startReportDate 	= new Formatter().formatDate(this.startReportDate);
		Calendar endReportDate 		= new Formatter().formatDate(this.endReportDate);
		
		layout.getAllProductsDetailReport(startReportDate, endReportDate);
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
		System.out.println("Fin de la ejecucion Reporte General...");
	}
	
	/** 
	 * @author gina
	 * Metodo para crear el reporte de todos los productos por un rango de fecha (DTO)
	 */
	public void getAllProductsDetailReportDTO() {
		
		LayoutOperations layout = LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        
		System.out.println("Inicio de la ejecucion Reporte General..." + new Date());
		bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.REPORT_ALL_PRODUCTS, userNumber);
		
		Calendar startReportDate 	= new Formatter().formatDate(this.startReportDate);
		Calendar endReportDate 		= new Formatter().formatDate(this.endReportDate);
		
		endReportDate.add(Calendar.DAY_OF_YEAR, 1); //Para agregar un dï¿½a a la fecha final del reporte
		
		layout.getAllProductsDetailReportDTO(startReportDate, endReportDate, dateReportFilter, statusReportFilter);
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
		System.out.println("Fin de la ejecucion Reporte General..." + new Date());
	}

	public void getAdquirerDetailReport() {
		
		LayoutOperations layout 	= LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        
		System.out.println("Inicio de la ejecucion Reporte Adquirente...");
		bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.REPORT_ADQUIRENTE, userNumber);
		
		Calendar startReportDate 	= new Formatter().formatDate(this.startReportDate);
		Calendar endReportDate 		= new Formatter().formatDate(this.endReportDate);
		
		endReportDate.add(Calendar.DAY_OF_YEAR, 1); //Para agregar un dï¿½a a la fecha final del reporte

		//layout.getAdquirerReportDTO(startReportDate, endReportDate, dateReportFilter, statusReportFilter);//anterior sin GL
		layout.getAdquirerReportDTO(startReportDate, endReportDate, dateReportFilter, statusReportFilter, statusGarantiaLiquida); //para el DTO
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
		System.out.println("Fin de la ejecucion Reporte Adquirente...");
	}
	
	public void getAdquirerDetailReportDaily() {
		
		LayoutOperations layout 	= LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        
		System.out.println("Inicio de la ejecucion manual de Reporte Diario Adquirente...");
		bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.REPORT_ADQUIRENTE_DIARIO, userNumber);
		
		Calendar startReportDate 	= new Formatter().formatDate(this.startReportDate);
		Calendar endReportDate 		= new Formatter().formatDate(this.endReportDate);
		
		//endReportDate.add(Calendar.DAY_OF_YEAR, 1); //Para agregar un dï¿½a a la fecha final del reporte

		layout.createAdquirerStatusDTOReport(startReportDate, endReportDate,false);
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
		System.out.println("Fin de la ejecucion manual de Reporte Adquirente Diario...");
	}

	public void getSIPDetailReport() {
		
		LayoutOperations layout 	= LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        
		System.out.println("Inicio de la ejecucion Reporte Solucion Integral PyME...");
		bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.REPORT_SOLUCION_INTEGRAL_PYME, userNumber);

		Calendar startReportDate 	= new Formatter().formatDate(this.startReportDate);
		Calendar endReportDate 		= new Formatter().formatDate(this.endReportDate);
		endReportDate.add(Calendar.DAY_OF_YEAR, 1); //se agrega un dï¿½a para que se tome completa la fecha final
		
		//layout.getSIPDetailReport(startReportDate, endReportDate);
		layout.getSipReportDTO(startReportDate, endReportDate, dateReportFilter, statusReportFilter);
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
		System.out.println("Fin de la ejecucion Reporte Solucion Integral PyME...");
	}
	
	
	/**
	 * Lee el archivo para cargar las nuevas comisiones 
	 */
	public String readLayoutCommision() {
		//CommisionLayout commisionLayout 	= new CommisionLayout();
		LayoutOperations layout = LayoutOperations.getInstance();
		Bitacora bitacora 					= new Bitacora();
        FacesContext fCtx 					= FacesContext.getCurrentInstance();
        String userNumber 					= BitacoraUtil.getUserNumberSession(fCtx);
        boolean flag=false;
        
		System.out.println("Carga de Nuevas Comisiones TPV...");
		bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.LOAD_COMMISION, userNumber);
		flag=layout.startRecipeCommisions();
		System.out.println("Fin de Carga de Nuevas Comisiones TPV..."+ (new java.sql.Timestamp(System.currentTimeMillis())));
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
		if(!flag){
			if(LayoutOperationsResponse.listaErroresC.isEmpty() || LayoutOperationsResponse.listaErroresV.isEmpty()){
				ContractMessageErrors e = new ContractMessageErrors();
				e.setMessage("No existen registros.");
				LayoutOperationsResponse.listaErroresV.add(e);
			}
		}
		return "SUCCESS";
	}
	
	public List<ContractMessageErrors>getErroresValidacion(){
		List<ContractMessageErrors> erroresValidacion = new ArrayList<ContractMessageErrors>();
		erroresValidacion=LayoutOperationsResponse.listaErroresV;
		return erroresValidacion;
	}
	public List<ContractMessageErrors>getErroresComPlan(){
		List<ContractMessageErrors>erroresComPlan=new ArrayList<ContractMessageErrors>();
		erroresComPlan=LayoutOperationsResponse.listaErroresC;
		return erroresComPlan;
	}
	
	 /**
	 * Metodo que llena las ciudades en base al estado elegido para datos de sucursal
	 * @throws ServletException 
	 * @throws IOException 
	 */
	public void findBranchCities(ActionEvent actionEvent) throws ServletException, IOException {
		HtmlCommandLink link 	= (HtmlCommandLink) actionEvent.getSource();
		boolean redirect 		= Boolean.TRUE;
		
		getCitiesBranchArray();
		
		// Para recuperar el parï¿½metro enviado por el JSP //String anchor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(link.getId().toString()); //"anchor" + 
		String anchor="officeRepInfo";
		if(redirect){
			//Redirect
			redirectGeneralInfo(anchor);
		}
	}
	
	public abstract boolean savePartialContract();

	public String getApplicationdate() {
		return applicationdate;
	}

	public void setApplicationdate(String applicationDate) {
		this.applicationdate = applicationDate;
	}

	public String getBankingsector() {
		return bankingsector;
	}
	

	/**
	 * @return the validationMtto
	 */
	public boolean isValidationMtto() {
		return validationMtto;
	}

	/**
	 * @param validationMtto the validationMtto to set
	 */
	public void setValidationMtto(boolean validationMtto) {
		this.validationMtto = validationMtto;
	}

	public void setBankingsector(String bankingsector) {
		this.bankingsector = bankingsector;
	}

	public String getBranchcity() {
		return branchcity;
	}

	public void setBranchcity(String branchcity) {
		this.branchcity = branchcity;
	}

	public String getBranchcolony() {
		return branchcolony;
	}

	public void setBranchcolony(String branchcolony) {
		this.branchcolony = branchcolony;
	}

	public String getBranchcounty() {
		return branchcounty;
	}

	public void setBranchcounty(String branchcounty) {
		this.branchcounty = branchcounty;
	}

	public String getBranchfax() {
		return branchfax;
	}

	public void setBranchfax(String branchfax) {
		this.branchfax = branchfax;
	}

	public String getBranchname() {
		return branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getBranchphone() {
		return branchphone;
	}

	public void setBranchphone(String branchphone) {
		this.branchphone = branchphone;
	}

	public String getBranchstate() {
		return branchstate;
	}

	public void setBranchstate(String branchstate) {
		this.branchstate = branchstate;
	}

	public String getBranchstreet() {
		return branchstreet;
	}

	public void setBranchstreet(String branchstreet) {
		this.branchstreet = branchstreet;
	}

	public String getCelebrationdate() {
		return celebrationdate;
	}

	public void setCelebrationdate(String celebrationdate) {
		this.celebrationdate = celebrationdate;
	}

	public String getCelebrationplace() {
		return celebrationplace;
	}

	public void setCelebrationplace(String celebrationplace) {
		this.celebrationplace = celebrationplace;
	}

	public String getCelebrationstate() {
		return celebrationstate;
	}

	public void setCelebrationstate(String celebrationstate) {
		this.celebrationstate = celebrationstate;
	}

	public String getClient_site() {
		return client.getSite() != null ? client.getSite().toString() : "";
	}

	public void setClient_site(String client_site) {
		client.setSite(client_site);
	}

	public String getClient_categorycode() {
		return client.getCategorycode() != null ? client.getCategorycode()
				.toString() : "";
	}

	public void setClient_categorycode(String client_categorycode) {
		client.setCategorycode(client_categorycode);
	}

	public String getClient_city() {
		return client.getCity() != null ? client.getCity() : "";
	}

	public void setClient_city(String client_city) {
		client.setCity(client_city);
	}

	public String getClient_colony() {
		return client.getColony() != null ? client.getColony() : "";
	}

	public void setClient_colony(String client_colony) {
		client.setColony(client_colony);
	}

	public String getClient_constitutiondate() {
		return client.getConstitutiondate() != null ? formatDate
				.formatDateToString(client.getConstitutiondate()) : null;
	}

	public void setClient_constitutiondate(String client_constitutiondate) {
		client.setConstitutiondate(formatDate.formatDate(
				client_constitutiondate).getTime());
	}

	public String getClient_county() {
		return client.getCounty() != null ? client.getCounty() : "";
	}

	public void setClient_county(String client_county) {
		client.setCounty(client_county);
	}

	public String getClient_email() {
		return client.getEmail() != null ? client.getEmail() : "";
	}

	public void setClient_email(String client_email) {
		client.setEmail(client_email);
	}

	public String getClient_fiscalname() {
		return client.getFiscalname() != null ? client.getFiscalname() : "";
	}

	public void setClient_fiscalname(String client_fiscalname) {
		client.setFiscalname(client_fiscalname);
	}

	public Integer getClient_fiscaltype() {
		return client.getFiscaltype();
	}

	public void setClient_fiscaltype(Integer client_fiscaltype) {
		client.setFiscaltype(client_fiscaltype);
	}

	public String getClient_fscity() {
		return client.getFscity() != null ? client.getFscity() : "";
	}

	public void setClient_fscity(String client_fscity) {
		if (client.getFsname() != null && !client.getFsname().trim().equals(""))
			client.setFscity(client_fscity);
	}

	public String getClient_fscolony() {
		return client.getFscolony() != null ? client.getFscolony() : "";
	}

	public void setClient_fscolony(String client_fscolony) {
		client.setFscolony(client_fscolony);
	}

	public String getClient_fscounty() {
		return client.getFscounty() != null ? client.getFscounty() : "";
	}

	public void setClient_fscounty(String client_fscounty) {
		client.setFscounty(client_fscounty);
	}

	public String getClient_fsemail() {
		return client.getFsemail() != null ? client.getFsemail() : "";
	}

	public void setClient_fsemail(String client_fsemail) {
		client.setFsemail(client_fsemail);
	}

	public Integer getClient_fsfax() {
		return client.getFsfax();
	}

	public void setClient_fsfax(Integer client_fsfax) {
		client.setFsfax(client_fsfax);
	}

	public Integer getClient_fsfaxext() {
		return client.getFsfaxext();
	}

	public void setClient_fsfaxext(Integer client_fsfaxext) {
		client.setFsfaxext(client_fsfaxext);
	}

	public String getClient_fsname() {
		return client.getFsname() != null ? client.getFsname() : "";
	}

	public void setClient_fsname(String client_fsname) {
		client.setFsname(client_fsname);
	}

	public String getClient_fsnum() {
		return client.getFsnum() != null ? client.getFsnum() : "";
	}

	public void setClient_fsnum(String client_fsnum) {
		client.setFsnum(client_fsnum);
	}

	public Integer getClient_fsphone() {
		return client.getFsphone();
	}

	public void setClient_fsphone(Integer client_fsphone) {
		client.setFsphone(client_fsphone);
	}

	public Integer getClient_fsphoneext() {
		return client.getFsphoneext();
	}

	public void setClient_fsphoneext(Integer client_fsphoneext) {
		client.setFsphoneext(client_fsphoneext);
	}

	public String getClient_fsrfc() {
		return client.getFsrfc() != null ? client.getFsrfc() : "";
	}

	public void setClient_fsrfc(String client_fsrfc) {
		client.setFsrfc(client_fsrfc);
	}

	public String getClient_fsstate() {
		return client.getFsstate() != null ? client.getFsstate() : "";
	}

	public void setClient_fsstate(String client_fsstate) {
		if (client.getFsname() != null && !client.getFsname().trim().equals(""))
			client.setFsstate(client_fsstate);
	}

	public String getClient_fsstreet() {
		return client.getFsstreet() != null ? client.getFsstreet() : "";
	}

	public void setClient_fsstreet(String client_fsstreet) {
		client.setFsstreet(client_fsstreet);
	}

	public String getClient_fszipcode() {
		return client.getFszipcode() != null ? client.getFszipcode().toString()
				: "";
	}

	public void setClient_fszipcode(String client_fszipcode) {
		if (client_fszipcode != null && !client_fszipcode.trim().equals("")) {
			try {
				setClient_fszipcode(new Integer(client_fszipcode));
			} catch (NumberFormatException ex) {
				log.log(Level.WARNING, ex.getMessage(), ex);
			}
		} else {
			setClient_fszipcode((Integer) null);
		}
	}

	public void setClient_fszipcode(Integer client_fszipcode) {
		client.setFszipcode(client_fszipcode);
	}

	public String getClient_merchantname() {
		
		return client.getMerchantname() != null ? client.getMerchantname() : "";
	}

	public void setClient_merchantname(String client_merchantname) {
		client.setMerchantname(client_merchantname);
	}

	public String getClient_messageservice() {
		return client.getMessageservice() != null ? client.getMessageservice(): "";
	}

	public void setClient_messageservice(String client_messageservice) {
		client.setMessageservice(client_messageservice);
	}

	public String getClient_numext() {
		return client.getNumext() != null ? client.getNumext() : "";
	}

	public void setClient_numext(String client_numext) {
		client.setNumext(client_numext);
	}

	public String getClient_numint() {
		return client.getNumint() != null ? client.getNumint() : "";
	}

	public void setClient_numint(String client_numint) {
		client.setNumint(client_numint);
	}

	public Integer getClient_phone() {
		return client.getPhone() != null ? client.getPhone() : new Integer(0);
	}

	public void setClient_phone(Integer client_phone) {
		client.setPhone(client_phone);
	}

	public Integer getClient_phoneext() {
		return client.getPhoneext() != null ? client.getPhoneext()
				: new Integer(0);
	}

	public void setClient_phoneext(Integer client_phoneext) {
		client.setPhoneext(client_phoneext);
	}

	public String getClient_rfc() {
		return client.getRfc() != null ? client.getRfc() : "";
	}

	public void setClient_rfc(String client_rfc) {
		client.setRfc(client_rfc);
	}

	public String getClient_sic() {
		return client.getSic() != null ? client.getSic().toString().trim() : "";
	}

	public void setClient_sic(String client_sic) {
		if (client_sic != null && !client_sic.trim().equals("")) {
			try {
				setClient_sic(new Long(client_sic.trim()));
			} catch (NumberFormatException ex) {
				log.log(Level.WARNING, ex.getMessage(), ex);
			}

		}
	}

	public void setClient_sic(Long client_sic) {
		client.setSic(client_sic);
	}

	public String getClient_state() {
		return client.getState() != null ? client.getState() : "";
	}

	public void setClient_state(String client_state) {
		client.setState(client_state);
	}

	public String getClient_street() {
		return client.getStreet() != null ? client.getStreet() : "";
	}

	public void setClient_street(String client_street) {
		client.setStreet(client_street);
	}

	public Integer getClient_zipcode() {
		return client.getZipcode() != null ? client.getZipcode() : new Integer(
				0);
	}

	public void setClient_zipcode(Integer client_zipcode) {
		client.setZipcode(client_zipcode);
	}

	public Integer getClient_fax() {
		return client.getFax();
	}

	public void setClient_fax(Integer client_fax) {
		client.setFax(client_fax);
	}

	public Integer getClient_faxext() {
		return client.getFaxext();
	}

	public void setClient_faxext(Integer client_faxext) {
		client.setFaxext(client_faxext);
	}

	public String getCrnumber() {
		return crnumber;
	}

	public void setCrnumber(String crnumber) {
		this.crnumber = crnumber;
	}

	public String getOfficerempnumber() {
		return officerempnumber;
	}

	public void setOfficerempnumber(String officerempnumber) {
		this.officerempnumber = officerempnumber;
	}

	public String getOfficerlastname() {
		return officerlastname;
	}

	public void setOfficerlastname(String officerlastname) {
		this.officerlastname = officerlastname;
	}

	public String getOfficerreplastname_1() {
		return officerreplastname_1;
	}

	public void setOfficerreplastname_1(String officerlastname_1) {
		this.officerreplastname_1 = officerlastname_1;
	}

	public String getOfficerreplastname_2() {
		return officerreplastname_2;
	}

	public void setOfficerreplastname_2(String officerlastname_2) {
		this.officerreplastname_2 = officerlastname_2;
	}

	public String getOfficermothersln() {
		return officermothersln;
	}

	public void setOfficermothersln(String officermothersln) {
		this.officermothersln = officermothersln;
	}

	public String getOfficerrepmothersln_1() {
		return officerrepmothersln_1;
	}

	public void setOfficerrepmothersln_1(String officermothersln_1) {
		this.officerrepmothersln_1 = officermothersln_1;
	}

	public String getOfficerrepmothersln_2() {
		return officerrepmothersln_2;
	}

	public void setOfficerrepmothersln_2(String officermothersln_2) {
		this.officerrepmothersln_2 = officermothersln_2;
	}

	public String getOfficername() {
		return officername;
	}

	public void setOfficername(String officername) {
		this.officername = officername;
	}

	public String getOfficerrepname_1() {
		return officerrepname_1;
	}

	public void setOfficerrepname_1(String officername_1) {
		this.officerrepname_1 = officername_1;
	}

	public String getOfficerrepname_2() {
		return officerrepname_2;
	}

	public void setOfficerrepname_2(String officername_2) {
		this.officerrepname_2 = officername_2;
	}

	public String getOfficerposition() {
		return officerposition;
	}

	public void setOfficerposition(String officerposition) {
		this.officerposition = officerposition;
	}

	public String getOfficerrepposition_1() {
		return officerrepposition_1;
	}

	public void setOfficerrepposition_1(String officerposition_1) {
		this.officerrepposition_1 = officerposition_1;
	}

	public String getOfficerrepposition_2() {
		return officerrepposition_2;
	}

	public void setOfficerrepposition_2(String officerrepposition_2) {
		this.officerrepposition_2 = officerrepposition_2;
	}

	public String getOfficerrepempnumber_1() {
		return officerrepempnumber_1;
	}

	public void setOfficerrepempnumber_1(String officerrepnumber_1) {
		this.officerrepempnumber_1 = officerrepnumber_1;
	}

	public String getOfficerrepempnumber_2() {
		return officerrepempnumber_2;
	}

	public void setOfficerrepempnumber_2(String officerrepempnumber_2) {
		this.officerrepempnumber_2 = officerrepempnumber_2;
	}

	// Datos ejecutivo ebanking

	public String getOfficerebankingempnumber() {
		return officerebankingempnumber;
	}

	public void setOfficerebankingempnumber(String officerebankingempnumber) {
		this.officerebankingempnumber = officerebankingempnumber;
	}

	public String getOfficerebankinglastname() {
		return officerebankinglastname;
	}

	public void setOfficerebankinglastname(String officerebankinglastname) {
		this.officerebankinglastname = officerebankinglastname;
	}

	public String getOfficerebankingmothersln() {
		return officerebankingmothersln;
	}

	public void setOfficerebankingmothersln(String officerebankingmothersln) {
		this.officerebankingmothersln = officerebankingmothersln;
	}

	public String getOfficerebankingname() {
		return officerebankingname;
	}

	public void setOfficerebankingname(String officerebankingname) {
		this.officerebankingname = officerebankingname;
	}

	public Integer getClient_areacode() {
		return client.getAreacode() != null ? client.getAreacode()
				: new Integer(0);
	}

	public void setClient_areacode(Integer client_areacode) {
		client.setAreacode(client_areacode);
	}

	// REPRESENTANTE LEGAL
	public String getLegalrepresentative_name_1() {
		return legalRep1.getName();
	}

	public void setLegalrepresentative_name_1(String legalrepresentative_name_1) {
		legalRep1.setName(legalrepresentative_name_1);
	}

	public String getLegalrepresentative_name_2() {
		return legalRep2.getName();
	}

	public void setLegalrepresentative_name_2(String legalrepresentative_name_2) {
		legalRep2.setName(legalrepresentative_name_2);
	}

	public String getLegalrepresentative_lastname_1() {
		return legalRep1.getLastname();
	}

	public void setLegalrepresentative_lastname_1(
			String legalrepresentative_lastname_1) {
		legalRep1.setLastname(legalrepresentative_lastname_1);
	}

	public String getLegalrepresentative_lastname_2() {
		return legalRep2.getLastname();
	}

	public void setLegalrepresentative_lastname_2(
			String legalrepresentative_lastname_2) {
		legalRep2.setLastname(legalrepresentative_lastname_2);
	}

	public String getLegalrepresentative_mothersln_1() {
		return legalRep1.getMothersln();
	}

	public void setLegalrepresentative_mothersln_1(
			String legalrepresentative_mothersln_1) {
		legalRep1.setMothersln(legalrepresentative_mothersln_1);
	}

	public String getLegalrepresentative_mothersln_2() {
		return legalRep2.getMothersln();
	}

	public void setLegalrepresentative_mothersln_2(
			String legalrepresentative_mothersln_2) {
		legalRep2.setMothersln(legalrepresentative_mothersln_2);
	}

	public String getLegalrepresentative_email_1() {
		return legalRep1.getEmail();
	}

	public void setLegalrepresentative_email_1(
			String legalrepresentative_email_1) {
		legalRep1.setEmail(legalrepresentative_email_1);
	}

	public String getLegalrepresentative_email_2() {
		return legalRep2.getEmail();
	}

	public void setLegalrepresentative_email_2(
			String legalrepresentative_email_2) {
		legalRep2.setEmail(legalrepresentative_email_2);
	}
	
	public String getLegalrepresentative_rfc_1() {
		return legalRep1.getRfc();
	}

	public void setLegalrepresentative_rfc_1(String legalrepresentative_rfc_1) {
		legalRep1.setRfc(legalrepresentative_rfc_1);
	}

	public String getLegalrepresentative_rfc_2() {
		return legalRep2.getRfc();
	}

	public void setLegalrepresentative_rfc_2(String legalrepresentative_rfc_2) {
		this.legalRep2.setRfc(legalrepresentative_rfc_2);
	}

	public String getLegalrepresentative_position_1() {
		return legalRep1.getPosition();
	}
	
	

	/**
	 * @return the officerebankingposition
	 */
	public String getOfficerebankingposition() {
		return officerebankingposition;
	}

	/**
	 * @param officerebankingposition the officerebankingposition to set
	 */
	public void setOfficerebankingposition(String officerebankingposition) {
		this.officerebankingposition = officerebankingposition;
	}


	public void setLegalrepresentative_position_1(
			String legalrepresentative_position_1) {
		this.legalRep1.setPosition(legalrepresentative_position_1);
	}

	public String getLegalrepresentative_position_2() {
		return legalRep2.getPosition();
	}

	public void setLegalrepresentative_position_2(
			String legalrepresentative_position_2) {
		this.legalRep2.setPosition(legalrepresentative_position_2);
	}

	// CONTACTOS ESPECIALES
	public String getClientcontact_name1() {
		return clientContact1.getName() != null ? clientContact1.getName() : "";
	}

	public void setClientcontact_name1(String clientcontact_name1) {
		clientContact1.setName(clientcontact_name1);
	}

	public String getClientcontact_name2() {
		return clientContact2.getName() != null ? clientContact2.getName() : "";
	}

	public void setClientcontact_name2(String clientcontact_name2) {
		clientContact2.setName(clientcontact_name2);
	}

	public String getClientcontact_name3() {
		return clientContact3.getName();
	}

	public void setClientcontact_name3(String clientcontact_name3) {
		clientContact3.setName(clientcontact_name3);
	}

	public String getClientcontact_name4() {
		return clientContact4.getName();
	}

	public void setClientcontact_name4(String clientcontact_name4) {
		clientContact4.setName(clientcontact_name4);
	}

	public String getClientcontact_name5() {
		return clientContact5.getName();
	}

	public void setClientcontact_name5(String clientcontact_name5) {
		clientContact5.setName(clientcontact_name5);
	}

	public String getClientcontact_name6() {
		return clientContact6.getName();
	}

	public void setClientcontact_name6(String clientcontact_name6) {
		clientContact6.setName(clientcontact_name6);
	}

	public String getClientcontact_name7() {
		return clientContact7.getName();
	}

	public void setClientcontact_name7(String clientcontact_name7) {
		clientContact7.setName(clientcontact_name7);
	}

	public String getClientcontact_lastname1() {
		return clientContact1.getLastname() != null ? clientContact1
				.getLastname() : "";
	}

	public void setClientcontact_lastname1(String clientcontact_lastname1) {
		clientContact1.setLastname(clientcontact_lastname1);
	}

	public String getClientcontact_lastname2() {
		return clientContact2.getLastname() != null ? clientContact2.getLastname() : "";
	}

	public void setClientcontact_lastname2(String clientcontact_lastname2) {
		clientContact2.setLastname(clientcontact_lastname2);
	}

	public String getClientcontact_lastname3() {
		return clientContact3.getLastname();
	}

	public void setClientcontact_lastname3(String clientcontact_lastname3) {
		clientContact3.setLastname(clientcontact_lastname3);
	}

	public String getClientcontact_lastname4() {
		return clientContact4.getLastname();
	}

	public void setClientcontact_lastname4(String clientcontact_lastname4) {
		clientContact4.setLastname(clientcontact_lastname4);
	}

	public String getClientcontact_lastname5() {
		return clientContact5.getLastname();
	}

	public void setClientcontact_lastname5(String clientcontact_lastname5) {
		clientContact5.setLastname(clientcontact_lastname5);
	}

	public String getClientcontact_lastname6() {
		return clientContact6.getLastname();
	}
	
	

	/**
	 * @return the officerebankingnumfirm
	 */
	public String getOfficerebankingnumfirm() {
		return officerebankingnumfirm;
	}

	/**
	 * @param officerebankingnumfirm the officerebankingnumfirm to set
	 */
	public void setOfficerebankingnumfirm(String officerebankingnumfirm) {
		this.officerebankingnumfirm = officerebankingnumfirm;
	}

	public void setClientcontact_lastname6(String clientcontact_lastname6) {
		clientContact6.setLastname(clientcontact_lastname6);
	}

	public String getClientcontact_lastname7() {
		return clientContact7.getLastname();
	}

	public void setClientcontact_lastname7(String clientcontact_lastname7) {
		clientContact7.setLastname(clientcontact_lastname7);
	}

	public String getClientcontact_mothersln1() {
		return clientContact1.getMothersln() != null ? clientContact1
				.getMothersln() : "";
	}

	public void setClientcontact_mothersln1(String clientcontact_mothersln1) {
		clientContact1.setMothersln(clientcontact_mothersln1);
	}

	public String getClientcontact_mothersln2() {
		return clientContact2.getMothersln() != null ? clientContact2
				.getMothersln() : "";
	}

	public void setClientcontact_mothersln2(String clientcontact_mothersln2) {
		clientContact2.setMothersln(clientcontact_mothersln2);
	}

	public String getClientcontact_mothersln3() {
		return clientContact3.getMothersln();
	}

	public void setClientcontact_mothersln3(String clientcontact_mothersln3) {
		clientContact3.setMothersln(clientcontact_mothersln3);
	}

	public String getClientcontact_mothersln4() {
		return clientContact4.getMothersln();
	}

	public void setClientcontact_mothersln4(String clientcontact_mothersln4) {
		clientContact4.setMothersln(clientcontact_mothersln4);
	}

	public String getClientcontact_mothersln5() {
		return clientContact5.getMothersln();
	}

	public void setClientcontact_mothersln5(String clientcontact_mothersln5) {
		clientContact5.setMothersln(clientcontact_mothersln5);
	}

	public String getClientcontact_mothersln6() {
		return clientContact6.getMothersln();
	}

	public void setClientcontact_mothersln6(String clientcontact_mothersln6) {
		clientContact6.setMothersln(clientcontact_mothersln6);
	}

	public String getClientcontact_mothersln7() {
		return clientContact7.getMothersln();
	}

	public void setClientcontact_mothersln7(String clientcontact_mothersln7) {
		clientContact7.setMothersln(clientcontact_mothersln7);
	}

	public Integer getClientcontact_phone1() {
		return clientContact1.getPhone();
	}

	public void setClientcontact_phone1(Integer clientcontact_phone1) {
		clientContact1.setPhone(clientcontact_phone1);
	}

	public Integer getClientcontact_phone2() {
		return clientContact2.getPhone();
	}

	public void setClientcontact_phone2(Integer clientcontact_phone2) {
		clientContact2.setPhone(clientcontact_phone2);
	}

	public Integer getClientcontact_phone3() {
		return clientContact3.getPhone();
	}

	public void setClientcontact_phone3(Integer clientcontact_phone3) {
		clientContact3.setPhone(clientcontact_phone3);
	}

	public Integer getClientcontact_phone4() {
		return clientContact4.getPhone();
	}

	public void setClientcontact_phone4(Integer clientcontact_phone4) {
		clientContact4.setPhone(clientcontact_phone4);
	}

	public Integer getClientcontact_phone5() {
		return clientContact5.getPhone();
	}

	public void setClientcontact_phone5(Integer clientcontact_phone5) {
		clientContact5.setPhone(clientcontact_phone5);
	}

	public Integer getClientcontact_phone6() {
		return clientContact6.getPhone();
	}

	public void setClientcontact_phone6(Integer clientcontact_phone6) {
		clientContact6.setPhone(clientcontact_phone6);
	}

	public Integer getClientcontact_phone7() {
		return clientContact7.getPhone();
	}

	public void setClientcontact_phone7(Integer clientcontact_phone7) {
		clientContact7.setPhone(clientcontact_phone7);
	}

	public Integer getClientcontact_phoneext1() {
		return clientContact1.getPhoneext();
	}

	public void setClientcontact_phoneext1(Integer clientcontact_phoneext1) {
		clientContact1.setPhoneext(clientcontact_phoneext1);
	}

	public Integer getClientcontact_phoneext2() {
		return clientContact2.getPhoneext();
	}

	public void setClientcontact_phoneext2(Integer clientcontact_phoneext2) {
		clientContact2.setPhoneext(clientcontact_phoneext2);
	}

	public Integer getClientcontact_phoneext3() {
		return clientContact3.getPhoneext();
	}

	public void setClientcontact_phoneext3(Integer clientcontact_phoneext3) {
		clientContact3.setPhoneext(clientcontact_phoneext3);
	}

	public Integer getClientcontact_phoneext4() {
		return clientContact4.getPhoneext();
	}

	public void setClientcontact_phoneext4(Integer clientcontact_phoneext4) {
		clientContact4.setPhoneext(clientcontact_phoneext4);
	}

	public Integer getClientcontact_phoneext5() {
		return clientContact5.getPhoneext();
	}

	public void setClientcontact_phoneext5(Integer clientcontact_phoneext5) {
		clientContact5.setPhoneext(clientcontact_phoneext5);
	}

	public Integer getClientcontact_phoneext6() {
		return clientContact6.getPhoneext();
	}

	public void setClientcontact_phoneext6(Integer clientcontact_phoneext6) {
		clientContact6.setPhoneext(clientcontact_phoneext6);
	}

	public Integer getClientcontact_phoneext7() {
		return clientContact7.getPhoneext();
	}

	public void setClientcontact_phoneext7(Integer clientcontact_phoneext7) {
		clientContact7.setPhoneext(clientcontact_phoneext7);
	}

	public String getClientcontact_email1() {
		return clientContact1.getEmail() != null ? clientContact1.getEmail(): "";
	}

	public void setClientcontact_email1(String clientcontact_email1) {
		clientContact1.setEmail(clientcontact_email1);
	}

	public String getClientcontact_email2() {
		return clientContact2.getEmail() != null ? clientContact2.getEmail(): "";
	}

	public void setClientcontact_email2(String clientcontact_email2) {
		clientContact2.setEmail(clientcontact_email2);
	}

	public String getClientcontact_email3() {
		return clientContact3.getEmail();
	}

	public void setClientcontact_email3(String clientcontact_email3) {
		clientContact3.setEmail(clientcontact_email3);
	}

	public String getClientcontact_email4() {
		return clientContact4.getEmail();
	}

	public void setClientcontact_email4(String clientcontact_email4) {
		clientContact4.setEmail(clientcontact_email4);
	}

	public String getClientcontact_email5() {
		return clientContact5.getEmail();
	}

	public void setClientcontact_email5(String clientcontact_email5) {
		clientContact5.setEmail(clientcontact_email5);
	}

	public String getClientcontact_email6() {
		return clientContact6.getEmail();
	}

	public void setClientcontact_email6(String clientcontact_email6) {
		clientContact6.setEmail(clientcontact_email6);
	}

	public String getClientcontact_email7() {
		return clientContact7.getEmail();
	}

	public void setClientcontact_email7(String clientcontact_email7) {
		clientContact7.setEmail(clientcontact_email7);
	}

	public String getClientcontact_position1() {
		return clientContact1.getPosition();
	}

	public void setClientcontact_position1(String clientcontact_position1) {
		clientContact1.setPosition(clientcontact_position1);
	}

	public String getClientcontact_position2() {
		return clientContact2.getPosition();
	}

	public void setClientcontact_position2(String clientcontact_position2) {
		clientContact2.setPosition(clientcontact_position2);
	}

	public String getClientcontact_position3() {
		return clientContact3.getPosition();
	}

	public void setClientcontact_position3(String clientcontact_position3) {
		clientContact3.setPosition(clientcontact_position3);
	}

	public String getClientcontact_position4() {
		return clientContact4.getPosition();
	}

	public void setClientcontact_position4(String clientcontact_position4) {
		clientContact4.setPosition(clientcontact_position4);
	}

	public String getClientcontact_position5() {
		return clientContact5.getPosition();
	}

	public void setClientcontact_position5(String clientcontact_position5) {
		clientContact5.setPosition(clientcontact_position5);
	}

	public String getClientcontact_position6() {
		return clientContact6.getPosition();
	}

	public void setClientcontact_position6(String clientcontact_position6) {
		clientContact6.setPosition(clientcontact_position6);
	}

	public String getClientcontact_position7() {
		return clientContact7.getPosition();
	}

	public void setClientcontact_position7(String clientcontact_position7) {
		clientContact7.setPosition(clientcontact_position7);
	}

	// Utilizados como Banderas para mostrar el Template Final
	public boolean getToPrint() {
		return toPrint;
	}

	public void setToPrint(boolean toPrint) {
		this.toPrint = toPrint;
	}

	public boolean getToAuthorize() {
		return toAuthorize;
	}

	public void setToAuthorize(boolean toAuthorize) {
		this.toAuthorize = toAuthorize;
	}

	public String getComments() {
		if(comments==null)
			this.comments ="";
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	// Arrays de estados y poblaciones
	public void setStatesArray(SelectItem[] statesArray) {
		this.statesArray = statesArray;
	}
	
	

	/**
	 * @return the officerebankingEspPosition
	 */
	public String getOfficerebankingEspPosition() {
		return officerebankingEspPosition;
	}

	/**
	 * @param officerebankingEspPosition the officerebankingEspPosition to set
	 */
	public void setOfficerebankingEspPosition(String officerebankingEspPosition) {
		this.officerebankingEspPosition = officerebankingEspPosition;
	}

	/**
	 * @return the officerebankingTposition
	 */
	public String getOfficerebankingTposition() {
		return officerebankingTposition;
	}

	/**
	 * @param officerebankingTposition the officerebankingTposition to set
	 */
	public void setOfficerebankingTposition(String officerebankingTposition) {
		this.officerebankingTposition = officerebankingTposition;
	}

	/**
	 * @return the officerebankingEspName
	 */
	public String getOfficerebankingEspName() {
		return officerebankingEspName;
	}

	/**
	 * @param officerebankingEspName the officerebankingEspName to set
	 */
	public void setOfficerebankingEspName(String officerebankingEspName) {
		this.officerebankingEspName = officerebankingEspName;
	}

	/**
	 * @return the officerebankingEspLastname
	 */
	public String getOfficerebankingEspLastname() {
		return officerebankingEspLastname;
	}

	/**
	 * @param officerebankingEspLastname the officerebankingEspLastname to set
	 */
	public void setOfficerebankingEspLastname(String officerebankingEspLastname) {
		this.officerebankingEspLastname = officerebankingEspLastname;
	}

	/**
	 * @return the officerebankingEspMothersln
	 */
	public String getOfficerebankingEspMothersln() {
		return officerebankingEspMothersln;
	}

	/**
	 * @param officerebankingEspMothersln the officerebankingEspMothersln to set
	 */
	public void setOfficerebankingEspMothersln(String officerebankingEspMothersln) {
		this.officerebankingEspMothersln = officerebankingEspMothersln;
	}

	/**
	 * @return the officerebankingEspEmpnumber
	 */
	public String getOfficerebankingEspEmpnumber() {
		return officerebankingEspEmpnumber;
	}

	/**
	 * @param officerebankingEspEmpnumber the officerebankingEspEmpnumber to set
	 */
	public void setOfficerebankingEspEmpnumber(String officerebankingEspEmpnumber) {
		this.officerebankingEspEmpnumber = officerebankingEspEmpnumber;
	}

	/**
	 * @return the officerebankingTname
	 */
	public String getOfficerebankingTname() {
		return officerebankingTname;
	}

	/**
	 * @param officerebankingTname the officerebankingTname to set
	 */
	public void setOfficerebankingTname(String officerebankingTname) {
		this.officerebankingTname = officerebankingTname;
	}

	/**
	 * @return the officerebankingTlastname
	 */
	public String getOfficerebankingTlastname() {
		return officerebankingTlastname;
	}

	/**
	 * @param officerebankingTlastname the officerebankingTlastname to set
	 */
	public void setOfficerebankingTlastname(String officerebankingTlastname) {
		this.officerebankingTlastname = officerebankingTlastname;
	}

	/**
	 * @return the officerebankingTmothersln
	 */
	public String getOfficerebankingTmothersln() {
		return officerebankingTmothersln;
	}

	/**
	 * @param officerebankingTmothersln the officerebankingTmothersln to set
	 */
	public void setOfficerebankingTmothersln(String officerebankingTmothersln) {
		this.officerebankingTmothersln = officerebankingTmothersln;
	}

	/**
	 * @return the officerebankingTempnumber
	 */
	public String getOfficerebankingTempnumber() {
		return officerebankingTempnumber;
	}

	/**
	 * @param officerebankingTempnumber the officerebankingTempnumber to set
	 */
	public void setOfficerebankingTempnumber(String officerebankingTempnumber) {
		this.officerebankingTempnumber = officerebankingTempnumber;
	}
	
	public SelectItem[] getStatesArray() {
		if (this.statesArray == null) {
			// Lista Estados
			List<States> states = statesBean.findAll();
			if (states != null) {
				statesArray = new SelectItem[states.size()];
				int i = 0;
				for (States sta : states) {
					// System.err.println("Estado: " + sta.getName());
					statesArray[i] = new SelectItem(sta.getName(), sta.getName());
					i++;
				}
			}
		}
		return statesArray;
	}


	public void setCitiesCelebrationArray(SelectItem[] citiesCelebrationArray) {
		this.citiesCelebrationArray = citiesCelebrationArray;
	}

	public SelectItem[] getCitiesCelebrationArray() {
		if(getCelebrationstate() ==null){
			setCelebrationstate(ApplicationConstants.EMPTY_STRING);
		}
		if (getCelebrationstate() != null) {
			List<Cities> cities = citiesBean.findByState(getCelebrationstate());
			if (cities != null) {
				citiesCelebrationArray = new SelectItem[cities.size()];
				int i = 0;
				for (Cities cit : cities) {
					// System.err.println("Ciudad: " + cit.getName());
					citiesCelebrationArray[i] = new SelectItem(cit.getName(),cit.getName());
					i++;
				}
			}
		}
		return citiesCelebrationArray;
	}

	public void setCitiesBranchArray(SelectItem[] citiesBranchArray) {
		this.citiesBranchArray = citiesBranchArray;
	}

	public SelectItem[] getCitiesBranchArray() {
		if (getBranchstate() != null) {
			List<Cities> cities = citiesBean.findByState(getBranchstate());
			if (cities != null) {
				citiesBranchArray = new SelectItem[cities.size()];
				int i = 0;
				for (Cities cit : cities) {
					// System.err.println("Ciudad: " + cit.getName());
					citiesBranchArray[i] = new SelectItem(cit.getName(), cit.getName());
					i++;
				}
			}
		}
		return citiesBranchArray;
	}
	
	public void setCitiesClientArray(SelectItem[] citiesClientArray) {
		this.citiesClientArray = citiesClientArray;
	}

	public SelectItem[] getCitiesClientArray() {
		if (getClient_state() != null) {
			List<Cities> cities = citiesBean.findByState(getClient_state());
			if (cities != null) {
				citiesClientArray = new SelectItem[cities.size()];
				int i = 0;
				for (Cities cit : cities) {
					// System.err.println("Ciudad: " + cit.getName());
					citiesClientArray[i] = new SelectItem(cit.getName(), cit
							.getName());
					i++;
				}
			}
		}
		return citiesClientArray;
	}

	/*
	 * Propiedades para reportes de Mitigacion de Riesgo
	 */
	
	//Propiedad: StartMaintanceProcessDate
	public void setStartMaintanceProcessDate(String startDate) {
		this.startMaintanceProcessDate = startDate;
	}
	public String getStartMaintanceProcessDate() {
		return this.startMaintanceProcessDate;
	}

	//Propiedad: ProcessDays
	public void setProcessDays(String processDays) {
		this.processDays = processDays;
	}
	public String getProcessDays() {
		return this.processDays;
	}

	/*
	 * Propiedades para reportes de productos
	 */
	
	//Propiedad: StartReportDate
	public void setStartReportDate(String startDate) {
		this.startReportDate = startDate;
	}
	public String getStartReportDate() {
		return this.startReportDate;
	}

	//Propiedad: EndReportDate
	public void setEndReportDate(String endDate) {
		this.endReportDate= endDate;
	}
	public String getEndReportDate() {
		return this.endReportDate;
	}
	
	
	// Validacion mensajes

	public Collection<ContractMessageErrors> getErrorsList() {
		return errorsList;
	}

	public void setErrorsList(Collection<ContractMessageErrors> errorsList) {
		this.errorsList = errorsList;
	}

	public ArrayList getGeneralInfoErrorsList() {
		return generalInfoErrorsList;
	}

	public void setGeneralInfoErrorsList(ArrayList generalInfoErrorsList) {
		this.generalInfoErrorsList = generalInfoErrorsList;
	}

	
	/*
	 * property: OfficerRepFirmNumber_1
	 */
	public void setOfficerrepfirmnumber_1(String officerrepfirmnumber_1){
		this.officerrepfirmnumber_1 = officerrepfirmnumber_1;
	}
	public String getOfficerrepfirmnumber_1(){
		return this.officerrepfirmnumber_1;
	}

	/*
	 * property: OfficerRepFirmNumber_2
	 */
	public void setOfficerrepfirmnumber_2(String officerrepfirmnumber_2){
		this.officerrepfirmnumber_2 = officerrepfirmnumber_2;
	}
	public String getOfficerrepfirmnumber_2(){
		return this.officerrepfirmnumber_2;
	}
	/*
	public String getRfcRepresentanteLegal1() {
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
	
	/**
	 * Validacion Legal Representative 1
	 * Nombre 
	 * Apellido Paterno 
	 * Apellido Materno
	 */
	private void validateLegalRepresentative1(){
		ContractMessageErrors errors;
		
		if (getLegalrepresentative_name_1() == null
				|| getLegalrepresentative_name_1().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderados - Nombre 1.");
			generalInfoErrorsList.add(errors);
		}

		if (getLegalrepresentative_lastname_1() == null
				|| getLegalrepresentative_lastname_1().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderados - Apellido Paterno 1.");
			generalInfoErrorsList.add(errors);
		}

		if (getLegalrepresentative_mothersln_1() == null
				|| getLegalrepresentative_mothersln_1().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderados - Apellido Materno 1.");
			generalInfoErrorsList.add(errors);
		}

	
	}
	
	/**
	 * Valida Estado y Ciudad 
	 */
	private void validateStateCity(){
		
		ContractMessageErrors errors;
		
		if (getCelebrationplace() == null
				|| getCelebrationplace().trim().length() == 0
				|| getCelebrationplace().equals("Seleccione una Ciudad")) {
			errors = new ContractMessageErrors();
			errors
					.setMessage("Favor de capturar Datos de Celebracion - Poblacion");
			generalInfoErrorsList.add(errors);
		}

		if (getCelebrationstate() == null
				|| getCelebrationstate().trim().length() == 0
				|| getCelebrationstate().equals("Seleccione un Estado")) {
			errors = new ContractMessageErrors();
			errors
					.setMessage("Favor de capturar Datos de Celebracion - Estado");
			generalInfoErrorsList.add(errors);
		}
	}
	
	/**
	 * Datos del Funcionario que coloco el Producto
	 * Nombre 
	 * Apellido Paterno 
	 * Apellido Materno
	 * Numero
	 * Posicion
	 */
	private void validateOfficerInfo(){
		ContractMessageErrors errors;
		
		if (getOfficername() == null
				|| getOfficername().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Funcionario que Coloco el Producto - Nombre.");
			generalInfoErrorsList.add(errors);
		}

		if (getOfficerlastname() == null
				|| getOfficerlastname().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Funcionario que Coloco el Producto - Apellido Paterno.");
			generalInfoErrorsList.add(errors);
		}

		if (getOfficermothersln() == null
				|| getOfficermothersln().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Funcionario que Coloco el Producto - Apellido Materno.");
			generalInfoErrorsList.add(errors);
		}

		if (getOfficerempnumber() == null
				|| getOfficerempnumber().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Funcionario que Coloco el Producto - No. Nómina.");
			generalInfoErrorsList.add(errors);
		}

		if (getOfficerposition() == null
				|| getOfficerposition().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Funcionario que Coloco el Producto - Puesto.");
			generalInfoErrorsList.add(errors);
		}
	}
	
	/**
	 * Datos del Funcionario EBanking que coloco el Producto
	 * Nombre 
	 * Apellido Paterno 
	 * Apellido Materno
	 * Numero
	 */
	private void validateOfficerEbanking(){
		
		ContractMessageErrors errors;
		
		if (getOfficerebankingname() == null
				|| getOfficerebankingname().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Funcionario Ebanking que Coloco el Producto - Nombre.");
			generalInfoErrorsList.add(errors);
		}

		if (getOfficerebankinglastname() == null
				|| getOfficerebankinglastname().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Funcionario Ebanking que Coloco el Producto - Apellido Paterno.");
			generalInfoErrorsList.add(errors);
		}

		if (getOfficerebankingmothersln() == null
				|| getOfficerebankingmothersln().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Funcionario Ebanking que Coloco el Producto - Apellido Materno.");
			generalInfoErrorsList.add(errors);
		}

		if (getOfficerebankingempnumber() == null
				|| getOfficerebankingempnumber().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Funcionario Ebanking que Coloco el Producto - No. Nomina.");
			generalInfoErrorsList.add(errors);
		}
		
//		if (getOfficerebankingnumfirm() == null
//				|| getOfficerebankingnumfirm().trim().length() == 0) {
//			errors = new ContractMessageErrors();
//			errors.setMessage("Favor de capturar Datos del Funcionario Ebanking que Coloco el Producto - Firma.");
//			generalInfoErrorsList.add(errors);
//		}
	}
	
	
	/**
	 * Datos del Representante Banorte 1
	 * Nombre 
	 * Apellido Paterno 
	 * Apellido Materno
	 * Numero
	 * Posicion
	 */
	private void validateOfficerRep1(){
		
		ContractMessageErrors errors;
		
		if (getOfficerrepempnumber_1().equals(getOfficerrepempnumber_2())) {
			errors = new ContractMessageErrors();
			errors.setMessage("Datos del Representante Legal o Apoderado Banorte deben ser diferentes");
			generalInfoErrorsList.add(errors);
		}

		if (getOfficerrepname_1() == null
				|| getOfficerrepname_1().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Nombre 1.");
			generalInfoErrorsList.add(errors);
		}

		if (getOfficerreplastname_1() == null
				|| getOfficerreplastname_1().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Apellido Paterno 1.");
			generalInfoErrorsList.add(errors);
		}

		if (getOfficerrepmothersln_1() == null
				|| getOfficerrepmothersln_1().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Apellido Materno 1.");
			generalInfoErrorsList.add(errors);
		}

		if (getOfficerrepposition_1() == null
				|| getOfficerrepposition_1().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Puesto 1.");
			generalInfoErrorsList.add(errors);
		}
		
		if (getOfficerrepfirmnumber_1() == null
				|| getOfficerrepfirmnumber_1().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Firma 1.");
			generalInfoErrorsList.add(errors);
		}
		
	}
	
	/**
	 * Datos del Representante Banorte 2
	 * Nombre 
	 * Apellido Paterno 
	 * Apellido Materno	 
	 * Posicion
	 */
	private void validateOfficerRep2(){
		ContractMessageErrors errors;
		
		if (getOfficerrepname_2() == null
				|| getOfficerrepname_2().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Nombre 2.");
			generalInfoErrorsList.add(errors);
		}

		if (getOfficerreplastname_2() == null
				|| getOfficerreplastname_2().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Apellido Paterno 2.");
			generalInfoErrorsList.add(errors);
		}

		if (getOfficerrepmothersln_2() == null
				|| getOfficerrepmothersln_2().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Apellido Materno 2.");
			generalInfoErrorsList.add(errors);
		}

		if (getOfficerrepposition_2() == null
				|| getOfficerrepposition_2().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Puesto 2.");
			generalInfoErrorsList.add(errors);
		}
		
		if (getOfficerrepfirmnumber_2() == null
				|| getOfficerrepfirmnumber_2().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Firma 2.");
			generalInfoErrorsList.add(errors);
		}
		
	}
	
	/**
	 * Datos de La Sucursal
	 * CR Sucursal
	 * Nombre Sucursal
	 * Calle
	 * Colonia
	 * Telefono
	 * ciudad
	 * Estado
	 */
	private void validateBranchInfo(){
		
		ContractMessageErrors errors;
		
		if (getCrnumber() == null || getCrnumber().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos de la sucursal - CR.");
			generalInfoErrorsList.add(errors);
		}

		if (getBranchname() == null || getBranchname().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos de la sucursal - Nombre de la Sucursal.");
			generalInfoErrorsList.add(errors);
		}

		if (getBranchstreet() == null
				|| getBranchstreet().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos de la sucursal - Domicilio.");
			generalInfoErrorsList.add(errors);
		}

		if (getBranchcolony() == null
				|| getBranchcolony().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos de la sucursal - Colonia.");
			generalInfoErrorsList.add(errors);
		}

		if (getBranchphone() == null
				|| getBranchphone().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos de la sucursal - Telefono.");
			generalInfoErrorsList.add(errors);
		}

		if (getBranchcity() == null || getBranchcity().trim().length() == 0
				|| getBranchcity().equals("Seleccione una Ciudad")) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos de la sucursal- Ciudad.");
			generalInfoErrorsList.add(errors);
		}

		if (getBranchstate() == null
				|| getBranchstate().trim().length() == 0
				|| getBranchstate().equals("Seleccione un Estado")) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos de la sucursal- Estado.");
			generalInfoErrorsList.add(errors);
		}
	}
	
	
	
	public boolean validateGeneralInfoMtto(){
//		generalInfoErrorsList.clear();
		
//		if (this.getErrorsList() != null) {
//			this.getErrorsList().clear();
//		}
		ContractMessageErrors errors;
		
		
		if (getClient_sic() == null || getClient_sic().trim().length() == 0 ) {
			if(validationMtto){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar Datos del Contratante - Numero de Cliente en altamira (SIC), asegurese de que no contenga espacios.");
				generalInfoErrorsList.add(errors);
			}
		} else {
			
			if(validationMtto){
				validateLegalRepresentative1();
				validateOfficerRep2();
			}
			
			if(validationEDO){
				validateStateCity();
			}
			
			validateOfficerInfo();
			validateOfficerEbanking();
			validateOfficerRep1();
		} // Llave del SIC

		this.setErrorsList(generalInfoErrorsList);

		if (generalInfoErrorsList.isEmpty()) {
			return true;
		} else {
			return false;
		}
		
	}

	public boolean validateGeneralInfo() {
		generalInfoErrorsList.clear();
		if (this.getErrorsList() != null) {
			this.getErrorsList().clear();
		}
		ContractMessageErrors errors;

		if (getClient_sic() == null || getClient_sic().trim().length() == 0) {
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de capturar Datos del Contratante - Numero de Cliente en altamira (SIC), asegurese de que no contenga espacios.");
			generalInfoErrorsList.add(errors);
		} else {
			// Datos Celebracion
			if (getCelebrationdate() == null
					|| getCelebrationdate().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar Datos de Celebracion - Fecha");
				generalInfoErrorsList.add(errors);
			}

			if (getCelebrationplace() == null
					|| getCelebrationplace().trim().length() == 0
					|| getCelebrationplace().equals("Seleccione una Ciudad")) {
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar Datos de Celebracion - Poblacion");
				generalInfoErrorsList.add(errors);
			}

			if (getCelebrationstate() == null
					|| getCelebrationstate().trim().length() == 0
					|| getCelebrationstate().equals("Seleccione un Estado")) {
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar Datos de Celebracion - Estado");
				generalInfoErrorsList.add(errors);
			}

			// Datos del Contrante
			if (contract.getProduct().getProductid() > 2 && contract.getProduct().getProductid()  != 7) {// Solo para Adquiriente
				if (getClient_merchantname() == null
						|| getClient_merchantname().trim().length() == 0) 
				{
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de capturar Datos del Contratante - Nombre Comercial.");
					generalInfoErrorsList.add(errors);
				}
				if(client.getPhone().toString().length()<7){
					errors = new ContractMessageErrors();
					errors.setMessage("Datos del Contratante - Por favor capture un valor correcto para el telï¿½fono");
					generalInfoErrorsList.add(errors);
				}
				if(client.getZipcode().toString().length()<4){
					errors = new ContractMessageErrors();
					errors.setMessage("Datos del Contratante - Por favor capture un valor correcto para el C.P.");
					generalInfoErrorsList.add(errors);
				}
				
			}//fin solo adquirente

			if (contract.getProduct().getProductid() != 2) {
				if (getClient_zipcode() == null || getClient_zipcode() == 0) {
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de capturar Datos del Contratante - Codigo Postal.");
					generalInfoErrorsList.add(errors);
				}
			}

			if (getClient_street() == null
					|| getClient_street().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Contratante - Calle.");
				generalInfoErrorsList.add(errors);
			}

			if (getClient_numext() == null
					|| getClient_numext().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Contratante - No. Exterior.");
				generalInfoErrorsList.add(errors);
			}

			if (getClient_colony() == null
					|| getClient_colony().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Contratante - Colonia.");
				generalInfoErrorsList.add(errors);
			}

			if (getClient_city() == null
					|| getClient_city().trim().length() == 0
					|| getClient_city().equals("Seleccione una Ciudad")) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Contratante - Ciudad.");
				generalInfoErrorsList.add(errors);
			}

			if (getClient_state() == null
					|| getClient_state().trim().length() == 0
					|| getClient_state().equals("Seleccione un Estado")) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Contratante - Estado.");
				generalInfoErrorsList.add(errors);
			}

			if (getClient_email() == null
					|| getClient_email().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Contratante - Correo Electronico.");
				generalInfoErrorsList.add(errors);
			}  //Validacion del Correo Electronico
			/*else{
				Pattern p = Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
				Matcher m = p.matcher(client.getEmail());
				if (!m.find()) {
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de capturar Datos del Contratante - Correo Electronico.No se permiten caracteres extraï¿½os antes del caracter @");
					generalInfoErrorsList.add(errors);
				}
				
			}*/

			if (getClient_fiscalname() == null
					|| getClient_fiscalname().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Contratante - Nombre/Denominacion/Razon Social.");
				generalInfoErrorsList.add(errors);
			}

			if (contract.getProduct().getProductid() == 2) {// Solo Nï¿½mina
				if (getClient_constitutiondate() == null
						|| getClient_constitutiondate().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors
							.setMessage("Favor de capturar Datos del Contratante - Fecha de Constitucion.");
					generalInfoErrorsList.add(errors);
				}
			}

			if (getClient_rfc() == null || getClient_rfc().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Contratante - RFC.");
				generalInfoErrorsList.add(errors);
			} else {
				// Validacion de formato RFC excepto Nomina
				//if (contract.getProduct().getProductid() != 2) { 
					
					if (client.getFiscaltype() == 1) {// 1=Persona Fisica
						// 2=Persona Moral
						Pattern p = Pattern.compile("^[a-zA-Z]{4}[0-9]{6}[A-Za-z0-9]{3}$");
						Matcher m = p.matcher(client.getRfc());
						if (!m.find()) {
							errors = new ContractMessageErrors();
							errors.setMessage("Favor de capturar Datos del Contratante - RFC. Ej. EDNI161178RE9");
							generalInfoErrorsList.add(errors);
						}
					} else {
						Pattern p = Pattern.compile("^[a-z&A-Z]{3}\\s[0-9]{6}[A-Za-z0-9]{3}$");
						Matcher m = p.matcher(client.getRfc());
						if (!m.find()) {
							errors = new ContractMessageErrors();
							errors.setMessage("Favor de capturar Datos del Contratante - RFC. Ej. EDN 161178RE9");
							generalInfoErrorsList.add(errors);
						}

					}
				//}
			}

			if (contract.getProduct().getProductid() == 2) {//Solo Nomina
				if (getClient_categorycode() == null
						|| getClient_categorycode().trim().length() == 0
						|| getClient_categorycode().equals("Seleccione un giro")) {
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de capturar Datos del Contratante - No. Giro y Nombre.");
					generalInfoErrorsList.add(errors);
				}
			}

			// Contactos Especiales

			if (contract.getProduct().getProductid() <= 2) {// BEM y Nomina
				if (getClientcontact_name1() == null
						|| getClientcontact_name1().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors
							.setMessage("Favor de capturar Contactos Especiales - Nombre 1.");
					generalInfoErrorsList.add(errors);
				}

				if (getClientcontact_lastname1() == null
						|| getClientcontact_lastname1().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors
							.setMessage("Favor de capturar Contactos Especiales - Apellido Paterno 1.");
					generalInfoErrorsList.add(errors);
				}

				if (getClientcontact_mothersln1() == null
						|| getClientcontact_mothersln1().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors
							.setMessage("Favor de capturar Contactos Especiales - Apellido Materno 1.");
					generalInfoErrorsList.add(errors);
				}
			}

			if (contract.getProduct().getProductid() == 1) {// BEM
				if (getClientcontact_position1() == null
						|| getClientcontact_position1().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors
							.setMessage("Favor de capturar Contactos Especiales - Puesto.");
					generalInfoErrorsList.add(errors);
				}
			}

			// Fiador Solidario unicamente Adquiriente

			if (contract.getProduct().getProductid() > 2 && contract.getProduct().getProductid()  != 7) {// Adquiriente
				if (getClient_fsname() != null
						&& getClient_fsname().trim().length() > 0) {
					if (getClient_fsrfc() == null
							|| getClient_fsrfc().trim().length() == 0) {
						errors = new ContractMessageErrors();
						errors
								.setMessage("Favor de capturar Datos del Fiador - RFC.");
						generalInfoErrorsList.add(errors);
					}

					if (getClient_fsstreet() == null
							|| getClient_fsstreet().trim().length() == 0) {
						errors = new ContractMessageErrors();
						errors
								.setMessage("Favor de capturar Datos del Fiador - Calle.");
						generalInfoErrorsList.add(errors);
					}

					if (getClient_fsnum() == null
							|| getClient_fsnum().trim().length() == 0) {
						errors = new ContractMessageErrors();
						errors
								.setMessage("Favor de capturar Datos del Fiador - Numero.");
						generalInfoErrorsList.add(errors);
					}

					if (getClient_fszipcode() == null
							|| getClient_fszipcode().trim().length() == 0) {
						errors = new ContractMessageErrors();
						errors
								.setMessage("Favor de capturar Datos del Fiador - Codigo Postal.");
						generalInfoErrorsList.add(errors);
					}

					if (getClient_fscolony() == null
							|| getClient_fscolony().trim().length() == 0) {
						errors = new ContractMessageErrors();
						errors
								.setMessage("Favor de capturar Datos del Fiador - Colonia.");
						generalInfoErrorsList.add(errors);
					}

					if (getClient_fsstate() == null
							|| getClient_fsstate().trim().length() <= 0
							|| getClient_fsstate().equals(
									"Seleccione un Estado")) {
						errors = new ContractMessageErrors();
						errors
								.setMessage("Favor de capturar Datos del Fiador - Estado.");
						generalInfoErrorsList.add(errors);
					}

					if (getClient_fscity() == null
							|| getClient_fscity().trim().length() <= 0
							|| getClient_fscity().equals(
									"Seleccione una Ciudad")) {
						errors = new ContractMessageErrors();
						errors
								.setMessage("Favor de capturar Datos del Fiador - Poblacion.");
						generalInfoErrorsList.add(errors);
					}

					if (getClient_fsphone() == null || getClient_fsphone() <= 0) {
						errors = new ContractMessageErrors();
						errors
								.setMessage("Favor de capturar Datos del Fiador - Telefono.");
						generalInfoErrorsList.add(errors);
					}

					if (getClient_fsemail() == null
							|| getClient_fsemail().trim().length() == 0) {
						errors = new ContractMessageErrors();
						errors
								.setMessage("Favor de capturar Datos del Fiador - Email.");
						generalInfoErrorsList.add(errors);
					}

				}
			}

			// Datos Apoderados Leagales:

			if (getLegalrepresentative_name_1() == null
					|| getLegalrepresentative_name_1().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Representante Legal o Apoderados - Nombre 1.");
				generalInfoErrorsList.add(errors);
			}

			if (getLegalrepresentative_lastname_1() == null
					|| getLegalrepresentative_lastname_1().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Representante Legal o Apoderados - Apellido Paterno 1.");
				generalInfoErrorsList.add(errors);
			}

			if (getLegalrepresentative_mothersln_1() == null
					|| getLegalrepresentative_mothersln_1().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Representante Legal o Apoderados - Apellido Materno 1.");
				generalInfoErrorsList.add(errors);
			}

			if (contract.getProduct().getProductid() > 2 && contract.getProduct().getProductid()  != 7) {// Solo para
				// Adquirente
				if (getLegalrepresentative_rfc_1() == null
						|| getLegalrepresentative_rfc_1().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors
							.setMessage("Favor de capturar Datos del Representante Legal o Apoderados - RFC 1.");
					generalInfoErrorsList.add(errors);
				}

				if (getLegalrepresentative_email_1() == null
						|| getLegalrepresentative_email_1().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors
							.setMessage("Favor de capturar Datos del Representante Legal o Apoderados - Correo Electronico 1.");
					generalInfoErrorsList.add(errors);
				}
			}

			////F-113829 Actualización Formatos Cobranza / abril 2022 :Solo validar para COBRANZA
			if (contract.getProduct().getProductid().intValue() == ProductType.COBRANZA_DOMICILIADA.value()) {// Solo vï¿½lidar para COBRANZA
				/*
				if (this.getRfcRepresentanteLegal1() == null || this.getRfcRepresentanteLegal1().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - RFC 1.");
					generalInfoErrorsList.add(errors);
				}

				if (this.getRfcRepresentanteLegal2() == null || this.getRfcRepresentanteLegal2().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - RFC 2.");
					generalInfoErrorsList.add(errors);
				}
				*/
				///////////////////////1
				
				//if (this.getRfcRepresentanteLegal1() == null || this.getRfcRepresentanteLegal1().trim().length() == 0) {
				if (getLegalrepresentative_rfc_1() == null || getLegalrepresentative_rfc_1().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado - RFC 1.");
					generalInfoErrorsList.add(errors);
				} else {
					// Validacion de formato RFC excepto Nomina
					//if (contract.getProduct().getProductid() != 2) { 
						
						//if (client.getFiscaltype() == 1) {// 1=Persona Fisica
							// 2=Persona Moral
							Pattern p = Pattern.compile("^[a-zA-Z]{4}[0-9]{6}[A-Za-z0-9]{3}$");
							//Matcher m = p.matcher(this.getRfcRepresentanteLegal1());
							Matcher m = p.matcher(getLegalrepresentative_rfc_1());
							if (!m.find()) {
								errors = new ContractMessageErrors();
								errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado - RFC 1. Ej. EDNI161178RE9");
								generalInfoErrorsList.add(errors);
						/*	}
						} else {
							Pattern p = Pattern.compile("^[a-z&A-Z]{3}\\s[0-9]{6}[A-Za-z0-9]{3}$");
							Matcher m = p.matcher(client.getRfc());
							if (!m.find()) {
								errors = new ContractMessageErrors();
								errors.setMessage("Favor de capturar Datos del Contratante - RFC. Ej. EDN 161178RE9");
								generalInfoErrorsList.add(errors);
							}

						}*/
					//}
				}
				}
				
				///////////////////////2
				//if (this.getRfcRepresentanteLegal2() == null || this.getRfcRepresentanteLegal2().trim().length() == 0) {
				if (getLegalrepresentative_rfc_2() == null || getLegalrepresentative_rfc_2().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado - RFC 2.");
					generalInfoErrorsList.add(errors);
				} else {
					// Validacion de formato RFC excepto Nomina
					//if (contract.getProduct().getProductid() != 2) { 
						
						//if (client.getFiscaltype() == 1) {// 1=Persona Fisica
							// 2=Persona Moral
							Pattern p = Pattern.compile("^[a-zA-Z]{4}[0-9]{6}[A-Za-z0-9]{3}$");
							//Matcher m = p.matcher(this.getRfcRepresentanteLegal2());
							Matcher m = p.matcher(getLegalrepresentative_rfc_2());
							if (!m.find()) {
								errors = new ContractMessageErrors();
								errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado - RFC 2. Ej. EDNI161178RE9");
								generalInfoErrorsList.add(errors);
						/*	}
						} else {
							Pattern p = Pattern.compile("^[a-z&A-Z]{3}\\s[0-9]{6}[A-Za-z0-9]{3}$");
							Matcher m = p.matcher(client.getRfc());
							if (!m.find()) {
								errors = new ContractMessageErrors();
								errors.setMessage("Favor de capturar Datos del Contratante - RFC. Ej. EDN 161178RE9");
								generalInfoErrorsList.add(errors);
							}

						}*/
					//}
				}
				}
			}
			//FIN VALIDACION RFC COBRANZA

			if (getOfficername() == null
					|| getOfficername().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Funcionario que Coloco el Producto - Nombre.");
				generalInfoErrorsList.add(errors);
			}

			if (getOfficerlastname() == null
					|| getOfficerlastname().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Funcionario que Coloco el Producto - Apellido Paterno.");
				generalInfoErrorsList.add(errors);
			}

			if (getOfficermothersln() == null
					|| getOfficermothersln().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Funcionario que Coloco el Producto - Apellido Materno.");
				generalInfoErrorsList.add(errors);
			}

			if (getOfficerempnumber() == null
					|| getOfficerempnumber().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Funcionario que Coloco el Producto - No. Nï¿½mina.");
				generalInfoErrorsList.add(errors);
			}

			if (getOfficerposition() == null
					|| getOfficerposition().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Funcionario que Coloco el Producto - Puesto.");
				generalInfoErrorsList.add(errors);
			}

			if (!isOIP) {
				if (getOfficerebankingname() == null
						|| getOfficerebankingname().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors
							.setMessage("Favor de capturar Datos del Funcionario Ebanking que Coloco el Producto - Nombre.");
					generalInfoErrorsList.add(errors);
				}

				if (getOfficerebankinglastname() == null
						|| getOfficerebankinglastname().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors
							.setMessage("Favor de capturar Datos del Funcionario Ebanking que Coloco el Producto - Apellido Paterno.");
					generalInfoErrorsList.add(errors);
				}

				if (getOfficerebankingmothersln() == null
						|| getOfficerebankingmothersln().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors
							.setMessage("Favor de capturar Datos del Funcionario Ebanking que Coloco el Producto - Apellido Materno.");
					generalInfoErrorsList.add(errors);
				}

				if (getOfficerebankingempnumber() == null
						|| getOfficerebankingempnumber().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors
							.setMessage("Favor de capturar Datos del Funcionario Ebanking que Coloco el Producto - No. Nomina.");
					generalInfoErrorsList.add(errors);
				}
			}
			// Datos de la Sucursal:
			if (getCrnumber() == null || getCrnumber().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos de la sucursal - CR.");
				generalInfoErrorsList.add(errors);
			}

			if (getBranchname() == null || getBranchname().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos de la sucursal - Nombre de la Sucursal.");
				generalInfoErrorsList.add(errors);
			}

			if (getBranchstreet() == null
					|| getBranchstreet().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos de la sucursal - Domicilio.");
				generalInfoErrorsList.add(errors);
			}

			if (getBranchcolony() == null
					|| getBranchcolony().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos de la sucursal - Colonia.");
				generalInfoErrorsList.add(errors);
			}

			if (getBranchphone() == null
					|| getBranchphone().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos de la sucursal - Telefono.");
				generalInfoErrorsList.add(errors);
			}

			if (getBranchcity() == null || getBranchcity().trim().length() == 0
					|| getBranchcity().equals("Seleccione una Ciudad")) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos de la sucursal- Ciudad.");
				generalInfoErrorsList.add(errors);
			}

			if (getBranchstate() == null
					|| getBranchstate().trim().length() == 0
					|| getBranchstate().equals("Seleccione un Estado")) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos de la sucursal- Estado.");
				generalInfoErrorsList.add(errors);
			}

			if (getOfficerrepempnumber_1().equals(getOfficerrepempnumber_2())) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Datos del Representante Legal o Apoderado Banorte deben ser diferentes");
				generalInfoErrorsList.add(errors);
			}

			if (getOfficerrepname_1() == null
					|| getOfficerrepname_1().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Nombre 1.");
				generalInfoErrorsList.add(errors);
			}

			if (getOfficerreplastname_1() == null
					|| getOfficerreplastname_1().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Apellido Paterno 1.");
				generalInfoErrorsList.add(errors);
			}

			if (getOfficerrepmothersln_1() == null
					|| getOfficerrepmothersln_1().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Apellido Materno 1.");
				generalInfoErrorsList.add(errors);
			}

			if (getOfficerrepposition_1() == null
					|| getOfficerrepposition_1().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Puesto 1.");
				generalInfoErrorsList.add(errors);
			}


			if (getOfficerrepname_2() == null
					|| getOfficerrepname_2().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Nombre 2.");
				generalInfoErrorsList.add(errors);
			}

			if (getOfficerreplastname_2() == null
					|| getOfficerreplastname_2().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Apellido Paterno 2.");
				generalInfoErrorsList.add(errors);
			}

			if (getOfficerrepmothersln_2() == null
					|| getOfficerrepmothersln_2().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Apellido Materno 2.");
				generalInfoErrorsList.add(errors);
			}

			if (getOfficerrepposition_2() == null
					|| getOfficerrepposition_2().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors
						.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - Puesto 2.");
				generalInfoErrorsList.add(errors);
			}

			//Joseles(07Nov2011):Solo validar para BEM, antes de homologar en todos los productos 
			if (contract.getProduct().getProductid().intValue() == ProductType.BEM.value()) {// Solo vï¿½lidar para BEM

				if (this.getOfficerrepfirmnumber_1() == null || this.getOfficerrepfirmnumber_1().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - No. de Firma 1.");
					generalInfoErrorsList.add(errors);
				}

				if (this.getOfficerrepfirmnumber_2() == null || this.getOfficerrepfirmnumber_2().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de capturar Datos del Representante Legal o Apoderado Banorte - No. de Firma 2.");
					generalInfoErrorsList.add(errors);
				}
			}
			
		} // Llave del SIC

		this.setErrorsList(generalInfoErrorsList);

		if (generalInfoErrorsList.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public void updateExecutives() {
		
		LayoutOperations layOut 	= LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        
        bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.UPDATE_EXECUTIVE, userNumber);
		System.out.println("Inicio Actualizacion del Catalogo de Ejecutivos...");
		layOut.startRecipeExecutives();
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
		System.out.println("Fin Actualizacion del Catalogo de Ejecutivos..." + bitacora.getEndDate());
		
	}
	
	public void updateCitiesAndStates() {
		System.out.println("Inicio Actualizacion de los Catálogos de Ciudades y Estados...");
		
		LayoutOperations layOut = LayoutOperations.getInstance();
		layOut.startRecipeCitiesAndStates();
		
		System.out.println("Fin Actualizacion de los Catálogos de Ciudades y Estados..." + (new java.sql.Timestamp(System.currentTimeMillis())));
	}
	
	public void updateExecutiveBranch() {
		LayoutOperations layOut 	= LayoutOperations.getInstance();
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        
        bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.UPDATE_BRANCH, userNumber);
		System.out.println("Inicio Actualizacion del Catalogo de Sucursales...");
		layOut.startRecipeExecutiveBranch();
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
		System.out.println("Fin Actualizacion del Catalogo de Sucursales..." + bitacora.getEndDate());
	}
	
	
	public String goToExecutiveBranchList() {
		
		Bitacora bitacora 			= new Bitacora();
        FacesContext fCtx 			= FacesContext.getCurrentInstance();
        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
        String result 				= ApplicationConstants.EMPTY_STRING;
        
        initList();
        bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.BRANCH_LIST, userNumber);
        this.executiveBranchList = executiveBranchBean.getAllExecutiveBranch();
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
		
		result = ApplicationConstants.OUTCOME_EXECUTIVE;
		
		return result;
	}
	
	public String goToExecutiveList() {
			
			Bitacora bitacora 			= new Bitacora();
	        FacesContext fCtx 			= FacesContext.getCurrentInstance();
	        String userNumber 			= BitacoraUtil.getUserNumberSession(fCtx);
	        String result 				= ApplicationConstants.EMPTY_STRING;
	        
	        initList();
	        bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.EXECUTIVE_LIST, userNumber);
	        this.executiveList = executiveBean.getAllExecutive();
			BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
			
			result = ApplicationConstants.OUTCOME_EXECUTIVE;
			
			return result;
		}
	
	
	
	private void initList(){
		this.executiveBranchList = null;
		this.executiveList = null;
	}

	/**
	 * @return the foundBranch
	 */
	public boolean isFoundBranch() {
		return foundBranch;
	}

	/**
	 * @param foundBranch the foundBranch to set
	 */
	public void setFoundBranch(boolean foundBranch) {
		this.foundBranch = foundBranch;
	}
	
	/**
	 * Bitacora por numero de usuario
	 * @return
	 */
	public String getInfoBitacora() {
		String result 				= ApplicationConstants.EMPTY_STRING;
		Calendar startDate 			= new Formatter().formatDate(this.startBitacoraDate);
		Calendar endDate 			= new Formatter().formatDate(this.endBitacoraDate);
		endDate.add(Calendar.DAY_OF_YEAR, 1);
		referenceNumberBitacora="";//limpio variable del folio
		generalInfoErrorsList.clear();//limpio errores
		
		if(userNumberBitacora.trim().isEmpty()){//busqueda general de bitacora
			Calendar fechaMaxima = new Formatter().formatDate(startBitacoraDate);
			fechaMaxima.add(Calendar.DAY_OF_YEAR, 7);
			generalInfoErrorsList.clear();
			if(endDate.after(fechaMaxima)){
				ContractMessageErrors e = new ContractMessageErrors();
				e.setMessage("El rango de fechas no puede ser mayor a 7 días");
				generalInfoErrorsList.add(e);
				ContractMessageErrors e2 = new ContractMessageErrors();
				e2.setMessage("Para ver un rango mayor a 7 días haga click en 'Generar Reporte de Bitacora'");
				generalInfoErrorsList.add(e2);
			}else{
				bitacoraList = (ArrayList<Bitacora>) bitacoraBean.findBitacora(startDate, endDate);
			}
		}else{
			userNumberBitacora=userNumberBitacora.toUpperCase();//busqueda de bitacora folio
			bitacoraList = (ArrayList<Bitacora>) bitacoraBean.findUserBitacora(this.userNumberBitacora,startDate, endDate);
		}
		//Ordenar por fecha
		if(bitacoraList!=null && bitacoraList.size()>0){
			Collections.sort(bitacoraList, new BitacoraComparator());
			bitacoraList=addEmployeeBitacora(bitacoraList);
		}
		result = ApplicationConstants.OUTCOME_BITACORA;
		//Llenar nombre de empleado
		if(bitacoraList!=null && !bitacoraList.isEmpty()){
			nombreUsuarioBitacora=userNumberBitacora+" : "+bitacoraList.get(0).getNombreEmpleado();
		}else{
			nombreUsuarioBitacora=userNumberBitacora;
		}
		
		if(generalInfoErrorsList.isEmpty()){
			return result ;	
		}else{
			return"";
		}
	}
	
	/**
	 * bitacora por numero de folio
	 * @author gmerla
	 * @return
	 */
	public String getInfoBitacoraFolio(){
		userNumberBitacora="";
		referenceNumberBitacora=referenceNumberBitacora.toUpperCase();
		bitacoraList = (ArrayList<Bitacora>) bitacoraBean.findReferenceBitacora(referenceNumberBitacora);
		Collections.sort(bitacoraList, new BitacoraComparator());
		
		if(bitacoraList.size()>0){
			bitacoraList=addEmployeeBitacora(bitacoraList);
		}
		return "BITACORA";
	}
	
	public ArrayList<Bitacora> addEmployeeBitacora(ArrayList<Bitacora> listaBitacoras){
		int found = 0;
		String numEmp = "";
		Map<String, Employee> mapaEmpleados = new HashMap<String, Employee>();
		String nombre = "";

		for (Bitacora bit : listaBitacoras) {
			Employee emp = new Employee();
			found = 0;
			numEmp = bit.getUserNumber()==null?"":bit.getUserNumber();
			if (numEmp != null && !numEmp.isEmpty()) {
				numEmp = numEmp.replaceFirst("A", "");
				for (String numero : mapaEmpleados.keySet()) {
					if (numEmp.equalsIgnoreCase(numero)) {
						found = 1;
					}
				}
				if (found != 1) {// si no esta en el mapa
					emp = findEmployeeById(numEmp);
					if (emp != null) {
						mapaEmpleados.put(numEmp, emp);
					}
				}else{
					emp=mapaEmpleados.get(numEmp);
				}
				if (emp != null) {
					nombre = emp.getName() + " " + emp.getFirstName() + " " + emp.getSecondName();
					bit.setNombreEmpleado(nombre);
				} else {
					bit.setNombreEmpleado("n/a");
				}
			}
		}
		return listaBitacoras;
	}
	/**
	 * Metodo para crear el reporte en excel de la bitï¿½cora general
	 */
	public void createBitacoraReport(){
		Bitacora bitacora = new Bitacora();
		FacesContext fCtx = FacesContext.getCurrentInstance();
		String userNumber = BitacoraUtil.getUserNumberSession(fCtx);

		Calendar startDate = new Formatter().formatDate(this.startBitacoraDate);
		Calendar endDate = new Formatter().formatDate(this.endBitacoraDate);
		endDate.add(Calendar.DAY_OF_YEAR, 1);
		LayoutOperations layout=LayoutOperations.getInstance();
		
		bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.REPORT_BITACORA, userNumber);
		log.log(Level.INFO, "Inicio de Reporte Bitacora General");
		referenceNumberBitacora=referenceNumberBitacora.toUpperCase();
		layout.generarReporteBitacora(startDate, endDate, userNumberBitacora);
		log.log(Level.INFO, "Fin de Reporte Bitacora General");
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
	}
	
	public void createTpvPayrollDtoReport(){
		Bitacora bitacora=new Bitacora();
		FacesContext facesCtx=FacesContext.getCurrentInstance();
		String userNumber=BitacoraUtil.getUserNumberSession(facesCtx);
		LayoutOperations layout=LayoutOperations.getInstance();
		System.out.println("Inicio Reporte ALTAS");
		bitacora=BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.REPORT_TPV_PAYROLL,userNumber);
		layout.createTpvPayrollDTOReport();
		System.out.println("Fin - Reporte ALTAS");
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
	}
	

	public String getStartBitacoraDate() {
		return startBitacoraDate;
	}

	public void setStartBitacoraDate(String startBitacoraDate) {
		this.startBitacoraDate = startBitacoraDate;
	}

	public String getEndBitacoraDate() {
		return endBitacoraDate;
	}

	public void setEndBitacoraDate(String endBitacoraDate) {
		this.endBitacoraDate = endBitacoraDate;
	}

	public ArrayList<Bitacora> getBitacoraList() {
		return bitacoraList;
	}

	public void setBitacoraList(ArrayList<Bitacora> bitacoraList) {
		this.bitacoraList = bitacoraList;
	}
	
	public String getBitacoraUserNumber (){
		String userNumber = ApplicationConstants.EMPTY_STRING;
		if(!bitacoraList.isEmpty()){
			userNumber = bitacoraList.get(ApplicationConstants.INIT_CONT_CERO).getUserNumber();
		}
		
		return userNumber;
		
	}
	public String getBitacoraReference (){
		String reference = ApplicationConstants.EMPTY_STRING;
		if(!bitacoraList.isEmpty()){
			reference = bitacoraList.get(ApplicationConstants.INIT_CONT_CERO).getFolio();
		}
		
		return reference;
		
	}
	
	
	
	/**
	 * CARGA A LA LISTA LOS DATOS DE LA CELEBRACION
	 * @param list
	 */
	protected void loadToSaveCelebrationInfo( ArrayList<ContractAttribute> list ){
		addToList(list,AttrConstants.CELEBRATION_PLACE, getCelebrationplace());
        addToList(list,AttrConstants.CELEBRATION_STATE, getCelebrationstate());
        addToList(list,AttrConstants.CELEBRATION_DATE,getCelebrationdate()!=null?getCelebrationdate():"");
	}
	
	/**
	 * CARGA A LA LISTA LOS DATOS GENERALES DEL CLIENTE
	 * @param list
	 */
	protected void loadToSaveGeneralClientInfo( ArrayList<ContractAttribute> list){
		addToList(list,AttrConstants.CLIENT_SIC, getClient_sic());
        addToList(list,AttrConstants.CLIENT_AREACODE, getClient_areacode().toString());
        addToList(list,AttrConstants.CLIENT_PHONE, getClient_phone().toString());
        addToList(list,AttrConstants.CLIENT_PHONEEXT, getClient_phoneext().toString());
        addToList(list,AttrConstants.CLIENT_STREET, getClient_street());
        addToList(list,AttrConstants.CLIENT_NUMINT, getClient_numint());
        addToList(list,AttrConstants.CLIENT_NUMEXT, getClient_numext());
        addToList(list,AttrConstants.CLIENT_COLONY, getClient_colony());
        addToList(list,AttrConstants.CLIENT_ZIPCODE, getClient_zipcode().toString());
        addToList(list,AttrConstants.CLIENT_STATE,getClient_state());
        addToList(list,AttrConstants.CLIENT_CITY, getClient_city());
        addToList(list,AttrConstants.CLIENT_EMAIL, getClient_email());
        addToList(list,AttrConstants.CLIENT_FISCALNAME, getClient_fiscalname());
        addToList(list,AttrConstants.CLIENT_FISCALTYPE, getClient_fiscaltype() != null ? getClient_fiscaltype().toString() : "");
        addToList(list,AttrConstants.CLIENT_RFC, getClient_rfc());
        
	}
	
	
	/**
	 * CARGA A LA LISTA LOS DATOS PRINCIPALES DEL PRIMER  REPRESENTATE LEGAL O APODERADO
	 * @param list
	 */
	protected void loadToSaveLegalRepresentative1( ArrayList<ContractAttribute> list ){
		addToList(list,AttrConstants.LEGALREPRESENTATIVE_NAME_1, getLegalrepresentative_name_1());
        addToList(list,AttrConstants.LEGALREPRESENTATIVE_LASTNAME_1, getLegalrepresentative_lastname_1());
        addToList(list,AttrConstants.LEGALREPRESENTATIVE_MOTHERS_NAME_1, getLegalrepresentative_mothersln_1());
	}
	
	/**
	 * CARGA A LA LISTA LOS DATOS PRINCIPALES DEL SEGUNDO REPRESENTATE LEGAL O APODERADO
	 * @param list
	 */
	protected void loadToSaveLegalRepresentative2( ArrayList<ContractAttribute> list ){
		addToList(list,AttrConstants.LEGALREPRESENTATIVE_NAME_2, getLegalrepresentative_name_2());
	    addToList(list,AttrConstants.LEGALREPRESENTATIVE_LASTNAME_2, getLegalrepresentative_lastname_2());
	    addToList(list,AttrConstants.LEGALREPRESENTATIVE_MOTHERS_NAME_2, getLegalrepresentative_mothersln_2());
	}
	
	

	/**
	 * CARGA A LA LISTA LOS DATOS DE LA PRIMERA FIRMA ADICIONAL FACULTADA
	 * @param list
	 */
	protected void loadToSaveClientContact1( ArrayList<ContractAttribute> list ){
		addToList(list,AttrConstants.CLIENTCONTACT_NAME_1,getClientcontact_name1());
		addToList(list,AttrConstants.CLIENTCONTACT_LASTNAME_1,getClientcontact_lastname1());
		addToList(list,AttrConstants.CLIENTCONTACT_MOTHERSNAME_1,getClientcontact_mothersln1());
		addToList(list,AttrConstants.CLIENTCONTACT_POSITION_1,getClientcontact_position1());
	}
	
	/**
	 * CARGA A LA LISTA LOS DATOS DE LA SEGUNDA FIRMA ADICIONAL FACULTADA
	 * @param list
	 */
	protected void loadToSaveClientContact2( ArrayList<ContractAttribute> list ){
		addToList(list,AttrConstants.CLIENTCONTACT_NAME_2,getClientcontact_name2());
		addToList(list,AttrConstants.CLIENTCONTACT_LASTNAME_2,getClientcontact_lastname2());
		addToList(list,AttrConstants.CLIENTCONTACT_MOTHERSNAME_2,getClientcontact_mothersln2());
		addToList(list,AttrConstants.CLIENTCONTACT_POSITION_2,getClientcontact_position2());
	}
	
	/**
	 * CARGA A LA LISTA LOS DATOS DE LA TERCERA FIRMA ADICIONAL FACULTADA
	 * @param list
	 */
	protected void loadToSaveClientContact3( ArrayList<ContractAttribute> list ){
		addToList(list,AttrConstants.CLIENTCONTACT_NAME_3,getClientcontact_name3());
		addToList(list,AttrConstants.CLIENTCONTACT_LASTNAME_3,getClientcontact_lastname3());
		addToList(list,AttrConstants.CLIENTCONTACT_MOTHERSNAME_3,getClientcontact_mothersln3());
		addToList(list,AttrConstants.CLIENTCONTACT_POSITION_3,getClientcontact_position3());
		}
	
	/**
	 * CARGA A LA LISTA LOS DATOS DE LA CUARTA FIRMA ADICIONAL FACULTADA
	 * @param list
	 */
	protected void loadToSaveClientContact4( ArrayList<ContractAttribute> list ){
		addToList(list,AttrConstants.CLIENTCONTACT_NAME_4,getClientcontact_name4());
		addToList(list,AttrConstants.CLIENTCONTACT_LASTNAME_4,getClientcontact_lastname4());
		addToList(list,AttrConstants.CLIENTCONTACT_MOTHERSNAME_4,getClientcontact_mothersln4());
		addToList(list,AttrConstants.CLIENTCONTACT_POSITION_4,getClientcontact_position4());
	}
	
	/**
	 * CARGA A LA LISTA LOS DATOS FUNCIONARIO QUE COLOCO EL PRODUCTO
	 * @param list
	 */
	protected void loadToSaveOfficer( ArrayList<ContractAttribute> list ){
		addToList(list,AttrConstants.OFFICER_NAME, getOfficername());
        addToList(list,AttrConstants.OFFICER_LASTNAME, getOfficerlastname());
        addToList(list,AttrConstants.OFFICER_MOTHERSNAME, getOfficermothersln());
        addToList(list,AttrConstants.OFFICER_NUMBER, getOfficerempnumber());
        addToList(list,AttrConstants.OFFICER_POSITION, getOfficerposition());
	}
	
	/**
	 * CARGA A LA LISTA LOS DATOS DE EL FUNCIONARIO EBANKING
	 * @param list
	 */
	protected void loadToSaveOfficerEbanking( ArrayList<ContractAttribute> list ){
		addToList(list,AttrConstants.OFFICER_EBANKING_NAME, getOfficerebankingname());
		addToList(list,AttrConstants.OFFICER_EBANKING_LASTNAME, getOfficerebankinglastname());
		addToList(list,AttrConstants.OFFICER_EBANKING_MOTHERSNAME, getOfficerebankingmothersln());
		addToList(list,AttrConstants.OFFICER_EBANKING_NUMBER, getOfficerebankingempnumber());
		addToList(list,AttrConstants.OFFICER_EBANKING_POSITION, getOfficerebankingposition());
	}
	
	/**
	 * CARGA A LA LISTA LOS DATOS DE LA SUCURSAL
	 * @param list
	 */
	protected void loadToSaveBranch( ArrayList<ContractAttribute> list ){
		addToList(list,AttrConstants.CR_NUMBER, getCrnumber());
        addToList(list,AttrConstants.BRANCH_NAME, getBranchname());
        addToList(list,AttrConstants.BRANCH_STREET, getBranchstreet());
        addToList(list,AttrConstants.BRANCH_COLONY, getBranchcolony());
        addToList(list,AttrConstants.BRANCH_COUNTY, getBranchcounty());
        addToList(list,AttrConstants.BRANCH_CITY, getBranchcity());
        addToList(list,AttrConstants.BRANCH_STATE, getBranchstate());
        addToList(list,AttrConstants.BRANCH_PHONE, getBranchphone());
        addToList(list,AttrConstants.BRANCH_FAX, getBranchfax());
        addToList(list,AttrConstants.BANKING_SECTOR, getBankingsector());
	}
	
	/**
	 * CARGA A LA LISTA LOS DATOS DEL PRIMER REPRESENTANTE LEGAL O APODERADO BANORTE
	 * SIN NUMERO DE FIRMA
	 * @param list
	 */
	protected void loadToSaveOfficerRep1( ArrayList<ContractAttribute> list ){
		addToList(list,AttrConstants.OFFICER_REP_NAME_1, getOfficerrepname_1());
        addToList(list,AttrConstants.OFFICER_REP_LASTNAME_1, getOfficerreplastname_1());
        addToList(list,AttrConstants.OFFICER_REP_MOTHERSNAME_1, getOfficerrepmothersln_1());
        addToList(list,AttrConstants.OFFICER_REP_NUMBER_1, getOfficerrepempnumber_1());
        addToList(list,AttrConstants.OFFICER_REP_POSITION_1, getOfficerrepposition_1());
	}
	
	/**
	 * CARGA A LA LISTA LOS DATOS DEL SEGUNDO REPRESENTANTE LEGAL O APODERADO BANORTE
	 * SIN NUMERO DE FIRMA
	 * @param list
	 */
	protected void loadToSaveOfficerRep2( ArrayList<ContractAttribute> list ){
        addToList(list,AttrConstants.OFFICER_REP_NAME_2, getOfficerrepname_2());
        addToList(list,AttrConstants.OFFICER_REP_LASTNAME_2, getOfficerreplastname_2());
        addToList(list,AttrConstants.OFFICER_REP_MOTHERSNAME_2, getOfficerrepmothersln_2());
        addToList(list,AttrConstants.OFFICER_REP_NUMBER_2, getOfficerrepempnumber_2());
        addToList(list,AttrConstants.OFFICER_REP_POSITION_2, getOfficerrepposition_2());
	}
	
	
	/**
	 * CARGA A LA LISTA LA INFORMACION COMPLETA, GENERAL DEL CONTRATO
	 * @param list
	 */
	protected void loadToSaveInfoDatosComplete( ArrayList<ContractAttribute> list ){
		
		String celebrationCompletePlaceState 		= getCelebrationplace() + ", " + getCelebrationstate();
        String celebrationComplete 					= getCelebrationplace() + ", " + getCelebrationstate() + " A " + getCelebrationdate() ;
        String officernameComplete 					= getOfficername() +  " "+ getOfficerlastname() + " " + getOfficermothersln();
        String officerebankingnameComplete 			= getOfficerebankingname() +  " "+ getOfficerebankinglastname() + " " + getOfficerebankingmothersln();
        String branchadressComplete 				= getBranchstreet() + ", Col. "+ getBranchcolony()+ ", " + getBranchcity() + ", " + getBranchstate()+ "."  ;
        String branchnameComplete 					= getCrnumber() + " " + getBranchname() ;
        String officerrepnameComplete1				= getOfficerrepname_1() + " " + getOfficerreplastname_1() + " " + getOfficerrepmothersln_1() ;
        String officerrepnameComplete2 				= getOfficerrepname_2() + " " + getOfficerreplastname_2() + " " + getOfficerrepmothersln_2();
        String clientcontact_nameComplete1 			= getClientcontact_name1() + " " + getClientcontact_lastname1() + " "  + getClientcontact_mothersln1();
        String clientcontact_nameComplete2 			= getClientcontact_name2() + " " + getClientcontact_lastname2() + " "  + getClientcontact_mothersln2();
        String clientcontact_nameComplete3 			= getClientcontact_name3() + " " + getClientcontact_lastname3() + " "  + getClientcontact_mothersln3();
        String clientcontact_nameComplete4 			= getClientcontact_name4() + " " + getClientcontact_lastname4() + " "  + getClientcontact_mothersln4();
        String client_phoneComplete 				= "("+ (getClient_areacode()) + ") " + (getClient_phone()) + (getClient_phoneext()!=null?" Ext. " + getClient_phoneext().toString():"" );
        String client_faxComplete 					= "("+ (getClient_areacode())+ ") " + (getClient_fax()) + (getClient_faxext()!=null?" Ext. " + getClient_faxext().toString():"" );
        String client_stateComplete 				= getClient_city() + ", " +  getClient_state();
        String legalrepresentative_nameComplete1 	= getLegalrepresentative_name_1() + " " + getLegalrepresentative_lastname_1() + " " + getLegalrepresentative_mothersln_1();
        String legalrepresentative_nameComplete2 	= getLegalrepresentative_name_2() + " " + getLegalrepresentative_lastname_2() + " " + getLegalrepresentative_mothersln_2();
        String client_addressComplete 				= (getClient_street()) + " " + (getClient_numext()!=null? "Ext. " + getClient_numext():"") + " " + (getClient_numint()!=null? "Int. " + getClient_numint():"  " + getClient_colony());
        String officerebankingEspNameComplete 		= getOfficerebankingEspName()+  " "+ getOfficerebankingEspLastname() +  " "+ getOfficerebankingEspMothersln();
        String officerebankingTnameComplete  		= getOfficerebankingTname() +  " "+ getOfficerebankingTlastname() +  " "+ getOfficerebankingTmothersln();
        String branchDirectorNameComplete               = getBranchDirectorName() + " " + getBranchDirectorLastName() + " " + getBranchDirectorMothersLn();
        
        addToList(list,AttrConstants.COMPLETE_CELEBRATION_PLACE_STATE,celebrationCompletePlaceState);
        addToList(list,AttrConstants.COMPLETE_CELEBRATION,celebrationComplete);
        addToList(list,AttrConstants.COMPLETE_OFFICER_NAME,officernameComplete);
        addToList(list,AttrConstants.COMPLETE_OFFICER_EBANKING_NAME,officerebankingnameComplete);        
        addToList(list,AttrConstants.COMPLETE_BRANCH_ADRESS,branchadressComplete);
        addToList(list,AttrConstants.COMPLETE_BRANCH_NAME,branchnameComplete );
        addToList(list,AttrConstants.COMPLETE_OFFICER_REP_NAME_1, officerrepnameComplete1 );
        addToList(list,AttrConstants.COMPLETE_OFFICER_REP_NAME_2, officerrepnameComplete2 );
        addToList(list,AttrConstants.COMPLETE_CLIENT_CONTACT_NAME_1,clientcontact_nameComplete1);
        addToList(list,AttrConstants.COMPLETE_CLIENT_CONTACT_NAME_2,clientcontact_nameComplete2);
        addToList(list,AttrConstants.COMPLETE_CLIENT_CONTACT_NAME_3,clientcontact_nameComplete3);
        addToList(list,AttrConstants.COMPLETE_CLIENT_CONTACT_NAME_4,clientcontact_nameComplete4);
        addToList(list,AttrConstants.COMPLETE_CLIENT_PHONE,client_phoneComplete);
        addToList(list,AttrConstants.COMPLETE_CLIENT_FAX,client_faxComplete);
        addToList(list,AttrConstants.COMPLETE_CLIENT_STATE,client_stateComplete);
        addToList(list,AttrConstants.COMPLETE_REPRESENTATIVE_NAME_1,legalrepresentative_nameComplete1);
        addToList(list,AttrConstants.COMPLETE_REPRESENTATIVE_NAME_2,legalrepresentative_nameComplete2);
        addToList(list,AttrConstants.COMPLETE_CLIENT_ADDRESS,client_addressComplete);
        addToList(list,AttrConstants.COMPLETE_OFFICER_EBANKING_ESP_NAME,officerebankingEspNameComplete);
        addToList(list,AttrConstants.COMPLETE_OFFICER_EBANKING_T_NAME,officerebankingTnameComplete);
        addToList(list,AttrConstants.COMPLETE_BRANCH_DIRECTOR_NAME,branchDirectorNameComplete);
	}
	
	
	
	/**
	 * CARGA A LA LISTA LA INFORMACION COMPLETA, GENERAL DEL CONTRATO EN MANTENIMIENTOS
	 * @param list
	 */
	protected void loadToSaveInfoDatosCompleteMtto( ArrayList<ContractAttribute> list ){
		
        String officernameComplete 					= getOfficername() +  " "+ getOfficerlastname() + " " + getOfficermothersln();
        String officerebankingnameComplete 			= getOfficerebankingname() +  " "+ getOfficerebankinglastname() + " " + getOfficerebankingmothersln();
        String officerrepnameComplete1				= getOfficerrepname_1() + " " + getOfficerreplastname_1() + " " + getOfficerrepmothersln_1() ;
        String officerrepnameComplete2 				= getOfficerrepname_2() + " " + getOfficerreplastname_2() + " " + getOfficerrepmothersln_2();
        String legalrepresentative_nameComplete1 	= getLegalrepresentative_name_1() + " " + getLegalrepresentative_lastname_1() + " " + getLegalrepresentative_mothersln_1();
        String legalrepresentative_nameComplete2 	= getLegalrepresentative_name_2() + " " + getLegalrepresentative_lastname_2() + " " + getLegalrepresentative_mothersln_2();
        String branchDirectorNameComplete               = getBranchDirectorName() + " " + getBranchDirectorLastName() + " " + getBranchDirectorMothersLn();
        
        addToList(list,AttrConstants.COMPLETE_OFFICER_NAME,officernameComplete);
        addToList(list,AttrConstants.COMPLETE_OFFICER_EBANKING_NAME,officerebankingnameComplete);        
        addToList(list,AttrConstants.COMPLETE_OFFICER_REP_NAME_1, officerrepnameComplete1 );
        addToList(list,AttrConstants.COMPLETE_OFFICER_REP_NAME_2, officerrepnameComplete2 );
        addToList(list,AttrConstants.COMPLETE_REPRESENTATIVE_NAME_1,legalrepresentative_nameComplete1);
        addToList(list,AttrConstants.COMPLETE_REPRESENTATIVE_NAME_2,legalrepresentative_nameComplete2);
        addToList(list,AttrConstants.COMPLETE_BRANCH_DIRECTOR_NAME,branchDirectorNameComplete);
	}
	
	protected void loadToEditGeneralInfo(Map<String, String> map){
		
		//Datos de Celebracion
		this.setCelebrationplace(map.get( AttrConstants.CELEBRATION_PLACE));
		this.setCelebrationstate(map.get( AttrConstants.CELEBRATION_STATE));
		this.setCelebrationdate(map.get( AttrConstants.CELEBRATION_DATE ));
		
		//Datos del Contratante
		this.setClient_sic(map.get( AttrConstants.CLIENT_SIC));
		this.setClient_areacode(map.get(AttrConstants.CLIENT_AREACODE) != null
				&& map.get(AttrConstants.CLIENT_AREACODE).trim().length() > 0 ? Integer
				.parseInt(map.get(AttrConstants.CLIENT_AREACODE)) : 0);
		this.setClient_phone(map.get(AttrConstants.CLIENT_PHONE) != null
				&& map.get(AttrConstants.CLIENT_PHONE).trim().length() > 0 ? Integer
				.parseInt(map.get(AttrConstants.CLIENT_PHONE)) : 0);
		this.setClient_phoneext(map.get(AttrConstants.CLIENT_PHONEEXT) != null
				&& map.get(AttrConstants.CLIENT_PHONEEXT).trim().length() > 0 ? Integer
				.parseInt(map.get(AttrConstants.CLIENT_PHONEEXT)) : 0);
		this.setClient_fax(map.get(AttrConstants.CLIENT_FAX) != null
				&& map.get(AttrConstants.CLIENT_FAX).trim().length() > 0 ? Integer
				.parseInt(map.get(AttrConstants.CLIENT_FAX)) : 0);
		this.setClient_faxext(map.get(AttrConstants.CLIENT_FAXEXT) != null
				&& map.get(AttrConstants.CLIENT_FAXEXT).trim().length() > 0 ? Integer
				.parseInt(map.get(AttrConstants.CLIENT_FAXEXT)) : 0);
		this.setClient_street(map.get(AttrConstants.CLIENT_STREET));
		this.setClient_numext(map.get(AttrConstants.CLIENT_NUMEXT));
		this.setClient_numint(map.get(AttrConstants.CLIENT_NUMINT));
		this.setClient_colony(map.get( AttrConstants.CLIENT_COLONY));
		this.setClient_zipcode(map.get(AttrConstants.CLIENT_ZIPCODE) != null
				&& map.get(AttrConstants.CLIENT_ZIPCODE).trim().length() > 0 ? Integer
				.parseInt(map.get(AttrConstants.CLIENT_ZIPCODE)) : 0);
		this.setClient_state(map.get( AttrConstants.CLIENT_STATE));
		this.setClient_city(map.get( AttrConstants.CLIENT_CITY));
		this.setClient_email(map.get( AttrConstants.CLIENT_EMAIL));
		this.setClient_fiscalname(map.get(AttrConstants.CLIENT_FISCALNAME));
		this.setClient_constitutiondate(map.get(AttrConstants.CLIENT_CONSTITUTIONDATE));
		this.setClient_categorycode(map.get(AttrConstants.CLIENT_CATEGORYCODE));
		this.setClient_fiscaltype(map.get(AttrConstants.CLIENT_FISCALTYPE) != null
				&& map.get(AttrConstants.CLIENT_FISCALTYPE).trim().length() > 0 ? Integer
				.parseInt(map.get(AttrConstants.CLIENT_FISCALTYPE)): 0);
		this.setClient_rfc(map.get(AttrConstants.CLIENT_RFC));
		
		
		

		this.setComments(map.get( AttrConstants.COMMENTS));
		
	}
	
	/**
	 * Datos del Representante Legal o Apoderados 1
	 * @param map
	 */
	protected void loadToEditLegalrepresentative1(Map<String, String> map){
		this.setLegalrepresentative_name_1(map.get(AttrConstants.LEGALREPRESENTATIVE_NAME_1));
		this.setLegalrepresentative_lastname_1(map.get(AttrConstants.LEGALREPRESENTATIVE_LASTNAME_1));
		this.setLegalrepresentative_mothersln_1(map.get(AttrConstants.LEGALREPRESENTATIVE_MOTHERS_NAME_1));
		this.setLegalrepresentative_position_1(map.get(AttrConstants.LEGALREPRESENTATIVE_POSITION_1));
	}
	
	/**
	 * Datos del Representante Legal o Apoderados 2
	 * @param map
	 */
	protected void loadToEditLegalrepresentative2(Map<String, String> map){
		this.setLegalrepresentative_name_2(map.get(AttrConstants.LEGALREPRESENTATIVE_NAME_2));
		this.setLegalrepresentative_lastname_2(map.get(AttrConstants.LEGALREPRESENTATIVE_LASTNAME_2));
		this.setLegalrepresentative_mothersln_2(map.get(AttrConstants.LEGALREPRESENTATIVE_MOTHERS_NAME_2));
		this.setLegalrepresentative_position_2(map.get(AttrConstants.LEGALREPRESENTATIVE_POSITION_2));
	}
	
	/**
	 * Datos de la sucursal
	 * @param map
	 */
	protected void loadToEditBranchInfo(Map<String, String> map){
		this.setCrnumber(map.get( AttrConstants.CR_NUMBER));
		this.setBranchname(map.get( AttrConstants.BRANCH_NAME));
		this.setBranchstreet(map.get( AttrConstants.BRANCH_STREET));
		this.setBranchcolony(map.get( AttrConstants.BRANCH_COLONY));
		this.setBranchphone(map.get( AttrConstants.BRANCH_PHONE));
		this.setBranchfax(map.get( AttrConstants.BRANCH_FAX));
		this.setBankingsector(map.get( AttrConstants.BANKING_SECTOR));
		this.setBranchcity(map.get( AttrConstants.BRANCH_CITY));
		this.setBranchstate(map.get( AttrConstants.BRANCH_STATE));
	}
	
	/**
	 * Datos del funcionario Ebanking
	 * @param map
	 */
	protected void loadToEditEbankingInfo(Map<String, String> map){
		this.setOfficerebankingempnumber(map.get(AttrConstants.OFFICER_EBANKING_NUMBER));
		this.setOfficerebankingname(map.get(AttrConstants.OFFICER_EBANKING_NAME));
		this.setOfficerebankinglastname(map.get(AttrConstants.OFFICER_EBANKING_LASTNAME));
		this.setOfficerebankingmothersln(map.get(AttrConstants.OFFICER_EBANKING_MOTHERSNAME));
		this.setOfficerebankingposition(map.get(AttrConstants.OFFICER_EBANKING_POSITION));
	}
	
	/**
	 * Datos del funcionario que coloco el producto
	 * @param map
	 */
	protected void loadToEditOfficerInfo(Map<String, String> map){
		this.setOfficerempnumber(map.get( AttrConstants.OFFICER_NUMBER));
		this.setOfficername(map.get(AttrConstants.OFFICER_NAME));
		this.setOfficerlastname(map.get(AttrConstants.OFFICER_LASTNAME));
		this.setOfficermothersln(map.get(AttrConstants.OFFICER_MOTHERSNAME));
		this.setOfficerposition(map.get(AttrConstants.OFFICER_POSITION));
		
	}
	
	/**
	 * Datos del Representante Legal o Apoderado Banorte
	 * @param map
	 */
	protected void loadToEditOfficerRep1Info(Map<String, String> map){
		this.setOfficerrepempnumber_1(map.get( AttrConstants.OFFICER_REP_NUMBER_1));
		this.setOfficerrepname_1(map.get( AttrConstants.OFFICER_REP_NAME_1));
		this.setOfficerreplastname_1(map.get( AttrConstants.OFFICER_REP_LASTNAME_1));
		this.setOfficerrepmothersln_1(map.get( AttrConstants.OFFICER_REP_MOTHERSNAME_1));
		this.setOfficerrepposition_1(map.get( AttrConstants.OFFICER_REP_POSITION_1));
		
	}
	
	/**
	 * Datos del Representante Legal o Apoderado Banorte
	 * @param map
	 */
	protected void loadToEditOfficerRep2Info(Map<String, String> map){
		this.setOfficerrepempnumber_2(map.get( AttrConstants.OFFICER_REP_NUMBER_2));
		this.setOfficerrepname_2(map.get( AttrConstants.OFFICER_REP_NAME_2));
		this.setOfficerreplastname_2(map.get( AttrConstants.OFFICER_REP_LASTNAME_2));
		this.setOfficerrepmothersln_2(map.get( AttrConstants.OFFICER_REP_MOTHERSNAME_2));
		this.setOfficerrepposition_2(map.get( AttrConstants.OFFICER_REP_POSITION_2));
	}

	/**
	 * @return the validationEDO
	 */
	public boolean isValidationEDO() {
		return validationEDO;
	}

	/**
	 * @param validationEDO the validationEDO to set
	 */
	public void setValidationEDO(boolean validationEDO) {
		this.validationEDO = validationEDO;
	}

	/**
	 * @return the userNumberBitacora
	 */
	public String getUserNumberBitacora() {
		return userNumberBitacora;
	}

	/**
	 * @param userNumberBitacora the userNumberBitacora to set
	 */
	public void setUserNumberBitacora(String userNumberBitacora) {
		this.userNumberBitacora = userNumberBitacora;
	}

	/**
	 * @return the executiveBranchList
	 */
	public List<ExecutiveBranch> getExecutiveBranchList() {
		return executiveBranchList;
	}

	/**
	 * @param executiveBranchList the executiveBranchList to set
	 */
	public void setExecutiveBranchList(List<ExecutiveBranch> executiveBranchList) {
		this.executiveBranchList = executiveBranchList;
	}

	/**
	 * @return the executiveList
	 */
	public List<Executive> getExecutiveList() {
		return executiveList;
	}

	/**
	 * @param executiveList the executiveList to set
	 */
	public void setExecutiveList(List<Executive> executiveList) {
		this.executiveList = executiveList;
	}

	public Integer getProcess() {
		return process;
	}

	public void setProcess(Integer process) {
		this.process = process;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getOfficerTerritory() {
		return officerTerritory;
	}

	public void setOfficerTerritory(String officerTerritory) {
		this.officerTerritory = officerTerritory;
	}

	public String getOfficerRegion() {
		return officerRegion;
	}

	public void setOfficerRegion(String officerRegion) {
		this.officerRegion = officerRegion;
	}
	
	public String getBranchDirectorNumber() {
		return branchDirectorNumber;
	}

	public void setBranchDirectorNumber(String branchDirectorNumber) {
		this.branchDirectorNumber = branchDirectorNumber;
	}

	public String getBranchDirectorName() {
		return branchDirectorName;
	}

	public void setBranchDirectorName(String branchDirectorName) {
		this.branchDirectorName = branchDirectorName;
	}

	public String getBranchDirectorLastName() {
		return branchDirectorLastName;
	}

	public void setBranchDirectorLastName(String branchDirectorLastName) {
		this.branchDirectorLastName = branchDirectorLastName;
	}

	public String getBranchDirectorMothersLn() {
		return branchDirectorMothersLn;
	}

	public void setBranchDirectorMothersLn(String branchDirectorMothersLn) {
		this.branchDirectorMothersLn = branchDirectorMothersLn;
	}

	public String getBranchDirectorposition() {
		return branchDirectorposition;
	}

	public void setBranchDirectorposition(String branchDirectorposition) {
		this.branchDirectorposition = branchDirectorposition;
	}

	public String getReferenceNumberBitacora() {
		return referenceNumberBitacora;
	}

	public void setReferenceNumberBitacora(String referenceNumberBitacora) {
		this.referenceNumberBitacora = referenceNumberBitacora;
	}

	public boolean isProcesoEnvio() {
		return procesoEnvio;
	}

	public void setProcesoEnvio(boolean procesoEnvio) {
		this.procesoEnvio = procesoEnvio;
	}

	public boolean isProcesoLectura() {
		return procesoLectura;
	}

	public void setProcesoLectura(boolean procesoLectura) {
		this.procesoLectura = procesoLectura;
	}

	public boolean isProcesoCargaCuentasMujerPyME() {
		return procesoCargaCuentasMujerPyME;
	}

	public void setProcesoCargaCuentasMujerPyME(boolean procesoCargaCuentasMujerPyME) {
		this.procesoCargaCuentasMujerPyME = procesoCargaCuentasMujerPyME;
	}

	public String getDateReportFilter() {
		return dateReportFilter;
	}

	public void setDateReportFilter(String dateReportFilter) {
		this.dateReportFilter = dateReportFilter;
	}

	public String getStatusReportFilter() {
		return statusReportFilter;
	}

	public void setStatusReportFilter(String statusReportFilter) {
		this.statusReportFilter = statusReportFilter;
	}
	
	public String getNombreUsuarioBitacora() {
		return nombreUsuarioBitacora;
	}

	public void setNombreUsuarioBitacora(String nombreUsuarioBitacora) {
		this.nombreUsuarioBitacora = nombreUsuarioBitacora;
	}

	public String getRed() {
		return red;
	}

	public void setRed(String red) {
		this.red = red;
	}

	
	//Metodo prueba para perfiles ejec.xhtml commandlink executiveList
	public String getUserProfile(){
		String profile="otro"; 
		return profile;
	}
	
	public String getStatusGarantiaLiquida() {
		return statusGarantiaLiquida;
	}

	public void setStatusGarantiaLiquida(String statusGarantiaLiquida) {
		this.statusGarantiaLiquida = statusGarantiaLiquida;
	}

	public void downloadFile(){
		try{
			FacesContext fCtx = FacesContext.getCurrentInstance();
			Map<String, String> params = fCtx.getExternalContext().getRequestParameterMap();
			String archivo = params.get("descarga").toString();
			String fileName = "";
			if(archivo.equalsIgnoreCase("sucursal")){
				fileName="LayoutSucursalesEjecutivos";
			}else if(archivo.equalsIgnoreCase("ejecutivo")){
				fileName="LayoutDirectorioEjecutivos";
			}else if(archivo.equalsIgnoreCase("comision")){
				fileName="LayoutComisionesTPV";
			}
            HttpServletResponse response =((HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse());
            HttpServletRequest request =((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest());
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            BufferedInputStream bIn = new BufferedInputStream(request.getSession().getServletContext().getResourceAsStream("/WEB-INF/docs/"+fileName+".xls"));
            
            OutputStream out = response.getOutputStream();            
            byte[] buffer = new byte[1024];
            int len = 	bIn.read(buffer);
            while (len != -1) {
                out.write(buffer, 0, len);
                len = bIn.read(buffer);
            }
            out.flush();
            out.close();
            //bIn.close();//para quitar vulnerabilidad
            FacesContext.getCurrentInstance().responseComplete();
      } catch(Exception e) {
            // Log the error
      }
	}
	
		/**
	     * Add attributes to a list only if the value is not null or empty
	     * @param list where the "contractAttribute" will be added to
	     * @param attribute to be added
	     * @param value of the attribute
	     */
	    protected void addToList(List<ContractAttribute> list,String attribute,String value){
	     if(attribute==null || attribute.isEmpty()){
	      return;
	     }
	     if(value==null || value.isEmpty() || value.trim().isEmpty() ){
	      return;
	     }
	     ContractAttribute contractAttribute=getContractAttribute(attribute, value);
	     if(contractAttribute!=null){
	      list.add(contractAttribute);
	     }
	    }
	
	
	/**
	 * Mï¿½todo PRUEBA para obtener la lista de atributos del contrato (sin guardar nulos o vacï¿½os)
	 * @param contractMB (el bean de donde se leerï¿½n las variables/propiedades)
	 * @return List<ContractAttribute> del contrato
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 * @author gina
	 */
	public <X extends ContractAbstractMB> ArrayList<ContractAttribute> obtainContractAttributeList(X contractMB, List<String> attributesToAdd) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException{
		ArrayList<ContractAttribute> contractAttributeList = new ArrayList<ContractAttribute>();
		Map<String, Object> objectMap = new HashMap<String, Object>();
		objectMap=(new ContractUtil().obtainObjectMap(contractMB, false,attributesToAdd));
		if(objectMap.size()>0 && objectMap!=null){
			for(String property:objectMap.keySet()){
				Attribute attribute=null;
				try{
				attribute=attributeBean.findByFieldname(property);
				}catch(Exception e){
				}
				if(attribute!=null){
					Object val=objectMap.get(property);
					String valor=(val!=null && !val.toString().trim().isEmpty())?val.toString():null;
					if(valor!=null){
						ContractAttribute contractAttribute=new ContractAttribute();
						contractAttribute.setValue(valor);
						contractAttribute.setAttribute(attribute);	
						contractAttributeList.add(contractAttribute);
					}
				}
		   }
		}
		return contractAttributeList;
	}
	
	public void reporteNomina(){
		ContractUtil x = new ContractUtil();
		try {
			x.completarReporteNomina();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getNominaMonthlyReports(){
		LayoutOperations layout = LayoutOperations.getInstance();
		Bitacora bitacora = new Bitacora();
        FacesContext fCtx = FacesContext.getCurrentInstance();
        String userNumber = BitacoraUtil.getUserNumberSession(fCtx);
        
		System.out.println("..:: Inicio de la ejecucion Reporte Nomina Mensual ::..");
		bitacora = BitacoraUtil.getInstance().saveBitacoraReporteStart(BitacoraType.REPORT_NOMINA_MENSUAL, userNumber);
		
		Calendar startReportDate 	= new Formatter().formatDate(this.startReportDate);
		Calendar endReportDate 		= new Formatter().formatDate(this.endReportDate);
		
		//endReportDate.add(Calendar.DAY_OF_YEAR, 1); //Para agregar un dï¿½a a la fecha final del reporte

		layout.getNominaMonthlyReportDTO(this.startReportDate, this.endReportDate); //para el DTO
		BitacoraUtil.getInstance().saveBitacoraReporteEnd(bitacora);
		System.out.println("..:: Fin de la ejecucion Reporte Nomina Mensual ::..");
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

	public boolean getIsSeleccionClienteNuevo() {
		return isSeleccionClienteNuevo;
	}

	public void setIsSeleccionClienteNuevo(boolean isSeleccionClienteNuevo) {
		this.isSeleccionClienteNuevo = isSeleccionClienteNuevo;
	}
}
