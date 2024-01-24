/**
 * 
 */
package com.banorte.contract.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author omar
 *
 */
public interface AttrConstants {
	
	public static final String REFERENCE 					= "reference";
	public static final String OFFICER_EMP_NUM				= "officerempnumber";
	public static final String OFFICER_NAME_COMPL			= "officernameComplete";
	public static final String OFFICER_BANKING_NUM			= "officerebankingempnumber";
	public static final String OFFICER_BANKING_NAME_COMPL	= "officerebankingnameComplete";
	public static final String OPERATIONS_COMMENT 			= "operations_comment";
	public static final String INSTALL_DATE 				= "installdate";
	public static final String OTHER_COMMERCIAL_PLAN 		= "affiliation_otherComercialPlan";
	public static final String COMMENTS 					= "comments";
	public static final String OPERATION_COMMENTS 			= "operations_comment";
	public static final String CONTRACT_TYPE 				= "contractType";
	public static final String CONTRACT_ID 					= "contractId";
	public static final String MTTO_TYPE 					= "mttoType";
	public static final String CONTRACT_REFERENCE 			= "contract_reference";
	public static final String RED="red";

	//COBRANZA DOMICILIADA

	//AFILIACION COBRANZA DOMICILIADA
	public static final String BEM_NUMBER 						= "bemnumber";
	public static final String AFFILIATION_ACCOUNT_NUMBER_MN	= "affiliation_accountnumbermn";
	public static final String AFFILIATION_THROUGH 				= "affiliation_Through";
	public static final String AFFILIATION_FORMATLAYOUT			= "affiliation_formatLayout";
	public static final String AFFILIATION_PAYWORKSCLABE 		= "affiliation_payworksClabe";
	public static final String AFFILIATION_AUTOREG 				= "affiliation_autoRegister";
	public static final String AFFILIATION_RETRIES 				= "affiliation_retries";
	public static final String AFFILIATION_MAXAMOUNT 			= "affiliation_maxAmount";
	public static final String AFFILIATION_INTERNALCREDITHIST	= "affiliation_internalcredithistory";
	public static final String AFFILIATION_EXTERNALCREDITHIST	= "affiliation_externalcredithistory";
	public static final String AFFILIATION_HAVEDEPOSITCOMP 		= "affiliation_havedepositcompany";
	public static final String AFFILIATION_DEPOSITCOMPANY 		= "affiliation_depositcompany";
	public static final String AFFILIATION_DEPOSITAMOUNT 		= "affiliation_depositamount";
	public static final String AFFILIATION_DUEDATE 				= "affiliation_duedate";
	public static final String AFFILIATION_OFFICER_DEPOSIT_EXNT	= "affiliation_officerdepositexent";
	public static final String MONTHLY_AMOUNT					= "monthlyAmount";
	
	//REFERENCIA COBRANZA DOMICILIADA
	public static final String REFERENCE_NAME 			= "referenceName";
	public static final String REFERENCE_LENGTH 		= "referenceLength";
	public static final String REFERENCE_TYPE 			= "referenceType";
	public static final String REFERENCE_DV				= "referenceDV";
	public static final String REFERENCE_REQUIRED		= "referenceRequired";
	public static final String REFERENCE_MODULETYPE		= "referenceModuleType";
	public static final String BATCH_COMISSIONTYPE		= "batchCommissionType";
	public static final String BANORTE_OE 				= "banorteOE";
	public static final String BANORTE_ONE 				= "banorteONE";
	public static final String OTHER_OE 				= "otrosOE";
	public static final String OTHER_ONE 				= "otrosONE";
	public static final String ONLINE_COMISSIONTYPE 	= "onLineCommissionType";
	public static final String ONLINE_BANORTE_OE 		= "onLineBanorteOE";
	public static final String ONLINE_BANORTE_ONE 		= "onLineBanorteONE";
	public static final String BRANCH_TRANSMISSION 		= "branchTransmission";
	public static final String BATCH_COMISSION_CODE 	= "comissionBatchCode";
	public static final String ONLINE_COMISSION_CODE 	= "comissionOnlineCode";
	
	
	//MTTO BEM  ALTA DE CUENTAS PROPIAS
	
