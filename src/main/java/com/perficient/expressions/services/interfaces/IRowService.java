package com.perficient.expressions.services.interfaces;


import java.util.ArrayList;

import org.bson.Document;
import com.mongodb.client.FindIterable;

public interface IRowService {
    public FindIterable<Document> applyQuery(String rule) throws IllegalArgumentException;
    public FindIterable<Document> findAll();
    public Document getColumnNames();
    public ArrayList<Document> IterableToList(FindIterable<Document> documents);
}
