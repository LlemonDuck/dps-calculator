package com.duckblade.osrs.dpscalc.calc.model.gson;

import com.duckblade.osrs.dpscalc.calc.model.EquipmentCategory;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class EquipmentCategoryAdapter extends TypeAdapter<EquipmentCategory>
{
	@Override
	public void write(JsonWriter jsonWriter, EquipmentCategory category) throws IOException
	{
		switch (category)
		{
			case TWO_HANDED_SWORD:
				jsonWriter.value("2h Sword");
				break;

			case AXE:
				jsonWriter.value("Axe");
				break;

			case BANNER:
				jsonWriter.value("Banner");
				break;

			case BLADED_STAFF:
				jsonWriter.value("Bladed Staff");
				break;

			case BLASTER:
				jsonWriter.value("Blaster");
				break;

			case BLUDGEON:
				jsonWriter.value("Bludgeon");
				break;

			case BLUNT:
				jsonWriter.value("Blunt");
				break;

			case BOW:
				jsonWriter.value("Bow");
				break;

			case BULWARK:
				jsonWriter.value("Bulwark");
				break;

			case CHINCHOMPA:
				jsonWriter.value("Chinchompas");
				break;

			case CLAW:
				jsonWriter.value("Claw");
				break;

			case CROSSBOW:
				jsonWriter.value("Crossbow");
				break;

			case GUN:
				jsonWriter.value("Gun");
				break;

			case PARTISAN:
				jsonWriter.value("Partisan");
				break;

			case PICKAXE:
				jsonWriter.value("Pickaxe");
				break;

			case POLEARM:
				jsonWriter.value("Polearm");
				break;

			case POLESTAFF:
				jsonWriter.value("Polestaff");
				break;

			case POWERED_STAFF:
				jsonWriter.value("Powered Staff");
				break;

			case SALAMANDER:
				jsonWriter.value("Salamander");
				break;

			case SCYTHE:
				jsonWriter.value("Scythe");
				break;

			case SLASH_SWORD:
				jsonWriter.value("Slash Sword");
				break;

			case SPEAR:
				jsonWriter.value("Spear");
				break;

			case SPIKED:
				jsonWriter.value("Spiked");
				break;

			case STAB_SWORD:
				jsonWriter.value("Stab Sword");
				break;

			case STAFF:
				jsonWriter.value("Staff");
				break;

			case THROWN:
				jsonWriter.value("Thrown");
				break;

			case WHIP:
				jsonWriter.value("Whip");
				break;

			default:
				jsonWriter.value("Unarmed");
				break;
		}
	}

	@Override
	public EquipmentCategory read(JsonReader jsonReader) throws IOException
	{
		switch (jsonReader.nextString())
		{
			case "2h Sword":
				return EquipmentCategory.TWO_HANDED_SWORD;
			case "Axe":
				return EquipmentCategory.AXE;
			case "Banner":
				return EquipmentCategory.BANNER;
			case "Bladed Staff":
				return EquipmentCategory.BLADED_STAFF;
			case "Blaster":
				return EquipmentCategory.BLASTER;
			case "Bludgeon":
				return EquipmentCategory.BLUDGEON;
			case "Blunt":
				return EquipmentCategory.BLUNT;
			case "Bow":
				return EquipmentCategory.BOW;
			case "Bulwark":
				return EquipmentCategory.BULWARK;
			case "Chinchompas":
				return EquipmentCategory.CHINCHOMPA;
			case "Claw":
				return EquipmentCategory.CLAW;
			case "Crossbow":
				return EquipmentCategory.CROSSBOW;
			case "Gun":
				return EquipmentCategory.GUN;
			case "Partisan":
				return EquipmentCategory.PARTISAN;
			case "Pickaxe":
				return EquipmentCategory.PICKAXE;
			case "Polearm":
				return EquipmentCategory.POLEARM;
			case "Polestaff":
				return EquipmentCategory.POLESTAFF;
			case "Powered Staff":
				return EquipmentCategory.POWERED_STAFF;
			case "Salamander":
				return EquipmentCategory.SALAMANDER;
			case "Scythe":
				return EquipmentCategory.SCYTHE;
			case "Slash Sword":
				return EquipmentCategory.SLASH_SWORD;
			case "Spear":
				return EquipmentCategory.SPEAR;
			case "Spiked":
				return EquipmentCategory.SPIKED;
			case "Stab Sword":
				return EquipmentCategory.STAB_SWORD;
			case "Staff":
				return EquipmentCategory.STAFF;
			case "Thrown":
				return EquipmentCategory.THROWN;
			case "Whip":
				return EquipmentCategory.WHIP;

			case "Unarmed":
			default:
				return EquipmentCategory.UNARMED;
		}
	}
}
