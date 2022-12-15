package com.perficient.expressions.repositories.interfaces;

import java.util.List;

import org.bson.Document;
import org.bson.json.JsonObject;


public interface IRow {
    
    public List<Document> findAll();

    public List<JsonObject> customQuery(String query);

}
