package com.saba.report.output;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.prefs.CsvPreference.Builder;

import com.saba.report.Report;
import com.saba.report.ReportRow;
import com.saba.report.attribute.ReportColumn;

public class CSVReportGenerator implements ReportGenerationEngine {

	ReportOutput reportOutput = new CsvReportOutput();
	@Override
	public ReportOutput createReport(Report report) throws Exception {
		reportOutput = new CsvReportOutput();
		
		File file = new File(""+System.nanoTime());
		reportOutput.setData(file);
		Builder csvPreference = new CsvPreference.Builder('\"', ',', "\n");
		CsvMapWriter mapWriter = null;

		mapWriter = new CsvMapWriter(new FileWriter(file),
				csvPreference.build());

		String[] header = new String[report.getAllColumns().size()];
		List<ReportColumn> reportColumns = report.getAllColumns();
		int r = 0;
		for (ReportColumn reportColumn : reportColumns) {
			header[r++] = reportColumn.getDisplayName();
		}
		mapWriter.writeHeader(header);
		CellProcessor[] processors = new CellProcessor[header.length];

		for (int i = 0; i < processors.length; i++) {
			processors[i] = new Optional();
		}
		ReportRow row = report.getNextRow();

		while (row != null) {
			Map<String, String> dataMap = new HashMap<String, String>();
			for (ReportColumn reportColumn : reportColumns) {
				String columnName = reportColumn.getDisplayName();
				Object vl = row.get(reportColumn.getColumnName());
				dataMap.put(columnName, (String)vl);
			}
			mapWriter.write(dataMap, header);
			row = report.getNextRow();
		}
		mapWriter.flush();
		mapWriter.close();
		
		return reportOutput; 
	}

}
