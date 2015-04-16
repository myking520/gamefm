package com.myking520.github.message.protocolbuffer;

import com.google.protobuf.MessageLite;
import com.myking520.github.message.IResponseMessage;

public class ResponseMessage implements IResponseMessage {
	private short actionId;
	//消息状态
	private short state;
	private byte[] data=new byte[0];
	public ResponseMessage(short actionId, short state, MessageLite message) {
		super();
		this.actionId = actionId;
		this.state = state;
		this.data = message.toByteArray();
	}
	/* (non-Javadoc)
	 * @see com.myking520.github.message.protocolbuffer.IResponseMessage#getActionId()
	 */
	@Override
	public short getActionId() {
		return actionId;
	}
	/* (non-Javadoc)
	 * @see com.myking520.github.message.protocolbuffer.IResponseMessage#getState()
	 */
	@Override
	public short getState() {
		return state;
	}
	/* (non-Javadoc)
	 * @see com.myking520.github.message.protocolbuffer.IResponseMessage#getData()
	 */
	@Override
	public byte[] getData() {
		return data;
	}
	
}
