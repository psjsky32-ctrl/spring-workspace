package com.ktdsuniversity.edu.exceptions.handlers;

public class HelloSpringApiException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2438629297538172889L;

	private int errorStatus;
	
	private Object error;
	
	
	public HelloSpringApiException(String message, int errorStatus, Object error) {
		super(message);
		this.errorStatus = errorStatus;
		this.error = error;
	}


	public int getErrorStatus() {
		return this.errorStatus;
	}

	public void setErrorStatus(int errorStatus) {
		this.errorStatus = errorStatus;
	}

	public Object getError() {
		return this.error;
	}

	public void setError(Object error) {
		this.error = error;
	}

	

}
