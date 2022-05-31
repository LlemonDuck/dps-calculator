package com.duckblade.osrs.dpscalc.plugin.ui.npc;

import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.NpcData;
import com.duckblade.osrs.dpscalc.plugin.osdata.wiki.NpcDataProvider;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelState;
import com.duckblade.osrs.dpscalc.plugin.ui.state.PanelStateManager;
import com.duckblade.osrs.dpscalc.plugin.ui.state.component.StateBoundJComboBox;
import java.awt.Dimension;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.BorderFactory;
import lombok.Getter;

@Singleton
public class NpcSelectPanel extends StateBoundJComboBox<NpcData>
{

	private static List<NpcData> getNpcs(NpcDataProvider npcDataProvider)
	{
		return npcDataProvider.getAll()
			.stream()
			.sorted(Comparator.comparing(npc -> npc.getAttributes().getName()))
			.collect(Collectors.toList());
	}

	private static NpcData readNpcFromState(PanelState state, NpcDataProvider npcDataProvider)
	{
		return npcDataProvider.getById(state.getDefenderAttributes().getNpcId());
	}

	@Getter
	private final PanelStateManager manager;

	@Inject
	public NpcSelectPanel(NpcDataProvider npcDataProvider, PanelStateManager manager)
	{
		super(
			getNpcs(npcDataProvider),
			npc -> npc.getAttributes().getName(),
			null,
			manager,
			PanelState::loadNpcData,
			ps -> readNpcFromState(ps, npcDataProvider)
		);
		this.manager = manager;

		setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
		setPreferredSize(new Dimension(200, 25));
		enableAutocomplete();
	}
}
