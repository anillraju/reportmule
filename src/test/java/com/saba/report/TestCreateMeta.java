package com.saba.report;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.saba.report.attribute.Attribute;

public class TestCreateMeta {
static	String scolumns = "_id,Potential Rating,ETHNICITY,Hire Date,WORKPHONE,MNAME,FNAME,LNAME,EMAIL,Manager Username,Company,SupervisorName,Employee Type,Date of Birth,Performance Rating,Country Code,Location Name,Hourly Wage,CostCenter,Salary Currency,Is Manager,GENDER,Jobtype Name,Employee Status,Last Performance Review Date,USERNAME,TITLE,";
	@Test
	public void testCreateMeta() {
		Datastore ds = new Morphia().createDatastore("saba"); //best to use (Mongo, String) method, where Mongo is a singleton.

		String columns[] = scolumns.split(",");
		List<Attribute> columnsList = new ArrayList<Attribute>();
		for (String column : columns) {
			Attribute attr = new Attribute();
			attr.setColumnName(column);
			if(isInt(column))
				attr.setFieldType("Integer");
			else if(isDate(column))
				attr.setFieldType("Date");
			else
				attr.setFieldType("String");
			columnsList.add(attr);
		}
		ds.save(columnsList);
	}

	private boolean isInt(String column) {
		String intColumns = "Hourly Wage,CostCenter,WORKPHONE";
		String[] col = intColumns.split(",");
		for (String string : col) {
			if(string.equalsIgnoreCase(column))
				return true;
		}
		return false;
	}
	
	private boolean isDate(String column) {
		String intColumns = "Date of Birth,Hire Date,Last Performance Review Date" ;
		String[] col = intColumns.split(",");
		for (String string : col) {
			if(string.equalsIgnoreCase(column))
				return true;
		}
		return false;
	}
}
