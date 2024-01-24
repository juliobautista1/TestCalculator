/*
 * @autor Nestor Archuleta
 */

package com.banorte.contract.util;

import java.io.ByteArrayInputStream;

public class HexUtil {
	public static String toHexadecimal(byte[] datos) {
        String resultado="";
        ByteArrayInputStream input = new ByteArrayInputStream(datos);
        String cadAux;
        int leido = input.read();
        while(leido != -1)
        {
                cadAux = Integer.toHexString(leido);
                if(cadAux.length() < 2)
                        resultado += "0";
                resultado += cadAux;
                leido = input.read();
        }
        return resultado;
	}

	public static byte[] toByteArray(String hex) {
		byte[] bts = new byte[hex.length() / 2];
		for (int i = 0; i < bts.length; i++) {
		   bts[i] = (byte) Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
		}

		return bts;
	}
}
