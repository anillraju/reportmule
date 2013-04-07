package com.saba.report.engine;

import com.saba.report.Report;

public interface ReportEngine {
    Report runReport(String reportId);
}
