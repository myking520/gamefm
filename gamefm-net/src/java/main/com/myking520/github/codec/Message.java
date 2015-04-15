package com.myking520.github.codec;


public class Message {

	private short actionID;
	byte[] actionData;
	
	public Message(short actionID, byte[] actionData) {
		this.actionID = actionID;
		this.actionData = actionData;
	}
	
	public Message(int actionID, byte[] actionData) {
		this.actionID = (short)actionID;
		this.actionData = actionData;
	}

	public short getActionID() {
		return actionID;
	}

	public void setActionID(short actionID) {
		this.actionID = actionID;
	}

	public byte[] getActionData() {
		return actionData;
	}

	public void setActionData(byte[] actionData) {
		this.actionData = actionData;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("actionID = ").append(actionID);
		return sb.toString();
	}
}
