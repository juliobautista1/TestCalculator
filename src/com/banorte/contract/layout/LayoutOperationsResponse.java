package com.banorte.contract.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.banorte.contract.business.AttributeRemote;
import com.banorte.contract.business.CategoriesRemote;
import com.banorte.contract.business.CitiesRemote;
import com.banorte.contract.business.CommisionPlanRemote;
import com.banorte.contract.business.ContractRemote;
import com.banorte.contract.business.DirectaResultRemote;
import com.banorte.contract.business.ExecutiveBranchRemote;
import com.banorte.contract.business.ExecutiveRemote;
import com.banorte.contract.business.LoadFileRemote;
import com.banorte.contract.business.MaintanceRemote;
import com.banorte.contract.business.PayrateRemote;
import com.banorte.contract.business.PlanRemote;
import com.banorte.contract.business.RecipentMailRemote;
import com.banorte.contract.business.StatesRemote;
import com.banorte.contract.business.StatusContractRemote;
import com.banorte.contract.business.StatusDirectaRemote;
import com.banorte.contract.model.Affiliation;
import com.banorte.contract.model.Attribute;
import com.banorte.contract.model.Categories;
import com.banorte.contract.model.Cities;
import com.banorte.contract.model.CommisionPlan;
import com.banorte.contract.model.CommisionPlanPK;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.ContractAttributePK;
import com.banorte.contract.model.ContractMessageErrors;
import com.banorte.contract.model.ContractStatusHistory;
import com.banorte.contract.model.CounterRecord;
import com.banorte.contract.model.DirectaResult;
import com.banorte.contract.model.Executive;
import com.banorte.contract.model.ExecutiveBranch;
import com.banorte.contract.model.FailReference;
import com.banorte.contract.model.LoadFile;
import com.banorte.contract.model.Maintance;
import com.banorte.contract.model.Payrate;
import com.banorte.contract.model.Plan;
import com.banorte.contract.model.RecipentMail;
import com.banorte.contract.model.States;
import com.banorte.contract.model.StatusContract;
import com.banorte.contract.model.StatusDirecta;
import com.banorte.contract.model.WrapContractGenerate;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.BitacoraType;
import com.banorte.contract.util.BitacoraUtil;
import com.banorte.contract.util.DirectaStatus;
import com.banorte.contract.util.EncryptBd;
import com.banorte.contract.util.FTPProperties;
import com.banorte.contract.util.FileLayout;
import com.banorte.contract.util.Formatter;
import com.banorte.contract.util.LayoutOperationResponseUtil;
import com.banorte.contract.util.MailUtil;
import com.banorte.contract.web.ContractAcquirerMB;
import com.banorte.contract.web.EjbInstanceManager;

/**
 * 
 * @author Darvy Arch
 * 
 */
public class LayoutOperationsResponse {

	protected ContractRemote contractBean;
	protected MaintanceRemote maintanceBean;
	protected ExecutiveRemote executiveBean;
	protected StatusContractRemote statusBean;
	protected PayrateRemote payRateBean;
	protected CitiesRemote citiesBean;
	protected StatesRemote statesBean;
	protected ExecutiveBranchRemote executiveBranchBean;
	protected LoadFileRemote loadFileBean;

	protected StatusDirectaRemote statusDirectaBean;
	protected DirectaResultRemote directaResultBean;
	protected RecipentMailRemote recipentMailBean;
	protected AttributeRemote attributeBean;
	
	protected CommisionPlanRemote commisionPlanBean;
	protected PlanRemote planBean;
	
	public static List<ContractMessageErrors> listaErroresV = new ArrayList<ContractMessageErrors>(); //errores de validacion
	public static List<ContractMessageErrors> listaErroresC = new ArrayList<ContractMessageErrors>(); //errores de comisiones
	private CategoriesRemote categoriesBean;
	private static final Logger LOG=Logger.getLogger(LayoutOperationsResponse.class.getCanonicalName());

	
	private FTPProperties fprops 					= new FTPProperties();
	private String RESPONSE_TYPE_ANSWER 			="RESPONSE_TYPE_ANSWER";
	private String RESPONSE_TYPE_DIRECTAANSWER 		="RESPONSE_TYPE_DIRECTAANSWER";
	private String RESPONSE_TYPE_MAINTANCE 			="RESPONSE_TYPE_MAINTANCE";
	private String RESPONSE_TYPE_EXECUTIVE 			="RESPONSE_TYPE_EXECUTIVE";
	private String RESPONSE_TYPE_EXECUTIVE_BRANCH 	="RESPONSE_TYPE_EXECUTIVE_BRANCH";
	private String RESPONSE_TYPE_CITY 				="RESPONSE_TYPE_CITY";
	private String NO_VALIDA 						= "NO";
	
	List<States> statesList;
	
	public LayoutOperationsResponse() {
		if (payRateBean == null) {
			payRateBean = (PayrateRemote) EjbInstanceManager
					.getEJB(PayrateRemote.class);
		}
		
		if (directaResultBean == null) {
			directaResultBean = (DirectaResultRemote) EjbInstanceManager
					.getEJB(DirectaResultRemote.class);
		}
		
		if ( loadFileBean == null ) {
        	loadFileBean = (LoadFileRemote) EjbInstanceManager.getEJB(LoadFileRemote.class);
        } 
		
		if ( recipentMailBean == null ) {
        	recipentMailBean = (RecipentMailRemote) EjbInstanceManager.getEJB(RecipentMailRemote.class);
        } 
		if( attributeBean == null){
			attributeBean = (AttributeRemote) EjbInstanceManager.getEJB(AttributeRemote.class);
		}
		if(planBean==null){
			planBean=(PlanRemote)EjbInstanceManager.getEJB(PlanRemote.class);
		}
		if(categoriesBean==null){
			categoriesBean=(CategoriesRemote)EjbInstanceManager.getEJB(CategoriesRemote.class);
		}
	}
	
	public void setExecutiveBean(ExecutiveRemote executiveBean) {
		this.executiveBean = executiveBean;
	}

	public void setMaintanceBean(MaintanceRemote maintanceBean) {
		this.maintanceBean = maintanceBean;
	}

	public void setContractBean(ContractRemote contractBean) {
		this.contractBean = contractBean;
	}

	public void setStatusBean(StatusContractRemote statusBean) {
		this.statusBean = statusBean;
	}

	public void setCitiesBean(CitiesRemote citiesBean) {
		this.citiesBean = citiesBean;
		
		if (this.statesBean == null) {
			this.statesBean = (StatesRemote) EjbInstanceManager
					.getEJB(StatesRemote.class);
		}
	}

	public void setStatusDirectaBean(StatusDirectaRemote statusDirectaBean) {
		this.statusDirectaBean = statusDirectaBean;
	}
	
	/**
	 * @return the executiveBranchBean
	 */
	public ExecutiveBranchRemote getExecutiveBranchBean() {
		return executiveBranchBean;
	}

	/**
	 * @param executiveBranchBean the executiveBranchBean to set
	 */
	public void setExecutiveBranchBean(ExecutiveBranchRemote executiveBranchBean) {
		this.executiveBranchBean = executiveBranchBean;
	}

	public CommisionPlanRemote getCommisionPlanBean() {
		return commisionPlanBean;
	}

	public void setCommisionPlanBean(CommisionPlanRemote commisionPlanBean) {
		this.commisionPlanBean = commisionPlanBean;
	}

	public boolean processRemoteAnswers(boolean isAutomatic) throws IOException {
		
		MailUtil  mailUtil 							= new MailUtil();
		List<Answer> records 						= new ArrayList<Answer>();
		List<LoadFile> loadedFileInfo 				= new ArrayList<LoadFile>();
		List<RecipentMail> recipents      			= new ArrayList<RecipentMail>();
		List<FailReference> failReferenceAllInfo	= new ArrayList<FailReference>();
		WrapContractGenerate wrapContractGenerate 	= new WrapContractGenerate();
		Map<LoadFile,List<Answer>> mapResult		= new HashMap<LoadFile,List<Answer>>(); 
		LoadFile loadFile 							= null;
		List<FailReference> listFailReference		= null;
		CounterRecord counterRecord 				= null;
		boolean flag 								= false;
		HashMap<String,Boolean> mapSendMail 		= new HashMap<String, Boolean>();
		List<LoadFile> emptyInfo  					= new ArrayList<LoadFile>();
		

		mapResult = processRemoteAnswersFiles(loadedFileInfo);
		recipents = recipentMailBean.getAllRecipentMail();
		
		//records = processRemote(RESPONSE_TYPE_ANSWER);
		
		if (mapResult.size() > 0) {
			Set<Map.Entry<LoadFile,List<Answer>>> mapValues 	= mapResult.entrySet();
			Iterator<Map.Entry<LoadFile,List<Answer>>> it 		= mapValues.iterator();
			while (it.hasNext()) {
				counterRecord 							= new CounterRecord();
				listFailReference						= new ArrayList<FailReference>();
				Map.Entry<LoadFile,List<Answer>> entry 	= it.next();
				loadFile 								= entry.getKey();
				records 								= entry.getValue();
				if(records.size()>0){
					counterRecord = this.verifyAndUpdate(records,listFailReference);
					flag =true;
					loadFile.setNumberSuccess(counterRecord.getSuccess());
					loadFile.setNumberFail(counterRecord.getFail());
				}
				loadedFileInfo.add(loadFile);
				failReferenceAllInfo.addAll(listFailReference);
				Integer fileType = Integer.valueOf(loadFile.getProductType());
				for(RecipentMail recipentMail : recipents){
            		if(recipentMail.getProductTypeId().equals(fileType)){
            			mailUtil.sendOneMail(ApplicationConstants.SUBJECT_MAIL_LOAD,recipentMail.getRecipent(),loadFile,listFailReference,isAutomatic);
            			mapSendMail.put(loadFile.getProductType().toString(), Boolean.TRUE);
            		}
            	}	
				listFailReference = null;
			  }		
			}
		
		for(LoadFile info : loadedFileInfo ){
        	loadFileBean.save(info);
        }	
		
		mapSendMail = mailUtil.validateFileTypeRead(mapSendMail);
		
		/*
		 * Envia Correos con los valores de CERO
		 */
		emptyInfo 			= mailUtil.fillInfoReadLayout(mapSendMail);
		listFailReference	= new ArrayList<FailReference>();
		for(LoadFile info : emptyInfo){
			Integer fileType = Integer.valueOf(info.getProductType());
			for(RecipentMail recipentMail : recipents){
	    		if(recipentMail.getProductTypeId().equals(fileType)){
	    			mailUtil.sendOneMail(ApplicationConstants.SUBJECT_MAIL_LOAD,recipentMail.getRecipent(),info,listFailReference,isAutomatic);
	    		}
	    	}
		}
		
		
		
		wrapContractGenerate.setLoadedFileInfo(mailUtil.sortListLoadFile( loadedFileInfo) );
		wrapContractGenerate.setListFailReference(failReferenceAllInfo);
		wrapContractGenerate.addAllLoadedFileInfo( emptyInfo ) ;
		
		 for(RecipentMail recipentMail : recipents){
	    		if(recipentMail.getProductTypeId().equals(ApplicationConstants.PT_TODOS)){
	    			mailUtil.sendListMail(ApplicationConstants.SUBJECT_MAIL_LOAD,recipentMail.getRecipent(),wrapContractGenerate,isAutomatic);
	    		}
	    	}
       
		return flag;
	}
	
