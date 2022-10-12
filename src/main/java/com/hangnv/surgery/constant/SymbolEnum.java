package com.hangnv.surgery.constant;

public enum SymbolEnum {
	AT_SIGN("@"),
	AMPERSAND("&"),
	COLON(":"),
	COMMA(","),
	DOT("."),
	EQUALS_SIGN("="),
	HYPHEN("-"),
	QUESTION_MARK("?"),
	SLASH("/"),
	BACKSLASH("\\"),
	HASH("#"),
	SPACE(" "),
	PERCENT("%"),
	UNDERSCORE("_");
	
	public String val;

	private SymbolEnum(String val) {
		this.val = val;
	}
}
