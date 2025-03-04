package com.duckblade.osrs.dpscalc.plugin.ui.state;

import com.duckblade.osrs.dpscalc.calc.model.Monster;
import com.duckblade.osrs.dpscalc.calc.model.Player;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ComputeInput;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PanelInputSet
{

	private final String uuid = UUID.randomUUID().toString();

	private String name = "Default Set";

	private final ComputeInput state = ComputeInput.builder()
		.player(Player.builder().build())
		.monster(Monster.builder().build())
		.build();

}
