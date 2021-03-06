package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.MathService;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/api/v1/math")
@CrossOrigin
public class MathController {

	@Autowired
	private MathService mathService;

	@PostMapping("/")
	public ResponseEntity<?> resultByPost(
			@RequestBody ObjectNode body) {

		Integer precision = null;
		String expression = null;

		if (body.hasNonNull("precision") && body.get("precision").isInt())
			precision = body.get("precision").asInt();

		if (body.hasNonNull("expression") && body.get("expression").isTextual()) {

			expression = body.get("expression").asText();

			String result = mathService.getResult(expression, precision);
			return this.validateResult(result);

		} else {
			return new ResponseEntity<>("expression is requerid", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/")
	public ResponseEntity<?> resultByGet(
			@RequestParam(required = true) String expression,
			@RequestParam(required = false) Integer precision) {

		String result = mathService.getResult(filter(expression), precision);

		return this.validateResult(result);

	}

	// validate result
	private ResponseEntity<?> validateResult(String result) {

		if (result.indexOf("Something went swrong") == -1) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

	}

	// Add symbol + to the math expression
	// Only use if URL is not encoded
	private String filter(String expression) {

		StringBuilder newExpression = new StringBuilder();
		newExpression.append(expression);

		for (int i = 0; i < newExpression.length(); i++) {

			if (newExpression.charAt(i) >= 48 && newExpression.charAt(i) <= 57 || newExpression.charAt(i) == 40) {
				if (i > 0 && newExpression.charAt(i - 1) == 32) {
					newExpression.setCharAt(i - 1, '+');
				}
			}
		}
		return newExpression.toString();
	}

}
