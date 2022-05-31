package com.duckblade.osrs.dpscalc.plugin.ui.state;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Singleton;
import lombok.Getter;

@Singleton
public class PanelInputSetManager
{

	@Getter
	private final List<PanelInputSet> sets;

	@Getter
	private PanelInputSet currentSet;

	public PanelInputSetManager()
	{
		sets = new ArrayList<>();
		createNewSet();
	}

	public void createNewSet()
	{
		sets.add(
			currentSet = PanelInputSet.builder().build()
		);
	}

	public void cloneCurrentSet()
	{
		sets.add(
			currentSet = PanelInputSet.builder()
				.name(currentSet.getName() + "Clone")
				.input(currentSet.getInput().toBuilderDeep().build())
				.build()
		);
	}

}
