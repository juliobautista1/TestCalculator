
package com.banorte.contract.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.banorte.contract.business.CategoriesRemote;
import com.banorte.contract.business.CommisionPlanRemote;
import com.banorte.contract.business.IncomePlanRemote;
import com.banorte.contract.business.PlanRemote;
import com.banorte.contract.model.Affiliation;
import com.banorte.contract.model.Attribute;
import com.banorte.contract.model.AttributeOption;
import com.banorte.contract.model.Categories;
import com.banorte.contract.model.Cities;
import com.banorte.contract.model.CommisionPlan;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.ContractMessageErrors;
import com.banorte.contract.model.IncomePlan;
import com.banorte.contract.model.Plan;
import com.banorte.contract.model.Template;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.AttrConstants;
import com.banorte.contract.util.AttributeOptionComparator;
import com.banorte.contract.util.ContractUtil;
import com.banorte.contract.util.CurrencyConverter;
import com.banorte.contract.util.EncryptBd;
import com.banorte.contract.util.EquipmentType;
import com.banorte.contract.util.Formatter;
import com.banorte.contract.util.PlanType;
import com.banorte.contract.util.pdf.PdfTemplateBinding;
import com.banorte.contract.util.pdf.PdfTemplateBindingContract;

/**
 *
 * @author Administrator
 */
public class ContractAcquirerMB extends ContractAbstractMB {

    private static Logger log = Logger.getLogger(ContractAcquirerMB.class.getName());
    private Affiliation affiliation;
    private String commchargetype;
    //private String avcommisiontcmnComplete;
    //private String avcommisiontcdllsComplete;
    //private String avcommisionintnlmnComplete;
    //private String avcommisionintnldllsComplete;
    //private String avcommisiontdmnComplete;
    //private String avcommisiontddllsComplete;
    private Integer formatType;
    private SelectItem[] affiliation_productdescArray;
    private SelectItem[] affiliation_soluciontypeArray;
    private SelectItem[] affiliation_modalityArray;
    private SelectItem[] affiliation_currencyArray;
    private SelectItem[] affiliation_servicetypeArray;   
    private SelectItem[] affiliation_servicetypeoipArray;  
    private SelectItem[] affiliation_devicetypeArray;    
    private PdfTemplateBindingContract pdfTemplateBinding;
    private String affiliation_duedate;
    private EncryptBd encrypt;
    private boolean toContact;
    private String affiliation_qps;
    private String affiliation_forceauth;
    private String affiliation_openkey;
    private String affiliation_accountnumbermn;
    private String affiliation_accountnumberdlls;
    private String affiliation_otherComercialPlan;
    private String affiliation_chargeType;
    private String affiliation_alliance;
    private String affiliation_chargeType_hidd;
    private String affiliation_alliance_hidd;
  //Tiempo Aire Netpay
	private String affiliation_tiempoaire;
    private String affiliation_telcel;
    private String affiliation_movistar;
    private String affiliation_iusacell;
    private String affiliation_nextel;
    
   // TPV Nomina
    private String affiliation_tpv_payroll;
    private String tpv_number_employee;
    private String tpv_penalty;
    private String affiliation_nominaselected;
 
    private String affiliation_devicetype;
    private String template;
    
    //Cybersource
    private String affiliation_integration;
    private String selected_hostBanorte;
    private String selected_revision;
    private String selected_hostComercio;
    private String selected_direct3D;
    
    private String affiliation_rentaDolar;
    private String selected_2000;
    private String selected_4000;
    
    private CategoriesRemote categoryAquirerBean;
    private SelectItem[] categoriesArray;
    private SelectItem[] citiesClientFsArray;
    
    private ArrayList<ContractMessageErrors> ordenErrorsList= new ArrayList();
    
    //Reciprocidad o Facturacion
    private String optionMonthlyratemn;
    private String replaceAmountratemn;
    private String optionMonthlyratedlls;
    private String replaceAmountratedlls;
    
    //Variables para Plan 2011
    private SelectItem[] affiliation_comercialplanArray;
    private SelectItem[] affiliation_OIPComercialplanArray;
    private PlanRemote planBean;
    private CommisionPlanRemote comissionPlanBean;
    private IncomePlanRemote incomePlanBean;
    private Boolean recalculateCommisionTable = false;
    private Boolean orderFormLoaded = false;
   
    private SelectItem[] affiliation_allianceArray;  
    private SelectItem[] affiliation_chargeTypeArray;
    
   //Para impulso Captación
    private String aff_impulsoCaptacion;
    
    //F-83585 Garantia Liquida
    private String aff_garantiaLiquida;
    private String aff_montoInicial;
    private String aff_montoPromDiario;
    private String aff_porcentajeGL;
    private String aff_excepcionPorceGL;
    private String aff_montoGL;
    private String aff_porcentajeInicialGL;
    private String aff_montoInicialGL;
    private String aff_porcentajeRestanteGL;
    private String aff_montoRestanteGL;
    private String aff_porcentajeDiarioGL;
    private String aff_promMontoDiarioGL;
    private String aff_diasAproxGL;
    private String aff_glOriginal;
    private String aff_comentariosDisminucionExcepcionGL;
    
    private String aff_auxMontoGL;
    private String aff_auxMontoInicial;
    private String aff_auxMontoPromDiario;
    private String aff_auxPorcentajeInicialGL;
    private String aff_auxMontoInicialGL;
    private String aff_auxPorcentajeRestanteGL;
    private String aff_auxMontoRestanteGL;
    private String auxOptionPorcentajeVentasDiarias;
    private String auxOptionMontoFijoDiario;
    private String aff_auxPromMontoDiarioGL;
    private String aff_auxPorcentajeDiarioGL;
    private String aff_auxDiasAproxGL;
    private String aff_auxGlOriginal;
    
    private String fianzaOculta;

    //option de garantia liquida
    private String optionPorcentajeVentasDiarias;
    private String optionMontoFijoDiario;
    
    private String aff_ventasEstimadasMensuales;
    private String aff_montoEstimadoDeTransaccion;
    
    private String seleccionGiroAltoRiesgoGL;
    
    //Motivos disminucion/exencion
    private String exencionConvenienciaComercialVIP;
    private String exencionOtros;
    private String exencionJustificacion;
    //conocimieto del cliente
    private String solvenciaEconimicaSi;
    private String solvenciaEconimicaNo;
    private String visitaOcularRecienteSi;
    private String visitaOcularRecienteNo;
    private String riesgoReputacionalOperacionalSi;
    private String riesgoReputacionalOperacionalNo;
    private String descBienServicioOfrece;
    private String territorioNacionalSi;
    private String territorioNacionalNo;
    private String territorioNacionalEspecificacion;
    private String enNombreDeUnTerceroSi;
    private String enNombreDeUnTerceroNo;
    private String enNombreDeUnTerceroEspecificacion;
    private String antiguedadAnio;
    private String antiguedadMeses;
	
	private String isMostrarMsjNoGrupo;
    
    //Mujer PyME
    private String mujerPyME;
    private String esCuentaMujerPyME;
    private String cuentaMujerPyMEValidada;
    private String noEsCuentaMujerPyMEValidada;
    private static Map<String,String> listaClientesMujerPyME;
    private String desactivarMujerPyME;
    private String isMsgMujerPyME;
    private String listaCuentaMujerPyMEVacia;
    
    private String esCuentaMujerPyMEDlls;
    private String cuentaMujerPyMEValidadaDlls;
    private String noEsCuentaMujerPyMEValidadaDlls;
    
    private String esClienteMujerPyME;
    private String clienteMujerPyMEValidado;
    private String noEsClienteMujerPyMEValidado;
    
           
    //para cashback
    private String aff_cashback;
    private String aff_commCbPymt = "0.0";
    private String aff_commCbChrg = "0.0";
    private String aff_commCbChrgDll = "0.0";
        
//----Variables para Contrato Unico IXE----
    private String aff_exentDep; //valor puente para "exentar fianza"
    private String amex;
    private String tpvUnattended;//tpv desatendida
    private String transConciliation;//conciliaci�n de transacciones
    private String promNoteRqst;//solicitud de pagar�s
    private String dcc; //DCC
    //Contacto Administrador
    private String adminName;
    private String adminFLastName;
    private String adminMLastName;
    private String adminEmail;
    private String adminPhone;
    private String adminComplete;
    //Contactos Adicionales para el M�dulo de Petici�n de Pagar�s(1)
    private String promNoteRqstName1;
    private String promNoteRqstFLastName1;
    private String promNoteRqstMLastName1;
    private String promNoteRqstComplete1;
    private String promNoteRqstEmail1;
    private String promNoteRqstPhone1;
    //Contactos Adicionales para el M�dulo de Petici�n de Pagar�s (2)
    private String promNoteRqstName2;
    private String promNoteRqstFLastName2;
    private String promNoteRqstMLastName2;
    private String promNoteRqstComplete2;
    private String promNoteRqstEmail2;
    private String promNoteRqstPhone2;
    //pago movil y pinpad o tpv propia
    private String mobilePymnt;
    private String ownTpvPinpad;
    
    private String ixePlan; //valor del plan del combo de ixe
    private String banortePlan; //valor del plan del combo de banorte
    private SelectItem[] commercialPlanArrayIxe; // para llenar el combo de planes de ixe
    
    //tabla de comisiones micros y cybersource pesos y dolares
    private Double transactionFeeCyberMn;//cuota por transaccion cyber
    private Double limitedCoverageCyberMn;//cobertura limitada cyber
    private Double wideCoverageCyberMn;//cobertura amplia cyber
    private Double transactionFeeMicrosMn;//cuota por transaccion micros
    
    private Double transactionFeeCyberDlls;//cuota por transaccion cyber dolares
    private Double limitedCoverageCyberDlls;//cobertura limitada cyber dolares
    private Double wideCoverageCyberDlls;//cobertura amplia cyber dolares 
    private Double transactionFeeMicrosDlls;//cuota por transaccion micros dolares
    
    private String antiguedad;//antiguedad menor a 6 meses
    private String userState;
//    private String option3dSecure;//para cuando quiere el servicio 3D secure sin alianza
    private int flag3dSelected;//banderita para seleccionar por default
    
    //2016Geoclocalizacion
    private String affiliation_latitude;
    private String affiliation_length;
    
    private String rentBy;
    private String groupNo;
    
    
    private String NAME_TEMPLATE ="nameTemplate";
    private String PATHTEMPLATE ="pathTemplate";

   
    public ContractAcquirerMB() {
        super();
        affiliation = new Affiliation(); 
        encrypt		= new EncryptBd();
        toContact	=false;        
        setStatusContract(statusBean.findById(new Integer(1))); // Status Nuevo = 1 
        
        if (categoryAquirerBean== null) {
        	categoryAquirerBean = (CategoriesRemote) EjbInstanceManager.getEJB(CategoriesRemote.class);
        }        
        
        if (planBean== null) {
        	planBean = (PlanRemote) EjbInstanceManager.getEJB(PlanRemote.class);
        }        

        if (comissionPlanBean== null) {
        	comissionPlanBean = (CommisionPlanRemote) EjbInstanceManager.getEJB(CommisionPlanRemote.class);
        }        

        if (incomePlanBean== null) {
        	incomePlanBean = (IncomePlanRemote) EjbInstanceManager.getEJB(IncomePlanRemote.class);
        }       
        
    }

    private void assignAffiliation(Affiliation assignedAffil) {
        Contract contract = this.getContract();
        contract.add(assignedAffil);
    }

    @Override
    public boolean savePartialContract() {
    	log.setLevel(Level.WARNING);
        Contract contract = getContract();
        log.info("************* GRABANDO ATRIBUTOS DE AFILIACION en ContractId:" + contract.getContractId());
        if(contract.getAffiliationCollection()!=null){
        }            contract.getAffiliationCollection().clear();

        // Cuando se modifica la divisa a Pesos los campos de dlls se deshabilitan y no se envian por lo que es necesario borrarlos manualmente
        if(this.getAffiliation_currency().equals("Pesos")){
        	this.initializeDllsFields();
        }
        
        //Validar que la cuenta este encriptada, sino lo esta, encriptarla
  /*      if(affiliation.getAccountnumbermn()!=null && affiliation.getAccountnumbermn().length()>0 &&  affiliation.getAccountnumbermn().length()<11)
        	System.out.println("no estaba encriptada la cuenta mn en el saveInfo, asi que se vuelve a encriptar");
        	affiliation.setAccountnumbermn(encrypt.encrypt(affiliation.getAccountnumbermn()));
        if(affiliation.getAccountnumberdlls()!=null && affiliation.getAccountnumberdlls().length()>0 && affiliation.getAccountnumberdlls().length()<11)
        	System.out.println("no estaba encriptada la cuenta dlls en el saveInfo, asi que se vuelve a encriptar");
        	affiliation.setAccountnumberdlls(encrypt.encrypt(affiliation.getAccountnumberdlls()));
       */ 
        
        //AGREGAR CADA UNA DE LAS AFILIACIONES DEL CONTRATO
        
        assignAffiliation(affiliation);

//        log.info("************* GRABANDO ATRIBUTOS GENERALES en ContractId:" + contract.getContractId());
        ArrayList<ContractAttribute> list = new ArrayList<ContractAttribute>();

        addToList(list,"celebrationplace", getCelebrationplace());
        addToList(list,"celebrationstate", getCelebrationstate());
        addToList(list,"celebrationdate", getCelebrationdate() != null ? getCelebrationdate() : "");
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
        addToList(list,"client_zipcode", getClient_zipcode() != null ? getClient_zipcode().toString() : "");
        addToList(list,"client_city", getClient_city());
        addToList(list,"client_state", getClient_state());
        addToList(list,"client_email", getClient_email());
        addToList(list,"client_fiscalname", getClient_fiscalname());
        addToList(list,"client_fiscaltype", getClient_fiscaltype() != null ? getClient_fiscaltype().toString() : "");
        addToList(list,"client_rfc", getClient_rfc());
        addToList(list,"client_categorycode", getClient_categorycode().length()<200?getClient_categorycode():getClient_categorycode().substring(0, 200));
        if(contract.getProduct().getProductid()==5){
	        addToList(list,"client_messageservice", getClient_messageservice());
	        addToList(list,"client_site", getClient_site());
      	}
        addToList(list,"legalrepresentative_name_1", getLegalrepresentative_name_1());
        addToList(list,"legalrepresentative_lastname_1", getLegalrepresentative_lastname_1());
        addToList(list,"legalrepresentative_mothersln_1", getLegalrepresentative_mothersln_1());
        addToList(list,"legalrepresentative_rfc_1", getLegalrepresentative_rfc_1());
        addToList(list,"legalrepresentative_email_1", getLegalrepresentative_email_1());

        addToList(list,"legalrepresentative_name_2", getLegalrepresentative_name_2());
        addToList(list,"legalrepresentative_lastname_2", getLegalrepresentative_lastname_2());
        addToList(list,"legalrepresentative_mothersln_2", getLegalrepresentative_mothersln_2());
        addToList(list,"legalrepresentative_rfc_2", getLegalrepresentative_rfc_2());
        addToList(list,"legalrepresentative_email_2", getLegalrepresentative_email_2());
       
        addToList(list,"clientcontact_name1", getClientcontact_name1());
        addToList(list,"clientcontact_lastname1", getClientcontact_lastname1());
        addToList(list,"clientcontact_mothersln1", getClientcontact_mothersln1());
        addToList(list,"clientcontact_position1", getClientcontact_position1());
        addToList(list,"clientcontact_phone1", getClientcontact_phone1() != null ? getClientcontact_phone1().toString() : "");
        addToList(list,"clientcontact_phoneext1", getClientcontact_phoneext1() != null ? getClientcontact_phoneext1().toString() : "");
        addToList(list,"clientcontact_email1", getClientcontact_email1());
       
        //Para contacto desarrollador si no es comercio electronico se limpian los valores (en caso de que cambie el tipo de producto)
        if((affiliation.getProductdesc()!=null) && !(affiliation.getProductdesc().equalsIgnoreCase("Comercio Electronico"))){
       	 setClientcontact_email2(null);
       	 setClientcontact_name2(null);
       	 setClientcontact_phone2(null);
       	 setClientcontact_phoneext2(null);
       	 setClientcontact_position2(null);
        }
        addToList(list,"clientcontact_name2", getClientcontact_name2()!=null?getClientcontact_name2():"");//nombre o razon social contacto desarrollador
//        addToList(list,"clientcontact_lastname2", getClientcontact_lastname2());
//        addToList(list,"clientcontact_mothersln2", getClientcontact_mothersln2());
        addToList(list,"clientcontact_position2", getClientcontact_position2()!=null?getClientcontact_position2():"");
        addToList(list,"clientcontact_phone2", getClientcontact_phone2() != null ? getClientcontact_phone2().toString() : "");
        addToList(list,"clientcontact_phoneext2", getClientcontact_phoneext2() != null ? getClientcontact_phoneext2().toString() : "");
        addToList(list,"clientcontact_email2", getClientcontact_email2()!=null?getClientcontact_email2():"");
        
       // if ( getClient_fsname() != null &&  !getClient_fsname().trim().equals("")){
        addToList(list,"client_fsname", getClient_fsname() != null? getClient_fsname():"");
        addToList(list,"client_fsrfc", getClient_fsrfc() != null? getClient_fsrfc():"" );
        addToList(list,"client_fsstreet", getClient_fsstreet() != null? getClient_fsstreet():"");
        addToList(list,"client_fsnum", getClient_fsnum() != null? getClient_fsnum():"");
        addToList(list,"client_fszipcode", getClient_fszipcode() != null? getClient_fszipcode() : "");
        addToList(list,"client_fscolony", getClient_fscolony() != null? getClient_fscolony():"");
        addToList(list,"client_fscounty", getClient_fscounty() != null? getClient_fscounty():"");
        addToList(list,"client_fscity",   getClient_fscity() != null && getClient_fsname() != null?  getClient_fscity():""); 
        addToList(list,"client_fsstate",  getClient_fsstate() != null && getClient_fsname() != null?  getClient_fsstate():"");
        addToList(list,"client_fsphone", getClient_fsphone() != null ? getClient_fsphone().toString() : "");
        addToList(list,"client_fsphoneext", getClient_fsphoneext() != null ? getClient_fsphoneext().toString() : "");
        addToList(list,"client_fsfax", getClient_fsfax() != null ? getClient_fsfax().toString() : "");
        addToList(list,"client_fsfaxext", getClient_fsfaxext() != null ? getClient_fsfaxext().toString() : "");
        addToList(list,"client_fsemail", getClient_fsemail() != null? getClient_fsemail():"");
        addToList(list,"client_fsaddressComplete", getClient_fsstreet() + " " + getClient_fsnum());
        addToList(list,"client_fsphoneComplete", (getClient_fsphone() !=null? getClient_fsphone().toString():"") + "" + (getClient_fsphoneext() !=null ? " Ext. " + getClient_fsphoneext().toString():""));
        addToList(list,"client_fsnameComplete",getClient_fsname() != null? getClient_fsemail():"");
        addToList(list,"client_fscolonyComplete", getClient_fscolony() + " " +getClient_fscity());
        //}
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
        //Joseles(15/Nov/2011): Se utilizara el atributo branchcounty para colocar lo escrito el el campo "Especifique"
        //Omar (14/Marzo/2012): Se Agrego el atributo para el campo de 'Especifique' que es el campo  affiliation_otherComercialPlan
        addToList(list,"affiliation_otherComercialPlan", getAffiliation_otherComercialPlan());
        addToList(list,"branchcity", getBranchcity());
        addToList(list,"branchstate", getBranchstate());
        addToList(list,"branchphone", getBranchphone());
        addToList(list,"branchfax", getBranchfax());
        addToList(list,"bankingsector", getBankingsector()==null?"":getBankingsector());

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

        addToList(list,"celebrationComplete",getCelebrationplace() + ", " + getCelebrationstate() + " A " + getCelebrationdate());
        addToList(list,"officernameComplete",getOfficername() +  " "+ getOfficerlastname() + " " + getOfficermothersln());
        addToList(list,"officerebankingnameComplete",getOfficerebankingname() +  " "+ getOfficerebankinglastname() + " " + getOfficerebankingmothersln());        
        addToList(list,"branchadressComplete",getBranchstreet() + ", Col. "+ getBranchcolony()+ ", " + getBranchcity() + ", " + getBranchstate() + "." );
        addToList(list,"branchnameComplete", getCrnumber() + " " + getBranchname());
        addToList(list,"branchplaceComplete",getBranchcity() + ", " + getBranchstate());
        addToList(list,"officerrepnameComplete1",getOfficerrepname_1() + " " + getOfficerreplastname_1() + " " + getOfficerrepmothersln_1() );
        addToList(list,"officerrepnameComplete2",getOfficerrepname_2() + " " + getOfficerreplastname_2() + " " + getOfficerrepmothersln_2());
        addToList(list,"clientcontact_nameComplete1",getClientcontact_name1() + " " + getClientcontact_lastname1() + " "  + getClientcontact_mothersln1());
        addToList(list,"clientcontact_nameComplete2",getClientcontact_name2());// + " " + getClientcontact_lastname2() + " "  + getClientcontact_mothersln2());        
        addToList(list,"clientcontact_phoneComplete1", (getClientcontact_phone1() !=null? getClientcontact_phone1().toString():"") + "" + (getClientcontact_phoneext1() !=null ? " Ext. " + getClientcontact_phoneext1().toString():""));
        addToList(list,"clientcontact_phoneComplete2", (getClientcontact_phone2() !=null? getClientcontact_phone2().toString():"") + "" + (getClientcontact_phoneext2() !=null ? " Ext. " + getClientcontact_phoneext2().toString():""));
        addToList(list,"client_phoneComplete",(getClient_areacode() != null ? getClient_areacode().toString():"") + (getClient_phone() != null ? getClient_phone().toString():"") + (getClient_phoneext()!=null?" Ext. " + getClient_phoneext().toString():"" ));
        addToList(list,"client_faxComplete",""+ (getClient_fax()!=null? getClient_areacode() + getClient_fax():"") + (getClient_faxext()!=null?" Ext. " + getClient_faxext().toString():"" ));
        addToList(list,"client_stateComplete",getCelebrationplace() + ", " + getCelebrationstate());
        addToList(list,"legalrepresentative_nameComplete1",getLegalrepresentative_name_1() + " " + getLegalrepresentative_lastname_1() + " " + getLegalrepresentative_mothersln_1());
        addToList(list,"legalrepresentative_nameComplete2",getLegalrepresentative_name_2() + " " + getLegalrepresentative_lastname_2() + " " + getLegalrepresentative_mothersln_2());
        addToList(list,"client_addressComplete", getClient_street() + " "  + (getClient_numext()  != null ? "Ext."  + getClient_numext():"" ) + " " + (getClient_numint() != null ?  "Int." + getClient_numint():""));
        
        if(this.getCommchargetype().equals("Monto"))
            formatType=1;
        else
            formatType=2;
        
//        addToList(list,"avcommisiontcmnComplete", getAvcommisiontcmnComplete()!=null  && getAvcommisiontcmnComplete().trim().length()>0 ? Formatter.formatValue((Double.parseDouble(getAvcommisiontcmnComplete())),formatType):"");
//        addToList(list,"avcommisiontcdllsComplete",getAvcommisiontcdllsComplete()!=null && getAvcommisiontcdllsComplete().trim().length()>0 ? Formatter.formatValue((Double.parseDouble(getAvcommisiontcdllsComplete())),formatType):"");
//        addToList(list,"avcommisiontdmnComplete", getAvcommisiontdmnComplete()!=null && getAvcommisiontdmnComplete().trim().length()>0 ? Formatter.formatValue((Double.parseDouble(getAvcommisiontdmnComplete())),formatType):"");
//        addToList(list,"avcommisiontddllsComplete", getAvcommisiontddllsComplete()!=null && getAvcommisiontddllsComplete().trim().length()>0 ? Formatter.formatValue((Double.parseDouble(getAvcommisiontddllsComplete())),formatType):"");
//        addToList(list,"avcommisionintnlmnComplete", getAvcommisionintnlmnComplete()!=null && getAvcommisionintnlmnComplete().trim().length()>0 ? Formatter.formatValue((Double.parseDouble(getAvcommisionintnlmnComplete())),formatType):"");
//        addToList(list,"avcommisionintnldllsComplete", getAvcommisionintnldllsComplete()!=null && getAvcommisionintnldllsComplete().trim().length()>0 ? Formatter.formatValue((Double.parseDouble(getAvcommisionintnldllsComplete())),formatType):"");
        addToList(list,"affiliationratemnComplete", affiliation.getAffiliationratemn()!= null && affiliation.getAffiliationratemn()>0 ?  Formatter.formatValue((affiliation.getAffiliationratemn()),1):"");
        addToList(list,"affiliationratedllsComplete", affiliation.getAffiliationratedlls()!= null && affiliation.getAffiliationratedlls()>0 ? Formatter.formatValue((affiliation.getAffiliationratedlls()),1):"");
        addToList(list,"transcriptorratemnComplete", affiliation.getTranscriptorratemn()!= null && affiliation.getTranscriptorratemn()>0 ? Formatter.formatValue((affiliation.getTranscriptorratemn()),1):"");
        addToList(list,"transcriptorratedllsComplete",  affiliation.getTranscriptorratedlls()!= null && affiliation.getTranscriptorratedlls()>0 ? Formatter.formatValue((affiliation.getTranscriptorratedlls()),1):"");        
        if( (this.getAffiliation_alliance().equals("Tradicional") && this.getAffiliation_integration().equals("Sin Cybersource / Con 3D"))
				|| (this.getAffiliation_alliance().equals("Hosted") && this.getAffiliation_integration().equals("Payworks Hosted")) ){
        	addToList(list,"monthlyratemnComplete", affiliation.getMonthlyratemn()!= null && affiliation.getMonthlyratemn()>0 ? Formatter.formatValue((affiliation.getMonthlyratemn() + (affiliation.getMonthlyrate3dsmn()!=null && affiliation.getMonthlyrate3dsmn()>0? affiliation.getMonthlyrate3dsmn():0)),1):"");//+ affiliation.getMonthlyrate3dsmn()
        	addToList(list,"monthlyratedllsComplete", affiliation.getMonthlyratedlls()!= null && affiliation.getMonthlyratedlls()>0? Formatter.formatValue((affiliation.getMonthlyratedlls() + (affiliation.getMonthlyrate3dsdlls()!=null && affiliation.getMonthlyrate3dsdlls()>0 ? affiliation.getMonthlyrate3dsdlls():0)),1):""); //getMonthlyrate3dsdlls
    	}else{
        	addToList(list,"monthlyratemnComplete", affiliation.getMonthlyratemn()!= null && affiliation.getMonthlyratemn()>0 ? Formatter.formatValue((affiliation.getMonthlyratemn()),1):"");
        	addToList(list,"monthlyratedllsComplete", affiliation.getMonthlyratedlls()!= null && affiliation.getMonthlyratedlls()>0? Formatter.formatValue((affiliation.getMonthlyratedlls()),1):"");
    	}
        addToList(list,"monthlyinvoicingminmnComplete", affiliation.getMonthlyinvoicingminmn()!= null && affiliation.getMonthlyinvoicingminmn()>0 ? Formatter.formatValue((affiliation.getMonthlyinvoicingminmn()),1):"$0.00"); 
        addToList(list,"monthlyinvoicingmindllsComplete", affiliation.getMonthlyinvoicingmindlls()!= null && affiliation.getMonthlyinvoicingmindlls()>0 ? Formatter.formatValue((affiliation.getMonthlyinvoicingmindlls()),1): "$0.00");
        addToList(list,"failmonthlyinvoicingmnComplete", affiliation.getFailmonthlyinvoicingmn() != null && affiliation.getFailmonthlyinvoicingmn()>0 ? Formatter.formatValue((affiliation.getFailmonthlyinvoicingmn()),1):"$0.00");
        addToList(list,"failmonthlyinvoicingdllsComplete", affiliation.getFailmonthlyinvoicingdlls()!= null && affiliation.getFailmonthlyinvoicingdlls()>0 ? Formatter.formatValue((affiliation.getFailmonthlyinvoicingdlls()),1):"$0.00");
        addToList(list,"minimiunbalancemnComplete", affiliation.getMinimiunbalancemn()!= null && affiliation.getMinimiunbalancemn()>=0 ? Formatter.formatValue((affiliation.getMinimiunbalancemn()),1):"");
        addToList(list,"minimiunbalancedllsComplete", affiliation.getMinimiunbalancedlls()!= null && affiliation.getMinimiunbalancedlls()>0 ? Formatter.formatValue((affiliation.getMinimiunbalancedlls()),1):"");
        addToList(list,"failminimiunbalancemnComplete", affiliation.getFailminimiunbalancemn()!= null && affiliation.getFailminimiunbalancemn()>=0 ? Formatter.formatValue((affiliation.getFailminimiunbalancemn()),1):"");
        addToList(list,"failminimiunbalancedllsComplete", affiliation.getFailminimiunbalancedlls()!= null && affiliation.getFailminimiunbalancedlls()>0 ? Formatter.formatValue((affiliation.getFailminimiunbalancedlls()),1):"");
        addToList(list,"otherconcept1mnComplete", affiliation.getOtherconcept1mn()!= null && affiliation.getOtherconcept1mn()>0 ? Formatter.formatValue((affiliation.getOtherconcept1mn()),1):"");//1 pesos - 2 porcentaje
        
        //Verifica si es Cybersource para que solo cuando sea cybersource despliegue 3 decimales.
        if (this.getAffiliation_alliance().equals(ApplicationConstants.ALLIANCE_CYBERSOURCE)){
        	addToList(list,"otherconcept1dllsComplete", affiliation.getOtherconcept1dlls()!= null && affiliation.getOtherconcept1dlls()>0 ? Formatter.formatValue3Digits(affiliation.getOtherconcept1dlls()):"");
		}else {
			addToList(list,"otherconcept1dllsComplete", affiliation.getOtherconcept1dlls()!= null && affiliation.getOtherconcept1dlls()>0 ? Formatter.formatValue((affiliation.getOtherconcept1dlls()),1):"");
		}
        
        addToList(list,"promnotemnComplete",affiliation.getPromnotemn() != null && affiliation.getPromnotemn()>0 ? Formatter.formatValue((affiliation.getPromnotemn()),1):""); 
        addToList(list,"failpromnotemnComplete", affiliation.getFailpromnotemn()!= null && affiliation.getFailpromnotemn()>0 ? Formatter.formatValue((affiliation.getFailpromnotemn()),1):""); 
        addToList(list,"promnotedllsComplete", affiliation.getPromnotedlls()!= null  && affiliation.getPromnotedlls()>0 ? Formatter.formatValue((affiliation.getPromnotedlls()),1):"");
        addToList(list,"failpromnotedllsComplete", affiliation.getFailpormnotedlls() != null && affiliation.getFailpormnotedlls()>0 ? Formatter.formatValue((affiliation.getFailpormnotedlls()),1): "");

        addToList(list,"depositamountComplete", affiliation.getDepositamount()!=null && affiliation.getDepositamount()>0? Formatter.formatValue((affiliation.getDepositamount().doubleValue()),1): "");
        
        addToList(list,"activation3dsmnComplete", affiliation.getActivation3dsmn()!=null && affiliation.getActivation3dsmn()>0 ? Formatter.formatValue((affiliation.getActivation3dsmn()),1): "");
        addToList(list,"activation3dsdllsComplete", affiliation.getActivation3dsdlls()!=null && affiliation.getActivation3dsdlls()>0 ? Formatter.formatValue((affiliation.getActivation3dsdlls()),1) : "");
        addToList(list,"monthlyrate3dsmnComplete", affiliation.getMonthlyrate3dsmn()!=null && affiliation.getMonthlyrate3dsmn()>0? Formatter.formatValue((affiliation.getMonthlyrate3dsmn()),1) : "");
        addToList(list,"monthlyrate3dsdllsComplete", affiliation.getMonthlyrate3dsdlls()!=null && affiliation.getMonthlyrate3dsdlls()>0 ? Formatter.formatValue((affiliation.getMonthlyrate3dsdlls()),1) : "");
                      
        addToList(list,"celebrationdateday",this.formatDate.getDayDate(getCelebrationdate()));       
        addToList(list,"celebrationdatemonth",this.formatDate.getMonthDesc(getCelebrationdate()));        
        addToList(list,"celebrationdateyear",this.formatDate.getYearDate(getCelebrationdate()));
        addToList(list,"celebrationlargedate",this.formatDate.getLargeDate(getCelebrationdate()));
        
        addToList(list,"contract_reference", this.getContract().getReference());
        addToList(list,"comments", this.getComments().length()<250?this.getComments():this.getComments().substring(0, 249));
        addToList(list,"operations_comment", this.getComments().length()<250?this.getComments():this.getComments().substring(0, 249));
        
        //Banderas de Pesos y Dlls para contrato
        
        addToList(list,"affiliationisdlls", affiliation.getCurrency().equals("Ambos") || affiliation.getCurrency().equals("Dolares") ?"X":"" );
        addToList(list,"affiliationismn", affiliation.getCurrency().equals("Ambos") || affiliation.getCurrency().equals("Pesos") ?"X":"");    
        addToList(list,"affiliation_duedate", getAffiliation_duedate());

        addToList(list,"commchargetype", this.getCommchargetype());
        
        //Info Micros 23 /4/2012
        addToList(list,ApplicationConstants.FIELDNAME_ALLIANCE, getAffiliation_alliance());
        addToList(list,ApplicationConstants.FIELDNAME_CHARGETYPE, getAffiliation_chargeType());
        
        //Tiempo Aire Netpay
        addToList(list,"affiliation_tiempoaire", this.getAffiliation_tiempoaire());
        if(affiliation_tiempoaire.equalsIgnoreCase("false")){
        	affiliation_telcel="";
        	affiliation_movistar="";
        	affiliation_iusacell="";
        	affiliation_nextel="";
        }
        addToList(list,"affiliation_telcel", this.getAffiliation_telcel());
        addToList(list,"affiliation_movistar", this.getAffiliation_movistar());
        addToList(list,"affiliation_iusacell", this.getAffiliation_iusacell());
        addToList(list,"affiliation_nextel", this.getAffiliation_nextel());
        
        //Reciprocidad o Facturacion
        addToList(list,AttrConstants.AFFILIATION_TPV_PAYROLL, this.getAffiliation_tpv_payroll());
        if(isTPVNomina()){
        	addToList(list,AttrConstants.TPV_NUMBER_EMPLOYEE, this.getTpv_number_employee());
            addToList(list,AttrConstants.TPV_PENALTY, this.getTpv_penalty());
            
          //Valida que solo se imprima el Valor en Dolares en el PDF , cuando se selecciona Dolares
            if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR)){
            	Double penaltyDll	= CurrencyConverter.toDollar(Double.valueOf(this.getTpv_penalty()));
            	addToList(list,AttrConstants.TPV_PENALTY_DLL, penaltyDll !=null && penaltyDll >0 ? Formatter.formatValue((penaltyDll),1) : "");
            }else{
            	addToList(list,AttrConstants.TPV_PENALTY_COMPLETE, this.getTpv_penalty()!=null ? (Formatter.formatValue((Double.valueOf(this.getTpv_penalty()) ),1)) : "");
            }
        }
        
        addToList(list,AttrConstants.OPTION_MONTHLY_RATE_MN, this.getOptionMonthlyratemn() != null ? this.getOptionMonthlyratemn() : "");
        addToList(list,AttrConstants.REPLACE_AMOUNT_RATE_MN, this.getReplaceAmountratemn() != null ? this.getReplaceAmountratemn() : "");
        addToList(list,AttrConstants.OPTION_MONTHLY_RATE_DLLS, this.getOptionMonthlyratedlls() != null ? this.getOptionMonthlyratedlls() : "");
        addToList(list,AttrConstants.REPLACE_AMOUNT_RATE_DLLS, this.getReplaceAmountratedlls() != null ? this.getReplaceAmountratedlls() : "");
       // addToList(list,AttrConstants.AFFILIATION_TPV_PAYROLL_SELECTED, this.getAffiliation_nominaselected() != null ? this.getOptionMonthlyratemn() : "");
        
        //CYBERSOURCE
        if(affiliation_integration!=null && !affiliation_integration.equalsIgnoreCase("0")){//solo si se eligio una integracion se guardan los valores
        	addToList(list,AttrConstants.AFFILIATION_INTEGRATION, this.getAffiliation_integration() != null ? this.getAffiliation_integration() : "");
            addToList(list,AttrConstants.SELECTED_HOST_BANORTE, this.getSelected_hostBanorte() != null ? this.getSelected_hostBanorte() : "");
            addToList(list,AttrConstants.SELECTED_REVISION, this.getSelected_revision() != null ? this.getSelected_revision() : "");
            addToList(list,AttrConstants.SELECTED_HOST_COMERCIO, this.getSelected_hostComercio() != null ? this.getSelected_hostComercio() : "");
            addToList(list,AttrConstants.SELECTED_DIRECT3D, this.getSelected_direct3D() != null ? this.getSelected_direct3D() : "");
        }
        
        if(affiliation_rentaDolar!=null && !affiliation_rentaDolar.equalsIgnoreCase("0")){//solo si renta en dolares tiene valor se agregan
        	addToList(list,AttrConstants.AFFILIATION_RENTADOLAR, this.getAffiliation_rentaDolar());
            addToList(list,AttrConstants.SELECTED_2000, this.getSelected_2000() != null ? this.getSelected_2000() : "");
            addToList(list,AttrConstants.SELECTED_4000, this.getSelected_4000() != null ? this.getSelected_4000() : "");
        }
        
                
        //....CASHBACK  (save cash)
        //si no eligio cashback
        if(!"X".equalsIgnoreCase(aff_cashback)){
        	aff_commCbChrg = "0.0";
        	aff_commCbChrgDll = "0.0";
        	aff_commCbPymt = "0.0";
        }    
//        cbChrg = cbChrg.replaceAll("[$]", "");
//        cbChrg = cbChrg.replaceAll("[%]", "");
        addToList(list,"aff_commCbPymt", getAff_commCbPymt()!=null && getAff_commCbPymt().trim().length()>0 ? Formatter.formatValue((Double.parseDouble(getAff_commCbPymt())),1):Formatter.formatValue(0d, 1));
        addToList(list,"aff_commCbChrg", getAff_commCbChrg()!=null && getAff_commCbChrg().trim().length()>0 ? Formatter.formatValue((Double.parseDouble(getAff_commCbChrg())),1):Formatter.formatValue(0d, 1));
        addToList(list,"aff_commCbChrgDll", getAff_commCbChrgDll()!=null && getAff_commCbChrgDll().trim().length()>0 ? Formatter.formatValue((Double.parseDouble(getAff_commCbChrgDll())),1):Formatter.formatValue(0d, 1));
        
        addToList(list,"aff_cashback", aff_cashback);
        
        //Atributos del contrato unificado ixeSave
        addToList(list,"amex", ("true".equalsIgnoreCase(amex)?"X":""));
        addToList(list,"tpvUnattended", ("true".equalsIgnoreCase(tpvUnattended)?"X":""));
        addToList(list,"transConciliation", ("true".equalsIgnoreCase(transConciliation)?"X":""));
        addToList(list,"promNoteRqst", ("true".equalsIgnoreCase(promNoteRqst)?"X":""));
        addToList(list,"aff_impulsoCaptacion", ("X".equalsIgnoreCase(aff_impulsoCaptacion)?"X":"")); //Se agrego tenía "true" en lugar de "X" en el primero
        addToList(list,"adminName", adminName);
        addToList(list,"adminEmail", adminEmail);
        addToList(list,"adminFLastName", adminFLastName);
        addToList(list,"adminMLastName", adminMLastName);
        addToList(list,"adminPhone", adminPhone);
        adminComplete = adminName+" "+adminFLastName+" "+adminMLastName;
        addToList(list,"adminComplete", adminComplete);
        addToList(list,"promNoteRqstName1", promNoteRqstName1);
        addToList(list,"promNoteRqstFLastName1", promNoteRqstFLastName1);
        addToList(list,"promNoteRqstMLastName1", promNoteRqstMLastName1);
        addToList(list,"promNoteRqstEmail1", promNoteRqstEmail1);
        addToList(list,"promNoteRqstPhone1", promNoteRqstPhone1);
        promNoteRqstComplete1 = promNoteRqstName1+" "+promNoteRqstFLastName1+" "+promNoteRqstMLastName1;
        addToList(list,"promNoteRqstComplete1", promNoteRqstComplete1);
        addToList(list,"promNoteRqstName2", promNoteRqstName2);
        addToList(list,"promNoteRqstFLastName2", promNoteRqstFLastName2);
        addToList(list,"promNoteRqstMLastName2", promNoteRqstMLastName2);
        addToList(list,"promNoteRqstEmail2", promNoteRqstEmail2);
        addToList(list,"promNoteRqstPhone2", promNoteRqstPhone2);
        promNoteRqstComplete2 = promNoteRqstName2+" "+promNoteRqstFLastName2+" "+promNoteRqstMLastName2;
        addToList(list,"promNoteRqstComplete2", promNoteRqstComplete2);
        addToList(list,"mobilePymnt", "true".equalsIgnoreCase(mobilePymnt)?"X":"");
        addToList(list,"dcc", ("true".equalsIgnoreCase(dcc)?"X":""));
        addToList(list,"red", red);
        addToList(list,"antiguedad", antiguedad);
//        addToList(list, "option3dSecure", ("true".equalsIgnoreCase(option3dSecure)?"X":""));
        
        if(this.getAffiliation_integration().equals("Cybersource Enterprise Revision Manual") || 
    			this.getAffiliation_integration().equals("Cybersource Enterprise Autenticación Selectiva") ||
    			this.getAffiliation_integration().equals("Cybersource Enterprise Call Center") ||
		        this.getAffiliation_integration().equals("Cybersource Call Center") ||
				this.getAffiliation_integration().equals("Cybersource Hosted") ||
				this.getAffiliation_integration().equals("Cybersource Direct") ||
				this.getAffiliation_integration().equals("Sin Cybersource / Sin 3D") ||
				this.getAffiliation_integration().equals("Sin Cybersource / Con 3D") ||
				this.getAffiliation_integration().equals("Payworks Punto de Venta") ||
				this.getAffiliation_integration().equals("Payworks Hosted")){	

        	addToList(list,"transactionFeeCyberMn", transactionFeeCyberMn!=null && transactionFeeCyberMn>0?Formatter.formatValue(transactionFeeCyberMn, 1):"");
            addToList(list,"transactionFeeCyberDlls", transactionFeeCyberDlls!=null && transactionFeeCyberDlls>0?Formatter.formatValue(transactionFeeCyberDlls, 1):"");
            addToList(list,"limitedCoverageCyberDlls", Formatter.formatValue(this.getLimitedCoverageCyberDlls(), 1));
        	addToList(list,"wideCoverageCyberDlls", Formatter.formatValue(this.getWideCoverageCyberDlls(), 1));
        	
            
/*            if(affiliation.getCurrency().equalsIgnoreCase(ApplicationConstants.CURRENCY_PESOS)){
            	if(ApplicationConstants.RENTA_DOLAR_2100.equalsIgnoreCase(affiliation_rentaDolar)){
            		addToList(list,"limitedCoverageCyberDlls", Formatter.formatValue(limitedCoverageCyberDlls, 1));
            	}else if(ApplicationConstants.RENTA_DOLAR_4000.equalsIgnoreCase(affiliation_rentaDolar)){
            		addToList(list,"wideCoverageCyberDlls", Formatter.formatValue(wideCoverageCyberDlls, 1));
            	}
            }else if(affiliation.getCurrency().equalsIgnoreCase(ApplicationConstants.CURRENCY_DOLLAR)){
            	if(ApplicationConstants.RENTA_DOLAR_2100.equalsIgnoreCase(affiliation_rentaDolar)){
            		addToList(list,"limitedCoverageCyberDlls", Formatter.formatValue(limitedCoverageCyberDlls, 1));
            	}else if(ApplicationConstants.RENTA_DOLAR_4000.equalsIgnoreCase(affiliation_rentaDolar)){
            		addToList(list,"wideCoverageCyberDlls", Formatter.formatValue(wideCoverageCyberDlls, 1));
            	}
            }else if(affiliation.getCurrency().equalsIgnoreCase(ApplicationConstants.CURRENCY_AMBOS)){
            	if(ApplicationConstants.RENTA_DOLAR_2100.equalsIgnoreCase(affiliation_rentaDolar)){
//            		addToList(list,"limitedCoverageCyberMn", Formatter.formatValue(limitedCoverageCyberMn, 1));
            		addToList(list,"limitedCoverageCyberDlls", Formatter.formatValue(limitedCoverageCyberDlls, 1));
            	}else if(ApplicationConstants.RENTA_DOLAR_4000.equalsIgnoreCase(affiliation_rentaDolar)){
            		addToList(list,"wideCoverageCyberDlls", Formatter.formatValue(wideCoverageCyberDlls, 1));
//            		addToList(list,"wideCoverageCyberMn", Formatter.formatValue(wideCoverageCyberMn, 1));
            	}
            }
*/         
        }else if(affiliation_alliance.equalsIgnoreCase(ApplicationConstants.ALLIANCE_MICROS)){
        	addToList(list,"transactionFeeMicrosMn", transactionFeeMicrosMn!=null && transactionFeeMicrosMn>0?Formatter.formatValue(transactionFeeMicrosMn, 1):"");
            addToList(list,"transactionFeeMicrosDlls", transactionFeeMicrosDlls!=null && transactionFeeMicrosDlls>0?Formatter.formatValue(transactionFeeMicrosDlls, 1):"");
        }  
        
        
        addToList(list, "affiliation_latitude", getAffiliation_latitude());
        addToList(list, "affiliation_length", getAffiliation_length());
        
        addToList(list, "rentBy", getRentBy());
        addToList(list, "groupNo", getGroupNo());
        
        if(getAffiliation_productdesc().equals("Terminal Personal Banorte (Mpos)")){
        	if(this.getAffiliation().getMposRateByEquipment() > 0){
        		if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)){
        			addToList(list,"otherconcept1mnComplete", this.getAffiliation_rateMposmn() != null ? this.getAffiliation_rateMposmn() : "");
        			addToList(list,"otherconcept1dllsComplete", this.getAffiliation_rateMposdlls() != null ? this.getAffiliation_rateMposdlls() : "");
            	}else if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_PESOS)){
            		addToList(list,"otherconcept1mnComplete", this.getAffiliation_rateMposmn() != null ? this.getAffiliation_rateMposmn() : "");
            		addToList(list,"otherconcept1dllsComplete", "");
            	}if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR)){
            		addToList(list,"otherconcept1mnComplete", "");
            		addToList(list,"otherconcept1dllsComplete", this.getAffiliation_rateMposdlls() != null ? this.getAffiliation_rateMposdlls() : "");
            	}
        	}
        }

        //Garantia Liquida: inicio parte
        //System.out.println("VALORES de aff_garantiaLiquida: "+aff_garantiaLiquida+" y de fianzaOculta:"+fianzaOculta);
        addToList(list,"aff_garantiaLiquida", (("0".equalsIgnoreCase(aff_garantiaLiquida)&&("false".equalsIgnoreCase(fianzaOculta)))?"true":"false")); //Se agrego tenía "true" en lugar de "X" en el primero
    	addToList(list,"aff_montoInicial", aff_auxMontoInicial); 
    	addToList(list,"aff_montoPromDiario", aff_auxMontoPromDiario);
    	addToList(list,"aff_porcentajeGL", aff_porcentajeGL);
    	addToList(list,"aff_excepcionPorceGL", (aff_excepcionPorceGL==null || "".equalsIgnoreCase(aff_excepcionPorceGL))?"":(aff_excepcionPorceGL+" %"));
        //addToList(list,"aff_montoGL", aff_auxMontoGL); //antes, SIN mandar con formato
    	Double valorDouble=0D;
    	if(aff_auxMontoGL.isEmpty()||aff_auxMontoGL==null){ // valida que tenga dato para hacer el parseo
    	}else{
    		valorDouble=Double.parseDouble(aff_auxMontoGL);
    	}
    	//addToList(list,"aff_montoGL", Formatter.formatValue(valorDouble,1).toString());// mal pq si lega vacio hay error    	
    	addToList(list,"aff_montoGL", ("0".equalsIgnoreCase(aff_garantiaLiquida)&&"false".equalsIgnoreCase(fianzaOculta)&&(aff_auxMontoGL != null &&  aff_auxMontoGL.isEmpty()!=true))?Formatter.formatValue(valorDouble,1).toString():"");
    	addToList(list,"montoGLDolares", ("0".equalsIgnoreCase(aff_garantiaLiquida)&&"false".equalsIgnoreCase(fianzaOculta)&&(this.getAffiliation_currency() != null && this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR) || this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)))?Formatter.formatValue(CurrencyConverter.toDollar(valorDouble),1).toString():"");
        addToList(list,"aff_porcentajeInicialGL", aff_auxPorcentajeInicialGL);
        addToList(list,"aff_montoInicialGL", aff_auxMontoInicialGL);
        addToList(list,"aff_porcentajeRestanteGL", aff_auxPorcentajeRestanteGL);
        //addToList(list,"aff_montoRestanteGL", aff_auxMontoRestanteGL); //antes, SIN mandar con formato
 
   //     valorDouble=Double.parseDouble(aff_auxMontoRestanteGL); // mal pq si lega vacio hay error   
   //     addToList(list,"aff_montoRestanteGL", Formatter.formatValue(valorDouble,1).toString()); 
        
        if(aff_auxMontoRestanteGL.isEmpty()||aff_auxMontoRestanteGL==null){
    	}else{
    		valorDouble=Double.parseDouble(aff_auxMontoRestanteGL);
    	}
        
        addToList(list,"aff_montoRestanteGL", ("0".equalsIgnoreCase(aff_garantiaLiquida)&&"false".equalsIgnoreCase(fianzaOculta)&&(aff_auxMontoGL != null &&  aff_auxMontoGL.isEmpty()!=true))?Formatter.formatValue(valorDouble,1).toString():"");
        addToList(list,"montoRestanteGLDolares",("0".equalsIgnoreCase(aff_garantiaLiquida)&&"false".equalsIgnoreCase(fianzaOculta)&&(this.getAffiliation_currency() != null && this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR) || this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)))?Formatter.formatValue(CurrencyConverter.toDollar(valorDouble),1).toString():"");
        addToList(list,"aff_porcentajeDiarioGL", aff_auxPorcentajeDiarioGL);
        addToList(list,"aff_promMontoDiarioGL", aff_auxPromMontoDiarioGL);
        addToList(list,"optionPorcentajeVentasDiarias", ("0".equalsIgnoreCase(auxOptionPorcentajeVentasDiarias)?"X":""));
        addToList(list,"optionMontoFijoDiario", auxOptionMontoFijoDiario);
        addToList(list,"aff_diasAproxGL", aff_auxDiasAproxGL);
        addToList(list,"aff_glOriginal", aff_auxGlOriginal);
        addToList(list,"aff_comentariosDisminucionExcepcionGL", (aff_comentariosDisminucionExcepcionGL==null || "".equalsIgnoreCase(aff_comentariosDisminucionExcepcionGL))?"":(aff_comentariosDisminucionExcepcionGL));
        
        
      //para los checkbox de Anexo E
        addToList(list,"seleccionComercioElectronico", ("Comercio Electronico".equalsIgnoreCase(affiliation.getProductdesc())?"X":""));
        //addToList(list,"seleccionAntiguedadMenor", ("-6".equalsIgnoreCase(antiguedad)?"X":""));
        addToList(list,"seleccionAntiguedadMenor", ((("-6".equalsIgnoreCase(antiguedad))&&(Integer.parseInt(aff_ventasEstimadasMensuales)>=100000)&&(groupNo==null || "".equalsIgnoreCase(groupNo)))?"X":""));
        addToList(list,"seleccionBuroInternoMalo", (("Malo".equalsIgnoreCase(affiliation.getInternalcredithistory()))?"X":""));
        addToList(list,"seleccionCargosPeriodicos", ("Cargo Recurrente".equalsIgnoreCase(affiliation.getProductdesc())?"X":""));
        addToList(list,"seleccionBuroCreditoMalo", (("Malo".equalsIgnoreCase(affiliation.getExternalcredithistory()))?"X":""));
        addToList(list,"seleccionGiroAgregador", ("Agregador".equalsIgnoreCase(affiliation_alliance)?"X":""));        
        //addToList(list,"seleccionDisminucionExcepGL", (("8".equalsIgnoreCase(aff_porcentajeGL)||"Excepcion".equalsIgnoreCase(aff_porcentajeGL)||"disminucion".equalsIgnoreCase(aff_porcentajeGL))?"X":""));
        addToList(list,"seleccionDisminucionExcepGL", (("5".equalsIgnoreCase(aff_porcentajeGL)||"Excepcion".equalsIgnoreCase(aff_porcentajeGL)||"disminucion".equalsIgnoreCase(aff_porcentajeGL))?"X":""));        
        //System.out.println("TRAMITE: "+affiliation.getServicetype().toString());
        addToList(list,"seleccionAlta", ("Alta".equalsIgnoreCase(affiliation.getServicetype().toString())?"X":""));
        
        addToList(list,"seleccionGiroAltoRiesgoGL", ("true".equalsIgnoreCase(seleccionGiroAltoRiesgoGL)?"X":""));

        //addToList(list,"aff_ventasEstimadasMensuales", aff_ventasEstimadasMensuales.toString()); //antes, SIN mandar con formato
        //addToList(list,"aff_montoEstimadoDeTransaccion", aff_montoEstimadoDeTransaccion.toString()); //antes, SIN mandar con formato
        valorDouble=Double.parseDouble(aff_ventasEstimadasMensuales);
   
        addToList(list,"aff_ventasEstimadasMensuales", Formatter.formatValue(valorDouble,1).toString());
        addToList(list,"ventasEstimadasMensualesDolares", ("0".equalsIgnoreCase(aff_garantiaLiquida)&&"false".equalsIgnoreCase(fianzaOculta)&&(this.getAffiliation_currency() != null && this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR) || this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)))?Formatter.formatValue(CurrencyConverter.toDollar(valorDouble),1).toString():"");
        valorDouble=Double.parseDouble(aff_montoEstimadoDeTransaccion);
        
        addToList(list,"aff_montoEstimadoDeTransaccion", Formatter.formatValue(valorDouble,1).toString());
        addToList(list,"montoEstimadoDeTransaccionDolares", ("0".equalsIgnoreCase(aff_garantiaLiquida)&&"false".equalsIgnoreCase(fianzaOculta)&&(this.getAffiliation_currency() != null && this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR) || this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)))?Formatter.formatValue(CurrencyConverter.toDollar(valorDouble),1).toString():"");
        
        if(getIsSeleccionClienteNuevo()){
        	addToList(list,"seleccionClienteNuevo", "X");
        }else{
        	addToList(list,"seleccionClienteActual", "X");
        }
        
        //if(("excepcion".equalsIgnoreCase(aff_porcentajeGL)) || ("disminucion".equalsIgnoreCase(aff_porcentajeGL)) || ("8".equalsIgnoreCase(aff_porcentajeGL))){
        if(("excepcion".equalsIgnoreCase(aff_porcentajeGL)) || ("disminucion".equalsIgnoreCase(aff_porcentajeGL)) || ("5".equalsIgnoreCase(aff_porcentajeGL))){
	        addToList(list,"seleccionExencion", (("excepcion".equalsIgnoreCase(aff_porcentajeGL))?"X":""));
	        //addToList(list,"seleccionDisminucion", (("disminucion".equalsIgnoreCase(aff_porcentajeGL)||("8".equalsIgnoreCase(aff_porcentajeGL)))?"X":""));
	        addToList(list,"seleccionDisminucion", (("disminucion".equalsIgnoreCase(aff_porcentajeGL)||("5".equalsIgnoreCase(aff_porcentajeGL)))?"X":""));
		       
	        Double ventasEstimadasMensuales= Double.parseDouble(aff_ventasEstimadasMensuales);
	        if(ventasEstimadasMensuales<=1000000){
	        	
	        	addToList(list,"seleccionFacturacionMenorUnMillon", "X");
	        	addToList(list,"seleccionFacturacionEntreUnDiezMillones", "");
	        	addToList(list,"seleccionFacturacionMayorDiezMillones", "");
	        		        	
	        	//addToList(list,"campoGlOrignal_menor1mdp", aff_auxGlOriginal);
	        	valorDouble=Double.parseDouble(aff_auxGlOriginal);
	        	addToList(list,"campoGlOrignal_menor1mdp", Formatter.formatValue(valorDouble,1).toString());
	        	addToList(list,"campoGlOrignal_entre1a10mdp", "");
	        	addToList(list,"campoGlOrignal_mayor10mdp", "");
	        	
	        	
	        	//addToList(list,"campoGlDisminucion_menos1mdp", aff_auxMontoGL);
	        	valorDouble=Double.parseDouble(aff_auxMontoGL);
	        	addToList(list,"campoGlDisminucion_menos1mdp", Formatter.formatValue(valorDouble,1).toString());
	        	addToList(list,"campoGlDisminucion_entre1a10mdp", "");
	        	addToList(list,"campoGlDisminucion_mayor10mdp", "");
	        	
	        	//if("8".equalsIgnoreCase(aff_porcentajeGL)){
	        	if("5".equalsIgnoreCase(aff_porcentajeGL)){
	        		
		        	addToList(list,"seleccionOcho_menos1mdp", "X");
		        	addToList(list,"seleccionOcho_entre1a10mdp", "");
		        	addToList(list,"seleccionOcho_mayor10mdp", "");
		        	
		        	addToList(list,"puestoCartaDisminucionExencion", ""
		        			+ "Para Banca Minorista: "
		        			+ "\nBanca: Dir. Regional o Dir. Ej. GEM o Dir. Ej. Pyme o Dir. Comercial GEM o Dir. Comercial Pyme o Dir. "
		        			+ "\nSoluciones Digitales, mancomunado con el área de Producto:  Dir. Solución Adquirente o el Dir. Comercialización Adquirente. "
		        			+ "\nPara Banca Mayorista: "
		        			+ "\nBanca: Dir. Empresarial Territorial o Dir. Ej. Banca Transaccional o Dir. Especialista Ventas Transaccional mancomunado con el área de Producto: Dir. Solución Adquirente o el Dir. Comercialización Adquirente.");
		        	
	        	}else if("disminucion".equalsIgnoreCase(aff_porcentajeGL)){
	        		
	        		addToList(list,"seleccionDisminucion_menos1mdp", "X");
	        		addToList(list,"seleccionDisminucion_entre1a10mdp", "");
	        		addToList(list,"seleccionDisminucion_mayor10mdp", "");
	        		
	        		addToList(list,"porcentajeDisminucion_menos1mdp", aff_excepcionPorceGL);
	        		addToList(list,"porcentajeDisminucion_entre1a10mdp", "");
	        		addToList(list,"porcentajeDisminucion_mayor10mdp", "");
	        		
	        		addToList(list,"puestoCartaDisminucionExencion", ""
	        				+ "Para Banca Minorista: "
	        				+ "\nBanca: Dir. Regional o Dir. Ej. GEM o Dir. Ej. Pyme o Dir. Comercial GEM o Dir. Comercial Pyme o Dir. "
	        				+ "\nSoluciones Digitales,  mancomunado con el área de Producto: Dir. Solución Adquirente o el Dir. Comercialización Adquirente. "
	        				+ "\nPara Banca Mayorista: "
	        				+ "\nBanca: Dir. Empresarial Territorial o Dir. Ej. Banca Transaccional mancomunado con el área de Producto: Dir. Solución Adquirente o el Dir. Comercialización Adquirente.");

	        	}else if("excepcion".equalsIgnoreCase(aff_porcentajeGL)){
	        		
	        		addToList(list,"seleccionExencion_menos1mdp", "X");
	        		addToList(list,"seleccionExencion_entre1a10mdp", "");
	        		addToList(list,"seleccionExencion_mayor10mdp", "");
	        		
	        		addToList(list,"puestoCartaDisminucionExencion", ""
	        				+ "Para Banca Minorista: "
	        				+ "\nBanca: Dir. Regional o Dir. Ej. GEM o Dir. Ej. Pyme o Dir. Comercial GEM o Dir. Comercial Pyme o Dir. "
	        				+ "\nSoluciones Digitales,  mancomunado con el área de Producto: Dir. Solución Adquirente o el Dir. Comercialización Adquirente. "
	        				+ "\nPara Banca Mayorista: "
	        				+ "\nBanca: Dir. Empresarial Territorial o Dir. Ej. Banca Transaccional mancomunado con el área de Producto: Dir. Solución Adquirente o el Dir. Comercialización Adquirente.");


	        	}
	        	
	        	
	        	
	        }else if(ventasEstimadasMensuales>1000000 && ventasEstimadasMensuales<=10000000){
	        	addToList(list,"seleccionFacturacionMenorUnMillon", "");
	        	addToList(list,"seleccionFacturacionEntreUnDiezMillones", "X");
	        	addToList(list,"seleccionFacturacionMayorDiezMillones", "");
	        	
	        	addToList(list,"campoGlOrignal_menor1mdp", "");
	        	//addToList(list,"campoGlOrignal_entre1a10mdp", aff_auxGlOriginal);
	        	valorDouble=Double.parseDouble(aff_auxGlOriginal);
	        	addToList(list,"campoGlOrignal_entre1a10mdp", Formatter.formatValue(valorDouble,1).toString());
	        	addToList(list,"campoGlOrignal_mayor10mdp", "");
	        	
	        	addToList(list,"campoGlDisminucion_menos1mdp", "");
	        	//addToList(list,"campoGlDisminucion_entre1a10mdp", aff_auxMontoGL);
	        	valorDouble=Double.parseDouble(aff_auxMontoGL);
	        	addToList(list,"campoGlDisminucion_entre1a10mdp", Formatter.formatValue(valorDouble,1).toString());
	        	addToList(list,"campoGlDisminucion_mayor10mdp", "");
	        	
	        	//if("8".equalsIgnoreCase(aff_porcentajeGL)){
	        	if("5".equalsIgnoreCase(aff_porcentajeGL)){
	        		
		        	addToList(list,"seleccionOcho_menos1mdp", "");
		        	addToList(list,"seleccionOcho_entre1a10mdp", "X");
		        	addToList(list,"seleccionOcho_mayor10mdp", "");
		        	
		        	addToList(list,"puestoCartaDisminucionExencion", ""
		        			+ "Para Banca Minorista: "
		        			+ "\nBanca: Dir. Regional o Dir. Ej. GEM o Dir. Ej. Pyme o Dir. Comercial GEM mancomunado con el área de Producto: Dir. Ej. Negocio Adquirente. "
		        			+ "\nPara Banca Mayorista: "
		        			+ "\nBanca: Dir. Empresarial Territorial o Dir. Ej. Banca Transaccional mancomunado con el área de Producto: Dir. Ej. Negocio Adquirente");
		        	
	        	}else if("disminucion".equalsIgnoreCase(aff_porcentajeGL)){
	        		
	        		addToList(list,"seleccionDisminucion_menos1mdp", "");
	        		addToList(list,"seleccionDisminucion_entre1a10mdp", "X");
	        		addToList(list,"seleccionDisminucion_mayor10mdp", "");
	        		
	        		addToList(list,"porcentajeDisminucion_menos1mdp", "");
	        		addToList(list,"porcentajeDisminucion_entre1a10mdp", aff_excepcionPorceGL);
	        		addToList(list,"porcentajeDisminucion_mayor10mdp", "");
	        		
	        		addToList(list,"puestoCartaDisminucionExencion", ""
	        				+ "Para Banca Minorista: "
	        				+ "\nBanca: Dir. Regional o Dir. Ej. GEM o Dir. Ej. Pyme o Dir. Comercial GEM mancomunado con el área de Producto: Dir. Ej. Negocio Adquirente. "
	        				+ "\nPara Banca Mayorista: "
	        				+ "\nBanca: Dir. Ej. Empresarial  o Dir. Ej. Banca Transaccional mancomunada con el área de Producto: Dir.  Ej. de Negocio Adquirente.");

	        	}else if("excepcion".equalsIgnoreCase(aff_porcentajeGL)){
	        		
	        		addToList(list,"seleccionExencion_menos1mdp", "");
	        		addToList(list,"seleccionExencion_entre1a10mdp", "X");
	        		addToList(list,"seleccionExencion_mayor10mdp", "");
	        		
	        		addToList(list,"puestoCartaDisminucionExencion", ""
	        				+ "Para Banca Minorista: "
	        				+ "\nBanca: Dir. Regional o Dir. Ej. GEM o Dir. Ej. Pyme o Dir. Comercial GEM mancomunado con el área de Producto: Dir. Ej. Negocio Adquirente. "
	        				+ "\nPara Banca Mayorista: "
	        				+ "\nBanca: Dir. Ej. Empresarial  o Dir. Ej. Banca Transaccional mancomunada con el área de Producto: Dir.  Ej. de Negocio Adquirente.");


	        	}
	        }else if(ventasEstimadasMensuales>10000000){
	        	addToList(list,"seleccionFacturacionMenorUnMillon", "");
	        	addToList(list,"seleccionFacturacionEntreUnDiezMillones", "");
	        	addToList(list,"seleccionFacturacionMayorDiezMillones", "X");
	        	
	        	addToList(list,"campoGlOrignal_menor1mdp", "");
	        	addToList(list,"campoGlOrignal_entre1a10mdp", "");
	        	//addToList(list,"campoGlOrignal_mayor10mdp", aff_auxGlOriginal);
	        	valorDouble=Double.parseDouble(aff_auxGlOriginal);
	        	addToList(list,"campoGlOrignal_mayor10mdp", Formatter.formatValue(valorDouble,1).toString());
	        	
	        	
	        	addToList(list,"campoGlDisminucion_menos1mdp", "");
	        	addToList(list,"campoGlDisminucion_entre1a10mdp", "");
	        	//addToList(list,"campoGlDisminucion_mayor10mdp", aff_auxMontoGL);
	        	valorDouble=Double.parseDouble(aff_auxMontoGL);
	        	addToList(list,"campoGlDisminucion_mayor10mdp", Formatter.formatValue(valorDouble,1).toString());
	        	
	        	//if("8".equalsIgnoreCase(aff_porcentajeGL)){
	        	if("5".equalsIgnoreCase(aff_porcentajeGL)){
	        		
		        	addToList(list,"seleccionOcho_menos1mdp", "");
		        	addToList(list,"seleccionOcho_entre1a10mdp", "");
		        	addToList(list,"seleccionOcho_mayor10mdp", "X");
		        	
		        	addToList(list,"puestoCartaDisminucionExencion", ""
		        			+ "Para Banca Minorista: "
		        			+ "\nBanca: Dir. Regional o Dir. Ej. GEM o Dir. Ej. Pyme  mancomunado con el área de Producto: Dir. Ej. Negocio Adquirente. "
		        			+ "\nPara Banca Mayorista: "
		        			+ "\nBanca: Dir. Empresarial Territorial o Dir. Ej. Banca Transaccional mancomunado con el área de Producto: Dir. Ej. Negocio Adquirente.");
		        	
	        	}else if("disminucion".equalsIgnoreCase(aff_porcentajeGL)){
	        		
	        		addToList(list,"seleccionDisminucion_menos1mdp", "");
	        		addToList(list,"seleccionDisminucion_entre1a10mdp", "");
	        		addToList(list,"seleccionDisminucion_mayor10mdp", "X");
	        		
	        		addToList(list,"porcentajeDisminucion_menos1mdp", "");
	        		addToList(list,"porcentajeDisminucion_entre1a10mdp", "");
	        		addToList(list,"porcentajeDisminucion_mayor10mdp", aff_excepcionPorceGL);
	        		
	        		addToList(list,"puestoCartaDisminucionExencion", ""
	        				+ "Para Banca Minorista: "
	        				+ "\nBanca: Dir. General de Banca Minorista mancomunado con el área de Producto: Dir. General de Medios de Pago. "
	        				+ "\nPara Banca Mayorista: "
	        				+ "\nBanca: Dir. General de Banca Mayorista mancomunado con el área de Producto: Dir. General de Medios de Pago.");

	        	}else if("excepcion".equalsIgnoreCase(aff_porcentajeGL)){
	        		
	        		addToList(list,"seleccionExencion_menos1mdp", "");
	        		addToList(list,"seleccionExencion_entre1a10mdp", "");
	        		addToList(list,"seleccionExencion_mayor10mdp", "X");
	        		
	        		addToList(list,"puestoCartaDisminucionExencion", ""
	        				+ "Para Banca Minorista: "
	        				+ "\nBanca: Dir. General de Banca Minorista mancomunado con el área de Producto: Dir. General de Medios de Pago. "
	        				+ "\nPara Banca Mayorista: "
	        				+ "\nBanca: Dir. General de Banca Mayorista mancomunado con el área de Producto: Dir. General de Medios de Pago.");


	        	}
	        }
        }

      //Garantia Liquida: fin parte
        
        //F-95084: RF001/campos para carta disminucion/exencion
        addToList(list,"numGiro", getClient_categorycode().length()>7?getClient_categorycode().substring(0,6):"000000");
        addToList(list,"descGiro", getClient_categorycode().length()>7?getClient_categorycode().substring(7):getClient_categorycode().substring(0, 200));

       	addToList(list,"seleccion3Dsecure", "Sin Cybersource / Con 3D".equals(this.getAffiliation_integration())?"X":"");
       	addToList(list,"seleccionCybersource", ("Cybersource Enterprise Revision Manual".equals(this.getAffiliation_integration()) || 
       											"Cybersource Enterprise Autenticación Selectiva".equals(this.getAffiliation_integration()) ||
       											"Cybersource Direct".equals(this.getAffiliation_integration()))?"X":"");
  	
      //F-95084: RF002/campos para carta disminucion/exencion
       	addToList(list,"exencionConvenienciaComercialVIP", ("true".equalsIgnoreCase(exencionConvenienciaComercialVIP)?"X":""));
       	//addToList(list,"exencionConvenienciaComercialVIP", (exencionConvenienciaComercialVIP==null || "".equalsIgnoreCase(exencionConvenienciaComercialVIP))?"":(exencionConvenienciaComercialVIP));
       	addToList(list,"exencionOtros", (exencionOtros==null || "".equalsIgnoreCase(exencionOtros))?"":(exencionOtros));
       	addToList(list,"exencionJustificacion", (exencionJustificacion==null || "".equalsIgnoreCase(exencionJustificacion))?"":(exencionJustificacion));
       	
       	//conocimiento del cliente
       	addToList(list,"solvenciaEconimicaSi", ("true".equalsIgnoreCase(solvenciaEconimicaSi)?"X":""));
       	addToList(list,"solvenciaEconimicaNo", ("true".equalsIgnoreCase(solvenciaEconimicaNo)?"X":""));
       	addToList(list,"visitaOcularRecienteSi", ("true".equalsIgnoreCase(visitaOcularRecienteSi)?"X":""));
       	addToList(list,"visitaOcularRecienteNo", ("true".equalsIgnoreCase(visitaOcularRecienteNo)?"X":""));
       	addToList(list,"riesgoReputacionalOperacionalSi", ("true".equalsIgnoreCase(riesgoReputacionalOperacionalSi)?"X":""));
       	addToList(list,"riesgoReputacionalOperacionalNo", ("true".equalsIgnoreCase(riesgoReputacionalOperacionalNo)?"X":""));       	
       	addToList(list,"descBienServicioOfrece", (descBienServicioOfrece==null || "".equalsIgnoreCase(descBienServicioOfrece))?"":(descBienServicioOfrece));
       	addToList(list,"territorioNacionalSi", ("true".equalsIgnoreCase(territorioNacionalSi)?"X":""));
       	addToList(list,"territorioNacionalNo", ("true".equalsIgnoreCase(territorioNacionalNo)?"X":""));    
       	addToList(list,"territorioNacionalEspecificacion", ("true".equalsIgnoreCase(territorioNacionalSi)||territorioNacionalEspecificacion==null || "".equalsIgnoreCase(territorioNacionalEspecificacion))?"":territorioNacionalEspecificacion);
       	addToList(list,"enNombreDeUnTerceroSi", ("true".equalsIgnoreCase(enNombreDeUnTerceroSi)?"X":""));
       	addToList(list,"enNombreDeUnTerceroNo", ("true".equalsIgnoreCase(enNombreDeUnTerceroNo)?"X":""));
       	addToList(list,"enNombreDeUnTerceroEspecificacion", ("true".equalsIgnoreCase(enNombreDeUnTerceroNo) || enNombreDeUnTerceroEspecificacion==null || "".equalsIgnoreCase(enNombreDeUnTerceroEspecificacion))?"":enNombreDeUnTerceroEspecificacion);
       	addToList(list,"antiguedadAnio", (antiguedadAnio==null || "".equalsIgnoreCase(antiguedadAnio))?"":(antiguedadAnio));
       	addToList(list,"antiguedadMeses", (antiguedadMeses==null || "".equalsIgnoreCase(antiguedadMeses))?"":(antiguedadMeses));
       	
       	//Mujer PyME
       	addToList(list,"mujerPyME", (mujerPyME==null || "".equalsIgnoreCase(mujerPyME))?"":(mujerPyME));
       	addToList(list,"esCuentaMujerPyME", (esCuentaMujerPyME==null || "".equalsIgnoreCase(esCuentaMujerPyME))?"":(esCuentaMujerPyME));
       	addToList(list,"cuentaMujerPyMEValidada", (cuentaMujerPyMEValidada==null || "".equalsIgnoreCase(cuentaMujerPyMEValidada))?"":(cuentaMujerPyMEValidada));
       	addToList(list,"noEsCuentaMujerPyMEValidada", (noEsCuentaMujerPyMEValidada==null || "".equalsIgnoreCase(noEsCuentaMujerPyMEValidada))?"":(noEsCuentaMujerPyMEValidada));
       	addToList(list,"desactivarMujerPyME", (desactivarMujerPyME==null || "".equalsIgnoreCase(desactivarMujerPyME))?"":(desactivarMujerPyME));
       	
       	//No existe cuentas en Dolares con Mujer PyME
       	addToList(list,"esCuentaMujerPyMEDlls", (esCuentaMujerPyMEDlls==null || "".equalsIgnoreCase(esCuentaMujerPyMEDlls))?"":(esCuentaMujerPyMEDlls));
       	addToList(list,"cuentaMujerPyMEValidadaDlls", (cuentaMujerPyMEValidadaDlls==null || "".equalsIgnoreCase(cuentaMujerPyMEValidadaDlls))?"":(cuentaMujerPyMEValidadaDlls));
       	addToList(list,"noEsCuentaMujerPyMEValidadaDlls", (noEsCuentaMujerPyMEValidadaDlls==null || "".equalsIgnoreCase(noEsCuentaMujerPyMEValidadaDlls))?"":(noEsCuentaMujerPyMEValidadaDlls));
       	
       	addToList(list,"esClienteMujerPyME", (esClienteMujerPyME==null || "".equalsIgnoreCase(esClienteMujerPyME))?"":(esClienteMujerPyME));
       	addToList(list,"clienteMujerPyMEValidado", (clienteMujerPyMEValidado==null || "".equalsIgnoreCase(clienteMujerPyMEValidado))?"":(clienteMujerPyMEValidado));
       	addToList(list,"noEsClienteMujerPyMEValidado", (noEsClienteMujerPyMEValidado==null || "".equalsIgnoreCase(noEsClienteMujerPyMEValidado))?"":(noEsClienteMujerPyMEValidado));
       	
       	
       	
       	
        contract.setContractAttributeCollection(list);
        contractBean.update(contract);
        pdfTemplateBinding = new PdfTemplateBindingContract();
        pdfTemplateBinding.setContract(contract);
        return true;
    }
    
    
    /**
     * Metodo para validar los datos del contacto desarrollador en caso de haber elegido comercio electronico.
     * Si no hay error se manda a guardar el contrato completo, si no regresa a la pantalla marcando el error.
     * @author gmerla
     * @return 
     */
    public String validarDatosCE(){
    	ContractMessageErrors errors;
    	ordenErrorsList.clear();
    	if(affiliation.getProductdesc()!=null && "Comercio Electronico".equalsIgnoreCase(affiliation.getProductdesc())){
    		if(clientContact2.getName().trim().isEmpty()){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar Contacto Desarrollador Comercio Electronico - Nombre/Razon Social");
				ordenErrorsList.add(errors);
  			}
			if(clientContact2.getEmail().trim().isEmpty()){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de capturar Contacto Desarrollador Comercio Electronico - Correo Electronico.");
				ordenErrorsList.add(errors);
			}
    	}
    	
    	String result="SUCCESS";
    	if (!ordenErrorsList.isEmpty()){
    		result= "UNSUCCESS";
    	}else{
    		if(saveCompleteContract().equalsIgnoreCase("FAILED")){
    			result= "UNSUCCESS";
    		}
    	}
    	return result;
    	
		
    }
    
    private void initializeDllsFields(){
		this.setAffiliation_avcommisiontcdlls(ApplicationConstants.ZERO_DOUBLE_STR);
		this.setAffiliation_avcommisiontddlls(ApplicationConstants.ZERO_DOUBLE_STR);
		this.setAffiliation_avcommisionintnldlls(ApplicationConstants.ZERO_DOUBLE_STR);
		
		this.setAffiliation_commisiontcdlls(ApplicationConstants.ZERO_DOUBLE_STR);
		this.setAffiliation_commisiontddlls(ApplicationConstants.ZERO_DOUBLE_STR);
		this.setAffiliation_commisionintnldlls(ApplicationConstants.ZERO_DOUBLE_STR);
		

    	//transcriptor
    	this.setAffiliation_transcriptorratedlls(new Double(0));
    	//renta mensual
    	this.setAffiliation_monthlyratedlls(new Double(0));
   
		//Cuota de Afiliaci�n
//    	if(affiliation.getCurrency().equalsIgnoreCase("Pesos")){
    		this.setAffiliation_affiliationratedlls(new Double(0));  
//    	}
		//Comision por equipo mpos
    	this.setAffiliation_rateMposdlls(new Double(0));
    	//facturacion mensual requerida
    	this.setAffiliation_monthlyinvoicingmindlls(new Double(0));
    	//Cobro por no cumplir con la facturaci�n m�nima
    	this.setAffiliation_failmonthlyinvoicingdlls(new Double(0));
    	//Saldo promedio Mensual
    	this.setAffiliation_minimiunbalancedlls(new Double(0));
    	//Comision por incumplimiento Saldo Promedio Mensual
    	this.setAffiliation_failminimiunbalancedlls(new Double(0));    	
    	//Pagare Minimo
    	this.setAffiliation_promnotedlls(new Double(0));
    	//Comision por incumplimiento Pagare minimo
    	this.setAffiliation_failpormnotedlls(new Double(0));
    	//Servicio de verificación de domicilio por cada consulta
    	this.setAffiliation_avsdlls(new Double(0));
    	//Cuota Activación 3D Secure
    	this.setAffiliation_activation3dsdlls(new Double(0));
    	//Renta Mensual 3D Secure
    	this.setAffiliation_monthlyrate3dsdlls(new Double(0));  //para hacerlo editable

    	//otro concepto
    	this.setAffiliation_otherconcept1dlls(new Double(0));
    	
    	transactionFeeCyberDlls=new Double(0);
    	transactionFeeMicrosDlls=new Double(0);
    	//wideCoverageCyberDlls=new Double(0);
    	//limitedCoverageCyberDlls=new Double(0);
    	
    	//Renta a Sustituir
    	this.setReplaceAmountratedlls(ApplicationConstants.ZERO_DOUBLE_STR);
    }

    private void initializePesosFields(){
		this.setAffiliation_avcommisiontcmn(ApplicationConstants.ZERO_DOUBLE_STR);
		this.setAffiliation_avcommisiontdmn(ApplicationConstants.ZERO_DOUBLE_STR);
		this.setAffiliation_avcommisionintnlmn(ApplicationConstants.ZERO_DOUBLE_STR);
		
		this.setAffiliation_commisiontcmn(ApplicationConstants.ZERO_DOUBLE_STR);
		this.setAffiliation_commisiontdmn(ApplicationConstants.ZERO_DOUBLE_STR);
		this.setAffiliation_commisionintnlmn(ApplicationConstants.ZERO_DOUBLE_STR);

    	//transcriptor
    	this.setAffiliation_transcriptorratemn(new Double(0));
    	//renta mensual
    	this.setAffiliation_monthlyratemn(new Double(0));
   
		//Cuota de Afiliaci�n
//    	if(affiliation.getCurrency().equalsIgnoreCase("Dolares")){
    	this.setAffiliation_affiliationratemn(new Double(0)); 
//    	}
    	//Comision por equipo mpos
    	this.setAffiliation_rateMposmn(new Double(0));
    	//facturacion mensual requerida
    	this.setAffiliation_monthlyinvoicingminmn(new Double(0));
    	//Cobro por no cumplir con la facturaci�n m�nima
    	this.setAffiliation_failmonthlyinvoicingmn(new Double(0));
    	//Saldo promedio Mensual
    	this.setAffiliation_minimiunbalancemn(new Double(0));
    	//Comision por incumplimiento Saldo Promedio Mensual
    	this.setAffiliation_failminimiunbalancemn(new Double(0));
    	//Pagare Minimo
    	this.setAffiliation_promnotemn(new Double(0));
    	//Comision por incumplimiento Pagare minimo
    	this.setAffiliation_failpromnotemn(new Double(0));
    	//Servicio de verificación de domicilio por cada consulta
    	this.setAffiliation_avsmn(new Double(0));
    	//Cuota Activación 3D Secure
    	this.setAffiliation_activation3dsmn(new Double(0));
    	//Renta Mensual 3D Secure
    	this.setAffiliation_monthlyrate3dsmn(new Double(0));

    	//otro concepto
    	this.setAffiliation_otherconcept1mn(new Double(0));
    	
    	transactionFeeCyberMn=new Double(0);
    	transactionFeeMicrosMn=new Double(0);
    	limitedCoverageCyberMn=new Double(0);
    	wideCoverageCyberMn=new Double(0);
    	
    	//Renta a Sustituir
    	this.setReplaceAmountratemn(ApplicationConstants.ZERO_DOUBLE_STR);

     }
    
      //Accion para editar
     public void setEditForm() {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        Map<String, String> params = fCtx.getExternalContext().getRequestParameterMap(); 
        Integer idContract = Integer.parseInt(params.get("contractId"));
        
        String AQUIRER_FILLS[] = {"commchargetype","affiliation_duedate",
                                  "avcommisiontcmnComplete","client_messageservice", "client_fsname","client_fsrfc",
                                  "client_fsstreet", "client_fsnum", "client_fscolony", "client_fscounty", 
                                  "client_fszipcode", "client_fscity", "client_fsstate", "client_fsemail", "client_fsphone",
                                  "client_fsphoneext", "client_fsfax","client_fsfaxext","affiliation_otherComercialPlan",
                                  ApplicationConstants.FIELDNAME_CHARGETYPE,ApplicationConstants.FIELDNAME_ALLIANCE, // checar en produccion si al editar un folio con alianza y paquete se conservan los valores sino es necesario agregar esta linea
                                  //Tiempo Aire Netpay
                                  "affiliation_tiempoaire", "affiliation_telcel", "affiliation_movistar","affiliation_iusacell", "affiliation_nextel", // estos campos deben estar en la tabla affiliation y  no solo como atributos, pero hay que hacer pruebas porque ya tiene demasiados campos esa tabla y ya llego al tope, mientras solo se tomaran como atributos
                                  AttrConstants.AFFILIATION_TPV_PAYROLL,AttrConstants.TPV_NUMBER_EMPLOYEE,AttrConstants.TPV_PENALTY,
                                  AttrConstants.OPTION_MONTHLY_RATE_MN,AttrConstants.REPLACE_AMOUNT_RATE_MN,
                                  AttrConstants.OPTION_MONTHLY_RATE_DLLS,AttrConstants.REPLACE_AMOUNT_RATE_DLLS,
                                  AttrConstants.AFFILIATION_TPV_PAYROLL_SELECTED,AttrConstants.AFFILIATION_INTEGRATION,
                                  AttrConstants.SELECTED_HOST_BANORTE,AttrConstants.SELECTED_REVISION,
                                  AttrConstants.SELECTED_HOST_COMERCIO,
                                  AttrConstants.SELECTED_DIRECT3D,
                                  AttrConstants.AFFILIATION_RENTADOLAR,
                                  AttrConstants.SELECTED_2000,AttrConstants.SELECTED_4000,
                                  "aff_cashback", "aff_commCbChrg", "aff_commCbPymt", "aff_commCbChrgDll",
                                  "amex", "tpvUnattended", "adminName","adminFLastName","adminMLastName","adminPhone","adminEmail",
                                  "transConciliation", "promNoteRqst",  "promNoteRqstName1", "promNoteRqstFLastName1", "promNoteRqstMLastName1",
                                  "promNoteRqstEmail1", "promNoteRqstPhone1", "promNoteRqstComplete1", "promNoteRqstName2", "promNoteRqstFLastName2",
                                  "promNoteRqstMLastName2", "promNoteRqstEmail2","promNoteRqstPhone2", "promNoteRqstComplete2", "ownTpvPinpad", "mobilePymnt",
                                  "aff_impulsoCaptacion","dcc", "red", "transactionFeeCyberMn", "limitedCoverageCyberMn", "wideCoverageCyberMn", "transactionFeeMicrosMn",
                                  "transactionFeeCyberDlls", "limitedCoverageCyberDlls", "wideCoverageCyberDlls", "transactionFeeMicrosDlls", "antiguedad",
                                  "affiliation_latitude", "affiliation_length", "rentBy", "groupNo",
                                  "aff_garantiaLiquida",
							      "aff_montoInicial",
							      "aff_montoPromDiario",
							      "aff_porcentajeGL",
							      "aff_excepcionPorceGL",
							      "aff_montoGL",
							      "aff_porcentajeInicialGL",
							      "aff_montoInicialGL",
							      "aff_porcentajeRestanteGL",
							      "aff_montoRestanteGL",
							      "aff_porcentajeDiarioGL",
							      "aff_promMontoDiarioGL",							
							      "optionPorcentajeVentasDiarias",
							      "optionMontoFijoDiario",							        
							      "aff_ventasEstimadasMensuales",
							      "aff_montoEstimadoDeTransaccion",
							      "aff_diasAproxGL",
							      "aff_glOriginal",
							      "aff_comentariosDisminucionExcepcionGL",
							      //motivos excencion
							      "exencionConvenienciaComercialVIP",
							      "exencionOtros",
							      "exencionJustificacion",
							      //conocimiento del cliente
							      "solvenciaEconimicaSi",
							      "solvenciaEconimicaNo",
							      "visitaOcularRecienteSi",
							      "visitaOcularRecienteNo",
							      "riesgoReputacionalOperacionalSi",
							      "riesgoReputacionalOperacionalNo",
							      "descBienServicioOfrece",
							      "territorioNacionalSi",
							      "territorioNacionalNo",
							      "territorioNacionalEspecificacion",
							      "enNombreDeUnTerceroSi",
							      "enNombreDeUnTerceroNo",
							      "enNombreDeUnTerceroEspecificacion",
							      "antiguedadAnio",
							      "antiguedadMeses",
							      //Mujer PyME
							      "mujerPyME",
							      "esCuentaMujerPyME",
							      "cuentaMujerPyMEValidada",
							      "noEsCuentaMujerPyMEValidada",
							      "desactivarMujerPyME",
							      "esCuentaMujerPyMEDlls",
							      "cuentaMujerPyMEValidadaDlls",
							      "noEsCuentaMujerPyMEValidadaDlls",
							      "esClienteMujerPyME",
							      "clienteMujerPyMEValidado",
							      "noEsClienteMujerPyMEValidado"
                                 };
     

        Contract contract_ = contractBean.findById(idContract);
        if(contract_.getProduct().getProductid()==6){ // Si el contrato tiene un producto de tipo OIP se prende la bandera
        	this.isOIP=true;
        } 
        if (contract_.getContractAttributeCollection()!=null){
         Map<String, String> map=  this.getContractAttributeFills(contract_,AQUIRER_FILLS);        

         
         if(map.get("commchargetype").trim().length()==0){
             if(map.get("avcommisiontcmnComplete").startsWith("$")){                
                this.setCommchargetype("Monto"); 
             }
            else
               this.setCommchargetype("Porcentaje");               
         }    
         else    
            this.setCommchargetype(map.get("commchargetype")); 

         this.setAffiliation_duedate(map.get("affiliation_duedate"));         
         this.setCelebrationplace(map.get("celebrationplace"));
         this.setCelebrationstate(map.get("celebrationstate"));
         this.setCelebrationdate(map.get("celebrationdate"));
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
         
         //Joseles(15/Nov/2011): Se utilizara el atributo branchcounty para colocar lo escrito el el campo "Especifique"
         //this.setBranchcounty(map.get("branchcounty"));
         
       //Omar (14/Marzo/2012): Se Agrego el atributo para el campo de 'Especifique' que es el campo  affiliation_otherComercialPlan
         this.setAffiliation_otherComercialPlan(map.get("affiliation_otherComercialPlan"));
         
         
         this.setOfficername(map.get("officername"));
         this.setOfficerlastname(map.get("officerlastname"));
         this.setOfficermothersln(map.get("officermothersln"));
         this.setOfficerempnumber(map.get("officerempnumber"));
         this.setOfficerposition(map.get("officerposition"));
         this.setOfficerebankingname(map.get("officerebankingname"));
         this.setOfficerebankinglastname(map.get("officerebankinglastname"));
         this.setOfficerebankingmothersln(map.get("officerebankingmothersln"));
         this.setOfficerebankingempnumber(map.get("officerebankingempnumber"));
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
         
         this.setClient_merchantname(map.get("client_merchantname"));
         this.setClient_messageservice(map.get("client_messageservice"));
         this.setClient_areacode(map.get("client_areacode")!=null && map.get("client_areacode").trim().length()>0?Integer.parseInt(map.get("client_areacode")):0);
         this.setClient_state(map.get("client_state"));
         this.setClient_city(map.get("client_city"));
         this.setClient_colony(map.get("client_colony"));         
         this.setClient_county(map.get("client_county"));
         this.setClient_email(map.get("client_email"));
         this.setClient_faxext(map.get("client_faxext")!=null && map.get("client_faxext").trim().length()>0?Integer.parseInt(map.get("client_faxext")):0);
         this.setClient_fax(map.get("client_fax")!=null && map.get("client_fax").trim().length()>0 ? Integer.parseInt(map.get("client_fax")):0);
         this.setClient_fiscalname(map.get("client_fiscalname"));
         this.setClient_fiscaltype(map.get("client_fiscaltype")!=null && map.get("client_fiscaltype").trim().length()>0 ?Integer.parseInt(map.get("client_fiscaltype")):0);
         this.setClient_numext(map.get("client_numext"));
         this.setClient_numint(map.get("client_numint"));
         this.setClient_phone(map.get("client_phone")!=null && map.get("client_phone").trim().length()>0 ? Integer.parseInt(map.get("client_phone")):0);
         this.setClient_phoneext(map.get("client_phoneext")!=null && map.get("client_phoneext").trim().length()>0 ? Integer.parseInt(map.get("client_phoneext")):0);
         this.setClient_rfc(map.get("client_rfc"));         
         this.setClient_site(map.get("client_site"));         
         this.setClient_street(map.get("client_street"));
         this.setClient_zipcode(map.get("client_zipcode")!=null && map.get("client_zipcode").trim().length()>0 ? Integer.parseInt(map.get("client_zipcode")):0);

         this.setLegalrepresentative_email_1(map.get("legalrepresentative_email_1"));
         this.setLegalrepresentative_email_2(map.get("legalrepresentative_email_2"));
         this.setLegalrepresentative_lastname_1(map.get("legalrepresentative_lastname_1"));
         this.setLegalrepresentative_lastname_2(map.get("legalrepresentative_lastname_2"));
         this.setLegalrepresentative_mothersln_1(map.get("legalrepresentative_mothersln_1"));
         this.setLegalrepresentative_mothersln_2(map.get("legalrepresentative_mothersln_2"));
         this.setLegalrepresentative_name_1(map.get("legalrepresentative_name_1"));
         this.setLegalrepresentative_name_2(map.get("legalrepresentative_name_2"));
         this.setLegalrepresentative_rfc_1(map.get("legalrepresentative_rfc_1"));
         this.setLegalrepresentative_rfc_2(map.get("legalrepresentative_rfc_2"));
           
         this.setClientcontact_email1(map.get("clientcontact_email1"));
         this.setClientcontact_lastname1(map.get("clientcontact_lastname1"));
         this.setClientcontact_mothersln1(map.get("clientcontact_mothersln1"));
         this.setClientcontact_name1(map.get("clientcontact_name1"));
         this.setClientcontact_position1(map.get("clientcontact_position1"));
         if(map.get("clientcontact_phone1")!=null && map.get("clientcontact_phone1").trim().length()>0)
            this.setClientcontact_phone1(Integer.parseInt(map.get("clientcontact_phone1")));
         if(map.get("clientcontact_phoneext1")!=null && map.get("clientcontact_phoneext1").trim().length()>0)
            this.setClientcontact_phoneext1(Integer.parseInt(map.get("clientcontact_phoneext1")));

         this.setClientcontact_email2(map.get("clientcontact_email2"));
//         this.setClientcontact_lastname2(map.get("clientcontact_lastname2"));
//         this.setClientcontact_mothersln2(map.get("clientcontact_mothersln2"));
         if(map.get("clientcontact_lastname2")!=null){
        	 String name = map.get("clientcontact_name2");
        	 String paterno = map.get("clientcontact_lastname2");
        	 String materno = map.get("clientcontact_mothersln2");
        	 if(name!=null && paterno !=null && materno !=null){
        		 this.setClientcontact_name2(name+" "+paterno+" "+materno);	 
        	 }
         }else{
        	 this.setClientcontact_name2(map.get("clientcontact_name2"));
         }
         this.setClientcontact_position2(map.get("clientcontact_position2"));   
         if(map.get("clientcontact_phone2")!=null && map.get("clientcontact_phone2").trim().length()>0)
            this.setClientcontact_phone2(Integer.parseInt(map.get("clientcontact_phone2")));
         if(map.get("clientcontact_phoneext2")!=null && map.get("clientcontact_phoneext2").trim().length()>0)
            this.setClientcontact_phoneext2(Integer.parseInt(map.get("clientcontact_phoneext2")));
         
         
         this.setClient_fsname(map.get("client_fsname"));
         this.setClient_fsstreet(map.get("client_fsstreet"));
         this.setClient_fsstate(map.get("client_fsstate"));
         this.setClient_fscity(map.get("client_fscity"));
         this.setClient_fscolony(map.get("client_fscolony"));
         this.setClient_fscounty(map.get("client_fscounty"));
         this.setClient_fsemail(map.get("client_fsemail"));
         
         //RECIPROCIDAD O FACTURACION
         this.setAffiliation_tpv_payroll(map.get(AttrConstants.AFFILIATION_TPV_PAYROLL));
         this.setTpv_number_employee(map.get(AttrConstants.TPV_NUMBER_EMPLOYEE));
         this.setTpv_penalty(map.get(AttrConstants.TPV_PENALTY));
         this.setOptionMonthlyratemn(map.get( AttrConstants.OPTION_MONTHLY_RATE_MN));
         this.setReplaceAmountratemn(map.get(AttrConstants.REPLACE_AMOUNT_RATE_MN));
         this.setOptionMonthlyratedlls(map.get(AttrConstants.OPTION_MONTHLY_RATE_DLLS));
         this.setReplaceAmountratedlls(map.get(AttrConstants.REPLACE_AMOUNT_RATE_DLLS));
        // this.setAffiliation_nominaselected(map.get(AttrConstants.AFFILIATION_TPV_PAYROLL_SELECTED));
         
         //CYBERSOURCE
         this.setAffiliation_integration(map.get(AttrConstants.AFFILIATION_INTEGRATION));
         this.setAffiliation_rentaDolar(map.get(AttrConstants.AFFILIATION_RENTADOLAR));
         
         
         if(map.get("client_fsfax")!=null && map.get("client_fsfax").trim().length()>0)
            this.setClient_fsfax(Integer.parseInt(map.get("client_fsfax")));
         if (map.get("client_fsfaxext")!=null && map.get("client_fsfaxext").trim().length()>0)
            this.setClient_fsfaxext(Integer.parseInt(map.get("client_fsfaxext")));         
         
         
         this.setClient_fsnum(map.get("client_fsnum"));
         
         if (map.get("client_fsphone")!=null && map.get("client_fsphone").trim().length()>0)
            this.setClient_fsphone(Integer.parseInt(map.get("client_fsphone")));
         
         if(map.get("client_fsphoneext")!=null && map.get("client_fsphoneext").trim().length()>0)
            this.setClient_fsphoneext(Integer.parseInt(map.get("client_fsphoneext")));
         
         this.setClient_fsrfc(map.get("client_fsrfc"));         
         this.setClient_fszipcode(map.get("client_fszipcode"));        
                 
         //this.setClient_sic(contract_.getClient().getSic());
         this.setClient_sic(map.get("client_sic"));

         //Alianza y Paquete
         this.setAffiliation_alliance_hidd(map.get(ApplicationConstants.FIELDNAME_ALLIANCE));
         
         //TODO: quitar despues de un tiempo solo es temporal
         if("OCCIDENTE 2014".equalsIgnoreCase(map.get(ApplicationConstants.FIELDNAME_CHARGETYPE))){
        	 this.setAffiliation_chargeType_hidd("OCCIDENTE 2016");
         }else{
        	 this.setAffiliation_chargeType_hidd(map.get(ApplicationConstants.FIELDNAME_CHARGETYPE));
         }
         
         
         
         //Tiempo Aire Netpay

          this.setAffiliation_tiempoaire(map.get("affiliation_tiempoaire"));
          this.setAffiliation_telcel(map.get("affiliation_telcel"));
          this.setAffiliation_movistar(map.get("affiliation_movistar"));
          this.setAffiliation_iusacell(map.get("affiliation_iusacell"));
          this.setAffiliation_nextel(map.get("affiliation_nextel"));
          
        

        //edit impulso        
	        if(map.get("aff_impulsoCaptacion")!= null && map.get("aff_impulsoCaptacion").equals("X")){
	        	this.setAff_impulsoCaptacion("true");
	        }else{
	            this.setAff_impulsoCaptacion("false");
	        }
	                  
            //edit cashback        
	        if(map.get("aff_cashback")!= null && map.get("aff_cashback").equals("X")){
	        	this.setAff_cashback("true");
	        }else{
	            this.setAff_cashback("false");
	        }
	        String cbChrg = map.get("aff_commCbChrg");
	        if(cbChrg.isEmpty()){
	        	cbChrg = "0.0";
	        }
	        cbChrg = cbChrg.replaceAll("[$]", "");
	        cbChrg = cbChrg.replaceAll("[%]", "");
	        this.setAff_commCbChrg(cbChrg);
	        String pymt = map.get("aff_commCbPymt");
	        if(pymt.isEmpty()){
	        	pymt = "0.0";
	        }
	        pymt = pymt.replaceAll("[$]", "");
	        pymt = pymt.replaceAll("[%]", "");
	        this.setAff_commCbPymt(pymt);
	        
	        String cbChrgDll = map.get("aff_commCbChrgDll");
	        if(cbChrgDll.isEmpty()){
	        	cbChrgDll = "0.0";
	        }
	        cbChrgDll = cbChrgDll.replaceAll("[$]", "");
	        cbChrgDll = cbChrgDll.replaceAll("[%]", "");
	        
	        this.setAff_commCbChrgDll(cbChrgDll);
        
        //ixeEdit
        if(map.get("amex")!= null && "X".equalsIgnoreCase(map.get("amex"))){
        	amex = "true";
        }else{amex="false";}
        if(map.get("tpvUnattended")!=null && "X".equalsIgnoreCase(map.get("tpvUnattended"))){
        	tpvUnattended = "true";
        }else{tpvUnattended="false";}
        if(map.get("transConciliation")!=null && "X".equalsIgnoreCase(map.get("transConciliation"))){
        	transConciliation = "true";
        }else{transConciliation="false";}
        if(map.get("promNoteRqst")!=null && "X".equalsIgnoreCase(map.get("promNoteRqst"))){
        	promNoteRqst = "true";
        }else{promNoteRqst="false";}
        adminName = map.get("adminName");
        adminFLastName = map.get("adminFLastName");
        adminMLastName = map.get("adminMLastName");
        adminPhone = map.get("adminPhone");
        adminEmail = map.get("adminEmail");
        promNoteRqstName1 = map.get("promNoteRqstName1");
        promNoteRqstFLastName1 = map.get("promNoteRqstFLastName1");
        promNoteRqstMLastName1 = map.get("promNoteRqstMLastName1");
        promNoteRqstEmail1 = map.get("promNoteRqstEmail1");
        promNoteRqstPhone1 = map.get("promNoteRqstPhone1");
        promNoteRqstName2 = map.get("promNoteRqstName2");
        promNoteRqstFLastName2 = map.get("promNoteRqstFLastName2");
        promNoteRqstMLastName2 = map.get("promNoteRqstMLastName2");
        promNoteRqstEmail2 = map.get("promNoteRqstEmail2");
        promNoteRqstPhone2 = map.get("promNoteRqstPhone2");
        mobilePymnt = "X".equalsIgnoreCase(map.get("mobilePymnt"))?"true":"false";
        dcc = "X".equalsIgnoreCase(map.get("dcc"))?"true":"false";
        red = map.get("red");
               
        String valor = map.get("transactionFeeCyberDlls");
        valor = valor.replaceAll("[$]", "");
        valor = valor.replaceAll("[,]", "");
        transactionFeeCyberDlls = (valor!=null&&!valor.isEmpty())?Double.valueOf(valor):null;
        valor = map.get("transactionFeeCyberMn");
        valor = valor.replaceAll("[$]", "");
        valor = valor.replaceAll("[,]", "");
        transactionFeeCyberMn = (valor!=null&&!valor.isEmpty())?Double.valueOf(valor):null;
        valor = map.get("transactionFeeMicrosDlls");
        valor = valor.replaceAll("[$]", "");
        valor = valor.replaceAll("[,]", "");
        transactionFeeMicrosDlls = (valor!=null&&!valor.isEmpty())?Double.valueOf(valor):null;
        valor = map.get("transactionFeeMicrosMn");
        valor = valor.replaceAll("[$]", "");
        valor = valor.replaceAll("[,]", "");
        transactionFeeMicrosMn = (valor!=null&&!valor.isEmpty())?Double.valueOf(valor):null;
        valor = map.get("limitedCoverageCyberDlls");
        valor = valor.replaceAll("[$]", "");
        valor = valor.replaceAll("[,]", "");
        limitedCoverageCyberDlls = (valor!=null&&!valor.isEmpty())?Double.valueOf(valor):null;
        valor = map.get("limitedCoverageCyberMn");
        valor = valor.replaceAll("[$]", "");
        valor = valor.replaceAll("[,]", "");
        limitedCoverageCyberMn = (valor!=null&&!valor.isEmpty())?Double.valueOf(valor):null;
        valor = map.get("wideCoverageCyberDlls");
        valor = valor.replaceAll("[$]", "");
        valor = valor.replaceAll("[,]", "");
        wideCoverageCyberDlls= (valor!=null&&!valor.isEmpty())?Double.valueOf(valor):null;
        valor = map.get("wideCoverageCyberMn");
        valor = valor.replaceAll("[$]", "");
        valor = valor.replaceAll("[,]", "");
        wideCoverageCyberMn = (valor!=null&&!valor.isEmpty())?Double.valueOf(valor):null;
        
        antiguedad=map.get("antiguedad")!=null?map.get("antiguedad"):"6";
//        option3dSecure="X".equalsIgnoreCase(map.get("option3dSecure"))?"true":"false";
       
        this.setAffiliation_latitude( map.get("affiliation_latitude") );
        this.setAffiliation_length( map.get("affiliation_length") ); 
        
        this.setRentBy( map.get("rentBy") );
        this.setGroupNo( map.get("groupNo") );
        
        //Garantia Liquida
      //edit garantia liquida
        if(map.get("aff_garantiaLiquida")!= null && !map.get("aff_garantiaLiquida").isEmpty()){
        	//System.out.println("En Edit +"+aff_garantiaLiquida);
        	if(map.get("aff_garantiaLiquida").equals("true")){
        		this.setAff_garantiaLiquida("0");
        	}
        	else if(map.get("aff_garantiaLiquida").equals("false")){
        		this.setAff_garantiaLiquida("1");
        	}
        }else{
            this.setAff_garantiaLiquida("0");
        }
	    //"aff_montoInicial",
	    this.setAff_montoInicial(map.get("aff_montoInicial"));  
	    //  "aff_montoPromDiario",
	    this.setAff_montoPromDiario(map.get("aff_montoPromDiario"));
	    //  "aff_porcentajeGL",	    
	    this.setAff_porcentajeGL(map.get("aff_porcentajeGL"));  
	    //	"aff_excepcionPorceGL"
	    //System.out.println("en editar: "+map.get("aff_excepcionPorceGL"));
	    //this.setAff_excepcionPorceGL(map.get("aff_excepcionPorceGL");
	    this.setAff_excepcionPorceGL(map.get("aff_excepcionPorceGL").replaceAll(" %", ""));
	    //  "aff_montoGL",
	    //this.setAff_montoGL(map.get("aff_montoGL"));
	    String valor2=map.get("aff_montoGL");//para quitar formato $, ',' y .00
	    valor2 = valor2.replaceAll("[$]", "");
	    valor2 = valor2.replaceAll("[,]", "");
	    valor2 = valor2.replaceAll("\\.00", "");
        this.setAff_montoGL(valor2);
	    
	    //  "aff_porcentajeInicialGL",
	    this.setAff_porcentajeInicialGL(map.get("aff_porcentajeInicialGL"));
	    //  "aff_montoInicialGL",
	    this.setAff_montoInicialGL(map.get("aff_montoInicialGL"));
	    //  "aff_porcentajeRestanteGL",
	    this.setAff_porcentajeRestanteGL(map.get("aff_porcentajeRestanteGL"));
	    //  "aff_montoRestanteGL",
	    //this.setAff_montoRestanteGL(map.get("aff_montoRestanteGL"));
	    valor2=map.get("aff_montoRestanteGL");//para quitar formato $, ',' y .00
	    valor2 = valor2.replaceAll("[$]", "");
	    valor2 = valor2.replaceAll("[,]", "");
	    valor2 = valor2.replaceAll("\\.00", "");
        this.setAff_montoRestanteGL(valor2);
	    
	    //  "aff_porcentajeDiarioGL",
	    this.setAff_porcentajeDiarioGL(map.get("aff_porcentajeDiarioGL"));
	    //  "aff_promMontoDiarioGL",
	    this.setAff_promMontoDiarioGL(map.get("aff_promMontoDiarioGL"));
	    //  "optionPorcentajeVentasDiarias",
	    this.setOptionPorcentajeVentasDiarias(map.get("optionPorcentajeVentasDiarias"));
	    //  "optionMontoFijoDiario",
	    this.setOptionMontoFijoDiario(map.get("optionMontoFijoDiario"));
	    //  "aff_ventasEstimadasMensuales",
	    //this.setAff_ventasEstimadasMensuales(map.get("aff_ventasEstimadasMensuales"));//recupera con formato
	    valor2=map.get("aff_ventasEstimadasMensuales");//para quitar formato $, ',' y .00
	    valor2 = valor2.replaceAll("[$]", "");
	    valor2 = valor2.replaceAll("[,]", "");
	    valor2 = valor2.replaceAll("\\.00", "");
        this.setAff_ventasEstimadasMensuales(valor2);
	    
	    //  "aff_montoEstimadoDeTransaccion",
	    //this.setAff_montoEstimadoDeTransaccion(map.get("aff_montoEstimadoDeTransaccion"));//recupera con formato 
	    valor2=map.get("aff_montoEstimadoDeTransaccion");//para quitar formato $, ',' y .00
	    valor2 = valor2.replaceAll("[$]", "");
	    valor2 = valor2.replaceAll("[,]", "");
	    valor2 = valor2.replaceAll("\\.00", "");
	    this.setAff_montoEstimadoDeTransaccion(valor2);
	    
        // "aff_diasAproxGL"
	    this.setAff_diasAproxGL(map.get("aff_diasAproxGL"));
	    //"aff_glOriginal"
	    //this.setAff_glOriginal(map.get("aff_glOriginal"));
	    valor2=map.get("aff_glOriginal");//para quitar formato $, ',' y .00
	    valor2 = valor2.replaceAll("[$]", "");
	    valor2 = valor2.replaceAll("[,]", "");
	    valor2 = valor2.replaceAll("\\.00", "");
	    this.setAff_glOriginal(map.get(valor2));
	    
	    //"aff_comentariosDisminucionExcepcionGL"
	    this.setAff_comentariosDisminucionExcepcionGL(map.get("aff_comentariosDisminucionExcepcionGL"));
	    
/*	    if(map.get("exencionConvenienciaComercialVIP")!=null && "X".equalsIgnoreCase(map.get("exencionConvenienciaComercialVIP"))){
	    	exencionConvenienciaComercialVIP = "true";
        }else{exencionConvenienciaComercialVIP="false";}
*/	    
	    this.setExencionConvenienciaComercialVIP(map.get("exencionConvenienciaComercialVIP")!=null && "X".equalsIgnoreCase(map.get("exencionConvenienciaComercialVIP"))?"true":"false");
	    
	    this.setExencionOtros(map.get("exencionOtros"));
	    this.setExencionJustificacion(map.get("exencionJustificacion"));
	    
	    this.setAff_comentariosDisminucionExcepcionGL(map.get("aff_comentariosDisminucionExcepcionGL"));
	    
	    this.setSolvenciaEconimicaSi(map.get("solvenciaEconimicaSi")!=null && "X".equalsIgnoreCase(map.get("solvenciaEconimicaSi"))?"true":"false");
	    /*if(map.get("solvenciaEconimicaSi")!=null && "X".equalsIgnoreCase(map.get("solvenciaEconimicaSi"))){
	    	solvenciaEconimicaSi = "true";
        }else{solvenciaEconimicaSi="false";}*/
	    this.setSolvenciaEconimicaNo(map.get("solvenciaEconimicaNo")!=null && "X".equalsIgnoreCase(map.get("solvenciaEconimicaNo"))?"true":"false");
	    /*if(map.get("solvenciaEconimicaNo")!=null && "X".equalsIgnoreCase(map.get("solvenciaEconimicaNo"))){
	    	solvenciaEconimicaNo = "true";
        }else{solvenciaEconimicaNo="false";}*/
	    this.setVisitaOcularRecienteSi(map.get("visitaOcularRecienteSi")!=null && "X".equalsIgnoreCase(map.get("visitaOcularRecienteSi"))?"true":"false");
	    /*if(map.get("visitaOcularRecienteSi")!=null && "X".equalsIgnoreCase(map.get("visitaOcularRecienteSi"))){
	    	visitaOcularRecienteSi = "true";
        }else{visitaOcularRecienteSi="false";}*/
	    this.setVisitaOcularRecienteNo(map.get("visitaOcularRecienteNo")!=null && "X".equalsIgnoreCase(map.get("visitaOcularRecienteNo"))?"true":"false");
	    /*if(map.get("visitaOcularRecienteNo")!=null && "X".equalsIgnoreCase(map.get("visitaOcularRecienteNo"))){
	    	visitaOcularRecienteNo = "true";
        }else{visitaOcularRecienteNo="false";}*/
	    this.setRiesgoReputacionalOperacionalSi(map.get("riesgoReputacionalOperacionalSi")!=null && "X".equalsIgnoreCase(map.get("riesgoReputacionalOperacionalSi"))?"true":"false");
	    /*if(map.get("riesgoReputacionalOperacionalSi")!=null && "X".equalsIgnoreCase(map.get("riesgoReputacionalOperacionalSi"))){
	    	riesgoReputacionalOperacionalSi = "true";
        }else{riesgoReputacionalOperacionalSi="false";}*/
	    this.setRiesgoReputacionalOperacionalNo(map.get("riesgoReputacionalOperacionalNo")!=null && "X".equalsIgnoreCase(map.get("riesgoReputacionalOperacionalNo"))?"true":"false");
	    /*if(map.get("riesgoReputacionalOperacionalNo")!=null && "X".equalsIgnoreCase(map.get("riesgoReputacionalOperacionalNo"))){
	    	riesgoReputacionalOperacionalNo = "true";
        }else{riesgoReputacionalOperacionalNo="false";}*/
	    
	    this.setDescBienServicioOfrece(map.get("descBienServicioOfrece"));
	    this.setTerritorioNacionalSi(map.get("territorioNacionalSi")!=null && "X".equalsIgnoreCase(map.get("territorioNacionalSi"))?"true":"false");
	    /*if(map.get("territorioNacionalSi")!=null && "X".equalsIgnoreCase(map.get("territorioNacionalSi"))){
	    	territorioNacionalSi = "true";
        }else{territorioNacionalSi="false";}*/
	    this.setTerritorioNacionalNo(map.get("territorioNacionalNo")!=null && "X".equalsIgnoreCase(map.get("territorioNacionalNo"))?"true":"false");
	    /*if(map.get("territorioNacionalNo")!=null && "X".equalsIgnoreCase(map.get("territorioNacionalNo"))){
	    	territorioNacionalNo = "true";
        }else{territorioNacionalNo="false";}*/
	    
	    this.setTerritorioNacionalEspecificacion(map.get("territorioNacionalEspecificacion"));
	    this.setEnNombreDeUnTerceroSi(map.get("enNombreDeUnTerceroSi")!=null && "X".equalsIgnoreCase(map.get("enNombreDeUnTerceroSi"))?"true":"false");
	    /*if(map.get("enNombreDeUnTerceroSi")!=null && "X".equalsIgnoreCase(map.get("enNombreDeUnTerceroSi"))){
	    	enNombreDeUnTerceroSi = "true";
        }else{enNombreDeUnTerceroSi="false";}*/
	    this.setEnNombreDeUnTerceroNo(map.get("enNombreDeUnTerceroNo")!=null && "X".equalsIgnoreCase(map.get("enNombreDeUnTerceroNo"))?"true":"false");
	    this.setEnNombreDeUnTerceroEspecificacion(map.get("enNombreDeUnTerceroEspecificacion"));
	    this.setAntiguedadAnio(map.get("antiguedadAnio"));
	    this.setAntiguedadMeses(map.get("antiguedadMeses"));
	    
	    //Mujer PyME
	    this.setMujerPyME(map.get("mujerPyME"));
	    this.setEsCuentaMujerPyME(map.get("esCuentaMujerPyME"));
	    this.setCuentaMujerPyMEValidada(map.get("cuentaMujerPyMEValidada"));
	    this.setNoEsCuentaMujerPyMEValidada(map.get("noEsCuentaMujerPyMEValidada"));
	    this.setDesactivarMujerPyME(map.get("desactivarMujerPyME"));
	    
	    this.setEsCuentaMujerPyMEDlls(map.get("esCuentaMujerPyMEDlls"));
	    this.setCuentaMujerPyMEValidadaDlls(map.get("cuentaMujerPyMEValidadaDlls"));
	    this.setNoEsCuentaMujerPyMEValidadaDlls(map.get("noEsCuentaMujerPyMEValidadaDlls"));
	    
	    this.setEsClienteMujerPyME(map.get("esClienteMujerPyME"));
	    this.setClienteMujerPyMEValidado(map.get("clienteMujerPyMEValidado"));
	    this.setNoEsClienteMujerPyMEValidado(map.get("noEsClienteMujerPyMEValidado"));

        }//fin de atributos
        
        
        
        if (contract_.getAffiliationCollection()!=null){       
	        Collection<Affiliation> affiliationColl = contract_.getAffiliationCollection();
	        Affiliation affi = null;
	        affi = affiliationColl.iterator().next();
	            
	        this.setAffiliation_productdesc(affi.getProductdesc());        
	        this.setAffiliation_soluciontype(affi.getSoluciontype());
	        //cambio en modalidad contrato unico ixe
	        this.setAffiliation_modality(affi.getModality());
	       /*String modalidad = affi.getModality();
	        if(modalidad!=null){
	        	if("Multicaja".equalsIgnoreCase(modalidad)||"Multicomercio".equalsIgnoreCase(modalidad)){
	        		setAffiliation_modality("Multicomercio/Servicomercio");
	        	}else{
	        		setAffiliation_modality(modalidad);
	        	}
	        }*/
	       
	        this.setAffiliation_currency(affi.getCurrency());
	        this.setAffiliation_servicetype(affi.getServicetype());
	        // EN BASE A LA CANTIDAD DE EQUIPO SE INDICA EL TIPO DE EQUIPO
	        //gina: lo borre porque no se donde se ocupa
//	        if(affi.getQuantdialup()>0){
//	        	this.setAffiliation_devicetype("Terminal Dial Up");
//	        }else if(affi.getQuantgprs()>0){
//	        	this.setAffiliation_devicetype("Terminal GPRS");
//	        }else if(affi.getQuantlan()>0){
//	        	this.setAffiliation_devicetype("Terminal LAN");
//	        }else if(affi.getQuantblue()>0){
//	        	this.setAffiliation_devicetype("Terminal Bluetooth");
//	        }else if(affi.getQuantwifi()>0){
//	        	this.setAffiliation_devicetype("Terminal Wifi");
//	        }
	
	        this.setAffiliation_currentbank(affi.getCurrentbank());
	        this.setAffiliation_numaffilmn(affi.getNumaffilmn());
	        this.setAffiliation_numaffildlls(affi.getNumaffildlls());
	        /*this.setAffiliation_accountnumberDecryptmn(affi.getAccountnumbermn());
	        this.setAffiliation_accountnumberDecryptdlls(affi.getAccountnumberdlls());*/
	        this.setAffiliation_accountnumbermn(encrypt.decrypt(affi.getAccountnumbermn()));
	        
	        if(affi.getAccountnumberdlls()!=null)
	        	this.setAffiliation_accountnumberdlls(encrypt.decrypt(affi.getAccountnumberdlls()));
	        else
	        	this.setAffiliation_accountnumberdlls("");
	        
	        this.setAffiliation_internalcredithistory(affi.getInternalcredithistory());
	        this.setAffiliation_externalcredithistory(affi.getExternalcredithistory());

	        if(affi.getExentDep()!=null){
	        	if(affi.getExentDep()==1){
	        		aff_exentDep="true";
	        		affiliation.setExentDep(1);
	        	}else{//exentdep=0 no exenta fianza
	        		aff_exentDep="false";
	        		affiliation.setExentDep(0);
	        	}
	        }else{//si exent es nulo
	        	if(affi.getHavedepositcompany()==null || affi.getHavedepositcompany()==0){ //requiere fianza?
	        		aff_exentDep="true";
	        		affiliation.setExentDep(1);
	        	}else if(affi.getHavedepositcompany()==1){
	        		aff_exentDep="false";
	        		affiliation.setExentDep(0);
	        	}
	        }
	        
        	this.setAffiliation_officerdepositexent(affi.getOfficerdepositexent());//autoriza exentar
	        this.setAffiliation_depositcompany(affi.getDepositcompany());//compa�ia
	        this.setAffiliation_depositamount(affi.getDepositamount());  //monto
	        
	        if(affi.getValidid()!=null){
	            this.setAffiliation_validid(affi.getValidid());}
	
	        if(affi.getFiscaldoc()!=null)
	            this.setAffiliation_fiscaldoc(affi.getFiscaldoc());
	        
	        if(affi.getFiscalenrolment()!=null)
	            this.setAffiliation_fiscalenrolment(affi.getFiscalenrolment());
	
	        if(affi.getLegalopinion()!=null)
	            this.setAffiliation_legalopinion(affi.getLegalopinion());
	
	        if(affi.getCheckingaccountmn()!=null)
	            this.setAffiliation_checkingaccountmn(affi.getCheckingaccountmn());
	        
	        if (affi.getCheckingaccountdlls()!=null)
	            this.setAffiliation_checkingaccountdlls(affi.getCheckingaccountdlls());
	        
	        
	        //Venta Forzada, Teclado Abierto y QPS no guardan true o false sino X por las etiquetas de PDF       
	       
	        if (affi.getOpenkey()!=null&&affi.getOpenkey().equals("X"))
	            this.setAffiliation_openkey("true");
	        else
	            this.setAffiliation_openkey("false");
	        
	        
	        if (affi.getForceauth()!=null&&affi.getForceauth().equals("X"))
	            this.setAffiliation_forceauth("true");
	        else
	            this.setAffiliation_forceauth("false");
	            
	        if(affi.getQps()!=null&&affi.getQps().equals("X"))
	            this.setAffiliation_qps("true");
	        else
	            this.setAffiliation_qps("false");
	        
	        //para contratos viejos la red es banorte por default
	        if(red==null || red.isEmpty()){
	        	red="0";
	        }
	        if(red.equalsIgnoreCase("0")){//banorte
	        	boolean existPlan = false;
	        	for (int i = 0; i <  getAffiliation_comercialplanArray().length; i++) {
					if(getAffiliation_comercialplanArray()[i].getValue().toString().equals(affi.getCommercialplan())){
						existPlan = true;
					}
				}
	        	if(existPlan)
	        		banortePlan=affi.getCommercialplan();
	        	else{
	        		banortePlan = "6";
	        		Plan planEntity = null;
	            	planEntity = planBean.findById(Integer.parseInt(affi.getCommercialplan()));
	            	setAffiliation_otherComercialPlan(planEntity.getDescription());
	        	}
	        	
	        	//banortePlan=affi.getCommercialplan();
	        	ixePlan="0";
	        }else if(red.equalsIgnoreCase("1")){//ixe
	        	boolean existPlan = false;
	        	for (int i = 0; i < getCommercialPlanArrayIxe().length; i++) {
					if(getCommercialPlanArrayIxe()[i].getValue().toString().equals(affi.getCommercialplan())){
						existPlan = true;
					}
				}
	        	if(existPlan)
	        		ixePlan=affi.getCommercialplan();
	        	else{
	        		ixePlan = "6";
	        		Plan planEntity = null;
	            	planEntity = planBean.findById(Integer.parseInt(affi.getCommercialplan()));
	            	setAffiliation_otherComercialPlan(planEntity.getDescription());
	        	}
	        	
	        	//ixePlan=affi.getCommercialplan();
	        	banortePlan="0";
	        }
	        //this.setAffiliation_comercialplan(affi.getCommercialplan());//plan comercial edit
	        
	        
           	this.setAffiliation_commisiontcmn(affi.getCommisiontcmn() != null ? affi.getCommisiontcmn().toString() : "");
            this.setAffiliation_commisiontcdlls(affi.getCommisiontcdlls() != null ? affi.getCommisiontcdlls().toString() : "");
            this.setAffiliation_commisiontdmn(affi.getCommisiontdmn() != null ? affi.getCommisiontdmn().toString() : "");
            this.setAffiliation_commisiontddlls(affi.getCommisiontddlls() != null ? affi.getCommisiontddlls().toString() : "");
            this.setAffiliation_commisionintnlmn(affi.getCommisionintnlmn() != null ? affi.getCommisionintnlmn().toString() : "");
            this.setAffiliation_commisionintnldlls(affi.getCommisionintnldlls() != null ? affi.getCommisionintnldlls().toString() : "");
            this.setAffiliation_avcommisiontcmn(affi.getAvcommisiontcmn() != null ? affi.getAvcommisiontcmn().toString() : "");
            this.setAffiliation_avcommisiontcdlls(affi.getAvcommisiontcdlls() != null ? affi.getAvcommisiontcdlls().toString() : "");
            this.setAffiliation_avcommisiontdmn(affi.getAvcommisiontdmn() != null ? affi.getAvcommisiontdmn().toString() : "");
            this.setAffiliation_avcommisiontddlls(affi.getAvcommisiontddlls() != null ? affi.getAvcommisiontddlls().toString() : "");
            this.setAffiliation_avcommisionintnlmn(affi.getAvcommisionintnlmn() != null ? affi.getAvcommisionintnlmn().toString() : "");
            this.setAffiliation_avcommisionintnldlls(affi.getAvcommisionintnldlls() != null ? affi.getAvcommisionintnldlls().toString() : "");  
	        
	        this.setAffiliation_affiliationratemn(affi.getAffiliationratemn());
	        this.setAffiliation_affiliationratedlls(affi.getAffiliationratedlls());
	        this.setAffiliation_transcriptorratemn(affi.getTranscriptorratemn());
	        this.setAffiliation_transcriptorratedlls(affi.getTranscriptorratedlls());
	        this.setAffiliation_monthlyratemn(affi.getMonthlyratemn());
	        this.setAffiliation_monthlyratedlls(affi.getMonthlyratedlls());
	        this.setAffiliation_monthlyinvoicingminmn(affi.getMonthlyinvoicingminmn());
	        this.setAffiliation_monthlyinvoicingmindlls(affi.getMonthlyinvoicingmindlls());
	        this.setAffiliation_failmonthlyinvoicingmn(affi.getFailmonthlyinvoicingmn());
	        this.setAffiliation_failmonthlyinvoicingdlls(affi.getFailmonthlyinvoicingdlls());
	        this.setAffiliation_minimiunbalancemn(affi.getMinimiunbalancemn());
	        this.setAffiliation_minimiunbalancedlls(affi.getMinimiunbalancedlls());
	        this.setAffiliation_failminimiunbalancemn(affi.getFailminimiunbalancemn());
	        this.setAffiliation_failminimiunbalancedlls(affi.getFailminimiunbalancedlls());
	        this.setAffiliation_promnotemn(affi.getPromnotemn());
	        this.setAffiliation_promnotedlls(affi.getPromnotedlls());
	        this.setAffiliation_failpromnotemn(affi.getFailpromnotemn());
	        this.setAffiliation_failpormnotedlls(affi.getFailpormnotedlls());
	        this.setAffiliation_avsmn(affi.getAvsmn());
	        this.setAffiliation_avsdlls(affi.getAvsdlls());
	        this.setAffiliation_activation3dsmn(affi.getActivation3dsmn());
	        this.setAffiliation_activation3dsdlls(affi.getActivation3dsdlls());
	        this.setAffiliation_monthlyrate3dsmn(affi.getMonthlyrate3dsmn());
	        this.setAffiliation_monthlyrate3dsdlls(affi.getMonthlyrate3dsdlls());
	        this.setAffiliation_otherconcept1des(affi.getOtherconcept1des());
	        this.setAffiliation_otherconcept1mn(affi.getOtherconcept1mn());
	        this.setAffiliation_otherconcept1dlls(affi.getOtherconcept1dlls());
	
	
	      if (affi.getProductdesc().equals("Interredes")||affi.getProductdesc().equals("Terminal punto de venta")){    
//	        this.setAffiliation_quantwifi(affi.getQuantwifi()!=null?affi.getQuantwifi():0);
//	        this.setAffiliation_quantgprs(affi.getQuantgprs()!=null?affi.getQuantgprs():0);
	        
//	        if(affi.getPlangprs()!=null)
//	            this.setAffiliation_plangprs(affi.getPlangprs());
//	        else
//	            this.setAffiliation_plangprs("false");
//	        
//	        if(affi.getPlanwifi()!=null)
//	            this.setAffiliation_planwifi(affi.getPlanwifi());
//	         else
//	            this.setAffiliation_planwifi("false");
//	        
//	        this.setAffiliation_quantlan(affi.getQuantlan()!=null?affi.getQuantlan():0);
//	        this.setAffiliation_quantdialup(affi.getQuantdialup()!=null?affi.getQuantdialup():0);
	  //      this.setAffiliation_quantmanual(affi.getQuantmanual()!=null?affi.getQuantmanual():0);//maquina transcriptora --se quita
	    	this.setAffiliation_quantmanual(0);///maquina transcriptora siempre manda 0
	    	  
	        this.setAffiliation_quantpinpad(affi.getQuantpinpad()!=null?affi.getQuantpinpad():0);//pinpad
//	        this.setAffiliation_quantblue(affi.getQuantblue()!=null?affi.getQuantblue():0);     
	        
	       affiliation.setTpvTel(affi.getTpvTel()!=null?affi.getTpvTel():0);
	       //affiliation.setTpvBlueInternet(affi.getTpvBlueInternet()!=null?affi.getTpvBlueInternet():0);// Se quita TPV inalámbrica (bluetooth) con conexión internet
	       affiliation.setTpvBlueInternet(0);
	       //affiliation.setTpvBlueTel(affi.getTpvBlueTel()!=null?affi.getTpvBlueTel():0);// se quita TPV inalámbrica (bluetooth) con conexión telefónica
	       affiliation.setTpvBlueTel(0);
	       affiliation.setQuantgprs(affi.getQuantgprs()!=null?affi.getQuantgprs():0);
	       affiliation.setTpvInternet(affi.getTpvInternet()!=null?affi.getTpvInternet():0);
	       affiliation.setTpvInternetTel(affi.getTpvInternetTel()!=null?affi.getTpvInternetTel():0);
	       affiliation.setTpvMovil(affi.getTpvMovil()!=null?affi.getTpvMovil():0);
	       affiliation.setQuantwifi(affi.getQuantwifi()!=null?affi.getQuantwifi():0);
	       affiliation.setWifiTel(affi.getWifiTel()!=null?affi.getWifiTel():0);
	        
	      }
	      
	      if (affi.getProductdesc().equals("Comercio Electronico")){
	          if(affi.getActive3ds()!=null)  
	            this.setAffiliation_active3ds(affi.getActive3ds());
	          else
	            this.setAffiliation_active3ds("false");  
	          
	          this.setClient_site(contract_.getClient().getSite());
	      }
	      
	      if (affi.getProductdesc().equals("Terminal Personal Banorte (Mpos)")){
	    	  //this.getAffiliation().setMposMonthlyRate(affi.getMposMonthlyRate());// SE QUITA RENTA DE MPOS
	    	  this.getAffiliation().setMposMonthlyRate(0);
	    	  this.getAffiliation().setMposRateByEquipment(affi.getMposRateByEquipment());
	      }else{
	    	  this.getAffiliation().setMposMonthlyRate(0);
	    	  this.getAffiliation().setMposRateByEquipment(0);
	      }
	       
	        //Parametrico
	        
	        this.setAffiliation_yearswithbank(affi.getYearswithbank());
	        this.setAffiliation_otherproducts(affi.getOtherproducts());
	        this.setAffiliation_creditline(affi.getCreditline());
	        this.setAffiliation_creditlinetype(affi.getCreditlinetype());
	        this.setAffiliation_creditlineamount(affi.getCreditlineamount());
	        this.setAffiliation_quantprevbanks(affi.getQuantprevbanks());
	        this.setAffiliation_quantmerchants(affi.getQuantmerchants());
	        this.setAffiliation_quantbranches(affi.getQuantbranches());
	        this.setAffiliation_quantemployees(affi.getQuantemployees());
	        this.setAffiliation_yearswithmerchant(affi.getYearswithmerchant());
	        this.setAffiliation_rootedplace(affi.getRootedplace());
	        this.setAffiliation_socioeconomiclocation(affi.getSocioeconomiclocation());
	        this.setAffiliation_locationtype(affi.getLocationtype());
	        this.setAffiliation_dimention(affi.getDimention());
	        this.setAffiliation_equipment(affi.getEquipment());
	        this.setAffiliation_inventary(affi.getInventary());
	        this.setAffiliation_parkingtype(affi.getParkingtype());
	        this.setAffiliation_ownphone(affi.getOwnphone());
	        this.setAffiliation_extannounce(affi.getExtannounce());
	        this.setAffiliation_productstockplace(affi.getProductstockplace());
	        this.setAffiliation_commercialgroup(affi.getCommercialgroup());
	        this.setAffiliation_otherpartners(affi.getOtherpartners());
	        this.setAffiliation_attractingtrade(affi.getAttractingtrade());
	        this.setAffiliation_recommended(affi.getRecommended());
	        this.setAffiliation_physicalconditions(affi.getPhysicalconditions());
	        this.setAffiliation_ownerage(affi.getOwnerage());
	        this.setAffiliation_societyage(affi.getSocietyage());
	        this.setAffiliation_monthlysales(affi.getMonthlysales());
	        this.setAffiliation_avcardsales(affi.getAvcardsales());
	        this.setAffiliation_attentiontime(affi.getAttentiontime());
	        this.setAffiliation_salesemployee(affi.getSalesemployee());
	        
        }//fin afiliacion

        //this.recalculateCommisionTable =
        this.setContract(contract_);
        
        this.recalculateCommisionTable = false;
        userState=userStatePack();
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
	    //log.info("******** Muestra Documento " + nameTemplate + "... " + flagTemplate.intValue());
        pdfTemplateBinding.setAffiliationId(1);
        createPdfOrXLSResponse(getPath() + pathTemplate+nameTemplate, nameTemplate, true, flagTemplate.intValue() == 1 ? true : false);
    }
    
    public void createXLS() {
//        log.info("******** Muestra Documento de ayuda...");
        createPdfOrXLSResponse(getPath()+"/WEB-INF/docs/Cotizador_V_20081024.xls", "Cotizador_V_20081024.xls", false, false); 
    }
    
    
    /**
	 * Metodo que llena las ciudades en base al estado elegido para datos del fiador
     * @throws ServletException 
     * @throws IOException 
	 */
	public void findClientFCities(ActionEvent actionEvent) throws ServletException, IOException {
		HtmlCommandLink link 	= (HtmlCommandLink) actionEvent.getSource(); //Se comento
		boolean redirect 		= Boolean.TRUE;
		
		getCitiesClientFsArray();
		
		// Para recuperar el par�metro enviado por el JSP
		String anchor="officeRepInfo";
		if(redirect){
			//Redirect
			redirectGeneralInfo(anchor);
		}
	}
    
    public Template[] getFormatList() {
        this.setToPrint(false);
        Collection<Template> templateCollection = getTemplateOption(this.getProduct().getProductid());
        Collection<Template> templateCollection2 = new ArrayList();
		for (Template template : templateCollection) {
			if (template.getName().equals(ApplicationConstants.ADQ_ANEXO_A_BANORTE)) {
				if (red.equalsIgnoreCase("0")) {
					templateCollection2.add(template);
				}
			} else if (template.getName().equals(ApplicationConstants.ADQ_ANEXO_A_IXE)) {
				if (red.equalsIgnoreCase("1")) {
					templateCollection2.add(template);
				}
			} else if (template.getName().equals(ApplicationConstants.ADQ_ANEXO_C_BANORTE)) {
				if (red.equalsIgnoreCase("0")) {
					templateCollection2.add(template);
				}
			} else if (template.getName().equals(ApplicationConstants.ADQ_ANEXO_C_IXE)) {
				if (red.equalsIgnoreCase("1")) {
					templateCollection2.add(template);
				}
			} else if (template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_ANEXO_E_BANORTE)) {	
				//Garantia liquida: si es seleccionada garantia liquida se agrega el anexo E a la lista
				if(aff_garantiaLiquida != null){
					if (aff_garantiaLiquida.equalsIgnoreCase("0") && fianzaOculta.equalsIgnoreCase("false")) {
						if(this.getAffiliation_currency() != null && (ApplicationConstants.CURRENCY_PESOS).equalsIgnoreCase(this.getAffiliation_currency())||(ApplicationConstants.CURRENCY_AMBOS).equalsIgnoreCase(this.getAffiliation_currency())){
							templateCollection2.add(template);
						}
					} 
				}
			}else if (template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_ANEXO_E_BANORTE_DOLARES)) {	
				//Garantia liquida: si es seleccionada garantia liquida y la divisa dolares o ambas se agrega el anexo E Dolares a la lista
				if(aff_garantiaLiquida != null){
					if (aff_garantiaLiquida.equalsIgnoreCase("0") && fianzaOculta.equalsIgnoreCase("false")) {						
						if(this.getAffiliation_currency() != null && (ApplicationConstants.CURRENCY_DOLLAR).equalsIgnoreCase(this.getAffiliation_currency())||(ApplicationConstants.CURRENCY_AMBOS).equalsIgnoreCase(this.getAffiliation_currency())){
							templateCollection2.add(template);
						}
						
					} 
				}
			}else if (template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_CARTA_DISMINUCION_EXENCION_GL)) {	
				
				//Garantia liquida: si es seleccionada garantia liquida se agrega el anexo E a la lista
				if(aff_porcentajeGL != null){
					//if (aff_porcentajeGL.equalsIgnoreCase("excepcion") || aff_porcentajeGL.equalsIgnoreCase("disminucion") || aff_porcentajeGL.equalsIgnoreCase("8")) {
					if (aff_porcentajeGL.equalsIgnoreCase("excepcion") || aff_porcentajeGL.equalsIgnoreCase("disminucion") || aff_porcentajeGL.equalsIgnoreCase("5")) {
						templateCollection2.add(template);
						
					} 
				}
			}else if (template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_ANEXO_MUJER_PYME_BANORTE)) {	
				
				//MujerPyMEBanorte: si es seleccionada el checkbox se agrega el anexo Mujer a la lista
				if(mujerPyME != null){
					if ("true".equalsIgnoreCase(mujerPyME)) {
						templateCollection2.add(template);
						
					} 
				}
			}else if (template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_CARATULA_BANORTE)) {
				if (red.equalsIgnoreCase("0")) {
					templateCollection2.add(template);
				}
			} else if (template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_CARATULA_IXE)) {
				if (red.equalsIgnoreCase("1")) {
					templateCollection2.add(template);
				}
			} else if(template.getName().equalsIgnoreCase(ApplicationConstants.ADQ_PAGA_RETIRA_EFECTIVO)){
				if("true".equalsIgnoreCase(aff_cashback)||"X".equalsIgnoreCase(aff_cashback)){
					templateCollection2.add(template);
				} 
			} else {
				templateCollection2.add(template);
			}
		}
		
        Template[] templateArray = new Template[templateCollection2.size()];
        
        String[] ordenamiento=new String[]{
        		"Caratula Banorte",
        		"Caratula SIP",
        		"Caratula Ixe",
        		"Clausulado Banorte e Ixe",
        		"Anexo A Banorte",
        		"Anexo A Ixe",
        		"Anexo C Banorte",
        		"Anexo C Ixe",
        		"Anexo E Banorte",
        		"Carta de Disminución o Excención de Garantia Liquida",
        		"Adendum SIP",
        		"Anexo de Comisiones SIP",
        		"Estudio Parametrico"
        		};
		List<String> orden=Arrays.asList(ordenamiento);
		Collection<Template> formatosOrdenados = new ContractUtil().orderList(orden, templateCollection2);
        
		return formatosOrdenados.toArray(templateArray);
//        return templateCollection2.toArray(templateArray);
    }

   

    //Validacion toContact
    
     public boolean getToContact() {
        if("Comercio Electronico".equalsIgnoreCase(affiliation.getProductdesc())){
        	return true;	
        }
        if ("internet".equalsIgnoreCase(affiliation.getSoluciontype())){
             if ("Cargo Recurrente".equalsIgnoreCase(affiliation.getProductdesc()) || 
            		 "Interredes".equalsIgnoreCase(affiliation.getProductdesc())){
            	 return true; 
             }else{
            	 return false;
             }
        }else{
        	return false; 
        }
     }


    public void setToContact(boolean toContact) {
        this.toContact =toContact;
    }

    



    //Datos Orden Afiliacion
    
    public String getCommchargetype() {
    	if(commchargetype == null)
    		this.commchargetype ="Porcentaje";
    	
        return commchargetype;
    }

    public void setCommchargetype(String commchargetype) {
        this.commchargetype = commchargetype;
    }

    
    
//    public String getAvcommisiontcdllsComplete() {
//        return avcommisiontcdllsComplete;
//    }
    
//    public void setAvcommisiontcdllsComplete(String avcommisiontcdllsComplete) {
//        if(this.getCommchargetype() != null  && this.getCommchargetype().equals("Monto")){
//            this.setAffiliation_commisiontcdlls(avcommisiontcdllsComplete);
//            this.setAffiliation_avcommisiontcdlls("0.0");
//        }else{
//            this.setAffiliation_avcommisiontcdlls(avcommisiontcdllsComplete);
//            this.setAffiliation_commisiontcdlls("0.0");
//        }       
//        this.avcommisiontcdllsComplete = avcommisiontcdllsComplete;
//    }

//    public String getAvcommisiontcmnComplete() {
//        return avcommisiontcmnComplete;
//    }
    
//    public void setAvcommisiontcmnComplete(String avcommisiontcmnComplete) {
//        if(this.getCommchargetype() != null  && this.getCommchargetype().equals("Monto")){
//            this.setAffiliation_commisiontcmn(avcommisiontcmnComplete);
//            this.setAffiliation_avcommisiontcmn("0.0");
//        }else{
//            this.setAffiliation_avcommisiontcmn(avcommisiontcmnComplete);
//            this.setAffiliation_commisiontcmn("0.0");
//       }
//        this.avcommisiontcmnComplete = avcommisiontcmnComplete;
//    }

//    public String getAvcommisiontddllsComplete() {
//        return avcommisiontddllsComplete;
//    }

//    public void setAvcommisiontddllsComplete(String avcommisiontddllsComplete) {
//        if(this.getCommchargetype() != null  && this.getCommchargetype().equals("Monto")){
//        	this.setAffiliation_commisiontddlls(avcommisiontddllsComplete);
//        	this.setAffiliation_avcommisiontddlls("0.0");
//        }else{
//        	this.setAffiliation_avcommisiontddlls(avcommisiontddllsComplete);
//        	this.setAffiliation_commisiontddlls("0.0");
//        }
//        this.avcommisiontddllsComplete = avcommisiontddllsComplete;
//    }
    
//    public String getAvcommisionintnldllsComplete() {
//        return avcommisionintnldllsComplete;
//    }

//    public void setAvcommisionintnldllsComplete(String avcommisionintnldllsComplete) {
//        if(this.getCommchargetype() != null  && this.getCommchargetype().equals("Monto")){
//        	this.setAffiliation_commisionintnldlls(avcommisionintnldllsComplete);
//        	this.setAffiliation_avcommisionintnldlls("0.0");
//        }else{
//        	this.setAffiliation_avcommisionintnldlls(avcommisionintnldllsComplete);
//        	this.setAffiliation_commisionintnldlls("0.0");
//        }
//        this.avcommisionintnldllsComplete = avcommisionintnldllsComplete;
//    }

//    public String getAvcommisiontdmnComplete() {
//        return avcommisiontdmnComplete;
//    }

//    public void setAvcommisiontdmnComplete(String avcommisiontdmnComplete) {
//        if(this.getCommchargetype() != null  && this.getCommchargetype().equals("Monto")){
//        	this.setAffiliation_commisiontdmn(avcommisiontdmnComplete);
//        	this.setAffiliation_avcommisiontdmn("0.0");
//        }else{
//        	this.setAffiliation_avcommisiontdmn(avcommisiontdmnComplete);
//        	this.setAffiliation_commisiontdmn("0.0");
//        }
//        this.avcommisiontdmnComplete = avcommisiontdmnComplete;
//    }
    
//    public String getAvcommisionintnlmnComplete() {
//        return avcommisionintnlmnComplete;
//    }
    
//    public void setAvcommisionintnlmnComplete(String avcommisionintnlmnComplete) {
//        if(this.getCommchargetype() != null  && this.getCommchargetype().equals("Monto")){
//        	this.setAffiliation_commisionintnlmn(avcommisionintnlmnComplete);
//        	this.setAffiliation_avcommisionintnlmn("0.0");
//        }else{
//        	this.setAffiliation_avcommisionintnlmn(avcommisionintnlmnComplete);
//        	this.setAffiliation_commisionintnlmn("0.0");
//        }
//        this.avcommisionintnlmnComplete = avcommisionintnlmnComplete;
//    }

     public String getAffiliation_duedate() {
        return affiliation_duedate;
    }

    public void setAffiliation_duedate(String affiliation_duedate) {
        this.affiliation_duedate = affiliation_duedate;
    }
    
    public String getAffiliation_active3ds() {
        return affiliation.getActive3ds()!=null ? affiliation.getActive3ds().toString() : "" ;
                
    }

    public void setAffiliation_active3ds(String affiliation_active3ds) {
        affiliation.setActive3ds(affiliation_active3ds);
    }
        
    public String getAffiliation_servicetype() {
        return affiliation.getServicetype() != null ? affiliation.getServicetype().toString() : "";
    }

    public void setAffiliation_servicetype(String affiliation_servicetype) {
        affiliation.setServicetype(affiliation_servicetype);
    }

    public String getAffiliation_comercialplan() {
        return affiliation.getCommercialplan();
    }

    public void setAffiliation_comercialplan(String affiliation_comercialplan) {
         affiliation.setCommercialplan(affiliation_comercialplan);              
    }

    public String getAffiliation_accountnumberdlls() {
    	 return this.affiliation_accountnumberdlls;
         /*if(affiliation.getAccountnumberdlls() != null && affiliation.getAccountnumberdlls().trim().length() > 0){   
        	 return encrypt.decrypt(affiliation.getAccountnumberdlls());            
         }
        else {
        return "";
        }  */
    }
    
    public void setAffiliation_accountnumberdlls(String affiliation_accountnumberdlls) {
//    	this.affiliation_accountnumberdlls = affiliation_accountnumberdlls;
//    	affiliation.setAccountnumberdlls(encrypt.encrypt(this.affiliation_accountnumberdlls));
    	
    	if(affiliation_accountnumberdlls != null && affiliation_accountnumberdlls.trim().length() > 0){        
        	this.affiliation_accountnumberdlls = affiliation_accountnumberdlls;
        } else {
        	this.affiliation_accountnumberdlls = "";
        }

    	affiliation.setAccountnumberdlls(encrypt.encrypt(this.affiliation_accountnumberdlls));            

    }
    
   /* public void setAffiliation_accountnumberDecryptdlls(String affiliation_accountnumberdlls) {      
    	   affiliation.setAccountnumberdlls(affiliation_accountnumberdlls);          
    }*/

    public String getAffiliation_accountnumbermn() {
		return this.affiliation_accountnumbermn;
  /*  	if( affiliation.getAccountnumbermn() != null &&  affiliation.getAccountnumbermn().trim().length() > 0){          
            
    		return encrypt.decrypt(affiliation.getAccountnumbermn());            
         }
        else {
	        return "";
        } */ 
    }

    public void setAffiliation_accountnumbermn(String affiliation_accountnumbermn) {
    	this.affiliation_accountnumbermn = affiliation_accountnumbermn;
    	affiliation.setAccountnumbermn(encrypt.encrypt(this.affiliation_accountnumbermn));
    	/*System.out.println("setAffiliation_accountnumbermn: " + affiliation_accountnumbermn);
	    
    	if(affiliation_accountnumbermn != null && affiliation_accountnumbermn.trim().length() > 0){      
        	 affiliation.setAccountnumbermn(encrypt.encrypt(affiliation_accountnumbermn));
        	 System.out.println("setAffiliation_accountnumbermn encrypt: " + encrypt.encrypt(affiliation_accountnumbermn));
     	    	 
        } else {
           affiliation.setAccountnumbermn("");
          }*/
    }

   /* public void setAffiliation_accountnumberDecryptmn(String affiliation_accountnumbermn) {     
    	System.out.println("setAffiliation_accountnumberDecryptmn: " + affiliation_accountnumbermn);
	    
    	affiliation.setAccountnumbermn(affiliation_accountnumbermn);
    }*/
    
    public String getAffiliation_activation3dsdlls() {
        return affiliation.getActivation3dsdlls() != null ? affiliation.getActivation3dsdlls().toString() : "";
    }

    public void setAffiliation_activation3dsdlls(Double affiliation_activation3dsdlls) {
        affiliation.setActivation3dsdlls(affiliation_activation3dsdlls);
    }

    public void setAffiliation_activation3dsdlls(String affiliation_activation3dsdlls) {
        if (affiliation_activation3dsdlls != null && !affiliation_activation3dsdlls.trim().equals("")) {
            try {
                setAffiliation_activation3dsdlls(new Double(affiliation_activation3dsdlls));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_activation3dsmn() {
        return affiliation.getActivation3dsmn() != null ? affiliation.getActivation3dsmn().toString() : "";
    }

    public void setAffiliation_activation3dsmn(Double affiliation_activation3dsmn) {
        affiliation.setActivation3dsmn(affiliation_activation3dsmn);
    }

    public void setAffiliation_activation3dsmn(String affiliation_activation3dsmn) {
        if (affiliation_activation3dsmn != null && !affiliation_activation3dsmn.trim().equals("")) {
            try {
                setAffiliation_activation3dsmn(new Double(affiliation_activation3dsmn));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    

    public String getAffiliation_affiliationratemn() {

        return affiliation.getAffiliationratemn() != null ? affiliation.getAffiliationratemn().toString() : "";
    }

    public void setAffiliation_affiliationratemn(Double affiliation_affiliationratemn) {

        affiliation.setAffiliationratemn(affiliation_affiliationratemn);
    }

    public void setAffiliation_affiliationratemn(String affiliation_affiliationratemn) {

        if (affiliation_affiliationratemn != null && !affiliation_affiliationratemn.trim().equals("")) {
            try {
                setAffiliation_affiliationratemn(new Double(affiliation_affiliationratemn));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }
    
    public String getAffiliation_affiliationratedlls() {
        return affiliation.getAffiliationratedlls() != null ? affiliation.getAffiliationratedlls().toString() : "";
    }

    public void setAffiliation_affiliationratedlls(Double affiliation_affiliationratedlls) {
        affiliation.setAffiliationratedlls(affiliation_affiliationratedlls);
    }

    public void setAffiliation_affiliationratedlls(String affiliation_affiliationratedlls) {
        if (affiliation_affiliationratedlls != null && !affiliation_affiliationratedlls.trim().equals("")) {
            try {
                setAffiliation_affiliationratedlls(new Double(affiliation_affiliationratedlls));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }
    
    public String getAffiliation_rateMposmn() {
        return affiliation.getRateMposmn() != null ? affiliation.getRateMposmn().toString() : "";
    }

    public void setAffiliation_rateMposmn(Double affiliation_rateMposmn) {
        affiliation.setRateMposmn(affiliation_rateMposmn);
    }

    public void setAffiliation_rateMposmn(String affiliation_rateMposmn) {
        if (affiliation_rateMposmn != null && !affiliation_rateMposmn.trim().equals("")) {
            try {
            	setAffiliation_rateMposmn(new Double(affiliation_rateMposmn));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }
    
    public String getAffiliation_rateMposdlls() {
        return affiliation.getRateMposdlls() != null ? affiliation.getRateMposdlls().toString() : "";
    }

    public void setAffiliation_rateMposdlls(Double affiliation_rateMposdlls) {
        affiliation.setRateMposdlls(affiliation_rateMposdlls);
    }

    public void setAffiliation_rateMposdlls(String affiliation_rateMposdlls) {
        if (affiliation_rateMposdlls != null && !affiliation_rateMposdlls.trim().equals("")) {
            try {
            	setAffiliation_rateMposdlls(new Double(affiliation_rateMposdlls));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }
    
    
    
    
    
    

    public String getAffiliation_attentiontime() {
        return affiliation.getAttentiontime() != null ? affiliation.getAttentiontime().toString() : "";
    }

    public void setAffiliation_attentiontime(Integer affiliation_attentiontime) {
        affiliation.setAttentiontime(affiliation_attentiontime);
    }

    public void setAffiliation_attentiontime(String affiliation_attentiontime) {
        if (affiliation_attentiontime != null && !affiliation_attentiontime.trim().equals("")) {
            try {
                setAffiliation_attentiontime(new Integer(affiliation_attentiontime));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_attractingtrade() {
        return affiliation.getAttractingtrade() != null ? affiliation.getAttractingtrade().toString() : "";
    }

    public void setAffiliation_attractingtrade(Integer affiliation_attractingtrade) {
        affiliation.setAttractingtrade(affiliation_attractingtrade);
    }

    public void setAffiliation_attractingtrade(String affiliation_attractingtrade) {
        if (affiliation_attractingtrade != null && !affiliation_attractingtrade.trim().equals("")) {
            try {
                setAffiliation_attractingtrade(new Integer(affiliation_attractingtrade));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_avcardsales() {
        return affiliation.getAvcardsales() != null ? affiliation.getAvcardsales().toString() : "";
    }

    public void setAffiliation_avcardsales(Integer affiliation_avcardsales) {
        affiliation.setAvcardsales(affiliation_avcardsales);
    }

    public void setAffiliation_avcardsales(String affiliation_avcardsales) {
        if (affiliation_avcardsales != null && !affiliation_avcardsales.trim().equals("")) {
            try {
                setAffiliation_avcardsales(new Integer(affiliation_avcardsales));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_avcommisiontcdlls() {
        return affiliation.getAvcommisiontcdlls() != null ? affiliation.getAvcommisiontcdlls().toString() : "";
    }

    public void setAffiliation_avcommisiontcdlls(Double affiliation_avcommisiontcdlls) {
        affiliation.setAvcommisiontcdlls(affiliation_avcommisiontcdlls);
    }

    public void setAffiliation_avcommisiontcdlls(String affiliation_avcommisiontcdlls) {
        if (affiliation_avcommisiontcdlls != null && !affiliation_avcommisiontcdlls.trim().equals("")) {
            try {
                setAffiliation_avcommisiontcdlls(new Double(affiliation_avcommisiontcdlls));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_avcommisiontcmn() {
        return affiliation.getAvcommisiontcmn() != null ? affiliation.getAvcommisiontcmn().toString() : "";
    }

    public void setAffiliation_avcommisiontcmn(Double affiliation_avcommisiontcmn) {
        affiliation.setAvcommisiontcmn(affiliation_avcommisiontcmn);
    }

    public void setAffiliation_avcommisiontcmn(String affiliation_avcommisiontcmn) {
        if (affiliation_avcommisiontcmn != null && !affiliation_avcommisiontcmn.trim().equals("")) {
            try {
                setAffiliation_avcommisiontcmn(new Double(affiliation_avcommisiontcmn));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_avcommisiontddlls() {
        return affiliation.getAvcommisiontddlls() != null ? affiliation.getAvcommisiontddlls().toString() : "";
    }

    public void setAffiliation_avcommisiontddlls(Double affiliation_avcommisiontddlls) {
        affiliation.setAvcommisiontddlls(affiliation_avcommisiontddlls);
    }

    public void setAffiliation_avcommisiontddlls(String affiliation_avcommisiontddlls) {
        if (affiliation_avcommisiontddlls != null && !affiliation_avcommisiontddlls.trim().equals("")) {
            try {
                setAffiliation_avcommisiontddlls(new Double(affiliation_avcommisiontddlls));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }
    
    public String getAffiliation_avcommisionintnldlls() {
        return affiliation.getAvcommisionintnldlls() != null ? affiliation.getAvcommisionintnldlls().toString() : "";
    }
    
    public void setAffiliation_avcommisionintnldlls(Double affiliation_avcommisionintnldlls) {
        affiliation.setAvcommisionintnldlls(affiliation_avcommisionintnldlls);//VR
    }

    public void setAffiliation_avcommisionintnldlls(String affiliation_avcommisionintnldlls) {
        if (affiliation_avcommisionintnldlls != null && !affiliation_avcommisionintnldlls.trim().equals("")) {
            try {
                setAffiliation_avcommisionintnldlls(new Double(affiliation_avcommisionintnldlls));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_avcommisiontdmn() {
        return affiliation.getAvcommisiontdmn() != null ? affiliation.getAvcommisiontdmn().toString() : "";
    }

    public void setAffiliation_avcommisiontdmn(Double affiliation_avcommisiontdmn) {
        affiliation.setAvcommisiontdmn(affiliation_avcommisiontdmn);
    }

    public void setAffiliation_avcommisiontdmn(String affiliation_avcommisiontdmn) {
        if (affiliation_avcommisiontdmn != null && !affiliation_avcommisiontdmn.trim().equals("")) {
            try {
                setAffiliation_avcommisiontdmn(new Double(affiliation_avcommisiontdmn));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }
    
    public String getAffiliation_avcommisionintnlmn() {
        return affiliation.getAvcommisionintnlmn() != null ? affiliation.getAvcommisionintnlmn().toString() : "";
    }
    
    public void setAffiliation_avcommisionintnlmn(String affiliation_avcommisionintnlmn) {
        if (affiliation_avcommisionintnlmn != null && !affiliation_avcommisionintnlmn.trim().equals("")) {
            try {
                setAffiliation_avcommisionintnlmn(new Double(affiliation_avcommisionintnlmn));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }
    
    public void setAffiliation_avcommisionintnlmn(Double affiliation_avcommisionintnlmn) {
        affiliation.setAvcommisionintnlmn(affiliation_avcommisionintnlmn);//VR
    }
    
    public String getAffiliation_avdifminbalanceandrealdlls() {
        return affiliation.getAvdifminbalanceandrealdlls() != null ? affiliation.getAvdifminbalanceandrealdlls().toString() : "";
    }

    public void setAffiliation_avdifminbalanceandrealdlls(Double affiliation_avdifminbalanceandrealdlls) {
        affiliation.setAvdifminbalanceandrealdlls(affiliation_avdifminbalanceandrealdlls);
    }

    public void setAffiliation_avdifminbalanceandrealdlls(String affiliation_avdifminbalanceandrealdlls) {
        if (affiliation_avdifminbalanceandrealdlls != null && !affiliation_avdifminbalanceandrealdlls.trim().equals("")) {
            try {
                setAffiliation_avdifminbalanceandrealdlls(new Double(affiliation_avdifminbalanceandrealdlls));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_avdifminbalanceandrealmn() {
        return affiliation.getAvdifminbalanceandrealmn() != null ? affiliation.getAvdifminbalanceandrealmn().toString() : "";
    }

    public void setAffiliation_avdifminbalanceandrealmn(Double affiliation_avdifminbalanceandrealmn) {
        affiliation.setAvdifminbalanceandrealmn(affiliation_avdifminbalanceandrealmn);
    }

    public void setAffiliation_avdifminbalanceandrealmn(String affiliation_avdifminbalanceandrealmn) {
        if (affiliation_avdifminbalanceandrealmn != null && !affiliation_avdifminbalanceandrealmn.trim().equals("")) {
            try {
                setAffiliation_avdifminbalanceandrealmn(new Double(affiliation_avdifminbalanceandrealmn));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_avsdlls() {
        return affiliation.getAvsdlls() != null ? affiliation.getAvsdlls().toString() : "";
    }

    public void setAffiliation_avsdlls(Double affiliation_avsdlls) {
        affiliation.setAvsdlls(affiliation_avsdlls);
    }

    public void setAffiliation_avsdlls(String affiliation_avsdlls) {
        if (affiliation_avsdlls != null && !affiliation_avsdlls.trim().equals("")) {
            try {
                setAffiliation_avsdlls(new Double(affiliation_avsdlls));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_avsmn() {
        return affiliation.getAvsmn() != null ? affiliation.getAvsmn().toString() : "";
    }

    public void setAffiliation_avsmn(Double affiliation_avsmn) {
        affiliation.setAvsmn(affiliation_avsmn);
    }

    public void setAffiliation_avsmn(String affiliation_avsmn) {
        if (affiliation_avsmn != null && !affiliation_avsmn.trim().equals("")) {
            try {
                setAffiliation_avsmn(new Double(affiliation_avsmn));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_categorycoderisk() {
        return affiliation.getCategorycoderisk() != null ? affiliation.getCategorycoderisk().toString() : "";
    }

    public void setAffiliation_categorycoderisk(Integer affiliation_categorycoderisk) {
        affiliation.setCategorycoderisk(affiliation_categorycoderisk);
    }

    public void setAffiliation_categorycoderisk(String affiliation_categorycoderisk) {
        if (affiliation_categorycoderisk != null && !affiliation_categorycoderisk.trim().equals("")) {
            try {
                setAffiliation_categorycoderisk(new Integer(affiliation_categorycoderisk));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_checkingaccountdlls() {
        return affiliation.getCheckingaccountdlls() != null ? affiliation.getCheckingaccountdlls().toString() : "";
    }

    public void setAffiliation_checkingaccountdlls(String affiliation_checkingaccountdlls) {
        affiliation.setCheckingaccountdlls(affiliation_checkingaccountdlls);
    }

    

    public String getAffiliation_checkingaccountmn() {
        return affiliation.getCheckingaccountmn() != null ? affiliation.getCheckingaccountmn().toString() : "";
    }

    public void setAffiliation_checkingaccountmn(String affiliation_checkingaccountmn) {
        affiliation.setCheckingaccountmn(affiliation_checkingaccountmn);
    }



    public String getAffiliation_commercialgroup() {
        return affiliation.getCommercialgroup() != null ? affiliation.getCommercialgroup().toString() : "";
    }

    public void setAffiliation_commercialgroup(Integer affiliation_commercialgroup) {
        affiliation.setCommercialgroup(affiliation_commercialgroup);
    }

    public void setAffiliation_commercialgroup(String affiliation_commercialgroup) {
        if (affiliation_commercialgroup != null && !affiliation_commercialgroup.trim().equals("")) {
            try {
                setAffiliation_commercialgroup(new Integer(affiliation_commercialgroup));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_commisiontcdlls() {
        return affiliation.getCommisiontcdlls() != null ? affiliation.getCommisiontcdlls().toString() : "";
    }

    public void setAffiliation_commisiontcdlls(Double affiliation_commisiontcdlls) {
        affiliation.setCommisiontcdlls(affiliation_commisiontcdlls);
    }

    public void setAffiliation_commisiontcdlls(String affiliation_commisiontcdlls) {
        if (affiliation_commisiontcdlls != null && !affiliation_commisiontcdlls.trim().equals("")) {
            try {
                setAffiliation_commisiontcdlls(new Double(affiliation_commisiontcdlls));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_commisiontcmn() {
        return affiliation.getCommisiontcmn() != null ? affiliation.getCommisiontcmn().toString() : "";
    }

    public void setAffiliation_commisiontcmn(Double affiliation_commisiontcmn) {
        affiliation.setCommisiontcmn(affiliation_commisiontcmn);
    }

    public void setAffiliation_commisiontcmn(String affiliation_commisiontcmn) {
        if (affiliation_commisiontcmn != null && !affiliation_commisiontcmn.trim().equals("")) {
            try {
                setAffiliation_commisiontcmn(new Double(affiliation_commisiontcmn));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_commisiontddlls() {
        return affiliation.getCommisiontddlls() != null ? affiliation.getCommisiontddlls().toString() : "";
    }

    public void setAffiliation_commisiontddlls(Double affiliation_commisiontddlls) {
        affiliation.setCommisiontddlls(affiliation_commisiontddlls);
    }

    public void setAffiliation_commisiontddlls(String affiliation_commisiontddlls) {
        if (affiliation_commisiontddlls != null && !affiliation_commisiontddlls.trim().equals("")) {
            try {
                setAffiliation_commisiontddlls(new Double(affiliation_commisiontddlls));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }
    
    public String getAffiliation_commisionintnldlls() {
        return affiliation.getCommisionintnldlls() != null ? affiliation.getCommisionintnldlls().toString() : "";
    }
    
    public void setAffiliation_commisionintnldlls(Double affiliation_commisionintnldlls) {
        affiliation.setCommisionintnldlls(affiliation_commisionintnldlls);//VR
    }

    public void setAffiliation_commisionintnldlls(String affiliation_commisionintnldlls) {
        if (affiliation_commisionintnldlls != null && !affiliation_commisionintnldlls.trim().equals("")) {
            try {
                setAffiliation_commisionintnldlls(new Double(affiliation_commisionintnldlls));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_commisiontdmn() {
        return affiliation.getCommisiontdmn() != null ? affiliation.getCommisiontdmn().toString() : "";
    }

    public void setAffiliation_commisiontdmn(Double affiliation_commisiontdmn) {
        affiliation.setCommisiontdmn(affiliation_commisiontdmn);
    }

    public void setAffiliation_commisiontdmn(String affiliation_commisiontdmn) {
        if (affiliation_commisiontdmn != null && !affiliation_commisiontdmn.trim().equals("")) {
            try {
                setAffiliation_commisiontdmn(new Double(affiliation_commisiontdmn));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }
    
    public String getAffiliation_commisionintnlmn() {
        return affiliation.getCommisionintnlmn() != null ? affiliation.getCommisionintnlmn().toString() : "";
    }
    
    public void setAffiliation_commisionintnlmn(String affiliation_commisionintnlmn) {
        if (affiliation_commisionintnlmn != null && !affiliation_commisionintnlmn.trim().equals("")) {
            try {
                setAffiliation_commisionintnlmn(new Double(affiliation_commisionintnlmn));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }
    
    public void setAffiliation_commisionintnlmn(Double affiliation_commisionintnlmn) {
        affiliation.setCommisionintnlmn(affiliation_commisionintnlmn);//VR
    }

    public String getAffiliation_creditline() {
        return affiliation.getCreditline() != null ? affiliation.getCreditline().toString() : "";
    }

    public void setAffiliation_creditline(Integer affiliation_creditline) {
        affiliation.setCreditline(affiliation_creditline);
    }

    public void setAffiliation_creditline(String affiliation_creditline) {
        if (affiliation_creditline != null && !affiliation_creditline.trim().equals("")) {
            try {
                setAffiliation_creditline(new Integer(affiliation_creditline));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_creditlineamount() {
        return affiliation.getCreditlineamount() != null ? affiliation.getCreditlineamount().toString() : "";
    }

    public void setAffiliation_creditlineamount(Integer affiliation_creditlineamount) {
        affiliation.setCreditlineamount(affiliation_creditlineamount);
    }

    public void setAffiliation_creditlineamount(String affiliation_creditlineamount) {
        if (affiliation_creditlineamount != null && !affiliation_creditlineamount.trim().equals("")) {
            try {
                setAffiliation_creditlineamount(new Integer(affiliation_creditlineamount));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_creditlinetype() {
        return affiliation.getCreditlinetype() != null ? affiliation.getCreditlinetype().toString() : "";
    }

    public void setAffiliation_creditlinetype(String affiliation_creditlinetype) {
        affiliation.setCreditlinetype(affiliation_creditlinetype);
    }

    

    public String getAffiliation_currency() {
        return affiliation.getCurrency();
    }

    public void setAffiliation_currency(String affiliation_currency) {
        affiliation.setCurrency(affiliation_currency);
        if (affiliation_currency != null && affiliation_currency.equals("Dolares") || affiliation_currency.equals("Ambos")){
        affiliation.setCurrencydlls("X");
        }        
    }

    public String getAffiliation_currentbank() {
        return affiliation.getCurrentbank();
    }

    public void setAffiliation_currentbank(String affiliation_currentbank) {
        affiliation.setCurrentbank(affiliation_currentbank);
    }

    public String getAffiliation_depositamount() {
        return affiliation.getDepositamount() != null ? affiliation.getDepositamount().toString() : "";
    }

    public void setAffiliation_depositamount(Integer affiliation_depositamount) {
        affiliation.setDepositamount(affiliation_depositamount);
    }

    public void setAffiliation_depositamount(String affiliation_depositamount) {
        if (affiliation_depositamount != null && !affiliation_depositamount.trim().equals("")) {
            try {
                setAffiliation_depositamount(new Integer(affiliation_depositamount));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_depositcompany() {
        return affiliation.getDepositcompany();
    }

    public void setAffiliation_depositcompany(String affiliation_depositcompany) {
        affiliation.setDepositcompany(affiliation_depositcompany);
    }

    public String getAffiliation_dimention() {
        return affiliation.getDimention() != null ? affiliation.getDimention().toString() : "";
    }

    public void setAffiliation_dimention(Integer affiliation_dimention) {
        affiliation.setDimention(affiliation_dimention);
    }

    public void setAffiliation_dimention(String affiliation_dimention) {
        if (affiliation_dimention != null && !affiliation_dimention.trim().equals("")) {
            try {
                setAffiliation_dimention(new Integer(affiliation_dimention));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    

    public String getAffiliation_equipment() {
        return affiliation.getEquipment() != null ? affiliation.getEquipment().toString() : "";
    }

    public void setAffiliation_equipment(Integer affiliation_equipment) {
        affiliation.setEquipment(affiliation_equipment);
    }

    public void setAffiliation_equipment(String affiliation_equipment) {
        if (affiliation_equipment != null && !affiliation_equipment.trim().equals("")) {
            try {
                setAffiliation_equipment(new Integer(affiliation_equipment));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_extannounce() {
        return affiliation.getExtannounce() != null ? affiliation.getExtannounce().toString() : "";
    }

    public void setAffiliation_extannounce(Integer affiliation_extannounce) {
        affiliation.setExtannounce(affiliation_extannounce);
    }

    public void setAffiliation_extannounce(String affiliation_extannounce) {
        if (affiliation_extannounce != null && !affiliation_extannounce.trim().equals("")) {
            try {
                setAffiliation_extannounce(new Integer(affiliation_extannounce));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }

    public String getAffiliation_externalcredithistory() {
        return affiliation.getExternalcredithistory();
    }

    public void setAffiliation_externalcredithistory(String affiliation_externalcredithistory) {
        affiliation.setExternalcredithistory(affiliation_externalcredithistory);
    }

    public String getAffiliation_failminimiunbalancedlls() {
        return affiliation.getFailminimiunbalancedlls() != null ? affiliation.getFailminimiunbalancedlls().toString() : "";
    }

    public void setAffiliation_failminimiunbalancedlls(Double affiliation_failminimiunbalancedlls) {
        affiliation.setFailminimiunbalancedlls(affiliation_failminimiunbalancedlls);
    }

    public void setAffiliation_failminimiunbalancedlls(String affiliation_failminimiunbalancedlls) {
        if (affiliation_failminimiunbalancedlls != null && !affiliation_failminimiunbalancedlls.trim().equals("")) {
            try {
                setAffiliation_failminimiunbalancedlls(new Double(affiliation_failminimiunbalancedlls));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }
    
    public void addAffiliation_failminimiunbalancedlls(Double affiliation_failminimiunbalancedlls){
    	double result = 0.0 ;
    	result = affiliation_failminimiunbalancedlls + new Double (this.getAffiliation_failminimiunbalancedlls());
    	
    	this.setAffiliation_failminimiunbalancedlls(result);
    }

    public String getAffiliation_failminimiunbalancemn() {
        return affiliation.getFailminimiunbalancemn() != null ? affiliation.getFailminimiunbalancemn().toString() : "";
    }

    public void setAffiliation_failminimiunbalancemn(Double affiliation_failminimiunbalancemn) {
        affiliation.setFailminimiunbalancemn(affiliation_failminimiunbalancemn);
    }
    
    public void setAffiliation_failminimiunbalancemn(String affiliation_failminimiunbalancemn) {
        if (affiliation_failminimiunbalancemn != null && !affiliation_failminimiunbalancemn.trim().equals("")) {
            try {
                setAffiliation_failminimiunbalancemn(new Double(affiliation_failminimiunbalancemn));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }
    
    public void addAffiliation_failminimiunbalancemn(Double affiliation_failminimiunbalancemn){
    	double result = 0.0 ;
    	result = affiliation_failminimiunbalancemn + new Double (this.getAffiliation_failminimiunbalancemn());
    	
    	this.setAffiliation_failminimiunbalancemn(result);
    }

    public String getAffiliation_failmonthlyinvoicingdlls() {
        return affiliation.getFailmonthlyinvoicingdlls() != null ? affiliation.getFailmonthlyinvoicingdlls().toString() : "";
    }

    public void setAffiliation_failmonthlyinvoicingdlls(Double affiliation_failmonthlyinvoicingdlls) {
        affiliation.setFailmonthlyinvoicingdlls(affiliation_failmonthlyinvoicingdlls);
    }

    public void setAffiliation_failmonthlyinvoicingdlls(String affiliation_failmonthlyinvoicingdlls) {
        if (affiliation_failmonthlyinvoicingdlls != null && !affiliation_failmonthlyinvoicingdlls.trim().equals("")) {
            try {
                setAffiliation_failmonthlyinvoicingdlls(new Double(affiliation_failmonthlyinvoicingdlls));
            } catch (NumberFormatException ex) {
                log.log(Level.WARNING, ex.getMessage(), ex);
            }

        }
    }
    
    public void addAffiliation_failmonthlyinvoicingdlls(Double affiliation_failmonthlyinvoicingdlls){
    	double result = 0.0 ;
    	result = affiliation_failmonthlyinvoicingdlls + new Double (this.getAffiliation_failmonthlyinvoicingdlls());
    	
    	this.setAffiliation_failmonthlyinvoicingdlls(result);
    }
    
   

    public Double getAffiliation_failmonthlyinvoicingmn() {
        return affiliation.getFailmonthlyinvoicingmn();
    }

    public void setAffiliation_failmonthlyinvoicingmn(Double affiliation_failmonthlyinvoicingmn) {
        affiliation.setFailmonthlyinvoicingmn(affiliation_failmonthlyinvoicingmn);
    }
    
    public void addAffiliation_failmonthlyinvoicingmn(Double affiliation_failmonthlyinvoicingmn){
    	double result = 0.0 ;
    	result = affiliation_failmonthlyinvoicingmn + new Double (this.getAffiliation_failmonthlyinvoicingmn());
    	
    	this.setAffiliation_failmonthlyinvoicingmn(result);
    }
    

    public Double getAffiliation_failpormnotedlls() {
        return affiliation.getFailpormnotedlls();
    }

    public void setAffiliation_failpormnotedlls(Double affiliation_failpormnotedlls) {
        affiliation.setFailpormnotedlls(affiliation_failpormnotedlls);
    }

    public Double getAffiliation_failpromnotemn() {
        return affiliation.getFailpromnotemn();
    }

    public void setAffiliation_failpromnotemn(Double affiliation_failpromnotemn) {
        affiliation.setFailpromnotemn(affiliation_failpromnotemn);
    }

    public String getAffiliation_fiscaldoc() {
        return affiliation.getFiscaldoc();
    }

    public void setAffiliation_fiscaldoc(String affiliation_fiscaldoc) {
        affiliation.setFiscaldoc(affiliation_fiscaldoc);
    }

    public String getAffiliation_fiscalenrolment() {
        return affiliation.getFiscalenrolment();
    }

    public void setAffiliation_fiscalenrolment(String affiliation_fiscalenrolment) {
        affiliation.setFiscalenrolment(affiliation_fiscalenrolment);
    }

    public String getAffiliation_forceauth() {
        //return affiliation.getForceauth();
        return this.affiliation_forceauth;
    }

    public void setAffiliation_forceauth(String affiliation_forceauth) {
        this.affiliation_forceauth=affiliation_forceauth;
        
        if (affiliation_forceauth.equals("true"))
            affiliation.setForceauth("X");
        else
            affiliation.setForceauth("");
    }

    public Integer getAffiliation_havedepositcompany() {
        return affiliation.getHavedepositcompany();
    }

    public void setAffiliation_havedepositcompany(Integer affiliation_havedepositcompany) {
        affiliation.setHavedepositcompany(affiliation_havedepositcompany);
    }

    public String getAffiliation_internalcredithistory() {
        return affiliation.getInternalcredithistory();
    }

    public void setAffiliation_internalcredithistory(String affiliation_internalcredithistory) {
        affiliation.setInternalcredithistory(affiliation_internalcredithistory);
    }

    public Integer getAffiliation_inventary() {
        return affiliation.getInventary();
    }

    public void setAffiliation_inventary(Integer affiliation_inventary) {
        affiliation.setInventary(affiliation_inventary);
    }

    public String getAffiliation_legalopinion() {
        return affiliation.getLegalopinion();
    }

    public void setAffiliation_legalopinion(String affiliation_legalopinion) {
        affiliation.setLegalopinion(affiliation_legalopinion);
    }

    public Integer getAffiliation_locationtype() {
        return affiliation.getLocationtype();
    }

    public void setAffiliation_locationtype(Integer affiliation_locationtype) {
        affiliation.setLocationtype(affiliation_locationtype);
    }

    public Double getAffiliation_minimiunbalancedlls() {
        return affiliation.getMinimiunbalancedlls();
    }

    public void setAffiliation_minimiunbalancedlls(Double affiliation_minimiunbalancedlls) {
        affiliation.setMinimiunbalancedlls(affiliation_minimiunbalancedlls);
    }
    
    public void addAffiliation_minimiunbalancedlls(Double affiliation_minimiunbalancedlls){
    	double result = 0.0 ;
    	result = affiliation_minimiunbalancedlls + new Double (this.getAffiliation_minimiunbalancedlls());
    	
    	this.setAffiliation_minimiunbalancedlls(result);
    }

    public Double getAffiliation_minimiunbalancemn() {
        return affiliation.getMinimiunbalancemn();
    }

    public void setAffiliation_minimiunbalancemn(Double affiliation_minimiunbalancemn) {
        affiliation.setMinimiunbalancemn(affiliation_minimiunbalancemn);
    }
    
    public void addAffiliation_minimiunbalancemn(Double affiliation_minimiunbalancemn){
    	double result = 0.0 ;
    	result = affiliation_minimiunbalancemn + new Double (this.getAffiliation_minimiunbalancemn());
    	
    	this.setAffiliation_minimiunbalancemn(result);
    }

    public String getAffiliation_modality() {
        return affiliation.getModality();
    }

    public void setAffiliation_modality(String affiliation_modality) {
        affiliation.setModality(affiliation_modality);
    }

    public Double getAffiliation_monthlyinvoicingmindlls() {
        return affiliation.getMonthlyinvoicingmindlls();
    }

    public void setAffiliation_monthlyinvoicingmindlls(Double affiliation_monthlyinvoicingmindlls) {
        affiliation.setMonthlyinvoicingmindlls(affiliation_monthlyinvoicingmindlls);
    }
    
    public void addAffiliation_monthlyinvoicingmindlls(Double affiliation_monthlyinvoicingmindlls){
    	double result = 0.0 ;
    	result = affiliation_monthlyinvoicingmindlls + new Double (this.getAffiliation_monthlyinvoicingmindlls());
    	
    	this.setAffiliation_monthlyinvoicingmindlls(result);
    }

    public Double getAffiliation_monthlyinvoicingminmn() {
        return affiliation.getMonthlyinvoicingminmn();
    }

    public void setAffiliation_monthlyinvoicingminmn(Double affiliation_monthlyinvoicingminmn) {
        affiliation.setMonthlyinvoicingminmn(affiliation_monthlyinvoicingminmn);
    }
    
    public void addAffiliation_monthlyinvoicingminmn(Double affiliation_monthlyinvoicingminmn){
    	double result = 0.0 ;
    	result = affiliation_monthlyinvoicingminmn + new Double (this.getAffiliation_monthlyinvoicingminmn());
    	
    	this.setAffiliation_monthlyinvoicingminmn(result);
    }


    public Double getAffiliation_monthlyrate3dsdlls() {
        return affiliation.getMonthlyrate3dsdlls();
    }

    public void setAffiliation_monthlyrate3dsdlls(Double affiliation_monthlyrate3dsdlls) {
        affiliation.setMonthlyrate3dsdlls(affiliation_monthlyrate3dsdlls);
    }

    public Double getAffiliation_monthlyrate3dsmn() {
        return affiliation.getMonthlyrate3dsmn();
    }

    public void setAffiliation_monthlyrate3dsmn(Double affiliation_monthlyrate3dsmn) {
        affiliation.setMonthlyrate3dsmn(affiliation_monthlyrate3dsmn);
    }

    public Double getAffiliation_monthlyratedlls() {
        return affiliation.getMonthlyratedlls();
    }

    public void setAffiliation_monthlyratedlls(Double affiliation_monthlyratedlls) {
        affiliation.setMonthlyratedlls(affiliation_monthlyratedlls);
    }

    public Double getAffiliation_monthlyratemn() {
        return affiliation.getMonthlyratemn();
    }

    public void setAffiliation_monthlyratemn(Double affiliation_monthlyratemn) {
        affiliation.setMonthlyratemn(affiliation_monthlyratemn);
    }

    public Integer getAffiliation_monthlysales() {
        return affiliation.getMonthlysales();
    }

    public void setAffiliation_monthlysales(Integer affiliation_monthlysales) {
        affiliation.setMonthlysales(affiliation_monthlysales);
    }

    public String getAffiliation_numaffildlls() {
        return affiliation.getNumaffildlls();
    }

    public void setAffiliation_numaffildlls(String affiliation_numaffildlls) {
        affiliation.setNumaffildlls(affiliation_numaffildlls);
    }

    public String getAffiliation_numaffilmn() {
        return affiliation.getNumaffilmn();
    }

    public void setAffiliation_numaffilmn(String affiliation_numaffilmn) {
        affiliation.setNumaffilmn(affiliation_numaffilmn);
    }

    public String getAffiliation_officerdepositexent() {
        return affiliation.getOfficerdepositexent();
    }

    public void setAffiliation_officerdepositexent(String affiliation_officerdepositexent) {
        affiliation.setOfficerdepositexent(affiliation_officerdepositexent);
    }

    public String getAffiliation_openkey() {
       // return affiliation.getOpenkey();
        return this.affiliation_openkey;
    }

    public void setAffiliation_openkey(String affiliation_openkey) {
        this.affiliation_openkey=affiliation_openkey;
        
        if (affiliation_openkey.equals("true"))
            affiliation.setOpenkey("X");
         else
             affiliation.setOpenkey("");
    }
    
   

    public String getAffiliation_otherconcept1des() {
        return affiliation.getOtherconcept1des();
    }

    public void setAffiliation_otherconcept1des(String affiliation_otherconcept1des) {
        affiliation.setOtherconcept1des(affiliation_otherconcept1des);
    }

    public Double getAffiliation_otherconcept1dlls() {
        return affiliation.getOtherconcept1dlls();
    }

    public void setAffiliation_otherconcept1dlls(Double affiliation_otherconcept1dlls) {
        affiliation.setOtherconcept1dlls(affiliation_otherconcept1dlls);
    }

    public Double getAffiliation_otherconcept1mn() {
        return affiliation.getOtherconcept1mn();
    }

    public void setAffiliation_otherconcept1mn(Double affiliation_otherconcept1mn) {
        affiliation.setOtherconcept1mn(affiliation_otherconcept1mn);
    }

    public Integer getAffiliation_otherpartners() {
        return affiliation.getOtherpartners();
    }

    public void setAffiliation_otherpartners(Integer affiliation_otherpartners) {
        affiliation.setOtherpartners(affiliation_otherpartners);
    }

    public Integer getAffiliation_otherproducts() {
        return affiliation.getOtherproducts();
    }

    public void setAffiliation_otherproducts(Integer affiliation_otherproducts) {
        affiliation.setOtherproducts(affiliation_otherproducts);
    }

    public Integer getAffiliation_ownerage() {
        return affiliation.getOwnerage();
    }

    public void setAffiliation_ownerage(Integer affiliation_ownerage) {
        affiliation.setOwnerage(affiliation_ownerage);
    }

    public Integer getAffiliation_ownphone() {
        return affiliation.getOwnphone();
    }

    public void setAffiliation_ownphone(Integer affiliation_ownphone) {
        affiliation.setOwnphone(affiliation_ownphone);
    }

    public Integer getAffiliation_parkingtype() {
        return affiliation.getParkingtype();
    }

    public void setAffiliation_parkingtype(Integer affiliation_parkingtype) {
        affiliation.setParkingtype(affiliation_parkingtype);
    }

    public Integer getAffiliation_physicalconditions() {
        return affiliation.getPhysicalconditions();
    }

    public void setAffiliation_physicalconditions(Integer affiliation_physicalconditions) {
        affiliation.setPhysicalconditions(affiliation_physicalconditions);
    }

    public Integer getAffiliation_placerisk() {
        return affiliation.getPlacerisk();
    }

    public void setAffiliation_placerisk(Integer affiliation_placerisk) {
        affiliation.setPlacerisk(affiliation_placerisk);
    }

    public String getAffiliation_productdesc() {
        return affiliation.getProductdesc();
    }

    public void setAffiliation_productdesc(String affiliation_productdesc) {
        affiliation.setProductdesc(affiliation_productdesc);
    }

    public Integer getAffiliation_productstockplace() {
        return affiliation.getProductstockplace();
    }

    public void setAffiliation_productstockplace(Integer affiliation_productstockplace) {
        affiliation.setProductstockplace(affiliation_productstockplace);
    }

    public Double getAffiliation_promnotedlls() {
        return affiliation.getPromnotedlls();
    }

    public void setAffiliation_promnotedlls(Double affiliation_promnotedlls) {
        affiliation.setPromnotedlls(affiliation_promnotedlls);
    }

    public Double getAffiliation_promnotemn() {
        return affiliation.getPromnotemn();
    }

    public void setAffiliation_promnotemn(Double affiliation_promnotemn) {
        affiliation.setPromnotemn(affiliation_promnotemn);
    }

    public String getAffiliation_qps() {      
        return affiliation_qps;
    }

    public void setAffiliation_qps(String affiliation_qps) {
        this.affiliation_qps=affiliation_qps;
        
        if(affiliation_qps.equals("true"))
             affiliation.setQps("X");
        else
            affiliation.setQps("");
    }

    
    public String getAffiliation_currencydlls() {
        return affiliation.getCurrencydlls();
    }

    public void setAffiliation_currencydlls(String affiliation_currencydlls) {
        affiliation.setCurrencydlls(affiliation_currencydlls);
    }


     public String getAffiliation_manualtpv() {
        return affiliation.getManualtpv();
    }

    public void setAffiliation_manualtpv(String affiliation_manualtpv) {
       affiliation.setManualtpv(affiliation_manualtpv);
    }

     public String getAffiliation_electronictpv() {
        return affiliation.getElectronictpv();
    }

    public void setAffiliation_electronictpv(String affiliation_electronictpv) {
        affiliation.setElectronictpv(affiliation_electronictpv);
    }

     public String getAffiliation_autopayment() {
        return affiliation.getAutopayment();
    }

    public void setAffiliation_autopayment(String affiliation_autopayment) {
        affiliation.setAutopayment(affiliation_autopayment);
    }

     public String getAffiliation_multinet() {
        return affiliation.getMultinet();
    }

    public void setAffiliation_multinet(String affiliation_multinet) {
        affiliation.setMultinet(affiliation_multinet);
    }
    

    public Integer getAffiliation_quantbranches() {
        return affiliation.getQuantbranches();
    }

    public void setAffiliation_quantbranches(Integer affiliation_quantbranches) {
        affiliation.setQuantbranches(affiliation_quantbranches);
    }

    public Integer getAffiliation_quantemployees() {
        return affiliation.getQuantemployees();
    }

    public void setAffiliation_quantemployees(Integer affiliation_quantemployees) {
        affiliation.setQuantemployees(affiliation_quantemployees);
    }

    public Integer getAffiliation_quantmerchants() {
        return affiliation.getQuantmerchants();
    }

    public void setAffiliation_quantmerchants(Integer affiliation_quantmerchants) {
        affiliation.setQuantmerchants(affiliation_quantmerchants);
    }

    public Integer getAffiliation_quantprevbanks() {
        return affiliation.getQuantprevbanks();
    }

    public void setAffiliation_quantprevbanks(Integer affiliation_quantprevbanks) {
        affiliation.setQuantprevbanks(affiliation_quantprevbanks);
    }

    public String getAffiliation_recommended() {
        return affiliation.getRecommended();
    }

    public void setAffiliation_recommended(String affiliation_recommended) {
        affiliation.setRecommended(affiliation_recommended);
    }

    public Integer getAffiliation_rootedplace() {
        return affiliation.getRootedplace();
    }

    public void setAffiliation_rootedplace(Integer affiliation_rootedplace) {
        affiliation.setRootedplace(affiliation_rootedplace);
    }

    public Integer getAffiliation_salesemployee() {
        return affiliation.getSalesemployee();
    }

    public void setAffiliation_salesemployee(Integer affiliation_salesemployee) {
        affiliation.setSalesemployee(affiliation_salesemployee);
    }

    public Integer getAffiliation_societyage() {
        return affiliation.getSocietyage();
    }

    public void setAffiliation_societyage(Integer affiliation_societyage) {
        affiliation.setSocietyage(affiliation_societyage);
    }

    public Integer getAffiliation_socioeconomiclocation() {
        return affiliation.getSocioeconomiclocation();
    }

    public void setAffiliation_socioeconomiclocation(Integer affiliation_socioeconomiclocation) {
        affiliation.setSocioeconomiclocation(affiliation_socioeconomiclocation);
    }

    public String getAffiliation_soluciontype() {
        return affiliation.getSoluciontype();
    }

    public String getAffiliation_devicetype() {
        return this.affiliation_devicetype;
    }
    public void setAffiliation_devicetype(String affiliation_devicetype) {
         this.affiliation_devicetype = affiliation_devicetype;
    }
    
    public void setAffiliation_soluciontype(String affiliation_soluciontype) {
        affiliation.setSoluciontype(affiliation_soluciontype);
        if(affiliation.getProductdesc() !=null){
	        if (affiliation.getProductdesc().equals("Cargo Recurrente")){
	        	affiliation.setAutopayment("X");
	        	affiliation.setMultinet("");
	        } else {
	            if (affiliation.getProductdesc().equals("Interredes")){
	            	affiliation.setMultinet("X");
	            	affiliation.setAutopayment("");
	            }else{           
	                affiliation.setMultinet("");
	                affiliation.setAutopayment("");
	            }
	        }
	        
	        if (affiliation.getProductdesc().equals("Cargo Recurrente") || affiliation.getProductdesc().equals("Comercio Electronico")){        
	            setAffiliation_quantgprs(0);
	            setAffiliation_quantwifi(0);
	            affiliation.setTpvBlueInternet(0);
	            affiliation.setTpvBlueTel(0);
	            affiliation.setTpvInternet(0);
	            affiliation.setTpvInternetTel(0);
	            affiliation.setTpvMovil(0);
	            affiliation.setTpvTel(0);
	            affiliation.setWifiTel(0);
	            setAffiliation_quantlan(0);
	            setAffiliation_quantblue(0);
	            setAffiliation_quantdialup(0);
	            setAffiliation_quantmanual(0);
	            setAffiliation_quantpinpad(0);
	            affiliation.setManualtpv("");
	            affiliation.setElectronictpv("");
	        }
        }
    }

    public Double getAffiliation_transcriptorratedlls() {
        return affiliation.getTranscriptorratedlls();
    }

    public void setAffiliation_transcriptorratedlls(Double affiliation_transcriptorratedlls) {
        affiliation.setTranscriptorratedlls(affiliation_transcriptorratedlls);
    }

    public Double getAffiliation_transcriptorratemn() {
        return affiliation.getTranscriptorratemn();
    }

    public void setAffiliation_transcriptorratemn(Double affiliation_transcriptorratemn) {
        affiliation.setTranscriptorratemn(affiliation_transcriptorratemn);
    }

    public String getAffiliation_validid() {
        return affiliation.getValidid();
    }

    public void setAffiliation_validid(String affiliation_validid) {
        affiliation.setValidid(affiliation_validid);
    }

    public Double getAffiliation_yearlyratedlls() {
        return affiliation.getYearlyratedlls();
    }

    public void setAffiliation_yearlyratedlls(Double affiliation_yearlyratedlls) {
        affiliation.setYearlyratedlls(affiliation_yearlyratedlls);
    }

    public Double getAffiliation_yearlyratemn() {
        return affiliation.getYearlyratemn();
    }

    public void setAffiliation_yearlyratemn(Double affiliation_yearlyratemn) {
        affiliation.setYearlyratemn(affiliation_yearlyratemn);
    }

    public Integer getAffiliation_yearswithbank() {
        return affiliation.getYearswithbank();
    }

    public void setAffiliation_yearswithbank(Integer affiliation_yearswithbank) {
        affiliation.setYearswithbank(affiliation_yearswithbank);
    }

    public Integer getAffiliation_yearswithmerchant() {
        return affiliation.getYearswithmerchant();
    }

    public void setAffiliation_yearswithmerchant(Integer affiliation_yearswithmerchant) {
        affiliation.setYearswithmerchant(affiliation_yearswithmerchant);
    }

    public String getAffiliation_plangprs() {
        return affiliation.getPlangprs();
    }

    public void setAffiliation_plangprs(String affiliation_plangprs) {
        affiliation.setPlangprs(affiliation_plangprs);
    }

    public String getAffiliation_planwifi() {
        return affiliation.getPlanwifi();
    }

    public void setAffiliation_planwifi(String affiliation_planwifi) {
        affiliation.setPlanwifi(affiliation_planwifi);
    }

    public Integer getAffiliation_quantdialup() {
        return affiliation.getQuantdialup();
    }

    public void setAffiliation_quantdialup(Integer affiliation_quantdialup) {
        affiliation.setQuantdialup(affiliation_quantdialup!=null?affiliation_quantdialup:0);
        if(affiliation_quantdialup != null && affiliation_quantdialup.intValue() > 0){
        affiliation.setElectronictpv("X");
        }        
    }

    public Integer getAffiliation_quantgprs() {
        return affiliation.getQuantgprs();
    }

    public void setAffiliation_quantgprs(Integer affiliation_quantgprs) {
        affiliation.setQuantgprs(affiliation_quantgprs!=null?affiliation_quantgprs:0);
        if(affiliation_quantgprs != null && affiliation_quantgprs.intValue() > 0){
        affiliation.setElectronictpv("X");
        }
    }

    public Integer getAffiliation_quantlan() {
        return affiliation.getQuantlan();
    }

    public void setAffiliation_quantlan(Integer affiliation_quantlan) {
        affiliation.setQuantlan(affiliation_quantlan!=null?affiliation_quantlan:0);
        if(affiliation_quantlan != null && affiliation_quantlan.intValue() > 0){
        affiliation.setElectronictpv("X");
        }   
    }

    public Integer getAffiliation_quantmanual() {
        return affiliation.getQuantmanual();        
    }

    public void setAffiliation_quantmanual(Integer affiliation_quantmanual) {
        affiliation.setQuantmanual(affiliation_quantmanual!=null?affiliation_quantmanual:0);
        if(affiliation_quantmanual != null && affiliation_quantmanual.intValue() > 0){
        affiliation.setManualtpv("X");
        }
    }

    public Integer getAffiliation_quantpinpad() {
        return affiliation.getQuantpinpad();
    }

    public void setAffiliation_quantpinpad(Integer affiliation_quantpinpad) {
        affiliation.setQuantpinpad(affiliation_quantpinpad!=null?affiliation_quantpinpad:0);
    }

    
     public Integer getAffiliation_quantblue() {
        return affiliation.getQuantblue();
    }

    public void setAffiliation_quantblue(Integer affiliation_quantblue) {
        affiliation.setQuantblue(affiliation_quantblue!=null?affiliation_quantblue:0);
        if(affiliation_quantblue != null && affiliation_quantblue.intValue() > 0){
        affiliation.setElectronictpv("X");
        }
    }
    
    
    

    public Integer getAffiliation_quantwifi() {
        return affiliation.getQuantwifi();
    }

    public void setAffiliation_quantwifi(Integer affiliation_quantwifi) {
        affiliation.setQuantwifi(affiliation_quantwifi!=null?affiliation_quantwifi:0);
        if(affiliation_quantwifi != null && affiliation_quantwifi.intValue() > 0){
        affiliation.setElectronictpv("X");
        }  
    }

    public SelectItem[] getAffiliation_productdescArray() { //Elegir producto
        if (this.affiliation_productdescArray == null) {
            Attribute att = this.getAttributeMB().getByFieldname("affiliation_productdesc");
            if (att != null) {
                Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
                if (attOptCollection != null) {
                    affiliation_productdescArray = new SelectItem[attOptCollection.size()];
                    int i = 0;
                    for (AttributeOption attOpt : attOptCollection) {
                        affiliation_productdescArray[i] = new SelectItem(attOpt.getValue(), attOpt.getDescription());
                        i++;
                    }
                }
            }
        }
        return affiliation_productdescArray;
    }

    public void setAffiliation_productdescArray(SelectItem[] affiliation_productdescArray) {
        this.affiliation_productdescArray = affiliation_productdescArray;
    }

    public SelectItem[] getAffiliation_soluciontypeArray() {
        if (this.affiliation_soluciontypeArray == null) {
            Attribute att = this.getAttributeMB().getByFieldname("affiliation_soluciontype");
            if (att != null) {
                Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
                if (attOptCollection != null) {
                    affiliation_soluciontypeArray = new SelectItem[attOptCollection.size()];
                    int i = 0;
                    for (AttributeOption attOpt : attOptCollection) {
                        affiliation_soluciontypeArray[i] = new SelectItem(attOpt.getDescription(), attOpt.getDescription());
                        i++;
                    }
                }
            }
        }
        return affiliation_soluciontypeArray;
    }

    public void setAffiliation_soluciontypeArray(SelectItem[] affiliation_soluciontypeArray) {
        this.affiliation_soluciontypeArray = affiliation_soluciontypeArray;
    }

    public SelectItem[] getAffiliation_devicetypeArray() {
        if (this.affiliation_devicetypeArray == null) {
            Attribute att = this.getAttributeMB().getByFieldname("affiliation_devicetype");
            if (att != null) {
                Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
                if (attOptCollection != null) {
                	affiliation_devicetypeArray = new SelectItem[attOptCollection.size()];
                    int i = 0;
                    for (AttributeOption attOpt : attOptCollection) {
                    	affiliation_devicetypeArray[i] = new SelectItem(attOpt.getDescription(), attOpt.getDescription());
                        i++;
                    }
                }
            }
        }
        return affiliation_devicetypeArray;
    }

    public void setAffiliation_devicetypeArray(SelectItem[] affiliation_devicetypeArray) {
        this.affiliation_devicetypeArray = affiliation_devicetypeArray;
    }
    
    public SelectItem[] getAffiliation_modalityArray() {
        if (this.affiliation_modalityArray == null) {
            Attribute att = this.getAttributeMB().getByFieldname("affiliation_modality");
            if (att != null) {
                Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
                
                //para acomodar por id las opciones
                List<AttributeOption> listaAtt=(List<AttributeOption>)attOptCollection;
                Collections.sort(listaAtt, new AttributeOptionComparator());
                
                if (listaAtt != null) {
                    affiliation_modalityArray = new SelectItem[listaAtt.size()];
                    int i = 0;
                    for (AttributeOption attOpt : listaAtt) {
                        affiliation_modalityArray[i] = new SelectItem(attOpt.getDescription(), attOpt.getDescription());
                        i++;
                    }
                }
            }
        }
        return affiliation_modalityArray;
    }

    public void setAffiliation_modalityArray(SelectItem[] affiliation_modalityArray) {
        this.affiliation_modalityArray = affiliation_modalityArray;
    }

    public SelectItem[] getAffiliation_currencyArray() { //Elegir divisa
        if (this.affiliation_currencyArray == null) {
            Attribute att = this.getAttributeMB().getByFieldname("affiliation_currency");
            if (att != null) {
                Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
                if (attOptCollection != null) {
                    affiliation_currencyArray = new SelectItem[attOptCollection.size()];
                    int i = 0;
                    for (AttributeOption attOpt : attOptCollection) {
                        affiliation_currencyArray[i] = new SelectItem(attOpt.getDescription(), attOpt.getDescription());
                        i++;
                    }
                }
            }
        }
        return affiliation_currencyArray;

    }

    public void setAffiliation_currencyArray(SelectItem[] affiliation_currencyArray) {
        this.affiliation_currencyArray = affiliation_currencyArray;
    }

    public SelectItem[] getAffiliation_servicetypeArray() {
        if (this.affiliation_servicetypeArray == null) {
            Attribute att = this.getAttributeMB().getByFieldname("affiliation_servicetype");
            if (att != null) {
                Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
                if (attOptCollection != null) {
                    affiliation_servicetypeArray = new SelectItem[attOptCollection.size()];
                    int i = 0;
                    for (AttributeOption attOpt : attOptCollection) {
                        affiliation_servicetypeArray[i] = new SelectItem(attOpt.getDescription(), attOpt.getDescription());
                        i++;
                    }
                }
            }
        }
        return affiliation_servicetypeArray;
    }

    public void setAffiliation_servicetypoipeArray(SelectItem[] affiliation_servicetypeoipArray) {
        this.affiliation_servicetypeoipArray = affiliation_servicetypeoipArray;
    }
    
    public SelectItem[] getAffiliation_servicetypeoipArray() {
        if (this.affiliation_servicetypeoipArray == null) {
            Attribute att = this.getAttributeMB().getByFieldname("affiliation_servicetypeoip");
            if (att != null) {
                Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
                if (attOptCollection != null) {
                	affiliation_servicetypeoipArray = new SelectItem[attOptCollection.size()];
                    int i = 0;
                    for (AttributeOption attOpt : attOptCollection) {
                    	affiliation_servicetypeoipArray[i] = new SelectItem(attOpt.getDescription(), attOpt.getDescription());
                        i++;
                    }
                }
            }
        }
        return affiliation_servicetypeoipArray;
    }

    public void setAffiliation_servicetypeArray(SelectItem[] affiliation_servicetypeArray) {
        this.affiliation_servicetypeArray = affiliation_servicetypeArray;
    }
    public SelectItem[] getCategoriesArray() {
        if(this.categoriesArray==null){
            //Lista Giros
            List <Categories> categories=categoryAquirerBean.findAquirerAll();
                if(categories!=null){
                   categoriesArray = new SelectItem[categories.size()];
                    int i=0;
                    for (Categories cat:categories){
                        categoriesArray[i]=new SelectItem(cat.getName(),cat.getName());
                        i++;
                    }
                }
        }

        return categoriesArray;    
    }

    public void setCategoriesArray(SelectItem[] categoriesArray) {
        this.categoriesArray = categoriesArray;
    }
    
    
    public SelectItem[] getCitiesClientFsArray() {
      if (getClient_fsstate()!=null){
        List <Cities> cities=citiesBean.findByState(getClient_fsstate());
                if(cities!=null){
                   citiesClientFsArray = new SelectItem[cities.size()];
                    int i=0;
                    for (Cities cit:cities){
                        citiesClientFsArray[i]=new SelectItem(cit.getName(),cit.getName());
                        i++;
                    }
                 }
     }
        return citiesClientFsArray;
    }

    public void setCitiesClientFsArray(SelectItem[] citiesClientFsArray) {
        this.citiesClientFsArray = citiesClientFsArray;
    }
    

    /*
     * Property: affiliation_comercialplanArray
     */
    public void setAffiliation_comercialplanArray(SelectItem[] affiliation_comercialplanArray) {
        this.affiliation_comercialplanArray = affiliation_comercialplanArray;
    }
    
    public SelectItem[] getAffiliation_comercialplanArray() {
        if (this.affiliation_comercialplanArray == null) {
//        	this.affiliation_comercialplanArray = new ContractUtil().getComercialPlanArray(false);
        	this.affiliation_comercialplanArray = new ContractUtil().planArray(false, "banorte");
        }
        return this.affiliation_comercialplanArray;
    }

    /*
     * Property: affiliation_OIPComercialPlanArray
     */
    public void setAffiliation_OIPComercialPlanArray(SelectItem[] selectedItem) {
        this.affiliation_comercialplanArray = selectedItem;
    }
    
    public SelectItem[] getAffiliation_OIPComercialPlanArray() {
        if (this.affiliation_OIPComercialplanArray == null) {
        	this.affiliation_OIPComercialplanArray = new ContractUtil().planArray(true, "banorte");
        }
        return this.affiliation_OIPComercialplanArray;
    }

    public SelectItem[] getCommercialPlanArrayIxe() {
    	if(commercialPlanArrayIxe==null){
    		commercialPlanArrayIxe = new ContractUtil().planArray(false, "ixe");
    	}
		return commercialPlanArrayIxe;
	}

	public void setCommercialPlanArrayIxe(SelectItem[] commercialPlanArrayIxe) {
		this.commercialPlanArrayIxe = commercialPlanArrayIxe;
	}

	
	@Override
    public PdfTemplateBinding getPdfTemplateBinding() {
        return pdfTemplateBinding;
    }

    @Override
    public String getProductPrefix() {
        return "AA";
    }
    
     @Override
    public void getProductIdDetail() {
        if (affiliation.getProductdesc() !=null && affiliation.getProductdesc().equals("Comercio Electronico"))
         setProduct(productBean.findById(new Integer(5))); // Producto Adquirente Comercio Electronico= 5
        else{
        	if(this.isOIP){
        		setProduct(productBean.findById(new Integer(6))); // Producto Adquirente OIP = 6
        	}else{
	            if(affiliation.getSoluciontype() !=null && affiliation.getSoluciontype().equals("Tradicional"))
	                setProduct(productBean.findById(new Integer(4))); // Producto Adquirente Solucion Tradicional= 4
	            else
	                setProduct(productBean.findById(new Integer(3))); // Producto Adquirente Solucion Internet= 3  
        	}
        }  
    }  

    @Override
    public void setResetForm() {
    	affiliation = new Affiliation();
        super.clearFields();
//        avcommisiontcmnComplete="";
//        avcommisiontcdllsComplete="";
//        avcommisiontdmnComplete="";
//        avcommisiontddllsComplete="";
        affiliation_duedate="";
        affiliation_qps="false";
        affiliation_forceauth="false";
        affiliation_openkey="false";
        affiliation_devicetype="";
        this.affiliation_accountnumbermn ="";
        this.affiliation_accountnumberdlls ="";
        affiliation_telcel="";
        affiliation_movistar="";
        affiliation_iusacell="";
        affiliation_nextel="";
        replaceAmountratemn="";
        replaceAmountratedlls="";
        affiliation_tpv_payroll= ApplicationConstants.VALUE_FALSE; 
        optionMonthlyratemn = "";
        optionMonthlyratedlls ="";
        //cashback
        aff_cashback="false";
        aff_commCbChrg="0.0";
        aff_commCbPymt="0.0";
        affiliation_alliance_hidd="";
        affiliation_alliance="";
        orderFormLoaded=false;
        
        ixePlan="";//valor del combo de planes ixe
        banortePlan="";//valor del combo de planes banorte
        mobilePymnt = ApplicationConstants.VALUE_FALSE;
        aff_impulsoCaptacion = "false"; //Se agrego
        //GARANTIA LIQUIDA
        aff_garantiaLiquida="0";
        optionPorcentajeVentasDiarias="0";
        optionMontoFijoDiario="";
        aff_ventasEstimadasMensuales="";
	    aff_montoEstimadoDeTransaccion="";
	    aff_porcentajeGL="";
	    aff_montoInicial="false";
	    aff_montoGL="";
	    aff_porcentajeGL="";
	    aff_montoInicialGL="";
	    aff_porcentajeRestanteGL="";
	    aff_montoRestanteGL="";
	    aff_promMontoDiarioGL="";
	    aff_porcentajeDiarioGL="";
	    aff_diasAproxGL="";
	    aff_excepcionPorceGL="";
	    aff_glOriginal="";
	    aff_comentariosDisminucionExcepcionGL="";
	    /*aff_montoInicial="0";
	    aff_montoPromDiario="0";
	    aff_porcentajeGL="0";
	    aff_excepcionPorceGL=0;
	    aff_montoGL="0";
	    aff_porcentajeInicialGL="0";
	    aff_montoInicialGL="0";
	    aff_porcentajeRestanteGL="0";
	    aff_montoRestanteGL="0";
	    aff_porcentajeDiarioGL="0";
	    aff_promMontoDiarioGL="0";				
	    optionPorcentajeVentasDiarias="0";
	    optionMontoFijoDiario="0";	        
	    aff_ventasEstimadasMensuales="";
	    aff_montoEstimadoDeTransaccion="0";
	    
        */
	    exencionConvenienciaComercialVIP= ApplicationConstants.EMPTY_STRING;
	    exencionOtros= ApplicationConstants.EMPTY_STRING;
	    exencionJustificacion=ApplicationConstants.EMPTY_STRING;
	    solvenciaEconimicaSi= ApplicationConstants.EMPTY_STRING;
	    solvenciaEconimicaNo= ApplicationConstants.EMPTY_STRING;
	    visitaOcularRecienteSi= ApplicationConstants.EMPTY_STRING;
	    visitaOcularRecienteNo= ApplicationConstants.EMPTY_STRING;
	    riesgoReputacionalOperacionalSi= ApplicationConstants.EMPTY_STRING;
	    riesgoReputacionalOperacionalNo= ApplicationConstants.EMPTY_STRING;
	    descBienServicioOfrece= ApplicationConstants.EMPTY_STRING;
	    territorioNacionalSi= ApplicationConstants.EMPTY_STRING;
	    territorioNacionalNo= ApplicationConstants.EMPTY_STRING;
	    territorioNacionalEspecificacion= ApplicationConstants.EMPTY_STRING;
	    enNombreDeUnTerceroSi= ApplicationConstants.EMPTY_STRING;
	    enNombreDeUnTerceroNo= ApplicationConstants.EMPTY_STRING;
	    enNombreDeUnTerceroEspecificacion= ApplicationConstants.EMPTY_STRING;
	    antiguedadAnio= ApplicationConstants.EMPTY_STRING;
	    antiguedadMeses= ApplicationConstants.EMPTY_STRING;
	    //Mujer PyME
	    mujerPyME= ApplicationConstants.EMPTY_STRING;
	    esCuentaMujerPyME = ApplicationConstants.EMPTY_STRING;
	    cuentaMujerPyMEValidada = ApplicationConstants.EMPTY_STRING;
	    noEsCuentaMujerPyMEValidada = ApplicationConstants.EMPTY_STRING;
	    desactivarMujerPyME = ApplicationConstants.EMPTY_STRING;
	    isMsgMujerPyME=ApplicationConstants.VALUE_TRUE;
	    listaCuentaMujerPyMEVacia=this.getListaCuentaMujerPyMEVacia();	    
	    esClienteMujerPyME = ApplicationConstants.EMPTY_STRING;
	    clienteMujerPyMEValidado = ApplicationConstants.EMPTY_STRING;
	    noEsClienteMujerPyMEValidado = ApplicationConstants.EMPTY_STRING;
	    esCuentaMujerPyMEDlls = ApplicationConstants.EMPTY_STRING;
	    cuentaMujerPyMEValidadaDlls = ApplicationConstants.EMPTY_STRING;
	    noEsCuentaMujerPyMEValidadaDlls = ApplicationConstants.EMPTY_STRING;
	    
	    isMostrarMsjNoGrupo= ApplicationConstants.VALUE_TRUE;
	    
        antiguedad="6";
        
 	    ordenErrorsList.clear();
        this.pyme=false;
        
        this.isOIP=false;
        setStatusContract(statusBean.findById(new Integer(1))); // Status Nuevo = 1
        
        this.recalculateCommisionTable =false;
        
        affiliation_integration 	= ApplicationConstants.EMPTY_STRING;
        selected_hostBanorte 		= ApplicationConstants.EMPTY_STRING;
        selected_revision			= ApplicationConstants.EMPTY_STRING;
        selected_hostComercio		= ApplicationConstants.EMPTY_STRING;
        selected_2000 				= ApplicationConstants.EMPTY_STRING;
        selected_4000 				= ApplicationConstants.EMPTY_STRING;
        selected_direct3D			= ApplicationConstants.EMPTY_STRING;
        
        //reset ixe
        aff_exentDep = ApplicationConstants.VALUE_FALSE;
        amex = ApplicationConstants.VALUE_FALSE;
        dcc = ApplicationConstants.VALUE_FALSE;
        tpvUnattended = ApplicationConstants.VALUE_FALSE;
        adminName = ApplicationConstants.EMPTY_STRING;
        adminFLastName = ApplicationConstants.EMPTY_STRING;
        adminMLastName = ApplicationConstants.EMPTY_STRING;
        adminPhone = ApplicationConstants.EMPTY_STRING;
        adminEmail = ApplicationConstants.EMPTY_STRING;
        transConciliation = ApplicationConstants.EMPTY_STRING;
        promNoteRqst = ApplicationConstants.EMPTY_STRING;
        promNoteRqstName1 = ApplicationConstants.EMPTY_STRING;
        promNoteRqstFLastName1 = ApplicationConstants.EMPTY_STRING;
        promNoteRqstMLastName1 = ApplicationConstants.EMPTY_STRING;
        promNoteRqstEmail1 = ApplicationConstants.EMPTY_STRING;
        promNoteRqstPhone1 = ApplicationConstants.EMPTY_STRING;
        promNoteRqstComplete1 = ApplicationConstants.EMPTY_STRING;
        promNoteRqstName2 = ApplicationConstants.EMPTY_STRING;
        promNoteRqstFLastName2 = ApplicationConstants.EMPTY_STRING;
        promNoteRqstMLastName2 = ApplicationConstants.EMPTY_STRING;
        promNoteRqstEmail2 = ApplicationConstants.EMPTY_STRING;
        promNoteRqstPhone2 = ApplicationConstants.EMPTY_STRING;
        promNoteRqstComplete2 = ApplicationConstants.EMPTY_STRING;
        affiliation_chargeType="0";
        affiliation_chargeType_hidd="0";
        userState=userStatePack();
//        option3dSecure=null;
//        flag3dSelected=-1;
//        setAffiliation_monthlyrate3dsdlls(0d);
//        setAffiliation_monthlyrate3dsmn(0d);
    }

    public String getResetForm() {
    	setResetForm();
        return "";
    }
    
    public String userStatePack(){
    	FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) (facesContext.getExternalContext().getSession(true));
		Map<String, String> userSession = (HashMap) session.getAttribute(ApplicationConstants.PROFILE);
		String estado = userSession.get("estado");
		String userNumber=userSession.get(ApplicationConstants.UID);
		String paquete="";
		Set<String> estadosOCDT = new HashSet<String>();
		estadosOCDT.add("COLIMA");
		estadosOCDT.add("JALISCO");
		estadosOCDT.add("MICHOACAN");
		estadosOCDT.add("NAYARIT");
		if(userNumber.equalsIgnoreCase("hritm780")|| //Marce
				userNumber.equalsIgnoreCase("hmedg870")|| //Gina
				userNumber.equalsIgnoreCase("A3936864")|| //Mayra
				userNumber.equalsIgnoreCase("A3933865")|| //Jose luis
				userNumber.equalsIgnoreCase("A3556867") )//ISRAEL
		{
			paquete="ALL";
		}else if(estadosOCDT.contains(estado)){
			paquete="OCDT";
		}else if("SAN LUIS POTOSI".equalsIgnoreCase(estado)){
			paquete="SLP";
		}
		return paquete;
    }
    
    
    public String validate_action() {
			String option = this.getAffiliation_productdesc();
			if (option.trim().equals("Comercio Electronico")) {
				return "COMERCIO_ELECTRONICO";
			} else if (option.trim().equals("Cargo Recurrente")) {
				return "CARGOS_AUTOMATICOS";
			} else {
				
				//extraer las comisiones
				
				return "SUCCESS";
			}
			
    }
	
	public String navigation_ordenAfiliacionAction() {
		boolean validate 	= this.validateOrdenAfiliacionAdquiriente();
		if (validate) {
			/*if(this.isTPVNomina()){
				this.setAffiliation_nominaselected(ApplicationConstants.OPCION_SELECTED);
			}else{
				this.setAffiliation_nominaselected(ApplicationConstants.EMPTY_STRING);
			}*/
			//Validacion para marcar en el PDF la opcion Seleccionada , solo aplica para Cybersource 
			if (this.getAffiliation_alliance().equals(ApplicationConstants.ALLIANCE_CYBERSOURCE)){
				this.selectOptionIntegration();
				this.selectOptionRentaDolar();
			}
			//Antes de obtener la informacion almacenada del cliente, respaldar el giro comercial capturado por en el inicio.
			String categoryCode = this.getClient_categorycode();
			this.validacionCurrency();
			this.validacionDeposit();
			this.continueToGeneralInfo();
			//Actualizar el giro comercial por el capturado por el usuario
			this.setClient_categorycode(categoryCode);
			String option = this.getAffiliation_productdesc();
			String navigateTo = "";
			if (option.trim().equals("Comercio Electronico")) {
				navigateTo =  "COMERCIO_ELECTRONICO";
			} 
//			else if (option.trim().equals("Cargos Automaticos")) {
//				navigateTo = "CARGOS_AUTOMATICOS";
//			}
			else if (option.trim().equals("Terminal punto de venta") && this.isOIP) {
				navigateTo = "OIP";
			}else if("true".equalsIgnoreCase(transConciliation) || "true".equalsIgnoreCase(promNoteRqst)){
				navigateTo = "HERRAMIENTA_COMERCIOS";
			} 
			else {		        
				navigateTo = "SUCCESS";
			}
			
			this.setOrderFormLoaded(false);


			return navigateTo;
			
		} else {
			return "UNSUCCESS";
		}
    }
	
	public String validateCommerce(){
		 ordenErrorsList.clear();
		 Boolean add1Error = false;
		 Boolean add2Error = false;
		 
			ContractMessageErrors errors;
			if (this.getErrorsList() != null) {
				this.getErrorsList().clear();
			}
			
			if( adminName== null || adminName.trim().length() == 0){
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de especificar el nombre del contacto Administrador");
					ordenErrorsList.add(errors);
			}
			if( adminFLastName== null || adminFLastName.trim().length() == 0){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de especificar el apellido paterno del contacto Administrador");
				ordenErrorsList.add(errors);
			}
			if( adminMLastName== null || adminMLastName.trim().length() == 0){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de especificar el apellido materno del contacto Administrador");
				ordenErrorsList.add(errors);
			}
			if( adminEmail== null || adminEmail.trim().length() == 0){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de especificar el correo electronico del contacto Administrador");
				ordenErrorsList.add(errors);
			}
			if( adminPhone== null || adminPhone.trim().length() < 8){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de especificar el telefono del contacto Administrador");
				ordenErrorsList.add(errors);
			}
			
			if( (promNoteRqstName1== null || promNoteRqstName1.trim().length()==0)||
					(promNoteRqstMLastName1==null || promNoteRqstMLastName1.trim().length()==0)||
					(promNoteRqstFLastName1== null || promNoteRqstFLastName1.trim().length()==0)||
					(promNoteRqstPhone1==null || promNoteRqstPhone1.trim().length()<8)
					){
				add1Error=true;
			}
			if( (promNoteRqstName2== null || promNoteRqstName2.trim().length()==0)||
					(promNoteRqstMLastName2==null || promNoteRqstMLastName2.trim().length()==0)||
					(promNoteRqstFLastName2== null || promNoteRqstFLastName2.trim().length()==0)||
					(promNoteRqstPhone2==null || promNoteRqstPhone2.trim().length()<8)
					){
					add2Error=true;
				}
			if(add1Error && add2Error){
				errors = new ContractMessageErrors();
				errors.setMessage("Al menos un Contacto adicional para el modulo de peticion de pagares debe tener datos completos");
				ordenErrorsList.add(errors);
			}
			
			if (ordenErrorsList.isEmpty()){
				return "SUCCESS";
			}else{
				return "UNSUCCESS";
			}
			
	}
	
	private void validacionCurrency(){
		if( this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_PESOS) ){
		   this.setAffiliation_accountnumberdlls(ApplicationConstants.EMPTY_STRING);
		   this.setAffiliation_currencydlls(ApplicationConstants.EMPTY_STRING);
//		   this.setAvcommisiontcdllsComplete(ApplicationConstants.ZERO_DOUBLE_STR);
//		   this.setAvcommisiontddllsComplete(ApplicationConstants.ZERO_DOUBLE_STR);
//		   this.setAvcommisionintnldllsComplete(ApplicationConstants.ZERO_DOUBLE_STR);
		}
		  else if ( this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR) ){
		   this.setAffiliation_accountnumbermn(ApplicationConstants.EMPTY_STRING);
//		   this.setAvcommisiontcmnComplete(ApplicationConstants.ZERO_DOUBLE_STR);
//		   this.setAvcommisiontdmnComplete(ApplicationConstants.ZERO_DOUBLE_STR);
//		   this.setAvcommisionintnlmnComplete(ApplicationConstants.ZERO_DOUBLE_STR);
		}
	}
	
	
	private void validacionDeposit(){
		if(this.getAffiliation_havedepositcompany() != null){
			if(this.getAffiliation_havedepositcompany()==0){ //si no requiere fianza
				this.setAffiliation_depositcompany(ApplicationConstants.EMPTY_STRING);
				this.setAffiliation_depositamount(ApplicationConstants.EMPTY_STRING);
				this.setAffiliation_depositamount(ApplicationConstants.INIT_CONT_CERO);
				this.setAffiliation_officerdepositexent(ApplicationConstants.EMPTY_STRING);
				this.setAffiliation_duedate(ApplicationConstants.EMPTY_STRING);
			}
		}
		
	}

	
	
    public String navigation_isOIP() {
    	if(this.isOIP){
    		return "OIP";
    	}else{
    		return "SUCCESS";
    	}
    }

    public ArrayList<ContractMessageErrors> getOrdenErrorsList() {
        return ordenErrorsList;
    }

    public void setOrdenErrorsList(ArrayList<ContractMessageErrors> ordenErrorsList) {
        this.ordenErrorsList = ordenErrorsList;
    }
   
    
    
    
    public boolean validateOrdenAfiliacionAdquiriente() {
    	ordenErrorsList.clear();
		ContractMessageErrors errors;
		if (this.getErrorsList() != null) {
			this.getErrorsList().clear();
		}
		
		//Validacion Numero de Cuenta Concentradora Pesos
		
		if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_PESOS)){
			if(affiliation_accountnumbermn == null || affiliation_accountnumbermn.equals(ApplicationConstants.EMPTY_STRING)){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de especificar Orden de afiliacion  - Numero de Cuenta concentradora Pesos");
				ordenErrorsList.add(errors);
			}
		 }else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR)){
			 if(affiliation_accountnumberdlls == null || affiliation_accountnumberdlls.equals(ApplicationConstants.EMPTY_STRING)){
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de especificar Orden de afiliacion  - Numero de Cuenta concentradora Dolares");
					ordenErrorsList.add(errors);
				}
			
		 }else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)){
			 if(affiliation_accountnumbermn == null || affiliation_accountnumbermn.equals(ApplicationConstants.EMPTY_STRING)){
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de especificar Orden de afiliacion  - Numero de Cuenta concentradora Pesos");
					ordenErrorsList.add(errors);
				}
			 
			 if(affiliation_accountnumberdlls == null || affiliation_accountnumberdlls.equals(ApplicationConstants.EMPTY_STRING)){
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de especificar Orden de afiliacion  - Numero de Cuenta concentradora Dolares");
					ordenErrorsList.add(errors);
				}
		 }
		// Datos De fianza
		//si requiere fianza y no exenta
		//valida que haya fianza o garantia liquida
    	if(!("0".equalsIgnoreCase(aff_garantiaLiquida))){//garantia liquida para que no valide estos campos cuando se seleccine GL
			if (affiliation.getHavedepositcompany()!=null && affiliation.getHavedepositcompany()==1 && affiliation.getExentDep()==0){
	
				if (affiliation.getDepositcompany() == null || affiliation.getDepositcompany().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de especificar Orden de Afiliacion - Compañia de Fianza");
					ordenErrorsList.add(errors);
				}
	
				if (affiliation.getDepositamount() == null || affiliation.getDepositamount() <= 0) {
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de especificar Orden de Afiliacion - Monto Fianza");
					ordenErrorsList.add(errors);
				}
	
				if (this.getAffiliation_duedate() == null || this.getAffiliation_duedate().trim().length() == 0) {
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de especificar Orden de Afiliacion - Fecha de vencimiento");
					ordenErrorsList.add(errors);
				}
			} else if (affiliation.getHavedepositcompany()!=null && affiliation.getHavedepositcompany()==1 && affiliation.getExentDep()==1) //si requiere fianza y la exenta
			{
				if((affiliation.getOfficerdepositexent().isEmpty()) || (affiliation.getOfficerdepositexent()==null)||
						(affiliation.getOfficerdepositexent().trim().equalsIgnoreCase(""))){
					errors = new ContractMessageErrors();
					errors.setMessage("Favor de especificar Orden de Afiliacion - Nombre de quien autoriza excentar la fianza");
					ordenErrorsList.add(errors);
				}
			}
    	}

		if (affiliation.getCurrency() != null && affiliation.getCurrency().equals("Ambos")) {
			if (affiliation.getAccountnumberdlls() == null || affiliation.getAccountnumberdlls().trim().length() == 0) {
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de Capturar Orden de Afiliacion - No. Cuenta Concentradora Dolares");
				ordenErrorsList.add(errors);
			}
		}
		if(this.recalculateCommisionTable){
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de Capturar Orden de Afiliacion - Es necesario calcular las comisiones antes de continuar.");
			ordenErrorsList.add(errors);
		}
		assignPlan();
		int planId= Integer.parseInt(affiliation.getCommercialplan());
			
		if(PlanType.OTRO.value().intValue() == planId){
			//Validar el valor de campo Especifique
			if(this.getAffiliation_otherComercialPlan().equals("")){
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de Capturar Orden de Afiliacion - Especifique");
				ordenErrorsList.add(errors);
			}
		}
		if (PlanType.RENTABILIDAD.value().intValue() != planId
				&& PlanType.OTRO.value().intValue() != planId) {
			// Validacion de Giro Comercial
			if (this.getClient_categorycode().equals("")) {
				errors = new ContractMessageErrors();
				errors.setMessage("Favor de Capturar Orden de Afiliacion - Giro Comercial");
				ordenErrorsList.add(errors);
			} else {
				String msjError = "El Giro Comercial seleccionado no se encuentra establecido. Favor de seleccionar un Giro Comercial valido";
				// Validar que exista comisiones relacionadas al plan y giro
				// seleccionado
				String categoryCode = Formatter.padLeft(this.getClient_categorycode(), '0', 6);
				categoryCode = categoryCode.substring(0, 6); //AGREGAR QUE SE HAGA SI CATEGORYCODE ES MAYOR A 6
				
				CommisionPlan commision = null;

				try {

					commision = comissionPlanBean.findByPK(planId, categoryCode,isTPVNomina());

				} catch (Exception e) {
					msjError = "No se puede obtener las comisiones de TC y TD por el momento. Favor de seleccionar Plan:OTRO para poder continuar.";
				}

				if (commision == null) {
					errors = new ContractMessageErrors();
					errors.setMessage(msjError);
					ordenErrorsList.add(errors);
				}

			}
		}
	  	//Validar los equipos
      	String productOption = this.getAffiliation_productdesc();
      //	if(this.getClient_categorycode().length() > 6) {
      	String categoryCode 	= Formatter.padLeft(this.getClient_categorycode(), '0', 6);
		categoryCode 			= categoryCode.substring(0,6);
      	/*}else {
          	errors = new ContractMessageErrors();
			errors.setMessage("Favor de Capturar Orden de Afiliacion - Giro Comercial correcto");
			ordenErrorsList.add(errors);
      	}*/
		if (productOption.trim().equals("Terminal punto de venta") || 
				productOption.trim().equals("Interredes")) {
			if(!this.isOIP){
				validateQuantTpvs();
			}
		}
		
		if(! validateCommCashBack()){
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de Capturar Tabla de comisiones - Comision Paga y Retira Efectivo Ej: 00.00");
			ordenErrorsList.add(errors);
		}
		if("true".equalsIgnoreCase(aff_cashback)||"X".equalsIgnoreCase(aff_cashback)){
			String category = getClient_categorycode();
	    	category = category.substring(0,6);
	    	int code = Integer.parseInt(category);
			if(!validateCategory(code, "cashback")){
				errors = new ContractMessageErrors();
				errors.setMessage("El giro no es elegible para Paga y Retira Efectivo.  Favor de verificar");
				ordenErrorsList.add(errors);
			}	
		}
		if("Occidente 2016".equalsIgnoreCase(affiliation_chargeType)){
			String category = getClient_categorycode();
	    	category = category.substring(0,6);
	    	int code = Integer.parseInt(category);
	    	if(!validateCategory(code, "OCDT")){
	    		errors = new ContractMessageErrors();
				errors.setMessage("El giro no aplica para paquete OCCIDENTE 2016.  Favor de verificar");
				ordenErrorsList.add(errors);
	    	}
		}
		if(! validateTA()){
			errors = new ContractMessageErrors();
			errors.setMessage("Favor de Capturar Comisiones Tiempo Aire - Entre 0% y 5%");
			ordenErrorsList.add(errors);
		}
		
			
	  this.setErrorsList(ordenErrorsList);    
               
       if (ordenErrorsList.isEmpty()){           
           return true;}
       else{
          return false;}    
        
    }   
    
    //vailidar comision cashback
    public boolean validateCommCashBack(){
    	String charge = aff_commCbChrg.trim();
    	//System.out.println(charge);
    String regex = "[0-9]{1,3}(.[0-9]{1,3})?";
    
   //System.out.println(charge.matches(regex));
    if(charge.matches(regex)){
    	return true;
    }
    	return false;	
    }
        
    /**
     * Metodo para validar las comisiones de tiempo aire
     * Minimo 0% Maximo 5%
     * @return
     */
    public boolean validateTA(){
    	if(affiliation_tiempoaire!=null && "true".equals(affiliation_tiempoaire)){
    		Double telcel = Double.valueOf(affiliation_telcel);
        	Double movistar = Double.valueOf(affiliation_movistar);
        	Double iusacell=Double.valueOf(affiliation_iusacell);
        	Double nextel=Double.valueOf(affiliation_nextel);
    		if(telcel>5.0||movistar>5.0||iusacell>5.0||nextel>5.0){
    			return false;
    		}else if(telcel<0||movistar<0||iusacell<0||nextel<0){
    			return false;
    		}
    	}
    	return true;
    }
    
    public boolean validateCategory(int code, String type){//Validacion de categorias
    	Set<String> cashbackFam = new HashSet<String>();
    	cashbackFam.add("Farmacias");
    	cashbackFam.add("Gasolineras");
    	cashbackFam.add("Miscel¿¿nea");
    	cashbackFam.add("Otros");
    	cashbackFam.add("Refacciones y Ferreter¿¿as");
    	cashbackFam.add("Supermercados");
    	cashbackFam.add("Ventas al detalle (Retail)");
    	String product = "Adquirente";
    	Set<String> ocdtFam=new HashSet<String>();
    	ocdtFam.add("Agencias de Viajes");
    	ocdtFam.add("Colegios y Universidades");
    	ocdtFam.add("Comida Rapida");
    	ocdtFam.add("Educaci¿¿n B¿¿sica");
    	ocdtFam.add("Farmacias");
    	ocdtFam.add("Guarder¿¿as");
    	ocdtFam.add("Hospitales");
    	ocdtFam.add("Hoteles");
    	ocdtFam.add("M¿¿dicos y dentistas");
    	ocdtFam.add("Otros");
    	ocdtFam.add("Refacciones y Ferreter¿¿as");
    	ocdtFam.add("Restaurantes");
    	ocdtFam.add("Salones de Belleza");
    	ocdtFam.add("Ventas al detalle (Retail)");  	
    	//ocdtFam.add("Gasolineras");
 
    	String family = categoryAquirerBean.findFamilyByCode(code, product).getFamily();
    	if("cashback".equalsIgnoreCase(type)){//familias para cashback
    		if(cashbackFam.contains(family)){
        		return true;
        	}	
    	}else if("OCDT".equalsIgnoreCase(type)){//familias para paquete occidente2014
    		if(ocdtFam.contains(family)){
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Entra solo si es TPV o interredes
     * @return
     */
    public String validateQuantTpvs() {
       
//        ordenErrorsList.clear();
//         if(this.getErrorsList()!=null){
//          this.getErrorsList().clear();
//         } 
        ContractMessageErrors errors;
//       int gprs_cdma=affiliation.getQuantgprs()!=null?affiliation.getQuantgprs():0;
//       int wifi=affiliation.getQuantwifi()!=null?affiliation.getQuantwifi():0;
//       int lan=affiliation.getQuantlan()!=null?affiliation.getQuantlan():0;
//       int blue=affiliation.getQuantblue()!=null?affiliation.getQuantblue():0;
//       int dial=affiliation.getQuantdialup()!=null? affiliation.getQuantdialup():0;       
       int man=affiliation.getQuantmanual()!=null?affiliation.getQuantmanual():0;//transcriptora
       int pin=affiliation.getQuantpinpad()!=null?affiliation.getQuantpinpad():0;//pinpad
       int tel=affiliation.getTpvTel()!=null?affiliation.getTpvTel():0;
       int movil = affiliation.getTpvMovil()!=null?affiliation.getTpvMovil():0;
       int inter = affiliation.getTpvInternet()!=null?affiliation.getTpvInternet():0;
       int inttel = affiliation.getTpvInternetTel()!=null?affiliation.getTpvInternetTel():0;
       int bluetel = affiliation.getTpvBlueTel()!=null?affiliation.getTpvBlueTel():0;
       int gprs = affiliation.getQuantgprs()!=null?affiliation.getQuantgprs():0;
       int blueint = affiliation.getTpvBlueInternet()!=null?affiliation.getTpvBlueInternet():0;
       int wifi = affiliation.getQuantwifi()!=null?affiliation.getQuantwifi():0;
       int wifitel = affiliation.getWifiTel()!=null?affiliation.getWifiTel():0;
//       int trad=dial+man;
       int inte = pin+movil+inter+inttel+bluetel+gprs+blueint+wifi+wifitel;//la tvpTel(dialup) no se toma en cuenta ...antes:gprs_cdma+wifi+blue+lan
       int q = man+inte+tel;
       String modalidad = affiliation.getModality()!=null?affiliation.getModality():"";
       String alianza = affiliation_alliance!=null?affiliation_alliance:"";
       String producto = affiliation.getProductdesc()!=null?affiliation.getProductdesc():"";
       String propia = affiliation.getOwnTpvPinpad()!=null?affiliation.getOwnTpvPinpad():"";
       String solucion = affiliation.getSoluciontype()!=null?affiliation.getSoluciontype():"";
       String result="SUCCESS";
       
       if( inte>0 && "Tradicional".equalsIgnoreCase(solucion) && "Terminal punto de venta".equalsIgnoreCase(producto)){
           this.setAffiliation_soluciontype("Internet");
       }
        
//       if (q==0 && !affiliation.getProductdesc().equals("Interredes")){  //si es TPV
//           if(! affiliation.getModality().equalsIgnoreCase("Multicomercio/Servicomercio")){
//        	   errors= new ContractMessageErrors();
//               errors.setMessage("Favor de especificar el numero de equipos");
//               ordenErrorsList.add(errors);        	   
//           }   
//       }else{
//            if(affiliation.getQuantmanual()==null || affiliation.getQuantpinpad()==null ||affiliation.getTpvBlueInternet()==null||
//            		affiliation.getTpvBlueTel()==null || affiliation.getQuantgprs()==null || affiliation.getTpvInternet()==null||
//            		affiliation.getTpvInternetTel()==null|| affiliation.getTpvMovil()==null || affiliation.getTpvTel()==null||
//            		affiliation.getQuantwifi()==null||affiliation.getWifiTel()==null)
//            {              
//              q=0;
//              errors= new ContractMessageErrors();
//              errors.setMessage("Favor de escribir 0 en los equipos que no desea contratar");
//              ordenErrorsList.add(errors);
//            }
//       }

//       if (q>0 || (q==0 && affiliation.getProductdesc().equals("Interredes"))){
//           return "SUCCESS";}
//       else{
       
       if(producto.equalsIgnoreCase("Terminal punto de venta")){
    	   if(alianza.equalsIgnoreCase("Netpay")){
    		   if(! modalidad.equalsIgnoreCase("Multicomercio/Servicomercio")){
    			   if(gprs==0){
    				   if(! propia.equalsIgnoreCase("X")){
    					   result="UNSUCCESS";
            			   errors= new ContractMessageErrors();
            			   errors.setMessage("Favor de seleccionar un equipo GPRS");
            			   ordenErrorsList.add(errors);
    				   }
        		   }
    		   }
    	   }
       }
       /*if(producto.equalsIgnoreCase("Interredes")){
    	   if(! modalidad.equalsIgnoreCase("Multicomercio/Servicomercio")){
    		   if(pin==0){
    			   if(! propia.equalsIgnoreCase("X")){
    				   result="UNSUCCESS";
        			   errors= new ContractMessageErrors();
        			   errors.setMessage("Favor de seleccionar un equipo Pinpad");
        			   ordenErrorsList.add(errors);   
    			   }
    		   }
    	   }
       }*/
       
           return result;       
       
    }
    
    
    public void assignPlan(){
    	if(red==null || red.equalsIgnoreCase("0")){
    		affiliation.setCommercialplan(banortePlan);
    	}else if(red.equalsIgnoreCase("1")){
    		affiliation.setCommercialplan(ixePlan);
    	}
    }
    /*//tabla comisiones
     * Metodo que calcula las comisiones y tarifas de la afiliacion
     * Diciembre 2012 se Modifico la funcionalidad para los calculos nuevos de TPV-Nomina
     */
    public void getCalculateComissionPlan(){
    	assignPlan();
    	ContractUtil contractUtil 	= new ContractUtil();
    	int planId 					= Integer.parseInt(affiliation.getCommercialplan());
    	String categoryCode 		= Formatter.padLeft(this.getClient_categorycode(), '0', 6);
    	Double equivalentAmount 	= 0.0;
    	Double penalty				= 0.0;
    	Double replaceAmountmn		=Double.valueOf( this.getReplaceAmountratemn() );
    	Double replaceAmountdlls	=Double.valueOf( this.getReplaceAmountratedlls() );
    	double monthlyRate 			= 0;
    	double transcriptorRate 	= 0;
    	double monthlyRate3D 		= 0;
    	CommisionPlan commision 	= null;
    	
    	//Limpiar los campos para funcionamiento
		this.setCommchargetype("Porcentaje");
		this.initializePesosFields();
		this.initializeDllsFields();
		
		
		//Extraer las Comisiones segun el plan y giro comercial
    	if(PlanType.RENTABILIDAD.value().intValue() != planId && PlanType.OTRO.value().intValue() != planId){
    		
    		//Si elige el PLAN 10,30,70 o 150
    		this.setCommchargetype(ApplicationConstants.COMMCHARGE_TYPE_PORCENTAJE);
    		
    		Plan plan 		= planBean.findById(planId);
    		categoryCode 	= categoryCode.substring(0,6);
    		commision 		= comissionPlanBean.findByPK(planId, categoryCode,isTPVNomina());
    		
	    	// Calculo transcriptorRate
	        int transcriptor	= affiliation.getQuantmanual()!=null?affiliation.getQuantmanual():0;
	        transcriptorRate = transcriptor * (plan.getTranscriptorRate()!=null?plan.getTranscriptorRate():0d);
//	        IncomePlan income 	= incomePlanBean.findByPK(planId, EquipmentType.FIXED.value());//equipo fijo 
//	        transcriptorRate 	= transcriptor * income.getMonthlyRate().doubleValue();
//	        transcriptorRate=plan.getTranscriptorRate()!=null?plan.getTranscriptorRate():0d;
	        
	        //Calculo Renta Mensual
	    	monthlyRate = calculateMonthlyRate(plan);
	    	
	    	//Calculo de renta mensual de 3D Secure
	    	if(this.getAffiliation_productdesc().equals("Comercio Electronico")){
	    		if( (this.getAffiliation_alliance().equals("Tradicional") && this.getAffiliation_integration().equals("Sin Cybersource / Con 3D"))
	    				|| (this.getAffiliation_alliance().equals("Hosted") && this.getAffiliation_integration().equals("Payworks Hosted")) ){
	    			monthlyRate3D = monthlyRate;
	    		}
//	    		else if(affiliation_alliance.equalsIgnoreCase("Ninguna")){
//	    			if("true".equalsIgnoreCase(option3dSecure)){
//	    				if(planId==4 || planId==7 || planId==13 || planId==14){
//			    			if(affiliation.getCurrency().equalsIgnoreCase("Pesos") 
//			    					||affiliation.getCurrency().equalsIgnoreCase("Ambos")){
//			    				monthlyRate3D=affiliation.getMonthlyrate3dsmn()!=null?affiliation.getMonthlyrate3dsmn():0;
//			    				affiliation.setMonthlyrate3dsdlls(0d);//se pone el campo de dolares en ceros
//			    			}else if(affiliation.getCurrency().equalsIgnoreCase("Dolares")){
//			    				monthlyRate3D=affiliation.getMonthlyrate3dsdlls()!=null?affiliation.getMonthlyrate3dsdlls():0;
//			    				affiliation.setMonthlyrate3dsmn(0d);//se pone el campo de pesos en ceros
//			    			}
//			    		}else{//Para planes que no aplica campo editable
//			    			if("Dolares".equalsIgnoreCase(affiliation.getCurrency())){
//			    				affiliation.setMonthlyrate3dsmn(0d);
//			    			}else{
//			    				affiliation.setMonthlyrate3dsdlls(0d);
//			    			}
//			    			monthlyRate3D = plan.getMonthly3D().doubleValue();	//250
//			    		}		
//	    			}
//	    		}
	    	}

    		if(affiliation.getCurrency().equals("Pesos")){
    			monthlyRate 		= monthlyRate - replaceAmountmn;
	    		equivalentAmount 	= contractUtil.calculateEquivalentTPV(replaceAmountmn);
		    	penalty 			= contractUtil.calculatePenaltyTPV(replaceAmountmn);
    			
	    		this.initializeDllsFields();
	    		this.setCommisionInPesos(plan, commision,transcriptorRate, monthlyRate, monthlyRate3D);
	    		
	    		if( isSaldomn() ){//sustituir saldo
	    			this.addAffiliation_minimiunbalancemn(equivalentAmount);
	    			this.addAffiliation_failminimiunbalancemn(penalty);
	    		}else if ( isFacturacionmn() ){//sustituir facturacion
	    			this.addAffiliation_monthlyinvoicingminmn(equivalentAmount);
		    		this.addAffiliation_failmonthlyinvoicingmn(penalty);
	    		}
	    			    		
	    		//this.discountTasaTPVNomina(categoryCode, this.getAvcommisiontcmnComplete(), 
	    		//		this.getAvcommisiontdmnComplete(), planId, this.getAffiliation_tpv_payroll());
	    		this.setReplaceAmountratemn(replaceAmountmn.toString());
	    		
	    	}else if(affiliation.getCurrency().equals("Dolares")){
	    		monthlyRate = monthlyRate - replaceAmountdlls;
	    		equivalentAmount 	= contractUtil.calculateEquivalentTPV(replaceAmountdlls);
	    		penalty 			= contractUtil.calculatePenaltyTPV(replaceAmountdlls);
	    		equivalentAmount	= CurrencyConverter.toDollar(equivalentAmount);
	    		penalty 			= CurrencyConverter.toDollar(penalty);
	    		
	    		this.initializePesosFields();
	    		this.setCommisionInDollar(plan, commision, transcriptorRate, monthlyRate, monthlyRate3D,true);
	    		
	    		if( isSaldodlls() ){
	    			this.addAffiliation_minimiunbalancedlls(equivalentAmount);
	    			this.addAffiliation_failminimiunbalancedlls(penalty);
	    		}else if ( isFacturaciondlls() ){
	    			this.addAffiliation_monthlyinvoicingmindlls(equivalentAmount);
		    		this.addAffiliation_failmonthlyinvoicingdlls(penalty);
	    		}
	    		
	    		//this.discountTasaTPVNomina(categoryCode, this.getAvcommisiontcdllsComplete(), 
	    		//	this.getAvcommisiontddllsComplete(), planId, this.getAffiliation_tpv_payroll());
	    		this.setReplaceAmountratedlls(replaceAmountdlls.toString());
	    		
	    	}else{//Divisa Ambos
	    		monthlyRate 		= monthlyRate - replaceAmountmn;
	    		equivalentAmount 	= contractUtil.calculateEquivalentTPV(replaceAmountmn);
	    		penalty 			= contractUtil.calculatePenaltyTPV(replaceAmountmn);
	    		
	    		this.setCommisionInPesos(plan, commision, transcriptorRate, monthlyRate, monthlyRate3D);
	    		this.setCommisionInDollar(plan, commision, transcriptorRate, monthlyRate, monthlyRate3D, false);
	    		
	    		if( isSaldomn() ){
	    			this.addAffiliation_minimiunbalancemn(equivalentAmount);
	    			this.addAffiliation_failminimiunbalancemn(penalty);
	    		}else if ( isFacturacionmn() ){
	    			this.addAffiliation_monthlyinvoicingminmn(equivalentAmount);
		    		this.addAffiliation_failmonthlyinvoicingmn(penalty);
	    		}
	    		
	    		//this.discountTasaTPVNomina(categoryCode, this.getAvcommisiontcmnComplete(), 
	    		//		this.getAvcommisiontdmnComplete(), planId, this.getAffiliation_tpv_payroll());
	    		this.setReplaceAmountratemn(replaceAmountmn.toString());
	    		
	    	}
    
    	}
    	
    	//clear errors
		ordenErrorsList.clear();
		if (this.getErrorsList() != null) {
			this.getErrorsList().clear();
		}
		
    	if(commision != null){
    		loadInfoPlanAlliance(commision);
    	}else if(commision==null && red.equalsIgnoreCase("1")){ //si es ixe y la comision es nula 
    		if(PlanType.RENTABILIDAD.value().intValue() != planId && PlanType.OTRO.value().intValue() != planId){ //y el plan es dif a esRent u Otro
        		ContractMessageErrors errors= new ContractMessageErrors();
                errors.setMessage("El giro con el plan seleccionado no aplica para Ixe");
                ordenErrorsList.add(errors);
    		}
    	}
    	this.setErrorsList(ordenErrorsList);  
    	
    	assignRentaDolar();
    	this.setRecalculateCommisionTable(false);
		this.setOrderFormLoaded(true);
    }
  
    
    private void loadInfoPlanAlliance( CommisionPlan commision){
    	BigDecimal tasa 			= new BigDecimal(0);
    	BigDecimal valueComsionTC 	= new BigDecimal(0); 
    	BigDecimal operationTC 		= new BigDecimal(0);
    	BigDecimal valueComsionTD 	= new BigDecimal(0); 
    	BigDecimal operationTD 		= new BigDecimal(0);
    	BigDecimal valueComsionINT 	= new BigDecimal(0); 
    	BigDecimal operationINT 	= new BigDecimal(0);
    //	Attribute att 				= new Attribute();
    	
    								/*INICIO  SI SE SELECCIONO MICROS*/ 
    	if( this.getAffiliation_alliance().equals(ApplicationConstants.ALLIANCE_MICROS )){
    		if( this.getAffiliation_chargeType().equals(ApplicationConstants.MICROS_CHARGE_TYPE_TASA )){// % en tasa
    			tasa = new BigDecimal(ApplicationConstants.COMM_TASA15);
	    		
    			valueComsionTC = BigDecimal.valueOf( commision.getCommisionTC() );
    			operationTC = valueComsionTC.add(tasa) ;
    			valueComsionTD = BigDecimal.valueOf( commision.getCommisionTD() );
    			operationTD = valueComsionTD.add(tasa) ;
    			valueComsionINT = BigDecimal.valueOf( commision.getCommisionINTNL() );
    			operationINT = valueComsionINT.add(tasa);
	    			 
    			if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_PESOS)){
    				this.setAffiliation_avcommisiontcmn(operationTC.toString());
    				this.setAffiliation_avcommisiontdmn(operationTD.toString());
    				this.setAffiliation_avcommisionintnlmn(operationINT.toString());
    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR)){
    				this.setAffiliation_avcommisiontcdlls(operationTC.toString());
    				this.setAffiliation_avcommisiontddlls(operationTD.toString());
    				this.setAffiliation_avcommisionintnldlls(operationINT.toString());
    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)){
    				this.setAffiliation_avcommisiontcmn(operationTC.toString());
    				this.setAffiliation_avcommisiontcdlls(operationTC.toString());
    				
    				this.setAffiliation_avcommisiontdmn(operationTD.toString());
    				this.setAffiliation_avcommisiontddlls(operationTD.toString());
    				
    				this.setAffiliation_avcommisionintnlmn(operationINT.toString());
    				this.setAffiliation_avcommisionintnldlls(operationINT.toString());
    			}
	    	    
    			this.setAffiliation_otherconcept1des(ApplicationConstants.EMPTY_STRING);
    		}else if( this.getAffiliation_chargeType().equals( ApplicationConstants.MICROS_CHARGE_TYPE_TRANS )){// $ por transaccion
    			if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_PESOS)){
    				transactionFeeMicrosMn=ApplicationConstants.MICROS_DIVISA_PESOS_TRANS;
    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR)){
    				transactionFeeMicrosDlls=ApplicationConstants.MICROS_DIVISA_DOLLAR_TRANS;
    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)){
    				transactionFeeMicrosDlls=ApplicationConstants.MICROS_DIVISA_DOLLAR_TRANS;
    				transactionFeeMicrosMn=ApplicationConstants.MICROS_DIVISA_PESOS_TRANS;
    			}
    		}else if( this.getAffiliation_chargeType().equals( ApplicationConstants.MICROS_CHARGE_TYPE_RENTA )){// $ por transaccion
    			tasa = new BigDecimal(ApplicationConstants.COMM_TASA05);
	    		
    			valueComsionTC = BigDecimal.valueOf( commision.getCommisionTC() );
    			operationTC = valueComsionTC.add(tasa) ;
    			valueComsionTD = BigDecimal.valueOf( commision.getCommisionTD() );
    			operationTD = valueComsionTD.add(tasa) ;
    			valueComsionINT = BigDecimal.valueOf( commision.getCommisionINTNL() );
    			operationINT = valueComsionINT.add(tasa);
	    			 
    			if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_PESOS)){
    				this.setAffiliation_avcommisiontcmn(operationTC.toString());
    				this.setAffiliation_avcommisiontdmn(operationTD.toString());
    				this.setAffiliation_avcommisionintnlmn(operationINT.toString());
    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR)){
    				this.setAffiliation_avcommisiontcdlls(operationTC.toString());
    				this.setAffiliation_avcommisiontddlls(operationTD.toString());
    				this.setAffiliation_avcommisionintnldlls(operationINT.toString());
    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)){
    				this.setAffiliation_avcommisiontcmn(operationTC.toString());
    				this.setAffiliation_avcommisiontcdlls(operationTC.toString());
    				
    				this.setAffiliation_avcommisiontdmn(operationTD.toString());
    				this.setAffiliation_avcommisiontddlls(operationTD.toString());
    				
    				this.setAffiliation_avcommisionintnlmn(operationINT.toString());
    				this.setAffiliation_avcommisionintnldlls(operationINT.toString());
    			}
    		}
    	}				/* FIN  SI SE SELECCIONO MICROS*/ 
    	
    					/* INICIO SI SE SELECCIONO NETPAY*/ 
    	else if( this.getAffiliation_alliance().equals(ApplicationConstants.ALLIANCE_NETPAY )){
    		if(affiliation.getProductdesc().equalsIgnoreCase("Terminal punto de venta")){
    			if(affiliation_chargeType.equalsIgnoreCase("(+)0.5 ppb en tasa")){
    				valueComsionTC = BigDecimal.valueOf(commision.getCommisionTC());
    				operationTC = valueComsionTC.add(BigDecimal.valueOf(0.50)) ;
	    			valueComsionTD = BigDecimal.valueOf(commision.getCommisionTD());
	    			operationTD = valueComsionTD.add(BigDecimal.valueOf(0.50));
	    			valueComsionINT = BigDecimal.valueOf( commision.getCommisionINTNL() );
	    			operationINT = valueComsionINT.add(BigDecimal.valueOf(0.50));
	    			
	    			if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_PESOS)){
	    				this.setAffiliation_avcommisiontcmn(operationTC.toString());
	    				this.setAffiliation_avcommisiontdmn(operationTD.toString());
	    				this.setAffiliation_avcommisionintnlmn(operationINT.toString());
	    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR)){
	    				this.setAffiliation_avcommisiontcdlls(operationTC.toString());
	    				this.setAffiliation_avcommisiontddlls(operationTD.toString());
	    				this.setAffiliation_avcommisionintnldlls(operationINT.toString());
	    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)){
	    				this.setAffiliation_avcommisiontcmn(operationTC.toString());
	    				this.setAffiliation_avcommisiontcdlls(operationTC.toString());
	    				
	    				this.setAffiliation_avcommisiontdmn(operationTD.toString());
	    				this.setAffiliation_avcommisiontddlls(operationTD.toString());
	    				
	    				this.setAffiliation_avcommisionintnlmn(operationINT.toString());
	    				this.setAffiliation_avcommisionintnldlls(operationINT.toString());
	    			}
				}
    		}else if(affiliation.getProductdesc().equalsIgnoreCase("Interredes")){
				if(affiliation_chargeType.equalsIgnoreCase("2- 1 a 20 afiliaciones")){
					valueComsionTC = BigDecimal.valueOf( commision.getCommisionTC() );
	    			operationTC = valueComsionTC.add(BigDecimal.valueOf(0.35)) ;
	    			valueComsionTD = BigDecimal.valueOf( commision.getCommisionTD() );
	    			operationTD = valueComsionTD.add(BigDecimal.valueOf(0.35));
	    			valueComsionINT = BigDecimal.valueOf( commision.getCommisionINTNL() );
	    			operationINT = valueComsionINT.add(BigDecimal.valueOf(0.35));
	    			
	    			if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_PESOS)){
	    				this.setAffiliation_avcommisiontcmn(operationTC.toString());
	    				this.setAffiliation_avcommisiontdmn(operationTD.toString());
	    				this.setAffiliation_avcommisionintnlmn(operationINT.toString());
	    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR)){
	    				this.setAffiliation_avcommisiontcdlls(operationTC.toString());
	    				this.setAffiliation_avcommisiontddlls(operationTD.toString());
	    				this.setAffiliation_avcommisionintnldlls(operationINT.toString());
	    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)){
	    				this.setAffiliation_avcommisiontcmn(operationTC.toString());
	    				this.setAffiliation_avcommisiontcdlls(operationTC.toString());
	    				
	    				this.setAffiliation_avcommisiontdmn(operationTD.toString());
	    				this.setAffiliation_avcommisiontddlls(operationTD.toString());
	    				
	    				this.setAffiliation_avcommisionintnlmn(operationINT.toString());
	    				this.setAffiliation_avcommisionintnldlls(operationINT.toString());
	    			}
				}else if(affiliation_chargeType.equalsIgnoreCase("3- (+)de 20 afiliaciones")){
					valueComsionTC = BigDecimal.valueOf( commision.getCommisionTC() );
	    			operationTC = valueComsionTC.add(BigDecimal.valueOf(0.25)) ;
	    			valueComsionTD = BigDecimal.valueOf( commision.getCommisionTD() );
	    			operationTD = valueComsionTD.add(BigDecimal.valueOf(0.25));
	    			valueComsionINT = BigDecimal.valueOf( commision.getCommisionINTNL() );
	    			operationINT = valueComsionINT.add(BigDecimal.valueOf(0.25));
	    			
	    			if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_PESOS)){
	    				this.setAffiliation_avcommisiontcmn(operationTC.toString());
	    				this.setAffiliation_avcommisiontdmn(operationTD.toString());
	    				this.setAffiliation_avcommisionintnlmn(operationINT.toString());
	    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR)){
	    				this.setAffiliation_avcommisiontcdlls(operationTC.toString());
	    				this.setAffiliation_avcommisiontddlls(operationTD.toString());
	    				this.setAffiliation_avcommisionintnldlls(operationINT.toString());
	    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)){
	    				this.setAffiliation_avcommisiontcmn(operationTC.toString());
	    				this.setAffiliation_avcommisiontcdlls(operationTC.toString());
	    				
	    				this.setAffiliation_avcommisiontdmn(operationTD.toString());
	    				this.setAffiliation_avcommisiontddlls(operationTD.toString());
	    				
	    				this.setAffiliation_avcommisionintnlmn(operationINT.toString());
	    				this.setAffiliation_avcommisionintnldlls(operationINT.toString());
	    			}
				}
    		}else if( this.getAffiliation_chargeType().equals(ApplicationConstants.NETPAY_CHARGE_TYPE_TASA )){
				if(this.getAffiliation_comercialplan().equals(ApplicationConstants.COMERCIALPLAN10)) {//plan15
					tasa = new BigDecimal(ApplicationConstants.COMM_TASA20);
				}else if(this.getAffiliation_comercialplan().equals(ApplicationConstants.COMERCIALPLAN30)) { //plan 35
					tasa = new BigDecimal(ApplicationConstants.COMM_TASA15);
				}else if(this.getAffiliation_comercialplan().equals(ApplicationConstants.COMERCIALPLAN70)) {
					tasa = new BigDecimal(ApplicationConstants.COMM_TASA15);
				}else if(this.getAffiliation_comercialplan().equals(ApplicationConstants.COMERCIALPLAN150)) {
					tasa = new BigDecimal(ApplicationConstants.COMM_TASA10);
				}else if(this.getAffiliation_comercialplan().equals(PlanType.PLAN500.value().toString())) {
					tasa = new BigDecimal(ApplicationConstants.COMM_TASA10);
				}
				
				valueComsionTC = BigDecimal.valueOf( commision.getCommisionTC() );
				operationTC = valueComsionTC.add(tasa) ;
				valueComsionTD = BigDecimal.valueOf( commision.getCommisionTD() );
				operationTD = valueComsionTD.add(tasa) ;
				valueComsionINT = BigDecimal.valueOf( commision.getCommisionINTNL() );
				operationINT = valueComsionINT.add(tasa);
	    			 
				if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_PESOS)){
    				this.setAffiliation_avcommisiontcmn(operationTC.toString());
    				this.setAffiliation_avcommisiontdmn(operationTD.toString());
    				this.setAffiliation_avcommisionintnlmn(operationINT.toString());
    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR)){
    				this.setAffiliation_avcommisiontcdlls(operationTC.toString());
    				this.setAffiliation_avcommisiontddlls(operationTD.toString());
    				this.setAffiliation_avcommisionintnldlls(operationINT.toString());
    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)){
    				this.setAffiliation_avcommisiontcmn(operationTC.toString());
    				this.setAffiliation_avcommisiontcdlls(operationTC.toString());
    				
    				this.setAffiliation_avcommisiontdmn(operationTD.toString());
    				this.setAffiliation_avcommisiontddlls(operationTD.toString());
    				
    				this.setAffiliation_avcommisionintnlmn(operationINT.toString());
    				this.setAffiliation_avcommisionintnldlls(operationINT.toString());
    			}
	    	    	 
				this.setAffiliation_otherconcept1des(ApplicationConstants.EMPTY_STRING);
    		}else if ( this.getAffiliation_chargeType().equals( ApplicationConstants.NETPAY_CHARGE_TYPE_TRANS)){ //1$ transaccion netpay
    			this.setAffiliation_otherconcept1des("Comision por transaccion en credito y debito");
    			
    			if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_PESOS)){
    				this.setAffiliation_otherconcept1mn(ApplicationConstants.NETPAY_DIVISA_PESOS_TRANS);//1.0
    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR)){
    				this.setAffiliation_otherconcept1dlls(ApplicationConstants.NETPAY_DIVISA_DOLLAR_TRANS);//.10
    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)){
    				this.setAffiliation_otherconcept1mn(ApplicationConstants.NETPAY_DIVISA_PESOS_TRANS);//1.0
    				this.setAffiliation_otherconcept1dlls(ApplicationConstants.NETPAY_DIVISA_DOLLAR_TRANS);//.10
    			}
    		}else if ( this.getAffiliation_chargeType().equals( ApplicationConstants.NETPAY_CHARGE_TYPE_EQ)){
	    		
    			BigDecimal quantPinpads = new BigDecimal(this.getAffiliation_quantpinpad());
    			BigDecimal monthlyratemn = new BigDecimal(this.getAffiliation_monthlyratemn());
    			BigDecimal monthlyratedlls = new BigDecimal(this.getAffiliation_monthlyratedlls());
    			BigDecimal totalMonthlyratemn = new BigDecimal("0");
    			BigDecimal totalMonthlyratedlls = new BigDecimal("0");;
	    			
    			if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_PESOS)){
    				totalMonthlyratemn = monthlyratemn.add(quantPinpads.multiply(new BigDecimal(ApplicationConstants.NETPAY_COMM_EQ_RENT_PESOS)));
    			 }else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR)){
    				totalMonthlyratedlls = monthlyratedlls.add(quantPinpads.multiply(new BigDecimal(ApplicationConstants.NETPAY_COMM_EQ_RENT_DLLS)));
    	    	 }else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)){
    	    		totalMonthlyratemn = monthlyratemn.add(quantPinpads.multiply(new BigDecimal(ApplicationConstants.NETPAY_COMM_EQ_RENT_PESOS)));
    	    	 }
    			this.setAffiliation_monthlyratemn(totalMonthlyratemn.doubleValue());
    			this.setAffiliation_monthlyratedlls(totalMonthlyratedlls.doubleValue());
    		}
    	}
    		/* FIN SI SE SELECCIONO NETPAY*/
    	
    		/* INICIO SI SE SELECCIONO CYBERSOURCE*/
    	else if( this.getAffiliation_alliance().equals(ApplicationConstants.ALLIANCE_CYBERSOURCE )){
    			
    		if( this.getAffiliation_chargeType().equals(ApplicationConstants.CYBER_CHARGE_TYPE_TASA )){ //% en tasa
    			tasa = new BigDecimal(ApplicationConstants.COMM_TASA10);
	    			 
    			valueComsionTC 	= BigDecimal.valueOf( commision.getCommisionTC() );
    			operationTC 		= valueComsionTC.add(tasa) ;
    			valueComsionTD 	= BigDecimal.valueOf( commision.getCommisionTD() );
    			operationTD 		= valueComsionTD.add(tasa) ;
    			valueComsionINT	= BigDecimal.valueOf( commision.getCommisionINTNL() );
    			operationINT		= valueComsionINT.add(tasa);
    			 
    			if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_PESOS)){
    				this.setAffiliation_avcommisiontcmn(operationTC.toString());
    				this.setAffiliation_avcommisiontdmn(operationTD.toString());
    				this.setAffiliation_avcommisionintnlmn(operationINT.toString());
    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR)){
    				this.setAffiliation_avcommisiontcdlls(operationTC.toString());
    				this.setAffiliation_avcommisiontddlls(operationTD.toString());
    				this.setAffiliation_avcommisionintnldlls(operationINT.toString());
    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)){
    				this.setAffiliation_avcommisiontcmn(operationTC.toString());
    				this.setAffiliation_avcommisiontcdlls(operationTC.toString());
    				
    				this.setAffiliation_avcommisiontdmn(operationTD.toString());
    				this.setAffiliation_avcommisiontddlls(operationTD.toString());
    				
    				this.setAffiliation_avcommisionintnlmn(operationINT.toString());
    				this.setAffiliation_avcommisionintnldlls(operationINT.toString());
    			}
	    	    	 
    			this.setAffiliation_otherconcept1des(ApplicationConstants.EMPTY_STRING);
    		}
    		else if ( this.getAffiliation_chargeType().equals( ApplicationConstants.CYBER_CHARGE_TYPE_TRANS )){ // $ por transaccion
    			if(this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_PESOS)){
    				transactionFeeCyberMn=ApplicationConstants.CYBER_DIVISA_PESOS_TRANS;
    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_DOLLAR)){
    				transactionFeeCyberDlls=ApplicationConstants.CYBER_DIVISA_DOLLAR_TRANS;
    			}else if (this.getAffiliation_currency().equals(ApplicationConstants.CURRENCY_AMBOS)){
    				transactionFeeCyberDlls=ApplicationConstants.CYBER_DIVISA_DOLLAR_TRANS;
    				transactionFeeCyberMn=ApplicationConstants.CYBER_DIVISA_PESOS_TRANS;
    			}
    		}
    		assignRentaDolar();
    	}
    	
    }

    private void assignRentaDolar(){
		if (affiliation_rentaDolar!=null && affiliation_rentaDolar.equalsIgnoreCase(ApplicationConstants.RENTA_DOLAR_2100)) {
			limitedCoverageCyberDlls = 2100d;
			/*if (affiliation.getCurrency().equalsIgnoreCase(ApplicationConstants.CURRENCY_PESOS)) {
				limitedCoverageCyberMn = (2100d * 13.50d);
			} else if (affiliation.getCurrency().equalsIgnoreCase(ApplicationConstants.CURRENCY_DOLLAR)) {
				limitedCoverageCyberDlls = 2100d;
			} else if (affiliation.getCurrency().equalsIgnoreCase(ApplicationConstants.CURRENCY_AMBOS)) {
				limitedCoverageCyberDlls = 2100d;
				limitedCoverageCyberMn = (2100d * 13.50d);
			}*/
		} else if (affiliation_rentaDolar!=null && affiliation_rentaDolar.equalsIgnoreCase(ApplicationConstants.RENTA_DOLAR_4000)) {
			wideCoverageCyberDlls = 4000d;
			/*if (affiliation.getCurrency().equalsIgnoreCase(ApplicationConstants.CURRENCY_PESOS)) {
				wideCoverageCyberMn = (4000d * 13.50d);
			} else if (affiliation.getCurrency().equalsIgnoreCase(ApplicationConstants.CURRENCY_DOLLAR)) {
				wideCoverageCyberDlls = 4000d;
			} else if (affiliation.getCurrency().equalsIgnoreCase(ApplicationConstants.CURRENCY_AMBOS)) {
				wideCoverageCyberDlls = 4000d;
				wideCoverageCyberMn = (4000d * 13.50d);
			*/
		}else{
			affiliation_rentaDolar="0";
		}
    }
    
	private void setCommisionInPesos(Plan plan, CommisionPlan commision, double transcriptorRate, double monthlyRate, double monthlyRate3D){
		List<String> planes		= ApplicationConstants.PLAN15_200;
		boolean isPlan15_200	= planes.contains(plan.getPlanId().toString());
		
		if(commision!=null){
    		//Campa�a Restaurantes RCRC	

    		if(getAffiliation_chargeType().equals("RCRC")){
    			this.setAffiliation_avcommisiontcmn("1.99");
	    		this.setAffiliation_avcommisiontdmn("1.99");
	    		this.setAffiliation_avcommisionintnlmn("2.50");
    		}else if("true".equalsIgnoreCase(aff_impulsoCaptacion)||"X".equalsIgnoreCase(aff_impulsoCaptacion) && isPlan15_200){ //"true"
	    		BigDecimal ComisionTC 	= new BigDecimal(0); 
    	    	BigDecimal ComisionTD 	= new BigDecimal(0);
    	    	BigDecimal ComisionTI 	= new BigDecimal(0);  
    	    	BigDecimal operationTC 	= new BigDecimal(0); 
    	    	BigDecimal operationTD 	= new BigDecimal(0); 
    	    	BigDecimal operationTI 	= new BigDecimal(0);
    	    
    	    	ComisionTC = BigDecimal.valueOf(commision.getCommisionTC() - 0.15D);
    	    	operationTC  = ComisionTC.setScale(2, RoundingMode.HALF_UP);
    	    	ComisionTD = BigDecimal.valueOf(commision.getCommisionTD() - 0.15D);
    			operationTD = ComisionTD.setScale(2, RoundingMode.HALF_UP);
    			ComisionTI = BigDecimal.valueOf(commision.getCommisionINTNL() - 0.15D);
    			operationTI = ComisionTI.setScale(2, RoundingMode.HALF_UP);
    			
    			this.setAffiliation_avcommisiontcmn(operationTC.toString());
    			this.setAffiliation_avcommisiontdmn(operationTD.toString());
    			this.setAffiliation_avcommisionintnlmn(operationTI.toString());

	    		this.getAffiliation_avcommisiontcmn();
	    		this.getAffiliation_avcommisiontdmn();
    			this.getAffiliation_avcommisionintnlmn();
    		}else{
		    	this.setAffiliation_avcommisiontcmn(commision.getCommisionTC().toString());
		    	this.setAffiliation_avcommisiontdmn(commision.getCommisionTD().toString());
		    	this.setAffiliation_avcommisionintnlmn(commision.getCommisionINTNL().toString());
    		}
	   	}

    	//transcriptor
    	this.setAffiliation_transcriptorratemn(transcriptorRate);
    	
    	//Terminal personal banorte Mpos
    	if(getAffiliation_productdesc().equals("Terminal Personal Banorte (Mpos)")){
    		
    		//se descomenta esta l�nea porque se quito la campaña de giros especiales
    		//setAffiliation_rateMposmn(this.getAffiliation().getMposRateByEquipment() * ApplicationConstants.VALOR_649);
    		setAffiliation_rateMposmn(this.getAffiliation().getMposRateByEquipment() * ApplicationConstants.VALOR_MPOS_VENTA);//F-92512 Esquemas 2019 RF_002_MPOS ($799)
    		
    		if(this.getAffiliation_chargeType().equals("Tasa + $0.40 por tx")){
    			this.setAffiliation_commisiontcmn(ApplicationConstants.CUARENTA_PORCIENTO);
    			this.setAffiliation_commisiontdmn(ApplicationConstants.CUARENTA_PORCIENTO);
    			this.setAffiliation_commisionintnlmn(ApplicationConstants.CUARENTA_PORCIENTO);
    		}
    		
    		if(this.getAffiliation_chargeType().equals("Tasa + 25 ppb")){
    			this.setAffiliation_avcommisiontcmn(String.valueOf((Double.valueOf(this.getAffiliation_avcommisiontcmn()) + 0.25D)));
	    		this.setAffiliation_avcommisiontdmn(String.valueOf((Double.valueOf(this.getAffiliation_avcommisiontdmn()) + 0.25D)));
	    		this.setAffiliation_avcommisionintnlmn(String.valueOf((Double.valueOf(this.getAffiliation_avcommisionintnlmn()) + 0.25D)));
    		}
       	}    	
    	//renta mensual
    	this.setAffiliation_monthlyratemn(monthlyRate);
   
    	if(plan!=null){
            
    		//Cuota de afiliacion
    		if(getAffiliation_productdesc().equals("Comercio Electronico")){//Comercio Electronico
    			this.setAffiliation_affiliationratemn(ApplicationConstants.VALOR_1500);
    		}else if((getAffiliation_productdesc().equals("Terminal punto de venta") && getAffiliation_chargeType().equals("RCRC")) 
    				|| (getAffiliation_comercialplan().equals("5"))){
    			this.setAffiliation_affiliationratemn(ApplicationConstants.ZERO_DOUBLE_STR);
    		}else{
    			this.setAffiliation_affiliationratemn(ApplicationConstants.ZERO_DOUBLE_STR);
    		}

	    	//facturacion mensual requerida
	    	this.setAffiliation_monthlyinvoicingminmn(plan.getMonthlyInvoicingMin().doubleValue());
	    	//Cobro por no cumplir con la facturaci�n m�nima
	    	this.setAffiliation_failmonthlyinvoicingmn(plan.getFailMonthlyInvoicing().doubleValue());
	    	if("true".equalsIgnoreCase(aff_impulsoCaptacion)||"X".equalsIgnoreCase(aff_impulsoCaptacion) && isPlan15_200){
		    	//Saldo Promedio Mensual Cuenta(s) Cheques M�nimo Exigido
		    	this.setAffiliation_minimiunbalancemn(this.getAffiliation_monthlyinvoicingminmn());
		    	//Comision por incumplimiento Saldo Promedio Mensual
		    	this.setAffiliation_failminimiunbalancemn(this.getAffiliation_failmonthlyinvoicingmn());
		    }else {
		    	//Saldo Promedio Mensual Cuenta(s) Cheques M�nimo Exigido
			    this.setAffiliation_minimiunbalancemn(plan.getFailMinimunBalance().doubleValue());
			    //Comision por incumplimiento Saldo Promedio Mensual
			    this.setAffiliation_failminimiunbalancemn(plan.getFailMinimunBalance().doubleValue());
		     }
	    	//Pagare Minimo
	    	this.setAffiliation_promnotemn(plan.getPromNote().doubleValue());
	    	//Comision por incumplimiento Pagare minimo
	    	this.setAffiliation_failpromnotemn(plan.getFailPromNote().doubleValue());
	    	//Servicio de verificación de domicilio por cada consulta
	    	this.setAffiliation_avsmn(plan.getAvs().doubleValue());
	    	//Cuota Activación 3D Secure
	    	this.setAffiliation_activation3dsmn(plan.getActivation3D().doubleValue());
	    	//Renta Mensual 3D Secure
	    	this.setAffiliation_monthlyrate3dsmn(monthlyRate3D);
    	}
    	
    	//otro concepto
    	this.setAffiliation_otherconcept1mn(new Double(0));
    	
    }

    private void setCommisionInDollar(Plan plan, CommisionPlan commision, double transcriptorRate, double monthlyRate, double monthlyRate3D, boolean calculateDollars){
    	List<String> planes		= ApplicationConstants.PLAN15_200;
    	boolean isPlan15_200	= planes.contains(plan.getPlanId().toString());
    	
    	if(commision != null){
    		if(getAffiliation_chargeType().equals("RCRC")){
    			this.setAffiliation_avcommisiontcdlls("1.99");
	    		this.setAffiliation_avcommisiontddlls("1.99");
	    		this.setAffiliation_avcommisionintnldlls("2.50");
	    		
    		}else if("true".equalsIgnoreCase(aff_impulsoCaptacion)||"X".equalsIgnoreCase(aff_impulsoCaptacion) && isPlan15_200){
    			BigDecimal ComisionTC 	= new BigDecimal(0); 
    	    	BigDecimal ComisionTD 	= new BigDecimal(0);
    	    	BigDecimal ComisionTI 	= new BigDecimal(0);  
    	    	BigDecimal operationTC 	= new BigDecimal(0); 
    	    	BigDecimal operationTD 	= new BigDecimal(0); 
    	    	BigDecimal operationTI 	= new BigDecimal(0);
    	    	
    	    	ComisionTC = BigDecimal.valueOf(commision.getCommisionTC() - 0.15D);
    	    	operationTC  = ComisionTC.setScale(2, RoundingMode.HALF_UP);
    	    	ComisionTD = BigDecimal.valueOf(commision.getCommisionTD() - 0.15D);
    			operationTD = ComisionTD.setScale(2, RoundingMode.HALF_UP);
    			ComisionTI = BigDecimal.valueOf(commision.getCommisionINTNL() - 0.15D);
    			operationTI = ComisionTI.setScale(2, RoundingMode.HALF_UP);
    			
    			this.setAffiliation_avcommisiontcdlls(operationTC.toString());
    			this.setAffiliation_avcommisiontddlls(operationTD.toString());
    			this.setAffiliation_avcommisionintnldlls(operationTI.toString());

	    		this.getAffiliation_avcommisiontcdlls();
	    		this.getAffiliation_avcommisiontddlls();
    			this.getAffiliation_avcommisionintnldlls();
    		}else{
    			this.setAffiliation_avcommisiontcdlls(commision.getCommisionTC().toString());
        		this.setAffiliation_avcommisiontddlls(commision.getCommisionTD().toString());
        		this.setAffiliation_avcommisionintnldlls(commision.getCommisionINTNL().toString());
    		}
    	}
    	
    	//Terminal personal banorte Mpos
    	if(getAffiliation_productdesc().equals("Terminal Personal Banorte (Mpos)")){
    		if(this.getAffiliation_chargeType().equals("Tasa + $0.40 por tx")){
    			this.setAffiliation_commisiontcdlls(ApplicationConstants.CUARENTA_PORCIENTO);
    			this.setAffiliation_commisiontddlls(ApplicationConstants.CUARENTA_PORCIENTO);
    			this.setAffiliation_commisionintnldlls(ApplicationConstants.CUARENTA_PORCIENTO);
    		}
    		
    		if(this.getAffiliation_chargeType().equals("Tasa + 25 ppb")){
    			this.setAffiliation_avcommisiontcdlls(String.valueOf((Double.valueOf(this.getAffiliation_avcommisiontcdlls()) + 0.25D)));
	    		this.setAffiliation_avcommisiontddlls(String.valueOf((Double.valueOf(this.getAffiliation_avcommisiontddlls()) + 0.25D)));
	    		this.setAffiliation_avcommisionintnldlls(String.valueOf((Double.valueOf(this.getAffiliation_avcommisionintnldlls()) + 0.25D)));
    		}
    	}

    	if(calculateDollars){
    		//this.setAffiliation_rateMposdlls(CurrencyConverter.toDollar(this.getAffiliation().getMposRateByEquipment() * ApplicationConstants.VALOR_649)); //VALOR_750
    		this.setAffiliation_rateMposdlls(CurrencyConverter.toDollar(this.getAffiliation().getMposRateByEquipment() * ApplicationConstants.VALOR_MPOS_VENTA)); //VALOR_750 //F-92512 Esquemas 2019 RF_002_MPOS
    		
	    	//transcriptor
	    	this.setAffiliation_transcriptorratedlls(CurrencyConverter.toDollar(transcriptorRate));
	    	
	    	//renta mensual
	    	this.setAffiliation_monthlyratedlls(CurrencyConverter.toDollar(monthlyRate));
	   
	    	if(plan!=null){
	    		//Cuota de Afiliacion
	    		
	    		if(getAffiliation_productdesc().equals("Comercio Electronico")){//Comercio Electronico
	    			this.setAffiliation_affiliationratedlls(CurrencyConverter.toDollar(ApplicationConstants.VALOR_1500));
	    		}else if((getAffiliation_productdesc().equals("Terminal punto de venta") && getAffiliation_chargeType().equals("RCRC")) 
	    				|| (getAffiliation_comercialplan().equals("5"))){
	    			this.setAffiliation_affiliationratemn(ApplicationConstants.ZERO_DOUBLE_STR);
	    		}else{
	    			this.setAffiliation_affiliationratemn(ApplicationConstants.ZERO_DOUBLE_STR);
	    		}

	    		
	        	//facturacion mensual requerida
		    	this.setAffiliation_monthlyinvoicingmindlls(CurrencyConverter.toDollar(plan.getMonthlyInvoicingMin()));
		    	//Cobro por no cumplir con la facturaci�n m�nima
		    	this.setAffiliation_failmonthlyinvoicingdlls(CurrencyConverter.toDollar(plan.getFailMonthlyInvoicing()));
		    	if("true".equalsIgnoreCase(aff_impulsoCaptacion)||"X".equalsIgnoreCase(aff_impulsoCaptacion) && isPlan15_200){
			    	//Saldo Promedio Mensual Cuenta(s) Cheques M�nimo Exigido
		    		this.setAffiliation_minimiunbalancedlls(this.getAffiliation_monthlyinvoicingmindlls());
			    	//Comision por incumplimiento Saldo Promedio Mensual
		    		this.setAffiliation_failminimiunbalancedlls(this.getAffiliation_failmonthlyinvoicingdlls());
			    }else {
			    	//Saldo promedio Mensual
					this.setAffiliation_minimiunbalancedlls(CurrencyConverter.toDollar(plan.getMinimunBalance()));
					//Comision por incumplimiento Saldo Promedio Mensual
					this.setAffiliation_failminimiunbalancedlls(CurrencyConverter.toDollar(plan.getFailMinimunBalance()));
			     }
		    	//Pagare Minimo
		    	this.setAffiliation_promnotedlls(CurrencyConverter.toDollar(plan.getPromNote()));
		    	//Comision por incumplimiento Pagare minimo
		    	this.setAffiliation_failpormnotedlls(CurrencyConverter.toDollar(plan.getFailPromNote()));
		    	//Servicio de verificación de domicilio por cada consulta
		    	this.setAffiliation_avsdlls(CurrencyConverter.toDollar(plan.getAvs()));
		    	//Cuota Activación 3D Secure
		    	this.setAffiliation_activation3dsdlls(CurrencyConverter.toDollar(plan.getActivation3D()));
		    	//Renta Mensual 3D Secure
		    	this.setAffiliation_monthlyrate3dsdlls(CurrencyConverter.toDollar(monthlyRate3D)); //ya viene en dolares
	    	}
	
	    	//otro concepto
	    	this.setAffiliation_otherconcept1dlls(new Double(0));
    	}
    }
    
	private double calculateMonthlyRate(Plan plan){
    	double monthlyRate = new Double(0.00);
    	String producto=affiliation.getProductdesc();
    	if(producto.equals(ApplicationConstants.CARGO_AUTOMATICO) || producto.equals(ApplicationConstants.COMERCIO_ELECTRONICO)){
    		if(PlanType.BANORTESINEXIGENCIAS.value().equals(plan.getPlanId())
    				|| PlanType.BANORTEPLAN15.value().equals(plan.getPlanId())
    				|| PlanType.IXESINEXIGENCIAS.value().equals(plan.getPlanId())
    				|| PlanType.IXEPLAN15.value().equals(plan.getPlanId())){
    			monthlyRate = ApplicationConstants.VALOR_250;
    		}else if(PlanType.BANORTEPLAN35.value().equals(plan.getPlanId())
    				|| PlanType.BANORTEPLAN75.value().equals(plan.getPlanId())
    				|| PlanType.IXEPLAN35.value().equals(plan.getPlanId())
    				|| PlanType.IXEPLAN75.value().equals(plan.getPlanId())){
    			monthlyRate = ApplicationConstants.VALOR_200;
    		}else
    			monthlyRate = new Double(0.00);
    	}else if(producto.equals(ApplicationConstants.PRODUCTO_MPOS)){
    		monthlyRate = this.getAffiliation().getMposMonthlyRate() * ApplicationConstants.VALOR_65; //Renta VALOR_75
    	}else{//INTERREDES Y TPV:
    		int fixedEqpmt=0;
	        int mobileEquipments =0;
	        double discountMonthlyRate = 0;
	        IncomePlan income = new IncomePlan();
	        List<IncomePlan> incomes =null;
	    	//Obtener el # de equipos fijos y moviles
	        //fijos
	        int tel = affiliation.getTpvTel()!=null?affiliation.getTpvTel():0;//tpv conexion telefonica
	        int cel = affiliation.getTpvMovil()!=null?affiliation.getTpvMovil():0;//tpv conexion celular
	        int net = affiliation.getTpvInternet()!=null?affiliation.getTpvInternet():0;//tpv conexion internet
	        int netel = affiliation.getTpvInternetTel()!=null?affiliation.getTpvInternetTel():0;//tpv conexion internet respaldo telefonico
	        int pin = affiliation.getQuantpinpad()!=null?affiliation.getQuantpinpad():0;//pinpad
	        //mobiles
	        int gprs = affiliation.getQuantgprs()!=null?affiliation.getQuantgprs():0;//tpv con conexion celular GPRS
	        int wifi = affiliation.getQuantwifi()!=null?affiliation.getQuantwifi():0;//wifi
	        int blue = affiliation.getTpvBlueTel()!=null?affiliation.getTpvBlueTel():0;//tpv inalambrica bluetooth con conexion telefonica
	        int bluenet = affiliation.getTpvBlueInternet()!=null?affiliation.getTpvBlueInternet():0;//tpv inalambrica bluetooth con conexion a internet
	        int wifitel = affiliation.getWifiTel()!=null?affiliation.getWifiTel():0;//wifi con respaldo telefonico
	       
	        fixedEqpmt = tel + cel + net + netel + pin;
	        mobileEquipments = gprs + wifi + blue + bluenet + wifitel;
	    	
	        //calcular el descuento de la renta mensual
	        
	        if((producto.equalsIgnoreCase("Terminal punto de venta")) && 
	        		(affiliation_alliance_hidd.equalsIgnoreCase(ApplicationConstants.ALLIANCE_NETPAY))){//TPV+NETPAY
	        	monthlyRate = pin * 100; //monthlyRate = gprs * 300;
	        	monthlyRate = monthlyRate + (gprs * 300) + (net * 300);  
	        	
	        	IncomePlan incomeFixed = incomePlanBean.findByPK(plan.getPlanId(), EquipmentType.FIXED.value());
	        	IncomePlan incomeMobile = incomePlanBean.findByPK(plan.getPlanId(), EquipmentType.MOBILE.value());
	        	
	        	Double extraFijos = (fixedEqpmt - pin - net) * incomeFixed.getMonthlyRate().doubleValue(); // fixedEqpmt 
	        	Double extraMoviles = (mobileEquipments - gprs) * incomeMobile.getMonthlyRate().doubleValue();
	        	
	        	monthlyRate = monthlyRate + extraFijos + extraMoviles;
	        	return monthlyRate;
	        	
	        }else if((producto.equalsIgnoreCase("Interredes")) &&
	        		(affiliation_alliance_hidd.equalsIgnoreCase(ApplicationConstants.ALLIANCE_NETPAY))){//INTERREDES + NETPAY
	        	monthlyRate = pin * 100;
	        	monthlyRate = monthlyRate + (gprs * 300) + (net * 300);
	        	
	        	IncomePlan incomeFixed = incomePlanBean.findByPK(plan.getPlanId(), EquipmentType.FIXED.value());
	        	IncomePlan incomeMobile = incomePlanBean.findByPK(plan.getPlanId(), EquipmentType.MOBILE.value());
	        	
	        	Double extraFijos = (fixedEqpmt - pin - net) * incomeFixed.getMonthlyRate().doubleValue(); 
	        	Double extraMoviles = (mobileEquipments - gprs) * incomeMobile.getMonthlyRate().doubleValue();
	        	
	        	monthlyRate = monthlyRate + extraFijos + extraMoviles;
	        	return monthlyRate;
	        }else if(affiliation_alliance.equalsIgnoreCase(ApplicationConstants.ALLIANCE_MICROS)){
	        	
	        	if(affiliation_chargeType.equals(ApplicationConstants.MICROS_CHARGE_TYPE_RENTA)){
	        		monthlyRate = fixedEqpmt * 200;
	        		monthlyRate = monthlyRate + (mobileEquipments * 200);
	        	}
	        	
	        	IncomePlan incomeFixed = incomePlanBean.findByPK(plan.getPlanId(), EquipmentType.FIXED.value());
	        	IncomePlan incomeMobile = incomePlanBean.findByPK(plan.getPlanId(), EquipmentType.MOBILE.value());
	        	
	        	if(ApplicationConstants.PLAN_BANORTE0_15.equalsIgnoreCase(plan.getName())
	        			|| ApplicationConstants.PLAN_15_15.equalsIgnoreCase(plan.getName())
	        			|| ApplicationConstants.PLAN_IXE0_15.equalsIgnoreCase(plan.getName())
	        			|| ApplicationConstants.PLAN_IXE015_15.equalsIgnoreCase(plan.getName())){
	        		if(fixedEqpmt > incomeFixed.getFreeEquipment())
		        		fixedEqpmt = fixedEqpmt - incomeFixed.getFreeEquipment();
		        	else
		        		fixedEqpmt = 0;
		        	
		        	if(mobileEquipments > incomeMobile.getFreeMobileEquipment())
		        		mobileEquipments = mobileEquipments - incomeMobile.getFreeMobileEquipment();
		        	else
		        		mobileEquipments = 0;
		        	
		        	Double extraFijos = fixedEqpmt * incomeFixed.getMonthlyRate().doubleValue();
		        	Double extraMoviles = mobileEquipments * incomeMobile.getMonthlyRate().doubleValue();
		        	
		        	monthlyRate = monthlyRate + extraFijos + extraMoviles;
	        	}else{
	        		Integer totalFreeEquipment = incomeMobile.getFreeMobileEquipment();
	        		
	        		if(mobileEquipments > 0){
	        			if(totalFreeEquipment >= mobileEquipments){
	        				totalFreeEquipment = totalFreeEquipment - mobileEquipments;
	        				mobileEquipments = 0;
	        			}else{
	        				mobileEquipments = mobileEquipments - totalFreeEquipment;
	        				totalFreeEquipment = 0;
	        			}
	        		}
	        		
	        		if(fixedEqpmt > 0){
	        			if(totalFreeEquipment >= fixedEqpmt){
	        				totalFreeEquipment = totalFreeEquipment - fixedEqpmt;
	        				fixedEqpmt = 0;
	        			}else{
	        				fixedEqpmt = fixedEqpmt - totalFreeEquipment;
	        				totalFreeEquipment = 0;
	        			}
	        		}
	        		
	        		Double extraFijos = fixedEqpmt * incomeFixed.getMonthlyRate().doubleValue();
		        	Double extraMoviles = mobileEquipments * incomeMobile.getMonthlyRate().doubleValue();
	        		
		        	monthlyRate = monthlyRate + extraFijos + extraMoviles;
	        	}
	        	
	        	return monthlyRate;
	        }else if(PlanType.BANORTESINEXIGENCIAS.value().equals(plan.getPlanId()) || PlanType.BANORTEPLAN15.value().equals(plan.getPlanId())
	        		|| PlanType.IXESINEXIGENCIAS.value().equals(plan.getPlanId()) || PlanType.IXEPLAN15.value().equals(plan.getPlanId())){// Sin Exigencias Banorte o Plan 15 Banorte e Ixe
	        	if(fixedEqpmt > 0){
	        		income = incomePlanBean.findByPK(plan.getPlanId(), EquipmentType.FIXED.value());
	        		
	        		if(getAffiliation_chargeType().equals("RCRC")){
	        			discountMonthlyRate = income.getFreeEquipment().intValue() * 175D;
	        		}else{
	        			discountMonthlyRate = income.getFreeEquipment().intValue() * income.getMonthlyRate().doubleValue();
	        		}
	        	}
	        }else{ //Plan 75 o 200 Banorte e Ixe
	        	IncomePlan incomeNotMobile = incomePlanBean.findByPK(plan.getPlanId(), EquipmentType.FIXED.value());
	        	income = incomePlanBean.findByPK(plan.getPlanId(), EquipmentType.MOBILE.value());
	        	
	        	if(PlanType.BANORTEPLAN75.value().equals(plan.getPlanId()) || PlanType.IXEPLAN75.value().equals(plan.getPlanId())) {
					if(fixedEqpmt >= 1 && mobileEquipments<1) { //se agrego
	        			fixedEqpmt = fixedEqpmt - incomeNotMobile.getFreeEquipment();
	        			//System.out.println("Solo equipo fijo: "+ fixedEqpmt + " - equipo gratis: "+incomeNotMobile.getFreeEquipment());
	        		}else if(fixedEqpmt < 1 && mobileEquipments>=1) {
		        		mobileEquipments = mobileEquipments - income.getFreeMobileEquipment();
	        			//System.out.println("Solo equipo Movil: "+ mobileEquipments + " - equipo gratis: "+income.getFreeMobileEquipment());
	        		}else if(fixedEqpmt >= 1 && mobileEquipments>=1) {
		        		mobileEquipments = mobileEquipments - income.getFreeMobileEquipment();
	        			//System.out.println("Equipos fijos y moviles, regala movil: "+ mobileEquipments + " - equipo gratis: "+income.getFreeMobileEquipment());
	        			
	        		}//termina
	        		/*if(fixedEqpmt > incomeNotMobile.getFreeEquipment()) {
	        			fixedEqpmt = fixedEqpmt - incomeNotMobile.getFreeEquipment();
	        		}
			        else {
			        	fixedEqpmt = 0;
	        		}
	        		if(mobileEquipments > income.getFreeMobileEquipment()) {
		        		mobileEquipments = mobileEquipments - income.getFreeMobileEquipment();
	        		}
		        	else {
		        		mobileEquipments = 0;
					}*/
	        		Double extraFijos = fixedEqpmt * incomeNotMobile.getMonthlyRate().doubleValue();
		        	Double extraMoviles = mobileEquipments * income.getMonthlyRate().doubleValue();
		        	
		        	monthlyRate = monthlyRate + extraFijos + extraMoviles;
				//Se agrego
				}else if(PlanType.BANORTEPLAN200.value().equals(plan.getPlanId()) || PlanType.IXEPLAN200.value().equals(plan.getPlanId())) {
					int auxMobileEquipments=mobileEquipments;
					//Saca total de dispositivos y valida si son mas de los dispositivos gratis que vienen de la BD INCOMEPLN  
					if((fixedEqpmt+mobileEquipments)>income.getFreeMobileEquipment()){
						if(mobileEquipments>=income.getFreeMobileEquipment()){//verifica si hay mas de los equipos moviles gratis
						mobileEquipments=mobileEquipments-income.getFreeMobileEquipment();//descuenta los equipos moviles gratis porque son los mas caros
						}else{
							mobileEquipments=mobileEquipments-auxMobileEquipments; //resta los equipos moviles gratis
							if(auxMobileEquipments==1){
								fixedEqpmt=fixedEqpmt-(income.getFreeMobileEquipment()-1);
							}else{
								fixedEqpmt=fixedEqpmt-incomeNotMobile.getFreeEquipment(); //resta los equipos fijos gratis
							}
						}
					}else{
						mobileEquipments = 0;
						fixedEqpmt = 0;
					}
					
					
	    		Double extraFijos = fixedEqpmt * incomeNotMobile.getMonthlyRate().doubleValue();
	        	Double extraMoviles = mobileEquipments * income.getMonthlyRate().doubleValue();
	        	
	        	monthlyRate = monthlyRate + extraFijos + extraMoviles;
				// Se termina
	        }else{
        		Integer totalFreeEquipment = income.getFreeMobileEquipment();
        		
        		if(mobileEquipments > 0){
        			if(totalFreeEquipment >= mobileEquipments){
        				totalFreeEquipment = totalFreeEquipment - mobileEquipments;
        				mobileEquipments = 0;
        			}else{
        				mobileEquipments = mobileEquipments - totalFreeEquipment;
        				totalFreeEquipment = 0;
        			}
        		}
        		
        		if(fixedEqpmt > 0){
        			if(totalFreeEquipment >= fixedEqpmt){
        				totalFreeEquipment = totalFreeEquipment - fixedEqpmt;
        				fixedEqpmt = 0;
        			}else{
        				fixedEqpmt = fixedEqpmt - totalFreeEquipment;
        				totalFreeEquipment = 0;
        			}
        		}
        		
        		Double extraFijos = fixedEqpmt * incomeNotMobile.getMonthlyRate().doubleValue();
	        	Double extraMoviles = mobileEquipments * income.getMonthlyRate().doubleValue();
        		
	        	monthlyRate = monthlyRate + extraFijos + extraMoviles;
        	}
        	
        	return monthlyRate;
	      }
	        //Obtener la lista de los Equipos de Renta segun el plan
	        incomes = incomePlanBean.findByPlanId(plan.getPlanId());
	
	    	//Calcular MonthlyRate
	    	if(incomes != null && (fixedEqpmt > 0 || mobileEquipments > 0 ) ){
	    		
	    		for(IncomePlan entity: incomes){
	    			
	    			if(getAffiliation_chargeType().equals("RCRC")){
	    				
	    				//Equipo Fijos
		    			if(fixedEqpmt > 0  && entity.getIncomePlanPK().getEquipmentId().equals(EquipmentType.FIXED.value())){
		    				monthlyRate += fixedEqpmt * 175D;
		    			}
	    				
	    				//Equipo Moviles
		    			if(mobileEquipments > 0 && entity.getIncomePlanPK().getEquipmentId().equals(EquipmentType.MOBILE.value())){
		    				monthlyRate += mobileEquipments * 250D;
		    			}
	    				
	    			}else{
	    				//Equipo Fijos
		    			if(fixedEqpmt > 0  && entity.getIncomePlanPK().getEquipmentId().equals(EquipmentType.FIXED.value())){
		    				monthlyRate += fixedEqpmt * entity.getMonthlyRate().doubleValue();
		    			}
		    			//Equipo Moviles
		    			if(mobileEquipments > 0 && entity.getIncomePlanPK().getEquipmentId().equals(EquipmentType.MOBILE.value())){
		    				if(16 == plan.getPlanId()) {
		    					if(mobileEquipments>1) {
		    						monthlyRate += 325+ ((mobileEquipments-1) * 250D);
		    					}else {
				    				monthlyRate += mobileEquipments * entity.getMonthlyRate().doubleValue();
			    				}
		    				}else {
		    				monthlyRate += mobileEquipments * entity.getMonthlyRate().doubleValue();
		    				}
		    			}
	    			}
	    		 }
		    	 monthlyRate = monthlyRate - discountMonthlyRate;
		    	 if(monthlyRate<0){
		    		monthlyRate=0;
		    	 }
		    }	 	
	    }
	    return monthlyRate;
	}
	    
    public String goToCommerce(){
    	if("true".equalsIgnoreCase(transConciliation)||"true".equalsIgnoreCase(promNoteRqst)){
    		return"HERRAMIENTA_COMERCIOS";
    	}
		return "SUCCESS";
    }
    
    
public void discountTasaTPVNomina(String categoryCode,String tCredito,String tDebito,int planId, String tpvSelected){
		
		if(tpvSelected.equals(ApplicationConstants.VALUE_TRUE)){
		
			double tasaC = Double.parseDouble(tCredito);
			double tasaD = Double.parseDouble(tDebito);
			double tasaCredito = 0.0;
			double tasaDebito = 0.0;
		
			if(ApplicationConstants.GIROS_NO_ESTRATEGICOS.contains(categoryCode)){
				if(PlanType.PLAN10.value().intValue() == planId){
					tasaCredito =  tasaC-ApplicationConstants.DIEZ_PORCIENTO ;
					tasaDebito = tasaD-ApplicationConstants.DIEZ_PORCIENTO ;
				}else if(PlanType.PLAN30.value().intValue() == planId){
					tasaCredito =  tasaC-ApplicationConstants.QUINCE_PORCIENTO ;
					tasaDebito =  tasaD-ApplicationConstants.QUINCE_PORCIENTO ;
				}else if(PlanType.PLAN150.value().intValue() == planId){
					tasaCredito = tasaC-ApplicationConstants.DIEZ_PORCIENTO ;
					tasaDebito = tasaD-ApplicationConstants.DIEZ_PORCIENTO;
				}else if(PlanType.PLAN500.value().intValue() == planId){
					tasaCredito =  tasaC-ApplicationConstants.CINCO_PORCIENTO ;
					tasaDebito =  tasaD-ApplicationConstants.CINCO_PORCIENTO ;
				}
				
			}else if(ApplicationConstants.GIROS_GASOLINERAS.contains(categoryCode)){
				if(PlanType.PLAN10.value().intValue() == planId){
					tasaCredito = tasaC-ApplicationConstants.DIEZ_PORCIENTO ;
					tasaDebito =  tasaD-ApplicationConstants.DIEZ_PORCIENTO ;
				}else if(PlanType.PLAN30.value().intValue() == planId){
					tasaDebito =  tasaD-ApplicationConstants.DIEZ_PORCIENTO ;
				}else if(PlanType.PLAN150.value().intValue() == planId){
					tasaDebito =  tasaD-ApplicationConstants.DIEZ_PORCIENTO ;
				}else if(PlanType.PLAN500.value().intValue() == planId){
					tasaDebito =  tasaD-ApplicationConstants.DIEZ_PORCIENTO ;
				}
			}else{
				if(PlanType.PLAN10.value().intValue() == planId){
					tasaCredito = tasaC-ApplicationConstants.DIEZ_PORCIENTO;
					tasaDebito =  tasaD-ApplicationConstants.DIEZ_PORCIENTO ;
				}else if(PlanType.PLAN30.value().intValue() == planId){
					tasaCredito = tasaC-ApplicationConstants.DOCE_PORCIENTO ;
					tasaDebito =  tasaD-ApplicationConstants.DOCE_PORCIENTO ;
				}else if(PlanType.PLAN150.value().intValue() == planId){
					tasaCredito =  tasaC-ApplicationConstants.SIETE_PORCIENTO ;
					tasaDebito =  tasaD-ApplicationConstants.SIETE_PORCIENTO ;
				}else if(PlanType.PLAN500.value().intValue() == planId){
					tasaCredito =  tasaC-ApplicationConstants.DOS_PORCIENTO ;
					tasaDebito = tasaD-ApplicationConstants.DOS_PORCIENTO ;
				}
				
			}
			
			tCredito 	= ApplicationConstants.DECIMAL_FORMAT_2D.format(tasaCredito);
			tDebito		= ApplicationConstants.DECIMAL_FORMAT_2D.format(tasaDebito);
			
			this.setAffiliation_avcommisiontcmn(tCredito);
			this.setAffiliation_avcommisiontdmn(tDebito);
			this.setAffiliation_avcommisiontcdlls(tCredito);
			this.setAffiliation_avcommisiontddlls(tDebito);
			

		}
	}

	private void selectOptionIntegration(){
		
		if(this.affiliation_integration.equals(ApplicationConstants.INTEGRATION_HOSTED_BANORTE)){
			this.setSelected_hostBanorte(ApplicationConstants.OPCION_SELECTED);
			this.setSelected_revision(ApplicationConstants.EMPTY_STRING);
			this.setSelected_hostComercio(ApplicationConstants.EMPTY_STRING);
			this.setSelected_direct3D(ApplicationConstants.EMPTY_STRING);
		}else if (this.affiliation_integration.equals(ApplicationConstants.INTEGRATION_REVISION)){
			this.setSelected_hostBanorte(ApplicationConstants.EMPTY_STRING);
			this.setSelected_revision(ApplicationConstants.OPCION_SELECTED);
			this.setSelected_hostComercio(ApplicationConstants.EMPTY_STRING);
			this.setSelected_direct3D(ApplicationConstants.EMPTY_STRING);
		}else if (this.affiliation_integration.equals(ApplicationConstants.INTEGRATION_HOSTED_COMERCIO)){
			this.setSelected_hostBanorte(ApplicationConstants.EMPTY_STRING);
			this.setSelected_revision(ApplicationConstants.EMPTY_STRING);
			this.setSelected_hostComercio(ApplicationConstants.OPCION_SELECTED);
			this.setSelected_direct3D(ApplicationConstants.EMPTY_STRING);
		}else if (this.affiliation_integration.equals(ApplicationConstants.INTEGRATION_DIRECT3D)){
			this.setSelected_hostBanorte(ApplicationConstants.EMPTY_STRING);
			this.setSelected_revision(ApplicationConstants.EMPTY_STRING);
			this.setSelected_hostComercio(ApplicationConstants.EMPTY_STRING);
			this.setSelected_direct3D(ApplicationConstants.OPCION_SELECTED);
		}
		
	}
	
	
	private void selectOptionRentaDolar(){
		
		if(this.affiliation_rentaDolar != null){
			
			if(this.affiliation_rentaDolar.equals(ApplicationConstants.RENTA_DOLAR_2100)){
				this.setSelected_2000(ApplicationConstants.OPCION_SELECTED);
				this.setSelected_4000(ApplicationConstants.EMPTY_STRING);
			}else if (this.affiliation_rentaDolar.equals(ApplicationConstants.RENTA_DOLAR_4000)){
				this.setSelected_2000(ApplicationConstants.EMPTY_STRING);
				this.setSelected_4000(ApplicationConstants.OPCION_SELECTED);
			}
			
		}
	}
	
	/**
     * F-82716 - Mujer PYME Banorte - febrero 2020
     * Funcion para validar numero de cliente con lista dejada por altamira
     * -solo TPV y MPOS
     * -solo con plan 15, 35, 75 y 200
    */
	public void findCuentaMujerPyME(ActionEvent actionEvent)  throws ServletException, IOException {
		if("true".equalsIgnoreCase(getListaCuentaMujerPyMEVacia())){
			//setListaCuentaMujerPyMEVacia("true"); // si no hay elementos en la lista de altamira regresa a la vista TRUE en listaCuentaMujerPyMEVacia
			System.out.println("No hay cuentas mujer pyme banorte en la lista (adquierente): "+ (getClient_sic() != null && !getClient_sic().isEmpty() && !("".equals(getClient_sic()))?getClient_sic():" --- ")
																						  +"("+(getAffiliation_accountnumbermn() != null && !getAffiliation_accountnumbermn().isEmpty() && !("".equals(getAffiliation_accountnumbermn()))?getAffiliation_accountnumbermn():"--"  +")" 
																						  + "... " + (new java.sql.Timestamp(System.currentTimeMillis()))));
		}else{
			//setListaCuentaMujerPyMEVacia("false");
			if(getClient_sic() != null && !getClient_sic().isEmpty() && !("".equals(getClient_sic()))){
				//System.out.println("VERIFICAR_si hay cliente: "+getClient_sic());
				if(getAffiliation_accountnumbermn() != null && !getAffiliation_accountnumbermn().isEmpty() && !("".equals(getAffiliation_accountnumbermn()))){
					//si tiene cuenta en pesos
					//findByCuenta(getClient_sic(),getAffiliation_accountnumbermn());
//					if(getAffiliation_accountnumberdlls() != null && !getAffiliation_accountnumberdlls().isEmpty() && !("".equals(getAffiliation_accountnumberdlls()))){
//						System.out.println("VERIFICAR_ HAY 2 cuentas MN: "+getAffiliation_accountnumbermn() +" y  DOLARES: "+getAffiliation_accountnumberdlls());
//						//findByCuenta(getClient_sic(), getAffiliation_accountnumbermn().replaceFirst("^0+(?!$)", ""), getAffiliation_accountnumberdlls().replaceFirst("^0+(?!$)", ""));
//						findByCuenta(getClient_sic(), getAffiliation_accountnumbermn(), getAffiliation_accountnumberdlls());
//					}else{
						//System.out.println("VERIFICAR_solo hay cuenta MN: "+getAffiliation_accountnumbermn());
						findByCuenta(getClient_sic(),getAffiliation_accountnumbermn(),null);
//					}
				}
//				else{
//					//si no tiene cuenta en pesos revisa la de dolares
//					if(getAffiliation_accountnumberdlls() != null && !getAffiliation_accountnumberdlls().isEmpty() && !("".equals(getAffiliation_accountnumberdlls()))){
//						System.out.println("VERIFICAR_solo hay cuenta DOLARES: "+getAffiliation_accountnumbermn());
//						findByCuenta(getClient_sic(), null, getAffiliation_accountnumberdlls());
//					}
//				}				
			}
		}		
	}

	// metodo para buscar el num. del cliente y su cuenta en la lista de altamira 
	private void findByCuenta(String cliente, String cuenta, String cuentaDlls){
		//System.out.println("Valores--> cliente: "+cliente+" cuenta: "+cuenta+" cuentaDlls: "+cuentaDlls);
		try {
			//if(listaClientesMujerPyME.contains(cuenta)){
			if(listaClientesMujerPyME.containsKey(cliente)){// el cliente esta en la lista
				//System.out.println("el cliente esta en la lista");
				if(cuenta!=null){//si la cuenta es diferente de null
					//System.out.println("la cuenta MN esDiferente de NULL");
					if(cuentaDlls!=null){//BUSCA CUENTA MN Y DOLARES
						//System.out.println("la cuenta DOLARES esDiferente de NULL");
						if (cuenta.equalsIgnoreCase(listaClientesMujerPyME.get(cuenta))) {// la cuenta esta ligada al cliente
							//System.out.println("la cuenta MN es Mujer PyME que tiene ligada");
							//falta opcion si tiene otra cuenta
							esClienteMujerPyME="true";
							clienteMujerPyMEValidado=cliente;
							noEsClienteMujerPyMEValidado="false";
							esCuentaMujerPyME="true";
							cuentaMujerPyMEValidada= cuenta;
							noEsCuentaMujerPyMEValidada="false";
							isMsgMujerPyME="true";							
						} else {
							//System.out.println("la cuenta MN <NO> es Mujer PyME que tiene ligada");
							esClienteMujerPyME="true";
							clienteMujerPyMEValidado=cliente;
							noEsClienteMujerPyMEValidado="false";
							esCuentaMujerPyME="false";
							cuentaMujerPyMEValidada= "false";
							noEsCuentaMujerPyMEValidada=cuenta;
							isMsgMujerPyME="false";
						}
						if(cuenta.equalsIgnoreCase(listaClientesMujerPyME.get(cuentaDlls))){// busca cuenta en dolares del cliente tambien
							//System.out.println("la cuenta DOLARES es Mujer PyME que tiene ligada");
							esClienteMujerPyME="true";
							clienteMujerPyMEValidado=cliente;
							noEsClienteMujerPyMEValidado="false";
							esCuentaMujerPyMEDlls="true";
							cuentaMujerPyMEValidadaDlls= cuentaDlls;
							noEsCuentaMujerPyMEValidadaDlls="false";
							isMsgMujerPyME="true";
						}else{
							//System.out.println("la cuenta DOLARES <NO> es Mujer PyME que tiene ligada");
							esClienteMujerPyME="true";
							clienteMujerPyMEValidado=cliente;
							noEsClienteMujerPyMEValidado="false";
							esCuentaMujerPyMEDlls="false";
							cuentaMujerPyMEValidadaDlls= "false";
							noEsCuentaMujerPyMEValidada=cuentaDlls;
							isMsgMujerPyME="false";
						}
/*aqui normla*/		}else{//SOLO BUSCA LA CUENTA MN
						//System.out.println("Solo tiene cuenta MN --> "+cuenta+"="+listaClientesMujerPyME.get(cliente));
						if (cuenta.equalsIgnoreCase(listaClientesMujerPyME.get(cliente))) {// la cuenta esta ligada al cliente
							//System.out.println("la cuenta MN es Mujer PyME que tiene ligada");
							//falta opcion si tiene otra cuenta
							esClienteMujerPyME="true";
							clienteMujerPyMEValidado=cliente;
							noEsClienteMujerPyMEValidado="false";
							esCuentaMujerPyME="true";
							cuentaMujerPyMEValidada= cuenta;
							noEsCuentaMujerPyMEValidada="false";
							isMsgMujerPyME="true";
						} else {
							//System.out.println("la cuenta MN <NO> es Mujer PyME que tiene ligada");
							//esClienteMujerPyME="false";
							//clienteMujerPyMEValidado="false";
							//noEsClienteMujerPyMEValidado=cliente;
							esClienteMujerPyME="true";
							clienteMujerPyMEValidado=cliente;
							noEsClienteMujerPyMEValidado="false";
							esCuentaMujerPyME="false";
							cuentaMujerPyMEValidada= "false";
							noEsCuentaMujerPyMEValidada=cuenta;
							isMsgMujerPyME="false";
						}				
					}					
				}
//				else{
//					//System.out.println("Solo tiene cuenta DOLARES ");
//					if(cuentaDlls.equalsIgnoreCase(listaClientesMujerPyME.get(cuentaDlls))){// busca cuenta en dolares del cliente tambien
//						System.out.println("la cuenta DOLARES es Mujer PyME que tiene ligada");
//						esClienteMujerPyME="true";
//						clienteMujerPyMEValidado=cliente;
//						noEsClienteMujerPyMEValidado="false";
//						esCuentaMujerPyMEDlls="true";
//						cuentaMujerPyMEValidadaDlls= cuentaDlls;
//						noEsCuentaMujerPyMEValidadaDlls="false";
//						isMsgMujerPyME="true";
//					}else{
//						System.out.println("la cuenta DOLARES <NO> es Mujer PyME que tiene ligada");
//						//esClienteMujerPyME="false";
//						//clienteMujerPyMEValidado="false";
//						//noEsClienteMujerPyMEValidado=cliente;
//						esClienteMujerPyME="true";
//						clienteMujerPyMEValidado=cliente;
//						noEsClienteMujerPyMEValidado="false";
//						esCuentaMujerPyMEDlls="false";
//						cuentaMujerPyMEValidadaDlls= "false";
//						noEsCuentaMujerPyMEValidada=cuentaDlls;
//						isMsgMujerPyME="false";
//					}
//				}
				
			}else{
				//System.out.println("no es cliente Mujer PyME");
				esClienteMujerPyME="false";
				clienteMujerPyMEValidado="false";
				noEsClienteMujerPyMEValidado=cliente;
				isMsgMujerPyME="false";
			}
		} catch (Exception ex) {
			System.out.println("Error al buscar cliente en cuentas Mujer PyME... "+ex.getMessage());
			log.log(Level.WARNING, ex.getMessage(), ex);
		}
		
	}
	
	public static void cargarListaCuentasMujerPyME(Map<String,String> lista) throws FileNotFoundException, IOException {
		listaClientesMujerPyME= new TreeMap<String,String>();
		//listaClientesMujerPyME.clear();
		
		try{
			//System.out.println("en carga lista cuentas Mujer Pyme: "+(!lista.isEmpty() || lista==null));
			if(!lista.isEmpty() || lista==null){
				//System.out.println("se llena la lista");
				//listaClientesMujerPyME=lista;
				//listaClientesMujerPyME.putAll(lista);
				//setListaCuentaMujerPyMEVacia("false");

				
				Iterator<String> it = lista.keySet().iterator();
				while(it.hasNext()){
				  String key = it.next().toString();
				  listaClientesMujerPyME.put(key, lista.get(key));
				  System.out.println("Cliente: " + key + " -> Cuenta: " + lista.get(key));//Comentar para PRD... muestra los clientes y su cuenta con Mujer PyME Banorte
				}
			}
		}catch(Exception ex){
			System.out.println("Error al cargar lista de cuentas Mujer PyME... "+ex.getMessage());
			log.log(Level.WARNING, ex.getMessage(), ex);
		}
	}

    
    // ***************************************SETTERS  y GETTERS *****************************************************
    
    
    public void setRecalculateCommisionTable(boolean recalculateCommisionTable){
    	this.recalculateCommisionTable = recalculateCommisionTable;
    }
    public boolean getRecalculateCommisionTable(){
    	if(this.recalculateCommisionTable==null){
    		this.recalculateCommisionTable = false;
    	}
    	
    	return this.recalculateCommisionTable;
    }

    public void setOrderFormLoaded(boolean orderFormLoaded){
    	this.orderFormLoaded = orderFormLoaded;
    }
    public boolean getOrderFormLoaded(){
    	if(this.orderFormLoaded==null)
    		this.orderFormLoaded = false;
    	
    	return this.orderFormLoaded;
    }

	/**
	 * @return the affiliation_otherComercialPlan
	 */
	public String getAffiliation_otherComercialPlan() {
		return affiliation_otherComercialPlan;
	}

	/**
	 * @param affiliation_otherComercialPlan the affiliation_otherComercialPlan to set
	 */
	public void setAffiliation_otherComercialPlan(
			String affiliation_otherComercialPlan) {
		this.affiliation_otherComercialPlan = affiliation_otherComercialPlan;
	}

	/**
	 * @return the affiliation_allianceArray
	 */
	public SelectItem[] getAffiliation_allianceArray() {
		if (this.affiliation_allianceArray == null) {
        	this.affiliation_allianceArray = new ContractUtil().getattributeOptionArray(ApplicationConstants.FIELDNAME_ALLIANCE);
        }
        
        return this.affiliation_allianceArray;
	}

	/**
	 * @param affiliation_allianceArray the affiliation_allianceArray to set
	 */
	public void setAffiliation_allianceArray(SelectItem[] affiliation_allianceArray) {
		this.affiliation_allianceArray = affiliation_allianceArray;
	}

	/**
	 * @return the affiliation_chargeTypeArray
	 */
	public SelectItem[] getAffiliation_chargeTypeArray() {
		if (this.affiliation_chargeTypeArray == null) {
        	this.affiliation_chargeTypeArray = new ContractUtil().getattributeOptionArray(ApplicationConstants.FIELDNAME_CHARGETYPE);
        }
        
        return this.affiliation_chargeTypeArray;
	}

	/**
	 * @param affiliation_chargeTypeArray the affiliation_chargeTypeArray to set
	 */
	public void setAffiliation_chargeTypeArray(
			SelectItem[] affiliation_chargeTypeArray) {
		this.affiliation_chargeTypeArray = affiliation_chargeTypeArray;
	}

	/**
	 * @return the affiliation_chargeType
	 */
	public String getAffiliation_chargeType() {
		return affiliation_chargeType;
	}

	/**
	 * @param affiliation_chargeType the affiliation_chargeType to set
	 */
	public void setAffiliation_chargeType(String affiliation_chargeType) {
		this.affiliation_chargeType = affiliation_chargeType;
	}

	/**
	 * @return the affiliation_chargeType_hidd
	 */
	public String getAffiliation_chargeType_hidd() {
		return affiliation_chargeType_hidd;
	}

	/**
	 * @param affiliation_chargeType the affiliation_chargeType to set
	 */
	public void setAffiliation_chargeType_hidd(String affiliation_chargeType_hidd) {
		this.affiliation_chargeType_hidd = affiliation_chargeType_hidd;
		this.setAffiliation_chargeType(this.affiliation_chargeType_hidd);
	}	
	/**
	 * @return the affiliation_alliance
	 */
	public String getAffiliation_alliance() {
		
		if(affiliation_alliance ==null){
			affiliation_alliance = ApplicationConstants.EMPTY_STRING;
		}
		return affiliation_alliance;
	}

	/**
	 * @param affiliation_alliance the affiliation_alliance to set
	 */
	public void setAffiliation_alliance(String affiliation_alliance) {
		this.affiliation_alliance = affiliation_alliance;
	}

	/**
	 * @return the affiliation_alliance
	 */
	public String getAffiliation_alliance_hidd() {
		
		if(affiliation_alliance_hidd ==null){
			affiliation_alliance_hidd = ApplicationConstants.EMPTY_STRING;
		}
		return affiliation_alliance_hidd;
	}

	/**
	 * @param affiliation_alliance the affiliation_alliance to set
	 */
	public void setAffiliation_alliance_hidd(String affiliation_alliance_hidd) {
		this.affiliation_alliance_hidd = affiliation_alliance_hidd;
		this.setAffiliation_alliance(this.affiliation_alliance_hidd);
	}
	//Tiempo Aire Netpay 
	public String getAffiliation_tiempoaire() {
		if(affiliation_tiempoaire ==null){
			affiliation_tiempoaire = ApplicationConstants.EMPTY_STRING;
		}
		return affiliation_tiempoaire;
	}

	public void setAffiliation_tiempoaire(String affiliation_tiempoaire) {
		this.affiliation_tiempoaire = affiliation_tiempoaire;
	}

	public String getAffiliation_telcel() {
		if(affiliation_telcel ==null){
			affiliation_telcel = ApplicationConstants.EMPTY_STRING;
		}
		return affiliation_telcel;
	}

	public void setAffiliation_telcel(String affiliation_telcel) {
		this.affiliation_telcel = affiliation_telcel;
	}

	public String getAffiliation_movistar() {
		if(affiliation_movistar ==null){
			affiliation_movistar = ApplicationConstants.EMPTY_STRING;
		} 		
		return affiliation_movistar;
	}

	public void setAffiliation_movistar(String affiliation_movistar) {
		this.affiliation_movistar = affiliation_movistar;
	}

	public String getAffiliation_iusacell() {
		if(affiliation_iusacell ==null){
			affiliation_iusacell = ApplicationConstants.EMPTY_STRING;
		} 			
		return affiliation_iusacell;
	}

	public void setAffiliation_iusacell(String affiliation_iusacell) {
		this.affiliation_iusacell = affiliation_iusacell;
	}

	public String getAffiliation_nextel() {
		if(affiliation_nextel ==null){
			affiliation_nextel = ApplicationConstants.EMPTY_STRING;
		} 			
		return affiliation_nextel;
	}

	public void setAffiliation_nextel(String affiliation_nextel) {
		this.affiliation_nextel = affiliation_nextel;
	}

	/**
	 * @return the affiliation_tpv_payroll
	 */
	public String getAffiliation_tpv_payroll() {
		return affiliation_tpv_payroll;
	}

	/**
	 * @param affiliation_tpv_payroll the affiliation_tpv_payroll to set
	 */
	public void setAffiliation_tpv_payroll(String affiliation_tpv_payroll) {
		this.affiliation_tpv_payroll = affiliation_tpv_payroll;
	}

	/**
	 * @return the tpv_number_employee
	 */
	public String getTpv_number_employee() {
		if(this.tpv_number_employee ==null){
			return ApplicationConstants.EMPTY_STRING;
		}else{
			return tpv_number_employee;
		}
	}

	/**
	 * @param tpv_number_employee the tpv_number_employee to set
	 */
	public void setTpv_number_employee(String tpv_number_employee) {
		this.tpv_number_employee = tpv_number_employee;
	}

	/**
	 * @return the tpv_penalty
	 */
	public String getTpv_penalty() {
		if(this.tpv_penalty==null){
			return ApplicationConstants.EMPTY_STRING;
		}else{
			return tpv_penalty;
		}
	}

	/**
	 * @param tpv_penalty the tpv_penalty to set
	 */
	public void setTpv_penalty(String tpv_penalty) {
		this.tpv_penalty = tpv_penalty;
	}

	/**
	 * @return the optionMonthlyratemn
	 */
	public String getOptionMonthlyratemn() {
		return optionMonthlyratemn;
	}

	/**
	 * @param optionMonthlyratemn the optionMonthlyratemn to set
	 */
	public void setOptionMonthlyratemn(String optionMonthlyratemn) {
		this.optionMonthlyratemn = optionMonthlyratemn;
	}

	/**
	 * @return the replaceAmountratemn
	 */
	public String getReplaceAmountratemn() {
		if(replaceAmountratemn==null || replaceAmountratemn.equals(ApplicationConstants.EMPTY_STRING)){
			replaceAmountratemn = ApplicationConstants.ZERO_DOUBLE_STR;
		}
		return replaceAmountratemn;
	}

	/**
	 * @param replaceAmountratemn the replaceAmountratemn to set
	 */
	public void setReplaceAmountratemn(String replaceAmountratemn) {
		this.replaceAmountratemn = replaceAmountratemn;
	}

	/**
	 * @return the optionMonthlyratedlls
	 */
	public String getOptionMonthlyratedlls() {
		return optionMonthlyratedlls;
	}

	/**
	 * @param optionMonthlyratedlls the optionMonthlyratedlls to set
	 */
	public void setOptionMonthlyratedlls(String optionMonthlyratedlls) {
		this.optionMonthlyratedlls = optionMonthlyratedlls;
	}

	/**
	 * @return the replaceAmountratedlls
	 */
	public String getReplaceAmountratedlls() {
		if(replaceAmountratedlls==null || replaceAmountratedlls.equals(ApplicationConstants.EMPTY_STRING)){
			replaceAmountratedlls = ApplicationConstants.ZERO_DOUBLE_STR;
		}
		return replaceAmountratedlls;
	}

	/**
	 * @param replaceAmountratedlls the replaceAmountratedlls to set
	 */
	public void setReplaceAmountratedlls(String replaceAmountratedlls) {
		this.replaceAmountratedlls = replaceAmountratedlls;
	}

	/**
	 * @return the affiliation_nominaselected
	 */
	public String getAffiliation_nominaselected() {
		if(this.affiliation_nominaselected == null){
			return ApplicationConstants.VALUE_FALSE;
		}else{
			return affiliation_nominaselected;
		}
	}

	/**
	 * @param affiliation_nominaselected the affiliation_nominaselected to set
	 */
	public void setAffiliation_nominaselected(String affiliation_nominaselected) {
		this.affiliation_nominaselected = affiliation_nominaselected;
	}
	
	
	public Boolean isTPVNomina(){
		if(this.affiliation_tpv_payroll !=null){
			if(this.getAffiliation_tpv_payroll().equals(ApplicationConstants.VALUE_TRUE)){
				return Boolean.TRUE;
			}
			else{
				return Boolean.FALSE;
			}
		}else{
			return Boolean.FALSE;
		}
		
	}
	
	public Boolean isSaldomn(){
		if(this.getOptionMonthlyratemn()!=null && this.getOptionMonthlyratemn().equals(ApplicationConstants.SUSTITUIR_SALDO)){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}
	
	public Boolean isFacturacionmn(){
		if(this.getOptionMonthlyratemn()!=null && this.getOptionMonthlyratemn().equals(ApplicationConstants.SUSTITUIR_FACTURACION)){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}
	
	public Boolean isSaldodlls(){
		if(this.getOptionMonthlyratedlls()!=null && this.getOptionMonthlyratedlls().equals( ApplicationConstants.SUSTITUIR_SALDO) ){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}
	
	public Boolean isFacturaciondlls(){
		if(this.getOptionMonthlyratedlls()!=null && this.getOptionMonthlyratedlls().equals(ApplicationConstants.SUSTITUIR_FACTURACION)){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}

	/**
	 * @return the affiliation_integration
	 */
	public String getAffiliation_integration() {
		if(this.affiliation_integration!=null){
			return affiliation_integration;
		}else {
			return ApplicationConstants.EMPTY_STRING;
		}
		
	}

	/**
	 * @param affiliation_integration the affiliation_integration to set
	 */
	public void setAffiliation_integration(String affiliation_integration) {
		this.affiliation_integration = affiliation_integration;
	}

	/**
	 * @return the selected_hostBanorte
	 */
	public String getSelected_hostBanorte() {
		return selected_hostBanorte;
	}

	/**
	 * @param selected_hostBanorte the selected_hostBanorte to set
	 */
	public void setSelected_hostBanorte(String selected_hostBanorte) {
		this.selected_hostBanorte = selected_hostBanorte;
	}

	/**
	 * @return the selected_revision
	 */
	public String getSelected_revision() {
		return selected_revision;
	}

	/**
	 * @param selected_revision the selected_revision to set
	 */
	public void setSelected_revision(String selected_revision) {
		this.selected_revision = selected_revision;
	}

	/**
	 * @return the selected_hostComercio
	 */
	public String getSelected_hostComercio() {
		return selected_hostComercio;
	}

	/**
	 * @param selected_hostComercio the selected_hostComercio to set
	 */
	public void setSelected_hostComercio(String selected_hostComercio) {
		this.selected_hostComercio = selected_hostComercio;
	}
	

	public String getSelected_direct3D() {
		return selected_direct3D;
	}

	public void setSelected_direct3D(String selected_direct3D) {
		this.selected_direct3D = selected_direct3D;
	}

	/**
	 * @return the affiliation_rentaDolar
	 */
	public String getAffiliation_rentaDolar() {
		return affiliation_rentaDolar;
	}

	/**
	 * @param affiliation_rentaDolar the affiliation_rentaDolar to set
	 */
	public void setAffiliation_rentaDolar(String affiliation_rentaDolar) {
		this.affiliation_rentaDolar = affiliation_rentaDolar;
	}

	/**
	 * @return the selected_2000
	 */
	public String getSelected_2000() {
		return selected_2000;
	}

	/**
	 * @param selected_2000 the selected_2000 to set
	 */
	public void setSelected_2000(String selected_2000) {
		this.selected_2000 = selected_2000;
	}

	/**
	 * @return the selected_4000
	 */
	public String getSelected_4000() {
		return selected_4000;
	}

	/**
	 * @param selected_4000 the selected_4000 to set
	 */
	public void setSelected_4000(String selected_4000) {
		this.selected_4000 = selected_4000;
	}
		
	public String getAff_cashback() {
		if(aff_cashback!=null && aff_cashback.equals("X")){
			return "true";
		}else{
			return "false";
		}
	}

	public void setAff_cashback(String aff_cashback) {
		if(aff_cashback.equals("true")){
			this.aff_cashback="X";
		}else{
			this.aff_cashback="";
		}
	}

	public String getAff_commCbPymt() {
		return aff_commCbPymt;
	}

	public void setAff_commCbPymt(String aff_commCbPymt) {
		this.aff_commCbPymt = aff_commCbPymt;
	}

	public String getAff_commCbChrg() {
		return aff_commCbChrg;
	}

	public void setAff_commCbChrg(String aff_commCbChrg) {
		this.aff_commCbChrg = aff_commCbChrg;
	}

	public String getAff_commCbChrgDll() {
		return aff_commCbChrgDll;
	}

	public void setAff_commCbChrgDll(String aff_commCbChrgDll) {
		this.aff_commCbChrgDll = aff_commCbChrgDll;
	}
	
	public String getAmex() {
		return amex;
	}

	public void setAmex(String amex) {
		this.amex = amex;
	}

	public String getTvpUnattended() {
		return tpvUnattended;
	}

	public void setTvpUnattended(String tvpUnattended) {
		this.tpvUnattended = tvpUnattended;
	}

	public String getTransConciliation() {
		return transConciliation;
	}

	public void setTransConciliation(String transConciliation) {
		this.transConciliation = transConciliation;
	}

	public String getPromNoteRqst() {
		return promNoteRqst;
	}

	public void setPromNoteRqst(String promNoteRqst) {
		this.promNoteRqst = promNoteRqst;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminFLastName() {
		return adminFLastName;
	}

	public void setAdminFLastName(String adminFLastName) {
		this.adminFLastName = adminFLastName;
	}

	public String getAdminMLastName() {
		return adminMLastName;
	}

	public void setAdminMLastName(String adminMLastName) {
		this.adminMLastName = adminMLastName;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminPhone() {
		return adminPhone;
	}

	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}

	public String getPromNoteRqstName1() {
		return promNoteRqstName1;
	}

	public void setPromNoteRqstName1(String promNoteRqstName1) {
		this.promNoteRqstName1 = promNoteRqstName1;
	}

	public String getPromNoteRqstFLastName1() {
		return promNoteRqstFLastName1;
	}

	public void setPromNoteRqstFLastName1(String promNoteRqstFLastName1) {
		this.promNoteRqstFLastName1 = promNoteRqstFLastName1;
	}

	public String getPromNoteRqstMLastName1() {
		return promNoteRqstMLastName1;
	}

	public void setPromNoteRqstMLastName1(String promNoteRqstMLastName1) {
		this.promNoteRqstMLastName1 = promNoteRqstMLastName1;
	}

	public String getPromNoteRqstComplete1() {
		return promNoteRqstComplete1;
	}

	public void setPromNoteRqstComplete1(String promNoteRqstComplete1) {
		this.promNoteRqstComplete1 = promNoteRqstComplete1;
	}

	public String getPromNoteRqstEmail1() {
		return promNoteRqstEmail1;
	}

	public void setPromNoteRqstEmail1(String promNoteRqstEmail1) {
		this.promNoteRqstEmail1 = promNoteRqstEmail1;
	}

	public String getPromNoteRqstPhone1() {
		return promNoteRqstPhone1;
	}

	public void setPromNoteRqstPhone1(String promNoteRqstPhone1) {
		this.promNoteRqstPhone1 = promNoteRqstPhone1;
	}

	public String getPromNoteRqstName2() {
		return promNoteRqstName2;
	}

	public void setPromNoteRqstName2(String promNoteRqstName2) {
		this.promNoteRqstName2 = promNoteRqstName2;
	}

	public String getPromNoteRqstFLastName2() {
		return promNoteRqstFLastName2;
	}

	public void setPromNoteRqstFLastName2(String promNoteRqstFLastName2) {
		this.promNoteRqstFLastName2 = promNoteRqstFLastName2;
	}

	public String getPromNoteRqstMLastName2() {
		return promNoteRqstMLastName2;
	}

	public void setPromNoteRqstMLastName2(String promNoteRqstMLastName2) {
		this.promNoteRqstMLastName2 = promNoteRqstMLastName2;
	}

	public String getPromNoteRqstComplete2() {
		return promNoteRqstComplete2;
	}

	public void setPromNoteRqstComplete2(String promNoteRqstComplete2) {
		this.promNoteRqstComplete2 = promNoteRqstComplete2;
	}

	public String getPromNoteRqstEmail2() {
		return promNoteRqstEmail2;
	}

	public void setPromNoteRqstEmail2(String promNoteRqstEmail2) {
		this.promNoteRqstEmail2 = promNoteRqstEmail2;
	}

	public String getPromNoteRqstPhone2() {
		return promNoteRqstPhone2;
	}

	public void setPromNoteRqstPhone2(String promNoteRqstPhone2) {
		this.promNoteRqstPhone2 = promNoteRqstPhone2;
	}

	public Affiliation getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(Affiliation affiliation) {
		this.affiliation = affiliation;
	}
	
	public String getAff_exentDep() {
		if(affiliation.getExentDep()!=null && affiliation.getExentDep()==1){
			return"true";
		}else{
			return "false";			
		}
	}

	public void setAff_exentDep(String aff_exentDep) {
		if("true".equalsIgnoreCase(aff_exentDep)){
			affiliation.setExentDep(1);
		}else{
			affiliation.setExentDep(0);
		}
	}
	
	public String getAff_impulsoCaptacion() {
		//return impulsoCaptacion;
		if(aff_impulsoCaptacion!=null && "X".equalsIgnoreCase(aff_impulsoCaptacion)){
			return "true";
		}else{
			return "false";
		}
	}

	public void setAff_impulsoCaptacion(String aff_impulsoCaptacion) {
		//this.impulsoCaptacion= impulsoCaptacion;
		if("true".equalsIgnoreCase(aff_impulsoCaptacion)){
			this.aff_impulsoCaptacion= "X";
		}else{
			this.aff_impulsoCaptacion="";
		}
	}
	
	//Garantia Liquida
	public String getAff_garantiaLiquida() {
		return aff_garantiaLiquida;
	}

	public void setAff_garantiaLiquida(String aff_garantiaLiquida) {
		this.aff_garantiaLiquida = aff_garantiaLiquida;

	}

	public String getAdminComplete() {
		return adminComplete;
	}

	public void setAdminComplete(String adminComplete) {
		this.adminComplete = adminComplete;
	}

	public String getTpvUnattended() {
		return tpvUnattended;
	}

	public void setTpvUnattended(String tpvUnattended) {
		this.tpvUnattended = tpvUnattended;
	}

	public String getMobilePymnt() {
		return mobilePymnt;
	}

	public void setMobilePymnt(String mobilePymnt) {
		this.mobilePymnt = mobilePymnt;
	}

	public String getOwnTpvPinpad() {
		if(affiliation.getOwnTpvPinpad()!=null && affiliation.getOwnTpvPinpad().equalsIgnoreCase("X")){
			return "true";
		}else{
			return "false";
		}
	}

	public void setOwnTpvPinpad(String ownTpvPinpad) {
		if(ownTpvPinpad.equalsIgnoreCase("true")){
			affiliation.setOwnTpvPinpad("X");
		}else{
			affiliation.setOwnTpvPinpad("");
		}
	}

	public String getDcc() {
		return dcc;
	}

	public void setDcc(String dcc) {
		this.dcc = dcc;
	}

	public String getIxePlan() {
		return ixePlan;
	}

	public void setIxePlan(String ixePlan) {
		this.ixePlan = ixePlan;
	}

	public String getBanortePlan() {
		return banortePlan;
	}

	public void setBanortePlan(String banortePlan) {
		this.banortePlan = banortePlan;
	}

	public Double getTransactionFeeCyberMn() {
		return transactionFeeCyberMn;
	}

	public void setTransactionFeeCyberMn(Double transactionFeeCyberMn) {
		this.transactionFeeCyberMn = transactionFeeCyberMn;
	}

	public Double getLimitedCoverageCyberMn() {
		return limitedCoverageCyberMn;
	}

	public void setLimitedCoverageCyberMn(Double limitedCoverageCyberMn) {
		this.limitedCoverageCyberMn = limitedCoverageCyberMn;
	}

	public Double getWideCoverageCyberMn() {
		return wideCoverageCyberMn;
	}

	public void setWideCoverageCyberMn(Double wideCoverageCyberMn) {
		this.wideCoverageCyberMn = wideCoverageCyberMn;
	}

	public Double getTransactionFeeMicrosMn() {
		return transactionFeeMicrosMn;
	}

	public void setTransactionFeeMicrosMn(Double transactionFeeMicrosMn) {
		this.transactionFeeMicrosMn = transactionFeeMicrosMn;
	}

	public Double getTransactionFeeCyberDlls() {
		return transactionFeeCyberDlls;
	}

	public void setTransactionFeeCyberDlls(Double transactionFeeCyberDlls) {
		this.transactionFeeCyberDlls = transactionFeeCyberDlls;
	}

	public Double getLimitedCoverageCyberDlls() {
		return limitedCoverageCyberDlls;
	}

	public void setLimitedCoverageCyberDlls(Double limitedCoverageCyberDlls) {
		this.limitedCoverageCyberDlls = limitedCoverageCyberDlls;
	}

	public Double getWideCoverageCyberDlls() {
		return wideCoverageCyberDlls;
	}

	public void setWideCoverageCyberDlls(Double wideCoverageCyberDlls) {
		this.wideCoverageCyberDlls = wideCoverageCyberDlls;
	}

	public Double getTransactionFeeMicrosDlls() {
		return transactionFeeMicrosDlls;
	}

	public void setTransactionFeeMicrosDlls(Double transactionFeeMicrosDlls) {
		this.transactionFeeMicrosDlls = transactionFeeMicrosDlls;
	}

	public String getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(String antiguedad) {
		this.antiguedad = antiguedad;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

//	public String getOption3dSecure() {
//		return option3dSecure;
//	}
//
//	public void setOption3dSecure(String option3dSecure) {
//		this.option3dSecure = option3dSecure;
//	}

	public int getFlag3dSelected() {
		return flag3dSelected;
	}

	public void setFlag3dSelected(int flag3dSelected) {
		this.flag3dSelected = flag3dSelected;
	}
	
	
	public String getAffiliation_latitude() {
		return affiliation_latitude;
	}
	public void setAffiliation_latitude(String affiliation_latitude) {
		this.affiliation_latitude = affiliation_latitude;
	}

	public String getAffiliation_length() {
		return affiliation_length;
	}
	public void setAffiliation_length(String affiliation_length) {
		this.affiliation_length = affiliation_length;
	}
	
	public String getRentBy() {
		return rentBy;
	}
	public void setRentBy(String rentBy) {
		this.rentBy = rentBy;
	}

	public String getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getAff_montoInicial() {
		return aff_montoInicial;
	}

	public void setAff_montoInicial(String aff_montoInicial) {
		this.aff_montoInicial = aff_montoInicial;
	}

	public String getAff_montoInicialGL() {
		return aff_montoInicialGL;
	}

	public void setAff_montoInicialGL(String aff_montoInicialGL) {
		this.aff_montoInicialGL = aff_montoInicialGL;
	}

	public String getAff_montoPromDiario() {
		return aff_montoPromDiario;
	}

	public void setAff_montoPromDiario(String aff_montoPromDiario) {
		this.aff_montoPromDiario = aff_montoPromDiario;
	}

	public String getAff_porcentajeRestanteGL() {
		return aff_porcentajeRestanteGL;
	}

	public void setAff_porcentajeRestanteGL(String aff_porcentajeRestanteGL) {
		this.aff_porcentajeRestanteGL = aff_porcentajeRestanteGL;
	}

	public String getAff_porcentajeGL() {
		return aff_porcentajeGL;
	}

	public void setAff_porcentajeGL(String aff_porcentajeGL) {
		this.aff_porcentajeGL = aff_porcentajeGL;
	}

	public String getAff_montoRestanteGL() {
		return aff_montoRestanteGL;
	}

	public void setAff_montoRestanteGL(String aff_montoRestanteGL) {
		this.aff_montoRestanteGL = aff_montoRestanteGL;
	}

	public String getAff_montoGL() {
		return aff_montoGL;
	}

	public void setAff_montoGL(String aff_montoGL) {
		this.aff_montoGL = aff_montoGL;
	}

	public String getAff_promMontoDiarioGL() {
		return aff_promMontoDiarioGL;
	}

	public void setAff_promMontoDiarioGL(String aff_promMontoDiarioGL) {
		this.aff_promMontoDiarioGL = aff_promMontoDiarioGL;
	}

	public String getAff_porcentajeInicialGL() {
		return aff_porcentajeInicialGL;
	}

	public void setAff_porcentajeInicialGL(String aff_porcentajeInicialGL) {
		this.aff_porcentajeInicialGL = aff_porcentajeInicialGL;
	}

	public String getAff_porcentajeDiarioGL() {
		return aff_porcentajeDiarioGL;
	}

	public void setAff_porcentajeDiarioGL(String aff_porcentajeDiarioGL) {
		this.aff_porcentajeDiarioGL = aff_porcentajeDiarioGL;
	}

	public String getAff_ventasEstimadasMensuales() {
		return aff_ventasEstimadasMensuales;
	}

	public void setAff_ventasEstimadasMensuales(String aff_ventasEstimadasMensuales) {
		this.aff_ventasEstimadasMensuales = aff_ventasEstimadasMensuales;
	}

	public String getAff_montoEstimadoDeTransaccion() {
		return aff_montoEstimadoDeTransaccion;
	}

	public void setAff_montoEstimadoDeTransaccion(String aff_montoEstimadoDeTransacción) {
		this.aff_montoEstimadoDeTransaccion = aff_montoEstimadoDeTransacción;
	}

	public String getOptionPorcentajeVentasDiarias() {
		//return optionPorcentajeVentasDiarias;
		/*if(optionPorcentajeVentasDiarias!=null && "X".equalsIgnoreCase(optionPorcentajeVentasDiarias)){
			return "0";
		}else{
			return "1";
		}*/
		return "0";
	}

	public void setOptionPorcentajeVentasDiarias(String optionPorcentajeVentasDiarias) {
		this.optionPorcentajeVentasDiarias = optionPorcentajeVentasDiarias;
	}

	public String getOptionMontoFijoDiario() {
		return "";
		//return optionMontoFijoDiario;
	}

	public void setOptionMontoFijoDiario(String optionMontoFijoDiario) {
		this.optionMontoFijoDiario = optionMontoFijoDiario;
	}

	public String getAff_excepcionPorceGL() {
		return aff_excepcionPorceGL;
		//return aff_excepcionPorceGL.replaceAll(" %", "");
	}

	public void setAff_excepcionPorceGL(String aff_excepcionPorceGL) {
		this.aff_excepcionPorceGL = aff_excepcionPorceGL;
	}

	public String getSeleccionGiroAltoRiesgoGL() {
		return seleccionGiroAltoRiesgoGL;
	}

	public void setSeleccionGiroAltoRiesgoGL(String seleccionGiroAltoRiesgoGL) {
		this.seleccionGiroAltoRiesgoGL = seleccionGiroAltoRiesgoGL;
	}

	public String getAff_auxMontoGL() {
		return aff_auxMontoGL;
	}

	public void setAff_auxMontoGL(String aff_auxMontoGL) {
		this.aff_auxMontoGL = aff_auxMontoGL;
	}

	public String getAff_auxMontoInicial() {
		return aff_auxMontoInicial;
	}

	public void setAff_auxMontoInicial(String aff_auxMontoInicial) {
		this.aff_auxMontoInicial = aff_auxMontoInicial;
	}

	public String getAff_auxMontoPromDiario() {
		return aff_auxMontoPromDiario;
	}

	public void setAff_auxMontoPromDiario(String aff_auxMontoPromDiario) {
		this.aff_auxMontoPromDiario = aff_auxMontoPromDiario;
	}

	public String getAff_auxPorcentajeInicialGL() {
		return aff_auxPorcentajeInicialGL;
	}

	public void setAff_auxPorcentajeInicialGL(String aff_auxPorcentajeInicialGL) {
		this.aff_auxPorcentajeInicialGL = aff_auxPorcentajeInicialGL;
	}

	public String getAff_auxMontoInicialGL() {
		return aff_auxMontoInicialGL;
	}

	public void setAff_auxMontoInicialGL(String aff_auxMontoInicialGL) {
		this.aff_auxMontoInicialGL = aff_auxMontoInicialGL;
	}

	public String getAff_auxPorcentajeRestanteGL() {
		return aff_auxPorcentajeRestanteGL;
	}

	public void setAff_auxPorcentajeRestanteGL(String aff_auxPorcentajeRestanteGL) {
		this.aff_auxPorcentajeRestanteGL = aff_auxPorcentajeRestanteGL;
	}

	public String getAff_auxMontoRestanteGL() {
		return aff_auxMontoRestanteGL;
	}

	public void setAff_auxMontoRestanteGL(String aff_auxMontoRestanteGL) {
		this.aff_auxMontoRestanteGL = aff_auxMontoRestanteGL;
	}

	public String getAuxOptionPorcentajeVentasDiarias() {
		return auxOptionPorcentajeVentasDiarias;
	}

	public void setAuxOptionPorcentajeVentasDiarias(String auxOptionPorcentajeVentasDiarias) {
		this.auxOptionPorcentajeVentasDiarias = auxOptionPorcentajeVentasDiarias;
	}

	public String getAuxOptionMontoFijoDiario() {
		return auxOptionMontoFijoDiario;
	}

	public void setAuxOptionMontoFijoDiario(String auxOptionMontoFijoDiario) {
		this.auxOptionMontoFijoDiario = auxOptionMontoFijoDiario;
	}

	public String getAff_auxPromMontoDiarioGL() {
		return aff_auxPromMontoDiarioGL;
	}

	public void setAff_auxPromMontoDiarioGL(String aff_auxPromMontoDiarioGL) {
		this.aff_auxPromMontoDiarioGL = aff_auxPromMontoDiarioGL;
	}

	public String getAff_auxPorcentajeDiarioGL() {
		return aff_auxPorcentajeDiarioGL;
	}

	public void setAff_auxPorcentajeDiarioGL(String aff_auxPorcentajeDiarioGL) {
		this.aff_auxPorcentajeDiarioGL = aff_auxPorcentajeDiarioGL;
	}

	public String getFianzaOculta() {
		return fianzaOculta;
	}

	public void setFianzaOculta(String fianzaOculta) {
		this.fianzaOculta = fianzaOculta;
	}

	public String getAff_diasAproxGL() {
		return aff_diasAproxGL;
	}

	public void setAff_diasAproxGL(String aff_diasAproxGL) {
		this.aff_diasAproxGL = aff_diasAproxGL;
	}

	public String getAff_auxDiasAproxGL() {
		return aff_auxDiasAproxGL;
	}

	public void setAff_auxDiasAproxGL(String aff_auxDiasAproxGL) {
		this.aff_auxDiasAproxGL = aff_auxDiasAproxGL;
	}

	public String getAff_glOriginal() {
		return aff_glOriginal;
	}

	public void setAff_glOriginal(String aff_glOriginal) {
		this.aff_glOriginal = aff_glOriginal;
	}

	public String getAff_auxGlOriginal() {
		return aff_auxGlOriginal;
	}

	public void setAff_auxGlOriginal(String aff_auxGlOriginal) {
		this.aff_auxGlOriginal = aff_auxGlOriginal;
	}

	public String getAff_comentariosDisminucionExcepcionGL() {
		return aff_comentariosDisminucionExcepcionGL;
	}

	public void setAff_comentariosDisminucionExcepcionGL(String aff_comentariosDisminucionExcepcionGL) {
		this.aff_comentariosDisminucionExcepcionGL = aff_comentariosDisminucionExcepcionGL;
	}

	public String getExencionConvenienciaComercialVIP() {
		return exencionConvenienciaComercialVIP;
	}

	public void setExencionConvenienciaComercialVIP(String exencionConvenienciaComercialVIP) {
		this.exencionConvenienciaComercialVIP = exencionConvenienciaComercialVIP;
	}

	public String getExencionOtros() {
		return exencionOtros;
	}

	public void setExencionOtros(String exencionOtros) {
		this.exencionOtros = exencionOtros;
	}

	public String getExencionJustificacion() {
		return exencionJustificacion;
	}

	public void setExencionJustificacion(String exencionJustificacion) {
		this.exencionJustificacion = exencionJustificacion;
	}

	public String getSolvenciaEconimicaSi() {
		return solvenciaEconimicaSi;
	}

	public void setSolvenciaEconimicaSi(String solvenciaEconimicaSi) {
		this.solvenciaEconimicaSi = solvenciaEconimicaSi;
	}

	public String getSolvenciaEconimicaNo() {
		return solvenciaEconimicaNo;
	}

	public void setSolvenciaEconimicaNo(String solvenciaEconimicaNo) {
		this.solvenciaEconimicaNo = solvenciaEconimicaNo;
	}

	public String getVisitaOcularRecienteSi() {
		return visitaOcularRecienteSi;
	}

	public void setVisitaOcularRecienteSi(String visitaOcularRecienteSi) {
		this.visitaOcularRecienteSi = visitaOcularRecienteSi;
	}

	public String getVisitaOcularRecienteNo() {
		return visitaOcularRecienteNo;
	}

	public void setVisitaOcularRecienteNo(String visitaOcularRecienteNo) {
		this.visitaOcularRecienteNo = visitaOcularRecienteNo;
	}

	public String getRiesgoReputacionalOperacionalSi() {
		return riesgoReputacionalOperacionalSi;
	}

	public void setRiesgoReputacionalOperacionalSi(String riesgoReputacionalOperacionalSi) {
		this.riesgoReputacionalOperacionalSi = riesgoReputacionalOperacionalSi;
	}

	public String getRiesgoReputacionalOperacionalNo() {
		return riesgoReputacionalOperacionalNo;
	}

	public void setRiesgoReputacionalOperacionalNo(String riesgoReputacionalOperacionalNo) {
		this.riesgoReputacionalOperacionalNo = riesgoReputacionalOperacionalNo;
	}

	public String getDescBienServicioOfrece() {
		return descBienServicioOfrece;
	}

	public void setDescBienServicioOfrece(String descBienServicioOfrece) {
		this.descBienServicioOfrece = descBienServicioOfrece;
	}

	public String getTerritorioNacionalSi() {
		return territorioNacionalSi;
	}

	public void setTerritorioNacionalSi(String territorioNacionalSi) {
		this.territorioNacionalSi = territorioNacionalSi;
	}

	public String getTerritorioNacionalNo() {
		return territorioNacionalNo;
	}

	public void setTerritorioNacionalNo(String territorioNacionalNo) {
		this.territorioNacionalNo = territorioNacionalNo;
	}

	public String getTerritorioNacionalEspecificacion() {
		return territorioNacionalEspecificacion;
	}

	public void setTerritorioNacionalEspecificacion(String territorioNacionalEspecificacion) {
		this.territorioNacionalEspecificacion = territorioNacionalEspecificacion;
	}

	public String getEnNombreDeUnTerceroSi() {
		return enNombreDeUnTerceroSi;
	}

	public void setEnNombreDeUnTerceroSi(String enNombreDeUnTerceroSi) {
		this.enNombreDeUnTerceroSi = enNombreDeUnTerceroSi;
	}

	public String getEnNombreDeUnTerceroNo() {
		return enNombreDeUnTerceroNo;
	}

	public void setEnNombreDeUnTerceroNo(String enNombreDeUnTerceroNo) {
		this.enNombreDeUnTerceroNo = enNombreDeUnTerceroNo;
	}

	public String getEnNombreDeUnTerceroEspecificacion() {
		return enNombreDeUnTerceroEspecificacion;
	}

	public void setEnNombreDeUnTerceroEspecificacion(String enNombreDeUnTerceroEspecificacion) {
		this.enNombreDeUnTerceroEspecificacion = enNombreDeUnTerceroEspecificacion;
	}

	public String getAntiguedadAnio() {
		return antiguedadAnio;
	}

	public void setAntiguedadAnio(String antiguedadAnio) {
		this.antiguedadAnio = antiguedadAnio;
	}

	public String getAntiguedadMeses() {
		return antiguedadMeses;
	}

	public void setAntiguedadMeses(String antiguedadMeses) {
		this.antiguedadMeses = antiguedadMeses;
	}


	public String getIsMostrarMsjNoGrupo() {
		return isMostrarMsjNoGrupo;
	}

	public void setIsMostrarMsjNoGrupo(String isMostrarMsjNoGrupo) {
		this.isMostrarMsjNoGrupo = isMostrarMsjNoGrupo;
	}
	
		
	//----- Setters & Getters: Mujer PyME Banorte
	public String getMujerPyME() {
		return mujerPyME;
	}

	public void setMujerPyME(String mujerPyME) {
		this.mujerPyME = mujerPyME;
	}

	public String getEsCuentaMujerPyME() {
		return esCuentaMujerPyME;
	}

	public void setEsCuentaMujerPyME(String esCuentaMujerPyME) {
		this.esCuentaMujerPyME = esCuentaMujerPyME;
	}

	public String getCuentaMujerPyMEValidada() {
		return cuentaMujerPyMEValidada;
	}

	public void setCuentaMujerPyMEValidada(String cuentaMujerPyMEValidada) {
		this.cuentaMujerPyMEValidada = cuentaMujerPyMEValidada;
	}

	public String getNoEsCuentaMujerPyMEValidada() {
		return noEsCuentaMujerPyMEValidada;
	}

	public void setNoEsCuentaMujerPyMEValidada(String noEsCuentaMujerPyMEValidada) {
		this.noEsCuentaMujerPyMEValidada = noEsCuentaMujerPyMEValidada;
	}

	public String getIsMsgMujerPyME() {
		return isMsgMujerPyME;
	}

	public void setIsMsgMujerPyME(String isMsgMujerPyME) {
		this.isMsgMujerPyME = isMsgMujerPyME;
	}

	public String getDesactivarMujerPyME() {
		return desactivarMujerPyME;
	}

	public void setDesactivarMujerPyME(String desactivarMujerPyME) {
		this.desactivarMujerPyME = desactivarMujerPyME;
	}

	public String getListaCuentaMujerPyMEVacia() {
		if(listaClientesMujerPyME==null || listaClientesMujerPyME.isEmpty()){
			return "true"; // si no hay elementos en la lista de altamira regresa a la vista TRUE en listaCuentaMujerPyMEVacia
		}else{
			return "false";			
		}
		//return listaCuentaMujerPyMEVacia;
	}

	public void setListaCuentaMujerPyMEVacia(String listaCuentaMujerPyMEVacia) {
		this.listaCuentaMujerPyMEVacia = listaCuentaMujerPyMEVacia;
	}

	public String getEsCuentaMujerPyMEDlls() {
		return esCuentaMujerPyMEDlls;
	}

	public void setEsCuentaMujerPyMEDlls(String esCuentaMujerPyMEDlls) {
		this.esCuentaMujerPyMEDlls = esCuentaMujerPyMEDlls;
	}

	public String getCuentaMujerPyMEValidadaDlls() {
		return cuentaMujerPyMEValidadaDlls;
	}

	public void setCuentaMujerPyMEValidadaDlls(String cuentaMujerPyMEValidadaDlls) {
		this.cuentaMujerPyMEValidadaDlls = cuentaMujerPyMEValidadaDlls;
	}

	public String getNoEsCuentaMujerPyMEValidadaDlls() {
		return noEsCuentaMujerPyMEValidadaDlls;
	}

	public void setNoEsCuentaMujerPyMEValidadaDlls(String noEsCuentaMujerPyMEValidadaDlls) {
		this.noEsCuentaMujerPyMEValidadaDlls = noEsCuentaMujerPyMEValidadaDlls;
	}

	public String getEsClienteMujerPyME() {
		return esClienteMujerPyME;
	}

	public void setEsClienteMujerPyME(String esClienteMujerPyME) {
		this.esClienteMujerPyME = esClienteMujerPyME;
	}

	public String getClienteMujerPyMEValidado() {
		return clienteMujerPyMEValidado;
	}

	public void setClienteMujerPyMEValidado(String clienteMujerPyMEValidado) {
		this.clienteMujerPyMEValidado = clienteMujerPyMEValidado;
	}

	public String getNoEsClienteMujerPyMEValidado() {
		return noEsClienteMujerPyMEValidado;
	}

	public void setNoEsClienteMujerPyMEValidado(String noEsClienteMujerPyMEValidado) {
		this.noEsClienteMujerPyMEValidado = noEsClienteMujerPyMEValidado;
	}
	
}
