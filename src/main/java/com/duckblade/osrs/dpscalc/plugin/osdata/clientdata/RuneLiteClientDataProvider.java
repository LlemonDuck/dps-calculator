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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
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
import net.runelite.api.Skill;
import net.runelite.api.VarPlayer;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RuneLiteClientDataProvider implements ClientDataProvider
{

	private static final int AUTOCAST_SPELL_VARBIT = 276;

	private final Client client;
	private final DpsCalcConfig config;
	private final ItemStatsProvider itemStatsProvider;

	@Override
	public Skills getPlayerSkills()
	{
		Map<Skill, Integer> levels = new EnumMap<>(Skill.class);
		Map<Skill, Integer> boosts = new EnumMap<>(Skill.class);
		for (Skill s : Skill.values())
		{
			int level = client.getRealSkillLevel(s);
			levels.put(s, level);
			boosts.put(s, client.getBoostedSkillLevel(s) - level);
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
		int asVarp = client.getVar(VarPlayer.ATTACK_STYLE);

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
		return null;
	}

	@Override
	public DefensiveBonuses getNpcTargetBonuses()
	{
		return null;
	}

	@Override
	public DefenderAttributes getNpcTargetAttributes()
	{
		return null;
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
