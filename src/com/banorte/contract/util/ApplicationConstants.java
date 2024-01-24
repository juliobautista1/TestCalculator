/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banorte.contract.util;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface ApplicationConstants {
	
	/*Product Type*/
	public static final Integer  PT_BEM 	= 1;		//BEM
	public static final Integer  PT_NOMINA 	= 2;		//NOMINA
	public static final Integer  PT_ADQ 	= 3;		//ADQUIRENTE
	public static final Integer  PT_MB 		= 5;			//MANTENIMIENTOS
	public static final Integer  PT_CD 		= 4;			//COBRANZA DOMICILIADA
	public static final Integer  PT_TODOS 	= 99;			//TODOS LOS PAQUETES PARA DIFERENCIAS AL MOMENTO DE ENVIAR LOS CORREOS
	
	public static final String  PT_BEM_STR 		= "BEM";					//BEM
	public static final String  PT_NOMINA_STR 	= "NOMINA";			//NOMINA
	public static final String  PT_ADQ_STR 		= "TPV";					//ADQUIRENTE
	public static final String  PT_MB_STR 		= "MANTENIMIENTOS";		//MANTENIMIENTOS
	public static final String  PT_CD_STR 		= "COBRANZA DOMICILIADA";	//COBRANZA DOMICILIADA
	
	public static final String  FILE_BEM 	= "BEM";					//BEM
	public static final String  FILE_NOMINA = "NOM";			//NOMINA
	public static final String  FILE_ADQ 	= "TPV";					//ADQUIRENTE
	public static final String  FILE_MB 	= "MTO";		//MANTENIMIENTOS
	public static final String  FILE_CD 	= "CDO";	//COBRANZA DOMICILIADA
	public static final String  FILE_ALTA 	= "ACT";	//ALTA ACTIVACIONES
	
	
    String PROFILE = "profile";
    String UNAME = "uname";
    String ROLE = "role";
    String UID = "uid";
    String TITLE = "title";
    String ROLE_OBLIX = "roleOblix";
    String COMMDISPERSALOTHERACC = "3.50";
    String COMMEXPEDITIONCARD = "0";
    String COMMREPLACEMENTCARD = "75";
    String COMMREPLACEMENTADDCARD = "75";
    String NUMTXDIFFERENTATM = "0";
    String NUMFAILOTHERBANK = "0";
    String NUMADDBALANCEOTHERBANK = "0";
    String COMMADDDISPOSITIONOTHERBANK = "19";
    String COMMADDBALANCEOTHERBANK = "9";
    String COMMADDREJECTOTHERBANK = "5";
    String ADMINISTRADOR = "1";
    String DESAROLLADOR = "2";
    String SOFTWARE = "3";
    String FIRMAFACULTADA = "3";
    String CONTACTOHERRAMIENTA = "3";
    String AVMONTHLYBALANCE = "7";
    String TRUE="wvc0s?$'Y:p2cMtT!5";
    String EXITO = "EXITO";
    String PAYWORKS = "PAYWORKS";
    String RECHAZO = "RECHAZO";
    String RUTA = "RUTA";
    String CANCELADO = "CANCELADO";
    Integer IDEXITO = new Integer(13);
    Integer IDPAYWORKS = new Integer(14);
    Integer IDRECHAZO = new Integer(15);
    Integer IDRUTA = new Integer(16);
    Integer IDCANCELADO = new Integer(17);
    Integer IDEXITOPARCIALMXP = new Integer(18);
    Integer IDEXITOPARCIALDLLS = new Integer(19);
    Integer IDPAYWORKSPARCIALMXP = new Integer(20);
    Integer IDPAYWORKSPARCIALDLLS = new Integer(21);
    String CURRENCYBOTH ="Ambos";
    //Double CURRENCY_EXCHANGE = new Double(18.00);
    Double CURRENCY_EXCHANGE = new Double(20.00); //F-92512 Esquemas 2019 RF_004_Tipo de Cambio
    
    /* GENERALES */
    public static final Integer ID_CONTRACT_TYPE_ALTA 		= 1;
    public static final Integer ID_CONTRACT_TYPE_MTTO 		= 2;
    
    public static final DecimalFormat DECIMAL_FORMAT_2D		= new DecimalFormat("#.##");
      
    
    public static final int VALUE_0 			= 0;
    public static final int VALUE_1 			= 1;
    public static final int VALUE_2 			= 2;
    public static final int VALUE_4 			= 4;
    public static final int VALUE_5 			= 5;
    
    public static final String NUMBER_1 		= "1";
    public static final String NUMBER_2 		= "2";
    public static final String NUMBER_3 		= "3";
    public static final String NUMBER_4 		= "4";
    public static final String NUMBER_5 		= "5";
    public static final String NUMBER_6 		= "6";
    public static final String NUMBER_7 		= "7";
    public static final String NUMBER_8 		= "8";
    public static final String NUMBER_9 		= "9";
    public static final String NUMBER_10 		= "10";
    public static final String NUMBER_11 		= "11";
    public static final String NUMBER_12 		= "12";
    public static final String NUMBER_13 		= "13";
    public static final String NUMBER_14 		= "14";
    public static final String NUMBER_15 		= "15";
    
    public static final String UNSUCCESS 		= "UNSUCCESS";
    public static final String SUCCESS 			= "SUCCESS";
    
    public static final String VALUE_TRUE		= "true";
    public static final String VALUE_FALSE		= "false";
    
    public static final String SIGN_PORCENT		= "%";
    public static final String SIGN_CURRENCY	= "$";
    
    public static final String EMPTY_STRING 	= "";
    public static final String BLANK_STRING 	= " ";
    public static final String ZERO_DOUBLE_STR	= "0.0";
    public static final String ZERO_STRING		= "0";
    
    public static final Integer EMPTY_LIST 		= 0;
    public static final Integer INIT_CONT_CERO 	= 0;
    public static final Integer INIT_CONT_UNO 	= 1;
    public static final Integer BEGIN_SUBSTRING	= 0;
    public static final Integer END_SUBSTRING 	= 9;
    public static final String LAYOUT 			= "Layout-";
    public static final String REPORT 			= "Report-";
    public static final String OPTION_SIC 		= "SIC";
    public static final String OPTION_FOLIO 	= "Folio";
    
    public static final Integer DEFAULT_VERSION_CONTRACT = 1;
    public static final String CONTRACT_TYPE_ALTA 	= "A";
    
    public static final String OPCION_SELECTED		= "X";
    
    public static final String DATE_HOUR_PRINT_PDF 				= "F[0].P1[0].dateHourDelivery[0]";
    
    /*NOMBRE ATRIBUTOS*/
    public static final String ATTR_BEMNUMBER		= "bemnumber";
    public static final String ATTR_USER_TOKEN		= "userToken";
    
    /*ID ATRIBUTOS*/
    public static final Integer ATTR_ID_COMENTARIO_RESPUESTA_OPERACIONES 	= 297;
    public static final Integer ATTR_ID_USER_TOKEN_1 = 443;
    public static final Integer ATTR_ID_USER_TOKEN_2 = 444;
    public static final Integer ATTR_ID_USER_TOKEN_3 = 445;
    public static final Integer ATTR_ID_USER_TOKEN_4 = 446;
    public static final Integer ATTR_ID_USER_TOKEN_5 = 447;
    public static final Integer ATTR_ID_USER_TOKEN_6 = 448;
    public static final Integer ATTR_ID_USER_TOKEN_7 = 449;
    public static final Integer ATTR_ID_USER_TOKEN_8 = 450;
    public static final Integer ATTR_ID_USER_TOKEN_9 = 451;
    public static final Integer ATTR_ID_USER_TOKEN_10 = 452;
    public static final Integer ATTR_ID_USER_TOKEN_11 = 453;
    public static final Integer ATTR_ID_USER_TOKEN_12 = 454;
    
    public static final Integer MAX_SIZE_COMENTARIO_OPERACIONES 		= 250;
    public static final Integer MAX_SIZE_COMENTARIO_OPERACIONES_SUBSTR 	= 249;
    
    //variables cambio de Layout de Respuesta
    public static final Integer FIRST_COLUMN_RESPONSE_USER_TOKEN 	= 7;
    public static final Integer MAX_NUMBER_USER_TOKEN 				= 12;
    
    // Id de Action Event Para Llenar los campos de Empleado o Sucursales
    public static final String ID_ACTEVENT_SEARCH_OFFICER  				= "SearchOfficer";
    public static final String ID_ACTEVENT_SEARCH_OFFICER_BANKING  		= "SearchOfficerBanking";
    public static final String ID_ACTEVENT_SEARCH_OFFICER_REP_EMP_1  	= "SearchOfficerRepEmp1";
    public static final String ID_ACTEVENT_SEARCH_OFFICER_REP_EMP_2  	= "SearchOfficerRepEmp2";
    public static final String ID_ACTEVENT_SEARCH_BRANCH				= "SearchOfficerBranch";
    public static final String ID_ACTEVENT_SEARCH_OFFICER_BANKING_ESP	= "SearchOfficerEsp";
    public static final String ID_ACTEVENT_SEARCH_OFFICER_BANKING_T		= "SearchOfficerT";
    
 // Id de Action Event Para Llenar los campos de Empleado o Sucursales para Mantenimientos
    public static final String ID_ACTEVENT_SEARCH_OFFICER_MTTO				= "SearchOfficerMtto";
    public static final String ID_ACTEVENT_SEARCH_OFFICER_BANKING_MTTO		= "SearchOfficerBankingMtto";
    public static final String ID_ACTEVENT_SEARCH_OFFICER_REP_EMP_1_MTTO  	= "SearchOfficerRepEmp1Mtto";
    public static final String ID_ACTEVENT_SEARCH_OFFICER_REP_EMP_2_MTTO  	= "SearchOfficerRepEmp2Mtto";
    public static final String ID_ACTEVENT_SEARCH_AFFILIATION_MTTO			= "SearchAffiliationId"; //Se agrego
    
 // Id de Action Event Para Llenar los campos de Director de sucursal
   public static final String ID_ACTEVENT_SEARCH_BRANCH_DIRECTOR		= "SearchBranchDirector";
    
    
    //FIELD NAME ATTRIBUTE
    public static final String FIELDNAME_ALLIANCE  				= "alliance";
    public static final String FIELDNAME_CHARGETYPE  			= "chargeType";
    
    //CALCULATE COMISSION PLAN
    
    public static final String COMMCHARGE_TYPE_PORCENTAJE		= "Porcentaje";
    public static final String ALLIANCE_MICROS 					= "Micros";
    public static final String ALLIANCE_NETPAY 					= "Netpay";
    public static final String ALLIANCE_NETPAY_COMPLETE			= "Netpay (Caja Total Banorte)";
    public static final String ALLIANCE_CYBERSOURCE				= "Cybersource";
    
    public static final String MICROS_CHARGE_TYPE_TASA 			= "(+)0.15 ppb en tasa";
    public static final String MICROS_CHARGE_TYPE_TRANS 		= "(+)$1.40 por transaccion";
    public static final String MICROS_CHARGE_TYPE_RENTA 		= "Renta Mensual $200 por equipo + 0.05 ppb en tasa";
    
    public static final String NETPAY_CHARGE_TYPE_TASA 			= "(+)% en tasa";
    public static final String NETPAY_CHARGE_TYPE_TRANS 		= "(+)$1 por transaccion";
    public static final String NETPAY_CHARGE_TYPE_EQ 			= "(+)$75 Renta Ad. por Eq";
    
    public static final String CYBER_CHARGE_TYPE_TASA 			= "(+)0.1 ppb en tasa";
    public static final String CYBER_CHARGE_TYPE_TRANS 			= "(+)$1.10 por transaccion";
    public static final String CYBER_CHARGE_TYPE_RECIPROCIDAD	= "Reciprocidad";
    
    public static final String COMERCIALPLAN10 					= "1"; //"PLAN15";
    public static final String COMERCIALPLAN30 					= "2"; //"PLAN35";
    public static final String COMERCIALPLAN70 					= "3";//"PLAN70";
    public static final String COMERCIALPLAN150 				= "4";//"PLAN150";
    public static final String COMERCIALPLAN500 				= "5"; //"PLAN500";
        
    public static final String ATT_TASA 						= "tasa_value";
    public static final String ATT_TRANSACTION_PESOS			= "transaccion_pesos_value";
    public static final String ATT_TRANSACTION_DOLLAR			= "transaccion_dollar_value";
    
    public static final Double MICROS_DIVISA_PESOS_TRANS 		= new Double (1.4);
    public static final Double MICROS_DIVISA_DOLLAR_TRANS 		= new Double (0.10);
    public static final Double NETPAY_DIVISA_PESOS_TRANS 		= new Double (1);
    public static final Double NETPAY_DIVISA_DOLLAR_TRANS 		= new Double (0.10);
    public static final Double CYBER_DIVISA_PESOS_TRANS 		= new Double (1.10);
    public static final Double CYBER_DIVISA_DOLLAR_TRANS 		= new Double (0.065);
    
    public static final String NETPAY_COMM_EQ_RENT_PESOS  		= "75";
    public static final String NETPAY_COMM_EQ_RENT_DLLS  		= "7.5";
    
    public static final String COMM_TASA15  			    	= "0.15";
    public static final String COMM_TASA10  			    	= "0.10";
    public static final String COMM_TASA20  			    	= "0.20";
    public static final String COMM_TASA05						= "0.05";
    
    
    public static final String PLAN_TASA15_SUB  			    = "-15";
    public static final String PLAN_TASA10_SUB  			    = "-10";
    public static final String PLAN_TASA20_SUB  			    = "-20";
    
    public static final String PLAN_TRANSACTION_SUB  			= "-TX";
    public static final String PLAN_EQ_RENT_SUB 				= "-R75";
    public static final String PLAN_TPV_NOMINA_SUB 				= "N";
    
    public static final String CURRENCY_PESOS					= "Pesos";
    public static final String CURRENCY_DOLLAR 					= "Dolares";
    public static final String CURRENCY_AMBOS 					= "Ambos";
    
    //PERMISO VIZUALISAR TOKEN BEM
    public static final String PREFIJO_BEM 					= "BA";
    public static final Integer ID_ATTRIBUTE_CR 			= 9;
    
    //RESULTS 
    
    public static final String OUTCOME_BITACORA   					= "BITACORA";
    public static final String OUTCOME_RESULT   					= "RESULT";
    public static final String OUTCOME_EXECUTIVE   					= "EXECUTIVE";
    
    
    // COBRANZA DOMICILIADA 
    public static final Integer ID_PORUDCT_CD					= 7;
    public static final String TRANSMITIR_SUCURSAL				= "15";
    public static final String BATCH_BANORTE_DEFAULT			= "6";
    public static final String BATCH_OTROS_DEFAULT				= "6";
    public static final String ONLINE_BANORTE_DEFAULT			= "8";
    public static final String PREFIJO_CD 						= "CD";
    public static final String OPCION_TRUE_STR					= "1";
    public static final String OPCION_FALSE_STR					= "0";
    public static final String FORMAT_LAYOUT_SIGA				= "SIGA";
    public static final String MODULE_OPCION_10					= "Modulo 10";
    public static final String MODULE_OPCION_11					= "Modulo 11";
    public static final String MODULE_OPCION_97					= "Modulo 97";
    public static final String MODULE_OPCION_OTROS				= "Otro"; 
    public static final String THROUGH_OPCION_EMISOR			= "Emisor";
    public static final String THROUGH_OPCION_BANORTE			= "Banorte"; 
    public static final String THROUGH_OPCION_AMBOS				= "Ambos";
    public static final String OPTION_TRUE_1					= "1"; 
    public static final String OPTION_FALSE_0					= "0";
    public static final String OPTION_HAVE_DEPOSIT				= "S";
    public static final String LAYOUT_VALUE_PAYWORKS			= "PAYWORKS";
    public static final String COMISSION_BATCH_CODE_DEFAULT		= "C02";
    public static final Integer ID_LAYOUT_CD_EXCEPCION_FIANZA	= 54;
    public static final int MAX_SIZE_CR_LAYOUT					= 4;
    public static final int MAX_SIZE_MN_LAYOUT					= 10;
    public static final String BOOLEAN_STRING_S					= "S";
    public static final String BOOLEAN_STRING_N					= "N";
    public static final String BOOLEAN_STRING_SI				= "SI";
    public static final String BOOLEAN_STRING_NO				= "NO";
    public static final String SIN_COLOR						= "Sin Color";
    public static final String SIN_REGISTRO_BUENO				= "Sin registro bueno";
    public static final String GIRO_ESPECIAL_CD_062000			= "062000";
    public static final String GIRO_ESPECIAL_CD_063000			= "063000";
    public static final String GIRO_ESPECIAL_CD_121200			= "121200";
    public static final String GIRO_ESPECIAL_CD_172000			= "172000";
    public static final String GIRO_ESPECIAL_CD_173000			= "173000";
    public static final String GIRO_ESPECIAL_CD_174000			= "174000";
    public static final String GIRO_ESPECIAL_CD_181000			= "181000";
    public static final String GIRO_ESPECIAL_CD_182000			= "182000";
    
     
    //MANTENIMIENTOS

    public static final String PREFIJO_MTTOS 					= "MB";
    public static final String PREFIJO_MTTOS_ADQUIRENTE			= "MA";
    public static final String SIC_DEFAULT 						= "111";
    public static final String SUCCESSMTTO 						= "SUCCESSMTTO";
    
    // OPCIONES MOTIVOS DE BAJA
    
    public static final String MOTIVE_DROP_1					= "No se obtuvo el soporte adecuado";
    public static final String MOTIVE_DROP_2					= "Problemas continuos para acceder";
    public static final String MOTIVE_DROP_3					= "Prefiero transaccionaren la sucursal";
    public static final String MOTIVE_DROP_4					= "Estoy inseguro de usar internet";
    public static final String MOTIVE_DROP_5					= "Lentitud en el servicio";
    public static final String MOTIVE_DROP_6					= "Mala experiencia al operar en internet";
    public static final String MOTIVE_DROP_7					= "Perd� mi identificaci�n, contrase�a y token";
    public static final String MOTIVE_DROP_8					= "No cubre mis necesidades";
    public static final String MOTIVE_DROP_9					= "Utilizo los servicios de Internet de otro Banco";
    public static final String MOTIVE_DROP_10					= "No estoy de acuerdo con las tarifas";
    public static final String MOTIVE_DROP_11					= "Ya no es cliente de Banorte";
    
    // REPOSICION ,Y TOKENS ADICIONALES 
    public static final String ADD_TOKEN_TYPE_RENOV					= "1";
    public static final String ADD_TOKEN_TYPE_ADD					= "2";
    
    
    //	RECHAZOS MTTO
    
    public static final String RECHAZO_400_STR		= "TRAMITE YA REALIZADO, FAVOR DE VERIFICAR CON SU TOKEN ADMINISTRADOR ANTES DE ENVIAR LA SOLICITUD";
    public static final String RECHAZO_401_STR		= "DEBER� UTILIZAR EL FORMATO DE CUENTAS DE TERCEROS YA QUE LA RAZON SOCIAL ES DIFERENTE A LA DE BEM O NO SE ENCUENTRA DENTRO DEL SIC";
    public static final String RECHAZO_402_STR		= "DATOS INVALIDOS O INEXISTENTES (CUENTAS DE CHEQUES � TOKENS) EN LA EMPRESA";
    public static final String RECHAZO_403_STR		= "RAZON SOCIAL DE LA CUENTA DE CHEQUES NO COINCIDE EN BEM";
    public static final String RECHAZO_404_STR		= "RAZON SOCIAL ANTERIOR NO COINCIDE CON BEM";
    public static final String RECHAZO_405_STR		= "EMPRESA NO EXISTE � RAZON SOCIAL DE LA EMPRESA NO COINCIDE CON BEM";
    public static final String RECHAZO_406_STR		= "EL TOKEN A DAR DE BAJA ES ADMINISTRADOR MANCOMUNADO, SE DEBER� ENVIAR CONVENIO PARA ELIMINAR LA MANCOMUNICACION Y POSTERIORMENTE SOLICITAR BAJA DE TOKEN";
    public static final String RECHAZO_407_STR		= "TOKEN NO SE PUEDE DAR DE BAJA POR SER EL ADMINISTRADOR DE LA EMPRESA";
    public static final String RECHAZO_408_STR		= "NO ES POSIBLE ASIGNAR CONTRASE�AS A TOKENS YA VENCIDOS";
    public static final String RECHAZO_409_STR		= "TOKENS Y/O FOLIOS YA ASIGNADOS A OTRA EMPRESA";
    public static final String RECHAZO_410_STR		= "EMPRESA YA DADA DE BAJA";
    public static final String RECHAZO_411_STR		= "FALTA INFORMAR LA CAUSA DE BAJA DE LA EMPRESA";
    public static final String RECHAZO_412_STR		= "APODERADO Y/O�ADMINISTRADOR NO COINCIDE EN BEM";
    public static final String RECHAZO_413_STR		= "RAZON SOCIAL NO COINCIDE CON SIC";
    public static final String RECHAZO_414_STR		= "RFC NO CORRESPONDE";
    public static final String RECHAZO_415_STR		= "CR DE CLIENTE NO CORRESPONDE A LA CUENTA DE CHEQUES";
    public static final String RECHAZO_416_STR		= "CUENTA DE CARGO INDICADA NO PERTENECE AL SIC DEL CLIENTE";
    public static final String RECHAZO_417_STR		= "NUMERO DE SIC NO EXISTE O ESTA FUSIONADO";
    public static final String RECHAZO_418_STR		= "FALTA INDICAR NUMERO SERIE TOKEN Y CORREO ELECTRONICO PARA EL ADMINISTRADOR";
    public static final String RECHAZO_419_STR		= "LA CUENTA DE CHEQUES NACIO EL DIA DE HOY";
    public static final String RECHAZO_420_STR		= "LA ACTIVACION DE LA CUENTA SE DEBERA TRAMITAR POR MEDIO DE SAPSOA";
    public static final String RECHAZO_421_STR		= "NO SE PERMITEN ALTA DE CUENTAS CARGO EN DOLARES";
    
    public static final String RECHAZO_400_ID		= "400";
    public static final String RECHAZO_401_ID		= "401";
    public static final String RECHAZO_402_ID		= "402";
    public static final String RECHAZO_403_ID		= "403";
    public static final String RECHAZO_404_ID		= "404";
    public static final String RECHAZO_405_ID		= "405";
    public static final String RECHAZO_406_ID		= "406";
    public static final String RECHAZO_407_ID		= "407";
    public static final String RECHAZO_408_ID		= "408";
    public static final String RECHAZO_409_ID		= "409";
    public static final String RECHAZO_410_ID		= "410";
    public static final String RECHAZO_411_ID		= "411";
    public static final String RECHAZO_412_ID		= "412";
    public static final String RECHAZO_413_ID		= "413";
    public static final String RECHAZO_414_ID		= "414";
    public static final String RECHAZO_415_ID		= "415";
    public static final String RECHAZO_416_ID		= "416";
    public static final String RECHAZO_417_ID		= "417";
    public static final String RECHAZO_418_ID		= "418";
    public static final String RECHAZO_419_ID		= "419";
    public static final String RECHAZO_420_ID		= "420";
    public static final String RECHAZO_421_ID		= "421";
    
    /* BITACORA */
    
    public static final String BITACORA_DESCRIPTION_PDF		= "Se Descargo el Formato ";
    
    /*TPV */
    
    public static final Double MULTIPLE_25					= new Double("25");
    public static final Double MULTIPLE_10000				= new Double("10000");
    public static final Double CUARENTA_PORCIENTO			= new Double("0.40");
    public static final Double DIEZ_PORCIENTO				= new Double("0.10");
    public static final Double DOCE_PORCIENTO				= new Double("0.12");
    public static final Double QUINCE_PORCIENTO				= new Double("0.15");
    public static final Double SIETE_PORCIENTO				= new Double("0.07");
    public static final Double DOS_PORCIENTO				= new Double("0.02");
    public static final Double CINCO_PORCIENTO				= new Double("0.05");
    public static final Double VALOR_65						= new Double("65"); /*Renta*/
    public static final Double VALOR_200					= new Double("200");
    public static final Double VALOR_250					= new Double("250");
    public static final Double VALOR_649					= new Double("649"); /*Venta*/
    public static final Double VALOR_MPOS_VENTA				= new Double("299"); //F-98896 Mejora Garantia Liquida
    public static final Double VALOR_1500					= new Double("1500");
    public static final String CAPTURA_PLAN_AUTOMATICO		= "Automatico";
    public static final String CAPTURA_PLAN_MANUAL			= "Manual";
    public static final List<String> GIROS_NO_ESTRATEGICOS	= Arrays.asList("006300", "005960","008398","007523",
																	"005912","005172","005541","005542",
																	"004784","005300","005310","005311",
																	"005331","005411","004814","004821",
																	"004511","004582","004789","004011",
																	"004112","004121","004131");
    public static final List<String> GIROS_GASOLINERAS			= Arrays.asList("005172", "005541","005542");
    /*************INICIO********************/
    public static final List<String> PLAN15_200			= Arrays.asList("5","6","16","21","17","22","18","23","19","24");
    /****************FIN****************/
    public static final String COMERCIO_ELECTRONICO 			= "Comercio Electronico";
    public static final String CARGO_AUTOMATICO 				= "Cargo Recurrente";
    public static final String SUSTITUIR_SALDO 					= "SiSaldo";
    public static final String SUSTITUIR_FACTURACION 			= "SiFac";
    
    //TEMPLATE TPV
//    public static final String TPV_ADENDUM_OIP 				= "AdquirenteAdendumOIP.pdf";
//    public static final String TPV_ADENDUM_3DS_COME			= "AdquirenteAdendumCyberSourceCE3D.pdf";
//    public static final String TPV_ADENDUM_PAYWORKS			= "AdquirenteAdendumPayworks.pdf";
    
    //TEMPLATE CYBERSOURCE
//	public static final String ADENDUM_CYBERSOURCE_COME_3D 	= "AdquirenteAdendumCyberSource.pdf";

	//TEMPLATE MTTO BEM
	public static final String TEMPLATE_MTTO_ACCOUNTS2		= "MBAltaCuentasAutorizadasPropias2.pdf";
	
	//TEMPLATE ADQUIRENTE
//	public static final String ADENDUM_PAYWORKS 			= "AdquirenteAdendumPayworks.pdf";
//	public static final String ADENDUM3DS_COMELECTRONICO	= "AdquirenteAdendum3DSCE.pdf";
	
	//TEMPLATE SIP
	public static final String SIP_CARATULA = "AdquirenteCaratulaOIP.pdf";
	public static final String SIP_ANEXO_COMISIONES = "AdquirenteAnexoComisionesOIP.pdf";
	public static final String SIP_PARAMETRICO = "AdquirenteParametrico.pdf";
	public static final String ADENDUM_OIP					= "AdquirenteAdendumOIP.pdf";
    
	//TEMPLATE BEM
	public static final String BEM_ANEXO_TARIFAS_PLAN1		= "BEMAnexoTarifasPlan1.pdf";
	public static final String BEM_ANEXO_TARIFAS_PLAN2		= "BEMAnexoTarifasPlan2.pdf";
	public static final String BEM_ANEXO_TARIFAS_PLAN4		= "BEMAnexoTarifasPlan4.pdf";
	public static final String BEM_ANEXO_TARIFAS_PLAN20		= "BEMAnexoTarifasPlan20.pdf";
	public static final String BEM_ANEXO_TARIFAS_PLAN1200	= "BEMAnexoTarifasPlan1200.pdf";
	public static final String BEM_CUENTAS_TERCEROS			= "BEMCuentasTerceros.pdf";
	public static final String BEM_CUENTAS_AUTORIZADAS1		= "BEMCuentasAutorizadas1.pdf";
	public static final String BEM_CUENTAS_AUTORIZADAS2		= "BEMCuentasAutorizadas2.pdf";
	public static final String BEM_CUENTAS_AUTORIZADAS3		= "BEMCuentasAutorizadas3.pdf";
	public static final String BEM_TOKENS_ADICIONALES		= "BEMTokensAdicionales.pdf";
	
	// Templates ContratoUnico - IXE Adquirente
	public static final String ADQ_ANEXO_A_IXE = "AdquirenteAnexoAIxe.pdf";
	public static final String ADQ_ANEXO_C_IXE = "AdquirenteAnexoCIxe.pdf";
	public static final String ADQ_CARATULA_IXE = "AdquirenteCaratulaIxe.pdf";
	public static final String ADQ_CLAUSULADO = "AdquirenteClausuladoBanorteIxe.pdf";
	public static final String ADQ_ANEXO_A_BANORTE = "AdquirenteAnexoABanorte.pdf";
	public static final String ADQ_ANEXO_C_BANORTE = "AdquirenteAnexoCBanorte.pdf";
	public static final String ADQ_CARATULA_BANORTE = "AdquirenteCaratulaBanorte.pdf";
	public static final String ADQ_ANEXO_E_BANORTE = "AdquirenteAnexoEBanorte.pdf";
	public static final String ADQ_ANEXO_E_BANORTE_DOLARES = "AdquirenteAnexoEBanorteDolares.pdf";
	public static final String ADQ_CASHBACK = "AdquirenteAdendumCashBack.pdf";
	public static final String ADQ_PAGA_RETIRA_EFECTIVO = "AdquirenteAdendumPagaRetiraEfectivo.pdf";
	public static final String ADQ_CARTA_DISMINUCION_EXENCION_GL = "AdquirenteCartaDisminucionExencionGL.pdf";
	public static final String ADQ_ANEXO_MUJER_PYME_BANORTE = "AdquirenteAnexoMujerPyMEBanorte.pdf";
	
	
    /*  BITACORA LECTURA Y ENVIO DE RESPUESTAS */
    public static final String FILE_TYPE_RESPUESTA 			= "Respuesta";
    public static final String FILE_TYPE_ALTAS 				= "Alta";
    public static final String FRECUENCY_DIARIO 			= "Diario";
    public static final String FRECUENCY_MES 				= "Mensual";
    
    
    /* CORREOS ELECTRONICOS PARA ENVIAR REPORTES DE LA BITACORA DE CARGA DE ARCHIVOS*/
    
    public static final String EMAIL_BEM_1 				= "rosa.rodriguez.herrera@banorte.com";//"maria.rios.medellin@banorte.com";
    public static final String EMAIL_BEM_2 				= "omar_estrella.ecternohildebrando@banorte.com";
    public static final String EMAIL_BEM_3 				= "omar_estrella.ecternohildebrando@banorte.com";
    public static final String EMAIL_TPV_1 				= "marcela.rios@banorte.com";//"marcela.rios@banorte.com";
    public static final String EMAIL_TPV_2 				= "rosa.rodriguez.herrera@banorte.com";
    public static final String EMAIL_NOMINA_1 			= "rosa.rodriguez.herrera@banorte.com";
    public static final String EMAIL_NOMINA_2 			= "omar_estrella.ecternohildebrando@banorte.com";
    public static final String EMAIL_TODOS_1			= "rosa.rodriguez.herrera@banorte.com";
    public static final String EMAIL_TODOS_2			= "sonia.reyes@banorte.com";
    
    public static final String SUBJECT_MAIL_GENERATION		= "HeB: Resumen de Generacion de Archivos";
    public static final String SUBJECT_MAIL_LOAD			= "HeB: Resumen de Carga de Archivos";
    
    public static final String SUBJECT_MAIL_AUTOMATICO		= "de forma Automatica";
    public static final String SUBJECT_MAIL_MANNUAL			= "de forma Manual";
    
    public static final Integer  FILE_SUBSTR_BEGIN 		= 0;		//BEM
	public static final Integer  FILE_SUBSTR_END 		= 3;		//NOMINA
	
	
	/* CYBERSOURCE*/
	public static final String INTEGRATION_HOSTED_BANORTE		= "Hosted 3D Secure";
	public static final String INTEGRATION_REVISION 			= "Enterprise Revision Manual";
	public static final String INTEGRATION_HOSTED_COMERCIO 		= "Enterprise 3D Secure";
	public static final String INTEGRATION_DIRECT3D				= "Direct 3D";
	
	public static final String RENTA_DOLAR_2100					= "2,100 USD";
	public static final String RENTA_DOLAR_4000					= "4,000 USD";
	
	
	//VARIABLES PARA HACER PESO EN HARVEST
	public static final String HARVEST					= "para hacer mas peso en harvest";
	public static final String HARVEST2					= "para hacer mas peso en harvest";
	public static final String HARVEST3					= "para hacer mas peso en harvest";
	
	
	
	//Planes 2015
	public static final String PLAN_BANORTE0_15 = "Sin exigencias Banorte";
	public static final String PLAN_15_15 = "Plan 15 Banorte";
	public static final String PLAN_35_15 = "Plan 35 Banorte";
	public static final String PLAN_75_15 = "Plan 75 Banorte";
	public static final String PLAN_200_15 = "Plan 200 Banorte";
	public static final String PLAN_IXE0_15 = "Sin exigencias IXE";
	public static final String PLAN_IXE015_15 = "Plan 15 IXE";
	public static final String PLAN_IXE035_15 = "Plan 35 IXE";
	public static final String PLAN_IXE075_15 = "Plan 75 IXE";
	public static final String PLAN_IXE200_15 = "Plan 200 IXE";
	public static final String PLAN_EST_RENT_15 = "Estudio de Rentabilidad";
	public static final String PLAN_OTRO_15 = "Otro";
	
	//Terminal Personal Banorte MPOS
	public static final String PRODUCTO_MPOS = "Terminal Personal Banorte (Mpos)";
}
