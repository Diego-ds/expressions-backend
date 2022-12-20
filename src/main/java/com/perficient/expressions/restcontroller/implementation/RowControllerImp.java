package com.perficient.expressions.restcontroller.implementation;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<List<Document>> findAll() {
    	FindIterable<Document> queryResult = rowService.findAll();
    	ArrayList<Document> list = rowService.IterableToList(queryResult);
        return ResponseEntity.ok().body(list);
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<List<Document>> applyQuery(RuleQuery query) {
    	
    	System.out.println(query.getRule().length());
        ArrayList<Document> responseList = null;

        try{
            FindIterable<Document> response = rowService.applyQuery(query.getRule());
            responseList = rowService.IterableToList(response);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok().body(responseList);
    }

    @Override
    @GetMapping("/names/")
    public ResponseEntity<Document> getColumnNames() {
        Document queryResult = rowService.getColumnNames();
        return ResponseEntity.ok().body(queryResult);
    }
    
}
