package com.perficient.expressions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;


import org.bson.json.JsonObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.perficient.expressions.model.RuleRequest;
import com.perficient.expressions.repositories.interfaces.IRow;
import com.perficient.expressions.repositories.interfaces.ITest;

@SpringBootTest
public class RowImpTest {
    
    @Autowired
    private IRow rowImp;

    @Autowired
    private ITest testImp;


    @Test
    public void test(){
        List<RuleRequest> rows = testImp.findAll();

        System.out.println(rows.size());

        assertTrue(rowImp.findAll().size() > 0);
    }

}
