package com.duckblade.osrs.dpscalc.ui.npc;

import com.duckblade.osrs.dpscalc.model.NpcStats;
import com.duckblade.osrs.dpscalc.ui.skills.StatBox;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;

public class NpcStatBox extends StatBox
{

	private final ToIntFunction<NpcStats> getter;
	private final ObjIntConsumer<NpcStats.NpcStatsBuilder> setter;

	public NpcStatBox(String iconName, ToIntFunction<NpcStats> getter, ObjIntConsumer<NpcStats.NpcStatsBuilder> setter)
	{
		super(iconName, false);
		this.getter = getter;
		this.setter = setter;
	}

	public void setEditable(boolean editable)
	{
		super.setEditable(editable);
	}

	public int getValue()
	{
		return super.getValue();
	}

	public void setValue(NpcStats newValue)
	{
		super.setValue(getter.applyAsInt(newValue));
	}

	public void consumeValue(NpcStats.NpcStatsBuilder builder)
	{
		setter.accept(builder, getValue());
	}

}
