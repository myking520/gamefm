package com.myking520.github.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class ProtocolBufferDecoder extends CumulativeProtocolDecoder {

	/*
	 * if return false that means continue to get next data package
	 * 
	 * if return true that means continue to process this buffer
	 */
	@Override
	protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
		synchronized (ioBuffer) {

			short actionID = 0;
			short dataSize = 0;
			int remainingLength = ioBuffer.remaining();

			if (remainingLength > 4) {// contains bytes data

				/*
				 * mark position for reset at later if necessary
				 */
				ioBuffer.mark();

				actionID = ioBuffer.getShort();
				dataSize = ioBuffer.getShort();
				/*
				 * if remaining length shorter than data size that means request
				 * data not enough, reset buffer
				 */

				byte[] bytes = null;
				Message message = null;
				if (dataSize > remainingLength - 4) {
					ioBuffer.reset();
					return false;
				} else {
					try {
						/*
						 * read request data
						 */
						//1234567     890
						bytes = new byte[dataSize];
						ioBuffer.get(bytes, 0, dataSize);
						// decode message using AESCipher
						// byte[] decodedMessage =
						// AESCipher.getInstance().decrypt(bytes);
						message = createMessage(actionID, bytes);

					} catch (Exception e) {
						if (bytes != null) {
							System.err.println("Error Data Format!");
							System.err.println("Action ID: " + actionID + " Data Size: " + dataSize + " Data: " + new String(bytes));
							return false;
						}
					}
					if (message != null) {
						// return message to handler to handle
						protocolDecoderOutput.write(message);
					} else {
						ioBuffer.mark();
						return false;
					}
					/*
					 * if remaining length longer than 0 that means has has
					 * other action in this session
					 */
					if (remainingLength - dataSize >= 4) {
						return true;
					}
				}
			}
			return false;// continue to get next data package
		}
	}

	private Message createMessage(short actionID, byte[] actionData) {
		Message message = new Message(actionID, actionData);
		return message;
	}
}
