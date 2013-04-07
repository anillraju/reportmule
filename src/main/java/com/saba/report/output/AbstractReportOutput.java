package com.saba.report.output;

public class AbstractReportOutput implements ReportOutput {
	private Object data;

	@Override
	public void setData(Object strBuf) {
		if (strBuf instanceof StringBuffer) {
			data = ((StringBuffer) strBuf).toString();
		} else {
			data = strBuf;
		}
	}

	public Object getData() {
		return data;
	}

}
