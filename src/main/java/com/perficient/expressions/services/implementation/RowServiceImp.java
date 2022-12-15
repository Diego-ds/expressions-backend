package com.perficient.expressions.services.implementation;


import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.client.FindIterable;
import com.perficient.expressions.repositories.interfaces.IRow;
import com.perficient.expressions.services.interfaces.IRowService;

import lombok.val;

public class RowServiceImp implements IRowService {

    @Autowired
    IRow rowRepository;

    @Override
    public FindIterable<Document>  applyQuery(String rule) { 
        // nombre = diego
        // nombre = #diego
        return rowRepository.customQuery(null);
    }

    private Bson createNumericFilter(){
        return null;
    }

    private Bson createStringFilter(){
        return null;
    }

    private Bson createBooleanFilter(){
        return null;
    }

    @Override
    public FindIterable<Document> findAll() {
        return rowRepository.findAll();
    }
    
}
