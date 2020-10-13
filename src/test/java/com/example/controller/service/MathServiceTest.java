package com.example.controller.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.service.MathService;

@SpringBootTest
class MathServiceTest {

	@Autowired
	private MathService mathService;

	@Test
	void test() {
		
		 assertEquals("5,4", mathService.getResult("sqrt(4)*2+1.4", 1));
        
	}

}
