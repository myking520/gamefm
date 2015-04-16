package com.myking520.github.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.myking520.github.client.IClient;
import com.myking520.github.client.IClientManager;
import com.myking520.github.message.RequestMessage;

public class GameHandler extends IoHandlerAdapter {
	
	private DispatchHandler dispatchHandler;
	private IClientManager clientManager;
	public void setDispatchHandler(DispatchHandler dispatchHandler) {
		this.dispatchHandler = dispatchHandler;
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		IClient client = clientManager.create(session);
		RequestMessage m = (RequestMessage)message;
		dispatchHandler.process(client,m);
	}
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		clientManager.create(session);
	}

}
