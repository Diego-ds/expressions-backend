package com.perficient.expressions.unitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
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

    @Autowired
    IRow repo;

    public ArrayList<Document> getQueryList(String rule) {
    	
    	FindIterable<Document> queryResult = service.applyQuery(rule);

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Document> result = new ArrayList<>();

        queryResult.map(d -> mapper.convertValue(d, Document.class)).into(result);
        return result;
	}
    
    @Nested
    class NumericQueries {
    	
    	@Nested
    	class LessThanX {
    		
    		@DisplayName("Less than X Query - Few values")
            @Test
            void numericLessThanXQueryTest1() {

                ArrayList<Document> result = getQueryList("price < 6");

                Assertions.assertEquals(result.size(), 2);
                Assertions.assertEquals(result.get(0).get("id"), "3");
                Assertions.assertEquals(result.get(1).get("id"), "4");
            }
            
            @DisplayName("Less than X Query - All values")
            @Test
            void numericLessThanXQueryTest2() {

                ArrayList<Document> result = getQueryList("price < 21");

                Assertions.assertEquals(result.size(), 8);
            }
            
            @DisplayName("Less than X Query - No values")
            @Test
            void numericLessThanXQueryTest3() {

                ArrayList<Document> result = getQueryList("price < 1");

                Assertions.assertEquals(result.size(), 0);
            }
    	}
    	
        @Nested
        class MoreThanX {

        	@DisplayName("More than X Query - Few values")
            @Test
            void numericMoreThanXQueryTest1() {

                ArrayList<Document> result = getQueryList("price > 10");

                Assertions.assertEquals(result.size(), 1);
                Assertions.assertEquals(result.get(0).get("id"), "2");
            }
            
            @DisplayName("More than X Query - All values")
            @Test
            void numericMoreThanXQueryTest2() {

                ArrayList<Document> result = getQueryList("price > 1");

                Assertions.assertEquals(result.size(), 8);
            }
            
            @DisplayName("More than X Query - No values")
            @Test
            void numericMoreThanXQueryTest3() {

                ArrayList<Document> result = getQueryList("price > 100");

                Assertions.assertEquals(result.size(), 0);
            }
        }
    
        @Nested
        class EqualsThanX {
        	
        	@DisplayName("Equals than X Query - Few values")
            @Test
            void numericEqualsThanXQueryTest1() {

                ArrayList<Document> result = getQueryList("quantity = 2");

                Assertions.assertEquals(result.size(), 1);
                Assertions.assertEquals(result.get(0).get("id"), "1");
            }
            
            @DisplayName("Equals than X Query - More values")
            @Test
            void numericEqualsThanXQueryTest2() {

                ArrayList<Document> result = getQueryList("quantity = 10");

                Assertions.assertEquals(result.size(), 3);
                Assertions.assertEquals(result.get(0).get("id"), "3");
                Assertions.assertEquals(result.get(1).get("id"), "5");
                Assertions.assertEquals(result.get(2).get("id"), "7");
            }
            
            @DisplayName("Equals than X Query - No values")
            @Test
            void numericEqualsThanXQueryTest3() {

                ArrayList<Document> result = getQueryList("quantity = 7");

                Assertions.assertEquals(result.size(), 0);
            }
        }
    
        @Nested
        class LessThanColumn {
        	
        	@DisplayName("Less than Column Query - Few values")
            @Test
            void numericLessThanColumnQueryTest1() {

                ArrayList<Document> result = getQueryList("price < $quantity");

                Assertions.assertEquals(result.size(), 3);
                Assertions.assertEquals(result.get(0).get("id"), "3");
                Assertions.assertEquals(result.get(1).get("id"), "4");
                Assertions.assertEquals(result.get(2).get("id"), "7");
            }
            
            @DisplayName("Less than Column Query - More values")
            @Test
            void numericLessThanColumnQueryTest2() {

                ArrayList<Document> result = getQueryList("quantity < $price");

                Assertions.assertEquals(result.size(), 4);
                Assertions.assertEquals(result.get(0).get("id"), "1");
                Assertions.assertEquals(result.get(1).get("id"), "2");
                Assertions.assertEquals(result.get(2).get("id"), "6");
                Assertions.assertEquals(result.get(3).get("id"), "8");
            }
        }
    
        @Nested
        class MoreThanColumn {
        	
        	@DisplayName("More than Column Query - Few values")
            @Test
            void numericMoreThanColumnQueryTest1() {

                ArrayList<Document> result = getQueryList("quantity > $price");

                Assertions.assertEquals(result.size(), 3);
                Assertions.assertEquals(result.get(0).get("id"), "3");
                Assertions.assertEquals(result.get(1).get("id"), "4");
                Assertions.assertEquals(result.get(2).get("id"), "7");
            }
            
            @DisplayName("More than Column Query - All values")
            @Test
            void numericMoreThanColumnQueryTest2() {

                ArrayList<Document> result = getQueryList("price > $quantity");

                Assertions.assertEquals(result.size(), 4);
                Assertions.assertEquals(result.get(0).get("id"), "1");
                Assertions.assertEquals(result.get(1).get("id"), "2");
                Assertions.assertEquals(result.get(2).get("id"), "6");
                Assertions.assertEquals(result.get(3).get("id"), "8");
            }
        }
   
        @Nested
        class EqualsThanColumn {
        	
        	@DisplayName("Equals than Column Query")
            @Test
            void numericEqualsThanColumnQueryTest1() {

                ArrayList<Document> result = getQueryList("quantity = $price");

                Assertions.assertEquals(result.size(), 1);
                Assertions.assertEquals(result.get(0).get("id"), "5");           
            }
        }
    }

    @Nested
    class StringQueries {
    	
        @Nested
        class EqualsThanX {
        	
        	@DisplayName("Equals than X Query - Few values")
            @Test
            void textEqualsThanXQueryTest1() {

                ArrayList<Document> result = getQueryList("item = jkl");

                Assertions.assertEquals(result.size(), 1);
                Assertions.assertEquals(result.get(0).get("id"), "2");
            }
            
            @DisplayName("Equals than X Query - More values")
            @Test
            void textEqualsThanXQueryTest2() {

                ArrayList<Document> result = getQueryList("item = abc");

                Assertions.assertEquals(result.size(), 3);
                Assertions.assertEquals(result.get(0).get("id"), "1");
                Assertions.assertEquals(result.get(1).get("id"), "5");
                Assertions.assertEquals(result.get(2).get("id"), "8");
            }
            
            @DisplayName("Equals than X Query - No values")
            @Test
            void textEqualsThanXQueryTest3() {

                ArrayList<Document> result = getQueryList("item = lmn");

                Assertions.assertEquals(result.size(), 0);
            }
        }

        @Nested
        class NotEqualsThanX {
        	
        	@DisplayName("Not Equals than X Query - Few values")
            @Test
            void textNotEqualsThanXQueryTest1() {

                ArrayList<Document> result = getQueryList("item != abc");

                Assertions.assertEquals(result.size(), 5);
                Assertions.assertEquals(result.get(0).get("id"), "2");
                Assertions.assertEquals(result.get(1).get("id"), "3");
                Assertions.assertEquals(result.get(2).get("id"), "4");
                Assertions.assertEquals(result.get(3).get("id"), "6");
                Assertions.assertEquals(result.get(4).get("id"), "7");
            }
            
            @DisplayName("Not Equals than X Query - All values")
            @Test
            void textNotEqualsThanXQueryTest2() {

                ArrayList<Document> result = getQueryList("item != nml");

                Assertions.assertEquals(result.size(), 8);
            }
        }
        
        @Nested
        class EqualsThanColumn {
        	
        	@DisplayName("Equals than Column Query")
            @Test
            void textEqualsThanColumnQueryTest1() {
        		//TODO valores para probar
            }
        }
        
        @Nested
        class NotEqualsThanColumn {
        	
        	//TODO valores para probar 
        }
    }
}
