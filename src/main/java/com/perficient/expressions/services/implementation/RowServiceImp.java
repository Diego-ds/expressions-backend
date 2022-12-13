package com.perficient.expressions.services.implementation;

import java.util.List;

import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.perficient.expressions.repositories.interfaces.IRow;
import com.perficient.expressions.services.interfaces.IRowService;

public class RowServiceImp implements IRowService {

    @Autowired
    IRow rowRepository;

    @Override
    public List<JsonObject> applyQuery(String rule) { 
        //to-do realizar manejo de la regla
        return rowRepository.customQuery(rule);
    }

    @Override
    public List<JsonObject> findAll() {
        return rowRepository.findAll();
    }
    
}
