
package com.banorte.contract.layout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.banorte.contract.business.ContractRemote;
import com.banorte.contract.business.EmployeeRemote;
import com.banorte.contract.business.MaintanceRemote;
import com.banorte.contract.business.StatusContractRemote;
import com.banorte.contract.dto.ContractAcquirerDTO;
import com.banorte.contract.dto.ContractAllProductsDTO;
import com.banorte.contract.dto.ContractNominaDTO;
import com.banorte.contract.dto.ContractSipDTO;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractStatusHistory;
import com.banorte.contract.model.FailReference;
import com.banorte.contract.model.LoadFile;
import com.banorte.contract.model.Maintance;
import com.banorte.contract.model.StatusContract;
import com.banorte.contract.model.WrapContractGenerate;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.ContractUtil;
import com.banorte.contract.util.DirectaStatus;
import com.banorte.contract.util.FTPProperties;
import com.banorte.contract.util.FileLayout;
import com.banorte.contract.util.Formatter;
import com.banorte.contract.web.EjbInstanceManager;

/**
 * 
 * @author Darvy Arch
 */
public abstract class LayoutOperationsTemplate {
	private Logger logger = Logger.getLogger(LayoutOperationsTemplate.class
			.getName());

	private ContractRemote contractBean;
	private StatusContractRemote statusBean;	
	private MaintanceRemote maintanceBean;
	private List<Contract> contracts = new ArrayList<Contract>();
	private List<Maintance> maintances = new ArrayList<Maintance>();
	private final Integer STATUSID_INPROCESS = 5;
	private final Integer STATUSID_TOACTIVATE = 11;
	private final Integer STATUSID_EDIT = 2;
	private final String ACTIVATE = "BEMActivacion";
	private final String OIP = "OIP";
	private final String ABEM = "ABEM";
	private final String MBEM = "MBEM";
	private final String DIRECTA = "DIRECTA";
	private final String MAINTANCENEW = "ALTA";

	private List<List<String>> contentFile;	
	Map<Integer, String> comercialPlanMap ;
	private List<ContractAllProductsDTO> contractsDTO; //para el reporte AllProductsDTO (richer)
	private List<ContractAcquirerDTO> contractAcquirerDTO; //para el reporte AcquirerDTO (esteban)
	private List<ContractNominaDTO> contractNominaDTO; //para el reporte ContractNominaDTO
	private List<ContractSipDTO> contractSipDTO;//para el reporte SipDTO (pyme)
	private EmployeeRemote employeeBean;
	
	public void setContractBean(ContractRemote contractBean) {
		this.contractBean = contractBean;
	}
	public ContractRemote getContractBean() {
		return contractBean;
	}
	
	public void setMaintanceBean(MaintanceRemote maintanceBean) {
		this.maintanceBean = maintanceBean;
	}

	public void setStatusBean(StatusContractRemote statusBean) {
		this.statusBean = statusBean;
	}

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(Contract contracts) {
		this.contracts.add(contracts);
	}

