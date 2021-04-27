package com.duckblade.osrs.dpscalc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NpcStats
{

	private int id;
	private String name;

	private int levelHp;
	private int levelAttack;
	private int levelStrength;
	private int levelDefense;
	private int levelMagic;
	private int levelRanged;

	private int attackBonus;
	private int strengthBonus;
	private int magicAccuracy;
	private int magicDamage;
	private int rangedAccuracy;
	private int rangedStrength;

	private int defenseStab;
	private int defenseSlash;
	private int defenseCrush;
	private int defenseMagic;
	private int defenseRanged;

	private int combatLevel;

	private boolean isDemon;
	private boolean isDragon;
	private boolean isKalphite;
	private boolean isLeafy;
	private boolean isUndead;
	private boolean isVampyre;
	private boolean isXerician;

}
