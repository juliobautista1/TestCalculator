/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.banorte.contract.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * @author JSANCHEZ
 */
public class CurrencyConverter {

	public static Double toDollar(Double pesos) {
		
		double value = 0;
		
		if(pesos.doubleValue() > 0){
			value = pesos.doubleValue()/ApplicationConstants.CURRENCY_EXCHANGE.doubleValue();
		}
		
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);
		value = Double.valueOf(df.format(value));

		
        return value;
	}

}
