package com.myking520.github.message;


public class RequestMessage {

	private short actionID;
	private byte[] data;
	public RequestMessage(short actionID, byte[] actionData) {
		this.actionID = actionID;
		this.data = actionData;
	}
	public short getActionID() {
		return actionID;
	}
	public byte[] getData() {
		return data;
	}
	
}
