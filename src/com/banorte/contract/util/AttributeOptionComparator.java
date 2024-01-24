package com.banorte.contract.util;

import java.util.Comparator;

import com.banorte.contract.model.AttributeOption;

public class AttributeOptionComparator implements Comparator<AttributeOption>{

	@Override
	public int compare(AttributeOption o1, AttributeOption o2) {
		
		Integer id1 = o1.getAttributeOptionPK().getOptionid();
		Integer id2 = o2.getAttributeOptionPK().getOptionid();
		int result = id1.compareTo(id2);
		return result;
	}

}
