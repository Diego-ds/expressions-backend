package com.perficient.expressions.repositories.interfaces;

import java.util.List;

import org.bson.json.JsonObject;


public interface IRow {
    
    public List<JsonObject> findAll();

    public List<JsonObject> customQuery(String query);

}
