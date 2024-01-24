/**
 * 
 */
package com.banorte.contract.util;

/**
 * @author omar
 *
 */
public enum MttoType {
	
	ALTA_CUENTAS(1,"Alta de Cuentas Propias"),
	NUEVA_CONTRASENA(2,"Nueva Contrasena"),
	REPOSICION_TOKENS(3,"Reposición de Tokens"),
	CONVENIO_MODIFICATORIO(4,"Act. Convenio Modificatorio"),
	BAJA_EMPRESA(5,"Baja de Empresa"),
	BAJA_TOKENS(6,"Baja de Tokens"),
	BAJA_CUENTAS(7,"Baja de Cuentas"),
	TOKENS_ADICIONALES(8,"Tokens Adicionales");

	
	private int mttoTypeId;
	private String description;
	
	MttoType(int mttoTypeId, String description) {
		this.mttoTypeId = mttoTypeId;
		this.description = description;
	}

	/**
	 * @return the mttoTypeId
	 */
	public int getMttoTypeId() {
		return mttoTypeId;
	}
	
	public String getMttoTypeIdStr(){
		return String.valueOf(mttoTypeId);
	}

	
	

}
