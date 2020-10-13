package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@SpringBootTest
class MathControllerTest {

	@Autowired
	private MathController mathController;
	

	@Test
	void testResultByGet() {

		assertEquals(new ResponseEntity<>("50,0", HttpStatus.OK), mathController.resultByGet("10+20*sqrt(4)", 1));

	}

	@Test
	void testResultByPost() {

		final ObjectMapper mapper = new ObjectMapper();
		final ObjectNode body = mapper.createObjectNode();
		body.set("expression", mapper.convertValue("10+20*3", JsonNode.class));

		assertEquals(new ResponseEntity<>("70", HttpStatus.OK), mathController.resultByPost(body));

	}

}
