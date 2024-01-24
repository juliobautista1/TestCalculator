package com.banorte.contract.layout.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.banorte.contract.dto.ContractTpvDTO;
import com.banorte.contract.util.EncryptBd;

public class TpvDTOHandler extends RowHandler<ContractTpvDTO>{

	public EncryptBd encryptBd = new EncryptBd();
	public String[] headers={
		"CR",
		"AFILIACION_PESOS",
		"AFILIACION_DLLS",
		"CTACHEQUES_PESOS",
		"CTACHEQUES_DLLS",
		"RAZONSOCIAL",
		"NOMBRECOMERCIO",
		"FECHAALTA",
		"TIPOPRODUCTO",
		"SIC",
		"EJECUTIVO_COLOCO"
	};
	@Override
	public List<String> getHeaders() {
		return Arrays.asList(headers);
	}

	@Override
	public String getLayoutName() {
		return "TPV_ALTAS";
	}

	@Override
	public List<String> castObject(ContractTpvDTO obj) {
		List<String>row=new ArrayList<String>();
		if(obj!=null){
			String cr=obj.getCr();
			String afiliacion=obj.getAfiliacion()!=null?obj.getAfiliacion():"";
			String afiliacionDlls=obj.getAfiliacionDlls()!=null?obj.getAfiliacionDlls():"";
			String ctaCheques=encryptBd.decrypt(obj.getCtaCheques());
			String ctaChequesDlls=encryptBd.decrypt(obj.getCtaChequesDlls());
			String razonSocial=obj.getRazonSocial()!=null?obj.getRazonSocial():"";
			String nombreComercio=obj.getNombreComercio()!=null?obj.getNombreComercio():"";
			String fechaAlta=obj.getFechaAlta()!=null?obj.getFechaAlta():"";
			String tipoProducto=obj.getTipoProducto()!=null?obj.getTipoProducto():"";
			String sic=obj.getSic()!=null?obj.getSic():"";
			String ejecutivoColoco=obj.getEjecutivoColoco()!=null?obj.getEjecutivoColoco():"";
			row.add(cr);
			row.add(afiliacion);
			row.add(afiliacionDlls);
			row.add(ctaCheques);
			row.add(ctaChequesDlls);
			row.add(razonSocial);
			row.add(nombreComercio);
			row.add(fechaAlta);
			row.add(tipoProducto);
			row.add(sic);
			row.add(ejecutivoColoco);
		}
		return row;
	}

}
