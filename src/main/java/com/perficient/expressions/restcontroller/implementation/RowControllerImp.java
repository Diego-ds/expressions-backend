package com.perficient.expressions.restcontroller.implementation;

import java.util.List;

import org.bson.json.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.perficient.expressions.model.RuleQuery;
import com.perficient.expressions.restcontroller.interfaces.IRowController;

@RestController
public class RowControllerImp implements IRowController {

    @Override
    public ResponseEntity<List<JsonObject>> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResponseEntity<List<JsonObject>> applyQuery(RuleQuery query) {
        // TODO Auto-generated method stub
        return null;
    }
    
}