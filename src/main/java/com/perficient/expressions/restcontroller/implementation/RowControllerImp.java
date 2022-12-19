package com.perficient.expressions.restcontroller.implementation;

import java.util.List;

import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.FindIterable;
import com.perficient.expressions.model.RuleQuery;
import com.perficient.expressions.restcontroller.interfaces.IRowController;
import com.perficient.expressions.services.interfaces.IRowService;
import org.bson.Document;

@RestController
@RequestMapping("/api/v1/rows")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE, RequestMethod.PUT})
public class RowControllerImp implements IRowController {

    @Autowired
    private IRowService rowService;

    @Override
    @GetMapping("/")
    public ResponseEntity<FindIterable<Document>> findAll() {
        // TODO Auto-generated method stub
        return ResponseEntity.ok().body(rowService.findAll());
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<FindIterable<Document>> applyQuery(RuleQuery query) {
        // TODO Auto-generated method stub
        FindIterable<Document> response = null;

        try{
            response = rowService.applyQuery(query.getRule());
        }catch(IllegalArgumentException e){
            ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().body(response);
    }
    
}
