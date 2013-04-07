package com.saba.report.attribute;

import java.io.Serializable;

public class ReportColumn implements Serializable{
    private String columnName;
    private String displayName;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

	@Override
	public String toString() {
		return "ReportColumn [columnName=" + columnName + ", displayName="
				+ displayName + "]";
	}

    
    
}
