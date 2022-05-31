package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import java.util.Map;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BlackMaskGearBonusTest
{

	@Mock
	private EquipmentItemIdsComputable equipmentItemIdsComputable;

	@Mock
	private SalveAmuletGearBonus salveAmuletGearBonus;

	@Mock
	private ComputeContext context;

	@InjectMocks
	private BlackMaskGearBonus blackMaskGearBonus;

	private Map<EquipmentInventorySlot, Integer> helmetMap(int helmet)
	{
		return singletonMap(EquipmentInventorySlot.HEAD, helmet);
	}

	@Test
	void isNotApplicableWithoutBlackMask()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(emptyMap());
		assertFalse(blackMaskGearBonus.isApplicable(context));
	}

	@Test
	void isApplicableWithBlackMask()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(helmetMap(ItemID.BLACK_MASK));
		assertTrue(blackMaskGearBonus.isApplicable(context));
	}

	@Test
	void isApplicableWithBlackMaskImbued()
	{
		when(context.get(equipmentItemIdsComputable)).thenReturn(helmetMap(ItemID.BLACK_MASK_I));
		assertTrue(blackMaskGearBonus.isApplicable(context));
	}

	@Test
	void warnsOffTask()
	{
		when(context.get(ComputeInputs.ON_SLAYER_TASK)).thenReturn(false);

		assertEquals(GearBonuses.EMPTY, blackMaskGearBonus.compute(context));
		verify(context).warn("Black mask/Slayer helmet off-task provides minimal or negative accuracy bonuses.");
	}

	@Test
	void warnsWhenSalveAlreadyInEffect()
	{
		when(context.get(ComputeInputs.ON_SLAYER_TASK)).thenReturn(true);
		when(context.get(salveAmuletGearBonus)).thenReturn(GearBonuses.symmetric(1.2));

		assertEquals(GearBonuses.EMPTY, blackMaskGearBonus.compute(context));
		verify(context).warn("Black mask/Slayer helmet does not stack with salve amulet.");
	}

	@Test
	void warnsForRangedWithUnimbuedMask()
	{
		when(context.get(ComputeInputs.ON_SLAYER_TASK)).thenReturn(true);
		when(context.get(salveAmuletGearBonus)).thenReturn(GearBonuses.EMPTY);
		when(context.get(equipmentItemIdsComputable)).thenReturn(helmetMap(ItemID.SLAYER_HELMET));
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));

		assertEquals(GearBonuses.EMPTY, blackMaskGearBonus.compute(context));
		verify(context).warn("Unimbued Black mask/Slayer helmet provides negative bonuses for ranged/magic.");
	}

	@Test
	void warnsForMagicWithUnimbuedMask()
	{
		when(context.get(ComputeInputs.ON_SLAYER_TASK)).thenReturn(true);
		when(context.get(salveAmuletGearBonus)).thenReturn(GearBonuses.EMPTY);
		when(context.get(equipmentItemIdsComputable)).thenReturn(helmetMap(ItemID.SLAYER_HELMET));
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertEquals(GearBonuses.EMPTY, blackMaskGearBonus.compute(context));
		verify(context).warn("Unimbued Black mask/Slayer helmet provides negative bonuses for ranged/magic.");
	}

	@Test
	void givesBonusForMeleeWithoutImbue()
	{
		when(context.get(ComputeInputs.ON_SLAYER_TASK)).thenReturn(true);
		when(context.get(salveAmuletGearBonus)).thenReturn(GearBonuses.EMPTY);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));

		assertEquals(GearBonuses.symmetric(7.0 / 6.0), blackMaskGearBonus.compute(context));
	}

	@Test
	void givesBonusForMeleeWithImbue()
	{
		when(context.get(ComputeInputs.ON_SLAYER_TASK)).thenReturn(true);
		when(context.get(salveAmuletGearBonus)).thenReturn(GearBonuses.EMPTY);
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.STAB));

		assertEquals(GearBonuses.symmetric(7.0 / 6.0), blackMaskGearBonus.compute(context));
	}

	@Test
	void givesBonusForRangedWithImbue()
	{
		when(context.get(ComputeInputs.ON_SLAYER_TASK)).thenReturn(true);
		when(context.get(salveAmuletGearBonus)).thenReturn(GearBonuses.EMPTY);
		when(context.get(equipmentItemIdsComputable)).thenReturn(helmetMap(ItemID.SLAYER_HELMET_I));
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.RANGED));

		assertEquals(GearBonuses.symmetric(1.15), blackMaskGearBonus.compute(context));
	}

	@Test
	void givesBonusForMagicWithImbue()
	{
		when(context.get(ComputeInputs.ON_SLAYER_TASK)).thenReturn(true);
		when(context.get(salveAmuletGearBonus)).thenReturn(GearBonuses.EMPTY);
		when(context.get(equipmentItemIdsComputable)).thenReturn(helmetMap(ItemID.SLAYER_HELMET_I));
		when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(ofAttackType(AttackType.MAGIC));

		assertEquals(GearBonuses.symmetric(1.15), blackMaskGearBonus.compute(context));
	}

}