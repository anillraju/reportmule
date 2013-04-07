package com.saba.report.service;

import com.saba.report.Report;
import com.saba.report.engine.ReportEngine;
import com.saba.report.engine.ReportEngineImpl;
import com.saba.report.metadata.ReportMetaData;
import com.saba.report.metadata.ReportMetadataImpl;
import com.saba.report.metadata.ReportMetadataManager;
import com.saba.report.output.ReportGenerationEngine;
import com.saba.report.output.ReportGenerationEngineFactory;
import com.saba.report.output.ReportGeneratorEnum;
import com.saba.report.output.ReportOutput;

public class ReportServiceImpl implements ReportService {

	@Override
	public ReportMetaData getReportMetadata(String reportId) {
		ReportMetaData reportMetaData = ReportMetadataManager.getReportMetadata(reportId);
		return reportMetaData;
	}
	
	@Override
	public ReportOutput getReport(String reportId,String reportType) throws Exception {
		ReportEngine reportEngine = new ReportEngineImpl();
		Report report = reportEngine.runReport(reportId);
		
		ReportGeneratorEnum reportGeneratorEnum = ReportGeneratorEnum.valueOf(reportType);
		ReportGenerationEngine reportGenerationEngine = ReportGenerationEngineFactory.getReportGenerator(reportGeneratorEnum);
		ReportOutput reportOutput = reportGenerationEngine.createReport(report);
		
		return reportOutput;
	}

	@Override
	public ReportOutput createMetadata(ReportMetadataImpl reportMetaData)
			throws Exception {
		System.out.println(reportMetaData);
		reportMetaData.save();
		return null;
	}

}