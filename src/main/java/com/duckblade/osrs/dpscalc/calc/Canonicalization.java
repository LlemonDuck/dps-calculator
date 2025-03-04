package com.duckblade.osrs.dpscalc.calc;

import com.duckblade.osrs.dpscalc.calc.model.EquipmentPiece;
import com.duckblade.osrs.dpscalc.calc.model.PlayerEquipment;
import static com.duckblade.osrs.dpscalc.plugin.osdata.wiki.WikiDataProvider.ALL_EQUIPMENT;
import static com.duckblade.osrs.dpscalc.plugin.osdata.wiki.WikiDataProvider.EQUIPMENT_ALIASES;

public class Canonicalization
{

	public static int getCanonicalItemId(int itemId)
	{
		return EQUIPMENT_ALIASES.getOrDefault(itemId, itemId);
	}

	public static EquipmentPiece getCanonicalItem(EquipmentPiece equipmentPiece)
	{
		if (equipmentPiece == null)
		{
			return null;
		}

		int canonicalId = getCanonicalItemId(equipmentPiece.getId());
		if (canonicalId == equipmentPiece.getId())
		{
			return equipmentPiece;
		}

		return ALL_EQUIPMENT.getOrDefault(canonicalId, equipmentPiece);
	}

	public static PlayerEquipment getCanonicalEquipment(PlayerEquipment inputEq)
	{
		return PlayerEquipment.builder()
			.head(getCanonicalItem(inputEq.getHead()))
			.cape(getCanonicalItem(inputEq.getCape()))
			.neck(getCanonicalItem(inputEq.getNeck()))
			.ammo(getCanonicalItem(inputEq.getAmmo()))
			.weapon(getCanonicalItem(inputEq.getWeapon()))
			.body(getCanonicalItem(inputEq.getBody()))
			.shield(getCanonicalItem(inputEq.getShield()))
			.legs(getCanonicalItem(inputEq.getLegs()))
			.hands(getCanonicalItem(inputEq.getHands()))
			.feet(getCanonicalItem(inputEq.getFeet()))
			.ring(getCanonicalItem(inputEq.getRing()))
			.build();
	}

}
