package com.saba.report.output;

import com.saba.report.Report;

public interface ReportGenerationEngine {

	ReportOutput createReport(Report report) throws Exception;

}
