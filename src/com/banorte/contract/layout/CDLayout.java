/**
 * 
 */
package com.banorte.contract.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.banorte.contract.model.Contract;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.AttrConstants;
import com.banorte.contract.util.ContractCDUtil;
import com.banorte.contract.util.FillConstants;
import com.banorte.contract.util.HeaderConstants;

/**
 * @author omar
 *
 */
public class CDLayout extends LayoutOperationsTemplate implements Layout  {
	
	private final Integer PRODUCTTYPEID = 4;
	
	private final String FILLS[] = {AttrConstants.CLIENT_FISCALNAME,AttrConstants.CLIENT_RFC,
			AttrConstants.CR_NUMBER,AttrConstants.AFFILIATION_ACCOUNT_NUMBER_MN,
			AttrConstants.AFFILIATION_AUTOREG,AttrConstants.SELECTED_OPTION_BATCH,
			AttrConstants.SELECTED_OPTION_ONLINE,
			AttrConstants.AFFILIATION_PAYWORKSCLABE,
			AttrConstants.AFFILIATION_HAVEDEPOSITCOMP,AttrConstants.AFFILIATION_DUEDATE,
			AttrConstants.AFFILIATION_DEPOSITAMOUNT,AttrConstants.BATCH_COMISSION_CODE,
			AttrConstants.ONLINE_COMISSION_CODE,AttrConstants.BRANCH_TRANSMISSION,AttrConstants.CLIENT_CATEGORYCODE,
			AttrConstants.COMPLETE_CLIENT_PHONE,AttrConstants.COMPLETE_REPRESENTATIVE_NAME_1, AttrConstants.COMMENTS,
			AttrConstants.RED,
			"issuerName","issuerAccount","issuerStreet","issuerExtNum","issuerIntNum","issuerColony","issuerZipCode",
			"issuerState","issuerCity","issuerContactEmail","issuerContactName","issuerContactAreaCode","issuerContactPhone",
			"issuerCategoryCode","officerebankingEspEmpnumber","officerebankingEspNameComplete","officerebankingTempnumber","officerebankingTnameComplete",
			"officerempnumber","officernameComplete","officerebankingempnumber","officerebankingnameComplete","officerrepempnumber_1",
			"officerrepnameComplete1","officerrepempnumber_2","officerrepnameComplete2","branchDirectornumber","branchDirectorNameComplete"
			};
    
	public CDLayout(List<Contract> search) {
        for ( Contract record : search ) {
           if( record.getProduct().getProductTypeid().getProductTypeid().intValue() ==  PRODUCTTYPEID) {
               this.setContracts(record);
           }
       }
   }

