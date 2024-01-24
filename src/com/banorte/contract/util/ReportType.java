package com.banorte.contract.util;



public enum ReportType {
    ALL_PRODUCTS(0),
    ADQUIRENTE(1),
    SIP(2),
    NOMINA(3);
    private final Integer id;   // in productId
    public Integer value()   { return id; }
 
    ReportType(Integer id) {
        this.id = id;
    }
    
}