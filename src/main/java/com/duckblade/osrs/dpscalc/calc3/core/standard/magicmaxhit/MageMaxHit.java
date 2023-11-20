package com.duckblade.osrs.dpscalc.calc3.core.standard.magicmaxhit;

import com.duckblade.osrs.dpscalc.calc3.core.EquipmentStats;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ComputeContext;
import com.duckblade.osrs.dpscalc.calc3.meta.context.ContextValue;
import com.duckblade.osrs.dpscalc.calc3.meta.context.FirstContextValue;
import java.util.List;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class MageMaxHit extends FirstContextValue<Integer>
{

	public static final String MAGE_MAX_HIT_PROVIDERS = "MageMaxHitProviders";

	private final EquipmentStats equipmentStats;

	public MageMaxHit(
		@Named(MAGE_MAX_HIT_PROVIDERS) List<ContextValue<Integer>> providers,
		SpellbookMaxHit spellbookMaxHit,
		EquipmentStats equipmentStats
	)
	{
		super(providers, spellbookMaxHit);
		this.equipmentStats = equipmentStats;
	}

	@Override
	public Integer compute(ComputeContext ctx)
	{
		int magicDamageBonus = ctx.get(equipmentStats).getStrengthMagic() * 10;
		return super.compute(ctx) * magicDamageBonus;
	}
}
