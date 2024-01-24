/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banorte.contract.util;
import org.apache.commons.ssl.OpenSSL;


/**
 *
 * @author cgomez
 */
public class EncryptBd {    
        public byte[] encrypt(String algorithm, byte[] contentBytes, String key) throws Exception {
		return encrypt(algorithm, contentBytes, key, true);
	}

	public byte[] encrypt(String algorithm, byte[] contentBytes, String key, boolean base64) throws Exception {
		byte[] encrypted = OpenSSL.encrypt(algorithm, key.toCharArray(), contentBytes, base64, true);
		return encrypted;
	}

	public byte[] encrypt(String algorithm, byte[] contentBytes, String key, boolean base64, boolean salt) throws Exception {
		byte[] encrypted = OpenSSL.encrypt(algorithm, key.toCharArray(), contentBytes, base64, salt);
		return encrypted;
	}

	public byte[] decrypt(String algorithm, byte[] contentBytes, String key) throws Exception {
		byte[] decrypted = OpenSSL.decrypt(algorithm, key.toCharArray(), contentBytes);
		return decrypted;
	}
        
        public String encrypt(String str){
                try{
                    byte[] result = encrypt("aes-128-cbc", str.getBytes(), ApplicationConstants.TRUE, true);
                    return new String(result);
                }catch(Exception e){
                    e.printStackTrace();
                    return "Error";
                    
                }
        }        
        
        public String decrypt(String str){
                try{
                    byte[] result = decrypt("aes-128-cbc", str.getBytes(), ApplicationConstants.TRUE);
                    return new String(result);
                }catch(Exception e){
                    e.printStackTrace();
                    return "Error";
                }
        }
        
}
