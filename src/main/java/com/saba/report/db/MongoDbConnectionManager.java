package com.saba.report.db;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoDbConnectionManager {

	public static DB getDB() {
		DBCollection collection;

		Mongo mongoClient = null;

		try {
			mongoClient = new Mongo();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		DB db = mongoClient.getDB("saba");
		return db;
		
	}

}
