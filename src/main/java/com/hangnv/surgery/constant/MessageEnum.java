package com.hangnv.surgery.constant;

public enum MessageEnum {
	NOT_FOUND("404 Not found."),
	INTERNAL_SERVER_ERROR("500 Internal Server Error.");
	
	public String message;
	
	private MessageEnum(String message) {
		this.message = message;
	}
}
