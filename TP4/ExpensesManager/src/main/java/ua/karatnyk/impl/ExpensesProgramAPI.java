package ua.karatnyk.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ua.karatnyk.IExpensesProgramAPI;

public class ExpensesProgramAPI implements IExpensesProgramAPI{
	
	
	public String readLine() {
		String result = "";
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader inputLine = new BufferedReader(isr);
		try {
			result = inputLine.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//validate date format	

	public boolean checkDate(String string) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			simpleDateFormat.parse(string.trim());
			return true;
		} catch (ParseException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	//divide line to specified amount of parts and return specified part of line
	
	public String selectWord(String line, int part, int number) {
		if(number > part || number <=0 || part <= 0)
			return null;
		if(checkPossibilityDivide(line, part)) {
			return line.trim().split(" ", part)[number-1];
		}
		return null;
	}
	
	
	public boolean checkPossibilityDivide(String line, int number) {
		if(number <= 0)
			return false;
		String[] array = line.trim().split(" ");
		if(array.length >= number) 
			return true;
		return false;
	}
	
	public boolean checkPrice(String string) {
		try {
			Double price = Double.parseDouble(string);
			if(price > 0)
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public boolean checkCurrency(String string, CurrencyConversion response) {
		if(response.getRates().containsKey(string.toUpperCase()))
			return true;
		return false;
	}

	public void printCommand() {
		System.out.println("------------------------");
		System.out.println("use one of the following commands:");
		System.out.println("add yyyy-MM-dd Price Currency Name_Product");
		System.out.println("list");
		System.out.println("clear yyyy-MM-dd");
		System.out.println("total Currency");
		System.out.println("exit");
	}
	
	public void notCorectRecord() {
		System.out.println("Input has incorrect format. Correct format: yyyy-MM-dd Price Currency Name_Product");
		System.out.println("Try again");
	}
	
	public void notCorectPrice() {
		System.out.println("Price has incorrect format");
		System.out.println("Try again");
	}
	
	public void notCorectDate() {
		System.out.println("Date has incorrect format. Correct format: yyyy-MM-dd");
		System.out.println("Try again");
	}
	
	public void notCorectCurrency() {
		System.out.println("Currency has incorrect format.");
		System.out.println("Try again");
	}
	
	public Date stringToDate(String string) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
