package com.perficient.expressions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.perficient.expressions.repositories.interfaces.IRow;
import com.perficient.expressions.services.implementation.RowServiceImp;

@SpringBootTest
public class RowRepositoryTest {

    @Autowired
    IRow repo;

    @Test
    void findAllTest() {
        FindIterable<Document> result = repo.findAll();
        for (Document document : result) {
            System.out.println("item " + document.get("item"));
            System.out.println("price " + document.get("price"));
            System.out.println("quantity " + document.get("quantity"));
        }
        assertNotNull(result);
    }

    @Test
    void customQueryTest() {
        Bson filter = Filters.and(Filters.eq("item", "abc"), Filters.gte("quantity", 10));

        FindIterable<Document> result = repo.customQuery(filter);

        for (Document document : result) {
            System.out.println("item " + document.get("item"));
            System.out.println("price " + document.get("price"));
            System.out.println("quantity " + document.get("quantity"));
        }
        assertNotNull(result);
    }

    @Test
    void customQueryTest2() {
        Bson filter = Filters.and(Filters.or(Filters.gt("qty", 8), Filters.eq("color", "pink")),
                Filters.lt(null, null));
        FindIterable<Document> result = repo.customQuery(filter);

        for (Document document : result) {
            System.out.println("item " + document.get("item"));
            System.out.println("price " + document.get("price"));
            System.out.println("quantity " + document.get("quantity"));
        }
        assertNotNull(result);
    }

    @Test
    void customQueryTest3() {
        Bson filter = Filters.where("this.quantity < this.price");

        FindIterable<Document> result = repo.customQuery(filter);

        for (Document document : result) {
            System.out.println("item " + document.get("item"));
            System.out.println("price " + document.get("price"));
            System.out.println("quantity " + document.get("quantity"));
        }
        assertNotNull(result);
    }

    @Test
    void customQueryTest4() {
        RowServiceImp service = new RowServiceImp();
        //String rule = "(Nombre = $Ciudad and (Nombre != marcela or Cantidad > 12)) or Cantidad < $3";
        String rule = "Cantidad = patata and (almuerzo < 20 or (muerto = false and mono = $fruta))";
        
        /*
         * And Filter{filters=[Or Filter{filters=[And Filter{filters=[Filter{fieldName='Cantidad', value=patata}, 
         * Operator Filter{fieldName='almuerzo', operator='$lt', value=20.0}]}, 
         * Filter{fieldName='muerto', value=false)}]}, {"$where": "this.mono = this.fruta"}]}
         */

        service.applyQuery(rule);
    }
}
