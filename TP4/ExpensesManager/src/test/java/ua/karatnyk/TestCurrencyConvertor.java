package ua.karatnyk;

import static org.junit.Assert.*;

import java.text.ParseException;

import ua.karatnyk.impl.CurrencyConvertor;
import ua.karatnyk.impl.CurrencyConversion;

import org.junit.Before;
import org.junit.Test;

public class TestCurrencyConvertor {
	
	@Test
	public void Test1() throws ParseException{
		CurrencyConversion i = new CurrencyConversion();
		double d = CurrencyConvertor.convert(30, "EUR", "CAD", i);
		assertTrue(d==1.0);
	}
}
