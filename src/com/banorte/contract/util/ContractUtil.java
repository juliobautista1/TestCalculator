/**
 * 
 */
package com.banorte.contract.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.model.SelectItem;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.banorte.contract.business.EmployeeRemote;
import com.banorte.contract.layout.LayoutTempleteContract;
import com.banorte.contract.layout.handlers.NominaHandler;
import com.banorte.contract.layout.handlers.RowHandler;
import com.banorte.contract.model.Attribute;
import com.banorte.contract.model.AttributeOption;
import com.banorte.contract.model.Template;
import com.banorte.contract.util.ReportGenerator.LayoutGenerator;
import com.banorte.contract.web.AttributeMB;
import com.banorte.contract.web.EjbInstanceManager;

/**
 * @author omar
 *
 */
public class ContractUtil {
	
	private AttributeMB attributeMB;
	
	/**
	 * 
	 */
	public ContractUtil() {
		super();
		attributeMB = new AttributeMB();
	}

	
	/**
	 * "+" indica que se agrego para el ordenamiento de los planes cuando se agrego el plan 500
	 * @param toOIP
	 * @return
	 * Se comento por que ya no se usa y marcaba errores en el fortify
	 */
//	public SelectItem[] getComercialPlanArray(boolean toOIP){
//    	
//    	SelectItem[] comercialPlanArray = null;
//   		Attribute att = this.getAttributeMB().getByFieldname("affiliation_comercialplan");	
//    	SelectItem[] comercialOtherPlanArray = null;
//        
//        if (att != null) {
//            Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
//            Map<Integer, String> optionsMap = new HashMap<Integer, String>();
//            
//            for (AttributeOption attOpt : attOptCollection) {
//            	optionsMap.put(Integer.parseInt(attOpt.getValue()), attOpt.getDescription());
//            }            
//            
//            if (optionsMap != null) {
//            	if(toOIP){ //Eliminar las opciones de Rentabilidad y Otro
//            		comercialPlanArray = new SelectItem[optionsMap.size()-2];
//            	}else{
//            		comercialPlanArray = new SelectItem[optionsMap.size()];
//            		comercialOtherPlanArray = new SelectItem[ApplicationConstants.VALUE_2];
//            	}
//            	
//                Map<Integer, String> sortedOptionsMap = new TreeMap<Integer, String>(optionsMap);
//        
//                int i = 0;
//                int j = 0;
//                for (Map.Entry<Integer, String> option : sortedOptionsMap.entrySet()){
//                	
//                	if(!toOIP){
//                		if(option.getKey().equals(PlanType.RENTABILIDAD.value()) || option.getKey().equals(PlanType.OTRO.value()))
//                        {
//                			comercialOtherPlanArray[j]= new SelectItem(option.getKey().toString(), option.getValue());
//                			j++;
//                        }else{
//                        	comercialPlanArray[i] = new SelectItem(option.getKey().toString(), option.getValue());
//                        	i++;
//                        	}
//                	}else{
//                    	if(!option.getKey().equals(PlanType.RENTABILIDAD.value()) && !option.getKey().equals(PlanType.OTRO.value()))
//                        {
//                    		comercialPlanArray[i] = new SelectItem(option.getKey().toString(), option.getValue());
//                    		i++;
//                        }
//                	}
//                	
//                }   
//                
//                if(!toOIP){
//                	comercialPlanArray[ApplicationConstants.VALUE_4]=comercialOtherPlanArray[ApplicationConstants.VALUE_0];
//                	comercialPlanArray[ApplicationConstants.VALUE_5]=comercialOtherPlanArray[ApplicationConstants.VALUE_1];
//                	
//                }
//            }
//        }
//    	return comercialPlanArray;
//    }
	

	
	
	
	/**
	 * Develve un Array de SelecItems cuando el valor de los Atributos en su Valor y Descripcion son Cadenas 
	 * @param attributeFieldName
	 * @return
	 */
	public SelectItem[] getattributeOptionArray(String attributeFieldName){
    	
		SelectItem[] itemArray 	= null;
		int iterator 			= 0;
	    Attribute att 			= this.getAttributeMB().getByFieldname(attributeFieldName);
	    
	    if (att != null) {
	        Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
	        itemArray = new SelectItem[attOptCollection.size()];
	
	        
	        for (AttributeOption attOpt : attOptCollection) {
	        	itemArray[iterator] = new SelectItem(attOpt.getValue(), attOpt.getDescription());
	        	++iterator;
	        }            
  
	    }
		
		return itemArray;
    }
	
	
	public Map<Integer, String> getComercialPlanMap(){
		
		Map<Integer, String> optionsMap 	= new HashMap<Integer, String>();
	    Attribute att 						= this.getAttributeMB().getByFieldname("affiliation_comercialplan");
	        
	        if (att != null) {
	            Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
	            
	            for (AttributeOption attOpt : attOptCollection) {
	            	optionsMap.put(Integer.parseInt(attOpt.getValue()), attOpt.getDescription());
	            }   
	        }
	            
	    	return optionsMap;
	    }

	
	
