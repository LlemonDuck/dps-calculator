package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.plugin.ui.skills.StatCategory;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundStatBox;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ObjIntConsumer;
import java.util.function.ToIntFunction;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.JPanel;
import lombok.Getter;
import net.runelite.api.Skill;

@Singleton
public class NpcSkillsPanel extends JPanel implements StateBoundComponent
{

	private static ObjIntConsumer<PanelState> writer(Skill skill)
	{
		return (state, lvl) -> state.getDefenderSkills().put(skill, lvl);
	}

	private static ToIntFunction<PanelState> reader(Skill skill)
	{
		return state -> state.getDefenderSkills().getOrDefault(skill, 0);
	}

	@Getter
	private final PanelStateManager manager;
	private final List<StateBoundStatBox> skillBoxes = new ArrayList<>(6);

	@Inject
	public NpcSkillsPanel(PanelStateManager manager)
	{
		this.manager = manager;

		skillBoxes.add(new StateBoundStatBox(manager, "hitpoints", "Hitpoints", false, writer(Skill.HITPOINTS), reader(Skill.HITPOINTS)));
		skillBoxes.add(new StateBoundStatBox(manager, "att", "Attack", false, writer(Skill.ATTACK), reader(Skill.ATTACK)));
		skillBoxes.add(new StateBoundStatBox(manager, "str", "Strength", false, writer(Skill.STRENGTH), reader(Skill.STRENGTH)));
		skillBoxes.add(new StateBoundStatBox(manager, "def", "Defence", false, writer(Skill.DEFENCE), reader(Skill.DEFENCE)));
		skillBoxes.add(new StateBoundStatBox(manager, "mage", "Magic", false, writer(Skill.MAGIC), reader(Skill.MAGIC)));
		skillBoxes.add(new StateBoundStatBox(manager, "range", "Ranged", false, writer(Skill.RANGED), reader(Skill.RANGED)));
		add(new StatCategory("Combat Stats", skillBoxes));
		add(Box.createVerticalStrut(5));
		setMaximumSize(new Dimension(200, 134));
	}

	@Override
	public void toState()
	{
		skillBoxes.forEach(StateBoundStatBox::toState);
	}

	@Override
	public void fromState()
	{
		skillBoxes.forEach(StateBoundStatBox::fromState);
	}

	public void setEditable(boolean editable)
	{
		skillBoxes.forEach(sbsb -> sbsb.setEditable(editable));
	}
}
