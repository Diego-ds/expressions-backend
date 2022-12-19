package com.perficient.expressions.IntegrationTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
    
    public static final int ALL_VALUES = 9;
    
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
                Assertions.assertEquals(result.get(0).get("_id"), "3");
                Assertions.assertEquals(result.get(1).get("_id"), "4");
            }
            
            @DisplayName("Less than X Query - All values")
            @Test
            void numericLessThanXQueryTest2() {

                ArrayList<Document> result = getQueryList("price < 21");

                Assertions.assertEquals(result.size(), ALL_VALUES);
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
                Assertions.assertEquals(result.get(0).get("_id"), "2");
            }
            
            @DisplayName("More than X Query - All values")
            @Test
            void numericMoreThanXQueryTest2() {

                ArrayList<Document> result = getQueryList("price > 1");

                Assertions.assertEquals(result.size(), ALL_VALUES);
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

                Assertions.assertEquals(1,result.size());
                Assertions.assertEquals( "1",result.get(0).get("_id"));
            }
            
            @DisplayName("Equals than X Query - More values")
            @Test
            void numericEqualsThanXQueryTest2() {

                ArrayList<Document> result = getQueryList("quantity = 10");

                Assertions.assertEquals(result.size(), 3);
                Assertions.assertEquals(result.get(0).get("_id"), "3");
                Assertions.assertEquals(result.get(1).get("_id"), "5");
                Assertions.assertEquals(result.get(2).get("_id"), "7");
            }
            
            @DisplayName("Equals than X Query - No values")
            @Test
            void numericEqualsThanXQueryTest3() {

                ArrayList<Document> result = getQueryList("quantity = 15");

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
                Assertions.assertEquals(result.get(0).get("_id"), "3");
                Assertions.assertEquals(result.get(1).get("_id"), "4");
                Assertions.assertEquals(result.get(2).get("_id"), "7");
            }
            
            @DisplayName("Less than Column Query - More values")
            @Test
            void numericLessThanColumnQueryTest2() {

                ArrayList<Document> result = getQueryList("quantity < $price");

                Assertions.assertEquals(5, result.size());
                Assertions.assertEquals("1", result.get(0).get("_id"));
                Assertions.assertEquals("2", result.get(1).get("_id"));
                Assertions.assertEquals("6", result.get(2).get("_id"));
                Assertions.assertEquals("8", result.get(3).get("_id"));
                Assertions.assertEquals("9", result.get(4).get("_id"));
            }
        }
    
        @Nested
        class MoreThanColumn {
        	
        	@DisplayName("More than Column Query - Few values")
            @Test
            void numericMoreThanColumnQueryTest1() {

                ArrayList<Document> result = getQueryList("quantity > $price");

                Assertions.assertEquals(result.size(), 3);
                Assertions.assertEquals(result.get(0).get("_id"), "3");
                Assertions.assertEquals(result.get(1).get("_id"), "4");
                Assertions.assertEquals(result.get(2).get("_id"), "7");
            }
            
            @DisplayName("More than Column Query - All values")
            @Test
            void numericMoreThanColumnQueryTest2() {

                ArrayList<Document> result = getQueryList("price > $quantity");

                Assertions.assertEquals(result.size(), 5);
                Assertions.assertEquals(result.get(0).get("_id"), "1");
                Assertions.assertEquals(result.get(1).get("_id"), "2");
                Assertions.assertEquals(result.get(2).get("_id"), "6");
                Assertions.assertEquals(result.get(3).get("_id"), "8");
                Assertions.assertEquals(result.get(4).get("_id"), "9");
            }
        }
   
        @Nested
        class EqualsThanColumn {
        	
        	@DisplayName("Equals than Column Query - One value")
            @Test
            void numericEqualsThanColumnQueryTest1() {

                ArrayList<Document> result = getQueryList("quantity == $price");

                Assertions.assertEquals(1, result.size());
                Assertions.assertEquals("5", result.get(0).get("_id"));           
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
                Assertions.assertEquals(result.get(0).get("_id"), "2");
            }
            
            @DisplayName("Equals than X Query - More values")
            @Test
            void textEqualsThanXQueryTest2() {

                ArrayList<Document> result = getQueryList("item = abc");

                Assertions.assertEquals(result.size(), 3);
                Assertions.assertEquals(result.get(0).get("_id"), "1");
                Assertions.assertEquals(result.get(1).get("_id"), "5");
                Assertions.assertEquals(result.get(2).get("_id"), "8");
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
        	
        	@DisplayName("Not Equals than X Query - More Values")
            @Test
            void textNotEqualsThanXQueryTest1() {

                ArrayList<Document> result = getQueryList("type != material");

                Assertions.assertEquals(6, result.size());
            }
            
            @DisplayName("Not Equals than X Query - All values")
            @Test
            void textNotEqualsThanXQueryTest2() {

                ArrayList<Document> result = getQueryList("item != nml");

                Assertions.assertEquals(result.size(), ALL_VALUES);
            }
        }
        
        @Nested
        class EqualsThanColumn {
        	
        	@DisplayName("Equals than Column Query - One Value")
            @Test
            void textEqualsThanColumnQueryTest1() {
        		ArrayList<Document> result = getQueryList("type == $kind");

                Assertions.assertEquals(2, result.size());
                Assertions.assertEquals(result.get(0).get("_id"), "2");
                Assertions.assertEquals(result.get(1).get("_id"), "9");
            }
        	
        	@DisplayName("Equals than Column Query - No Values")
            @Test
            void textEqualsThanColumnQueryTest2() {
        		ArrayList<Document> result = getQueryList("item == $kind");

                Assertions.assertEquals(0, result.size());
            }
        }
        
        @Nested
        class NotEqualsThanColumn {
        	
        	@DisplayName("Not Equals than Column Query - All values")
            @Test
            void textNotEqualsThanColumnQueryTest1() {
        		ArrayList<Document> result = getQueryList("kind != $item");

                Assertions.assertEquals(result.size(), ALL_VALUES);
            }
        	
        	@DisplayName("Not Equals than Column Query - More values")
            @Test
            void textNotEqualsThanColumnQueryTest2() {
        		ArrayList<Document> result = getQueryList("type != $kind");

                Assertions.assertEquals(result.size(), 7);
            }	
        }
    }
    
    @Nested
    class booleanQueries {
    	
    	@Nested
    	class IsTrue{
    		
    		@DisplayName("Is true Query 1")
            @Test
            void booleanTrueQueryTest1() {

                ArrayList<Document> result = getQueryList("perishable = true");

                Assertions.assertEquals(4, result.size());
                Assertions.assertEquals("3", result.get(0).get("_id"));
                Assertions.assertEquals("4", result.get(1).get("_id"));
                Assertions.assertEquals("5", result.get(2).get("_id"));
                Assertions.assertEquals("9", result.get(3).get("_id"));
            }
    		
    		@DisplayName("Is true Query 2")
            @Test
            void booleanTrueQueryTest2() {

                ArrayList<Document> result = getQueryList("natural = true");

                Assertions.assertEquals(5, result.size());
                Assertions.assertEquals("1", result.get(0).get("_id"));
                Assertions.assertEquals("3", result.get(1).get("_id"));
                Assertions.assertEquals("5", result.get(2).get("_id"));
                Assertions.assertEquals("8", result.get(3).get("_id"));
                Assertions.assertEquals("9", result.get(4).get("_id"));
            }
    	}
    	
    	@Nested
    	class IsFalse{
    		
    		@DisplayName("Is false Query 1")
            @Test
            void booleanFalseQueryTest1() {

                ArrayList<Document> result = getQueryList("perishable = false");

                Assertions.assertEquals(5, result.size());
                Assertions.assertEquals("1", result.get(0).get("_id"));
                Assertions.assertEquals("2", result.get(1).get("_id"));
                Assertions.assertEquals("6", result.get(2).get("_id"));
                Assertions.assertEquals("7", result.get(3).get("_id"));
                Assertions.assertEquals("8", result.get(4).get("_id"));
            }
    		
    		@DisplayName("Is false Query 2")
            @Test
            void booleanFalseQueryTest2() {

                ArrayList<Document> result = getQueryList("natural = false");

                Assertions.assertEquals(4, result.size());
                Assertions.assertEquals("2", result.get(0).get("_id"));
                Assertions.assertEquals("4", result.get(1).get("_id"));
                Assertions.assertEquals("6", result.get(2).get("_id"));
                Assertions.assertEquals("7", result.get(3).get("_id"));
            }
    	}
    }
}
