package com.saba.report;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PersonObject {
	private DBObject personDbObject;

	public PersonObject(Map<String, Object> currentLine) {
		personDbObject = new BasicDBObject(getPersonMap(currentLine));

	}

	private Map<String,Object> getPersonMap(Map<String, Object> currentLine) {
		Map<String, Object> person = new HashMap<String, Object>();
		person.putAll(currentLine);
		Map<String, Object> name = new HashMap<String, Object>();
		name.put("FNAME", currentLine.get("FNAME"));
		name.put("LNAME", currentLine.get("LNAME"));
		name.put("MNAME", currentLine.get("MNAME"));
		person.put("name", name);

		Map<String, Object> compensation = new HashMap<String, Object>();
		int salCurr=0,hourlyWage=0,costCenter=0;
		try{
			salCurr = Integer.parseInt((String)currentLine.get("Salary Currency"));
		}catch (Exception e) {
		}
		try{
			hourlyWage = Integer.parseInt((String)currentLine.get("Hourly Wage"));
		}catch (Exception e) {
		}
		try{
			costCenter = Integer.parseInt((String)currentLine.get("CostCenter"));
		}catch (Exception e) {
		}
		compensation.put("Hourly Wage", hourlyWage);
		compensation.put("Salary Currency", salCurr);
		compensation.put("CostCenter", costCenter);
		person.put("compensation", compensation);

		person.remove("FNAME");
		person.remove("LNAME");
		person.remove("MNAME");
		person.remove("Hourly Wage");
		person.remove("Salary Currency");
		person.remove("CostCenter");
		person.put("_id", System.nanoTime());
		return person;
	}

	public DBObject getDBObject() {
		return personDbObject;
	}
}
