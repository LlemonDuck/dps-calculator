package com.duckblade.osrs.dpscalc.calc3.meta.context;

import com.duckblade.osrs.dpscalc.calc3.meta.exceptions.DpsException;
import com.duckblade.osrs.dpscalc.calc3.meta.exceptions.MissingInputException;
import com.duckblade.osrs.dpscalc.calc3.model.ComputeInput;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unchecked")
@Slf4j
public class ComputeContext
{

	private final Map<String, Object> computedValueCache = new HashMap<>();
	private final List<String> warnings = new ArrayList<>();

	private CallGraphBuilder graphBuilder = null;

	public ComputeContext()
	{
	}

	public ComputeContext(ComputeInput input)
	{
		this.put(ComputeInputs.ATTACKER_SKILLS, input.getAttackerSkills());
		this.put(ComputeInputs.ATTACKER_ITEMS, input.getAttackerItems());
		this.put(ComputeInputs.ATTACKER_PRAYERS, input.getAttackerPrayers());
		this.put(ComputeInputs.ATTACK_STYLE, input.getAttackStyle());
		this.put(ComputeInputs.SPELL, input.getSpell());
		this.put(ComputeInputs.BLOWPIPE_DARTS, input.getBlowpipeDarts());
		this.put(ComputeInputs.ATTACK_DISTANCE, input.getAttackerDistance());
		this.put(ComputeInputs.DEFENDER_SKILLS, input.getDefenderSkills());
		this.put(ComputeInputs.DEFENDER_BONUSES, input.getDefenderBonuses());
		this.put(ComputeInputs.DEFENDER_ATTRIBUTES, input.getDefenderAttributes());
		this.put(ComputeInputs.ON_SLAYER_TASK, input.isOnSlayerTask());
		this.put(ComputeInputs.USING_CHARGE_SPELL, input.isUsingChargeSpell());
		this.put(ComputeInputs.USING_MARK_OF_DARKNESS, input.isUsingMarkOfDarkness());
		this.put(ComputeInputs.IN_WILDERNESS, input.isInWilderness());
		this.put(ComputeInputs.RAID_PARTY_SIZE, input.getRaidPartySize());
	}

	public void initDebug()
	{
		graphBuilder = new CallGraphBuilder();
	}

	public <T> T get(ContextValue<T> computable)
	{
		if (graphBuilder == null)
		{
			return getSilent(computable);
		}

		T val = null;
		try
		{
			graphBuilder.enter(computable.key());
			val = getSilent(computable);
			return val;
		}
		finally
		{
			graphBuilder.exit(val);
		}
	}

	public <T> T getSilent(ContextValue<T> computable)
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
		catch (DpsException | MissingInputException e)
		{
			// don't re-wrap our exceptions
			throw e;
		}
		catch (Exception inner)
		{
			throw new DpsException(inner);
		}

		// store and return
		computedValueCache.put(key, value);
		return value;
	}

	public <T> void put(ContextValue<T> computable, T override)
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

	public String toGraph()
	{
		if (graphBuilder == null)
		{
			log.warn("toGraph called without initDebug");
			return "";
		}

		return graphBuilder.toString();
	}

}