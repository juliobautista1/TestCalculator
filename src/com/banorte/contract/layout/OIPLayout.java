package com.banorte.contract.layout;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.banorte.contract.model.Affiliation;
import com.banorte.contract.model.Client;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.Maintance;
import com.banorte.contract.util.EncryptBd;
import com.banorte.contract.util.Formatter;

public class OIPLayout extends LayoutOperationsTemplate implements Layout {

	private final Integer OIP = 6;
	private final String FILLS[] = { "installdate" };

	public OIPLayout(List<Contract> search) {
		for (Contract record : search) {
			if (record.getProduct().getProductid().intValue() == this.OIP) {
				this.setContracts(record);
			}
		}
	}

	@Override
	public List<String> getContent(Contract contract) {
		List<String> list = new ArrayList();
		Collection<Affiliation> affiliation = contract.getAffiliationCollection(); // affiliation
		Client client = contract.getClient();
		Map<String, String> map = new LayoutTempleteContract().bindFrom(contract, FILLS);

		Affiliation affi = null;
		if (affiliation.size() > 0) {
			affi = affiliation.iterator().next();
		} else {
			affi = new Affiliation();
		}
		list.add(client.getSic().toString());
		list.add(affi.getNumaffilmn() != null ? affi.getNumaffilmn(): "000000");

		String installdate = map.get("installdate") != null ? map.get("installdate") : "";

		list.add(installdate.length() > 10 ? installdate.substring(0, 10): installdate);

		return list;
	}

	@Override
	public List<String> getHeader() {
		List<String> list = new ArrayList();
		list.add("NumCliente"); // Numero de Cliente
		list.add("Afiliacion"); // Afiliacion
		list.add("FechaInstalacion"); // Fecha de Instalacion

		return list;
	}

	@Override
	public boolean hasElements() {
		if (this.getContracts().size() > 0) {
			return true;
		} else
			return false;
	}

	public void createTxtLayout(String productName, boolean saveLocalFile) {

		List<List<String>> result = new ArrayList<List<String>>();

		// Iniciamos Generacion del Contenido en base al Contrato
		for (Contract contract : this.getContracts()) {
			result.add(getTxtContent(contract));
		}

		byte[] contentLayout = this.getContentTxtLayout(productName, "", result);

		if (contentLayout!=null && contentLayout.length > 0)
			this.saveRemoteFile(productName, contentLayout, saveLocalFile);

	}

	private List<String> getTxtContent(Contract contract) {

		List<String> list = new ArrayList<String>();
		Formatter formatter = new Formatter();

		Collection<Affiliation> affiliation = contract.getAffiliationCollection(); // affiliation
		Client client = contract.getClient();
		Map<String, String> map = new LayoutTempleteContract().bindFrom(contract, FILLS);

		Affiliation affi = null;
		if (affiliation.size() > 0) {
			affi = affiliation.iterator().next();
		} else {
			affi = new Affiliation();
		}

		String numAffiliation = affi.getNumaffilmn() != null ? affi.getNumaffilmn() : "000000";
		String installdate = map.get("installdate") != null ? map.get("installdate") : "";
		installdate = installdate.length() > 10 ? installdate.substring(0, 10): installdate;

		list.add(Formatter.fixLenght(client.getSic().toString(), 8));
		list.add(Formatter.fixLenght(numAffiliation, 10));
		list.add(installdate != "" ? formatter.formatDateToStringOIP(formatter.formatDate(installdate).getTime()) : "0000-00-00");

		return list;
	}

}
