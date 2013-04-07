package com.saba.report.filter;

import java.io.Serializable;

public class ReportFilter implements Serializable {
    private String   fieldName;
    private Operator operator;
    private String   value;
    

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

	@Override
	public String toString() {
		return "FilterCondition [fieldName=" + fieldName + ", operator="
				+ operator + ", value=" + value + "]";
	}


}