	// Maintance Property
	public void setMaintance(Maintance maintance) {
		this.maintances.add(maintance);
	}
	public void setMaintances(List<Maintance> maintances) {
		this.maintances.addAll(maintances);
	}
	public List<Maintance> getMaintances() {
		return this.maintances;
	}
	public List<ContractAllProductsDTO> getContractsDTO() {
		return contractsDTO;
	}
	public void setContractsDTO(List<ContractAllProductsDTO> contractsDTO) {
		this.contractsDTO = contractsDTO;
	}
	public List<ContractAcquirerDTO> getContractAcquirerDTO() {
		return contractAcquirerDTO;
	}
	public void setContractAcquirerDTO(List<ContractAcquirerDTO> contractAcquirerDTO) {
		this.contractAcquirerDTO = contractAcquirerDTO;
	}
	public List<ContractNominaDTO> getContractNominaDTO() {
		return contractNominaDTO;
	}
	public void setContractNominaDTO(List<ContractNominaDTO> contractNominaDTO) {
		this.contractNominaDTO = contractNominaDTO;
	}
	public List<ContractSipDTO> getContractSipDTO() {
		return contractSipDTO;
	}
	public void setContractSipDTO(List<ContractSipDTO> contractSipDTO) {
		this.contractSipDTO = contractSipDTO;
	}
	public EmployeeRemote getEmployeeBean() {
		return employeeBean;
	}
	public void setEmployeeBean(EmployeeRemote employeeBean) {
		this.employeeBean = employeeBean;
	}
	
	
	public void createTextLayout(String productName, boolean saveFile) {
		logger.setLevel(Level.WARNING);
		if (contracts.size() > 0) {
			// Iniciamos Generacion del Contenido en base al Contrato
			List<List<String>> result = new ArrayList();
			for (Contract contract : this.getContracts()) {
				try{
					result.add(getContent(contract));
				}catch (Exception e){
					logger.log(Level.WARNING,"Error al procesar la informacion del contrato "+ contract.getReference());
				}
			}
			try {

				byte[] content = new LayoutTempleteContract().createTextLayout(
						productName, result);

				FileLayout fLayout = new FileLayout();
				FTPProperties fprops = new FTPProperties();
				Formatter format = new Formatter();

				String date = format.formatDateToStringTextLayout(new Date());

				// Como siguiente, enviamos el archivo al servidor de FTP
				fLayout.storeRemoteOIPFile(productName + "PYMCTESTPV_" + date
						+ ".txt", content, fprops);

				if (saveFile) {
					try {
						fLayout.storeLocalFile(productName + "PYMCTESTPV_"
								+ date + ".txt", content);
					} catch (IOException e) {
						logger.log(Level.WARNING,"No se pudo almacenar el archivo "+ productName);
					}
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE,
						"Ocurrio un error al procesar el archivo de "
								+ productName, e);
			}
		} else {
			// Si no hubo registros se notifica que se termino correctamente sin
			// ninguna coincidencia.
			logger.log(Level.INFO, productName + " termino OK sin Registros!");
		}

	}

