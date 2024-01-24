/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banorte.contract.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Darvy Arch
 */
public class FileLayout {    
	
	/**
	 * Metodo que obtiene los archivos de RESPUESTA
	 * @param ftp
	 * @return
	 * @throws IOException
	 */
    public Map<String, InputStream> getRemoteFiles(FTPProperties ftp) throws IOException {
        Map<String, InputStream> map = new HashMap<String, InputStream>();
        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathDownload());
//        List<String> fileList = fDownload.remoteFileList(); //descomentar para PRODUCCION
        List<String> fileList = new ArrayList<String>();//FIXME gina save local
//        fileList.add("TPVRespuesta.xls");//FIXME gina save local
        fileList.add("Respuesta1.xls");//FIXME gina save local
////        fileList.add("Respuesta2.xls");//FIXME gina save local
        if ( fileList.size() > 0 ) {
            for ( String name : fileList) {                
                map.put(name ,new ByteArrayInputStream((fDownload.downloadFile(name, new ByteArrayOutputStream())).toByteArray()));
            }
        }
        return map;
    }
	
	    public Map<String, InputStream> getRemoteFilesMujerPyME(FTPProperties ftp) throws IOException {
        Map<String, InputStream> map = new HashMap<String, InputStream>();
        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathCuentasMujerPyME());
        List<String> fileList = fDownload.remoteFileList(); //descomentar para PRODUCCION
//        List<String> fileList = new ArrayList<String>();//FIXME gina save local
//        fileList.add("TPVRespuesta.xls");//FIXME gina save local
////        fileList.add("Respuesta1.xls");//FIXME gina save local
////        fileList.add("Respuesta2.xls");//FIXME gina save local
        if ( fileList.size() > 0 ) {
            for ( String name : fileList) {                
            	//map.put(name ,new ByteArrayInputStream((fDownload.downloadFileLocal(name, new ByteArrayOutputStream())).toByteArray()));
                //map.put(name ,new ByteArrayInputStream((fDownload.downloadFile2(name, new ByteArrayOutputStream())).toByteArray()));
            	map.put(name ,new ByteArrayInputStream((fDownload.downloadFile(name, new ByteArrayOutputStream())).toByteArray()));
            }
        }
        return map;
    }
        
    public Map<String, InputStream> getRemoteFile(String fileName, FTPProperties ftp) throws IOException {
        Map<String, InputStream> map = new HashMap<String, InputStream>();

        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathMaintanceDownload());
                
        map.put(fileName ,new ByteArrayInputStream((fDownload.downloadFile(fileName, new ByteArrayOutputStream())).toByteArray()));
        
        return map;
    }
        
    public Map<String, InputStream> getRemoteInstallFiles(FTPProperties ftp) throws IOException {
        Map<String, InputStream> map = new HashMap();
        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathInstall());
        List<String> fileList = fDownload.remoteFileList();
   //     fileList.add("ComisionesPrueba.xls");
        if ( fileList.size() > 0 ) {
            for ( String name : fileList) {                
                map.put(name ,new ByteArrayInputStream((fDownload.downloadFile(name, new ByteArrayOutputStream())).toByteArray()));
            }
        }                
        
        return map;
    }
    
    public Map<String, InputStream> getRemoteFileCommision(FTPProperties ftp) throws IOException {
        Map<String, InputStream> map = new HashMap<String, InputStream>();
        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathCommision());
        List<String> fileList = fDownload.remoteFileList(); //FIXME gina descomentar para prod
