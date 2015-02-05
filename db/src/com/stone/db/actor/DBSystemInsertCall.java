package com.stone.db.actor;

import com.stone.actor.id.IActorId;
import com.stone.actor.system.AbstractActorSystemCall;
import com.stone.actor.system.IActorSystem;
import com.stone.core.db.service.IDBService;
import com.stone.core.entity.IEntity;

/**
 * Insert call;
 * 
 * @author crazyjohn
 *
 */
public class DBSystemInsertCall extends AbstractActorSystemCall<IEntity<?>> {
	private IEntity<?> entity;

	public DBSystemInsertCall(IActorSystem callerSystem, IActorId callerActorId, IEntity<?> entity) {
		super(callerSystem, callerActorId);
		this.entity = entity;
	}

	@Override
	public IEntity<?> execute(IDBService dbService) {
		// TODO Auto-generated method stub
		return null;
	}

	public IEntity<?> getEntity() {
		return entity;
	}

}