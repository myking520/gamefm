package com.myking520.github.plugin.autonet.expr;

import java.util.ArrayList;
import java.util.List;

import com.myking520.github.plugin.autonet.lexer.Token;

public class MethodParamType extends Node {
	protected Token paramType;
	protected Token paramValue;
	protected List<MethodParamType> methodParams = new ArrayList<MethodParamType>();
	//不是泛型
	private boolean notAnnotation=false;
	
	public boolean isNotAnnotation() {
		return notAnnotation;
	}
	public void setNotAnnotation(boolean notAnnotation) {
		this.notAnnotation = notAnnotation;
	}
	public Token getParamValue() {
		return paramValue;
	}
	public void setParamValue(Token paramValue) {
		this.paramValue = paramValue;
	}
	public Token getParamType() {
		return paramType;
	}
	public void setParamType(Token paramType) {
		this.paramType = paramType;
	}
	public List<MethodParamType> getMethodParams() {
		return methodParams;
	}
	public void setMethodParams(List<MethodParamType> methodParams) {
		this.methodParams = methodParams;
	}
	public String toString() {
		
		StringBuffer sb=new StringBuffer();
		if(this.paramType!=null){
			sb.append(this.paramType);
		}

		if(methodParams.isEmpty()){
			return sb.toString();
		}
		if(notAnnotation){
			sb.append(",");
		}else{
			sb.append("<");
		}
	
			
		
		for(int i=0;i<methodParams.size();i++){
			MethodParamType pt=methodParams.get(i);
			sb.append(pt);
		}
		if(!notAnnotation){
			sb.append(">");
		}
	
		if(this.paramValue!=null){
			sb.append(" ");
			sb.append(this.paramValue);
			return sb.toString();
		}
		return sb.toString();
	}

}
