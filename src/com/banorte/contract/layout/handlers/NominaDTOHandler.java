package com.banorte.contract.layout.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.banorte.contract.dto.ContractPayrollDTO;
import com.banorte.contract.util.EncryptBd;

public class NominaDTOHandler extends RowHandler<ContractPayrollDTO>{

	public EncryptBd encriptBd = new EncryptBd();
	public String[] headers={
			"CR",
			"NUMEMPRESA",
			"EMPRESA",
			"CTACHEQUES",
			"FECHAALTA",
			"SIC",
			"EJECUTIVO_COLOCO"
	};
	@Override
	public List<String> getHeaders() {
		return Arrays.asList(headers);
	}

	@Override
	public String getLayoutName() {
		return "NOMINA_ALTAS";
	}

	@Override
	public List<String> castObject(ContractPayrollDTO obj) {
		List<String> row=new ArrayList<String>();
		if(obj!=null){
			String cr=obj.getCr();
			String numEmpresa=obj.getNumEmpresa()!=null?obj.getNumEmpresa():"";
			String empresa=obj.getEmpresa()!=null?obj.getEmpresa():"";
			String cuenta=obj.getCtaCheques();
			String ctaCheques=encriptBd.decrypt(cuenta);
			String fechaAlta=obj.getFechaAlta()!=null?obj.getFechaAlta():"";
			String sic=obj.getSic()!=null?obj.getSic():"";
			String ejecutivoColoco=obj.getEjecutivoColoco()!=null?obj.getEjecutivoColoco():"";
			
			row.add(cr);
			row.add(numEmpresa);
			row.add(empresa);
			row.add(ctaCheques);
			row.add(fechaAlta);
			row.add(sic);
			row.add(ejecutivoColoco);
		}
		return row;
	}

}
