package com.myking520.github;

import java.util.concurrent.Executors;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class BootStrap {
	private ProtocolCodecFactory protocolCodecFactory;
	private NioSocketAcceptor gameAcceptor;
	public void init(){
		gameAcceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors()*2+1);
		gameAcceptor.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE,10);
		gameAcceptor.getSessionConfig().setReadBufferSize(2048);
		gameAcceptor.getSessionConfig().setReceiveBufferSize(10240);
		gameAcceptor.getSessionConfig().setSendBufferSize(10240);
		gameAcceptor.getSessionConfig().setTcpNoDelay(true);
		gameAcceptor.getSessionConfig().setSoLinger(0);
		// nio.getFilterChain().addLast("mainLogger", new LoggingFilter("mainLogger"));
		// 增加编码过滤器
		gameAcceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new LuaCodecFactory()));
		gameAcceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(Executors.newSingleThreadExecutor()));
		gameAcceptor.getSessionConfig().setReaderIdleTime( getMyConfig().getReaderIdleTime());
		gameAcceptor.getSessionConfig().setWriterIdleTime( getMyConfig().getWriterIdleTime());
		gameAcceptor.getSessionConfig().setWriteTimeout(getMyConfig().getWriteTimeout());
		gameAcceptor.getSessionConfig().setTcpNoDelay(true);
		gameAcceptor.setHandler(gameHandler);
		// 绑定端口
		gameAcceptor.bind(SpringBeanFactory.getBindAddrASServer());
	}
}
