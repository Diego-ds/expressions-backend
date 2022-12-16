package com.perficient.expressions.services.interfaces;


import org.bson.Document;
import com.mongodb.client.FindIterable;

public interface IRowService {
    public FindIterable<Document> applyQuery(String rule);
    public FindIterable<Document> findAll();
}
