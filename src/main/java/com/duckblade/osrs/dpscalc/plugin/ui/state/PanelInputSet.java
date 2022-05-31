package com.duckblade.osrs.dpscalc.plugin.ui.state;

import com.duckblade.osrs.dpscalc.calc.model.ComputeInput;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder(toBuilder = true)
public class PanelInputSet
{

	@Builder.Default
	private String uuid = UUID.randomUUID().toString();

	@Builder.Default
	private String name = "Default Set";

	@Builder.Default
	private ComputeInput input = ComputeInput.builder().build();

}
