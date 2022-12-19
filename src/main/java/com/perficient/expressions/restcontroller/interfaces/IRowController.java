package com.perficient.expressions.restcontroller.interfaces;

import java.util.List;

import org.bson.json.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.mongodb.client.FindIterable;
import com.perficient.expressions.model.RuleQuery;
import org.bson.Document;

public interface IRowController {
    public ResponseEntity<FindIterable<Document>> findAll();
    //to-do crear objeto custom para agregar reglas 
    public ResponseEntity<FindIterable<Document>> applyQuery(@RequestBody RuleQuery query);
}
