package com.duckblade.osrs.dpscalc.plugin.ui.result;

import com.duckblade.osrs.dpscalc.calc.AttackSpeedComputable;
import com.duckblade.osrs.dpscalc.calc.DefenseRollComputable;
import com.duckblade.osrs.dpscalc.calc.DpsComputable;
import com.duckblade.osrs.dpscalc.calc.HitChanceComputable;
import com.duckblade.osrs.dpscalc.calc.TimeToKillComputable;
import com.duckblade.osrs.dpscalc.calc.attack.AttackRollComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.exceptions.DpsComputeException;
import com.duckblade.osrs.dpscalc.calc.exceptions.MissingInputException;
import com.duckblade.osrs.dpscalc.calc.maxhit.MaxHitComputable;
import com.duckblade.osrs.dpscalc.calc.prayer.PrayerDurationRemainingComputable;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.FontManager;
import org.apache.commons.lang3.time.DurationFormatUtils;

@Slf4j
public class CalcResultPanel extends JPanel
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

	private final DpsComputable dpsComputable;

	private final JLabel dpsValue;
	private static final String DPS_CALC_FAIL = "DPS: ???";

	private final List<CalcResultLabel> resultLabels;

	@Inject
	public CalcResultPanel(
		DpsComputable dpsComputable,
		AttackRollComputable attackRollComputable,
		DefenseRollComputable defenseRollComputable,
		MaxHitComputable maxHitComputable,
		HitChanceComputable hitChanceComputable,
		AttackSpeedComputable attackSpeedComputable,
		TimeToKillComputable timeToKillComputable,
		PrayerDurationRemainingComputable prayerDurationRemainingComputable
	)
	{
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
			new CalcResultLabel("Max Hit:", ctx -> String.valueOf(ctx.get(maxHitComputable))),
			new CalcResultLabel("Hit Chance:", ctx -> HIT_CHANCE_FORMAT.format(ctx.get(hitChanceComputable))),

			new CalcResultLabel("Attack Every:", ctx -> HIT_RATE_FORMAT.format(ctx.get(attackSpeedComputable) / 0.6)),
			new CalcResultLabel("Avg TTK:", ctx -> timeFormat(ctx.get(timeToKillComputable))),

			new CalcResultLabel("Prayer Lasts:", ctx -> timeFormat(ctx.get(prayerDurationRemainingComputable)))
		);

		resultLabels.subList(0, 4).forEach(this::add);
		add(Box.createVerticalStrut(10));

		resultLabels.subList(4, 6).forEach(this::add);
		add(Box.createVerticalStrut(10));

		resultLabels.subList(6, 7).forEach(this::add);
	}

	public void setValue(ComputeContext context)
	{
		SwingUtilities.invokeLater(() ->
		{
			try
			{
				double dps = context.get(dpsComputable);
				dpsValue.setText(DPS_FORMAT.format(dps));
				resultLabels.forEach(l -> l.setValue(context));
			}
			catch (DpsComputeException e)
			{
				log.debug("Failed compute: ", e);
				dpsValue.setText(DPS_CALC_FAIL);
				resultLabels.forEach(l -> l.setValue(null));
				if (e.getCause() instanceof MissingInputException)
				{
					// ignore
				}
			}
		});
	}
}
