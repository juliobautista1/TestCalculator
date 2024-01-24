
package com.banorte.contract.layout;

import com.banorte.contract.model.Contract;
import com.banorte.contract.util.EncryptBd;
import com.banorte.contract.util.Formatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Darvy Arch
 */
public class BemLayout extends LayoutOperationsTemplate implements Layout {
    private final Integer PRODUCTTYPEID = 1;
    private final String FILLS[] = {"crnumber", "officerrepname_1", "officerreplastname_1",
                                "officerrepmothersln_1", "officerempnumber","selectedbemplan", "accountnumber",
                                "accownnum_1", "accownnum_2", "accownnum_3", "accownnum_4","accownnum_5", 
                                "accownnum_6", "accownnum_7", "accownnum_8", "accownnum_9","accownnum_10",
                                "accownnum_11", "accownnum_12", "accownnum_13", "accownnum_14","accownnum_15", 
                                "accothersnum_1", "accothersnum_2","accothersnum_3", "accothersnum_4", "accothersnum_5",
                                "serialtoken_1", "serialtoken_2", "serialtoken_3","serialtoken_4", "serialtoken_5", "serialtoken_6", 
                                "serialtoken_7", "serialtoken_8", "serialtoken_9","serialtoken_10", "serialtoken_11", "serialtoken_12", 
                                "comments", "quantofadminusers",
                                "clientcontact_nameComplete1","clientcontact_nameComplete2","clientcontact_nameComplete3",
                                "legalrepresentative_nameComplete1","legalrepresentative_nameComplete2",
                                "client_fiscalname","client_rfc","client_sic","client_state","client_city","client_colony",
                                "client_street","client_numext","client_numint","client_zipcode",
                                "client_email","client_areacode","client_phone",
                                "officerebankingnameComplete","officerebankingempnumber","commchargetype",
                                "referencetoken_1","referencetoken_2","referencetoken_3","referencetoken_4",
                                "referencetoken_5","referencetoken_6","referencetoken_7","referencetoken_8",
                                "referencetoken_9","referencetoken_10","referencetoken_11","referencetoken_12"
                                };

    public BemLayout(List<Contract> search) {
         for ( Contract record : search ) {
            if( record.getProduct().getProductTypeid().getProductTypeid().intValue() == PRODUCTTYPEID ) {
                this.setContracts(record);
            }
        }
    }
    
    public boolean hasElements() {

        if( this.getContracts().size() > 0 ) {
            return true;
        } else
            return false;
        
    }
    
