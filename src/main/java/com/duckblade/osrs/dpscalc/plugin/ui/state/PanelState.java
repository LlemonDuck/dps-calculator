package com.duckblade.osrs.dpscalc.plugin.ui.state;

import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.ComputeInput;
import com.duckblade.osrs.dpscalc.calc.model.DefenderAttributes;
import com.duckblade.osrs.dpscalc.calc.model.DefensiveBonuses;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.duckblade.osrs.dpscalc.calc.model.Skills;
import com.duckblade.osrs.dpscalc.calc.model.Spell;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.NpcData;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.Skill;

// contains all the information for one tab of the ui panel
@Data
public class PanelState
{

	@Data
	public static class MutableDefensiveBonuses
	{
		private int defenseStab = 0;
		private int defenseSlash = 0;
		private int defenseCrush = 0;
		private int defenseRanged = 0;
		private int defenseMagic = 0;

		public DefensiveBonuses toImmutable()
		{
			return DefensiveBonuses.builder()
				.defenseStab(defenseStab)
				.defenseSlash(defenseSlash)
				.defenseCrush(defenseCrush)
				.defenseRanged(defenseRanged)
				.defenseMagic(defenseMagic)
				.build();
		}

		public static MutableDefensiveBonuses fromImmutable(DefensiveBonuses immutable)
		{
			MutableDefensiveBonuses mbd = new MutableDefensiveBonuses();
			if (immutable != null)
			{
				mbd.defenseStab = immutable.getDefenseStab();
				mbd.defenseSlash = immutable.getDefenseSlash();
				mbd.defenseCrush = immutable.getDefenseCrush();
				mbd.defenseRanged = immutable.getDefenseRanged();
				mbd.defenseMagic = immutable.getDefenseMagic();
			}
			return mbd;
		}
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MutableDefenderAttributes
	{
		private int npcId = -1;
		private String name = null;
		private boolean isDemon = false; // demonbane
		private boolean isDragon = false; // dhl/dhcb
		private boolean isKalphite = false; // keris
		private boolean isLeafy = false; // leaf-bladed
		private boolean isUndead = false; // salve
		private boolean isVampyre1 = false; // no immunities
		private boolean isVampyre2 = false; // requires silver weapon
		private boolean isVampyre3 = false; // requires blisterwood
		private int size = 1; // scythe
		private int accuracyMagic = 0; // tbow
		private int raidPartySize = 0;

		public DefenderAttributes toImmutable()
		{
			return DefenderAttributes.builder()
				.npcId(npcId)
				.name(name)
				.isDemon(isDemon)
				.isDragon(isDragon)
				.isKalphite(isKalphite)
				.isLeafy(isLeafy)
				.isUndead(isUndead)
				.isVampyre1(isVampyre1)
				.isVampyre2(isVampyre2)
				.isVampyre3(isVampyre3)
				.size(size)
				.accuracyMagic(accuracyMagic)
				.build();
		}

		public static MutableDefenderAttributes fromImmutable(DefenderAttributes immutable)
		{
			MutableDefenderAttributes mda = new MutableDefenderAttributes();
			if (immutable != null)
			{
				mda.npcId = immutable.getNpcId();
				mda.name = immutable.getName();
				mda.isDemon = immutable.isDemon();
				mda.isDragon = immutable.isDragon();
				mda.isKalphite = immutable.isKalphite();
				mda.isLeafy = immutable.isLeafy();
				mda.isUndead = immutable.isUndead();
				mda.isVampyre1 = immutable.isVampyre1();
				mda.isVampyre2 = immutable.isVampyre2();
				mda.isVampyre3 = immutable.isVampyre3();
				mda.size = immutable.getSize();
				mda.accuracyMagic = immutable.getAccuracyMagic();
			}
			return mda;
		}
	}

	// attacker
	private Map<Skill, Integer> attackerSkills = new HashMap<>();

	private Map<Skill, Integer> attackerBoosts = new HashMap<>();

	private Map<EquipmentInventorySlot, ItemStats> attackerItems = new HashMap<>();

	private Set<Prayer> attackerPrayers = new HashSet<>();

	private AttackStyle attackStyle = null;

	private Spell spell = null;

	private ItemStats blowpipeDarts = null;

	private int attackerDistance = 1;

	// defender
	private Map<Skill, Integer> defenderSkills = new HashMap<>();

	private MutableDefensiveBonuses defenderBonuses = new MutableDefensiveBonuses();

	private MutableDefenderAttributes defenderAttributes = new MutableDefenderAttributes();

	// extras
	private boolean onSlayerTask = false;

	private boolean usingChargeSpell = false;

	private boolean usingMarkOfDarkness = false;

	private boolean inWilderness = false;

	private int raidPartySize = 1;

	public void loadNpcData(NpcData npc)
	{
		if (npc == null)
		{
			defenderSkills = new HashMap<>();
			defenderBonuses = new MutableDefensiveBonuses();
			defenderAttributes = new MutableDefenderAttributes();
		}
		else
		{
			defenderSkills = npc.getSkills() == null ? new HashMap<>() : new HashMap<>(npc.getSkills().getTotals());
			defenderBonuses = MutableDefensiveBonuses.fromImmutable(npc.getDefensiveBonuses());
			defenderAttributes = MutableDefenderAttributes.fromImmutable(npc.getAttributes());
		}
	}

	public ComputeInput toComputeInput()
	{
		return ComputeInput.builder()
			.attackerSkills(Skills.builder()
				.levels(new HashMap<>(attackerSkills))
				.boosts(new HashMap<>(attackerBoosts))
				.build())
			.attackerItems(new HashMap<>(attackerItems))
			.attackerPrayers(new HashSet<>(attackerPrayers))
			.attackStyle(attackStyle.toBuilder().build())
			.spell(spell)
			.blowpipeDarts(blowpipeDarts)
			.attackerDistance(attackerDistance)
			.defenderSkills(Skills.builder()
				.levels(new HashMap<>(defenderSkills))
				.build())
			.defenderBonuses(defenderBonuses.toImmutable())
			.defenderAttributes(defenderAttributes.toImmutable())
			.onSlayerTask(onSlayerTask)
			.usingChargeSpell(usingChargeSpell)
			.usingMarkOfDarkness(usingMarkOfDarkness)
			.inWilderness(inWilderness)
			.raidPartySize(raidPartySize)
			.build();
	}

	public PanelState()
	{

	}

	// copy constructor
	public PanelState(PanelState original)
	{
		this.attackerBoosts.putAll(original.attackerBoosts);
		this.attackerDistance = original.attackerDistance;
		this.attackerItems.putAll(original.attackerItems);
		this.attackerPrayers.addAll(original.attackerPrayers);
		this.attackerSkills.putAll(original.attackerSkills);
		this.attackStyle = original.attackStyle;
		this.blowpipeDarts = original.blowpipeDarts;
		this.defenderAttributes = MutableDefenderAttributes.fromImmutable(original.defenderAttributes.toImmutable());
		this.defenderBonuses = MutableDefensiveBonuses.fromImmutable(original.defenderBonuses.toImmutable());
		this.defenderSkills.putAll(original.defenderSkills);
		this.inWilderness = original.inWilderness;
		this.onSlayerTask = original.onSlayerTask;
		this.raidPartySize = original.raidPartySize;
		this.spell = original.spell;
		this.usingChargeSpell = original.usingChargeSpell;
		this.usingMarkOfDarkness = original.usingMarkOfDarkness;
	}
}
