package com.perficient.expressions.restcontroller.implementation;

import java.util.List;

import org.bson.json.JsonObject;
import org.springframework.http.ResponseEntity;

import com.perficient.expressions.model.RuleRequest;
import com.perficient.expressions.restcontroller.interfaces.IRowController;

public class RowControllerImp implements IRowController{

    @Override
    public ResponseEntity<List<JsonObject>> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<List<JsonObject>> applyQuery(RuleRequest query) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
