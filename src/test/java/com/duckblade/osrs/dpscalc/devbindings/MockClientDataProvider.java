package com.duckblade.osrs.dpscalc.devbindings;

import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.CombatStyle;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.DefensiveBonuses;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ClientDataProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ClientDataProviderThreadProxy;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.ItemStatsProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.NpcDataProvider;
import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import net.runelite.api.NpcID;
import net.runelite.api.Skill;

@Singleton
public class MockClientDataProvider implements ClientDataProvider
{

	public static class MockClientDataProviderThreadProxy extends ClientDataProviderThreadProxy
	{

		private final ClientDataProvider clientDataProvider;

		@Inject
		public MockClientDataProviderThreadProxy(ClientDataProvider clientDataProvider)
		{
			super(null, null, clientDataProvider);
			this.clientDataProvider = clientDataProvider;
		}

		@Override
		public void tryAcquire(Consumer<ClientDataProvider> runnable)
		{
			runnable.accept(clientDataProvider);
		}
	}

	private final ItemStatsProvider itemStatsProvider;
	private final NpcDataProvider npcDataProvider;

	@Inject
	MockClientDataProvider(ItemStatsProvider itemStatsProvider, NpcDataProvider npcDataProvider)
	{
		this.itemStatsProvider = itemStatsProvider;
		this.npcDataProvider = npcDataProvider;
	}

	@Override
	public Skills getPlayerSkills()
	{
		return Skills.builder()
			.level(Skill.ATTACK, 99)
			.level(Skill.STRENGTH, 99)
			.level(Skill.MAGIC, 99)
			.level(Skill.RANGED, 99)
			.level(Skill.PRAYER, 99)
			.boost(Skill.ATTACK, 19)
			.boost(Skill.STRENGTH, 19)
			.boost(Skill.RANGED, 13)
			.build();
	}

	@Override
	public Map<EquipmentInventorySlot, ItemStats> getPlayerEquipment()
	{
		return ImmutableMap.<EquipmentInventorySlot, ItemStats>builder()
			.put(EquipmentInventorySlot.WEAPON, itemStatsProvider.getById(ItemID.TWISTED_BOW))
			.put(EquipmentInventorySlot.AMMO, itemStatsProvider.getById(ItemID.DRAGON_ARROW))
			.build();
	}

	@Override
	public Set<Prayer> getPlayerActivePrayers()
	{
		return Collections.singleton(Prayer.RIGOUR);
	}

	@Override
	public AttackStyle getAttackStyle()
	{
		return WeaponCategory.BOW.getAttackStyles()
			.stream()
			.filter(as -> as.getCombatStyle() == CombatStyle.RAPID)
			.findFirst()
			.get();
	}

	@Override
	public Spell getSpell()
	{
		return null;
	}

	@Override
	public ItemStats getBlowpipeDarts()
	{
		return null;
	}

	@Override
	public Skills getNpcTargetSkills()
	{
		return npcDataProvider.getById(NpcID.ZULRAH).getSkills();
	}

	@Override
	public DefensiveBonuses getNpcTargetBonuses()
	{
		return npcDataProvider.getById(NpcID.ZULRAH).getDefensiveBonuses();
	}

	@Override
	public DefenderAttributes getNpcTargetAttributes()
	{
		return npcDataProvider.getById(NpcID.ZULRAH).getAttributes();
	}

	@Override
	public boolean playerIsOnSlayerTask()
	{
		return false;
	}

	@Override
	public boolean playerIsUsingChargeSpell()
	{
		return false;
	}

	@Override
	public boolean playerIsUsingMarkOfDarkness()
	{
		return false;
	}

	@Override
	public boolean playerIsInWilderness()
	{
		return false;
	}
}
