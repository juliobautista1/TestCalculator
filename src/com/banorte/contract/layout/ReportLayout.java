
package com.banorte.contract.layout;


import com.banorte.contract.business.ContractRemote;
import com.banorte.contract.business.ContractStatusHistoryRemote;
import com.banorte.contract.dto.ContractAcquirerDTO;
import com.banorte.contract.dto.ContractAllProductsDTO;
import com.banorte.contract.dto.ContractNominaDTO;
import com.banorte.contract.dto.ContractSipDTO;
import com.banorte.contract.model.Affiliation;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractStatusHistory;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.AttrConstants;
import com.banorte.contract.util.Formatter;
import com.banorte.contract.util.HeaderConstants;
import com.banorte.contract.util.ProductType;
import com.banorte.contract.util.ReportType;
import com.banorte.contract.web.EjbInstanceManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
*
* @author Joseles Sanchez
**/
public class ReportLayout extends LayoutOperationsTemplate implements Layout {
    
	private ReportType reportType = ReportType.ALL_PRODUCTS;
	ContractStatusHistoryRemote contractStatusHistoryBean;
	
	static Logger logger = Logger.getLogger(LayoutOperations.class.getName());
    private final String FILLS[] = {AttrConstants.REFERENCE, AttrConstants.CR_NUMBER,
    								AttrConstants.OFFICER_EMP_NUM, AttrConstants.OFFICER_NAME_COMPL, 
    								AttrConstants.OFFICER_BANKING_NUM,AttrConstants.OFFICER_BANKING_NAME_COMPL,
    								AttrConstants.OPERATIONS_COMMENT, AttrConstants.INSTALL_DATE,
    								AttrConstants.OTHER_COMMERCIAL_PLAN};
    
    public ReportLayout(List<Contract> search) {
    	filterByReportType(search);
    }
    
    public ReportLayout(List<Contract> search, ReportType reportType, ContractRemote contractBean) {
    	this.setContractBean(contractBean);
    	this.reportType = reportType;
    	filterByReportType(search);
    }
	
    private void filterByReportType(List<Contract> search){
    	
        if ( contractStatusHistoryBean == null ) {
        	contractStatusHistoryBean = (ContractStatusHistoryRemote) EjbInstanceManager.getEJB(ContractStatusHistoryRemote.class);
        }  
    	
    	if(this.reportType.value() == ReportType.ADQUIRENTE.value()){
    		for(Contract record : search){
    			if (record.getProduct().getProductid().intValue() == ProductType.INTERNET.value()
    					|| record.getProduct().getProductid().intValue() == ProductType.TRADICIONAL.value()
    					|| record.getProduct().getProductid().intValue() == ProductType.COMERCIOELECTRONICO.value()
    					||record.getProduct().getProductid().intValue() == ProductType.PYME.value()){
    				
    				if(record.getAffiliationCollection().size()>0){
	                	//Buscar la informacion completa del contrato
	    				Contract foundContact = this.getContractBean().findById(record.getContractId());
	    				this.setContracts(foundContact);
    				}
    			}
    		}
    		
    	}else if (this.reportType.value() == ReportType.SIP.value()){
    		for(Contract record : search){
    			if (record.getProduct().getProductid().intValue() == ProductType.PYME.value()){
    				if(record.getAffiliationCollection().size()>0){
	    				//Buscar la informacion completa del contrato
	    				Contract foundContact = this.getContractBean().findById(record.getContractId());
	    				this.setContracts(foundContact);
    				}
				}
    		}
    	}else{
    		//ReportType.ALL_PRODUCTS
            for ( Contract record : search ) {
            	//Buscar la informacion completa del contrato
				Contract foundContact = this.getContractBean().findById(record.getContractId());
				this.setContracts(foundContact);
            }
    	}
 
    }
    
    

    public boolean hasElements() {

        if( this.getContracts().size() > 0 ) {
            return true;
        } else
            return false;
        
    }
    
