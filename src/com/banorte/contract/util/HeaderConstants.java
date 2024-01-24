/**
 * 
 */
package com.banorte.contract.util;


/**
 * @author omar
 *
 */
public interface HeaderConstants {
	
	//CABECERAS REPORTES
    //Adquirer
    public static final String NUMEMPLEADOCOLOCO  			= "NUMEMPLEADOCOLOCO";
    public static final String NOMBREEMPLEADOCOLOCO  		= "NOMBREEMPLEADOCOLOCO";
    public static final String AFILIACIONPESOS  			= "AFILIACIONPESOS";
    public static final String AFFILIACIONDLLS  			= "AFFILIACIONDLLS";
    public static final String ESQUEMACOMERCIAL  			= "ESQUEMACOMERCIAL";
    public static final String OTROESQUEMACOMERCIAL  		= "OTROESQUEMACOMERCIAL";
    public static final String COMENTARIOSOPERACIONES  		= "COMENTARIOSOPERACIONES";
    public static final String FECHAINSTALACION  			= "FECHAINSTALACION";
    public static final String EMAIL_CLIENTE	  			= "EMAILCLIENTE";
    public static final String LADA_CLIENTE		  			= "LADACLIENTE";
    public static final String TELEFONO_CLIENTE	  			= "TELEFONOCLIENTE";
    public static final String EXTENSION_CLIENTE  			= "EXTENSIONCLIENTE";
    
    //GARANTIA LIQUIDA
    public static final String GARANTIA_LIQUIDA				= "GARANTIA LIQUIDA";
    public static final String VENTAS_ESTIMADAS_MENSUALES	= "VENTAS ESTIMADAS MENSUALES";
    public static final String MONTO_ESTIMADO_TRANSACCION	= "MONTO ESTIMADO DE TRANSACCION";
    public static final String DATOS_SUCURSAL				= "DATOS DE SUCURSAL";
    public static final String BURO_INTERNO					= "BURO INTERNO";
    public static final String DIVISA						= "DIVISA";
    public static final String MONTO_INICIAL				= "MONTO INICIAL";
    public static final String MONTO_PROM_DIARIO			= "PROM MONTO DIARIO GL";
    public static final String PORCENTAJEGL					= "% GL";
    public static final String MONTOGL						= "MONTO GL";
    public static final String PORCENTAJEINIGL				= "% INICIAL GL";
    public static final String MONTOINIGL					= "MONTO INICIAL GL";
    public static final String PORCENTAJERESTGL				= "% RESTANTE GL";
    public static final String MONTORESTGL					= "MONTO RESTANTE GL";
    public static final String PORCENTAJEDIAGL				= "% DIARIO GL";
    public static final String PROMMONTODIARIOGL			= "PROM MONTO DIARIO GL";
    public static final String DIASAPROXGL					= "DIAS APROX GL";
    public static final String GLORIGINAL					= "GL ORIGINAL";
    public static final String COMENTARIOSMODISMINUCION		= "COMENTARIOS MOTIVO DISMINUCION";
    public static final String DISMINUCIONLG				= "DISMINUCION % GL";
    
    
    //SIP
    public static final String FECHACOFORMALIZACION  		= "FECHACOFORMALIZACION";
    public static final String FECHAACEPTADO  				= "FECHAACEPTADO";
    
    //ALL PRODUCTS
    public static final String MES  						= "MES";
    public static final String PRODUCTO  					= "PRODUCTO";
    public static final String FOLIO  						= "FOLIO";
    public static final String VERSION  					= "VERSION";
    public static final String NOMBRECOMERCIAL  			= "NOMBRECOMERCIAL";
    public static final String RAZONSOCIAL  				= "RAZONSOCIAL";
    public static final String ESTATUSACTUAL  				= "ESTATUSACTUAL";
    public static final String ESTATUSDESCRIPCION  			= "ESTATUSDESCRIPCION";
    public static final String FECHAULTIMAMODIFICACION		= "FECHAULTIMAMODIFICACION";
    public static final String FECHACREACION  				= "FECHACREACION";
    public static final String CR  							= "CR";
    public static final String NUMEMPLEADO  				= "NUMEMPLEADO";
    public static final String NOMBREEMPLEADO  				= "NOMBREEMPLEADO";
    public static final String PAYWORKS_CLABE				= "PAYWORKS CLABE"; // nuevas modificacion de reporte mensual
    public static final String NUMEMPLEADOFORMALIZO			= "NUMEMPLEADOFORMALIZO";
    public static final String NOMBREEMPLEADOFORMALIZO		= "NOMBREEMPLEADOFORMALIZO";
    public static final String FECHAFORMALIZACION			= "FECHAFORMALIZACION";
    public static final String NUMCOFORMALIZO				= "NUMCOFORMALIZO";
    public static final String NOMBRECOFORMALIZO			= "NOMBRECOFORMALIZO";
    public static final String GIROCLIENTE					= "GIROCLIENTE";//F-92512 Esquemas 2019 RF_006_Agrear a Reporte_Giro
    
