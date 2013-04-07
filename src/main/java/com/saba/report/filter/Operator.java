package com.saba.report.filter;

public enum Operator {
	STRING_NOTEQUAL("$ne"), STRING_EQUAL("$e"), NUMBER_GREATER("$gt"), NUMBER_GREATERTHANEQUAL(
			"$gte"), IN("$in"), NUMBER_LESSTHAN("$lt"), NUMBER_LESSTHANEQUAL("$lte"), 			
			NUMBER_NOTEQUAL("$ne"), NUMBER_EQUAL("$e"), 			
			
			
			
			
			;

	String symbol;

	private Operator(String sym) {
		symbol = sym;
	}

	public String getSymbol() {
		return symbol;
	}
}
