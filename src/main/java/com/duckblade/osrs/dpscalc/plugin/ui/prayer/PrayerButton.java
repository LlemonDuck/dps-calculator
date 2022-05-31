package com.duckblade.osrs.dpscalc.plugin.ui.prayer;

import com.duckblade.osrs.dpscalc.calc.model.Prayer;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import lombok.Getter;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.SwingUtil;

public class PrayerButton extends JPanel implements StateBoundComponent
{

	private static final Image SELECTED_BACKGROUND = ImageUtil.loadImageResource(PrayerButton.class, "activated_background.png");
	private static final int WIDTH = 34;
	private static final int HEIGHT = 34;

	private static BufferedImage createSelectedGraphic(Image prayerIcon)
	{
		// create selectedIcon from SELECTED_BACKGROUND + deselectedIcon
		BufferedImage selectedIcon = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = selectedIcon.getGraphics();
		g.drawImage(SELECTED_BACKGROUND, 0, 0, null);
		g.drawImage(prayerIcon, 0, 0, null);
		g.dispose();

		return selectedIcon;
	}

	@Getter
	private final Prayer prayer;

	@Getter
	private final PanelStateManager manager;

	private final ImageIcon selectedIcon;
	private final ImageIcon deselectedIcon;

	private final JButton button;
	private boolean selected;

	private final List<Runnable> callbacks = new ArrayList<>();

	public PrayerButton(PanelStateManager manager, Prayer prayer)
	{
		this.manager = manager;
		this.prayer = prayer;
		callbacks.add(this::toState);

		String iconFileName = prayer.name().toLowerCase() + ".png";
		BufferedImage deselected = ImageUtil.loadImageResource(getClass(), iconFileName);
		this.deselectedIcon = new ImageIcon(deselected);
		this.selectedIcon = new ImageIcon(createSelectedGraphic(deselected));

		button = new JButton(deselectedIcon);
		SwingUtil.removeButtonDecorations(button);
		button.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		button.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		button.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		button.addActionListener(e ->
		{
			setSelected(!selected);
			callbacks.forEach(Runnable::run);
		});
		add(button);
	}

	private void setSelected(boolean newValue)
	{
		selected = newValue;
		button.setIcon(selected ? selectedIcon : deselectedIcon);
	}

	@Override
	public void toState()
	{
		if (selected)
		{
			getState().getAttackerPrayers().add(prayer);
		}
		else
		{
			getState().getAttackerPrayers().remove(prayer);
		}
	}

	@Override
	public void fromState()
	{
		setSelected(getState().getAttackerPrayers().contains(prayer));
	}

	public void addCallback(Runnable r)
	{
		callbacks.add(r);
	}
}
