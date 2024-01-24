package com.banorte.contract.util;



public enum EquipmentType {
    DIALUP(1),
    LAN_INTERNET(2),
    PINPAD(3),
    GPRS_CDMA(4),
    WIFI(5),
    BLUETOOTH(6),
    TRANSCRIPTOR(7),
    FIXED(8),
    MOBILE(9);
    
    private final Integer id;   // in productId
    public Integer value()   { return id; }
 
    EquipmentType(Integer id) {
        this.id = id;
    }
    
}