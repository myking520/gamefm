package com.myking520.github.plugin.autonet.lexer;

public class Word extends Token {
	public static final Word wPublic = new Word("public", Tag.PUBLIC);
	public static final Word wInclude = new Word("include", Tag.INCLUDE);
	public static final Word wRequest = new Word("request", Tag.REQUEST);
	public static final Word wResponse = new Word("response", Tag.RESPONSE);
	public static final Word wVoid = new Word("void", Tag.VOID);
	public static final Word wStatic = new Word("static", Tag.STATIC);
	public String txt = "";

	public Word(String txt, int t) {
		super(t);
		this.txt = txt;
	}

	public String toString() {
		return txt;
	}
}
