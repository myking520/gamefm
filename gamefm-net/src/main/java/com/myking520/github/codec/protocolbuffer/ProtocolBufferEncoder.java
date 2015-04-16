package com.myking520.github.codec.protocolbuffer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;


public class ProtocolBufferEncoder implements ProtocolEncoder {

	public void dispose(IoSession paramIoSession) throws Exception {

	}

	public void encode(IoSession paramIoSession, Object paramObject, ProtocolEncoderOutput out) throws Exception {
		IoBuffer buffer = (IoBuffer) paramObject;
		buffer.flip();
		out.write(buffer);
	}

}
