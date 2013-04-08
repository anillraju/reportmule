package com.saba.report.output;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.saba.report.Report;
import com.saba.report.ReportRow;
import com.saba.report.attribute.ReportColumn;

public class PDFReportGenerator implements ReportGenerationEngine {

	@Override
	public ReportOutput createReport(Report report) throws Exception {
		
		PDDocument doc = new PDDocument();
		PDPage page = new PDPage();
		doc.addPage( page );

		PDPageContentStream contentStream = new PDPageContentStream(doc, page);

		String [][]content = getArrayData(report);

		drawTable(page, contentStream, 700, 100, content);
		contentStream.close();
		doc.save(System.nanoTime() + ".pdf");
		return null;
	}

	private String[][] getArrayData(Report report) {
		
		List<ReportColumn> columns = report.getAllColumns();
		List<List<String>> rows = new ArrayList<List<String>>(); 
		List<String> row = new ArrayList<String>();
		for (ReportColumn column : columns) {
			row.add(column.getDisplayName());
		}
		rows.add(row);
		
		ReportRow reportRow = report.getNextRow();
		while(reportRow!=null){
			List<String> currRow = new ArrayList<String>();
			for (ReportColumn reportColumn : columns) {
				Object value = reportRow.get(reportColumn.getColumnName());
				if(value instanceof Date){
					Format formatter = new SimpleDateFormat("yyyy-MM-dd");
					String s = formatter.format(value);
					currRow.add(s);
				}else{
					currRow.add(value+"");
				}
				
			}
			rows.add(currRow);
			reportRow = report.getNextRow();
		}
		String returnData[][] = new String[rows.size()][columns.size()];
		
		
		for (int i = 0; i < returnData.length; i++) {
			returnData[i] =  rows.get(i).toArray(new String[rows.get(i).size()]);
		}
		
		
		return returnData;
	}

	public static void drawTable(PDPage page,
			PDPageContentStream contentStream, float y, float margin,
			String[][] content) throws IOException {
		final int rows = content.length;
		final int cols = content[0].length;
		final float rowHeight = 20f;
		final float tableWidth = page.findMediaBox().getWidth() - margin
				- margin;
		final float tableHeight = rowHeight * rows;
		final float colWidth = tableWidth / (float) cols;
		final float cellMargin = 5f;

		// draw the rows
		float nexty = y;
		for (int i = 0; i <= rows; i++) {
			contentStream.drawLine(margin, nexty, margin + tableWidth, nexty);
			nexty -= rowHeight;
		}

		// draw the columns
		float nextx = margin;
		for (int i = 0; i <= cols; i++) {
			contentStream.drawLine(nextx, y, nextx, y - tableHeight);
			nextx += colWidth;
		}

		// now add the text
		contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

		float textx = margin + cellMargin;
		float texty = y - 15;
		for (int i = 0; i < content.length; i++) {
			for (int j = 0; j < content[i].length; j++) {
				String text = content[i][j];
				contentStream.beginText();
				contentStream.moveTextPositionByAmount(textx, texty);
				contentStream.drawString(text);
				contentStream.endText();
				textx += colWidth;
			}
			texty -= rowHeight;
			textx = margin + cellMargin;
		}
	}

}
