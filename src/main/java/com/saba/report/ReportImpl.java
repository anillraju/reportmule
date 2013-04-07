package com.saba.report;

//~--- non-JDK imports --------------------------------------------------------

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.saba.report.attribute.ReportColumn;
import com.saba.report.metadata.ReportMetaData;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

public class ReportImpl implements Report {
	private DBCursor dbcursor;
	private ReportMetaData reportMetaData;

	public ReportImpl(DBCursor dbcursor) {
		this.dbcursor = dbcursor;
	}

	@Override
	public List<ReportColumn> getAllColumns() {
		return reportMetaData.getColumns();
	}

	public ReportMetaData getReportMetaData() {
		return reportMetaData;
	}

	public void setReportMetaData(ReportMetaData reportMetaData) {
		this.reportMetaData = reportMetaData;
	}

	@Override
	public ReportRow getNextRow() {
		DBObject dbObject = null;

		if (this.dbcursor.hasNext()) {
			dbObject = this.dbcursor.next();
		} else {
			return null;
		}

		ReportRow reportRow = new ReportRow();

		reportRow.setDbObject(dbObject);

		return reportRow;
	}
}
