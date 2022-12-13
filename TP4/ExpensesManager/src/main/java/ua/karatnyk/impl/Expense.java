package ua.karatnyk.impl;

import ua.karatnyk.IExpense;

public class Expense implements IExpense{
	
	private double amount;
	
	private String currency;
	
	private String nameProduct;

	public Expense(double amount, String currency, String nameProduct) {
		super();
		this.amount = amount;
		this.currency = currency;
		this.nameProduct = nameProduct;
	}

	public Expense() {
		super();
	}

	public double getAmount() {
		return amount;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public String getNameProduct() {
		return nameProduct;
	}

	@Override
	public String toString() {
		return nameProduct+" "+amount+" "+currency;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((nameProduct == null) ? 0 : nameProduct.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Expense other = (Expense) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (nameProduct == null) {
			if (other.nameProduct != null)
				return false;
		} else if (!nameProduct.equals(other.nameProduct))
			return false;
		return true;
	}
	
	


}
