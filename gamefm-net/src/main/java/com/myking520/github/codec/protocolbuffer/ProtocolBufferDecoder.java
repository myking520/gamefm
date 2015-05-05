package com.myking520.github.codec.protocolbuffer;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.myking520.github.message.RequestMessage;

/**
 * <pre>
 * BEFORE DECODE       AFTER DECODE 
 * +----------+               +-----------+-------------+-------+
 * |    Data  |-------------->| actionID  | datalength  |  Data |
 * |  (bytes) |               |	short     | short       | bytes |  
 * +----------+               +-----------+-------------+-------+
 * </pre>
 * 
 * @author 孔国安
 *
 */
public class ProtocolBufferDecoder extends CumulativeProtocolDecoder {

	/*
	 * if return false that means continue to get next data package
	 * 
	 * if return true that means continue to process this buffer
	 */
	@Override
	protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput)
			throws Exception {

		short actionID = 0;
		short dataSize = 0;
		int remainingLength = ioBuffer.remaining();

		if (remainingLength > 4) {// contains bytes data

			ioBuffer.mark();

			actionID = ioBuffer.getShort();
			dataSize = ioBuffer.getShort();
			/*
			 * if remaining length shorter than data size that means request
			 * data not enough, reset buffer
			 */

			byte[] bytes = null;
			RequestMessage message = null;
			if (dataSize > remainingLength - 4) {
				ioBuffer.reset();
				return false;
			} else {
				bytes = new byte[dataSize];
				ioBuffer.get(bytes, 0, dataSize);
				message = createMessage(actionID, bytes);
				if (message != null) {
					// return message to handler to handle
					protocolDecoderOutput.write(message);
				} else {
					ioBuffer.mark();
					return false;
				}
				/*
				 * if remaining length longer than 0 that means has has other
				 * action in this session
				 */
				if (remainingLength - dataSize >= 4) {
					return true;
				}
			}
		}
		return false;// continue to get next data package
	}

	private RequestMessage createMessage(short actionID, byte[] actionData) {
		RequestMessage message = new RequestMessage(actionID, actionData);
		return message;
	}
}