	public static final String ACCOUNT_NUMBER_1 	= "accownnum_1";
	public static final String ACCOUNT_REGIMEN_1 	= "regimen_1";
	public static final String ACCOUNT_NAME_1 		= "accownname_1";
	public static final String ACCOUNT_NUMBER_2 	= "accownnum_2";
	public static final String ACCOUNT_REGIMEN_2 	= "regimen_2";
	public static final String ACCOUNT_NAME_2 		= "accownname_2";
	public static final String ACCOUNT_NUMBER_3 	= "accownnum_3";
	public static final String ACCOUNT_REGIMEN_3 	= "regimen_3";
	public static final String ACCOUNT_NAME_3 		= "accownname_3";
	public static final String ACCOUNT_NUMBER_4 	= "accownnum_4";
	public static final String ACCOUNT_REGIMEN_4 	= "regimen_4";
	public static final String ACCOUNT_NAME_4 		= "accownname_4";
	public static final String ACCOUNT_NUMBER_5 	= "accownnum_5";
	public static final String ACCOUNT_REGIMEN_5 	= "regimen_5";
	public static final String ACCOUNT_NAME_5 		= "accownname_5";
	public static final String QTY_ACCOUNTS 		= "quantityAccounts";
	public static final String QTY_TOKENS 			= "quantityTokens";
	public static final String QTY_PASS 			= "quantityPass";
	public static final String QTY_ENVELOPE			= "quantityEnvelope";
	public static final String ACCOUNT_NUMBER_6 	= "accownnum_6";
	public static final String ACCOUNT_REGIMEN_6 	= "regimen_6";
	public static final String ACCOUNT_NAME_6 		= "accownname_6";
	public static final String ACCOUNT_NUMBER_7 	= "accownnum_7";
	public static final String ACCOUNT_REGIMEN_7 	= "regimen_7";
	public static final String ACCOUNT_NAME_7 		= "accownname_7";
	public static final String ACCOUNT_NUMBER_8 	= "accownnum_8";
	public static final String ACCOUNT_REGIMEN_8 	= "regimen_8";
	public static final String ACCOUNT_NAME_8 		= "accownname_8";
	
	
	
	
	//MTTO BEM BAJA DE EMPRESAS
	
	public static final String DROP_MOTIVE 					= "dropMotiveSelected";
	public static final String DROP_MOTIVE_OPTION_1			= "dropMotiveOption1";
	public static final String DROP_MOTIVE_OPTION_2			= "dropMotiveOption2";
	public static final String DROP_MOTIVE_OPTION_3			= "dropMotiveOption3";
	public static final String DROP_MOTIVE_OPTION_4			= "dropMotiveOption4";
	public static final String DROP_MOTIVE_OPTION_5			= "dropMotiveOption5";
	public static final String DROP_MOTIVE_OPTION_6			= "dropMotiveOption6";
	public static final String DROP_MOTIVE_OPTION_7			= "dropMotiveOption7";
	public static final String DROP_MOTIVE_OPTION_8			= "dropMotiveOption8";
	public static final String DROP_MOTIVE_OPTION_9			= "dropMotiveOption9";
	public static final String DROP_MOTIVE_OPTION_10		= "dropMotiveOption10";
	public static final String DROP_MOTIVE_OPTION_11		= "dropMotiveOption11";
	
	
	
	
	//MTTO BEM BAJA DE TOKENS
	
	public static final String MTTO_TOKEN_NUMBER_1			= "mttoTokenNumber_1";
	public static final String MTTO_TOKEN_NUMBER_2 			= "mttoTokenNumber_2";
	public static final String MTTO_TOKEN_NUMBER_3 			= "mttoTokenNumber_3";
	public static final String MTTO_TOKEN_NUMBER_4 			= "mttoTokenNumber_4";
	public static final String MTTO_TOKEN_NUMBER_5 			= "mttoTokenNumber_5";
	public static final String MTTO_TOKEN_NUMBER_6 			= "mttoTokenNumber_6";
	public static final String MTTO_TOKEN_NUMBER_7 			= "mttoTokenNumber_7";
	public static final String MTTO_TOKEN_NUMBER_8 			= "mttoTokenNumber_8";
	public static final String MTTO_TOKEN_NUMBER_9 			= "mttoTokenNumber_9";
	public static final String MTTO_TOKEN_NUMBER_10 		= "mttoTokenNumber_10";
	public static final String DROP_TOKEN_TEMPLATE_OPTION	= "dropTokensTemplate";
	
	
	//MTTO BEM BAJA DE CUENTAS
	
