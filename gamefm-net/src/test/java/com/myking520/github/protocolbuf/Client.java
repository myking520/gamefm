package com.myking520.github.protocolbuf;

import java.net.SocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.myking520.github.SpringContext;
import com.myking520.github.client.impl.DefaultClient;
import com.myking520.github.codec.DefaultProtocolCodecFactory;
import com.myking520.github.codec.protocolbuffer.ProtocolBufferDecoder;
import com.myking520.github.codec.protocolbuffer.ProtocolBufferEncoder;
import com.myking520.github.message.IResponseMessage;
import com.myking520.github.message.protocolbuffer.ResponseMessage;
import com.myking520.github.protocolbuf.Test.Person;

public class Client extends IoHandlerAdapter{
	
	public static void main(String[] args) {
		SocketConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(30 * 1000);
		DefaultProtocolCodecFactory defaultProtocolCodecFactory=new DefaultProtocolCodecFactory();
		defaultProtocolCodecFactory.setDecoder(new ProtocolBufferDecoder());
		defaultProtocolCodecFactory.setEncoder(new ProtocolBufferEncoder());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(defaultProtocolCodecFactory));
		connector.setHandler(new Client());
		ConnectFuture future = connector.connect((SocketAddress)SpringContext.getBean("gameServerBindAddress"));
		future.awaitUninterruptibly();
		DefaultClient client=new DefaultClient(future.getSession());
		Person login=Test.Person.newBuilder().setId(1).setName("2").build();
		IResponseMessage msg=new ResponseMessage(1, 1,login);
		client.write(msg);

	}
}
