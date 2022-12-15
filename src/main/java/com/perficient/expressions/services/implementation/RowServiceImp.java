package com.perficient.expressions.services.implementation;


import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.client.FindIterable;
import com.perficient.expressions.repositories.interfaces.IRow;
import com.perficient.expressions.services.interfaces.IRowService;

public class RowServiceImp implements IRowService {

    @Autowired
    IRow rowRepository;

    @Override
    public FindIterable<Document>  applyQuery(String rule) { 
        //to-do realizar manejo de la regla
        return rowRepository.customQuery(null);
    }

    @Override
    public FindIterable<Document> findAll() {
        return rowRepository.findAll();
    }
    
}
