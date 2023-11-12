package com.duckblade.osrs.dpscalc.calc3.core;

import com.duckblade.osrs.dpscalc.calc3.core.standard.StandardAccuracy;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.context.FirstContextValue;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class Accuracy extends FirstContextValue<Double>
{

	public static final String ACCURACY_PROVIDERS = "AccuracyProviders";

	@Inject
	public Accuracy(
		@Named(ACCURACY_PROVIDERS) List<ContextValue<Double>> accuracyProviders,
		StandardAccuracy standardAccuracy
	)
	{
		super(accuracyProviders, standardAccuracy);
	}
}
