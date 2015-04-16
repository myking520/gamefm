package com.myking520.github.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myking520.github.client.IClient;
import com.myking520.github.message.RequestMessage;
public class ActionDispatch {
	 final static Logger logger = LoggerFactory.getLogger(ActionDispatch.class);

	private Map<Integer, IAction> actions = new HashMap<Integer, IAction>();
	public void setActions(List<IAction> actionlt) {
		for (IAction m : actionlt) {
			this.actions.put(m.getActionId()/IAction.SPLIT, m);
		}
	}
	public void process(IClient session, RequestMessage msg)  {
		IAction nm = actions.get(msg.getActionID()/IAction.SPLIT);
		if (nm != null) {
			nm.doAction(msg);
		} else {
			logger.error(" action id is not found ->{} ", msg.getActionID());
		}
	}
}
