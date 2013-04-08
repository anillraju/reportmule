package com.saba.report.attribute;

import com.google.code.morphia.annotations.Entity;

@Entity
public class Attribute {
	private String columnName;
	private String fieldType;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
}
