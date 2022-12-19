package com.perficient.expressions.UnitTests;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Nested;
import org.springframework.boot.test.context.SpringBootTest;
import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;
import com.perficient.expressions.repositories.interfaces.IRow;
import com.perficient.expressions.services.implementation.RowServiceImp;
import com.perficient.expressions.services.interfaces.IRowService;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class RowServiceTest {

    @Autowired
    IRowService service;

    @Mock
    IRow repo;
    
    @BeforeEach
    void setup() {
    	service = new RowServiceImp(repo);
    }
    
    @Test
    void nullRuleTest() {
    	
    	when(repo.customQuery(null)).thenReturn(null);
    	Assertions.assertThrows(IllegalArgumentException.class, () -> service.applyQuery(""));
    }
    
    @Nested
    class TextQueries {
    	
    	@DisplayName("Equals than X Query")
    	@Test
        void textEqualThanXQueryTest() {
        	
        	Bson filter = Filters.eq("item", "def");
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("item = def");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("Not equals than X Query")
    	@Test
        void textNotEqualsThanXQueryTest() {
        	
        	Bson filter = Filters.ne("type", "food");
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("type != food");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("Equals than Column Query")
    	@Test
        void textEqualsThanColumnQueryTest() {

        	Bson filter = Filters.where("this.kind == this.type");
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("kind == $type");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("Not Equals than Column Query")
    	@Test
        void textNotEqualsThanColumnQueryTest() {
 
        	Bson filter = Filters.where("this.kind != this.type");
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("kind != $type");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    }
    
    @Nested
    class NumericQueries {
    	
    	@DisplayName("Less than X Query")
    	@Test
        void numericLessThanXQueryTest() {

        	Bson filter = Filters.lt("quantity", 5.0);
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("quantity < 5");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("More than X Query")
    	@Test
        void numericMoreThanXQueryTest() {

        	Bson filter = Filters.gt("quantity", 5.0);
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("quantity > 5");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("Equals than X Query")
    	@Test
        void numericEqualsThanXQueryTest() {

        	Bson filter = Filters.eq("quantity", 5.0);
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("quantity = 5");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("Less than Column Query")
    	@Test
        void numericLessThanColumnQueryTest() {

        	Bson filter = Filters.where("this.quantity < this.price");
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("quantity < $price");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("Less than Column Query")
    	@Test
        void numericMoreThanColumnQueryTest() {

        	Bson filter = Filters.where("this.quantity > this.price");
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("quantity > $price");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("Less than Column Query")
    	@Test
        void numericEqualsThanColumnQueryTest() {

        	Bson filter = Filters.where("this.quantity == this.price");
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("quantity == $price");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    }
    
    @Nested
    class BooleanQueries {

    	
    	@DisplayName("Is true Query")
    	@Test
        void booleanIsTrueQueryTest() {

        	Bson filter = Filters.eq("natural", true);
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("natural = true");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("Is false Query")
    	@Test
        void booleanIsFalseQueryTest() {

        	Bson filter = Filters.eq("perishable", false);
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("perishable = false");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("Equals than Column Query")
    	@Test
        void booleanEqualsThanColumnQueryTest() {

        	Bson filter = Filters.where("this.natural = this.perishable");
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("natural = $perishable");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("Not Equals than Column Query")
    	@Test
        void booleanNotEqualsThanColumnQueryTest() {

        	Bson filter = Filters.where("this.perishable != this.natural");
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("perishable != $natural");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	

    }

    @Nested
    class multipleExpression {
    	
    	@DisplayName("2 'And' Expression Query")
    	@Test
        void twoAndExpressionQueryTest() {

        	Bson filter = Filters.and(Filters.lt("quantity",5.0),
        			Filters.where("this.perishable = this.natural"));
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("quantity < 5 and perishable = $natural");
        	
        	verify(repo, times(1)).customQuery(filter);
        }

    	@DisplayName("2 'Or' Expression Query")
    	@Test
        void twoOrExpressionQueryTest() {

        	Bson filter = Filters.or(Filters.eq("kind","food"),
        			Filters.ne("type","meat"));
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("kind = food or type != meat");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("3 Expression Query")
    	@Test
        void threeExpressionQueryTest() {

        	Bson filter = Filters.or(
        			Filters.and(
        			Filters.eq("natural",false),
        			Filters.gt("price",10.0)),
        			Filters.where("this.natural != this.perishable"));
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("natural = false and price > 10 or "
        			+ "natural != $perishable");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("4 Expression Query")
    	@Test
        void fourExpressionQueryTest() {
        	
        	Bson filter = Filters.and(
        			Filters.and(
        					Filters.or(
        							Filters.where("this.price < this.quantity"),
        							Filters.lt("price",25.0)),
        					Filters.eq("perishable",true)),
        			Filters.eq("natural",true));
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("price < $quantity or price < 25 and "
        			+ "perishable = true and natural = true");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    }
    
    @Nested
    class Brackets {
    	
    	@DisplayName("Left brackets Query")
    	@Test
        void leftBracketsQueryTest() {
        	
        	Bson filter = Filters.and(
        			Filters.and(
        					Filters.or(
        							Filters.where("this.quantity < this.price"),
        							Filters.lt("price",25.0)),
        					Filters.eq("perishable",true)),
        			Filters.eq("natural",true));
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("(quantity < $price or price < 25) and "
        			+ "perishable = true and natural = true");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("Central brackets Query")
    	@Test
        void centralBracketsQueryTest() {
        	
    		Bson filter = Filters.and(
    				Filters.or(Filters.where("this.quantity < this.price"),
    						Filters.and(
    								Filters.gt("price",25.0),
    								Filters.eq("perishable",true))
    						),Filters.eq("natural",true));

        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("quantity < $price or (price > 25 and "
        			+ "perishable = true) and natural = true");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("Right brackets Query")
    	@Test
        void rightBracketsQueryTest() {
        	
    		Bson filter = Filters.and(
    				Filters.or(Filters.where("this.quantity < this.price"),
    						Filters.eq("price",25.0)),
    						Filters.and(
    								Filters.eq("perishable",true)
    						,Filters.eq("natural",true)));

        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("quantity < $price or price = 25 and "
        			+ "(perishable = true and natural = true)");
        	
        	verify(repo, times(1)).customQuery(filter);
        }
    	
    	@DisplayName("Double brackets Query")
    	@Test
        void doubleBracketsQueryTest() {
        	
    		Bson filter = Filters.and(
    				Filters.or(Filters.where("this.quantity < this.price"),
    						Filters.eq("price",25.0)),
    						Filters.and(
    								Filters.eq("perishable",true)
    						,Filters.eq("natural",true)));
        	
        	when(repo.customQuery(filter)).thenReturn(null);
        	service.applyQuery("(quantity < $price or price > 25) and "
        			+ "(perishable = true and natural = true)");
        	
        	verify(repo, times(1)).customQuery(filter);
        }

    	@DisplayName("Double Inside right brackets Query")
    	@Test
    	void doubleInsideBracketsQueryTest() {

    		Bson filter = Filters.or(
    				Filters.where("this.quantity < this.price"),
    				Filters.and(
    						Filters.and(
    								Filters.gt("price",25.0),
    								Filters.eq("perishable",true)),
    						Filters.eq("natural",false)));

    		when(repo.customQuery(filter)).thenReturn(null);
    		service.applyQuery("quantity < $price or (price > 25 and "
    				+ "(perishable = true and natural = false))");

    		verify(repo, times(1)).customQuery(filter);
    	}
    }
}
