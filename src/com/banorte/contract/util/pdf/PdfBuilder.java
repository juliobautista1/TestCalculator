package com.banorte.contract.util.pdf;

import com.banorte.contract.util.ApplicationConstants;
import com.banorte.contract.util.AttrConstants;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MRIOS
 */
public class PdfBuilder {
    private PdfTemplateBinding binding;
    private PdfTemplateReader reader;
    private OutputStream outputStream;
    
    public PdfBuilder(){
        
    }

    public PdfBuilder(OutputStream output){
        setOutputStream(output);
    }
    
    public PdfTemplateBinding getBinding() {
        return binding;
    }

    public void setBinding(PdfTemplateBinding binding) {
        this.binding = binding;
    }

    public PdfTemplateReader getReader() {
        return reader;
    }

    public void setReader(PdfTemplateReader reader) {
        this.reader = reader;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    
    private String getDateHourPrint(){
    	Date day = new Date();
		SimpleDateFormat formatter= new SimpleDateFormat ("dd-MMM-yy hh:mm");
    
		return formatter.format( day );
    }
    
    public Document createDocument(){
        Document document = new Document();
        OutputStream os = null;
        PdfCopy copy = null;
        try {
        	os = getOutputStream();
            copy = new PdfCopy(document, os);
            copy.setPdfVersion(PdfWriter.PDF_VERSION_1_4);
            //PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            Set keys = reader.getFileds();
            AcroFieldsItemFormatter formatter = new AcroFieldsItemFormatter();
            for(Object o:keys){
                String key = o.toString();
 //               System.out.println(key);
                String keyFormatted = formatter.format(key);
 //               System.out.println(keyFormatted);
                reader.setField(key, binding.getBind(keyFormatted));                
            }
            
            
            reader.setField(ApplicationConstants.DATE_HOUR_PRINT_PDF, getDateHourPrint());
            reader.release();
            //Logger.getLogger(PdfBuilder.class.getName()).log(Level.INFO, "tamaño en paginas: " + reader.getNumberOfPages());
            reader = new PdfTemplateReader(reader.getOutputStream().toByteArray());

            for ( int i = 1; i <= reader.getNumberOfPages(); i++ ) {
                copy.addPage(copy.getImportedPage(reader, i));
            }
            //copy.addPage(copy.getImportedPage(reader, 2));
            document.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
        	if (os != null) {
        		safeClose(os);
        	}
        	if (copy != null) {
        		safeClose(copy);
        	}
        }
        return document;
    }
    
    public static void main(String[] args){
        try {
            PdfTemplateReader reader = new PdfTemplateReader("c:/temp/pruebaContratoAfiliacionAdquirienteMN.pdf");
            
            PdfBuilder builder = new PdfBuilder(new FileOutputStream("c:/temp/result2.pdf"));

            builder.setReader(reader);
            builder.setBinding(new MockPdfTemplateBinding());
            builder.createDocument();
            
        } catch (IOException ex) {
            Logger.getLogger(PdfBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public static void safeClose(OutputStream os) {
    	if (os != null) {
    		try {
    			os.close();
    		} catch (Exception e) {
    			System.out.println(e);
    		}
    	}
    }
    
    public static void safeClose(PdfCopy copy) {
    	if (copy != null) {
    		try {
    			copy.close();
    		} catch (Exception e) {
    			System.out.println(e);
    		}
    	}
    }
}
