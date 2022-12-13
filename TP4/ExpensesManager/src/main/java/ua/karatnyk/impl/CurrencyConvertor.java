package ua.karatnyk.impl;

import java.text.ParseException;

public final class CurrencyConvertor {
	
	public static double convert(double amount, String from, String to, CurrencyConversion conversion) throws ParseException{
		if(!conversion.getRates().containsKey(to)||!conversion.getRates().containsKey(from))
			throw new ParseException("Not correct format currency"
					+ "", 0);
		double curencyTo = conversion.getRates().get(to);
		double curencyFrom = conversion.getRates().get(from);
		return amount*(curencyTo/curencyFrom);
		
	}
}
