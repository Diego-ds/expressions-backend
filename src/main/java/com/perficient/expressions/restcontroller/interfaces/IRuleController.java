package com.perficient.expressions.restcontroller.interfaces;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import com.perficient.expressions.model.Rule;

public interface IRuleController {

	public ResponseEntity<List<Rule>> findAll();
    public ResponseEntity<Boolean> delete(ObjectId id);
    public ResponseEntity<Boolean> create(Rule rule);
}