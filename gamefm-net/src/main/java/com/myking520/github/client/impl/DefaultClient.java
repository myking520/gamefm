package com.myking520.github.client.impl;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;

import com.myking520.github.client.IClient;
import com.myking520.github.message.IResponseMessage;

public class DefaultClient implements IClient{
	private IoSession session;

	public DefaultClient(IoSession session) {
		super();
		this.session = session;
	}
	public IoSession getSession() {
		return session;
	}

	public WriteFuture write(IResponseMessage message) {
		IoBuffer buffer = toBuffer(message);
		return session.write(buffer);
	}
	
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
	private IoBuffer toBuffer(IResponseMessage message) {
		short ioSize = (short) message.getData().length;
		IoBuffer buffer = IoBuffer.allocate(ioSize + 8).setAutoExpand(true);
		buffer.putShort(message.getActionId());
		buffer.putShort(message.getState());
		buffer.putShort(ioSize);
		buffer.put(message.getData());
		buffer.flip();
		return buffer;
	}
	
}
