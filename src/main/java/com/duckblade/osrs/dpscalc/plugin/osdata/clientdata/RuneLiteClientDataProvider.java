package com.duckblade.osrs.dpscalc.plugin.osdata.clientdata;

import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.DefensiveBonuses;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.duckblade.osrs.dpscalc.plugin.config.DpsCalcConfig;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.ItemStatsProvider;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.NpcData;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.NpcDataProvider;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Client;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.runelite.api.VarPlayer;
import net.runelite.api.Varbits;
import net.runelite.client.plugins.slayer.SlayerPluginService;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RuneLiteClientDataProvider implements ClientDataProvider
{

	private static final int AUTOCAST_SPELL_VARBIT = 276;

	private final Client client;
	private final SlayerPluginService slayerPluginService;

	private final DpsCalcConfig config;
	private final ItemStatsProvider itemStatsProvider;
	private final NpcDataProvider npcDataProvider;
	private final InteractingNpcTracker interactingNpcTracker;

	@Override
	public Skills getPlayerSkills()
	{
		Map<Skill, Integer> levels = new EnumMap<>(Skill.class);
		Map<Skill, Integer> boosts = new EnumMap<>(Skill.class);
		for (Skill s : Skill.values())
		{
			if (s != Skill.OVERALL)
			{
				int level = client.getRealSkillLevel(s);
				levels.put(s, level);
				boosts.put(s, client.getBoostedSkillLevel(s) - level);
			}
		}

		return Skills.builder()
			.levels(ImmutableMap.copyOf(levels))
			.boosts(ImmutableMap.copyOf(boosts))
			.build();
	}

	@Override
	public Map<EquipmentInventorySlot, ItemStats> getPlayerEquipment()
	{
		ItemContainer inv = client.getItemContainer(InventoryID.EQUIPMENT);
		if (inv == null)
		{
			return Collections.emptyMap();
		}

		Map<EquipmentInventorySlot, ItemStats> equipment = new EnumMap<>(EquipmentInventorySlot.class);
		for (EquipmentInventorySlot slot : EquipmentInventorySlot.values())
		{
			Item i = inv.getItem(slot.getSlotIdx());
			ItemStats is = i == null ? null : itemStatsProvider.getById(i.getId());
			if (is != null)
			{
				equipment.put(slot, is);
			}
		}
		return ImmutableMap.copyOf(equipment);
	}

	@Override
	public Set<Prayer> getPlayerActivePrayers()
	{
		Set<Prayer> prayers = EnumSet.noneOf(Prayer.class);
		for (Prayer p : Prayer.values())
		{
			if (client.isPrayerActive(p.getRlPrayer()))
			{
				prayers.add(p);
			}
		}

		return ImmutableSet.copyOf(prayers);
	}

	@Override
	public AttackStyle getAttackStyle()
	{
		ItemStats weapon = getPlayerEquipment().getOrDefault(EquipmentInventorySlot.WEAPON, ItemStats.EMPTY);
		int asVarp = client.getVarpValue(VarPlayer.ATTACK_STYLE);

		for (AttackStyle as : weapon.getWeaponCategory().getAttackStyles())
		{
			if (as.getVarpValue() == asVarp)
			{
				return as;
			}
		}

		return null;
	}

	@Override
	public Spell getSpell()
	{
		int spellVarb = client.getVarbitValue(AUTOCAST_SPELL_VARBIT);
		for (Spell s : Spell.values())
		{
			if (s.getVarbValue() == spellVarb)
			{
				return s;
			}
		}

		return null;
	}

	@Override
	public ItemStats getBlowpipeDarts()
	{
		return itemStatsProvider.getById(config.defaultBlowpipeDarts().getItemId());
	}

	@Override
	public Skills getNpcTargetSkills()
	{
		NpcData lastInteracted = interactingNpcTracker.getLastInteracted();
		return lastInteracted == null ? null : lastInteracted.getSkills();
	}

	@Override
	public DefensiveBonuses getNpcTargetBonuses()
	{
		NpcData lastInteracted = interactingNpcTracker.getLastInteracted();
		return lastInteracted == null ? null : lastInteracted.getDefensiveBonuses();
	}

	@Override
	public DefenderAttributes getNpcTargetAttributes()
	{
		NpcData lastInteracted = interactingNpcTracker.getLastInteracted();
		return lastInteracted == null ? null : lastInteracted.getAttributes();
	}

	@Override
	public boolean playerIsOnSlayerTask()
	{
		DefenderAttributes attr = getNpcTargetAttributes();
		if (attr == null)
		{
			return false;
		}

		List<NPC> targets = slayerPluginService.getTargets();
		if (targets == null)
		{
			return false;
		}

		// this will only work if the task npc is on-screen which should always be true for the overlay
		// but could be inaccurate if loading the data into side panel after a delay
		return targets.stream()
			.anyMatch(npc -> npcDataProvider.canonicalize(npc.getId()) == attr.getNpcId());
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
		return client.getVarbitValue(Varbits.PVP_SPEC_ORB) == 1;
	}
}
