package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.calc.model.Monster;
import com.duckblade.osrs.dpscalc.plugin.osdata.clientdata.ComputeInput;
import static com.duckblade.osrs.dpscalc.plugin.osdata.wiki.WikiDataProvider.ALL_MONSTERS;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJComboBox;
import java.awt.Dimension;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.BorderFactory;

@Singleton
public class MonsterSelectPanel extends StateBoundJComboBox<Monster>
{

	@Inject
	public MonsterSelectPanel(PanelStateManager manager)
	{
		super(
			ALL_MONSTERS.values()
				.stream()
				.sorted(Comparator.comparing(Monster::getName))
				.collect(Collectors.toList()),
			Monster::getName,
			null,
			manager,
			(ps, v) -> ps.loadMonster(ALL_MONSTERS.get(v.getId())),
			ps -> ALL_MONSTERS.get(ps.getMonster().getId())
		);

		setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
		setPreferredSize(new Dimension(200, 25));
		enableAutocomplete();
	}
}
