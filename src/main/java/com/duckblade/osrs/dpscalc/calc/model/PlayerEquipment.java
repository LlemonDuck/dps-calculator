package com.duckblade.osrs.dpscalc.calc.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import net.runelite.api.EquipmentInventorySlot;

@Data
@Builder(toBuilder = true)
@With
public class PlayerEquipment
{

	@Builder.Default
	EquipmentPiece head = null;

	@Builder.Default
	EquipmentPiece cape = null;

	@Builder.Default
	EquipmentPiece neck = null;

	@Builder.Default
	EquipmentPiece ammo = null;

	@Builder.Default
	EquipmentPiece weapon = null;

	@Builder.Default
	EquipmentPiece body = null;

	@Builder.Default
	EquipmentPiece shield = null;

	@Builder.Default
	EquipmentPiece legs = null;

	@Builder.Default
	EquipmentPiece hands = null;

	@Builder.Default
	EquipmentPiece feet = null;

	@Builder.Default
	EquipmentPiece ring = null;

	public EquipmentPiece get(EquipmentInventorySlot slot)
	{
		switch (slot)
		{
			case HEAD:
				return head;

			case CAPE:
				return cape;

			case AMULET:
				return neck;

			case AMMO:
				return ammo;

			case WEAPON:
				return weapon;

			case BODY:
				return body;

			case SHIELD:
				return shield;

			case LEGS:
				return legs;

			case GLOVES:
				return hands;

			case BOOTS:
				return feet;

			case RING:
				return ring;

			default:
				throw new IllegalArgumentException("unmapped equipment slot " + slot.name());
		}
	}

	public void set(EquipmentInventorySlot slot, EquipmentPiece value)
	{
		switch (slot)
		{
			case HEAD:
				head = value;
				break;

			case CAPE:
				cape = value;
				break;

			case AMULET:
				neck = value;
				break;

			case AMMO:
				ammo = value;
				break;

			case WEAPON:
				weapon = value;
				break;

			case BODY:
				body = value;
				break;

			case SHIELD:
				shield = value;
				break;

			case LEGS:
				legs = value;
				break;

			case GLOVES:
				hands = value;
				break;

			case BOOTS:
				feet = value;
				break;

			case RING:
				ring = value;
				break;

			default:
				throw new IllegalArgumentException("unmapped equipment slot " + slot.name());
		}
	}

	public List<EquipmentPiece> getAllPieces()
	{
		return Stream.of(head, cape, neck, ammo, weapon, body, shield, legs, hands, feet, ring)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	public EquipmentStats getStats()
	{
		return getAllPieces().stream()
			.map(EquipmentPiece::getStats)
			.filter(Objects::nonNull)
			.reduce(EquipmentStats::merge)
			.orElseGet(() -> EquipmentStats.builder().build());
	}
}
