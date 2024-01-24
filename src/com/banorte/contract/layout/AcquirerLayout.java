
package com.banorte.contract.layout;

import com.banorte.contract.model.Affiliation;
import com.banorte.contract.model.Attribute;
import com.banorte.contract.model.Client;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.Employee;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.AttrConstants;
import com.banorte.contract.util.CityStateTaxRate;
import com.banorte.contract.util.EncryptBd;
import com.banorte.contract.util.Formatter;
import com.banorte.contract.util.HeaderConstants;
import com.banorte.contract.util.PlanType;
import com.banorte.contract.web.EjbInstanceManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.banorte.contract.model.Plan;
import com.banorte.contract.business.AttributeRemote;
import com.banorte.contract.business.CommisionPlanRemote;
import com.banorte.contract.business.EmployeeRemote;
import com.banorte.contract.business.PlanRemote;;

/**
 *
 * @author Darvy Arch
 */
public class AcquirerLayout extends LayoutOperationsTemplate implements Layout {

    static Logger logger = Logger.getLogger(LayoutOperations.class.getName());
    private final Integer PRODUCTTYPEID = 3;    
    private final String FILLS[] = {"reference", "celebrationplace", "celebrationstate","celebrationdate",                                   
                                    "branchname", "crnumber", "branchstreet",
                                    "officerempnumber","officernameComplete", "branchphone", "branchfax",
                                    "officerposition", "comments","officerrepnameComplete1","officerrepnameComplete2",
                                    "officerrepposition_1", "officerrepposition_2","officernameComplete",
                                    "legalrepresentative_nameComplete1","legalrepresentative_nameComplete2",
                                    "legalrepresentative_rfc_1","legalrepresentative_rfc_2",
                                    "legalrepresentative_email_1","legalrepresentative_email_2",
                                    "clientcontact_nameComplete1","clientcontact_email1","clientcontact_phoneComplete1",
                                    "client_fsname","client_fsstreet","client_fsnum",
                                    "client_fscolony","client_fszipcode","client_fscity",
                                    "client_fsstate","client_fsemail","client_fsphone",
                                    "client_fsphoneext","client_fsfax","client_fsfaxext",
                                    "client_fsrfc","affiliation_duedate","client_merchantname",
                                    "client_street","client_numext","client_numint",
                                    "client_colony","client_zipcode","client_city",
                                    "client_state","client_email","client_areacode",
                                    "client_phone","client_fiscalname","client_rfc","client_site",
                                    "client_categorycode","client_sic","officerebankingnameComplete","officerebankingempnumber","branchcounty",
                                    ApplicationConstants.FIELDNAME_CHARGETYPE,ApplicationConstants.FIELDNAME_ALLIANCE,"affiliation_otherComercialPlan" , 
                                    "affiliation_tiempoaire", "affiliation_telcel", "affiliation_movistar","affiliation_iusacell", "affiliation_nextel", // estos campos deben estar en la tabla affiliation y  no solo como atributos, pero hay que hacer pruebas porque ya tiene demasiados campos esa tabla y ya llego al tope, mientras solo se tomaran como atributos
                                    AttrConstants.AFFILIATION_TPV_PAYROLL,AttrConstants.TPV_NUMBER_EMPLOYEE,AttrConstants.TPV_PENALTY,AttrConstants.TPV_PENALTY_DLL,
                                    AttrConstants.AFFILIATION_INTEGRATION,AttrConstants.AFFILIATION_RENTADOLAR,
                                    "aff_cashback","aff_impulsoCaptacion",
                                    "aff_commCbChrg",
                                    "aff_commCbPymt", //agrego para cashback
                                    //contrato unico ixe
                                    "aff_exentDep","amex", "tpvUnattended", "aggregator","adminComplete",
                                    "adminPhone","adminEmail","transConciliation", "promNoteRqst",  "promNoteRqstName1", "promNoteRqstFLastName1", 
                                    "promNoteRqstMLastName1", "promNoteRqstEmail1", "promNoteRqstPhone1", "promNoteRqstComplete1", "promNoteRqstName2", 
                                    "promNoteRqstFLastName2", "promNoteRqstMLastName2", "promNoteRqstEmail2","promNoteRqstPhone2", "promNoteRqstComplete2",
                                    "clientcontact_nameComplete2","clientcontact_email2","clientcontact_phoneComplete2", "dcc", "red",
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
  							      "transactionFeeCyberMn",
  							      "limitedCoverageCyberMn",
  							      "wideCoverageCyberMn",
  							      "mujerPyME"
                                    };
    
    private PlanRemote planBean;
    private AttributeRemote attributeBean;
    private EmployeeRemote employeeBean;
    
    public AcquirerLayout(List<Contract> search) {

    	if (planBean== null) {
        	planBean = (PlanRemote) EjbInstanceManager.getEJB(PlanRemote.class);
        }   
    	
    	if(attributeBean ==null){
    		attributeBean = (AttributeRemote)EjbInstanceManager.getEJB(AttributeRemote.class);
    	}
    	if(employeeBean==null){
    		employeeBean = (EmployeeRemote)EjbInstanceManager.getEJB(EmployeeRemote.class);
    	}

    	for ( Contract record : search ) {
            if( record.getProduct().getProductTypeid().getProductTypeid().intValue() == PRODUCTTYPEID ) {
                this.setContracts(record);
            }
        }
         
    }
    
    public boolean hasElements() {

        if( this.getContracts().size() > 0 ) { 
            return true;
        } else
            return false;
        
    }
    
    
    private Boolean isMicro(Map<String, String> map){
    	String alliance = map.get(ApplicationConstants.FIELDNAME_ALLIANCE);
    	Boolean resultado = Boolean.FALSE;
    	
    	if(alliance.equals(ApplicationConstants.ALLIANCE_MICROS)){
			resultado = Boolean.TRUE;
		}
    	return resultado;
    }
    private Boolean isNetpay(Map<String, String> map){
    	String alliance = map.get(ApplicationConstants.FIELDNAME_ALLIANCE);
    	Boolean resultado = Boolean.FALSE;
    	
    	if(alliance.equals(ApplicationConstants.ALLIANCE_NETPAY)){
			resultado = Boolean.TRUE;
		}
    	return resultado;
    }
    
    private Boolean isCybersource(Map<String, String> map){
    	String alliance = map.get(ApplicationConstants.FIELDNAME_ALLIANCE);
    	Boolean resultado = Boolean.FALSE;
    	
    	if(alliance.equals(ApplicationConstants.ALLIANCE_CYBERSOURCE)){
			resultado = Boolean.TRUE;
		}
    	return resultado;
    }
    
    private Boolean isPlanTransactionMicros(Map<String, String> map){
    	String chargeType = map.get(ApplicationConstants.FIELDNAME_CHARGETYPE);
    	Boolean resultado = Boolean.FALSE;
    	
    	if(chargeType.equals(ApplicationConstants.MICROS_CHARGE_TYPE_TRANS)){
			resultado = Boolean.TRUE;
		}
    	return resultado;
    }
    private Boolean isPlanTransactionNetpay(Map<String, String> map){
    	String chargeType = map.get(ApplicationConstants.FIELDNAME_CHARGETYPE);
    	Boolean resultado = Boolean.FALSE;
    	
    	if(chargeType.equals(ApplicationConstants.NETPAY_CHARGE_TYPE_TRANS)){
			resultado = Boolean.TRUE;
		}
    	return resultado;
    }
    
    private Boolean isPlanTransactionCybersource(Map<String, String> map){
    	String chargeType = map.get(ApplicationConstants.FIELDNAME_CHARGETYPE);
    	Boolean resultado = Boolean.FALSE;
    	
    	if(chargeType.equals(ApplicationConstants.CYBER_CHARGE_TYPE_TRANS)){
			resultado = Boolean.TRUE;
		}
    	return resultado;
    }
    
