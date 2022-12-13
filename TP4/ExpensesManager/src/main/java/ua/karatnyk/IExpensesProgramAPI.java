package ua.karatnyk;

import java.util.Date;

import ua.karatnyk.impl.CurrencyConversion;

public interface IExpensesProgramAPI {
	
	String readLine();
	boolean checkDate(String string);
	String selectWord(String line, int part, int number);
	boolean checkPossibilityDivide(String line, int number);
	
	boolean checkPrice(String string);
	boolean checkCurrency(String string, CurrencyConversion response);
	Date stringToDate(String string);
}
