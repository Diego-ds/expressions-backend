package com.perficient.expressions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExpressionsApplicationTests {

	@Test
	void contextLoads() {
		boolean result = true;
		assertTrue(result);
	}

	@Test
	void test1(){
		assertTrue(true);
	}


	@Test
	void testXYZ(){
		assertFalse(false);
	}

}
