package ua.karatnyk;

import java.util.Map;

public interface ICurrencyConversion {
	
	Map<String, Double> getRates();
	void setRates(Map<String, Double> rates);
	String getBase();
	void setBase(String base);
	String getDate();
	void setDate(String date);

}
