package com.stone.game.human;

import akka.actor.ActorRef;

import com.stone.db.entity.HumanEntity;
import com.stone.game.player.Player;

/**
 * 玩家持久化管理器接口;
 * 
 * @author crazyjohn
 *
 */
public interface IHumanModule {
	/**
	 * 加载数据;
	 * 
	 * @param entity
	 */
	public void onLoad(HumanEntity entity);

	/**
	 * 持久化数据;
	 * 
	 * @param entity
	 */
	public void onPersistence(HumanEntity entity);

	/**
	 * 获取宿主玩家;
	 * 
	 * @return
	 */
	public Human getOwner();
	
	public void onMessage(Object message, Player player, ActorRef playerActor);
}
