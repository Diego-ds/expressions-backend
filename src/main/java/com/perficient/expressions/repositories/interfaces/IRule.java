package com.perficient.expressions.repositories.interfaces;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;

public interface IRule {
	
	public FindIterable<Document> findAll();
	
	public Document delete(ObjectId id);

	public boolean create(String name, String rule);
}
