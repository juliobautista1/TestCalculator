package com.banorte.contract.layout.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.banorte.contract.dto.ContractAcquirerReportStatusDTO;

public class AcquirerDailyStatusDTOHandler extends RowHandler<ContractAcquirerReportStatusDTO>{

	public String[] headers={
			"FOLIO",
			"CONTRACTID",
			"PRODUCTO",
			"PRODUCTODESCRIPCION",
			"VERSION",
			"STATUSID",
			"ESTATUSNOMBRE",
			"ESTATUSDESCRIPCION",
			"ESTATUSACTUAL",
			"ESTATUSACTUALDESCRIPCION",
			"FECHAULTIMAMODIFICACION",
			"FECHACREACION",
			"COMENTARIOS",
			"NUMEROEMPLEADO",
			"NOMBREEMPLEADO",
			"NUMEROEMPCOLOCACION",
			"NOMBREEMPCOLOCACION",
			"OFFICERPOSITION",
			"NUMEROEMPEBANKING",
			"NOMBREEMPEBANKING",
			"OBSERVACIONES",
			"ESQUEMA",
			"PLANESQUEMACOMERCIAL",
			"DETALLEOTRO",
			"COMENTARIOSOPERACIONES",
			"PAQUETE",
			"REQUIEREFIANZA",
			"EXCENTARFIANZA",
			"TECLADOABIERTO",
			"VENTAFORZADA",
			"DUALIDADAMEX",
			"TPVDESANTENDIDAS",
			"MODCONCILIACIONTRANS",
			"PAGAREMINIMOMN",
			"INCUMPPAGAREMINIMOMN",
			"PAGAREMINIMODLLS",
			"INCUMPPAGAREMINIMODLLS",
			"IMPULSOCAPTACION",
			"CR",
			"NOMBRECOMERCIAL",
			"RFC",
			"TRAMITE",
			"NUMGIRO",
			"DESCRIPGIRO",
			"CLIENTSIC"
	};
	
	@Override
	public List<String> getHeaders() {
		return Arrays.asList(headers);
	}

	@Override
	public String getLayoutName() {
		return "ReportAdqDaily";
	}

