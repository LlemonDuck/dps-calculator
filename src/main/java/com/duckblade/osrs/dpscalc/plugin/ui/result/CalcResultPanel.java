package com.duckblade.osrs.dpscalc.plugin.ui.result;

import com.duckblade.osrs.dpscalc.calc.AttackSpeedComputable;
import com.duckblade.osrs.dpscalc.calc.defender.DefenseRollComputable;
import com.duckblade.osrs.dpscalc.calc.DpsComputable;
import com.duckblade.osrs.dpscalc.calc.HitChanceComputable;
import com.duckblade.osrs.dpscalc.calc.TimeToKillComputable;
import com.duckblade.osrs.dpscalc.calc.attack.AttackRollComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.exceptions.DpsComputeException;
import com.duckblade.osrs.dpscalc.calc.exceptions.MissingInputException;
import com.duckblade.osrs.dpscalc.calc.maxhit.BaseMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.maxhit.TrueMaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.prayer.PrayerDurationRemainingComputable;
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
	private final DpsComputable dpsComputable;

	private final JLabel dpsValue;
	private static final String DPS_CALC_FAIL = "DPS: ???";

	private final List<CalcResultLabel> resultLabels;
	private final JTextArea warningsLabel;

	@Inject
	public CalcResultPanel(
		PanelStateManager manager,
		DpsComputable dpsComputable,
		AttackRollComputable attackRollComputable,
		DefenseRollComputable defenseRollComputable,
		BaseMaxHitComputable baseMaxHitComputable,
		TrueMaxHitComputable trueMaxHitComputable,
		HitChanceComputable hitChanceComputable,
		AttackSpeedComputable attackSpeedComputable,
		TimeToKillComputable timeToKillComputable,
		PrayerDurationRemainingComputable prayerDurationRemainingComputable
	)
	{
		this.manager = manager;
		this.dpsComputable = dpsComputable;

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
			new CalcResultLabel("Max Attack Roll:", ctx -> ROLL_FORMAT.format(ctx.get(attackRollComputable))),
			new CalcResultLabel("NPC Defense Roll:", ctx -> ROLL_FORMAT.format(ctx.get(defenseRollComputable))),
			new CalcResultLabel("Hit Chance:", ctx -> HIT_CHANCE_FORMAT.format(ctx.get(hitChanceComputable))),

			new CalcResultLabel("Max Hit:", ctx -> String.valueOf(ctx.get(trueMaxHitComputable))),
			new CalcResultLabel("Base Max Hit:", ctx ->
			{
				int baseMaxHit = ctx.get(baseMaxHitComputable);
				if (baseMaxHit < ctx.get(trueMaxHitComputable))
				{
					return String.valueOf(baseMaxHit);
				}
				return null;
			}),

			new CalcResultLabel("Attack Every:", ctx -> HIT_RATE_FORMAT.format(ctx.get(attackSpeedComputable) / 0.6)),
			new CalcResultLabel("Avg TTK:", ctx -> timeFormat(ctx.get(timeToKillComputable))),
			new CalcResultLabel("Prayer Lasts:", ctx -> timeFormat(ctx.get(prayerDurationRemainingComputable)))
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
			ComputeContext ctx = new ComputeContext(getState().toComputeInput());
			double dps = ctx.get(dpsComputable);
			dpsValue.setText(DPS_FORMAT.format(dps));
			resultLabels.forEach(l -> l.setValue(ctx));

			List<String> warnings = ctx.getWarnings();
			warningsLabel.setText(String.join("\n\n", warnings));
			warningsLabel.setVisible(!warnings.isEmpty());
		}
		catch (DpsComputeException e)
		{
			log.debug("Failed compute: ", e);
			clear();

			if (!(e.getCause() instanceof MissingInputException))
			{
				throw e;
			}
		}
	}

	public void clear()
	{
		dpsValue.setText(DPS_CALC_FAIL);
		resultLabels.forEach(l -> l.setValue(null));
		warningsLabel.setVisible(false);
	}
}