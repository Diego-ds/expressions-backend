package com.perficient.expressions.UnitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Nested;
import org.springframework.boot.test.context.SpringBootTest;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.perficient.expressions.repositories.interfaces.IRow;
import com.perficient.expressions.services.interfaces.IRowService;

@SpringBootTest
public class RowServiceTest {

    @Autowired
    IRowService service;

    @Mock
    IRow repo;
    
    public static final int ALL_VALUES = 9;
    
    public ArrayList<Document> getQueryList(String rule) {
    	
    	FindIterable<Document> queryResult = service.applyQuery(rule);

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Document> result = new ArrayList<>();

        queryResult.map(d -> mapper.convertValue(d, Document.class)).into(result);
        return result;
	}
    
    @Test
    void nullRuleTest() {
    	
    	FindIterable<Document> findIterable = (FindIterable<Document>) 
    			Mockito.mock(FindIterable.class);
    	when(repo.customQuery(null)).thenReturn(findIterable);
    	Assertions.assertThrows(IllegalArgumentException.class, () -> service.applyQuery(""));
    }
    
    @Test
    void stringRuleTest() {
    	
    	FindIterable<Document> findIterable = (FindIterable<Document>) 
    			Mockito.mock(FindIterable.class);
    	when(repo.customQuery(null)).thenReturn(findIterable);
    	Assertions.assertThrows(IllegalArgumentException.class, () -> service.applyQuery(""));
    }
}
