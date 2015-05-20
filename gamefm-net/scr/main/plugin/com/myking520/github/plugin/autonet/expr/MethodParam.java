package com.myking520.github.plugin.autonet.expr;

import java.util.ArrayList;
import java.util.List;

public class MethodParam {
	private List<MethodParamType> methodParamType=new ArrayList<MethodParamType>();

	public List<MethodParamType> getMethodParamType() {
		return methodParamType;
	}


	public void addMethodParamType(MethodParamType methodParamType){
		this.methodParamType.add(methodParamType);
	}


	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<methodParamType.size();i++){
			sb.append(methodParamType.get(i));
			sb.append(",");
			
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
}
