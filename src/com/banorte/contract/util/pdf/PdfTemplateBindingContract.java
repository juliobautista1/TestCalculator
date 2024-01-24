package com.banorte.contract.util.pdf;

import com.banorte.contract.business.AttributeRemote;
import com.banorte.contract.business.ContractRemote;
import com.banorte.contract.model.Affiliation;
import com.banorte.contract.model.Attribute;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.ContractAttributePK;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.EncryptBd;
import com.banorte.contract.web.EjbInstanceManager;
import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.logging.Logger;

/**
 *
 * @author MRIOST
 */
public class PdfTemplateBindingContract implements PdfTemplateBinding {

    private Logger log = Logger.getLogger(PdfTemplateBindingContract.class.getName());
    private Contract contract;
    private Integer affiliationId;
   
    protected AttributeRemote attributeBean;
    protected ContractRemote contractBean;
    
    public PdfTemplateBindingContract() {
    	if( attributeBean == null){
			attributeBean = (AttributeRemote) EjbInstanceManager.getEJB(AttributeRemote.class);
		}
    	if( contractBean == null){
			contractBean = (ContractRemote) EjbInstanceManager.getEJB(ContractRemote.class);
		}
    }

    public void setAffiliationId(Integer affiliationId) {
        this.affiliationId = affiliationId;
    }

