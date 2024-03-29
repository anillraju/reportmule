package com.saba.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PersonObject {
	private DBObject personDbObject;

	public PersonObject(Map<String, Object> currentLine) {
		personDbObject = new BasicDBObject(getPersonMap(currentLine));

	}

	private Map<String, Object> getPersonMap(Map<String, Object> currentLine) {
		Map<String, Object> person = new HashMap<String, Object>();
		person.putAll(currentLine);
		Map<String, Object> name = new HashMap<String, Object>();
		name.put("FNAME", currentLine.get("FNAME"));
		name.put("LNAME", currentLine.get("LNAME"));
		name.put("MNAME", currentLine.get("MNAME"));
		person.put("name", name);

		Map<String, Object> compensation = new HashMap<String, Object>();
		int salCurr = 0, hourlyWage = 0, costCenter = 0;
		try {
			hourlyWage = Integer.parseInt((String) currentLine
					.get("Hourly Wage"));
		} catch (Exception e) {
		}
		try {
			costCenter = Integer.parseInt((String) currentLine
					.get("CostCenter"));
		} catch (Exception e) {
		}
		compensation.put("Hourly Wage", hourlyWage);
		compensation.put("Salary Currency", salCurr);
		compensation.put("CostCenter", costCenter);
		person.put("compensation", compensation);

		Date dob = getDate((String) currentLine.get("Date of Birth"));
		Date hireDate = getDate((String) currentLine.get("Hire Date"));
		Date lstPerfRevDate = getDate((String) currentLine
				.get("Last Performance Review Date"));

		person.remove("Date of Birth");
		person.remove("Hire Date");
		person.remove("Last Performance Review Date");

		person.put("Date of Birth", dob);
		person.put("Hire Date", hireDate);
		person.put("Last Performance Review Date", lstPerfRevDate);

		person.remove("FNAME");
		person.remove("LNAME");
		person.remove("MNAME");
		person.remove("Hourly Wage");
		person.remove("Salary Currency");
		person.remove("CostCenter");
		person.put("_id", System.nanoTime());
		return person;
	}

	private Date getDate(String str) {
		int ranMath = new Double((Math.random()*100)%10).intValue();
		if(ranMath==0)
			ranMath=6;
		int ranMatha = new Double((Math.random()*100)%10).intValue();
		if(ranMatha==0)
			ranMatha=6;
		try {
			
			Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
					.parse(str);
			return date;
		} catch (Exception e) {
			try {
				return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
						.parse("200"+ranMath+"-"+ranMatha+"-"+ranMath);
			} catch (ParseException e1) {
				return new Date();
			}
		}

	}

	public DBObject getDBObject() {
		return personDbObject;
	}
}
