package com.banorte.contract.layout.handlers;

import java.util.Arrays;
import java.util.List;

public class NominaHandler extends RowHandler<String>{
	public String[]headers={
			"Folio",
			"Cuenta encriptada",
			"Cuenta",
			"No Formalizo",
			"Formalizo",
			"No Coformalizo",
			"Coformalizo"
			};

	@Override
	public List<String> getHeaders() {
		return Arrays.asList(headers);
	}

	@Override
	public String getLayoutName() {
		return "InfoReportNomina";
	}

	@Override
	public List<String> castObject(String obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
