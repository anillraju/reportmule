package com.saba.report.engine;

//~--- non-JDK imports --------------------------------------------------------

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.saba.report.Report;
import com.saba.report.ReportImpl;
import com.saba.report.attribute.ReportColumn;
import com.saba.report.db.MongoDbConnectionManager;
import com.saba.report.filter.Operator;
import com.saba.report.filter.ReportFilter;
import com.saba.report.metadata.ReportMetaData;
import com.saba.report.metadata.ReportMetadataManager;

public class ReportEngineImpl implements ReportEngine {

	@Override
	public Report runReport(String reportId) {
		DBCollection employeeCollection = MongoDbConnectionManager.getDB()
				.getCollection("employees");
		ReportMetaData reportMetaData = ReportMetadataManager
				.getReportMetadata(reportId);

		DBObject filter = getMetricReportFilter(reportMetaData.getFilters());
		DBObject columns = getMetricReportQuery(reportMetaData.getColumns());

		if (filter == null) {
			filter = new BasicDBObject();
		}

		if (columns == null) {
			columns = new BasicDBObject();
		}

		DBCursor dbcursor = employeeCollection.find(filter, columns);
		Report report = new ReportImpl(dbcursor);

		report.setReportMetaData(reportMetaData);

		return report;
	}

	private static DBObject getMetricReportFilter(List<ReportFilter> list) {
		if (list.size() == 0) {
			return getMetricReportFilter();
		}

		BasicDBObject filter = new BasicDBObject();

		for (int i = list.size() - 1; i >= 0; i--) {
			ReportFilter filterCondition = list.get(i);

			switch (filterCondition.getOperator()) {
			case STRING_EQUAL:
				filter.append(filterCondition.getFieldName(),
						filterCondition.getValue());
				break;
			case NUMBER_EQUAL:
				filter.append(filterCondition.getFieldName(),
						Integer.parseInt((String) filterCondition.getValue()));
				break;
			case STRING_NOTEQUAL:
				filter.append(
						filterCondition.getFieldName(),
						new BasicDBObject(filterCondition.getOperator()
								.getSymbol(), (String) filterCondition
								.getValue()));
				break;
			case NUMBER_NOTEQUAL:
			case NUMBER_GREATER:
			case NUMBER_GREATERTHANEQUAL:
			case NUMBER_LESSTHAN:
			case NUMBER_LESSTHANEQUAL:
				filter.append(
						filterCondition.getFieldName(),
						new BasicDBObject(filterCondition.getOperator()
								.getSymbol(), Integer
								.parseInt((String) filterCondition.getValue())));
				break;
			default:
				filter.append(filterCondition.getFieldName(),
						new BasicDBObject(filterCondition.getOperator()
								.getSymbol(), filterCondition.getValue()));
				break;
			}
		}

		return filter;
	}

	private static DBObject getMetricReportFilter() {
		return new BasicDBObject();
	}

	private static DBObject getMetricReportQuery(List<ReportColumn> list) {
		if (list.size() == 0) {
			return getMetricReport();
		}

		DBObject dbobj = new BasicDBObject();

		for (ReportColumn string : list) {
			dbobj.put(string.getColumnName(), 1);
		}

		return dbobj;
	}

	private static DBObject getMetricReport() {
		return new BasicDBObject();
	}
}
