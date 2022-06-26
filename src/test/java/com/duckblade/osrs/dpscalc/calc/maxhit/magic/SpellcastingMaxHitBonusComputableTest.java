package com.duckblade.osrs.dpscalc.calc.maxhit.magic;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import static com.duckblade.osrs.dpscalc.calc.testutil.SkillsUtil.ofSkill;
import com.google.common.collect.ImmutableMap;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SpellcastingMaxHitBonusComputableTest
{

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private SpellcastingMaxHitBonusComputable spellcastingMaxHitBonusComputable;

	@Test
	void givesBonusForChaosGauntletsWithBoltSpells()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.of(EquipmentInventorySlot.GLOVES, ItemID.CHAOS_GAUNTLETS));
		when(context.get(ComputeInputs.SPELL)).thenReturn(
			Spell.WIND_BOLT,
			Spell.WATER_BOLT,
			Spell.EARTH_BOLT,
			Spell.FIRE_BOLT
		);

		assertEquals(3, spellcastingMaxHitBonusComputable.compute(context));
		assertEquals(3, spellcastingMaxHitBonusComputable.compute(context));
		assertEquals(3, spellcastingMaxHitBonusComputable.compute(context));
		assertEquals(3, spellcastingMaxHitBonusComputable.compute(context));
	}

	@Test
	void givesBonusForChargeWithGodSpells()
	{
		when(context.get(ComputeInputs.USING_CHARGE_SPELL)).thenReturn(true);
		when(context.get(ComputeInputs.SPELL)).thenReturn(
			Spell.SARADOMIN_STRIKE,
			Spell.CLAWS_OF_GUTHIX,
			Spell.FLAMES_OF_ZAMORAK
		);

		assertEquals(10, spellcastingMaxHitBonusComputable.compute(context));
		assertEquals(10, spellcastingMaxHitBonusComputable.compute(context));
		assertEquals(10, spellcastingMaxHitBonusComputable.compute(context));
	}

	@Test
	void givesBonusForMagicDart()
	{
		when(context.get(ComputeInputs.SPELL)).thenReturn(Spell.MAGIC_DART);
		when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.of(
			EquipmentInventorySlot.WEAPON, ItemID.SLAYERS_STAFF
		));
		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(
			ofSkill(Skill.MAGIC, 0),
			ofSkill(Skill.MAGIC, 50),
			ofSkill(Skill.MAGIC, 99)
		);

		assertEquals(0, spellcastingMaxHitBonusComputable.compute(context));
		assertEquals(5, spellcastingMaxHitBonusComputable.compute(context));
		assertEquals(9, spellcastingMaxHitBonusComputable.compute(context));
	}

	@Test
	void givesBonusForMagicDartE()
	{
		when(context.get(ComputeInputs.SPELL)).thenReturn(Spell.MAGIC_DART);
		when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.of(
			EquipmentInventorySlot.WEAPON, ItemID.SLAYERS_STAFF_E
		));
		when(context.get(ComputeInputs.ATTACKER_SKILLS)).thenReturn(ofSkill(Skill.MAGIC, 99));
		when(context.get(ComputeInputs.ON_SLAYER_TASK)).thenReturn(true, false);

		assertEquals(19, spellcastingMaxHitBonusComputable.compute(context)); // on task
		assertEquals(9, spellcastingMaxHitBonusComputable.compute(context)); // off task
	}

	@Test
	void givesNoBonusForOtherInputs()
	{
		when(context.get(ComputeInputs.SPELL)).thenReturn(
			Spell.WIND_BOLT,
			Spell.FLAMES_OF_ZAMORAK,
			Spell.FIRE_SURGE
		);
		when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.of(
			EquipmentInventorySlot.GLOVES, ItemID.LEATHER_GLOVES
		));
		when(context.get(ComputeInputs.USING_CHARGE_SPELL)).thenReturn(false);

		assertEquals(0, spellcastingMaxHitBonusComputable.compute(context));
		assertEquals(0, spellcastingMaxHitBonusComputable.compute(context));
		assertEquals(0, spellcastingMaxHitBonusComputable.compute(context));
	}

}