package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.calc.model.MonsterDefensive;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ComputeInput;
import com.duckblade.osrs.dpscalc.plugin.ui.skills.StatCategory;
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

@Singleton
public class NpcBonusesPanel extends JPanel implements StateBoundComponent
{

	private static ObjIntConsumer<ComputeInput> writer(ObjIntConsumer<MonsterDefensive> inner)
	{
		return (state, value) -> inner.accept(state.getMonster().getDefensive(), value);
	}

	private static ToIntFunction<ComputeInput> reader(ToIntFunction<MonsterDefensive> inner)
	{
		return state -> inner.applyAsInt(state.getMonster().getDefensive());
	}

	@Getter
	private final PanelStateManager manager;
	private final List<StateBoundStatBox> statBoxes = new ArrayList<>(5);

	@Inject
	public NpcBonusesPanel(PanelStateManager manager)
	{
		this.manager = manager;

		statBoxes.add(new StateBoundStatBox(
			manager,
			"dstab",
			"Stab",
			false,
			writer(MonsterDefensive::setStab),
			reader(MonsterDefensive::getStab)
		));
		statBoxes.add(new StateBoundStatBox(
			manager,
			"dslash",
			"Slash",
			false,
			writer(MonsterDefensive::setSlash),
			reader(MonsterDefensive::getSlash)
		));
		statBoxes.add(new StateBoundStatBox(
			manager,
			"dcrush",
			"Crush",
			false,
			writer(MonsterDefensive::setCrush),
			reader(MonsterDefensive::getCrush)
		));
		statBoxes.add(new StateBoundStatBox(
			manager,
			"dlight",
			"Light",
			false,
			writer(MonsterDefensive::setLight),
			reader(MonsterDefensive::getLight)
		));
		statBoxes.add(new StateBoundStatBox(
			manager,
			"dstandard",
			"Standard",
			false,
			writer(MonsterDefensive::setStandard),
			reader(MonsterDefensive::getStandard)
		));
		statBoxes.add(new StateBoundStatBox(
			manager,
			"dheavy",
			"Heavy",
			false,
			writer(MonsterDefensive::setHeavy),
			reader(MonsterDefensive::getHeavy)
		));
		statBoxes.add(new StateBoundStatBox(
			manager,
			"dmagic",
			"Magic",
			false,
			writer(MonsterDefensive::setMagic),
			reader(MonsterDefensive::getMagic)
		));
		add(new StatCategory("Defensive Bonuses", statBoxes));

		add(Box.createVerticalStrut(5));
		setMaximumSize(new Dimension(200, 134));
	}

	@Override
	public void toState()
	{
		statBoxes.forEach(StateBoundStatBox::toState);
	}

	@Override
	public void fromState()
	{
		statBoxes.forEach(StateBoundStatBox::fromState);
	}

	public void setEditable(boolean editable)
	{
		statBoxes.forEach(sbsb -> sbsb.setEditable(editable));
	}
}
