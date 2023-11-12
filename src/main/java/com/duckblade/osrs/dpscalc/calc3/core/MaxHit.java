package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.core.standard.StandardMaxHit;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.context.FirstContextValue;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class MaxHit extends FirstContextValue<Integer>
{

	public static final String MAX_HIT_PROVIDERS = "MaxHitProviders";

	@Inject
	public MaxHit(
		@Named(MAX_HIT_PROVIDERS) List<ContextValue<Integer>> providers,
		StandardMaxHit standardMaxHit
	)
	{
		super(providers, standardMaxHit);
	}

}
