/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banorte.contract.layout;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Darvy Arch
 */
public class Answer {
    private String reference;
    //private Integer idStatus;   //Se convirtio en String para que reciba cualquier valor y se transforme en Integer en un validacion mas adelante
    private String idStatus;
    private String comments;
    private String noEmpresa;
    private String noAffiliationMXP;
    private String noAffiliationDLLS;
    private String rate;
    private Integer installType;
    private String installSupplier;
    private Date installDate;
    private String installCurrency;
    private String installAffiliation;
    private String installStatus;
    private List<String> listUserToken;

    public String getInstallStatus() {
		return installStatus;
	}

	public void setInstallStatus(String installStatus) {
		this.installStatus = installStatus;
	}

	public Answer() {
        this.reference = "";
        this.idStatus = null;
        this.comments = "";
        this.noEmpresa = "";
        this.noAffiliationMXP = "";
        this.noAffiliationDLLS = "";
        this.rate = "";
        this.installType = null;
        this.installSupplier = "";
        this.installDate = null;
        this.installCurrency = "";
        this.installAffiliation = "";
        this.installStatus = "";
    }    
    
    public String getInstallAffiliation() {
		return installAffiliation;
	}

	public void setInstallAffiliation(String installAffiliation) {
		this.installAffiliation = installAffiliation;
	}

	public String getInstallCurrency() {
		return installCurrency;
	}

	public void setInstallCurrency(String installCurrency) {
		this.installCurrency = installCurrency;
	}

	public Integer getInstallType() {
		return installType;
	}

	public void setInstallType(Integer installType) {
		this.installType = installType;
	}

	public String getInstallSupplier() {
		return installSupplier;
	}

	public void setInstallSupplier(String installSupplier) {
		this.installSupplier = installSupplier;
	}

	public Date getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}

	public String getNoAffiliationDLLS() {
        return noAffiliationDLLS;
    }

    public void setNoAffiliationDLLS(String noAffiliationDLLS) {
        this.noAffiliationDLLS = noAffiliationDLLS;
    }

    public String getNoAffiliationMXP() {
        return noAffiliationMXP;
    }

    public void setNoAffiliationMXP(String noAffiliationMXP) {
        this.noAffiliationMXP = noAffiliationMXP;
    }

    public String getNoEmpresa() {
        return noEmpresa;
    }

    public void setNoEmpresa(String noEmpresa) {
        this.noEmpresa = noEmpresa;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(String idStatus) {
        this.idStatus = idStatus;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRate() {
        return reference;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

	/**
	 * @return the listUserToken
	 */
	public List<String> getListUserToken() {
		return listUserToken;
	}

	/**
	 * @param listUserToken the listUserToken to set
	 */
	public void setListUserToken(List<String> listUserToken) {
		this.listUserToken = listUserToken;
	}

    
    
    
}
