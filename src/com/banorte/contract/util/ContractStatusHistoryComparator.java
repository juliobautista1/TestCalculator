package com.banorte.contract.util;

import java.util.Comparator;
import java.util.Date;

import com.banorte.contract.model.ContractStatusHistory;

public class ContractStatusHistoryComparator implements Comparator<ContractStatusHistory>{

	@Override
	public int compare(ContractStatusHistory o1, ContractStatusHistory o2) {

		Date modifyDate1 = o1.getModifydate();
		Date modifyDate2 = o2.getModifydate();
		Integer result = 1;
		result = modifyDate1.compareTo(modifyDate2);
			
		
		return result;
	}


	
}
