
package com.banorte.contract.layout;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.banorte.contract.business.AttributeRemote;
import com.banorte.contract.business.BitacoraRemote;
import com.banorte.contract.business.CitiesRemote;
import com.banorte.contract.business.CommisionPlanRemote;
import com.banorte.contract.business.ContractAttributeRemote;
import com.banorte.contract.business.ContractRemote;
import com.banorte.contract.business.ContractStatusHistoryRemote;
import com.banorte.contract.business.DirectaLoadRemote;
import com.banorte.contract.business.ExecutiveBranchRemote;
import com.banorte.contract.business.ExecutiveRemote;
import com.banorte.contract.business.LoadFileRemote;
import com.banorte.contract.business.MaintanceRemote;
import com.banorte.contract.business.RecipentMailRemote;
import com.banorte.contract.business.StatusContractRemote;
import com.banorte.contract.business.StatusDirectaRemote;
import com.banorte.contract.dto.ContractAcquirerDTO;
import com.banorte.contract.dto.ContractAcquirerReportStatusDTO;
import com.banorte.contract.dto.ContractAllProductsDTO;
import com.banorte.contract.dto.ContractNominaDTO;
import com.banorte.contract.dto.ContractPayrollDTO;
import com.banorte.contract.dto.ContractSipDTO;
import com.banorte.contract.dto.ContractTpvDTO;
import com.banorte.contract.layout.handlers.AcquirerDailyStatusDTOHandler;
import com.banorte.contract.layout.handlers.BitacoraHandler;
import com.banorte.contract.layout.handlers.NominaDTOHandler;
import com.banorte.contract.layout.handlers.RowHandler;
import com.banorte.contract.layout.handlers.TpvDTOHandler;
import com.banorte.contract.model.Bitacora;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.ContractStatusHistory;
import com.banorte.contract.model.DirectaLoad;
import com.banorte.contract.model.FailReference;
import com.banorte.contract.model.LoadFile;
import com.banorte.contract.model.Maintance;
import com.banorte.contract.model.RecipentMail;
import com.banorte.contract.model.StatusDirecta;
import com.banorte.contract.model.WrapContractGenerate;
import com.banorte.contract.schedule.ScheduleServlet;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.BitacoraComparator;
import com.banorte.contract.util.DirectaStatus;
import com.banorte.contract.util.FTPProperties;
import com.banorte.contract.util.FileLayout;
import com.banorte.contract.util.Formatter;
import com.banorte.contract.util.MailUtil;
import com.banorte.contract.util.ReportGenerator.LayoutGenerator;
import com.banorte.contract.util.ReportType;
import com.banorte.contract.web.EjbInstanceManager;

/**
 *
 * @author Darvy Arch
 */
public class LayoutOperations {

    static Logger logger = Logger.getLogger(LayoutOperations.class.getName());
    private static LayoutOperations instance = null;
    
    public final Integer STATUSID_FORMALIZADO = 4; 
    public final Integer STATUSID_ACTIVACIONPTE = 10;
    public final Integer STATUSID_ACTIVADOINSTALADO = 12;
    public final Integer STATUSID_INSTALADOEX = 13;
    public final Integer FECHA_INSTALACION = 417;
    public final Integer OIP = 6;
    
    protected ContractRemote contractBean;
    protected MaintanceRemote maintanceBean;
    protected StatusContractRemote statusBean;
    protected AttributeRemote attributeBean;
    protected ContractStatusHistoryRemote historyBean;
    protected ContractAttributeRemote contractAttBean;
	protected ExecutiveRemote executiveBean;
	protected CitiesRemote citiesBean;	
    protected StatusDirectaRemote statusDirectaBean;
    protected DirectaLoadRemote directaLoadBean;
    protected ExecutiveBranchRemote executiveBranchBean;
    protected LoadFileRemote loadFileBean;
    protected RecipentMailRemote recipentMailBean;
    protected CommisionPlanRemote commisionPlanBean;
    protected BitacoraRemote bitacoraBean;
    
    protected Integer producto;
    
    
    private LayoutOperations() {
    	logger.setLevel(Level.WARNING);
        if ( contractBean == null ) {
            contractBean = (ContractRemote) EjbInstanceManager.getEJB(ContractRemote.class);
        }
        
        if ( statusBean == null ) {
            statusBean = (StatusContractRemote) EjbInstanceManager.getEJB(StatusContractRemote.class);
        }    
        
        if ( attributeBean == null ) {
        	attributeBean = (AttributeRemote) EjbInstanceManager.getEJB(AttributeRemote.class);
        }   
        
        if ( historyBean == null ) {
        	historyBean = (ContractStatusHistoryRemote) EjbInstanceManager.getEJB(ContractStatusHistoryRemote.class);
        }  
        if ( contractAttBean == null ) {
        	contractAttBean = (ContractAttributeRemote) EjbInstanceManager.getEJB(ContractAttributeRemote.class);
        }  
        
        if ( maintanceBean == null ) {
        	maintanceBean = (MaintanceRemote) EjbInstanceManager.getEJB(MaintanceRemote.class);
        }
        
		if (executiveBean == null) {
			executiveBean = (ExecutiveRemote) EjbInstanceManager.getEJB(ExecutiveRemote.class);
		}
        
		if (citiesBean == null) {
			citiesBean = (CitiesRemote) EjbInstanceManager.getEJB(CitiesRemote.class);
		}
		
        if ( statusDirectaBean == null ) {
            statusDirectaBean = (StatusDirectaRemote) EjbInstanceManager.getEJB(StatusDirectaRemote.class);
        }    
        		
        if ( directaLoadBean == null ) {
        	directaLoadBean = (DirectaLoadRemote) EjbInstanceManager.getEJB(DirectaLoadRemote.class);
        }  
        
        if ( executiveBranchBean == null ) {
        	executiveBranchBean = (ExecutiveBranchRemote) EjbInstanceManager.getEJB(ExecutiveBranchRemote.class);
        } 
        
        if ( loadFileBean == null ) {
        	loadFileBean = (LoadFileRemote) EjbInstanceManager.getEJB(LoadFileRemote.class);
        } 
        
        if ( recipentMailBean == null ) {
        	recipentMailBean = (RecipentMailRemote) EjbInstanceManager.getEJB(RecipentMailRemote.class);
        } 
        if(commisionPlanBean==null){
        	commisionPlanBean=(CommisionPlanRemote)EjbInstanceManager.getEJB(CommisionPlanRemote.class);
        }
        if(bitacoraBean==null){
        	bitacoraBean=(BitacoraRemote)EjbInstanceManager.getEJB(BitacoraRemote.class);
        }
        
    }

