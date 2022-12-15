package com.perficient.expressions.repositories.implementation;

import java.util.List;

import org.bson.Document;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.perficient.expressions.repositories.interfaces.IRow;

@Repository
public class RowImp implements IRow{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public FindIterable<Document> findAll() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test");
        return collection.find();
    }

    @Override
    public List<JsonObject> customQuery(String query) {
        return null;
    }

}
