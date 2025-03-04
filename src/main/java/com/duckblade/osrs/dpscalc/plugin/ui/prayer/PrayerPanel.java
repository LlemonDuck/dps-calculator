package com.duckblade.osrs.dpscalc.plugin.ui.prayer;

import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ClientDataProviderThreadProxy;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.util.LoadFromClientButton;
import com.google.common.collect.ImmutableList;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lombok.Getter;
import net.runelite.client.ui.PluginPanel;

@Singleton
public class PrayerPanel extends JPanel implements StateBoundComponent
{

	@Getter
	private final PanelStateManager manager;
	private final ClientDataProviderThreadProxy clientDataProviderThreadProxy;

	private final JLabel drainLabel;
	private final List<PrayerButton> prayerButtons;

	@Inject
	public PrayerPanel(PanelStateManager manager, ClientDataProviderThreadProxy clientDataProviderThreadProxy)
	{
		this.manager = manager;
		this.clientDataProviderThreadProxy = clientDataProviderThreadProxy;

		add(new LoadFromClientButton(this::loadFromClient));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMinimumSize(new Dimension(PluginPanel.PANEL_WIDTH, 0));

		drainLabel = new JLabel("Total Drain: 0");
		drainLabel.setAlignmentX(CENTER_ALIGNMENT);
		add(drainLabel);

		JPanel prayerGrid = new JPanel(new GridLayout(6, 5, 1, 1));
		prayerGrid.setMinimumSize(new Dimension(182, 219));
		prayerGrid.setPreferredSize(new Dimension(182, 219));
		prayerGrid.setMaximumSize(new Dimension(182, 219));
		add(prayerGrid);

		ImmutableList.Builder<PrayerButton> builder = ImmutableList.builder();
		for (Prayer prayer : Prayer.values())
		{
			PrayerButton panel = new PrayerButton(manager, prayer);
			panel.addCallback(this::calculateDrain);
			prayerGrid.add(panel);
			builder.add(panel);
		}
		prayerButtons = builder.build();
	}

	public void loadFromClient()
	{
		clientDataProviderThreadProxy.tryAcquire(clientDataProvider ->
		{
			getState().getPlayer().setPrayers(clientDataProvider.getPlayer().getPrayers());
			SwingUtilities.invokeLater(this::fromState);
		});
	}

	@Override
	public void fromState()
	{
		prayerButtons.forEach(PrayerButton::fromState);
		calculateDrain();
	}

	private void calculateDrain()
	{
		int drain = getState().getPlayer()
			.getPrayers()
			.stream()
			.mapToInt(Prayer::getDrainRate)
			.sum();

		drainLabel.setText("Total Drain: " + drain);
	}

	public String getSummary()
	{
		Set<Prayer> enabled = getState().getPlayer().getPrayers();
		if (enabled.isEmpty())
		{
			return "None";
		}

		if (enabled.size() == 1)
		{
			return enabled.iterator().next().getName();
		}
		else
		{
			return enabled.size() + " Selected";
		}
	}

}
