package com.banorte.contract.web;

import com.banorte.contract.business.CategoriesRemote;
import com.banorte.contract.business.StatusRemote;
import com.banorte.contract.model.Attribute;
import com.banorte.contract.model.AttributeOption;
import com.banorte.contract.model.Branch;
import com.banorte.contract.model.Categories;
import com.banorte.contract.util.pdf.PdfTemplateBinding;
import java.util.Collection;
import javax.faces.model.SelectItem;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.DMFiltroEmisoras;
import com.banorte.contract.model.Employee;
import com.banorte.contract.model.Template;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.AttrConstants;
import com.banorte.contract.util.EncryptBd;
import com.banorte.contract.util.pdf.PdfTemplateBindingContract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * 
 * @author Administrator
 ************************************************************************************* 
 *         ModId 	Fecha 		ModificadoPor 	Descripción de la Modificación 
 *         001		26/Nov/2010 Joseles Sánchez Se agregó la propiedad:automaticAffiliation 
 *         002 		27/Ene/2011 Joseles Sánchez Se modificaron las propiedades para corregir el funcionamiento de
 *         										payrollType
 * 
 * 
 * 
 *************************************************************************************/
public class ContractPayrollMB extends ContractAbstractMB {

	private static Logger log = Logger.getLogger(ContractPayrollMB.class.getName());
	private String payrolltype;
	private SelectItem[] payrolltypeArray;
	private String bemnumber;
	private String perioddispersion;
	private SelectItem[] perioddispersionArray;
	private String daystankpayroll;
	private String quantemployees;
	private String avsalarybyemployee;
	private String avstaffturnover;
	private String packagetype;
	private SelectItem[] packagetypeArray;
	private String accountnumber;
	private String commtraddispersal;
	private String commdispersalanotherbank;
	private String commonlinedispersal;
	private String commfiletransmition;
	private String charge;
	private SelectItem[] chargeArray;
	private String payrollcomments;
	private PdfTemplateBindingContract pdfTemplateBinding;
	private EncryptBd encrypt;
	private CategoriesRemote categoryPayrollBean;
	private SelectItem[] categoriesArray;
	private String autoRegister;
	
    private String NAME_TEMPLATE ="nameTemplate";
    private String PATHTEMPLATE ="pathTemplate";

	// private String disableBemNumber = "true";
    
    private String bancaSegmentoNomina;
    private String folioValidacionNomina;
    private String esFolioValido;
    private Boolean isValidarFolio=true;
    protected StatusRemote statusRemote;
    
    private Boolean repetirValidarFolio = false;
    private Boolean orderFormLoaded = false;
    

	public ContractPayrollMB() {
		super();
		encrypt = new EncryptBd();
		setStatusContract(statusBean.findById(new Integer(1))); // Status Nuevo
		// = 1

		if (categoryPayrollBean == null) {
			categoryPayrollBean = (CategoriesRemote) EjbInstanceManager.getEJB(CategoriesRemote.class);
		}
		if(statusRemote == null){
			statusRemote = (StatusRemote) EjbInstanceManager.getEJB(StatusRemote.class);
		}

	}