    public List<String> getContent(Contract contract) {
        
        Map<String, String> map 					= new LayoutTempleteContract().bindFrom(contract, FILLS);
        List<String> list 							= new ArrayList<String>();
        ContractStatusHistory lastModifiedContract 	= contractStatusHistoryBean.findLastModifyDate(contract.getContractId());

        list.add(Formatter.fixLenght(new Formatter().getMonthDate(contract.getCreationdate().toString()),2)); 	//MM: Mes
        list.add(contract.getProduct().getName());																// Nombre Producto
        list.add(contract.getReference());      																// Número de Folio
        list.add(contract.getVersion().toString());																// Version        
        list.add(Formatter.fixtoLenght(contract.getClient().getMerchantname(), 24));  							//  Nombre Comercial
        list.add(contract.getClient().getFiscalname());  														// Nombre de la RazÃ³n Social
        list.add(contract.getStatus().getName());  																// Estatus Actual
        list.add(contract.getStatus().getDescription());  														// Descripcion del Estatus

        //Feca de ultima modificacion
        if( lastModifiedContract!=null)
        	list.add(new Formatter().formatLongDateToString(lastModifiedContract.getModifydate()));
        else
        	list.add(new Formatter().formatLongDateToString(contract.getCreationdate()));

        list.add(new Formatter().formatLongDateToString(contract.getCreationdate())); 	// Fecha de Creacion
        list.add(Formatter.fixLenght(map.get(AttrConstants.CR_NUMBER),5)); 				//CR
        list.add(contract.getCreationoffempnum());  									// Num Empleado
        list.add(contract.getCreationoffname());  										// Nombre del Empleado
        
        if(this.reportType.value() == ReportType.ADQUIRENTE.value() 
        		||this.reportType.value() == ReportType.SIP.value() ){

            Collection<Affiliation> affiliation= contract.getAffiliationCollection();    // affiliation
            Affiliation affi = null;
            if ( affiliation.size() > 0) {
                affi = affiliation.iterator().next();
            } else {
                affi = new Affiliation();
            }
            
            if(this.reportType.value() == ReportType.ADQUIRENTE.value()){ 
	        	//Inicio Reporte Adquirente
	            list.add(map.get(AttrConstants.OFFICER_EMP_NUM));  				// numempleadoColoco
	            list.add(map.get(AttrConstants.OFFICER_NAME_COMPL));  			// nombreempleadoColoco
	            list.add(map.get(AttrConstants.OFFICER_BANKING_NUM));  			// num Empleado Ebanking Coloco
	            list.add(map.get(AttrConstants.OFFICER_BANKING_NAME_COMPL));  	// Nombre Empleado Ebanking Coloco        
	            //Fin Reporte Adquirente
            }
            list.add(affi.getNumaffilmn());  //  Afiliacion Pesos
            if(this.reportType.value() == ReportType.ADQUIRENTE.value()){ 
				//Inicio Reporte Adquirente
	            list.add(affi.getNumaffildlls());  		//  Afiliacion Dlls Pesos
	            String comercialPlanDescription = getComercialPlanFromMap(affi.getCommercialplan());
	            list.add(comercialPlanDescription);  	// Plan Comercial
	            //Fin Reporte Adquirente
            }
            if(this.reportType.value() == ReportType.ADQUIRENTE.value()){
            	list.add(map.get(AttrConstants.OTHER_COMMERCIAL_PLAN));  // Otro esquema comercial
            }
            
	        list.add(map.get(AttrConstants.OPERATIONS_COMMENT));  	// Comentario de Operaciones
	        list.add(map.get(AttrConstants.INSTALL_DATE)); 			// Fecha de Instalación
	        
	        if(this.reportType.value() == ReportType.SIP.value()){
	            ContractStatusHistory csh = contractStatusHistoryBean.findByContractAndStatusId(contract.getContractId(), 4);
	            if(csh!=null)
	            	list.add(new Formatter().formatLongDateToString(csh.getModifydate())); 		// Fecha Coformalizacion        
	            else
	            	list.add(ApplicationConstants.BLANK_STRING); 			// Fecha Coformalizacion        
	            csh = contractStatusHistoryBean.findByContractAndStatusId(contract.getContractId(), 6);
	            if(csh!=null)
			        list.add(new Formatter().formatLongDateToString(csh.getModifydate())); 		// Fecha Aceptado        
	            else
	            	list.add(ApplicationConstants.BLANK_STRING); 			// Fecha Aceptado        
	        }        
        }
        return list;
    }
    
    public List<String> getHeader(){
        List<String> list = new ArrayList<String>();
        
        if(this.reportType.value() == ReportType.ADQUIRENTE.value()){
        	list = this.getAdquirerHeader();
        }else if(this.reportType.value() == ReportType.SIP.value()){
        	list = this.getSIPHeader();
        }else if (this.reportType.value()== ReportType.ALL_PRODUCTS.value()){
        	list = this.getAllProductsHeaderDTO(); //reporte de todos los productos por fecha *nuevo*
        }else if (this.reportType.value()== ReportType.NOMINA.value()){
        	list = this.getNominaHeader();
        }else{
        	list = this.getAllProductsHeader();
        }
        
        return list;
    }
    