	/**
	 * @return the attributeMB
	 */
		public AttributeMB getAttributeMB() {
			return attributeMB;
		}

	/**
	 * @param attributeMB the attributeMB to set
	 */
		public void setAttributeMB(AttributeMB attributeMB) {
			this.attributeMB = attributeMB;
		}
		
		
	public Double calculatePenaltyTPV(Double replaceAmountRateDouble){
		
		Double result = new Double(ApplicationConstants.ZERO_DOUBLE_STR);
		
		result = replaceAmountRateDouble/ ApplicationConstants.MULTIPLE_25;
		result = Math.ceil(result);
		result = result * ApplicationConstants.MULTIPLE_25;
		
		return result;
	}
	
	public Double calculateEquivalentTPV(Double replaceAmountRateDouble){
			
			Double result = new Double(ApplicationConstants.ZERO_DOUBLE_STR);
			
			result = replaceAmountRateDouble/ ApplicationConstants.MULTIPLE_25;
			result = Math.ceil(result);
			result = result * ApplicationConstants.MULTIPLE_10000;
			
			return result;
		}
	
	/**
	 * Get the available plan array based on profile (banorte/ixe)
	 * @author gmerla
	 * @param oip
	 * @param profile
	 * @return SelectItem array with the commercial plan available for the specific profile
	 */
	public SelectItem[] planArray(Boolean oip, String profile){
		Attribute attribute=null;
		SelectItem[] planComercial=null;
		
		//Se obtienen los planes en base al perfil
		if("ixe".equalsIgnoreCase(profile)){
			attribute = attributeMB.getByFieldname("ixeCommercialPlan");
		}else{
			attribute = attributeMB.getByFieldname("affiliation_comercialplan");
		}
		Collection<AttributeOption> attributeOption = attribute.getAttributeOptionCollection();
		
		//Se ordenan los planes por optionId
        List<AttributeOption> listaAtt=(List<AttributeOption>)attributeOption;
        Collections.sort(listaAtt, new AttributeOptionComparator());
        
        //Si es SIP se eliminan las ultimas 2 opciones (Estudio de rentabilidad y Otro)
        if(oip){
        	planComercial = new SelectItem[(attributeOption.size()+1)-2]; //(el +1 es para agregar el "seleccione...)
        	planComercial[0] = new SelectItem (0, "Seleccione...");
        	int i =1;
    		for(AttributeOption ao: attributeOption){
    			if(!ao.getDescription().equalsIgnoreCase("Estudio de Rentabilidad") && !ao.getDescription().equalsIgnoreCase("Otro")){
    				planComercial[i] = new SelectItem (ao.getValue(), ao.getDescription());
        			i++;	
    			}
    		}
        }else{
        	planComercial = new SelectItem[attributeOption.size()+1]; //(el +1 es para agregar el "seleccione...)
        	planComercial[0] = new SelectItem (0, "Seleccione...");
    		int i =1;
    		for(AttributeOption ao: attributeOption){
    			planComercial[i] = new SelectItem (ao.getValue(), ao.getDescription());
    			i++;
    		}
        }
		
		return planComercial;
	}
	
	
	/**
	 * Sort a collection of templates by specific order
	 * @param valoresConOrdenCorrecto the specific order (template's description)
	 * @param lista a list of templates to order
	 * @return the list sorted
	 */
	public Collection<Template> orderList(List<String> valoresConOrdenCorrecto,Collection<Template> listaTemplates){
		List<Template> nuevaLista=new ArrayList<Template>();
		List<Template> listaOrdenada = new ArrayList<Template>();
		if(listaTemplates!=null && !(listaTemplates.isEmpty())){
			//Se llena de valores nulos la lista de acuerdo al num de elementos de valores con orden correcto
			for(int i=0;i<valoresConOrdenCorrecto.size();i++){
				nuevaLista.add(null);
			}

			for(Template formato:listaTemplates){
				String valorComparar=formato.getDescription();
				int indiceCorrecto=valoresConOrdenCorrecto.indexOf(valorComparar);
				//Si se encontro el elemento en la lista de valores con orden correcto.
				if(indiceCorrecto>-1){
					nuevaLista.set(indiceCorrecto, formato);
				}else{
					System.out.println("No se encontro en la lista de valores ordenados, se agrega al final.Elemento:"+formato);
					nuevaLista.add(formato);
				}
			}
			for(Template pdf:nuevaLista){
				if(pdf!=null){
					listaOrdenada.add(pdf);
				}	
			}
			
		}
		return listaOrdenada;
	}
	
