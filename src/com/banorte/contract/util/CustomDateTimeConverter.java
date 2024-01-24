/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banorte.contract.util;
import java.util.TimeZone;
import javax.faces.convert.DateTimeConverter;


/**
 *
 * @author cgomez
 */
public class CustomDateTimeConverter extends DateTimeConverter  {

    public CustomDateTimeConverter() {
        super();
        
        TimeZone.setDefault(TimeZone.getTimeZone("America/Mexico_City"));
        setTimeZone(TimeZone.getDefault()); 
        setPattern("dd/MM/yyyy HH:mm:ss");
}
}
