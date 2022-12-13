package ua.karatnyk.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ua.karatnyk.IExpensesProgram;

public class ExpensesProgram implements IExpensesProgram{
	
	private ExpensesProgramAPI consoleHelper;
	private String line;
	private Map<Date, List<Expense>> expenses;
	private boolean flag;
	private OfflineJsonWorker manager;
	private CurrencyConversion conversion;
	
	public ExpensesProgram() {
		consoleHelper = new ExpensesProgramAPI();
		expenses = new TreeMap<>();
		flag = true;
		manager = new OfflineJsonWorker();
		conversion = manager.parser();
	}
	//start application
	
	public void start() {
		
		while(flag) {
			if(conversion == null) {
				System.out.println("No internet connection. Try again later");
				flag = false;
				break;
			}
			consoleHelper.printCommand();
			line = consoleHelper.readLine();
			String command = chooseCommand(line);
			switch (command) {
			case "add":
				if(checkAddCommand(consoleHelper.selectWord(line, 2, 2))) {
					add(consoleHelper.selectWord(line, 2, 2));
					list();
				}
				break;
			case "list":
				list();
				break;
			case "clear":
				if(checkClearComand(consoleHelper.selectWord(line, 2, 2))) {
					clear(consoleHelper.stringToDate(consoleHelper.selectWord(line, 2, 2)));
					list();
				}
				
				break;
			case "total":
				if(checkTotalCommand(consoleHelper.selectWord(line, 2, 2))) {
					total(consoleHelper.selectWord(line, 2, 2));
				}
				break;	
			case "exit":
				flag = false;
				break;
			default:
				System.out.println("Not exist so command. Try again");
				break;
			}	
		}
	}
	
	//add new expense
	private void add(String string) {
		Date date = consoleHelper.stringToDate(consoleHelper.selectWord(string, 4, 1));
		Expense expense = new Expense(Double.parseDouble(consoleHelper.selectWord(string, 4, 2).trim()), 
				consoleHelper.selectWord(string, 4, 3).trim().toUpperCase(), consoleHelper.selectWord(string, 4, 4).trim());
		if(expenses.containsKey(date)) {
			expenses.get(date).add(expense);
		} else {
			List<Expense> list = new ArrayList<>();
			list.add(expense);
			expenses.put(date, list);
		}
	}
	
	//show added expenses
	private void list() {
		if(expenses.isEmpty())
			System.out.println("There are no expenses");
		for(Map.Entry<Date, List<Expense>> e: expenses.entrySet()) {
			System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(e.getKey()));
			for(Expense v: e.getValue()) {
				System.out.println(v);
			}
			System.out.println();
		}
	}
	
	//remove all expenses for date
	private void clear(Date date) {
		expenses.remove(date);
	}
	//check total command format 
	private boolean checkTotalCommand(String s) {
		if(s == null) {
			consoleHelper.notCorectCurrency();
			return false;
		} if (!consoleHelper.checkCurrency(s, conversion)) {
			consoleHelper.notCorectCurrency();
			return false;
		}
		return true;
	}
	
	//calculate total
	private void total(String currency) {
		conversion = manager.parser();
		if(expenses.isEmpty()) {
			System.out.println("There are no expenses");
		}
		double total = 0;
		try {
			for(Map.Entry<Date, List<Expense>> k: expenses.entrySet()) {
				for(Expense e: k.getValue()) {
					total = total+CurrencyConvertor.convert(e.getAmount(), e.getCurrency(), currency.toUpperCase(), conversion);
				}
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
			}
		System.out.println(total + " "+ currency.toUpperCase());
	}
	
	//check clear command format 
	private boolean checkClearComand(String line) {
		if(line == null || !consoleHelper.checkDate(line)) {
			consoleHelper.notCorectDate();
			return false;
		}
		
		return true;
	}
	//check add command format 
	private boolean checkAddCommand(String line) {
		if(line == null) {
			consoleHelper.notCorectRecord();
			return false;
		}
		if(!consoleHelper.checkPossibilityDivide(line, 4)) {
			consoleHelper.notCorectRecord();
			return false;
		}
		if(!consoleHelper.checkDate(consoleHelper.selectWord(line, 4, 1).trim())) {
			consoleHelper.notCorectDate();
			return false;
		}
		if(!consoleHelper.checkPrice(consoleHelper.selectWord(line, 4, 2).trim())) {
			consoleHelper.notCorectPrice();
			return false;
		}
		if(!consoleHelper.checkCurrency(consoleHelper.selectWord(line, 4, 3).trim(), conversion)) {
			consoleHelper.notCorectCurrency();
			return false;
		}
		return true;
	}
	
	//choose command
	private String chooseCommand(String line) {
		if(consoleHelper.checkPossibilityDivide(line, 2))
			return consoleHelper.selectWord(line, 2, 1).trim();
		return line.trim();
	}
	/*
	 add 2017-04-25 2 USD Jougurt
	 add 2017-04-25 3 EUR "French fries"
	 add 2017-04-27 4.75 EUR Beer
	 add 2017-04-26 2.5 PLN Sweets
	 */

}
