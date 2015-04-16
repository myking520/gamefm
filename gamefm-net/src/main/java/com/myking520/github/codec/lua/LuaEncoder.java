package com.myking520.github.codec.lua;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;


public class LuaEncoder implements ProtocolEncoder {
	
	
	@Override
	public void dispose(IoSession session) throws Exception {
		
	}


	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		try {
			byte compressedBytes[] = (byte[]) message;
			final IoBuffer buffer = IoBuffer.allocate(compressedBytes.length).setAutoExpand(true);
			
			buffer.put(compressedBytes);
			buffer.flip();
			out.write(buffer);
			WriteFuture wf = out.flush();
			wf.addListener(new IoFutureListener<IoFuture>() {
				@Override
				public void operationComplete(IoFuture future) {
					buffer.free();
				}
			});
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

