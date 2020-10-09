package com.example.service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MathService {

	// Resolve math expression
	public ResponseEntity<?> getResult(String expression, Integer precision) {

		try {

			ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
			ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("Nashorn");

			Object expResult = scriptEngine.eval(this.validate(expression));
			
			//Validate range precision and not null
			if(precision != null && precision >= 0 && precision <= 30) {
				
			String result = String.format("%." + precision + "f", Double.parseDouble(expResult.toString()));
			
			return new ResponseEntity<>(result, HttpStatus.OK);
			}
			
			return new ResponseEntity<>(expResult, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("Something went swrong " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	// Validation of Math class operations
	private String validate(String expression) {

		String[] mathOperations = { "sqrt", "pow", "E", "PI", "abs", "cos", "tan(", "atan2", "exp", "log" };

		for (String item : mathOperations) {
			if (expression.indexOf(item) != -1)
				expression = expression.replace(item, "Math." + item);
		}

		if (expression.indexOf("Math.Math.") != -1)
			expression = expression.replace("Math.Math.", "Math.");

		return expression;
	}

}
