package com.stone.game.human.skill.handler;

import com.stone.core.annotation.Handler;
import com.stone.core.processor.MessageType;
import com.stone.game.handler.IMessageHandlerWithType;
import com.stone.game.human.Human;
import com.stone.game.msg.CGMessage;

/**
 * װ�����ܴ�����;
 * 
 * @author crazyjohn
 *
 */
@Handler
public class HumanEquipSkillHandler implements IMessageHandlerWithType {

	@Override
	public MessageType getMessageType() {
		return MessageType.CG_EQUIP_SKILL;
	}

	@Override
	public void execute(CGMessage msg) {
		// TODO do some check
		Human human = msg.getHuman();
		human.getSkillManager().equipSkill();
	}

}