package com.myking520.github.plugin.autonet.expr;

import java.lang.reflect.Modifier;

public class RequestMethod {
	private int ass = Modifier.PUBLIC;
	private MethodParam methodParam;
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
