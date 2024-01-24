
package com.banorte.contract.web;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import com.banorte.contract.model.Attribute;
import com.banorte.contract.model.AttributeOption;
import com.banorte.contract.model.Contract;
import com.banorte.contract.model.ContractAttribute;
import com.banorte.contract.model.ContractMessageErrors;
import com.banorte.contract.model.Template;
import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.AttributeOptionComparator;
import com.banorte.contract.util.EncryptBd;
import com.banorte.contract.util.pdf.PdfTemplateBinding;
import com.banorte.contract.util.pdf.PdfTemplateBindingContract;

/**
 *
 * @author MRIOST
 */
public class ContractBemMB extends ContractAbstractMB {

    private static Logger log = Logger.getLogger(ContractBemMB.class.getName());
    private String selectedbemplan;
    private SelectItem[] selectedbemplanArray;
    private String quanttokens;
    private SelectItem[] quanttokensArray;
    private String accountnumber;
    private String commchargetype;
    private SelectItem[] commchargetypeArray;
    private String quantaccown;
    private SelectItem[] quantaccownArray;
    private String quantaccothers;
    private SelectItem[] quantaccothersArray;
    private String serialtoken_1;
    private String emailtoken_1;
    private String serialtoken_2;
    private String emailtoken_2;
    private String serialtoken_3;
    private String emailtoken_3;
    private String serialtoken_4;
    private String emailtoken_4;
    private String serialtoken_5;
    private String emailtoken_5;
    private String serialtoken_6;
    private String emailtoken_6;    
    private String serialtoken_7;
    private String emailtoken_7;
    private String serialtoken_8;
    private String emailtoken_8;
    private String serialtoken_9;
    private String emailtoken_9;
    private String serialtoken_10;
    private String emailtoken_10;
    private String serialtoken_11;
    private String emailtoken_11;
    private String serialtoken_12;
    private String emailtoken_12;
    
    private String referencetoken_1;
    private String referencetoken_2;
    private String referencetoken_3;
    private String referencetoken_4;
    private String referencetoken_5;
    private String referencetoken_6;
    
    private String referencetoken_7;
    private String referencetoken_8;
    private String referencetoken_9;
    private String referencetoken_10;
    private String referencetoken_11;
    private String referencetoken_12;

    private String accownnum_1;
    private String accownmerchantname_1;
    private String accownname_1;
    private String accownnum_2;
    private String accownmerchantname_2;
    private String accownname_2;
    private String accownnum_3;
    private String accownmerchantname_3;
    private String accownname_3;
    private String accownnum_4;
    private String accownmerchantname_4;
    private String accownname_4;
    private String accownnum_5;
    private String accownmerchantname_5;
    private String accownname_5;
    private String accownnum_6;
    private String accownmerchantname_6;
    private String accownname_6;
    private String accownnum_7;
    private String accownmerchantname_7;
    private String accownname_7;
    private String accownnum_8;
    private String accownmerchantname_8;
    private String accownname_8;
    private String accownnum_9;
    private String accownmerchantname_9;
    private String accownname_9;
    private String accownnum_10;
    private String accownmerchantname_10;
    private String accownname_10;

  //Nuevos
    private String accownnum_11;
    private String accownmerchantname_11;
    private String accownname_11;
    private String accownnum_12;
    private String accownmerchantname_12;
    private String accownname_12;
    private String accownnum_13;
    private String accownmerchantname_13;
    private String accownname_13;
    private String accownnum_14;
    private String accownmerchantname_14;
    private String accownname_14;
    private String accownnum_15;
    private String accownmerchantname_15;
    private String accownname_15;


    private String accothersnum_1;
    private String accothersofficername_1;
    private String accotherscr_1;
    private String accothersbranch_1;
    private String accothersnum_2;
    private String accothersofficername_2;
    private String accotherscr_2;
    private String accothersbranch_2;
    private String accothersnum_3;
    private String accothersofficername_3;
    private String accotherscr_3;
    private String accothersbranch_3;
    private String accothersnum_4;
    private String accothersofficername_4;
    private String accotherscr_4;
    private String accothersbranch_4;
    private String accothersnum_5;
    private String accothersofficername_5;
    private String accotherscr_5;
    private String accothersbranch_5;
    private String accothersnum_6;
    private String accothersofficername_6;
    private String accotherscr_6;
    private String accothersbrach_6;
    private String accothersnum_7;
    private String accothersofficername_7;
    private String accotherscr_7;
    private String accothersbranch_7;
    private String accothersnum_8;
    private String accothersofficername_8;
    private String accotherscr_8;
    private String accothersbranch_8;
    private String accothersnum_9;
    private String accothersofficername_9;
    private String accotherscr_9;
    private String accothersbranch_9;
    private String accothersnum_10;
    private String accothersofficername_10;
    private String accotherscr_10;
    private String accothersbranch_10;

    private String NAME_TEMPLATE ="nameTemplate";
    private String PATHTEMPLATE ="pathTemplate";

    private PdfTemplateBindingContract pdfTemplateBinding;
    
    //Campos agregados
    private String accothersname_1;
    private String accothersname_2;
    private String accothersname_3;
    private String accothersname_4;
    private String accothersname_5;
    private String quantofadminusers;
    public EncryptBd encrypt;
    //private String operationofadminusers;
    
    //Banderas  Tokens
    private boolean tokenflag[] = new boolean[12];
    
    //Banderas Cuentas propias    
    private boolean accownflag[] = new boolean[15];  
    
    //Banderas Cuentas Terceros
    private boolean accotherflag[] = new boolean[5];

    private boolean loadflag;
    
    private ArrayList<ContractMessageErrors> ordenErrorsList= new ArrayList();
    
    //Joseles(04/Nov/2011): Variables para requerimiento: Cambio de Formatos BEM 2011
    private SelectItem[] regimenArray;
    private String regimen_1;
    private String regimen_2;
    private String regimen_3;
    private String regimen_4;
    private String regimen_5;
    private String regimen_6;    
    private String regimen_7;
    private String regimen_8;
    private String regimen_9;
    private String regimen_10;
    private String regimen_11;
    private String regimen_12;
    private String regimen_13;
    private String regimen_14;
    private String regimen_15;
    
    public ContractBemMB() {
        super();
        encrypt= new EncryptBd();
        setStatusContract(statusBean.findById(new Integer(1))); // Status Nuevo = 1

        //Banderas tokens
        tokenflag[0]=true;
        tokenflag[1]=true;
        for (int i=2; i<=11; i++){
         tokenflag[i]=false;
        }
        //Banderas Cuentas propias
        accownflag[0]=true;
        for (int i=1; i<=14; i++){
            accownflag[i]=false;
        }
        
        //Banderas Cuentas terceros        
        for (int i=0; i<=4; i++){
            accotherflag[i]=false;
        }

        loadflag=false;
    }