	public boolean processRemoteAnswersMujerPyME(boolean isAutomatic) throws IOException {
		List<LoadFile> loadedFileInfo 				= new ArrayList<LoadFile>();
		boolean flag 								= false;
		processRemoteAnswersFilesMujerPyME(loadedFileInfo);
		return flag;
	}
	
	public boolean processRemoteDirectaAnswers(Date startProcessDate, Date endProcessDate) throws IOException {
		
		boolean flag = false;
		List<Maintance> records = new ArrayList<Maintance>();

		//Obtencion de resultados de directa mediante layout
		//records = processRemote(startProcessDate, RESPONSE_TYPE_DIRECTAANSWER);	
		
		//Obtencion de resultados de directa mediandte BD
		List<DirectaResult> results = directaResultBean.findByRangeDate(startProcessDate, endProcessDate);
		records = parseDirectaResult(results); 
		
		if(records.size()>0){
			this.verifyAndUpdate(startProcessDate, records);
			flag =true;
		}
		
		return flag;
	}
	
	public List<Maintance> processRemoteMaintance(Date processDate) throws IOException {
		
		List<Maintance> records = processRemote(processDate, RESPONSE_TYPE_MAINTANCE);
		
		return records;
	}
	

	
	@SuppressWarnings("unchecked")
	private <T> List<T> processRemote(String responseType) throws IOException {
		
		return this.processRemote(null, responseType);

	}
	
	@SuppressWarnings("unchecked")
	private <T> List<T> processRemote(Date processDate,String responseType ) throws IOException {
		FileLayout fLayout 				= new FileLayout();
		Map<String, InputStream> map 	= new HashMap<String, InputStream>();
		CommisionLayout comissionLayout = new CommisionLayout();
		
		if(responseType.equals(RESPONSE_TYPE_ANSWER)){
			map = fLayout.getRemoteFiles(fprops);
		}else if(responseType.equals(RESPONSE_TYPE_DIRECTAANSWER)){
			String fileName = "DIRECTALayout-"+ new Formatter().formatDateToStringTextLayout(processDate) + ".xls";
			map = fLayout.getRemoteDirectaFile(fileName ,fprops);
		}else if(responseType.equals(RESPONSE_TYPE_MAINTANCE)){	
			String fileName = "BEMmttoLayout-"+ new Formatter().formatDateToStringMaintance(processDate) + ".TXT";
			map = fLayout.getRemoteFile(fileName ,fprops);
		}else if(responseType.equals(RESPONSE_TYPE_EXECUTIVE)){	
			map = fLayout.getRemoteFiles(fprops,fprops.getPathExecutives());
		}else if(responseType.equals(RESPONSE_TYPE_CITY)){	
			
		}else if (responseType.equals(RESPONSE_TYPE_EXECUTIVE_BRANCH)){
			map = fLayout.getRemoteFiles(fprops,fprops.getPathExecutiveBranch());
		}else if(responseType.equalsIgnoreCase("Comisiones")){
			map=fLayout.getRemoteFileCommision(fprops);
		}	
			
		List<T> records = new ArrayList<T>();

		if (map.size() > 0) {
			Set<Map.Entry<String, InputStream>> mapValues 		= map.entrySet();
			Iterator<Map.Entry<String, InputStream>> it 		= mapValues.iterator();

			while (it.hasNext()) {

				Map.Entry<String, InputStream> entry 	= it.next();
				String fileName 						= entry.getKey();
				InputStream contentBytes 				= entry.getValue();

				if(responseType.equals(RESPONSE_TYPE_ANSWER)){
					records.addAll((List<T>)parseLayout(contentBytes));
					//delete file after get data
					fLayout.deleteRemoteFiles(fileName, fprops);	
				}else if(responseType.equals(RESPONSE_TYPE_DIRECTAANSWER)){
					records = (List<T>)parseDirectaLayout(contentBytes);
				}else if(responseType.equals(RESPONSE_TYPE_MAINTANCE)){
					records= (List<T>)parseTxtLayout(contentBytes);
				}else if(responseType.equals(RESPONSE_TYPE_EXECUTIVE)){
					records= (List<T>)parseExecutiveLayout(contentBytes);
					//delete file after get data
					//fLayout.deleteRemoteExecutivesFiles(fileName, fprops);	
					fLayout.deleteRemoteFiles(fileName, fprops,fprops.getPathExecutives());
				}else if(responseType.equals(RESPONSE_TYPE_CITY)){
					records= (List<T>)parseCitiesLayout(contentBytes);
					//delete file after get data
					//fLayout.deleteRemoteFiles(fileName, fprops);	
				}else if(responseType.equals(RESPONSE_TYPE_EXECUTIVE_BRANCH)){
					records= (List<T>)parseExecutiveBranchLayout(contentBytes);
					//delete file after get data
					fLayout.deleteRemoteFiles(fileName, fprops,fprops.getPathExecutiveBranch());	
				}else if(responseType.equalsIgnoreCase("Comisiones")){
						records= (List<T>)parseComissionLayout(contentBytes);
						fLayout.deleteRemoteFiles(fileName, fprops,fprops.getPathCommision());	//FIXME gina descomentar para prod
				}
			}
		}
		return records;
	}
	
	
	@SuppressWarnings("unchecked")
	private <T> Map<LoadFile,List<T>> processRemoteAnswersFiles( List<LoadFile> listLoadFile) throws IOException{
		
		LayoutOperationResponseUtil layoutOperationResponseUtil = new LayoutOperationResponseUtil();
		FileLayout fLayout 										= new FileLayout();
		Map<String, InputStream> map 							= new HashMap<String, InputStream>();
		Map<LoadFile,List<T>> mapFileInfo 						= new HashMap<LoadFile,List<T>>();
		LoadFile loadFile 										= null;
		List<T> records 										= null;
		String fileName 										= ApplicationConstants.EMPTY_STRING;
		
		try{
			map = fLayout.getRemoteFiles(fprops);	
			if (map.size() > 0) {
				Set<Map.Entry<String, InputStream>> mapValues 		= map.entrySet();
				Iterator<Map.Entry<String, InputStream>> it 		= mapValues.iterator();
				while (it.hasNext()) {
					loadFile 								= new LoadFile();
					records 								= new ArrayList<T>();
					Map.Entry<String, InputStream> entry 	= it.next();
					fileName 								= entry.getKey();
					InputStream contentBytes 				= entry.getValue();
					
					loadFile.setFileName(fileName);
					loadFile.setCreationDate(new Date());
					loadFile.setFileType(ApplicationConstants.FILE_TYPE_RESPUESTA);
					loadFile.setProductType(layoutOperationResponseUtil.getProductTypeByFileName(fileName));
	
					System.out.println("*******FILE NAME "+ fileName);
					try {
						records.addAll((List<T>)parseLayout(contentBytes));
						//fLayout.deleteRemoteFiles(fileName, fprops);//FIXME gina PROD		//delete file after get data	
							
						mapFileInfo.put(loadFile, records);	
					} catch (Exception e) {
						System.out.println(" ERROR al leer el archivo: "+fileName);
						continue;
					}
					
				}
			}
		}catch (Exception e){
			System.out.println("--- Error al Leer El Archivo "+ fileName);
		}

		return mapFileInfo;
		
	}
	
	private <T> Map<LoadFile,List<T>> processRemoteAnswersFilesMujerPyME( List<LoadFile> listLoadFile) throws IOException{
		//LayoutOperationResponseUtil layoutOperationResponseUtil = new LayoutOperationResponseUtil();
		FileLayout fLayout 										= new FileLayout();
		Map<String, InputStream> map 							= new HashMap<String, InputStream>();
		Map<LoadFile,List<T>> mapFileInfo 						= new HashMap<LoadFile,List<T>>();
		//LoadFile loadFile 										= null;
		//List<T> records 										= null;
		String fileName 										= ApplicationConstants.EMPTY_STRING;
		
		try{
			map = fLayout.getRemoteFilesMujerPyME(fprops);	
			if (map.size() > 0) {
				Set<Map.Entry<String, InputStream>> mapValues 		= map.entrySet();
				Iterator<Map.Entry<String, InputStream>> it 		= mapValues.iterator();
				while (it.hasNext()) {
					//loadFile 								= new LoadFile();
					//records 								= new ArrayList<T>();
					Map.Entry<String, InputStream> entry 	= it.next();
					fileName 								= entry.getKey();
					InputStream contentBytes 				= entry.getValue();
					
					//loadFile.setFileName(fileName);
					//loadFile.setCreationDate(new Date());
					//loadFile.setFileType(ApplicationConstants.FILE_TYPE_MUJER_PYME);
					//loadFile.setProductType(layoutOperationResponseUtil.getProductTypeByFileName(fileName));
	
					System.out.println("*******FILE NAME "+ fileName);
					try {

						//ContractAcquirerMB.cargarListaCuentasMujerPyME(parseCuentasMujerPyME(contentBytes));
						//new ContractAcquirerMB().cargarListaCuentasMujerPyME(parseCuentasMujerPyME(contentBytes));//AQUI LO LEE
						ContractAcquirerMB.cargarListaCuentasMujerPyME(parseCuentasMujerPyME(contentBytes));//AQUI LO LEE
						//fLayout.deleteRemoteFiles(fileName, fprops);//FIXME gina PROD		//delete file after get data	
							
						//mapFileInfo.put(loadFile, records);	
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(" ERROR al leer el archivo: "+fileName);
						continue;
					}
					
				}
			}
		}catch (Exception e){
			System.out.println("--- Error al Leer El Archivo "+ fileName);
		}

		return mapFileInfo;
		
	}

