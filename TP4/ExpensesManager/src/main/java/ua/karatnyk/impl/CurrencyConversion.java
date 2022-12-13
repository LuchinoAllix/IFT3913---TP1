package ua.karatnyk.impl;

import java.util.Map;
import java.util.TreeMap;

import ua.karatnyk.ICurrencyConversion;

//class is used for convert currency from json format to java object

public class CurrencyConversion implements ICurrencyConversion{
	
	private String base;
	private String date;

	private Map<String, Double> rates = new TreeMap<String, Double>();

	
	public Map<String, Double> getRates() {
		return rates;
	}

	
	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}

	
	public String getBase() {
		return base;
	}

	
	public void setBase(String base) {
		this.base = base;
	}

	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	

}
