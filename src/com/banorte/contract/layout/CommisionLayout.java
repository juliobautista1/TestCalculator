/**
 * 
 */
package com.banorte.contract.layout;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.banorte.contract.business.CategoriesRemote;
import com.banorte.contract.business.PlanRemote;
import com.banorte.contract.model.Categories;
import com.banorte.contract.model.ContractMessageErrors;
import com.banorte.contract.model.CounterRecord;
import com.banorte.contract.model.FailReference;
import com.banorte.contract.model.LoadFile;
import com.banorte.contract.model.Plan;
import com.banorte.contract.model.RecipentMail;
import com.banorte.contract.model.WrapContractGenerate;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.FTPProperties;
import com.banorte.contract.util.FileLayout;
import com.banorte.contract.util.MailUtil;
import com.banorte.contract.web.EjbInstanceManager;

/**
 * @author omar
 *
 */
public class CommisionLayout {
	
	private FTPProperties fprops 					= new FTPProperties();
	private PlanRemote planBean;
	private CategoriesRemote categoriesBean;
	
	List<Plan> listaPlanes = new ArrayList<Plan>();
	List<Categories> listaGiros = new ArrayList<Categories>();
		
	public CommisionLayout(){
		if(planBean==null){
			planBean= (PlanRemote)EjbInstanceManager.getEJB((PlanRemote.class));
		}
		if (categoriesBean == null) {
			categoriesBean = (CategoriesRemote) EjbInstanceManager.getEJB(CategoriesRemote.class);
		}
	}
	
	
public boolean processRemoteAnswers() throws IOException {
		
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
		

		mapResult = processRemoteAnswersFiles();
		
		//records = processRemote(RESPONSE_TYPE_ANSWER)
		
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
					//counterRecord = this.verifyAndUpdate(records,listFailReference);
					flag =true;
				}
			  }		
			}		
       
		return flag;
	}


		@SuppressWarnings("unchecked")
		private <T> Map<LoadFile,List<T>> processRemoteAnswersFiles() throws IOException{
			
			FileLayout fLayout 										= new FileLayout();
			Map<String, InputStream> map 							= new HashMap<String, InputStream>();
			Map<LoadFile,List<T>> mapFileInfo 						= new HashMap<LoadFile,List<T>>();
			List<T> records 										= null;
			
			map = fLayout.getRemoteFileCommision(fprops);		
		
			if (map.size() > 0) {
				Set<Map.Entry<String, InputStream>> mapValues 		= map.entrySet();
				Iterator<Map.Entry<String, InputStream>> it 		= mapValues.iterator();
		
				while (it.hasNext()) {
					records 								= new ArrayList<T>();
					Map.Entry<String, InputStream> entry 	= it.next();
					String fileName 						= entry.getKey();
					InputStream contentBytes 				= entry.getValue();
		
					records.addAll((List<T>)parseLayout(contentBytes));
					fLayout.deleteRemoteFilesCommision(fileName, fprops);		//delete file after get data	
					
				}
			}
		
			return mapFileInfo;
			
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
						// Referencia, Código de Estatus, Comentarios, Número de
						// Empresa, No. Afiliación Pesos, No. Afiliación Dólares

						if (getCellValue(row, 0).trim().length() > 0
								&& getCellValue(row, 1).trim().length() > 0
								&& getCellValue(row, 1) != null) {
							
							List<String> listUserTokens 	= new ArrayList<String>();
							String reference 				= getCellValue(row, 0);
							String idStatus 				= getCellValue(row, 1);
							
							a.setReference(reference);
							a.setIdStatus(idStatus);
							
							a.setComments(getCellValue(row, 2));
							String empresa = getCellValue(row, 3);
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
		
		private String getCellValue(Row row, int position) {
			String result = "";
			try {
				if (row.getCell(position).getCellType() == Cell.CELL_TYPE_STRING) {
					result = row.getCell(position).getRichStringCellValue().getString();
				} else if (row.getCell(position).getCellType() == Cell.CELL_TYPE_NUMERIC) {
					double doubleValue = row.getCell(position).getNumericCellValue();
					if ((doubleValue % 1)!=0) {
						result = Double.toString(doubleValue);
					} else {
						result = Integer.toString((int) doubleValue);
					}
				} else if (row.getCell(position).getCellType() == Cell.CELL_TYPE_FORMULA) {
					double doubleValue = row.getCell(position).getNumericCellValue();
					if ((doubleValue % 1) !=0) {
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
		
		
		
}
