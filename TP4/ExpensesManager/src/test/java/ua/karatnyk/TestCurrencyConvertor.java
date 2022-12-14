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
	private static final double o = Double.MIN_VALUE;
	private static final double[] jeuValAmount = {-1000,0-o,0,3000,10000,10000+o,15000}; 
	private static final String[] jeuValCur = {"CAD","EUR","NOK","BOB"};
	
	@Test
	public void TestBN1() throws ParseException{
		double amount = jeuValAmount[0];
		String from = jeuValCur[0]; //CAD
		String to = jeuValCur[0]; //CAD
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN2() throws ParseException{
		double amount = jeuValAmount[0];
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[1]; // EUR
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN3() throws ParseException{
		double amount = jeuValAmount[0];
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[2]; // NOK
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN4() throws ParseException{
		double amount = jeuValAmount[0];
		String from = jeuValCur[2]; // NOK
		String to = jeuValCur[2]; // NOK
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN5() throws ParseException{
		double amount = jeuValAmount[0];
		String from = jeuValCur[2]; // NOK
		String to = jeuValCur[3]; // BOB
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}
	@Test
	public void TestBN6() throws ParseException{
		double amount = jeuValAmount[1];
		String from = jeuValCur[0]; //CAD
		String to = jeuValCur[0]; //CAD
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN7() throws ParseException{
		double amount = jeuValAmount[1];
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[1]; // EUR
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN8() throws ParseException{
		double amount = jeuValAmount[1];
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[2]; // NOK
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN9() throws ParseException{
		double amount = jeuValAmount[1];
		String from = jeuValCur[2]; // NOK
		String to = jeuValCur[2]; // NOK
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN10() throws ParseException{
		double amount = jeuValAmount[1];
		String from = jeuValCur[2]; // NOK
		String to = jeuValCur[3]; // BOB
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}
	@Test
	public void TestBN11() throws ParseException{
		double amount = jeuValAmount[2];
		String from = jeuValCur[0]; //CAD
		String to = jeuValCur[0]; //CAD
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN12() throws ParseException{
		double amount = jeuValAmount[2];
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[1]; // EUR
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN13() throws ParseException{
		double amount = jeuValAmount[2];
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[2]; // NOK
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN14() throws ParseException{
		double amount = jeuValAmount[2];
		String from = jeuValCur[2]; // NOK
		String to = jeuValCur[2]; // NOK
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN15() throws ParseException{
		double amount = jeuValAmount[2];
		String from = jeuValCur[2]; // NOK
		String to = jeuValCur[3]; // BOB
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}
	@Test
	public void TestBN16() throws ParseException{
		double amount = jeuValAmount[3];
		String from = jeuValCur[0]; //CAD
		String to = jeuValCur[0]; //CAD
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN17() throws ParseException{
		double amount = jeuValAmount[3];
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[1]; // EUR
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN18() throws ParseException{
		double amount = jeuValAmount[3];
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[2]; // NOK
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN19() throws ParseException{
		double amount = jeuValAmount[3];
		String from = jeuValCur[2]; // NOK
		String to = jeuValCur[2]; // NOK
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN20() throws ParseException{
		double amount = jeuValAmount[3];
		String from = jeuValCur[2]; // NOK
		String to = jeuValCur[3]; // BOB
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}
	@Test
	public void TestBN21() throws ParseException{
		double amount = jeuValAmount[4];
		String from = jeuValCur[0]; //CAD
		String to = jeuValCur[0]; //CAD
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN22() throws ParseException{
		double amount = jeuValAmount[4];
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[1]; // EUR
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN23() throws ParseException{
		double amount = jeuValAmount[4];
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[2]; // NOK
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN24() throws ParseException{
		double amount = jeuValAmount[4];
		String from = jeuValCur[2]; // NOK
		String to = jeuValCur[2]; // NOK
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN25() throws ParseException{
		double amount = jeuValAmount[4];
		String from = jeuValCur[2]; // NOK
		String to = jeuValCur[3]; // BOB
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}
	@Test
	public void TestBN26() throws ParseException{
		double amount = jeuValAmount[5];
		String from = jeuValCur[0]; //CAD
		String to = jeuValCur[0]; //CAD
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN27() throws ParseException{
		double amount = jeuValAmount[5];
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[1]; // EUR
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN28() throws ParseException{
		double amount = jeuValAmount[5];
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[2]; // NOK
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN29() throws ParseException{
		double amount = jeuValAmount[5];
		String from = jeuValCur[2]; // NOK
		String to = jeuValCur[2]; // NOK
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN30() throws ParseException{
		double amount = jeuValAmount[5];
		String from = jeuValCur[2]; // NOK
		String to = jeuValCur[3]; // BOB
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}
	@Test
	public void TestBN31() throws ParseException{
		double amount = jeuValAmount[6];
		String from = jeuValCur[0]; //CAD
		String to = jeuValCur[0]; //CAD
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN32() throws ParseException{
		double amount = jeuValAmount[6];
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[1]; // EUR
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN33() throws ParseException{
		double amount = jeuValAmount[6];
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[2]; // NOK
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN34() throws ParseException{
		double amount = jeuValAmount[6];
		String from = jeuValCur[2]; // NOK
		String to = jeuValCur[2]; // NOK
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBN35() throws ParseException{
		double amount = jeuValAmount[6];
		String from = jeuValCur[2]; // NOK
		String to = jeuValCur[3]; // BOB
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBB1() throws ParseException{
		double amount = jeuValAmount[3]; 
		String from = jeuValCur[0]; // CAD
		String to = jeuValCur[1]; // EUR
		double automatic = CurrencyConvertor.convert(amount, from, to, conversion);
		double manual = amount*(rates.get(to)/rates.get(from));
		assertTrue(manual==automatic);
	}

	@Test
	public void TestBB2(){
		double amount = jeuValAmount[3]; 
		String from = "";
		String to = jeuValCur[1]; // EUR

		try {
			CurrencyConvertor.convert(amount, from, to, conversion);
			assertTrue(false); // Test fails if no exception is thrown
		} catch (ParseException e) {
			assertTrue(true);
		} 
		
	}

	@Test
	public void TestBB3(){
		double amount = jeuValAmount[3]; 
		String from = jeuValCur[1]; // EUR
		String to = "";

		try {
			CurrencyConvertor.convert(amount, from, to, conversion);
			assertTrue(false); // Test fails if no exception is thrown
		} catch (ParseException e) {
			assertTrue(true);
		} 
		
	}
	

	
}
