package com.duckblade.osrs.dpscalc.plugin.ui.result;

import com.duckblade.osrs.dpscalc.calc.CalcOpts;
import com.duckblade.osrs.dpscalc.calc.DpsCalc;
import com.duckblade.osrs.dpscalc.calc.DpsResultCache;
import com.duckblade.osrs.dpscalc.calc.model.Monster;
import com.duckblade.osrs.dpscalc.calc.model.Player;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import org.apache.commons.lang3.time.DurationFormatUtils;

@Singleton
@Slf4j
public class CalcResultPanel extends JPanel implements StateBoundComponent
{

	private static final DecimalFormat DPS_FORMAT = new DecimalFormat("'DPS:' #.###");
	private static final DecimalFormat ROLL_FORMAT = new DecimalFormat("#,###");
	private static final DecimalFormat HIT_CHANCE_FORMAT = new DecimalFormat("#.#%");
	private static final DecimalFormat HIT_RATE_FORMAT = new DecimalFormat("#.# 'secs'");

	private static String timeFormat(Duration duration)
	{
		if (duration == null)
		{
			return "Infinity";
		}
		return DurationFormatUtils.formatDuration(duration.toMillis(), "mm:ss", true);
	}

	@Getter
	private final PanelStateManager manager;

	private final JLabel dpsValue;
	private static final String DPS_CALC_FAIL = "DPS: ???";

	private final List<CalcResultLabel> resultLabels;
	private final JTextArea warningsLabel;

	@Inject
	public CalcResultPanel(
		PanelStateManager manager
	)
	{
		this.manager = manager;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		Font originalBold = FontManager.getRunescapeBoldFont();
		Font dpsFont = originalBold.deriveFont(originalBold.getSize() * 2f);

		dpsValue = new JLabel(DPS_CALC_FAIL, JLabel.CENTER);
		dpsValue.setAlignmentX(CENTER_ALIGNMENT);
		dpsValue.setForeground(Color.white);
		dpsValue.setFont(dpsFont);
		add(dpsValue);

		add(Box.createVerticalStrut(5));

		resultLabels = Arrays.asList(
			new CalcResultLabel("Max Attack Roll:", dpsResultCache -> ROLL_FORMAT.format(dpsResultCache.getAttackRoll())),
			new CalcResultLabel("NPC Defense Roll:", dpsResultCache -> ROLL_FORMAT.format(dpsResultCache.getDefenceRoll())),
			new CalcResultLabel("Hit Chance:", dpsResultCache -> HIT_CHANCE_FORMAT.format(dpsResultCache.getHitChance())),

			new CalcResultLabel("Max Hit:", dpsResultCache -> String.valueOf(dpsResultCache.getDistribution().getMax())),
			new CalcResultLabel(
				"Base Max Hit:", dpsResultCache ->
			{
				int baseMaxHit = dpsResultCache.getMinMax().getMax();
				if (baseMaxHit != dpsResultCache.getDistribution().getMax())
				{
					return String.valueOf(baseMaxHit);
				}
				return null;
			}
			),

			new CalcResultLabel("Attack Every:", dpsResultCache -> HIT_RATE_FORMAT.format(dpsResultCache.getAttackSpeed() / 0.6)),
			new CalcResultLabel("Avg TTK:", dpsResultCache -> timeFormat(dpsResultCache.getTtkP50())),
			new CalcResultLabel("Prayer Lasts:", dpsResultCache -> timeFormat(dpsResultCache.getPrayerDuration()))
		);

		resultLabels.subList(0, 3).forEach(this::add);
		add(Box.createVerticalStrut(10));

		resultLabels.subList(3, 5).forEach(this::add);
		add(Box.createVerticalStrut(10));

		resultLabels.subList(6, 8).forEach(this::add);
		add(Box.createVerticalStrut(20));

		warningsLabel = new JTextArea();
		warningsLabel.setFont(FontManager.getRunescapeBoldFont().deriveFont(Font.ITALIC));
		warningsLabel.setLineWrap(true);
		warningsLabel.setMaximumSize(new Dimension(PluginPanel.PANEL_WIDTH, 2000));
		warningsLabel.setVisible(false);
		warningsLabel.setFocusable(false);
		warningsLabel.setBackground(ColorScheme.DARK_GRAY_COLOR);
		add(warningsLabel);
	}

	@Override
	public void fromState()
	{
		try
		{
			Player player = getState().getPlayer();
			Monster monster = getState().getMonster();
			if (player == null || monster == null)
			{
				log.debug("Clearing results due to insufficient inputs");
				clear();
				return;
			}

			DpsCalc dpsCalc = new DpsCalc(player, monster, CalcOpts.builder().build());
			DpsResultCache dpsResultCache = new DpsResultCache(dpsCalc);
			double dps = dpsResultCache.getDps();
			dpsValue.setText(DPS_FORMAT.format(dps));
			resultLabels.forEach(l -> l.setValue(dpsResultCache));

			List<String> warnings = dpsCalc.getIssues();
			warningsLabel.setText(String.join("\n\n", warnings));
			warningsLabel.setVisible(!warnings.isEmpty());
		}
		catch (Exception e)
		{
			log.debug("Failed compute: ", e);
			clear();
		}
	}

	public void clear()
	{
		dpsValue.setText(DPS_CALC_FAIL);
		resultLabels.forEach(l -> l.setValue(null));
		warningsLabel.setVisible(false);
	}
}