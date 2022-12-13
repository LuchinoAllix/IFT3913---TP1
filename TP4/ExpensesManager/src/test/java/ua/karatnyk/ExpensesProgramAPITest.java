package ua.karatnyk;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ua.karatnyk.impl.CurrencyConversion;
import ua.karatnyk.impl.ExpensesProgramAPI;
import ua.karatnyk.impl.JsonWorker;
import ua.karatnyk.impl.OfflineJsonWorker;

public class ExpensesProgramAPITest {
	
	
	
	private ExpensesProgramAPI test;
	private CurrencyConversion conversion;

	
	@Before
	public void init() {
		test = new ExpensesProgramAPI();
	}

	@Test
	public void testCheckDate() {
		boolean output = test.checkDate("2017-03-25");
		assertTrue(output);
	}
	
	@Test
	public void testCheckDate2() {
		boolean output = test.checkDate("");
		assertFalse(output);
	}
	
	@Test
	public void testCheckDate3() {
		boolean output = test.checkDate("hi");
		assertFalse(output);
	}
	
	@Test
	public void testCheckDate4() {
		boolean output = test.checkDate(null);
		assertFalse(output);
	}

	@Test
	public void testSelectWord() {
		String input = test.selectWord("add 2017-04-25 2 USD Jougurt", 5, 4);
		assertEquals("USD", input);
	}
	
	@Test
	public void testSelectWord2() {
		String input = test.selectWord("add 2017-04-25 2 USD Jougurt", 6, 4);
		assertNull(input);
	}
	@Test
	public void testSelectWord3() {
		String input = test.selectWord("add 2017-04-25 2 USD Jougurt", 4, 7);
		assertNull(input);
	}
	@Test
	public void testSelectWord4() {
		String input = test.selectWord("add 2017-04-25 2 USD Jougurt", 4, -7);
		assertNull(input);
	}

	@Test
	public void testCheckPossibilityDivide() {
		boolean output = test.checkPossibilityDivide("add 2017-02-25 25 USD Jogurt", 5);
		assertTrue(output);
	}
	@Test
	public void testCheckPossibilityDivide2() {
		boolean output = test.checkPossibilityDivide("add 2017-02-25 25 USD Jogurt", -6);
		assertFalse(output);
	}
	@Test
	public void testCheckPossibilityDivide3() {
		boolean output = test.checkPossibilityDivide("add 2017-02-25 25 USD Jogurt", 0);
		assertFalse(output);
	}
	@Test
	public void testCheckPossibilityDivide4() {
		boolean output = test.checkPossibilityDivide("", 5);
		assertFalse(output);
	}
	@Test(expected = NullPointerException.class)
	public void testCheckPossibilityDivide5() {
		test.checkPossibilityDivide(null, 5);

	}
	

	@Test
	public void testCheckPrice() {
		boolean output = test.checkPrice("hi");
		assertFalse(output);
	}
	@Test(expected = NullPointerException.class)
	public void testCheckPrice2() {
		test.checkPrice(null);
	}
	@Test
	public void testCheckPrice3() {
		boolean output = test.checkPrice("0");
		assertFalse(output);
	}
	@Test
	public void testCheckPrice4() {
		boolean output = test.checkPrice("25");
		assertTrue(output);
	}
	@Test
	public void testCheckPrice5() {
		boolean output = test.checkPrice("-25");
		assertFalse(output);
	}

	@Test
	public void testCheckCurrency() {
		conversion  = new OfflineJsonWorker().parser();
		boolean input = test.checkCurrency("USD", conversion);
		assertTrue(input);
	}
	
	@Test
	public void testCheckCurrency2() {
		conversion  = new OfflineJsonWorker().parser();
		boolean input = test.checkCurrency("eur", conversion);
		assertTrue(input);
	}
	@Test
	public void testCheckCurrency3() {
		conversion  = new OfflineJsonWorker().parser();
		boolean input = test.checkCurrency("", conversion);
		assertFalse(input);
	}
	@Test
	public void testCheckCurrency4() {
		conversion  = new OfflineJsonWorker().parser();
		boolean input = test.checkCurrency("hi", conversion);
		assertFalse(input);
	}

}