    //CABECERAS LAYOUT
    // ACQUIRER
    
    public static final String SUBFIJO_MX  				= " (MXP)";
    public static final String SUBFIJO_DLLS  			= " (DLLS)";
    public static final String SUBFIJO_DSC  			= " (Descripcion)";
    
    
    public static final String COMISION_TC						= " Comision TC";
    public static final String COMISION_TD						= " Comision TD";
    public static final String COMISION_INTNL					= " Comision INTNL";
    public static final String CUOTA_AFILIACION					= "Cuota de afiliacion";
    public static final String MAQUINA_TRANSCRIPTORA			= "Por cada Maquina transcriptora/Placa";
    public static final String RENTA_MENSUAL					= "Renta Mensual";
    public static final String RENTA_MENSUAL_3DSEC				= "Renta Mensual 3DSecure";
    public static final String ANUALIDAD_3DSEC 					= "Anualidad 3DSecure";
    public static final String FACTURACION_MENSUAL_MINIMA		= "Facturacion Mensualidad Minima Exigida";
    public static final String COMISION_NO_CUMPLIR_ANTERIOR		= "Comision por no cumplir con la anterior";
    public static final String SALDO_PROMEDIO_MENSUAL			= "Saldo promedio mensual cuenta cheques minimo exigido";
    public static final String PAGARE_MINIMO  					= "Pagare Minimo";
    public static final String COMISION_INCUMPLIMIENTO			= "Comision incumplimiento Pagare minimo";
    public static final String SERVICIO_VERIFICACION 			= "Servicio de verificacion de domicilio por cada consulta";
    public static final String OTROS_CONCEPTOS  				= "Otros Conceptos";
    public static final String SOBRETASA  						= "Sobretasa";
    public static final String CATEGORIA_CREDITO				= "Categoria Credito";
    public static final String CATEGORIA_DEBITO 				= "Categoria Debito Tarifa";
    public static final String ALIANZA  						= "Alianza";
    public static final String PAQUETE  						= "Paquete";
    public static final String TIEMPO_AIRE  					= "Tiempo Aire";
    public static final String TELCEL  							= "Telcel";
    public static final String MOVISTAR  						= "Movistar";
    public static final String IUSACELL 						= "Iusacell";
    public static final String NEXTEL  							= "Nextel";
    public static final String CAPTURE_SCHEMA					= "Captura de Esquema";
    public static final String LATITUD							= "Latitud";
    public static final String LONGITUD							= "Longitud";
    public static final String RentBy							= "Renta por";
    public static final String GroupBy							= "No. de Grupo";
    
    //Cobranza Domiciliada
    
