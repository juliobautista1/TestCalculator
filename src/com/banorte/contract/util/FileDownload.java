/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banorte.contract.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.poi.util.IOUtils;


/**
 *
 * @author Darvy Arch
 */
public class FileDownload {
    private static Logger log = Logger.getLogger(FileDownload.class.getName());
    private String destination;
    private String username;
    private String password;
    private String fileRemoteDir;
    private List<String> result = new ArrayList<String>();
    private static FTPClient ftp = new FTPClient();

    public FileDownload(String destination, String username, String password, String fileRemoteDir) {

        this.destination = destination;
        this.username = username;
        this.password = password;
        this.fileRemoteDir = fileRemoteDir;

    }

    public String[] remoteFileListing() throws IOException {

        String[] list = null;

        this.connectFTPServer();
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        FTPFile[] rFiles = ftp.listFiles(fileRemoteDir);
        if(rFiles != null && rFiles.length > 0){

            list = new String[rFiles.length];
            for(int i = 0; i < rFiles.length; i++) {
                list[i] = rFiles[i].getName();
            }

        }

        this.disconnectFTPServer();

        return list;
    }

    public List<String> remoteFileList() {
        List<String> fileNames = new ArrayList<String>();
        try {
			this.connectFTPServer();
		
	        System.out.println("Antes de Leer Los archivos");
	        FTPFile[] rFiles = ftp.listFiles(fileRemoteDir);
	        System.out.println("Despues de Leer Los archivos");
	        if (rFiles != null && rFiles.length > 0) {
	            for(int i = 0; i < rFiles.length; i++) {
	            	System.out.println(rFiles[i].getName());
	                fileNames.add(rFiles[i].getName());
	            }
	        }
	        this.disconnectFTPServer();
        
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        return fileNames;
    }
    
    public boolean deleteFile(String fileName) throws IOException {
        boolean flag = false;
        this.connectFTPServer();

        flag = ftp.deleteFile(fileRemoteDir + "/" + fileName);
        
        this.disconnectFTPServer();

        return flag;
    }

    public ByteArrayOutputStream downloadFile(String fileName, ByteArrayOutputStream bop) throws IOException { 
    	//FIXME gina prueba local inicio
    		File f=new File("C:\\Prueba\\"+fileName); //Se comento
    		InputStream inputStream=new FileInputStream( f );
    		 
	        byte[] data=IOUtils.toByteArray(inputStream);
	        bop.write(data);
	        bop.flush();
	        bop.close();
	        inputStream.close(); 
	       
	    //...FIXME gina prueba local fin
//	    FIXME gina descomentar para prod INICIO
//	        this.connectFTPServer(); 
//	        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
//	        if (!ftp.retrieveFile(fileRemoteDir + File.separator + fileName, bop)) {
            //	El archivo no pudo ser almacenado
//            log.log(Level.WARNING, "File Downloader, el archivo no pudo ser almacenado. " + fileName);
//        } 
//        this.disconnectFTPServer();
//	     FIXME gina descomentar para prod FIN
        return bop;
    }


    public Map<String, ByteArrayOutputStream> downloadFilesList(List<String> files) throws IOException {

        this.connectFTPServer();
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        FTPFile[] rFiles = ftp.listFiles(fileRemoteDir);

        if(rFiles != null && rFiles.length > 0){
            Map<String, ByteArrayOutputStream> filesBOS = new HashMap<String, ByteArrayOutputStream>();
            for(int i=0; i < rFiles.length; i++){
                String fileName = rFiles[i].getName();
                //	Revisamos que los archivos en el folder sean los que tenemos en procesamiento
                //	no debemos bajar archivos del que no tengamos referencia.
                boolean exist = false;
                for(String file : files){
                    if(fileName.equals(file)){
                        exist = true;
                        break;
                    }

                }

                if(exist){
                    ByteArrayOutputStream bop = new ByteArrayOutputStream();
                    if(ftp.retrieveFile(fileRemoteDir + "/" + fileName, bop)){
                        filesBOS.put(fileName, bop);
                    } else {
                            //	El archivo no pudo ser almacenado
                        log.log(Level.WARNING, "File Downloader, el archivo no pudo ser almacenado. " + fileName);
                    }
                    bop.close();
                }
            }
            this.disconnectFTPServer();
            return filesBOS;
        }

        return null;
    }

    public Map<String, ByteArrayOutputStream> downloadFiles(List<File> files) throws IOException {

        List<String> fileNames = new ArrayList<String>();

        for(File file : files){
            fileNames.add(file.getName());
        }

        return downloadFilesList(fileNames);
    }

    private void connectFTPServer() throws IOException {

        ftp.connect(destination);
        ftp.login(username, password);

    }

    private void disconnectFTPServer() throws IOException {

        if(ftp.isConnected()){
            ftp.logout();
            ftp.disconnect();
        }

    }

    public List<String> getResult() {
        return result;
    }
    
/*    public ByteArrayOutputStream downloadFileLocal(String fileName, ByteArrayOutputStream bop) throws IOException { 
        this.connectFTPServer();
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        //Agregado
        File f=File.createTempFile("temp","Excel.xls"); //El nombre del archivo es: tempExcel.xls
        File f1=new File("C:\\miExcel.xls"); //Estaba comentado
        OutputStream outputStream = new FileOutputStream(f); 
        bop.writeTo(outputStream);
        //Fin de Agregado
        if (!ftp.retrieveFile(fileRemoteDir + "/" + fileName, bop)) {
            // El archivo no pudo ser almacenado
            log.log(Level.WARNING, "File Downloader, el archivo no pudo ser almacenado. " + fileName);
        }
        this.disconnectFTPServer();

        return bop;
    }*/
}
