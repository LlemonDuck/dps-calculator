package com.duckblade.osrs.dpscalc.calc.maxhit.magic;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.google.common.collect.ImmutableMap;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
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