/**
 * 
 */
package com.banorte.contract.layout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.banorte.contract.model.Contract;
import com.banorte.contract.model.LoadFile;
import com.banorte.contract.util.ResultMonthLoadFile;

/**
 * @author omar
 *
 */
public class LoadFileDailyLayout {


	public List<String> getContent(LoadFile loadFile) {
        List<String> list = new ArrayList<String>();        
        
        list.add(loadFile.getCreationDate().toString());  
        list.add(loadFile.getFileName());
        list.add(loadFile.getCreationDateDay());   
        list.add(loadFile.getNumberSuccess().toString());   
        list.add(loadFile.getNumberFail().toString()); 
        list.add(loadFile.getNumberSuccess().toString());   
        list.add(loadFile.getFileType());  
        
        return list;
    }
	
	
	public List<String> getContent(ResultMonthLoadFile resultMonthLoadFile) {
        List<String> list = new ArrayList<String>();        
        
        list.add(resultMonthLoadFile.getProductType());  
        list.add(resultMonthLoadFile.getNumberGenerate().toString());
        list.add(resultMonthLoadFile.getTotal().toString());
        list.add(resultMonthLoadFile.getNumberSuccess().toString());
        list.add(resultMonthLoadFile.getNumberFail().toString());
        list.add(resultMonthLoadFile.getTotal().toString()) ;
        list.add(resultMonthLoadFile.getFileType());
   
        
        return list;
    }
	
	
		public List<String> getHeaderMonthly(){
			 
		    List<String> list = new ArrayList<String>();
		    
		    list.add("Producto");      //Producto
		    list.add("Registros Generados");       //client_fiscalname
		    list.add("Registros Depositados");              //client_rfc
		    list.add("Registros Con Exito");      //Registros Con Exito
		    list.add("Registros Con Error");   //client_sic
		    list.add("Total de Registros");                     
		    list.add("Tipo");            //client_state
		    
		    return list;
		}


	
	 public List<String> getHeaderDaily(){
		 
	        List<String> list = new ArrayList<String>();
	        
	        list.add("Fecha");      //reference
	        list.add("Nombre Del Archivo");      //reference
	        list.add("Registros Generados");       //client_fiscalname
	        list.add("Registros Depositados");              //client_rfc
	        list.add("Registros Con Error");   //client_sic
	        list.add("Total de Registros");                     
	        list.add("Tipo");            //client_state
	        
	        return list;
	    }
	 
	 
	 
	 public byte[] createLayoutByte(String layoutName, List<List<String>> contracts, boolean header, List<String> headerFills) throws IOException {
	        if( contracts.size() > 0 ) {
	            short rownum;
	            ByteArrayOutputStream out = new ByteArrayOutputStream();
	            Workbook wb 	= new HSSFWorkbook();
	            Sheet s 		= wb.createSheet();
	            Font f 			= wb.createFont();
	            Row r 			= null;
	            Cell c 			= null;

	            
	            f.setFontHeightInPoints((short) 12);
	            f.setColor( (short)0x0 );

	            wb.setSheetName(0, layoutName );

	            rownum = (short)0;
	            if ( header ) {
	                r = s.createRow(rownum);
	                for ( short cellnum = (short) 0; cellnum < headerFills.size(); cellnum++ ) {
	                    c = r.createCell(cellnum);
	                    c.setCellValue( headerFills.get(cellnum) );
	                    s.setColumnWidth((cellnum + 0), (short) ((50 * 8) / ((double) 1 / 20)));
	                }
	                rownum++;
	            }

	            for ( List<String> content: contracts ) {
	                // create a row
	                r = s.createRow(rownum);
	                // create 10 cells (0-9)
	                for ( short cellnum = (short) 0; cellnum < content.size(); cellnum++ ) {
	                    // create a numeric cell
	                    c = r.createCell(cellnum);
	                    // do some goofy math to demonstrate decimals
	                    c.setCellValue( content.get(cellnum) );
	                    // make this column a bit wider
	                    s.setColumnWidth((cellnum + 0), (short) ((50 * 8) / ((double) 1 / 20)));
	                }
	                rownum++;
	            }
	            // write the workbook to the output stream
	            // close our file (don't blow out our file handles
	            wb.write(out);
	            byte[] contentBytes = out.toByteArray();
	            out.close();

	            return contentBytes;
	        } else
	            return null;
	    }

}
