package com.perficient.expressions.repositories.implementation;


import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.perficient.expressions.repositories.interfaces.IRow;
import com.mongodb.client.model.Filters;

@Repository
public class RowImp implements IRow{

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public FindIterable<Document> findAll() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test");
        return collection.find();
    }


    @Override
    public FindIterable<Document> customQuery(Bson filter) {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test");

        FindIterable<Document> result = collection.find(filter);
        return result;
    }


    @Override
    public FindIterable<Document> findOne() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("test");
        Bson filter = Filters.eq("_id", "1");
        return collection.find(filter);
    }

}
