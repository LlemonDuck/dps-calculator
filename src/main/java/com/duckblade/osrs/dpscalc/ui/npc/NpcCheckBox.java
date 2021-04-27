package com.duckblade.osrs.dpscalc.ui.npc;

import com.duckblade.osrs.dpscalc.model.NpcStats;
import com.duckblade.osrs.dpscalc.ui.util.CustomJCheckBox;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class NpcCheckBox extends CustomJCheckBox
{

	private final Function<NpcStats, Boolean> getter;
	private final BiConsumer<NpcStats.NpcStatsBuilder, Boolean> setter;

	public NpcCheckBox(String iconName, Function<NpcStats, Boolean> getter, BiConsumer<NpcStats.NpcStatsBuilder, Boolean> setter)
	{
		super(iconName);
		super.setEditable(false);
		this.getter = getter;
		this.setter = setter;
	}
	
	public void setEditable(boolean editable)
	{
		super.setEditable(editable);
	}

	public boolean getValue()
	{
		return super.getValue();
	}

	public void setValue(NpcStats newValue)
	{
		super.setValue(getter.apply(newValue));
	}

	public void consumeValue(NpcStats.NpcStatsBuilder builder)
	{
		setter.accept(builder, getValue());
	}

}
