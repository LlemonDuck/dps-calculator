package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.plugin.ui.util.CustomJCheckBox;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class NpcCheckBox extends CustomJCheckBox
{

	private final Function<DefenderAttributes, Boolean> getter;
	private final BiConsumer<DefenderAttributes.DefenderAttributesBuilder, Boolean> setter;

	public NpcCheckBox(String iconName, Function<DefenderAttributes, Boolean> getter, BiConsumer<DefenderAttributes.DefenderAttributesBuilder, Boolean> setter)
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

	public void setValue(DefenderAttributes newValue)
	{
		super.setValue(getter.apply(newValue));
	}

	public void consumeValue(DefenderAttributes.DefenderAttributesBuilder builder)
	{
		setter.accept(builder, getValue());
	}

}
