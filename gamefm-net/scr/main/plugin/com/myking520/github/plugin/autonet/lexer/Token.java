package com.myking520.github.plugin.autonet.lexer;
public class Token {
	public final int tag;
	public Token(int t) {
		tag = t;
	}
	public String toString() {
		return "" + (char) tag;
	}
}