	@Override
	public List<String> castObject(ContractAcquirerReportStatusDTO obj) {
		List<String>row=new ArrayList<String>();
		if(obj!=null){
			String folio=obj.getFolio()!=null?obj.getFolio():"";
			String contractId=obj.getContractId()!=null?obj.getContractId():"";
			String producto=obj.getProducto()!=null?obj.getProducto():"";
			String productoDescripcion=obj.getProductoDescripcion()!=null?obj.getProductoDescripcion():"";
			String version=obj.getVersion()!=null?obj.getVersion():"";
			String statusId=obj.getStatusId()!=null?obj.getStatusId():"";
			String estatusNombre=obj.getEstatusNombre()!=null?obj.getEstatusNombre():"";
			String estatusDescripcion=obj.getEstatusDescripcion()!=null?obj.getEstatusDescripcion():"";
			String estatusActual=obj.getEstatusActual()!=null?obj.getEstatusActual():"";
			String estatusActualDescripcion=obj.getEstatusActualDescripcion()!=null?obj.getEstatusActualDescripcion():"";
			String fechaUltimaModificacion=obj.getFechaUltimaModificacion()!=null?obj.getFechaUltimaModificacion():"";
			String fechaCreacion=obj.getFechaCreacion()!=null?obj.getFechaCreacion():"";
			String comentarios=obj.getComentarios()!=null?obj.getComentarios():"";
			String numeroEmpleado=obj.getNumeroEmpleado()!=null?obj.getNumeroEmpleado():"";
			String nombreEmpleado=obj.getNombreEmpleado()!=null?obj.getNombreEmpleado():"";
			String numeroEmpColocacion=obj.getNumeroEmpColocacion()!=null?obj.getNumeroEmpColocacion():"";
			String nombreEmpcolocacion=obj.getNombreEmpcolocacion()!=null?obj.getNombreEmpcolocacion():"";
			String officerposition=obj.getOfficerposition()!=null?obj.getOfficerposition():"";
			String numeroEmpEbanking=obj.getNumeroEmpEbanking()!=null?obj.getNumeroEmpEbanking():"";
			String nombreEmpEbanking=obj.getNombreEmpEbanking()!=null?obj.getNombreEmpEbanking():"";
			String observaciones=obj.getObservaciones()!=null?obj.getObservaciones():"";
			String esquema=obj.getEsquema()!=null?obj.getEsquema():"";
			String planEsquemacomercial=obj.getPlanEsquemacomercial()!=null?obj.getPlanEsquemacomercial():"";
			String detalleOtro=obj.getDetalleOtro()!=null?obj.getDetalleOtro():"";
			String comentariosOperaciones=obj.getComentariosOperaciones()!=null?obj.getComentariosOperaciones():"";
			String paquete=obj.getPaquete()!=null?obj.getPaquete():"";
			String requiereFianza=obj.getRequiereFianza()!=null?obj.getRequiereFianza():"";
			String excentarFianza=obj.getExcentarFianza()!=null?obj.getExcentarFianza():"";
			String tecladoAbierto=obj.getTecladoAbierto()!=null?obj.getTecladoAbierto():"";
			String ventaForzada=obj.getVentaForzada()!=null?obj.getVentaForzada():"";
			String dualidadAmex=obj.getDualidadAmex()!=null?obj.getDualidadAmex():"";
			String tpvDesantendidas=obj.getTpvDesantendidas()!=null?obj.getTpvDesantendidas():"";
			String modConciliacionTrans=obj.getModConciliacionTrans()!=null?obj.getModConciliacionTrans():"";
			String pagareMinimoMN=obj.getPagareMinimoMN()!=null?obj.getPagareMinimoMN():"";
			String incumpPagareMinimoMN=obj.getIncumpPagareMinimoMN()!=null?obj.getIncumpPagareMinimoMN():"";
			String pagareMinimoDLLS=obj.getPagareMinimoDLLS()!=null?obj.getPagareMinimoDLLS():"";
			String incumpPagareMinimoDLLS=obj.getIncumpPagareMinimoDLLS()!=null?obj.getIncumpPagareMinimoDLLS():"";
			String impulsoCaptacion=obj.getImpulsoCaptacion()!=null?obj.getImpulsoCaptacion():"";
			String cr=obj.getCr()!=null?obj.getCr():"";
			String nombreComercial=obj.getNombreComercial()!=null?obj.getNombreComercial():"";
			String rfc=obj.getRfc()!=null?obj.getRfc():"";
			String tramite=obj.getTramite()!=null?obj.getTramite():"";
			String numGiro=obj.getNumGiro()!=null?obj.getNumGiro():"";
			String descripGiro=obj.getDescripGiro()!=null?obj.getDescripGiro():"";
			String clientSic=obj.getClientSic()!=null?obj.getClientSic():"";
			row.add(folio);
			row.add(contractId);
			row.add(producto);
			row.add(productoDescripcion);
			row.add(version);
			row.add(statusId);
			row.add(estatusNombre);
			row.add(estatusDescripcion);
			row.add(estatusActual);
			row.add(estatusActualDescripcion);
			row.add(fechaUltimaModificacion);
			row.add(fechaCreacion);
			row.add(comentarios);
			row.add(numeroEmpleado);
			row.add(nombreEmpleado);
			row.add(numeroEmpColocacion);
			row.add(nombreEmpcolocacion);
			row.add(officerposition);
			row.add(numeroEmpEbanking);
			row.add(nombreEmpEbanking);
			row.add(observaciones);
			row.add(esquema);
			row.add(planEsquemacomercial);
			row.add(detalleOtro);
			row.add(comentariosOperaciones);
			row.add(paquete);
			row.add(requiereFianza);
			row.add(excentarFianza);
			row.add(tecladoAbierto);
			row.add(ventaForzada);
			row.add(dualidadAmex);
			row.add(tpvDesantendidas);
			row.add(modConciliacionTrans);
			row.add(pagareMinimoMN);
			row.add(incumpPagareMinimoMN);
			row.add(pagareMinimoDLLS);
			row.add(incumpPagareMinimoDLLS);
			row.add(impulsoCaptacion);
			row.add(cr);
			row.add(nombreComercial);
			row.add(rfc);
			row.add(tramite);
			row.add(numGiro);
			row.add(descripGiro);
			row.add(clientSic);
		}
		return row;
	}

}
