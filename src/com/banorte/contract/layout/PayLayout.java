
package com.banorte.contract.layout;

import com.banorte.contract.business.PayrateRemote;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.Payrate;
import com.banorte.contract.util.EncryptBd;
import com.banorte.contract.util.Formatter;
import com.banorte.contract.web.EjbInstanceManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
*
* @author Darvy Arch
*************************************************************************************
* ModId	Fecha			ModificadoPor			Descripci�n	de la Modificaci�n
* 001	29/Nov/2010 	Joseles S�nchez			-Se agreg� el consumo del atributo "autoregister" 
* 												 para la generaci�n del layout de N�mina.
* 
* 
* 
* 
*************************************************************************************/

public class PayLayout extends LayoutOperationsTemplate implements Layout {

    private final Integer PRODUCTTYPEID = 2;
    private final String FILLS[] = {"crnumber", "accountnumber", "charge", "commtraddispersal",
                                    "commdispersalanotherbank", "commonlinedispersal",
                                    "commfiletransmition", "comments","payrolltype","packagetype",
                                    "client_fiscalname","client_rfc","bemnumber",
                                    "officerebankingnameComplete","officerebankingempnumber","autoregister",
                                    "folioValidacionNomina","bancaSegmentoNomina"
                                    };
    protected PayrateRemote payRateBean;
    
    public PayLayout(List<Contract> search) {
        for ( Contract record : search ) {
            if( record.getProduct().getProductTypeid().getProductTypeid().intValue() == PRODUCTTYPEID ) {
               this.setContracts(record);
            }
        }

        if ( payRateBean == null ) {
            payRateBean = (PayrateRemote) EjbInstanceManager.getEJB(PayrateRemote.class);
        }
    }
    
    public boolean hasElements() {

        if( this.getContracts().size() > 0 ) {
            return true;
        } else
            return false;
        
    }

