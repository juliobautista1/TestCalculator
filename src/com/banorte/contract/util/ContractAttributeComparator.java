package com.banorte.contract.util;

import java.util.Comparator;

import com.banorte.contract.model.ContractAttribute;

public class ContractAttributeComparator implements Comparator<ContractAttribute>{

	@Override
	public int compare(ContractAttribute o1, ContractAttribute o2) {

		String a1 = o1.getAttribute().getFieldname();
		String a2 = o2.getAttribute().getFieldname();
		int result = a1.compareToIgnoreCase(a2);
		return result;
	}

}
