package com.duckblade.osrs.dpscalc.plugin.osdata.clientdata;

import com.duckblade.osrs.dpscalc.calc.model.Monster;
import com.duckblade.osrs.dpscalc.calc.model.Player;

public interface ClientDataProvider
{

	Player getPlayer();

	Monster getMonster();

	default ComputeInput toComputeInput()
	{
		return ComputeInput.builder()
			.player(getPlayer())
			.monster(getMonster())
			.build();
	}

}
