package com.perficient.expressions.repositories.implementation;

import java.util.List;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.perficient.expressions.repositories.interfaces.IRow;

@Repository
public class RowImp implements IRow{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<JsonObject> findAll() {
        List<JsonObject> rows = mongoTemplate.findAll(JsonObject.class);
        return rows;
    }

    @Override
    public List<JsonObject> customQuery(String query) {
        return null;
    }

}
