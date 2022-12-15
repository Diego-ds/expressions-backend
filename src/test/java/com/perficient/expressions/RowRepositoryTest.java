package com.perficient.expressions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.perficient.expressions.repositories.interfaces.IRow;

@SpringBootTest
public class RowRepositoryTest {

    @Autowired
    IRow repo;

    @Test
	void contextLoads() {
		assertNotNull(repo.findAll());
	}
}
