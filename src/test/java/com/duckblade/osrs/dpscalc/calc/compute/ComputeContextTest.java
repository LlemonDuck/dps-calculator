package com.duckblade.osrs.dpscalc.calc.compute;

import com.duckblade.osrs.dpscalc.calc.exceptions.DpsComputeException;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.AttackType;
import com.duckblade.osrs.dpscalc.calc.model.ComputeInput;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.DefensiveBonuses;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import static com.duckblade.osrs.dpscalc.calc.testutil.AttackStyleUtil.ofAttackType;
import static com.duckblade.osrs.dpscalc.calc.testutil.ItemStatsUtil.ofItemId;
import static com.duckblade.osrs.dpscalc.calc.testutil.SkillsUtil.ofSkill;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import net.runelite.api.Skill;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ComputeContextTest
{

	@Mock
	private Computable<String> computable;

	private ComputeContext context;

	@BeforeEach
	void setUp()
	{
		context = new ComputeContext();
	}

	@Test
	void usesComputableComputeForUnknownValues()
	{
		when(computable.key()).thenReturn("MOCK_KEY");
		when(computable.compute(context)).thenReturn("MOCK_VALUE");

		assertEquals("MOCK_VALUE", context.get(computable));
	}

	@Test
	void usesCacheForKnownValues()
	{
		when(computable.key()).thenReturn("MOCK_KEY");
		when(computable.compute(context)).thenReturn("MOCK_VALUE");

		assertEquals("MOCK_VALUE", context.get(computable));
		assertEquals("MOCK_VALUE", context.get(computable));

		verify(computable, atMostOnce()).compute(context);
	}

	@Test
	void wrapsExceptionsInDpsComputeException()
	{
		when(computable.compute(context)).thenThrow(RuntimeException.class);
		assertThrows(DpsComputeException.class, () -> context.get(computable));
	}

	@Test
	void doesNotWrapNestedDpsComputeExceptions()
	{
		when(computable.compute(context)).thenThrow(DpsComputeException.class);

		try
		{
			context.get(computable);
			fail();
		}
		catch (DpsComputeException e)
		{
			if (e.getCause() instanceof DpsComputeException)
			{
				fail();
			}
		}
	}

	@Test
	void doesNotRecomputeManualEntries()
	{
		when(computable.key()).thenReturn("MOCK_KEY");
		context.put(computable, "MOCK_VALUE");

		assertEquals("MOCK_VALUE", context.get(computable));
		verify(computable, never()).compute(context);
	}

	@Test
	void combinesAllWarnings()
	{
		context.warn("WARN1");
		context.warn("WARN2");

		assertEquals(Arrays.asList("WARN1", "WARN2"), context.getWarnings());
	}

	@Test
	void initializesWithAllValuesFromInput()
	{
		Skills attackerSkills = ofSkill(Skill.HITPOINTS, 100);
		Map<EquipmentInventorySlot, ItemStats> attackerEquipment = Collections.singletonMap(EquipmentInventorySlot.WEAPON, ofItemId(100));
		Set<Prayer> attackerPrayers = Collections.singleton(Prayer.PIETY);
		AttackStyle attackStyle = ofAttackType(AttackType.RANGED);
		Spell spell = Spell.FIRE_SURGE;
		ItemStats blowpipeDarts = ofItemId(ItemID.DRAGON_DART);
		Skills defenderSkills = ofSkill(Skill.DEFENCE, 100);
		DefensiveBonuses defensiveBonuses = DefensiveBonuses.builder().defenseRanged(100).build();
		DefenderAttributes defenderAttributes = DefenderAttributes.builder().size(100).build();
		boolean onSlayerTask = true;
		boolean usingChargeSpell = true;
		boolean usingMarkOfDarkness = true;
		boolean inWilderness = true;

		ComputeInput input = ComputeInput.builder()
			.attackerSkills(attackerSkills)
			.attackerItems(attackerEquipment)
			.attackerPrayers(attackerPrayers)
			.attackStyle(attackStyle)
			.spell(spell)
			.blowpipeDarts(blowpipeDarts)
			.defenderSkills(defenderSkills)
			.defenderBonuses(defensiveBonuses)
			.defenderAttributes(defenderAttributes)
			.onSlayerTask(onSlayerTask)
			.usingChargeSpell(usingChargeSpell)
			.usingMarkOfDarkness(usingMarkOfDarkness)
			.inWilderness(inWilderness)
			.build();
		context = new ComputeContext(input);

		assertEquals(attackerSkills, context.get(ComputeInputs.ATTACKER_SKILLS));
		assertEquals(attackerEquipment, context.get(ComputeInputs.ATTACKER_EQUIPMENT));
		assertEquals(attackerPrayers, context.get(ComputeInputs.ATTACKER_PRAYERS));
		assertEquals(attackStyle, context.get(ComputeInputs.ATTACK_STYLE));
		assertEquals(spell, context.get(ComputeInputs.SPELL));
		assertEquals(blowpipeDarts, context.get(ComputeInputs.BLOWPIPE_DARTS));
		assertEquals(defenderSkills, context.get(ComputeInputs.DEFENDER_SKILLS));
		assertEquals(defensiveBonuses, context.get(ComputeInputs.DEFENDER_BONUSES));
		assertEquals(defenderAttributes, context.get(ComputeInputs.DEFENDER_ATTRIBUTES));
		assertEquals(onSlayerTask, context.get(ComputeInputs.ON_SLAYER_TASK));
		assertEquals(usingChargeSpell, context.get(ComputeInputs.USING_CHARGE_SPELL));
		assertEquals(usingMarkOfDarkness, context.get(ComputeInputs.USING_MARK_OF_DARKNESS));
		assertEquals(inWilderness, context.get(ComputeInputs.IN_WILDERNESS));
	}

}