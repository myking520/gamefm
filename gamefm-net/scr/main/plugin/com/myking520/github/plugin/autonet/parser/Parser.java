package com.myking520.github.plugin.autonet.parser;

import java.io.IOException;

import com.myking520.github.plugin.autonet.expr.Node;
import com.myking520.github.plugin.autonet.expr.RequestMethod;
import com.myking520.github.plugin.autonet.lexer.Lexer;
import com.myking520.github.plugin.autonet.lexer.Tag;
import com.myking520.github.plugin.autonet.lexer.Token;

public class Parser {
	private Lexer lex;
	private Token look;

	public Parser(Lexer l) throws IOException {
		lex = l;
	}

	public void prog(String str) {
		lex.read(str);
		look = lex.scan();
		switch (look.tag) {
		case Tag.REQUEST:
			this.pRequest();
			break;
		case Tag.RESPONSE:

			break;
		case Tag.INCLUDE:

			break;
		case Tag.PACKAGE:

			break;
		case Tag.IMPORT:

			break;
		}
	}

	private void pImport() {

	}

	private void pRequest() {
		this.look = lex.scan();
		this.match(Tag.LEFTBRACE);
		this.match(Tag.PUBLIC);
		RequestMethod request = new RequestMethod();
		if (this.look == null) {
			throw new RuntimeException("语法错误！第" + lex.getLine() + "行 需要\"static | void \"");
		}
		if (this.look.tag == Tag.STATIC) {
			this.match(Tag.STATIC);
			request.setStatic();
		}
		this.match(Tag.VOID);
		this.match(Tag.ID);
		this.match('(');
		this.methodParam();
		this.match(')');
		this.match(';');
	}

	private Node methodParam() {
		switch (look.tag) {
		case Tag.ID:
			this.match(Tag.ID);
			if (this.look.tag == '<') {
				this.generic();
			}
			this.match(Tag.ID);
			break;
		case Tag.INT:
			this.match(Tag.INT);
			this.match(Tag.ID);
			break;
		}
		if (this.look.tag == ',') {
			this.match(',');
			this.methodParam();
		}
		return null;
	}
	/**
	 * 泛型
	 */
	private void generic() {
		this.match('<');
		this.innerAnnotation();
		this.match('>');
	}

	/**
	 * 泛型
	 */
	private void innerAnnotation() {
		this.match(Tag.ID);
		switch (this.look.tag) {
		case ',':
			this.match(',');
			this.innerAnnotation();
			break;
		case '<':
			this.generic();
			if (this.look.tag == ',') {
				this.match(',');
				this.innerAnnotation();
			}
		default:
			break;
		}
	}
	void match(int t) {
		if (look != null && look.tag == t)
			look = lex.scan();
		else
			throw new RuntimeException("语法错误！第" + lex.getLine() + "行 需要\"" + (char) t + "\"");
	}

	public static void main(String[] args) throws IOException {
		Parser p = new Parser(new Lexer());
		p.prog("request{ public static void aa(int  bb,aacc<aa,bb<aaaa,bb<aaa>>,cc<aaa,aac,aaa>> s);");
	}
}