	@Override
	public boolean savePartialContract() {
		Contract contract = getContract();
		ArrayList<ContractAttribute> list = new ArrayList<ContractAttribute>();

		addToList(list,"payrolltype", getPayrolltype());
		addToList(list,"bemnumber", getBemnumber());
		addToList(list,"perioddispersion", getPerioddispersion());
		addToList(list,"daystankpayroll", getDaystankpayroll());
		addToList(list,"quantemployees", getQuantemployees());
		addToList(list,"avsalarybyemployee", getAvsalarybyemployee());
		addToList(list,"avstaffturnover", getAvstaffturnover());
		addToList(list,"packagetype", getPackagetype());
		addToList(list,"accountnumber", getAccountnumberEncrypt());
		//Alta emisoras con calidad
		addToList(list,"bancaSegmentoNomina", getBancaSegmentoNomina());
		addToList(list,"folioValidacionNomina", getFolioValidacionNomina());
		// ModId:001
		addToList(list,"autoregister", getAutoRegister());
		addToList(list,"commtraddispersal",getCommtraddispersal());
		addToList(list,"commdispersalanotherbank", getCommdispersalanotherbank());
		addToList(list,"commonlinedispersal", getCommonlinedispersal());
		addToList(list,"commfiletransmition", getCommfiletransmition());
		addToList(list,"charge", getCharge());
		addToList(list,"payrollcomments", getPayrollcomments());
		addToList(list,"celebrationplace", getCelebrationplace());
		addToList(list,"celebrationstate", getCelebrationstate());
		addToList(list,"celebrationdate", getCelebrationdate());
		addToList(list,"client_merchantname", getClient_merchantname());
		addToList(list,"client_sic", getClient_sic());
		addToList(list,"client_areacode", getClient_areacode() != null ? getClient_areacode().toString() : "");
		addToList(list,"client_phone", getClient_phone() != null ? getClient_phone().toString() : "");
		addToList(list,"client_phoneext", getClient_phoneext() != null ? getClient_phoneext().toString() : "");
		addToList(list,"client_fax", getClient_fax() != null ? getClient_fax().toString() : "");
		addToList(list,"client_faxext", getClient_faxext() != null ? getClient_faxext().toString() : "");
		addToList(list,"client_street", getClient_street());
		addToList(list,"client_numint", getClient_numint());
		addToList(list,"client_numext", getClient_numext());
		addToList(list,"client_colony", getClient_colony());
		addToList(list,"client_county", getClient_county());
		addToList(list,"client_city", getClient_city());
		addToList(list,"client_state", getClient_state());
		addToList(list,"client_zipcode", getClient_zipcode() != null ? getClient_zipcode().toString(): "");
		addToList(list,"client_fiscaltype", getClient_fiscaltype().toString());
		addToList(list,"client_rfc", getClient_rfc());
		addToList(list,"client_email", getClient_email());
		addToList(list,"client_fiscalname",getClient_fiscalname());
		addToList(list,"client_constitutiondate",getClient_constitutiondate() != null ? getClient_constitutiondate().toString(): "");
		addToList(list,"client_categorycode",getClient_categorycode() != null ? getClient_categorycode().toString() : "");
		addToList(list,"legalrepresentative_name_1", getLegalrepresentative_name_1());
		addToList(list,"legalrepresentative_lastname_1", getLegalrepresentative_lastname_1());
		addToList(list,"legalrepresentative_mothersln_1", getLegalrepresentative_mothersln_1());
		addToList(list,"legalrepresentative_position_1", getLegalrepresentative_position_1());
		addToList(list,"legalrepresentative_name_2", getLegalrepresentative_name_2());
		addToList(list,"legalrepresentative_lastname_2", getLegalrepresentative_lastname_2());
		addToList(list,"legalrepresentative_mothersln_2", getLegalrepresentative_mothersln_2());
		addToList(list,"legalrepresentative_position_2", getLegalrepresentative_position_2());
		addToList(list,"clientcontact_name1",getClientcontact_name1());
		addToList(list,"clientcontact_lastname1",getClientcontact_lastname1());
		addToList(list,"clientcontact_mothersln1",getClientcontact_mothersln1());
		addToList(list,"clientcontact_position1",getClientcontact_position1());
		addToList(list,"clientcontact_name2",getClientcontact_name2());
		addToList(list,"clientcontact_lastname2",getClientcontact_lastname2());
		addToList(list,"clientcontact_mothersln2",getClientcontact_mothersln2());
		addToList(list,"clientcontact_position2",getClientcontact_position2());
		addToList(list,"clientcontact_name3",getClientcontact_name3());
		addToList(list,"clientcontact_lastname3",getClientcontact_lastname3());
		addToList(list,"clientcontact_mothersln3",getClientcontact_mothersln3());
		addToList(list,"clientcontact_position3",getClientcontact_position3());
		addToList(list,"clientcontact_name4",getClientcontact_name4());
		addToList(list,"clientcontact_lastname4",getClientcontact_lastname4());
		addToList(list,"clientcontact_mothersln4",getClientcontact_mothersln4());
		addToList(list,"clientcontact_position4",getClientcontact_position4());
		addToList(list,"clientcontact_name5",getClientcontact_name5());
		addToList(list,"clientcontact_lastname5",getClientcontact_lastname5());
		addToList(list,"clientcontact_mothersln5",getClientcontact_mothersln5());
		addToList(list,"clientcontact_position5",getClientcontact_position5());
		addToList(list,"clientcontact_name6",getClientcontact_name6());
		addToList(list,"clientcontact_lastname6", getClientcontact_lastname6());
		addToList(list,"clientcontact_mothersln6", getClientcontact_mothersln6());
		addToList(list,"clientcontact_position6", getClientcontact_position6());
		addToList(list,"clientcontact_name7", getClientcontact_name7());
		addToList(list,"clientcontact_lastname7", getClientcontact_lastname7());
		addToList(list,"clientcontact_mothersln7", getClientcontact_mothersln7());
		addToList(list,"clientcontact_position7", getClientcontact_position7());
		addToList(list,"officername", getOfficername());
		addToList(list,"officerlastname", getOfficerlastname());
		addToList(list,"officermothersln", getOfficermothersln());
		addToList(list,"officerempnumber", getOfficerempnumber());
		addToList(list,"officerposition", getOfficerposition());
		addToList(list,"officerebankingname", getOfficerebankingname());
		addToList(list,"officerebankinglastname", getOfficerebankinglastname());
		addToList(list,"officerebankingmothersln", getOfficerebankingmothersln());
		addToList(list,"officerebankingempnumber", getOfficerebankingempnumber());
		addToList(list,"crnumber", getCrnumber());
		addToList(list,"branchname", getBranchname());
		addToList(list,"branchstreet", getBranchstreet());
		addToList(list,"branchcolony", getBranchcolony());
		addToList(list,"branchcounty", getBranchcounty());
		addToList(list,"branchcity", getBranchcity());
		addToList(list,"branchstate", getBranchstate());
		addToList(list,"branchphone", getBranchphone());
		addToList(list,"branchfax", getBranchfax());
		addToList(list,"bankingsector", getBankingsector());
		addToList(list,"officerrepname_1", getOfficerrepname_1());
		addToList(list,"officerreplastname_1", getOfficerreplastname_1());
		addToList(list,"officerrepmothersln_1", getOfficerrepmothersln_1());
		addToList(list,"officerrepempnumber_1", getOfficerrepempnumber_1());
		addToList(list,"officerrepposition_1", getOfficerrepposition_1());
		addToList(list,"officerrepname_2", getOfficerrepname_2());
		addToList(list,"officerreplastname_2", getOfficerreplastname_2());
		addToList(list,"officerrepmothersln_2", getOfficerrepmothersln_2());
		addToList(list,"officerrepempnumber_2", getOfficerrepempnumber_2());
		addToList(list,"officerrepposition_2", getOfficerrepposition_2());
		addToList(list,"celebrationComplete", getCelebrationplace() + ", " + getCelebrationstate());
		addToList(list,"officernameComplete", getOfficername() + " " + getOfficerlastname() + " " + getOfficermothersln());
		addToList(list,"officerebankingnameComplete", getOfficerebankingname() + " " + getOfficerebankinglastname() + " " + getOfficerebankingmothersln());
		addToList(list,"branchadressComplete", getBranchstreet() + ", Col. " + getBranchcolony() + ", " + getBranchcity() + ", " + getBranchstate() + ".");
		addToList(list,"officerrepnameComplete1", getOfficerrepname_1() + " " + getOfficerreplastname_1() + " " + getOfficerrepmothersln_1());
		addToList(list,"officerrepnameComplete2", getOfficerrepname_2() + " " + getOfficerreplastname_2() + " " + getOfficerrepmothersln_2());
		addToList(list,"client_phoneComplete", (getClient_areacode() != null ? getClient_areacode().toString() : "") + (getClient_phone() != null ? getClient_phone().toString() : "")+ (getClient_phoneext() != null ? " Ext. "+ getClient_phoneext().toString() : ""));
		addToList(list,"client_stateComplete", getClient_city() + ", " + getClient_state());
		addToList(list,"legalrepresentative_nameComplete1", getLegalrepresentative_name_1() + " " + getLegalrepresentative_lastname_1() + " " + getLegalrepresentative_mothersln_1());
		addToList(list,"legalrepresentative_nameComplete2", getLegalrepresentative_name_2() + " " + getLegalrepresentative_lastname_2() + " " + getLegalrepresentative_mothersln_2());
		addToList(list,"branchnameComplete", getCrnumber() + " "+ getBranchname());
		addToList(list,"client_faxComplete",(getClient_areacode() != null ? getClient_areacode().toString(): "")+ (getClient_fax() != null ? getClient_fax().toString(): "")+ (getClient_faxext() != null ? " Ext. "+ getClient_faxext().toString() : ""));
		addToList(list,"client_addressComplete", getClient_street() + " "+ (getClient_numext() != null ? "Ext."+ getClient_numext() : "")+ " "+ (getClient_numint() != null ? "Int."+ getClient_numint() : ""));
		addToList(list,"clientcontact_nameComplete1", getClientcontact_name1() + " " + getClientcontact_lastname1() + " " + getClientcontact_mothersln1());
		addToList(list,"clientcontact_nameComplete2", getClientcontact_name2() + " " + getClientcontact_lastname2() + " " + getClientcontact_mothersln2());
		addToList(list,"clientcontact_nameComplete3", getClientcontact_name3() + " " + getClientcontact_lastname3() + " " + getClientcontact_mothersln3());
		addToList(list,"clientcontact_nameComplete4", getClientcontact_name4() + " " + getClientcontact_lastname4() + " " + getClientcontact_mothersln4());
		addToList(list,"clientcontact_nameComplete5", getClientcontact_name5() + " " + getClientcontact_lastname5() + " " + getClientcontact_mothersln5());
		addToList(list,"clientcontact_nameComplete6", getClientcontact_name6() + " " + getClientcontact_lastname6() + " " + getClientcontact_mothersln6());
		addToList(list,"clientcontact_nameComplete7", getClientcontact_name7() + " " + getClientcontact_lastname7() + " " + getClientcontact_mothersln7());
		addToList(list,"commdispersalotheracc",ApplicationConstants.COMMDISPERSALOTHERACC);
		addToList(list,"commexpeditioncard",ApplicationConstants.COMMEXPEDITIONCARD);
		addToList(list,"commreplacementcard",ApplicationConstants.COMMREPLACEMENTCARD);
		addToList(list,"commreplacementaddcard",ApplicationConstants.COMMREPLACEMENTADDCARD);
		addToList(list,"numtxdifferentatm",ApplicationConstants.NUMTXDIFFERENTATM);
		addToList(list,"numaddbalanceotherbank",ApplicationConstants.NUMADDBALANCEOTHERBANK);
		addToList(list,"numfailotherbank",ApplicationConstants.NUMFAILOTHERBANK);
		addToList(list,"commadddispositionotherbank",ApplicationConstants.COMMADDDISPOSITIONOTHERBANK);
		addToList(list,"commaddbalanceotherbank",ApplicationConstants.COMMADDBALANCEOTHERBANK);
		addToList(list,"commaddrejectotherbank",ApplicationConstants.COMMADDREJECTOTHERBANK);
		addToList(list,"avmonthlybalance",ApplicationConstants.AVMONTHLYBALANCE);
		addToList(list,"contract_reference", this.getContract().getReference());
		addToList(list,"comments",this.getComments().length() < 250 ? this.getComments() : this.getComments().substring(0, 249));
		addToList(list,"operations_comment", this.getComments().length() < 250 ? this.getComments() : this.getComments().substring(0, 249));
		addToList(list,"payrollemitter", " ");

		contract.setContractAttributeCollection(list);
		contractBean.update(contract);
		pdfTemplateBinding = new PdfTemplateBindingContract();
		pdfTemplateBinding.setContract(contract);

		return true;
	}

