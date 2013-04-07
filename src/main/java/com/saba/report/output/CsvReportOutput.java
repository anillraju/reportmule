package com.saba.report.output;

import java.io.File;

public class CsvReportOutput implements ReportOutput {
	
	private Object string;
	
	@Override
	public void setData(Object strBuf) {
		if(strBuf instanceof File)
			string = ((File)strBuf).getAbsolutePath();

	}

	@Override
	public Object getData() {
		return string;
	}

}
