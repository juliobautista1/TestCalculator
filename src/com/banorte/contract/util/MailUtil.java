/**
 * 
 */
package com.banorte.contract.util;


/**
 * @file MandarEmail.java
 * @version 1.0
 * @author Linea de Codigo (http://lineadecodigo.com)
 * @date   26.marzo.2011
 * @url    http://lineadecodigo.com/java/mandar-emails-con-javamail/
 * @description Clase que nos permite enviar un email con Java Mail  
 */


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.banorte.contract.model.FailReference;
import com.banorte.contract.model.LoadFile;
import com.banorte.contract.model.WrapContractGenerate;
 
public class MailUtil {
	
	
	/**
	 * proceso para enviar una lista de correos 
	 * @param subject
	 * @param recipients
	 * @param wrap
	 * @param isAutomatic
	 */
	public  void sendListMail(String subject,String recipients,WrapContractGenerate wrap,boolean isAutomatic) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "15.128.4.36");
		//props.put("mail.smtp.socketFactory.port", "465");
		//props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		//props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.port", "465");
 
		Autentificacion pwd 			= new Autentificacion();
		Session session 				= Session.getInstance(props,pwd);
		String mailBody 				= ApplicationConstants.EMPTY_STRING;

		try {
			
			if(isAutomatic){
				subject = subject + " " +ApplicationConstants.SUBJECT_MAIL_AUTOMATICO + getCorte();
			}else{
				subject = subject + " " +ApplicationConstants.SUBJECT_MAIL_MANNUAL;
			}
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ebanking@banorte.com"));
			if(recipients==null){
				recipients = "georgina.merla@banorte.com";
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("georgina.merla@banorte.com"));
			}
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recipients));
			message.setSubject(subject);
			mailBody = formatMailListGenerateLayout(wrap);
			message.setContent(mailBody, "text/html");
 
			Transport.send(message);
			
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	/**
	 * Proceso para enviar un correo
	 * @param subject
	 * @param recipients
	 * @param loadedFileInfo
	 * @param failReference
	 * @param isAutomatic
	 */
	public  void sendOneMail(String subject,String recipients,LoadFile loadedFileInfo,List<FailReference> failReference,boolean isAutomatic) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "15.128.4.36");
		//props.put("mail.smtp.socketFactory.port", "465");
		//props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		//props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.port", "465");
 
		Autentificacion pwd = new Autentificacion();
		Session session = Session.getInstance(props,pwd);
		String mailBody = ApplicationConstants.EMPTY_STRING;
 
		try {
			
			if(isAutomatic){
				subject = subject + " " +ApplicationConstants.SUBJECT_MAIL_AUTOMATICO  + getCorte();
			}else{
				subject = subject + " " +ApplicationConstants.SUBJECT_MAIL_MANNUAL;
			}
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ebanking@banorte.com"));
			message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recipients));
			message.setSubject(subject);
			mailBody = formatMailOneGenerateLayout(loadedFileInfo,failReference);
			message.setContent(mailBody, "text/html");
			//message.setText(bodyMail);
			//message.setText("Dear Mail Crawler," + "\n\n No spam to my email, please!");
 
			Transport.send(message);
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	/**
	 * Proceso para enviar correos
	 * @param wrap
	 * @return
	 */
	private String formatMailListGenerateLayout(WrapContractGenerate wrap){		
			
			String mailBody 					= ApplicationConstants.EMPTY_STRING;
			SimpleDateFormat formateador 		= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			List<LoadFile> loadedFileInfo  		= wrap.getLoadedFileInfo();
			List<FailReference> failReference  	= wrap.getListFailReference();
		
			mailBody = "Los Siguientes archivos han sido Generados/Cargados por la Herramienta eBanking: <br/><br/>";
			//Errores al inicio del correo
			if(failReference != null){
				if(failReference.size() > ApplicationConstants.VALUE_0){
					mailBody = mailBody + "<div style=\"color:red;\">Folios Con Error</div>";
					for(FailReference failInfo : failReference ){
						mailBody = mailBody + "*" +failInfo.getReference() + "<br/>"  ;
					}
					mailBody = mailBody + "<div>- - - - - - - - - -</div>";
				}
			}
			if(!loadedFileInfo.isEmpty()){
				for(LoadFile info : loadedFileInfo ){
					int totalReg = info.getNumberSuccess() + info.getNumberFail();
					mailBody = mailBody + this.getProductTypeStr( info.getProductType().toString() ) + "<br/>";
					mailBody = mailBody + "Tipo de Archivo: " + info.getFileType()+ "<br/>"  ;
					mailBody = mailBody + "Nombre de Archivo: " + info.getFileName()   + "<br/>" ;
					if(totalReg == ApplicationConstants.VALUE_0){
						mailBody = mailBody + "Fecha y Hora: ";
					}else{
						mailBody = mailBody + "Fecha y Hora: " +formateador.format( info.getCreationDate() ) + "<br/>" ;
					}
					mailBody = mailBody + "Numero de Registros: " + totalReg  + "<br/>" ;
					mailBody = mailBody + "Numero de Registros Exitosos: " + info.getNumberSuccess()  + "<br/>" ;
					mailBody = mailBody + "Numero de Registros Fallidos: " + info.getNumberFail() + "<br/>" ;
					mailBody = mailBody +  "<br/><br/>" ;
				}
			}else {
				int total = ApplicationConstants.VALUE_0;
				if(failReference != null){
					total = failReference.size();
				}
				
				mailBody = mailBody + wrap.getContractTypeName()+ "<br/>";
				mailBody = mailBody + "Tipo de Archivo: " +  "<br/>"  ;
				mailBody = mailBody + "Nombre de Archivo: " + "<br/>" ;
				mailBody = mailBody + "Fecha y Hora: ";
				mailBody = mailBody + "Numero de Registros: " + total  + "<br/>" ;
				mailBody = mailBody + "Numero de Registros Exitosos: " + ApplicationConstants.ZERO_STRING + "<br/>" ;
				mailBody = mailBody + "Numero de Registros Fallidos: " + failReference.size() + "<br/>" ;
				mailBody = mailBody +  "<br/><br/>" ;
			}
			return mailBody;
	}
	
	
	/**
	 * genera el Cuerpo del correo que se envia para la lectura/Generacion de layout
	 * @param info
	 * @param failReference
	 * @return
	 */
	private String formatMailOneGenerateLayout(LoadFile info , List<FailReference> failReference){		
		
		String mailBody = ApplicationConstants.EMPTY_STRING;
		SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
		mailBody = "Los Siguientes archivos han sido Generados/Cargados por la Herramienta eBanking: <br/><br/>";
		
		if(info != null){
		
			int totalReg = info.getNumberSuccess() + info.getNumberFail();
			
			mailBody = mailBody + getProductTypeStr( info.getProductType().toString() ) + "<br/>";
			mailBody = mailBody + "Tipo de Archivo: " + info.getFileType()+ "<br/>"  ;
			mailBody = mailBody + "Nombre de Archivo: " + info.getFileName()   + "<br/>" ;
			if(totalReg == ApplicationConstants.VALUE_0){
				mailBody = mailBody + "Fecha y Hora: ";
			}else{
				mailBody = mailBody + "Fecha y Hora: " +formateador.format( info.getCreationDate() ) + "<br/>" ;
			}
			
			mailBody = mailBody + "Numero de Registros: " + totalReg  + "<br/>" ;
			mailBody = mailBody + "Numero de Registros Exitosos: " + info.getNumberSuccess()  + "<br/>" ;
			mailBody = mailBody + "Numero de Registros Fallidos: " + info.getNumberFail() + "<br/>" ;
			mailBody = mailBody +  "<br/><br/>" ;
			
		}else{
			mailBody = mailBody + "No hay Regsitros<br/>"  ;
		}
			
		if(failReference != null){
			if(failReference.size() > ApplicationConstants.VALUE_0){
				for(FailReference failInfo : failReference ){
					mailBody = mailBody + "<div style=\"color:red;\">Folios Con Error</div>";
					mailBody = mailBody + "*" +failInfo.getReference() + "<br/>"  ;
				}
			}
		}
		
		return mailBody;
}
	
	
	/**
	 * Obtiene el nombre del tipo de producto de acuerdo al id que se le proporcione , de tipo String
	 * @param fileTypeStr
	 * @return
	 */
		public String getProductTypeStr(String fileTypeStr){
				
				String result 	= ApplicationConstants.EMPTY_STRING;
				Integer fileType = Integer.valueOf(fileTypeStr);
				
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
		
		
		/**
		 * Obtiene la Hora en la que se realiza el Corte de envio y recoleccion de Informacion para agregar al encabezado del correo
		 * @return
		 */
		private String getCorte(){
			
			Date creationDate 			= new Date();
			String timeHora				= new SimpleDateFormat("hh a").format(creationDate);
			
			return " corte " + timeHora;
			
		}
		
		
		
		/**
		 * genera una lista de LoadFile con 1 objeto , con puro valor cero o vacio de acuerdo al caso
		 * @param productType
		 * @param fileType
		 * @return
		 */
		public List<LoadFile> emptyLoadFile(int productType, String fileType){
			List<LoadFile>	loadedFileInfo 	= new ArrayList<LoadFile>();
			LoadFile result 				= new LoadFile();
			
			
			result.setProductType(productType);
			result.setFileType(fileType);
			result.setFileName(ApplicationConstants.EMPTY_STRING);
			result.setNumberSuccess(ApplicationConstants.VALUE_0) ;
			result.setNumberFail(ApplicationConstants.VALUE_0) ;
			
			loadedFileInfo.add(result);
			
			return loadedFileInfo;
		}
		
		
		public HashMap<String,Boolean> validateFileTypeRead(HashMap<String,Boolean> mapSendMail){
			
		if(! mapSendMail.containsKey(ApplicationConstants.PT_BEM.toString()) ){
				mapSendMail.put(ApplicationConstants.PT_BEM.toString(), Boolean.FALSE);
			}
		if (! mapSendMail.containsKey( ApplicationConstants.PT_NOMINA.toString())) { 
				mapSendMail.put( ApplicationConstants.PT_NOMINA.toString(), Boolean.FALSE) ;
			}
		if (! mapSendMail.containsKey( ApplicationConstants.PT_ADQ.toString())) { 
				mapSendMail.put( ApplicationConstants.PT_ADQ.toString(), Boolean.FALSE) ;
			}
		if (! mapSendMail.containsKey( ApplicationConstants.PT_MB.toString())) { 
				mapSendMail.put(ApplicationConstants.PT_MB.toString(), Boolean.FALSE) ;
			}
		if (! mapSendMail.containsKey( ApplicationConstants.PT_CD.toString())) { 
				mapSendMail.put(ApplicationConstants.PT_CD.toString(), Boolean.FALSE) ;
			}
			
			
			return mapSendMail;
		}
		
		
		public List<LoadFile> fillInfoReadLayout(HashMap<String,Boolean> mapSendMail){
			List<LoadFile> result	= new ArrayList<LoadFile>();
			
			if(! mapSendMail.get(ApplicationConstants.PT_BEM.toString()) ){
					result.addAll( emptyLoadFile(ApplicationConstants.PT_BEM, ApplicationConstants.FILE_TYPE_RESPUESTA) );
			}
		if (! mapSendMail.get( ApplicationConstants.PT_NOMINA.toString())) { 
					result.addAll( emptyLoadFile(ApplicationConstants.PT_NOMINA, ApplicationConstants.FILE_TYPE_RESPUESTA) );
			}
		if (! mapSendMail.get( ApplicationConstants.PT_ADQ.toString())) {
					result.addAll( emptyLoadFile(ApplicationConstants.PT_ADQ, ApplicationConstants.FILE_TYPE_RESPUESTA) );
			}
		if (! mapSendMail.get( ApplicationConstants.PT_MB.toString())) {
					result.addAll(emptyLoadFile(ApplicationConstants.PT_MB, ApplicationConstants.FILE_TYPE_RESPUESTA) );
			}
		if (! mapSendMail.get( ApplicationConstants.PT_CD.toString())) { 
					result.addAll(emptyLoadFile(ApplicationConstants.PT_CD, ApplicationConstants.FILE_TYPE_RESPUESTA) );
			}
			
			
			return result;
		}
		
		
		
		public List<LoadFile> sortListLoadFile(List<LoadFile> loadedFileInfo){
			
			List<LoadFile> sortList = new ArrayList<LoadFile>();
			
			for(LoadFile info:loadedFileInfo){
				if(info.getProductType() == ApplicationConstants.PT_BEM){
					sortList.add(info);
				}
			}
			
			for(LoadFile info:loadedFileInfo){
				if(info.getProductType() == ApplicationConstants.PT_NOMINA){
					sortList.add(info);
				}
			}
			
			for(LoadFile info:loadedFileInfo){
				if(info.getProductType() == ApplicationConstants.PT_ADQ){
					sortList.add(info);
				}
			}
			
			for(LoadFile info:loadedFileInfo){
				if(info.getProductType() == ApplicationConstants.PT_MB){
					sortList.add(info);
				}
			}
			
			for(LoadFile info:loadedFileInfo){
				if(info.getProductType() == ApplicationConstants.PT_CD){
					sortList.add(info);
				}
			}
			
			return sortList;
		}
	
}