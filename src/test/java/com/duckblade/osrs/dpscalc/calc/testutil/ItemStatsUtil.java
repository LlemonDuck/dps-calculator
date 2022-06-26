package com.duckblade.osrs.dpscalc.calc.testutil;

import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.WeaponCategory;

public class ItemStatsUtil
{

	public static ItemStats ofItemId(int itemId)
	{
		return ItemStats.builder()
			.itemId(itemId)
			.build();
	}

	public static ItemStats ofWeaponCategory(WeaponCategory category)
	{
		return ItemStats.builder()
			.weaponCategory(category)
			.build();
	}

}
