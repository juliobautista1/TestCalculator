
package com.banorte.contract.layout;

import com.banorte.contract.model.Contract;
import com.banorte.contract.util.EncryptBd;
import com.banorte.contract.util.Formatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MRIOS
 */
public class BemActivateLayout extends LayoutOperationsTemplate implements Layout {
    private final Integer PRODUCTTYPEID = 1;
    private final String FILLS[] = {"bemnumber", "client_fiscalname"};

    public BemActivateLayout(List<Contract> search) {
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

        list.add(contract.getReference());      //reference
        list.add(map.get("bemnumber"));         //numero de empresa
        list.add(map.get("client_fiscalname")); //nombre de la empresa
        
        return list;
    }
        
    public List<String> getHeader(){
        List<String> list = new ArrayList();
        list.add("Folio Solicitud");      //reference
        list.add("Numero de Empresa");    //bemnumber
        list.add("Nombre de la Empresa"); //client_fiscalname
        
        return list;
    }
}