    public static synchronized LayoutOperations getInstance() {
        if ( instance == null)
            instance = new LayoutOperations();
        return instance;
    }

    public void startSend(boolean saveCopy, boolean addHeader, boolean isAutomatic) {
        MailUtil  mailUtil 							= new MailUtil();
        List<Contract> search 						= new ArrayList<Contract>();
        List<LoadFile> loadedFileInfoAll 			= new ArrayList<LoadFile>();
        List<FailReference> failReferenceAllInfo	= new ArrayList<FailReference>();
        List<RecipentMail> recipents      			= new ArrayList<RecipentMail>();
        WrapContractGenerate wrapContractGenerate 	= new WrapContractGenerate();
        WrapContractGenerate wrapAllInfo 			= new WrapContractGenerate();
        //String mensaje 								= ApplicationConstants.EMPTY_STRING;
        List<Contract> result = null;
        
        if(producto!=null && producto!=0){
        	result = contractBean.findByProductIdAndStatusAll(producto, STATUSID_FORMALIZADO);
        }else{//si el producto es nulo: "todos los productos"
        	result = contractBean.findByStatusIds(STATUSID_FORMALIZADO);
        }
        if(producto==null){
        	producto=99;
        }
        	
        recipents = recipentMailBean.getAllRecipentMail();
	        for ( Contract contract : result ) {
	            search.add(contractBean.findById(contract.getContractId()));
	        }
        //if ( search.size() > 0 ) {
        	try{
        		if(producto==1 || producto==99){
        			BemLayout bem = new BemLayout(search);
    	            bem.setContractBean(contractBean);
    	            bem.setStatusBean(statusBean);
    	            
    	            if ( bem.hasElements()) {
    	            	System.out.println("Generando Layout BEM...");
    	            	wrapContractGenerate = bem.createLayout(ApplicationConstants.PT_BEM_STR, saveCopy, addHeader,ApplicationConstants.PT_BEM);
    	            	wrapContractGenerate.setContractTypeName(ApplicationConstants.PT_BEM_STR);
    	            	
    	            	/*if(wrapContractGenerate.isLoadedFileInfoEmpty() ){
    	            		wrapContractGenerate.setLoadedFileInfo( mailUtil.emptyLoadFile(ApplicationConstants.PT_BEM, ApplicationConstants.FILE_TYPE_ALTAS) );
    	            	}*/
    	            }else{
    	            	System.out.println("No hay registros de BEM para generar Layout");
    	            	wrapContractGenerate.setLoadedFileInfo( mailUtil.emptyLoadFile(ApplicationConstants.PT_BEM, ApplicationConstants.FILE_TYPE_ALTAS) );
    	            	wrapContractGenerate.setContractTypeName(ApplicationConstants.PT_BEM_STR);
    	            	
    	            }

                	for(RecipentMail recipentMail : recipents){
                		if(recipentMail.getProductTypeId().equals(ApplicationConstants.PT_BEM)){
                			mailUtil.sendListMail(ApplicationConstants.SUBJECT_MAIL_GENERATION,recipentMail.getRecipent(),wrapContractGenerate,isAutomatic);
                		}
                	}
                    loadedFileInfoAll.addAll(wrapContractGenerate.getLoadedFileInfo());
                    failReferenceAllInfo.addAll(wrapContractGenerate.getListFailReference());
                    wrapContractGenerate = null;
        		}
        	}catch (Exception ex) {
				Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
				System.out.println("ERROR:" + ex);
			}
        	
        	try{
        		if(producto==2 || producto==99){
        			PayLayout pay = new PayLayout(search);
    	            pay.setContractBean(contractBean);
    	            pay.setStatusBean(statusBean);
    	            
    	            if ( pay.hasElements() ) {
    	            	System.out.println("Generando Layout Nomina...");
    	            	wrapContractGenerate = pay.createLayout("Nomina", saveCopy, addHeader, ApplicationConstants.PT_NOMINA);	
    	            	wrapContractGenerate.setContractTypeName("Nomina");
    	            	
    	            	/*if(wrapContractGenerate.isLoadedFileInfoEmpty() ){
    	            		wrapContractGenerate.setLoadedFileInfo( mailUtil.emptyLoadFile(ApplicationConstants.PT_NOMINA, ApplicationConstants.FILE_TYPE_ALTAS) );
    	            	}*/
    	            }else{
    	            	System.out.println("No hay registros de Nomina para generar Layout");
    	            	wrapContractGenerate 	= new WrapContractGenerate();
    	            	wrapContractGenerate.setLoadedFileInfo( mailUtil.emptyLoadFile(ApplicationConstants.PT_NOMINA, ApplicationConstants.FILE_TYPE_ALTAS) );
    	            	wrapContractGenerate.setContractTypeName(ApplicationConstants.PT_NOMINA_STR);
    	            }
    	            
    	            for(RecipentMail recipentMail : recipents){
    	            	if(recipentMail.getProductTypeId().equals(ApplicationConstants.PT_NOMINA)){
    	           			mailUtil.sendListMail(ApplicationConstants.SUBJECT_MAIL_GENERATION,recipentMail.getRecipent(),wrapContractGenerate,isAutomatic);	
    	           		}
    	            }
    	            	loadedFileInfoAll.addAll(wrapContractGenerate.getLoadedFileInfo());
    	                failReferenceAllInfo.addAll(wrapContractGenerate.getListFailReference());	   
    	                wrapContractGenerate = null;	
        		}
        	}catch (Exception ex) {
				Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
				System.out.println("ERROR:" + ex);
			}

        	try{
        		if(producto==3 || producto==99){
        			AcquirerLayout acquirer = new AcquirerLayout(search);
    	            acquirer.setContractBean(contractBean);
    	            acquirer.setStatusBean(statusBean);
    	            
    	            if ( acquirer.hasElements() ) {
    	            	System.out.println("Generando Layout Adquirente...");
    	            	wrapContractGenerate = acquirer.createLayout("Adquirente", saveCopy, addHeader,ApplicationConstants.PT_ADQ);
    	            	wrapContractGenerate.setContractTypeName("Adquirente");
    	            	/*  if(wrapContractGenerate.isLoadedFileInfoEmpty() ){
    	            		wrapContractGenerate.setLoadedFileInfo( mailUtil.emptyLoadFile(ApplicationConstants.PT_ADQ, ApplicationConstants.FILE_TYPE_ALTAS) );
    	            	} */
    	            }else{
    	            	System.out.println("No hay registros de Adquirente para generar Layout");
    	            	wrapContractGenerate 	= new WrapContractGenerate();
    	            	wrapContractGenerate.setLoadedFileInfo( mailUtil.emptyLoadFile(ApplicationConstants.PT_ADQ, ApplicationConstants.FILE_TYPE_ALTAS) );
    	            	wrapContractGenerate.setContractTypeName(ApplicationConstants.PT_ADQ_STR);
    	            }
                	
                	for(RecipentMail recipentMail : recipents){
                		if(recipentMail.getProductTypeId().equals(ApplicationConstants.PT_ADQ)){
               				mailUtil.sendListMail(ApplicationConstants.SUBJECT_MAIL_GENERATION,recipentMail.getRecipent(),wrapContractGenerate,isAutomatic);	
                		}
                	}
                	loadedFileInfoAll.addAll(wrapContractGenerate.getLoadedFileInfo());
                    failReferenceAllInfo.addAll(wrapContractGenerate.getListFailReference());	   
                    wrapContractGenerate = null;	
        		}
                
        	}catch (Exception ex) {
				Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
				System.out.println("ERROR:" + ex);
			}
            
        	
        	 try{
        		 if(producto==4 || producto==99){
        			CDLayout cd = new CDLayout(search);
     	            cd.setContractBean(contractBean);
     	            cd.setStatusBean(statusBean);
     	            
     	            if(cd.hasElements()){
     	            	System.out.println("Generando Layout Cobranza...");
     	            	wrapContractGenerate = cd.createLayout(ApplicationConstants.PREFIJO_CD, saveCopy, addHeader,ApplicationConstants.PT_CD);
     	            	wrapContractGenerate.setContractTypeName(ApplicationConstants.PREFIJO_CD);
     	            	
     	            	/*if(wrapContractGenerate.isLoadedFileInfoEmpty() ){
     	            		wrapContractGenerate.setLoadedFileInfo( mailUtil.emptyLoadFile(ApplicationConstants.PT_CD, ApplicationConstants.FILE_TYPE_ALTAS) );
     	            	}*/
     	            }else{
     	            	System.out.println("No hay registros de Cobranza para generar Layout");
     	            	wrapContractGenerate 	= new WrapContractGenerate();
     	            	wrapContractGenerate.setLoadedFileInfo( mailUtil.emptyLoadFile(ApplicationConstants.PT_CD, ApplicationConstants.FILE_TYPE_ALTAS) );
     	            	wrapContractGenerate.setContractTypeName(ApplicationConstants.PT_CD_STR);
     	            }

     	            for(RecipentMail recipentMail : recipents){
                 		if(recipentMail.getProductTypeId().equals(ApplicationConstants.PT_CD)){
               				mailUtil.sendListMail(ApplicationConstants.SUBJECT_MAIL_GENERATION,recipentMail.getRecipent(),wrapContractGenerate,isAutomatic);	
                 		}
                 	}
                 	loadedFileInfoAll.addAll(wrapContractGenerate.getLoadedFileInfo());
                     failReferenceAllInfo.addAll(wrapContractGenerate.getListFailReference());	   
                     wrapContractGenerate = null;
        		 }
	            
        	}catch (Exception ex) {
				Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
				System.out.println("ERROR:" + ex);
			}
        	
            try{
            	if(producto==5 || producto==99){
            		MttoBEMLayout mtto = new MttoBEMLayout(search);
    	            mtto.setContractBean(contractBean);
    	            mtto.setStatusBean(statusBean);
    	            
    	            if(mtto.hasElements()){
    	            	System.out.println("Generando Layout Mtto...");
    	            	wrapContractGenerate = mtto.createLayout(ApplicationConstants.PREFIJO_MTTOS, saveCopy, addHeader,ApplicationConstants.PT_MB);
    	            	wrapContractGenerate.setContractTypeName("MTTOS");
    	            	
    	            	/*if(wrapContractGenerate.isLoadedFileInfoEmpty() ){
    	            		wrapContractGenerate.setLoadedFileInfo( mailUtil.emptyLoadFile(ApplicationConstants.PT_MB, ApplicationConstants.FILE_TYPE_ALTAS) );
    	            	}*/
    	            }else{
    	            	System.out.println("No hay registros de Mtto para generar Layout");
    	            	wrapContractGenerate 	= new WrapContractGenerate();
    	            	wrapContractGenerate.setLoadedFileInfo( mailUtil.emptyLoadFile(ApplicationConstants.PT_MB, ApplicationConstants.FILE_TYPE_ALTAS) );
    	            	wrapContractGenerate.setContractTypeName(ApplicationConstants.PT_MB_STR);
    	            }

    	            for(RecipentMail recipentMail : recipents){
    	            		if(recipentMail.getProductTypeId().equals(ApplicationConstants.PT_MB)){
    	            			mailUtil.sendListMail(ApplicationConstants.SUBJECT_MAIL_GENERATION,recipentMail.getRecipent(),wrapContractGenerate,isAutomatic);	
    	            		}
    	            	}
                	loadedFileInfoAll.addAll(wrapContractGenerate.getLoadedFileInfo());
                    failReferenceAllInfo.addAll(wrapContractGenerate.getListFailReference());	   
                    wrapContractGenerate = null;
            	}
	            
            }catch (Exception ex) {
				Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
				System.out.println("ERROR:" + ex);
			} 
 
        search = null;
        result = null;
        
        search = new ArrayList();
        result = contractBean.findByStatusIds(STATUSID_ACTIVACIONPTE);
        for ( Contract contract : result ) {
            search.add(contractBean.findById(contract.getContractId()));
        }
        
		if (producto == 1 || producto == 99) {
			BemActivateLayout bem = new BemActivateLayout(search);
			bem.setContractBean(contractBean);
			bem.setStatusBean(statusBean);

			if (bem.hasElements()) {
				System.out.println("Generando Layout Activacion BEM...");
				wrapContractGenerate = null;
				wrapContractGenerate = bem.createLayout("BEMActivacion",saveCopy, addHeader, ApplicationConstants.PT_BEM);
				wrapContractGenerate.setContractTypeName("BEMActivacion");

			} else {
				System.out.println("No hay registros de BEMActivacion para generar Layout");
				wrapContractGenerate = new WrapContractGenerate();
				wrapContractGenerate.setLoadedFileInfo(mailUtil.emptyLoadFile(ApplicationConstants.PT_BEM,ApplicationConstants.FILE_TYPE_ALTAS));
				wrapContractGenerate.setContractTypeName("BEMActivacion");
			}

			for (RecipentMail recipentMail : recipents) {
				if (recipentMail.getProductTypeId().equals(ApplicationConstants.PT_BEM)) {
					mailUtil.sendListMail(ApplicationConstants.SUBJECT_MAIL_GENERATION,recipentMail.getRecipent(),wrapContractGenerate, isAutomatic);
				}
			}

			loadedFileInfoAll.addAll(wrapContractGenerate.getLoadedFileInfo());
			failReferenceAllInfo.addAll(wrapContractGenerate.getListFailReference());
			wrapContractGenerate = null;
		}
        
        for(LoadFile info : loadedFileInfoAll ){
        	loadFileBean.save(info);
        }
        
        wrapAllInfo.setLoadedFileInfo(loadedFileInfoAll);
        wrapAllInfo.setListFailReference(failReferenceAllInfo);

        for(RecipentMail recipentMail : recipents){
    		if(recipentMail.getProductTypeId().equals(ApplicationConstants.PT_TODOS)){
    			mailUtil.sendListMail(ApplicationConstants.SUBJECT_MAIL_GENERATION,recipentMail.getRecipent(),wrapAllInfo,isAutomatic);
    		}
    	}

        search = null;
        result = null;
        
        return;
    }

