package com.banorte.contract.layout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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

import com.banorte.contract.business.AttributeRemote;
import com.banorte.contract.business.ContractRemote;
import com.banorte.contract.business.StatusContractRemote;
import com.banorte.contract.model.Affiliation;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.ContractStatusHistory;
import com.banorte.contract.model.StatusContract;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.FTPProperties;
import com.banorte.contract.util.FileLayout;
import com.banorte.contract.util.Formatter;
import com.banorte.contract.web.EjbInstanceManager;

/**
 * 
 * @author MRIOST
 */
public class LayoutOperationsInstall {

	protected ContractRemote contractBean;
	protected StatusContractRemote statusBean;
	private FTPProperties fprops = new FTPProperties();
	private List<Contract> contractList = new ArrayList();
	private AttributeRemote attributeBean;

	public LayoutOperationsInstall() {
	}

	public void setContractBean(ContractRemote contractBean) {
		this.contractBean = contractBean;
	}

	public void setStatusBean(StatusContractRemote statusBean) {
		this.statusBean = statusBean;
	}
	
	public void setAttributeBean(AttributeRemote attributeBean) {
		this.attributeBean = attributeBean;
	}	

	public boolean processRemote() throws IOException {
		boolean flag = false;
		FileLayout fLayout = new FileLayout();
		Map<String, InputStream> map = fLayout.getRemoteInstallFiles(fprops);

		if (map.size() > 0) {
			Set<Map.Entry<String, InputStream>> mapValues = map.entrySet();
			Iterator<Map.Entry<String, InputStream>> it = mapValues.iterator();

			List<Answer> records = new ArrayList<Answer>();

			while (it.hasNext()) {
				Map.Entry<String, InputStream> entry = it.next();
				String fileName = entry.getKey();
				InputStream contentBytes = entry.getValue();
				records.addAll(parseLayout(contentBytes));

				fLayout.deleteRemoteInstallFiles(fileName, fprops);
			}

			this.verifyAndUpdate(records);

			flag = true;
		}

		return flag;
	}