    @Override
    public boolean savePartialContract() {
        //TODO ARMAR LA COLECCION DE CONTRACTATTRIBUTE, ASIGNARLA AL CONTRACT Y GUARDARLO
        Contract contract = getContract();
        
        ArrayList<ContractAttribute> list = new ArrayList<ContractAttribute>();
        
       //cuentas propias y cuentas terceros       
        Integer accowns= Integer.parseInt(this.getQuantaccown());
        Integer accothers= Integer.parseInt(this.getQuantaccothers());

        addToList(list,"selectedbemplan", getSelectedbemplan());
        addToList(list,"quanttokens", getQuanttokens());
        addToList(list,"accountnumber", getAccountnumberEncrypt());
        addToList(list,"commchargetype", getCommchargetype());
        addToList(list,"quantaccown", getQuantaccown());
        addToList(list,"quantaccothers", getQuantaccothers());
        addToList(list,"serialtoken_1", getSerialtoken_1());
        addToList(list,"emailtoken_1", getEmailtoken_1());
        addToList(list,"serialtoken_2", getSerialtoken_2());
        addToList(list,"emailtoken_2", getEmailtoken_2());
        addToList(list,"serialtoken_3", getSerialtoken_3());
        addToList(list,"emailtoken_3", getEmailtoken_3());
        addToList(list,"serialtoken_4", getSerialtoken_4());
        addToList(list,"emailtoken_4", getEmailtoken_4());
        addToList(list,"serialtoken_5", getSerialtoken_5());
        addToList(list,"emailtoken_5", getEmailtoken_5());
        addToList(list,"serialtoken_6", getSerialtoken_6());
        addToList(list,"emailtoken_6", getEmailtoken_6()); 
        addToList(list,"serialtoken_7", getSerialtoken_7());
        addToList(list,"emailtoken_7", getEmailtoken_7());
        addToList(list,"serialtoken_8", getSerialtoken_8());
        addToList(list,"emailtoken_8", getEmailtoken_8());
        addToList(list,"serialtoken_9", getSerialtoken_9());
        addToList(list,"emailtoken_9", getEmailtoken_9());
        addToList(list,"serialtoken_10", getSerialtoken_10());
        addToList(list,"emailtoken_10", getEmailtoken_10());
        addToList(list,"serialtoken_11", getSerialtoken_11());
        addToList(list,"emailtoken_11", getEmailtoken_11());
        addToList(list,"serialtoken_12", getSerialtoken_12());
        addToList(list,"emailtoken_12", getEmailtoken_12());        
        //Campos Agregados
        addToList(list,"referencetoken_1", getReferencetoken_1());
        addToList(list,"referencetoken_2", getReferencetoken_2());
        addToList(list,"referencetoken_3", getReferencetoken_3());
        addToList(list,"referencetoken_4", getReferencetoken_4());
        addToList(list,"referencetoken_5", getReferencetoken_5());
        addToList(list,"referencetoken_6", getReferencetoken_6()); 
        addToList(list,"referencetoken_7", getReferencetoken_7());
        addToList(list,"referencetoken_8", getReferencetoken_8());
        addToList(list,"referencetoken_9", getReferencetoken_9());
        addToList(list,"referencetoken_10", getReferencetoken_10());
        addToList(list,"referencetoken_11", getReferencetoken_11());
        addToList(list,"referencetoken_12", getReferencetoken_12()); 
        addToList(list,"accownnum_1", getAccountnumberEncrypt());       
        addToList(list,"accownmerchantname_1", getClient_fiscalname());
        addToList(list,"accownname_1", getAccownname_1());
        addToList(list,"accownnum_2", getAccownnumEncrypt_2());
        addToList(list,"accownmerchantname_2", accowns>=2?getClient_fiscalname():"");
        addToList(list,"accownname_2", getAccownname_2());
        addToList(list,"accownnum_3", getAccownnumEncrypt_3());
        addToList(list,"accownmerchantname_3", accowns>=3?getClient_fiscalname():"");
        addToList(list,"accownname_3", getAccownname_3());
        addToList(list,"accownnum_4", getAccownnumEncrypt_4());
        addToList(list,"accownmerchantname_4", accowns>=4?getClient_fiscalname():"");
        addToList(list,"accownname_4", getAccownname_4());
        addToList(list,"accownnum_5", getAccownnumEncrypt_5());
        addToList(list,"accownmerchantname_5", accowns>=5?getClient_fiscalname():"");
        addToList(list,"accownname_5", getAccownname_5());
        addToList(list,"accownnum_6", getAccownnumEncrypt_6());
        addToList(list,"accownmerchantname_6", accowns>=6?getClient_fiscalname():"");
        addToList(list,"accownname_6", getAccownname_6());
        addToList(list,"accownnum_7", getAccownnumEncrypt_7());
        addToList(list,"accownmerchantname_7", accowns>=7?getClient_fiscalname():"");
        addToList(list,"accownname_7", getAccownname_7());
        addToList(list,"accownnum_8", getAccownnumEncrypt_8());
        addToList(list,"accownmerchantname_8", accowns>=8?getClient_fiscalname():"");
        addToList(list,"accownname_8", getAccownname_8());
        addToList(list,"accownnum_9", getAccownnumEncrypt_9());
        addToList(list,"accownmerchantname_9", accowns>=9?getClient_fiscalname():"");
        addToList(list,"accownname_9", getAccownname_9());
        addToList(list,"accownnum_10", getAccownnumEncrypt_10());
        addToList(list,"accownmerchantname_10", accowns>=10?getClient_fiscalname():"");
        addToList(list,"accownname_10", getAccownname_10());
        addToList(list,"accownnum_11", getAccownnumEncrypt_11());
        addToList(list,"accownmerchantname_11", accowns>=11?getClient_fiscalname():"");
        addToList(list,"accownname_11", getAccownname_11());
        addToList(list,"accownnum_12", getAccownnumEncrypt_12());
        addToList(list,"accownmerchantname_12", accowns>=12?getClient_fiscalname():"");
        addToList(list,"accownname_12", getAccownname_12());
        addToList(list,"accownnum_13", getAccownnumEncrypt_13());
        addToList(list,"accownmerchantname_13", accowns>=13?getClient_fiscalname():"");
        addToList(list,"accownname_13", getAccownname_13());
        addToList(list,"accownnum_14", getAccownnumEncrypt_14());
        addToList(list,"accownmerchantname_14", accowns>=14?getClient_fiscalname():"");
        addToList(list,"accownname_14", getAccownname_14());
        addToList(list,"accownnum_15", getAccownnumEncrypt_15());
        addToList(list,"accownmerchantname_15", accowns==15?getClient_fiscalname():"");
        addToList(list,"accownname_15", getAccownname_15());
        addToList(list,"accothersnum_1", getAccothersnumEncrypt_1());
        addToList(list,"accothersofficername_1", getAccothersofficername_1());
        addToList(list,"accotherscr_1", getAccotherscr_1());
        addToList(list,"accothersbranch_1", getAccothersbranch_1());
        addToList(list,"accothersnum_2", getAccothersnumEncrypt_2());
        addToList(list,"accothersofficername_2", getAccothersofficername_2());
        addToList(list,"accotherscr_2", getAccotherscr_2());
        addToList(list,"accothersbranch_2", getAccothersbranch_2());
        addToList(list,"accothersnum_3", getAccothersnumEncrypt_3());
        addToList(list,"accothersofficername_3", getAccothersofficername_3());
        addToList(list,"accotherscr_3", getAccotherscr_3());
        addToList(list,"accothersbranch_3", getAccothersbranch_3());
        addToList(list,"accothersnum_4", getAccothersnumEncrypt_4());
        addToList(list,"accothersofficername_4", getAccothersofficername_4());
        addToList(list,"accotherscr_4", getAccotherscr_4());
        addToList(list,"accothersbranch_4", getAccothersbranch_4());
        addToList(list,"accothersnum_5", getAccothersnumEncrypt_5());
        addToList(list,"accothersofficername_5", getAccothersofficername_5());
        addToList(list,"accotherscr_5", getAccotherscr_5());
        addToList(list,"accothersbranch_5", getAccothersbranch_5());
       // Campos Agregados
        addToList(list,"accothersname_1", getAccothersname_1());
        addToList(list,"accothersname_2", accothers>=2?getAccothersname_1():"");
        addToList(list,"accothersname_3", accothers>=3?getAccothersname_1():"");
        addToList(list,"accothersname_4", accothers>=4?getAccothersname_1():"");
        addToList(list,"accothersname_5", accothers==5?getAccothersname_1():"");        
        addToList(list,"celebrationplace", getCelebrationplace());
        addToList(list,"celebrationstate", getCelebrationstate());
        addToList(list,"celebrationdate",getCelebrationdate()!=null?getCelebrationdate():"");
        addToList(list,"applicationdate",getApplicationdate()!=null?getCelebrationdate():"");
        addToList(list,"client_merchantname", getClient_merchantname());
        addToList(list,"client_sic", getClient_sic());
        addToList(list,"client_areacode", getClient_areacode().toString());
        addToList(list,"client_phone", getClient_phone().toString());
        addToList(list,"client_phoneext", getClient_phoneext().toString());
        addToList(list,"client_street", getClient_street());
        addToList(list,"client_numint", getClient_numint());
        addToList(list,"client_numext", getClient_numext());
        addToList(list,"client_colony", getClient_colony());
        addToList(list,"client_city", getClient_city());
        addToList(list,"client_state",getClient_state());
        addToList(list,"client_zipcode", getClient_zipcode().toString());
        addToList(list,"client_rfc", getClient_rfc());
        addToList(list,"client_email", getClient_email());
        addToList(list,"client_fiscalname", getClient_fiscalname());
        addToList(list,"client_fiscaltype", getClient_fiscaltype() != null ? getClient_fiscaltype().toString() : "");
        addToList(list,"legalrepresentative_name_1", getLegalrepresentative_name_1());
        addToList(list,"legalrepresentative_lastname_1", getLegalrepresentative_lastname_1());
        addToList(list,"legalrepresentative_mothersln_1", getLegalrepresentative_mothersln_1());
        addToList(list,"legalrepresentative_name_2", getLegalrepresentative_name_2());
        addToList(list,"legalrepresentative_lastname_2", getLegalrepresentative_lastname_2());
        addToList(list,"legalrepresentative_mothersln_2", getLegalrepresentative_mothersln_2());
        addToList(list,"clientcontact_name1", getClientcontact_name1());
        addToList(list,"clientcontact_lastname1", getClientcontact_lastname1());
        addToList(list,"clientcontact_mothersln1", getClientcontact_mothersln1());
        addToList(list,"clientcontact_position1", getClientcontact_position1());
        addToList(list,"clientcontact_phone1", getClientcontact_phone1()!=null?getClientcontact_phone1().toString():"");
        addToList(list,"clientcontact_phoneext1", getClientcontact_phoneext1()!=null?getClientcontact_phoneext1().toString():"");
        addToList(list,"clientcontact_email1", getClientcontact_email1());
        addToList(list,"clientcontact_name2", getClientcontact_name2());
        addToList(list,"clientcontact_lastname2", getClientcontact_lastname2());
        addToList(list,"clientcontact_mothersln2", getClientcontact_mothersln2());
        addToList(list,"clientcontact_position2", getClientcontact_position2());
        addToList(list,"clientcontact_phone2", getClientcontact_phone2()!=null?getClientcontact_phone2().toString():"");
        addToList(list,"clientcontact_phoneext2", getClientcontact_phoneext2()!=null?getClientcontact_phoneext2().toString():"");
        addToList(list,"clientcontact_email2", getClientcontact_email2());
        addToList(list,"clientcontact_name3", getClientcontact_name3());
        addToList(list,"clientcontact_lastname3", getClientcontact_lastname3());
        addToList(list,"clientcontact_mothersln3", getClientcontact_mothersln3());
        addToList(list,"clientcontact_position3", getClientcontact_position3());
        addToList(list,"clientcontact_phone3", getClientcontact_phone3()!=null?getClientcontact_phone3().toString():"");
        addToList(list,"clientcontact_phoneext3", getClientcontact_phoneext3()!=null?getClientcontact_phoneext3().toString():"");
        addToList(list,"clientcontact_email3", getClientcontact_email3());

        addToList(list,"officername", getOfficername());
        addToList(list,"officerlastname", getOfficerlastname());
        addToList(list,"officermothersln", getOfficermothersln());
        addToList(list,"officerempnumber", getOfficerempnumber());
        addToList(list,"officerposition", getOfficerposition());
        
        addToList(list,"officerebankingname", getOfficerebankingname());
        addToList(list,"officerebankinglastname", getOfficerebankinglastname());
        addToList(list,"officerebankingmothersln", getOfficerebankingmothersln());
        addToList(list,"officerebankingempnumber", getOfficerebankingempnumber());

        addToList(list,"crnumber", getCrnumber());
        addToList(list,"branchname", getBranchname());
        addToList(list,"branchstreet", getBranchstreet());
        addToList(list,"branchcolony", getBranchcolony());
        addToList(list,"branchcounty", getBranchcounty());
        addToList(list,"branchcity", getBranchcity());
        addToList(list,"branchstate", getBranchstate());
        addToList(list,"branchphone", getBranchphone());
        addToList(list,"branchfax", getBranchfax());
        addToList(list,"bankingsector", getBankingsector());

        addToList(list,"officerrepname_1", getOfficerrepname_1());
        addToList(list,"officerreplastname_1", getOfficerreplastname_1());
        addToList(list,"officerrepmothersln_1", getOfficerrepmothersln_1());
        addToList(list,"officerrepempnumber_1", getOfficerrepempnumber_1());
        addToList(list,"officerrepposition_1", getOfficerrepposition_1());

        addToList(list,"officerrepname_2", getOfficerrepname_2());
        addToList(list,"officerreplastname_2", getOfficerreplastname_2());
        addToList(list,"officerrepmothersln_2", getOfficerrepmothersln_2());
        addToList(list,"officerrepempnumber_2", getOfficerrepempnumber_2());
        addToList(list,"officerrepposition_2", getOfficerrepposition_2());

        addToList(list,"celebrationCompletePlaceState",getCelebrationplace() + ", " + getCelebrationstate());
        addToList(list,"celebrationComplete",getCelebrationplace() + ", " + getCelebrationstate() + " A " + getCelebrationdate());
        addToList(list,"officernameComplete",getOfficername() +  " "+ getOfficerlastname() + " " + getOfficermothersln());
        addToList(list,"officerebankingnameComplete",getOfficerebankingname() +  " "+ getOfficerebankinglastname() + " " + getOfficerebankingmothersln());        
        addToList(list,"branchadressComplete",getBranchstreet() + ", Col. "+ getBranchcolony()+ ", " + getBranchcity() + ", " + getBranchstate()+ "." );
        addToList(list,"branchnameComplete", getCrnumber() + " " + getBranchname());

        addToList(list,"officerrepnameComplete1", getOfficerrepname_1() + " " + getOfficerreplastname_1() + " " + getOfficerrepmothersln_1() );
        addToList(list,"officerrepnameComplete2", getOfficerrepname_2() + " " + getOfficerreplastname_2() + " " + getOfficerrepmothersln_2());

        addToList(list,"clientcontact_nameComplete1",getClientcontact_name1() + " " + getClientcontact_lastname1() + " "  + getClientcontact_mothersln1());        
        int quant=Integer.valueOf(quantofadminusers).intValue();
        addToList(list,"clientcontact_nameComplete2", quant>1 ? (getClientcontact_name2() + " " + getClientcontact_lastname2() + " "  + getClientcontact_mothersln2()):"");               
        addToList(list,"clientcontact_nameComplete3", quant>2 ? (getClientcontact_name3() + " " + getClientcontact_lastname3() + " "  + getClientcontact_mothersln3()):"");
        
        addToList(list,"client_phoneComplete","("+ (getClient_areacode()) + ") " + (getClient_phone()) + (getClient_phoneext()!=null?" Ext. " + getClient_phoneext().toString():"" ));
        addToList(list,"client_faxComplete","("+ (getClient_areacode())+ ") " + (getClient_fax()) + (getClient_faxext()!=null?" Ext. " + getClient_faxext().toString():"" ));
        addToList(list,"client_stateComplete",getClient_city() + ", " +  getClient_state());
        addToList(list,"legalrepresentative_nameComplete1",getLegalrepresentative_name_1() + " " + getLegalrepresentative_lastname_1() + " " + getLegalrepresentative_mothersln_1());
        addToList(list,"legalrepresentative_nameComplete2",getLegalrepresentative_name_2() + " " + getLegalrepresentative_lastname_2() + " " + getLegalrepresentative_mothersln_2());
        StringBuffer buffer = new StringBuffer();
        buffer.append((getClient_street()) + " " + (getClient_numext()!=null? "Ext. " + getClient_numext():"") + " " + (getClient_numint()!=null? "Int. " + getClient_numint():""));
        addToList(list,"client_addressComplete",buffer.toString());

        addToList(list,"accothersbranchComplete1",getAccotherscr_1() + " " + getAccothersbranch_1());
        addToList(list,"accothersbranchComplete2",getAccotherscr_2() + " " + getAccothersbranch_2());
        addToList(list,"accothersbranchComplete3",getAccotherscr_3() + " " + getAccothersbranch_3());
        addToList(list,"accothersbranchComplete4",getAccotherscr_4() + " " + getAccothersbranch_4());
        addToList(list,"accothersbranchComplete5",getAccotherscr_5() + " " + getAccothersbranch_5());

        addToList(list,"contract_reference", this.getContract().getReference());
        addToList(list,"comments", this.getComments().length()<250?this.getComments():this.getComments().substring(0, 249));
        addToList(list,"operations_comment", this.getComments().length()<250?this.getComments():this.getComments().substring(0, 249));

        addToList(list,"quantofadminusers", getQuantofadminusers());
        //addToList(list,"operationofadminusers", getOperationofadminusers());
        addToList(list,"bemmodeop1", getQuantofadminusers().equals("1")?"X":"");
        addToList(list,"bemmodeop2", getQuantofadminusers().equals("2")?"X":"");
        addToList(list,"bemmodeop3", getQuantofadminusers().equals("3") || getQuantofadminusers().equals("4")?"X":"");
        addToList(list,"bemmodeopman1", getQuantofadminusers().equals("3")?"X":"");
        addToList(list,"bemmodeopman2", getQuantofadminusers().equals("4")?"X":"");

        //variables de opciones para pdf BEMTokensAdicionales
        addToList(list,"benaddop1", getSerialtoken_7()!= null || !getSerialtoken_7().equals("")?"X":""); //Adicionales
        addToList(list,"benaddop2", ""); //Reposicion
        addToList(list,"benaddop3", ""); //Daño
        addToList(list,"benaddop4", ""); //Contraseña

        addToList(list,"bemnumber","");
        
        //Joseles(04/11/2011): Guardar el regimen y no. de firma digital en los atributos del contrato
        addToList(list,"regimen_1",this.getRegimen_1());
        addToList(list,"regimen_2",this.getRegimen_2());
        addToList(list,"regimen_3",this.getRegimen_3());
        addToList(list,"regimen_4",this.getRegimen_4());
        addToList(list,"regimen_5",this.getRegimen_5());
        addToList(list,"regimen_6",this.getRegimen_6());
        addToList(list,"regimen_7",this.getRegimen_7());
        addToList(list,"regimen_8",this.getRegimen_8());
        addToList(list,"regimen_9",this.getRegimen_9());
        addToList(list,"regimen_10",this.getRegimen_10());
        addToList(list,"regimen_11",this.getRegimen_11());
        addToList(list,"regimen_12",this.getRegimen_12());
        addToList(list,"regimen_13",this.getRegimen_13());
        addToList(list,"regimen_14",this.getRegimen_14());
        addToList(list,"regimen_15",this.getRegimen_15());
        addToList(list,"officerrepfirmnumber_1",this.getOfficerrepfirmnumber_1());
        addToList(list,"officerrepfirmnumber_2",this.getOfficerrepfirmnumber_2());
        
        contract.setContractAttributeCollection(list);
        contractBean.update(contract);

//        log.info("-----------------> CONTRACT: " + contract);
        pdfTemplateBinding = new PdfTemplateBindingContract();
        pdfTemplateBinding.setContract(contract);

        return true;
    }