	public WrapContractGenerate createLayout(String productName, boolean saveFile,boolean header, Integer productTypeId) {
		
		logger.setLevel(Level.WARNING);
		WrapContractGenerate wrapContractGenerate 	= new WrapContractGenerate();
		LoadFile loadFile 							= new LoadFile();
		List<LoadFile>	loadedFileInfo 				= new ArrayList<LoadFile>();
		List<FailReference>	listFailReference 		= new ArrayList<FailReference>();
		List<Contract> listFailContracts			= new ArrayList<Contract>();
		List<Contract> listSuccessContracts			= new ArrayList<Contract>();
		Date creationDate 							= new Date();
		FailReference failReference 				= null;
		String fileName 							= ApplicationConstants.EMPTY_STRING;
	
		if (contracts.size() > 0) { 
			try {
				// Iniciamos Generacion del Contenido en base al Contrato
				
				List<List<String>> result = new ArrayList();
				for (Contract contract : this.getContracts()) {
					try {
						List<String> contenido = getContent(contract);
						result.add(contenido);
						listSuccessContracts.add(contract);
					}catch (Exception e){
						logger.log(Level.WARNING,"Error al procesar la informacion del contrato "+ contract.getReference());
						failReference = new FailReference();
						failReference.setError(e.getMessage());
						failReference.setReference(contract.getReference());
						listFailReference.add(failReference);
						listFailContracts.add(contract);
					}
				}

				// Obtenemos el archivo XSL del listado de Contratos
				byte[] content = new LayoutTempleteContract().createLayout(
						productName, result, header, getHeader());

				FileLayout fLayout = new FileLayout();
				FTPProperties fprops = new FTPProperties();
				Formatter format = new Formatter();
				String date = format.formatDateToStringLayout(creationDate);
				
				

				//Como siguiente, enviamos el archivo al servidor de FTP
				try {
					
					if(listSuccessContracts.size() > 0){
						if (productName.equals(ACTIVATE)){
								fileName= productName + "Layout-"+ date + ".xls";
								fLayout.storeRemoteActivateFile(fileName, content, fprops);    //DESBLOQUEAR SOLO PARA PRUEBAS EN DESARROLLO SE BLOQUEO POR QUE FALLA AL DEPOSITARSE EL DOCUMENTO EN EL FTP
								fLayout.storeLocalFile(fileName, content); //FIXME gina saveLocal
							}
						else{
								fileName = productName + "Layout-" + date+ ".xls";
								//fLayout.storeRemoteFile(fileName, content, fprops);      //DESBLOQUEAR SOLO PARA PRUEBAS EN DESARROLLO SE BLOQUEO POR QUE FALLA AL DEPOSITARSE EL DOCUMENTO EN EL FTP, cambiar para produccion
								fLayout.storeLocalFile(fileName, content); //FIXME gina saveLocal
						}
					}
					
					loadFile.setCreationDate(creationDate);
					loadFile.setFileName(fileName);
					loadFile.setFileType(ApplicationConstants.FILE_TYPE_ALTAS);
					loadFile.setProductType(productTypeId);
					loadFile.setNumberSuccess(listSuccessContracts.size());
					loadFile.setNumberFail(listFailReference.size());
					loadedFileInfo.add(loadFile);				
					

					StatusContract status = new StatusContract();
					// Por ultimo modificamos el status de los diferentes
					// Contratos
					for (Contract contract : listSuccessContracts) {
						if (productName.equals(ACTIVATE)) // Primero modificamos// el status
							status = statusBean.findById(STATUSID_TOACTIVATE); // autorizado  para su activacion
						else
							status = statusBean.findById(STATUSID_INPROCESS); // contrato formalizado

						contract.setStatus(status);
						ContractStatusHistory statusHistory = new ContractStatusHistory();
						statusHistory.setContract(contract);
						statusHistory.setStatusContract(status);
						statusHistory.setOffempnum("appsstb");
						statusHistory.setCommentary("APPSSTB envio el contrato a operaciones para su revision.");
						statusHistory.setModifydate(new Date());

						contract.add(statusHistory);

						contractBean.update(contract);
				
						// Si se pidio que se almacenara una copia del archivo
						// de manera local
						// Iniciamos el guardado
						if (saveFile) {
							try {
								fLayout.storeLocalFile(fileName, content);
							} catch (IOException e) {
								logger.log(Level.WARNING,"No se pudo almacenar el archivo " + productName);
							}
						} 
					}
					
					status = statusBean.findById(STATUSID_EDIT);
					
					for(Contract contract :listFailContracts ){
						
						contract.setStatus(status);
						ContractStatusHistory statusHistory = new ContractStatusHistory();
						statusHistory.setContract(contract);
						statusHistory.setStatusContract(status);
						statusHistory.setOffempnum("appsstb");
						statusHistory.setCommentary("APPSSTB Se encontro un error en la informacion del contrato y no se pudo enviar a Operaciones,por lo que se regreso a Edicion");
						statusHistory.setModifydate(new Date());

						contract.add(statusHistory);
						
						contract.setAuthoffempnum1(ApplicationConstants.EMPTY_STRING);
						contract.setAuthoffempnum2(ApplicationConstants.EMPTY_STRING);
						
						contractBean.update(contract);
						
					}
				} catch (Exception e) {
					
					logger.log(Level.SEVERE,"Ocurrio un error al procesar el archivo de " + productName, e);
				}
			} catch (IOException e) {
				logger.log(Level.SEVERE, null, e);
			}
			
			
		} else {
			// Si no hubo registros se notifica que se termino correctamente sin
			// ninguna coincidencia.
			logger.log(Level.INFO, productName + " termino OK sin Registros!");
		}

		wrapContractGenerate.setListFailReference(listFailReference);
		wrapContractGenerate.setLoadedFileInfo(loadedFileInfo);
		
		return wrapContractGenerate;
	}

	public abstract List<String> getContent(Contract contract) throws Exception;


	public abstract List<String> getHeader();

    public byte[] getContentTxtLayout(String productName,String delimiter, List<List<String>> contentFile){
    	
    	logger.setLevel(Level.WARNING);
    	byte[] content = null;
    	
		if (contentFile.size() > 0) {

			try {

				content = new LayoutTempleteContract().createTextLayout(contentFile, delimiter, null);

			} catch (Exception e) {
				logger.log(Level.SEVERE,
						"Ocurrio un error al procesar el contenido del archivo de "
								+ productName, e);
			}
		} else {
			// Si no hubo registros se notifica que se termino correctamente sin
			// ninguna coincidencia.
			logger.log(Level.INFO, productName + " termino OK sin Registros!");
		}
		
		return content;
    }

