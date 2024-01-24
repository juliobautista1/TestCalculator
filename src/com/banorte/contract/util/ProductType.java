package com.banorte.contract.util;



public enum ProductType {
    BEM(1),
    NOMINA(2),
    INTERNET(3),
    TRADICIONAL(4),
    COMERCIOELECTRONICO(5),
    PYME(6),
    COBRANZA_DOMICILIADA(7),
    MTTO_ALTA_CUENTAS(8),
    MTTO_NUEVA_CONTRASENA(9),
    MTTO_REPOSICION_TOKEN(10),
    MTTO_CONVENIO_MODIFICATORIO(11),
    MTTO_DROP_COMPANY(12),
    MTTO_DROP_TOKENS(13),
    MTTO_DROP_ACCOUNT(14),
    MTTO_ADD_TOKEN(15);
    
    private final int id;   // in productId
    
    public int value()   { return id; }

    ProductType(int id) {
        this.id = id;
    }

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 * ELIMINAR SOLO FUE PARA GENERAR MAS PESO EN HARVEST
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		System.out.println("ELIMINAR SOLO FUE PARA GENERAR MAS PESO EN HARVEST");
		return super.toString();
	}
    
    
   
    
}