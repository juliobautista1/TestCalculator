package com.banorte.contract.web;

import com.banorte.contract.util.pdf.MockPdfTemplateBinding;
import com.banorte.contract.util.pdf.PdfBuilder;
import com.banorte.contract.util.pdf.PdfTemplateReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MRIOS
 */
public class PdfBuilderMB {

    private static final Logger log = Logger.getLogger(PdfBuilderMB.class.getName());

    public PdfBuilderMB() {
    }

    public void createPdfResponse() {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        try {

            String fileName = "result.pdf";
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            PdfBuilder builder = new PdfBuilder(byteArray);
//            PdfTemplateReader reader = new PdfTemplateReader("c:/temp/pruebaContratoAfiliacionAdquirienteMN.pdf");
            PdfTemplateReader reader = new PdfTemplateReader("c:/temp/Caratula.pdf");
            builder.setReader(reader);
            builder.setBinding(new MockPdfTemplateBinding());
            builder.createDocument();
            byte[] pdf = byteArray.toByteArray();
            HttpServletResponse response = (HttpServletResponse) fCtx.getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(pdf.length);
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
            ServletOutputStream out = response.getOutputStream();
            out.write(pdf);
        } catch (IOException ioEx) {
            log.severe(ioEx.getMessage());
        } finally {
            fCtx.responseComplete();
        }
    }
}
