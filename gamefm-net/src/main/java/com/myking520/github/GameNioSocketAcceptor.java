package com.myking520.github;

import java.io.IOException;
import java.net.SocketAddress;

import org.apache.mina.core.filterchain.IoFilterChainBuilder;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameNioSocketAcceptor {
	final static Logger logger = LoggerFactory.getLogger(GameNioSocketAcceptor.class);
	private NioSocketAcceptor gameAcceptor;
	private IoFilterChainBuilder ioFilterChainBuilder;
	private IoHandler ioHandler;
	private SocketAddress socketAddress;
	public void init() {
		gameAcceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() * 2 + 1);
		gameAcceptor.setBacklog(2000);
		gameAcceptor.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, 10);
		gameAcceptor.getSessionConfig().setReadBufferSize(2048);
		gameAcceptor.getSessionConfig().setReceiveBufferSize(2048);
		gameAcceptor.getSessionConfig().setSendBufferSize(2048);
		gameAcceptor.getSessionConfig().setTcpNoDelay(true);
		gameAcceptor.setFilterChainBuilder(ioFilterChainBuilder);
		gameAcceptor.setHandler(ioHandler);
	}
	public void setGameAcceptor(NioSocketAcceptor gameAcceptor) {
		this.gameAcceptor = gameAcceptor;
	}
	public void setIoFilterChainBuilder(IoFilterChainBuilder ioFilterChainBuilder) {
		this.ioFilterChainBuilder = ioFilterChainBuilder;
	}

	public void setIoHandler(IoHandler ioHandler) {
		this.ioHandler = ioHandler;
	}

	public void setSocketAddress(SocketAddress socketAddress) {
		this.socketAddress = socketAddress;
	}

	public void start() {
		try {
			gameAcceptor.bind(socketAddress);
		} catch (IOException e) {
			logger.error("start failed",e);
			System.exit(3);
		}
	}
	public void stop(){
		gameAcceptor.unbind();
	}
}
