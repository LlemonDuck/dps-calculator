package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.plugin.ui.skills.StatBox;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;

public class NpcStatBox<StatType, BuilderType> extends StatBox
{

	private final ToIntFunction<StatType> getter;
	private final ObjIntConsumer<BuilderType> setter;

	public NpcStatBox(String iconName, ToIntFunction<StatType> getter, ObjIntConsumer<BuilderType> setter)
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

	public void setValue(StatType newValue)
	{
		super.setValue(getter.applyAsInt(newValue));
	}

	public void consumeValue(BuilderType builder)
	{
		setter.accept(builder, getValue());
	}

}
