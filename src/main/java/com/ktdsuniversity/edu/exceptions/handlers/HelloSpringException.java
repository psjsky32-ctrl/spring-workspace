package com.ktdsuniversity.edu.exceptions.handlers;

public class HelloSpringException extends RuntimeException{

	private static final long serialVersionUID = 5344517791626726005L;

	/**
	 * 예외가 발생했을 때, 사용자에게 보여주고 싶은 페이지(템플릿/뷰)의 이름
	 */
	private String errorPage;
	
	
	/**
	 * 사용자에게 보여주고 싶은 페이지(템플릿/뷰)에 보내줄 모델 데이터.
	 */
	private Object object;
	
	public HelloSpringException(String message, String errorPage) {
		super(message);
		this.errorPage = errorPage;
	}
	
	public HelloSpringException(String message, String errorPage, Object object) {
		super(message);
		this.errorPage = errorPage;
		this.object = object;
	}
	
	public String getErrorPage() {
		return this.errorPage;
	}

	public Object getObject() {
		return this.object;
	}
	
}
