package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ComputeInput;
import com.duckblade.osrs.dpscalc.plugin.ui.skills.StatCategory;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundStatBox;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

	private static ObjIntConsumer<ComputeInput> writer(Skill skill)
	{
		return (state, lvl) -> state.getMonster().getSkills().set(skill, lvl);
	}

	private static ToIntFunction<ComputeInput> reader(Skill skill)
	{
		return state -> state.getMonster().getSkills().get(skill);
	}

	@Getter
	private final PanelStateManager manager;
	private final Map<Skill, StateBoundStatBox> skillBoxes = new HashMap<>(6);

	@Inject
	public NpcSkillsPanel(PanelStateManager manager)
	{
		this.manager = manager;

		skillBoxes.put(Skill.HITPOINTS, new StateBoundStatBox(manager, "hp", "Hitpoints", false, writer(Skill.HITPOINTS), reader(Skill.HITPOINTS)));
		skillBoxes.put(Skill.ATTACK, new StateBoundStatBox(manager, "att", "Attack", false, writer(Skill.ATTACK), reader(Skill.ATTACK)));
		skillBoxes.put(Skill.STRENGTH, new StateBoundStatBox(manager, "str", "Strength", false, writer(Skill.STRENGTH), reader(Skill.STRENGTH)));
		skillBoxes.put(Skill.DEFENCE, new StateBoundStatBox(manager, "def", "Defence", false, writer(Skill.DEFENCE), reader(Skill.DEFENCE)));
		skillBoxes.put(Skill.MAGIC, new StateBoundStatBox(manager, "mage", "Magic", false, writer(Skill.MAGIC), reader(Skill.MAGIC)));
		skillBoxes.put(Skill.RANGED, new StateBoundStatBox(manager, "range", "Ranged", false, writer(Skill.RANGED), reader(Skill.RANGED)));
		add(new StatCategory("Combat Stats", new ArrayList<>(skillBoxes.values())));
		add(Box.createVerticalStrut(5));
		setMaximumSize(new Dimension(200, 134));
	}

	@Override
	public void toState()
	{
		skillBoxes.values().forEach(StateBoundStatBox::toState);
	}

	@Override
	public void fromState()
	{
		skillBoxes.values().forEach(StateBoundStatBox::fromState);
	}

	public void setEditable(boolean editable)
	{
		skillBoxes.values().forEach(sbsb -> sbsb.setEditable(editable));
	}
}
