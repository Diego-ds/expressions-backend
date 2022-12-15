package com.perficient.expressions.repositories.interfaces;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;


public interface IRow {
    
    public FindIterable<Document> findAll();

    public FindIterable<Document> customQuery(Bson filter);

}