    public void startMaintanceProcess(Date operationDate) {
    	
    	
    	//boolean collectSucceed  = false;
    	List<Maintance> remoteInfo = new ArrayList<Maintance>();
    	
    	//Definir la fecha de la información que se va a procesar, T-1
		Calendar lastOperationDay = new GregorianCalendar();
		lastOperationDay.setTime(operationDate);
		//T-1
		lastOperationDay.add(Calendar.DAY_OF_MONTH, -1);

		
    	//Obtener la informacion de los mantenientos del dia T-1, que TI deja en un archivo.
    	try{
    		
    		LayoutOperationsResponse response = new LayoutOperationsResponse();
        	response.setMaintanceBean(maintanceBean);

    		remoteInfo = response.processRemoteMaintance(lastOperationDay.getTime());
    		
    	} catch ( IOException e ) {
    		logger.log(Level.SEVERE, "Ha ocurrido un error en la recopilacion de la información(Operaciones-HEB)", e);
    	}

    	//Obtener Altas de cuentas( con estatus de activadas)(BD)
        List<Contract> search = new ArrayList<Contract>();

        Date startDate = lastOperationDay.getTime();
        lastOperationDay.roll(Calendar.DAY_OF_MONTH, true);
        Date endDate  = lastOperationDay.getTime();
        List<Contract> result = contractBean.findByProductIdAndStatusIds(1, STATUSID_ACTIVADOINSTALADO, startDate, endDate);

        for ( Contract contract : result ) {
            search.add(contractBean.findById(contract.getContractId()));
        }
             
        try{
        	
	        MaintanceLayout localInfo = new MaintanceLayout(search);
	        localInfo.setMaintances(remoteInfo);
	        localInfo.setMaintanceBean(maintanceBean);
	        
	        if ( localInfo.hasElements() ) {
	        	localInfo.save(operationDate);
	        } else {
	            logger.log(Level.INFO, "No se encontraron mantenimientos para guardar");
	        }
        
		} catch ( Exception e ) {
			logger.log(Level.SEVERE, "Ha ocurrido un error en el guardado de los Mantenimientos", e);
		}
        
    }

  
	public void startMaintanceSend() {

		List<Maintance> result = new ArrayList<Maintance>();

		// Obtener de la tabla de MAINTANCE, las altas y mantenimientos que se
		// encuentren con estatus de procesado.

		result = maintanceBean.findByStatusIds(DirectaStatus.HEB_PROCESADO.value());

		if (result.size() > 0) {

			// Enviar a Tralix la relación de altas y mantenimientos
			 this.startTralixSend(result, false);

		} else {
			logger.log(Level.INFO,"No existe informacion para enviarse a Tralix(Status 50)");
		}

		// Agregar a la lista los mantenimientos con algun estatus de reenvio
		result.addAll(this.getResendMaintances());

		if (result.size() > 0) {

			this.startDirectaSend(result, false);    // Comentar para Pruebas, Para impedir que se envie la informacion a la BD de Directa y no guarde nada en su BD!

		} else {
			logger.log(Level.INFO, "No existe informacion para enviarse o reenviarse a Directa");
			result = null;
		}

	}
        