	private CounterRecord verifyAndUpdate(List<Answer> list , List<FailReference> listFailReference) {
	
		LayoutOperationResponseUtil layoutORUtil 	= new LayoutOperationResponseUtil();
		Contract contract 							= null;
		//LoadFile loadFile 						= new LoadFile(); 
		//List<FailReference> listFailReference		= new ArrayList<FailReference>();
		FailReference failReference 				= null;
		String errorDescription	 					= ApplicationConstants.EMPTY_STRING;
		int contador								= ApplicationConstants.INIT_CONT_UNO;
		Integer idStatus 							= ApplicationConstants.VALUE_0; 
		CounterRecord counterRecord 				= new CounterRecord();

		
		for (Answer answer : list) {
			try{
				// Revisamos si existe contrato para la referencia indicada
				// Contract contract = this.contractBean.findByReference(answer.getReference());
				contract = this.contractBean.findByRefLastVersion(answer.getReference());
				idStatus = Integer.valueOf( answer.getIdStatus() );
	
				if (contract != null) {
					int producttypeid = contract.getProduct().getProductTypeid().getProductTypeid().intValue();
	
					Collection<ContractAttribute> atts = contract.getContractAttributeCollection();
	
					//Joseles(01Mar2011): se agregó variable para obtener el merchant number y agregarlo a la 
					//propiedad merchantNumber del contrato
					String merchantNumber = "";
					
					if (producttypeid == 1) { // BEM Numero de Empresa
						// El numero de empresa puede modificarse siempre y cuando no sea un estatus 12 - Cuentas Activadas,
						// segun la definicion del usuario no se tiene restriccion para volver a cargar
						// una respuesta, excepto cuando es estatus 12 no se debe modificar el numero de empresa
						if ( !idStatus.equals(12) ) { 	
							for (ContractAttribute att : atts) {
								if (att.getAttribute().getFieldname().equals(ApplicationConstants.ATTR_BEMNUMBER)) {
									if (answer.getNoEmpresa().length() < 21){
										merchantNumber = answer.getNoEmpresa();// numero de empresa <-- bem | nomina
									}else {
										merchantNumber = answer.getNoEmpresa().substring(0, 19);
									}
									att.setValue(merchantNumber);
								}
							}
							if (merchantNumber==""){ //se crea el atributo (si no se encontro) y se agrega al contrato
								ContractAttribute contAttTemp = new ContractAttribute();
								Attribute bemNumberTemp = new Attribute();
								ContractAttributePK pk = new ContractAttributePK();
								
								bemNumberTemp = attributeBean.findByFieldname("bemnumber");
								pk.setAttributeid(bemNumberTemp.getAttributeid());
								pk.setContractid(contract.getContractId());
								pk.setVersion(contract.getVersion());
								contAttTemp.setValue(answer.getNoEmpresa());
								contAttTemp.setContractAttributePK(pk);
								contAttTemp.setAttribute(bemNumberTemp);
								
								atts.add(contAttTemp);
								contract.setMerchantNumber(answer.getNoEmpresa());//se agrega el mismo campo del atributo al merchant number
							}
						}
					}else if (producttypeid == 3) { // Adquirente No. Afiliacion
						// MXP, No. Afiliacion DLLS
						Collection<Affiliation> affiliation = contract.getAffiliationCollection(); // affiliation
						Affiliation affi = null;
						if (affiliation.size() > 0) {
							affi = affiliation.iterator().next();
						} else {
							affi = new Affiliation();
						}
						if (answer.getNoAffiliationMXP().length() < 21){
							merchantNumber = answer.getNoAffiliationMXP(); // NUMAFFILMN <-- adquirente
						}else{
							merchantNumber = answer.getNoAffiliationMXP().substring(0, 20);
						}
						//merchant number
						affi.setNumaffilmn(merchantNumber);
						if (answer.getNoAffiliationDLLS().length() < 21){
							affi.setNumaffildlls(answer.getNoAffiliationDLLS()); // NUMAFFILDLLS <-- adquirente
						}else{
							affi.setNumaffildlls(answer.getNoAffiliationDLLS().substring(0, 20));
						}
					}else if (producttypeid == 2) {// Nomina
						for (ContractAttribute att : atts) {
							if (att.getAttribute().getFieldname().equals("payrollemitter")) {
								if (answer.getNoEmpresa().length() < 21)
									merchantNumber = answer.getNoEmpresa(); // numero de empresa <-- bem | nomina
								else
									merchantNumber = answer.getNoEmpresa().substring(0,19);
								att.setValue(merchantNumber);
							}
						}
						if (merchantNumber==""){ //se crea el atributo (si no se encontro) y se agrega al contrato
							ContractAttribute contAttTemp = new ContractAttribute();
							Attribute numEmpT = new Attribute();
							ContractAttributePK pk = new ContractAttributePK();
							
							numEmpT = attributeBean.findByFieldname("payrollemitter");
							pk.setAttributeid(numEmpT.getAttributeid());
							pk.setContractid(contract.getContractId());
							pk.setVersion(contract.getVersion());
							contAttTemp.setValue(answer.getNoEmpresa());
							contAttTemp.setContractAttributePK(pk);
							contAttTemp.setAttribute(numEmpT);
							
							atts.add(contAttTemp);
							contract.setMerchantNumber(answer.getNoEmpresa());//se agrega el mismo campo del atributo al merchant number
						}
						
						if (answer.getRate() != null) {
							String[] rateData = answer.getRate().split("|");
							if (rateData.length == 5) {
								Payrate rate = new Payrate();
								rate.getPayratePK().setRateid(new Integer(rateData[0]));
								rate.getPayratePK().setRate(rateData[0]);
								rate.setCorrectb(new Double(rateData[1]));
								rate.setErrorb(new Double(rateData[2]));
								rate.setCorrectob(new Double(rateData[3]));
								rate.setErrorob(new Double(rateData[4]));
								payRateBean.save(rate);
							}
						}
					}else if (producttypeid == 4) {//cobranza domiciliada
						for (ContractAttribute att : atts) {
							if (att.getAttribute().getFieldname().equals("cdemitter")) {
								if (answer.getNoEmpresa().length() < 21){
									merchantNumber = answer.getNoEmpresa(); // numero de empresa -> cd emitter
								}
								else{
									merchantNumber = answer.getNoEmpresa().substring(0,19);
								}
								att.setValue(merchantNumber);
								contract.setMerchantNumber(merchantNumber);//se agrega el mismo campo del atributo al merchant number
							}
						}
						if (merchantNumber==""){
							ContractAttribute contAttTemp = new ContractAttribute();
							Attribute cdEmitterTemp = new Attribute();
							ContractAttributePK pk = new ContractAttributePK();
							
							cdEmitterTemp = attributeBean.findByFieldname("cdemitter");
							pk.setAttributeid(cdEmitterTemp.getAttributeid());
							pk.setContractid(contract.getContractId());
							pk.setVersion(contract.getVersion());
							contAttTemp.setValue(answer.getNoEmpresa());
							contAttTemp.setContractAttributePK(pk);
							contAttTemp.setAttribute(cdEmitterTemp);
							
							atts.add(contAttTemp);
							contract.setMerchantNumber(answer.getNoEmpresa());//se agrega el mismo campo del atributo al merchant number
						}
					}
					
					String comentarios = "";
					for (ContractAttribute att : atts) {//busca el atributo de respuesta de operaciones
						if (att.getAttribute().getFieldname().equalsIgnoreCase("operations_comment")){ //comentario de respuesta de operaciones
							if (answer.getComments().length() < 250){
								att.setValue(answer.getComments());
							}else{
								att.setValue(answer.getComments().substring(0,249));
							}
							comentarios = "found";
						}
					}
					if(comentarios.isEmpty()){//si no encontró el atributo de comentario de operaciones, lo agrega
						ContractAttribute contAttTemp = new ContractAttribute();
						Attribute operationCommentsTemp = new Attribute();
						ContractAttributePK pk = new ContractAttributePK();
						
						operationCommentsTemp = attributeBean.findByFieldname("operations_comment");
						pk.setAttributeid(operationCommentsTemp.getAttributeid());
						pk.setContractid(contract.getContractId());
						pk.setVersion(contract.getVersion());
						comentarios=(answer.getComments()!=null && answer.getComments().length()>249)?answer.getComments().substring(0,249):answer.getComments();
						contAttTemp.setValue(comentarios);
						contAttTemp.setContractAttributePK(pk);
						contAttTemp.setAttribute(operationCommentsTemp);
						
						atts.add(contAttTemp);
					}
						
					// Carga la informacion de los numero de usuario que tienen token
					contador = ApplicationConstants.INIT_CONT_UNO;
					if(answer.getListUserToken()!=null || answer.getListUserToken().size()>ApplicationConstants.EMPTY_LIST){
						for(String employeeToken:answer.getListUserToken()){
							ContractAttribute attTemp 	= new ContractAttribute();
							Attribute attribute 		= new Attribute();
							
							attribute.setAttributeid(layoutORUtil.getIdUserTokenAttr(contador));
							attTemp.setValue(employeeToken);
							attTemp.setAttribute(attribute);
							atts.add(attTemp);
							contador = contador + 1;
							}
					}
	
					contract.setContractAttributeCollection(atts);
					StatusContract status = statusBean.findById(idStatus);
	
					if (status != null) {
						try{
							Integer statusActual=contract.getStatus().getStatusid();
							if(statusActual==9||statusActual==10){
								if(status.getStatusid()==6){
									throw new Exception("-Contrato aceptado previamente");
								}
								contract.setStatus(status);
							}else{
								contract.setStatus(status);
							}
//							contract.setStatus(status);
							
							//Joseles: Se agregá el valor del merchant number en la propiedad del contrato
							if(status.getStatusid() == 6)//ACEPTADO
								contract.setMerchantNumber(merchantNumber);
							
							ContractStatusHistory statusHistory = new ContractStatusHistory();
							statusHistory.setContract(contract);
							statusHistory.setStatusContract(status);
							statusHistory.setOffempnum("appsstb");
							String respuesta = answer.getComments()!=null?answer.getComments():"";//se agrega comentarios de respuesta de operaciones al statushistory
							Formatter.fixtoLenght(respuesta, 545);
							statusHistory.setCommentary("APPSSTB contrato contestado por operaciones."+" - "+respuesta);
		
							Calendar lastOperationDay = new GregorianCalendar();
							
							lastOperationDay.setTime(new Date());
							lastOperationDay.add(Calendar.DAY_OF_MONTH, -1);
							
							statusHistory.setModifydate(new Date());
							//statusHistory.setModifydate(lastOperationDay.get);
		
							contract.add(statusHistory);
							contractBean.update(contract);
							
							FacesContext fCtx=FacesContext.getCurrentInstance();
					        String userNumber="";
					        try{
					        	userNumber=BitacoraUtil.getUserNumberSession(fCtx);	
					        }catch(Exception e){
					        }
					        String folio=ApplicationConstants.EMPTY_STRING;
					        String folioVersion=ApplicationConstants.EMPTY_STRING;
					        folio=contract.getReference();
					        folioVersion=contract.getVersion().toString();
							BitacoraUtil.getInstance().saveBitacoraFolio(BitacoraType.ANSWER_CONTRACT, userNumber, folio, folioVersion);
							
							counterRecord.addOneSuccess();
						}catch( Exception e){
							failReference = new FailReference();
							errorDescription = "FALLO ALMACENAR CONTRATO CON REFERENCIA " + answer.getReference();
							failReference.setError(errorDescription);
							failReference.setReference(contract.getReference());
							listFailReference.add(failReference);
							System.out.println(errorDescription + e.getMessage());
						}
					}else {//estatus es nulo (no se encontro estatus)
							failReference = new FailReference();
							errorDescription = "Referencia: " + contract.getReference()+ " Estatus NO Existe: " + answer.getIdStatus();
							failReference.setError(errorDescription);
							failReference.setReference(contract.getReference());
							listFailReference.add(failReference);
							System.out.println(errorDescription);
					}
				}else {//contract is null
					failReference = new FailReference();
					errorDescription = "La Referencia " + answer.getReference()+ " No esta Registrada en la Herramienta";
					failReference.setError(errorDescription);
					failReference.setReference(answer.getReference());
					listFailReference.add(failReference);
					System.out.println(errorDescription);
				}
			}	catch (Exception e) {
				failReference = new FailReference();
				errorDescription = "FALLO ALMACENAR CONTRATO CON REFERENCIA " + answer.getReference();
				failReference.setError(errorDescription);
				failReference.setReference(contract.getReference()!=null?contract.getReference():"");
				listFailReference.add(failReference);
				System.out.println(errorDescription);
			}
		}
		counterRecord.setFail( listFailReference.size());
		return counterRecord;
	}

