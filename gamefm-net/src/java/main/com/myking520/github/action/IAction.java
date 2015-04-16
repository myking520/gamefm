package com.myking520.github.action;

import com.myking520.github.message.RequestMessage;

public interface IAction {
	public int getActionId();
	/**
	 * 一个模块最多包含50个编号，如果超过50个请将模块拆分开
	 */
	public final static short SPLIT=50;
	public void doAction(RequestMessage msg);
}
