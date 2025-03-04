package com.duckblade.osrs.dpscalc.calc;

import static com.duckblade.osrs.dpscalc.calc.Canonicalization.getCanonicalEquipment;
import static com.duckblade.osrs.dpscalc.calc.Claws.burningClawDoT;
import static com.duckblade.osrs.dpscalc.calc.Claws.burningClawSpec;
import static com.duckblade.osrs.dpscalc.calc.Claws.dClawDist;
import static com.duckblade.osrs.dpscalc.calc.Constants.ALWAYS_MAX_HIT_MONSTERS_MAGIC;
import static com.duckblade.osrs.dpscalc.calc.Constants.ALWAYS_MAX_HIT_MONSTERS_MELEE;
import static com.duckblade.osrs.dpscalc.calc.Constants.ALWAYS_MAX_HIT_MONSTERS_RANGED;
import static com.duckblade.osrs.dpscalc.calc.Constants.BONE_WEAPONS;
import static com.duckblade.osrs.dpscalc.calc.Constants.CAST_STANCES;
import static com.duckblade.osrs.dpscalc.calc.Constants.DEFAULT_ATTACK_SPEED;
import static com.duckblade.osrs.dpscalc.calc.Constants.GUARDIAN_IDS;
import static com.duckblade.osrs.dpscalc.calc.Constants.GUARDIAN_PICKAXE_BONUSES;
import static com.duckblade.osrs.dpscalc.calc.Constants.ONE_HIT_MONSTERS;
import static com.duckblade.osrs.dpscalc.calc.Constants.PARTIALLY_IMPLEMENTED_SPECS;
import static com.duckblade.osrs.dpscalc.calc.Constants.SECONDS_PER_TICK;
import static com.duckblade.osrs.dpscalc.calc.Constants.TITAN_ELEMENTAL_IDS;
import static com.duckblade.osrs.dpscalc.calc.Constants.TOMBS_OF_AMASCUT_MONSTER_IDS;
import static com.duckblade.osrs.dpscalc.calc.Constants.TWINFLAME_STAFF_SPELL_CLASSES;
import static com.duckblade.osrs.dpscalc.calc.Constants.UNIMPLEMENTED_SPECS;
import static com.duckblade.osrs.dpscalc.calc.Constants.USES_DEFENCE_LEVEL_FOR_MAGIC_DEFENCE_NPC_IDS;
import static com.duckblade.osrs.dpscalc.calc.Constants.VERZIK_P1_IDS;
import static com.duckblade.osrs.dpscalc.calc.Constants.WEAPON_SPEC_COSTS;
import com.duckblade.osrs.dpscalc.calc.bolts.BoltContext;
import static com.duckblade.osrs.dpscalc.calc.bolts.Bolts.diamondBolts;
import static com.duckblade.osrs.dpscalc.calc.bolts.Bolts.dragonstoneBolts;
import static com.duckblade.osrs.dpscalc.calc.bolts.Bolts.onyxBolts;
import static com.duckblade.osrs.dpscalc.calc.bolts.Bolts.opalBolts;
import static com.duckblade.osrs.dpscalc.calc.bolts.Bolts.pearlBolts;
import static com.duckblade.osrs.dpscalc.calc.bolts.Bolts.rubyBolts;
import com.duckblade.osrs.dpscalc.calc.dist.AttackDistribution;
import com.duckblade.osrs.dpscalc.calc.dist.HitDistribution;
import static com.duckblade.osrs.dpscalc.calc.dist.HitTransformer.divisionTransformer;
import static com.duckblade.osrs.dpscalc.calc.dist.HitTransformer.flatAddTransformer;
import static com.duckblade.osrs.dpscalc.calc.dist.HitTransformer.flatLimitTransformer;
import static com.duckblade.osrs.dpscalc.calc.dist.HitTransformer.multiplyTransformer;
import com.duckblade.osrs.dpscalc.calc.dist.Hitsplat;
import com.duckblade.osrs.dpscalc.calc.dist.TransformOpts;
import com.duckblade.osrs.dpscalc.calc.dist.WeightedHit;
import com.duckblade.osrs.dpscalc.calc.model.AmmoApplicability;
import static com.duckblade.osrs.dpscalc.calc.model.AmmoApplicability.INCLUDED;
import static com.duckblade.osrs.dpscalc.calc.model.AmmoApplicability.INVALID;
import com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance.ACCURATE;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance.AGGRESSIVE;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance.CONTROLLED;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance.MANUAL_CAST;
import static com.duckblade.osrs.dpscalc.calc.model.CombatStyleStance.RAPID;
import com.duckblade.osrs.dpscalc.calc.model.CombatStyleType;
import com.duckblade.osrs.dpscalc.calc.model.ElementalWeakness;
import com.duckblade.osrs.dpscalc.calc.model.EquipmentCategory;
import static com.duckblade.osrs.dpscalc.calc.model.EquipmentCategory.CROSSBOW;
import static com.duckblade.osrs.dpscalc.calc.model.EquipmentCategory.PICKAXE;
import static com.duckblade.osrs.dpscalc.calc.model.EquipmentCategory.getCombatStylesForCategory;
import com.duckblade.osrs.dpscalc.calc.model.EquipmentPiece;
import com.duckblade.osrs.dpscalc.calc.model.Factor;
import com.duckblade.osrs.dpscalc.calc.model.FeatureStatus;
import com.duckblade.osrs.dpscalc.calc.model.Monster;
import com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.DEMON;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.KALPHITE;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.SHADE;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.VAMPYRE_1;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.VAMPYRE_2;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.VAMPYRE_3;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.isVampyre;
import com.duckblade.osrs.dpscalc.calc.model.Player;
import com.duckblade.osrs.dpscalc.calc.model.PlayerBonuses;
import com.duckblade.osrs.dpscalc.calc.model.PlayerBuffs;
import com.duckblade.osrs.dpscalc.calc.model.PlayerCombatStyle;
import com.duckblade.osrs.dpscalc.calc.model.PlayerEquipment;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.duckblade.osrs.dpscalc.calc.model.PrayerCombatStyle;
import static com.duckblade.osrs.dpscalc.calc.model.RangedDamageType.HEAVY;
import static com.duckblade.osrs.dpscalc.calc.model.RangedDamageType.getRangedDamageType;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.duckblade.osrs.dpscalc.calc.model.Spellbook;
import com.duckblade.osrs.dpscalc.calc.model.Spellement;
import static com.duckblade.osrs.dpscalc.calc.scaling.MonsterScaling.scaleMonster;
import com.google.common.collect.ImmutableList;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DpsCalc
{

	private Player player;
	private Monster baseMonster;
	private Monster monster;
	private final CalcOpts opts;

	@Getter
	private final List<String> issues = new ArrayList<>();

	private HashSet<String> allEquippedItems = new HashSet<>(); // not final because sometimes sub-init'd

	@Getter(lazy = true)
	private final AttackDistribution distribution = getDistributionImpl();

	public DpsCalc(
		Player player,
		Monster monster,
		CalcOpts opts
	)
	{
		this.opts = opts;

		if (opts.isNoInit())
		{
			this.player = player;
			this.baseMonster = monster;
			this.monster = monster;
			return;
		}

		this.player = player;
		this.baseMonster = monster;
		this.monster = opts.isDisableMonsterScaling() ? baseMonster : scaleMonster(baseMonster);

		canonicalizeEquipment();
		initAllEquippedItems();
		sanitizeInputs();

		if (!this.opts.isNoInit() && this.isSpecSupported() == FeatureStatus.UNIMPLEMENTED)
		{
			issues.add("This loadout's weapon special attack is not yet supported in the calculator.");
		}
	}

	private void canonicalizeEquipment()
	{
		this.player = this.player.withEquipment(
			getCanonicalEquipment(this.player.getEquipment())
		);
	}

	private void initAllEquippedItems()
	{
		this.player
			.getEquipment()
			.getAllPieces()
			.forEach(eq ->
				allEquippedItems.add(eq.getName()));
	}

	private void sanitizeInputs()
	{
		// make sure currentHp is set and valid
		int currHp = monster.getInputs().getCurrentHp();
		int maxHp = monster.getSkills().getHp();
		if (currHp <= 0 || currHp > maxHp)
		{
			monster = monster.withInputs(
				monster.getInputs()
					.withCurrentHp(maxHp)
			);
		}

		// specs are never manul cast, although the player may have it selected otherwise
		PlayerEquipment eq = player.getEquipment();
		if (opts.isUsingSpecialAttack())
		{
			if (player.getStyle().getStance() == CombatStyleStance.MANUAL_CAST)
			{
				player = player.toBuilder()
					.style(getCombatStylesForCategory(eq.getWeapon()).get(0))
					.spell(null)
					.build();
			}

			// these staves use a built-in spell for their spec
			if (this.wearing("Accursed sceptre (a)", "Eldritch nightmare staff", "Volatile nightmare staff"))
			{
				player = player.toBuilder()
					.style(getCombatStylesForCategory(EquipmentCategory.POWERED_STAFF).get(0))
					.spell(null)
					.build();
			}
		}

		if (!CAST_STANCES.contains(player.getStyle().getStance()))
		{
			player = player.withSpell(null);
		}

		if (player.getStyle().getStance() != MANUAL_CAST && ammoApplicability() == INVALID)
		{
			if (eq.getAmmo() != null)
			{
				issues.add("This ammo does not work with your current weapon.");
			}
			else
			{
				issues.add("Your weapon requires ammo to use.");
			}
		}

		String spellName = player.getSpell() != null ? player.getSpell().getName() : null;
		if (spellName != null)
		{
			boolean wrongStaff = false;
			switch (spellName)
			{
				case "Iban Blast":
					wrongStaff = !this.wearing("Iban's staff", "Iban's staff (u)");
					break;

				case "Saradomin Strike":
					wrongStaff = !this.wearing("Saradomin staff", "Staff of light");
					break;

				case "Claws of Guthix":
					wrongStaff = !this.wearing("Guthix staff", "Void knight mace", "Staff of balance");
					break;

				case "Flames of Zamorak":
					wrongStaff = !this.wearing(
						"Zamorak staff",
						"Staff of the dead",
						"Toxic staff of the dead",
						"Thammaron's sceptre (a)",
						"Accursed sceptre (a)"
					);
					break;

				case "Magic Dart":
					wrongStaff = !this.wearing(
						"Slayer's staff",
						"Slayer's staff (e)",
						"Staff of the dead",
						"Toxic staff of the dead",
						"Staff of light",
						"Staff of balance"
					);
					break;
			}
			if (wrongStaff)
			{
				player = player.withSpell(null);
				issues.add("This spell needs a specific weapon equipped to cast.");
			}

			if (spellName.contains("Demonbane") && !this.monster.getAttributes().contains(MonsterAttribute.DEMON)
				|| spellName.equals("Crumble Undead") && !this.monster.getAttributes().contains(MonsterAttribute.UNDEAD))
			{
				player = player.withSpell(null);
				issues.add("This spell cannot be case on the selected monster.");
			}
		}

		if (this.wearing("Dawnbringer") && (!this.monster.getName().equals("Verzik Vitur") || !this.monster.getVersion().contains("Phase 1")))
		{
			issues.add("This weapon cannot be used against the selected monster.");
		}

		if (this.wearingAll("Blue moon helm", "Blue moon chestplate", "Blue moon tassets", "Blue moon spear")
			|| this.wearingAll("Eclipse moon helm", "Eclipse moon chestplate", "Eclipse moon tassets", "Eclipse atlatl"))
		{
			issues.add("The calculator currently does not account for your equipment set effect.");
		}

		if (this.wearing("Ring of recoil", "Ring of suffering", "Ring of suffering (i)", "Echo boots"))
		{
			issues.add("The calculator does not account for recoil damage.");
		}
	}

	private boolean wearing(String... potentialItems)
	{
		return Arrays.stream(potentialItems)
			.anyMatch(allEquippedItems::contains);
	}

	private boolean wearingAll(String... requiredItems)
	{
		return Arrays.stream(requiredItems)
			.allMatch(allEquippedItems::contains);
	}

	public static double stdRoll(int attack, int defence)
	{
		return attack > defence
			? 1 - (double) (defence + 2) / (2 * (attack + 1))
			: (double) attack / (2 * (defence + 1));
	}

	public static double getNormalAccuracyRoll(int atk, int def)
	{
		if (atk < 0)
		{
			atk = Math.min(0, atk + 2);
		}
		if (def < 0)
		{
			def = Math.min(0, def + 2);
		}

		if (atk >= 0 && def >= 0)
		{
			return stdRoll(atk, def);
		}
		if (atk >= 0 && def < 0)
		{
			return 1 - (double) 1 / (-def + 1) / (atk + 1);
		}
		if (atk < 0 && def >= 0)
		{
			return 0;
		}
		if (atk < 0 && def < 0)
		{
			return stdRoll(-def, -atk);
		}
		return 0;
	}

	public static double rvRoll(int attack, int defence)
	{
		return attack < defence
			? (double) (attack * (defence * 6 - 2 * attack + 5)) / 6 / (defence + 1) / (defence + 1)
			: 1 - (double) ((defence + 2) * (2 * defence + 3)) / 6 / (defence + 1) / (attack + 1);
	}

	public static double getFangAccuracyRoll(int atk, int def)
	{
		if (atk < 0)
		{
			atk = Math.min(0, atk + 2);
		}
		if (def < 0)
		{
			def = Math.min(0, def + 2);
		}

		if (atk >= 0 && def >= 0)
		{
			return stdRoll(atk, def);
		}
		if (atk >= 0 && def < 0)
		{
			return 1 - (double) 1 / (-def + 1) / (atk + 1);
		}
		if (atk < 0 && def >= 0)
		{
			return 0;
		}
		if (atk < 0 && def < 0)
		{
			return rvRoll(-def, -atk);
		}
		return 0;
	}

	public boolean isUsingMeleeStyle()
	{
		return player.getStyle().getType().isMelee();
	}

	public boolean isWearingVoidRobes()
	{
		return this.wearing("Void knight top", "Void knight top (or)", "Elite void top", "Elite void top (or)")
			&& this.wearing("Void knight robe", "Void knight robe (or)", "Elite void robe", "Elite void robe (or)")
			&& this.wearing("Void knight gloves");
	}

	public boolean isWearingEliteVoidRobes()
	{
		return this.wearing("Elite void top", "Elite void top (or)")
			&& this.wearing("Elite void robe", "Elite void robe (or)")
			&& this.wearing("Void knight gloves");
	}

	public boolean isWearingMeleeVoid()
	{
		return this.isWearingVoidRobes() && this.wearing("Void melee helm", "Void melee helm (or)");
	}

	public boolean isWearingEliteRangedVoid()
	{
		return this.isWearingEliteVoidRobes() && this.wearing("Void ranger helm", "Void ranger helm (or)");
	}

	public boolean isWearingEliteMagicVoid()
	{
		return this.isWearingEliteVoidRobes() && this.wearing("Void mage helm", "Void mage helm (or)");
	}

	public boolean isWearingRangedVoid()
	{
		return this.isWearingVoidRobes() && this.wearing("Void ranger helm", "Void ranger helm (or)");
	}

	public boolean isWearingMagicVoid()
	{
		return this.isWearingVoidRobes() && this.wearing("Void mage helm", "Void mage helm (or)");
	}

	public boolean isWearingSlayerHelmet()
	{
		return this.wearing("Slayer helmet", "Slayer helmet (i)");
	}

	public boolean isWearingBlackMask()
	{
		return this.isWearingImbuedBlackMask() || this.wearing("Black mask", "Slayer helmet");
	}

	public boolean isWearingImbuedBlackMask()
	{
		return this.wearing("Black mask (i)", "Slayer helmet (i)");
	}

	public boolean isWearingSmokeStaff()
	{
		return this.wearing("Smoke battlestaff", "Mystic smoke staff", "Twinflame staff");
	}

	public boolean isWearingTzhaarWeapon()
	{
		return this.wearing("Tzhaar-ket-em", "Tzhaar-ket-om", "Tzhaar-ket-om (t)", "Toktz-xil-ak", "Toktz-xil-ek", "Toktz-mej-tal");
	}

	public boolean isWearingObsidian()
	{
		return this.wearingAll("Obsidian helmet", "Obsidian platelegs", "Obsidian platebody");
	}

	public boolean isWearingBerserkerNecklace()
	{
		return this.wearing("Berserker necklace", "Berserker necklace (or)");
	}

	public boolean isWearingCrystalBow()
	{
		return this.wearing("Crystal bow") || this.allEquippedItems.stream().anyMatch(s -> s.contains("Bow of faerdhinen"));
	}

	public boolean isWearingFang()
	{
		return this.wearing("Osmumten's fang", "Osmumten's fang (or)");
	}

	public boolean isWearingAccursedSceptre()
	{
		return this.wearing("Accursed sceptre", "Accursed sceptre (a)");
	}

	public boolean isWearingBlowpipe()
	{
		return this.wearing("Toxic blowpipe", "Blazing blowpipe");
	}

	public boolean isWearingGodsword()
	{
		return this.wearing("Ancient godsword", "Armadyl godsword", "Bandos godsword", "Saradomin godsword", "Zamorak godsword");
	}

	public boolean isWearingScythe()
	{
		return this.wearing("Scythe of vitur")
			|| this.allEquippedItems.stream().anyMatch(s -> s.contains("of vitur"));
	}

	public boolean isWearingTwoHitWeapon()
	{
		return this.wearing(
			"Torag's hammers",
			"Sulphur blades",
			"Glacial temotli"
		);
	}

	public boolean isWearingKeris()
	{
		return this.allEquippedItems.stream().anyMatch(s -> s.contains("Keris"));
	}

	public boolean isWearingDharok()
	{
		return this.wearingAll("Dharok's helm", "Dharok's platebody", "Dharok's platelegs", "Dharok's greataxe");
	}

	public boolean isWearingVeracs()
	{
		return this.wearingAll("Verac's helm", "Verac's brassard", "Verac's plateskirt", "Verac's flail");
	}

	public boolean isWearingKarils()
	{
		return this.wearingAll("Karil's coif", "Karil's leathertop", "Karil's leatherskirt", "Karil's crossbow", "Amulet of the damned");
	}

	public boolean isWearingAhrims()
	{
		return this.wearingAll("Ahrim's staff", "Ahrim's hood", "Ahrim's robetop", "Ahrim's robeskirt", "Amulet of the damned");
	}

	public boolean isWearingTorags()
	{
		return this.wearingAll("Torag's helm", "Torag's platebody", "Torag's platelegs", "Torag's hammers", "Amulet of the damned");
	}

	public boolean isWearingBloodMoonSet()
	{
		return this.wearingAll("Dual macuahuitl", "Blood moon helm", "Blood moon chestplate", "Blood moon tassets");
	}

	public boolean isWearingSilverWeapon()
	{
		EquipmentPiece ammo = player.getEquipment().getAmmo();
		if (ammo != null && ammo.getName().startsWith("Silver bolts")
			&& player.getStyle().getType() == CombatStyleType.RANGED)
		{
			return true;
		}

		return this.isUsingMeleeStyle() && this.wearing(
			"Blessed axe",
			"Ivandis flail",
			"Blisterwood flail",
			"Silver sickle",
			"Silver sickle (b)",
			"Emerald sickle",
			"Emerald sickle (b)",
			"Enchanted emerald sickle (b)",
			"Ruby sickle (b)",
			"Enchanted ruby sickle (b)",
			"Blisterwood sickle",
			"Silverlight",
			"Darklight",
			"Arclight",
			"Rod of ivandis",
			"Wolfbane"
		);
	}

	public boolean wearingVampyrebane(MonsterAttribute tier)
	{
		boolean t2 = tier == VAMPYRE_2;
		if (tier == VAMPYRE_2)
		{
			return this.wearing(
				"Rod of ivandis",
				"Ivandis flail",
				"Blisterwood sickle",
				"Blisterwood flail"
			);
		}

		return this.wearing(
			"Ivandis flail",
			"Blisterwood sickle",
			"Blisterwood flail"
		);
	}

	public boolean isWearingMsb()
	{
		return this.wearing("Magic shortbow", "Magic shortbow (i)");
	}

	public boolean isWearingMlb()
	{
		return this.wearing("Magic longbow", "Magic comp bow");
	}

	public boolean isWearingLeafBladedWeapon()
	{
		if (isUsingMeleeStyle())
		{
			return this.wearing(
				"Leaf-bladed battleaxe",
				"Leaf-bladed spear",
				"Leaf-bladed sword"
			);
		}

		if (player.getStyle().getType() == CombatStyleType.MAGIC)
		{
			Spell spell = player.getSpell();
			return spell != null && spell.getName().equals("Magic Dart");
		}

		if (player.getStyle().getType() == CombatStyleType.RANGED)
		{
			return ammoApplicability() == INCLUDED
				&& this.wearing(
				"Broad arrows",
				"Broad bolts",
				"Amethyst broad bolts"
			);
		}

		return false;
	}

	public boolean isWearingCorpbaneWeapon()
	{
		if (player.getStyle().getType() == CombatStyleType.MAGIC)
		{
			return true;
		}

		EquipmentPiece weapon = player.getEquipment().getWeapon();
		if (weapon == null)
		{
			return false;
		}

		boolean isStab = player.getStyle().getType() == CombatStyleType.STAB;
		if (this.isWearingFang())
		{
			return isStab;
		}

		String weaponName = weapon.getName();
		if (weaponName.endsWith("halberd"))
		{
			return isStab;
		}

		// https://twitter.com/JagexAsh/status/1777673598099968104
		if (weaponName.contains("spear") && !weaponName.equals("Blue moon spear"))
		{
			return isStab;
		}

		return false;
	}

	public boolean isRevWeaponBuffApplicable()
	{
		EquipmentPiece weapon = player.getEquipment().getWeapon();
		if (!player.getBuffs().isInWilderness()
			|| weapon == null
			|| !weapon.getVersion().equals("Charged"))
		{
			return false;
		}

		switch (player.getStyle().getType())
		{
			case MAGIC:
				return this.wearing("Accursed sceptre", "Accursed sceptre (a)", "Thammaron's sceptre", "Thammaron's sceptre (a)");

			case RANGED:
				return this.wearing("Craw's bow", "Webweaver bow");

			default:
				return this.wearing("Ursine chainmace", "Viggora's chainmace");
		}
	}

	public boolean isWearingRatBoneWeapon()
	{
		return this.wearing(
			"Bone mace",
			"Bone shortbow",
			"Bone staff"
		);
	}

	public boolean isChargeSpellApplicable()
	{
		Spell spell = player.getSpell();
		if (spell == null || !player.getBuffs().isChargeSpell())
		{
			return false;
		}

		switch (spell.getName())
		{
			case "Saradomin Strike":
				return this.wearing("Saradomin cape", "Imbued saradomin cape", "Saradomin max cape", "Imbued saradomin max cape");
			case "Claws of Guthix":
				return this.wearing("Guthix cape", "Imbued guthix cape", "Guthix max cape", "Imbued guthix max cape");
			case "Flames of Zamorak":
				return this.wearing("Zamorak cape", "Imbued zamorak cape", "Zamorak max cape", "Imbued zamorak max cape");
			default:
				return false;
		}
	}

	public boolean isUsingDemonbane()
	{
		switch (player.getStyle().getType())
		{
			case MAGIC:
				Spell spell = player.getSpell();
				return spell != null
					&& spell.getName().contains("Demonbane");

			case RANGED:
				return this.wearing("Scorching bow");

			default:
				return this.wearing("Silverlight", "Darklight", "Arclight", "Emberlight", "Burning claws");
		}
	}

	public boolean isUsingAbyssal()
	{
		return isUsingMeleeStyle()
			&& this.wearing("Abyssal bludgeon", "Abyssal dagger", "Abyssal whip", "Abyssal tentacle");
	}

	public boolean tdUnshieldedBonusApplies()
	{
		if (!monster.getName().equals("Tormented Demon") || !monster.getInputs().getPhase().equals("Unshielded"))
		{
			return false;
		}

		switch (player.getStyle().getType())
		{
			case MAGIC:
				return player.getSpell() != null;

			case RANGED:
				return getRangedDamageType(player.getEquipment().getWeapon().getCategory()) == HEAVY;

			case CRUSH:
				return true;

			default:
				return false;
		}
	}

	public Factor inqBonus()
	{
		int inqPieces =
			(this.wearing("Inquisitor's great helm") ? 1 : 0) +
				(this.wearing("Inquisitor's hauberk") ? 1 : 0) +
				(this.wearing("Inquisitor's plateskirt") ? 1 : 0);

		if (this.wearing("Inquisitor's mace"))
		{
			inqPieces *= 5;
		}
		else if (inqPieces == 3)
		{
			inqPieces = 5;
		}

		return Factor.of(200 + inqPieces, 200);
	}

	public Factor crystalBonus(boolean accuracy)
	{
		int crystalPieces =
			(this.wearing("Crystal helm") ? 1 : 0) +
				(this.wearing("Crystal body") ? 3 : 0) +
				(this.wearing("Crystal legs") ? 1 : 0);

		if (accuracy)
		{
			crystalPieces *= 2;
		}

		return Factor.of(40 + crystalPieces, 40);
	}

	public int getNPCDefenceRoll()
	{
		if (opts.getOverrides().getDefenceRoll() != null)
		{
			return opts.getOverrides().getDefenceRoll();
		}

		CombatStyleType defenceStyle = player.getStyle().getType();
		if (opts.isUsingSpecialAttack())
		{
			if (this.wearing(
				"Dragon claws",
				"Dragon dagger",
				"Dragon halberd",
				"Crystal halberd",
				"Abyssal dagger"
			) || this.isWearingGodsword())
			{
				defenceStyle = CombatStyleType.SLASH;
			}
			else if (this.wearing("Arclight", "Emberlight"))
			{
				defenceStyle = CombatStyleType.STAB;
			}
			else if (this.wearing("Voidwaker"))
			{
				// doesn't really matter since it's 100% accuracy but eh
				defenceStyle = CombatStyleType.MAGIC;
			}
		}

		int level = defenceStyle == CombatStyleType.MAGIC && !USES_DEFENCE_LEVEL_FOR_MAGIC_DEFENCE_NPC_IDS.contains(monster.getId())
			? this.monster.getSkills().getMagic()
			: this.monster.getSkills().getDef();


		int effectiveLevel = level + 9;

		int bonus = defenceStyle == CombatStyleType.RANGED
			? monster.getDefensive().of(getRangedDamageType(player.getEquipment().getWeapon().getCategory()))
			: monster.getDefensive().of(defenceStyle);

		int statBonus = 64 + bonus;
		int defenceRoll = effectiveLevel * statBonus;

		boolean isCustomMonster = monster.getId() == -1;
		if ((TOMBS_OF_AMASCUT_MONSTER_IDS.contains(monster.getId()) || isCustomMonster) && monster.getInputs().getToaInvocationLevel() != 0)
		{
			defenceRoll = defenceRoll * (250 + monster.getInputs().getToaInvocationLevel()) / 250;
		}

		return defenceRoll;
	}

	private int getPlayerMaxMeleeAttackRoll()
	{
		PlayerCombatStyle style = player.getStyle();

		int effectiveLevel = player.getSkills().getAtk() + player.getBoosts().getAtk();

		for (Prayer p : getCombatPrayers(Prayer::getFactorAccuracy))
		{
			effectiveLevel = p.getFactorAccuracy().apply(effectiveLevel);
		}

		int stanceBonus = 8;
		if (style.getStance() == ACCURATE)
		{
			stanceBonus += 3;
		}
		else if (style.getStance() == CONTROLLED)
		{
			stanceBonus += 1;
		}

		effectiveLevel += stanceBonus;

		if (isWearingMeleeVoid())
		{
			effectiveLevel = effectiveLevel * 11 / 10;
		}

		int gearBonus = player.getEquipment()
			.getStats()
			.getOffensive()
			.of(style.getType());
		int baseRoll = effectiveLevel * (gearBonus + 64);
		int attackRoll = baseRoll;

		Set<MonsterAttribute> mattrs = monster.getAttributes();
		PlayerBuffs buffs = player.getBuffs();

		// These bonuses do not stack with each other
		if (this.wearing("Amulet of avarice") && monster.getName().startsWith("Revenant"))
		{
			Factor factor = Factor.of(buffs.isForinthrySurge() ? 27 : 24, 20);
			attackRoll = factor.apply(attackRoll);
		}
		else if (this.wearing("Salve amulet (e)", "Salve amulet (ei)") && mattrs.contains(MonsterAttribute.UNDEAD))
		{
			attackRoll = attackRoll * 6 / 5;
		}
		else if (this.wearing("Salve amulet", "Salve amulet (i)") && mattrs.contains(MonsterAttribute.UNDEAD))
		{
			attackRoll = attackRoll * 7 / 6;
		}
		else if (isWearingBlackMask() && buffs.isOnSlayerTask())
		{
			attackRoll = attackRoll * 7 / 6;
		}

		if (isWearingTzhaarWeapon() && this.isWearingObsidian())
		{
			attackRoll += baseRoll / 10;
		}

		if (isRevWeaponBuffApplicable())
		{
			attackRoll = attackRoll * 3 / 2;
		}
		if (this.wearing("Arclight", "Emberlight") && mattrs.contains(MonsterAttribute.DEMON))
		{
			attackRoll = demonbaneFactor(Factor.of(7, 10)).apply(attackRoll);
		}
		if (this.wearing("Burning claws") && mattrs.contains(MonsterAttribute.DEMON))
		{
			attackRoll = demonbaneFactor(Factor.of(1, 20)).apply(attackRoll);
		}
		if (mattrs.contains(MonsterAttribute.DRAGON))
		{
			if (this.wearing("Dragon hunter lance"))
			{
				attackRoll = attackRoll * 6 / 5;
			}
			else if (this.wearing("Dragon hunter wand"))
			{
				attackRoll = attackRoll * 3 / 2;
			}
		}
		if (this.wearing("Keris partisan of breaching") && mattrs.contains(MonsterAttribute.KALPHITE))
		{
			// https://twitter.com/JagexAsh/status/1704107285381787952
			attackRoll = attackRoll * 133 / 100;
		}
		if (this.wearing("Keris partisan of the sun")
			&& TOMBS_OF_AMASCUT_MONSTER_IDS.contains(monster.getId())
			&& monster.getInputs().getCurrentHp() < monster.getSkills().getHp() / 4)
		{
			attackRoll = attackRoll * 5 / 4;
		}
		if (this.wearing("Blisterwood flail", "Blisterwood sickle") && isVampyre(mattrs))
		{
			attackRoll = attackRoll * 21 / 20;
		}
		if (this.isWearingSilverWeapon() && this.wearing("Efaritay's aid") && isVampyre(mattrs))
		{
			attackRoll = attackRoll * 23 / 20; // todo ordering? does this stack multiplicatively with vampyrebane?
		}

		if (style.getType() == CombatStyleType.CRUSH)
		{
			attackRoll = inqBonus().apply(attackRoll);
		}

		if (opts.isUsingSpecialAttack())
		{
			if (this.isWearingGodsword())
			{
				attackRoll = attackRoll * 2;
			}
			else if (this.isWearingFang())
			{
				attackRoll = attackRoll * 3 / 2;
			}
			else if (this.wearing("Elder maul"))
			{
				attackRoll = attackRoll * 5 / 4;
			}
			else if (this.wearing("Dragon dagger"))
			{
				attackRoll = attackRoll * 23 / 20;
			}
			else if (this.wearing("Abyssal dagger"))
			{
				attackRoll = attackRoll * 5 / 4;
			}
			else if (this.wearing("Soulreaper axe"))
			{
				int stacks = Math.max(0, Math.min(5, player.getBuffs().getSoulreaperStacks()));
				attackRoll = attackRoll * (100 + 6 * stacks) / 100;
			}
		}

		return attackRoll;
	}

	public MinMax getPlayerMaxMeleeHit()
	{
		PlayerCombatStyle style = player.getStyle();
		PlayerBuffs buffs = player.getBuffs();

		int baseLevel = player.getSkills().getStr() + player.getBoosts().getStr();
		int effectiveLevel = baseLevel;

		for (Prayer p : getCombatPrayers(Prayer::getFactorStrength))
		{
			if (p == Prayer.BURST_OF_STRENGTH && effectiveLevel <= 20)
			{
				effectiveLevel += 1;
			}
			else
			{
				effectiveLevel = p.getFactorStrength().apply(effectiveLevel);
			}
		}

		if (this.wearing("Soulreaper axe") && !opts.isUsingSpecialAttack())
		{
			// does not stack multiplicatively with prayers
			int stacks = Math.max(0, Math.min(5, buffs.getSoulreaperStacks()));
			int bonus = baseLevel * 6 * stacks;
			effectiveLevel += bonus;
		}

		int stanceBonus = 8;
		if (style.getStance() == AGGRESSIVE)
		{
			stanceBonus += 3;
		}
		else if (style.getStance() == CONTROLLED)
		{
			stanceBonus += 1;
		}

		effectiveLevel += stanceBonus;

		if (isWearingMeleeVoid())
		{
			effectiveLevel = effectiveLevel * 11 / 10;
		}

		int gearBonus = 64 +
			player.getEquipment()
				.getStats()
				.getBonuses()
				.getStr();
		int baseMax = (effectiveLevel * gearBonus + 320) / 640;

		int minHit = 0;
		int maxHit = baseMax;

		Set<MonsterAttribute> mattrs = monster.getAttributes();

		if (this.wearing("Amulet of avarice") && monster.getName().startsWith("Revenant"))
		{
			Factor factor = Factor.of(buffs.isForinthrySurge() ? 27 : 24, 20);
			maxHit = factor.apply(maxHit);
		}
		else if (this.wearing("Salve amulet (e)", "Salve amulet(ei)") && mattrs.contains(MonsterAttribute.UNDEAD))
		{
			maxHit = maxHit * 6 / 5;
		}
		else if (this.wearing("Salve amulet", "Salve amulet(i)") && mattrs.contains(MonsterAttribute.UNDEAD))
		{
			maxHit = maxHit * 7 / 6;
		}
		else if (this.isWearingBlackMask() && buffs.isOnSlayerTask())
		{
			maxHit = maxHit * 7 / 6;
		}

		if (this.wearing("Arclight", "Emberlight") && mattrs.contains(MonsterAttribute.DEMON))
		{
			maxHit = demonbaneFactor(Factor.of(7, 10)).apply(maxHit);
		}
		if (this.wearing("Burning claws") && mattrs.contains(MonsterAttribute.DEMON))
		{
			maxHit = demonbaneFactor(Factor.of(1, 20)).apply(maxHit);
		}
		if (this.isWearingTzhaarWeapon() && this.isWearingObsidian())
		{
			maxHit += baseMax / 10;
		}
		if (this.wearing("Dragon hunter lance", "Dragon hunter wand") && mattrs.contains(MonsterAttribute.DRAGON))
		{
			// still applies to dhw when wand bashing
			maxHit = maxHit * 6 / 5;
		}
		if (this.isWearingKeris() && mattrs.contains(MonsterAttribute.KALPHITE))
		{
			maxHit = maxHit * 133 / 100;
		}
		if (this.wearing("Barronite mace") && mattrs.contains(MonsterAttribute.GOLEM))
		{
			maxHit = maxHit * 23 / 20;
		}
		if (this.isRevWeaponBuffApplicable())
		{
			maxHit = maxHit * 3 / 2;
		}
		if (this.wearing("Silverlight", "Darklight", "Silverlight (dyed)") && mattrs.contains(MonsterAttribute.DEMON))
		{
			maxHit = demonbaneFactor(Factor.of(3, 5)).apply(maxHit);
		}

		if (this.wearing("Leaf-bladed battleaxe") && mattrs.contains(MonsterAttribute.LEAFY))
		{
			maxHit = maxHit * 47 / 40;
		}
		if (this.wearing("Colossal blade"))
		{
			int bonus = Math.min(this.monster.getSize() * 2, 10);
			maxHit += bonus;
		}

		if (this.isWearingRatBoneWeapon() && mattrs.contains(MonsterAttribute.RAT))
		{
			// applies before inq, tested 2024-01-25, str level 99 str gear 112
			maxHit += 10;
		}

		if (style.getType() == CombatStyleType.CRUSH)
		{
			maxHit = inqBonus().apply(maxHit);
		}

		if (this.isWearingFang())
		{
			int shrink = maxHit * 3 / 20;
			minHit = shrink;
			if (!opts.isUsingSpecialAttack()) // not reduced during spec, but min hit is changed as usual
			{
				maxHit -= shrink;
			}
		}

		if (opts.isUsingSpecialAttack())
		{
			if (this.isWearingGodsword())
			{
				maxHit = maxHit * 11 / 10;
			}

			if (this.wearing("Bandos godsword"))
			{
				maxHit = maxHit * 11 / 10;
			}
			else if (this.wearing("Armadyl godsword"))
			{
				maxHit = maxHit * 5 / 4;
			}
			else if (this.wearing("Dragon warhammer"))
			{
				maxHit = maxHit * 3 / 2;
			}
			else if (this.wearing("Voidwaker"))
			{
				minHit = maxHit / 2;
				maxHit += minHit;
			}
			else if (this.wearing("Dragon halberd", "Crystal halberd"))
			{
				maxHit = maxHit * 11 / 10;
			}
			else if (this.wearing("Dragon dagger"))
			{
				maxHit = maxHit * 23 / 20;
			}
			else if (this.wearing("Abyssal dagger"))
			{
				maxHit = maxHit * 17 / 20;
			}
			else if (this.wearing("Abyssal bludgeon"))
			{
				int prayerMissing = Math.max(-player.getBoosts().getPrayer(), 0);
				maxHit = maxHit * (100 + prayerMissing / 2) / 100;
			}
			else if (this.isWearingBloodMoonSet())
			{
				minHit = maxHit / 4;
				maxHit += minHit;
			}
			else if (this.wearing("Soulreaper axe"))
			{
				int stacks = Math.max(0, Math.min(5, buffs.getSoulreaperStacks()));
				maxHit = maxHit * (100 + 6 * stacks) / 100;
			}
		}

		return new MinMax(minHit, maxHit);
	}

	public int getPlayerMaxRangedAttackRoll()
	{
		PlayerCombatStyle style = player.getStyle();

		int effectiveLevel = player.getSkills().getRanged() + player.getBoosts().getRanged();
		for (Prayer p : getCombatPrayers(Prayer::getFactorAccuracy))
		{
			effectiveLevel = p.getFactorAccuracy().apply(effectiveLevel);
		}

		int stanceBonus = 8;
		if (style.getStance() == ACCURATE)
		{
			stanceBonus += 3;
		}
		effectiveLevel += stanceBonus;

		if (isWearingRangedVoid())
		{
			effectiveLevel = effectiveLevel * 11 / 10;
		}

		int gearBonus = 64 +
			player.getEquipment()
				.getStats()
				.getOffensive()
				.getRanged();

		int attackRoll = effectiveLevel * gearBonus;

		if (isWearingCrystalBow())
		{
			attackRoll = crystalBonus(true).apply(attackRoll);
		}

		Set<MonsterAttribute> mattrs = monster.getAttributes();
		PlayerBuffs buffs = player.getBuffs();

		if (this.wearing("Amulet of avarice") && monster.getName().startsWith("Revenant"))
		{
			Factor factor = Factor.of(buffs.isForinthrySurge() ? 27 : 24, 20);
			attackRoll = factor.apply(attackRoll);
		}
		else if (this.wearing("Salve amulet(ei)") && mattrs.contains(MonsterAttribute.UNDEAD))
		{
			attackRoll = attackRoll * 6 / 5;
		}
		else if (this.wearing("Salve amulet(i)") && mattrs.contains(MonsterAttribute.UNDEAD))
		{
			attackRoll = attackRoll * 7 / 6;
		}
		else if (this.isWearingImbuedBlackMask() && buffs.isOnSlayerTask())
		{
			attackRoll = attackRoll * 23 / 20;
		}

		if (this.wearing("Twisted bow"))
		{
			int cap = mattrs.contains(MonsterAttribute.XERICIAN) ? 350 : 250;
			int tbowMagic = Math.min(cap, Math.max(monster.getSkills().getMagic(), monster.getOffensive().getMagic()));
			attackRoll = tbowScaling(attackRoll, tbowMagic, true);
		}

		if (this.isRevWeaponBuffApplicable())
		{
			attackRoll = attackRoll * 3 / 2;
		}
		if (this.wearing("Dragon hunter crossbow") && mattrs.contains(MonsterAttribute.DRAGON))
		{
			// TODO: https://twitter.com/JagexAsh/status/1647928422843273220 for max_hit seems to be additive now
			attackRoll = attackRoll * 13 / 10;
		}
		if (player.getEquipment().getWeapon().getCategory() == EquipmentCategory.CHINCHOMPA)
		{
			attackRoll = chinchompaFactor().apply(attackRoll);
		}

		if (this.wearing("Scorching bow") && mattrs.contains(MonsterAttribute.DEMON))
		{
			attackRoll = demonbaneFactor(Factor.of(3, 10)).apply(attackRoll);
		}

		if (this.opts.isUsingSpecialAttack())
		{
			if (this.wearing("Zaryte crossbow", "Webweaver bow") || this.isWearingBlowpipe())
			{
				attackRoll = attackRoll * 2;
			}
			else if (this.isWearingMsb())
			{
				attackRoll = attackRoll * 10 / 7;
			}
		}

		return attackRoll;
	}

	public MinMax getPlayerMaxRangedHit()
	{
		PlayerCombatStyle style = player.getStyle();

		boolean scalesWithStr = this.wearing("Eclipse atlatl", "Hunter's spear");
		int effectiveLevel = scalesWithStr
			? player.getSkills().getStr() + player.getBoosts().getStr()
			: player.getSkills().getRanged() + player.getBoosts().getRanged();

		// short circuit ignoring a lot of other things
		if (opts.isUsingSpecialAttack() && (isWearingMsb() || isWearingMlb()))
		{
			effectiveLevel += 10;

			EquipmentPiece ammo = player.getEquipment().getAmmo();
			int gearBonus = ammo != null
				? ammo.getStats().getBonuses().getRangedStr()
				: 0;

			int maxHit = (effectiveLevel * (gearBonus + 64) + 320) / 640;
			return new MinMax(0, maxHit);
		}

		for (Prayer p : getCombatPrayers(Prayer::getFactorStrength))
		{
			if (p == Prayer.SHARP_EYE && effectiveLevel <= 20)
			{
				effectiveLevel += 1;
			}
			else
			{
				effectiveLevel = p.getFactorStrength().apply(effectiveLevel);
			}
		}

		int stanceBonus = 8;
		if (style.getStance() == ACCURATE)
		{
			stanceBonus += 3;
		}
		effectiveLevel += stanceBonus;

		if (this.isWearingEliteRangedVoid())
		{
			effectiveLevel = effectiveLevel * 9 / 8;
		}
		else if (this.isWearingRangedVoid())
		{
			effectiveLevel = effectiveLevel * 11 / 10;
		}

		PlayerBonuses bonuses = player.getEquipment().getStats().getBonuses();
		int bonusStr = scalesWithStr
			? bonuses.getStr()
			: bonuses.getRangedStr();

		int baseMax = (effectiveLevel * (bonusStr + 64) + 320) / 640;
		int minHit = 0;
		int maxHit = baseMax;

		if (isWearingCrystalBow())
		{
			maxHit = crystalBonus(false).apply(maxHit);
		}

		Set<MonsterAttribute> mattrs = monster.getAttributes();
		PlayerBuffs buffs = player.getBuffs();

		boolean needRevWeaponBonus = isRevWeaponBuffApplicable();
		boolean needDragonbane = this.wearing("Dragon hunter crossbow") && mattrs.contains(MonsterAttribute.DRAGON);
		boolean needDemonbane = this.wearing("Scorching bow") && mattrs.contains(MonsterAttribute.DEMON);

		if (this.wearing("Amulet of avarice") && monster.getName().startsWith("Revenant"))
		{
			Factor factor = Factor.of(buffs.isForinthrySurge() ? 27 : 24, 20);
			maxHit = factor.apply(maxHit);
		}
		else if ((this.wearing("Salve amulet(ei)") || scalesWithStr && this.wearing("Salve amulet (e)")) && mattrs.contains(MonsterAttribute.UNDEAD))
		{
			maxHit = maxHit * 6 / 5;
		}
		else if ((this.wearing("Salve amulet(i)") || scalesWithStr && this.wearing("Salve amulet")) && mattrs.contains(MonsterAttribute.UNDEAD))
		{
			maxHit = maxHit * 7 / 6;
		}
		else if (scalesWithStr && this.isWearingBlackMask() && buffs.isOnSlayerTask())
		{
			maxHit = maxHit * 7 / 6;
		}
		else if (this.isWearingImbuedBlackMask() && buffs.isOnSlayerTask())
		{
			int numerator = 23;
			// these are additive with slayer only
			if (needRevWeaponBonus)
			{
				needRevWeaponBonus = false;
				numerator += 10;
			}
			if (needDragonbane)
			{
				needDragonbane = false;
				numerator += 5;
			}
			if (needDemonbane)
			{
				needDemonbane = false;
				numerator += 6;
			}
			maxHit = maxHit * numerator / 20;
		}

		if (this.wearing("Twisted bow"))
		{
			int cap = mattrs.contains(MonsterAttribute.XERICIAN) ? 350 : 250;
			int tbowMagic = Math.min(cap, Math.max(monster.getSkills().getMagic(), monster.getOffensive().getMagic()));
			maxHit = tbowScaling(maxHit, tbowMagic, false);
		}

		// multiplicative if not with slayer helm
		if (needRevWeaponBonus)
		{
			maxHit = maxHit * 3 / 2;
		}
		if (needDragonbane)
		{
			maxHit = maxHit * 5 / 4;
		}
		if (needDemonbane)
		{
			maxHit = demonbaneFactor(Factor.of(3, 10)).apply(maxHit);
		}

		if (this.isWearingRatBoneWeapon() && mattrs.contains(MonsterAttribute.RAT))
		{
			maxHit += 10;
		}

		if (this.wearing("Tonalztics of ralos"))
		{
			// rolls 75% of max hit, but can hit twice
			// double hit is implemented in hit distribution
			maxHit = maxHit * 3 / 4;
		}

		if (this.opts.isUsingSpecialAttack())
		{
			if (this.isWearingBlowpipe())
			{
				maxHit = maxHit * 3 / 2;
			}
			else if (this.wearing("Webweaver bow"))
			{
				int maxReduction = maxHit * 6 / 10;
				maxHit -= maxReduction;
			}
			if (this.wearing("Dark bow"))
			{
				boolean descentOfDragons = this.wearing("Dragon arrow");
				minHit = descentOfDragons ? 8 : 5;
				maxHit = maxHit * (descentOfDragons ? 15 : 13) / 10;
			}
		}

		return new MinMax(minHit, maxHit);
	}

	public int getPlayerMaxMagicAttackRoll()
	{
		PlayerCombatStyle style = player.getStyle();

		int effectiveLevel = player.getSkills().getMagic() + player.getBoosts().getMagic();
		for (Prayer p : getCombatPrayers(Prayer::getFactorAccuracy))
		{
			effectiveLevel = p.getFactorAccuracy().apply(effectiveLevel);
		}

		int stanceBonus = 9;
		if (style.getStance() == ACCURATE)
		{
			stanceBonus += 2;
		}
		effectiveLevel += stanceBonus;

		if (isWearingMagicVoid())
		{
			effectiveLevel = effectiveLevel * 29 / 20;
		}

		int gearBonus = player.getEquipment().getStats().getOffensive().getMagic();
		int baseRoll = effectiveLevel * (gearBonus + 64);
		int attackRoll = baseRoll;

		Set<MonsterAttribute> mattrs = monster.getAttributes();
		PlayerBuffs buffs = player.getBuffs();
		Spell spell = player.getSpell();

		int additiveBonus = 0;
		boolean blackMaskBonus = false;

		if (this.wearing("Amulet of avarice") && monster.getName().startsWith("Revenant"))
		{
			additiveBonus += buffs.isForinthrySurge() ? 35 : 20;
		}
		else if (this.wearing("Salve amulet(ei)") && mattrs.contains(MonsterAttribute.UNDEAD))
		{
			additiveBonus += 20;
		}
		else if (this.wearing("Salve amulet(i)") && mattrs.contains(MonsterAttribute.UNDEAD))
		{
			additiveBonus += 15;
		}
		else if (this.isWearingImbuedBlackMask() && buffs.isOnSlayerTask())
		{
			blackMaskBonus = true;
		}

		if (this.wearing("Efaritay's aid") && isVampyre(mattrs) && this.isWearingSilverWeapon())
		{
			// https://x.com/JagexAsh/status/1792829802996498524
			additiveBonus += 15;
		}

		if (this.isWearingSmokeStaff() && spell != null && spell.getSpellbook() == Spellbook.STANDARD)
		{
			// https://twitter.com/JagexAsh/status/1791070064369647838
			additiveBonus += 10;
		}

		if (additiveBonus != 0)
		{
			attackRoll = attackRoll * (100 + additiveBonus) / 100;
		}

		if (mattrs.contains(MonsterAttribute.DRAGON))
		{
			// this still applies to dhl and dhcb when manual casting
			if (this.wearing("Dragon hunter crossbow"))
			{
				attackRoll = attackRoll * 13 / 10;
			}
			else if (this.wearing("Dragon hunter lance"))
			{
				attackRoll = attackRoll * 6 / 5;
			}
			else if (this.wearing("Dragon hunter wand"))
			{
				attackRoll = attackRoll * 3 / 2;
			}
		}

		if (blackMaskBonus)
		{
			attackRoll = attackRoll * 23 / 20;
		}

		if (spell != null && spell.getName().contains("Demonbane") && mattrs.contains(DEMON))
		{
			int numerator = 4;
			if (buffs.isMarkOfDarknessSpell())
			{
				numerator *= 2;
			}
			if (this.wearing("Purging staff"))
			{
				numerator *= 2;
			}
			attackRoll = demonbaneFactor(Factor.of(numerator, 20)).apply(attackRoll);
		}
		if (isRevWeaponBuffApplicable())
		{
			attackRoll = attackRoll * 3 / 2;
		}
		if (this.wearing("Tome of water")
			&& spell != null
			&& (spell.getSpellement() == Spellement.WATER || spell.isBindSpell()))
		{
			attackRoll = attackRoll * 6 / 5;
		}

		if (opts.isUsingSpecialAttack())
		{
			if (this.isWearingAccursedSceptre())
			{
				attackRoll = attackRoll * 3 / 2;
			}
			else if (this.wearing("Volatile nightmare staff"))
			{
				attackRoll = attackRoll * 3 / 2;
			}
		}

		ElementalWeakness weakness = monster.getWeakness();
		if (weakness != null && spell != null && weakness.getElement() == spell.getSpellement())
		{
			int severity = weakness.getSeverity();
			int bonus = baseRoll * severity / 100;
			attackRoll += bonus;
		}

		return attackRoll;
	}

	public MinMax getPlayerMaxMagicHit()
	{
		int magicLevel = player.getSkills().getMagic() + player.getBoosts().getMagic();
		Spell spell = player.getSpell();
		PlayerBuffs buffs = player.getBuffs();
		Set<MonsterAttribute> mattrs = monster.getAttributes();

		int minHit = 0;
		int maxHit = 0;

		if (spell != null)
		{
			maxHit = spell.getMaxHit(magicLevel);
			if (spell.getName().equals("Magic Dart"))
			{
				maxHit = this.wearing("Slayer's staff (e)") && buffs.isOnSlayerTask()
					? 13 + magicLevel / 6
					: 10 + magicLevel / 10;
			}
		}
		else if (this.wearing("Starter staff"))
		{
			maxHit = 8;
		}
		else if (this.wearing("Trident of the seas", "Trident of the seas (e)"))
		{
			maxHit = magicLevel / 3 - 5;
		}
		else if (this.wearing("Thammaron's sceptre"))
		{
			maxHit = magicLevel / 3 - 8;
		}
		else if (this.wearing("Accursed sceptre") || this.wearing("Accursed sceptre (a)") && opts.isUsingSpecialAttack())
		{
			maxHit = magicLevel / 3 - 6;
		}
		else if (this.wearing("Trident of the swamp", "Trident of the swamp (e)"))
		{
			maxHit = magicLevel / 3 - 2;
		}
		else if (this.wearing("Sanguinesti staff", "Holy sanguinesti staff"))
		{
			maxHit = magicLevel / 3 - 1;
		}
		else if (this.wearing("Dawnbringer"))
		{
			if (this.opts.isUsingSpecialAttack())
			{
				// guaranteed hit between 75-150, ignores everything else
				return new MinMax(75, 150);
			}
			maxHit = magicLevel / 6 - 1;
		}
		else if (this.wearing("Tumeken's shadow"))
		{
			maxHit = magicLevel / 3 + 1;
		}
		else if (this.wearing("Warped sceptre"))
		{
			maxHit = (8 * magicLevel + 96) / 37;
		}
		else if (this.wearing("Bone staff"))
		{
			// although the +10 is technically a ratbane bonus, the weapon can't be used against non-rats
			// and shows this max hit against the combat dummy as well
			maxHit = Math.max(1, magicLevel / 3 - 5) + 10;
		}
		else if (this.wearing("Eldritch nightmare staff") && opts.isUsingSpecialAttack())
		{
			maxHit = Math.min(44, 44 * (magicLevel / 99) + 1);
		}
		else if (this.wearing("Volatile nightmare staff") && opts.isUsingSpecialAttack())
		{
			maxHit = Math.min(58, 58 * (magicLevel / 99) + 1);
		}
		else if (this.wearing("Crystal staff (basic)", "Corrupted staff (basic)"))
		{
			maxHit = 23;
		}
		else if (this.wearing("Crystal staff (attuned)", "Corrupted staff (attuned)"))
		{
			maxHit = 31;
		}
		else if (this.wearing("Crystal staff (perfected)", "Corrupted staff (perfected)"))
		{
			maxHit = 39;
		}
		else if (this.wearing("Swamp lizard"))
		{
			maxHit = (magicLevel * (56 + 64) + 320) / 640;
		}
		else if (this.wearing("Orange salamander"))
		{
			maxHit = (magicLevel * (59 + 64) + 320) / 640;
		}
		else if (this.wearing("Red salamander"))
		{
			maxHit = (magicLevel * (77 + 64) + 320) / 640;
		}
		else if (this.wearing("Black salamander"))
		{
			maxHit = (magicLevel * (92 + 64) + 320) / 640;
		}
		else if (this.wearing("Tecu salamander"))
		{
			maxHit = (magicLevel * (104 + 64) + 320) / 640;
		}

		if (maxHit == 0)
		{
			// at this point either they've selected a 0-dmg spell
			// or they picked a staff-casting option without choosing a spell
			return new MinMax(0, 0);
		}

		if (this.wearing("Chaos gauntlets") && spell != null && spell.getName().toLowerCase().contains("bolt"))
		{
			maxHit += 3;
		}
		if (this.isChargeSpellApplicable())
		{
			maxHit += 10;
		}

		// We need the basehit value for the elemental bonus later.
		int baseMax = maxHit;
		int magicDmgBonus = player.getEquipment().getStats().getBonuses().getMagicStr();

		if (this.isWearingSmokeStaff() && spell != null && spell.getSpellbook() == Spellbook.STANDARD)
		{
			magicDmgBonus += 100;
		}

		boolean blackMaskBonus = false;
		if (this.wearing("Salve amulet(ei)") && mattrs.contains(MonsterAttribute.UNDEAD))
		{
			magicDmgBonus += 200;
		}
		else if (this.wearing("Salve amulet(i)") && mattrs.contains(MonsterAttribute.UNDEAD))
		{
			magicDmgBonus += 150;
		}
		else if (this.wearing("Amulet of avarice") && monster.getName().startsWith("Revenant"))
		{
			magicDmgBonus += buffs.isForinthrySurge() ? 350 : 200;
		}
		else if (this.isWearingImbuedBlackMask() && buffs.isOnSlayerTask())
		{
			blackMaskBonus = true;
		}

		for (Prayer p : getCombatPrayers(Prayer::getMagicDamageBonus))
		{
			magicDmgBonus += p.getMagicDamageBonus();
		}

		maxHit += maxHit * magicDmgBonus / 1000;

		if (blackMaskBonus)
		{
			maxHit = maxHit * 23 / 20;
		}

		if (mattrs.contains(MonsterAttribute.DRAGON))
		{
			// this still applies to dhl and dhcb when manual casting
			if (this.wearing("Dragon hunter wand", "Dragon hunter lance"))
			{
				maxHit = maxHit * 6 / 5;
			}
			else if (this.wearing("Dragon hunter crossbow"))
			{
				maxHit = maxHit * 5 / 4;
			}
		}

		if (this.isRevWeaponBuffApplicable())
		{
			maxHit = maxHit * 3 / 2;
		}

		if (opts.isUsingSpecialAttack())
		{
			if (this.isWearingAccursedSceptre())
			{
				maxHit = maxHit * 3 / 2;
			}
		}

		ElementalWeakness weakness = monster.getWeakness();
		if (weakness != null && spell != null && weakness.getElement() == spell.getSpellement())
		{
			int severity = weakness.getSeverity();
			int bonus = baseMax * severity / 100;
			maxHit += bonus;
		}

		if (buffs.isUsingSunfireRunes() && spell != null && spell.canUseSunfireRunes())
		{
			// sunfire runes are applied pre-tome
			minHit = maxHit / 10;
		}

		EquipmentPiece shield = player.getEquipment().getShield();
		if (shield != null && spell != null && spell.getSpellement() != null)
		{
			if ((this.wearing("Tome of fire") && shield.getVersion().equals("Charged") && spell.getSpellement() == Spellement.FIRE)
				|| (this.wearing("Tome of water") && shield.getVersion().equals("Charged") && spell.getSpellement() == Spellement.WATER)
				|| (this.wearing("Tome of earth") && shield.getVersion().equals("Charged") && spell.getSpellement() == Spellement.EARTH))
			{
				maxHit = maxHit * 11 / 10;
			}
		}

		return new MinMax(minHit, maxHit);
	}

	public MinMax getMinAndMax()
	{
		if (player.getStyle().getStance() != MANUAL_CAST
			&& ammoApplicability() == INVALID)
		{
			return new MinMax(0, 0);
		}

		MinMax minMax;
		switch (player.getStyle().getType())
		{
			case MAGIC:
				minMax = getPlayerMaxMagicHit();
				break;
			case RANGED:
				minMax = getPlayerMaxRangedHit();
				break;
			default:
				minMax = getPlayerMaxMeleeHit();
				break;
		}

		// some cursed (literally, cursed amulet of magic) stuff throws this off
		if (minMax.getMin() <= 0)
		{
			minMax = minMax.withMin(0);
		}
		if (minMax.getMax() <= 0)
		{
			minMax = minMax.withMax(0);
		}

		return minMax;
	}

	public int getMaxAttackRoll()
	{
		if (opts.getOverrides().getAttackRoll() != null)
		{
			return opts.getOverrides().getAttackRoll();
		}

		if (player.getStyle().getStance() != MANUAL_CAST
			&& ammoApplicability() == INVALID)
		{
			return 0;
		}

		switch (player.getStyle().getType())
		{
			case MAGIC:
				return getPlayerMaxMagicAttackRoll();
			case RANGED:
				return getPlayerMaxRangedAttackRoll();
			default:
				return getPlayerMaxMeleeAttackRoll();
		}
	}

	public double getHitChance()
	{
		if (opts.getOverrides().getAccuracy() != null)
		{
			return opts.getOverrides().getAccuracy();
		}

		if (VERZIK_P1_IDS.contains(monster.getId()) && this.wearing("Dawnbringer"))
		{
			return 1.0;
		}

		// Giant rat (Scurrius)
		if (monster.getId() == 7223 && player.getStyle().getStance() != MANUAL_CAST)
		{
			return 1.0;
		}

		if (monster.getName().equals("Tormented Demon") && !monster.getInputs().getPhase().equals("Shielded"))
		{
			return 1.0;
		}

		if (TITAN_ELEMENTAL_IDS.contains(monster.getId()) && player.getStyle().getType() == CombatStyleType.MAGIC)
		{
			int magic = player.getEquipment().getStats().getOffensive().getMagic();
			double accuracy = Math.min(1.0, Math.max(0.0, magic) / 100 + 0.3);
			if (isWearingEliteMagicVoid() || isWearingMagicVoid())
			{
				accuracy = Math.min(1.0, accuracy * 1.45);
			}
			return accuracy;
		}

		if (opts.isUsingSpecialAttack() && this.wearing("Voidwaker", "Dawnbringer"))
		{
			return 1.0;
		}

		int atk = getMaxAttackRoll();
		int def = getNPCDefenceRoll();

		double hitChance = getNormalAccuracyRoll(atk, def);
		if (this.wearing("Brimstone ring") && player.getStyle().getType() == CombatStyleType.MAGIC)
		{
			int effectDef = def * 9 / 10;
			double effectHitChance = getNormalAccuracyRoll(atk, effectDef);

			hitChance = (0.75 * hitChance) + (0.25 * effectHitChance);
		}

		if (this.isWearingFang() && player.getStyle().getType() == CombatStyleType.STAB)
		{
			if (TOMBS_OF_AMASCUT_MONSTER_IDS.contains(monster.getId()))
			{
				hitChance = 1 - Math.pow(1 - hitChance, 2);
			}
			else
			{
				hitChance = getFangAccuracyRoll(atk, def);
			}
		}

		return hitChance;
	}

	public double getDoTExpected()
	{
		if (opts.isUsingSpecialAttack())
		{
			if (this.wearing("Burning claws"))
			{
				return burningClawDoT(this.getHitChance());
			}
			else if (this.wearing("Scorching bow"))
			{
				return monster.getAttributes().contains(DEMON) ? 5 : 1;
			}
		}

		return 0;
	}

	public int getDoTMax()
	{
		if (opts.isUsingSpecialAttack())
		{
			if (this.wearing("Burning claws"))
			{
				return 29;
			}
			else if (this.wearing("Scorching bow"))
			{
				return monster.getAttributes().contains(DEMON) ? 5 : 1;
			}
		}

		return 0;
	}

	public int getMax()
	{
		return getDistribution().getMax() + getDoTMax();
	}

	public double getExpectedDamage()
	{
		return getDistribution().getExpectedDamage() + getDoTExpected();
	}

	public int getAttackSpeed()
	{
		if (player.getAttackSpeed() != null)
		{
			return player.getAttackSpeed();
		}

		return calculateAttackSpeed();
	}

	private int calculateAttackSpeed()
	{
		EquipmentPiece weapon = player.getEquipment().getWeapon();
		int attackSpeed = weapon != null ? weapon.getSpeed() : DEFAULT_ATTACK_SPEED;

		CombatStyleStance stance = player.getStyle().getStance();
		if (player.getStyle().getType() == CombatStyleType.RANGED && stance == RAPID)
		{
			attackSpeed -= 1;
		}
		else if (CAST_STANCES.contains(stance))
		{
			if (this.wearing("Harmonised nightmare staff")
				&& player.getSpell() != null
				&& player.getSpell().getSpellbook() == Spellbook.STANDARD
				&& stance != MANUAL_CAST)
			{
				attackSpeed = 4;
			}
			else if (this.wearing("Twinflame staff"))
			{
				attackSpeed = 6;
			}
			else
			{
				attackSpeed = 5;
			}
		}

		// Giant rat (Scurrius)
		if (monster.getId() == 7223 && stance != MANUAL_CAST
			&& weapon != null
			&& BONE_WEAPONS.contains(weapon.getName()))
		{
			attackSpeed = 1;
		}

		return Math.max(attackSpeed, 1);
	}

	private double getExpectedAttackSpeed()
	{
		if (this.isWearingBloodMoonSet())
		{
			double acc = this.getHitChance();
			double procChance = opts.isUsingSpecialAttack()
				? 1 - Math.pow(1 - acc, 2)
				: (acc / 3) + ((acc * acc) * 2 / 9);
			return getAttackSpeed() - procChance;
		}

		if (this.tdUnshieldedBonusApplies())
		{
			return getAttackSpeed() - 1;
		}

		return getAttackSpeed();
	}

	private AttackDistribution getDistributionImpl()
	{
		Set<MonsterAttribute> mattrs = monster.getAttributes();
		PlayerBuffs buffs = player.getBuffs();
		Spell spell = player.getSpell();
		String spellName = spell == null ? null : spell.getName();
		double acc = getHitChance();
		MinMax minMax = getMinAndMax();
		int min = minMax.getMin();
		int max = minMax.getMax();
		CombatStyleType style = player.getStyle().getType();

		HitDistribution standardHitDist = HitDistribution.linear(acc, min, max);
		AttackDistribution dist = new AttackDistribution(standardHitDist);

		if (ONE_HIT_MONSTERS.contains(monster.getId()))
		{
			return new AttackDistribution(
				HitDistribution.single(1.0, new Hitsplat(monster.getSkills().getHp()))
			);
		}

		if ((style == CombatStyleType.MAGIC && ALWAYS_MAX_HIT_MONSTERS_MAGIC.contains(monster.getId()))
			|| (isUsingMeleeStyle() && ALWAYS_MAX_HIT_MONSTERS_MELEE.contains(monster.getId()))
			|| (style == CombatStyleType.RANGED && ALWAYS_MAX_HIT_MONSTERS_RANGED.contains(monster.getId())))
		{
			return new AttackDistribution(
				HitDistribution.single(1.0, new Hitsplat(max))
			);
		}

		EquipmentPiece weapon = player.getEquipment().getWeapon();
		String weaponName = weapon == null ? null : weapon.getName();
		String weaponVersion = weapon == null ? null : weapon.getVersion();
		EquipmentCategory weaponCategory = weapon == null ? null : weapon.getCategory();
		if (style == CombatStyleType.RANGED && wearing("Tonalztics of ralos") && Objects.equals(weaponVersion, "Charged"))
		{
			// roll two independent hits
			if (!opts.isUsingSpecialAttack())
			{
				dist = new AttackDistribution(standardHitDist, standardHitDist);
			}
			else
			{
				double loweredDefHitAccuracy = this.noInitSubCalc(
					this.player, scaleMonster(
						monster.withInputs(
							monster.getInputs().withDefenceReductions(
								monster.getInputs().getDefenceReductions().withTonalztic(
									monster.getInputs().getDefenceReductions().getTonalztic() + 1
								)
							)
						)
					)
				).getHitChance();

				HitDistribution loweredDefHitDist = HitDistribution.linear(loweredDefHitAccuracy, min, max);
				dist = dist.transform((firstHit) ->
				{
					HitDistribution firstHitDist = HitDistribution.single(1.0, firstHit);
					HitDistribution secondHitDist = firstHit.isAccurate() ? loweredDefHitDist : standardHitDist;
					return firstHitDist.zip(secondHitDist);
				});
			}
		}

		if (this.isUsingMeleeStyle() && this.wearing("Gadderhammer") && mattrs.contains(SHADE))
		{
			dist = new AttackDistribution(
				new HitDistribution(
					ImmutableList.<WeightedHit>builder()
						.addAll(standardHitDist.scaleProbability(0.95).scaleDamage(5, 4).getHits())
						.addAll(standardHitDist.scaleProbability(0.05).scaleDamage(2).getHits())
						.build()
				)
			);
		}

		if (style == CombatStyleType.RANGED && this.wearing("Dark bow"))
		{
			dist = new AttackDistribution(standardHitDist, standardHitDist);
			if (opts.isUsingSpecialAttack())
			{
				dist = dist.transform(flatLimitTransformer(48, min));
			}
		}

		boolean accurateZeroApplicable = true;
		if (opts.isUsingSpecialAttack())
		{
			if (this.wearing("Dragon claws"))
			{
				accurateZeroApplicable = false;
				dist = dClawDist(acc, max);
			}
			else if (this.wearing("Burning claws"))
			{
				accurateZeroApplicable = false;
				dist = burningClawSpec(acc, max);
			}
		}

		if (opts.isUsingSpecialAttack() && this.wearing("Dragon halberd", "Crystal halberd") && monster.getSize() > 1)
		{
			int secondHitAttackRoll = getMaxAttackRoll() * 3 / 4;
			double secondHitAcc = this.noInitSubCalc(
				player,
				monster,
				CalcOpts.builder()
					.overrides(CalcOpts.Overrides.builder()
						.attackRoll(secondHitAttackRoll)
						.build())
					.build()
			).getHitChance();

			dist = new AttackDistribution(standardHitDist, HitDistribution.linear(secondHitAcc, min, max));
		}

		if (opts.isUsingSpecialAttack())
		{
			int hitCount = 1;
			if (this.wearing("Dragon dagger", "Abyssal dagger") || this.isWearingMsb())
			{
				hitCount = 2;
			}
			else if (this.wearing("Webweaver bow"))
			{
				hitCount = 4;
			}

			if (hitCount != 1)
			{
				HitDistribution[] dists = new HitDistribution[hitCount];
				Arrays.fill(dists, standardHitDist);
				dist = new AttackDistribution(dists);
			}
		}

		if (this.isUsingMeleeStyle() && this.isWearingVeracs())
		{
			dist = new AttackDistribution(
				new HitDistribution(
					ImmutableList.<WeightedHit>builder()
						.addAll(standardHitDist.scaleProbability(0.75).getHits())
						.addAll(HitDistribution.linear(1.0, 1, max + 1).scaleProbability(0.2).getHits())
						.build()
				)
			);
		}

		if (style == CombatStyleType.RANGED && this.isWearingKarils())
		{
			dist = dist.transform(
				h -> new HitDistribution(
					new WeightedHit(0.75, h),
					new WeightedHit(0.25, h, new Hitsplat(h.getDamage() / 2))
				),
				TransformOpts.builder()
					.transformInaccurate(false)
					.build()
			);
		}

		if (this.isUsingMeleeStyle() && this.isWearingScythe())
		{
			int hits = Math.min(3, Math.max(monster.getSize(), 1));
			HitDistribution[] dists = new HitDistribution[hits];
			for (int i = 0; i < hits; i++)
			{
				int splatMax = max / ((int) Math.pow(2, i));
				dists[i] = HitDistribution.linear(acc, Math.min(min, splatMax), splatMax);
			}
			dist = new AttackDistribution(dists);
		}

		if (this.isUsingMeleeStyle() && this.wearing("Dual macuahuitl"))
		{
			HitDistribution secondHit = HitDistribution.linear(acc, 0, max - (max / 2));
			AttackDistribution firstHit = new AttackDistribution(HitDistribution.linear(acc, 0, max / 2));
			dist = firstHit.transform(h ->
			{
				if (h.isAccurate())
				{
					return HitDistribution.single(1.0, h).zip(secondHit);
				}
				return new HitDistribution(new WeightedHit(1.0, h, Hitsplat.INACCURATE));
			});
		}

		if (this.isUsingMeleeStyle() && this.isWearingTwoHitWeapon())
		{
			dist = new AttackDistribution(
				HitDistribution.linear(acc, 0, max / 2),
				HitDistribution.linear(acc, 0, max - (max / 2))
			);
		}

		if (this.isUsingMeleeStyle() && this.isWearingKeris() && mattrs.contains(KALPHITE))
		{
			dist = new AttackDistribution(
				new HitDistribution(
					ImmutableList.<WeightedHit>builder()
						.addAll(standardHitDist.scaleProbability(50.0 / 51.0).getHits())
						.addAll(standardHitDist.scaleProbability(1.0 / 51.0).scaleDamage(3).getHits())
						.build()
				)
			);
		}

		if (this.isUsingMeleeStyle() && GUARDIAN_IDS.contains(monster.getId()) && weaponCategory == PICKAXE)
		{
			int pickBonus = GUARDIAN_PICKAXE_BONUSES.getOrDefault(weaponName, 61);
			int factor = 50 + player.getSkills().getMining() + pickBonus;
			int divisor = 150;

			dist = dist.transform(multiplyTransformer(factor, divisor));
		}

		if (buffs.isMarkOfDarknessSpell() && spellName != null && spellName.contains("Demonbane") && mattrs.contains(DEMON))
		{
			dist = dist.scaleDamage(this.wearing("Purging staff") ? 6 : 5, 4);
		}

		if (style == CombatStyleType.MAGIC
			&& this.wearing("Twinflame staff")
			&& TWINFLAME_STAFF_SPELL_CLASSES.contains(spellName))
		{
			dist = dist.transform(h -> HitDistribution.single(
				1.0,
				h,
				h.scaleDamage(4, 10)
			));
		}

		if (style == CombatStyleType.MAGIC && this.isWearingAhrims())
		{
			dist = dist.transform(h -> new HitDistribution(
				new WeightedHit(0.75, h),
				new WeightedHit(0.25, h, new Hitsplat(h.getDamage() * 13 / 10, h.isAccurate()))
			));
		}

		if (this.tdUnshieldedBonusApplies())
		{
			int attackSpeed = this.getAttackSpeed();
			int bonusDmg = Math.max(0, (attackSpeed * attackSpeed) - 16);
			dist = dist.transform(
				flatAddTransformer(bonusDmg),
				TransformOpts.builder()
					.transformInaccurate(false)
					.build()
			);
		}

		if (this.isUsingMeleeStyle() && this.isWearingDharok())
		{
			int newMax = player.getSkills().getHp();
			int curr = player.getSkills().getHp() + player.getBoosts().getHp();
			dist = dist.scaleDamage(10000 + (newMax - curr) * newMax, 10000);
		}

		if (this.isUsingMeleeStyle() && this.isWearingBerserkerNecklace() && this.isWearingTzhaarWeapon())
		{
			dist = dist.scaleDamage(6, 5);
		}

		if (isVampyre(mattrs))
		{
			boolean efaritay = this.wearing("Efaritay's aid");
			Function<AttackDistribution, AttackDistribution> doEfaritay =
				(AttackDistribution d) -> (efaritay ? d.scaleDamage(11, 10) : d);

			if (this.wearing("Blisterwood flail"))
			{
				dist = doEfaritay.apply(dist);
				dist = dist.scaleDamage(5, 4);
			}
			else if (this.wearing("Blisterwood sickle"))
			{
				dist = doEfaritay.apply(dist);
				dist = dist.scaleDamage(23, 20);
			}
			else if (this.wearing("Ivandis flail"))
			{
				dist = doEfaritay.apply(dist);
				dist = dist.scaleDamage(6, 5);
			}
			else if (this.wearing("Rod of ivandis") && mattrs.contains(VAMPYRE_3))
			{
				dist = doEfaritay.apply(dist);
				dist = dist.scaleDamage(11, 10);
			}
			else if (this.isWearingSilverWeapon() && mattrs.contains(VAMPYRE_1))
			{
				dist = doEfaritay.apply(dist);
				dist = dist.scaleDamage(11, 10);
			}
		}

		BoltContext boltContext = BoltContext.builder()
			.maxHit(max)
			.rangedLvl(player.getSkills().getRanged() + player.getBoosts().getRanged())
			.zcb(this.wearing("Zaryte crossbow"))
			.spec(opts.isUsingSpecialAttack())
			.kandarinDiary(buffs.isKandarinDiary())
			.monster(monster)
			.build();
		if (style == CombatStyleType.RANGED && weaponCategory == CROSSBOW)
		{
			if (this.wearing("Opal bolts (e)", "Opal dragon bolts (e)"))
			{
				dist = dist.transform(opalBolts(boltContext));
			}
			else if (this.wearing("Pearl bolts (e)", "Pearl dragon bolts (e)"))
			{
				dist = dist.transform(pearlBolts(boltContext));
			}
			else if (this.wearing("Diamond bolts (e)", "Diamond dragon bolts (e)"))
			{
				dist = dist.transform(diamondBolts(boltContext));
			}
			else if (this.wearing("Dragonstone bolts (e)", "Dragonstone dragon bolts (e)"))
			{
				dist = dist.transform(dragonstoneBolts(boltContext));
			}
			else if (this.wearing("Onyx bolts (e)", "Onyx dragon bolts (e)"))
			{
				dist = dist.transform(onyxBolts(boltContext));
			}
		}

		if (spell != null && spell.getBaseMaxHit() == 0)
		{
			// don't raise things like bind
			accurateZeroApplicable = false;
		}

		// raise accurate 0s to 1s
		if (accurateZeroApplicable)
		{
			dist = dist.transform(
				h -> HitDistribution.single(1.0, new Hitsplat(Math.max(h.getDamage(), 1))),
				TransformOpts.builder()
					.transformInaccurate(false)
					.build()
			);
		}

		// we apply corp earlier than other limiters,
		// and rubies later than other bolts,
		// since corp takes full ruby bolt effect damage but reduced damage from bolts otherwise
		if (monster.getName().equals("Corporeal Beast") && !this.isWearingCorpbaneWeapon())
		{
			dist = dist.transform(divisionTransformer(2));
		}

		if (style == CombatStyleType.RANGED && weaponCategory == CROSSBOW)
		{
			int currentHp = player.getSkills().getHp() + player.getBoosts().getHp();
			if (this.wearing("Ruby bolts (e)", "Ruby dragon bolts (e)") && currentHp >= 10)
			{
				dist = dist.transform(rubyBolts(boltContext));
			}
		}

		return dist;
//		return this.applyNpcTransforms(dist);
	}

	public double getDpt()
	{
		return this.getExpectedDamage() / this.getExpectedAttackSpeed();
	}

	public double getDps()
	{
		return this.getDpt() / SECONDS_PER_TICK;
	}

	public int getPrayerTicks()
	{
		int drain = player.getPrayers()
			.stream()
			.mapToInt(Prayer::getDrainRate)
			.sum();

		int drainResistance = 60 + 2 * player.getEquipment().getStats().getBonuses().getPrayer();
		int prayerPoints = player.getSkills().getPrayer() + player.getBoosts().getPrayer();
		int totalPrayerUnits = prayerPoints * drainResistance;

		return (int) Math.ceil(totalPrayerUnits / drain);
	}

	public Duration getPrayerDuration()
	{
		return Duration.ofMillis(600 * getPrayerTicks());
	}

	private DpsCalc noInitSubCalc(Player player, Monster monster, CalcOpts opts)
	{
		DpsCalc subCalc = new DpsCalc(player, monster, opts.withNoInit(true));
		subCalc.allEquippedItems = this.allEquippedItems;
		subCalc.baseMonster = this.baseMonster;

		return subCalc;
	}

	private DpsCalc noInitSubCalc(Player player, Monster monster)
	{
		return noInitSubCalc(player, monster, CalcOpts.builder().build());
	}

	private static int tbowScaling(int current, int magic, boolean accuracyMode)
	{
		int factor = accuracyMode ? 10 : 14;
		int base = accuracyMode ? 140 : 250;

		int t2 = (3 * magic - factor) / 100;
		int t3 = 3 * magic / 10 - 10 * factor;
		t3 = t3 * t3;
		t3 = t3 / 100;

		int bonus = base + t2 - t3;
		return current * bonus / 100;
	}

	private Factor chinchompaFactor()
	{
		int distance = Math.min(7, Math.max(1, player.getBuffs().getChinchompaDistance()));

		int numerator = 4;
		switch (player.getStyle().getName())
		{
			case "Short fuse":
				if (distance >= 7)
				{
					numerator = 2;
				}
				else if (distance >= 4)
				{
					numerator = 3;
				}
				break;
			case "Medium fuse":
				if (distance < 4 || distance >= 7)
				{
					numerator = 3;
				}
				break;
			case "Long fuse":
				if (distance < 4)
				{
					numerator = 2;
				}
				else if (distance < 7)
				{
					numerator = 3;
				}
				break;
		}

		return Factor.of(numerator, 4);
	}

	private AmmoApplicability ammoApplicability()
	{
		PlayerEquipment eq = player.getEquipment();
		int weaponId = eq.getWeapon() != null ? eq.getWeapon().getId() : -1;
		int ammoId = eq.getAmmo() != null ? eq.getAmmo().getId() : -1;
		return AmmoApplicability.ammoApplicability(weaponId, ammoId);
	}

	private Stream<Prayer> getCombatPrayers()
	{
		Stream<Prayer> stream = player.getPrayers()
			.stream();

		switch (player.getStyle().getType())
		{
			case MAGIC:
				stream = stream.filter(p -> p.getCombatStyle() == PrayerCombatStyle.MAGIC);
			case RANGED:
				stream = stream.filter(p -> p.getCombatStyle() == PrayerCombatStyle.RANGED);
			default:
				stream = stream.filter(p -> p.getCombatStyle() == PrayerCombatStyle.MELEE);
		}

		return stream;
	}

	private List<Prayer> getCombatPrayers(Function<Prayer, Factor> selector)
	{
		return getCombatPrayers()
			.filter(p -> selector.apply(p) != null)
			.collect(Collectors.toList());
	}

	private List<Prayer> getCombatPrayers(ToIntFunction<Prayer> selector)
	{
		return getCombatPrayers()
			.filter(p -> selector.applyAsInt(p) != 0)
			.collect(Collectors.toList());
	}

	private Factor demonbaneFactor(Factor baseFactor)
	{
		if (monster.getName().equals("Dule Sucellus"))
		{
			return baseFactor.multiply(Factor.of(7, 10));
		}

		return baseFactor;
	}

	private Integer getSpecCost()
	{
		EquipmentPiece weapon = player.getEquipment().getWeapon();
		if (weapon == null)
		{
			return null;
		}

		return WEAPON_SPEC_COSTS.get(weapon);
	}

	private FeatureStatus isSpecSupported()
	{
		EquipmentPiece weapon = player.getEquipment().getWeapon();
		if (weapon == null)
		{
			return FeatureStatus.NOT_APPLICABLE;
		}

		if (this.wearing("Dual macuahuitl") && !this.isWearingBloodMoonSet())
		{
			return FeatureStatus.NOT_APPLICABLE;
		}

		if (this.wearing("Soulreaper axe"))
		{
			return this.player.getBuffs().getSoulreaperStacks() == 0
				? FeatureStatus.NOT_APPLICABLE
				: FeatureStatus.IMPLEMENTED;
		}

		if (PARTIALLY_IMPLEMENTED_SPECS.contains(weapon.getName()))
		{
			return FeatureStatus.PARTIALLY_IMPLEMENTED;
		}

		if (this.getSpecCost() != null)
		{
			return FeatureStatus.IMPLEMENTED;
		}

		if (UNIMPLEMENTED_SPECS.contains(weapon.getName()))
		{
			return FeatureStatus.UNIMPLEMENTED;
		}

		return FeatureStatus.NOT_APPLICABLE;
	}

}
