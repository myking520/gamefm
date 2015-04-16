package com.myking520.github.client.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;

import com.myking520.github.client.IClient;
import com.myking520.github.client.IClientManager;

public class ClientManager implements IClientManager {
	private Map<Long, IClient> clients = new HashMap<Long, IClient>();

	@Override
	public IClient create(IoSession session) {
		IClient client =clients.get(session.getId());
		if(client==null){
			synchronized (clients) {
				client = clients.get(session.getId());
				if (client == null) {
					client = new DefaultClient(session);
				}
				clients.put(session.getId(), client);
			}
		}
		return client;
	}

	@Override
	public void remove(IoSession session) {
		synchronized (clients) {
			clients.remove(session.getId());
		}

	}

}
