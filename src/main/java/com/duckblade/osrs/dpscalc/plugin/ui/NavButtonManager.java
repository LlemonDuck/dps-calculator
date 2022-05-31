package com.duckblade.osrs.dpscalc.plugin.ui;

import com.duckblade.osrs.dpscalc.plugin.module.PluginLifecycleComponent;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SwingUtilities;
import lombok.RequiredArgsConstructor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class NavButtonManager implements PluginLifecycleComponent
{

	private final ClientToolbar clientToolbar;
	private final DpsPluginPanel pluginPanel;

	private NavigationButton navButton;

	@Override
	public void startUp()
	{
		navButton = NavigationButton.builder()
			.priority(5)
			.icon(ImageUtil.loadImageResource(getClass(), "equip/slot_0.png"))
			.tooltip("DPS Calculator")
			.panel(pluginPanel)
			.build();
		clientToolbar.addNavigation(navButton);
	}

	@Override
	public void shutDown()
	{
		clientToolbar.removeNavigation(navButton);
		navButton = null;
	}

	public void openPanel()
	{
		SwingUtilities.invokeLater(() ->
		{
			if (!navButton.isSelected())
			{
				navButton.getOnSelect().run();
			}
		});
	}
}