	/**
	 * Método para buscar las propiedades de una clase y su valor.
	 * @param bean - (Bean de donde se buscarán las variables declaradas)
	 * @return Mapa de Objetos
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 * @author gina
	 */
	public Map<String, Object> obtainObjectMap (Object bean, boolean inheritedAtts, List attributesToAdd) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		Map<String, Object> objectAsMap = new HashMap<String, Object>();
		BeanInfo info = Introspector.getBeanInfo(bean.getClass());
		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			Method reader = pd.getReadMethod();
//			Class actualClass=reader.getDeclaringClass();
//			boolean considerInherited=(inheritedAtts || (actualClass.equals(bean.getClass())));
			Logger log=Logger.getLogger(ContractUtil.class.getCanonicalName());
			if (reader != null){
				if(attributesToAdd.contains(reader.getName())){
					try{
						objectAsMap.put(pd.getName(), reader.invoke(bean));	
					}catch(Exception e){
						log.log(Level.WARNING,"Propiedad:"+(pd.getName())+"-Metodo get no se logro invocar!");
					}	
				}
			}
		}
		return objectAsMap;
	}
	
	public void completarReporteNomina() throws IOException{
		EmployeeRemote employeeBean=null;
		if (employeeBean == null) {
			employeeBean = (EmployeeRemote) EjbInstanceManager.getEJB(EmployeeRemote.class);
		}
		EncryptBd encrypt=new EncryptBd();
		FTPProperties fprops=new FTPProperties();
		FileLayout fLayout=new FileLayout();
		Map<String, InputStream> map=new HashMap<String, InputStream>();
		map = fLayout.getRemoteFiles(fprops);
		InputStream contenido=map.get("ReporteNomina.xls");
		
		List<List<String>>registros = new LinkedList<List<String>>();
		POIFSFileSystem fs=new POIFSFileSystem(contenido);
		Workbook wb=new HSSFWorkbook(fs);
		Sheet s=wb.getSheetAt(0);
		int rows=s.getPhysicalNumberOfRows();
		
		Map<String, String> mEmpleados=new HashMap<String, String>();
		HashMap<String, String> empMap = employeeBean.findAll();
		     mEmpleados.putAll(empMap);
		     System.out.println("mapa empleados: "+empMap.size());
		
		for (int i = 0; i < rows; i++) {
			List<String> result=new ArrayList<String>();
			Row row = s.getRow(i);
			int cells = (row != null) ? row.getPhysicalNumberOfCells() : 0;
			if (cells > 0) {
				/**
				 * A - folio - 0
				 * B - cuenta - 1
				 * C - # formalizo - 2
				 * D - # coformalizo - 3
				 */
				String folio = getCellValue(row, 0);
				String cuenta = getCellValue(row, 1); //numero de cuenta encriptada
				String cuentaDesencriptada = encrypt.decrypt(cuenta); // numero de cuenta desencriptada
				String idFormalizo = getCellValue(row, 2);//numero formalizo
				if(idFormalizo!=null && !idFormalizo.isEmpty()){
					idFormalizo = idFormalizo.replaceFirst("A", "");
				}
				String nombreFormalizo = mEmpleados.get(idFormalizo);//nombre formalizo
				String idCoFormalizo = getCellValue(row, 3);//numero coFormalizo
				if(idCoFormalizo!=null && !idCoFormalizo.isEmpty()){
					idCoFormalizo = idCoFormalizo.replaceFirst("A", "");
				}
				String nombreCoFormalizo = mEmpleados.get(idCoFormalizo);//nombre coFormalizo
				result.add(folio);
				result.add(cuenta);
				result.add(cuentaDesencriptada);
				result.add(idFormalizo);
				result.add(nombreFormalizo);
				result.add(idCoFormalizo);
				result.add(nombreCoFormalizo);
			}
			registros.add(result);
		}
		LayoutTempleteContract template=new LayoutTempleteContract();
		RowHandler<String> handler = new NominaHandler();
    	byte[] contenidoByte;
		try {
			contenidoByte = template.createLayout(handler.getLayoutName(), registros, true,handler.getHeaders());
			LayoutGenerator.saveRemoteFile(handler.getLayoutName(), LayoutGenerator.DocumentType.EXCEL, contenidoByte, true,"reportes");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error al enviar al generar el excel...");
		}
	}
	private String getCellValue(Row row, int position) {
		String result = "";
		try {
			if (row.getCell(position).getCellType() == Cell.CELL_TYPE_STRING) {
				result = row.getCell(position).getRichStringCellValue().getString();
			} else if (row.getCell(position).getCellType() == Cell.CELL_TYPE_NUMERIC) {	 
				double doubleValue = row.getCell(position).getNumericCellValue();
				if ((doubleValue % 1 ) == 0 ) { //Si es igual a 0, entonces es entero sin decimales
					result = Integer.toString((int) doubleValue);
				} else { //si tiene decimales
					result = Double.toString(doubleValue);
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

}
