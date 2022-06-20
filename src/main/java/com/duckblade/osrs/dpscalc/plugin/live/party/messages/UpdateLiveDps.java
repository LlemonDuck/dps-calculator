package com.duckblade.osrs.dpscalc.plugin.live.party.messages;

import com.duckblade.osrs.dpscalc.plugin.live.TargetedDps;
import lombok.EqualsAndHashCode;
import lombok.Value;
import net.runelite.client.party.messages.PartyMemberMessage;

@Value
@EqualsAndHashCode(callSuper = true)
public class UpdateLiveDps extends PartyMemberMessage
{

	private final TargetedDps targetedDps;

	public UpdateLiveDps(long memberId, TargetedDps targetedDps)
	{
		setMemberId(memberId);
		this.targetedDps = targetedDps;
	}
}
