package com.javainuse;

public class RuleExecutorException extends RuntimeException {

	public RuleExecutorException(String message) {
		super(message);
	}

	public RuleExecutorException(String message, Throwable cause) {
		super(message, cause);
	}
}