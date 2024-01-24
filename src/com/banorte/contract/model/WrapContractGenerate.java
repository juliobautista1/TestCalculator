/**
 * 
 */
package com.banorte.contract.model;

import java.util.ArrayList;
import java.util.List;

import com.banorte.contract.util.ApplicationConstants;

/**
 * @author omar
 *
 */
public class WrapContractGenerate {
	
	private List<LoadFile>	loadedFileInfo  = new ArrayList<LoadFile>() ;
	
	
	private List<FailReference>	listFailReference  = new ArrayList<FailReference>() ;
	
	private String contractTypeName;


	/**
	 * @return the loadedFileInfo
	 */
	public List<LoadFile> getLoadedFileInfo() {
		if(loadedFileInfo == null){
			loadedFileInfo = new ArrayList<LoadFile>();
		}
		return loadedFileInfo;
	}
	
	/**
	 * Agrega todos los elementos de la lista
	 * @param loadedFileInfo
	 */
	public void addAllLoadedFileInfo(List<LoadFile> loadedFileInfo){
		this.loadedFileInfo.addAll(loadedFileInfo);
	}


	/**
	 * @param loadedFileInfo the loadedFileInfo to set
	 */
	public void setLoadedFileInfo(List<LoadFile> loadedFileInfo) {
		this.loadedFileInfo = loadedFileInfo;
	}


	/**
	 * @return the listFailReference
	 */
	public List<FailReference> getListFailReference() {
		if(listFailReference == null){
			listFailReference = new ArrayList<FailReference>();
		}
		return listFailReference;
	}


	/**
	 * @param listFailReference the listFailReference to set
	 */
	public void setListFailReference(List<FailReference> listFailReference) {
		this.listFailReference = listFailReference;
	}


	/**
	 * @return the contractTypeName
	 */
	public String getContractTypeName() {
		return contractTypeName;
	}


	/**
	 * @param contractTypeName the contractTypeName to set
	 */
	public void setContractTypeName(String contractTypeName) {
		this.contractTypeName = contractTypeName;
	}
	
	public boolean isLoadedFileInfoEmpty(){
		if(loadedFileInfo != null){
			if(loadedFileInfo.size() > ApplicationConstants.VALUE_0 ){
				return Boolean.FALSE;
			}else{
				return Boolean.TRUE;
			}
		}else{
			return Boolean.TRUE;
		}
	}
	
	

}
