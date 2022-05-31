package com.duckblade.osrs.dpscalc.calc.compute;

import com.duckblade.osrs.dpscalc.calc.exceptions.DpsComputeException;
import com.duckblade.osrs.dpscalc.calc.model.ComputeInput;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ComputeContext
{

	private final Map<String, Object> computedValueCache;
	private final List<String> warnings;

	public ComputeContext()
	{
		computedValueCache = new HashMap<>();
		warnings = new ArrayList<>();
	}

	public ComputeContext(ComputeInput input)
	{
		this();
		this.put(ComputeInputs.ATTACKER_SKILLS, input.getAttackerSkills());
		this.put(ComputeInputs.ATTACKER_EQUIPMENT, input.getAttackerItems());
		this.put(ComputeInputs.ATTACKER_PRAYERS, input.getAttackerPrayers());
		this.put(ComputeInputs.ATTACK_STYLE, input.getAttackStyle());
		this.put(ComputeInputs.SPELL, input.getSpell());
		this.put(ComputeInputs.BLOWPIPE_DARTS, input.getBlowpipeDarts());
		this.put(ComputeInputs.DEFENDER_SKILLS, input.getDefenderSkills());
		this.put(ComputeInputs.DEFENDER_BONUSES, input.getDefenderBonuses());
		this.put(ComputeInputs.DEFENDER_ATTRIBUTES, input.getDefenderAttributes());
		this.put(ComputeInputs.ON_SLAYER_TASK, input.getOnSlayerTask());
		this.put(ComputeInputs.USING_CHARGE_SPELL, input.getUsingChargeSpell());
		this.put(ComputeInputs.USING_MARK_OF_DARKNESS, input.getUsingMarkOfDarkness());
		this.put(ComputeInputs.IN_WILDERNESS, input.getInWilderness());
	}

	public <T> T get(Computable<T> computable)
	{
		// hit cache first
		String key = computable.key();
		T value = (T) computedValueCache.get(key);
		if (value != null)
		{
			return value;
		}

		// compute only if new
		try
		{
			value = computable.compute(this);
		}
		catch (DpsComputeException e)
		{
			// don't re-wrap DpsComputeExceptions
			throw e;
		}
		catch (Exception inner)
		{
			throw new DpsComputeException(inner);
		}

		// store and return
		computedValueCache.put(key, value);
		return value;
	}

	public <T> void put(Computable<T> computable, T override)
	{
		computedValueCache.put(computable.key(), override);
	}

	public void warn(String warning)
	{
		warnings.add(warning);
	}

	public List<String> getWarnings()
	{
		return ImmutableList.copyOf(warnings);
	}

}