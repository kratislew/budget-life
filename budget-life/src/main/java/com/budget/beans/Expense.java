package com.budget.beans;

public class Expense extends Value{
	public Expense() {
		super();
	}
	
	public Expense(double amount, String title, String desc) {
		super(amount, title, desc);
	}
}