	public static final String MTTO_ACCOUNT_NUMBER_1			= "accownnum_1";  //mttoAccountNumber_10
	public static final String MTTO_ACCOUNT_NUMBER_2 			= "accownnum_2";
	public static final String MTTO_ACCOUNT_NUMBER_3 			= "accownnum_3";
	public static final String MTTO_ACCOUNT_NUMBER_4 			= "accownnum_4";
	public static final String MTTO_ACCOUNT_NUMBER_5 			= "accownnum_5";
	public static final String MTTO_ACCOUNT_NUMBER_6 			= "accownnum_6";
	public static final String MTTO_ACCOUNT_NUMBER_7 			= "accownnum_7";
	public static final String MTTO_ACCOUNT_NUMBER_8 			= "accownnum_8";
	public static final String MTTO_ACCOUNT_NUMBER_9 			= "accownnum_9";
	public static final String MTTO_ACCOUNT_NUMBER_10 			= "accownnum_10";
	public static final String DROP_ACCOUNT_TEMPLATE_OPTION		= "dropAccountsTemplate";
	
	// MTTO BEM NUEVA CONTRASEÑA
	
	public static final String MTTO_FOLIO_PSD_1				= "mttoFolioPsd_1";
	public static final String MTTO_FOLIO_PSD_2 			= "mttoFolioPsd_2";
	public static final String MTTO_FOLIO_PSD_3 			= "mttoFolioPsd_3";
	public static final String MTTO_FOLIO_PSD_4 			= "mttoFolioPsd_4";
	public static final String MTTO_FOLIO_PSD_5 			= "mttoFolioPsd_5";
	
	public static final String BEM_COLONY 			= "bem_colony";
	public static final String BEM_ADDRESS 			= "bem_address";
	public static final String BEM_CP 				= "bem_cp";
	public static final String BEM_STATE 			= "bem_state";  		// AttrConstants.CELEBRATION_STATE
	public static final String BEM_POPULATION		= "bem_population";   	// AttrConstants.CELEBRATION_PLACE
	
	// MTTO TOKENS ADICIONALES 
	
	public static final String MTTO_NEW_TOKEN_NUMBER_1			= "mttoNewTokenNumber_1";
	public static final String MTTO_NEW_TOKEN_NUMBER_2 			= "mttoNewTokenNumber_2";
	public static final String MTTO_NEW_TOKEN_NUMBER_3 			= "mttoNewTokenNumber_3";
	public static final String MTTO_NEW_TOKEN_NUMBER_4 			= "mttoNewTokenNumber_4";
	public static final String MTTO_NEW_TOKEN_NUMBER_5 			= "mttoNewTokenNumber_5";
	
	//MTTO TOKENS NUEVOS Y ADICIONALES Y NUEVA CONTRASEÑA
	
	public static final String MTTO_TEMPLATE_SOLICITUD_RENOV_TOKEN 			= "templateSolicitud_renovToken";
	public static final String MTTO_TEMPLATE_SOLICITUD_NEW_PASS 			= "templateSolicitud_newPass";
	public static final String MTTO_TEMPLATE_SOLICITUD_ADD_TOKEN 			= "templateSolicitud_addToken";
	
	
	
	//MTTO CONVENIO MODIFICATORIO
	
