package com.saba.report.metadata;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.saba.report.attribute.ReportColumn;
import com.saba.report.db.MongoDbConnectionManager;
import com.saba.report.filter.ReportFilter;
import com.saba.report.filter.Operator;
import com.saba.report.filter.ReportFilter;

import edu.emory.mathcs.backport.java.util.Collections;

public class ReportMetadataImpl implements ReportMetaData {
	private String reportName;
	private List<ReportFilter> reportFilters = new ArrayList<ReportFilter>();
	private List<ReportColumn> reportColumns = new ArrayList<ReportColumn>();

	@Override
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}


	public ReportMetadataImpl(String reportId) {
		if ("test".equalsIgnoreCase(reportId)) {
			setTestDefaults();
		}
		else{
			setReportName(reportId);
			restore();
		}
	}

	public ReportMetadataImpl() {
		
	}

	@Override
	public List<ReportFilter> getFilters() {
		return reportFilters;

	}

	@Override
	public List<ReportColumn> getColumns() {
		return reportColumns;
	}

	private void setTestDefaults() {
		ReportFilter f1 = new ReportFilter();

		f1.setFieldName("name.FNAME");
		f1.setOperator(Operator.STRING_EQUAL);
		f1.setValue("Shailesh");

		ReportFilter f2 = new ReportFilter();

		f2.setFieldName("city");
		f2.setOperator(Operator.STRING_NOTEQUAL);
		f2.setValue("CHEYENNE");
		reportFilters.add(f1);
		setReportName("test");
		ReportColumn rc = new ReportColumn();

		rc.setColumnName("name.FNAME");
		rc.setDisplayName("first name");

		ReportColumn rc1 = new ReportColumn();

		rc1.setColumnName("name.LNAME");
		rc1.setDisplayName("Last name");

		ReportColumn rc2 = new ReportColumn();

		rc2.setColumnName("compensation.Hourly Wage");
		rc2.setDisplayName("cost");
		reportColumns.add(rc);
		reportColumns.add(rc1);
		reportColumns.add(rc2);

	}

	@Override
	public void setFilters(List<ReportFilter> reportFilters) {
		this.reportFilters = reportFilters;
	}

	@Override
	public void setColumns(List<ReportColumn> reportColumns) {
		this.reportColumns = reportColumns;
	}

	@Override
	public void save() {
		DBCollection metaData = MongoDbConnectionManager.getDB().getCollection(
				"metadata");
		DBObject dbObject = this.getDBObject();
		WriteResult res = metaData.save(dbObject);
	}

	private DBObject getDBObject() {
		List<Map> filters = new ArrayList<Map>();
		for (ReportFilter reportFilter : reportFilters) {
			Map<String, String> filter = new HashMap<String, String>();
			filter.put("fieldName", reportFilter.getFieldName());
			filter.put("operator", reportFilter.getOperator().name());
			filter.put("fieldValue", reportFilter.getValue());
			filters.add(filter);
		}

		List<Map> columns = new ArrayList<Map>();
		for (ReportColumn reportColumn : reportColumns) {
			Map<String, String> column = new HashMap<String, String>();
			column.put("columnName", reportColumn.getColumnName());
			column.put("displayName", reportColumn.getDisplayName());
			columns.add(column);
		}

		Map map = new HashMap();
		map.put("filters", filters);
		map.put("columns", columns);
		map.put("_id", reportName);
		DBObject dbObjfilters = new BasicDBObject(map);
		return dbObjfilters;
	}

	@Override
	public ReportMetaData restore() {
		DBCollection metaData = MongoDbConnectionManager.getDB().getCollection(
		"metadata");
		DBObject reportObj = metaData.findOne(this.reportName);
		System.out.println(reportObj);
		return buildReportMetaData(reportObj);
		
	}

	private ReportMetaData buildReportMetaData(DBObject reportObj) {
		
		List<Map> filters = (List<Map>) reportObj.get("filters");
		List<Map> columns = (List<Map>) reportObj.get("columns");
		String reportName = (String) reportObj.get("_id");
		
		
		for (Map map : filters) {
			ReportFilter reportFilter = new ReportFilter();
			reportFilter.setFieldName((String)map.get("fieldName"));
			reportFilter.setOperator(Operator.valueOf((String)map.get("operator")));
			reportFilter.setValue((String)map.get("fieldValue"));
			reportFilters.add(reportFilter);
		}

		for (Map map : columns) {
			ReportColumn reportColumn = new ReportColumn();
			reportColumn.setColumnName((String)map.get("columnName"));
			reportColumn.setDisplayName((String)map.get("displayName"));
			reportColumns.add(reportColumn);
		}

		setReportName(reportName);
		
		return this;
	}

	@Override
	public String toString() {
		return "ReportMetadataImpl [reportName=" + reportName
				+ ",\n reportFilters=" + reportFilters + ",\n reportColumns="
				+ reportColumns + "]";
	}
	
	
	
}
