package com.saba.report.service;

import java.util.List;
import java.util.Set;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.BasicDBObject;
import com.saba.report.Report;
import com.saba.report.attribute.Attribute;
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
		ReportMetaData reportMetaData = ReportMetadataManager
				.getReportMetadata(reportId);
		return reportMetaData;
	}

	@Override
	public ReportOutput getReport(String reportId, String reportType)
			throws Exception {
		ReportEngine reportEngine = new ReportEngineImpl();
		Report report = reportEngine.runReport(reportId);

		ReportGeneratorEnum reportGeneratorEnum = ReportGeneratorEnum
				.valueOf(reportType);
		ReportGenerationEngine reportGenerationEngine = ReportGenerationEngineFactory
				.getReportGenerator(reportGeneratorEnum);
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

	@Override
	public List<Attribute> getColumns() throws Exception {
		Datastore ds = new Morphia().createDatastore("saba");

		List<Attribute> list = ds.find(Attribute.class).asList();

		return list;
	}

	private void addToList(List<Attribute> list, Object value) {
		BasicDBObject dbobj = ((BasicDBObject) value);
		Set<String> set = dbobj.keySet();
		for (String string : set) {
			Attribute r = new Attribute();
			r.setColumnName(string);
			Object valu = dbobj.get(string);
			if (valu instanceof BasicDBObject) {
				addToList(list, valu);
				continue;
			}
			if (valu != null)
				r.setFieldType(valu.getClass().getSimpleName());
			else
				r.setFieldType("String");
			list.add(r);
		}

	}

}