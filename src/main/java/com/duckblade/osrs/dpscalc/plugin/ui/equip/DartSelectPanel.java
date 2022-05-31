package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.plugin.config.BlowpipeDarts;
import com.duckblade.osrs.dpscalc.plugin.ui.util.CustomJComboBox;
import java.util.Arrays;

public class DartSelectPanel extends CustomJComboBox<BlowpipeDarts>
{

	public DartSelectPanel()
	{
		super(Arrays.asList(BlowpipeDarts.values()), BlowpipeDarts::getDisplayName, "Blowpipe Darts");
	}

}
