package com.banorte.contract.layout.handlers;

import java.util.ArrayList;
import java.util.List;

public abstract class RowHandler<X> {
	
	/**
	 * The sort of the list must be the same as the castObject
	 * @return the list of headers to display in the report
	 */
	public abstract List<String> getHeaders();


	/**
	 * 
	 * @return the report name
	 */
	public abstract String getLayoutName();
	
	
	/**
	 * Cast the object to a specific type
	 * @param obj
	 * @return a list of strings of the converted object
	 */
	public abstract List<String> castObject(X obj);

	
	/**
	 * Cast all the objects to a list of lists of strings
	 * @param list (the list of a specific object)
	 * @return the list of lists of strings
	 */
	public List<List<String>> castAllObjects(List<X> list) {
		List<List<String>> data = new ArrayList<List<String>>();
		for (X o : list) {
			List<String> row = castObject(o);
			data.add(row);
		}
		return data;
	}
}
