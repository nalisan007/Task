package io.example.Wellness360.exception;

public class TaskException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	String message;

	public TaskException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
