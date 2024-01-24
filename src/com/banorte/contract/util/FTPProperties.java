
package com.banorte.contract.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 *
 * @author Darvy Arch
 */
public class FTPProperties {
	
	private InputStream is;

    private String host;
    private String port;
    private String user;
    private String pass;
    private String sftpHost;
    private String sftpPort;
    private String sftpUser;
    private String sftpPass;
    private String sftpPathUploadABEM;
    private String sftpPathUploadMBEM;
    
    private String pathUpload;
    private String pathReportes;
    private String pathDownload;
    private String pathActivate;
    private String pathInstall;
    private String pathOIP;
    private String pathExecutives;
    private String pathExecutiveBranch;
    private String pathCities;
    private String pathDirectaDownload;
    private String pathDirectaUpload;
    private String pathMaintanceDownload;
    private String pathCommision;
    //nuevas propiedades para Reporte de altas tpv y nomina
    private String hostAltas;
    private String userAltas;
    private String passAltas;
    private String pathAltas;
    private String mttoTPV;
    //nuevas propiedades para carpeta adquirente
    private String pathUploadAdquirente;
    private String pathCuentasMujerPyME;
    private String pathUploadAdquirentReporteDaily;

	public FTPProperties() {
        this.loadProps();
    }

	public String getHost() {
        return host;
    }
    private void setHost(String host) {
        this.host = host;
    }
    public String getPass() {
        return pass;
    }
    private void setPass(String pass) {
        this.pass = pass;
    }

  //sftpPort Property
    private void setSFTPHost(String sftpHost) {
        this.sftpHost = sftpHost;
    }
	public String getSFTPHost() {
        return sftpHost;
    }
	//sftpPort Property
    public String getSFTPPort() {
        return sftpPort;
    }
    private void setSFTPPort(String sftpPort) {
        this.sftpPort = sftpPort;
    }
    //sftpPass Property
    public String getSFTPPass() {
        return sftpPass;
    }
    private void setSFTPPass(String sftpPass) {
        this.sftpPass = sftpPass;
    }
    //sftpUser Property
    private void setSFTPUser(String sftpUser) {
        this.sftpUser = sftpUser;
    }
    public String getSFTPUser() {
        return sftpUser;
    }
    
    public String getPathDownload() {
        return pathDownload;
    }

    private void setPathDownload(String pathDownload) {
        this.pathDownload = pathDownload;
    }

    public String getPathUpload() {
        return pathUpload;
    }

    private void setPathUpload(String pathUpload) {
        this.pathUpload = pathUpload;
    }
    
    public String getPathActivate() {
		return pathActivate;
	}

    private void setPathActivate(String pathActivate) {
		this.pathActivate = pathActivate;
	}
    
    public String getPathOIP() {
		return pathOIP;
	}

    private void setPathOIP(String pathOIP) {
		this.pathOIP = pathOIP;
	}
    
    public String getPathInstall() {
		return pathInstall;
	}

    private void setPathInstall(String pathInstall) {
		this.pathInstall = pathInstall;
	}
	
    public String getPort() {
        return port;
    }

    private void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    private void setUser(String user) {
        this.user = user;
    }
    
    private void setPathDirectaDownload(String path){
    	this.pathDirectaDownload = path;
    }
    public String getPathDirectaDownload(){
    return this.pathDirectaDownload;
    }
    
    private void setPathDirectaUpload(String path){
    	this.pathDirectaUpload = path;    	
    }
    public String getPathDirectaUpload(){
        return this.pathDirectaUpload;
    }

    //pathSFTPUploadABEM Property
    private void setSFTPPathUploadABEM(String path){
    	this.sftpPathUploadABEM = path;    	
    }
    public String getSFTPPathUploadABEM(){
    	return this.sftpPathUploadABEM;    	
    }
    
    //pathSFTPUploadMBEM Property
    private void setSFTPPathUploadMBEM(String path){
    	this.sftpPathUploadMBEM = path;    	
    }
    public String getSFTPPathUploadMBEM(){
    	return this.sftpPathUploadMBEM;    	
    }
    
    private void setPathMaintanceDownload(String path){
    	this.pathMaintanceDownload = path;
    }
    public String getPathMaintanceDownload(){
    	return this.pathMaintanceDownload;    	
    }
    
    private void setPathExecutives(String path){
    	this.pathExecutives = path;
    }
    public String getPathExecutives(){
    	return this.pathExecutives;    	
    } 
    
    /**
	 * @return the pathReportes
	 */
	public String getPathReportes() {
		return pathReportes;
	}

	/**
	 * @param pathReportes the pathReportes to set
	 */
	public void setPathReportes(String pathReportes) {
		this.pathReportes = pathReportes;
	}


	/**
	 * @return the pathExecutiveBranch
	 */
	public String getPathExecutiveBranch() {
		return pathExecutiveBranch;
	}

	/**
	 * @param pathExecutiveBranch the pathExecutiveBranch to set
	 */
	public void setPathExecutiveBranch(String pathExecutiveBranch) {
		this.pathExecutiveBranch = pathExecutiveBranch;
	}
	
	

