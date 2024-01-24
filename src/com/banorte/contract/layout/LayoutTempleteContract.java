
package com.banorte.contract.layout;

import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.util.Formatter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Darvy Arch
 */



public class LayoutTempleteContract {
    
    private Contract contract = null;
    private Formatter formatter = new Formatter();	
    private String getBind(String key) {
        String value = "";
        if(contract!=null){
            Collection<ContractAttribute> atts = contract.getContractAttributeCollection();
            for(ContractAttribute att:atts){
                if(att.getAttribute().getFieldname().equals(key)){
                    value = att.getValue()!=null?att.getValue():"";
                }
            }
        }
        //log.info("Looking binding for "+key+"=["+value+"]");
        return value;
    }

    public Map<String, String> bindFrom(Contract contract, String[] fills) {
        Map<String, String> map = new HashMap<String, String>();
        
        this.contract = contract;
        for ( int i = 0; i < fills.length; i++ )
            map.put(fills[i], getBind(fills[i]));
        
        return  map;
    }
 
    public byte[] createTextLayout(String layoutName, List<List<String>> contracts) throws IOException {
    	StringBuffer buffer = new StringBuffer();
    	
    	if( contracts!=null && contracts.size() > 0 ) {
    	     for ( List<String> content: contracts ) {
    	    	 buffer.append(Formatter.fixLenght(content.get(0),8));
            	 buffer.append(Formatter.fixLenght(content.get(1),10));
            	 buffer.append(content.get(2)!=""?formatter.formatDateToStringOIP(formatter.formatDate(content.get(2)).getTime()):"0000-00-00");
            	 buffer.append("\n");
        	}
    		 return buffer.toString().getBytes();
	    } else
	        return null;
	    }
    
    public byte[] createTextLayout(List<List<String>> rows,String delimiter, List<String>headers) throws IOException {
    	StringBuffer buffer = new StringBuffer();
    	if(headers!=null){//se agregan los headers como una linea al inicio
    		Object[] cells = headers.toArray();
	    	 for(int index = 0; index < cells.length ; index++){
	    		 	if(index == 0)
	    		 		buffer.append(cells[index].toString());
	    		 	else
	    		 		buffer.append(delimiter + cells[index].toString());
	    	 }
	    	 buffer.append("\r\n");
    	}
    	if( rows!=null && rows.size() > 0 ) {//
    	     for ( List<String> row: rows ) {
    	    	 Object[] cells = row.toArray();
    	    	 for(int index = 0; index < cells.length ; index++){
    	    		 	if(index == 0)
    	    		 		buffer.append(cells[index].toString());
    	    		 	else
    	    		 		buffer.append(delimiter + cells[index].toString());
    	    	 }
    	    	 buffer.append("\r\n");
        	}
    		 return buffer.toString().getBytes();
    	} else
	        return null;
	    }
    
    public byte[] createLayout(String layoutName, List<List<String>> contracts, boolean header, List<String> headerFills) throws IOException {
        if( contracts.size() > 0 ) {
            short rownum;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Workbook wb = new HSSFWorkbook();
            Sheet s = wb.createSheet();
            Row r = null;
            Cell c = null;

            Font f = wb.createFont();
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
                // create cells
                for ( short cellnum = (short) 0; cellnum < content.size(); cellnum++ ) {
                    // create a cell
                    c = r.createCell(cellnum);
                    // enter value into cell
                    c.setCellValue( content.get(cellnum) );
                    // make this column a bit wider
                    s.setColumnWidth((cellnum + 0), (short) ((50 * 8) / ((double) 1 / 20)));
                }
                rownum++;
            }
            // write the workbook to the output stream
            // close our file (don't blow out our file handles)
            wb.write(out);
            byte[] contentBytes = out.toByteArray();
            out.close();

            return contentBytes;
        } else
            return null;
    }
    

}
