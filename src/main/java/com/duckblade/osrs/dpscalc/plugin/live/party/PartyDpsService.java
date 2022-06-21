package com.duckblade.osrs.dpscalc.plugin.live.party;

import com.duckblade.osrs.dpscalc.plugin.config.DpsCalcConfig;
import com.duckblade.osrs.dpscalc.plugin.live.TargetedDps;
import com.duckblade.osrs.dpscalc.plugin.live.TargetedDpsChanged;
import com.duckblade.osrs.dpscalc.plugin.live.party.messages.UpdateLiveDps;
import com.duckblade.osrs.dpscalc.plugin.module.PluginLifecycleComponent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.PartyChanged;
import net.runelite.client.party.PartyMember;
import net.runelite.client.party.PartyService;
import net.runelite.client.party.WSClient;
import net.runelite.client.party.events.UserPart;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PartyDpsService implements PluginLifecycleComponent
{

	private final EventBus eventBus;
	private final PartyService partyService;
	private final WSClient wsClient;

	private final Map<Long, TargetedDps> partyMemberDps = new HashMap<>();

	@Override
	public Predicate<DpsCalcConfig> isConfigEnabled()
	{
		return DpsCalcConfig::enablePartyService;
	}

	@Override
	public void startUp()
	{
		eventBus.register(this);
		wsClient.registerMessage(UpdateLiveDps.class);
	}

	@Override
	public void shutDown()
	{
		eventBus.unregister(this);
		wsClient.unregisterMessage(UpdateLiveDps.class);
	}

	@Subscribe
	public void onPartyChanged(PartyChanged e)
	{
		partyMemberDps.clear();
	}

	@Subscribe
	public void onUserPart(UserPart e)
	{
		partyMemberDps.remove(e.getMemberId());
	}

	@Subscribe
	public void onUpdateLiveDps(UpdateLiveDps e)
	{
		setMemberDps(e.getMemberId(), e.getTargetedDps());
	}

	@Subscribe
	public void onTargetedDpsChanged(TargetedDpsChanged e)
	{
		PartyMember localMember;
		if (partyService.isInParty() && (localMember = partyService.getLocalMember()) != null)
		{
			long localId = localMember.getMemberId();
			setMemberDps(localId, e.getTargetedDps());
			partyService.send(new UpdateLiveDps(localId, e.getTargetedDps()));
		}
	}

	private void setMemberDps(long memberId, TargetedDps dps)
	{
		if (dps == null)
		{
			partyMemberDps.remove(memberId);
		}
		else
		{
			partyMemberDps.put(memberId, dps);
		}
	}

	public double getPartyDps(int npcIndex)
	{
		return partyMemberDps.values()
			.stream()
			.filter(td -> td.getNpcIndex() == npcIndex)
			.mapToDouble(TargetedDps::getDps)
			.sum();
	}

	public boolean hasDps()
	{
		return partyService.isInParty() &&
			!partyMemberDps.isEmpty();
	}
}