	public static final String CHANGE_CLIENT_CHECK 						= "changeClientInfoCheck";
	public static final String CHANGE_FISCAL_NAME_CHECK 				= "changeFiscalNameCheck";
	public static final String CHANGE_LEGAL_REP_CHECK 					= "changeLegalRepCheck";
	public static final String CHANGE_ADDRESS_CHECK 					= "changeAddressCheck";
	public static final String CHANGE_RFC_CHECK 						= "changeRFCCheck";
	public static final String CHANGE_COLONY_CHECK 						= "changeColonyCheck";
	public static final String CHANGE_CP_CHECK 							= "changeCPCheck";
	public static final String CHANGE_STATE_CHECK 						= "changeStateCheck";
	public static final String CHANGE_CITY_CHECK 						= "changeCityCheck";
	public static final String CHANGE_PHONE_NUMBER_CHECK				= "changePhoneNumberCheck";
	public static final String CHANGE_EMAIL_CHECK 						= "changeEmailCheck";
	public static final String CHANGE_FISCAL_NAME_INFO 					= "changeFiscalNameInfo";
	public static final String CHANGE_LEGAL_REP_INFO 					= "changeLegalRepInfo";
	public static final String CHANGE_ADDRESS_INFO 						= "changeAddressInfo";
	public static final String CHANGE_RFC_INFO 							= "changeRFCInfo";
	public static final String CHANGE_COLONY_INFO 						= "changeColonyInfo";
	public static final String CHANGE_CP_INFO 							= "changeCPCInfo";
	public static final String CHANGE_STATE_INFO 						= "changeStateInfo";
	public static final String CHANGE_CITY_INFO 						= "changeCityInfo";
	public static final String CHANGE_PHONE_INFO 						= "changePhoneNumberInfo";
	public static final String CHANGE_EMAIL_INFO 						= "changeEmailInfo";
	public static final String CHANGE_CONTRACT_CHECK 					= "changeContractCheck";
	public static final String CHANGE_ACCOUNT_CHARGE_CHECK 				= "changeAccountChargeCheck";
	public static final String CHANGE_PLAN_CHECK 						= "changePlanCheck";
	public static final String CHANGE_CHARGE_CHECK 						= "changeChargeCheck";
	public static final String CHANGE_ACCOUNT_CENTRAL_CHECK 			= "changeAccountCentralCheck";
	public static final String CHANGE_ORIGIN_TRANSACTION_CHECK 			= "changeOriginTransactionCheck";
	public static final String CHANGE_ACCOUNT_CHARGE_INFO 				= "changeAccountChargeInfo";
	public static final String CHANGE_PLAN_INFO 						= "changePlanInfo";
	
	public static final String LEGALREPRESENTATIVE_NAME_3 				= "legalrepresentative_name_3";
	public static final String MANCOMUNADO 								= "mancomunado";
	
	public static final String OPTION_A 								= "optionA";
	public static final String OPTION_B 								= "optionB";
	public static final String OPTION_C 								= "optionC";
	public static final String OPTION_D 								= "optionD";
	public static final String OPTION_E 								= "optionE";
	public static final String OPTION_F 								= "optionF";
	public static final String OPTION_G 								= "optionG";
	public static final String OPTION_H 								= "optionH";
	public static final String OPTION_I 								= "optionI";
	public static final String OPTION_J 								= "optionJ";
	public static final String OPTION_K 								= "optionK";
	public static final String OPTION_L 								= "optionL";
	public static final String OPTION_M 								= "optionM";
	public static final String QUANTITY_ATTR							= "quantityAttr";
	public static final String CITY_STATE								= "cityState";
	
	public static final String OPTION_ACOUNT_CENTRAL					= "optionCuentaCentralizada";
	public static final String OPTION_ORIGIN_TRANS						= "optionOrigenTransaccion";
	public static final String PLACE_DATE_CONVENIO						= "placeDateConvenio";

	

	
	//INFORMACION GENERAL
	
	// DATOS DEL CONTRATANTE
	public static final String CELEBRATION_PLACE 		= "celebrationplace";
	public static final String CELEBRATION_STATE 		= "celebrationstate";
	public static final String CELEBRATION_DATE 		= "celebrationdate";
	public static final String CLIENT_SIC 				= "client_sic";
	public static final String CLIENT_AREACODE 			= "client_areacode";
	public static final String CLIENT_PHONE 			= "client_phone";
	public static final String CLIENT_PHONEEXT 			= "client_phoneext";
	public static final String CLIENT_FAX 				= "client_fax";
	public static final String CLIENT_FAXEXT 			= "client_faxext";
	public static final String CLIENT_STREET 			= "client_street";
	public static final String CLIENT_NUMINT 			= "client_numint";
	public static final String CLIENT_NUMEXT 			= "client_numext";
	public static final String CLIENT_COLONY 			= "client_colony";
	public static final String CLIENT_ZIPCODE 			= "client_zipcode";
	public static final String CLIENT_STATE 			= "client_state";
	public static final String CLIENT_CITY 				= "client_city";
	public static final String CLIENT_EMAIL 			= "client_email";
	public static final String CLIENT_FISCALNAME 		= "client_fiscalname";
	public static final String CLIENT_FISCALTYPE 		= "client_fiscaltype";
	public static final String CLIENT_RFC 				= "client_rfc";
	public static final String CLIENT_CONSTITUTIONDATE	= "client_constitutiondate";
	public static final String CLIENT_CATEGORYCODE 		= "client_categorycode";
	