    private List<Maintance> getResendMaintances(){
    	
        //Obtener los mantenimiento del día anterior que se van a volver a reenviar a directa.  
        List<Maintance> resendMaintenance = new ArrayList<Maintance>();

        
		ArrayList<Integer> statusIds = new ArrayList<Integer>();

		//Lista de estatus que requieren un reenvio.
		statusIds.add(DirectaStatus.CE_DESCONFIA.value());
		statusIds.add(DirectaStatus.CNE_NO_SE_ENCUENTRA.value());
		statusIds.add(DirectaStatus.CNE_CLIENTE_OCUPADO.value());
		statusIds.add(DirectaStatus.SC_GRABADORA.value());
		statusIds.add(DirectaStatus.SC_NO_CONTESTAN.value());
		statusIds.add(DirectaStatus.SC_TEL_OCUPADO.value());
		
		//List<Maintance> list = maintanceBean.findByStatusIds(statusIds);
		List<Maintance> list = new ArrayList<Maintance>();
		
		for(Integer status : statusIds){
			list.addAll(maintanceBean.findByStatusIds(status));
		}

        Integer resend =0;
        for ( Maintance maintance : list ) {
        	
        	resend = maintance.getResend()==null? 0:maintance.getResend();
        	
        	//Aplicar reenvio si todavia no llega al maximo de reenvios: 1
        	if (resend < 1){
        		Calendar today = Calendar.getInstance();
				
				maintance.setCreationDate(new Formatter().formatDate(today.getTime()).getTime());
        		resendMaintenance.add(maintance);
        	}
        }
        
        return resendMaintenance;
    }
    
