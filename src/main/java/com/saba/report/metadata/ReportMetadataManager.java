package com.saba.report.metadata;

public class ReportMetadataManager {
    public static ReportMetaData getReportMetadata(String reportId) {
        return new ReportMetadataImpl(reportId);
    }
}
