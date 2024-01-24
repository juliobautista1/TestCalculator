package com.banorte.contract.util;

import java.io.IOException;
import java.util.Date;

public class ReportGenerator {

	public static class LayoutGenerator{
		
		public enum DocumentType{EXCEL,PLAIN_TEXT};
		
		public static void saveRemoteFile(String productName,DocumentType tipoDocumento,byte[] content,boolean saveCopyLocal,String folder){
				FileLayout fLayout = new FileLayout();
				Formatter format = new Formatter();
				String date = format.formatDateToStringLayout(new Date());
				FTPProperties fprops = new FTPProperties();
				String extension=null;
				
				switch(tipoDocumento){
					case EXCEL:
						extension=".xls";
					break;
					case PLAIN_TEXT:
						extension=".txt";
					break;
				}
				
				String fileName = productName+"-"+date+extension;
				
				try {
					// Upload report to FTP
					fLayout.uploadToFtp(fileName, content, fprops, folder); //reportes o layout FIXME gina descomentar para prod
//					fLayout.storeLocalFile(fileName, content); //FIXME gina saveLocal 
			    		
			    	//Save local copy if requested
			    	if (saveCopyLocal) {
						try {
							fLayout.storeLocalFile(fileName, content);
						} catch (IOException e) {
							System.out.println("No se pudo almacenar el archivo " + productName);
						}
					}
			    	
				} catch (Exception e) {
					System.out.println("Ocurrio un error al guardar el archivo de "+ productName + e);
				}
		    }
	}

}
