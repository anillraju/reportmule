package com.saba.report.output;


public class ReportGenerationEngineFactory {

	public static ReportGenerationEngine getReportGenerator(ReportGeneratorEnum reportGeneratorEnum) {
		switch (reportGeneratorEnum) {
		case CSV:
			return new CSVReportGenerator();
		case REST:
			return new RestReportGenerator();

		case PDF:
			return new PDFReportGenerator();

		default:
			return new DefaultReportGenerator();
		}
	}

}
