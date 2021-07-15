package com.duckblade.osrs.dpscalc.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // for gson
public class ItemStats
{

	private int itemId;
	private String name;

	@SerializedName("astab")
	private int accuracyStab;
	@SerializedName("aslash")
	private int accuracySlash;
	@SerializedName("acrush")
	private int accuracyCrush;
	@SerializedName("amagic")
	private int accuracyMagic;
	@SerializedName("arange")
	private int accuracyRanged;

	@SerializedName("str")
	private int strengthMelee;
	@SerializedName("rstr")
	private int strengthRanged;
	@SerializedName("mdmg")
	private float strengthMagic;

	@SerializedName(value = "speed", alternate = {"aspeed"})
	private int speed;

	private int prayer;
	
	private int slot;
	private boolean is2h;
	
	private WeaponType weaponType = WeaponType.UNARMED;

}