	@Override
	public boolean hasElements() {
        if( this.getContracts().size() > 0 ) {
            return true;
        } else
            return false;
        
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> getContent(Contract contract) throws Exception {
		List<String> list 			= new ArrayList();        
        Map<String, String> map 	= new LayoutTempleteContract().bindFrom(contract, FILLS);
        String payworksStr			= ApplicationConstants.EMPTY_STRING;
        String payworksValue 		= ApplicationConstants.EMPTY_STRING;
        ContractCDUtil util 		= new ContractCDUtil();
       
        
        list.add(contract.getReference());      
        list.add(map.get(AttrConstants.CLIENT_FISCALNAME)); 
        list.add(FillConstants.STATIC_COLUMN_EMPTY);     						//EMISORA   COLUMNA 3
        list.add(util.subStringUtil( map.get(AttrConstants.CLIENT_FISCALNAME) ) );
        list.add(map.get(AttrConstants.CLIENT_RFC));
        list.add(FillConstants.STATIC_CODIGO_5);
        list.add(map.get(AttrConstants.CR_NUMBER) );
        list.add(map.get(AttrConstants.AFFILIATION_ACCOUNT_NUMBER_MN) );
        list.add(FillConstants.STATIC_DESGLOSEMOV_8);
        list.add(FillConstants.STATIC_COLUMN_EMPTY);  							// C. Aper 	COLUMNA 9
        list.add(util.transformSN(map.get(AttrConstants.AFFILIATION_AUTOREG)));
        list.add(FillConstants.STATIC_PYME_11);
        list.add( map.get(  AttrConstants.BATCH_COMISSION_CODE) );  			// EMIS. ADEUDOS EMISORA(BATCH)  COLUMNA 12 
        list.add(FillConstants.STATIC_DIASRET_13);
        list.add(FillConstants.STATIC_COLUMN_EMPTY);   							// Act.plastico (A/M)  COLUMNA 14 
        list.add(FillConstants.STATIC_NUMREIN_15);
        list.add( map.get( AttrConstants.ONLINE_COMISSION_CODE) );   			// COM RAFAGA(LINEA)  COLUMNA 16
        list.add(FillConstants.STATIC_VERIFCUENTAS_17);
        list.add(FillConstants.STATIC_TIPO_EMISION_PRIV); 						// Tipo Emis  COLUMNA 18
        list.add(FillConstants.STATIC_COLUMN_EMPTY);   							// Alta Cr  COLUMNA 19
        list.add(FillConstants.STATIC_DEPURACION_20);   
        list.add(FillConstants.STATIC_FORMATORESP_21);   
        list.add(FillConstants.STATIC_SW_22);
        list.add(FillConstants.STATIC_COLUMN_EMPTY);  							// D. Abo.  COLUMNA 23
        list.add(FillConstants.STATIC_COM_TRANS_SUC_24 );  						// Com Trans/Suc  COLUMNA 24
        list.add(FillConstants.STATIC_RECIPROCID_25); 
        list.add(FillConstants.STATIC_COLUMN_EMPTY);  							// D. Car.  COLUMNA 26
        list.add( map.get("officerebankingEspEmpnumber") );
        list.add( map.get("officerebankingEspNameComplete") );
        list.add( map.get("officerebankingTempnumber") );
        list.add( map.get("officerebankingTnameComplete") );
        list.add( map.get("officerempnumber") );
        list.add( map.get("officernameComplete") );
        list.add( map.get("officerebankingempnumber") );
        list.add( map.get("officerebankingnameComplete") );
        list.add( map.get("officerrepempnumber_1") );
        list.add( map.get("officerrepnameComplete1") );
        list.add( map.get("officerrepempnumber_2") );
        list.add( map.get("officerrepnameComplete2") );
        list.add( map.get(AttrConstants.COMPLETE_REPRESENTATIVE_NAME_1) );		// Nom. Con  COLUMNA 27
        list.add(FillConstants.STATIC_COLUMN_EMPTY); 								// D. Dev  COLUMNA 28
        list.add( map.get(AttrConstants.COMPLETE_CLIENT_PHONE) );  				// Telefono.  COLUMNA 29
        list.add(FillConstants.STATIC_VALUE_0_00);  							// Comison por NTF.  COLUMNA 30
        list.add(FillConstants.STATIC_VALUE_0_00);  							// BXI  COLUMNA 31
        list.add(FillConstants.STATIC_VALUE_0_00);  							// BEM  COLUMNA 32
        list.add(FillConstants.STATIC_COLUMN_EMPTY);  							// Emi  COLUMNA 33
        list.add(FillConstants.STATIC_MMAX_34);  
        list.add(FillConstants.STATIC_COLUMN_EMPTY);  							// Contacto  COLUMNA 35
        list.add(FillConstants.STATIC_COLUMN_EMPTY);  							// Afiliación  COLUMNA 36
        
        payworksValue = map.get(AttrConstants.AFFILIATION_PAYWORKSCLABE);
        if( payworksValue.equals( ApplicationConstants.VALUE_TRUE)){
        	payworksStr = ApplicationConstants.LAYOUT_VALUE_PAYWORKS;
        }
        list.add(payworksStr);
        list.add(map.get(AttrConstants.AFFILIATION_HAVEDEPOSITCOMP));
        list.add(map.get(AttrConstants.AFFILIATION_DUEDATE));
        list.add(map.get(AttrConstants.AFFILIATION_DEPOSITAMOUNT));
        list.add(map.get(AttrConstants.CLIENT_CATEGORYCODE));  		// Desc. Bien o Serv  COLUMNA 41
        list.add(map.get( AttrConstants.COMMENTS) );  		// Obser.  COLUMNA 42
        String red=map.get(AttrConstants.RED);
        if("1".equalsIgnoreCase(red)){
        	list.add("Ixe");
        }else{//nulo o 0
        	list.add("Banorte");
        }
        list.add(map.get("issuerName"));
        list.add(map.get("issuerAccount"));
        list.add(map.get("issuerStreet"));
        list.add(map.get("issuerExtNum"));
        list.add(map.get("issuerIntNum"));
        list.add(map.get("issuerColony"));
        list.add(map.get("issuerZipCode"));
        list.add(map.get("issuerCity"));
        list.add(map.get("issuerState"));
        list.add(map.get("issuerContactEmail"));
        list.add(map.get("issuerContactName"));
        list.add(map.get("issuerContactAreaCode"));
        list.add(map.get("issuerContactPhone"));
        list.add(map.get("issuerCategoryCode"));
        list.add( map.get("branchDirectornumber") );
        list.add( map.get("branchDirectorNameComplete") );
        return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> getHeader() {
		List<String> list = new ArrayList();
		list.add(HeaderConstants.FOLIO);
		list.add(HeaderConstants.NOMBRECOMERCIAL+"/"+HeaderConstants.RAZONSOCIAL);
		list.add(HeaderConstants.EMISORA);
		list.add(HeaderConstants.NOMBRE_REDUCIDO);
		list.add(HeaderConstants.RFC);
		list.add(HeaderConstants.CODIGO);
		list.add(HeaderConstants.CR);
		list.add(HeaderConstants.CTA_CARGO_ABONO);
		list.add(HeaderConstants.DESGLOSE_MOV);
		list.add(HeaderConstants.C_APER);
		list.add(HeaderConstants.REGISTRO_AUTOMATICO);
		list.add(HeaderConstants.PYME);
		list.add(HeaderConstants.ADEUDOS_EMISORAS);
		list.add(HeaderConstants.DIAS_RET);
		list.add(HeaderConstants.ACT_PLASTICO);
		list.add(HeaderConstants.REINTENTOS);
		list.add(HeaderConstants.COM_RAFAGA);
		list.add(HeaderConstants.VERIF_CUENTAS);
		list.add(HeaderConstants.TIPO_EMIS);
		list.add(HeaderConstants.ALTA_CR);
		list.add(HeaderConstants.DEPURACION);
		list.add(HeaderConstants.FORMATO_RESPUESTA);
		list.add(HeaderConstants.SW);
		list.add(HeaderConstants.D_ABO);
		list.add(HeaderConstants.COM_TRANS_SUC);
		list.add(HeaderConstants.RECIPROCIDD);
		list.add(HeaderConstants.D_CAR);
		list.add(HeaderConstants.NO_EMP_BANCA_ESPECIALIZADA);
		list.add(HeaderConstants.NOM_EMP_BANCA_ESPECIALIZADA);
		list.add(HeaderConstants.NO_EMP_DIRECTOR_TERRITORIAL);
		list.add(HeaderConstants.NOM_EMP_DIRECTOR_TERRITORIAL);
		list.add(HeaderConstants.NO_EMP_FUNCIONARIO_COLOCO);
		list.add(HeaderConstants.NOM_EMP_FUNCIONARIO_COLOCO);
		list.add(HeaderConstants.NO_EMP_FUNCIONARIO_EBANKIG);
		list.add(HeaderConstants.NOM_EMP_FUNCIONARIO_EBANKIG);
		list.add(HeaderConstants.NO_EMP_REP_LEGAL_1);
		list.add(HeaderConstants.NOM_EMP_REP_LEGAL_1);
		list.add(HeaderConstants.NO_EMP_REP_LEGAL_2);
		list.add(HeaderConstants.NOM_EMP_REP_LEGAL_2);
		list.add(HeaderConstants.NOM_CON);
		list.add(HeaderConstants.D_DEV);
		list.add(HeaderConstants.TELEFONO);
		list.add(HeaderConstants.COMISION_NTF);
		list.add(HeaderConstants.BXI);
		list.add(HeaderConstants.BEM);
		list.add(HeaderConstants.EMI);
		list.add(HeaderConstants.MMAX);
		list.add(HeaderConstants.CONTACTO);
		list.add(HeaderConstants.AFILIACION);
		list.add(HeaderConstants.PAYWORKS);
		list.add(HeaderConstants.FIANZA);
		list.add(HeaderConstants.VENCIMIENTO);
		list.add(HeaderConstants.IMP_FIANZA);
		list.add(HeaderConstants.DESC_BIEN_SERV);
		list.add("Justificación de excepcion de fianza");
		list.add("Red");
		list.add("Nombre emisora");
		list.add("No. Cuenta emisora");
		list.add("Calle domicilio emisora");
		list.add("Num. Ext. domicilio emisora");
		list.add("Num. Int. domicilio emisora");
		list.add("Colonia domicilio emisora");
		list.add("CP domicilio emisora");
		list.add("Poblacion domicilio emisora");
		list.add("Estado domicilio emisora");
		list.add("Correo emisora");
		list.add("Nombre contacto emisora");
		list.add("Lada contacto emisora");
		list.add("Telefono contacto emisora");
		list.add("Giro Emisora");
		list.add("Numero Director Sucursal");
		list.add("Nombre Director Sucursal");
        return list;
	}

}
