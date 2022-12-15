package com.perficient.expressions.repositories.implementation;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;
import com.perficient.expressions.repositories.interfaces.IRow;

@Repository
public class RowImp implements IRow{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Document> findAll() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test");
        List<Document> rows = new ArrayList<Document>();;
        for (String name :  mongoTemplate.getCollectionNames()) {
            System.out.println(name);
        }
        for (Document document : collection.find()) {
            System.out.println("Item "+document.getString("item"));
            System.out.println("Price "+document.get("price"));
            rows.add(document);
        }
        return rows;
    }

    @Override
    public List<JsonObject> customQuery(String query) {
        return null;
    }

}
