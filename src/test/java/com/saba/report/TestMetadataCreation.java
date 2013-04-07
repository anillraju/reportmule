package com.saba.report;

import org.testng.annotations.Test;

import com.saba.report.metadata.ReportMetaData;
import com.saba.report.metadata.ReportMetadataImpl;

public class TestMetadataCreation {
	@Test
	public void testCreateMetadata() {
		ReportMetaData reportMetadata = new ReportMetadataImpl("test");

		reportMetadata.save();
		
	}
	
	@Test
	public void testGetMetadata() {
		ReportMetaData reportMetadata = new ReportMetadataImpl();
		reportMetadata.setReportName("test");
		System.out.println(reportMetadata.restore());
	}	
}
