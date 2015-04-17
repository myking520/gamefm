package com.myking520.github.protocolbuf;

import com.myking520.github.GameNioSocketAcceptor;
import com.myking520.github.SpringContext;

public class Server {
	public static void main(String[] args) {
		GameNioSocketAcceptor gameNioSocketAcceptor = SpringContext.getInstance().getBean("gameNioSocketAcceptor");
		gameNioSocketAcceptor.start();
	}
}