      //Accion para editar
     public void setEditForm() {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        Map<String, String> params = fCtx.getExternalContext().getRequestParameterMap();        
        Integer idContract = Integer.parseInt(params.get("contractId"));
        String BEM_FILLS[] = {  "selectedbemplan","quanttokens","accountnumber","commchargetype","quantaccown","quantaccothers","quantofadminusers",
                                "serialtoken_1","emailtoken_1","serialtoken_2","emailtoken_2",
                                "serialtoken_3","emailtoken_3","serialtoken_4","emailtoken_4",
                                "serialtoken_5","emailtoken_5","serialtoken_6","emailtoken_6",
                                "serialtoken_7","emailtoken_7","serialtoken_8","emailtoken_8",
                                "serialtoken_9","emailtoken_9","serialtoken_10","emailtoken_10",
                                "serialtoken_11","emailtoken_11","serialtoken_12","emailtoken_12",
                                "accownnum_1","accownname_1","accownnum_2","accownname_2",                                
                                "accownnum_3","accownname_3","accownnum_4","accownname_4", 
                                "accownnum_5","accownname_5","accownnum_6","accownname_6",
                                "accownnum_7","accownname_7","accownnum_8","accownname_8",
                                "accownnum_9","accownname_9","accownnum_10","accownname_10",
                                "accownnum_11","accownname_11","accownnum_12","accownname_12",
                                "accownnum_13","accownname_13","accownnum_14","accownname_14",
                                "accownnum_15","accownname_15","accothersname_1",
                                "accothersnum_1","accothersofficername_1","accotherscr_1","accothersbranch_1",
                                "accothersnum_2","accothersofficername_2","accotherscr_2","accothersbranch_2",
                                "accothersnum_3","accothersofficername_3","accotherscr_3","accothersbranch_3",
                                "accothersnum_4","accothersofficername_4","accotherscr_4","accothersbranch_4",
                                "accothersnum_5","accothersofficername_5","accotherscr_5","accothersbranch_5",
                                "referencetoken_1","referencetoken_2","referencetoken_3","referencetoken_4",
                                "referencetoken_5","referencetoken_6","referencetoken_7","referencetoken_8",
                                "referencetoken_9","referencetoken_10","referencetoken_11","referencetoken_12",
                                "regimen_1","regimen_2","regimen_3","regimen_4","regimen_5","regimen_6","regimen_7","regimen_8",
                                "regimen_9","regimen_10","regimen_11","regimen_12","regimen_13","regimen_14","regimen_15"
                                };
        
        Contract contract_ = contractBean.findById(idContract);
        if (contract_.getContractAttributeCollection()!=null){
         Map<String, String> map=  this.getContractAttributeFills(contract_,BEM_FILLS);        
         
         if("Plan 20".equalsIgnoreCase(map.get("selectedbemplan"))){
        	 selectedbemplan="0"; //como el plan ya no existe se pone en 0 para que eliga uno.
         }else{
        	 selectedbemplan=map.get("selectedbemplan");
         }
         this.setSelectedbemplan(map.get("selectedbemplan"));
         this.setQuanttokens(map.get("quanttokens")!=null && map.get("quanttokens").trim().length()>0?map.get("quanttokens"):"12");
         this.setAccountnumberDecrypt(map.get("accountnumber"));
         this.setCommchargetype(map.get("commchargetype"));
         this.setQuantaccown(map.get("quantaccown")!=null && map.get("quantaccown").trim().length()>0?map.get("quantaccown"):"15");
         this.setQuantaccothers(map.get("quantaccothers")!=null && map.get("quantaccothers").trim().length()>0?map.get("quantaccothers"):"5");

         //Establecer banderas para cargar los elementos necesarios para la pantalla

         this.setSerialtoken_1(map.get("serialtoken_1"));
         this.setEmailtoken_1(map.get("emailtoken_1"));
         this.setSerialtoken_2(map.get("serialtoken_2"));
         this.setEmailtoken_2(map.get("emailtoken_2"));
         this.setSerialtoken_3(map.get("serialtoken_3"));
         this.setEmailtoken_3(map.get("emailtoken_3"));
         this.setSerialtoken_4(map.get("serialtoken_4"));
         this.setEmailtoken_4(map.get("emailtoken_4"));
         this.setSerialtoken_5(map.get("serialtoken_5"));
         this.setEmailtoken_5(map.get("emailtoken_5"));
         this.setSerialtoken_6(map.get("serialtoken_6"));
         this.setEmailtoken_6(map.get("emailtoken_6"));
         this.setSerialtoken_7(map.get("serialtoken_7"));
         this.setEmailtoken_7(map.get("emailtoken_7"));
         this.setSerialtoken_8(map.get("serialtoken_8"));
         this.setEmailtoken_8(map.get("emailtoken_8"));
         this.setSerialtoken_9(map.get("serialtoken_9"));
         this.setEmailtoken_9(map.get("emailtoken_9"));
         this.setSerialtoken_10(map.get("serialtoken_10"));
         this.setEmailtoken_10(map.get("emailtoken_10"));
         this.setSerialtoken_11(map.get("serialtoken_11"));
         this.setEmailtoken_11(map.get("emailtoken_11"));
         this.setSerialtoken_12(map.get("serialtoken_12"));
         this.setEmailtoken_12(map.get("emailtoken_12"));
         
         this.setReferencetoken_1(map.get("referencetoken_1"));
         this.setReferencetoken_2(map.get("referencetoken_2"));
         this.setReferencetoken_3(map.get("referencetoken_3"));
         this.setReferencetoken_4(map.get("referencetoken_4"));
         this.setReferencetoken_5(map.get("referencetoken_5"));
         this.setReferencetoken_6(map.get("referencetoken_6"));         
         this.setReferencetoken_7(map.get("referencetoken_7"));
         this.setReferencetoken_8(map.get("referencetoken_8"));
         this.setReferencetoken_9(map.get("referencetoken_9"));
         this.setReferencetoken_10(map.get("referencetoken_10"));
         this.setReferencetoken_11(map.get("referencetoken_11"));
         this.setReferencetoken_12(map.get("referencetoken_12"));         

        
         this.setAccownname_1(map.get("accownname_1"));
         this.setAccownnumDecrypt_2(map.get("accownnum_2"));
         this.setAccownname_2(map.get("accownname_2"));
         this.setAccownnumDecrypt_3(map.get("accownnum_3"));
         this.setAccownname_3(map.get("accownname_3"));
         this.setAccownnumDecrypt_4(map.get("accownnum_4"));
         this.setAccownname_4(map.get("accownname_4"));
         this.setAccownnumDecrypt_5(map.get("accownnum_5"));
         this.setAccownname_5(map.get("accownname_5"));
          this.setAccownnumDecrypt_6(map.get("accownnum_6"));
         this.setAccownname_6(map.get("accownname_6"));
          this.setAccownnumDecrypt_7(map.get("accownnum_7"));
         this.setAccownname_7(map.get("accownname_7"));
          this.setAccownnumDecrypt_8(map.get("accownnum_8"));
         this.setAccownname_8(map.get("accownname_8"));
          this.setAccownnumDecrypt_9(map.get("accownnum_9"));
         this.setAccownname_9(map.get("accownname_9"));
          this.setAccownnumDecrypt_10(map.get("accownnum_10"));
         this.setAccownname_10(map.get("accownname_10"));
          this.setAccownnumDecrypt_11(map.get("accownnum_11"));
         this.setAccownname_11(map.get("accownname_11"));
          this.setAccownnumDecrypt_12(map.get("accownnum_12"));
         this.setAccownname_12(map.get("accownname_12"));
          this.setAccownnumDecrypt_13(map.get("accownnum_13"));
         this.setAccownname_13(map.get("accownname_13"));
          this.setAccownnumDecrypt_14(map.get("accownnum_14"));
         this.setAccownname_14(map.get("accownname_14"));
          this.setAccownnumDecrypt_15(map.get("accownnum_15"));
         this.setAccownname_15(map.get("accownname_15"));
         
         
         this.setAccothersname_1(map.get("accothersname_1"));
         this.setAccothersnumDecrypt_1(map.get("accothersnum_1"));
         this.setAccothersofficername_1(map.get("accothersofficername_1"));
         this.setAccotherscr_1(map.get("accotherscr_1"));
         this.setAccothersbranch_1(map.get("accothersbranch_1"));         
         this.setAccothersnumDecrypt_2(map.get("accothersnum_2"));
         this.setAccothersofficername_2(map.get("accothersofficername_2"));
         this.setAccotherscr_2(map.get("accotherscr_2"));
         this.setAccothersbranch_2(map.get("accothersbranch_2"));
         this.setAccothersnumDecrypt_3(map.get("accothersnum_3"));
         this.setAccothersofficername_3(map.get("accothersofficername_3"));
         this.setAccotherscr_3(map.get("accotherscr_3"));
         this.setAccothersbranch_3(map.get("accothersbranch_3"));
         this.setAccothersnumDecrypt_4(map.get("accothersnum_4"));
         this.setAccothersofficername_4(map.get("accothersofficername_4"));
         this.setAccotherscr_4(map.get("accotherscr_4"));
         this.setAccothersbranch_4(map.get("accothersbranch_4"));         
         this.setAccothersnumDecrypt_5(map.get("accothersnum_5"));
         this.setAccothersofficername_5(map.get("accothersofficername_5"));
         this.setAccotherscr_5(map.get("accotherscr_5"));
         this.setAccothersbranch_5(map.get("accothersbranch_5"));

         
         this.setCelebrationplace(map.get("celebrationplace"));
         this.setCelebrationstate(map.get("celebrationstate"));
         this.setCelebrationdate(map.get("celebrationdate"));
         this.setClient_categorycode(map.get("client_categorycode"));
         this.setComments(map.get("comments"));
         this.setCrnumber(map.get("crnumber"));
         this.setBranchname(map.get("branchname"));
         this.setBranchstreet(map.get("branchstreet"));         
         this.setBranchcolony(map.get("branchcolony"));
         this.setBranchphone(map.get("branchphone"));
         this.setBranchfax(map.get("branchfax"));         
         this.setBankingsector(map.get("bankingsector"));
         this.setBranchcity(map.get("branchcity"));
         this.setBranchstate(map.get("branchstate"));
         this.setOfficername(map.get("officername"));
         this.setOfficerlastname(map.get("officerlastname"));
         this.setOfficermothersln(map.get("officermothersln"));
         this.setOfficerempnumber(map.get("officerempnumber"));
         this.setOfficerposition(map.get("officerposition"));
         this.setOfficerebankingname(map.get("officerebankingname"));
         this.setOfficerebankinglastname(map.get("officerebankinglastname"));
         this.setOfficerebankingmothersln(map.get("officerebankingmothersln"));
         this.setOfficerebankingempnumber(map.get("officerebankingempnumber"));
         this.setOfficerrepname_1(map.get("officerrepname_1"));
         this.setOfficerreplastname_1(map.get("officerreplastname_1"));
         this.setOfficerrepmothersln_1(map.get("officerrepmothersln_1"));
         this.setOfficerrepempnumber_1(map.get("officerrepempnumber_1"));         
         this.setOfficerrepposition_1(map.get("officerrepposition_1"));
         this.setOfficerrepname_2(map.get("officerrepname_2"));
         this.setOfficerreplastname_2(map.get("officerreplastname_2"));
         this.setOfficerrepmothersln_2(map.get("officerrepmothersln_2"));
         this.setOfficerrepempnumber_2(map.get("officerrepempnumber_2"));
         this.setOfficerrepposition_2(map.get("officerrepposition_2"));

         //Joseles(07/Nov/2011):Asignar los valores a los campos del No. de firma digital
         this.setOfficerrepfirmnumber_1(map.get("officerrepfirmnumber_1"));
         this.setOfficerrepfirmnumber_2(map.get("officerrepfirmnumber_2"));
        
         this.setClient_areacode(map.get("client_areacode")!=null && map.get("client_areacode").trim().length()>0?Integer.parseInt(map.get("client_areacode")):0);          
         this.setClient_state(map.get("client_state"));
         this.setClient_city(map.get("client_city"));
         this.setClient_colony(map.get("client_colony"));         
         this.setClient_county(map.get("client_county"));
         this.setClient_email(map.get("client_email"));
         this.setClient_faxext(map.get("client_faxext")!=null && map.get("client_faxext").trim().length()>0?Integer.parseInt(map.get("client_faxext")):0);
         this.setClient_fax(map.get("client_fax")!=null && map.get("client_fax").trim().length()>0 ? Integer.parseInt(map.get("client_fax")):0);
         this.setClient_fiscalname(map.get("client_fiscalname"));
         this.setClient_fiscaltype(map.get("client_fiscaltype")!=null && map.get("client_fiscaltype").trim().length()>0 ?Integer.parseInt(map.get("client_fiscaltype")):0);
         this.setClient_numext(map.get("client_numext"));
         this.setClient_numint(map.get("client_numint"));
         this.setClient_phone(map.get("client_phone")!=null && map.get("client_phone").trim().length()>0 ? Integer.parseInt(map.get("client_phone")):0);
         this.setClient_phoneext(map.get("client_phoneext")!=null && map.get("client_phoneext").trim().length()>0 ? Integer.parseInt(map.get("client_phoneext")):0);
         this.setClient_rfc(map.get("client_rfc")); 
         this.setClient_street(map.get("client_street"));
         this.setClient_zipcode(map.get("client_zipcode")!=null && map.get("client_zipcode").trim().length()>0 ? Integer.parseInt(map.get("client_zipcode")):0);

         
         this.setLegalrepresentative_lastname_1(map.get("legalrepresentative_lastname_1"));
         this.setLegalrepresentative_lastname_2(map.get("legalrepresentative_lastname_2"));
         this.setLegalrepresentative_mothersln_1(map.get("legalrepresentative_mothersln_1"));
         this.setLegalrepresentative_mothersln_2(map.get("legalrepresentative_mothersln_2"));
         this.setLegalrepresentative_name_1(map.get("legalrepresentative_name_1"));
         this.setLegalrepresentative_name_2(map.get("legalrepresentative_name_2"));
         
         this.setQuantofadminusers(map.get("quantofadminusers"));
         
         this.setClientcontact_email1(map.get("clientcontact_email1"));
         this.setClientcontact_lastname1(map.get("clientcontact_lastname1"));
         this.setClientcontact_mothersln1(map.get("clientcontact_mothersln1"));
         this.setClientcontact_name1(map.get("clientcontact_name1"));         
         this.setClientcontact_position1(map.get("clientcontact_position1"));
         if(map.get("clientcontact_phone1")!=null && map.get("clientcontact_phone1").trim().length()>0)
            this.setClientcontact_phone1(Integer.parseInt(map.get("clientcontact_phone1")));
         if(map.get("clientcontact_phoneext1")!=null && map.get("clientcontact_phoneext1").trim().length()>0)
            this.setClientcontact_phoneext1(Integer.parseInt(map.get("clientcontact_phoneext1")));
         
         this.setClientcontact_email2(map.get("clientcontact_email2"));
         this.setClientcontact_lastname2(map.get("clientcontact_lastname2"));
         this.setClientcontact_mothersln2(map.get("clientcontact_mothersln2"));
         this.setClientcontact_name2(map.get("clientcontact_name2"));
          this.setClientcontact_position2(map.get("clientcontact_position2"));
         if(map.get("clientcontact_phone2")!=null && map.get("clientcontact_phone2").trim().length()>0)
            this.setClientcontact_phone2(Integer.parseInt(map.get("clientcontact_phone2")));
         if(map.get("clientcontact_phoneext2")!=null && map.get("clientcontact_phoneext2").trim().length()>0)
            this.setClientcontact_phoneext2(Integer.parseInt(map.get("clientcontact_phoneext2")));

         this.setClientcontact_email3(map.get("clientcontact_email3"));
         this.setClientcontact_lastname3(map.get("clientcontact_lastname3"));
         this.setClientcontact_mothersln3(map.get("clientcontact_mothersln3"));
         this.setClientcontact_name3(map.get("clientcontact_name3"));
         this.setClientcontact_position3(map.get("clientcontact_position3"));
         if(map.get("clientcontact_phone3")!=null && map.get("clientcontact_phone3").trim().length()>0)
            this.setClientcontact_phone3(Integer.parseInt(map.get("clientcontact_phone3")));
         if(map.get("clientcontact_phoneext3")!=null && map.get("clientcontact_phoneext3").trim().length()>0)
            this.setClientcontact_phoneext3(Integer.parseInt(map.get("clientcontact_phoneext3")));
         
         //this.setClient_sic(contract_.getClient().getSic());
         this.setClient_sic(map.get("client_sic"));
         
         
         //Joseles(04/Nov/2011): Obtener el valor del campo regimen_x para cada cuenta concentradora.
         this.setRegimen_1(map.get("regimen_1"));
         this.setRegimen_2(map.get("regimen_2"));
         this.setRegimen_3(map.get("regimen_3"));
         this.setRegimen_4(map.get("regimen_4"));
         this.setRegimen_5(map.get("regimen_5"));
         this.setRegimen_6(map.get("regimen_6"));
         this.setRegimen_7(map.get("regimen_7"));
         this.setRegimen_8(map.get("regimen_8"));
         this.setRegimen_9(map.get("regimen_9"));
         this.setRegimen_10(map.get("regimen_10"));
         this.setRegimen_11(map.get("regimen_11"));
         this.setRegimen_12(map.get("regimen_12"));
         this.setRegimen_13(map.get("regimen_13"));
         this.setRegimen_14(map.get("regimen_14"));
         this.setRegimen_15(map.get("regimen_15"));

        }
        

        this.setContract(contract_);

        
    }
    
     public String getEditForm() {
        setEditForm();
        loadflag=false;
        return "";
    }
    
    
    public void createPDF() {
        FacesContext fCtx = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest) fCtx.getExternalContext().getRequest();
		   	