    private void startTralixSend(List<Maintance> maintances, boolean saveCopy){
    
  
            MaintanceLayout layout = new MaintanceLayout();
            layout.setMaintanceBean(maintanceBean);
            layout.setMaintances(maintances);
            
            if ( layout.hasElements() ) {
            	//Enviar Altas
            	layout.createTxtLayout("ABEM", saveCopy);

            	//Enviar Mantenimientos
            	layout.createTxtLayout("MBEM", saveCopy);
            	
            }
            
    }
    
    private void startDirectaSend(List<Maintance> maintances, boolean saveCopy){
        
    	  
        MaintanceLayout layout = new MaintanceLayout();
        layout.setMaintanceBean(maintanceBean);
        layout.setMaintances(maintances);
        
        if ( layout.hasElements() ) {

        	//Enviar Altas/Mantenimientos a Directa
        	try {
        		
				//layout.createXlsLayout("DIRECTA", saveCopy); 
				
				StatusDirecta statusSent = new StatusDirecta(DirectaStatus.HEB_ENVIADO_DIRECTA.value());
				Calendar today = Calendar.getInstance();
				DirectaLoad loadInDirecta;
				
				for(Maintance entity : maintances){
					//Crear una instancia de DirectaLoad en base a un mantenimiento
					loadInDirecta = layout.parseToDirectaLoad(entity);

					if(loadInDirecta!=null){
						//Guardar la informacion en Directa
						try{
												
							directaLoadBean.save(loadInDirecta);
							
							if (entity.getStatus().getId().intValue() == DirectaStatus.HEB_PROCESADO.value().intValue()){
						
								//Cambiar el estatus de los mantenimientos que tengan estatus PROCESADO a estatus ENVIADO_DIRECTA
								entity.setStatus(statusSent);
								entity.setSentDirectaDate(today.getTime());
								
								maintanceBean.update(entity);
							}else{
								
								//Incrementar el contador de reenvios
								Integer resend = entity.getResend();
								
								entity.setResend(resend + 1);
		
								entity.setSentDirectaDate(today.getTime());
								
								maintanceBean.update(entity);
							}
						
						}catch(Exception e){
							//Escribir en el log el registro que no se pudo enviar guardar en directa
							logger.log(Level.INFO, "LayoutOperations.startDirectaSend(ID="+entity.getId()+"): Error en el proceso de envío de info a Directa. " +e.getMessage());
						}
					}
				}
				
				
			} catch (Exception e) {
				logger.log(Level.SEVERE, null, e);
			}
        }
        
    }
    
    public void startRecipeDirecta(Date startOperationDate, Date endOperationDate){
        try {
            LayoutOperationsResponse response = new LayoutOperationsResponse();
            response.setMaintanceBean(maintanceBean);
            response.setStatusDirectaBean(statusDirectaBean);
            
//    		//TODO:Eliminar despues de pruebas
/*    		Calendar lastOperationDay = new GregorianCalendar(); 
    		lastOperationDay.setTime(operationDate); 
    		lastOperationDay.add(Calendar.DAY_OF_MONTH, -1);
    		operationDate = lastOperationDay.getTime(); */
            
            response.processRemoteDirectaAnswers(startOperationDate, endOperationDate);
            
        } catch ( IOException e ) {
        	logger.log(Level.SEVERE, "Ha ocurrido un error en la recepcion de la informacion", e);
        }
        
    }
    
    public void startRecipeExecutives(){
        try {
            LayoutOperationsResponse response = new LayoutOperationsResponse();
            response.setExecutiveBean(executiveBean);
            
            response.processRemoteExecutive();
            
        } catch ( IOException e ) {
        	logger.log(Level.SEVERE, "Ha ocurrido un error en la recepcion de la informacion", e);
        }
        
    }
    
    public void startRecipeCitiesAndStates(){
        try {
            LayoutOperationsResponse response = new LayoutOperationsResponse();
            response.setCitiesBean(citiesBean);
            
            response.processRemoteCitiesAndStates();
            
        } catch ( IOException e ) {
        	logger.log(Level.SEVERE, "Ha ocurrido un error en la recepcion de la informacion", e);
        }
        
    }
    
    public void startRecipe(boolean saveCopy,boolean isAutomatic) {

            LayoutOperationsResponse response = new LayoutOperationsResponse();
            response.setContractBean(contractBean);
            response.setStatusBean(statusBean);
            
            try {

            	response.processRemoteAnswers(isAutomatic);            	
            	
            } catch ( IOException e ) {
                logger.log(Level.SEVERE, "(IOException)Ha ocurrido un error  en la recepcion de la informacion", e);
            } catch ( Exception e ) {
                logger.log(Level.SEVERE, "Ha ocurrido un error en la recepcion de la informacion", e);
            }
    }
    
    public void startLecturaCuentasMujerPyME(boolean saveCopy,boolean isAutomatic) {
        LayoutOperationsResponse response = new LayoutOperationsResponse();
        try {
        	response.processRemoteAnswersMujerPyME(isAutomatic); 
        } catch ( IOException e ) {
            logger.log(Level.SEVERE, "(IOException)Ha ocurrido un error  en la recepcion de la informacion", e);
        } catch ( Exception e ) {
            logger.log(Level.SEVERE, "Ha ocurrido un error en la recepcion de la informacion", e);
        }
}
    public void startRecipeInstall(boolean saveCopy) {

            LayoutOperationsInstall install = new LayoutOperationsInstall();
            install.setContractBean(contractBean);
            install.setStatusBean(statusBean);
            install.setAttributeBean(attributeBean);
            try {
                install.processRemote();
            } catch ( IOException e ) {
                logger.log(Level.SEVERE, "Ha ocurrido un error en la recepcion de la informacion", e);
            }

    }
    
    public void startRecipeExecutiveBranch(){
        try {
            LayoutOperationsResponse response = new LayoutOperationsResponse();
            response.setExecutiveBranchBean(executiveBranchBean);
            
            response.processRemoteExecutiveBranch();
            
        } catch ( IOException e ) {
        	logger.log(Level.SEVERE, "Ha ocurrido un error en la recepcion de la informacion", e);
        }
        
    }
    
