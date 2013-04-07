package com.saba.report.output;

import java.util.ArrayList;
import java.util.List;

import com.saba.report.Report;
import com.saba.report.ReportRow;
import com.saba.report.attribute.ReportColumn;

public class RestReportGenerator implements ReportGenerationEngine {

	ReportOutput reportOutput;

	@Override
	public ReportOutput createReport(Report report) throws Exception {
		reportOutput = new RestReportOutput();
		List<ReportColumn> reportColumns = report.getAllColumns();
		List<ReportRow> rows = new ArrayList<ReportRow>();

		ReportRow nextRow = report.getNextRow();
		while (nextRow != null) {
			rows.add(nextRow);
			nextRow = report.getNextRow();
		}
		reportOutput.setData(rows);
		return reportOutput;
	}

}