	   	   String  nameTemplate = request.getParameter(NAME_TEMPLATE);
	       String  pathTemplate = request.getParameter(PATHTEMPLATE);
	       Integer flagTemplate = Integer.parseInt(request.getParameter("flagTemplate"));
//        log.info("******** Muestra Documento " + nameTemplate + "... " + flagTemplate.intValue());
        pdfTemplateBinding.setAffiliationId(1);
        createPdfOrXLSResponse(getPath() + pathTemplate+nameTemplate, nameTemplate, true, flagTemplate.intValue() == 1 ? true : false);
    }
    
	public Template[] getFormatList() {
		this.setToPrint(false);
		Collection<Template> templateCollection = getTemplateOption(this.getProduct().getProductid());
		Collection<Template> templateCollection2 = new ArrayList();
		for (Template template : templateCollection) {
			// if ( template.getEditable().intValue() == 0 ) { //se hace
			// editable para folio
			if (template.getName().equals(ApplicationConstants.BEM_ANEXO_TARIFAS_PLAN1)
					|| template.getName().equals(ApplicationConstants.BEM_ANEXO_TARIFAS_PLAN2)
					|| template.getName().equals(ApplicationConstants.BEM_ANEXO_TARIFAS_PLAN4)
					// template.getName().equals(ApplicationConstants.BEM_ANEXO_TARIFAS_PLAN20)||//se
					// da de baja plan 20 150203
					|| template.getName().equals(ApplicationConstants.BEM_ANEXO_TARIFAS_PLAN1200)) {
				String planSeleccionado = selectedbemplan.replaceAll(" ", "");
				String formato = template.getName().replaceAll("BEMAnexoTarifas", "").replaceAll(".pdf", "");
				if (formato.equalsIgnoreCase(planSeleccionado)) {
					templateCollection2.add(template);
				}
				// } else {
				// templateCollection2.add(template);
				// }
			} else{
				// Revisar para agregar los teceros
				if (template.getName().equals(ApplicationConstants.BEM_CUENTAS_TERCEROS)) {
					if (this.getAccothersnum_1() != null&& !this.getAccothersnum_1().equals("")) {
						templateCollection2.add(template);
					}
				}else{
					if(template.getName().equals(ApplicationConstants.BEM_CUENTAS_AUTORIZADAS1)){
						if (this.getAccownname_1() != null&& !this.getAccownname_1().equals("")
								|| this.getAccownname_2() != null&& !this.getAccownname_2().equals("")
								|| this.getAccownname_3() != null&& !this.getAccownname_3().equals("")
								|| this.getAccownname_4() != null&& !this.getAccownname_4().equals("")
								|| this.getAccownname_5() != null&& !this.getAccownname_5().equals("")) {
							templateCollection2.add(template);
						}
					} else {
						if (template.getName().equals(ApplicationConstants.BEM_CUENTAS_AUTORIZADAS2)) {
							if (this.getAccownname_6() != null&& !this.getAccownname_6().equals("")
										|| this.getAccownname_7() != null&& !this.getAccownname_7().equals("")
									|| this.getAccownname_8() != null&& !this.getAccownname_8().equals("")
									|| this.getAccownname_9() != null&& !this.getAccownname_9().equals("")
									|| this.getAccownname_10() != null&& !this.getAccownname_10().equals("")) {
								templateCollection2.add(template);
							}
						} else {
							if (template.getName().equals(ApplicationConstants.BEM_CUENTAS_AUTORIZADAS3)) {
								if (this.getAccownname_11() != null&& !this.getAccownname_11().equals("")
										|| this.getAccownname_12() != null&& !this.getAccownname_12().equals("")
										|| this.getAccownname_13() != null&& !this.getAccownname_13().equals("")
										|| this.getAccownname_14() != null&& !this.getAccownname_14().equals("")
										|| this.getAccownname_15() != null&& !this.getAccownname_15().equals("")) {
									templateCollection2.add(template);
								}
							} else {
								templateCollection2.add(template);
							}//fin else autorizadas 3
						}// fin else autorizadas 2
					}//fin else autorizadas 1
				}//fin else cuentas terceros
			}//fin else tarifas
		}//fin ciclo

		Template[] templateArray = new Template[templateCollection2.size()];
		return templateCollection2.toArray(templateArray);
	}



    public String getQuantofadminusers() {
        return quantofadminusers;
    }

    public void setQuantofadminusers(String quantofadminusers) {
        this.quantofadminusers = quantofadminusers;
    }

    public String getAccothersname_1() {
        return accothersname_1;
    }

    public void setAccothersname_1(String accothersname_1) {
        this.accothersname_1 = accothersname_1;
    }

    public String getAccothersname_2() {
        return accothersname_2;
    }

    public void setAccothersname_2(String accothersname_2) {
        this.accothersname_2 = accothersname_2;
    }

    public String getAccothersname_3() {
        return accothersname_3;
    }

    public void setAccothersname_3(String accothersname_3) {
        this.accothersname_3 = accothersname_3;
    }

    public String getAccothersname_4() {
        return accothersname_4;
    }

    public void setAccothersname_4(String accothersname_4) {
        this.accothersname_4 = accothersname_4;
    }

    public String getAccothersname_5() {
        return accothersname_5;
    }

    public void setAccothersname_5(String accothersname_5) {
        this.accothersname_5 = accothersname_5;
    }

    //Sets y gets


    public String getAccothersbrach_6() {
        return accothersbrach_6;
    }

    public void setAccothersbrach_6(String accothersbrach_6) {
        this.accothersbrach_6 = accothersbrach_6;
    }

    public String getAccothersbranch_10() {
        return accothersbranch_10;
    }

    public void setAccothersbranch_10(String accothersbranch_10) {
        this.accothersbranch_10 = accothersbranch_10;
    }

    public String getAccothersbranch_2() {
        return accothersbranch_2;
    }

    public void setAccothersbranch_2(String accothersbranch_2) {
        this.accothersbranch_2 = accothersbranch_2;
    }

    public String getAccothersbranch_3() {
        return accothersbranch_3;
    }

    public void setAccothersbranch_3(String accothersbranch_3) {
        this.accothersbranch_3 = accothersbranch_3;
    }

    public String getAccothersbranch_4() {
        return accothersbranch_4;
    }

    public void setAccothersbranch_4(String accothersbranch_4) {
        this.accothersbranch_4 = accothersbranch_4;
    }

    public String getAccothersbranch_5() {
        return accothersbranch_5;
    }

    public void setAccothersbranch_5(String accothersbranch_5) {
        this.accothersbranch_5 = accothersbranch_5;
    }

    public String getAccothersbranch_7() {
        return accothersbranch_7;
    }

    public void setAccothersbranch_7(String accothersbranch_7) {
        this.accothersbranch_7 = accothersbranch_7;
    }

    public String getAccothersbranch_8() {
        return accothersbranch_8;
    }

    public void setAccothersbranch_8(String accothersbranch_8) {
        this.accothersbranch_8 = accothersbranch_8;
    }

    public String getAccothersbranch_9() {
        return accothersbranch_9;
    }

    public void setAccothersbranch_9(String accothersbranch_9) {
        this.accothersbranch_9 = accothersbranch_9;
    }

    public String getAccotherscr_10() {
        return accotherscr_10;
    }

    public void setAccotherscr_10(String accotherscr_10) {
        this.accotherscr_10 = accotherscr_10;
    }

    public String getAccotherscr_2() {
        return accotherscr_2;
    }

    public void setAccotherscr_2(String accotherscr_2) {
        this.accotherscr_2 = accotherscr_2;
    }

    public String getAccotherscr_3() {
        return accotherscr_3;
    }

    public void setAccotherscr_3(String accotherscr_3) {
        this.accotherscr_3 = accotherscr_3;
    }

    public String getAccotherscr_4() {
        return accotherscr_4;
    }

    public void setAccotherscr_4(String accotherscr_4) {
        this.accotherscr_4 = accotherscr_4;
    }

    public String getAccotherscr_5() {
        return accotherscr_5;
    }

    public void setAccotherscr_5(String accotherscr_5) {
        this.accotherscr_5 = accotherscr_5;
    }

    public String getAccotherscr_6() {
        return accotherscr_6;
    }

    public void setAccotherscr_6(String accotherscr_6) {
        this.accotherscr_6 = accotherscr_6;
    }

    public String getAccotherscr_7() {
        return accotherscr_7;
    }

    public void setAccotherscr_7(String accotherscr_7) {
        this.accotherscr_7 = accotherscr_7;
    }

    public String getAccotherscr_8() {
        return accotherscr_8;
    }

    public void setAccotherscr_8(String accotherscr_8) {
        this.accotherscr_8 = accotherscr_8;
    }

    public String getAccotherscr_9() {
        return accotherscr_9;
    }

    public void setAccotherscr_9(String accotherscr_9) {
        this.accotherscr_9 = accotherscr_9;
    }

    public String getAccothersnum_10() {
        return accothersnum_10;
    }

    public void setAccothersnum_10(String accothersnum_10) {
        this.accothersnum_10 = accothersnum_10;
    }

    public String getAccothersnum_2() {
        if(accothersnum_2 != null && accothersnum_2.trim().length() > 0){                       
            return encrypt.decrypt(accothersnum_2);
         }
        else {
        return ""; 
        }         
    }

    public void setAccothersnum_2(String accothersnum_2) {
        if(accothersnum_2 != null && accothersnum_2.trim().length() > 0){                     
             this.accothersnum_2 = encrypt.encrypt(accothersnum_2);
        } else {
            this.accothersnum_2 = accothersnum_2;
          }
    }

    public String getAccothersnum_3() {
         if(accothersnum_3 != null && accothersnum_3.trim().length() > 0){
            return encrypt.decrypt(accothersnum_3); 
         }
        else {
        return ""; 
        }
        
    }

    public void setAccothersnum_3(String accothersnum_3) {
        if(accothersnum_3 != null && accothersnum_3.trim().length() > 0){            
             this.accothersnum_3 = encrypt.encrypt(accothersnum_3);           
        } else {
            this.accothersnum_3 = accothersnum_3;
          }          
    }

    public String getAccothersnum_4() {
         if(accothersnum_4 != null && accothersnum_4.trim().length() > 0){            
            return encrypt.decrypt(accothersnum_4);
         }
        else {
        return "";
        }
    }

    public void setAccothersnum_4(String accothersnum_4) {
        if(accothersnum_4 != null && accothersnum_4.trim().length() > 0){
             this.accothersnum_4 = encrypt.encrypt(accothersnum_4);
        } else {
            this.accothersnum_4 = accothersnum_4;
          }          
    }

    public String getAccothersnum_5() {
         if(accothersnum_5 != null && accothersnum_5.trim().length() > 0){                       
            return encrypt.decrypt(accothersnum_5);
         }
        else {
        return ""; 
        }       
    }

    public void setAccothersnum_5(String accothersnum_5) {
        if(accothersnum_5 != null && accothersnum_5.trim().length() > 0){                        
             this.accothersnum_5 = encrypt.encrypt(accothersnum_5);            
        } else {
            this.accothersnum_5 = accothersnum_5;
          }        
    }

    public String getAccothersnum_6() {
        return accothersnum_6;
    }

    public void setAccothersnum_6(String accothersnum_6) {
        this.accothersnum_6 = accothersnum_6;
    }

    public String getAccothersnum_7() {
        return accothersnum_7;
    }

    public void setAccothersnum_7(String accothersnum_7) {
        this.accothersnum_7 = accothersnum_7;
    }

    public String getAccothersnum_8() {
        return accothersnum_8;
    }

    public void setAccothersnum_8(String accothersnum_8) {
        this.accothersnum_8 = accothersnum_8;
    }

    public String getAccothersnum_9() {
        return accothersnum_9;
    }

    public void setAccothersnum_9(String accothersnum_9) {
        this.accothersnum_9 = accothersnum_9;
    }

    public String getAccothersofficername_10() {
        return accothersofficername_10;
    }

    public void setAccothersofficername_10(String accothersofficername_10) {
        this.accothersofficername_10 = accothersofficername_10;
    }

    public String getAccothersofficername_2() {
        return accothersofficername_2;
    }

    public void setAccothersofficername_2(String accothersofficername_2) {
        this.accothersofficername_2 = accothersofficername_2;
    }

    public String getAccothersofficername_3() {
        return accothersofficername_3;
    }

    public void setAccothersofficername_3(String accothersofficername_3) {
        this.accothersofficername_3 = accothersofficername_3;
    }

    public String getAccothersofficername_4() {
        return accothersofficername_4;
    }

    public void setAccothersofficername_4(String accothersofficername_4) {
        this.accothersofficername_4 = accothersofficername_4;
    }

    public String getAccothersofficername_5() {
        return accothersofficername_5;
    }

    public void setAccothersofficername_5(String accothersofficername_5) {
        this.accothersofficername_5 = accothersofficername_5;
    }

    public String getAccothersofficername_6() {
        return accothersofficername_6;
    }

    public void setAccothersofficername_6(String accothersofficername_6) {
        this.accothersofficername_6 = accothersofficername_6;
    }

    public String getAccothersofficername_7() {
        return accothersofficername_7;
    }

    public void setAccothersofficername_7(String accothersofficername_7) {
        this.accothersofficername_7 = accothersofficername_7;
    }

    public String getAccothersofficername_8() {
        return accothersofficername_8;
    }

    public void setAccothersofficername_8(String accothersofficername_8) {
        this.accothersofficername_8 = accothersofficername_8;
    }

    public String getAccothersofficername_9() {
        return accothersofficername_9;
    }

    public void setAccothersofficername_9(String accothersofficername_9) {
        this.accothersofficername_9 = accothersofficername_9;
    }

    public String getAccownmerchantname_10() {
        return accownmerchantname_10;
    }

    public void setAccownmerchantname_10(String accownmerchantname_10) {
        this.accownmerchantname_10 = accownmerchantname_10;
    }

    public String getAccownmerchantname_2() {
        return accownmerchantname_2;
    }

    public void setAccownmerchantname_2(String accownmerchantname_2) {
        this.accownmerchantname_2 = accownmerchantname_2;
    }

    public String getAccownmerchantname_3() {
        return accownmerchantname_3;
    }

    public void setAccownmerchantname_3(String accownmerchantname_3) {
        this.accownmerchantname_3 = accownmerchantname_3;
    }

    public String getAccownmerchantname_4() {
        return accownmerchantname_4;
    }

    public void setAccownmerchantname_4(String accownmerchantname_4) {
        this.accownmerchantname_4 = accownmerchantname_4;
    }

    public String getAccownmerchantname_5() {
        return accownmerchantname_5;
    }

    public void setAccownmerchantname_5(String accownmerchantname_5) {
        this.accownmerchantname_5 = accownmerchantname_5;
    }

    public String getAccownmerchantname_6() {
        return accownmerchantname_6;
    }

    public void setAccownmerchantname_6(String accownmerchantname_6) {
        this.accownmerchantname_6 = accownmerchantname_6;
    }

    public String getAccownmerchantname_7() {
        return accownmerchantname_7;
    }

    public void setAccownmerchantname_7(String accownmerchantname_7) {
        this.accownmerchantname_7 = accownmerchantname_7;
    }

    public String getAccownmerchantname_8() {
        return accownmerchantname_8;
    }

    public void setAccownmerchantname_8(String accownmerchantname_8) {
        this.accownmerchantname_8 = accownmerchantname_8;
    }

    public String getAccownmerchantname_9() {
        return accownmerchantname_9;
    }

    public void setAccownmerchantname_9(String accownmerchantname_9) {
        this.accownmerchantname_9 = accownmerchantname_9;
    }

    public String getAccownmerchantname_11() {
        return accownmerchantname_11;
    }

    public void setAccownmerchantname_11(String accownmerchantname_11) {
        this.accownmerchantname_11 = accownmerchantname_11;
    }

    public String getAccownmerchantname_12() {
        return accownmerchantname_12;
    }

    public void setAccownmerchantname_12(String accownmerchantname_12) {
        this.accownmerchantname_12 = accownmerchantname_12;
    }

    public String getAccownmerchantname_13() {
        return accownmerchantname_13;
    }

    public void setAccownmerchantname_13(String accownmerchantname_13) {
        this.accownmerchantname_13 = accownmerchantname_13;
    }

    public String getAccownmerchantname_14() {
        return accownmerchantname_14;
    }

    public void setAccownmerchantname_14(String accownmerchantname_14) {
        this.accownmerchantname_14 = accownmerchantname_14;
    }

    public String getAccownmerchantname_15() {
        return accownmerchantname_15;
    }

    public void setAccownmerchantname_15(String accownmerchantname_15) {
        this.accownmerchantname_15 = accownmerchantname_15;
    }



    public String getAccownname_10() {
        return accownname_10;
    }

    public void setAccownname_10(String accownname_10) {
        this.accownname_10 = accownname_10;
    }

    public String getAccownname_2() {
        return accownname_2;
    }

    public void setAccownname_2(String accownname_2) {
        this.accownname_2 = accownname_2;
    }

    public String getAccownname_3() {
        return accownname_3;
    }

    public void setAccownname_3(String accownname_3) {
        this.accownname_3 = accownname_3;
    }

    public String getAccownname_4() {
        return accownname_4;
    }

    public void setAccownname_4(String accownname_4) {
        this.accownname_4 = accownname_4;
    }

    public String getAccownname_5() {
        return accownname_5;
    }

    public void setAccownname_5(String accownname_5) {
        this.accownname_5 = accownname_5;
    }

    public String getAccownname_6() {
        return accownname_6;
    }

    public void setAccownname_6(String accownname_6) {
        this.accownname_6 = accownname_6;
    }

    public String getAccownname_7() {
        return accownname_7;
    }

    public void setAccownname_7(String accownname_7) {
        this.accownname_7 = accownname_7;
    }

    public String getAccownname_8() {
        return accownname_8;
    }

    public void setAccownname_8(String accownname_8) {
        this.accownname_8 = accownname_8;
    }

    public String getAccownname_9() {
        return accownname_9;
    }

    public void setAccownname_9(String accownname_9) {
        this.accownname_9 = accownname_9;
    }

    public String getAccownname_11() {
        return accownname_11;
    }

    public void setAccownname_11(String accownname_11) {
        this.accownname_11 = accownname_11;
    }

    public String getAccownname_12() {
        return accownname_12;
    }

    public void setAccownname_12(String accownname_12) {
        this.accownname_12 = accownname_12;
    }

    public String getAccownname_13() {
        return accownname_13;
    }

    public void setAccownname_13(String accownname_13) {
        this.accownname_13 = accownname_13;
    }

    public String getAccownname_14() {
        return accownname_14;
    }

    public void setAccownname_14(String accownname_14) {
        this.accownname_14 = accownname_14;
    }

    public String getAccownname_15() {
        return accownname_15;
    }

    public void setAccownname_15(String accownname_15) {
        this.accownname_15 = accownname_15;
    }