	/**
	 * @return the pathCommision
	 */
	public String getPathCommision() {
		return pathCommision;
	}

	/**
	 * @param pathCommision the pathCommision to set
	 */
	public void setPathCommision(String pathCommision) {
		this.pathCommision = pathCommision;
	}
	public String getHostAltas() {
		return hostAltas;
	}

	public void setHostAltas(String hostAltas) {
		this.hostAltas = hostAltas;
	}

	public String getUserAltas() {
		return userAltas;
	}

	public void setUserAltas(String userAltas) {
		this.userAltas = userAltas;
	}

	public String getPassAltas() {
		return passAltas;
	}

	public void setPassAltas(String passAltas) {
		this.passAltas = passAltas;
	}

	public String getPathAltas() {
		return pathAltas;
	}

	public void setPathAltas(String pathAltas) {
		this.pathAltas = pathAltas;
	}
	
	public String getMttoTPV() {
		return mttoTPV;
	}

	public String getPathUploadAdquirente() {
		return pathUploadAdquirente;
	}

	public void setPathUploadAdquirente(String pathUploadAdquirente) {
		this.pathUploadAdquirente = pathUploadAdquirente;
	}

	public String getPathCuentasMujerPyME() {
		return pathCuentasMujerPyME;
	}

	public void setPathCuentasMujerPyME(String pathCuentasMujerPyME) {
		this.pathCuentasMujerPyME = pathCuentasMujerPyME;
	}

	public void setMttoTPV(String mttoTPV) {
		this.mttoTPV = mttoTPV;
	}

	public String getPathUploadAdquirentReporteDaily() {
		return pathUploadAdquirentReporteDaily;
	}

	public void setPathUploadAdquirentReporteDaily(String pathUploadAdquirentReporteDaily) {
		this.pathUploadAdquirentReporteDaily = pathUploadAdquirentReporteDaily;
	}

	private void loadProps() { 
        try {
            is = this.getClass().getResourceAsStream("/com/banorte/contract/resources/operations.properties");
            Properties props = new Properties();
            props.load(is);
            is.close();
            
            this.setHost(props.getProperty("contract.ftp.host"));
            this.setPort(props.getProperty("contract.ftp.port"));
            this.setUser(props.getProperty("contract.ftp.user"));
            this.setPass(props.getProperty("contract.ftp.pass"));
            this.setPathUpload(props.getProperty("contract.ftp.path.upload"));
            this.setPathReportes(props.getProperty("contract.ftp.path.reportes"));
            this.setPathDownload(props.getProperty("contract.ftp.path.download"));
            this.setPathActivate(props.getProperty("contract.ftp.path.activate"));
            this.setPathInstall(props.getProperty("contract.ftp.path.install"));
            this.setPathOIP(props.getProperty("contract.ftp.path.oip"));
            this.setPathCommision(props.getProperty("contract.ftp.path.commision"));
            
            this.setPathExecutives(props.getProperty("contract.ftp.path.executives"));
            this.setPathExecutiveBranch(props.getProperty("contract.ftp.path.executivebranch"));

            this.setPathDirectaDownload(props.getProperty("contract.ftp.path.directa.download"));
            this.setPathDirectaUpload(props.getProperty("contract.ftp.path.directa.upload"));
            this.setPathMaintanceDownload(props.getProperty("contract.ftp.path.maintance.download"));
            this.setSFTPHost(props.getProperty("contract.sftp.host"));
            this.setSFTPPort(props.getProperty("contract.sftp.port"));
            this.setSFTPUser(props.getProperty("contract.sftp.user"));
            this.setSFTPPass(props.getProperty("contract.sftp.pass"));
            this.setSFTPPathUploadABEM(props.getProperty("contract.sftp.path.upload.abem"));
            this.setSFTPPathUploadMBEM(props.getProperty("contract.sftp.path.upload.mbem"));
            //para reportes de altas
            hostAltas=props.getProperty("contract.altas.host");
            passAltas=props.getProperty("contract.altas.pass");
            userAltas=props.getProperty("contract.altas.user");
            pathAltas=props.getProperty("contract.altas.path");
            
            this.mttoTPV = props.getProperty("contract.ftp.path.mttoTPV");
            
            this.setPathUploadAdquirente(props.getProperty("contract.ftp.path.upload.adquirente"));
            this.setPathCuentasMujerPyME(props.getProperty("contract.ftp.path.upload.cuentasMujerPyME"));
            this.setPathUploadAdquirentReporteDaily(props.getProperty("contract.ftp.path.upload.adquirentReportDaily"));

        } catch ( IOException e ) { 
            
        }finally{
			if (null != is) {
				safeClose(is);
			}
		}
    }
	
	public static void safeClose(InputStream is) {
    	if (is != null) {
    		try {
    			is.close();
    		} catch (IOException e) {
    			
    		}
    	}
    }
    
}