    private List<String> getAdquirerHeader(){
        List<String> list = new ArrayList<String>();
    	
    	list.addAll(this.getAllProductsHeader());
        list.add(HeaderConstants.NUMEMPLEADOCOLOCO); 
        list.add(HeaderConstants.NOMBREEMPLEADOCOLOCO); 
        list.add("NUMEMPEBANKING"); 
        list.add("NOMBREEMPEBANKING"); 
        list.add(HeaderConstants.AFILIACIONPESOS); 
        list.add(HeaderConstants.AFFILIACIONDLLS); 
        list.add(HeaderConstants.ESQUEMACOMERCIAL);
        list.add(HeaderConstants.OTROESQUEMACOMERCIAL); 
        list.add(HeaderConstants.COMENTARIOSOPERACIONES); 
        list.add(HeaderConstants.FECHAINSTALACION);
        list.add(HeaderConstants.EMAIL_CLIENTE);
        list.add(HeaderConstants.LADA_CLIENTE);
        list.add(HeaderConstants.TELEFONO_CLIENTE);
        list.add(HeaderConstants.EXTENSION_CLIENTE);
        
        //Garantia Liquida
        list.add(HeaderConstants.GARANTIA_LIQUIDA);
        list.add(HeaderConstants.VENTAS_ESTIMADAS_MENSUALES);
        list.add(HeaderConstants.MONTO_ESTIMADO_TRANSACCION);
        list.add(HeaderConstants.DATOS_SUCURSAL);
        list.add(HeaderConstants.BURO_INTERNO);
        list.add(HeaderConstants.DIVISA);
        list.add(HeaderConstants.MONTO_INICIAL);
        list.add(HeaderConstants.MONTO_PROM_DIARIO);
        list.add(HeaderConstants.PORCENTAJEGL);
        list.add(HeaderConstants.MONTOGL);
        list.add(HeaderConstants.PORCENTAJEINIGL);
        list.add(HeaderConstants.MONTOINIGL);
        list.add(HeaderConstants.PORCENTAJERESTGL);
        list.add(HeaderConstants.MONTORESTGL);
        list.add(HeaderConstants.PORCENTAJEDIAGL);
        list.add(HeaderConstants.PROMMONTODIARIOGL);
        list.add(HeaderConstants.DIASAPROXGL);
        list.add(HeaderConstants.GLORIGINAL);
        list.add(HeaderConstants.DISMINUCIONLG);
        list.add(HeaderConstants.COMENTARIOSMODISMINUCION);

        list.add(HeaderConstants.GIROCLIENTE);//F-92512 Esquemas 2019 RF_006_Agrear a Reporte_Giro
         
    	return list;
    }

    private List<String> getSIPHeader(){
        List<String> list = new ArrayList<String>();

    	list.addAll(this.getAllProductsHeader());
    	list.add(HeaderConstants.AFILIACIONPESOS); 
        list.add(HeaderConstants.COMENTARIOSOPERACIONES); 
        list.add(HeaderConstants.FECHAINSTALACION); 
        list.add(HeaderConstants.FECHACOFORMALIZACION);
        list.add(HeaderConstants.FECHAACEPTADO);         

    	return list;
    }
    
    private List<String> getAllProductsHeader(){
        List<String> list = new ArrayList<String>();
    	
        list.add(HeaderConstants.MES);  
        list.add(HeaderConstants.PRODUCTO);  
        list.add(HeaderConstants.FOLIO);  
        list.add(HeaderConstants.VERSION);  
        list.add(HeaderConstants.NOMBRECOMERCIAL); 
        list.add(HeaderConstants.RAZONSOCIAL); 
        list.add(HeaderConstants.ESTATUSACTUAL); 
        list.add(HeaderConstants.ESTATUSDESCRIPCION); 
        list.add(HeaderConstants.FECHAULTIMAMODIFICACION);
        list.add(HeaderConstants.FECHACREACION); 
        list.add(HeaderConstants.CR); 
        list.add(HeaderConstants.NUMEMPLEADO); 
        list.add(HeaderConstants.NOMBREEMPLEADO); 

        return list;
    }
//encabezados para el nuevo reporte de todos los productos por un rango de fechas
    public List<String> getAllProductsHeaderDTO(){
        List<String> list = new ArrayList<String>();
    	
        list.add(HeaderConstants.MES);  
        list.add(HeaderConstants.PRODUCTO);  
        list.add(HeaderConstants.FOLIO);  
        list.add(HeaderConstants.VERSION);  
        list.add(HeaderConstants.NOMBRECOMERCIAL); 
        list.add(HeaderConstants.RAZONSOCIAL); 
        list.add(HeaderConstants.ESTATUSACTUAL); 
        list.add(HeaderConstants.ESTATUSDESCRIPCION); 
        list.add(HeaderConstants.FECHAULTIMAMODIFICACION);
        list.add(HeaderConstants.FECHACREACION); 
        list.add(HeaderConstants.CR); 
        list.add(HeaderConstants.NUMEMPLEADO); 
        list.add(HeaderConstants.NOMBREEMPLEADO);
        list.add(HeaderConstants.SIC);
        list.add(HeaderConstants.PLAN.toUpperCase()+" O "+HeaderConstants.ESQUEMACOMERCIAL);
        list.add(HeaderConstants.OTROESQUEMACOMERCIAL);
        list.add(HeaderConstants.TPV_NOMINA.toUpperCase());
        list.add(HeaderConstants.PAQUETE.toUpperCase());
        list.add(HeaderConstants.PLAN.toUpperCase());
        list.add(HeaderConstants.PAYWORKS_CLABE);
        list.add(HeaderConstants.NUMEMPLEADOCOLOCO);
        list.add(HeaderConstants.NOMBREEMPLEADOCOLOCO);
        list.add(HeaderConstants.NUM_EMP_EBANKING.toUpperCase());
        list.add(HeaderConstants.NOMBRE_EMP_EBANKING.toUpperCase());
        list.add(HeaderConstants.NUMEMPLEADOFORMALIZO);
        list.add(HeaderConstants.NOMBREEMPLEADOFORMALIZO);
        list.add(HeaderConstants.FECHAFORMALIZACION);
        list.add(HeaderConstants.NUMCOFORMALIZO);
        list.add(HeaderConstants.NOMBRECOFORMALIZO);
        list.add(HeaderConstants.FECHACOFORMALIZACION);

        return list;
    }
    
