package com.banorte.contract.util.pdf;

/**
 *
 * @author MRIOS
 */
public class MockPdfTemplateBinding implements PdfTemplateBinding{

    public String getBind(String key) {
        System.out.println("Binding "+key+" = Data["+key+"]");
        
        return "Data["+key+"]";
    }

}
