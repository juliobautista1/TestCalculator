package com.banorte.contract.util.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MRIOS
 */
public class PdfTemplateReader extends PdfReader {

    private String sourcePathName;
    private String sourceFileName;
    private Set fields;
    private AcroFields form;
    private PdfStamper stamper;
    private ByteArrayOutputStream baos;
    
    public PdfTemplateReader() {
        super();
    }

    public PdfTemplateReader(String name) throws IOException {
        super(name);
    }

    public PdfTemplateReader(byte[] data) throws IOException {
        super(data);
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getSourcePathName() {
        return sourcePathName;
    }

    public void setSourcePathName(String sourcePathName) {
        this.sourcePathName = sourcePathName;
    }

    public Set getFileds() {
        if (fields == null) {
            try {
                baos = new ByteArrayOutputStream();
                stamper = new PdfStamper(this, baos);
                form = stamper.getAcroFields();
                form.setExtraMargin(0, 1);
                fields = form.getFields().keySet();
            } catch (DocumentException ex) {
                Logger.getLogger(PdfTemplateReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PdfTemplateReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fields;
    }
    
    public ByteArrayOutputStream getOutputStream(){
        return baos;
    }

    public void setField(String key,String value){
        try {
            if(form!=null){
                form.setField(key, value);
            }
        } catch (IOException ex) {
            Logger.getLogger(PdfTemplateReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfTemplateReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void release(){
        if(stamper!=null){
            try {
                stamper.setFormFlattening(true);
                stamper.close();
            } catch (DocumentException ex) {
                Logger.getLogger(PdfTemplateReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PdfTemplateReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
//    public static void main(String[] args) {
////        String[] keys = {NAME, TWO_FIRST_FIRST_NAMES, BIRTH_DATE, SEX, BIRTH_LOCATION, NATIONALITY, ADDRESS, MUNICIPALITY, ZIP};
//        String[][] values = {
//            {"Specimen", "Laura Aurora", "03-03-1970", "Female", "Foobar", "Foobarese", "Movie Drive 1", "Foobar", "70084"}/*,
//        {"Wayne", "Bruce", "05-05-1950", "Male", "Gotham City", "American", "Wayne Street 2", "Gotham City", "1234"},
//        {"Bond", "James", "07-07-1960", "Male", "Manchester", "British", "Bond Street 007", "London", "1000"}*/
//
//        };
//
//        PdfReader reader;
//        ByteArrayOutputStream baos;
//        PdfStamper stamper;
//        AcroFields form;
//        Document document = new Document();
//        try {
//            PdfCopy copy = new PdfCopy(document, new FileOutputStream("c:/temp/result.pdf"));
//            document.open();
//            for (int i = 0; i < values.length; i++) {
//                // set fields
//                reader = new PdfReader("c:/temp/pruebaContratoAfiliacionAdquirienteMN.pdf");
//                baos = new ByteArrayOutputStream();
//                stamper = new PdfStamper(reader, baos);
//                form = stamper.getAcroFields();
//                HashMap fieldsMap = form.getFields();
//                form.setExtraMargin(0, 1);
//                System.err.println("TotalKeys:" + fieldsMap.size());
//                AcroFieldsItemFormatter keyFormatter = new AcroFieldsItemFormatter();
//                MockPdfTemplateBinding binding = new MockPdfTemplateBinding();
//                for (Object key : fieldsMap.keySet()) {
//                    System.err.println(key + " = " + keyFormatter.format(key));
//                    form.setField((String) key, binding.getBind(keyFormatter.format(key)));
//                }
////                for (int j = 0; j < keys.length; j++) {
////                    form.setField(keys[j], values[i][j]);
////                }
//                stamper.setFormFlattening(true);
//                stamper.close();
//                // add page
//                reader = new PdfReader(baos.toByteArray());
//                copy.addPage(copy.getImportedPage(reader, 1));
//            }
//            document.close();
//        } catch (Exception de) {
//            System.err.println(de.getMessage());
//        }
//    }
}