	// Accion para editar
	public void setEditForm() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		Map<String, String> params = fCtx.getExternalContext()
				.getRequestParameterMap();
		Integer idContract = Integer.parseInt(params.get("contractId"));
		String PAYROLL_FILLS[] = { "client_constitutiondate", "payrolltype",
				"bemnumber", "perioddispersion", "daystankpayroll",
				"quantemployees", "avsalarybyemployee", "packagetype",
				"avstaffturnover", "accountnumber", "commtraddispersal",
				"commdispersalanotherbank", "commonlinedispersal",
				"commfiletransmition", "charge", "autoregister",
				"bancaSegmentoNomina", "folioValidacionNomina"//Alta emisoras con calidad
				};

		Contract contract_ = contractBean.findById(idContract);
		if (contract_.getContractAttributeCollection() != null) {
			Map<String, String> map = this.getContractAttributeFills(contract_,
					PAYROLL_FILLS);

			// ModId:002. Se estaba mapeando con un atributo diferente
			// this.setPayrolltype(map.get("celebrationplace"));
			this.setPayrolltype(map.get("payrolltype"));

			this.setBemnumber(map.get("bemnumber"));
			this.setPerioddispersion(map.get("perioddispersion"));
			this.setDaystankpayroll(map.get("daystankpayroll"));
			this.setQuantemployees(map.get("quantemployees"));
			this.setAvsalarybyemployee(map.get("avsalarybyemployee"));
			this.setPackagetype(map.get("packagetype"));
			this.setAvstaffturnover(map.get("avstaffturnover"));
			this.setAccountnumberDecrypt(map.get("accountnumber"));

			// ModID:001
			this.setAutoRegister(map.get("autoregister") != null ? map
					.get("autoregister") : "false");

			this.setCommtraddispersal(map.get("commtraddispersal"));
			this.setCommdispersalanotherbank(map
					.get("commdispersalanotherbank"));
			this.setCommonlinedispersal(map.get("commonlinedispersal"));
			this.setCommfiletransmition(map.get("commfiletransmition"));
			this.setCharge(map.get("charge"));

			this.setCelebrationplace(map.get( AttrConstants.CELEBRATION_PLACE));
			this.setCelebrationstate(map.get( AttrConstants.CELEBRATION_STATE));
			this.setCelebrationdate(map.get( AttrConstants.CELEBRATION_DATE ));
			
			this.setClient_categorycode(map.get("client_categorycode"));
			this.setComments(map.get("comments"));
			this.setCrnumber(map.get("crnumber"));
			this.setBranchname(map.get("branchname"));
			this.setBranchstreet(map.get("branchstreet"));
			this.setBranchcolony(map.get("branchcolony"));
			this.setBranchphone(map.get("branchphone"));
			this.setBranchfax(map.get("branchfax"));
			this.setBankingsector(map.get("bankingsector"));
			this.setBranchcity(map.get("branchcity"));
			this.setBranchstate(map.get("branchstate"));
			this.setOfficername(map.get("officername"));
			this.setOfficerlastname(map.get("officerlastname"));
			this.setOfficermothersln(map.get("officermothersln"));
			this.setOfficerempnumber(map.get("officerempnumber"));
			this.setOfficerposition(map.get("officerposition"));
			this.setOfficerebankingname(map.get("officerebankingname"));
			this.setOfficerebankinglastname(map.get("officerebankinglastname"));
			this.setOfficerebankingmothersln(map
					.get("officerebankingmothersln"));
			this.setOfficerebankingempnumber(map
					.get("officerebankingempnumber"));
			this.setOfficerrepname_1(map.get("officerrepname_1"));
			this.setOfficerreplastname_1(map.get("officerreplastname_1"));
			this.setOfficerrepmothersln_1(map.get("officerrepmothersln_1"));
			this.setOfficerrepempnumber_1(map.get("officerrepempnumber_1"));
			this.setOfficerrepposition_1(map.get("officerrepposition_1"));
			this.setOfficerrepname_2(map.get("officerrepname_2"));
			this.setOfficerreplastname_2(map.get("officerreplastname_2"));
			this.setOfficerrepmothersln_2(map.get("officerrepmothersln_2"));
			this.setOfficerrepempnumber_2(map.get("officerrepempnumber_2"));
			this.setOfficerrepposition_2(map.get("officerrepposition_2"));

			this.setClient_constitutiondate(map.get("client_constitutiondate"));
			this.setClient_areacode(map.get("client_areacode") != null
					&& map.get("client_areacode").trim().length() > 0 ? Integer
					.parseInt(map.get("client_areacode")) : 0);
			this.setClient_state(map.get("client_state"));
			this.setClient_city(map.get("client_city"));
			this.setClient_colony(map.get("client_colony"));
			this.setClient_county(map.get("client_county"));
			this.setClient_email(map.get("client_email"));
			this.setClient_faxext(map.get("client_faxext") != null
					&& map.get("client_faxext").trim().length() > 0 ? Integer
					.parseInt(map.get("client_faxext")) : 0);
			this.setClient_fax(map.get("client_fax") != null
					&& map.get("client_fax").trim().length() > 0 ? Integer
					.parseInt(map.get("client_fax")) : 0);
			this.setClient_fiscalname(map.get("client_fiscalname"));
			this
					.setClient_fiscaltype(map.get("client_fiscaltype") != null
							&& map.get("client_fiscaltype").trim().length() > 0 ? Integer
							.parseInt(map.get("client_fiscaltype"))
							: 0);
			this.setClient_numext(map.get("client_numext"));
			this.setClient_numint(map.get("client_numint"));
			this.setClient_phone(map.get("client_phone") != null
					&& map.get("client_phone").trim().length() > 0 ? Integer
					.parseInt(map.get("client_phone")) : 0);
			this.setClient_phoneext(map.get("client_phoneext") != null
					&& map.get("client_phoneext").trim().length() > 0 ? Integer
					.parseInt(map.get("client_phoneext")) : 0);
			this.setClient_rfc(map.get("client_rfc"));
			this.setClient_street(map.get("client_street"));
			this.setClient_zipcode(map.get("client_zipcode") != null
					&& map.get("client_zipcode").trim().length() > 0 ? Integer
					.parseInt(map.get("client_zipcode")) : 0);

			this.setLegalrepresentative_lastname_1(map
					.get("legalrepresentative_lastname_1"));
			this.setLegalrepresentative_lastname_2(map
					.get("legalrepresentative_lastname_2"));
			this.setLegalrepresentative_mothersln_1(map
					.get("legalrepresentative_mothersln_1"));
			this.setLegalrepresentative_mothersln_2(map
					.get("legalrepresentative_mothersln_2"));
			this.setLegalrepresentative_name_1(map
					.get("legalrepresentative_name_1"));
			this.setLegalrepresentative_name_2(map
					.get("legalrepresentative_name_2"));
			this.setLegalrepresentative_position_1(map
					.get("legalrepresentative_position_1"));
			this.setLegalrepresentative_position_2(map
					.get("legalrepresentative_position_2"));

			this.setClientcontact_lastname1(map.get("clientcontact_lastname1"));
			this.setClientcontact_mothersln1(map
					.get("clientcontact_mothersln1"));
			this.setClientcontact_name1(map.get("clientcontact_name1"));
			this.setClientcontact_position1(map.get("clientcontact_position1"));

			this.setClientcontact_lastname2(map.get("clientcontact_lastname2"));
			this.setClientcontact_mothersln2(map
					.get("clientcontact_mothersln2"));
			this.setClientcontact_name2(map.get("clientcontact_name2"));
			this.setClientcontact_position2(map.get("clientcontact_position2"));

			this.setClientcontact_lastname3(map.get("clientcontact_lastname3"));
			this.setClientcontact_mothersln3(map
					.get("clientcontact_mothersln3"));
			this.setClientcontact_name3(map.get("clientcontact_name3"));
			this.setClientcontact_position3(map.get("clientcontact_position3"));

			this.setClientcontact_lastname4(map.get("clientcontact_lastname4"));
			this.setClientcontact_mothersln4(map
					.get("clientcontact_mothersln4"));
			this.setClientcontact_name4(map.get("clientcontact_name4"));
			this.setClientcontact_position4(map.get("clientcontact_position4"));

			this.setClientcontact_lastname5(map.get("clientcontact_lastname5"));
			this.setClientcontact_mothersln5(map
					.get("clientcontact_mothersln5"));
			this.setClientcontact_name5(map.get("clientcontact_name5"));
			this.setClientcontact_position5(map.get("clientcontact_position5"));

			this.setClientcontact_lastname6(map.get("clientcontact_lastname6"));
			this.setClientcontact_mothersln6(map
					.get("clientcontact_mothersln6"));
			this.setClientcontact_name6(map.get("clientcontact_name6"));
			this.setClientcontact_position6(map.get("clientcontact_position6"));

			this.setClientcontact_lastname7(map.get("clientcontact_lastname7"));
			this.setClientcontact_mothersln7(map
					.get("clientcontact_mothersln7"));
			this.setClientcontact_name7(map.get("clientcontact_name7"));
			this.setClientcontact_position7(map.get("clientcontact_position7"));

			// MARCE this.setClient_sic(contract_.getClient().getSic());
			this.setClient_sic(map.get("client_sic"));
			
			//Alta emisoras con calidad
			this.setBancaSegmentoNomina(map.get("bancaSegmentoNomina"));
			this.setFolioValidacionNomina(map.get("folioValidacionNomina"));

		}

