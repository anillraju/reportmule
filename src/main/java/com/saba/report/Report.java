package com.saba.report;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

import com.saba.report.attribute.ReportColumn;
import com.saba.report.metadata.ReportMetaData;

public interface Report {
    List<ReportColumn> getAllColumns();

    ReportRow getNextRow();

    void setReportMetaData(ReportMetaData reportMetaData);
}
