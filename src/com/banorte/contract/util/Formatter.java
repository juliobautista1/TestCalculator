/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.banorte.contract.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DecimalFormat;

/**
 * 
 * @author MRIOST
 */
public class Formatter {

	private Calendar calendarDate = Calendar.getInstance();
	private SimpleDateFormat formatFormDate = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat formatLongDate = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");
	private SimpleDateFormat formatFormDateLayout = new SimpleDateFormat(
			"ddMMyyyy.HHmmss");
	private SimpleDateFormat formatFormDateTextLayout = new SimpleDateFormat(
			"yyMMdd");
	private SimpleDateFormat formatFormDateOIP = new SimpleDateFormat(
			"yyyy-MM-dd");

	private SimpleDateFormat formatFormDateMaintance = new SimpleDateFormat(
			"yyyyMMdd");

	public String getLargeDate(String date) {
		String result = this.getDayDate(date) + " de " + getMonthDesc(date)
				+ " de " + this.getYearDate(date);
		return result;
	}

	@SuppressWarnings("static-access")
	public String getDayDate(String date) {
		return String.valueOf(formatDate(date).get(Calendar.DAY_OF_MONTH));
	}

	public String getMonthDate(String date) {
		return String.valueOf(formatDate(date).get(Calendar.MONTH) + 1);
	}

	public String getMonthDesc(String date) {
		return translateMonth(this.getMonthDate(date));
	}

	@SuppressWarnings("static-access")
	public String getYearDate(String date) {
		return String.valueOf(formatDate(date).get(Calendar.YEAR));
	}

	public Calendar formatDate(String date) {
		try {
			java.util.Date formDate = formatFormDate.parse(date);
			calendarDate.setTime(formDate);
		} catch (Exception e) {
		}
		return calendarDate;
	}

	public Calendar formatDate(java.util.Date date) {
		try {
			String onlyDate = formatFormDate.format(date);
			java.util.Date formDate = formatFormDate.parse(onlyDate);
			calendarDate.setTime(formDate);
		} catch (Exception e) {
		}
		return calendarDate;
	}
	
	public Calendar formatLongDate(String date) {
		try {
			java.util.Date formDate = formatLongDate.parse(date);
			calendarDate.setTime(formDate);
		} catch (Exception e) {
		}
		return calendarDate;
	}

	public String formatDateToString(java.util.Date date) {
		return formatFormDate.format(date);

	}

	public String formatLongDateToString(java.util.Date date) {
		return formatLongDate.format(date);

	}

	public String formatDateToStringOIP(java.util.Date date) {
		return formatFormDateOIP.format(date);

	}
	
	public String formatDateToStringMaintance(java.util.Date date) {
		return formatFormDateMaintance.format(date);

	}

	public String formatDateToStringLayout(java.util.Date date) {
		return formatFormDateLayout.format(date);

	}

	public String formatDateToStringTextLayout(java.util.Date date) {
		return formatFormDateTextLayout.format(date);

	}

	private String translateMonth(String month) {
		String result = null;
		if (month.equals(String.valueOf(calendarDate.JANUARY + 1))) {
			result = "Enero";
		} else if (month.equals(String.valueOf(calendarDate.FEBRUARY + 1))) {
			result = "Febrero";
		} else if (month.equals(String.valueOf(calendarDate.MARCH + 1))) {
			result = "Marzo";
		} else if (month.equals(String.valueOf(calendarDate.APRIL + 1))) {
			result = "Abril";
		} else if (month.equals(String.valueOf(calendarDate.MAY + 1))) {
			result = "Mayo";
		} else if (month.equals(String.valueOf(calendarDate.JUNE + 1))) {
			result = "Junio";
		} else if (month.equals(String.valueOf(calendarDate.JULY + 1))) {
			result = "Julio";
		} else if (month.equals(String.valueOf(calendarDate.AUGUST + 1))) {
			result = "Agosto";
		} else if (month.equals(String.valueOf(calendarDate.SEPTEMBER + 1))) {
			result = "Septiembre";
		} else if (month.equals(String.valueOf(calendarDate.OCTOBER + 1))) {
			result = "Octubre";
		} else if (month.equals(String.valueOf(calendarDate.NOVEMBER + 1))) {
			result = "Noviembre";
		} else if (month.equals(String.valueOf(calendarDate.DECEMBER + 1))) {
			result = "Diciembre";
		}
		return result;
	}

	public static String fixLenght(String content, int lenght) {
		String fixString = "0000000000" + content;
		return fixString.substring(fixString.length() - lenght, fixString
				.length());
	}

	public static String fixToString(Object value) {
		String result = null;
		try {
			result = value.toString();
		} catch (NullPointerException e) {
			result = "";
		}

		return result;
	}

	public static String fixtoLenght(String value, int lenght) {
		String result;
		try {
			if (value.length() > lenght)
				result = value.substring(0, lenght);
			else
				result = value;
		} catch (NullPointerException e) {
			result = "";
		}

		return result;
	}

	public static String subString(String value, int from, int to) {
		String result;
		try {
			result = value.substring(from, to);
		} catch (NullPointerException e) {
			result = "";
		}

		return result;
	}

	public static String subString(String value, int from) {
		String result;
		try {
			result = value.substring(from);
		} catch (NullPointerException e) {
			result = "";
		}

		return result;
	}

	public static String formatValue(Double value, int type) {
		// type 1 = pesos, 2 = porcentaje
		DecimalFormat format1 = new DecimalFormat("$#,##0.00");
		DecimalFormat format2 = new DecimalFormat("#0.00");
		String result;

		if (type == 1 && value != null) {
			result = format1.format(value).toString();
		} else if (type == 2 && value != null) {
			result = format2.format(value).toString() + " %";
		} else {
			result = "";
		}
		return result.toString();

	}
	
	/**
	 * te devuelve el valor con 3 digitos decimales.
	 * @param value
	 * @return
	 */
	public static String formatValue3Digits(Double value) {
		
		DecimalFormat format1 = new DecimalFormat("$#,##0.000");		
		String result;

		result = format1.format(value).toString();
		
		return result.toString();

	}
	
	public static boolean isNumeric(String value){
		
		//Regular Expression
		String regex ="((-|\\+)?[0-9]+(\\.[0-9]+)?)+";
		
		Pattern p = Pattern.compile(regex);
		
		//Evaluate string
		Matcher m = p.matcher(value);
		
		return m.matches();	
	}
	
	public static String padLeft(String s, Character pad, int width) {
		int j = s.length();
		for (int k = 0; k < width - j; k++)
			s = pad.toString() + s;

		return s;
	}
	
	public static String maskAccount(String accountNumber) {

		String maskedAccount="";
		try{
			
			if(accountNumber!=null && !accountNumber.equals("")){
				String digits ="";
				String accountFixed ="";
				//Rellenar con ceros a la izq.
				accountFixed =  fixLenght(accountNumber, 10);
				
				int beginIndex = accountFixed.length()-4;
				
				//Extraer los ultimos 4 digitos del numero de cuenta
				digits = accountFixed.substring(beginIndex, accountFixed.length());
				
				maskedAccount = padLeft(digits, '*' , 10);
				
			}
		}
		catch(Exception e){
			System.out.println("Error al enmascarar el No. Cuenta:" + accountNumber);
			maskedAccount="";
		}
		
		return maskedAccount;
	}
	
}
