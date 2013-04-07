package com.saba.report;

//~--- non-JDK imports --------------------------------------------------------

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.bson.BSONObject;

//~--- JDK imports ------------------------------------------------------------

import java.util.Map;
import java.util.Set;

public class ReportRow implements DBObject {
	private DBObject dbObject;

	public void setDbObject(DBObject dbObject) {
		this.dbObject = dbObject;
	}

	@Override
	public boolean containsField(String arg0) {
		return dbObject.containsField(arg0);
	}

	@Override
	@Deprecated
	public boolean containsKey(String arg0) {
		return dbObject.containsKey(arg0);
	}

	@Override
	public Object get(String columnName) {

		if (columnName.contains(".")) {
			String[] columnNameArr = columnName.split("\\.");
			Object map = dbObject;
			for (String string : columnNameArr) {
				if (map instanceof ReportRow)
					map = ((ReportRow) (map)).get(string);
				else if (map instanceof BasicDBObject)
					map = ((BasicDBObject) (map)).get(string);
			}
			return (String) map;
		}
		return dbObject.get(columnName);
	}

	@Override
	public Set<String> keySet() {
		return dbObject.keySet();
	}

	@Override
	public Object put(String arg0, Object arg1) {
		return dbObject.put(arg0, arg1);
	}

	@Override
	public void putAll(BSONObject arg0) {
		dbObject.putAll(arg0);
	}

	@Override
	public void putAll(Map arg0) {
		dbObject.putAll(arg0);
	}

	@Override
	public Object removeField(String arg0) {
		return dbObject.removeField(arg0);
	}

	@Override
	public Map toMap() {
		return dbObject.toMap();
	}

	@Override
	public boolean isPartialObject() {
		return dbObject.isPartialObject();
	}

	@Override
	public void markAsPartialObject() {
		dbObject.markAsPartialObject();
	}

	@Override
	public String toString() {
		return dbObject.toMap().toString();
	}

	public DBObject getDbObject() {
		return dbObject;
	}
	
	
}
