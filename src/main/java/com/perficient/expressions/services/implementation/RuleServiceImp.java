package com.perficient.expressions.services.implementation;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.perficient.expressions.repositories.interfaces.IRule;
import com.perficient.expressions.services.interfaces.IRuleService;

@Service
public class RuleServiceImp implements IRuleService {

	@Autowired
	IRule repository;
	
	public RuleServiceImp (IRule repository) {
		
		this.repository = repository;
	}
	
	@Override
	public FindIterable<Document> findAll() {
		
		return repository.findAll();
	}

	@Override
	public ArrayList<Document> IterableToList(FindIterable<Document> documents) {
		
		ObjectMapper mapper = new ObjectMapper();
        ArrayList<Document> list = new ArrayList<>();

        documents.map(d -> mapper.convertValue(d, Document.class)).into(list);
        return list;
	}

	@Override
	public boolean create(String name, String rule) throws IllegalArgumentException {

		if ( rule == null || name == null || rule.isBlank() || name.isBlank()) {

			throw new IllegalArgumentException("Both name and rule cannot"
					+ " be null or empty");
		} else {
			return repository.create(name, rule);
		}
	}

	@Override
	public boolean delete(ObjectId id) {
		
		Document result = repository.delete(id);
		
		if (result == null) {
			return false;
		} 
		
		return true;
	}

}
