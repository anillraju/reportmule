package com.saba.report;
//~--- non-JDK imports --------------------------------------------------------

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.antlr.test.BaseTest;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.prefs.CsvPreference;
import org.supercsv.prefs.CsvPreference.Builder;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class TestMigrationData extends BaseTest {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Mongo mongoClient = new Mongo();
		DBCollection db = mongoClient.getDB("saba").getCollection("employees");

		Builder csvPreference = new CsvPreference.Builder('\"', ',', "\n");
		CsvMapReader mapReader = null;

		mapReader = new CsvMapReader(
				new FileReader(
						"D:\\installation\\mongodb-win32-x86_64-2008plus-2.2.3-rc0\\person.csv"),
				csvPreference.build());

		String[] header = mapReader.getHeader(true);

		CellProcessor[] processors = new CellProcessor[header.length];

		for (int i = 0; i < processors.length; i++) {
			processors[i] = new Optional();
		}
		List<DBObject> list = new LinkedList<DBObject>();
		Map<String, Object> currentLine = mapReader.read(header, processors);
		while (currentLine != null) {

			DBObject obj = new PersonObject(currentLine).getDBObject();
			currentLine = mapReader.read(header, processors);
			list.add(obj);
		}
		long time = System.nanoTime();
		db.insert(list);
		System.out.println((System.nanoTime() - time) / 1000000);
	}
}