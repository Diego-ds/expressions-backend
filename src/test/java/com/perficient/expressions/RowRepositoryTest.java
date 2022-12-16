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
	void findAllTest() {
        FindIterable<Document> result = repo.findAll();
        for (Document document : result) {
            System.out.println("item "+document.get("item"));
            System.out.println("price "+document.get("price"));
            System.out.println("quantity "+document.get("quantity"));
        }
		assertNotNull(result);
	}

    @Test
    void customQueryTest() {
        Bson filter = Filters.and(Filters.eq("item", "abc"), Filters.gte("quantity", 10));

        FindIterable<Document> result = repo.customQuery(filter);

        for(Document document : result) {
            System.out.println("item "+document.get("item"));
            System.out.println("price "+document.get("price"));
            System.out.println("quantity "+document.get("quantity"));
        }
        assertNotNull(result);
    }

    @Test
    void customQueryTest2() {
        Bson filter = Filters.and(Filters.or(Filters.gt("qty", 8), Filters.eq("color", "pink")),Filters.lt(null, null));
        FindIterable<Document> result = repo.customQuery(filter);

        for(Document document : result) {
            System.out.println("item "+document.get("item"));
            System.out.println("price "+document.get("price"));
            System.out.println("quantity "+document.get("quantity"));
        }
        assertNotNull(result);
    }

    @Test
    void customQueryTest3() {
        Bson filter = Filters.where("this.quantity < this.price");

        FindIterable<Document> result = repo.customQuery(filter);

        for(Document document : result) {
            System.out.println("item "+document.get("item"));
            System.out.println("price "+document.get("price"));
            System.out.println("quantity "+document.get("quantity"));
        }
        assertNotNull(result);
    }
}
