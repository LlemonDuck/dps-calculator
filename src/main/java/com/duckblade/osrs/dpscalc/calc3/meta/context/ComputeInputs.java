package com.duckblade.osrs.dpscalc.calc3.meta.context;

import com.duckblade.osrs.dpscalc.calc3.model.ScenarioInputs;
import com.duckblade.osrs.dpscalc.calc3.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc3.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc3.model.DefensiveStats;
import com.duckblade.osrs.dpscalc.calc3.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc3.model.Prayer;
import com.duckblade.osrs.dpscalc.calc3.model.Skills;
import com.duckblade.osrs.dpscalc.calc3.model.Spell;
import com.duckblade.osrs.dpscalc.calc3.meta.exceptions.MissingInputException;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;

@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class ComputeInputs<T> implements ContextValue<T>
{

	// attacker
	public static ComputeInputs<Skills> ATTACKER_SKILLS = ComputeInputs.of("AttackerSkills");
	public static ComputeInputs<Map<EquipmentInventorySlot, ItemStats>> ATTACKER_ITEMS = ComputeInputs.of("AttackerEquipment", emptyMap());
	public static ComputeInputs<Set<Prayer>> ATTACKER_PRAYERS = ComputeInputs.of("AttackerPrayers", emptySet());
	public static ComputeInputs<AttackStyle> ATTACK_STYLE = ComputeInputs.of("AttackStyle");
	public static ComputeInputs<Spell> SPELL = ComputeInputs.of("InputSpell");
	public static ComputeInputs<ItemStats> BLOWPIPE_DARTS = ComputeInputs.of("TbpDarts");
	public static ComputeInputs<Integer> ATTACK_DISTANCE = ComputeInputs.of("AttackDistance");

	// defender
	public static ComputeInputs<DefensiveStats> DEFENDER_STATS = ComputeInputs.of("DefenderBonuses");
	public static ComputeInputs<DefenderAttributes> DEFENDER_ATTRIBUTES = ComputeInputs.of("DefenderAttributes", DefenderAttributes.EMPTY);

	// extras
	public static ComputeInputs<ScenarioInputs> SCENARIO = ComputeInputs.of("Scenario", ScenarioInputs.EMPTY);
	public static ComputeInputs<Boolean> ON_SLAYER_TASK = ComputeInputs.of("OnSlayerTask", false);
	public static ComputeInputs<Boolean> USING_CHARGE_SPELL = ComputeInputs.of("UsingChargeSpell", false);
	public static ComputeInputs<Boolean> USING_MARK_OF_DARKNESS = ComputeInputs.of("UsingMarkOfDarkness", false);
	public static ComputeInputs<Boolean> IN_WILDERNESS = ComputeInputs.of("InWilderness", false);
	public static ComputeInputs<Integer> RAID_PARTY_SIZE = ComputeInputs.of("RaidPartySize");

	private final String key;

	private final T defaultValue;

	@Override
	public String key()
	{
		return this.key;
	}

	@Override
	public T compute(ComputeContext ctx)
	{
		if (defaultValue != null)
		{
			return defaultValue;
		}

		throw new MissingInputException(this);
	}

	public static <T> ComputeInputs<T> of(String key)
	{
		return new ComputeInputs<>(key, null);
	}

}
