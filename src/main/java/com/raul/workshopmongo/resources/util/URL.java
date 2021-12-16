package com.raul.workshopmongo.resources.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

//Classe para trabalhar com url da requisição
public class URL {
	
	//Decodifica o param da URL. Se o param for "bom%20dia", o método decodifica para "bom dia"
	public static String decodeParam(String text) {
		try {
			return URLDecoder.decode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	public static Date convertDate(String textDate, Date defaultValue) {
		//yyyy-MM-dd é o formato mais comum encontrado nos frameworks de JS
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		try {
			return sdf.parse(textDate);
		} catch (ParseException e) {
			return defaultValue;			//defaultValue enviado por param
		}		
	}
}
