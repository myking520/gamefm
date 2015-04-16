package com.myking520.github.client;

import org.apache.mina.core.session.IoSession;

/**
 * 客户端管理
 */
public interface IClientManager {
	public IClient create(IoSession session);
	public void remove(IoSession session);
}
