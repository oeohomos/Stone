package com.stone.game.msg;

import com.stone.core.msg.IDBMessage;
import com.stone.core.msg.IMessage;
import com.stone.core.processor.BaseDispatcher;
import com.stone.core.processor.IDispatcher;
import com.stone.core.processor.IMessageProcessor;

/**
 * ��Ϸ����Ϣ�ַ����;
 * 
 * @author crazyjohn
 *
 */
public class GameDispatcher extends BaseDispatcher {
	/** ���ݿ�ַ��� */
	private IDispatcher dbDispatcher;

	public GameDispatcher(int processorCount) {
		super(processorCount);
	}

	public void setDbDispatcher(IDispatcher dbDispatcher) {
		this.dbDispatcher = dbDispatcher;
	}

	@Override
	public void put(IMessage msg) {
		// TODO �����ַ�
		// ����DB��Ϣ
		if (msg instanceof IDBMessage) {
			dbDispatcher.put(msg);
		}
		// ����CGMessage
		if (msg instanceof CGMessage) {
			IMessageProcessor processor = ((CGMessage) msg).getHuman()
					.getProcessor(this);
			if (processor != null) {
				processor.put(msg);
			}
		}
	}

}