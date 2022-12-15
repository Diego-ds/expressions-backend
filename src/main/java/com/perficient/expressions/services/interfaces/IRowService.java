package com.perficient.expressions.services.interfaces;

import java.util.List;

import org.bson.Document;
import org.bson.json.JsonObject;

import com.mongodb.client.FindIterable;

public interface IRowService {
    public List<JsonObject> applyQuery(String rule);

    public FindIterable<Document> findAll();
}