    public void saveRemoteFile(String productName, byte[] content,  boolean saveCopyLocal){
    	
    	logger.setLevel(Level.WARNING);
    	
		FileLayout fLayout = new FileLayout();
		Formatter format = new Formatter();
		String date = format.formatDateToStringTextLayout(new Date());
		FTPProperties fprops = new FTPProperties();
		String fileName = "";
		
		try {
			// Como siguiente, enviamos el archivo al servidor de FTP
	    	if(productName.equals(ABEM)){
	    		fileName = productName + date + ".txt";
				fLayout.storeSFTPRemoteFileABEM(fileName, content, fprops);
				
	    	}else if(productName.equals(MBEM)){
	    		
	    		fileName = productName + date + ".txt";
				fLayout.storeSFTPRemoteFileMBEM(fileName, content, fprops);
				
	    	}else if(productName.equals(OIP)){
	    		
	    		fileName = "PYMCTESTPV_" + date + ".txt";
	    		fLayout.storeRemoteOIPFile(fileName, content, fprops);
	    	
	    	}else if(productName.equals(DIRECTA)){
		    	
	    		fileName = productName + ApplicationConstants.LAYOUT + date + ".xls";
				fLayout.storeRemoteDirectaFile(fileName, content, fprops);
				
	    	}else if(productName.equals(ACTIVATE)){
	    		date = format.formatDateToStringLayout(new Date());
	    		fileName = productName + ApplicationConstants.LAYOUT + date + ".xls";
				fLayout.storeRemoteActivateFile(fileName, content, fprops);
				
	    	}else{
	    		date = format.formatDateToStringLayout(new Date());
	    		fileName = productName + ApplicationConstants.REPORT + date + ".xls";
	    		fLayout.storeLocalFile(fileName, content);//FIXME gina saveLocal usar local para probar
//	    		fLayout.storeRemoteReportFile(fileName, content, fprops); //FIXME gina descomentar para prod
	    	}
	    	
	    	
	    	//Se guarda una copia local del archivo en caso de que haya sido requerido
	   	if (saveCopyLocal) { 
				try {
					fLayout.storeLocalFile(fileName, content);
				} catch (IOException e) {
					logger.log(Level.WARNING, "No se pudo almacenar el archivo " + productName);
				}
			}
	    	
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Ocurrio un error al guardar el archivo de "+ productName, e);
		}
    	
    }

    public void saveMaintances(Date createDate){ 
    	
    	//find maintances will be delete before persist data, all records that not been sent to Directa will be deleted
    	List<Maintance> list = maintanceBean.findByDateAndStatus(createDate, DirectaStatus.HEB_PROCESADO.value());
    	
    	for(Maintance entity : list){
    		maintanceBean.delete(entity);
    	}
    	
    	
    	//persist maintances
    	for(Maintance entity : this.maintances){
    		entity.setCreationDate(createDate);
    		
        	try{
        		if(maintanceBean.findByMerchantNumberAndType(entity.getMerchantNumber(), MAINTANCENEW)==null){
        			maintanceBean.save(entity);
        		}
			
        	} catch (Exception e) {
				logger.log(Level.SEVERE, "("+entity.getCreationDate().toString()+"). Ocurrio un error al guardar el registro con el numero de comercio: "+ entity.getMerchantNumber(), e);
			}
    	}
    	
    }
    
    public void updateSentDate(String update){
    	
		Date sendDate = new Date();
		
    	//persist maintances
    	for(Maintance entity : this.maintances){
    		
    		if (update.equals("DIRECTA"))
    			entity.setSentDirectaDate(sendDate);
    		else if (update.equals("TRALIX"))
				entity.setSentTralixDate(sendDate);

    		maintanceBean.update(entity);
    	}
    }
    