public String getAccownnum_1(){
        if(accownnum_1 != null && accownnum_1.trim().length() > 0){
            return encrypt.decrypt(accownnum_1);
         }
        else {
        return "";
        }
    }

    public void setAccownnum_1(String accountnumber) {
        if(accountnumber != null && accountnumber.trim().length() > 0){
             this.accownnum_1 = encrypt.encrypt(accountnumber);
        } else {
            this.accownnum_1= getAccountnumberEncrypt();            
          }

     }


    public String getAccownnum_2() {
        if(accownnum_2 != null && accownnum_2.trim().length() > 0){           
            return encrypt.decrypt(accownnum_2);                 
        }    
        else {
        return "";
        }
    }

    public void setAccownnum_2(String accownnum_2) {
        if(accownnum_2 != null && accownnum_2.trim().length() > 0){                       
             this.accownnum_2 = encrypt.encrypt(accownnum_2);
        } else {    
            this.accownnum_2=accownnum_2;
          }          
    }

    public String getAccownnum_3() {
        if(accownnum_3 != null && accownnum_3.trim().length() > 0){            
            return encrypt.decrypt(accownnum_3);
         }
        else {
        return "";
        }
    }

    public void setAccownnum_3(String accownnum_3) {
        if(accownnum_3 != null && accownnum_3.trim().length() > 0){                       
             this.accownnum_3 = encrypt.encrypt(accownnum_3);           
        } else {    
            this.accownnum_3=accownnum_3;
          }          
    }

    public String getAccownnum_4() {
        if(accownnum_4 != null && accownnum_4.trim().length() > 0){            
            return encrypt.decrypt(accownnum_4);  
         }
        else {
        return "";
        }        
    }

    public void setAccownnum_4(String accownnum_4) {
        if(accownnum_4 != null && accownnum_4.trim().length() > 0){
             this.accownnum_4 = encrypt.encrypt(accownnum_4);            
        } else {
            this.accownnum_4=accownnum_4;
          }     
    }

    public String getAccownnum_5() {
        if(accownnum_5 != null && accownnum_5.trim().length() > 0){
            return encrypt.decrypt(accownnum_5);           
         }
        else {
        return ""; 
        }
    }

    public void setAccownnum_5(String accownnum_5) {
        if(accownnum_5 != null && accownnum_5.trim().length() > 0){           
             this.accownnum_5 = encrypt.encrypt(accownnum_5);            
        } else {
            this.accownnum_5=accownnum_5;
          }  
    }


    public String getAccownnum_6() {
        if(accownnum_6 != null && accownnum_6.trim().length() > 0){
            return encrypt.decrypt(accownnum_6);
         }
        else {
        return "";
        }
    }

    public void setAccownnum_6(String accownnum_6) {
        if(accownnum_6 != null && accownnum_6.trim().length() > 0){
             this.accownnum_6 = encrypt.encrypt(accownnum_6);
        } else {
            this.accownnum_6=accownnum_6;
          }
    }

     public String getAccownnum_7() {
        if(accownnum_7 != null && accownnum_7.trim().length() > 0){
            return encrypt.decrypt(accownnum_7);
         }
        else {
        return "";
        }
    }

    public void setAccownnum_7(String accownnum_7) {
        if(accownnum_7 != null && accownnum_7.trim().length() > 0){
             this.accownnum_7 = encrypt.encrypt(accownnum_7);
        } else {
            this.accownnum_7=accownnum_7;
          }
    }


     public String getAccownnum_8() {
        if(accownnum_8 != null && accownnum_8.trim().length() > 0){
            return encrypt.decrypt(accownnum_8);
         }
        else {
        return "";
        }
    }

    public void setAccownnum_8(String accownnum_8) {
        if(accownnum_8 != null && accownnum_8.trim().length() > 0){
             this.accownnum_8 = encrypt.encrypt(accownnum_8);
        } else {
            this.accownnum_8=accownnum_8;
          }
    }

     public String getAccownnum_9() {
        if(accownnum_9 != null && accownnum_9.trim().length() > 0){
            return encrypt.decrypt(accownnum_9);
         }
        else {
        return "";
        }
    }

    public void setAccownnum_9(String accownnum_9) {
        if(accownnum_9 != null && accownnum_9.trim().length() > 0){
             this.accownnum_9 = encrypt.encrypt(accownnum_9);
        } else {
            this.accownnum_9=accownnum_9;
          }
    }

     public String getAccownnum_10() {
        if(accownnum_10 != null && accownnum_10.trim().length() > 0){
            return encrypt.decrypt(accownnum_10);
         }
        else {
        return "";
        }
    }


    public void setAccownnum_10(String accownnum_10) {
        if(accownnum_10 != null && accownnum_10.trim().length() > 0){
             this.accownnum_10 = encrypt.encrypt(accownnum_10);
        } else {
            this.accownnum_10=accownnum_10;
          }
    }

     public String getAccownnum_11() {
        if(accownnum_11 != null && accownnum_11.trim().length() > 0){
            return encrypt.decrypt(accownnum_11);
         }
        else {
        return "";
        }
    }

    public void setAccownnum_11(String accownnum_11) {
        if(accownnum_11 != null && accownnum_11.trim().length() > 0){
             this.accownnum_11 = encrypt.encrypt(accownnum_11);
        } else {
            this.accownnum_11=accownnum_11;
          }
    }

    public String getAccownnum_12() {
        if(accownnum_12 != null && accownnum_12.trim().length() > 0){
            return encrypt.decrypt(accownnum_12);
         }
        else {
        return "";
        }
    }

    public void setAccownnum_12(String accownnum_12) {
        if(accownnum_12 != null && accownnum_12.trim().length() > 0){
             this.accownnum_12 = encrypt.encrypt(accownnum_12);
        } else {
            this.accownnum_12=accownnum_12;
          }
    }

    public String getAccownnum_13() {
        if(accownnum_13 != null && accownnum_13.trim().length() > 0){
            return encrypt.decrypt(accownnum_13);
         }
        else {
        return "";
        }
    }

    public void setAccownnum_13(String accownnum_13) {
        if(accownnum_13 != null && accownnum_13.trim().length() > 0){
             this.accownnum_13 = encrypt.encrypt(accownnum_13);
        } else {
            this.accownnum_13=accownnum_13;
          }
    }
    public String getAccownnum_14() {
        if(accownnum_14 != null && accownnum_14.trim().length() > 0){
            return encrypt.decrypt(accownnum_14);
         }
        else {
        return "";
        }
    }

    public void setAccownnum_14(String accownnum_14) {
        if(accownnum_14 != null && accownnum_14.trim().length() > 0){
             this.accownnum_14 = encrypt.encrypt(accownnum_14);
        } else {
            this.accownnum_14=accownnum_14;
          }
    }
    public String getAccownnum_15() {
        if(accownnum_15 != null && accownnum_15.trim().length() > 0){
            return encrypt.decrypt(accownnum_15);
         }
        else {
        return "";
        }
    }

    public void setAccownnum_15(String accownnum_15) {
        if(accownnum_15 != null && accownnum_15.trim().length() > 0){
             this.accownnum_15 = encrypt.encrypt(accownnum_15);
        } else {
            this.accownnum_15=accownnum_15;
          }
    }

    

    public String getEmailtoken_1() {
        return emailtoken_1;
    }

    public void setEmailtoken_1(String emailtoken_1) {
        this.emailtoken_1 = emailtoken_1;
    }
    
    public String getEmailtoken_2() {
        return emailtoken_2;
    }

    public void setEmailtoken_2(String emailtoken_2) {
        this.emailtoken_2 = emailtoken_2;
    }

    public String getEmailtoken_3() {
        return emailtoken_3;
    }

    public void setEmailtoken_3(String emailtoken_3) {
        this.emailtoken_3 = emailtoken_3;
    }

    public String getEmailtoken_4() {
        return emailtoken_4;
    }

    public void setEmailtoken_4(String emailtoken_4) {
        this.emailtoken_4 = emailtoken_4;
    }

    public String getEmailtoken_5() {
        return emailtoken_5;
    }

    public void setEmailtoken_5(String emailtoken_5) {
        this.emailtoken_5 = emailtoken_5;
    }

    public String getEmailtoken_6() {
        return emailtoken_6;
    }

    public void setEmailtoken_6(String emailtoken_6) {
        this.emailtoken_6 = emailtoken_6;
    }

    public String getEmailtoken_10() {
        return emailtoken_10;
    }

    public void setEmailtoken_10(String emailtoken_10) {
        this.emailtoken_10 = emailtoken_10;
    }

    public String getEmailtoken_11() {
        return emailtoken_11;
    }

    public void setEmailtoken_11(String emailtoken_11) {
        this.emailtoken_11 = emailtoken_11;
    }

    public String getEmailtoken_12() {
        return emailtoken_12;
    }

    public void setEmailtoken_12(String emailtoken_12) {
        this.emailtoken_12 = emailtoken_12;
    }

    public String getEmailtoken_7() {
        return emailtoken_7;
    }

    public void setEmailtoken_7(String emailtoken_7) {
        this.emailtoken_7 = emailtoken_7;
    }

    public String getEmailtoken_8() {
        return emailtoken_8;
    }

    public void setEmailtoken_8(String emailtoken_8) {
        this.emailtoken_8 = emailtoken_8;
    }

    public String getEmailtoken_9() {
        return emailtoken_9;
    }

    public void setEmailtoken_9(String emailtoken_9) {
        this.emailtoken_9 = emailtoken_9;
    }

    public String getSerialtoken_2() {
        return serialtoken_2;
    }

    public void setSerialtoken_2(String serialtoken_2) {
        this.serialtoken_2 = serialtoken_2;
    }

    public String getSerialtoken_3() {
        return serialtoken_3;
    }

    public void setSerialtoken_3(String serialtoken_3) {
        this.serialtoken_3 = serialtoken_3;
    }

    public String getSerialtoken_4() {
        return serialtoken_4;
    }

    public void setSerialtoken_4(String serialtoken_4) {
        this.serialtoken_4 = serialtoken_4;
    }

    public String getSerialtoken_5() {
        return serialtoken_5;
    }

    public void setSerialtoken_5(String serialtoken_5) {
        this.serialtoken_5 = serialtoken_5;
    }

    public String getSerialtoken_6() {
        return serialtoken_6;
    }

    public void setSerialtoken_6(String serialtoken_6) {
        this.serialtoken_6 = serialtoken_6;
    }

    public String getSerialtoken_10() {
        return serialtoken_10;
    }

    public void setSerialtoken_10(String serialtoken_10) {
        this.serialtoken_10 = serialtoken_10;
    }

    public String getSerialtoken_11() {
        return serialtoken_11;
    }

    public void setSerialtoken_11(String serialtoken_11) {
        this.serialtoken_11 = serialtoken_11;
    }

    public String getSerialtoken_12() {
        return serialtoken_12;
    }

    public void setSerialtoken_12(String serialtoken_12) {
        this.serialtoken_12 = serialtoken_12;
    }

    public String getSerialtoken_7() {
        return serialtoken_7;
    }

    public void setSerialtoken_7(String serialtoken_7) {
        this.serialtoken_7 = serialtoken_7;
    }

    public String getSerialtoken_8() {
        return serialtoken_8;
    }

    public void setSerialtoken_8(String serialtoken_8) {
        this.serialtoken_8 = serialtoken_8;
    }

    public String getSerialtoken_9() {
        return serialtoken_9;
    }

    public void setSerialtoken_9(String serialtoken_9) {
        this.serialtoken_9 = serialtoken_9;
    }






    public String getAccothersbranch_1() {
        return accothersbranch_1;
    }

    public void setAccothersbranch_1(String accothersbranch_1) {
        this.accothersbranch_1 = accothersbranch_1;
    }

    public String getAccotherscr_1() {
        return accotherscr_1;
    }

    public void setAccotherscr_1(String accotherscr_1) {
        this.accotherscr_1 = accotherscr_1;
    }

    public String getAccothersnum_1() {
        if(accothersnum_1 != null && accothersnum_1.trim().length() > 0){
            return encrypt.decrypt(accothersnum_1);    
         }
        else {
        return ""; 
        } 
    }

    public void setAccothersnum_1(String accothersnum_1) {
        if(accothersnum_1 != null && accothersnum_1.trim().length() > 0){
             this.accothersnum_1 = encrypt.encrypt(accothersnum_1);             
        } else {
            this.accothersnum_1 = accothersnum_1;
          }  
    }

    public String getAccothersofficername_1() {
        return accothersofficername_1;
    }

    public void setAccothersofficername_1(String accothersofficername_1) {
        this.accothersofficername_1 = accothersofficername_1;
    }

    public String getAccountnumber() {
        if(accountnumber != null && accountnumber.trim().length() > 0){
            return encrypt.decrypt(accountnumber);                 
            
         }
        else {
        return ""; 
        }        
       
    }

    public String getAccountnumberEncrypt(){
        return accountnumber;
    }    
    
    public void setAccountnumber(String accountnumber) {        
         if(accountnumber != null && accountnumber.trim().length() > 0){                       
             this.accountnumber = encrypt.encrypt(accountnumber);             
        } else {
            this.accountnumber = accountnumber;
          }          
    }
    
    public void setAccountnumberDecrypt(String accountnumber) {     
        this.accountnumber = accountnumber;                  
    }

    public String getAccownmerchantname_1() {
        return accownmerchantname_1;
    }

    public void setAccownmerchantname_1(String accownmerchantname_1) {
        this.accownmerchantname_1 = accownmerchantname_1;
    }

    public String getAccownname_1() {
        return accownname_1;
    }

    public void setAccownname_1(String accownname_1){        
            this.accownname_1 = accownname_1;      
    }

    

     public String getAccownnumEncrypt_1(){       
        return accownnum_1;         
     }

     public String getAccownnumEncrypt_2(){
        return accownnum_2;
     }

     public String getAccownnumEncrypt_3(){
        return accownnum_3;
     }

     public String getAccownnumEncrypt_4(){
        return accownnum_4;
     }
     
     public String getAccownnumEncrypt_5(){       
        return accownnum_5;         
     }
     
     public String getAccownnumEncrypt_6(){
        return accownnum_6;
     }

     public String getAccownnumEncrypt_7(){
        return accownnum_7;
     }

     public String getAccownnumEncrypt_8(){
        return accownnum_8;
     }

     public String getAccownnumEncrypt_9(){
        return accownnum_9;
     }
     
     public String getAccownnumEncrypt_10(){
        return accownnum_10;         
     }

     
     public String getAccownnumEncrypt_11(){
        return accownnum_11;
     }

     public String getAccownnumEncrypt_12(){
        return accownnum_12;
     }

     public String getAccownnumEncrypt_13(){
        return accownnum_13;
     }

     public String getAccownnumEncrypt_14(){
        return accownnum_14;
     }
     
     public String getAccownnumEncrypt_15(){
        return accownnum_15;
     }


     


     public String getAccothersnumEncrypt_1(){
        return accothersnum_1;         
     }
     
      public String getAccothersnumEncrypt_2(){
        return accothersnum_2;
     }
       
     public String getAccothersnumEncrypt_3(){
        return accothersnum_3;         
     }
     
     public String getAccothersnumEncrypt_4(){
        return accothersnum_4;
     }
      public String getAccothersnumEncrypt_5(){
        return accothersnum_5;         
     }
     
    public void setAccownnumDecrypt_1(String accownnum_1) {         
            this.accownnum_1=accownnum_1;          
     }    
     
    public void setAccownnumDecrypt_2(String accownnum_2) {
            this.accownnum_2=accownnum_2;
     } 
    
    public void setAccownnumDecrypt_3(String accownnum_3) {
            this.accownnum_3=accownnum_3;
     } 
    
    public void setAccownnumDecrypt_4(String accownnum_4) {
            this.accownnum_4=accownnum_4;
     } 
    
    public void setAccownnumDecrypt_5(String accownnum_5) {         
            this.accownnum_5=accownnum_5;          
     } 
    
    public void setAccownnumDecrypt_6(String accownnum_6) {         
            this.accownnum_6=accownnum_6;          
     }    
     
    public void setAccownnumDecrypt_7(String accownnum_7) {
            this.accownnum_7=accownnum_7;
     }   
    
    public void setAccownnumDecrypt_8(String accownnum_8) {
            this.accownnum_8=accownnum_8;
     } 
    
    public void setAccownnumDecrypt_9(String accownnum_9) {         
            this.accownnum_9=accownnum_9;          
     } 
      public void setAccownnumDecrypt_10(String accownnum_10) {
            this.accownnum_10=accownnum_10;
     } 
    
    public void setAccownnumDecrypt_11(String accownnum_11) {
            this.accownnum_11=accownnum_11;
     }    
     
    public void setAccownnumDecrypt_12(String accownnum_12) {
            this.accownnum_12=accownnum_12;
     } 
    
    public void setAccownnumDecrypt_13(String accownnum_13) {
            this.accownnum_13=accownnum_13;
     } 
    
    public void setAccownnumDecrypt_14(String accownnum_14) {
            this.accownnum_14=accownnum_14;
     } 
    
    public void setAccownnumDecrypt_15(String accownnum_15) {
            this.accownnum_15=accownnum_15;
     } 
    
   
    public void setAccothersnumDecrypt_1(String accothersnum_1) {        
            this.accothersnum_1 = accothersnum_1;          
    }
    
    public void setAccothersnumDecrypt_2(String accothersnum_2) {
            this.accothersnum_2 = accothersnum_2;
    }
    
    public void setAccothersnumDecrypt_3(String accothersnum_3) {
            this.accothersnum_3 = accothersnum_3;
    }
    
    public void setAccothersnumDecrypt_4(String accothersnum_4) {
            this.accothersnum_4 = accothersnum_4;
    }
    
    public void setAccothersnumDecrypt_5(String accothersnum_5) {        
            this.accothersnum_5 = accothersnum_5;          
    }
    
    

    public String getQuantaccothers() {
        return quantaccothers;
    }

    public void setQuantaccothers(String quantaccothers) {
        this.quantaccothers = quantaccothers;
    }
    
    public SelectItem[] getQuantaccothersArray() {
        if (this.quantaccothersArray == null) {
            Attribute att = this.getAttributeMB().getByFieldname("quantaccothers");
            if (att != null) {
                Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
                if (attOptCollection != null) {
                    this.quantaccothersArray = new SelectItem[attOptCollection.size()];
                    int i = 0;
                    for (AttributeOption attOpt : attOptCollection) {
                        this.quantaccothersArray[i] = new SelectItem(attOpt.getDescription(), attOpt.getDescription());
                        i++;
                    }
                }
            }
        }
        return this.quantaccothersArray;
    }
    
    public void setQuantaccothersArray(SelectItem[] quantaccothersArray) {
        this.quantaccothersArray = quantaccothersArray;
    }

    public String getQuantaccown() {
        return quantaccown;
    }

    public void setQuantaccown(String quantaccown) {
        this.quantaccown = quantaccown;
    }
    
    public SelectItem[] getQuantaccownArray() {
        if (this.quantaccownArray == null) {
            Attribute att = this.getAttributeMB().getByFieldname("quantaccown");
            if (att != null) {
                Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
                if (attOptCollection != null) {
                    this.quantaccownArray = new SelectItem[attOptCollection.size()];
                    int i = 0;
                    for (AttributeOption attOpt : attOptCollection) {
                        this.quantaccownArray[i] = new SelectItem(attOpt.getDescription(), attOpt.getDescription());
                        i++;
                    }
                }
            }
        }
        return this.quantaccownArray;
    }
    
    public void setQuantaccownArray(SelectItem[] quantaccownArray) {
        this.quantaccownArray = quantaccownArray;
    }

    public String getQuanttokens() {
        return quanttokens;
    }

    public void setQuanttokens(String quanttokens) {
        this.quanttokens = quanttokens;
    }

    public String getSelectedbemplan() {
        return selectedbemplan;
    }

    public void setSelectedbemplan(String selectedbemplan) {
        this.selectedbemplan = selectedbemplan;
    }

    public String getSerialtoken_1() {
        return serialtoken_1;
    }

    public void setSerialtoken_1(String serialtoken_1) {
        this.serialtoken_1 = serialtoken_1;
    }

    public String getCommchargetype() {
        return commchargetype;
    }

    public void setCommchargetype(String commchargetype) {
        this.commchargetype = commchargetype;
    }


    //Folio Token 19 Agosto 2009

    public String getReferencetoken_1() {
        return referencetoken_1;
    }

    public void setReferencetoken_1(String referencetoken_1) {
        this.referencetoken_1 = referencetoken_1;
    }

    public String getReferencetoken_2() {
        return referencetoken_2;
    }

    public void setReferencetoken_2(String referencetoken_2) {
        this.referencetoken_2 = referencetoken_2;
    }

    public String getReferencetoken_3() {
        return referencetoken_3;
    }

    public void setReferencetoken_3(String referencetoken_3) {
        this.referencetoken_3 = referencetoken_3;
    }

    public String getReferencetoken_4() {
        return referencetoken_4;
    }

    public void setReferencetoken_4(String referencetoken_4) {
        this.referencetoken_4 = referencetoken_4;
    }

    public String getReferencetoken_5() {
        return referencetoken_5;
    }

    public void setReferencetoken_5(String referencetoken_5) {
        this.referencetoken_5 = referencetoken_5;
    }

    public String getReferencetoken_6() {
        return referencetoken_6;
    }

    public void setReferencetoken_6(String referencetoken_6) {
        this.referencetoken_6 = referencetoken_6;
    }

    public String getReferencetoken_10() {
        return referencetoken_10;
    }

    public void setReferencetoken_10(String referencetoken_10) {
        this.referencetoken_10 = referencetoken_10;
    }

    public String getReferencetoken_11() {
        return referencetoken_11;
    }

    public void setReferencetoken_11(String referencetoken_11) {
        this.referencetoken_11 = referencetoken_11;
    }

    public String getReferencetoken_12() {
        return referencetoken_12;
    }

    public void setReferencetoken_12(String referencetoken_12) {
        this.referencetoken_12 = referencetoken_12;
    }

    public String getReferencetoken_7() {
        return referencetoken_7;
    }

    public void setReferencetoken_7(String referencetoken_7) {
        this.referencetoken_7 = referencetoken_7;
    }

    public String getReferencetoken_8() {
        return referencetoken_8;
    }

    public void setReferencetoken_8(String referencetoken_8) {
        this.referencetoken_8 = referencetoken_8;
    }

    public String getReferencetoken_9() {
        return referencetoken_9;
    }

    public void setReferencetoken_9(String referencetoken_9) {
        this.referencetoken_9 = referencetoken_9;
    }

    public boolean isloadflag() {
        return loadflag;
    }

    public void setLoadflag(boolean loadflag) {
        this.loadflag = loadflag;
    }
    public boolean getLoadflag() {
        return this.loadflag;
    }

    //Banderas de las pantallas

    
    public boolean[] getTokenflag() {
        return tokenflag;
    }

    public void setTokenflag(boolean[] tokenflag) {
        this.tokenflag = tokenflag;
    }

    public boolean[] getAccownflag() {
        return accownflag;
    }

    public void setAccownflag(boolean[] accownflag) {
        this.accownflag = accownflag;
    }

    public boolean[] getAccotherflag() {
        return accotherflag;
    }

    public void setAccotherflag(boolean[] accotherflag) {
        this.accotherflag = accotherflag;
    }

    public SelectItem[] getSelectedbemplanArray() {
        if (this.selectedbemplanArray == null) {
            Attribute att = this.getAttributeMB().getByFieldname("selectedbemplan");
            if (att != null) {
                Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
                
                List<AttributeOption> listaAtt=(List<AttributeOption>)attOptCollection;
                Collections.sort(listaAtt, new AttributeOptionComparator());
                
                if (listaAtt != null) {
                    this.selectedbemplanArray = new SelectItem[listaAtt.size()];
                    int i = 0;
                    for (AttributeOption attOpt : listaAtt) {
                        this.selectedbemplanArray[i] = new SelectItem(attOpt.getDescription(), attOpt.getDescription());
                        i++;
                    }
                }
            }
        }
        return this.selectedbemplanArray;
    }

    public void setSelectedbemplanArray(SelectItem[] selectedbemplanArray) {
        this.selectedbemplanArray = selectedbemplanArray;
    }

    public SelectItem[] getQuanttokensArray() {
        if (this.quanttokensArray == null) {
            Attribute att = this.getAttributeMB().getByFieldname("quanttokens");
            if (att != null) {
                Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
                if (attOptCollection != null) {
                    this.quanttokensArray = new SelectItem[attOptCollection.size()];
                    int i = 0;
                    for (AttributeOption attOpt : attOptCollection) {
                        this.quanttokensArray[i] = new SelectItem(attOpt.getDescription(), attOpt.getDescription());
                        i++;
                    }
                }
            }
        }
        return this.quanttokensArray;
    }

    public void setQuanttokensArray(SelectItem[] quanttokensArray) {
        this.quanttokensArray = quanttokensArray;
    }

    public SelectItem[] getCommchargetypeArray() {
        if (this.commchargetypeArray == null) {
            Attribute att = this.getAttributeMB().getByFieldname("commchargetype");
            if (att != null) {
                Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
                if (attOptCollection != null) {
                    this.commchargetypeArray = new SelectItem[attOptCollection.size()];
                    int i = 0;
                    for (AttributeOption attOpt : attOptCollection) {
                        this.commchargetypeArray[i] = new SelectItem(attOpt.getDescription(), attOpt.getDescription());
                        i++;
                    }
                }
            }
        }
        return this.commchargetypeArray;
    }

    public void setCommchargetypeArray(SelectItem[] commchargetypeArray) {
        this.commchargetypeArray = commchargetypeArray;
    }

    
     public ArrayList<ContractMessageErrors> getOrdenErrorsList() {
        return ordenErrorsList;
    }

    public void setOrdenErrorsList(ArrayList<ContractMessageErrors> ordenErrorsList) {
        this.ordenErrorsList = ordenErrorsList;
    }

    /*
     * Property: regimenArray
     */
    public void setRegimenArray(SelectItem[] regimenArray) {
        this.regimenArray = regimenArray;
    }
    public SelectItem[] getRegimenArray() {
        if (this.regimenArray == null) {
            Attribute att = this.getAttributeMB().getByFieldname("regimen");
            if (att != null) {
                Collection<AttributeOption> attOptCollection = att.getAttributeOptionCollection();
                if (attOptCollection != null) {
                    this.regimenArray = new SelectItem[attOptCollection.size()];
                    
                    int i = 0;
                    for (AttributeOption attOpt : attOptCollection) {
                        this.regimenArray[i] = new SelectItem(attOpt.getDescription(), attOpt.getDescription());
                        i++;
                    }
                }
            }
        }
        return this.regimenArray;
    }
    
    /*
     *Property: Regimen_1 
     */
    public void setRegimen_1(String regimen_1) {
        this.regimen_1 = regimen_1;
    }
    public String getRegimen_1() {
        return this.regimen_1;
    }
    
    /*
     *Property: Regimen_2 
     */
    public void setRegimen_2(String regimen_2) {
        this.regimen_2 = regimen_2;
    }
    public String getRegimen_2() {
        return this.regimen_2;
    }
    
    /*
     *Property: Regimen_3 
     */
    public void setRegimen_3(String regimen_3) {
        this.regimen_3 = regimen_3;
    }
    public String getRegimen_3() {
        return this.regimen_3;
    } 
    
    /*
     *Property: Regimen_4 
     */
    public void setRegimen_4(String regimen_4) {
        this.regimen_4 = regimen_4;
    }
    public String getRegimen_4() {
        return this.regimen_4;
    }
    
    /*
     *Property: Regimen_5 
     */
    public void setRegimen_5(String regimen_5) {
        this.regimen_5 = regimen_5;
    }
    public String getRegimen_5() {
        return this.regimen_5;
    }
    
    /*
     *Property: Regimen_6 
     */
    public void setRegimen_6(String regimen_6) {
        this.regimen_6 = regimen_6;
    }
    public String getRegimen_6() {
        return this.regimen_6;
    }
    
    /*
     *Property: Regimen_7 
     */
    public void setRegimen_7(String regimen_7) {
        this.regimen_7 = regimen_7;
    }
    public String getRegimen_7() {
        return this.regimen_7;
    }    
    
    /*
     *Property: Regimen_8 
     */
    public void setRegimen_8(String regimen_8) {
        this.regimen_8 = regimen_8;
    }
    public String getRegimen_8() {
        return this.regimen_8;
    }
    
    /*
     *Property: Regimen_9 
     */
    public void setRegimen_9(String regimen_9) {
        this.regimen_9 = regimen_9;
    }
    public String getRegimen_9() {
        return this.regimen_9;
    }
    
    /*
     *Property: Regimen_10 
     */
    public void setRegimen_10(String regimen_10) {
        this.regimen_10 = regimen_10;
    }
    public String getRegimen_10() {
        return this.regimen_10;
    }
    
    /*
     *Property: Regimen_11 
     */
    public void setRegimen_11(String regimen_11) {
        this.regimen_11 = regimen_11;
    }
    public String getRegimen_11() {
        return this.regimen_11;
    }
    
    /*
     *Property: Regimen_12 
     */
    public void setRegimen_12(String regimen_12) {
        this.regimen_12 = regimen_12;
    }
    public String getRegimen_12() {
        return this.regimen_12;
    }
    
    /*
     *Property: Regimen_13 
     */
    public void setRegimen_13(String regimen_13) {
        this.regimen_13 = regimen_13;
    }
    public String getRegimen_13() {
        return this.regimen_13;
    }
    
    /*
     *Property: Regimen_14 
     */
    public void setRegimen_14(String regimen_14) {
        this.regimen_14 = regimen_14;
    }
    public String getRegimen_14() {
        return this.regimen_14;
    }
    
    /*
     *Property: Regimen_15 
     */
    public void setRegimen_15(String regimen_15) {
        this.regimen_15 = regimen_15;
    }
    public String getRegimen_15() {
        return this.regimen_15;
    }
    
    public void loadOrdenBEM(){
       loadflag=true; 

       ordenErrorsList.clear();         
         if(this.getErrorsList()!=null){
          this.getErrorsList().clear();
         }
       
       //Banderas Tokens,cuentas propias y cuentas terceros
       Integer tokens = Integer.parseInt(this.getQuanttokens());
       Integer accowns= Integer.parseInt(this.getQuantaccown());       
       Integer accothers= Integer.parseInt(this.getQuantaccothers());


        for (int i=2; i<=11; i++){
            if(i<tokens)
              tokenflag[i]=true;
            else
              tokenflag[i]=false;  
        }
        //Banderas Cuentas propias        
        for (int i=1; i<=14; i++){
            if (i<accowns)
              accownflag[i]=true;
            else
              accownflag[i]=false;  
        }
       
       //Banderas Cuentas terceros
        for (int i=0; i<=4; i++){
            if (i<accothers)
              accotherflag[i]=true;
            else
              accotherflag[i]=false;  
        }

    }
    
    public String validateOrdenBEM(){ 
    	
         ordenErrorsList.clear();
         ContractMessageErrors errors;
         if(this.getErrorsList()!=null){
          this.getErrorsList().clear();
         }

     int tokens = Integer.parseInt(this.getQuanttokens());
     int accowns= Integer.parseInt(this.getQuantaccown());
     int accothers=Integer.parseInt(this.getQuantaccothers());
         
     
     
     if (this.getClient_sic()==null || this.getClient_sic().trim().length()<=0){
              errors= new ContractMessageErrors();
              errors.setMessage("Favor de especificar Datos Especiales BEM - Numero de cliente");
              ordenErrorsList.add(errors);     
     }

     //Tokens

      if (this.getSerialtoken_1()==null || this.getSerialtoken_1().trim().length()!=9){
              errors= new ContractMessageErrors();
              errors.setMessage("Favor de especificar Datos Contratacion Tokens - Serie de Token 1");
              ordenErrorsList.add(errors);     
     }
     
     if (this.getReferencetoken_1()==null || this.getReferencetoken_1().trim().length()<=0){
              errors= new ContractMessageErrors();
              errors.setMessage("Favor de especificar Datos Contratación Tokens - Folio de Token 1");
              ordenErrorsList.add(errors);     
     }
     
     if (this.getEmailtoken_1()==null || this.getEmailtoken_1().trim().length()<=0){
              errors= new ContractMessageErrors();
              errors.setMessage("Favor de especificar Datos Contratación Tokens - Email de Token 1");
              ordenErrorsList.add(errors);    
     }
     
     
     if (this.getSerialtoken_2()==null || this.getSerialtoken_2().trim().length()!=9){
              errors= new ContractMessageErrors();
              errors.setMessage("Favor de especificar Datos Contratación Tokens - Serie de Token 2");
              ordenErrorsList.add(errors);     
     }
     
     if (this.getReferencetoken_2()==null || this.getReferencetoken_2().trim().length()<=0){
              errors= new ContractMessageErrors();
              errors.setMessage("Favor de especificar Datos Contratación Tokens - Folio de Token 2");
              ordenErrorsList.add(errors);     
     }
     
     if (this.getEmailtoken_2()==null || this.getEmailtoken_2().trim().length()<=0){
              errors= new ContractMessageErrors();
              errors.setMessage("Favor de especificar Datos Contratación Tokens - Email de Token 2");
              ordenErrorsList.add(errors);    
     }
          
     
     
     if(tokens>=3){
                  if (this.getSerialtoken_3()==null || this.getSerialtoken_3().trim().length()!=9){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Favor de especificar Datos Contratación Tokens - Serie de Token 3");
                      ordenErrorsList.add(errors);
                  }

                 if (this.getReferencetoken_3()==null || this.getReferencetoken_3().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Folio de Token 3");
                          ordenErrorsList.add(errors);
                 }

                 if (this.getEmailtoken_3()==null || this.getEmailtoken_3().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Email de Token 3");
                          ordenErrorsList.add(errors);
                 }
        }
         
     
     
     if(tokens>=4){
                  if (this.getSerialtoken_4()==null || this.getSerialtoken_4().trim().length()!=9){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Favor de especificar Datos Contratación Tokens - Serie de Token 4");
                      ordenErrorsList.add(errors);
                  }

                 if (this.getReferencetoken_4()==null || this.getReferencetoken_4().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Folio de Token 4");
                          ordenErrorsList.add(errors);
                 }

                 if (this.getEmailtoken_4()==null || this.getEmailtoken_4().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Email de Token 4");
                          ordenErrorsList.add(errors);
                 }
        }
     
     if(tokens>=5){
                  if (this.getSerialtoken_5()==null || this.getSerialtoken_5().trim().length()!=9){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Favor de especificar Datos Contratación Tokens - Serie de Token 5");
                      ordenErrorsList.add(errors);
                  }

                 if (this.getReferencetoken_5()==null || this.getReferencetoken_5().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Folio de Token 5");
                          ordenErrorsList.add(errors);
                 }

                 if (this.getEmailtoken_5()==null || this.getEmailtoken_5().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Email de Token 5");
                          ordenErrorsList.add(errors);
                 }
        }
     
     if(tokens>=6){
                  if (this.getSerialtoken_6()==null || this.getSerialtoken_6().trim().length()!=9){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Favor de especificar Datos Contratación Tokens - Serie de Token 6");
                      ordenErrorsList.add(errors);
                  }

                 if (this.getReferencetoken_6()==null || this.getReferencetoken_3().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Folio de Token 6");
                          ordenErrorsList.add(errors);
                 }

                 if (this.getEmailtoken_6()==null || this.getEmailtoken_6().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Email de Token 6");
                          ordenErrorsList.add(errors);
                 }
        }
     
     if(tokens>=7){
                  if (this.getSerialtoken_7()==null || this.getSerialtoken_7().trim().length()!=9){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Favor de especificar Datos Contratación Tokens - Serie de Token 7");
                      ordenErrorsList.add(errors);
                  }

                 if (this.getReferencetoken_7()==null || this.getReferencetoken_7().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Folio de Token 7");
                          ordenErrorsList.add(errors);
                 }

                 if (this.getEmailtoken_7()==null || this.getEmailtoken_7().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Email de Token 7");
                          ordenErrorsList.add(errors);
                 }
        }
     
     if(tokens>=8){
                  if (this.getSerialtoken_8()==null || this.getSerialtoken_8().trim().length()!=9){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Favor de especificar Datos Contratación Tokens - Serie de Token 8");
                      ordenErrorsList.add(errors);
                  }

                 if (this.getReferencetoken_8()==null || this.getReferencetoken_8().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Folio de Token 8");
                          ordenErrorsList.add(errors);
                 }

                 if (this.getEmailtoken_8()==null || this.getEmailtoken_8().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Email de Token 8");
                          ordenErrorsList.add(errors);
                 }
        }
     
     if(tokens>=9){
                  if (this.getSerialtoken_9()==null || this.getSerialtoken_9().trim().length()!=9){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Favor de especificar Datos Contratación Tokens - Serie de Token 9");
                      ordenErrorsList.add(errors);
                  }

                 if (this.getReferencetoken_9()==null || this.getReferencetoken_9().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Folio de Token 9");
                          ordenErrorsList.add(errors);
                 }

                 if (this.getEmailtoken_9()==null || this.getEmailtoken_9().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Email de Token 9");
                          ordenErrorsList.add(errors);
                 }
        }
     
     if(tokens>=10){
                  if (this.getSerialtoken_10()==null || this.getSerialtoken_10().trim().length()!=9){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Favor de especificar Datos Contratación Tokens - Serie de Token 10");
                      ordenErrorsList.add(errors);
                  }

                 if (this.getReferencetoken_10()==null || this.getReferencetoken_10().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Folio de Token 10");
                          ordenErrorsList.add(errors);
                 }

                 if (this.getEmailtoken_10()==null || this.getEmailtoken_10().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Email de Token 10");
                          ordenErrorsList.add(errors);
                 }
        }
     
      
     if(tokens>=11){
                  if (this.getSerialtoken_11()==null || this.getSerialtoken_11().trim().length()!=9){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Favor de especificar Datos Contratación Tokens - Serie de Token 11");
                      ordenErrorsList.add(errors);
                  }

                 if (this.getReferencetoken_11()==null || this.getReferencetoken_11().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Folio de Token 11");
                          ordenErrorsList.add(errors);
                 }

                 if (this.getEmailtoken_11()==null || this.getEmailtoken_11().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Email de Token 11");
                          ordenErrorsList.add(errors);
                 }
        }

     if(tokens>=12){
                  if (this.getSerialtoken_12()==null || this.getSerialtoken_12().trim().length()!=9){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Favor de especificar Datos Contratación Tokens - Serie de Token 12");
                      ordenErrorsList.add(errors);
                  }

                 if (this.getReferencetoken_12()==null || this.getReferencetoken_12().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Folio de Token 12");
                          ordenErrorsList.add(errors);
                 }

                 if (this.getEmailtoken_12()==null || this.getEmailtoken_12().trim().length()<=0){
                          errors= new ContractMessageErrors();
                          errors.setMessage("Favor de especificar Datos Contratación Tokens - Email de Token 12");
                          ordenErrorsList.add(errors);
                 }
        }
     
		// Para recuperar el par�metro enviado por el JSP
        //TODO: Quitar comentarios despues de la liberacion de Formatos BEM
//		String duplicatedTokens = FacesContext.getCurrentInstance().getExternalContext()
//				.getRequestParameterMap().get(
//						"duplicatedTokens");	
//		if(duplicatedTokens!=null){
//			if(duplicatedTokens.equals("true")){
//                errors= new ContractMessageErrors();
//	            errors.setMessage("Datos Contratacion Tokens - Existe al menos un Serial de Token duplicado.");
//	            ordenErrorsList.add(errors);
//            }
//		}
     
       //cuentas propias

     if (this.getAccountnumber()==null || this.getAccountnumber().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - Cuenta Cargo");
                      ordenErrorsList.add(errors);
        }
     if (this.getAccownname_1()==null || this.getAccownname_1().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 1");
                      ordenErrorsList.add(errors);
     }else{
    	 //Validar Regimen mancomunado
    	 errors = validarRegimen(this.getRegimen_1(),this.getAccownname_1(),'1');
    	 if(errors != null){
             ordenErrorsList.add(errors);
    	 }
     } 
     
     if(accowns>=2){
		if (this.getAccownnum_2()==null || this.getAccownnum_2().trim().length()<=0){
		          errors= new ContractMessageErrors();
		          errors.setMessage("Cuentas Autorizadas Propias  - No. cuenta 2");
		          ordenErrorsList.add(errors);
		}
		 if (this.getAccownname_2()==null || this.getAccownname_2().trim().length()<=0){
		                  errors= new ContractMessageErrors();
		                  errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 2");
		                  ordenErrorsList.add(errors);
		 }else{
			 //Validar Regimen mancomunado
			 errors = validarRegimen(this.getRegimen_2(),this.getAccownname_2(),'2');
			 if(errors != null){
		         ordenErrorsList.add(errors);
			 }   
		}
     }
     
     
     if(accowns>=3){
            if (this.getAccownnum_3()==null || this.getAccownnum_3().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - No. cuenta 3");
                      ordenErrorsList.add(errors);
            }
             if (this.getAccownname_3()==null || this.getAccownname_3().trim().length()<=0){
                              errors= new ContractMessageErrors();
                              errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 3");
                              ordenErrorsList.add(errors);
             }      
     }
     
     if(accowns>=4){
            if (this.getAccownnum_4()==null || this.getAccownnum_4().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - No. cuenta 4");
                      ordenErrorsList.add(errors);
            }
             if (this.getAccownname_4()==null || this.getAccownname_4().trim().length()<=0){
                              errors= new ContractMessageErrors();
                              errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 4");
                              ordenErrorsList.add(errors);
             }      
     }
     
     
     if(accowns>=5){
            if (this.getAccownnum_5()==null || this.getAccownnum_5().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - No. cuenta 5");
                      ordenErrorsList.add(errors);
            }
             if (this.getAccownname_5()==null || this.getAccownname_5().trim().length()<=0){
                              errors= new ContractMessageErrors();
                              errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 5");
                              ordenErrorsList.add(errors);
             }      
     }
     
     if(accowns>=6){
            if (this.getAccownnum_6()==null || this.getAccownnum_6().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - No. cuenta 6");
                      ordenErrorsList.add(errors);
            }
             if (this.getAccownname_6()==null || this.getAccownname_6().trim().length()<=0){
                              errors= new ContractMessageErrors();
                              errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 6");
                              ordenErrorsList.add(errors);
             }      
     }    
    
     
     if(accowns>=7){
            if (this.getAccownnum_7()==null || this.getAccownnum_7().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - No. cuenta 7");
                      ordenErrorsList.add(errors);
            }
             if (this.getAccownname_7()==null || this.getAccownname_7().trim().length()<=0){
                              errors= new ContractMessageErrors();
                              errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 7");
                              ordenErrorsList.add(errors);
             }      
     }
     
     if(accowns>=8){
            if (this.getAccownnum_8()==null || this.getAccownnum_8().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - No. cuenta 8");
                      ordenErrorsList.add(errors);
            }
             if (this.getAccownname_8()==null || this.getAccownname_8().trim().length()<=0){
                              errors= new ContractMessageErrors();
                              errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 8");
                              ordenErrorsList.add(errors);
             }      
     }
     
     if(accowns>=9){
            if (this.getAccownnum_9()==null || this.getAccownnum_9().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - No. cuenta 9");
                      ordenErrorsList.add(errors);
            }
             if (this.getAccownname_9()==null || this.getAccownname_9().trim().length()<=0){
                              errors= new ContractMessageErrors();
                              errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 9");
                              ordenErrorsList.add(errors);
             }      
     }
     
     if(accowns>=10){
            if (this.getAccownnum_10()==null || this.getAccownnum_10().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - No. cuenta 10");
                      ordenErrorsList.add(errors);
            }
             if (this.getAccownname_10()==null || this.getAccownname_10().trim().length()<=0){
                              errors= new ContractMessageErrors();
                              errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 10");
                              ordenErrorsList.add(errors);
             }      
     }
     
     if(accowns>=11){
            if (this.getAccownnum_11()==null || this.getAccownnum_11().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - No. cuenta 11");
                      ordenErrorsList.add(errors);
            }
             if (this.getAccownname_11()==null || this.getAccownname_11().trim().length()<=0){
                              errors= new ContractMessageErrors();
                              errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 11");
                              ordenErrorsList.add(errors);
             }      
     }
     
     if(accowns>=12){
            if (this.getAccownnum_12()==null || this.getAccownnum_12().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - No. cuenta 12");
                      ordenErrorsList.add(errors);
            }
             if (this.getAccownname_12()==null || this.getAccownname_12().trim().length()<=0){
                              errors= new ContractMessageErrors();
                              errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 12");
                              ordenErrorsList.add(errors);
             }      
     }
     
     if(accowns>=13){
            if (this.getAccownnum_13()==null || this.getAccownnum_13().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - No. cuenta 13");
                      ordenErrorsList.add(errors);
            }
             if (this.getAccownname_13()==null || this.getAccownname_13().trim().length()<=0){
                              errors= new ContractMessageErrors();
                              errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 13");
                              ordenErrorsList.add(errors);
             }      
     }
     
     if(accowns>=14){
            if (this.getAccownnum_14()==null || this.getAccownnum_14().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - No. cuenta 14");
                      ordenErrorsList.add(errors);
            }
             if (this.getAccownname_14()==null || this.getAccownname_14().trim().length()<=0){
                              errors= new ContractMessageErrors();
                              errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 14");
                              ordenErrorsList.add(errors);
             }      
     }
     
     if(accowns==15){
            if (this.getAccownnum_15()==null || this.getAccownnum_15().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas Autorizadas Propias  - No. cuenta 15");
                      ordenErrorsList.add(errors);
            }
             if (this.getAccownname_15()==null || this.getAccownname_15().trim().length()<=0){
                              errors= new ContractMessageErrors();
                              errors.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas 15");
                              ordenErrorsList.add(errors);
             }      
     }
     
     
     //Cuentas Terceros
     
     if(accothers>=1){         
            if (this.getAccothersname_1()==null || this.getAccothersname_1().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - Nombre o Razón Social de la Cuenta");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccothersnum_1()==null || this.getAccothersnum_1().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - No. cuenta 1");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccothersofficername_1()==null || this.getAccothersofficername_1().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - Nombre del titular que autoriza 1");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccotherscr_1()==null || this.getAccotherscr_1().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - CR 1");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccothersbranch_1()==null || this.getAccothersbranch_1().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - Nombre sucursal 1");
                      ordenErrorsList.add(errors);
            }
     
     }
     
     
     if(accothers>=2){
                        
            if (this.getAccothersnum_2()==null || this.getAccothersnum_2().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - No. cuenta 2");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccothersofficername_2()==null || this.getAccothersofficername_2().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - Nombre del titular que autoriza 2");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccotherscr_2()==null || this.getAccotherscr_2().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - CR 2");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccothersbranch_2()==null || this.getAccothersbranch_2().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - Nombre sucursal 2");
                      ordenErrorsList.add(errors);
            }
     
     }
     
     
     if(accothers>=3){
                        
            if (this.getAccothersnum_3()==null || this.getAccothersnum_3().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - No. cuenta 3");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccothersofficername_3()==null || this.getAccothersofficername_3().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - Nombre del titular que autoriza 3");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccotherscr_3()==null || this.getAccotherscr_3().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - CR 3");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccothersbranch_3()==null || this.getAccothersbranch_3().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - Nombre sucursal 3");
                      ordenErrorsList.add(errors);
            }
     
     }
     
     if(accothers>=4){
                        
            if (this.getAccothersnum_4()==null || this.getAccothersnum_4().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - No. cuenta 4");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccothersofficername_4()==null || this.getAccothersofficername_4().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - Nombre del titular que autoriza 4");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccotherscr_4()==null || this.getAccotherscr_4().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - CR 4");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccothersbranch_4()==null || this.getAccothersbranch_4().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - Nombre sucursal 4");
                      ordenErrorsList.add(errors);
            }
     
     }
     
     if(accothers>=5){
                        
            if (this.getAccothersnum_5()==null || this.getAccothersnum_5().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - No. cuenta 5");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccothersofficername_5()==null || this.getAccothersofficername_5().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - Nombre del titular que autoriza 5");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccotherscr_5()==null || this.getAccotherscr_5().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - CR 5");
                      ordenErrorsList.add(errors);
            }
            
            if (this.getAccothersbranch_5()==null || this.getAccothersbranch_5().trim().length()<=0){
                      errors= new ContractMessageErrors();
                      errors.setMessage("Cuentas de terceros  - Nombre sucursal 5");
                      ordenErrorsList.add(errors);
            }
     
     }
     
         
      this.setErrorsList(ordenErrorsList);    
              
       if (ordenErrorsList.isEmpty()){
    	   this.loadOrdenBEM();
    	   this.loadflag = false;
    	   setInfoTokens();
    	   setInfoAccount();
    	   
           return this.continueToGeneralInfo();
           }
       else{
          return "UNSUCCESS";}       
    }    
    
    private ContractMessageErrors validarRegimen(String regimen, String accownName, char regimenFieldId){
    	
    	ContractMessageErrors error=null;
    	
    	if(regimen.equalsIgnoreCase("MANCOMUNADO")){
    		
    		String[] names = accownName.split(",");
    		
    		if(names.length < 2){
    			error = new ContractMessageErrors();
    			error.setMessage("Cuentas Autorizadas Propias  - Nombre del titular , Apoderado o personas autorizadas " + regimenFieldId +" - El Regimen Mancomunado, requiere la captura de al menos dos Personas Autorizadas");
    		}
    	}
    	
    	return error;
    };
    

    @Override
    public PdfTemplateBinding getPdfTemplateBinding() {
        return pdfTemplateBinding;
    }

    @Override
    public String getProductPrefix() {
        return "BA";
    }
    
    @Override
    public void getProductIdDetail() {
        setProduct(productBean.findById(new Integer(1))); // Producto Bem = 1
    }

    @Override
    public void setResetForm() {
        loadflag=false;
        selectedbemplan = "";
        quanttokens = "";
        accountnumber = "";
        commchargetype = "";
        quantaccown = "0";
        quantaccothers = "0";
        serialtoken_1 = "";
        emailtoken_1 = "";
        serialtoken_2 = "";
        emailtoken_2 = "";
        serialtoken_3 = "";
        emailtoken_3 = "";
        serialtoken_4 = "";
        emailtoken_4 = "";
        serialtoken_5 = "";
        emailtoken_5 = "";
        serialtoken_6 = "";
        emailtoken_6 = "";        
        serialtoken_7 = "";
        emailtoken_7 = "";
        serialtoken_8 = "";
        emailtoken_8 = "";
        serialtoken_9 = "";
        emailtoken_9 = "";
        serialtoken_10 = "";
        emailtoken_10 = "";
        serialtoken_11 = "";
        emailtoken_11 = "";
        serialtoken_12 = "";
        emailtoken_12 = "";
        
        accownnum_1 = "";
        accownmerchantname_1 = "";
        accownname_1 = "";
        accownnum_2 = "";
        accownmerchantname_2 = "";
        accownname_2 = "";
        accownnum_3 = "";
        accownmerchantname_3 = "";
        accownname_3 = "";
        accownnum_4 = "";
        accownmerchantname_4 = "";
        accownname_4 = "";
        accownnum_5 = "";
        accownmerchantname_5 = "";
        accownname_5 = "";
        accownnum_6 = "";
        accownmerchantname_6 = "";
        accownname_6 = "";
        accownnum_7 = "";
        accownmerchantname_7 = "";
        accownname_7 = "";
        accownnum_8 = "";
        accownmerchantname_8 = "";
        accownname_8 = "";
        accownnum_9 = "";
        accownmerchantname_9 = "";
        accownname_9 = "";
        accownnum_10 = "";
        accownmerchantname_10 = "";
        accownname_10 = "";
        accownnum_11 = "";
        accownmerchantname_11 = "";
        accownname_11 = "";
        accownnum_12 = "";
        accownmerchantname_12 = "";
        accownname_12 = "";
        accownnum_13 = "";
        accownmerchantname_13 = "";
        accownname_13 = "";
        accownnum_14 = "";
        accownmerchantname_14 = "";
        accownname_14 = "";
        accownnum_15 = "";
        accownmerchantname_15 = "";
        accownname_15 = "";
        
        
        accothersnum_1 = "";
        accothersofficername_1 = "";
        accotherscr_1 = "";
        accothersbranch_1 = "";
        accothersnum_2 = "";
        accothersofficername_2 = "";
        accotherscr_2 = "";
        accothersbranch_2 = "";
        accothersnum_3 = "";
        accothersofficername_3 = "";
        accotherscr_3 = "";
        accothersbranch_3 = "";
        accothersnum_4 = "";
        accothersofficername_4 = "";
        accotherscr_4 = "";
        accothersbranch_4 = "";
        accothersnum_5 = "";
        accothersofficername_5 = "";
        accotherscr_5 = "";
        accothersbranch_5 = "";
        accothersnum_6 = "";
        accothersofficername_6 = "";
        accotherscr_6 = "";
        accothersbrach_6 = "";
        accothersnum_7 = "";
        accothersofficername_7 = "";
        accotherscr_7 = "";
        accothersbranch_7 = "";
        accothersnum_8 = "";
        accothersofficername_8 = "";
        accotherscr_8 = "";
        accothersbranch_8 = "";
        accothersnum_9 = "";
        accothersofficername_9 = "";
        accotherscr_9 = "";
        accothersbranch_9 = "";
        accothersnum_10 = "";
        accothersofficername_10 = "";
        accotherscr_10 = "";
        accothersbranch_10 = "";

        
        accothersname_1= "";
        accothersname_2= "";
        accothersname_3= "";
        accothersname_4= "";
        accothersname_5= "";
        
        
        referencetoken_1="";
        referencetoken_2="";
        referencetoken_3="";
        referencetoken_4="";
        referencetoken_5="";
        referencetoken_6="";
        referencetoken_7="";
        referencetoken_8="";
        referencetoken_9="";
        referencetoken_10="";
        referencetoken_11="";
        referencetoken_12="";
        
        super.clearFields();
        setProduct(productBean.findById(new Integer(1))); // Producto BEM = 1
        setStatusContract(statusBean.findById(new Integer(1))); // Status Nuevo = 1
    }

    public String getResetForm() {
        setResetForm();
        return "";
    }
    
    
	private void setInfoTokens(){
		if(getQuanttokens().equals( ApplicationConstants.NUMBER_1)){
			setEmptyInfoToken2();
			setEmptyInfoToken3();
			setEmptyInfoToken4();
			setEmptyInfoToken5();
			setEmptyInfoToken6();
			setEmptyInfoToken7();
			setEmptyInfoToken8();
			setEmptyInfoToken9();
			setEmptyInfoToken10();
			setEmptyInfoToken11();
			setEmptyInfoToken12();
		}else if(getQuanttokens().equals( ApplicationConstants.NUMBER_2)){
			setEmptyInfoToken3();
			setEmptyInfoToken4();
			setEmptyInfoToken5();
			setEmptyInfoToken6();
			setEmptyInfoToken7();
			setEmptyInfoToken8();
			setEmptyInfoToken9();
			setEmptyInfoToken10();
			setEmptyInfoToken11();
			setEmptyInfoToken12();
		}else if(getQuanttokens().equals( ApplicationConstants.NUMBER_3)){
			setEmptyInfoToken4();
			setEmptyInfoToken5();
			setEmptyInfoToken6();
			setEmptyInfoToken7();
			setEmptyInfoToken8();
			setEmptyInfoToken9();
			setEmptyInfoToken10();
			setEmptyInfoToken11();
			setEmptyInfoToken12();
		}else if(getQuanttokens().equals( ApplicationConstants.NUMBER_4)){
			setEmptyInfoToken5();
			setEmptyInfoToken6();
			setEmptyInfoToken7();
			setEmptyInfoToken8();
			setEmptyInfoToken9();
			setEmptyInfoToken10();
			setEmptyInfoToken11();
			setEmptyInfoToken12();
		}else if(getQuanttokens().equals( ApplicationConstants.NUMBER_5)){
			setEmptyInfoToken6();
			setEmptyInfoToken7();
			setEmptyInfoToken8();
			setEmptyInfoToken9();
			setEmptyInfoToken10();
			setEmptyInfoToken11();
			setEmptyInfoToken12();
		}else if(getQuanttokens().equals( ApplicationConstants.NUMBER_6)){
			setEmptyInfoToken7();
			setEmptyInfoToken8();
			setEmptyInfoToken9();
			setEmptyInfoToken10();
			setEmptyInfoToken11();
			setEmptyInfoToken12();
		}else if(getQuanttokens().equals( ApplicationConstants.NUMBER_7)){
			setEmptyInfoToken8();
			setEmptyInfoToken9();
			setEmptyInfoToken10();
			setEmptyInfoToken11();
			setEmptyInfoToken12();
		}else if(getQuanttokens().equals( ApplicationConstants.NUMBER_8)){
			setEmptyInfoToken9();
			setEmptyInfoToken10();
			setEmptyInfoToken11();
			setEmptyInfoToken12();
		}else if(getQuanttokens().equals( ApplicationConstants.NUMBER_9)){
			setEmptyInfoToken10();
			setEmptyInfoToken11();
			setEmptyInfoToken12();
		}else if(getQuanttokens().equals( ApplicationConstants.NUMBER_10)){
			setEmptyInfoToken11();
			setEmptyInfoToken12();
		}else if(getQuanttokens().equals( ApplicationConstants.NUMBER_11)){
			setEmptyInfoToken12();
		}else if(getQuanttokens().equals( ApplicationConstants.NUMBER_12)){
		}
		
	}
    
    
    private void setEmptyInfoToken2(){
		this.setSerialtoken_2(ApplicationConstants.EMPTY_STRING);
		this.setEmailtoken_2(ApplicationConstants.EMPTY_STRING);
		this.setReferencetoken_2(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfoToken3(){
		this.setSerialtoken_3(ApplicationConstants.EMPTY_STRING);
		this.setEmailtoken_3(ApplicationConstants.EMPTY_STRING);
		this.setReferencetoken_3(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfoToken4(){
		this.setSerialtoken_4(ApplicationConstants.EMPTY_STRING);
		this.setEmailtoken_4(ApplicationConstants.EMPTY_STRING);
		this.setReferencetoken_4(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfoToken5(){
		this.setSerialtoken_5(ApplicationConstants.EMPTY_STRING);
		this.setEmailtoken_5(ApplicationConstants.EMPTY_STRING);
		this.setReferencetoken_5(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfoToken6(){
		this.setSerialtoken_6(ApplicationConstants.EMPTY_STRING);
		this.setEmailtoken_6(ApplicationConstants.EMPTY_STRING);
		this.setReferencetoken_6(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfoToken7(){
		this.setSerialtoken_7(ApplicationConstants.EMPTY_STRING);
		this.setEmailtoken_7(ApplicationConstants.EMPTY_STRING);
		this.setReferencetoken_7(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfoToken8(){
		this.setSerialtoken_8(ApplicationConstants.EMPTY_STRING);
		this.setEmailtoken_8(ApplicationConstants.EMPTY_STRING);
		this.setReferencetoken_8(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfoToken9(){
		this.setSerialtoken_9(ApplicationConstants.EMPTY_STRING);
		this.setEmailtoken_9(ApplicationConstants.EMPTY_STRING);
		this.setReferencetoken_9(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfoToken10(){
		this.setSerialtoken_10(ApplicationConstants.EMPTY_STRING);
		this.setEmailtoken_10(ApplicationConstants.EMPTY_STRING);
		this.setReferencetoken_10(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfoToken11(){
		this.setSerialtoken_11(ApplicationConstants.EMPTY_STRING);
		this.setEmailtoken_11(ApplicationConstants.EMPTY_STRING);
		this.setReferencetoken_11(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfoToken12(){
		this.setSerialtoken_12(ApplicationConstants.EMPTY_STRING);
		this.setEmailtoken_12(ApplicationConstants.EMPTY_STRING);
		this.setReferencetoken_12(ApplicationConstants.EMPTY_STRING);
	}
    
    
    private void setInfoAccount(){
		if(getQuantaccown().equals( ApplicationConstants.NUMBER_1)){
			setEmptyInfo2();
			setEmptyInfo3();
			setEmptyInfo4();
			setEmptyInfo5();
			setEmptyInfo6();
			setEmptyInfo7();
			setEmptyInfo8();
			setEmptyInfo9();
			setEmptyInfo10();
			setEmptyInfo11();
			setEmptyInfo12();
			setEmptyInfo13();
			setEmptyInfo14();
			setEmptyInfo15();
		}else if(getQuantaccown().equals( ApplicationConstants.NUMBER_2)){
			setEmptyInfo3();
			setEmptyInfo4();
			setEmptyInfo5();
			setEmptyInfo6();
			setEmptyInfo7();
			setEmptyInfo8();
			setEmptyInfo9();
			setEmptyInfo10();
			setEmptyInfo11();
			setEmptyInfo12();
			setEmptyInfo13();
			setEmptyInfo14();
			setEmptyInfo15();
			
		}else if(getQuantaccown().equals( ApplicationConstants.NUMBER_3)){
			setEmptyInfo4();
			setEmptyInfo5();
			setEmptyInfo6();
			setEmptyInfo7();
			setEmptyInfo8();
			setEmptyInfo9();
			setEmptyInfo10();
			setEmptyInfo11();
			setEmptyInfo12();
			setEmptyInfo13();
			setEmptyInfo14();
			setEmptyInfo15();
			
		}else if(getQuantaccown().equals( ApplicationConstants.NUMBER_4)){
			setEmptyInfo5();
			setEmptyInfo6();
			setEmptyInfo7();
			setEmptyInfo8();
			setEmptyInfo9();
			setEmptyInfo10();
			setEmptyInfo11();
			setEmptyInfo12();
			setEmptyInfo13();
			setEmptyInfo14();
			setEmptyInfo15();
		
		}else if(getQuantaccown().equals( ApplicationConstants.NUMBER_5)){
			setEmptyInfo6();
			setEmptyInfo7();
			setEmptyInfo8();
			setEmptyInfo9();
			setEmptyInfo10();
			setEmptyInfo11();
			setEmptyInfo12();
			setEmptyInfo13();
			setEmptyInfo14();
			setEmptyInfo15();
			
		}else if(getQuantaccown().equals( ApplicationConstants.NUMBER_6)){
			setEmptyInfo7();
			setEmptyInfo8();
			setEmptyInfo9();
			setEmptyInfo10();
			setEmptyInfo11();
			setEmptyInfo12();
			setEmptyInfo13();
			setEmptyInfo14();
			setEmptyInfo15();
			
		}else if(getQuantaccown().equals( ApplicationConstants.NUMBER_7)){
			setEmptyInfo8();
			setEmptyInfo9();
			setEmptyInfo10();
			setEmptyInfo11();
			setEmptyInfo12();
			setEmptyInfo13();
			setEmptyInfo14();
			setEmptyInfo15();
			
		}else if(getQuantaccown().equals( ApplicationConstants.NUMBER_8)){
			setEmptyInfo9();
			setEmptyInfo10();
			setEmptyInfo11();
			setEmptyInfo12();
			setEmptyInfo13();
			setEmptyInfo14();
			setEmptyInfo15();
			
		}else if(getQuantaccown().equals( ApplicationConstants.NUMBER_9)){
			setEmptyInfo10();
			setEmptyInfo11();
			setEmptyInfo12();
			setEmptyInfo13();
			setEmptyInfo14();
			setEmptyInfo15();
			
		}else if(getQuantaccown().equals( ApplicationConstants.NUMBER_10)){
			setEmptyInfo11();
			setEmptyInfo12();
			setEmptyInfo13();
			setEmptyInfo14();
			setEmptyInfo15();
			
		}else if(getQuantaccown().equals( ApplicationConstants.NUMBER_11)){
			setEmptyInfo12();
			setEmptyInfo13();
			setEmptyInfo14();
			setEmptyInfo15();
			
		}else if(getQuantaccown().equals( ApplicationConstants.NUMBER_12)){
			setEmptyInfo13();
			setEmptyInfo14();
			setEmptyInfo15();
		}else if(getQuantaccown().equals( ApplicationConstants.NUMBER_13)){
			setEmptyInfo14();
			setEmptyInfo15();
		}else if(getQuantaccown().equals( ApplicationConstants.NUMBER_14)){
			setEmptyInfo15();
		}else if(getQuantaccown().equals( ApplicationConstants.NUMBER_15)){
		}
		
	}
    
    
    
    private void setEmptyInfo2(){
		this.setAccownmerchantname_2(ApplicationConstants.EMPTY_STRING);
		this.setAccownname_2(ApplicationConstants.EMPTY_STRING);
		this.setRegimen_2(ApplicationConstants.EMPTY_STRING);
		this.setAccownnum_2(ApplicationConstants.EMPTY_STRING);
		
	}
    
    private void setEmptyInfo3(){
		this.setAccownmerchantname_3(ApplicationConstants.EMPTY_STRING);
		this.setAccownname_3(ApplicationConstants.EMPTY_STRING);
		this.setRegimen_3(ApplicationConstants.EMPTY_STRING);
		this.setAccownnum_3(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfo4(){
		this.setAccownmerchantname_4(ApplicationConstants.EMPTY_STRING);
		this.setAccownname_4(ApplicationConstants.EMPTY_STRING);
		this.setRegimen_4(ApplicationConstants.EMPTY_STRING);
		this.setAccownnum_4(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfo5(){
		this.setAccownmerchantname_5(ApplicationConstants.EMPTY_STRING);
		this.setAccownname_5(ApplicationConstants.EMPTY_STRING);
		this.setRegimen_5(ApplicationConstants.EMPTY_STRING);
		this.setAccownnum_5(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfo6(){
		this.setAccownmerchantname_6(ApplicationConstants.EMPTY_STRING);
		this.setAccownname_6(ApplicationConstants.EMPTY_STRING);
		this.setRegimen_6(ApplicationConstants.EMPTY_STRING);
		this.setAccownnum_6(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfo7(){
		this.setAccownmerchantname_7(ApplicationConstants.EMPTY_STRING);
		this.setAccownname_7(ApplicationConstants.EMPTY_STRING);
		this.setRegimen_7(ApplicationConstants.EMPTY_STRING);
		this.setAccownnum_7(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfo8(){
		this.setAccownmerchantname_8(ApplicationConstants.EMPTY_STRING);
		this.setAccownname_8(ApplicationConstants.EMPTY_STRING);
		this.setRegimen_8(ApplicationConstants.EMPTY_STRING);
		this.setAccownnum_8(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfo9(){
		this.setAccownmerchantname_9(ApplicationConstants.EMPTY_STRING);
		this.setAccownname_9(ApplicationConstants.EMPTY_STRING);
		this.setRegimen_9(ApplicationConstants.EMPTY_STRING);
		this.setAccownnum_9(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfo10(){
		this.setAccownmerchantname_10(ApplicationConstants.EMPTY_STRING);
		this.setAccownname_10(ApplicationConstants.EMPTY_STRING);
		this.setRegimen_10(ApplicationConstants.EMPTY_STRING);
		this.setAccownnum_10(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfo11(){
		this.setAccownmerchantname_11(ApplicationConstants.EMPTY_STRING);
		this.setAccownname_11(ApplicationConstants.EMPTY_STRING);
		this.setRegimen_11(ApplicationConstants.EMPTY_STRING);
		this.setAccownnum_11(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfo12(){
		this.setAccownmerchantname_12(ApplicationConstants.EMPTY_STRING);
		this.setAccownname_12(ApplicationConstants.EMPTY_STRING);
		this.setRegimen_12(ApplicationConstants.EMPTY_STRING);
		this.setAccownnum_12(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfo13(){
		this.setAccownmerchantname_13(ApplicationConstants.EMPTY_STRING);
		this.setAccownname_13(ApplicationConstants.EMPTY_STRING);
		this.setRegimen_13(ApplicationConstants.EMPTY_STRING);
		this.setAccownnum_13(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfo14(){
		this.setAccownmerchantname_14(ApplicationConstants.EMPTY_STRING);
		this.setAccownname_14(ApplicationConstants.EMPTY_STRING);
		this.setRegimen_14(ApplicationConstants.EMPTY_STRING);
		this.setAccownnum_14(ApplicationConstants.EMPTY_STRING);
	}
    
    private void setEmptyInfo15(){
		this.setAccownmerchantname_15(ApplicationConstants.EMPTY_STRING);
		this.setAccownname_15(ApplicationConstants.EMPTY_STRING);
		this.setRegimen_15(ApplicationConstants.EMPTY_STRING);
		this.setAccownnum_15(ApplicationConstants.EMPTY_STRING);
	}
    
    
}
