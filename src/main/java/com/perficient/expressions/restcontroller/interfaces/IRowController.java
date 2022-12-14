package com.perficient.expressions.restcontroller.interfaces;

import java.util.List;

import org.bson.json.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.perficient.expressions.model.RuleRequest;

public interface IRowController {
    public ResponseEntity<List<JsonObject>> findAll();
    //to-do crear objeto custom para agregar reglas 
    public ResponseEntity<List<JsonObject>> applyQuery(@RequestBody RuleRequest query);
}
