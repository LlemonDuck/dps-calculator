package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.model.CombatMode;
import com.duckblade.osrs.dpscalc.model.EquipmentStats;
import com.duckblade.osrs.dpscalc.model.ItemStats;
import com.duckblade.osrs.dpscalc.model.NpcStats;
import com.duckblade.osrs.dpscalc.model.Prayer;
import com.duckblade.osrs.dpscalc.model.Spell;
import com.duckblade.osrs.dpscalc.model.WeaponMode;
import java.util.Collection;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.Skill;

@Getter
@Builder
public class CalcInput
{
	
	private final CombatMode combatMode;

	private final WeaponMode weaponMode;

	private final NpcStats npcTarget;

	private final Map<Skill, Integer> playerSkills;

	private final Map<Skill, Integer> playerBoosts;

	private final EquipmentStats equipmentStats;

	private final Map<EquipmentInventorySlot, ItemStats> playerEquipment;
	
	private final Spell spell;
	
	private final Collection<Prayer> enabledPrayers;

	private final int prayerDrain;

	private final boolean onSlayerTask; // slayer helms
	
	// dharok's
	private final int activeHp;
	private final int maxHp;
	
	private final boolean usingCharge;

}
