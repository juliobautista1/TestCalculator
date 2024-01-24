package com.banorte.contract.util;

import java.util.Comparator;
import java.util.Date;

import com.banorte.contract.model.Bitacora;

public class BitacoraComparator implements Comparator<Bitacora>{

	@Override
	public int compare(Bitacora o1, Bitacora o2) {
		String folio1 = o1.getFolio()==null?"":o1.getFolio();
		String folio2 = o2.getFolio()==null?"":o2.getFolio();
		Date fecha1 = o1.getCreationDate();
		Date fecha2 = o2.getCreationDate();
		
		int result = fecha1.compareTo(fecha2);
		if(result!=0){
		return result;
		}
		
		return folio1.compareToIgnoreCase(folio2);
	}

}
