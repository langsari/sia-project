package com.sia.modul.domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Operator {

	private HashMap<String, ArrayList<String>> mapOperator;
	
	public HashMap<String, ArrayList<String>> getMapOperator() {
		return mapOperator;
	}
	
	public ArrayList<String> getByOperator(String string)
	{
		ArrayList<String> result = new ArrayList<String>();
		this.mapOperator.get(string);
		return result;
	}
	
	private void addMap(String string, ArrayList<String> arrayListString)
	{
		this.mapOperator.put(string, arrayListString);
	}
	
	public Operator() {
		ArrayList<String> operatorInteger = new ArrayList<String>();
		operatorInteger.add("=");
		operatorInteger.add(">=");
		operatorInteger.add("<=");
		operatorInteger.add(">");
		operatorInteger.add("<");
		
		addMap("operatorInteger", operatorInteger);
		
		ArrayList<String> operatorString = new ArrayList<String>();
		operatorString.add("=");
		
		addMap("operatorString", operatorString);
		
		ArrayList<String> operatorBoolean = new ArrayList<String>();
		operatorString.add("=");
		
		addMap("operatorBoolean", operatorBoolean);
		
		ArrayList<String> operatorDate = new ArrayList<String>();
		operatorString.add(">=");
		operatorString.add("<=");
		operatorString.add("=");
		
		addMap("operatorDate", operatorDate);
		
		ArrayList<String> operatorObjek = new ArrayList<String>();
		operatorString.add("=");
		
		addMap("operatorObjek", operatorObjek);
	}

}
