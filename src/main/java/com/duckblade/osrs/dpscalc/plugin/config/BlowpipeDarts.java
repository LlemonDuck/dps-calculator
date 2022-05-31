package com.duckblade.osrs.dpscalc.plugin.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

@Getter
@RequiredArgsConstructor
public enum BlowpipeDarts
{

	DRAGON("Dragon", ItemID.DRAGON_DART),
	RUNE("Rune", ItemID.RUNE_DART),
	AMETHYST("Amethyst", ItemID.AMETHYST_DART),
	ADAMANT("Adamant", ItemID.ADAMANT_DART),
	MITHRIL("Mithril", ItemID.MITHRIL_DART),
	BLACK("Black", ItemID.BLACK_DART),
	STEEL("Steel", ItemID.STEEL_DART),
	IRON("Iron", ItemID.IRON_DART),
	BRONZE("Bronze", ItemID.BRONZE_DART),
	;

	private final String displayName;
	private final int itemId;

	public static BlowpipeDarts fromId(int id)
	{
		for (BlowpipeDarts dartType : values())
		{
			if (id == dartType.itemId)
			{
				return dartType;
			}
		}

		return null;
	}

}
