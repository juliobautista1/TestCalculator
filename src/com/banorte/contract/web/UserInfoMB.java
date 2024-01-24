package com.banorte.contract.web;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.banorte.contract.util.ApplicationConstants;

/**
 *
 * @author Darvy Arch
 */
public class UserInfoMB {

    private String userName 	= ApplicationConstants.EMPTY_STRING;
    private String fullName 	= ApplicationConstants.EMPTY_STRING;
    private String roleOblix 	= ApplicationConstants.EMPTY_STRING;
    private String userProfile = "";

    public UserInfoMB() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) (facesContext.getExternalContext().getSession(true));
        Map<String, String> userSession = (HashMap) session.getAttribute(ApplicationConstants.PROFILE);
  
        	this.setUserName(userSession.get(ApplicationConstants.UID));
            this.setFullName(userSession.get(ApplicationConstants.UNAME));
            this.setRoleOblix(userSession.get(ApplicationConstants.ROLE_OBLIX));
            this.setUserProfile(userSession.get("userprofile"));
    }

    public String getFullName() {
        return fullName;
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

	/**
	 * @return the roleOblix
	 */
	public String getRoleOblix() {
		return roleOblix;
	}

	/**
	 * @param roleOblix the roleOblix to set
	 */
	public void setRoleOblix(String roleOblix) {
		this.roleOblix = roleOblix;
	}

	public String getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(String userProfile) {
		this.userProfile = userProfile;
	}
    
    
    
    
}
