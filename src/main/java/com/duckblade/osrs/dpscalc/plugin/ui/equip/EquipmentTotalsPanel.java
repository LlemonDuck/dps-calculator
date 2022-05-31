package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import com.duckblade.osrs.dpscalc.calc.AttackerItemStatsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.ItemStats;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.util.ComputeUtil;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.function.ToDoubleFunction;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Getter;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.client.ui.PluginPanel;

@Singleton
public class EquipmentTotalsPanel extends JPanel implements StateBoundComponent
{

	private static class StatLine extends JLabel
	{
		private static final DecimalFormat STAT_LABEL_FORMAT = new DecimalFormat(": #.#");

		private final String title;
		private final ToDoubleFunction<ItemStats> getter;

		public StatLine(String title, ToDoubleFunction<ItemStats> getter)
		{
			super(title + ": 0");
			this.title = title;
			this.getter = getter;
		}

		public void update(ItemStats itemStats)
		{
			setText(title + STAT_LABEL_FORMAT.format(getter.applyAsDouble(itemStats)));
		}
	}

	@Getter
	private final PanelStateManager manager;
	private final AttackerItemStatsComputable attackerItemStatsComputable;

	private final Set<StatLine> statLines = new HashSet<>();

	@Inject
	public EquipmentTotalsPanel(PanelStateManager manager, AttackerItemStatsComputable attackerItemStatsComputable)
	{
		this.manager = manager;
		this.attackerItemStatsComputable = attackerItemStatsComputable;

		setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(CENTER_ALIGNMENT);
		add(Box.createHorizontalGlue());

		addLine("Stab Accuracy", ItemStats::getAccuracyStab);
		addLine("Slash Accuracy", ItemStats::getAccuracySlash);
		addLine("Crush Accuracy", ItemStats::getAccuracyCrush);
		addLine("Magic Accuracy", ItemStats::getAccuracyMagic);
		addLine("Ranged Accuracy", ItemStats::getAccuracyRanged);
		add(Box.createVerticalStrut(10));

		addLine("Melee Strength", ItemStats::getStrengthMelee);
		addLine("Ranged Strength", ItemStats::getStrengthRanged);
		addLine("Magic Damage Bonus", ItemStats::getStrengthMagic);
		add(Box.createVerticalStrut(10));

		addLine("Weapon Speed", ItemStats::getSpeed);
		addLine("Prayer", ItemStats::getPrayer);
	}

	private void addLine(String title, ToDoubleFunction<ItemStats> getter)
	{
		StatLine line = new StatLine(title, getter);
		statLines.add(line);
		add(line);
	}

	@Override
	public void fromState()
	{
		ComputeUtil.computeSilent(() ->
		{
			// force an attack style so that the stats panel updates whether the user has selected one or not
			AttackStyle attackStyle = getState().getAttackStyle();
			if (attackStyle == null)
			{
				attackStyle = getState().getAttackerItems()
					.getOrDefault(EquipmentInventorySlot.WEAPON, ItemStats.EMPTY)
					.getWeaponCategory()
					.getAttackStyles()
					.get(0);
			}

			ComputeContext ctx = new ComputeContext();

			ctx.put(ComputeInputs.ATTACKER_ITEMS, getState().getAttackerItems());
			ctx.put(ComputeInputs.ATTACK_STYLE, attackStyle);
			ctx.put(ComputeInputs.BLOWPIPE_DARTS, getState().getBlowpipeDarts());

			ItemStats aggregate = ctx.get(attackerItemStatsComputable);
			statLines.forEach(sl -> sl.update(aggregate));
		});
	}
}