    public static final String NOMBRE_REDUCIDO  					= "NOMBRE REDUCIDO";
    public static final String EMISORA  							= "EMISORA";
    public static final String RFC  								= "RFC";
    public static final String CODIGO  								= "CODIGO";
    public static final String CTA_CARGO_ABONO  					= "CTA. CARGO/ABONO";
    public static final String DESGLOSE_MOV  						= "DESGLOSE MOV";
    public static final String C_APER  								= "C. APER";
    public static final String REGISTRO_AUTOMATICO  				= "REGISTRO AUT.";
    public static final String PYME  								= "Pyme";
    public static final String ADEUDOS_EMISORAS  					= "EMIS. ADEUDOS EMISORA(BATCH)";
    public static final String DIAS_RET  							= "DIAS/RET";
    public static final String ACT_PLASTICO  						= "Act.plastico (A/M)";
    public static final String REINTENTOS  							= "Num/REIN";
    public static final String COM_RAFAGA  							= "COM RAFAGA(LINEA)";
    public static final String VERIF_CUENTAS  						= "Verif. Cuentas";
    public static final String TIPO_EMIS  							= "Tipo Emis";
    public static final String ALTA_CR  							= "Alta Cr";
    public static final String DEPURACION  							= "Depuraci�n";
    public static final String FORMATO_RESPUESTA  					= "Formato Respuesta";
    public static final String SW  									= "SW";
    public static final String D_ABO  								= "D. Abo.";
    public static final String COM_TRANS_SUC  						= "Com Trans/Suc";
    public static final String RECIPROCIDD  						= "Reciprocidd";
    public static final String D_CAR  								= "D. Car.";
    public static final String NOM_CON  							= "Nom. Con";
    public static final String D_DEV  								= "D. Dev.";
    public static final String TELEFONO  							= "Telefono";
    public static final String COMISION_NTF  						= "Comison por NTF";
    public static final String BXI  								= "BXI";
    public static final String BEM  								= "BEM";
    public static final String EMI  								= "Emi";
    public static final String MMAX  								= "Mmax";
    public static final String CONTACTO  							= "Contacto";
    public static final String AFILIACION  							= "Afiliaci�n";
    public static final String PAYWORKS  							= "Payworks";
    public static final String FIANZA  								= "FIANZA";
    public static final String VENCIMIENTO  						= "VENCIMIENTO";
    public static final String IMP_FIANZA  							= "Imp. Fianza";
    public static final String DESC_BIEN_SERV  						= "Desc. Bien o Serv";
    public static final String OBSERVACIONES  						= "Observaciones";
    public static final String NO_EMP_BANCA_ESPECIALIZADA			= "No. Empleado Banca Especializada";
    public static final String NOM_EMP_BANCA_ESPECIALIZADA  		= "Nombre Empleado Banca Especializada";
    public static final String NO_EMP_DIRECTOR_TERRITORIAL			= "No. Empleado Director Territorial/Regional";
    public static final String NOM_EMP_DIRECTOR_TERRITORIAL			= "Nombre Empleado Director Territorial/Regional";
    public static final String NO_EMP_FUNCIONARIO_COLOCO			= "No. Empleado Coloc�";
    public static final String NOM_EMP_FUNCIONARIO_COLOCO			= "Nombre Empleado Coloc�";
    public static final String NO_EMP_FUNCIONARIO_EBANKIG			= "No. Empleado e-Banking";
    public static final String NOM_EMP_FUNCIONARIO_EBANKIG			= "Nombre Empleado e-Banking";
    public static final String NO_EMP_REP_LEGAL_1					= "No. Empleado Rep Legal Banorte 1";
    public static final String NOM_EMP_REP_LEGAL_1					= "Nombre Empleado Rep Legal Banorte 1";
    public static final String NO_EMP_REP_LEGAL_2 					= "No. Empleado Rep Legal Banorte 2";
    public static final String NOM_EMP_REP_LEGAL_2					= "Nombre Empleado Rep Legal Banorte 2";
    
    
    