		this.setContract(contract_);
		this.isValidarFolio= true;//Alta emisoras con calida
		this.repetirValidarFolio = false;

	}

	public String getEditForm() {
		setEditForm();
		return "";
	}

	public void createPDF() {
		FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) fCtx.getExternalContext().getRequest();
		   	
	   	   String  nameTemplate = request.getParameter(NAME_TEMPLATE);
	       String  pathTemplate = request.getParameter(PATHTEMPLATE);
	       Integer flagTemplate = Integer.parseInt(request.getParameter("flagTemplate"));
		// log.info("******** Muestra Documento " + nameTemplate + "... " +
		// flagTemplate.intValue());
		pdfTemplateBinding.setAffiliationId(1);
		createPdfOrXLSResponse(getPath() + pathTemplate + nameTemplate,
				nameTemplate, true, flagTemplate.intValue() == 1 ? true : false);
	}

	public void createXLS() {
		// log.info("******** Muestra Documento de ayuda...");
		createPdfOrXLSResponse(getPath()
				+ "/WEB-INF/docs/TC_x_Dispersion_Nomina.xls",
				"TC_x_Dispersion_Nomina.xls", false, false);
	}

	public Template[] getFormatList() {
		this.setToPrint(false);
		Collection<Template> templateCollection = getTemplateOption(this.getProduct().getProductid());
		Collection<Template> templateCollection2 = new ArrayList<Template>();
		
		
        for (Template template : templateCollection ) {
        	if(this.payrolltype.equals("Impulso Nomina")){

        		if (template.getTemplateid()!= 11)
        			templateCollection2.add(template);
        	}else{
        		
        		if (template.getTemplateid()!= 48)
        			templateCollection2.add(template);
        	}
        			
        }
		
		Template[] templateArray = new Template[templateCollection2.size()];
		return templateCollection2.toArray(templateArray);
	}

	public String getAccountnumber() {
		if (accountnumber != null && accountnumber.trim().length() > 0) {
			return encrypt.decrypt(accountnumber);
		} else {
			return "";
		}

	}

	public String getAccountnumberEncrypt() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {

		if (accountnumber != null && accountnumber.trim().length() > 0) {
			this.accountnumber = encrypt.encrypt(accountnumber);
		} else {
			this.accountnumber = "";
		}
	}

	public void setAccountnumberDecrypt(String accountnumber) {

		if (accountnumber != null && accountnumber.trim().length() > 0) {
			this.accountnumber = accountnumber;
		} else {
			this.accountnumber = "";
		}
	}

	public String getAvsalarybyemployee() {
		return avsalarybyemployee;
	}

	public void setAvsalarybyemployee(String avsalarybyemployee) {
		this.avsalarybyemployee = avsalarybyemployee;
	}

	public String getAvstaffturnover() {
		return avstaffturnover;
	}

	public void setAvstaffturnover(String avstaffturnover) {
		this.avstaffturnover = avstaffturnover;
	}

	public void setBemnumber(String bemnumber) {
		this.bemnumber = bemnumber;
	}

	public String getBemnumber() {
		return bemnumber;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getCommdispersalanotherbank() {
		return commdispersalanotherbank;
	}

	public void setCommdispersalanotherbank(String commdispersalanotherbank) {
		this.commdispersalanotherbank = commdispersalanotherbank;
	}

	public String getCommfiletransmition() {
		return commfiletransmition;
	}

	public void setCommfiletransmition(String commfiletransmition) {
		this.commfiletransmition = commfiletransmition;
	}

	public String getCommonlinedispersal() {
		return commonlinedispersal;
	}

	public void setCommonlinedispersal(String commonlinedispersal) {
		this.commonlinedispersal = commonlinedispersal;
	}

	public String getCommtraddispersal() {
		return commtraddispersal;
	}

	public void setCommtraddispersal(String commtraddispersal) {
		this.commtraddispersal = commtraddispersal;
	}

	public String getDaystankpayroll() {
		return daystankpayroll;
	}

	public void setDaystankpayroll(String daystankpayroll) {
		this.daystankpayroll = daystankpayroll;
	}

	public String getPackagetype() {
		return packagetype;
	}

	public void setPackagetype(String packagetype) {
		this.packagetype = packagetype;
	}

	public String getPayrollcomments() {
		return payrollcomments;
	}

	public void setPayrollcomments(String payrollcomments) {
		this.payrollcomments = payrollcomments;
	}

	public String getPayrolltype() {
		return payrolltype;
	}

	public void setPayrolltype(String payrolltype) {
		this.payrolltype = payrolltype;

		// ModID:002
		if (payrolltype.equals("Nomina Tradicional"))
			this.setBemnumber("");

	}

	public String getPerioddispersion() {
		return perioddispersion;
	}

	public void setPerioddispersion(String perioddispersion) {
		this.perioddispersion = perioddispersion;
	}

	public String getQuantemployees() {
		return quantemployees;
	}

	public void setQuantemployees(String quantemployees) {
		this.quantemployees = quantemployees;
	}

	// ModId:001
	// Propiedad autoregister
	public void setAutoRegister(String isAutoRegister) {
		this.autoRegister = isAutoRegister;
	}

	public String getAutoRegister() {
		return this.autoRegister;
	}

	public SelectItem[] getPerioddispersionArray() {
		if (this.perioddispersionArray == null) {
			Attribute att = this.getAttributeMB().getByFieldname(
					"perioddispersion");
			if (att != null) {
				Collection<AttributeOption> attOptCollection = att
						.getAttributeOptionCollection();
				if (attOptCollection != null) {
					perioddispersionArray = new SelectItem[attOptCollection
							.size()];
					int i = 0;
					for (AttributeOption attOpt : attOptCollection) {
						perioddispersionArray[i] = new SelectItem(attOpt
								.getDescription(), attOpt.getDescription());
						i++;
					}
				}
			}
		}
		return perioddispersionArray;
	}

	public void setPerioddispersionArray(SelectItem[] perioddispersionArray) {
		this.perioddispersionArray = perioddispersionArray;
	}

	public SelectItem[] getPayrolltypeArray() {
		if (this.payrolltypeArray == null) {
			Attribute att = this.getAttributeMB().getByFieldname("payrolltype");
			if (att != null) {
				Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
				if (attOptCollection != null) {

					payrolltypeArray = new SelectItem[attOptCollection.size()];
					int i = attOptCollection.size()-1;
					for (AttributeOption attOpt : attOptCollection) {
						payrolltypeArray[i] = new SelectItem(attOpt.getDescription(), attOpt.getDescription());
						i--;
					}
				}				
			}
		}
		return payrolltypeArray;
	}

	public void setPayrolltypeArray(SelectItem[] payrolltypeArray) {
		this.payrolltypeArray = payrolltypeArray;
	}

	public SelectItem[] getPackagetypeArray() {
		if (this.packagetypeArray == null) {
			Attribute att = this.getAttributeMB().getByFieldname("packagetype");
			if (att != null) {
				Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
				if (attOptCollection != null) {
					packagetypeArray = new SelectItem[attOptCollection.size()];
					for (AttributeOption attOpt : attOptCollection) {
						//packagetypeArray[i] = new SelectItem(attOpt.getDescription(), attOpt.getDescription());//ORIGINAL
						//QUITA EL ORDNE ALFABETICO, Y ORGANIZA POR EL ID DE LA TABLA
						try{
						packagetypeArray[attOpt.getAttributeOptionPK().getOptionid()-1]= new SelectItem(attOpt.getDescription(), attOpt.getDescription());
						}catch(Exception e){
							System.out.println("verificar que el id no sea cero en la tabla AttributeOption");
						}
					}
				}
			}
		}
		return packagetypeArray;
	}

	public void setPackagetypeArray(SelectItem[] packagetypeArray) {
		this.packagetypeArray = packagetypeArray;
	}

	public SelectItem[] getChargeArray() {
		if (this.chargeArray == null) {
			Attribute att = this.getAttributeMB().getByFieldname("charge");
			if (att != null) {
				Collection<AttributeOption> attOptCollection = att
						.getAttributeOptionCollection();
				if (attOptCollection != null) {
					chargeArray = new SelectItem[attOptCollection.size()];
					int i = 0;
					for (AttributeOption attOpt : attOptCollection) {
						chargeArray[i] = new SelectItem(
								attOpt.getDescription(), attOpt
										.getDescription());
						i++;
					}
				}
			}
		}
		return chargeArray;
	}

	public void setChargeArray(SelectItem[] chargeArray) {
		this.chargeArray = chargeArray;
	}

	public SelectItem[] getCategoriesArray() {
		System.err.println("getCategory---->" + getClient_categorycode());
		if (this.categoriesArray == null) {
			// Lista Giros
			List<Categories> categories = categoryPayrollBean.findPayrollAll();
			if (categories != null) {
				categoriesArray = new SelectItem[categories.size()];
				int i = 0;
				for (Categories cat : categories) {
					categoriesArray[i] = new SelectItem(cat.getName(), cat
							.getName());
					i++;
				}
			}
		}

		return categoriesArray;
	}

	@Override
	public PdfTemplateBinding getPdfTemplateBinding() {
		return pdfTemplateBinding;
	}

	@Override
	public String getProductPrefix() {
		return "NA";
	}

	@Override
	public void getProductIdDetail() {
		setProduct(productBean.findById(new Integer(2))); // Producto Nomina = 2
	}

	@Override
	public void setResetForm() {
		payrolltype = "";
		bemnumber = "";
		perioddispersion = "";
		daystankpayroll = "";
		quantemployees = "";
		avsalarybyemployee = "";
		avstaffturnover = "";
		packagetype = "";
		accountnumber = "";
		// autoRegister = "";
		commtraddispersal = "";
		commdispersalanotherbank = "";
		commonlinedispersal = "";
		commfiletransmition = "";
		charge = "";
		payrollcomments = "";

		super.clearFields();
		setProduct(productBean.findById(new Integer(2))); // Producto Nomina
		// Tradicional = 2
		setStatusContract(statusBean.findById(new Integer(1))); // Status Nuevo
		// = 1
		
		bancaSegmentoNomina="";//Alta emisoras con calidad
		folioValidacionNomina="";//Alta emisoras con calidad
		esFolioValido="";//Alta emisoras con calidad
		isValidarFolio= true;//Alta emisoras con calidad
		
		orderFormLoaded=false;
		this.repetirValidarFolio  =false;
		
	}

	public String getResetForm() {
		setResetForm();
		return "";
	}

	public String getBancaSegmentoNomina() {//Alta emisoras con calidad
		return bancaSegmentoNomina;
	}

	public void setBancaSegmentoNomina(String bancaSegmentoNomina) {//Alta emisoras con calidad
		this.bancaSegmentoNomina = bancaSegmentoNomina;
	}

	public String getFolioValidacionNomina() {//Alta emisoras con calidad
		return folioValidacionNomina;
	}

	public void setFolioValidacionNomina(String folioValidacionNomina) {//Alta emisoras con calidad
		this.folioValidacionNomina = folioValidacionNomina;
	}

	
	
	public String getEsFolioValido() {
		return esFolioValido;
/*		System.out.println("aqui pasa getEsFolioValido()::> "+esFolioValido);
		System.out.println("1.- validacion"+(esFolioValido==null));
		System.out.println("2.- validacion"+("".equals(esFolioValido)));
		//return esFolioValido;
		if("".equals(esFolioValido)){
			System.out.println("NULO...");
			return "nuevo";
		}else{
			
			System.out.println("No es NULO");
			return esFolioValido;
		}
*/
		/*
		 * if(this.affiliation_integration!=null){
				return affiliation_integration;
			}else {
				return ApplicationConstants.EMPTY_STRING;
			}
		 */
	}

	public void setEsFolioValido(String esFolioValido) {
		System.out.println("aqui pasa setEsFolioValido()--> "+esFolioValido);
		this.esFolioValido = esFolioValido;
	}

	public Boolean getIsValidarFolio() {
		if(this.isValidarFolio==null){
    		this.isValidarFolio = true;
    	}
		return isValidarFolio;
	}

	public void setIsValidarFolio(Boolean isValidarFolio) {
		this.isValidarFolio = isValidarFolio;
	}

	public void findFolioNomina(ActionEvent actionEvent)  throws ServletException, IOException {
		System.out.println("LLEGASTE!!!");
		System.out.println("Folio:"+this.getFolioValidacionNomina());
		
		System.out.println("Segemento: "+getBancaSegmentoNomina());
		System.out.println("folio: "+this.getFolioValidacionNomina());
		if("pyme".equalsIgnoreCase(getBancaSegmentoNomina()) && !this.getFolioValidacionNomina().isEmpty() && this.getFolioValidacionNomina()!=null && !("".equals(this.getFolioValidacionNomina()))){
			System.out.println("DENTRO DE IF ");
			findByFolio(this.getFolioValidacionNomina());

		}
	}
	
	/**
	 * Metodo que obtiene la informaciï¿½n del status de validacion en base a su folio
	 * 
	 * @return folio
	 */
	private void findByFolio(String folio) {
		
		/*Employee employee = new Employee();
		try {
			employee = employeeBean.findByNumEmpleado(id);
		} catch (Exception ex) {log.log(Level.SEVERE, "Error en ContractAbstractMB.findEmployeeById: " + ex.getMessage(), ex);
			return null;
		}
		return employee;
		*/
		DMFiltroEmisoras filtroEmisoras = new  DMFiltroEmisoras();
		setIsValidarFolio(false);
		/*if("SDNTE19031300000070".equalsIgnoreCase(folio)){
			setEsFolioValido("AUTORIZADA");
			System.out.println(getEsFolioValido());
		}else{
			setEsFolioValido("NO AUTORIZADA");
			System.out.println(getEsFolioValido());
		}*/
		//************************************************
		try{
			System.out.println("Dentro de findByFolio: "+folio);
			if(folio != null && !folio.isEmpty()){
				filtroEmisoras = statusRemote.findByFolio(folio);
				if(filtroEmisoras!=null){
					System.out.println("ESTATUS DE BD: "+filtroEmisoras.getIdEstatus());
					System.out.println(filtroEmisoras.getCatEstatusFE().getEstatus());
					if("98".equalsIgnoreCase(filtroEmisoras.getIdEstatus().toString())){
						setEsFolioValido("AUTORIZADA");
						System.out.println(getEsFolioValido()+" /ESTATUS: "+filtroEmisoras.getIdEstatus()+" "+filtroEmisoras.getCatEstatusFE().getEstatus());
					}else{
						setEsFolioValido("NO AUTORIZADA");
						System.out.println(getEsFolioValido());
					}
				}else{
					setEsFolioValido("NO EXISTE FOLIO EN BASE DE DATOS");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public Boolean getRepetirValidarFolio() {
		System.out.println("Dentrooo de getRepetirValidarFolio: "+this.repetirValidarFolio+" - "+(this.repetirValidarFolio==null));
		if(this.repetirValidarFolio==null){
    		this.repetirValidarFolio = false;
    	}
		
    	return this.repetirValidarFolio;
		//return repetirValidarFolio;
	}

	public void setRepetirValidarFolio(Boolean repetirValidarFolio) {
		System.out.println("Dentrooo de SETRepetirValidarFolio: "+repetirValidarFolio);
		//if(this.repetirValidarFolio==null){
		if("pyme".equalsIgnoreCase(bancaSegmentoNomina)){
			System.out.println("SI ES PYME");
			if(!this.getFolioValidacionNomina().isEmpty() && this.getFolioValidacionNomina()!=null && !("".equals(this.getFolioValidacionNomina()))){
				System.out.println("No esta vacio "+ getFolioValidacionNomina());
				if("AUTORIZADA".equalsIgnoreCase(getEsFolioValido())){
					System.out.println("Es autorizada "+ getEsFolioValido());
					if(getIsValidarFolio()==false){
						System.out.println("ya esta autorizada "+ getIsValidarFolio());
						this.repetirValidarFolio = true;
					}

				}
			}
    		
    	}else{
    		this.repetirValidarFolio = false;
    	}
		//this.repetirValidarFolio = repetirValidarFolio;
	}

	public Boolean getOrderFormLoaded() {
		/*if(this.orderFormLoaded==null)
    		this.orderFormLoaded = false;
    	*/
		System.out.println("En getOrderFormLoaded: "+this.orderFormLoaded);
    	return this.orderFormLoaded;
	}

	public void setOrderFormLoaded(Boolean orderFormLoaded) {
		System.out.println("En setOrderFormLoaded: "+orderFormLoaded);
		this.orderFormLoaded = orderFormLoaded;
	}

}
