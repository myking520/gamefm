package com.myking520.github.plugin.autonet.expr;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class RequestBlock {
	private List<RequestMethod> methods = new ArrayList<RequestMethod>();

	public void addRequestMethod(RequestMethod requestMethod) {
		this.methods.add(requestMethod);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < methods.size(); i++) {
			RequestMethod method = methods.get(i);
			if ((method.getAss() & Modifier.PUBLIC) != 0) {
				sb.append("public ");
			}
			if ((method.getAss() & Modifier.STATIC) != 0) {
				sb.append("static ");
			}
			if ((method.getAss() & Modifier.FINAL) != 0) {
				sb.append("final ");
			}
			sb.append("void ");
			sb.append(method.getName());
			sb.append("(");
			if (method.getMethodParam() != null) {
				sb.append(method.getMethodParam().toString());
			}
			sb.append(");");
		}
		return sb.toString();
	}

}
