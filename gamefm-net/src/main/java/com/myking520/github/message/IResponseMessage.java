package com.myking520.github.message;

/**
 * @author 孔国安
 */
public interface IResponseMessage {

	short getActionId();

	/**
	 * @return 消息状态
	 */
	short getState();

	/**
	 * @return 消息体
	 */
	byte[] getData();

}