    public Integer getAffiliationId() {
        return affiliationId;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getAffiliationAttribute(Affiliation affiliation, String att) {
        String result = "";
        if (att != null && att.length() > 1) {
            try {
                Class c = affiliation.getClass();
                Method method = c.getMethod("get" + att.substring(0, 1).toUpperCase() + att.substring(1, att.length()), null);
                Object obj = method.invoke(affiliation, null);
                result = obj != null ? obj.toString() : "";

            } catch (Exception ex) {
            }
        }
        return result;
    }

    // 
    public String getBind(String key) {
    	String value="";
    	String valueAccMN="";
    	String valueAccDLLS="";
        EncryptBd  decrypt= new EncryptBd();
        if (contract != null) {
        	Collection<ContractAttribute> atributos = contract.getContractAttributeCollection();
        	Collection<Affiliation> collAffiliation = contract.getAffiliationCollection();
        	
        	//Etiqueta de eCommerce para validar si el producto es Comerico electronico
        	if(key.equalsIgnoreCase("eCommerce")){
        		if("Comercio Electronico".equalsIgnoreCase(contract.getProduct().getName())){
        			return "X";
        		}
        	}
        	//gina Para marcar el tipo de alianza de la afiliacion en adquirente:
//        	Collection<ContractAttribute> atributos = contract.getContractAttributeCollection();
        	ContractAttribute alianza = null;
        	String tipoAlianza = null;
        	if(atributos!=null && !atributos.isEmpty()){
        		for(ContractAttribute a:atributos){
        			String nombreA = a.getAttribute().getFieldname();
        			if("alliance".equalsIgnoreCase(nombreA)){
        				alianza = a;
        				break;
        			}
        		}
        		tipoAlianza=(alianza!=null)?alianza.getValue():"";
        	
	        	if("netPay".equalsIgnoreCase(key)){
	        		if("netPay".equalsIgnoreCase(tipoAlianza)){
	        			return "X";
	        		}else{
	        			return"";
	        		}
	        	}else if("cybersource".equalsIgnoreCase(key)){
	        		if("Cybersource".equalsIgnoreCase(tipoAlianza)){
	        			return "X";
	        		}else{
	        			return "";
	        		}
	        	}else if("micros".equalsIgnoreCase(key)){
	        		if("Micros".equalsIgnoreCase(tipoAlianza)){
	        			return"X";
	        		}else{
	        			return "";
	        		}
	        	}else if("agregador".equalsIgnoreCase(key)){
	        		if("agregador".equalsIgnoreCase(tipoAlianza)){
	        			return "X";
	        		}else{
	        			return "";
	        		}
	        	}
        	}//fin para etiqueta de alianza
        	
        	//Para leyenda de planes SLP y OCDT
        	if("slpNote".equalsIgnoreCase(key)){
        		for(ContractAttribute at : atributos){
        			if("chargeType".equalsIgnoreCase(at.getAttribute().getFieldname())){
        				if("SLP 2014".equalsIgnoreCase(at.getValue())||"OCCIDENTE 2016".equalsIgnoreCase(at.getValue())){
        					value="** A partir del quinto mes se aplicará la cuota por Baja Facturación. Promoción válida al 31 de Julio del 2016.";
            				return value;	
        				}
        			}
        		}
        	}
        	if("slpMark".equalsIgnoreCase(key)){
        		for(ContractAttribute at : atributos){
        			if("chargeType".equalsIgnoreCase(at.getAttribute().getFieldname())){
        				if("SLP 2014".equalsIgnoreCase(at.getValue())||"OCCIDENTE 2016".equalsIgnoreCase(at.getValue())){
        					value="**";
            				return value;	
        				}
        			}
        		}
        	}
        	// fin planes SLP y OCDT
        	
        	//Restaurantes 2017
        	if("restNote".equalsIgnoreCase(key)){
        		for(ContractAttribute at : atributos){
        			if("chargeType".equalsIgnoreCase(at.getAttribute().getFieldname())){
        				if("RCRC".equalsIgnoreCase(at.getValue())){
        					value= "Las Tasa de Descuento Crédito de 1.99%, la Tasa de Descuento Débito de 1.99% y Tasa de Descuento Internacional 2.50% aplicará durante los "
        					+ "primeros 3 meses a partir de la fecha de alta de la afiliación. Una vez finalizado este periodo se ajustarán las tasas de descuento en la quincena inmediata posterior al "
        					+ "vencimiento de plazo conforme al Plan sin Exigencias 2017, según corresponda: " + Chunk.NEWLINE
        					+ "Comida Rápida Giro 5814 Tasa de Descuento Crédito 2.50%, Tasa de Descuento Débito 2.15%, Tasa de Descuento Internacional 2.90%. " + Chunk.NEWLINE
        					+ "Restaurantes y Cafeterías Giros 5812 y 5813 Tasa de Descuento Crédito 2.50%, Tasa de Descuento Débito 1.90%, Tasa de Descuento Internacional 2.90% " + Chunk.NEWLINE
        					+ "Vigencia de la Campaña Restaurantes Comida Rápida y Cafeterías hasta el 31 de Diciembre del 2017.";
        				}//"Restaurantes y CafeterÃ­as Giros 5812 y 5813 Tasa de Descuento CrÃ©dito 2.50%, Tasa de Descuento DÃ©bito 1.90%, Tasa de Descuento Internacional 2.95%.--> Modificación en la linea 165
        			}
        		}
        	}
        	//Fin Restaurantes 2017
        	
        	if("failmonthlyinvoicingTPVmnComplete".equalsIgnoreCase(key) || "failmonthlyinvoicingTPVdllsComplete".equalsIgnoreCase(key)){
        		for (Affiliation affil : collAffiliation) {
                	if(affil.getProductdesc().equals("Terminal punto de venta") || affil.getProductdesc().equals("Interredes")) {
                		for(ContractAttribute at : atributos){
                			if("failmonthlyinvoicingTPVmnComplete".equalsIgnoreCase(key) && "failmonthlyinvoicingmnComplete".equalsIgnoreCase(at.getAttribute().getFieldname())){
                				return at.getValue();
                			}
                			if("failmonthlyinvoicingTPVdllsComplete".equalsIgnoreCase(key) && "failmonthlyinvoicingdllsComplete".equalsIgnoreCase(at.getAttribute().getFieldname())){
                				return at.getValue();
                			}
                		}
                	} /*Inicio Mpos*/
                    if(affil.getProductdesc().equals("Terminal Personal Banorte (Mpos)")) {
                        for(ContractAttribute at : atributos){
                          if("failmonthlyinvoicingTPVmnComplete".equalsIgnoreCase(key) && "failmonthlyinvoicingmnComplete".equalsIgnoreCase(at.getAttribute().getFieldname())){
                            return at.getValue();
                          }
                          if("failmonthlyinvoicingTPVdllsComplete".equalsIgnoreCase(key) && "failmonthlyinvoicingdllsComplete".equalsIgnoreCase(at.getAttribute().getFieldname())){
                            return at.getValue();
                          }
                        }
                      }/*Fin Mpos*/
                }
        	}
        	if("failmonthlyinvoicingCargosmnComplete".equalsIgnoreCase(key) || "failmonthlyinvoicingCargosdllsComplete".equalsIgnoreCase(key)){
        		for (Affiliation affil : collAffiliation) {
                	if(affil.getProductdesc().equals("Comercio Electronico") || affil.getProductdesc().equals("Cargo Recurrente")) {
                		for(ContractAttribute at : atributos){
                			if("failmonthlyinvoicingCargosmnComplete".equalsIgnoreCase(key) && "failmonthlyinvoicingmnComplete".equalsIgnoreCase(at.getAttribute().getFieldname())){
                				return at.getValue();
                			}
                			if("failmonthlyinvoicingCargosdllsComplete".equalsIgnoreCase(key) && "failmonthlyinvoicingdllsComplete".equalsIgnoreCase(at.getAttribute().getFieldname())){
                				return at.getValue();
                			}
                		}
                	}
                }
        	}
        	//Se agrego para comisión tpv y cargos
/*        	if("failminimiunbalanceTPVmnComplete".equalsIgnoreCase(key) || "failminimiunbalanceTPVdllsComplete".equalsIgnoreCase(key)){
        		for (Affiliation affil : collAffiliation) {
                	if(affil.getProductdesc().equals("Terminal punto de venta") || affil.getProductdesc().equals("Interredes") || affil.getProductdesc().equals("Terminal Personal Banorte (Mpos)")) {
                		for(ContractAttribute at : atributos){
                			if("failminimiunbalanceTPVmnComplete".equalsIgnoreCase(key) && "failminimiunbalancemnComplete".equalsIgnoreCase(at.getAttribute().getFieldname())){
                				return at.getValue();
                			}
                			if("failminimiunbalanceTPVdllsComplete".equalsIgnoreCase(key) && "failminimiunbalancedllsComplete".equalsIgnoreCase(at.getAttribute().getFieldname())){
                				return at.getValue();
                			}
                		}
                	}
                }
        	}
*/        	
        	if("affiliation_otherconcept1des".equalsIgnoreCase(key)){
        		Affiliation affiliation = null;
                
                for (Affiliation affil : collAffiliation) {
                	if(affil.getProductdesc().equals("Terminal Personal Banorte (Mpos)")) {
        			return "Costo por equipo MPOS: $".concat(ApplicationConstants.VALOR_MPOS_VENTA.toString()); //$799 $649 $500 //F-98896 Mejora Garantia Liquida
                    }
                }
        	}
        	
        	//tpb mpos
        	if("checkTpbMpos".equalsIgnoreCase(key)){
        		Affiliation affiliation = null;
                
                for (Affiliation affil : collAffiliation) {
                	if(affil.getProductdesc().equals("Terminal Personal Banorte (Mpos)")) {
    						return "X";
                    	}
                }
        	}
        	
        	if("iscurrencymn".equalsIgnoreCase(key)){                
                for (Affiliation affil : collAffiliation) {
                	if("Ambos".equals(affil.getCurrency()) || "Pesos".equals(affil.getCurrency())) {
    					return "X";
                    }
                }
        	}
        	
        	if("iscurrencydlls".equalsIgnoreCase(key)){
                for (Affiliation affil : collAffiliation) {
                	if("Ambos".equals(affil.getCurrency()) || "Dolares".equals(affil.getCurrency())) {
    					return "X";
                    }
                }
        	}
        	
        	//Si el producto es Cobranza Domiciliada...
        	if(contract.getProduct().getProductid().equals(ApplicationConstants.ID_PORUDCT_CD) ){
//        		Collection<ContractAttribute> atts = contract.getContractAttributeCollection();  
                for (ContractAttribute att : atributos) {
                    if (att.getAttribute().getFieldname().equals(key)) {
                    	value = att.getValue();
                	}
                }
        	}
        	//...fin de Cobranza Domiciliada
        	//para numero de pagina de cuentas propias BEM
        	if("bemAccountsPage".equalsIgnoreCase(key)){
        		Integer numCuentas=1;
        		for(ContractAttribute ca:atributos){
       				if("quantaccown".equalsIgnoreCase(ca.getAttribute().getFieldname())){
           				try {
           					numCuentas=Integer.parseInt(ca.getValue());	
						} catch (Exception e) {
							System.out.println("Error al obtener el numero de cuentas BEM");
						}
           			}	
        		}
        		if(numCuentas>10){
        			return "3";
        		}else if(numCuentas>5){
        			return "2";
        		}else{
        			return "1";
        		}
        	}
        	
        	if (key.startsWith("affiliation_")) {
//                Collection<Affiliation> collAffiliation = contract.getAffiliationCollection();
                Affiliation affiliation = null;
                
                for (Affiliation affil : collAffiliation) {
                    if (affil.getAffiliationPK().getAffiliationid().intValue() == affiliationId.intValue()) {
                        affiliation = affil;
                        //valueEnc="";
                       if (key.equals("affiliation_accountnumbermn")){   
                    	   //System.out.println("es affiliation_accountnumbermn ***");
                    	   if (affiliation.getAccountnumbermn()!=null && affiliation.getAccountnumbermn().trim().length()>10){
                              //  valueEnc=decrypt.decrypt(affiliation.getAccountnumbermn());
                              //  affiliation.setAccountnumbermn(valueEnc);  
                        	   valueAccMN = decrypt.decrypt(affiliation.getAccountnumbermn());
                            }
                       }
                       
                       if (key.equals("affiliation_accountnumberdlls")){
                    	  if (affiliation.getAccountnumberdlls()!=null && affiliation.getAccountnumberdlls().trim().length()>10){
                          //  valueEnc=decrypt.decrypt(affiliation.getAccountnumberdlls());
                          //  affiliation.setAccountnumberdlls(valueEnc);  
                    		  valueAccDLLS = decrypt.decrypt(affiliation.getAccountnumberdlls());
                        }
                       } 
                        break;
                    }
                }
                if (affiliation != null) {
                	if(key.equals("affiliation_accountnumbermn") && affiliation.getAccountnumbermn()!=null && affiliation.getAccountnumbermn().trim().length()>10){
                		value= valueAccMN;
                	}else if(key.equals("affiliation_accountnumberdlls") && affiliation.getAccountnumberdlls()!=null && affiliation.getAccountnumberdlls().trim().length()>10){
                		value= valueAccDLLS;
                	}else{
                		value = getAffiliationAttribute(affiliation, key.substring(key.indexOf("_") + 1, key.length()));
                	}
                	if(key.equalsIgnoreCase("affiliation_qps")){
                		value=affiliation.getQps()!=null?affiliation.getQps():"";
                	}
                	 if(key.equalsIgnoreCase("affiliation_mobileTpv")){
                     	if(affiliation.getTpvBlueTel()!=null && affiliation.getTpvBlueTel()>0 ||
                     		affiliation.getQuantgprs()!=null && affiliation.getQuantgprs()>0 ||
                     		affiliation.getTpvBlueInternet()!=null && affiliation.getTpvBlueInternet()>0 ||
                     		affiliation.getQuantwifi()!=null && affiliation.getQuantwifi()>0 ||
                     		affiliation.getWifiTel()!=null && affiliation.getWifiTel()>0
                     		){
                     		return "X";
                     	}
                     }
                     if(key.equalsIgnoreCase("affiliation_fixedTpv")){
                     	if((affiliation.getTpvTel()!=null && affiliation.getTpvTel()>0) ||
                     		affiliation.getQuantpinpad()!=null && affiliation.getQuantpinpad()>0||
                     		affiliation.getTpvMovil()!=null && affiliation.getTpvMovil()>0 ||
                     		affiliation.getTpvInternetTel()!=null && affiliation.getTpvInternetTel()>0 ||
                     		(affiliation.getTpvInternet()!=null && affiliation.getTpvInternet()>0))
                     	{
                     		return "X";
                     	}
                     }
                     if(key.equalsIgnoreCase("affiliation_manual")){//maquina transcriptora
                    	 if(affiliation.getQuantmanual()!=null && affiliation.getQuantmanual()>0){
                    		 return "X";
                    	 }
                     }
                     if("affiliation_monthlyrate3dsmn".equalsIgnoreCase(key)){
                    	 if(affiliation.getMonthlyrate3dsmn()!=null){
                    		 return "$  "+affiliation.getMonthlyrate3dsmn();	 
                    	 }else{
                    		 return "";
                    	 }
                     }
                     if("affiliation_monthlyrate3dsdlls".equalsIgnoreCase(key)){
                    	 if(affiliation.getMonthlyrate3dsdlls()!=null){
                    		 return "$  "+affiliation.getMonthlyrate3dsdlls();
                    	 }else{
                    		 return "";
                    	 }
                     }
                }
            }
        	if(key.equalsIgnoreCase("ownTpvPinpad")){ //si la etiqueta es de TPV/pinpad Propia
//        		Collection<Affiliation> collAffiliation = contract.getAffiliationCollection();
                Affiliation affiliation = null;
                
                for (Affiliation affil : collAffiliation) {
                    if (affil.getAffiliationPK().getAffiliationid().intValue() == affiliationId.intValue()) {
                        affiliation = affil;
                    }
                    if (affiliation!=null){
                    	return affiliation.getOwnTpvPinpad()!=null?affiliation.getOwnTpvPinpad():"";
                    }
                }
        		
        	}

//                Collection<ContractAttribute> atts = contract.getContractAttributeCollection();
                
            if(key.equalsIgnoreCase("contract_reference")){ //para mantenimientos si no tienen el folio guardado como atributo
            	String folio = "";
            	for (ContractAttribute atri : atributos){
            		if(atri.getAttribute().getFieldname().equals("contract_reference")){
            			folio = atri.getValue();
            		}
            	}
            	if(folio.trim().isEmpty()){
            		//crear el atributo y guardarlo
        			ContractAttribute contAttTemp = new ContractAttribute();
					Attribute folioTemp = new Attribute();
					ContractAttributePK pkTemp = new ContractAttributePK();
					
					folioTemp = attributeBean.findByFieldname("contract_reference");
					pkTemp.setAttributeid(folioTemp.getAttributeid());
					pkTemp.setContractid(contract.getContractId());
					pkTemp.setVersion(contract.getVersion());
					contAttTemp.setValue(contract.getReference());
					contAttTemp.setContractAttributePK(pkTemp);
					contAttTemp.setAttribute(folioTemp);
					contAttTemp.setContract(contract);
					
					atributos.add(contAttTemp);
					contract.setContractAttributeCollection(atributos);
					contractBean.update(contract);
					
            	}
            }//fin contract_reference
                
            for (ContractAttribute att : atributos) {                       
            	if (att.getAttribute().getFieldname().equals(key)) {                        
                    if (att.getAttribute().getFieldname().equals("accountnumber") ||
                        att.getAttribute().getFieldname().equals("accownnum_1") ||
                        att.getAttribute().getFieldname().equals("accownnum_2") ||
                        att.getAttribute().getFieldname().equals("accownnum_3") ||
                        att.getAttribute().getFieldname().equals("accownnum_4") ||
                        att.getAttribute().getFieldname().equals("accownnum_5") ||
                        att.getAttribute().getFieldname().equals("accownnum_6") ||
                        att.getAttribute().getFieldname().equals("accownnum_7") ||
                        att.getAttribute().getFieldname().equals("accownnum_8") ||
                        att.getAttribute().getFieldname().equals("accownnum_9") ||
                        att.getAttribute().getFieldname().equals("accownnum_10") ||
                        att.getAttribute().getFieldname().equals("accownnum_11") ||
                        att.getAttribute().getFieldname().equals("accownnum_12") ||
                        att.getAttribute().getFieldname().equals("accownnum_13") ||
                        att.getAttribute().getFieldname().equals("accownnum_14") ||
                        att.getAttribute().getFieldname().equals("accownnum_15") ||
                        att.getAttribute().getFieldname().equals("accothersnum_1") ||
                        att.getAttribute().getFieldname().equals("accothersnum_2") ||
                        att.getAttribute().getFieldname().equals("accothersnum_3") ||
                        att.getAttribute().getFieldname().equals("accothersnum_4") ||
                        att.getAttribute().getFieldname().equals("accothersnum_5")                         
                        ){
                    		if(att.getValue() != null && att.getValue().trim().length() > 10){                                                           
                    			value = decrypt.decrypt(att.getValue());
                    		}else {	
                    			value= att.getValue();
                    		}
                    }else{
                    	value = att.getValue();
                    }
            	}
            }
        }
        return value;
    }
    
    
    public void metodo(){
    	System.out.println("para crear peso en harvest");
    }
}