	private void verifyAndUpdate(List<Answer> list) { 
		String installDate;
		boolean isNewStatus = false;
		Integer idStatus 	= ApplicationConstants.VALUE_0;
		
		for (Answer answer : list) {
			boolean att417 = false;
			boolean att418 = false;
			boolean att419 = false;
			boolean att420 = false;
			contractBean = (ContractRemote) EjbInstanceManager.getEJB(ContractRemote.class);
			idStatus 	= Integer.valueOf( answer.getIdStatus() );
			
			// Revisamos si existe contrato para la referencia indicada
			//Contract contract = this.contractBean.findByRefLastVersion(answer.getReference());
			Contract contract = this.contractBean.findByLastVersionAndRef(answer.getReference());
			if (contract != null) {
				Collection<ContractAttribute> atts = contract.getContractAttributeCollection();
				Collection<ContractStatusHistory> colHist = contract.getContractStatusHistoryCollection();
				Collection<Affiliation> affiliation = contract.getAffiliationCollection(); // affiliation
				ContractAttribute cAtt = new ContractAttribute();
				Formatter formatter = new Formatter();					
				
				Affiliation affi = null;
				if (affiliation.size() > 0) {
					affi = affiliation.iterator().next();
				} else {
					affi = new Affiliation();
				}
				// SI LA AFILIACION (Pesos o Dlls) DEL ARCHIVO CONCUERDA CON LA AFILIACION DEL CONTRATO CAPTURADO CONTINUA
				if ((answer.getInstallCurrency().equals("MXP") && answer.getInstallAffiliation().equals(affi.getNumaffilmn()))
						|| (answer.getInstallCurrency().equals("DLLS") && answer.getInstallAffiliation().equals(affi.getNumaffildlls()))) {
					for (ContractAttribute att : atts) {
						if (att.getAttribute().getAttributeid() == 297) { // COMENTARIOS DE  OPERACIONES
							if (answer.getComments().length() < 250)
								att.setValue(answer.getComments());
							else
								att.setValue(answer.getComments().substring(0, 249));
						} else if (att.getAttribute().getAttributeid() == 417) { // FECHA DE INSTALACION
							att417 = true;
							Calendar calCurrentInstallDate = null;
							Calendar calNewInstallDate = Calendar.getInstance();
							installDate = att.getValue() != null ? att.getValue() : "";
							if(installDate.length()>10){
								calCurrentInstallDate = !installDate.equals("") ? formatter.formatLongDate(installDate): null;
							}else{
								calCurrentInstallDate = !installDate.equals("") ? formatter.formatDate(installDate): null;
							}
							if(answer.getInstallDate()!=null){
								calNewInstallDate.setTime(answer.getInstallDate());
							}else{
								calNewInstallDate = null;
							}
							if (calCurrentInstallDate == null || calCurrentInstallDate.before(calNewInstallDate)) { // SI LA FECHA ALMACENADA ES NULA O ES ANTERIOR A LA QUE VIENE EN EL ARCHIVO SI ES VALIDO PARA ALMACENARSE
								if(calNewInstallDate!=null)
									att.setValue(formatter.formatLongDateToString(calNewInstallDate.getTime()));
								else
									att.setValue("");
								isNewStatus = true;
							}
						} else if (att.getAttribute().getAttributeid() == 418) {
							att418 = true;
							att.setValue(answer.getInstallSupplier());
						} else if (att.getAttribute().getAttributeid() == 419 && answer.getInstallCurrency().equals("MXP") && 
							    (idStatus.equals( ApplicationConstants.IDEXITO ) ||idStatus.equals( ApplicationConstants.IDPAYWORKS) )) {
							att419 = true;
							att.setValue(answer.getInstallAffiliation());
						} else if (att.getAttribute().getAttributeid() == 420 && answer.getInstallCurrency().equals("DLLS") &&
							    (idStatus.equals(ApplicationConstants.IDEXITO) || idStatus.equals( ApplicationConstants.IDPAYWORKS) )) {
							att420 = true;
							att.setValue(answer.getInstallAffiliation());
						}

					}
					if(!att417){// FECHA INSTALACION
						cAtt = new ContractAttribute();
						String instDate = answer.getInstallDate()!=null?formatter.formatLongDateToString(answer.getInstallDate()):null;
						cAtt.setValue(instDate);
						cAtt.setAttribute(attributeBean.findById(417));
						atts.add(cAtt);
						isNewStatus = true;
					}
					if(!att418){// PROVEEDOR QUE INSTALO
						cAtt = new ContractAttribute();
						cAtt.setValue(answer.getInstallSupplier());
						cAtt.setAttribute(attributeBean.findById(418));
						atts.add(cAtt);
					}
					if(!att419 && answer.getInstallCurrency().equals("MXP") && 
					    ( idStatus.equals( ApplicationConstants.IDEXITO ) || idStatus.equals(ApplicationConstants.IDPAYWORKS) ) ){ // AFILIACION PESOS INSTALADA
						cAtt = new ContractAttribute();
						cAtt.setValue(answer.getInstallAffiliation());
						cAtt.setAttribute(attributeBean.findById(419));
						atts.add(cAtt);
					}
					if(!att420 && answer.getInstallCurrency().equals("DLLS") && 
						( idStatus.equals( ApplicationConstants.IDEXITO ) || idStatus.equals(ApplicationConstants.IDPAYWORKS) )){ // AFILIAICON DLLS INSTALADA
						cAtt = new ContractAttribute();
						cAtt.setValue(answer.getInstallAffiliation());
						cAtt.setAttribute(attributeBean.findById(420));
						atts.add(cAtt);
					}
						
					if(affi.getCurrency().equals(ApplicationConstants.CURRENCYBOTH)) {
						if( idStatus.equals( ApplicationConstants.IDEXITO) ){
							if(answer.getInstallCurrency().equals("MXP")){
								if (contract.getStatus().getStatusid()!=ApplicationConstants.IDEXITOPARCIALDLLS){// SI EL ESTATUS ACTUAL ES EXITO PARCIAL DLLS SE CIERRA CON EXITO (tal como dice en el archivo) SINO, SE QUEDA COMO EXITO PARCIAL PESOS
									answer.setIdStatus(ApplicationConstants.IDEXITOPARCIALMXP.toString());
								}
							}else if(answer.getInstallCurrency().equals("DLLS")){
								if (contract.getStatus().getStatusid()!=ApplicationConstants.IDEXITOPARCIALMXP){// SI EL ESTATUS ACTUAL ES EXITO PARCIAL MXP SE CIERRA CON EXITO (tal como dice en el archivo) SINO, SE QUEDA COMO EXITO PARCIAL DLLS
									answer.setIdStatus(ApplicationConstants.IDEXITOPARCIALDLLS.toString());
								}
							}
						}else if( idStatus.equals( ApplicationConstants.IDPAYWORKS ) ){
							if(answer.getInstallCurrency().equals("MXP")){
								if (contract.getStatus().getStatusid()!=ApplicationConstants.IDPAYWORKSPARCIALDLLS){// SI EL ESTATUS ACTUAL ES PAYWORKS PARCIAL DLLS SE CIERRA CON EXITO (tal como dice en el archivo) SINO, SE QUEDA COMO EXITO PARCIAL PESOS
									answer.setIdStatus(ApplicationConstants.IDPAYWORKSPARCIALMXP.toString());
								}
							}else if(answer.getInstallCurrency().equals("DLLS")){
								if (contract.getStatus().getStatusid()!=ApplicationConstants.IDPAYWORKSPARCIALMXP){// SI EL ESTATUS ACTUAL ES PAYWORKS PARCIAL MXP SE CIERRA CON EXITO (tal como dice en el archivo) SINO, SE QUEDA COMO EXITO PARCIAL DLLS
									answer.setIdStatus(ApplicationConstants.IDPAYWORKSPARCIALDLLS.toString());
								}
							}
						}
								
					}
					
					if (!contract.getStatus().getStatusid().equals(answer.getIdStatus()) && isNewStatus) {
						contract.setContractAttributeCollection(atts);
						StatusContract status = statusBean.findById( idStatus );

						if (status != null) {
							contract.setStatus(status);
							ContractStatusHistory statusHistory = new ContractStatusHistory();
							statusHistory.setContract(contract);
							statusHistory.setStatusContract(status);
							statusHistory.setOffempnum("appsstb");
							String respuesta = answer.getComments()!=null?answer.getComments():"";
							statusHistory.setCommentary("APPSSTB contrato contestado por operaciones."+": "+respuesta);
							statusHistory.setModifydate(new Date());

							contract.add(statusHistory);
							contractBean.update(contract);
						}
					}
				}
			}
		}
	}

