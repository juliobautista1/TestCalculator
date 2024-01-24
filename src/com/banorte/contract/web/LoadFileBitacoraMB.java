/**
 * 
 */
package com.banorte.contract.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.event.ActionEvent;

import com.banorte.contract.business.LoadFileRemote;
import com.banorte.contract.layout.BemLayout;
import com.banorte.contract.layout.LayoutOperationsTemplate;
import com.banorte.contract.layout.LayoutTempleteContract;
import com.banorte.contract.layout.LoadFileDailyLayout;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractStatusHistory;
import com.banorte.contract.model.LoadFile;
import com.banorte.contract.model.StatusContract;
import com.banorte.contract.schedule.ScheduleServlet;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.EncryptBd;
import com.banorte.contract.util.FTPProperties;
import com.banorte.contract.util.FileLayout;
import com.banorte.contract.util.Formatter;
import com.banorte.contract.util.ResultMonthLoadFile;

/**
 * @author omar
 *
 */
public class LoadFileBitacoraMB {
	
	private Logger logger = Logger.getLogger(LoadFileBitacoraMB.class.getName());
	
	private LoadFileRemote loadFileRemote;
	
	private String frequency;
	
	private String startDate;
	
	private String endDate;
	
	private String product;
	
	private String fileType;
	
	private ArrayList<LoadFile> bitacoraList;
	
	private ArrayList<ResultMonthLoadFile> bitacoraMonthList;
	
	private String monthSelected;
	
	
	public LoadFileBitacoraMB() {
		super();
		if (loadFileRemote == null) {
			loadFileRemote = (LoadFileRemote) EjbInstanceManager.getEJB(LoadFileRemote.class);
		}
	}
	
	
	public String search(ActionEvent actionEvent){
		
		String result 				= ApplicationConstants.EMPTY_STRING;
		Calendar startReportDate 	= null;
		Calendar endReportDate 		= null;
		Integer productTypeNumber 	= Integer.valueOf(getProduct()) ;
		Integer month 				= ApplicationConstants.VALUE_0;
		
		
		if(isFrecuencyDaily()){
			startReportDate 	= new Formatter().formatDate(this.startDate);
			endReportDate 		= new Formatter().formatDate(this.endDate);
			
		}else if (isFrecuencyMonthly()) {
			month 				= Integer.valueOf(monthSelected);
			startReportDate 	= firstDayMonthCurrentYear(month);
			endReportDate 		= lastDayMonthCurrentYear(month);
		}
		
		bitacoraList = (ArrayList<LoadFile>) loadFileRemote.findLoadFilesByFecha(startReportDate, endReportDate, productTypeNumber, getFileType());
		
		if(this.frequency.equals(ApplicationConstants.FRECUENCY_MES)){
			
			bitacoraMonthList = (ArrayList<ResultMonthLoadFile>) processInfoLoadFile(bitacoraList);
			bitacoraList = null;
			
		}
		
		result = ApplicationConstants.OUTCOME_RESULT;
		
		return result ;
	}
	
	
	public String generateBitacoraReport(ActionEvent actionEvent){
    	
		String result 				= ApplicationConstants.EMPTY_STRING;
        
        if(isFrecuencyDaily()){
        	if(bitacoraList.size()> ApplicationConstants.VALUE_0){
        		try{
        			createLayout(); 
    	           
            	}catch (Exception ex) {
    				Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
    				System.out.println("ERROR:" + ex);
    			}
        		
        	}
					
		}else if (isFrecuencyMonthly()) {
			if(bitacoraMonthList.size() > ApplicationConstants.VALUE_0){
				try{
        			createLayout(); 
    	           
            	}catch (Exception ex) {
    				Logger.getLogger(ScheduleServlet.class.getName()).log(Level.SEVERE, null, ex);
    				System.out.println("ERROR:" + ex);
    			}
				
			}
		}
        
        result = ApplicationConstants.OUTCOME_RESULT;
		
		return result ;
    	
    }
	
	/**
	 * Devuelve un dia antes del primer dia del mes
	 * @param month
	 * @return
	 */
	public Calendar firstDayMonthCurrentYear(Integer month){
	
		Calendar dateMonth 	= GregorianCalendar.getInstance();
		
		dateMonth.set(dateMonth.get(Calendar.YEAR), month, dateMonth.getActualMinimum(Calendar.DAY_OF_MONTH)); //Año , Mes , Dia
		dateMonth.add(Calendar.DATE, -ApplicationConstants.VALUE_1);
		return dateMonth;
	}
	
