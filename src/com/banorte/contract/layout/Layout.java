

package com.banorte.contract.layout;

import com.banorte.contract.model.Contract;
import java.util.List;

/**
 *
 * @author Darvy Arch
 */
public interface Layout {

    public boolean hasElements();    
    public List<String> getContent(Contract contract) throws Exception;
    public List<String> getHeader();
}
