package com.duckblade.osrs.dpscalc;

import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class TestPanelStateDuplicate
{
	private static void statePresetA(PanelState state)
	{
		state.getAttackerBoosts().put(Skill.ATTACK, 10);
		state.setAttackerDistance(11);
		state.getAttackerItems().put(EquipmentInventorySlot.BODY, ItemStatsConstants.TORVA_BODY);
		state.getAttackerPrayers().add(Prayer.CHIVALRY);
		state.getAttackerSkills().put(Skill.STRENGTH, 50);
		state.setAttackStyle(WeaponCategory.UNARMED.getAttackStyles().get(0));
		state.getDefenderAttributes().setName("fake defender");
		state.getDefenderBonuses().setDefenseRanged(55);
		state.getDefenderSkills().put(Skill.COOKING, 20);
	}

	private static void statePresetB(PanelState state)
	{
		state.getAttackerBoosts().put(Skill.ATTACK, 99);
		state.setAttackerDistance(4);
		state.getAttackerItems().put(EquipmentInventorySlot.WEAPON, ItemStatsConstants.SCYTHE);
		state.getAttackerPrayers().remove(Prayer.CHIVALRY);
		state.getAttackerPrayers().add(Prayer.PIETY);
		state.getAttackerSkills().put(Skill.STRENGTH, 20);
		state.getAttackerSkills().put(Skill.MAGIC, 85);
		state.setAttackStyle(WeaponCategory.SCYTHE.getAttackStyles().get(1));
		state.getDefenderAttributes().setName("Ket-Zek");
		state.getDefenderBonuses().setDefenseRanged(20);
		state.getDefenderSkills().put(Skill.COOKING, 1);
		state.getDefenderSkills().put(Skill.RANGED, 47);
	}

	@Test
	void validatePanelStateEquals()
	{
		PanelState baseline = new PanelState();
		statePresetA(baseline);

		PanelState independent = new PanelState();
		statePresetA(independent);
		assertEquals(baseline, independent);

		statePresetB(baseline);
		assertNotEquals(baseline, independent);

		statePresetB(independent);
		assertEquals(baseline, independent);
	}

	@Test
	void validatePanelStateCopyConstructorIsDeep()
	{
		PanelState baseline = new PanelState();
		statePresetA(baseline);

		PanelState copied = new PanelState(baseline);
		assertEquals(baseline, copied);

		statePresetB(baseline);
		assertNotEquals(baseline, copied);

		statePresetB(copied);
		assertEquals(baseline, copied);
	}
}