    public List<String> getContent(Contract contract) throws Exception {
        List<String> list = new ArrayList();        
        Map<String, String> map = new LayoutTempleteContract().bindFrom(contract, FILLS);
        String account="";
        String accown_1="";
        String accown_2="";
        String accown_3="";
        String accown_4="";
        String accown_5="";
        String accown_6="";
        String accown_7="";
        String accown_8="";
        String accown_9="";
        String accown_10="";
        String accown_11="";
        String accown_12="";
        String accown_13="";
        String accown_14="";
        String accown_15="";
        String accother_1="";
        String accother_2="";
        String accother_3="";
        String accother_4="";
        String accother_5="";
        
        EncryptBd  decrypt= new EncryptBd();
                
        if (map.get("accountnumber")!=null && map.get("accountnumber").trim().length()>0){            
            account=decrypt.decrypt(map.get("accountnumber"));               
        }

        //Desencriptando cuentas propias

        if (map.get("accownnum_1")!=null && map.get("accownnum_1").trim().length()>0){
            accown_1=decrypt.decrypt(map.get("accownnum_1"));                
        }
        
        if (map.get("accownnum_2")!=null && map.get("accownnum_2").trim().length()>0){          
            accown_2=decrypt.decrypt(map.get("accownnum_2"));
        }
        
        if (map.get("accownnum_3")!=null && map.get("accownnum_3").trim().length()>0){
            accown_3=decrypt.decrypt(map.get("accownnum_3"));
        }
        
        if (map.get("accownnum_4")!=null && map.get("accownnum_4").trim().length()>0){            
            accown_4=decrypt.decrypt(map.get("accownnum_4"));
        }
        
        
        if (map.get("accownnum_5")!=null && map.get("accownnum_5").trim().length()>0){
           accown_5=decrypt.decrypt(map.get("accownnum_5"));
        }
        
        if (map.get("accownnum_6")!=null && map.get("accownnum_6").trim().length()>0){
            accown_6=decrypt.decrypt(map.get("accownnum_6"));
        }

        if (map.get("accownnum_7")!=null && map.get("accownnum_7").trim().length()>0){
            accown_7=decrypt.decrypt(map.get("accownnum_7"));
        }

        if (map.get("accownnum_8")!=null && map.get("accownnum_8").trim().length()>0){
            accown_8=decrypt.decrypt(map.get("accownnum_8"));
        }

        if (map.get("accownnum_9")!=null && map.get("accownnum_9").trim().length()>0){
            accown_9=decrypt.decrypt(map.get("accownnum_9"));
        }

        if (map.get("accownnum_10")!=null && map.get("accownnum_10").trim().length()>0){
           accown_10=decrypt.decrypt(map.get("accownnum_10"));
        }

        if (map.get("accownnum_11")!=null && map.get("accownnum_11").trim().length()>0){
            accown_11=decrypt.decrypt(map.get("accownnum_11"));
        }

        if (map.get("accownnum_12")!=null && map.get("accownnum_12").trim().length()>0){
            accown_12=decrypt.decrypt(map.get("accownnum_12"));
        }

        if (map.get("accownnum_13")!=null && map.get("accownnum_13").trim().length()>0){
            accown_13=decrypt.decrypt(map.get("accownnum_13"));
        }

        if (map.get("accownnum_14")!=null && map.get("accownnum_14").trim().length()>0){
            accown_14=decrypt.decrypt(map.get("accownnum_14"));
        }

        if (map.get("accownnum_15")!=null && map.get("accownnum_15").trim().length()>0){
           accown_15=decrypt.decrypt(map.get("accownnum_15"));
        }
        

        //Desencriptando cuentas a terceros
        
        if (map.get("accothersnum_1")!=null && map.get("accothersnum_1").trim().length()>0){
           accother_1=decrypt.decrypt(map.get("accothersnum_1"));
        }
        
        if (map.get("accothersnum_2")!=null && map.get("accothersnum_2").trim().length()>0){
            accother_2=decrypt.decrypt(map.get("accothersnum_2"));
               
        }
        
        if (map.get("accothersnum_3")!=null && map.get("accothersnum_3").trim().length()>0){
            accother_3=decrypt.decrypt(map.get("accothersnum_3"));
        }
        
        if (map.get("accothersnum_4")!=null && map.get("accothersnum_4").trim().length()>0){
            accother_4=decrypt.decrypt(map.get("accothersnum_4"));
        }
        
        if (map.get("accothersnum_5")!=null && map.get("accothersnum_5").trim().length()>0){
            accother_5=decrypt.decrypt(map.get("accothersnum_5"));
        }
        
        
        
        list.add(contract.getReference());      //reference
        list.add(map.get("client_fiscalname"));       //client_fiscalname
        list.add(map.get("client_rfc"));              //client_rfc
        list.add(Formatter.fixLenght(map.get("client_sic"),8));   //client_sic
        list.add("Mexico");                     
        list.add(map.get("client_state"));            //client_state
        list.add(map.get("client_city"));             //client_city
        list.add(map.get("client_colony"));           //client_colony
        list.add(map.get("client_street") + " " + map.get("client_numext") + " " +map.get("client_numint"));  // calle + numero
        list.add(map.get("client_zipcode"));   //client_zipcode        
        list.add(map.get("crnumber"));          //crnumber
        list.add(map.get("officerrepname_1") + " " + map.get("officerreplastname_1") + " " + map.get("officerrepmothersln_1"));//officerrepname_1 + officerreplastname_1 + officerrepmothersln_1
        list.add(map.get("officerempnumber"));
        list.add(map.get("legalrepresentative_nameComplete1")); // legalrepresentative_nameComplete1
        list.add(map.get("legalrepresentative_nameComplete2")); //legalrepresentative_nameComplete2
        list.add(map.get("client_email"));      //client_email
        list.add(map.get("client_areacode") + " " + map.get("client_phone"));//client_areacode+client_phone
        list.add(map.get("selectedbemplan"));   //selectedbemplan 
        list.add(map.get("commchargetype")); //commchargetype tipo cobro comision
        list.add(Formatter.fixLenght(account,10));     //accountnumber        
        
        list.add(map.get("serialtoken_1"));     //serialtoken_1
        list.add(map.get("referencetoken_1"));     //referencetoken_1
        list.add(map.get("serialtoken_2"));     //serialtoken_2
        list.add(map.get("referencetoken_2"));     //referencetoken_2
        list.add(map.get("serialtoken_3"));     //serialtoken_3
        list.add(map.get("referencetoken_3"));     //referencetoken_3
        list.add(map.get("serialtoken_4"));     //serialtoken_4
        list.add(map.get("referencetoken_4"));     //referencetoken_4
        list.add(map.get("serialtoken_5"));     //serialtoken_5
        list.add(map.get("referencetoken_5"));     //referencetoken_5
        list.add(map.get("serialtoken_6"));     //serialtoken_6
        list.add(map.get("referencetoken_6"));     //referencetoken_6        
        list.add(map.get("serialtoken_7"));     //serialtoken_7
        list.add(map.get("referencetoken_7"));     //referencetoken_7
        list.add(map.get("serialtoken_8"));     //serialtoken_8
        list.add(map.get("referencetoken_8"));     //referencetoken_8
        list.add(map.get("serialtoken_9"));     //serialtoken_9
        list.add(map.get("referencetoken_9"));     //referencetoken_9
        list.add(map.get("serialtoken_10"));     //serialtoken_10
        list.add(map.get("referencetoken_10"));     //referencetoken_10
        list.add(map.get("serialtoken_11"));     //serialtoken_11
        list.add(map.get("referencetoken_11"));     //referencetoken_11
        list.add(map.get("serialtoken_12"));     //serialtoken_12
        list.add(map.get("referencetoken_12"));     //referencetoken_12
        list.add(map.get("clientcontact_nameComplete1")); //clientcontact_nameComplete1
        list.add(map.get("clientcontact_nameComplete2")); //clientcontact_nameComplete2
        list.add(map.get("clientcontact_nameComplete3")); //clientcontact_nameComplete3
        list.add(map.get("quantofadminusers"));  //  mancomunados   numero de administradores designados     
        list.add(Formatter.fixLenght(accown_1,10));       //accownnum_1
        list.add(Formatter.fixLenght(accown_2,10));       //accownnum_2
        list.add(Formatter.fixLenght(accown_3,10));       //accownnum_3
        list.add(Formatter.fixLenght(accown_4,10));       //accownnum_4
        list.add(Formatter.fixLenght(accown_5,10));       //accownnum_5
        list.add(Formatter.fixLenght(accown_6,10));       //accownnum_6
        list.add(Formatter.fixLenght(accown_7,10));       //accownnum_7
        list.add(Formatter.fixLenght(accown_8,10));       //accownnum_8
        list.add(Formatter.fixLenght(accown_9,10));       //accownnum_9
        list.add(Formatter.fixLenght(accown_10,10));       //accownnum_10
        list.add(Formatter.fixLenght(accown_11,10));       //accownnum_11
        list.add(Formatter.fixLenght(accown_12,10));       //accownnum_12
        list.add(Formatter.fixLenght(accown_13,10));       //accownnum_13
        list.add(Formatter.fixLenght(accown_14,10));       //accownnum_14
        list.add(Formatter.fixLenght(accown_15,10));       //accownnum_15
        list.add(Formatter.fixLenght(accother_1,10));    //accothersnum_1
        list.add(Formatter.fixLenght(accother_2,10));    //accothersnum_2
        list.add(Formatter.fixLenght(accother_3,10));    //accothersnum_3
        list.add(Formatter.fixLenght(accother_4,10));    //accothersnum_4
        list.add(Formatter.fixLenght(accother_5,10));    //accothersnum_5
        list.add(map.get("comments"));  //  para escribir las observacones
        list.add(map.get("officerebankingempnumber"));  //  N° de empleado  Ebanking(nuevo)
        list.add(map.get("officerebankingnameComplete")); //Nombre del empleado Ebanking 
        
        
        return list;
    }
        