	//DATOC REPRESENTANTE LEGAL
	public static final String LEGALREPRESENTATIVE_NAME_1 			= "legalrepresentative_name_1";
	public static final String LEGALREPRESENTATIVE_LASTNAME_1		= "legalrepresentative_lastname_1";
	public static final String LEGALREPRESENTATIVE_MOTHERS_NAME_1	= "legalrepresentative_mothersln_1";
	public static final String LEGALREPRESENTATIVE_POSITION_1 		= "legalrepresentative_position_1";
	public static final String LEGALREPRESENTATIVE_NAME_2 			= "legalrepresentative_name_2";
	public static final String LEGALREPRESENTATIVE_LASTNAME_2		= "legalrepresentative_lastname_2";
	public static final String LEGALREPRESENTATIVE_MOTHERS_NAME_2	= "legalrepresentative_mothersln_2";
	public static final String LEGALREPRESENTATIVE_POSITION_2 		= "legalrepresentative_position_2";
	
	
	 //DATOS FIRMAS ADICIONALES FACULTADAS
	public static final String CLIENTCONTACT_NAME_1 			= "clientcontact_name1";
	public static final String CLIENTCONTACT_LASTNAME_1 		= "clientcontact_lastname1";
	public static final String CLIENTCONTACT_MOTHERSNAME_1 		= "clientcontact_mothersln1";
	public static final String CLIENTCONTACT_POSITION_1 		= "clientcontact_position1";
	public static final String CLIENTCONTACT_NAME_2 			= "clientcontact_name2";
	public static final String CLIENTCONTACT_LASTNAME_2 		= "clientcontact_lastname2";
	public static final String CLIENTCONTACT_MOTHERSNAME_2 		= "clientcontact_mothersln2";
	public static final String CLIENTCONTACT_POSITION_2 		= "clientcontact_position2";
	public static final String CLIENTCONTACT_NAME_3 			= "clientcontact_name3";
	public static final String CLIENTCONTACT_LASTNAME_3 		= "clientcontact_lastname3";
	public static final String CLIENTCONTACT_MOTHERSNAME_3 		= "clientcontact_mothersln3";
	public static final String CLIENTCONTACT_POSITION_3 		= "clientcontact_position3";
	public static final String CLIENTCONTACT_NAME_4 			= "clientcontact_name4";
	public static final String CLIENTCONTACT_LASTNAME_4 		= "clientcontact_lastname4";
	public static final String CLIENTCONTACT_MOTHERSNAME_4 		= "clientcontact_mothersln4";
	public static final String CLIENTCONTACT_POSITION_4 		= "clientcontact_position4";
	
	//DATOS FUNCIONARIO COLOCO PRODUCTO
	public static final String OFFICER_NUMBER 			= "officerempnumber";
	public static final String OFFICER_NAME 			= "officername";
	public static final String OFFICER_LASTNAME 		= "officerlastname";
	public static final String OFFICER_MOTHERSNAME 		= "officermothersln";
	public static final String OFFICER_POSITION			= "officerposition";
	
	//DATOS FUNCIONARIO EBANKING
	public static final String OFFICER_EBANKING_NUMBER 			= "officerebankingempnumber";
	public static final String OFFICER_EBANKING_NAME 			= "officerebankingname";
	public static final String OFFICER_EBANKING_LASTNAME 		= "officerebankinglastname";
	public static final String OFFICER_EBANKING_MOTHERSNAME 	= "officerebankingmothersln";
	public static final String OFFICER_EBANKING_POSITION 		= "officerebankingposition";
	public static final String OFFICER_EBANKING_FIRMNUMBER		= "officerebankingnumfirm";
	
	//DATOS DE LA SUCURSAL
	public static final String CR_NUMBER 			= "crnumber";
	public static final String BRANCH_NAME 			= "branchname";
	public static final String BRANCH_STREET 		= "branchstreet";
	public static final String BRANCH_COLONY 		= "branchcolony";
	public static final String BRANCH_COUNTY 		= "branchcounty";
	public static final String BRANCH_CITY			= "branchcity";
	public static final String BRANCH_STATE 		= "branchstate";
	public static final String BRANCH_PHONE 		= "branchphone";
	public static final String BRANCH_FAX 			= "branchfax";
	public static final String BANKING_SECTOR 		= "bankingsector";
	
