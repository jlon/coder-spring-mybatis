package com.junit.mongodb;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongodbTest {

	public static void main(String[] args) throws UnknownHostException {
		try {
			// 连接到 mongodb 服务
			MongoClient mongoClient = new MongoClient("123.57.155.6", 27017);
			// 连接到数据库
			DB db = mongoClient.getDB("test");
			
			Set<String> collections = db.getCollectionNames();
			Iterator<String> iterator = collections.iterator();
			
			while (iterator.hasNext()) {
				String string = (String) iterator.next();
				System.err.println(string);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
