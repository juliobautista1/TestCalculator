package com.banorte.contract.web;


import com.banorte.contract.util.Formatter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.banorte.contract.business.ContractRemote;
import com.banorte.contract.business.MaintanceRemote;

public abstract class ReportMB {


	
	public static final String SEARCH = "SEARCH";
	public static final String SUCCESS = "SUCCESS";

	protected MaintanceRemote maintanceBean;
	protected ContractRemote contractBean;



    //selectedReport property
	private String selectedReport;
	public void setSelectedReport(String type){
		this.selectedReport =type;
	}
	public String getSelectedReport(){
		return this.selectedReport;
	}
	public ReportMB() {

		if (maintanceBean == null) {
			maintanceBean = (MaintanceRemote) EjbInstanceManager.getEJB(MaintanceRemote.class);
		}
		if (contractBean == null) {
			contractBean = (ContractRemote) EjbInstanceManager.getEJB(ContractRemote.class);
		}

	}

	public abstract String getReportResult();
	
	public String getFormatedDate(Date date) {
		Formatter format = new Formatter();
		return format.formatDateToString(date);
	}


	public void reportChanged(ValueChangeEvent event) {
		
		String selectedValue = event.getNewValue().toString();
		String url ="";
		
		if(selectedValue.equals("0"))
			url = "contractReport.jsf";
		else
			url = "outboundReport.jsf";
			
		try {
			
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
			
			//reset selection
			this.selectedReport ="";
			
		} catch (IOException e) {
			e.printStackTrace();
		}			
		
	}

}