	public void createXlsLayout(String productName, boolean saveLocalFile)throws IOException {
		List<List<String>> result = new ArrayList<List<String>>();
		this.setComercialPlanMap(new ContractUtil().getComercialPlanMap());
		for (Contract entity : this.getContracts()) {
			try{
				result.add(this.getContent(entity));
			}catch (Exception e){
				logger.log(Level.WARNING,"Error al procesar la informacion del contrato "+ entity.getReference());
			}
		}
		
		// Obtenemos el archivo XSL del listado de Contratos
		 byte[] contentLayout = new LayoutTempleteContract().createLayout(productName, result, true, getHeader());

		if (contentLayout!= null && contentLayout.length > 0){
			this.saveRemoteFile(productName, contentLayout, saveLocalFile);
		}
		
	}

	/**@author gmerla
	 * Get content for All products by date range excel report
	 * @param productName
	 * @param saveLocalFile
	 */
	public void createXlsLayoutAllProductsDTO(String productName, boolean saveLocalFile)throws IOException {
		if (employeeBean == null) {
			employeeBean = (EmployeeRemote) EjbInstanceManager.getEJB(EmployeeRemote.class);
		}
		List<List<String>> result = new ArrayList<List<String>>();
		this.setComercialPlanMap(new ContractUtil().getComercialPlanMap());
		
		Long numMaxRegistros=employeeBean.countRecords();
		int limite=1000;
		Map<String, String> mEmpleados=new HashMap<String, String>();
		int numBloques=(int) (numMaxRegistros/limite);
		numBloques=(numMaxRegistros % limite !=0)? numBloques+1:numBloques; // Si hay un residuo en la division, significa que debe haber un bloque mas grande.
		for(int pagina=0; pagina<=numBloques;pagina++){
		     int inicio=pagina*limite;
		     HashMap<String, String> empMap = employeeBean.findAllPaging(inicio,limite);
		     mEmpleados.putAll(empMap);
		}
		for (ContractAllProductsDTO entity : this.getContractsDTO()) {
				List<String> contentContractDTO = new ArrayList<String>();
				contentContractDTO.add(entity.getMes());
				contentContractDTO.add(entity.getProducto());
				contentContractDTO.add(entity.getFolio());
				contentContractDTO.add(entity.getVersion());
				contentContractDTO.add(entity.getNombreComercial());
				contentContractDTO.add(entity.getRazonSocial());
				contentContractDTO.add(entity.getEstatusActual());
				contentContractDTO.add(entity.getEstatusDesc());
				contentContractDTO.add(entity.getFechaUltimaMod());
				contentContractDTO.add(entity.getFechaCreacion());
				contentContractDTO.add(entity.getCr());
				contentContractDTO.add(entity.getNumEmpleado());
				contentContractDTO.add(entity.getNomEmpleado());
				contentContractDTO.add(entity.getSic());
				contentContractDTO.add(entity.getPlanEsquemaComercial());
				contentContractDTO.add(entity.getDetalleOtro());
				if("true".equalsIgnoreCase(entity.getPaqTpvNomina())){
					contentContractDTO.add("Si");	
				}else{
					contentContractDTO.add("No");
				}
				contentContractDTO.add(entity.getTipoPaquete());
				contentContractDTO.add(entity.getPlanSeleccionado());
				if("true".equalsIgnoreCase(entity.getPayworksClabe())){
					contentContractDTO.add("Si");	
				}else{
					contentContractDTO.add("No");
				}
				contentContractDTO.add(entity.getNumEmpColocacion());
				contentContractDTO.add(entity.getNomEmpColocacion());
				contentContractDTO.add(entity.getNumEmpEbanking());
				contentContractDTO.add(entity.getNomEmpEbanking());
				contentContractDTO.add(entity.getNumFormalizo());
				String idFormalizo = entity.getNumFormalizo();
				if(idFormalizo!=null && !idFormalizo.isEmpty()){
					idFormalizo = idFormalizo.replaceFirst("A", "");
				}
				String nombreFormalizo = mEmpleados.get(idFormalizo);
				contentContractDTO.add(nombreFormalizo);
				contentContractDTO.add(entity.getFechaFormalizacion());
				contentContractDTO.add(entity.getNumCoformalizo());
				String idCoformalizo = entity.getNumCoformalizo();
				if(idCoformalizo != null && !idCoformalizo.isEmpty()){
					idCoformalizo = idCoformalizo.replaceFirst("A", "");
				}
				String nombreCoformalizo = mEmpleados.get(idCoformalizo);
				contentContractDTO.add(nombreCoformalizo);
				contentContractDTO.add(entity.getFechaCoformalizo());
				result.add(contentContractDTO);
		}
		// Obtenemos el archivo XSL del listado de Contratos
		 byte[] contentLayout = new LayoutTempleteContract().createLayout(productName, result, true, getHeader());
		 if (contentLayout!= null && contentLayout.length > 0){
			this.saveRemoteFile(productName, contentLayout, saveLocalFile);
		}
	}
	