    public List<String> getHeader(){
        List<String> list = new ArrayList();
        list.add("Folio Solicitud");      //reference
        list.add("Razon Social");       //client_fiscalname
        list.add("RFC");              //client_rfc
        list.add("SIC");   //client_sic
        list.add("Pais");                     
        list.add("Estado");            //client_state
        list.add("Ciudad");             //client_city
        list.add("Colonia");           //client_colony
        list.add("Calle");  // calle + numero
        list.add("CP");   //client_zipcode        
        list.add("CR Cliente");          //crnumber
        list.add("Nombre Ejecutivo");
        list.add("No Empleado Ejecutivo");
        list.add("Apoderado 1");
        list.add("Apoderado 2");        
        list.add("Email");      //client_email
        list.add("Telefono");//client_areacode+client_phone
        list.add("Plan");   //selectedbemplan
        list.add("Tipo Cobro Comisión");//commchargetype
        list.add("Cuenta");     //accountnumber        
        list.add("Token 1");     //serialtoken_1
        list.add("Folio 1");     //referencetoken_1
        list.add("Token 2");     //serialtoken_2
        list.add("Folio 2");     //referencetoken_2
        list.add("Token 3");     //serialtoken_3
        list.add("Folio 3");     //referencetoken_3
        list.add("Token 4");     //serialtoken_4
        list.add("Folio 4");     //referencetoken_4
        list.add("Token 5");     //serialtoken_5
        list.add("Folio 5");     //referencetoken_5
        list.add("Token 6");     //serialtoken_6
        list.add("Folio 6");     //referencetoken_6
        list.add("Token 7");     //serialtoken_7
        list.add("Folio 7");     //referencetoken_7
        list.add("Token 8");     //serialtoken_8
        list.add("Folio 8");     //referencetoken_8
        list.add("Token 9");     //serialtoken_9
        list.add("Folio 9");     //referencetoken_9
        list.add("Token 10");     //serialtoken_10
        list.add("Folio 10");     //referencetoken_10
        list.add("Token 11");     //serialtoken_11
        list.add("Folio 11");     //referencetoken_11
        list.add("Token 12");     //serialtoken_12
        list.add("Folio 12");     //referencetoken_12
        list.add("Administrador 1");//officerrepname_1 + officerreplastname_1 + officerrepmothersln_1
        list.add("Administrador 2");//officerrepname_1 + officerreplastname_1 + officerrepmothersln_1
        list.add("Administrador 3");//officerrepname_1 + officerreplastname_1 + officerrepmothersln_1
        list.add("Mancomunado"); //quantofadminusers
        list.add("Cuenta 1");       //accownnum_1
        list.add("Cuenta 2");       //accownnum_2
        list.add("Cuenta 3");       //accownnum_3
        list.add("Cuenta 4");       //accownnum_4
        list.add("Cuenta 5");       //accownnum_5        
        list.add("Cuenta 6");       //accownnum_6
        list.add("Cuenta 7");       //accownnum_7
        list.add("Cuenta 8");       //accownnum_8
        list.add("Cuenta 9");       //accownnum_9
        list.add("Cuenta 10");       //accownnum_10
        list.add("Cuenta 11");       //accownnum_11
        list.add("Cuenta 12");       //accownnum_12
        list.add("Cuenta 13");       //accownnum_13
        list.add("Cuenta 14");       //accownnum_14
        list.add("Cuenta 15");       //accownnum_15
        list.add("Cuenta terceros 1");    //accothersnum_1
        list.add("Cuenta terceros 2");    //accothersnum_2
        list.add("Cuenta terceros 3");    //accothersnum_3
        list.add("Cuenta terceros 4");    //accothersnum_4
        list.add("Cuenta terceros 5");    //accothersnum_5
        list.add("Comentarios");
        list.add("No. Empleado Ebanking");  //  N° de empleado (nuevo)
        list.add("Nombre Empleado Ebanking");  //  Nombre del empleado (nuevo)
        
        return list;
    }
}