	/**
	 * devuelve un dia despues del primer dia del mes.
	 * @param month
	 * @return
	 */
	public Calendar lastDayMonthCurrentYear(Integer month){
		
		Calendar dateMonth 	= GregorianCalendar.getInstance();
		
		dateMonth.set(dateMonth.get(Calendar.YEAR), month, dateMonth.getActualMaximum(Calendar.DAY_OF_MONTH)); //Año , Mes , Dia
		dateMonth.add(Calendar.DATE, ApplicationConstants.VALUE_1);

		return dateMonth;
	}
		
	

	
	public List<ResultMonthLoadFile> processInfoLoadFile(ArrayList<LoadFile> listLoadFile){
		
		List<ResultMonthLoadFile> resultaDayList	= new ArrayList<ResultMonthLoadFile>();
		ResultMonthLoadFile resultMonthLoadFile 	= new ResultMonthLoadFile();
		
		if(!listLoadFile.isEmpty()){
			resultMonthLoadFile.setFileType(this.fileType);
			resultMonthLoadFile.setProductType(this.getProductTypeStr(this.product));
			
			
			for(LoadFile temp:listLoadFile){
				resultMonthLoadFile.addSuccess(temp.getNumberSuccess());
				resultMonthLoadFile.addFail(temp.getNumberFail());
			}
			
			resultaDayList.add(resultMonthLoadFile);
		}
		
		return resultaDayList;
		
	}
	
	
	private String getProductTypeStr(String productTypeStr){
		
		String result 	= ApplicationConstants.EMPTY_STRING;
		Integer fileType = Integer.valueOf(productTypeStr);
		
		if(fileType.equals( ApplicationConstants.PT_BEM)){
			result = ApplicationConstants.PT_BEM_STR;
		}else if (fileType.equals( ApplicationConstants.PT_NOMINA)) { 
			result = ApplicationConstants.PT_NOMINA_STR;
		}else if (fileType.equals( ApplicationConstants.PT_ADQ)) { 
			result = ApplicationConstants.PT_ADQ_STR;
		}else if (fileType.equals( ApplicationConstants.PT_MB)) { 
			result = ApplicationConstants.PT_MB_STR;
		}else if (fileType.equals( ApplicationConstants.PT_CD)) { 
			result = ApplicationConstants.PT_CD_STR;
		}
		
		return result;

	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createLayout() {
		
		logger.setLevel(Level.WARNING);
		
		String fileName 							= ApplicationConstants.EMPTY_STRING;
		Date creationDate 							= new Date();
		LoadFileDailyLayout loadFileDailyLayout 	= new LoadFileDailyLayout();
		byte[] content 								= null;
		
			try {
					// Iniciamos Generacion del Contenido en base al Contrato
					List<List<String>> result = new ArrayList();
					
					if(isFrecuencyDaily()){
						for (LoadFile loadFile : this.bitacoraList) {
							result.add(loadFileDailyLayout.getContent(loadFile));
						}
						
						content = loadFileDailyLayout.createLayoutByte(
								"BitacoraCarga", result, Boolean.TRUE, loadFileDailyLayout.getHeaderDaily());
						
					}else if (isFrecuencyMonthly()) {
						for (ResultMonthLoadFile resultMonthLoadFile : this.bitacoraMonthList) {
							result.add(loadFileDailyLayout.getContent(resultMonthLoadFile));
						}
						
						content = loadFileDailyLayout.createLayoutByte(
								"BitacoraCarga", result, Boolean.TRUE, loadFileDailyLayout.getHeaderMonthly());
					}
	
					
	
					// Obtenemos el archivo XSL del listado de Contratos
					
	
					FileLayout fLayout = new FileLayout();
					FTPProperties fprops = new FTPProperties();
					Formatter format = new Formatter();
					String date = format.formatDateToStringLayout(creationDate);
					
					//Como siguiente, enviamos el archivo al servidor de FTP
					try {
							fileName= "LayoutBitacoraCargaArchivos"+ date + ".xls";
							fLayout.storeRemoteReportFile(fileName, content, fprops);
							}catch (Exception e) {
								logger.log(Level.SEVERE,"Ocurrio un error al procesar el archivo de Bitacora de Carga de Archivo ", e);
							}					
				} catch (Exception e) {
					logger.log(Level.SEVERE,"Ocurrio un error al procesar el archivo de Bitacora de Carga de Archivo ", e);
				}
			
		
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the bitacoraList
	 */
	public ArrayList<LoadFile> getBitacoraList() {
		return bitacoraList;
	}

	/**
	 * @param bitacoraList the bitacoraList to set
	 */
	public void setBitacoraList(ArrayList<LoadFile> bitacoraList) {
		this.bitacoraList = bitacoraList;
	}


	/**
	 * @return the loadFileRemote
	 */
	public LoadFileRemote getLoadFileRemote() {
		return loadFileRemote;
	}


	/**
	 * @param loadFileRemote the loadFileRemote to set
	 */
	public void setLoadFileRemote(LoadFileRemote loadFileRemote) {
		this.loadFileRemote = loadFileRemote;
	}


	/**
	 * @return the bitacoraMonthList
	 */
	public ArrayList<ResultMonthLoadFile> getBitacoraMonthList() {
		return bitacoraMonthList;
	}


	/**
	 * @param bitacoraMonthList the bitacoraMonthList to set
	 */
	public void setBitacoraMonthList(
			ArrayList<ResultMonthLoadFile> bitacoraMonthList) {
		this.bitacoraMonthList = bitacoraMonthList;
	}


	/**
	 * @return the monthSelected
	 */
	public String getMonthSelected() {
		return monthSelected;
	}


	/**
	 * @param monthSelected the monthSelected to set
	 */
	public void setMonthSelected(String monthSelected) {
		this.monthSelected = monthSelected;
	}
	
	
	private Boolean isFrecuencyDaily(){
		Boolean response = Boolean.FALSE;
		
		if(this.frequency.equals(ApplicationConstants.FRECUENCY_DIARIO)){
			response = Boolean.TRUE;
		}
		
		return response;
	}
	
	private Boolean isFrecuencyMonthly(){
		Boolean response = Boolean.FALSE;
		
		if (this.frequency.equals(ApplicationConstants.FRECUENCY_MES)) {
			response = Boolean.TRUE;
		}
		
		return response;
	}
	
	

}
