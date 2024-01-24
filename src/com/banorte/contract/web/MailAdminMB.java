/**
 * 
 */
package com.banorte.contract.web;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;



import com.banorte.contract.business.RecipentMailRemote;
import com.banorte.contract.model.RecipentMail;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.RecipientMailComparator;

/**
 * @author omar
 *
 */
public class MailAdminMB {
	
	private String mail;
	
	private String productType;
	
	private List<RecipentMail> listRecipentMail;
	
	protected RecipentMailRemote recipentMailBean;

	/**
	 * 
	 */
	public MailAdminMB() {
		if (recipentMailBean == null) {
			recipentMailBean = (RecipentMailRemote) EjbInstanceManager.getEJB(RecipentMailRemote.class);
		}
		
		initInfo();
	}
	
	private void initInfo(){
		
		listRecipentMail 	= recipentMailBean.getAllRecipentMail();
		Collections.sort(listRecipentMail, new RecipientMailComparator());
		mail = ApplicationConstants.EMPTY_STRING;
		productType = ApplicationConstants.EMPTY_STRING;
		
	}
		
	
	
	public void deleteRecipentMail(ActionEvent actionEvent){
		
		RecipentMail recipentDelete 	= new RecipentMail();
		String recipentMailId 			= FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("recipentMailId");
		
		try {
			for(RecipentMail recipentMail:this.listRecipentMail){
				if(recipentMail.getRecipentMailId().equals(Integer.parseInt(recipentMailId) ) ){
					recipentDelete = recipentMail;
					break;
				}
			}
			
			recipentMailBean.delete(recipentDelete);
			initInfo();
			FacesContext.getCurrentInstance().getExternalContext().redirect("mailAdmin.jsf");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void saveRecipentMail(ActionEvent actionEvent){
		
		RecipentMail recipentMail 	= new RecipentMail();
		
		try{
			recipentMail.setProductTypeId(Integer.parseInt( this.productType) );
			recipentMail.setRecipent(this.mail);
	
			recipentMailBean.save(recipentMail);
			
			initInfo();
			FacesContext.getCurrentInstance().getExternalContext().redirect("mailAdmin.jsf");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}



	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
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
	 * @return the listRecipentMail
	 */
	public List<RecipentMail> getListRecipentMail() {
		return listRecipentMail;
	}



	/**
	 * @param listRecipentMail the listRecipentMail to set
	 */
	public void setListRecipentMail(List<RecipentMail> listRecipentMail) {
		this.listRecipentMail = listRecipentMail;
	}
	
	
	public void harvest(){
		System.out.println("metodo para crear peso en harvest");
	}
	

}
