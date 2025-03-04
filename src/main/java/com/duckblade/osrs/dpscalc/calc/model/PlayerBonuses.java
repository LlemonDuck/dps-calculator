package com.duckblade.osrs.dpscalc.calc.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
public class PlayerBonuses
{

	@Builder.Default
	int str = 0;

	@Builder.Default
	@SerializedName("ranged_str")
	int rangedStr = 0;

	@Builder.Default
	@SerializedName("magic_str")
	int magicStr = 0;

	@Builder.Default
	int prayer = 0;

	public PlayerBonuses merge(PlayerBonuses other)
	{
		return PlayerBonuses.builder()
			.str(this.str + other.str)
			.rangedStr(this.rangedStr + other.rangedStr)
			.magicStr(this.magicStr + other.magicStr)
			.prayer(this.prayer + other.prayer)
			.build();
	}

}
