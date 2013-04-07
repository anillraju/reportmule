package com.saba.report.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.saba.report.metadata.ReportMetaData;
import com.saba.report.metadata.ReportMetadataImpl;
import com.saba.report.output.ReportOutput;

@Path("/report")
public interface ReportService {
	@GET
	@Path("/metadata/{reportId}")
	@Produces("application/json")
	public ReportMetaData getReportMetadata(
			@PathParam("reportId") String reportId);

	@GET
	@Path("/get/{reportId}/{reportType}")
	@Produces("application/json")
	public ReportOutput getReport(@PathParam("reportId") String reportId,
			@PathParam("reportType") String reportType) throws Exception;
	
	
	@POST
	@Path("/metadata/create")
	@Consumes("application/json")
	@Produces("application/json")
public	ReportOutput createMetadata(ReportMetadataImpl reportMetaData)
			throws Exception;

}