	//DATOS DEL APODERADO 
	public static final String OFFICER_REP_NUMBER_1 		= "officerrepempnumber_1";
	public static final String OFFICER_REP_NAME_1 			= "officerrepname_1";
	public static final String OFFICER_REP_LASTNAME_1 		= "officerreplastname_1";
	public static final String OFFICER_REP_MOTHERSNAME_1	= "officerrepmothersln_1";
	public static final String OFFICER_REP_POSITION_1 		= "officerrepposition_1";
	public static final String OFFICER_REP_FIRMNUMBER_1		= "officerrepfirmnumber_1";
	
	public static final String OFFICER_REP_NUMBER_2 		= "officerrepempnumber_2";
	public static final String OFFICER_REP_NAME_2 			= "officerrepname_2";
	public static final String OFFICER_REP_LASTNAME_2 		= "officerreplastname_2";
	public static final String OFFICER_REP_MOTHERSNAME_2	= "officerrepmothersln_2";
	public static final String OFFICER_REP_POSITION_2 		= "officerrepposition_2";
	public static final String OFFICER_REP_FIRMNUMBER_2		= "officerrepfirmnumber_2";
	
	// DATOS EMPLEADO EBANKING ESPECIALIZADO
	
	public static final String OFFICER_EBANKING_ESP_NUMBER 			= "officerebankingEspEmpnumber";
	public static final String OFFICER_EBANKING_ESP_NAME 			= "officerebankingEspName";
	public static final String OFFICER_EBANKING_ESP_LASTNAME 		= "officerebankingEspLastname";
	public static final String OFFICER_EBANKING_ESP_MOTHERSNAME 	= "officerebankingEspMothersln";
	public static final String OFFICER_EBANKING_ESP_POSITION 		= "officerebankingEspPosition";
	
	// DATOS EMPLEADO EBANKING TERRITORIAL
	
	public static final String OFFICER_EBANKING_T_NUMBER 			= "officerebankingTempnumber";
	public static final String OFFICER_EBANKING_T_NAME 				= "officerebankingTname";
	public static final String OFFICER_EBANKING_T_LASTNAME 			= "officerebankingTlastname";
	public static final String OFFICER_EBANKING_T_MOTHERSNAME 		= "officerebankingTmothersln";
	public static final String OFFICER_EBANKING_T_POSITION	 		= "officerebankingTposition";
	
	//DATOS DIRECTOR SUCURSAL
	
	public static final String BRANCH_DIRECTOR_NUMBER 			    = "branchDirectornumber";
	public static final String BRANCH_DIRECTOR_NAME                 = "branchDirectorname";
	public static final String BRANCH_DIRECTOR_LASTNAME             = "branchDirectorlastname";
	public static final String BRANCH_DIRECTOR_MOTHERSLN            = "branchDirectorMothersLn";
	public static final String BRANCH_DIRECTOR_POSITION             = "branchDirectorposition";
	
	// DATOS OPCION MULTIPLE SELECCIONADOS
	
	public static final String SELECTED_OPTION_BANORTE 				= "affiliation_SelectedBanorte";
	public static final String SELECTED_OPTION_NO_BANORTE 			= "affiliation_NoselectedBanorte";
	public static final String SELECTED_OPTION_EMISOR 				= "affiliation_SelectedEmisor";
	public static final String SELECTED_OPTION_NO_EMISOR 			= "affiliation_NoselectedEmisor";
	public static final String SELECTED_OPTION_SIGA 				= "formatLayoutSelectedSiga";
	public static final String SELECTED_OPTION_PAYWORK_SI 			= "payworksClabeSelectSi";
	public static final String SELECTED_OPTION_PAYWORK_NO 			= "payworksClabeSelectNo";
	public static final String SELECTED_OPTION_MODULE_10 			= "moduleTypeSelect10";
	public static final String SELECTED_OPTION_MODULE_11 			= "moduleTypeSelect11";
	public static final String SELECTED_OPTION_MODULE_97 			= "moduleTypeSelect97";
	public static final String SELECTED_OPTION_MODULE_OTRO 			= "moduleTypeSelectOtro";
	public static final String SELECTED_OPTION_BATCH 				= "batchCommissionSelect";
	public static final String SELECTED_OPTION_ONLINE 				= "onlineCommissionSelect";
	
	 
	