	private List<Answer> parseLayout(InputStream is) throws IOException {
		List<Answer> result 	= new ArrayList();
		POIFSFileSystem fs 		= new POIFSFileSystem(is);
		Workbook wb 			= new HSSFWorkbook(fs);
		Sheet s 				= wb.getSheetAt(0);
		int rows 				= s.getPhysicalNumberOfRows();
		
	
		for (int i = 0; i < rows; i++) {
			Row row = s.getRow(i);
			int cells = (row != null) ? row.getPhysicalNumberOfCells() : 0;
			if (cells > 0) {
				try {
					Answer a = new Answer();
					/**
					 *  Columnas respuesta:
					 *  0 - A - Referencia, 
					 *  1 - B - Codigo de Estatus, 
					 *  2 - C - Comentarios, 
					 *  3 - D - Numero de Empresa, 
					 *  4 - E - No. Afiliacion Pesos, 
					 *  5 - F - No. Afiliacion Dolares
					 */
					if (getCellValue(row, 0).trim().length() > 0 // referencia
							&& getCellValue(row, 1).trim().length() > 0 //estatus
							&& getCellValue(row, 1) != null) {
						
						List<String> listUserTokens 	= new ArrayList<String>();
						String reference 				= getCellValue(row, 0);
						String idStatus 				= getCellValue(row, 1);
						
						a.setReference(reference);
						a.setIdStatus(idStatus);
						
						a.setComments(getCellValue(row, 2));
						String empresa = getCellValue(row, 3); // debe estar lleno si es BEM, Nomina y CD
						a.setNoEmpresa(empresa != null ? empresa : ApplicationConstants.EMPTY_STRING);
						String noAffiMXP = getCellValue(row, 4);
						a.setNoAffiliationMXP(noAffiMXP != null ? noAffiMXP: ApplicationConstants.EMPTY_STRING);
						String noAffiDLL = getCellValue(row, 5);
						a.setNoAffiliationDLLS(noAffiDLL != null ? noAffiDLL: ApplicationConstants.EMPTY_STRING);
						String rate = getCellValue(row, 6);
						a.setRate(rate != null ? rate : ApplicationConstants.EMPTY_STRING);
						
						for(int iterator=0;iterator < ApplicationConstants.MAX_NUMBER_USER_TOKEN;iterator ++){
							int numColumn = iterator + ApplicationConstants.FIRST_COLUMN_RESPONSE_USER_TOKEN;
							String userTemp = getCellValue(row, numColumn);
							if(userTemp != ApplicationConstants.EMPTY_STRING){
								listUserTokens.add(userTemp);
							}	
						}
						
						a.setListUserToken(listUserTokens);
						result.add(a);
					}
				} catch (NumberFormatException e) {
					System.err.println("Valor : " + getCellValue(row, 1)+ " + " + i + " \n" + e);
					// e.printStackTrace();
				} catch (NullPointerException e) {
					System.err.println("Registro " + i + " nulo: " + e);
					// e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	//private List<String> parseCuentasMujerPyME(InputStream is) throws IOException {
	private Map<String,String> parseCuentasMujerPyME(InputStream is) throws IOException {
		Map<String,String> result 	= new TreeMap<String,String>();
		try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = reader.readLine()) != null) {
            	if(line != null && line.length()>18){// la linea debe ser mayor a 18 caracteres
   				 //System.out.println(line.substring(0,8));
   				//result.add(line.substring(0,8).replaceFirst("^0+(?!$)", ""));
            		//result.put(line.substring(0,8).replaceFirst("^0+(?!$)", ""), line.substring(9,19).replaceFirst("^0+(?!$)", ""));
            		result.put(line.substring(0,8).replaceFirst("^0+(?!$)", ""), line.substring(9,19));
   			 }
            }
            reader.close();
            if(result.isEmpty()){
            	System.out.println("No hay datos en el archivo de cuentas de Mujer PyME Banorte");
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
		return result;
	}
	private boolean isMtto(String reference){
		boolean result = Boolean.FALSE;
		
		if( reference.contains(ApplicationConstants.PREFIJO_MTTOS) ){
			result = Boolean.TRUE;
		}
		
		return result;
	}
	
	private String getStatusDescription(String idStatus){
		
		String resultado = ApplicationConstants.EMPTY_STRING;
	
		if(idStatus.equals(ApplicationConstants.RECHAZO_400_ID)){
			resultado = ApplicationConstants.RECHAZO_400_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_401_ID)){
			resultado = ApplicationConstants.RECHAZO_401_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_402_ID)){
			resultado = ApplicationConstants.RECHAZO_402_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_403_ID)){
			resultado = ApplicationConstants.RECHAZO_403_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_404_ID)){
			resultado = ApplicationConstants.RECHAZO_404_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_405_ID)){
			resultado = ApplicationConstants.RECHAZO_405_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_406_ID)){
			resultado = ApplicationConstants.RECHAZO_406_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_407_ID)){
			resultado = ApplicationConstants.RECHAZO_407_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_408_ID)){
			resultado = ApplicationConstants.RECHAZO_408_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_409_ID)){
			resultado = ApplicationConstants.RECHAZO_409_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_410_ID)){
			resultado = ApplicationConstants.RECHAZO_410_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_410_ID)){
			resultado = ApplicationConstants.RECHAZO_410_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_411_ID)){
			resultado = ApplicationConstants.RECHAZO_411_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_412_ID)){
			resultado = ApplicationConstants.RECHAZO_412_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_413_ID)){
			resultado = ApplicationConstants.RECHAZO_413_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_414_ID)){
			resultado = ApplicationConstants.RECHAZO_414_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_415_ID)){
			resultado = ApplicationConstants.RECHAZO_415_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_416_ID)){
			resultado = ApplicationConstants.RECHAZO_416_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_417_ID)){
			resultado = ApplicationConstants.RECHAZO_417_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_418_ID)){
			resultado = ApplicationConstants.RECHAZO_418_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_419_ID)){
			resultado = ApplicationConstants.RECHAZO_419_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_420_ID)){
			resultado = ApplicationConstants.RECHAZO_420_STR;
		}else if(idStatus.equals(ApplicationConstants.RECHAZO_421_ID)){
			resultado = ApplicationConstants.RECHAZO_421_STR;
		}
		
		return resultado;
		
	} 

	private List<Maintance> parseDirectaLayout(InputStream is) throws IOException {
		
		List<Maintance> result = new ArrayList<Maintance>();
		POIFSFileSystem fs = new POIFSFileSystem(is);
		Workbook wb = new HSSFWorkbook(fs);
		Sheet s = wb.getSheetAt(0);
		int rows = s.getPhysicalNumberOfRows();

		for (int i = 1; i < rows; i++) {
			Row row = s.getRow(i);
			int cells = (row != null) ? row.getPhysicalNumberOfCells() : 0;
			if (cells > 0) {
				try {
					Maintance entity = new Maintance();
					// Referencia, CÃ³digo de Estatus, Comentarios, NÃºmero de
					// Empresa, No. AfiliaciÃ³n Pesos, No. AfiliaciÃ³n DÃ³lares

					if (getCellValue(row, 0) != null && getCellValue(row, 0).trim().length() > 0) {
						
						/*Column - Property(Maintance)	
						 * 0  - merchannumber
						 * 27 - isValidAccountNumber1
						 * 28 - isValidAccountNumber2
						 * 29 - isValidAccountNumber3
						 * 30 - isValidAccountNumber4
						 * 31 - isValidAccountNumber5
						 * 32 - isValidAccountNumber6
						 * 33 - isValidAccountNumber7
						 * 34 - isValidAccountNumber8
						 * 35 - isValidAccountNumber9
						 * 36 - isValidAccountNumber10
						 * 37 - isValidAccountNumber11
						 * 38 - isValidAccountNumber12
						 * 39 - isValidAccountNumber13
						 * 40 - isValidAccountNumber14
						 * 41 - isValidAccountNumber15
						 * 42 - answeredCall
						 * 43 - officerDirectaName
						 * 44 - callResult
						 * 45 - retries
						 * 46 - comments
						 * 47 - officerBEMName
						 * */
						
						/*Column - Property(Maintance)	
						 * 1  - merchannumber
						 * 28 - isValidAccountNumber1
						 * 29 - isValidAccountNumber2
						 * 30 - isValidAccountNumber3
						 * 31 - isValidAccountNumber4
						 * 32 - isValidAccountNumber5
						 * 33 - isValidAccountNumber6
						 * 34 - isValidAccountNumber7
						 * 35 - isValidAccountNumber8
						 * 36 - isValidAccountNumber9
						 * 37 - isValidAccountNumber10
						 * 38 - isValidAccountNumber11
						 * 39 - isValidAccountNumber12
						 * 40 - isValidAccountNumber13
						 * 41 - isValidAccountNumber14
						 * 42 - isValidAccountNumber15
						 * 43 - answeredCall
						 * 44 - officerDirectaName
						 * 45 - callResult
						 * 46 - retries
						 * 47 - comments
						 * 48 - officerBEMName
						 * */
						//Obtener el id del resultado de la llamada
						Integer statusId = DirectaStatus.SC_SIN_RESULTADO.value();
						
						if(getCellValue(row, 45) != null && !getCellValue(row, 45).equals(""))
							statusId = Integer.parseInt(getCellValue(row, 45));

						
						if( statusId.intValue() != DirectaStatus.SC_SIN_RESULTADO.value().intValue()){
							
							String isValid = null;
								
							isValid = getCellValue(row, 28)==null ? "" : getCellValue(row, 28).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber1(1);
									
							isValid = getCellValue(row, 29)==null ? "" : getCellValue(row, 29).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber2(1);
	
							isValid = getCellValue(row, 30)==null ? "" : getCellValue(row, 30).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber3(1);
	
							isValid = getCellValue(row, 31)==null ? "" : getCellValue(row, 31).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber4(1);
	
							isValid = getCellValue(row, 32)==null ? "" : getCellValue(row, 32).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber5(1);
	
							isValid = getCellValue(row, 33)==null ? "" : getCellValue(row, 33).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber6(1);
	
							isValid = getCellValue(row, 34)==null ? "" : getCellValue(row, 34).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber7(1);
	
							isValid = getCellValue(row, 35)==null ? "" : getCellValue(row, 35).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber8(1);
	
							isValid = getCellValue(row, 36)==null ? "" : getCellValue(row, 36).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber9(1);
	
							isValid = getCellValue(row, 37)==null ? "" : getCellValue(row, 37).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber10(1);
	
							isValid = getCellValue(row, 38)==null ? "" : getCellValue(row, 38).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber11(1);
	
							isValid = getCellValue(row, 39)==null ? "" : getCellValue(row, 39).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber12(1);
	
							isValid = getCellValue(row, 40)==null ? "" : getCellValue(row, 40).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber13(1);
	
							isValid = getCellValue(row, 41)==null ? "" : getCellValue(row, 41).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber14(1);
	
							isValid = getCellValue(row, 42)==null ? "" : getCellValue(row, 42).trim().toUpperCase();
							if(isValid.length()>0 && isValid.equalsIgnoreCase(NO_VALIDA))
									entity.setIsValidAccountNumber15(1);
	
							entity.setAnsweredCall(getCellValue(row, 43));
							entity.setOfficerDirectaName(getCellValue(row, 44));
							entity.setRetries(getCellValue(row, 46)==null ? 0: Integer.parseInt(getCellValue(row, 46)));
							entity.setComments(getCellValue(row, 47));
							entity.setOfficerBEMName(getCellValue(row, 48));
						
						}

						entity.setId(Integer.parseInt(getCellValue(row, 0)));
						entity.setMerchantNumber(getCellValue(row, 1));
						
						//Obtener la entidad Estatus referente al id del resultado de la llamada
						StatusDirecta status = statusDirectaBean.findById(statusId);
						entity.setStatus(status);
						
						result.add(entity);
					}
					
				} catch (NumberFormatException e) {
					System.err.println("Valor : " + getCellValue(row, 1)
							+ " + " + i + " \n" + e);
				} catch (NullPointerException e) {
					System.err.println("Registro " + i + " nulo: " + e);
				}
			}
		}

		return result;
	}
	
	private List<Maintance> parseDirectaResult(List<DirectaResult> resultList){
		
		List<Maintance> maintanceList = new ArrayList<Maintance>();
		
		for(DirectaResult result: resultList ){
			Maintance maintance = new Maintance();
			
			maintance.setId(Integer.parseInt(result.getId()));
			maintance.setMerchantNumber(result.getMerchantNumber());
			

			
			if(result.getIsValidAccountNumber1().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber1(1);
			if(result.getIsValidAccountNumber2().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber2(1);
			if(result.getIsValidAccountNumber3().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber3(1);
			if(result.getIsValidAccountNumber4().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber4(1);
			if(result.getIsValidAccountNumber5().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber5(1);
			if(result.getIsValidAccountNumber6().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber6(1);
			if(result.getIsValidAccountNumber7().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber7(1);
			if(result.getIsValidAccountNumber8().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber8(1);
			if(result.getIsValidAccountNumber9().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber9(1);
			if(result.getIsValidAccountNumber10().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber10(1);
			if(result.getIsValidAccountNumber11().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber11(1);
			if(result.getIsValidAccountNumber12().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber12(1);
			if(result.getIsValidAccountNumber13().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber13(1);
			if(result.getIsValidAccountNumber14().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber14(1);
			if(result.getIsValidAccountNumber15().equals(NO_VALIDA))
				maintance.setIsValidAccountNumber15(1);
			
			maintance.setAnsweredCall(result.getContact());
			maintance.setOfficerDirectaName(result.getOtherContact());
			maintance.setRetries(result.getRetries() == null ? 0: Integer.parseInt(result.getRetries()));
			maintance.setComments(result.getComments());
			maintance.setOfficerBEMName(result.getBemCentral());
			
			//Obtener la entidad Estatus referente al id del resultado de la llamada
			StatusDirecta status = statusDirectaBean.findById(Integer.parseInt(result.getStatus()));
			maintance.setStatus(status);

			//Agregar la entidad a la lista
			maintanceList.add(maintance);
			
		}
		
		return maintanceList;
	}
	
	
	private void verifyAndUpdate(Date processDate, List<Maintance> list) {

		for (Maintance receivedEntity : list) {

			// Revisamos si existe el mantenimiento para la referencia indicada
			Maintance foundEntity = this.maintanceBean.findById(receivedEntity.getId());

			if (foundEntity != null) {

				foundEntity.setIsValidAccountNumber1(receivedEntity
						.getIsValidAccountNumber1());
				foundEntity.setIsValidAccountNumber2(receivedEntity
						.getIsValidAccountNumber2());
				foundEntity.setIsValidAccountNumber3(receivedEntity
						.getIsValidAccountNumber3());
				foundEntity.setIsValidAccountNumber4(receivedEntity
						.getIsValidAccountNumber4());
				foundEntity.setIsValidAccountNumber5(receivedEntity
						.getIsValidAccountNumber5());
				foundEntity.setIsValidAccountNumber6(receivedEntity
						.getIsValidAccountNumber6());
				foundEntity.setIsValidAccountNumber7(receivedEntity
						.getIsValidAccountNumber7());
				foundEntity.setIsValidAccountNumber8(receivedEntity
						.getIsValidAccountNumber8());
				foundEntity.setIsValidAccountNumber9(receivedEntity
						.getIsValidAccountNumber9());
				foundEntity.setIsValidAccountNumber10(receivedEntity
						.getIsValidAccountNumber10());
				foundEntity.setIsValidAccountNumber11(receivedEntity
						.getIsValidAccountNumber11());
				foundEntity.setIsValidAccountNumber12(receivedEntity
						.getIsValidAccountNumber12());
				foundEntity.setIsValidAccountNumber13(receivedEntity
						.getIsValidAccountNumber13());
				foundEntity.setIsValidAccountNumber14(receivedEntity
						.getIsValidAccountNumber14());
				foundEntity.setIsValidAccountNumber15(receivedEntity
						.getIsValidAccountNumber15());
				foundEntity.setAnsweredCall(receivedEntity.getAnsweredCall());
				foundEntity.setOfficerDirectaName(receivedEntity
						.getOfficerDirectaName());

				Integer retries = foundEntity.getRetries() == null ? 0
						: foundEntity.getRetries();
				retries += receivedEntity.getRetries();

				foundEntity.setRetries(retries);
				foundEntity.setComments(receivedEntity.getComments());
				foundEntity.setOfficerBEMName(receivedEntity
						.getOfficerBEMName());

				foundEntity.setStatus(receivedEntity.getStatus());
				foundEntity.setUpdateDate(new Formatter().formatDate(
						processDate).getTime());

				// Actualizar el mantenimiento
				maintanceBean.update(foundEntity);

			}
		}
	}
	
	/**
	 * Parse a InputStream from txt file
	 * @param is : InputStream
	 * @return : List of Maintance entities
	 * @throws IOException
	 */
	private List<Maintance> parseTxtLayout(InputStream is) throws IOException{
		
		HashMap<String,Maintance> records= new HashMap<String,Maintance>();
		Maintance record= null;
		
		//create BufferedReader to read InputStream
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = "";
		int lineNumber = 0;
		int accountId = 0;
		
		//read pipes separated file line by line
		while( (line = br.readLine()) != null)
		{
			if(lineNumber > 0){//The header is the first line
				
				record = parseToMaintance(line, "\\|");
				
				if (record != null){
					
					if(records.containsKey(record.getMerchantNumber())){
						//validar que el numero de cuenta no se encuentre repetido.
						//Si no se encuentra repetido, lo agregamos a la entidad.
						if(!validateIsDuplicateAccount(records.get(record.getMerchantNumber()), record.getAccountNumber1())){
							
							//Insertar en la entidad el numero de cuenta es su respectiva propiedad
							setAccountInMaintance(records.get(record.getMerchantNumber()),accountId, record.getAccountNumber1());
							accountId++;
						}
					}else{
						//Registro Nuevo
						record.setMaintanceType("MANTENIMIENTO");
						record.setResend(0);
						
						StatusDirecta status = new StatusDirecta();
						status.setId(DirectaStatus.HEB_PROCESADO.value());
						record.setStatus(status);
						
						records.put(record.getMerchantNumber(),record);
						
						accountId = 2;
					}
						
				}
			}
			
			lineNumber++;
		}

		//Convert HashMap<Strint,Maintance> to List<Maintance>
		List<Maintance> values = new ArrayList<Maintance>(records.values());

		return values;
	}
	
	private boolean validateIsDuplicateAccount(Maintance record, String accountNumber){
		boolean isDuplicate = false;
		
		if (record.getAccountNumber1().equals(accountNumber) ||
		(record.getAccountNumber2() !=null && record.getAccountNumber2().equals(accountNumber)) ||
		(record.getAccountNumber3() !=null && record.getAccountNumber3().equals(accountNumber)) ||
		(record.getAccountNumber4() !=null && record.getAccountNumber4().equals(accountNumber)) ||
		(record.getAccountNumber5() !=null && record.getAccountNumber5().equals(accountNumber)) ||
		(record.getAccountNumber6() !=null && record.getAccountNumber6().equals(accountNumber)) ||
		(record.getAccountNumber7() !=null && record.getAccountNumber7().equals(accountNumber)) ||
		(record.getAccountNumber8() !=null && record.getAccountNumber8().equals(accountNumber)) ||
		(record.getAccountNumber9() !=null && record.getAccountNumber9().equals(accountNumber)) ||
		(record.getAccountNumber10() !=null && record.getAccountNumber10().equals(accountNumber)) ||
		(record.getAccountNumber11() !=null && record.getAccountNumber11().equals(accountNumber)) ||
		(record.getAccountNumber12() !=null && record.getAccountNumber12().equals(accountNumber)) ||
		(record.getAccountNumber13() !=null && record.getAccountNumber13().equals(accountNumber)) ||
		(record.getAccountNumber14() !=null && record.getAccountNumber14().equals(accountNumber)) ||
		(record.getAccountNumber15() !=null && record.getAccountNumber15().equals(accountNumber))){
			
			isDuplicate = true;
			
		}
		
		
		return isDuplicate;
	}
	
	/**
	 * Set account number into Maintance entity
	 * @param record: maintance Entity
	 * @param accountId: identifier account property
	 * @param accountNumber: account number 
	 */
	private void setAccountInMaintance(Maintance record,int accountId,String accountNumber){
		switch (accountId) {
		
		case 2://account2	
			record.setAccountNumber2(accountNumber);
			break;
		case 3://merchantNumber
			record.setAccountNumber3(accountNumber);
			break;
		case 4://merchantNumber
			record.setAccountNumber4(accountNumber);
			break;
		case 5://merchantNumber
			record.setAccountNumber5(accountNumber);
			break;
		case 6://merchantNumber
			record.setAccountNumber6(accountNumber);
			break;
		case 7://merchantNumber
			record.setAccountNumber7(accountNumber);
			break;
		case 8://merchantNumber
			record.setAccountNumber8(accountNumber);
			break;
		case 9://merchantNumber
			record.setAccountNumber9(accountNumber);
			break;
		case 10://merchantNumber
			record.setAccountNumber10(accountNumber);
			break;
		case 11://merchantNumber
			record.setAccountNumber11(accountNumber);
			break;
		case 12://merchantNumber
			record.setAccountNumber12(accountNumber);
			break;
		case 13://merchantNumber
			record.setAccountNumber13(accountNumber);
			break;
		case 14://merchantNumber
			record.setAccountNumber14(accountNumber);
			break;
		case 15://merchantNumber
			record.setAccountNumber15(accountNumber);
			break;
		}
		
	}

	/**
	 * Create a maintance entity 
	 * @param line: Contains values to maintance entity
	 * @param delimiter: regex the delimiting regular expression
	 * @return the maintance entity computed by splitting this string(line) around matches of the given regular expression
	 */
	private Maintance parseToMaintance(String line,String delimiter){
		
		Maintance entity = new Maintance();
		
		boolean isValid = true;
		EncryptBd decrypt = new EncryptBd();
		
		//break comma separated line using "|"
		 String[] tokens = line.split(delimiter);
		 
		 for (int tokenNumber=0; tokenNumber<tokens.length; tokenNumber++){
			 
			 if (isValid){
				 
				switch (tokenNumber) {
				case 0://merchantNumber
					String merchantNumber = tokens[tokenNumber];
					if(!merchantNumber.equals("")){
						if(null != entity){
							entity.setMerchantNumber(Formatter.fixLenght(merchantNumber,8));
						}
					}else{
						//if don't have merchant number is a invalid Maintance
						entity =null;
						//Stop while
						isValid =false;
					}
						
					break;
				case 1://companyName
					if(null != entity){
						entity.setCompanyName(tokens[tokenNumber]);
					}
					break;
				case 2://legalRepresentative
					if(null != entity){
						entity.setLegalRepresentative(tokens[tokenNumber]);
					}
					break;
				case 3://manager1
					if(null != entity){
						entity.setManager1(tokens[tokenNumber]);
					}
					break;
				case 4://email1
					if(null != entity){
						entity.setEmail1(tokens[tokenNumber]);
					}
					break;
				case 5://manager2
					if(null != entity){
						entity.setManager2(tokens[tokenNumber]);
					}
					break;
				case 6://email2
					if(null != entity){
						entity.setEmail2(tokens[tokenNumber]);
					}
					break;
				case 7://manager3
					if(null != entity){
						entity.setManager3(tokens[tokenNumber]);
					}
					break;
				case 8://email3
					if(null != entity){
						entity.setEmail3(tokens[tokenNumber]);
					}
					break;
				case 9://CR
					if(null != entity){
						entity.setCR(Formatter.fixLenght(tokens[tokenNumber],5));
					}
					break;
				case 10://branchName
					if(null != entity){
						entity.setBranchName(tokens[tokenNumber]);
					}
					break;
				case 11://module
					if(null != entity){
						entity.setModule(tokens[tokenNumber]);
					}
					break;
				case 12://territorial
					if(null != entity){
						entity.setTerritorial(tokens[tokenNumber]);
					}
					break;
				case 13://sic
					if(null != entity){
						if (Formatter.isNumeric(tokens[tokenNumber]))
							entity.setSic(Integer.parseInt(tokens[tokenNumber]));
					}
					break;
				case 14://phoneNumber1
					if(null != entity){
						entity.setPhoneNumber1(tokens[tokenNumber]== null ? "": tokens[tokenNumber].trim());
					}
					break;
				case 15://phoneNumber2
					if(null != entity){
						entity.setPhoneNumber2(tokens[tokenNumber]== null ? "": tokens[tokenNumber].trim());
					}
					break;
				case 16://account
					if(null != entity){
						entity.setAccountNumber1(decrypt.encrypt(tokens[tokenNumber]));
					}
					break;
				}
				 
			 }else
				 break;
			 
		 }

		return entity;
	}
	
	
	private String getCellValue(Row row, int position) {
		String result = "";
		try {
			if (row.getCell(position).getCellType() == Cell.CELL_TYPE_STRING) {
				result = row.getCell(position).getRichStringCellValue().getString();
			} else if (row.getCell(position).getCellType() == Cell.CELL_TYPE_NUMERIC) {	 
				double doubleValue = row.getCell(position).getNumericCellValue();
				if ((doubleValue % 1 ) == 0 ) { //Si es igual a 0, entonces es entero sin decimales
//				if ((doubleValue % 1d) > 1) {
					result = Integer.toString((int) doubleValue);
//					result = Double.toString(doubleValue);
				} else { //si tiene decimales
					result = Double.toString(doubleValue);
//					result = Integer.toString((int) doubleValue);
				}
			} else if (row.getCell(position).getCellType() == Cell.CELL_TYPE_FORMULA) {
				double doubleValue = row.getCell(position).getNumericCellValue();
				if ((doubleValue % 1d) > 1) {
					result = Double.toString(doubleValue);
				} else {
					result = Integer.toString((int) doubleValue);
				}
			}
		} catch (NullPointerException e) {
			// e.printStackTrace();
		}

		return result;
	}
	

	public boolean processRemoteExecutive() throws IOException {
		
		boolean flag = false;
		List<Executive> records = new ArrayList<Executive>();

		records = processRemote(RESPONSE_TYPE_EXECUTIVE);
		
		if(records.size()>0){
			this.updateExecutives(records);
			flag =true;
		}
		
		return flag;
	}
	
	public boolean processRemoteExecutiveBranch() throws IOException {
		
		
		List<ExecutiveBranch> records 	= new ArrayList<ExecutiveBranch>();
		boolean flag 					= false;
		records 						= processRemote(RESPONSE_TYPE_EXECUTIVE_BRANCH);
		
		//this.probandoExecutiveBranch();
		//flag =true;
		if(records.size()>0){
			this.updateExecutiveBranch(records);
			flag =true;
		}
		
		return flag;
	}

	private List<Executive> parseExecutiveLayout(InputStream is) throws IOException {
		
		List<Executive> result 	= new ArrayList<Executive>();
		POIFSFileSystem fs 		= new POIFSFileSystem(is);
		Workbook wb 			= new HSSFWorkbook(fs);
		Sheet s 				= wb.getSheetAt(0);
		int rows 				= s.getPhysicalNumberOfRows();
		int id 					= 1;
		Executive entity;
		//se cambia el inicio a 8 porque se pidió eliminar las filas en blanco del template de ejecutivos el excel debe empezar en renglon 9
		for (int i = 8; i < rows; i++) {
			Row row = s.getRow(i);
			int cells = (row != null) ? row.getPhysicalNumberOfCells() : 0;
			if (cells > 0) {
				try {
					entity = new Executive();

					if (getCellValue(row, 1) != null && getCellValue(row, 1).trim().length() > 1) {
						
						/*Column - Property(Maintance)	
						 * 1  - userId
						 * 5  - position
						 * 3  - complete name
						 * */
						entity.setId(id);	
						entity.setUserid("A"+Formatter.fixLenght(getCellValue(row, 1),7));
						entity.setPosition(getCellValue(row, 5));
						entity.setName(getCellValue(row, 3));
						entity.setLastname(" ");
						entity.setMothersname(" ");
						entity.setIdRegion(getCellValue(row, 22));
						entity.setIdTerritorio(getCellValue(row, 23));
						entity.setCoordinador(getCellValue(row, 24));
						result.add(entity);
						id++;
					}
					
				} catch (NumberFormatException e) {
					System.err.println("Valor : " + getCellValue(row, 1) + " + " + i + " \n" + e);
				} catch (NullPointerException e) {
					System.err.println("Registro " + i + " nulo: " + e);
				}
			}
		}

		//Añadir a los usuarios Administradores de la aplicacion
		entity = new Executive();
		entity.setId(id);
		entity.setUserid("HMEDG870");
		entity.setPosition("Administrador");
		entity.setName("Georgina Merla Davila");
		entity.setCoordinador(Boolean.TRUE);
		entity.setIdTerritorio("7");
		result.add(entity);
		
		entity = new Executive();
		entity.setId(id+1);
		entity.setUserid("HRITM780");
		entity.setPosition("Administrador");
		entity.setName("Marcela Rios Trevino");
		entity.setCoordinador(Boolean.TRUE);
		entity.setIdTerritorio("7");
		result.add(entity);
		
		return result;
	}
	
	private void updateExecutives(List<Executive> list) {

		//Eliminar e insertar los ejecutivos siempre y cuando existan elementos en la lista
		if(list.size() > 0){
			int line = 10;
			
			//borrar los ejecutivos actuales.
			executiveBean.deleteAll();
			
			//insertar los ejecutivos obtenidos del layout.
			for(Executive entity: list){
				try {
					executiveBean.save(entity);
				} catch (Exception e) {
					System.err.println("Ocurrio un error al insertar el registro:"+line+" ERROR:"+e.getMessage());
				}
				line++;
			}							
		}
		
	}
	

		
	/**
	 * Parse a InputStream from txt file
	 * @param is : InputStream
	 * @return : List of Cities entities
	 * @throws IOException
	 */
	private List<Hashtable<String,List<String>>> parseCitiesLayout(InputStream is) throws IOException{
		
		Hashtable<String,List<String>> records= new Hashtable<String,List<String>>();
		Cities record= null;
		
		//create BufferedReader to read InputStream
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = "";
		int lineNumber = 0;
		
		//read pipes separated file line by line
		while( (line = br.readLine()) != null)
		{
			if(lineNumber > 1){//The headers are the two first lines
				
				record = parseLineToCity(line, "\\|");
				
				if (record != null){
					
					if(records.containsKey(record.getId_state())){
						
						List<String> list = records.get(record.getId_state());
						
						if(!list.contains(record.getName())){
							list.add(record.getName());
						}
					
					}else{
						List<String> list = new ArrayList<String>();
						list.add(record.getName());
						records.put(record.getId_state(), list);
					}						
				}
			}
			
			lineNumber++;
		}
		
		List<Hashtable<String,List<String>>> values= new ArrayList<Hashtable<String,List<String>>>();
		values.add(records);
		
		return values;
	}

	private Cities parseLineToCity(String line, String delimiter){
		Cities city = new Cities();
		
		//break comma separated line using "|"
		 String[] tokens = line.split(delimiter);
		 
		//Municipio
		city.setName(tokens[3].trim().toUpperCase());
		//Estado
		city.setId_state(tokens[4].trim().toUpperCase());
		 
		return city;
	}

	public boolean processRemoteCitiesAndStates() throws IOException {
		
		boolean flag = false;
		List<Hashtable<String,List<String>>> records= new ArrayList<Hashtable<String,List<String>>>();

		records = processRemote(RESPONSE_TYPE_CITY);
		
		if(records.size()>0){
			this.updateCitiesAndStates(records);
			flag =true;
		}
		
		return flag;
	}

	private void updateCitiesAndStates(List<Hashtable<String,List<String>>> list) {

		Hashtable<String,List<String>> citiesStates = list.get(0);
		
		//Extraer del hashtable los estados y ordernarlos
		List<String> stateList = Collections.list(citiesStates.keys());		
		Collections.sort(stateList);
		
		//Eliminar el contenido de los catalogos de Estados y Ciudades
		statesBean.deleteAll();
		citiesBean.deleteAll();
		
		//Contadores
		int stateId =1;
		int cityId =1;
		States stateEntity;
		Cities cityEntity;
		List<String> citiesList;
		
		//Poblar los Catalogos: States y Cities
		for(String stateName: stateList){
			
			//Crear instancia del estado
			stateEntity = new States();
			stateEntity.setId(stateId);
			stateEntity.setName(stateName);

			//Guardar el estado
			statesBean.save(stateEntity);
			
			//Extraer las ciudades relacionadas al estado
			citiesList = citiesStates.get(stateName);
			Collections.sort(citiesList);
			
			for(String cityName: citiesList){
				cityEntity = new Cities();
				cityEntity.setId(cityId);
				cityEntity.setId_state(stateName);
				cityEntity.setName(cityName);
				
				//Guardar la Ciudad
				citiesBean.save(cityEntity);
				
				cityId++;
			}
			
			stateId++;
		}
	}	
	
	
		private List<ExecutiveBranch> parseExecutiveBranchLayout(InputStream is) throws IOException {
		
			List<ExecutiveBranch> result 	= new ArrayList<ExecutiveBranch>();
			POIFSFileSystem fs 				= new POIFSFileSystem(is);
			Workbook wb 					= new HSSFWorkbook(fs);
			Sheet s 						= wb.getSheetAt(0);
			int rows 						= s.getPhysicalNumberOfRows();
			ExecutiveBranch executiveBranch;
			
			for (int i = 7; i < rows; i++) {// inicia la información en el indice 7 (fila 8) del template excel de sucursales
				Row row = s.getRow(i);
				int cells = (row != null) ? row.getPhysicalNumberOfCells() : 0;
				if (cells > 0) {
					try {
						executiveBranch = new ExecutiveBranch();
	
						if (getCellValue(row, 1) != null && getCellValue(row, 1).trim().length() > 1) {
							
							/*Column - Property(Maintance)
							 * 0  - CR  	
							 * 1  - Numero Empleado Ejecutivo
							 * 4  - Sucursal (debe tener el valor, NO FORMULA)
							 * 12 - ID Region
							 * 13 - ID Territorio
							 * */
							
							executiveBranch.setCr( Integer.valueOf( getCellValue(row, 0) ) );
							executiveBranch.setNumeroEmpleadoEjecutivo(  getCellValue(row, 1)  );
							executiveBranch.setSucursal( getCellValue(row, 4) );
							executiveBranch.setIdRegion( Integer.valueOf( getCellValue(row, 12) ) );
							executiveBranch.setIdTerritorio( Integer.valueOf( getCellValue(row, 13) ) );
							result.add(executiveBranch);
						}
						
					} catch (NumberFormatException e) {
						System.err.println("Valor : " + getCellValue(row, 1) + " + " + i + " \n" + e);
					} catch (NullPointerException e) {
						System.err.println("Registro " + i + " nulo: " + e);
					}
				}
			}			
			return result;
		}
		
		
		private void updateExecutiveBranch(List<ExecutiveBranch> list) {

			//Eliminar e insertar las sucursales siempre y cuando existan elementos en la lista
			if(list.size() > 0){
				int line = 9;
				
				//borrar los ejecutivos actuales.
				executiveBranchBean.deleteAll();
				
				//insertar los ejecutivos obtenidos del layout.
				for(ExecutiveBranch executiveBranch: list){
				
					try {
						executiveBranchBean.save(executiveBranch);
					} catch (Exception e) {
						System.err.println("Ocurrio un error al insertar el registro:"+line+" ERROR:"+e.getMessage());
					}
					line++;
				}							
			}
			
		}
		
		
		public boolean processRemoteCommisionPlan() throws IOException {
			boolean flag = false;
			List<CommisionPlan> records = new ArrayList<CommisionPlan>();
			records = processRemote("Comisiones");//lee archivo  
			System.out.println("termina de leer archivo: "+new java.sql.Timestamp(System.currentTimeMillis()));
			if(records!=null && records.size()>0){
				updateCommisions(records);//actualiza BD 
				System.out.println("termina de actualizar BD: "+new java.sql.Timestamp(System.currentTimeMillis()));
				flag =true;
			}
			return flag;
		}
		
		
		/**
		 * @author gmerla //metodo para leer el layout de comisiones
		 * @param is
		 * @return
		 * @throws IOException
		 */
		private List<CommisionPlan> parseComissionLayout(InputStream is) throws IOException {
			List<CommisionPlan> result 	= new ArrayList<CommisionPlan>();
			POIFSFileSystem fs 				= new POIFSFileSystem(is);
			Workbook wb 					= new HSSFWorkbook(fs);
			Sheet s 						= wb.getSheetAt(0);
			int rows 						= s.getPhysicalNumberOfRows();
			CommisionPlan commisionPlan;
			CommisionPlanPK commisionPlanPK;
			CommisionLayout comLayout = new CommisionLayout();
			List<ContractMessageErrors> errores = new ArrayList<ContractMessageErrors>();
			Set<CommisionPlanPK> listaPK = new HashSet<CommisionPlanPK>();
			long tiempo = System.currentTimeMillis();
			LOG.log(Level.INFO,"Consultando todos los planes");
			List<Plan>listaPlanes=planBean.findAll();
			long tiempoRestante=System.currentTimeMillis()-tiempo;
			LOG.log(Level.INFO,"Termina consulta de planes: "+tiempoRestante);
			List<Categories> listaGiros = new ArrayList<Categories>();
			LOG.log(Level.INFO,"Consultando todos los giros");
			tiempo=System.currentTimeMillis();
			listaGiros=categoriesBean.findAquirerAll();
			tiempoRestante=System.currentTimeMillis()-tiempo;
			LOG.log(Level.INFO,"Termina consulta de giros: "+tiempoRestante);
			listaErroresV.clear();
			listaErroresC.clear();
			LOG.log(Level.INFO,"Inicia validacion y creacion de objetos por registro");
			tiempo=System.currentTimeMillis();
			for (int i = 1; i < rows; i++) {// inicia la información en el indice 1 (fila 2) del template excel de comisiones
				Row row = s.getRow(i);
				int cells = (row != null) ? row.getPhysicalNumberOfCells() : 0;
				if (cells > 0) {
					try {
						//validar registro
						//if lista errores es cero continua con crear el objeto
						validateRow(row, listaPlanes, listaGiros); //llena la lista de erroresV de validaciones
						boolean hayErrorEnRegistro=(listaErroresV!=null && !listaErroresV.isEmpty());
						if(hayErrorEnRegistro){
							continue;//para ir a la siguiente iteracion
						}
						commisionPlanPK = new CommisionPlanPK();
						Plan plan=findPlanId(getCellValue(row, 0), listaPlanes);
							/*Columnas de comisionestpv
							 *	 	0-Plan
							 * 		1-Giro
							 * 		2-Porcentaje comision TC
							 * 		3.Porcentaje comision TD
							 * 		4-Nomina
							 * 		5-Porcentaje comision INTNL
							 * */
							commisionPlanPK.setPlanId(plan.getPlanId());
								String originalCode=getCellValue(row,1);
								String newCode=String.format("%06d", Integer.parseInt(originalCode));
//								System.out.println(newCode);//que valor nuevo hizo
								commisionPlanPK.setCategoryCode(newCode);
							String nomina =getCellValue(row, 4);
							commisionPlanPK.setTpvNomina((nomina.equalsIgnoreCase("si")?true:false));
							if( listaPK.contains(commisionPlanPK)){
								ContractMessageErrors error = new ContractMessageErrors();
								error.setMessage("Comision duplicada linea: "+(row.getRowNum()+1)+": "+commisionPlanPK.getCategoryCode()+"-"+commisionPlanPK.getPlanId());
								listaErroresC.add(error);
							}else{
								listaPK.add(commisionPlanPK);
								commisionPlan = new CommisionPlan();
								commisionPlan.setCommisionPlanPK(commisionPlanPK);
								commisionPlan.setPlan(plan);
								commisionPlan.setCommisionTC(redondear(getCellValue(row, 2)));
								commisionPlan.setCommisionTD(redondear(getCellValue(row, 3)));
								commisionPlan.setNominaSelected((nomina.equalsIgnoreCase("Si")?true:false));
								commisionPlan.setCommisionINTNL(redondear(getCellValue(row, 5)));
								result.add(commisionPlan);
							}
					} catch (NumberFormatException e) {
						System.err.println("Valor : " + getCellValue(row, 1) + " + " + i + " \n" + e);
					} catch (NullPointerException e) {
						System.err.println("Registro " + i + " nulo: " + e);
					}
				}
			}
			tiempoRestante=System.currentTimeMillis()-tiempo;
			LOG.log(Level.INFO,"Termina validacion y creacion de objetos: "+tiempoRestante);
			return result;
		}
		
		/**
		 * Metodo para revolver el id del plan del layout de comisiones
		 * @param plan
		 * @return
		 */
		public Plan findPlanId(String nombrePlan, List<Plan>listaPlanes){
			for(Plan p : listaPlanes){
				if(p.getName().equalsIgnoreCase(nombrePlan)){
					return p;
				}
			}
			return null;
		}
		
		private void updateCommisions(List<CommisionPlan> list) {
			//Eliminar e insertar las comisiones siempre y cuando existan elementos en la lista
			if(list.size() > 0){
				int line = 1;
				//borrar las comisiones actuales.
				System.out.println("inicia borrar las actuales: "+new java.sql.Timestamp(System.currentTimeMillis()));
				commisionPlanBean.deleteAll();
				//insertar las comisiones obtenidos del layout.
				System.out.println("inicia guardar uno por uno: "+new java.sql.Timestamp(System.currentTimeMillis()));
				for(CommisionPlan entity: list){
					try {
						commisionPlanBean.save(entity);
					} catch (Exception e) {
						System.err.println("Ocurrio un error al insertar el registro:"+line+" ERROR:"+e.getMessage());
					}
					line++;
				}							
			}
		}
		/**
		 *  --------------------------------- validacion layout comisiones
		 */
		
		public void validateRow(Row row, List<Plan> listaPlanes,  List<Categories> listaGiros) throws IOException{
				int cells = (row != null) ? row.getPhysicalNumberOfCells() : 0;
				if (cells > 0) {
						validarPlan(row, listaPlanes);
						validarGiro(row, listaGiros);
						validarComisiones(row);
						validarNominaTPV(row);
				}
		}
		
		private void agregarError(String mensaje){
		    ContractMessageErrors e=new ContractMessageErrors();
		        e.setMessage(mensaje);
		        listaErroresV.add(e);
		}
		
		private void validarPlan(Row row, List<Plan>listaPlanes){
			boolean error=true;
			String valor =getCellValue(row, 0);
			if(valor==null||valor.trim().isEmpty()){
				error=true;
			}else{
				for(Plan p :listaPlanes){
					if(valor.equalsIgnoreCase(p.getName())){
						error = false;
					}
				}
			}
			if(error){
				int linea = row.getRowNum()+1;
				agregarError("Plan no valido en linea: "+linea);
			}
		}

		private void validarGiro(Row row, List<Categories> listaGiros){
			boolean error=true;
			String valor=getCellValue(row, 1);
			if(valor==null||valor.trim().isEmpty()){
				error=true;
			}else{
				for(Categories giro:listaGiros){
					String giroStr = giro.getCode().toString();
					if(valor.equalsIgnoreCase(giroStr)){
						error=false;
					}
				}
			}
			if(error){
				agregarError("Giro no valido en linea: "+(row.getRowNum()+1));
			}
		}
	
		private void validarComisiones(Row row){
			String valorTC="";
			String valorTD="";
			String valorINTNL = "";
			Double ctc=0d;
			Double ctd=0d;
			try {
				boolean error=true;
				valorTC=getCellValue(row, 2);
				valorTD=getCellValue(row, 3);
				valorINTNL=getCellValue(row, 5);
				if(valorTC.trim().isEmpty()||valorTD.trim().isEmpty()||valorINTNL.trim().isEmpty()){
					error=true;
				}else{
					error=false;
					 ctc = redondear(valorTC);
					 ctd = redondear(valorTD);
				}
				if(error){
					agregarError("Comisiones no validas en linea: "+(row.getRowNum()+1));
				}
			} catch (NumberFormatException e) {
				agregarError("Error en las comisiones linea: "+(row.getRowNum()+1));
			}
		}
		
		private void validarNominaTPV(Row row){
			String valor=getCellValue(row, 4);
			boolean error=true;
			if(valor==null||valor.trim().isEmpty()){
				error=true;
			}else{
				if(valor.equalsIgnoreCase("si")||valor.equalsIgnoreCase("no")){
					error=false;
				}
			}
			if(error){
				agregarError("El valor en la columna 'NominaTPV' no es valido"+(row.getRowNum()+1));
			}
		}
		
	public static double redondear(String value) {
		Double decimal = new Double(value);
		return redondear(decimal, 2);
	}

	public static double redondear(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static String formatoDecimal(double valor) {
		DecimalFormat df = new DecimalFormat("0.00##");
		String result = df.format(valor);
		return result;
	}
}