    @SuppressWarnings("unchecked")
	public List<String> getContent(Contract contract) throws Exception  {
        List<String> list = new ArrayList();
        Map<String, String> map = new LayoutTempleteContract().bindFrom(contract, FILLS);
        String account="";
        EncryptBd decrypt= new EncryptBd();
        
        if (map.get("accountnumber")!=null && map.get("accountnumber").trim().length()>0){            
                account=decrypt.decrypt(map.get("accountnumber"));              
        }
        
        list.add(contract.getReference());      //	Numero de Folio
        list.add(map.get("client_fiscalname"));       //      Razon Social
        
        list.add(map.get("folioValidacionNomina"));       	//      Folio Filtro de N�mina
        list.add(map.get("bancaSegmentoNomina"));       	//      Banca o Segmento

        //Joseles(20/04/2011): Se elimin� el envi� del bemnumber a peticion de Jose Rivera Escobar(Gerente Op. Medios Electronicos)
        list.add(""); //list.add(map.get("bemnumber"));       //	Numero y Nombre de Emisora

        list.add("");                           //	Nombre reducido Emisora
        list.add(map.get("client_rfc"));              //	RFC de Cliente
        list.add("");                           //	Cambio de estatus de emisora
        list.add("0161");//  0161 8888 9999999999       // Entidad (0161=Banco, 8888=CR Cuenta de Cheques, 9999999999=Cuenta de Cheques)
        list.add(Formatter.fixLenght(map.get("crnumber"), 4));              // CR Number
        list.add(Formatter.fixLenght(account, 10));        // Cuenta de Cheque
        list.add("N");  //  Desglose Mov
        
        //ModId:001
        //list.add("N");   //	Al dispersar se registra la cuenta
        list.add(map.get("autoregister").equals("true")?"S":"N");

        list.add(getRate(map.get("commtraddispersal"), map.get("commdispersalanotherbank"),map.get("packagetype"),1));   //	Tarifa dispersión batch
        list.add(getRate(map.get("commonlinedispersal"), null,map.get("packagetype"),2));   //	Tarifa dispersión línea(RAFAGA)
        list.add("2");  //      Tipo de emisora
        list.add("");   //	Puedan distribuir empleados en diferentes CR's       
        list.add("06");   //	Periodo de meses para depurar relación cuenta-empresa
        
        String payrollType ="4";// ID Solucion Web
        
        /*
         * Se solicito que enviara siempre el valor de 4 
         * if(map.get("payrolltype").equals("Nomina Web"))
        	payrollType="2";
        else if(map.get("payrolltype").equals("Impulso Nomina"))
        	payrollType="4";*/
        	
        
        list.add(payrollType);   //	ID Soluciones Web
        list.add("DEPOSITO ELECTRONICO");   //	Descripción de abono
        list.add("RETIRO DEP. ELECTRONICO");   //	Descripción de cargo
        list.add("DEV. DEPOSITO ELECTRONICO");   //	Descripción de devolución
        list.add("TS1");        // Comision Trans / Suc
        list.add("");   //	Cuenta buzón Banorte.Net
        list.add("N");   //	Genera estado de cuenta
        list.add(map.get("charge").equals("Empleado")?"S":"N");   //	El cobro de tarjeta al empleado
        list.add("S");          //Comisión reposición de tarjeta
        list.add("75.00");      //Comisión reposición de tarjeta
        list.add("N");   //	Comisión emisión de tarjeta S /N
        list.add("0.00");   //	Comisión emisión de tarjeta Cantidad
        list.add("S");   //	Comisión tarjeta adicional
        list.add("75.00");   //	Comisión tarjeta adicional
        list.add("N");   //	Comisión renovación de tarjeta
        list.add("0.00");   //	Comisión renovación de tarjeta
        list.add("S");   //	Comisión por uso de cajero
        list.add("99");   //	Numero de retiros gratis cajero propio
        list.add("0");   //	Comisión retiro cajero propio
        list.add("00");   //	Numero de retiros gratis cajero ajeno
        list.add("19.00");   //	Comisión retiro cajero ajeno
        list.add("00");   //	Numero de retiros gratis cajero extranjero
        list.add("31.5");   //	Comisión retiro cajero extranjero
        list.add("99");   //	Numero de consultas gratis cajero propio
        list.add("0.00");   //	Comisión consulta cajero propio
        list.add("00");   //	Numero de consultas gratis cajero ajeno
        list.add("9.00");   //	Comisión consulta cajero ajeno
        list.add("00");   //	Numero de consultas gratis cajero extranjero
        list.add("31.5");   //	Comisión consulta cajero extranjero
        list.add("00");   //	Numero de estados de cuenta gratis cajero
        list.add("3.00");   //	Comisión estado de cuenta cajero
        list.add("00");   //	Numero de transacciones rechazadas gratis cajero propio banco
        list.add("5.00");   //	Comisión transacción rechazada cajero propio banco
        list.add("00");   //	Numero de transacciones rechazadas gratis cajero ajeno
        list.add("5.00");   //	Comisión transacción rechazada cajero ajeno
        list.add("10021914");   //	Clave de promotor PROMOTOR FICTICIO CAPTACION
        list.add("N");   //	Emisora permite registros duplicados
        list.add("N");   //	Emisora requiere retención especial de saldos
        list.add(map.get("comments"));  //  para escribir las observacones
        list.add(map.get("officerebankingempnumber"));  //  N° de empleado  Ebanking(nuevo)
        list.add(map.get("officerebankingnameComplete")); //Nombre del empleado Ebanking 
        
        return list;
    }
    