//        List<String> fileList = new ArrayList<String>();//FIXME gina prueba local
//        fileList.add("ComisionesPrueba2.xls");// prueba local
        if ( fileList.size() > 0 ) {
            for ( String name : fileList) {                
                map.put(name ,new ByteArrayInputStream((fDownload.downloadFile(name, new ByteArrayOutputStream())).toByteArray()));
            }
        }                
        
        return map;
    }
    

    public boolean deleteRemoteFiles(String fileName, FTPProperties ftp) throws IOException { 
        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathDownload());
        return fDownload.deleteFile(fileName);
    }
    
    public boolean deleteRemoteInstallFiles(String fileName, FTPProperties ftp) throws IOException { 
        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathInstall());
        return fDownload.deleteFile(fileName);
    }
    
    public boolean deleteRemoteFilesCommision(String fileName, FTPProperties ftp) throws IOException { 
        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathCommision());
        return fDownload.deleteFile(fileName);
    }
    
    public void storeRemoteFile(String fileName, byte[] content, FTPProperties ftp) throws Exception {
        FileUpload fUpload = new FileUpload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathUpload());
        fUpload.uploadFile(fileName, content);
    }
    
    public void storeRemoteFileAdquirente(String fileName, byte[] content, FTPProperties ftp) throws Exception {
        FileUpload fUpload = new FileUpload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathUploadAdquirente());
        fUpload.uploadFile(fileName, content);
    }
    
    public void storeRemoteReportFile(String fileName, byte[] content, FTPProperties ftp) throws Exception {
        FileUpload fUpload = new FileUpload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathReportes());
        fUpload.uploadFile(fileName, content);
    }
    public void uploadToFtp(String fileName, byte[] content, FTPProperties ftp, String folder) throws Exception {
    	FileUpload fUpload =null;
    	if(folder.equalsIgnoreCase("entradas")){
    		fUpload = new FileUpload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathReportes());
    	}else if(folder.equalsIgnoreCase("reportes")){
    		fUpload = new FileUpload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathReportes());
    	}else if(folder.equalsIgnoreCase("mttoTPV")){
    		fUpload = new FileUpload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getMttoTPV());
    	}else if(folder.equalsIgnoreCase("reportesDiariosAdquirente")){
    		fUpload = new FileUpload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathUploadAdquirentReporteDaily());
    	}else{//el else es para carpeta ebanking //if(folder.equalsIgnoreCase("ebanking")){
    		fUpload=new FileUpload(ftp.getHostAltas(), ftp.getUserAltas(), ftp.getPassAltas(), ftp.getPathAltas());
    	}
        fUpload.uploadFile(fileName, content);
    }    
    
    public void storeRemoteActivateFile(String fileName, byte[] content, FTPProperties ftp) throws Exception {
        FileUpload fUpload = new FileUpload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathActivate());
        fUpload.uploadFile(fileName, content);
    }
    
    public void storeRemoteOIPFile(String fileName, byte[] content, FTPProperties ftp) throws Exception {
        FileUpload fUpload = new FileUpload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathOIP());
        fUpload.uploadFile(fileName, content);
    }
    
    public void storeRemoteDirectaFile(String fileName, byte[] content, FTPProperties ftp) throws Exception {
        FileUpload fUpload = new FileUpload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathDirectaUpload());
        fUpload.uploadFile(fileName, content);
    }
    
    public void storeSFTPRemoteFileABEM(String fileName, byte[] content, FTPProperties ftp) throws Exception {
        FileUpload fUpload = new FileUpload(ftp.getSFTPHost(), ftp.getSFTPUser(), ftp.getSFTPPass(), ftp.getSFTPPathUploadABEM());
        fUpload.uploadFileBySFTP(fileName, content);
    }
    
    public void storeSFTPRemoteFileMBEM(String fileName, byte[] content, FTPProperties ftp) throws Exception {
        FileUpload fUpload = new FileUpload(ftp.getSFTPHost(), ftp.getSFTPUser(), ftp.getSFTPPass(), ftp.getSFTPPathUploadMBEM());
        fUpload.uploadFileBySFTP(fileName, content);
    }
    
    public void storeLocalFile(String fileName, byte[] content) throws IOException {        
    	FileOutputStream fos = null;
    	try {
  //  		fos = new FileOutputStream(fileName); //FIXME gina descomentar para prod
    		fos = new FileOutputStream("C:\\Prueba\\"+fileName); //FIXME gina local 
    		fos.write(content);
            fos.close();
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}finally{
			 if (fos != null) {
				 safeClose(fos);
			 }
		}
    }
    
