package com.myking520.github.client;

import org.apache.mina.core.session.IoSession;


public interface IClientManager {
	public IClient create(IoSession session);
	public void remove(IoSession session);
}
