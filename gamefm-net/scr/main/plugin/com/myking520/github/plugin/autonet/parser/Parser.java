package com.myking520.github.plugin.autonet.parser;

import java.io.IOException;

import com.myking520.github.plugin.autonet.lexer.Lexer;
import com.myking520.github.plugin.autonet.lexer.Token;

public class Parser {
	private Lexer lex;
	private Token look;

	public Parser(Lexer l) throws IOException {
		lex = l;
	}
	public void prog(String str){
	}
}
