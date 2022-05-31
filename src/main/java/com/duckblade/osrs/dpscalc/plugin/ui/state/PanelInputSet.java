package com.duckblade.osrs.dpscalc.plugin.ui.state;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder(toBuilder = true)
public class PanelInputSet
{

	@Builder.Default
	private final String uuid = UUID.randomUUID().toString();

	@Builder.Default
	private String name = "Default Set";

	@Builder.Default
	private final PanelState state = new PanelState();

}
