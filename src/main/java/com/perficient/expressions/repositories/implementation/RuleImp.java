package com.perficient.expressions.repositories.implementation;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.perficient.expressions.repositories.interfaces.IRule;

@Repository
public class RuleImp implements IRule{
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	@Override
    public FindIterable<Document> findAll() {
        MongoCollection<Document> collection = mongoTemplate.getCollection("rules");
        return collection.find();
    }

	@Override
	public Document delete(ObjectId id) {

		MongoCollection<Document> collection = mongoTemplate.getCollection("rules");
		Bson filter = Filters.eq("_id",id);
		Document deleted = collection.findOneAndDelete(filter);
		return deleted;
	}

	@Override
	public boolean create(String name, String rule) {
		MongoCollection<Document> collection = mongoTemplate.getCollection("rules");
		Document doc = new Document("name",name).append("rule", rule);
		InsertOneResult result = collection.insertOne(doc);
		return result.wasAcknowledged();
	}
}
