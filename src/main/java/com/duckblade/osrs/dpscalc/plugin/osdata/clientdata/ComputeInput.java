package com.duckblade.osrs.dpscalc.plugin.osdata.clientdata;

import com.duckblade.osrs.dpscalc.calc.model.Monster;
import com.duckblade.osrs.dpscalc.calc.model.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ComputeInput
{

	private Player player;
	private Monster monster;

	// just preserve inputs to avoid nulls
	public void loadMonster(Monster m)
	{
		if (m == null) {
			monster = Monster.builder()
				.inputs(monster.getInputs())
				.build();
		}
		else
		{
			monster = m.withInputs(monster.getInputs());
		}
	}

}
