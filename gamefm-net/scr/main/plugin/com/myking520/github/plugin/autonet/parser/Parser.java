package com.myking520.github.plugin.autonet.parser;

import java.io.IOException;

import com.myking520.github.plugin.autonet.expr.MethodParam;
import com.myking520.github.plugin.autonet.expr.MethodParamType;
import com.myking520.github.plugin.autonet.expr.Node;
import com.myking520.github.plugin.autonet.expr.RequestBlock;
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
			requestBlock=new RequestBlock();
			this.match(Tag.REQUEST);
			this.match(Tag.LEFTBRACE);
			this.pRequest();
			this.match(Tag.RIGHTBRACE);
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
	RequestBlock requestBlock=null;
	private void pRequest() {
		this.match(Tag.PUBLIC);
		RequestMethod request = new RequestMethod();
		requestBlock.addRequestMethod(request);
		if (this.look == null) {
			throw new RuntimeException("语法错误！第" + lex.getLine() + "行 需要\"static | void \"");
		}
		if (this.look.tag == Tag.STATIC) {
			this.match(Tag.STATIC);
			request.setStatic();
		}
		this.match(Tag.VOID);
		request.setName(look);
		this.match(Tag.ID);
		this.match('(');
		methodParam = new MethodParam();
		request.setMethodParam(methodParam);
		this.methodParam();
		this.match(')');
		this.match(';');
		if (this.look.tag != Tag.RIGHTBRACE)
			this.pRequest();
	}

	MethodParam methodParam = null;

	private Node methodParam() {
		MethodParamType methodParamType = null;
		switch (look.tag) {
		case Tag.ID:
			methodParamType = new MethodParamType();
			methodParamType.setParamType(look);
			this.match(Tag.ID);
			if (this.look.tag == '<') {
				methodParamType.getMethodParams().add(this.generic());
			}
			methodParamType.setParamValue(look);
			this.match(Tag.ID);
			break;
		case Tag.INT:
			methodParamType = new MethodParamType();
			methodParamType.setParamType(look);
			this.match(Tag.INT);
			methodParamType.setParamValue(look);
			this.match(Tag.ID);
			break;
		}
		if (methodParamType != null) {
			methodParam.addMethodParamType(methodParamType);
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
	private MethodParamType generic() {
		this.match('<');
		MethodParamType type=this.innerAnnotation();
		this.match('>');
		return type;
	}

	/**
	 * 泛型
	 */
	private MethodParamType innerAnnotation() {
		MethodParamType methodParamType=new MethodParamType();
		methodParamType.setParamType(look);
		this.match(Tag.ID);
		switch (this.look.tag) {
		case ',':
			this.match(',');
			methodParamType.setNotAnnotation(true);
			methodParamType.getMethodParams().add(this.innerAnnotation());
			break;
		case '<':
			methodParamType.getMethodParams().add(this.generic());
			if (this.look.tag == ',') {
				this.match(',');
				methodParamType.getMethodParams().add(this.innerAnnotation());
			}
		default:
			break;
		}
		return methodParamType;
	}

	void match(int t) {
		if (look != null && look.tag == t)
			look = lex.scan();
		else
			throw new RuntimeException("语法错误！第" + lex.getLine() + "行 需要\"" + (char) t + "\"");
	}

	public static void main(String[] args) throws IOException {
		Parser p = new Parser(new Lexer());
		StringBuffer sb = new StringBuffer();
		sb.append("request{");
//		sb.append("public static void aa1(int  a,b<c,d<e,f<g>>,h<i,j,k>> s);");
//		sb.append("public static void aa2(int  a,b<c,d<e,f<g>>,h<i,j,k>> s);");
//		sb.append("public static void aa3(int  a,b<c,d<e,f<g>>,h<i,j,k>> s);");
		sb.append("public static void aa3(a<b,c<d,e<f,g>>> s,aa i);");
		sb.append("}");
		p.prog(sb.toString());
		System.out.println(p.requestBlock);
	}
}
