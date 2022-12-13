package ua.karatnyk;

import static org.junit.Assert.*;
import org.junit.Test;

import java.text.ParseException;
import java.util.Map;

import ua.karatnyk.impl.CurrencyConvertor;
import ua.karatnyk.impl.CurrencyConversion;
import ua.karatnyk.impl.OfflineJsonWorker;



public class TestCurrencyConvertor {

	private static OfflineJsonWorker offlineJson = new OfflineJsonWorker();
	private static CurrencyConversion conversion = offlineJson.parser();
	private static Map<String, Double> rates = conversion.getRates();
	
	@Test
	public void TestBN1() throws ParseException{
		double amount = 30;
		String from = "EUR";
		String to = "CAD";
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}
	
}
