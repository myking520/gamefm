package com.myking520.github.plugin.autonet.expr;

import java.lang.reflect.Modifier;

import com.myking520.github.plugin.autonet.lexer.Token;

public class RequestMethod {
	private int ass = Modifier.PUBLIC;
	private MethodParam methodParam;
	private Token name;
	
	public int getAss() {
		return ass;
	}
	public Token getName() {
		return name;
	}
	public void setName(Token name) {
		this.name = name;
	}
	public void setStatic() {
		this.ass |= Modifier.STATIC;
	}
	public void setFinal() {
		this.ass|=Modifier.FINAL;
	}
	public MethodParam getMethodParam() {
		return methodParam;
	}
	public void setMethodParam(MethodParam methodParam) {
		this.methodParam = methodParam;
	}
	
	
}
