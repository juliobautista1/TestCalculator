/**
 * 
 */
package com.banorte.contract.model;

import com.banorte.contract.util.ApplicationConstants;

/**
 * @author omar
 *
 */
public class CounterRecord {
	
	private Integer success;
	
	private Integer fail;

	/**
	 * @return the success
	 */
	public Integer getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Integer success) {
		this.success = success;
	}

	/**
	 * @return the fail
	 */
	public Integer getFail() {
		return fail;
	}

	/**
	 * @param fail the fail to set
	 */
	public void setFail(Integer fail) {
		this.fail = fail;
	}
	
	
	public void addOneSuccess(){
		if(this.success == null){
			this.success = ApplicationConstants.VALUE_0;
		}
		this.success = this.success + 1;
	}
	
	

}
