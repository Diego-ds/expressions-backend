package com.perficient.expressions.services.interfaces;

import java.util.List;

import org.bson.Document;
import org.bson.json.JsonObject;

public interface IRowService {
    public List<JsonObject> applyQuery(String rule);

    public List<Document> findAll();
}
