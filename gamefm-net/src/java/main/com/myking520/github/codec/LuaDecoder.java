package com.myking520.github.codec;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


public class LuaDecoder extends ProtocolDecoderAdapter {
	private final AttributeKey IOBUFFER = new AttributeKey(this.getClass(),"IOBUFFER");
	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)throws Exception {
		IoBuffer buffer = (IoBuffer) session.getAttribute(IOBUFFER);
		boolean usingSessionBuffer = true;
		if (buffer != null) {
			buffer.put(in);
			buffer.flip();
		} else {
			buffer = in;
			usingSessionBuffer = false;
		}
		// 当有数据时
		while(buffer.hasRemaining()){
			boolean b = this.doDecode(session, buffer, out);
			if(!b){
				break;
			}
		}
		
		// 如果还有剩余的数据,则应该是下个数据包的部分信息
		if (buffer.hasRemaining()) {
			if (usingSessionBuffer) {
				buffer.compact();
			} else {
				storeRemainingInSession(buffer, session);
			}
		} else {
			if(usingSessionBuffer){
				this.removeSessionBuffer(session);
			}
		}
	}
		
	private boolean doDecode(IoSession session, IoBuffer buffer,ProtocolDecoderOutput out) throws Exception {
		if(buffer.remaining() < 8){	//<=8
			return false;
		}
		byte[] tmp = new byte[8];
		buffer.get(tmp);
		ByteBuffer bytebuffer = ByteBuffer.wrap(tmp);
		bytebuffer.order(ByteOrder.LITTLE_ENDIAN);
		bytebuffer.getInt();
		int dataLen = bytebuffer.getInt();
		buffer.position(buffer.position()-8);//读取包括数据长度的信息
		//数据体长度有效时
		if(buffer.remaining()>=dataLen){
				byte[] b = new byte[dataLen];
				buffer.get(b);
				out.write(b);
				return true;
		}
		return false;
	}
	
	private void removeSessionBuffer(IoSession session) {
		session.removeAttribute(IOBUFFER);
	}

	
	private void storeRemainingInSession(IoBuffer buf, IoSession session) {
		final IoBuffer remainingBuf = IoBuffer.allocate(buf.capacity()).setAutoExpand(true);

		remainingBuf.order(buf.order());
		remainingBuf.put(buf);
		session.setAttribute(IOBUFFER, remainingBuf);
	}
	
}
