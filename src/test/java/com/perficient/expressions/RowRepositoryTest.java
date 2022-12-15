package com.perficient.expressions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.perficient.expressions.repositories.interfaces.IRow;


@SpringBootTest
public class RowRepositoryTest {

    @Autowired
    IRow repo;

    @Test
	void contextLoads() {
        FindIterable<Document> result = repo.findAll();
		assertNotNull(result);
	}

    @Test
    void customQueryTest() {
        Bson filter = Filters.and(Filters.eq("item", "abc"), Filters.gte("quantity", 10));

        FindIterable<Document> result = repo.customQuery(filter);

        for(Document data : result) {
            System.out.println(data.get("item"));
            System.out.println(data.get("price"));
            System.out.println(data.get("quantity"));
            System.out.println();
        }
        assertNotNull(result);
    }
}