    // MTTO BEM
    
    
    public static final String NUMERO_EMPRESA  					= "Numero de Empresa";
    public static final String TIPO_MTTO  						= "Tipo de Mtto";
    public static final String RAZON_SOCIAL  					= "Razon Social";
    public static final String RAZON_SOCIAL_ANTERIOR 			= "Razon Social Anterior";
    public static final String SIC 								= "SIC";
    public static final String MEXICO 							= "Mexico";
    public static final String PAIS 							= "Pais";
    public static final String ESTADO 							= "Estado";
    public static final String CIUDAD 							= "Ciudad";
    public static final String COLONIA 							= "Colonia";
    public static final String CALLE 							= "Calle";
    public static final String CP 								= "CP";
    public static final String CR_MTTO 							= "CR del Bem del Cliente";
    public static final String APODERADO_1 						= "Apoderado 1";
    public static final String APODERADO_2 						= "Apoderado 2";
    public static final String EMAIL 							= "Email";
    public static final String PLAN 							= "Plan";
    public static final String TIPO_COBRO_COMISION 				= "Tipo Cobro Comisi�n";
    public static final String CUENTA 							= "Cuenta";
    public static final String TOKEN 							= "Token";
    public static final String TOKEN_NUEVO 						= "Token Nuevo";
    public static final String ADMINISTRADOR 					= "Administrador";
    public static final String MANCOMUNADO 						= "Mancomunado";
    public static final String MOTIVO_BAJA 						= "Motivo Baja";
    public static final String NUM_EMP_EBANKING 				= "No. Empleado Ebanking";
    public static final String NOMBRE_EMP_EBANKING 				= "Nombre Empleado Ebanking"; 
    public static final String BAJA_CUENTA 							= "Baja de Cuenta";
    public static final int MAX_CUENTAS 						= 10;
    public static final int MAX_FOLIOS 							= 10;
    public static final int MAX_ADMINISTRADOR					= 3;
    
    //TPV NOMINA EN ADQUIRENTE
    
    public static final String TPV_NOMINA 							= "TPV-Nomina";
    public static final String EMPLOYEE_NOMINA 						= "Empleados N�mina";
    public static final String PENALIZACION_NOMINA_MN				= "Penalizaci�n Empleados N�mina MN";
    public static final String PENALIZACION_NOMINA_DLLS				= "Penalizaci�n Empleados N�mina DLLS";
    
    //TPV CYBERSOURCE
    
    public static final String INTEGRATION				= "Tipo de Integracion";
    public static final String RENTA_CYBERSOURCE		= "Renta Cyber Dolar";
    
    
    //Nomina
    public static final String NOMINA_MES = "MES";
    public static final String NOMINA_FOLIO = "NO FOLIO";
    public static final String NOMINA_RAZON_SOCIAL = "NOMBRE COMERCIAL/RAZON SOCIAL";
    public static final String NOMINA_VERSION = "VERSION";
    public static final String NOMINA_STATUS_ID = "ESTATUS";
    public static final String NOMINA_STATUS_DESCRIPCION = "ESTATUS DESCRIPCION";
    public static final String NOMINA_FECHA_CREACION = "FECHA CREACION";
    public static final String NOMINA_FECHA_MODIFICACION = "FECHA MODIFICACION";
    public static final String NOMINA_MERCHANT_NUMBER = "MERCHANT NUMBER";
    public static final String NOMINA_EMISORA = "NUMERO DE EMISORA";
    public static final String NOMINA_CR = "CR DE SUCURSAL";
    public static final String NOMINA_SUCURSAL = "NOMBRE DE LA SUCURSAL";
    public static final String NOMINA_NO_EMPLEADOS = "NUMERO DE EMPLEADOS";
    public static final String NOMINA_REP_LEGAL = "REPRESENTANTE LEGAL";
    public static final String NOMINA_NUM_COLOCO = "NUMERO EMPLEADO COLOCO";
    public static final String NOMINA_COLOCO = "NOMBRE EMPLEADO COLOCO";
    public static final String NOMINA_NUM_EBANKING = "NUMERO EMPLEADO EBANKING";
    public static final String NOMINA_EBANKING = "NOMBRE EMPLEADO EBANKING";
    public static final String NOMINA_EMAIL = "EMAIL";
    public static final String NOMINA_AREA_CODE = "CLAVE LADA";
    public static final String NOMINA_PHONE = "TELEFONO";
    public static final String NOMINA_PHONE_EXT = "EXTENSION";
    public static final String NOMINA_DIRECCION = "DIRECCION";
    public static final String NOMINA_STATE = "ESTADO";
    public static final String NOMINA_ZIP_CODE = "CODIGO POSTAL";
    
    //MPOS
    
    public static final String CANT_MPOS_RENTA_MENSUAL = "MPOS Renta";
    public static final String TOTAL_MPOS_COSTO_EQUIPO_DLLS = "Total MPOS Costo equipo(DLLS)";
    public static final String CANT_MPOS_COSTO_EQUIPO = "MPOS Compra";
    public static final String TOTAL_MPOS_COSTO_EQUIPO = "Total MPOS Costo equipo(MXP)";

}
