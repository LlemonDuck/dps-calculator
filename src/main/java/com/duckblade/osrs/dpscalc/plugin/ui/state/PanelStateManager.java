package com.duckblade.osrs.dpscalc.plugin.ui.state;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;

@Singleton
public class PanelStateManager
{

	private static final AtomicInteger PANEL_NAME_GENERATOR = new AtomicInteger(1);

	private final List<PanelInputSet> sets;

	@Getter
	private PanelInputSet currentSet;

	private final List<Runnable> onSetChangedCallbacks = new ArrayList<>();

	@Inject
	public PanelStateManager()
	{
		sets = new ArrayList<>();
		createNewSet();
	}

	public void createNewSet()
	{
		createNewSet("Set " + PANEL_NAME_GENERATOR.getAndIncrement());
	}

	public void createNewSet(String name)
	{
		PanelInputSet newSet = PanelInputSet.builder()
			.name(name)
			.build();

		sets.add(newSet);
		selectSet(newSet);
	}

	public PanelInputSet currentSet()
	{
		return currentSet;
	}

	public PanelState currentState()
	{
		return currentSet.getState();
	}

	public List<PanelInputSet> getInputSets()
	{
		return ImmutableList.copyOf(sets);
	}

	public void selectSet(PanelInputSet set)
	{
		currentSet = sets.contains(set) ? set : null;
		invokeCallbacks();
	}

	public void deleteSet(PanelInputSet set)
	{
		sets.remove(set);
	}

	private void invokeCallbacks()
	{
		onSetChangedCallbacks.forEach(Runnable::run);
	}

	public void addOnSetChangedListener(Runnable r)
	{
		onSetChangedCallbacks.add(r);
	}

}
