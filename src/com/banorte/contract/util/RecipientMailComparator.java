package com.banorte.contract.util;

import java.util.Comparator;

import com.banorte.contract.model.RecipentMail;

public class RecipientMailComparator implements Comparator<RecipentMail>{

	@Override
	public int compare(RecipentMail o1, RecipentMail o2) {
		String a1 = o1.getProductTypeStr();
		String a2 = o2.getProductTypeStr();
		int result = a1.compareToIgnoreCase(a2);
		return result;
	}

}
