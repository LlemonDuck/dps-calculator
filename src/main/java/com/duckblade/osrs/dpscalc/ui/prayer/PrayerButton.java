package com.duckblade.osrs.dpscalc.ui.prayer;

import com.duckblade.osrs.dpscalc.model.Prayer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.SwingUtil;

public class PrayerButton extends JPanel
{

	private static final Image SELECTED_BACKGROUND =
			ImageUtil.loadImageResource(PrayerButton.class, "activated_background.png");
	private static final int WIDTH = 34;
	private static final int HEIGHT = 34;

	@Getter
	private final Prayer prayer;

	private final ImageIcon selectedIcon;
	private final ImageIcon deselectedIcon;

	private final JButton button;
	
	@Setter
	private Consumer<PrayerButton> callback;

	@Getter
	private boolean selected;

	public PrayerButton(Prayer prayer)
	{
		this.prayer = prayer;
		String iconFileName = prayer.getDisplayName().toLowerCase().replace(" ", "_") + ".png";
		BufferedImage deselected = ImageUtil.loadImageResource(getClass(), iconFileName);
		this.deselectedIcon = new ImageIcon(deselected);
		this.selectedIcon = new ImageIcon(createSelectedGraphic(deselected));

		button = new JButton(deselectedIcon);
		SwingUtil.removeButtonDecorations(button);
		button.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		button.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		button.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		button.addActionListener(e -> setSelected(!selected));
		add(button);
	}
	
	public void setSelected(boolean newValue)
	{
		if (this.selected != newValue)
		{
			this.selected = newValue;
			button.setIcon(selected ? selectedIcon : deselectedIcon);
			if (callback != null)
				callback.accept(this);
		}
	}

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

}
