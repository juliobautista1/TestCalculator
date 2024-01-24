/**
 * 
 */
package com.banorte.contract.web;

import java.util.ArrayList;
import java.util.Calendar;

import com.banorte.contract.business.BitacoraRemote;
import com.banorte.contract.model.Bitacora;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.Formatter;


/**
 * @author omar
 *
 */
public class BitacoraMB {
	
	private BitacoraRemote bitacoraBean;

	private String startDate;
	private String endDate;
	private ArrayList<Bitacora> bitacoraList;

	public BitacoraMB() {
		super();
		if (bitacoraBean == null) {
			bitacoraBean = (BitacoraRemote) EjbInstanceManager.getEJB(BitacoraRemote.class);
		}
	}
	
	
	public String loadBitacora(){
		String result 				= ApplicationConstants.EMPTY_STRING;
		Calendar startReportDate 	= new Formatter().formatDate(this.startDate);
		Calendar endReportDate 		= new Formatter().formatDate(this.endDate);
		bitacoraList = (ArrayList<Bitacora>) bitacoraBean.findBitacora(startReportDate, endReportDate);
		result = ApplicationConstants.OUTCOME_BITACORA;
		return result ;
	}
	
	
	
	
	
//Setters & Getters
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public ArrayList<Bitacora> getBitacoraList() {
		return bitacoraList;
	}


	public void setBitacoraList(ArrayList<Bitacora> bitacoraList) {
		this.bitacoraList = bitacoraList;
	}

}
