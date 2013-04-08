package com.saba.report;

import org.testng.annotations.Test;

import com.saba.report.engine.ReportEngine;
import com.saba.report.engine.ReportEngineImpl;
import com.saba.report.output.PDFReportGenerator;

public class TestPdf {
	@Test
	public void testPdfCreation() throws Exception {
		
		String reportId = "test";
		ReportEngine reportEngine = new ReportEngineImpl();
		Report report = reportEngine.runReport(reportId);

		
		
		PDFReportGenerator pdf = new PDFReportGenerator();
		pdf.createReport(report);
	}
}
