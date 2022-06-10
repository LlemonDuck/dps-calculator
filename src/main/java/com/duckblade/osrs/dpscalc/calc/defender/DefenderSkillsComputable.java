package com.duckblade.osrs.dpscalc.calc.defender;

import com.duckblade.osrs.dpscalc.calc.compute.Computable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.defender.skills.SkillScaling;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DefenderSkillsComputable implements Computable<Skills>
{

	private final Set<SkillScaling> skillScalings;

	@Override
	public Skills compute(ComputeContext context)
	{
		return skillScalings.stream()
			.filter(dst -> dst.isApplicable(context))
			.findFirst()
			.map(context::get)
			.orElseGet(() -> context.get(ComputeInputs.DEFENDER_SKILLS));
	}

}
