package com.perficient.expressions.repositories.interfaces;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.perficient.expressions.model.RuleRequest;

@Repository
public interface ITest extends MongoRepository<RuleRequest,String>{
    
    List<RuleRequest> findAll();

}
