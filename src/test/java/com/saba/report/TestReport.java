package com.saba.report;

//~--- non-JDK imports --------------------------------------------------------

import java.io.File;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.saba.report.attribute.ReportColumn;
import com.saba.report.engine.ReportEngine;
import com.saba.report.engine.ReportEngineImpl;
import com.saba.report.output.ReportGenerationEngine;
import com.saba.report.output.ReportGenerationEngineFactory;
import com.saba.report.output.ReportGeneratorEnum;
import com.saba.report.output.ReportOutput;

public class TestReport {
	@Test
	public void testExistingReport() {
		String reportId = "test";
		ReportEngine reportEngine = new ReportEngineImpl();
		Report report = reportEngine.runReport(reportId);
		List<ReportColumn> reportColumns = report.getAllColumns();

		for (ReportColumn reportColumn : reportColumns) {
			System.out.print(reportColumn.getDisplayName() + ",");
		}

		System.out.println();

		ReportRow nextRow = report.getNextRow();
		int i = 0;
		while (nextRow != null) {
			for (ReportColumn reportColumn : reportColumns) {
				System.out.print(nextRow.get(reportColumn.getColumnName())
						+ ",");
				if (reportColumn.getDisplayName().equals("first name"))
					Assert.assertEquals(
							nextRow.get(reportColumn.getColumnName()), "Anil");
			}
			i++;

			nextRow = report.getNextRow();
			System.out.println();
		}
		Assert.assertEquals(i, 3);

	}

	@Test
	public void testCsvGeneration() throws Exception {
		String reportId = "test";
		ReportEngine reportEngine = new ReportEngineImpl();
		Report report = reportEngine.runReport(reportId);
		File csvFile = new File("anil.csv");

		ReportGenerationEngine reportGenerationEngine = ReportGenerationEngineFactory.getReportGenerator(ReportGeneratorEnum.CSV);
		 ReportOutput reportOutput = reportGenerationEngine.createReport(report);
	}
}