	/**@author gmerla
	 * Get content for Acquirer Product by date range excel report
	 * @param productName
	 * @param saveLocalFile
	 * @throws IOException
	 */
	public void createXlsLayoutAcquirerDTO(String productName, boolean saveLocalFile) throws IOException{
		List<List<String>> content = new ArrayList<List<String>>();
		this.setComercialPlanMap(new ContractUtil().getComercialPlanMap());
		
		for(ContractAcquirerDTO dto : contractAcquirerDTO){
			setStatusGarantiaLiquida(dto); 
			List<String> record = new ArrayList<String>();
			record.add(dto.getMes());
			record.add(dto.getProducto());
			record.add(dto.getFolio());
			record.add(dto.getVersion());
			record.add(dto.getNombreComercial());
			record.add(dto.getRazonSocial());
			record.add(dto.getEstatusActual());
			record.add(dto.getEstatusDescripcion());
			record.add(dto.getFechaUltModificacion());
			record.add(dto.getFechaCreacion());
			record.add(dto.getCr());
			record.add(dto.getNumeroEmpleado());
			record.add(dto.getNombreEmpleado());
			record.add(dto.getNumeroEmpColocacion());
			record.add(dto.getNombreEmpcolocacion());
			record.add(dto.getNumeroEmpEbanking());
			record.add(dto.getNombreEmpEbanking());
			record.add(dto.getAffPesos());
			record.add(dto.getAffDlls());
			
			record.add(dto.getPlanEsquemaComercial());
			record.add(dto.getDetalleOtro());
			record.add(dto.getComentarioOperaciones());
			record.add(dto.getFechaInstalacion());
			record.add(dto.getEmail());
			record.add(dto.getLada());
			record.add(dto.getTelefono());
			record.add(dto.getExtension());
			
			//Nuevo Garantia liquida
			record.add(dto.getGarantiaLiquida());
			record.add(dto.getVenEstimadasMensuales());
			record.add(dto.getMontoEstimadoTransaccion());
			record.add(dto.getDatoSucursal());
			record.add(dto.getBuroInterno());
			record.add(dto.getDivisa());
			record.add(dto.getMontoIncial());
			record.add(dto.getMontoPromDiario());
			record.add(dto.getPorcentajeGarantiaLiquida());
			record.add(dto.getMontoGarantiaLiquida());
			record.add(dto.getPorcentajeIniGarantiaLiquida());
			record.add(dto.getMontoIniGarantiaLiquida());
			record.add(dto.getPorcentajeRestGarantialiquida());
			record.add(dto.getMontoRestGarantiaLiquida());
			record.add(dto.getPorcentajeDiaGarantiaLiquida());
			record.add(dto.getPromMontoDiarioGarantiaLiquida());
			record.add(dto.getDiasAproxGarantiaLiquida());
			record.add(dto.getGlOriginal());
			record.add(dto.getExcepcionGarantiaLiquida());
			record.add(dto.getComentariosMotivoDisminucion());
			
			//F-92512 Esquemas 2019
			record.add(dto.getClientCategorycode());
			
			content.add(record);
		}
		// Obtenemos el archivo XSL del listado de Contratos
		 byte[] contentLayout = new LayoutTempleteContract().createLayout(productName, content, true, getHeader());
		 if (contentLayout!= null && contentLayout.length > 0){
			this.saveRemoteFile(productName, contentLayout, saveLocalFile);
		}
	}
	
