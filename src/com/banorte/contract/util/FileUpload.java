package com.banorte.contract.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.zehon.FileTransferStatus;
import com.zehon.exception.FileTransferException;
import com.zehon.sftp.SFTPClient;
import com.zehon.sftp.SFTP;
/**
 *
 * @author Darvy Arch
 */
public class FileUpload {
    private static Logger log = Logger.getLogger(FileUpload.class.getName());
    private String destination;
    private String username;
    private String password;
    private String fileDestinyDir;
    private static FTPClient ftp = new FTPClient();
    private List<String> result = new ArrayList<String>();

    private static SFTPClient sftp = new SFTPClient();

    public FileUpload(String destination, String username, String password, String fileDestinyDir) {

        this.destination = destination;
        this.username = username;
        this.password = password;
        this.fileDestinyDir = fileDestinyDir;

    }

    public void uploadFiles(List<File> files) throws IOException {
        this.connectFTPServer();

        for (File file: files) {
            try {
                this.storeFile(file);
            } catch (Exception e ) {
                log.log(Level.WARNING, "A ocurrido un error al intentar subir el siguiente archivo: " + file.getName() + " " + e.getMessage());
                result.add(file.getName());
            }
        }

        this.disconnectFTPServer();
    }

    public void uploadFile(File file) throws Exception {
        this.connectFTPServer();
        storeFile(file);
        this.disconnectFTPServer();
    }
    
    public void uploadFile(String fileName, byte[] content) throws Exception {
        this.connectFTPServer();
        ByteArrayInputStream bais = new ByteArrayInputStream(content);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        if(!ftp.storeFile(fileDestinyDir+ "/" + fileName, bais)){
            throw new Exception();
        }
        this.disconnectFTPServer();
    }
    
    public void uploadFileBySFTP(String fileName, byte[] content) throws Exception {

    	String destFolder =fileDestinyDir+"/";
    	
		try {
			this.setSFTPParameters();
			
			int status = sftp.sendFile(content, destFolder, fileName);
			if(FileTransferStatus.SUCCESS == status){
				System.out.println(fileName + " got sftp-ed successfully to  folder "+ destFolder);
			}
			else if(FileTransferStatus.FAILURE == status){
				System.out.println("Fail to sftp  to  folder "+destFolder);
			}
			
		} catch (FileTransferException e) {
			 log.log(Level.WARNING, "A ocurrido un error al intentar subir el siguiente archivo: " + fileName + " " + e.getMessage());
		}
		catch (Exception e) {
			 log.log(Level.WARNING, "A ocurrido un error al intentar subir el siguiente archivo: " + fileName + " " + e.getMessage());
		}
		
		
  }

    private void storeFile(File file) throws Exception {

        FileInputStream in = null;
        boolean storedFile = false;
        try {
        	in = new FileInputStream(file);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            storedFile = ftp.storeFile(fileDestinyDir+ "/" + file.getName(), in);
            if(!storedFile){
                result.add(file.getName());
            }
            in.close();
		} catch (Exception e) {
			throw new Exception(e); 
		}finally{
			if (in != null) {
				safeClose(in);
			}
		}
    }
    
    private void setSFTPParameters() throws IOException {

    	sftp.setServerName(destination);
    	sftp.setPassword(password);
    	sftp.setUsername(username);
    	//sftp.setPort(21);
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
    
    public static void safeClose(FileInputStream fis) {
    	if (fis != null) {
    		try {
    			fis.close();
    		} catch (IOException e) {
    			
    		}
    	}
    }
}
