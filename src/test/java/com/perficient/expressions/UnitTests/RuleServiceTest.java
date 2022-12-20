package com.perficient.expressions.UnitTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.perficient.expressions.repositories.interfaces.IRule;
import com.perficient.expressions.services.implementation.RuleServiceImp;
import com.perficient.expressions.services.interfaces.IRuleService;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class RuleServiceTest {

	@Autowired
	IRuleService service;
	
	@Mock
	IRule repo;
	
	@BeforeEach
	void setup() {
		service = new RuleServiceImp(repo);
	}
	
	@Nested
	class FindAll {
		@Test
		void findAllTest() {
			
			when(repo.findAll()).thenReturn(null);
			service.findAll();
			
			verify(repo, times(1)).findAll();
		}
	}
	
	@Nested
	class Create {
		
		@Test
		void createEmptyValuesTest1() {
			
			Assertions.assertThrows(IllegalArgumentException.class, () -> service.create("query",""));
		}
		
		@Test
		void createEmptyValuesTest2() {
			
			Assertions.assertThrows(IllegalArgumentException.class, () -> service.create("","rule"));
		}
		
		@Test
		void createEmptyValuesTest3() {
			
			Assertions.assertThrows(IllegalArgumentException.class, () -> service.create("",""));
		}
		
		@Test
		void createNullValuesTest1() {
			
			Assertions.assertThrows(IllegalArgumentException.class, () -> service.create("query",null));
		}

		@Test
		void createNullValuesTest2() {
			
			Assertions.assertThrows(IllegalArgumentException.class, () -> service.create(null,"rule"));
		}
		
		@Test
		void createNullValuesTest3() {
			
			Assertions.assertThrows(IllegalArgumentException.class, () -> service.create(null,null));
		}
		
		@Test
		void createTest1() {
			
			when(repo.create("query", "rule")).thenReturn(true);
			service.create("query", "rule");
			
			verify(repo, times(1)).create("query", "rule");
		}
	}
	
	@Nested
	class Delete {
		
		@Test
		void deleteTest1() {
			
			ObjectId id = new ObjectId();
			when(repo.delete(id)).thenReturn(new Document());
			boolean response = service.delete(id);
			
			verify(repo, times(1)).delete(id);
			Assertions.assertTrue(response);
		}
		
		@Test
		void deleteTest2() {
			
			ObjectId id = new ObjectId();
			when(repo.delete(id)).thenReturn(null);
			boolean response = service.delete(id);
			
			verify(repo, times(1)).delete(id);
			Assertions.assertFalse(response);
		}
	}
}
