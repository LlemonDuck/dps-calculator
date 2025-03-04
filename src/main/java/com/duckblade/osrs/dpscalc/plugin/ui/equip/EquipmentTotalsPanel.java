package com.duckblade.osrs.dpscalc.plugin.ui.equip;

import static com.duckblade.osrs.dpscalc.calc.Constants.DEFAULT_ATTACK_SPEED;
import com.duckblade.osrs.dpscalc.calc.model.EquipmentPiece;
import com.duckblade.osrs.dpscalc.calc.model.Player;
import com.duckblade.osrs.dpscalc.calc.model.PlayerBonuses;
import com.duckblade.osrs.dpscalc.calc.model.PlayerCombatStyleStats;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.function.ToIntFunction;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Getter;
import net.runelite.client.ui.PluginPanel;

@Singleton
public class EquipmentTotalsPanel extends JPanel implements StateBoundComponent
{

	private static class StatLine extends JLabel
	{
		private static final DecimalFormat STAT_LABEL_FORMAT = new DecimalFormat(": #.#");

		private final String title;
		private final ToIntFunction<Player> getter;

		public StatLine(String title, ToIntFunction<Player> getter)
		{
			super(title + ": 0");
			this.title = title;
			this.getter = getter;
		}

		public void update(Player stats)
		{
			setText(title + STAT_LABEL_FORMAT.format(getter.applyAsInt(stats)));
		}
	}

	@Getter
	private final PanelStateManager manager;

	private final Set<StatLine> statLines = new HashSet<>();

	@Inject
	public EquipmentTotalsPanel(PanelStateManager manager)
	{
		this.manager = manager;

		setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(CENTER_ALIGNMENT);
		add(Box.createHorizontalGlue());

		addStyleLine("Stab Accuracy", PlayerCombatStyleStats::getStab);
		addStyleLine("Slash Accuracy", PlayerCombatStyleStats::getSlash);
		addStyleLine("Crush Accuracy", PlayerCombatStyleStats::getCrush);
		addStyleLine("Magic Accuracy", PlayerCombatStyleStats::getMagic);
		addStyleLine("Ranged Accuracy", PlayerCombatStyleStats::getRanged);
		add(Box.createVerticalStrut(10));

		addBonusesLine("Melee Strength", PlayerBonuses::getStr);
		addBonusesLine("Ranged Strength", PlayerBonuses::getRangedStr);
		addBonusesLine("Magic Damage Bonus", PlayerBonuses::getMagicStr);
		add(Box.createVerticalStrut(10));

		addLine("Weapon Speed", p ->
		{
			EquipmentPiece weapon = p.getEquipment().getWeapon();
			return weapon != null ? weapon.getSpeed() : DEFAULT_ATTACK_SPEED;
		});
		addBonusesLine("Prayer", PlayerBonuses::getPrayer);
	}

	private void addLine(String title, ToIntFunction<Player> getter)
	{
		StatLine line = new StatLine(title, getter);
		statLines.add(line);
		add(line);
	}

	private void addStyleLine(String title, ToIntFunction<PlayerCombatStyleStats> getter)
	{
		addLine(title, p -> getter.applyAsInt(p.getEquipment().getStats().getOffensive()));
	}

	private void addBonusesLine(String title, ToIntFunction<PlayerBonuses> getter)
	{
		addLine(title, p -> getter.applyAsInt(p.getEquipment().getStats().getBonuses()));
	}

	@Override
	public void fromState()
	{
		statLines.forEach(sl -> sl.update(getState().getPlayer()));
	}
}