	private List<Answer> parseLayout(InputStream is) throws IOException {
		List<Answer> result = new ArrayList();
		POIFSFileSystem fs = new POIFSFileSystem(is);
		Workbook wb = new HSSFWorkbook(fs);
		Sheet s = wb.getSheetAt(0);
		int rows = s.getPhysicalNumberOfRows();
		for (int i = 0; i < rows; i++) {
			Row row = s.getRow(i);
			int cells = (row != null) ? row.getPhysicalNumberOfCells() : 0;
			if (cells > 0) {
				try {
					Answer a = new Answer();
					// Unicamente cargar los folios de 10 caracteres que tienen afiliacion y que el tipo de instalacion es 1 (Alta) y el estatus no es nulo
					// Referencia (2), Afiliacion(3), Divisa (7), Tipo(9), Proveedor(12), Fecha Attn(13), Estatus(14), Comentarios(15)
					if (getCellValue(row, 2) != null && getCellValue(row, 2).trim().length() == 10 && getCellValue(row, 3) != null && getCellValue(row, 3).trim().length() > 0 &&
							getCellValue(row, 9) != null && !getCellValue(row, 9).trim().equals("") && Integer.parseInt(getCellValue(row, 9).trim()) == 1 &&
							getCellValue(row, 14)!=null && !getCellValue(row, 14).trim().equals("") ) {
						a.setReference(getCellValue(row, 2));
						a.setInstallAffiliation(getCellValue(row, 3));
						a.setInstallCurrency(getCellValue(row, 7) != null ? getCellValue(row, 7): "");
						a.setInstallType(getCellValue(row, 9) != null && !getCellValue(row, 9).trim().equals("")? Integer.parseInt(getCellValue(row, 9)) : null);
						a.setInstallSupplier(getCellValue(row, 12) != null ? getCellValue(row, 12): "");
						if(getCellDateValue(row, 13) != null){
							a.setInstallDate((Date)getCellDateValue(row, 13));
						}else{
							a.setInstallDate(null);
						}
						a.setInstallStatus(getCellValue(row, 14) != null ? getCellValue(row, 14).toUpperCase(): "");
						if (a.getInstallStatus().equals(ApplicationConstants.EXITO)) {
							a.setIdStatus(ApplicationConstants.IDEXITO.toString());
						} else if (a.getInstallStatus().equals(ApplicationConstants.PAYWORKS)) {
							a.setIdStatus(ApplicationConstants.IDPAYWORKS.toString());
						} else if (a.getInstallStatus().equals(ApplicationConstants.RECHAZO)) {
							a.setIdStatus(ApplicationConstants.IDRECHAZO.toString());
						} else if (a.getInstallStatus().equals(ApplicationConstants.RUTA)) {
							a.setIdStatus(ApplicationConstants.IDRUTA.toString());
						} else if (a.getInstallStatus().equals(ApplicationConstants.CANCELADO)) {
							a.setIdStatus(ApplicationConstants.IDCANCELADO.toString());
						}
						a.setComments(getCellValue(row, 15) != null ? getCellValue(row, 15): "");
						result.add(a);
					}
				} catch (NumberFormatException e) {
					System.err.println("Valor : " + getCellValue(row, 1) + " + " + i + " \n" + e);
					e.printStackTrace();
				} catch (NullPointerException e) {
					System.err.println("Registro " + i + " nulo: " + e);
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	private Date getCellDateValue(Row row, int position){
		Date result = null ;
		try {
			if(row.getCell(position).getCellType()!=Cell.CELL_TYPE_BLANK && row.getCell(position).getCellType()!=Cell.CELL_TYPE_STRING){
				result =  row.getCell(position).getDateCellValue();
			}
		} catch (NullPointerException e) {}
		return result;
	}
	
	private String getCellValue(Row row, int position) {
		String result = "";
		try {
			if (row.getCell(position).getCellType() == Cell.CELL_TYPE_STRING) {
				result = row.getCell(position).getRichStringCellValue()
						.getString();
			} else if (row.getCell(position).getCellType() == Cell.CELL_TYPE_NUMERIC) {
				double doubleValue = row.getCell(position)
						.getNumericCellValue();
				if ((doubleValue % 1) > 1) {
					result = Double.toString(doubleValue);
				} else {
					result = Integer.toString((int) doubleValue);
				}
			} else if (row.getCell(position).getCellType() == Cell.CELL_TYPE_FORMULA) {
				double doubleValue = row.getCell(position)
						.getNumericCellValue();
				if ((doubleValue % 1) > 1) {
					result = Double.toString(doubleValue);
				} else {
					result = Integer.toString((int) doubleValue);
				}
			} 
		} catch (NullPointerException e) {} // Si la celda esta nula

		return result;
	}
}
