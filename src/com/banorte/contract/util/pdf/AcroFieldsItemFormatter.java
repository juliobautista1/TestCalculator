/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banorte.contract.util.pdf;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.StringTokenizer;

/**
 *
 * @author MRIOS
 */
public class AcroFieldsItemFormatter extends Format{

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        StringBuffer buffer=new StringBuffer();
        
// ********************************************************        
// Format input expected  : F[0].P1[0].NombreComercial[0]
// Format output expected : NombreComercial
// ********************************************************        
        
        if(obj instanceof String){
            StringTokenizer tokenizer = new StringTokenizer(obj.toString(), ".");
            pos.setBeginIndex(0);
            if(tokenizer.countTokens()>2){
                tokenizer.nextToken();
                pos.setBeginIndex(1);
                tokenizer.nextToken();
                pos.setBeginIndex(2);
                String keyData = tokenizer.nextToken();
                if(keyData.length()>3){
                	StringTokenizer tokenizerAttribute = new StringTokenizer(keyData, "[");
                	String attribute = tokenizerAttribute.nextToken();
//                	System.out.println("ATRIBUTO : "+attribute);
                    buffer.append(attribute);
                }
            }
        }
        return buffer;
    }

    /* 
      public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        StringBuffer buffer=new StringBuffer();
        
// ********************************************************        
// Format input expected  : F[0].P1[0].NombreComercial[0]
// Format output expected : NombreComercial
// ********************************************************        
        
        if(obj instanceof String){
            StringTokenizer tokenizer = new StringTokenizer(obj.toString(), ".");
            pos.setBeginIndex(0);
            if(tokenizer.countTokens()>2){
                tokenizer.nextToken();
                pos.setBeginIndex(1);
                tokenizer.nextToken();
                pos.setBeginIndex(2);
                String keyData = tokenizer.nextToken();
                if(keyData.length()>3){
                    buffer.append(keyData.substring(0, keyData.length()-3));
                }
            }
        }
        return buffer;
    }

     * */
    
    
    @Override
    public Object parseObject(String source, ParsePosition pos) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void dummy(){
    	System.out.println("para crear peso en harvest");
    }

}