	public void createXlsLayoutSipDTO(String productName, boolean saveLocal) throws IOException{
		List<List<String>> contenido = new ArrayList<List<String>>();
		this.setComercialPlanMap(new ContractUtil().getComercialPlanMap());
		for(ContractSipDTO dto : contractSipDTO){
			List<String> linea = new ArrayList<String>();
			linea.add(dto.getMes());
			linea.add(dto.getProducto());
			linea.add(dto.getFolio());
			linea.add(dto.getVersion());
			linea.add(dto.getNombreComercial());
			linea.add(dto.getRazonSocial());
			linea.add(dto.getEstatusActual());
			linea.add(dto.getEstatusDescripcion());
			linea.add(dto.getFechaUltimaMod());
			linea.add(dto.getFechaCreacion());
			linea.add(dto.getCr());
			linea.add(dto.getNumeroEmp());
			linea.add(dto.getNombreEmp());
			linea.add(dto.getAffPesos());
			linea.add(dto.getComentarioOperaciones());
			linea.add(dto.getFechaInstalacion());
			linea.add(dto.getFechaCoFormalizacion());
			linea.add(dto.getFechaAceptado());
			
			contenido.add(linea);
		}
		// Obtenemos el archivo XSL del listado de Contratos
		 byte[] contentLayout = new LayoutTempleteContract().createLayout(productName, contenido, true, getHeader());
		
		 if (contentLayout!= null && contentLayout.length > 0){
			this.saveRemoteFile(productName, contentLayout, saveLocal);
		}
	}
	
	/**@author vicman
	 * Get content for Nomina by date range excel report
	 */
	public void createXlsLayoutNominaDTO(String productName, boolean saveLocalFile) throws IOException{
		List<List<String>> content = new ArrayList<List<String>>();
		this.setComercialPlanMap(new ContractUtil().getComercialPlanMap());
		
		for(ContractNominaDTO dto : contractNominaDTO){
			List<String> record = new ArrayList<String>();
			record.add(dto.getMes());
			record.add(dto.getFolio());
			record.add(dto.getRazonSocial());
			record.add(dto.getVersion());
			record.add(dto.getStatusId());
			record.add(dto.getStatusDescripcion());
			record.add(dto.getFechaCreacion());
			record.add(dto.getFechaModificacion());
			record.add(dto.getEmisora());
			record.add(dto.getCr());
			record.add(dto.getSucursal());
			record.add(dto.getNoEmpleado());
			record.add(dto.getRepLegal());
			record.add(dto.getNumColoco());
			record.add(dto.getColoco());
			record.add(dto.getNumEbanking());
			record.add(dto.getEbanking());
			record.add(dto.getEmail());
			record.add(dto.getAreaCode());
			record.add(dto.getPhone());
			record.add(dto.getPhoneExt());
			record.add(dto.getDireccion());
			record.add(dto.getState());
			record.add(dto.getZipCode());
			
			content.add(record);
		}
		// Obtenemos el archivo XSL del listado de Contratos
		 byte[] contentLayout = new LayoutTempleteContract().createLayout(productName, content, true, getHeader());
		
		 if (contentLayout!= null && contentLayout.length > 0){
			this.saveRemoteFile(productName, contentLayout, saveLocalFile);
		}
	}
	
	/**
	 * @return the comercialPlanMap
	 */
	public Map<Integer, String> getComercialPlanMap() {
		return comercialPlanMap;
	}
	/**
	 * @param comercialPlanMap the comercialPlanMap to set
	 */
	public void setComercialPlanMap(Map<Integer, String> comercialPlanMap) {
		this.comercialPlanMap = comercialPlanMap;
	}
	
	public String getComercialPlanFromMap(String key){
		int valueKey = Integer.parseInt(key); 
		return this.comercialPlanMap.get(valueKey);
	}
	
	public void setStatusGarantiaLiquida(ContractAcquirerDTO dto){
		if(dto.getGarantiaLiquida() != null){
			if(dto.getGarantiaLiquida().equalsIgnoreCase("true")){
				dto.setGarantiaLiquida("Si");
			}else{
				dto.setGarantiaLiquida("No");
			} 
		}
		if(dto.getMontoIncial() != null){
			if(dto.getMontoIncial().equalsIgnoreCase("true")){
				dto.setMontoIncial("Si");
			}
			else{
				dto.setMontoIncial("No");
			} 
		}
	}
}