    public List<String> getHeader(){
        List<String> list = new ArrayList();
        list.add("Folio Solicitud");      //	Numero de Folio
        list.add("Razon Social");          // Razon Social
        list.add("Folio Filtro de Nomina");          // Folio Filtro de N�mina
        list.add("Banca o Segmento");          // Banca o Segmento
        list.add("Emisora");                           //	Numero y Nombre de Emisora
        list.add("Reducido");                           //	Nombre reducido Emisora
        list.add("RFC");              //	RFC de Cliente
        list.add("Status");                           //	Cambio de estatus de emisora
        list.add("Entidad");//  0161 8888 9999999999 (0161=Banco, 8888=CR Cuenta de Cheques, 9999999999=Cuenta de Cheques)
        list.add("CR");
        list.add("Cta. Cheques");
        list.add("Desglose Mov.");  //  Desglose Mov
        list.add("Registro Aut.");   //	Al dispersar se registra la cuenta
        list.add("Emis. Adeudos Emisora");   //	Tarifa dispersión batch
        list.add("Com Rafaga");   //	Tarifa dispersión línea
        list.add("Tipo Emis");  //      Tipo de emisora
        list.add("Alta CR");   //	Puedan distribuir empleados en diferentes CR's
        list.add("Depuracion");   //	Periodo de meses para depurar relación cuenta-empresa
        list.add("SW");   //	ID Soluciones Web
        list.add("D. Abo.");   //	Descripción de abono
        list.add("D. Car.");   //	Descripción de cargo
        list.add("D. Dev.");   //	Descripción de devolución
        list.add("Com Trans/Suc");   //  Nuevo Operaciones
        list.add("Emi");   //	Cuenta buzón Banorte.Net
        list.add("Ind. Edo. Cta.");   //	Genera estado de cuenta
        list.add("Cob. Tar. Empl.");   //	El cobro de tarjeta al empleado
        list.add("Com. Rep. Tarj.");   //	Comisión reposición de tarjeta
        list.add("Monto Com. Rep. Tarj.");   //	Comisión reposición de tarjeta
        list.add("Com. Emi. Tarj.");   //	Comisión emisión de tarjeta
        list.add("Monto Com. Emi. Tarj.");   //	Comisión emisión de tarjeta
        list.add("Com. Tarj. Adi.");   //	Comisión tarjeta adicional
        list.add("Monto Com. Tarj. Adi.");   //	Comisión tarjeta adicional
        list.add("Com. Ren. Tar.");   //	Comisión renovación de tarjeta
        list.add("Monto Com. Ren. Tar.");   //	Comisión renovación de tarjeta
        list.add("Com. Uso. Cajero");   //	Comisión por uso de cajero
        list.add("Num. Ret. C. Propio");   //	Numero de retiros gratis cajero propio
        list.add("Com. Ret. C. Propio");   //	Comisión retiro cajero propio
        list.add("Num. Ret. C. Ajeno");   //	Numero de retiros gratis cajero ajeno
        list.add("Com. Ret. C. Ajeno");   //	Comisión retiro cajero ajeno
        list.add("Num. Ret. C. Extranjero");   //	Numero de retiros gratis cajero extranjero
        list.add("Com. Ret. C. Extranjero");   //	Comisión retiro cajero extranjero
        list.add("Num. Con. C. Propio");   //	Numero de consultas gratis cajero propio
        list.add("Com. Con. C. Propio");   //	Comisión consulta cajero propio
        list.add("Num. Con. C. Ajeno");   //	Numero de consultas gratis cajero ajeno
        list.add("Com. Con. C. Ajeno");   //	Comisión consulta cajero ajeno
        list.add("Num. Con. C. Extranjero");   //	Numero de consultas gratis cajero extranjero
        list.add("Com. Con. C. Extranjero");   //	Comisión consulta cajero extranjero
        list.add("Num. Edos. de Cta.");   //	Numero de estados de cuenta gratis cajero
        list.add("Com. Gen. Edo. Cta.");   //	Comisión estado de cuenta cajero
        list.add("Num. Trn. Recha. Pro.");   //	Numero de transacciones rechazadas gratis cajero propio banco
        list.add("Com. Trn. Recha. Pro.");   //	Comisión transacción rechazada cajero propio banco
        list.add("Num. Trn. Recha. Aje.");   //	Numero de transacciones rechazadas gratis cajero ajeno
        list.add("Com. Trn. Recha. Aje.");   //	Comisión transacción rechazada cajero ajeno
        list.add("Promotor");   //	Clave de promotor
        list.add("Perm. Reg. Diplic");   //	Emisora permite registros duplicados
        list.add("Retencion Especial");   //	Emisora requiere retención especial de saldos
        list.add("Comentarios");
        list.add("No. Empleado Ebanking");  //  N° de empleado (nuevo)
        list.add("Nombre Empleado Ebanking");  //  Nombre del empleado (nuevo)
        return list;
    }

    private String getRate( String correctB, String correctOB, String packagetype, int typedispersal ) {
        String result = null;

        if (packagetype.equals("Enlace Negocios Basica") || packagetype.equals("Enlace Negocios Avanzada") )
            if (typedispersal==1)
                result="EGL";
            else
                result="077" ;  
        else{
            if ( correctOB != null) {
                Payrate payRate = payRateBean.findByRateData(Double.parseDouble(correctB), Double.parseDouble(correctOB));
                if ( payRate == null ) {
                    result = "NVO|" + correctB + "|" + correctB + "|" + correctOB + "|" + correctOB;
                } else {
                    result = payRate.getPayratePK().getRate();
                }
            } else {
            	if (correctB.equals("4")||correctB.equals("4.0")||correctB.equals("4.00")) {
					result="131";
				}else{
	                List<Payrate> payRates = payRateBean.findByCorrectb(Double.parseDouble(correctB));
	                for ( Payrate payRate : payRates ) {
	                        result = payRate.getPayratePK().getRate();
	                        break;
	                }
	                if ( result == null ) {
	                    result = "NVO|" + correctB + "|" + correctB + "|" + correctOB + "|" + correctOB;
	                }
				}
            }
        }
        return result;
    }
}
