package com.duckblade.osrs.dpscalc.ui.skills;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import net.runelite.client.util.ImageUtil;

public class SkillBoostPresetButton extends JButton
{
	
	public SkillBoostPresetButton(SkillsPanel skillsPanel, SkillBoostPreset boostPreset, String iconPath)
	{
		super(new ImageIcon(ImageUtil.loadImageResource(SkillBoostPresetButton.class, iconPath)));
		setToolTipText(boostPreset.getDisplayName());
		addActionListener(ignored -> skillsPanel.applyBoostPreset(boostPreset));
	}
	
}