    private List<String> getNominaHeader(){
        List<String> list = new ArrayList<String>();
        
    	list.add(HeaderConstants.NOMINA_MES);
    	list.add(HeaderConstants.NOMINA_FOLIO);
    	list.add(HeaderConstants.NOMINA_RAZON_SOCIAL);
    	list.add(HeaderConstants.NOMINA_VERSION);
    	list.add(HeaderConstants.NOMINA_STATUS_ID);
    	list.add(HeaderConstants.NOMINA_STATUS_DESCRIPCION);
    	list.add(HeaderConstants.NOMINA_FECHA_CREACION);
    	list.add(HeaderConstants.NOMINA_FECHA_MODIFICACION);
    	list.add(HeaderConstants.NOMINA_EMISORA);
    	list.add(HeaderConstants.NOMINA_CR);
    	list.add(HeaderConstants.NOMINA_SUCURSAL);
    	list.add(HeaderConstants.NOMINA_NO_EMPLEADOS);
    	list.add(HeaderConstants.NOMINA_REP_LEGAL);
    	list.add(HeaderConstants.NOMINA_NUM_COLOCO);
    	list.add(HeaderConstants.NOMINA_COLOCO);
    	list.add(HeaderConstants.NOMINA_NUM_EBANKING);
    	list.add(HeaderConstants.NOMINA_EBANKING);
    	list.add(HeaderConstants.NOMINA_EMAIL);
    	list.add(HeaderConstants.NOMINA_AREA_CODE);
    	list.add(HeaderConstants.NOMINA_PHONE);
    	list.add(HeaderConstants.NOMINA_PHONE_EXT);
    	list.add(HeaderConstants.NOMINA_DIRECCION);
    	list.add(HeaderConstants.NOMINA_STATE);
    	list.add(HeaderConstants.NOMINA_ZIP_CODE);
    	return list;
    }
    
    
   //constructor para la lista de contratos todos los productos DTO
    public ReportLayout(List<ContractAllProductsDTO> search, ContractRemote contractBean) {
    	this.setContractBean(contractBean);
    	this.setContractsDTO(search);
    }
    
    //constructor para la lista de contractAquirerDTO (el x es dummy para crear el constructor)
    public ReportLayout(List<ContractAcquirerDTO> search, ContractRemote contractBean, int x) {
    	this.setContractBean(contractBean);
    	this.setContractAcquirerDTO(search);
    	reportType = ReportType.ADQUIRENTE; //como es el constructor para el DTO de adquierente, lo pongo directo
    }
    
    //constructor para la lista de contractSipDTO (el x es dummy para crear el constructor)
    public ReportLayout(List<ContractSipDTO> search, ContractRemote contractBean, boolean x) {
    	this.setContractBean(contractBean);
    	this.setContractSipDTO(search);
    	reportType = ReportType.SIP; //como es el constructor para el DTO de SIP, lo pongo directo
    }
    
  //constructor para la lista de contractNominaDTO (el x es dummy para crear el constructor)
    public ReportLayout(List<ContractNominaDTO> search, ContractRemote contractBean, String x) {
    	this.setContractBean(contractBean);
    	this.setContractNominaDTO(search);
    	reportType = ReportType.NOMINA; //como es el constructor para el DTO de nomina, lo pongo directo
    }
}
