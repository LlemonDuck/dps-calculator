package com.duckblade.osrs.dpscalc.ui.result;

import com.duckblade.osrs.dpscalc.calc.CalcResult;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.runelite.client.ui.FontManager;

public class CalcResultPanel extends JPanel
{

	private static final DecimalFormat DPS_FORMAT = new DecimalFormat("#.###");
	private static final DecimalFormat ROLL_FORMAT = new DecimalFormat("#,###");
	private static final DecimalFormat HIT_CHANCE_FORMAT = new DecimalFormat("#.#%");
	private static final DecimalFormat HIT_RATE_FORMAT = new DecimalFormat("#.# 'secs'");

	private final JLabel dpsValue;
	private static final String DPS_CALC_FAIL = "???";
	
	private final List<CalcResultLabel> resultLabels;

	@Inject
	public CalcResultPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Font originalBold = FontManager.getRunescapeBoldFont();
		Font dpsFont = originalBold.deriveFont(originalBold.getSize() * 2f);

		JLabel dpsHeader = new JLabel("DPS:", JLabel.CENTER);
		dpsHeader.setAlignmentX(CENTER_ALIGNMENT);
		dpsHeader.setForeground(Color.white);
		dpsHeader.setFont(dpsFont);
		add(dpsHeader);

		dpsValue = new JLabel(DPS_CALC_FAIL, JLabel.CENTER);
		dpsValue.setAlignmentX(CENTER_ALIGNMENT);
		dpsValue.setForeground(Color.white);
		dpsValue.setFont(dpsFont);
		add(dpsValue);

		add(Box.createVerticalStrut(5));

		resultLabels = Arrays.asList(
				new CalcResultLabel("Max Attack Roll:", r -> ROLL_FORMAT.format(r.getAttackRoll())),
				new CalcResultLabel("NPC Defense Roll:", r -> ROLL_FORMAT.format(r.getDefenseRoll())),
				new CalcResultLabel("Max Hit:", r -> String.valueOf(r.getMaxHit())),
				new CalcResultLabel("Hit Chance:", r -> HIT_CHANCE_FORMAT.format(r.getHitChance())),
				new CalcResultLabel("Attack Every:", r -> HIT_RATE_FORMAT.format(r.getHitRate())),
				new CalcResultLabel("Prayer Lasts:", r ->
				{
					int seconds = r.getPrayerSeconds();
					if (seconds == -1)
						return "N/A"; //infinity
					return String.format("%02d:%02d", seconds / 60, seconds % 60);
				})
		);
		resultLabels.forEach(this::add);
	}
	
	public void setValue(CalcResult newValue)
	{
		SwingUtilities.invokeLater(() ->
		{
			if (newValue == null)
			{
				dpsValue.setText(DPS_CALC_FAIL);
				resultLabels.forEach(l -> l.setValue(null));
				return;
			}
			
			dpsValue.setText(DPS_FORMAT.format(newValue.getDps()));
			resultLabels.forEach(l -> l.setValue(newValue));
		});
	}
}
