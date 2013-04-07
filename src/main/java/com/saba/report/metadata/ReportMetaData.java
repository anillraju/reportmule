package com.saba.report.metadata;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

import com.saba.report.attribute.ReportColumn;
import com.saba.report.filter.ReportFilter;

public interface ReportMetaData {
    public List<ReportFilter> getFilters();

    public List<ReportColumn> getColumns();

	public void setFilters(List<ReportFilter> reportFilters);

	public void setColumns(List<ReportColumn> reportColumns);

	public void save();

	public void setReportName(String string);

	public String getReportName();

	public ReportMetaData restore();
	
}