    // metodo para el excel adquirenteLayout
    public List<String> getContent(Contract contract) throws Exception{
    	Employee employee						= new Employee();
        List<String> list 						= new ArrayList();
        Map<String, String> map 				= new LayoutTempleteContract().bindFrom(contract, FILLS);
        Client client 							= contract.getClient();   // client        
        Collection<Affiliation> affiliation		= contract.getAffiliationCollection();    // affiliation
        String accMn							= ApplicationConstants.EMPTY_STRING;
        String accDlls							= ApplicationConstants.EMPTY_STRING;
        EncryptBd  decrypt						= new EncryptBd();
        Affiliation affi 						= null;
        boolean isMicro 						= Boolean.FALSE;
        boolean isNetpay 						= Boolean.FALSE;
        boolean isCybersource					= Boolean.FALSE;
        String capturePlan						= ApplicationConstants.EMPTY_STRING;
        String currency							= ApplicationConstants.EMPTY_STRING;
        String penalty_mn						= ApplicationConstants.EMPTY_STRING;
        String penalty_dlls						= ApplicationConstants.EMPTY_STRING;

        if ( affiliation.size() > 0) {
            affi = affiliation.iterator().next();
        } else {
            affi = new Affiliation();
        }
        
        if (affi.getAccountnumbermn()!=null && affi.getAccountnumbermn().trim().length()>0){
            accMn="";            
            accMn=decrypt.decrypt(affi.getAccountnumbermn());
        }
        
        if (affi.getAccountnumberdlls()!=null && affi.getAccountnumberdlls().trim().length()>0){
            accDlls="";            
            accDlls=decrypt.decrypt(affi.getAccountnumberdlls());                      
        }

        isMicro = this.isMicro(map);
        isNetpay = this.isNetpay(map);
        isCybersource = this.isCybersource(map);
        currency = affi.getCurrency();
        
        list.add(contract.getReference());      //	Numero de Folio
        list.add(map.get("celebrationplace") + ", " + map.get("celebrationstate") + ", " + map.get("celebrationdate")); //  Lugar y Fecha de Celebracion
        list.add(Formatter.fixtoLenght(map.get("client_merchantname"), 24));  //  Nombre Comercial
//      list.add(map.get("client_street") + " " + (map.get("client_numext")!=null?"NO " + map.get("client_numext"):" ") + " " + (map.get("client_numint")!=null?"L " + map.get("client_numint"):" "));  //  Calle Y numero
        String calle = map.get("client_street");
        String numeroE = map.get("client_numext");
        if(numeroE!=null && !numeroE.isEmpty()){
        	numeroE = " NO "+ numeroE;
        }else{
        	numeroE="";
        }
//        String numeroE = map.get("client_numext")!=null?" NO "+map.get("client_numext"):" ";
        String numeroI = map.get("client_numint");
        if(numeroI!=null && !numeroI.isEmpty()){
        	numeroI=" L "+numeroI;
        }else{
        	numeroI="";
        }
        String dire = calle+numeroE+numeroI;
//        dire=Formatter.fixtoLenght(dire, 25);
        list.add(dire);
        
        list.add(map.get("client_colony"));  //  Colonia
        list.add(map.get("client_zipcode"));//  C.P.
        list.add(map.get("client_city"));  //  Ciudad
        list.add(map.get("client_state"));  //  Estado
        list.add(map.get("client_email"));  //  Correo electronico
        list.add(map.get("client_areacode")); // Lada
        list.add(map.get("client_phone"));  //  Telefono
        list.add(map.get("client_fiscalname"));  //  Nombre de la RazÃ³n Social
        list.add(map.get("client_rfc"));  //  RFC        
        list.add(affi.getAccountnumbermn()!= null? Formatter.fixLenght(accMn,10):" ");  //  Cuenta Concentradora (mxp)
        list.add(affi.getAccountnumberdlls()!=null? Formatter.fixLenght(accDlls,10): " ");  //  Cuenta Concentradora (dlls)
        
        CityStateTaxRate.getInstance();
        boolean taxrate = CityStateTaxRate.findInList(client.getCity(), client.getState());
        
        list.add(taxrate?"11":"16");  //  IVA
        if(map.get("client_categorycode")!=null && map.get("client_categorycode").length()>7){
            list.add(Formatter.subString(map.get("client_categorycode"), 0, 6));  //  NÂ° de Giro
            list.add(Formatter.subString(map.get("client_categorycode"), 7));  //  DescripciÃ³n
        }else{        
            list.add(map.get("client_categorycode"));  //  NÂ° de Giro
            list.add(map.get("client_categorycode"));  //  DescripciÃ³n
        }
            
            
        
        list.add(Formatter.fixToString(Formatter.fixLenght(map.get("client_sic"),8)));  //  SIC (Cliente Altamira)
        list.add(map.get("client_site"));  //  Sitio o tienda virtual    
        

        list.add(map.get("legalrepresentative_nameComplete1")); // Nombre (s) 1 y Apellidos Apoderado Comercio
        list.add(map.get("legalrepresentative_rfc_1")); // RFC Apoderado Comercio
        list.add(map.get("legalrepresentative_email_1")); // Correo Electronico Apoderado Comercio
        
        list.add(map.get("legalrepresentative_nameComplete2")); // Nombre (s) 1 y Apellidos Apoderado Comercio
        list.add(map.get("legalrepresentative_rfc_2")); // RFC Apoderado Comercio
        list.add(map.get("legalrepresentative_email_2")); // Correo Electronico Apoderado Comercio        
        
//
//        list.add(affi.getOpenkey() == 1  ? "Si": "No"); // Teclado Abierto
//        list.add(affi.getForceauth() == 1 ? "Si": "No"); // Venta Forzada
//        list.add(affi.getQps() == 1 ? "Si": "No"); // QPS
        
        list.add(map.get("officerrepnameComplete1")); // Nombre (s) 1 y Apellidos
        list.add(map.get("officerrepposition_1")); // Puesto        
        list.add(map.get("officerrepnameComplete2")); // Nombre (s) 2 y Apellidos
        list.add(map.get("officerrepposition_2")); // Puesto

        list.add(affi.getNumaffilmn());  //  AfiliaciÃ³n Asignada (mxp)
        list.add(affi.getNumaffildlls());  //  AfiliaciÃ³n Asignada (dll)* solo tpv dolares
        list.add(map.get("branchname"));  //  Nombre Sucursal
        list.add(map.get("crnumber"));  //  C.R.
        list.add(map.get("branchstreet"));  //  Domicilio Sucursal
        list.add(map.get("branchphone"));  //  Telefono
        list.add(map.get("branchfax"));  //  Fax
        
        String numColoco = map.get("officerempnumber"); //variable para sacar el territorio y region
        list.add(numColoco);  //  NÂ° de empleado (nuevo) colocacion

        list.add(map.get("officernameComplete")); //Nombre del empleado colocacion
        list.add(map.get("officerposition"));  //  Puesto
        list.add(map.get("officerebankingempnumber"));  //  NÂ° de empleado  Ebanking(nuevo)
        list.add(map.get("officerebankingnameComplete")); //Nombre del empleado Ebanking 
        list.add(map.get("client_fsname"));  //  Nombre, DenominaciÃ³n o RazÃ³n Social (Fiador)
        
        String calleFiador = map.get("client_fsstreet")!=null?map.get("client_fsstreet"):""; //calle fiador
        String numFiador = map.get("client_fsnum")!=null?map.get("client_fsnum"):"";//num fiador
        list.add(calleFiador+" "+numFiador);  //  Calle y Numero fiador
        
        list.add(map.get("client_fscolony"));  //  Colonia
        list.add(map.get("client_fszipcode"));  //  C.P.
        list.add(map.get("client_fscity"));  //  Ciudad
        list.add(map.get("client_fsstate"));  //  Estado
        list.add(map.get("client_fsemail"));  //  Correo electronico
        list.add(map.get("client_fsphone"));  //  Telefono
        list.add(map.get("client_fsphoneext"));  //  ExtenciÃ³n (nuevo)
        list.add(map.get("client_fsfax"));  //  Fax
        list.add(map.get("client_fsfaxext"));  //  ExtenciÃ³n (nuevo)
        list.add(map.get("client_fsrfc"));  //  RFC        
                
        list.add(map.get("clientcontact_nameComplete1")); // Nombre Contacto Herramienta
        list.add(map.get("clientcontact_email1")); // Correo Elecrronico contacto Herramienta
        list.add(map.get("clientcontact_phoneComplete1")); // Telefono Contacto Herramienta
        
        
        list.add(affi.getDepositcompany()); // affiliation_officerdepositexent COMPANIA FIANZA
        list.add(affi.getOfficerdepositexent()); // affiliation_officerdepositexent QUIEN EXENTA FIANZA
        list.add(Formatter.fixToString(affi.getDepositamount())); // affiliation_depositamount MONTO FIANZA
        list.add(map.get("affiliation_duedate")); // affiliation_duedate VIGENCIA FIANZA
        list.add(map.get("comments"));  //  para escribir las observaciones
        list.add(affi.getCurrency()); // affiliation_currency - divisa
        list.add(affi.getModality()); // affiliation_modality - modalidad
        
        // Accion para validar si es TPV Nomina.
        String tpvNomina = map.get(AttrConstants.AFFILIATION_TPV_PAYROLL);
        String planSubNomina = ApplicationConstants.EMPTY_STRING;
        
        if(tpvNomina.equals(ApplicationConstants.VALUE_TRUE)){
        	planSubNomina = ApplicationConstants.PLAN_TPV_NOMINA_SUB;
		}
        //Obtener la descripcion del plan comercial, si es que existe en el catagolo
        if(Formatter.isNumeric(affi.getCommercialplan())){
        	try{
        		if(affi.getCommercialplan().equals(PlanType.RENTABILIDAD.value().toString())){
               		list.add("Estudio de Rentabilidad"); 
               		capturePlan = ApplicationConstants.CAPTURA_PLAN_MANUAL;
        		}else if(affi.getCommercialplan().equals(PlanType.OTRO.value().toString())){
               		//list.add("Otro"); 
               		list.add(map.get("affiliation_otherComercialPlan"));
               		capturePlan = ApplicationConstants.CAPTURA_PLAN_MANUAL;
        		}else{
                	Plan planEntity = null;
	            	planEntity = planBean.findById(Integer.parseInt(affi.getCommercialplan()));
	            	capturePlan = ApplicationConstants.CAPTURA_PLAN_AUTOMATICO;
	
	            	if(planEntity != null){
	            		String plan = planEntity.getName();
	            		String chargeType = map.get("chargeType");
	            		
	            		if(plan.equals(ApplicationConstants.PLAN_BANORTE0_15))
	            			plan = "Banorte0";
	            		else if(plan.equals(ApplicationConstants.PLAN_15_15))
	            			plan = "Plan 15";
	            		else if(plan.equals(ApplicationConstants.PLAN_35_15))
	            			plan = "Plan 35";
	            		else if(plan.equals(ApplicationConstants.PLAN_75_15))
	            			plan = "Plan 75";
	            		else if(plan.equals(ApplicationConstants.PLAN_200_15))
	            			plan = "Plan 200";
	            		else if(plan.equals(ApplicationConstants.PLAN_IXE0_15))
	            			plan = "IXE0";
	            		else if(plan.equals(ApplicationConstants.PLAN_IXE015_15))
	            			plan = "IXE 015";
	            		else if(plan.equals(ApplicationConstants.PLAN_IXE035_15))
	            			plan = "IXE 035";
	            		else if(plan.equals(ApplicationConstants.PLAN_IXE075_15))
	            			plan = "IXE 075";
	            		else if(plan.equals(ApplicationConstants.PLAN_IXE200_15))
	            			plan = "IXE 200";
	            		else if(plan.equals(ApplicationConstants.PLAN_EST_RENT_15) || plan.equals(ApplicationConstants.PLAN_OTRO_15))
	            			plan = "EST RENT";
	            		
	            		
	            		if(isMicro){
	            			if(chargeType.equals(ApplicationConstants.MICROS_CHARGE_TYPE_TASA)){
	            				plan = plan + ApplicationConstants.PLAN_TASA15_SUB;
	           				}else if(chargeType.equals(ApplicationConstants.MICROS_CHARGE_TYPE_TRANS)){
	           					plan = plan + ApplicationConstants.PLAN_TRANSACTION_SUB;
	           				}
	           			}else if(isNetpay){
	           				if(chargeType.equals(ApplicationConstants.NETPAY_CHARGE_TYPE_TASA)){
	           					if(affi.getCommercialplan().equals(ApplicationConstants.COMERCIALPLAN10)){
	           						plan = plan + ApplicationConstants.PLAN_TASA20_SUB;
	           					}else if(affi.getCommercialplan().equals(ApplicationConstants.COMERCIALPLAN30)){
	           						plan = plan + ApplicationConstants.PLAN_TASA15_SUB;	
	           					}else if(affi.getCommercialplan().equals(ApplicationConstants.COMERCIALPLAN70)){
	           						plan = plan + ApplicationConstants.PLAN_TASA15_SUB;	
	           					}else if(affi.getCommercialplan().equals(ApplicationConstants.COMERCIALPLAN150)){
	           						plan = plan + ApplicationConstants.PLAN_TASA10_SUB;	
	           					}/*else if(affi.getCommercialplan().equals(ApplicationConstants.COMERCIALPLAN500)){
	           						plan = plan + ApplicationConstants.PLAN_TASA10_SUB;	
	           					}*/
	           				}else if(chargeType.equals(ApplicationConstants.NETPAY_CHARGE_TYPE_TRANS)){
	           					plan = plan + ApplicationConstants.PLAN_TRANSACTION_SUB;
	           				}else if(chargeType.equals(ApplicationConstants.NETPAY_CHARGE_TYPE_EQ)){
	           					plan = plan + ApplicationConstants.PLAN_EQ_RENT_SUB;
	           				}
	           			}
	            		
	            		if("SLP 2014".equalsIgnoreCase(chargeType)){
           					plan = plan + " SLP";
           				}else if("OCCIDENTE 2016".equalsIgnoreCase(chargeType)){
           					plan = plan + " OCDT";
           				}else if("RCRC".equalsIgnoreCase(chargeType)){
           					plan = plan + " RCRC";
           				}
	            		list.add(plan + planSubNomina); // affiliation_comercialplan
	            	}else{
	            		list.add(affi.getCommercialplan() + planSubNomina); // affiliation_comercialplan
	            	}
        		}
        	}
        	catch(Exception e){
           		list.add(affi.getCommercialplan() + planSubNomina); // affiliation_comercialplan
        	}
        }else{
            list.add(affi.getCommercialplan() + planSubNomina); // affiliation_comercialplan
        }
        
        list.add(affi.getInternalcredithistory()); // affiliation_internalcredithistory
        list.add(affi.getExternalcredithistory()); // affiliation_externalcredithistory
        
        list.add(affi.getServicetype());                           // affiliation_servicetype
        
        list.add(affi.getQuantmanual() != null ? affi.getQuantmanual()==1?"Si":"No" : "No"); // affiliation_quantmanual
        list.add(affi.getNumaffilmn() != null ? affi.getNumaffilmn() : ""); //afiliacion pesos
        list.add(affi.getNumaffildlls() != null ? affi.getNumaffildlls() : ""); //afiliacion dolares
        list.add(affi.getCurrentbank()); // affiliation_currentbank
        list.add(affi.getSoluciontype());
        list.add( "Cargo Recurrente".equals(affi.getProductdesc()) ? "Cargo Periódico" : affi.getProductdesc()); 
      //EQUIPOS
        String red = map.get("red");
        Boolean old=false;
        if(red.isEmpty()||red==null){
        	old=true;
        }
        list.add(old?Formatter.fixToString(affi.getQuantgprs()):"0"); // affiliation_quantgprs -  (se inhabilita el campo)
        list.add(old?(affi.getPlangprs()!=null?affi.getPlangprs().equals("true")?"Si":"No":""):"NA");   //  Plan Banorte -  (se inhabilita el campo) 
        list.add(old?Formatter.fixToString(affi.getQuantwifi()):"0"); // affiliation_quantwifi -  (se inhabilita el campo)
        list.add(old?(affi.getPlanwifi()!=null?affi.getPlanwifi().equals("true")?"Si":"No":""):"NA");   //  Plan Banorte -  (se inhabilita el campo)
        list.add(old?Formatter.fixToString(affi.getQuantlan()):"0"); // affiliation_quantlan - (se inhabilita el campo)
        list.add(old?Formatter.fixToString(affi.getQuantpinpad()):"0"); // affiliation_quantpinpad -  (se inhabilita el campo)
        list.add(old?Formatter.fixToString(affi.getQuantdialup()):"0"); // affiliation_quantdialup -  (se inhabilita el campo)
        list.add(old?Formatter.fixToString(affi.getQuantblue()):"0"); // affiliation_quantbluetoth -  (se inhabilita el campo)
        list.add(Formatter.fixToString(affi.getMposMonthlyRate())); //cantidad renta mensual
        list.add(Formatter.fixToString(affi.getMposRateByEquipment())); //cantidad costo por equipo
        list.add(""); //"Compartir AMEX"
        list.add(""); //"Modelo Compartir AMEX"
        list.add(""); //"Es una nueva afiliacion AMEX"
        list.add(""); //"No. Afiliacion AMEX"
        
        list.add(Formatter.fixToString(affi.getAvcommisiontcmn())); // affiliation_avcommisiontcmn
        list.add(Formatter.fixToString(affi.getAvcommisiontcdlls())); // affiliation_avcommisiontcdlls
        list.add(Formatter.fixToString(affi.getAvcommisiontdmn())); // affiliation_avcommisiontdmn
        list.add(Formatter.fixToString(affi.getAvcommisiontddlls())); // affiliation_avcommisiontddlls
        
        double affiliation_commisiontcmn= affi.getCommisiontcmn() == null ? 0.0 :affi.getCommisiontcmn();
        double affiliation_commisiontcdlls = affi.getCommisiontcdlls() == null ? 0.0 : affi.getCommisiontcdlls();
        double affiliation_commisiontdmn = affi.getCommisiontdmn() == null ? 0.0 : affi.getCommisiontdmn();
        double affiliation_commisiontddlls = affi.getCommisiontddlls() == null ? 0.0 : affi.getCommisiontddlls();
        double affiliation_commisionintnlmn = affi.getCommisionintnlmn() == null ? 0.0 : affi.getCommisionintnlmn();
        double affiliation_commisionintnldlls = affi.getCommisionintnldlls() == null ? 0.0 : affi.getCommisionintnldlls();
        
        /// ***************** DUDA***************  PORQUE SE SUMA EN EL LAYOUT Y NO EN EL MB???? en la pagina unicamente se altera el campo otros conceptos. es necesario agregarle algo para el paquete de renta de equipos?
       /// ***************** DUDA***************  PORQUE TOMA LOS VALORES DE ATTRIBUTE Y NO DE CONSTANTES?  CHECAR LOS VALORES QUE ESTOY PONIENDO PARA NETPAY A VER SI CORRESPONDEN A LOS QUE DEBEN SUMARSE
        // Sumarle el valor de Micro, cuando es una Transaccion.
        if(isMicro){
        	if(isPlanTransactionMicros(map)){
        		Attribute att =  attributeBean.findByFieldname(ApplicationConstants.ATT_TRANSACTION_PESOS);
        		double valorTransactionMN = Double.valueOf( att.getName()) ;
        		att =  attributeBean.findByFieldname(ApplicationConstants.ATT_TRANSACTION_DOLLAR);
        		double valorTransactionDLL = Double.valueOf( att.getName()) ;
        		
        		
        		if(currency.equals(ApplicationConstants.CURRENCY_PESOS)){
        			affiliation_commisiontcmn = affiliation_commisiontcmn + valorTransactionMN;
            		affiliation_commisiontdmn = affiliation_commisiontdmn + valorTransactionMN;
            		affiliation_commisionintnlmn = affiliation_commisionintnlmn + valorTransactionMN;
        		}else if(currency.equals(ApplicationConstants.CURRENCY_DOLLAR)){
        			affiliation_commisiontcdlls = affiliation_commisiontcdlls + valorTransactionDLL;
            		affiliation_commisiontddlls = affiliation_commisiontddlls + valorTransactionDLL;
        			affiliation_commisionintnldlls = affiliation_commisionintnldlls + valorTransactionDLL;
        		}else if(currency.equals(ApplicationConstants.CURRENCY_AMBOS)){
        			affiliation_commisiontcmn = affiliation_commisiontcmn + valorTransactionMN;
            		affiliation_commisiontcdlls = affiliation_commisiontcdlls + valorTransactionDLL;
            		affiliation_commisiontdmn = affiliation_commisiontdmn + valorTransactionMN;
            		affiliation_commisiontddlls = affiliation_commisiontddlls + valorTransactionDLL;
            		affiliation_commisionintnlmn = affiliation_commisionintnlmn + valorTransactionMN;
            		affiliation_commisionintnldlls = affiliation_commisionintnldlls + valorTransactionDLL;
        		}	
        	}
        }else if(isNetpay){
        	if(isPlanTransactionNetpay(map)){
        		//Attribute att =  attributeBean.findByFieldname(ApplicationConstants.ATT_TRANSACTION_PESOS);
        		double valorTransactionMN = Double.valueOf( ApplicationConstants.NETPAY_DIVISA_PESOS_TRANS) ;
        		//att =  attributeBean.findByFieldname(ApplicationConstants.ATT_TRANSACTION_DOLLAR);
        		double valorTransactionDLL = Double.valueOf(ApplicationConstants.NETPAY_DIVISA_DOLLAR_TRANS) ;
        		
        		if(currency.equals(ApplicationConstants.CURRENCY_PESOS)){
        			affiliation_commisiontcmn = affiliation_commisiontcmn + valorTransactionMN;
            		affiliation_commisiontdmn = affiliation_commisiontdmn + valorTransactionMN;
            		affiliation_commisionintnlmn = affiliation_commisionintnlmn + valorTransactionMN;
        		}else if(currency.equals(ApplicationConstants.CURRENCY_DOLLAR)){
        			affiliation_commisiontcdlls = affiliation_commisiontcdlls + valorTransactionDLL;
            		affiliation_commisiontddlls = affiliation_commisiontddlls + valorTransactionDLL;
            		affiliation_commisionintnldlls = affiliation_commisionintnldlls + valorTransactionDLL;
        		}else if(currency.equals(ApplicationConstants.CURRENCY_AMBOS)){
        			affiliation_commisiontcmn = affiliation_commisiontcmn + valorTransactionMN;
            		affiliation_commisiontcdlls = affiliation_commisiontcdlls + valorTransactionDLL;
            		affiliation_commisiontdmn = affiliation_commisiontdmn + valorTransactionMN;
            		affiliation_commisiontddlls = affiliation_commisiontddlls + valorTransactionDLL;
            		affiliation_commisionintnlmn = affiliation_commisionintnlmn + valorTransactionMN;
            		affiliation_commisionintnldlls = affiliation_commisionintnldlls + valorTransactionDLL;
        		}	
        	}
        	
        }else if(isCybersource){
        	if(isPlanTransactionCybersource(map)){
        		double valorTransactionMN = Double.valueOf(ApplicationConstants.CYBER_DIVISA_PESOS_TRANS) ;
        		double valorTransactionDLL = Double.valueOf(ApplicationConstants.CYBER_DIVISA_DOLLAR_TRANS) ;
            		
        		if(currency.equals(ApplicationConstants.CURRENCY_PESOS)){
        			affiliation_commisiontcmn = affiliation_commisiontcmn + valorTransactionMN;
            		affiliation_commisiontdmn = affiliation_commisiontdmn + valorTransactionMN;
            		affiliation_commisionintnlmn = affiliation_commisionintnlmn + valorTransactionMN;
        		}else if(currency.equals(ApplicationConstants.CURRENCY_DOLLAR)){
        			affiliation_commisiontcdlls = affiliation_commisiontcdlls + valorTransactionDLL;
            		affiliation_commisiontddlls = affiliation_commisiontddlls + valorTransactionDLL;
            		affiliation_commisionintnldlls = affiliation_commisionintnldlls + valorTransactionDLL;
        		}else if(currency.equals(ApplicationConstants.CURRENCY_AMBOS)){
        			affiliation_commisiontcmn = affiliation_commisiontcmn + valorTransactionMN;
            		affiliation_commisiontcdlls = affiliation_commisiontcdlls + valorTransactionDLL;
            		affiliation_commisiontdmn = affiliation_commisiontdmn + valorTransactionMN;
            		affiliation_commisiontddlls = affiliation_commisiontddlls + valorTransactionDLL;
            		affiliation_commisionintnlmn = affiliation_commisionintnlmn + valorTransactionMN;
            		affiliation_commisionintnldlls = affiliation_commisionintnldlls + valorTransactionDLL;
        		}	
        	}
        }
        
        list.add(Formatter.fixToString(affiliation_commisiontcmn)); // affiliation_commisiontcmn
        list.add(Formatter.fixToString(affiliation_commisiontcdlls)); // affiliation_commisiontcdlls
        list.add(Formatter.fixToString(affiliation_commisiontdmn)); // affiliation_commisiontdmn
        list.add(Formatter.fixToString(affiliation_commisiontddlls)); // affiliation_commisiontddlls
        
        list.add(Formatter.fixToString(affi.getAffiliationratemn())); // affiliation_affiliationratemn
        list.add(Formatter.fixToString(affi.getAffiliationratedlls())); // affiliation_affiliationratedlls
        list.add(Formatter.fixToString(affi.getTranscriptorratemn())); // affiliation_transcriptorratemn
        //list.add(Formatter.fixToString(affi.getTranscriptorratedlls())); // affiliation_transcriptorratedlls
        list.add(Formatter.fixToString(affi.getMonthlyratemn())); // affiliation_monthlyratemn
        list.add(Formatter.fixToString(affi.getMonthlyratedlls())); // affiliation_monthlyratedlls
        
        list.add(Formatter.fixToString(affi.getMonthlyrate3dsmn())); // affiliation_monthlyrate3dsmn
        list.add(Formatter.fixToString(affi.getMonthlyrate3dsdlls())); // affiliation_monthlyrate3dsdlls
        list.add(Formatter.fixToString(affi.getActivation3dsmn())); // affiliation_activation3dsmn
        list.add(Formatter.fixToString(affi.getActivation3dsdlls())); // affiliation_activation3dsdlls
        list.add(Formatter.fixToString(affi.getMonthlyinvoicingminmn())); // affiliation_monthlyinvoicingminmn
        list.add(Formatter.fixToString(affi.getMonthlyinvoicingmindlls())); // affiliation_monthlyinvoicingmindlls
        list.add(Formatter.fixToString(affi.getFailmonthlyinvoicingmn())); // affiliation_failmonthlyinvoicingmn
        list.add(Formatter.fixToString(affi.getFailmonthlyinvoicingdlls())); // affiliation_failmonthlyinvoicingdlls
        list.add(Formatter.fixToString(affi.getMinimiunbalancemn())); // affiliation_minimiunbalancemn
        list.add(Formatter.fixToString(affi.getMinimiunbalancedlls())); // affiliation_minimiunbalancedlls
        list.add(Formatter.fixToString(affi.getFailminimiunbalancemn())); // affiliation_failminimiunbalancemn
        list.add(Formatter.fixToString(affi.getFailminimiunbalancedlls())); // affiliation_failminimiunbalancedlls
        
        list.add(Formatter.fixToString(affi.getPromnotemn())); // affiliation_promnotemn
        list.add(Formatter.fixToString(affi.getPromnotedlls())); // affiliation_promnotedlls
        list.add(Formatter.fixToString(affi.getFailpromnotemn())); // affiliation_failpromnotemn
        list.add(Formatter.fixToString(affi.getFailpormnotedlls())); // affiliation_failpormnotedlls
        list.add(Formatter.fixToString(affi.getAvsmn())); // affiliation_avsmn
        list.add(Formatter.fixToString(affi.getAvsdlls())); // affiliation_avsdlls
        list.add(Formatter.fixToString(affi.getOtherconcept1des())==null?"0":Formatter.fixToString(affi.getOtherconcept1des()));//pidieron cero en caso de no haber descripcion en el nuevo contrato ixe
        list.add(Formatter.fixToString(affi.getOtherconcept1mn()));
        list.add(Formatter.fixToString(affi.getOtherconcept1dlls()));
        list.add(""); //
        list.add(""); //
        list.add(""); //
        
        String alianza = map.get("alliance")!=null?map.get("alliance"):"";
        if("Agregador".equalsIgnoreCase(alianza)){
        	alianza="AGREGA/PSP";
        }else if(ApplicationConstants.ALLIANCE_NETPAY.equalsIgnoreCase(alianza)){
        	alianza=ApplicationConstants.ALLIANCE_NETPAY_COMPLETE;
        }
        list.add(alianza); // Alianza (si no aplica o quedo deshabilitado el campo se queda en blanco)
        
        list.add(map.get("chargeType")); // Paquete
      //Tiempo Aire Netpay
        list.add(map.get("affiliation_tiempoaire")!=null&& map.get("affiliation_tiempoaire").equals(ApplicationConstants.VALUE_TRUE)?"SI":"NO"); // Tiempo Aire si o no 
        list.add(map.get("affiliation_telcel")!=null?map.get("affiliation_telcel"):""); //  affiliation_telcel - comision de tiempo aire para telcel
        list.add(map.get("affiliation_movistar")!=null?map.get("affiliation_movistar"):""); // affiliation_movistar - comision de tiempo aire para movistal
        list.add(map.get("affiliation_iusacell")!=null?map.get("affiliation_iusacell"):""); // affiliation_iusacell - comision de tiempo aire para iusacell
        list.add(map.get("affiliation_nextel")!=null?map.get("affiliation_nextel"):""); // affiliation_nextel - comision de tiempo aire para nextel
        
        //TPV NOMINA
        if(tpvNomina.equals(ApplicationConstants.VALUE_TRUE)){
        	list.add(ApplicationConstants.BOOLEAN_STRING_SI);
		}else{
			list.add(ApplicationConstants.BOOLEAN_STRING_NO);
		}
        
        list.add(map.get(AttrConstants.TPV_NUMBER_EMPLOYEE));
        
        if(currency.equals(ApplicationConstants.CURRENCY_PESOS)){
        	penalty_mn = map.get(AttrConstants.TPV_PENALTY);
        	penalty_dlls = ApplicationConstants.EMPTY_STRING;
		}else if(currency.equals(ApplicationConstants.CURRENCY_DOLLAR)){
			penalty_mn = ApplicationConstants.EMPTY_STRING;
			penalty_dlls = map.get(AttrConstants.TPV_PENALTY_DLL);
		}else if(currency.equals(ApplicationConstants.CURRENCY_AMBOS)){
			penalty_mn = map.get(AttrConstants.TPV_PENALTY);
			penalty_dlls = ApplicationConstants.EMPTY_STRING;
			
		}
        
        list.add(penalty_mn); 							//Penalizacion Pesos
        list.add(penalty_dlls);							//Penalizacion en Dollares
        list.add(capturePlan); 							//Captura de Esquema
        list.add(map.get(AttrConstants.AFFILIATION_INTEGRATION));
        list.add(map.get(AttrConstants.AFFILIATION_RENTADOLAR));
        
 //...PARA CASHBACK (get content)
//        System.out.println(map.get("aff_cashback"));
        list.add(map.get("aff_cashback") != null && map.get("aff_cashback").equals("X")?"SI":"NO"); // cashback
        
		String payment = map.get("aff_commCbPymt");
		//%p $p %c $c 
		if(payment!=null && !payment.isEmpty()){
			if(payment.startsWith("$")){
				list.add("0.0");
				list.add(payment);
			}else if(payment.endsWith("%")){
				list.add(payment);
				list.add("0.0");
			}
		}else{
			list.add(""); //contratos viejos que no tienen atributo de cashback
			list.add(""); 
		}
		String charge = map.get("aff_commCbChrg");
		if(charge!=null && !charge.isEmpty()){
			if(charge.startsWith("$")){
				list.add("0.0");
				list.add(charge);
			}else if(charge.endsWith("%")){
				list.add(charge);
				list.add("0.0");
			}
		}else{
			list.add("");// contratos viejos que no tienen atributo de cashback
			list.add(""); 
		}
		
		//NUEVOS CAMPOS DE CONTRATO UNICO IXE		
		list.add(affi.getHavedepositcompany()!=null && 0==affi.getHavedepositcompany()?"No":"Si");//requiere fianza
		list.add(affi.getExentDep()!=null && 0==affi.getExentDep()?"No":"Si");//exentar fianza
		list.add(affi.getOpenkey()!=null && "X".equalsIgnoreCase(affi.getOpenkey())?"Si":"No"); // teclado abierto
		list.add(affi.getForceauth()!=null && "X".equalsIgnoreCase(affi.getForceauth())?"Si":"No");//venta forzada
		list.add(affi.getQps()!=null && "X".equalsIgnoreCase(affi.getQps())?"Si":"No");//QPS
		list.add(map.get("amex")!=null && "X".equalsIgnoreCase(map.get("amex"))?"Si":"No");//amex
		list.add(map.get("tpvUnattended")!=null && "X".equalsIgnoreCase(map.get("tpvUnattended"))?"Si":"No");//TPV desatendida
		list.add(map.get("transConciliation")!=null && "X".equalsIgnoreCase(map.get("transConciliation"))?"Si":"No"); //Mï¿½dulo de Conciliaciï¿½n de Transacciones  transConciliation
		list.add(map.get("promNoteRqst")!=null && "X".equalsIgnoreCase(map.get("promNoteRqst"))?"Si":"No");//Mï¿½dulo de Solicitud de Pagarï¿½s promNoteRqst
		list.add(map.get("aff_impulsoCaptacion") != null && map.get("aff_impulsoCaptacion").equalsIgnoreCase("X")?"SI":"NO"); 
		list.add(map.get("adminComplete")!=null?map.get("adminComplete"):"");//Nombre Contacto Administrador 1
		list.add(map.get("adminEmail")!=null?map.get("adminEmail"):"");//email Administrador 1
		list.add(map.get("adminPhone")!=null?map.get("adminPhone"):"");//telefono Administrador 1
		list.add(map.get("promNoteRqstComplete1")!=null?map.get("promNoteRqstComplete1"):"");//Nombre Contacto Adicional Mï¿½dulo Peticiï¿½n Pagarï¿½s 1
		list.add(map.get("promNoteRqstEmail1")!=null?map.get("promNoteRqstEmail1"):"");
		list.add(map.get("promNoteRqstPhone1")!=null?map.get("promNoteRqstPhone1"):"");
		list.add(map.get("promNoteRqstComplete2")!=null?map.get("promNoteRqstComplete2"):"");
		list.add(map.get("promNoteRqstEmail2")!=null?map.get("promNoteRqstEmail2"):"");
		list.add(map.get("promNoteRqstPhone2")!=null?map.get("promNoteRqstPhone2"):"");
		if(numColoco!=null && !numColoco.isEmpty()){
			numColoco = numColoco.replaceFirst("A", "");
		}
		//employee = employeeBean.findByNumEmpleado(numColoco);
		if(employee!=null){
			list.add(employee.getRegion()!=null?employee.getRegion():"");//region del ejecutivo que coloca
			list.add(employee.getTerritorio()!=null?employee.getTerritorio():"");//territorio del ejecutivo que coloca
		}else{
			list.add("");
			list.add("");
		}
		list.add(map.get("clientcontact_nameComplete2")!=null?map.get("clientcontact_nameComplete2"):"");//nombre y apellidos desarrollador
		list.add(map.get("clientcontact_email2")!=null?map.get("clientcontact_email2"):"");//email desarrollador
		list.add(map.get("clientcontact_phoneComplete2")!=null?map.get("clientcontact_phoneComplete2"):"");//telefono y extension desarrollador
		list.add(map.get("mobilePymnt")!=null && "X".equalsIgnoreCase(map.get("mobilePymnt"))?"Si":"No");//pago movil
		//EQUIPOS
		list.add(!old?affi.getTpvTel()!=null?affi.getTpvTel().toString():"0":"0");//TPV con conexiï¿½n telefï¿½nica (Fija)
		list.add(!old?affi.getTpvMovil()!=null?affi.getTpvMovil().toString():"0":"0");//TPV con conexiï¿½n celular (Fija)
		list.add(!old?affi.getTpvInternet()!=null?affi.getTpvInternet().toString():"0":"0");//TPV con conexiï¿½n a internet (Fija)
		list.add(!old?affi.getTpvInternetTel()!=null?affi.getTpvInternetTel().toString():"0":"0");//TPV con conexiï¿½n a internet con respaldo telefï¿½nica (Fija)
		list.add(!old?affi.getTpvBlueTel()!=null?affi.getTpvBlueTel().toString():"0":"0");//TPV inalï¿½mbrica (bluetooth) con conexiï¿½n telefï¿½nica (Mï¿½vil)
		list.add(!old?affi.getQuantgprs()!=null?affi.getQuantgprs().toString():"0":"0");//TPV con conexiï¿½n celular (GPRS) (Mï¿½vil)
		list.add(!old?affi.getTpvBlueInternet()!=null?affi.getTpvBlueInternet().toString():"0":"0");//TPV inalï¿½mbrica (bluetooth) con conexiï¿½n internet (Mï¿½vil)
		list.add(!old?affi.getQuantwifi()!=null?affi.getQuantwifi().toString():"0":"0");//WiFi (Mï¿½vil)
		list.add(!old?affi.getWifiTel()!=null?affi.getWifiTel().toString():"0":"0");//WiFi con respaldo telefï¿½nico (Mï¿½vil)
		list.add(!old?affi.getQuantpinpad()!=null?Formatter.fixToString(affi.getQuantpinpad()):"0":"0"); // numero de pinpad
		list.add(!old?affi.getOwnTpvPinpad()!=null && "X".equalsIgnoreCase(affi.getOwnTpvPinpad())?"Si":"No":"0");//tpv o pinpad propia
		list.add(map.get("dcc")!=null && "X".equalsIgnoreCase(map.get("dcc"))?"Si":"No");//DCC
		list.add(map.get("red")!=null && "1".equalsIgnoreCase(map.get("red"))?"Ixe":"Banorte");//red
		
		//Comisiones internacional %
		list.add(Formatter.fixToString(affi.getAvcommisionintnlmn())); // affiliation_avcommisionintnlmn
        list.add(Formatter.fixToString(affi.getAvcommisionintnldlls())); // affiliation_avcommisionintnldlls
        //Comisiones internacional $
        list.add(Formatter.fixToString(affiliation_commisionintnlmn)); // affiliation_commisionintnlmn
        list.add(Formatter.fixToString(affiliation_commisionintnldlls)); // affiliation_commisionintnldlls
        
        //Geolocalizacion
        list.add(map.get("affiliation_latitude"));//Latitud
        list.add(map.get("affiliation_length"));//Longitud
        
        list.add(map.get("rentBy"));
        list.add(map.get("groupNo"));
        
        //Equipos
       
        list.add(Formatter.fixToString(affi.getRateMposmn()));//total costo por equipo pesos
        list.add(Formatter.fixToString(affi.getRateMposdlls()));//total costo por equipo dolares
        
        //Garantia Liquida
        //list.add(map.get("aff_garantiaLiquida"));
        list.add(map.get("aff_garantiaLiquida") != null && map.get("aff_garantiaLiquida").equalsIgnoreCase("true")?"Si":"No");
        // "aff_montoInicial",
        //list.add(map.get("aff_montoInicial"));
        list.add(map.get("aff_montoInicial") != null && map.get("aff_montoInicial").equalsIgnoreCase("true")?"Si":"No");
        
        //"aff_montoPromDiario",
        list.add(map.get("aff_montoPromDiario"));
	    //"aff_porcentajeGL",
        list.add(map.get("aff_porcentajeGL"));
        //"aff_excepcionPorceGL"
        list.add(map.get("aff_excepcionPorceGL"));
        //"aff_montoGL",
        list.add(map.get("aff_montoGL"));
	    //  "aff_porcentajeInicialGL",
        list.add(map.get("aff_porcentajeInicialGL"));
	    //  "aff_montoInicialGL",
        list.add(map.get("aff_montoInicialGL"));
	    //  "aff_porcentajeRestanteGL",
        list.add(map.get("aff_porcentajeRestanteGL"));
	    //  "aff_montoRestanteGL",
        list.add(map.get("aff_montoRestanteGL"));
	    //  "aff_porcentajeDiarioGL",
        list.add(map.get("aff_porcentajeDiarioGL"));
	    //  "aff_promMontoDiarioGL",
        list.add(map.get("aff_promMontoDiarioGL"));
	    //  "optionPorcentajeVentasDiarias",
	    //  "optionMontoFijoDiario",							        
	    //"aff_ventasEstimadasMensuales",
        list.add(map.get("aff_ventasEstimadasMensuales"));
	    //  "aff_montoEstimadoDeTransaccion"
        list.add(map.get("aff_montoEstimadoDeTransaccion"));
        //  "aff_diasAproxGL"
        list.add(map.get("aff_diasAproxGL"));
        // "aff_glOriginal"
        list.add(map.get("aff_glOriginal"));
        // "aff_comentariosDisminucionExcepcionGL"
        list.add(map.get("aff_comentariosDisminucionExcepcionGL"));
        
        //cybersource
        list.add(map.get("transactionFeeCyberMn"));
        list.add(map.get("limitedCoverageCyberMn"));
        list.add(map.get("wideCoverageCyberMn"));
        
        list.add(map.get("mujerPyME")!= null && map.get("mujerPyME").equalsIgnoreCase("true")?"SI":"NO");
        
        return list;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> getHeader(){
        List<String> list = new ArrayList();
        list.add("Folio Solicitud");      //	Numero de Folio
        list.add("Lugar y Fecha de Celebracion"); //  Lugar y Fecha de Celebracion
        list.add("Nombre Comercial");  //  Nombre Comercial
        list.add("Calle y Numero");  //  Calle Y numero
        list.add("Colonia");  //  Colonia
        list.add("CP");  //  C.P.
        list.add("Ciudad");  //  Ciudad
        list.add("Estado");  //  Estado
        list.add("Email");  //  Correo electronico
        list.add("Lada");  //  Telefono
        list.add("Telefono");  //  Telefono
        list.add("Nombre Razon Social");  //  Nombre de la RazÃ³n Social
        list.add("RFC");  //  RFC
        list.add("Cuenta Concentradora (MXP)");  //  Cuenta Concentradora (mxp)
        list.add("Cuenta Concentradora (DLLS)");  //  Cuenta Concentradora (dlls)
        list.add("IVA");  //  IVA
        list.add("No. Giro");  //  NÂ° de Giro
        list.add("Descripcion");  //  DescripciÃ³n
        list.add("SIC");  //  SIC (Cliente Altamira)
        list.add("Sitio o tienda Virtual");  //  Sitio o tienda virtual
        list.add("Nombre y Apellidos 1 Cliente");
        list.add("RFC 1");
        list.add("Email 1");
        list.add("Nombre y Apellidos 2 Cliente");
        list.add("RFC 2");
        list.add("Email 2");
        list.add("Nombre y Apellidos 1 Banco"); // Nombre (s) 1 y Apellidos
        list.add("Puesto 1"); // Puesto        
        list.add("Nombre y Apellidos 2 Banco"); // Nombre (s) 1 y Apellidos
        list.add("Puesto 2"); // Puesto
        list.add("Afiliacion Asignada (MXP)");  //  AfiliaciÃ³n Asignada (mxp)
        list.add("Afiliacion Asignada (DLLS)");  //  AfiliaciÃ³n Asignada (dll)* solo tpv dolares
        list.add("Nombre Sucursal");  //  Nombre Sucursal
        list.add("CR");  //  C.R.
        list.add("Domicilio");  //  Domicilio Sucursal
        list.add("Telefono");  //  Telefono
        list.add("Fax");  //  Fax
        list.add("No. Empleado Colocacion");  //  NÂ° de empleado (nuevo)
        list.add("Nombre Empleado Colocacion");  //  Nombre del empleado (nuevo)        
        list.add("Puesto Colocacion");  //  Puesto
        list.add("No. Empleado Ebanking");  //  NÂ° de empleado (nuevo)
        list.add("Nombre Empleado Ebanking");  //  Nombre del empleado (nuevo)
        list.add("Fiador Nombre, Denominación o Razón Social");  //  Nombre, DenominaciÃ³n o RazÃ³n Social
        list.add("Calle y Numero");  //  Calle y Numero
        list.add("Colonia");  //  Colonia
        list.add("CP");  //  C.P.
        list.add("Poblacion");  //  Ciudad
        list.add("Estado");  //  Estado
        list.add("Correo Electronico");  //  Correo electronico
        list.add("Telefono");  //  Telefono
        list.add("Ext.");  //  ExtenciÃ³n (nuevo)
        list.add("Fax");  //  Fax
        list.add("Ext.");  //  ExtenciÃ³n (nuevo)
        list.add("RFC");  //  RFC
        list.add("Nombre y Apellidos Contacto Resp Hta");
        list.add("Correo Electronico");
        list.add("Telefono");
        list.add("Compañia Fianza"); // affiliation_officerdepositexent
        list.add("Nombre y Apellido de quien Excenta la Fianza"); // affiliation_officerdepositexent
        list.add("Monto Fianza"); // affiliation_depositamount
        list.add("Vigencia Fianza"); // affiliation_duedate
        list.add("Observaciones");  //  para escribir las observacones
        list.add("Divisa"); // affiliation_currency
        list.add("Modalidad"); // affiliation_modality
        list.add("Esquema"); // affiliation_comercialplan
        list.add("Buro Interno"); // affiliation_internalcredithistory
        list.add("Buro Externo"); // affiliation_externalcredithistory        
        list.add("Tramite");                           // fijo
        list.add("Placa y Transcriptora"); // affiliation_quantmanual
        list.add("Afiliacion pesos"); //
        list.add("Afiliacion dolares"); //
        list.add("Banco Actual"); // affiliation_currentbank
        list.add("Tipo de Solucion"); //
        list.add("Producto"); // 
        list.add("No. Terminales GPRS"); // affiliation_quantgprs
        list.add("Plan Banorte GPRS"); //        
        list.add("No. Terminales WiFi"); // affiliation_quantwifi
        list.add("Plan Banorte GPRS"); //
        list.add("No. Terminales LAN"); // affiliation_quantlan
        list.add("No. Pinpad"); // affiliation_quantpinpad
        list.add("No. Dialup"); // affiliation_quantdialup
        list.add("No. Bluetooth"); // affiliation_quantdialup
        list.add(HeaderConstants.CANT_MPOS_RENTA_MENSUAL);//cantidad equipos renta mensual
        list.add(HeaderConstants.CANT_MPOS_COSTO_EQUIPO); // cantidad costo equipo
        list.add("Compartir AMEX"); //
        list.add("Modelo Compartir AMEX"); //
        list.add("Es una nueva afiliacion AMEX"); //
        list.add("No. Afiliacion AMEX"); //
        list.add(ApplicationConstants.SIGN_PORCENT + HeaderConstants.COMISION_TC + HeaderConstants.SUBFIJO_MX); // affiliation_avcommisiontcmn
        list.add(ApplicationConstants.SIGN_PORCENT + HeaderConstants.COMISION_TC + HeaderConstants.SUBFIJO_DLLS); // affiliation_avcommisiontcdlls
        list.add(ApplicationConstants.SIGN_PORCENT + HeaderConstants.COMISION_TD + HeaderConstants.SUBFIJO_MX); // affiliation_avcommisiontdmn
        list.add(ApplicationConstants.SIGN_PORCENT + HeaderConstants.COMISION_TD + HeaderConstants.SUBFIJO_DLLS); // affiliation_avcommisiontddlls
        
        list.add(ApplicationConstants.SIGN_CURRENCY + HeaderConstants.COMISION_TC + HeaderConstants.SUBFIJO_MX); // affiliation_commisiontcmn
        list.add(ApplicationConstants.SIGN_CURRENCY + HeaderConstants.COMISION_TC + HeaderConstants.SUBFIJO_DLLS); // affiliation_commisiontcdlls
        list.add(ApplicationConstants.SIGN_CURRENCY + HeaderConstants.COMISION_TD + HeaderConstants.SUBFIJO_MX); // affiliation_commisiontdmn
        list.add(ApplicationConstants.SIGN_CURRENCY + HeaderConstants.COMISION_TD + HeaderConstants.SUBFIJO_DLLS); // affiliation_commisiontddlls
        
        list.add(HeaderConstants.CUOTA_AFILIACION + HeaderConstants.SUBFIJO_MX); // affiliation_affiliationratemn
        list.add(HeaderConstants.CUOTA_AFILIACION + HeaderConstants.SUBFIJO_DLLS); // affiliation_affiliationratedlls
        list.add(HeaderConstants.MAQUINA_TRANSCRIPTORA + HeaderConstants.SUBFIJO_MX); // affiliation_transcriptorratemn
        //list.add("Por cada Maquina transcriptora/Placa (DLLS)"); // affiliation_transcriptorratedlls
        list.add(HeaderConstants.RENTA_MENSUAL + HeaderConstants.SUBFIJO_MX); // affiliation_monthlyratemn
        list.add(HeaderConstants.RENTA_MENSUAL + HeaderConstants.SUBFIJO_DLLS); // affiliation_monthlyratedlls
        
        list.add(HeaderConstants.RENTA_MENSUAL_3DSEC + HeaderConstants.SUBFIJO_MX); // affiliation_monthlyrate3dsmn
        list.add(HeaderConstants.RENTA_MENSUAL_3DSEC + HeaderConstants.SUBFIJO_DLLS); // affiliation_monthlyrate3dsdlls
        list.add(HeaderConstants.ANUALIDAD_3DSEC + HeaderConstants.SUBFIJO_MX); // affiliation_activation3dsmn
        list.add(HeaderConstants.ANUALIDAD_3DSEC +  HeaderConstants.SUBFIJO_DLLS); // affiliation_activation3dsdlls
        list.add(HeaderConstants.FACTURACION_MENSUAL_MINIMA+  HeaderConstants.SUBFIJO_MX); // affiliation_monthlyinvoicingminmn
        list.add(HeaderConstants.FACTURACION_MENSUAL_MINIMA + HeaderConstants.SUBFIJO_DLLS); // affiliation_monthlyinvoicingmindlls
        list.add(HeaderConstants.COMISION_NO_CUMPLIR_ANTERIOR+ HeaderConstants.SUBFIJO_MX); // affiliation_failmonthlyinvoicingmn
        list.add(HeaderConstants.COMISION_NO_CUMPLIR_ANTERIOR + HeaderConstants.SUBFIJO_DLLS); // affiliation_failmonthlyinvoicingdlls
        list.add(HeaderConstants.SALDO_PROMEDIO_MENSUAL+ HeaderConstants.SUBFIJO_MX); // affiliation_minimiunbalancemn
        list.add(HeaderConstants.SALDO_PROMEDIO_MENSUAL + HeaderConstants.SUBFIJO_DLLS); // affiliation_minimiunbalancedlls
        list.add(HeaderConstants.COMISION_NO_CUMPLIR_ANTERIOR + HeaderConstants.SUBFIJO_MX); // affiliation_failminimiunbalancemn
        list.add(HeaderConstants.COMISION_NO_CUMPLIR_ANTERIOR + HeaderConstants.SUBFIJO_DLLS); // affiliation_failminimiunbalancedlls
        
        list.add(HeaderConstants.PAGARE_MINIMO + HeaderConstants.SUBFIJO_MX); // affiliation_promnotemn
        list.add(HeaderConstants.PAGARE_MINIMO + HeaderConstants.SUBFIJO_DLLS); // affiliation_promnotedlls
        list.add(HeaderConstants.COMISION_INCUMPLIMIENTO + HeaderConstants.SUBFIJO_MX); // affiliation_failpromnotemn
        list.add(HeaderConstants.COMISION_INCUMPLIMIENTO + HeaderConstants.SUBFIJO_DLLS); // affiliation_failpormnotedlls
        list.add(HeaderConstants.SERVICIO_VERIFICACION + HeaderConstants.SUBFIJO_MX); // affiliation_avsmn
        list.add(HeaderConstants.SERVICIO_VERIFICACION + HeaderConstants.SUBFIJO_DLLS); // affiliation_avsdlls
        list.add(HeaderConstants.OTROS_CONCEPTOS + HeaderConstants.SUBFIJO_DSC);
        list.add(HeaderConstants.OTROS_CONCEPTOS + HeaderConstants.SUBFIJO_MX);
        list.add(HeaderConstants.OTROS_CONCEPTOS + HeaderConstants.SUBFIJO_DLLS);
        list.add(HeaderConstants.SOBRETASA); //
        list.add(HeaderConstants.CATEGORIA_CREDITO); //
        list.add(HeaderConstants.CATEGORIA_DEBITO); //
        list.add(HeaderConstants.ALIANZA); 
        list.add(HeaderConstants.PAQUETE); 
      //Tiempo Aire Netpay
        list.add(HeaderConstants.TIEMPO_AIRE); 
        list.add(HeaderConstants.TELCEL); 
        list.add(HeaderConstants.MOVISTAR); 
        list.add(HeaderConstants.IUSACELL); 
        list.add(HeaderConstants.NEXTEL); 
        //TPV NOMINA
        list.add(HeaderConstants.TPV_NOMINA); 
        list.add(HeaderConstants.EMPLOYEE_NOMINA); 
        list.add(HeaderConstants.PENALIZACION_NOMINA_MN); 
        list.add(HeaderConstants.PENALIZACION_NOMINA_DLLS);
        
        list.add(HeaderConstants.CAPTURE_SCHEMA);//Captura de Esquema
        list.add(HeaderConstants.INTEGRATION); // Integracion
        list.add(HeaderConstants.RENTA_CYBERSOURCE);//renta cyber dolar
       
        //para cashback (get header)
        list.add("Cash Back");
        list.add("Comision pago Cash Back(%)");
        list.add("Comision pago Cash Back($)");
        list.add("Comision cobro Cash Back(%)");
        list.add("Comision cobro Cash Back($)");
        //ixe
        list.add("¿Requiere fianza?");
        list.add("¿Exentar fianza?");
        list.add("Teclado Abierto");
        list.add("Venta Forzada ");
        list.add("QPS");
        list.add("Dualidad Amex");
        list.add("TPV Desatendida");
        list.add("Módulo de Conciliacion de transacciones");
        list.add("Módulo de Solicitud de pagares");
        list.add("Impulso captación");
        list.add("Nombre Contacto administrador 1");
        list.add("Correo electronico contacto administrador 1");
        list.add("Telefono Contacto administrador 1");
        list.add("Nombre de Contacto adicional para el modulo de petición de pagares 1");
        list.add("Correo electrónico contacto adicional para el modulo de peticion de pagares 1");
        list.add("Telefono contacto adicional para el modulo de peticion de pagares 1");
        list.add("Nombre de Contacto Adicional para el modulo de peticion de pagares 2");
        list.add("Correo electrónico contacto adicional para el modulo de peticion de pagares 2");
        list.add("Telefono contacto adicional para el modulo de peticion de pagares 2");
        list.add("Region del Ejecutivo que coloca");
        list.add("Territorial del Ejecutivo que coloca");
        list.add("Nombre/Razon Social Contacto Desarollador Comercio Electronico");
        list.add("e-mail desarrollador");
        list.add("Tel y Ext");
        list.add("Pago Movil");
        list.add("TPV con conexion telefonica (Fija)");
        list.add("TPV con conexion celular (Fija)");
        list.add("TPV con conexion a internet (Fija)");
        list.add("TPV con conexion a internet con respaldo telefonica (Fija)");
        list.add("TPV inalámbrica (bluetooth) con conexión telefonica (Movil)");
        list.add("TPV con conexión celular (GPRS) (Movil)");
        list.add("TPV inalambrica (bluetooth) con conexión internet (Movil)");
        list.add("WiFi (Movil)");
        list.add("WiFi con respaldo telefonico (Movil)");
        list.add("No. Pinpad");
        list.add("TPV/Pinpad Propia");
        list.add("DCC");
        list.add("Red");
        //Comisiones internacionales %
        list.add(ApplicationConstants.SIGN_PORCENT + HeaderConstants.COMISION_INTNL + HeaderConstants.SUBFIJO_MX); // affiliation_avcommisionintnlmn
        list.add(ApplicationConstants.SIGN_PORCENT + HeaderConstants.COMISION_INTNL + HeaderConstants.SUBFIJO_DLLS); // affiliation_avcommisionintnldlls
        //Comisiones internacionales $
        list.add(ApplicationConstants.SIGN_CURRENCY + HeaderConstants.COMISION_INTNL + HeaderConstants.SUBFIJO_MX); // affiliation_commisionintnlmn
        list.add(ApplicationConstants.SIGN_CURRENCY + HeaderConstants.COMISION_INTNL + HeaderConstants.SUBFIJO_DLLS); // affiliation_commisionintnldlls
        
        //Geolocalizacion
        list.add(HeaderConstants.LATITUD);//Latitud
        list.add(HeaderConstants.LONGITUD);//Longitud
        
        list.add(HeaderConstants.RentBy);
        list.add(HeaderConstants.GroupBy);
        
        //EQUIPOS
        list.add(HeaderConstants.TOTAL_MPOS_COSTO_EQUIPO);
        list.add(HeaderConstants.TOTAL_MPOS_COSTO_EQUIPO_DLLS);
        
        //Garantia Liquida
        
        list.add("Garantía Liquida");
        //"aff_montoInicial",
        list.add("Monto Inicial");
        //"aff_montoPromDiario",
        list.add("Monto Promedio Diario");
	    //"aff_porcentajeGL",
	    list.add("% GL");
	    //"aff_excepcionPorceGL"
        list.add("Disminucion % GL");
        //  "aff_montoGL",
	    list.add("Monto GL");
	    //  "aff_porcentajeInicialGL",
	    list.add("% Inicial GL");
	    //  "aff_montoInicialGL",
	    list.add("Monto Inicial GL");
	    //  "aff_porcentajeRestanteGL",
	    list.add("% Restante GL");
	    //  "aff_montoRestanteGL",
	    list.add("Monto Restante GL");
	    //  "aff_porcentajeDiarioGL",
	    list.add("% Diario GL");
	    //  "aff_promMontoDiarioGL",
	    list.add("Prom Monto Diario GL");
	    //  "optionPorcentajeVentasDiarias",
	    //  "optionMontoFijoDiario",							        
	    //"aff_ventasEstimadasMensuales",
	    list.add("Ventas Estimadas Mensuales");
	    //  "aff_montoEstimadoDeTransaccion"
	    list.add("Monto Estimado De Transaccion");
	    // "aff_diasAproxGL"
	    list.add("Días Aprox GL");
	    // "aff_glOriginal"
	    list.add("GL Original");
        // "aff_comentariosDisminucionExcepcionGL"
	    list.add("Motivos Disminución o Excepción de GL");
	    
	    //cybersource
	    list.add("Cybersource cuota por transaccion");
	    list.add("Cybersource cobertura limitada");
	    list.add("Cybersource cobertura amplia");
	    
	    list.add("Mujer PyME Banorte");
        
        return list;
    }
    
}
