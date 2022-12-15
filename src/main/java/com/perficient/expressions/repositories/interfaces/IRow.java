package com.perficient.expressions.repositories.interfaces;

import java.util.List;

import org.bson.Document;
import org.bson.json.JsonObject;

import com.mongodb.client.FindIterable;


public interface IRow {
    
    public FindIterable<Document> findAll();

    public List<JsonObject> customQuery(String query);

}
