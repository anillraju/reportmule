package com.saba.report;
//~--- non-JDK imports --------------------------------------------------------

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

//~--- JDK imports ------------------------------------------------------------

import java.net.UnknownHostException;

import java.util.Set;

public class test {

    /**
     * @param args
     * @throws UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException {
        Mongo       mongoClient = new Mongo();
        DB          db          = mongoClient.getDB("mydb");
        Set<String> colNames    = db.getCollectionNames();
        DBObject    match       = new BasicDBObject("$match", new BasicDBObject("type", "airfare"));

        for (String string : colNames) {
            DBCollection      collection = db.getCollection(string);
            AggregationOutput output     = getQuery(collection);

            System.out.println(collection.getName());
            System.out.println("-->" + output.getCommandResult());

            Iterable<DBObject> results = output.results();

            for (DBObject dbObject : results) {
                System.out.println("--" + dbObject.toMap().toString());
            }

            /*
             *   Map <String,String> map = new HashMap<String, String>();
             *           map.put("state", "AL");
             *
             *           DBObject query = new BasicDBObject(map);
             *           DBCursor resultCursor = collection.find();
             *           for (DBObject dbObject : resultCursor) {
             *                   System.out.println(dbObject.get("_id") + " - " + dbObject.get("state")+ " - " + dbObject.get("city"));
             *           }
             */
        }
    }

    private static AggregationOutput getQuery(DBCollection collection) {    // create our pipeline operations, first with the $match

//      DBObject match = new BasicDBObject("$match", new BasicDBObject("pop", "12937926") );
        // build the $projection operation
        // DBObject fields = new BasicDBObject("state", 1);
        // fields.put("pop", 1);
        // DBObject project = new BasicDBObject("$project", fields);
        // Now the $group operation
        DBObject groupFields = new BasicDBObject("_id", "$state");

        groupFields.put("_id", "$city");
        
        groupFields.put("total_pop", new BasicDBObject("$avg", "$pop"));

        DBObject group = new BasicDBObject("$group", groupFields);

        // run aggregation
        AggregationOutput output = collection.aggregate(group);

        return output;
    }
}



/*
db.zipcodes.aggregate( { $group :
                         { _id : { state : "$state", city : "$city" },
                           pop : { $sum : "$pop" } } },
                       { $group :
                       { _id : "$_id.state",
                         avgCityPop : { $avg : "$pop" } } } )


                         select state,sum(population) from ttt group by state


*/
