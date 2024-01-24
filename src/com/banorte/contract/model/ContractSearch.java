/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banorte.contract.model;

import java.util.Date;

/**
 *
 * @author Darvy Arch
 */
public class ContractSearch {
    private boolean enable;
    private Contract contract;
    private String operationComments;
    private String emitter;//Afiliacion=Adquiriente, Emisora=BEM y Nomina
    private boolean editContract;
    private boolean cancelContract;
    private String navigationUrl;
    private Date formalDate;
    private String product;
    private String fiscalName;
    private boolean activate;
    private boolean activate2user;
    private String statusDetail;
    private boolean toOutboundReport;
    private String outboundReportUrl;
    private boolean mtto;
   
    public String getStatusDetail() {
		return statusDetail;
	}

	public void setStatusDetail(String statusDetail) {
		this.statusDetail = statusDetail;
	}
	public boolean getActivate() {
		return activate;
	}

	public void setActivate(boolean activate) {
		this.activate = activate;
	}
    public boolean getActivate2user() {
		return activate2user;
	}

	public void setActivate2user(boolean activate2user) {
		this.activate2user = activate2user;
	}
	public String getEmitter() {
        return emitter;
    }

    public void setEmitter(String emitter) {
        this.emitter = emitter;
    }

    public String getOperationComments() {
        return operationComments;
    }

    public void setOperationComments(String operationComments) {
        this.operationComments = operationComments;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean getEditContract() {
        return editContract;
    }

    public void setEditContract(boolean editContract) {
        this.editContract = editContract;
    }

    public boolean getCancelContract() {
        return cancelContract;
    }

    public void setCancelContract(boolean cancelContract) {
        this.cancelContract = cancelContract;
    }

    public String getNavigationUrl() {
        return navigationUrl;
    }

    public void setNavigationUrl(String navigationUrl) {
        this.navigationUrl = navigationUrl;
    }

    public Date getFormalDate() {
        return formalDate;
    }

    public void setFormalDate(Date formalDate) {
        this.formalDate = formalDate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getFiscalName() {
        return fiscalName;
    }

    public void setFiscalName(String fiscalName) {
        this.fiscalName = fiscalName;
    }


    //property ToOutboundReport
    public void setToOutboundReport(boolean toOutboundReport) {
        this.toOutboundReport = toOutboundReport;
    }
    public boolean getToOutboundReport() {
        return this.toOutboundReport;
    }

    //property outboundReportUrl
    public void setOutboundReportUrl(String url) {
        this.outboundReportUrl = url;
    }
    public String getOutboundReportUrl() {
        return this.outboundReportUrl;
    }

    
    public String getPrueba() {
        return "Texto de prueba";
    }

	/**
	 * @return the mtto
	 */
	public boolean isMtto() {
		return mtto;
	}

	/**
	 * @param mtto the mtto to set
	 */
	public void setMtto(boolean mtto) {
		this.mtto = mtto;
	}

    
    
    
    
}