    public boolean startRecipeCommisions(){
    	boolean flag = false;
    	try {
            LayoutOperationsResponse response = new LayoutOperationsResponse();
            response.setCommisionPlanBean(commisionPlanBean);
            flag= response.processRemoteCommisionPlan();
        } catch ( IOException e ) {
        	logger.log(Level.SEVERE, "Ha ocurrido un error en la recepcion de la informacion", e);
        }
        return flag;
    }
    
    public void startSendOIP(boolean saveCopy) {
        List<Contract> search = new ArrayList<Contract>();

       //List<Contract> result = contractBean.findByStatusIds(STATUSID_INSTALADOEX); //Buscar cuando el productid=6, estatus =13 y fecha de estatus 13 = x
       Formatter format = new Formatter();
   		Calendar cal = Calendar.getInstance();
   		if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
   			cal.add(Calendar.DATE, -3);
   		}else{
   			cal.add(Calendar.DATE, -1);
   		}
   		String strDate = format.formatDateToString(cal.getTime());
   			
   		List<ContractStatusHistory> histResult = historyBean.findByStatusAndDate(STATUSID_INSTALADOEX, new java.sql.Date(format.formatDate(strDate).getTimeInMillis()));
        
       //List<ContractStatusHistory> histResult = historyBean.findByStatusAndDate(STATUSID_INSTALADOEX, new java.sql.Date(format.formatDate("16/08/2010").getTimeInMillis()));
       for ( ContractStatusHistory hist : histResult ) {
    	   if(hist.getContract().getProduct().getProductid().equals(this.OIP)){
    		   Contract cont = hist.getContract();
    		   List <ContractAttribute> cAttCol = new ArrayList<ContractAttribute>();
    		   if(contractAttBean.findByContractidAndAttributeid(cont.getContractId(), FECHA_INSTALACION)!=null){
    			   cAttCol.add(contractAttBean.findByContractidAndAttributeid(cont.getContractId(), FECHA_INSTALACION));
    		   }
    		   cont.setContractAttributeCollection(cAttCol);
    		   search.add(cont);
    	   }
           
       }
        if ( search.size() > 0 ) {
            OIPLayout oip = new OIPLayout(search);
	        if ( oip.hasElements() ) {
	            oip.createTxtLayout("OIP", saveCopy);
	        }
        }

    }
    
    /*
     * 
     */
    public void getAdquirerDetailReport(Calendar startDate, Calendar endDate){
        List<Contract> result = contractBean.findByCreationDateRangeToReports(startDate, endDate, true);
        ReportLayout report = new ReportLayout(result, ReportType.ADQUIRENTE, this.contractBean);
		if (report.hasElements()) {

			try {
				report.createXlsLayout(ReportType.ADQUIRENTE.toString(), false);

			} catch (IOException e) {
				logger.log(Level.SEVERE,"Ha ocurrido un error en la recepcion de la informacion",e);
			}
		} else {
			logger.log(Level.INFO, "No Existen Registros");
		}

    }
    
    /*
     * 
     */
    public void getSIPDetailReport(Calendar startDate, Calendar endDate) {
        List<Contract> result = contractBean.findByCreationDateRangeToReports(startDate, endDate, true);
        ReportLayout report = new ReportLayout(result, ReportType.SIP,this.contractBean);
        if ( report.hasElements() ) {
        	try {
				report.createXlsLayout(ReportType.SIP.toString(), false);
			} catch (IOException e) {
	        	logger.log(Level.SEVERE, "Ha ocurrido un error en la recepcion de la informacion", e);
			}
        }else{
			logger.log(Level.INFO, "No Existen Registros");
       }
    }
    
    /*
     * 
     */
    public void getAllProductsDetailReport(Calendar startDate, Calendar endDate){
        List<Contract> result = contractBean.findByCreationDateRangeToReports(startDate, endDate, false);
        ReportLayout report = new ReportLayout(result, ReportType.ALL_PRODUCTS, this.contractBean);
        if ( report.hasElements() ) {
        	try{
				report.createXlsLayout(ReportType.ALL_PRODUCTS.toString(), false);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Ha ocurrido un error en la recepcion de la informacion", e);
			}
       }else{
			logger.log(Level.INFO, "No Existen Registros");
       }
    }    
    
    /**Create All Products Report by date range
     * @param startDate
     * @param endDate
     */
    public void getAllProductsDetailReportDTO(Calendar startDate, Calendar endDate, String filtroFecha, String filtroStatus){
        List<ContractAllProductsDTO> result = contractBean.findByCreationDateRangeToReportsDTO(startDate, endDate, filtroFecha, filtroStatus);
        ReportLayout report = new ReportLayout(result, this.contractBean);
        if ( report.getContractsDTO()!=null && !report.getContractsDTO().isEmpty() ) {
        	try{
				report.createXlsLayoutAllProductsDTO(ReportType.ALL_PRODUCTS.toString(), false); 
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Ha ocurrido un error en la recepcion de la informacion", e);
			}
       }else{
			logger.log(Level.INFO, "No Existen Registros");
       }

    }

    /**Create Adquirer Report by date range
     * @param startDate
     * @param endDate
     */
    public void getAdquirerReportDTO(Calendar startDate, Calendar endDate, String filtroFecha, String filtroStatus, String filtroGarantia){
    	List<ContractAcquirerDTO> result = contractBean.findByDateRangeToAcquirerReportDTO(startDate, endDate, filtroFecha, filtroStatus, filtroGarantia);
        ReportLayout report = new ReportLayout(result, this.contractBean, 0);
        if (report.getContractAcquirerDTO()!=null && !report.getContractAcquirerDTO().isEmpty()) {
        	try{
				report.createXlsLayoutAcquirerDTO(ReportType.ADQUIRENTE.toString(), false); 
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Ha ocurrido un error en la recepcion de la informacion", e);
			}
       }else{
			logger.log(Level.INFO, "No Existen Registros");
       }
    }
    
    public void getSipReportDTO(Calendar startDate, Calendar endDate, String filtroFecha, String filtroStatus){
    	List<ContractSipDTO> result = contractBean.findByFilterSipReportDTO(startDate, endDate, filtroFecha, filtroStatus);
    	ReportLayout report = new ReportLayout(result, this.contractBean, false);
    	if(report.getContractSipDTO()!=null && !report.getContractSipDTO().isEmpty()){
    		try{
    			report.createXlsLayoutSipDTO(ReportType.SIP.toString(), false);
    		}catch(IOException e){
    			logger.log(Level.SEVERE, "Ha ocurrido un error en la recepcion de la informacion", e);
    		}
    	}else{
    		logger.log(Level.INFO, "No Existen Registros");
    	}
    }
    
	public Integer getProducto() {
		return producto;
	}

	public void setProducto(Integer producto) {
		this.producto = producto;
	}
	
	
	/**
	 * Nuevo reporte para generar el reporte de bitácora usando Handlers
	 * @param startDate
	 * @param endDate
	 * @param referenceNumber
	 */
	public void generarReporteBitacora(Calendar startDate, Calendar endDate, String referenceNumber){
		List<Bitacora> result=null;
    	if(referenceNumber!=null && !referenceNumber.isEmpty()){
    		result=bitacoraBean.findUserBitacora(referenceNumber, startDate, endDate);
    	}else{
    		result=bitacoraBean.findBitacora(startDate, endDate);	
    	}
		
    	if(result!=null && !result.isEmpty()){
    		Collections.sort(result, new BitacoraComparator());
    		BitacoraHandler handler=new BitacoraHandler();
    		List<List<String>> registros=handler.castAllObjects(result);
    		generarReporte(handler, registros, true,"Reportes",LayoutGenerator.DocumentType.EXCEL);
    	}
    }
    /**
     * Método para crear el reporte diario de altas tpv y nomina
     * @param startDate
     * @param endDate
     */
	public void createTpvPayrollDTOReport(){
		List<ContractPayrollDTO> resultPayroll=null;
		List<ContractTpvDTO>resultTpv=null;
		//fechas
		Calendar fecha=Calendar.getInstance();
		Date fechaInicio=null;
		Date fechaFin=null;
		
		fecha.add(Calendar.DATE, -90);//para TPV 90 días
		fecha.set(Calendar.HOUR_OF_DAY,0);
		fecha.set(Calendar.MINUTE, 0);
		fecha.set(Calendar.SECOND, 0);
		fecha.set(Calendar.MILLISECOND, 0);
		fechaInicio=fecha.getTime();//se pone la fecha de inicio
		
		fecha=Calendar.getInstance();//se vuelve a poner el día actual
		fecha.add(Calendar.DATE, -1);//se pone el día de ayer
		fecha.set(Calendar.HOUR_OF_DAY, 23);//se pone el ultimo segundo del dia
		fecha.set(Calendar.MINUTE, 59);
		fecha.set(Calendar.SECOND, 59);
		fecha.set(Calendar.MILLISECOND, 99);

		fechaFin=fecha.getTime();//se agrega a fecha final (día completo)

		//se obtienen reporte TPV
		resultTpv=contractBean.findByDateTpvReportDTO(fechaInicio, fechaFin);
		
		fecha=Calendar.getInstance(); //actual
		fecha.add(Calendar.DATE,-91);//para Nomina son 91 dias
		fecha.set(Calendar.HOUR_OF_DAY,0);
		fecha.set(Calendar.MINUTE, 0);
		fecha.set(Calendar.SECOND, 0);
		fecha.set(Calendar.MILLISECOND, 0);
		fechaInicio=fecha.getTime();//se pone la fecha de inicio
		fecha=Calendar.getInstance();//se vuelve a poner el día actual
		fecha.add(Calendar.DATE, -1);//se pone el día de ayer
		fecha.set(Calendar.HOUR_OF_DAY, 23);//se pone el ultimo segundo del dia
		fecha.set(Calendar.MINUTE, 59);
		fecha.set(Calendar.SECOND, 59);
		fecha.set(Calendar.MILLISECOND, 99);
		fechaFin=fecha.getTime();//se agrega a fecha final (día completo)
		
		//se obtiene reporte nomina
		resultPayroll=contractBean.findByDatePayrollReportDTO(fechaInicio, fechaFin);
		
		//si existen datos en ambos reportes se eliminan los existentes en FTP
		if(resultTpv!=null&&!resultTpv.isEmpty()&&resultPayroll!=null && !resultPayroll.isEmpty()){
			eliminarReporteAltas();
		}
		
		System.out.println("... generando reporte Altas_TPV...");
		TpvDTOHandler handlerTpv=new TpvDTOHandler();
		if(resultTpv!=null&&!resultTpv.isEmpty()){
			List<List<String>>registrosTpv=handlerTpv.castAllObjects(resultTpv);
			generarReporte(handlerTpv, registrosTpv, true, "ebanking", LayoutGenerator.DocumentType.PLAIN_TEXT);	
		}
		System.out.println("... generando reporte Altas_Nomina...");
		NominaDTOHandler handlerNomina= new NominaDTOHandler();
		if(resultPayroll!=null&&!resultPayroll.isEmpty()){
			List<List<String>>registrosNomina=handlerNomina.castAllObjects(resultPayroll);
			generarReporte(handlerNomina, registrosNomina, true, "ebanking",LayoutGenerator.DocumentType.PLAIN_TEXT);	
		}
	}
	
    private void generarReporte(RowHandler handler, List<List<String>> registros,boolean headers,String carpeta, LayoutGenerator.DocumentType type){
    	LayoutTempleteContract template=new LayoutTempleteContract();
    	byte[] contenido;
		try {
			if(type.equals(LayoutGenerator.DocumentType.PLAIN_TEXT)){
				contenido=template.createTextLayout(registros, "\t", handler.getHeaders());
			}else{
				contenido = template.createLayout(handler.getLayoutName(), registros, headers, handler.getHeaders());	
			}
			//se guarda el reporte generado en el FTP
			LayoutGenerator.saveRemoteFile(handler.getLayoutName(), type, contenido, true,carpeta);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error al crear el reporte...");
		}
    }
    
    private void eliminarReporteAltas(){
    	FTPProperties fprops = new FTPProperties();
    	FileLayout fl = new FileLayout();
    	try{
    		//obtiene los reportes existentes
    		Map<String, InputStream> altas=fl.buscarReportesAltas(fprops);	
	    	if(altas!=null){//si hay reportes
		    		for(String reporte:altas.keySet()){
		    			System.out.println("reporte: "+reporte);
		        		fl.eliminarReportesAltas(reporte, fprops, fprops.getPathAltas());
		    		}
	    	}else{
	    		System.out.println("No se encontraron archivos para eliminar");
	    	}
    	}catch(IOException e){
	    		e.printStackTrace();
	    		System.out.println(e.getMessage());
	    }
    }
    	
    /**Create Nomina Report Mensual by date range
     * @param startDate
     * @param endDate
     */
    public void getNominaMonthlyReportDTO(String startDate, String endDate){
        List<ContractNominaDTO> result = contractBean.findByDateRangeToNominaMonthlyReportDTO(startDate, endDate);
        ReportLayout report = new ReportLayout(result, this.contractBean, "x");
        if (report.getContractNominaDTO()!=null && !report.getContractNominaDTO().isEmpty()) {
        	try{
				report.createXlsLayoutNominaDTO(ReportType.NOMINA.toString(), false);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Ha ocurrido un error en la recepcion de la informacion", e);
			}
       }else{
			logger.log(Level.INFO, "No Existen Registros");
       }
    }
    
    /**
     * Método para crear el reporte diario de altas tpv y nomina
     * @param startDate
     * @param endDate
     */
	public void createAdquirerStatusDTOReport(Calendar startDate, Calendar endDate, boolean isAutomatic){
		List<ContractAcquirerReportStatusDTO> resultAcquirerReportStatus=null;
		//fechas
		Calendar fecha=Calendar.getInstance();
		Date fechaInicioDate=null;
		Date fechaFinDate=null;
		String fechaInicio=null;
		String fechaFin=null;
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		if(isAutomatic){
			fecha.add(Calendar.DATE, -1);//para el día anterior
			fecha.set(Calendar.HOUR_OF_DAY,0);
			fecha.set(Calendar.MINUTE, 0);
			fecha.set(Calendar.SECOND, 0);
			fecha.set(Calendar.MILLISECOND, 0);
			fechaInicioDate=fecha.getTime();//se pone la fecha de inicio
			fechaInicio=formateador.format(fechaInicioDate);
			
			fecha=Calendar.getInstance();//se vuelve a poner el día actual
			fecha.add(Calendar.DATE, -1);//se pone el día de ayer
			fecha.set(Calendar.HOUR_OF_DAY, 23);//se pone el ultimo segundo del dia
			fecha.set(Calendar.MINUTE, 59);
			fecha.set(Calendar.SECOND, 59);
			fecha.set(Calendar.MILLISECOND, 99);			
			fechaFinDate=fecha.getTime();//se agrega a fecha final (día completo)
			fechaFin=formateador.format(fechaFinDate);
		}else{
			if(startDate!=null && endDate != null) {
				startDate.set(Calendar.HOUR_OF_DAY,0);
				startDate.set(Calendar.MINUTE, 0);
				startDate.set(Calendar.SECOND, 0);
				startDate.set(Calendar.MILLISECOND, 0);			
				fechaInicioDate=startDate.getTime();//se pone la fecha de inicio
				fechaInicio=formateador.format(fechaInicioDate);
				
				endDate.set(Calendar.HOUR_OF_DAY, 23);//se pone el ultimo segundo del dia
				endDate.set(Calendar.MINUTE, 59);
				endDate.set(Calendar.SECOND, 59);
				endDate.set(Calendar.MILLISECOND, 99);			
				fechaFinDate=endDate.getTime();//se agrega a fecha final (día completo)	
				fechaFin=formateador.format(fechaFinDate);
			}		
		}
		//se obtienen reporte AdquirerStatus	
		resultAcquirerReportStatus=contractBean.findByDateAcquirerReportStatus(fechaInicio, fechaFin);
		
		if(resultAcquirerReportStatus!=null && !resultAcquirerReportStatus.isEmpty()){
			AcquirerDailyStatusDTOHandler handler=new AcquirerDailyStatusDTOHandler();			
			List<List<String>> registros=handler.castAllObjects(resultAcquirerReportStatus);
    		generarReporte(handler, registros, true,"reportesDiariosAdquirente",LayoutGenerator.DocumentType.EXCEL);		
    	} else {
    		System.out.println("No Existen Registros Para Generar Reporte Diario Adquirente");
    		logger.log(Level.INFO, "No Existen Registros");
    	}
		eliminarReporteAdquirenteDiarios();

    }
	
	private void eliminarReporteAdquirenteDiarios(){
    	FTPProperties fprops = new FTPProperties();
    	FileLayout fl = new FileLayout();
    	
    	Calendar fecha=Calendar.getInstance();
		Date fechaDepuracionDate=null;
		fecha.add(Calendar.DATE, -7);//7 días atras
		fecha.set(Calendar.HOUR_OF_DAY,0);
		fecha.set(Calendar.MINUTE, 0);
		fecha.set(Calendar.SECOND, 0);	
		fecha.set(Calendar.MILLISECOND, 0);
		fechaDepuracionDate=fecha.getTime();//se pone la fecha de inicio
		Date fechaDeArchivo=null;
		String[] arrOfStr = null;
		SimpleDateFormat date = new SimpleDateFormat("ddMMyyyy");
		
    	try{
    		//obtiene los reportes existentes
    		Map<String, InputStream> reportesAdqDiarios=fl.buscarReportesAdqDiarios(fprops);	
	    	if(reportesAdqDiarios!=null){//si hay reportes
		    		for(String reporte:reportesAdqDiarios.keySet()){
		    			arrOfStr = reporte.split("-");

		    			try {
		    				fechaDeArchivo = date.parse(arrOfStr[1].substring(0, 2)+arrOfStr[1].substring(2, 4)+arrOfStr[1].substring(4, 8));//obtiene el dia/mes/año y los vuelve fecha
		    			} catch (ParseException e) {
		    				e.printStackTrace();
		    			}  

		    			 //comprueba si es que inicio esta después que fecha actual  
		    			if(!fechaDeArchivo.after(fechaDepuracionDate)){
		    				fl.eliminarReportesAdqDiarios(reporte, fprops, fprops.getPathUploadAdquirentReporteDaily());
		    			}
		    			
		    			fechaDeArchivo=null;
		    			arrOfStr=null;
		    		}
	    	}else{
	    		System.out.println("No se encontraron reportes adquirente diarios para eliminar");
	    	}
    	}catch(IOException e){
	    		e.printStackTrace();
	    		System.out.println(e.getMessage());
	    }
    	
    }

}
