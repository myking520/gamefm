package com.myking520.github.protocolbuf;

import java.net.SocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.TextFormat;
import com.myking520.github.SpringContext;
import com.myking520.github.client.IClient;
import com.myking520.github.codec.DefaultProtocolCodecFactory;
import com.myking520.github.codec.protocolbuffer.ProtocolBufferDecoder;
import com.myking520.github.codec.protocolbuffer.ProtocolBufferEncoder;
import com.myking520.github.protocolbuf.Test.Person;

public class Client extends IoHandlerAdapter implements IClient{
	
	public static void main(String[] args) throws InvalidProtocolBufferException {
		SocketConnector connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(30 * 1000);
		DefaultProtocolCodecFactory defaultProtocolCodecFactory=new DefaultProtocolCodecFactory();
		defaultProtocolCodecFactory.setDecoder(new ProtocolBufferDecoder());
		defaultProtocolCodecFactory.setEncoder(new ProtocolBufferEncoder());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(defaultProtocolCodecFactory));
		connector.setHandler(new Client());
		ConnectFuture future = connector.connect((SocketAddress)SpringContext.getBean("gameServerBindAddress"));
		future.awaitUninterruptibly();
		
		
		
		
		IoSession session=future.getSession();
		Person persion=Test.Person.newBuilder().setId(1).setName("侧方dfdfdfd").build();
		
//		IResponseMessage msg=new ResponseMessage(1, 1,persion);
//		client.write(msg);
		
		/**
		 * <body>
		 * <pre>
		 *      BEFORE ENCODE 			 AFTER ENCODE 
		 * +-----------+-------------+------------+    	    +----------+               
		 * | actionID  | datalength  |  Data 	  | ----->  |    Data  |
		 * |	short  | short       | bytes(300) |    	    |   (bytes)|                
		 * +-----------+-------------+------------+    	    +----------+    
		 * </pre>           
		 * </body>                     
		 */
			short ioSize = (short) persion.toByteArray().length;
			
			IoBuffer buffer = IoBuffer.allocate(ioSize + 8).setAutoExpand(true);
			buffer.putShort((short)1);
			buffer.putShort(ioSize);
			buffer.put( persion.toByteArray());
			buffer.flip();
//			session.write(buffer);
			 persion=Test.Person.newBuilder().mergeFrom( persion.toByteArray()).build();
			
			System.out.println( TextFormat.printToUnicodeString(persion));

	}
}
