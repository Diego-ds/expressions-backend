package com.perficient.expressions.services.interfaces;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;

public interface IRuleService {
	
	public FindIterable<Document> findAll();
	public ArrayList<Document> IterableToList(FindIterable<Document> documents);
	public boolean create(String name, String rule);
	public boolean delete(ObjectId id);
}
