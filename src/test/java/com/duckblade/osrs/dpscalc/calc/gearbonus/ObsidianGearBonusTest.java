package com.duckblade.osrs.dpscalc.calc.gearbonus;

import com.duckblade.osrs.dpscalc.calc.EquipmentItemIdsComputable;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeContext;
import com.duckblade.osrs.dpscalc.calc.compute.ComputeInputs;
import com.duckblade.osrs.dpscalc.calc.model.AttackStyle;
import com.duckblade.osrs.dpscalc.calc.model.GearBonuses;
import com.google.common.collect.ImmutableMap;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.ItemID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObsidianGearBonusTest
{

    @Mock
    private EquipmentItemIdsComputable equipmentItemIdsComputable;

    @Mock
    private ComputeContext context;

    @InjectMocks
    private ObsidianGearBonus obsidianGearBonus;

    @Test
    void isApplicableWhenUsingObsidianWeapons()
    {
        when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
                AttackStyle.builder().isManualCast(false).build()
        );
        //noinspection unchecked
        when(context.get(equipmentItemIdsComputable)).thenReturn(
                singletonMap(EquipmentInventorySlot.WEAPON, ItemID.TOKTZXILAK),
                singletonMap(EquipmentInventorySlot.WEAPON, ItemID.TOKTZXILEK),
                singletonMap(EquipmentInventorySlot.WEAPON, ItemID.TZHAARKETEM),
                singletonMap(EquipmentInventorySlot.WEAPON, ItemID.TZHAARKETOM),
                singletonMap(EquipmentInventorySlot.WEAPON, ItemID.TZHAARKETOM_T)
        );
        assertTrue(obsidianGearBonus.isApplicable(context));
        assertTrue(obsidianGearBonus.isApplicable(context));
        assertTrue(obsidianGearBonus.isApplicable(context));
        assertTrue(obsidianGearBonus.isApplicable(context));
        assertTrue(obsidianGearBonus.isApplicable(context));
    }

    @Test
    void isNotApplicableWithoutObsidianWeapons()
    {
        when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
                AttackStyle.builder().isManualCast(false).build()
        );
        //noinspection unchecked
        when(context.get(equipmentItemIdsComputable)).thenReturn(
                emptyMap(),
                singletonMap(EquipmentInventorySlot.WEAPON, ItemID.MAGIC_SHORTBOW)
        );
        assertFalse(obsidianGearBonus.isApplicable(context));
        assertFalse(obsidianGearBonus.isApplicable(context));
    }

    @Test
    void isNotApplicableWhenCasting()
    {
        //noinspection unchecked
        when(context.get(equipmentItemIdsComputable)).thenReturn(
                singletonMap(EquipmentInventorySlot.WEAPON, ItemID.TOKTZXILAK)
        );
        when(context.get(ComputeInputs.ATTACK_STYLE)).thenReturn(
                AttackStyle.builder().isManualCast(true).build()
        );
        assertFalse(obsidianGearBonus.isApplicable(context));
    }

    @Test
    void doesNotGrantBonusForPartialSet()
    {
        //noinspection unchecked
        when(context.get(equipmentItemIdsComputable)).thenReturn(
                singletonMap(EquipmentInventorySlot.HEAD, ItemID.OBSIDIAN_HELMET),
                singletonMap(EquipmentInventorySlot.BODY, ItemID.OBSIDIAN_PLATEBODY),
                singletonMap(EquipmentInventorySlot.LEGS, ItemID.OBSIDIAN_PLATELEGS),
                // Helm + body
                ImmutableMap.<EquipmentInventorySlot, Integer>builder()
                    .put(EquipmentInventorySlot.HEAD, ItemID.OBSIDIAN_HELMET)
                    .put(EquipmentInventorySlot.BODY, ItemID.OBSIDIAN_PLATEBODY)
                    .build(),
                // Helm + legs
                ImmutableMap.<EquipmentInventorySlot, Integer>builder()
                        .put(EquipmentInventorySlot.HEAD, ItemID.OBSIDIAN_HELMET)
                        .put(EquipmentInventorySlot.LEGS, ItemID.OBSIDIAN_PLATELEGS)
                        .build(),
                // Body + legs
                ImmutableMap.<EquipmentInventorySlot, Integer>builder()
                        .put(EquipmentInventorySlot.BODY, ItemID.OBSIDIAN_PLATEBODY)
                        .put(EquipmentInventorySlot.LEGS, ItemID.OBSIDIAN_PLATELEGS)
                        .build()
        );
        assertEquals(GearBonuses.of(1.0, 1.0), obsidianGearBonus.compute(context));
        assertEquals(GearBonuses.of(1.0, 1.0), obsidianGearBonus.compute(context));
        assertEquals(GearBonuses.of(1.0, 1.0), obsidianGearBonus.compute(context));
        assertEquals(GearBonuses.of(1.0, 1.0), obsidianGearBonus.compute(context));
        assertEquals(GearBonuses.of(1.0, 1.0), obsidianGearBonus.compute(context));
        assertEquals(GearBonuses.of(1.0, 1.0), obsidianGearBonus.compute(context));
    }

    @Test
    void grantsBonusForFullSet()
    {
        //noinspection unchecked
        when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.<EquipmentInventorySlot, Integer>builder()
                .put(EquipmentInventorySlot.HEAD, ItemID.OBSIDIAN_HELMET)
                .put(EquipmentInventorySlot.BODY, ItemID.OBSIDIAN_PLATEBODY)
                .put(EquipmentInventorySlot.LEGS, ItemID.OBSIDIAN_PLATELEGS)
                .build()
        );
        assertEquals(GearBonuses.of(1.1, 1.1), obsidianGearBonus.compute(context));
    }

    @Test
    void grantsBonusForBerserkerNecklace()
    {
        //noinspection unchecked
        when(context.get(equipmentItemIdsComputable)).thenReturn(
                singletonMap(EquipmentInventorySlot.AMULET, ItemID.BERSERKER_NECKLACE),
                singletonMap(EquipmentInventorySlot.AMULET, ItemID.BERSERKER_NECKLACE_OR)
        );
        assertEquals(GearBonuses.of(1.0, 1.2), obsidianGearBonus.compute(context));
    }

    @Test
    void fullSetAndBerserkerNecklaceStack()
    {
        //noinspection unchecked
        when(context.get(equipmentItemIdsComputable)).thenReturn(ImmutableMap.<EquipmentInventorySlot, Integer>builder()
                .put(EquipmentInventorySlot.HEAD, ItemID.OBSIDIAN_HELMET)
                .put(EquipmentInventorySlot.BODY, ItemID.OBSIDIAN_PLATEBODY)
                .put(EquipmentInventorySlot.LEGS, ItemID.OBSIDIAN_PLATELEGS)
                .put(EquipmentInventorySlot.AMULET, ItemID.BERSERKER_NECKLACE)
                .build()
        );
        assertEquals(GearBonuses.of(1.1, 1.32), obsidianGearBonus.compute(context));
    }

}
