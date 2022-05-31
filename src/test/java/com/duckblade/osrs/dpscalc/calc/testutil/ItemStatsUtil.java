package com.duckblade.osrs.dpscalc.calc.testutil;

import com.duckblade.osrs.dpscalc.calc.model.ItemStats;

public class ItemStatsUtil
{

	public static ItemStats ofItemId(int itemId)
	{
		return ItemStats.builder()
			.itemId(itemId)
			.build();
	}

}
