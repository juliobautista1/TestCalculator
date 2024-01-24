/**
 * 
 */
package com.banorte.contract.util;

import java.util.Date;
/**
 * @author omar
 *
 */
public class ResultMonthLoadFile {
	
	
	private String fileType;
	
	private String productType;
	
	private Integer numberSuccess;
	
	private Integer numberFail;
	
	private Integer numberGenerate;
	
	private Integer numberRead;   // Respuestas leidas , READ! 
	
	private Integer total;
	

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return the numberSuccess
	 */
	public Integer getNumberSuccess() {
		return numberSuccess;
	}

	/**
	 * @param numberSuccess the numberSuccess to set
	 */
	public void setNumberSuccess(Integer numberSuccess) {
		this.numberSuccess = numberSuccess;
	}

	/**
	 * @return the numberFail
	 */
	public Integer getNumberFail() {
		return numberFail;
	}

	/**
	 * @param numberFail the numberFail to set
	 */
	public void setNumberFail(Integer numberFail) {
		this.numberFail = numberFail;
	}

	/**
	 * @return the numberGenerate
	 */
	public Integer getNumberGenerate() {
		
		if(fileType.equals(ApplicationConstants.FILE_TYPE_ALTAS)){
			numberGenerate = this.numberSuccess + this.numberFail;
		}else{
			numberGenerate = ApplicationConstants.VALUE_0;
		}
		
		return numberGenerate;
	}

	/**
	 * @param numberGenerate the numberGenerate to set
	 */
	public void setNumberGenerate(Integer numberGenerate) {
		this.numberGenerate = numberGenerate;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		total = this.numberSuccess + this.numberFail;
		
		return total;
	}

	 	
	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}


	
	public void addSuccess(Integer success){
		if(this.numberSuccess == null){
			this.numberSuccess = 0;
		}
		this.numberSuccess = this.numberSuccess + success;
	}

	public void addFail(Integer fail){
		if(this.numberFail == null){
			this.numberFail = 0;
		}
		this.numberFail = this.numberFail + fail;
	}

	/**
	 * @return the numberRead
	 */
	public Integer getNumberRead() {
		
		if(this.numberRead == null){
			this.numberRead = 0;
		}
		if(fileType.equals(ApplicationConstants.FILE_TYPE_RESPUESTA)){
			numberRead = this.numberSuccess + this.numberFail;
		}else{
			numberRead = ApplicationConstants.VALUE_0;
		}
		
		return numberRead;
	}

	/**
	 * @param numberRead the numberRead to set
	 */
	public void setNumberRead(Integer numberRead) {
		this.numberRead = numberRead;
	}

	

}