//    public InputStream getRemoteDirectaFile(String fileName, FTPProperties ftp) throws IOException {
//        InputStream in = null;
//        
//        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathDirectaDownload());
//                
//        in = new ByteArrayInputStream((fDownload.downloadFile(fileName, new ByteArrayOutputStream())).toByteArray());
//        
//        return in;
//    }

    public Map<String, InputStream> getRemoteDirectaFile(String fileName, FTPProperties ftp) throws IOException {
        Map<String, InputStream> map = new HashMap<String, InputStream>();

        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathDirectaDownload());
                
        map.put(fileName ,new ByteArrayInputStream((fDownload.downloadFile(fileName, new ByteArrayOutputStream())).toByteArray()));
        
        return map;
    }
    
    /*public Map<String, InputStream> getRemoteExecutivesFiles(FTPProperties ftp) throws IOException {
        Map<String, InputStream> map = new HashMap<String, InputStream>();
        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathExecutives());
        List<String> fileList = fDownload.remoteFileList();
        if ( fileList.size() > 0 ) {
            for ( String name : fileList) {                
                map.put(name ,new ByteArrayInputStream((fDownload.downloadFile(name, new ByteArrayOutputStream())).toByteArray()));
            }
        }                
        
        return map;
    }*/
    
    public Map<String, InputStream> getRemoteFiles(FTPProperties ftp,String path) throws IOException {
        Map<String, InputStream> map 	= new HashMap<String, InputStream>();
        FileDownload fDownload 			= new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), path);
        List<String> fileList 			= fDownload.remoteFileList();
        
        if ( fileList.size() > 0 ) {
            for ( String name : fileList) {  
                map.put(name ,new ByteArrayInputStream((fDownload.downloadFile(name, new ByteArrayOutputStream())).toByteArray()));
            }
        }                
        
        return map;
    }
    public Map<String, InputStream> buscarReportesAltas(FTPProperties ftp) throws IOException {
        Map<String, InputStream> map = new HashMap<String, InputStream>();
        FileDownload fDownload = new FileDownload(ftp.getHostAltas(), ftp.getUserAltas(), ftp.getPassAltas(), ftp.getPathAltas());
        List<String> fileList = fDownload.remoteFileList();
        if ( fileList.size() > 0 ) {
            for ( String name : fileList) {                
                map.put(name ,new ByteArrayInputStream((fDownload.downloadFile(name, new ByteArrayOutputStream())).toByteArray()));
            }
        }                
        return map;
    }
    
    public boolean eliminarReportesAltas(String fileName, FTPProperties ftp,String path) throws IOException { 
        FileDownload fDownload = new FileDownload(ftp.getHostAltas(), ftp.getUserAltas(), ftp.getPassAltas(), path);
        return fDownload.deleteFile(fileName);
    }
    
    public Map<String, InputStream> buscarReportesAdqDiarios(FTPProperties ftp) throws IOException {
    	Map<String, InputStream> map = new HashMap<String, InputStream>();
        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathUploadAdquirentReporteDaily());
        List<String> fileList = fDownload.remoteFileList();
        if ( fileList.size() > 0 ) {
            for ( String name : fileList) {                
                map.put(name ,new ByteArrayInputStream((fDownload.downloadFile(name, new ByteArrayOutputStream())).toByteArray()));
            }
        }                 
    	return map;
    }
    
    public boolean eliminarReportesAdqDiarios(String fileName, FTPProperties ftp,String path) throws IOException { 
        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), path);
        return fDownload.deleteFile(fileName);
    }
    
    /*public boolean deleteRemoteExecutivesFiles(String fileName, FTPProperties ftp) throws IOException { 
        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), ftp.getPathExecutives());
        return fDownload.deleteFile(fileName);
    }*/
    
    public boolean deleteRemoteFiles(String fileName, FTPProperties ftp,String path) throws IOException { 
        FileDownload fDownload = new FileDownload(ftp.getHost(), ftp.getUser(), ftp.getPass(), path);
        return fDownload.deleteFile(fileName);
    }
    
    public static void safeClose(FileOutputStream fos) {
    	if (fos != null) {
    		try {
    			fos.close();
    		} catch (IOException e) {
    			System.out.println(e);
    		}
    	}
    }
}