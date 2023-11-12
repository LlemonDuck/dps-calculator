package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.core.standard.StandardDefenceRoll;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.context.FirstContextValue;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class DefenceRoll extends FirstContextValue<Integer>
{

	public static final String DEFENCE_ROLL_PROVIDERS = "DefenceRollProviders";

	@Inject
	public DefenceRoll(
		@Named(DEFENCE_ROLL_PROVIDERS) List<ContextValue<Integer>> providers,
		StandardDefenceRoll standardDefenceRoll
	)
	{
		super(providers, standardDefenceRoll);
	}
}
