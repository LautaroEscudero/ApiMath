package com.example.service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.springframework.stereotype.Service;

@Service
public class MathService {

	private ScriptEngineManager scriptEngineManager;
	private ScriptEngine scriptEngine;

	public MathService() {
		scriptEngineManager = new ScriptEngineManager();
		scriptEngine = scriptEngineManager.getEngineByName("Nashorn");
	}

	// Resolve math expression
	public String getResult(String expression, Integer precision) {

		try {

			Object expResult = scriptEngine.eval(this.validate(expression));

			// Validate range precision and not null
			if (precision != null && precision >= 0 && precision <= 30) {
				expResult = String.format("%." + precision + "f", Double.parseDouble(expResult.toString()));
			}

			return expResult.toString();

		} catch (Exception e) {
			// TODO: handle exception
			return "Something went swrong " + e.getMessage();

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
