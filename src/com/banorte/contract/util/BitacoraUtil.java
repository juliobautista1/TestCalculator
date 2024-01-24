/**
 * 
 */
package com.banorte.contract.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.banorte.contract.business.BitacoraRemote;
import com.banorte.contract.business.ContractBean;
import com.banorte.contract.business.ContractRemote;
import com.banorte.contract.business.ProductTypeBean;
import com.banorte.contract.business.ProductTypeRemote;
import com.banorte.contract.model.Bitacora;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.Product;
import com.banorte.contract.model.ProductType;
import com.banorte.contract.web.EjbInstanceManager;

/**
 * @author omar
 *
 */
public class BitacoraUtil {
	
	private static BitacoraUtil instance;
	private BitacoraRemote bitacoraBean;
	
	private ContractRemote contractBean;
	private ProductTypeRemote productTypeBean;

	
 
    public static BitacoraUtil getInstance() {
    	if (instance == null){
    		instance = new BitacoraUtil();
    		}
        return instance;
    }
    
	@SuppressWarnings("unchecked")
	public static String getUserNumberSession(FacesContext ctx) {
		String userNumber = ApplicationConstants.EMPTY_STRING;
		Map<String, String> userSession = null;
		HttpSession session = null;
		session = (HttpSession) (ctx.getExternalContext().getSession(true));
		userSession = (Map<String, String>) session.getAttribute(ApplicationConstants.PROFILE);
		userNumber = userSession.get(ApplicationConstants.UID);
		return userNumber;
	}
	
	

	/**
	 * @param bitacoraBean
	 */
	public BitacoraUtil() {
		super();
		if (bitacoraBean == null) {
			bitacoraBean = (BitacoraRemote) EjbInstanceManager.getEJB(BitacoraRemote.class);
		}
		if(contractBean==null){
			contractBean = (ContractRemote) EjbInstanceManager.getEJB(ContractRemote.class);
		}
		if(productTypeBean==null){
			productTypeBean = (ProductTypeRemote) EjbInstanceManager.getEJB(ProductTypeRemote.class);
		}

	}
	
	
	public void saveBitacoraGeneral(String description,String userNumber){
		if(!userNumber.equals(null)){
				Bitacora bitacora 	= new Bitacora();
				Date creationDate 	= new Date( System.currentTimeMillis() );
				bitacora.setCreationDate(creationDate);
				bitacora.setDescription(description);
				bitacora.setUserNumber(userNumber);
				bitacoraBean.save(bitacora);
		}
	}
	
	public void saveBitacoraFolio(String description,String userNumber,String folio, String folioVersion){
		
		if(!userNumber.equals(null)){
				Bitacora bitacora 	= new Bitacora();
				Date creationDate 	= new Date( System.currentTimeMillis() );
				
				bitacora.setCreationDate(creationDate);
				bitacora.setDescription(description);
				bitacora.setUserNumber(userNumber);
				bitacora.setFolio(folio);
				bitacora.setFolioVersion(folioVersion);
				
				loadBitacoraProduct(bitacora, folio);
				bitacoraBean.save(bitacora);
		}
		
	}
	
	public void saveBitacoraSIC(String description,String userNumber,String sic,String folio, String folioVersion){
		
		if(!userNumber.equals(null)){
			if(!userNumber.equals(ApplicationConstants.EMPTY_STRING)){
		
				Bitacora bitacora 	= new Bitacora();
				Date creationDate 	= new Date( System.currentTimeMillis() );
				
				bitacora.setCreationDate(creationDate);
				bitacora.setDescription(description);
				bitacora.setUserNumber(userNumber);
				bitacora.setSic(sic);
				bitacora.setFolio(folio);
				bitacora.setFolioVersion(folioVersion);
				
				loadBitacoraProduct(bitacora, folio);
				bitacoraBean.save(bitacora);
			}
		}		
	}
	
	public void saveBitacoraNumberBussines(String description,String userNumber,String folio,String folioVersion,String numberBussines){
		
		if(!userNumber.equals(null)){
			if(!userNumber.equals(ApplicationConstants.EMPTY_STRING)){
		
				Bitacora bitacora 	= new Bitacora();
				Date creationDate 	= new Date( System.currentTimeMillis() );
				
				bitacora.setCreationDate(creationDate);
				bitacora.setDescription(description);
				bitacora.setUserNumber(userNumber);
				bitacora.setFolio(folio);
				bitacora.setFolioVersion(folioVersion);
				bitacora.setNumberBussines(numberBussines);
				
				loadBitacoraProduct(bitacora, folio);
				
				bitacoraBean.save(bitacora);
			}
		}
		
	}
	
	public Bitacora saveBitacoraReporteStart(String description,String userNumber){
		Bitacora bitacora 	= new Bitacora();
		Date creationDate 	= new Date( System.currentTimeMillis() );
		bitacora.setCreationDate(creationDate);
		bitacora.setDescription(description);
		bitacora.setUserNumber(userNumber);
		return bitacora;
	}
	
	public void saveBitacoraReporteEnd(Bitacora bitacora){
		String userNumber = bitacora.getUserNumber();
		if(!userNumber.equals(null)){
			if(!userNumber.equals(ApplicationConstants.EMPTY_STRING)){
				Date endDate 	= new Date( System.currentTimeMillis() );
				bitacora.setEndDate(endDate);
				bitacoraBean.save(bitacora);
			}
		}
	}
	
	public void loadBitacoraProduct(Bitacora bitacora, String folio){
		Contract contract = contractBean.findByLastVersionAndRef(folio);
		String productName = "";
		String productType = "";
		if(contract!=null){
			Product product = contract.getProduct();
			if(product!= null){
				productName = product.getName();	
			}
			Integer productTypeId = contract.getProduct().getProductTypeid().getProductTypeid();
			if(productTypeId!=null){
				ProductType productT = productTypeBean.findById(productTypeId);
				if(productT!=null){
					productType = productT.getName();
				}
			}
			bitacora.setProduct(productName);
			bitacora.setProducttype(productType);
		}
		
	}
	
	/**
	 * Save the info sent in bitacora object
	 * @param bitacora 
	 */
	public void saveBitacora(Bitacora bitacora){
		bitacoraBean.save(bitacora);
	}
	
	

}
