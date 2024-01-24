package com.banorte.contract.util;



public enum DirectaStatus {
    CE_CUENTAS_CONFIRMADAS(0),
    CE_CUENTAS_NO_CONFIRMADAS(1),
    CE_CUENTAS_EN_REINTENTO(2),
    CE_DESCONFIA(3),
    CNE_NO_SE_ENCUENTRA(10),
    CNE_CLIENTE_OCUPADO(11),
    CNE_NO_TRABAJA_AHI(12),
    CNE_ILOCALIZABLE(13),
    SC_FAX(20),
    SC_GRABADORA(21),
    SC_NO_CONTESTAN(22),
    SC_TEL_OCUPADO(23),
    SC_TEL_NO_EXISTE(24),
    SC_TEL_SUSPENDIDO(25),
    SC_TEL_INCORRECTO(26),
    SC_REQUIERE_EXTENSION(27),
    SC_SIN_RESULTADO(28),
    HEB_PROCESADO(50),
    HEB_ENVIADO_DIRECTA(51);
    private final Integer id;   // in productId
    public Integer value()   { return id; }
 
    DirectaStatus(Integer id) {
        this.id = id;
    }
    
}