	//DATOS COMPLETOS 
	public static final String COMPLETE_CELEBRATION_PLACE_STATE 	= "celebrationCompletePlaceState";
	public static final String COMPLETE_CELEBRATION 				= "celebrationComplete";
	public static final String COMPLETE_OFFICER_NAME 				= "officernameComplete";
	public static final String COMPLETE_OFFICER_EBANKING_NAME		= "officerebankingnameComplete";
	public static final String COMPLETE_BRANCH_ADRESS 				= "branchadressComplete";
	public static final String COMPLETE_BRANCH_NAME 				= "branchnameComplete";
	public static final String COMPLETE_OFFICER_REP_NAME_1 			= "officerrepnameComplete1";
	public static final String COMPLETE_OFFICER_REP_NAME_2			= "officerrepnameComplete2";
	public static final String COMPLETE_CLIENT_CONTACT_NAME_1		= "clientcontact_nameComplete1";
	public static final String COMPLETE_CLIENT_CONTACT_NAME_2		= "clientcontact_nameComplete2";
	public static final String COMPLETE_CLIENT_CONTACT_NAME_3		= "clientcontact_nameComplete3";
	public static final String COMPLETE_CLIENT_CONTACT_NAME_4		= "clientcontact_nameComplete4";
	public static final String COMPLETE_CLIENT_PHONE 				= "client_phoneComplete";
	public static final String COMPLETE_CLIENT_FAX 					= "client_faxComplete";
	public static final String COMPLETE_CLIENT_STATE 				= "client_stateComplete";
	public static final String COMPLETE_REPRESENTATIVE_NAME_1		= "legalrepresentative_nameComplete1";
	public static final String COMPLETE_REPRESENTATIVE_NAME_2		= "legalrepresentative_nameComplete2";
	public static final String COMPLETE_CLIENT_ADDRESS 				= "client_addressComplete";
	public static final String COMPLETE_OFFICER_EBANKING_ESP_NAME	= "officerebankingEspNameComplete";
	public static final String COMPLETE_OFFICER_EBANKING_T_NAME		= "officerebankingTnameComplete";
	public static final String COMPLETE_BRANCH_DIRECTOR_NAME		= "branchDirectorNameComplete";
	
	
	//ADQUIRENTE
	
	public static final String AFFILIATION_TPV_PAYROLL 				= "affiliation_tpv_payroll";
	public static final String TPV_NUMBER_EMPLOYEE 					= "tpv_number_employee";
	public static final String TPV_PENALTY 							= "tpv_penalty";
	public static final String TPV_PENALTY_COMPLETE					= "tpv_penaltyComplete";
	public static final String TPV_PENALTY_DLL						= "tpv_penalty_dll";
	public static final String OPTION_MONTHLY_RATE_MN 				= "optionMonthlyratemn";
	public static final String REPLACE_AMOUNT_RATE_MN 				= "replaceAmountratemn";
	public static final String OPTION_MONTHLY_RATE_DLLS				= "optionMonthlyratedlls";
	public static final String REPLACE_AMOUNT_RATE_DLLS				= "replaceAmountratedlls";
	public static final String AFFILIATION_TPV_PAYROLL_SELECTED		= "nominaselected";
	
	// CYBERSOURCE
	
	public static final String AFFILIATION_INTEGRATION			= "affiliation_integration";
	public static final String SELECTED_HOST_BANORTE			= "selected_hostBanorte";
	public static final String SELECTED_REVISION				= "selected_revision";
	public static final String SELECTED_HOST_COMERCIO			= "selected_hostComercio";
	public static final String SELECTED_DIRECT3D				= "selected_direct3D";
	
	public static final String AFFILIATION_RENTADOLAR			= "affiliation_rentaDolar";
	public static final String SELECTED_2000					= "selected_2000";
	public static final String SELECTED_4000					= "selected_4000";
	

	//Variables dummy para crear peso en harvest
	public static final String dummy = "para creart peso en harvest";
	public static final String dummy1 = "para creart peso en harvest";
	public static final String dummy2 = "para creart peso en harvest";
	public static final String dummy3 = "para creart peso en harvest";
	
}
