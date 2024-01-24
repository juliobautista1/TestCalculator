package com.banorte.contract.util;



public enum PlanType {
    PLAN10(1),//plan 15
    PLAN30(2),//plan 35
    PLAN70(3),//ya no existe
    PLAN150(4),//plan 150
    RENTABILIDAD(5),
    OTRO(6),
    PLAN500(7),//plan500
    IXECOM1(8),
    IXECOM25(9),
    IXECOM35(10),
    IXECOM50(11),
    IXECOM100(12),
    IXECOMORO(13),
    IXECOMPLATINO(14),
    BANORTESINEXIGENCIAS(15),
    BANORTEPLAN15(16),
    BANORTEPLAN35(17), 
    BANORTEPLAN75(18),
    BANORTEPLAN200(19),
    IXESINEXIGENCIAS(20),
    IXEPLAN15(21),
    IXEPLAN35(22),
    IXEPLAN75(23),
    IXEPLAN200(24);
    private final Integer id;   // in productId
    public Integer value()   { return id; }
 
    PlanType(Integer id) {
        this.id = id;
    }
    
}