package com.duckblade.osrs.dpscalc.plugin.osdata.clientdata;

import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.ComputeInput;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.DefensiveBonuses;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import java.util.Map;
import java.util.Set;
import net.runelite.api.EquipmentInventorySlot;

public interface ClientDataProvider
{

	Skills getPlayerSkills();

	Map<EquipmentInventorySlot, ItemStats> getPlayerEquipment();

	Set<Prayer> getPlayerActivePrayers();

	AttackStyle getAttackStyle();

	Spell getSpell();

	ItemStats getBlowpipeDarts();

	Skills getNpcTargetSkills();

	DefensiveBonuses getNpcTargetBonuses();

	DefenderAttributes getNpcTargetAttributes();

	boolean playerIsOnSlayerTask();

	boolean playerIsUsingChargeSpell();

	boolean playerIsUsingMarkOfDarkness();

	boolean playerIsInWilderness();

	default ComputeInput toComputeInput()
	{
		return ComputeInput.builder()
			.attackerSkills(getPlayerSkills())
			.attackerItems(getPlayerEquipment())
			.attackerPrayers(getPlayerActivePrayers())
			.attackStyle(getAttackStyle())
			.spell(getSpell())
			.blowpipeDarts(getBlowpipeDarts())
			.defenderSkills(getNpcTargetSkills())
			.defenderBonuses(getNpcTargetBonuses())
			.defenderAttributes(getNpcTargetAttributes())
			.onSlayerTask(playerIsOnSlayerTask())
			.usingChargeSpell(playerIsUsingChargeSpell())
			.usingMarkOfDarkness(playerIsUsingMarkOfDarkness())
			.inWilderness(playerIsInWilderness())
			.build();
	}

}
