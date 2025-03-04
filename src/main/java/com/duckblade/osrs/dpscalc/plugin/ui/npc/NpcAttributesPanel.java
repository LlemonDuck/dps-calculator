package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.calc.model.Monster;
import com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.DEMON;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.DRAGON;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.KALPHITE;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.LEAFY;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.UNDEAD;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.VAMPYRE_1;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.VAMPYRE_2;
import static com.duckblade.osrs.dpscalc.calc.model.MonsterAttribute.VAMPYRE_3;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ComputeInput;
import com.duckblade.osrs.dpscalc.plugin.ui.skills.StatCategory;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.StateBoundComponent;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJCheckBox;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundStatBox;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import lombok.Getter;

@Singleton
public class NpcAttributesPanel extends JPanel implements StateBoundComponent
{

	private static ObjIntConsumer<ComputeInput> writer(ObjIntConsumer<Monster> inner)
	{
		return (state, value) -> inner.accept(state.getMonster(), value);
	}

	private static ToIntFunction<ComputeInput> reader(ToIntFunction<Monster> inner)
	{
		return state -> inner.applyAsInt(state.getMonster());
	}

	private static BiConsumer<ComputeInput, Boolean> writer(MonsterAttribute mattr)
	{
		return (state, value) ->
		{
			Set<MonsterAttribute> mattrs = state.getMonster().getAttributes();
			if (value)
			{
				mattrs.add(mattr);
			}
			else
			{
				mattrs.remove(mattr);
			}
		};
	}

	private static Predicate<ComputeInput> reader(MonsterAttribute mattr)
	{
		return state -> state.getMonster().getAttributes().contains(mattr);
	}

	@Getter
	private final PanelStateManager manager;
	private final List<StateBoundStatBox> numericalAttrBoxes = new ArrayList<>(5);
	private final List<StateBoundJCheckBox> booleanAttrBoxes = new ArrayList<>(6);

	@Inject
	public NpcAttributesPanel(PanelStateManager manager)
	{
		this.manager = manager;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(200, 300));
		setMaximumSize(new Dimension(200, 300));

		numericalAttrBoxes.add(new StateBoundStatBox(
			manager,
			"id",
			"NPC ID",
			false,
			writer(Monster::setId),
			reader(Monster::getId)
		));
		numericalAttrBoxes.add(new StateBoundStatBox(
			manager,
			"amagic",
			"Magic Acc.",
			false,
			writer((m, v) -> m.getOffensive().setMagic(v)),
			reader(m -> m.getOffensive().getMagic())
		));
		numericalAttrBoxes.add(new StateBoundStatBox(
			manager,
			"size",
			"Size",
			false,
			writer(Monster::setSize),
			reader(Monster::getSize)
		));
		numericalAttrBoxes.add(new StateBoundStatBox(
			manager,
			"flat_armour",
			"Flat Armour",
			false,
			writer((m, v) -> m.getDefensive().setFlatArmour(v)),
			reader(m -> m.getDefensive().getFlatArmour())
		));
		add(new StatCategory("Defensive Bonuses", numericalAttrBoxes));
		add(Box.createVerticalStrut(5));

		JPanel booleanAttrPanel = new JPanel();
		booleanAttrPanel.setLayout(new GridLayout(4, 2));
		add(booleanAttrPanel);

		booleanAttrBoxes.add(new StateBoundJCheckBox(
			"Demon",
			manager,
			writer(DEMON),
			reader(DEMON)
		));
		booleanAttrBoxes.add(new StateBoundJCheckBox(
			"Dragon",
			manager,
			writer(DRAGON),
			reader(DRAGON)
		));
		booleanAttrBoxes.add(new StateBoundJCheckBox(
			"Kalphite",
			manager,
			writer(KALPHITE),
			reader(KALPHITE)
		));
		booleanAttrBoxes.add(new StateBoundJCheckBox(
			"Leafy",
			manager,
			writer(LEAFY),
			reader(LEAFY)
		));
		booleanAttrBoxes.add(new StateBoundJCheckBox(
			"Undead",
			manager,
			writer(UNDEAD),
			reader(UNDEAD)
		));
		booleanAttrBoxes.add(new StateBoundJCheckBox(
			"T1 Vampyre",
			manager,
			writer(VAMPYRE_1),
			reader(VAMPYRE_1)
		));
		booleanAttrBoxes.add(new StateBoundJCheckBox(
			"T2 Vampyre",
			manager,
			writer(VAMPYRE_2),
			reader(VAMPYRE_2)
		));
		booleanAttrBoxes.add(new StateBoundJCheckBox(
			"T3 Vampyre",
			manager,
			writer(VAMPYRE_3),
			reader(VAMPYRE_3)
		));
		booleanAttrBoxes.forEach(sbsb -> sbsb.setEditable(false));
		booleanAttrBoxes.forEach(booleanAttrPanel::add);
	}

	@Override
	public void toState()
	{
		numericalAttrBoxes.forEach(StateBoundStatBox::toState);
	}

	@Override
	public void fromState()
	{
		setPreferredSize(new Dimension(200, 300));
		setMaximumSize(new Dimension(200, 300));
		numericalAttrBoxes.forEach(StateBoundStatBox::fromState);
	}

	public void setEditable(boolean editable)
	{
		numericalAttrBoxes.forEach(sbsb -> sbsb.setEditable(editable));
		booleanAttrBoxes.forEach(sbsb -> sbsb.setEditable(editable));
	}
}
