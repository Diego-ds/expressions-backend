package com.perficient.expressions.restcontroller.implementation;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.FindIterable;
import com.perficient.expressions.model.Rule;
import com.perficient.expressions.restcontroller.interfaces.IRuleController;
import com.perficient.expressions.services.interfaces.IRuleService;

@RestController
@RequestMapping("/api/v1/rules")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE, RequestMethod.PUT})
public class RuleControllerImp implements IRuleController {
	
	@Autowired
    private IRuleService ruleService;

    @Override
    @GetMapping("/")
    public ResponseEntity<List<Document>> findAll() {
    	
    	FindIterable<Document> queryResult = ruleService.findAll();
    	ArrayList<Document> list = ruleService.IterableToList(queryResult);
        return ResponseEntity.ok().body(list);
    }

	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable("id") ObjectId id) {
		
		return ResponseEntity.ok().body(ruleService.delete(id));
	}

	@Override
	@PostMapping("/")
	public ResponseEntity<Boolean> create(@RequestBody Rule rule) {
		
		boolean response;
		
        try{
            response = ruleService.create(rule.getName(),rule.getRule());
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok().body(response);
	}
}
