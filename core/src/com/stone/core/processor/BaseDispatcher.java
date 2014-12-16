package com.stone.core.processor;

import java.util.ArrayList;
import java.util.List;

import com.stone.core.msg.IMessage;

/**
 * 分发器基础实现;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseDispatcher implements IDispatcher {
	private List<IMessageProcessor> processors;

	public BaseDispatcher(int processorCount) {
		processors = new ArrayList<IMessageProcessor>();
		for (int i = 0; i < processorCount; i++) {
			processors.add(new QueueMessageProcessor());
		}
	}

	@Override
	public void start() {
		for (IMessageProcessor processor : processors) {
			processor.start();
		}
	}

	@Override
	public void stop() {
		for (IMessageProcessor processor : processors) {
			processor.stop();
		}
	}

	/**
	 * 处理分发的接口由子类去实现;
	 */
	@Override
	public abstract void put(IMessage msg);

	@Override
	public int getProcessorCount() {
		return processors.size();
	}

	@Override
	public IMessageProcessor getProcessor(int processorIndex) {
		return processors.get(processorIndex);
	}

}
