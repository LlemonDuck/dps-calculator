# Calculation took 5561ms
```mermaid
classDiagram
DragonClawsDistribution --|> Accuracy
DragonClawsDistribution --|> MaxHit
EffectiveStrength --|> AttackStyle
EffectiveStrength --|> AttackerPrayers
EffectiveStrength --|> AttackerSkills
EffectiveStrength --|> MeleeCombatStyleBonus
EffectiveStrength --|> VoidEquipmentCheck
EquipmentIds --|> AttackerEquipment
EquipmentStats --|> AttackerEquipment
MaxHit --|> AttackStyle
MaxHit --|> MeleeMaxHit
MeleeCombatStyleBonus --|> AttackStyle
MeleeMaxHit --|> AttackStyle
MeleeMaxHit --|> DefenderAttributes
MeleeMaxHit --|> EffectiveStrength
MeleeMaxHit --|> EquipmentIds
MeleeMaxHit --|> EquipmentStats
MeleeMaxHit --|> Weapon
VoidEquipmentCheck --|> EquipmentIds
Weapon --|> AttackerEquipment
class Accuracy { 0.982759546699608 }
class AttackStyle { Chop (Slash/Aggressive) }
class AttackerEquipment { (WEAPON=ItemStats(itemId=13652,\n name=null,\n accuracyStab=41,\n accuracySlash=57,\n accuracyCrush=-4,\n accuracyMagic=0,\n accuracyRanged=0,\n strengthMelee=56,\n strengthRanged=0,\n strengthMagic=0,\n prayer=0,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED)) }
class AttackerPrayers { () }
class AttackerSkills { Skills(levels=(ATTACK=99,\n STRENGTH=99,\n RANGED=99,\n MAGIC=99),\n boosts=()) }
class DefenderAttributes { DefenderAttributes(npcId=-1,\n name=null,\n isDemon=false,\n isDragon=false,\n isKalphite=false,\n isLeafy=false,\n isUndead=false,\n isVampyre1=false,\n isVampyre2=false,\n isVampyre3=false,\n isGolem=false,\n size=1,\n accuracyMagic=0) }
class DragonClawsDistribution { (HitDist(max=27,\navg=52.216108873284426,\nhits=(0=0.05552049222888633,\n 1=0.027760246114443166,\n 5=0.08628774685029196,\n 6=0.12868780034045368,\n 7=0.17258854245996363,\n 8=0.21648928457947358,\n 9=0.21648928457947358,\n 10=0.2611601730658194,\n 11=0.3058310615521652,\n 12=0.3058310615521652,\n 13=0.3058310615521652,\n 14=0.3058310615521652,\n 15=0.3058310615521652,\n 16=0.21954331470187327,\n 17=0.21954331470187327,\n 18=0.17564257258236332,\n 19=0.13174183046285337,\n 20=0.13174183046285337,\n 21=0.08707094197650755,\n 22=0.04240005349016173,\n 23=0.04240005349016173,\n 24=0.04240005349016173,\n 25=0.04240005349016173,\n 26=0.04240005349016173,\n 27=0.04240005349016173)),\n HitDist(max=16,\navg=23.634172175022144,\nhits=(0=0.9883216690124444,\n 1=0.027760246114443166,\n 3=0.04390074211950995,\n 4=0.1756029684780398,\n 5=0.30961563393707725,\n 6=0.440574269273715,\n 7=0.440574269273715,\n 8=0.440574269273715,\n 9=0.30887204291518516,\n 10=0.2203004123093294,\n 11=0.08628774685029196,\n 12=0.08628774685029196,\n 13=0.08628774685029196,\n 14=0.08628774685029196,\n 15=0.08628774685029196,\n 16=0.08628774685029196)),\n HitDist(max=10,\navg=10.20037269985316,\nhits=(0=1.9652471304800976,\n 2=0.13401266545903745,\n 3=0.35736710789076653,\n 4=0.4012678500102765,\n 5=0.30961563393707725,\n 6=0.1756029684780398,\n 7=0.1756029684780398,\n 8=0.1756029684780398,\n 9=0.1756029684780398,\n 10=0.04390074211950995)),\n HitDist(max=6,\navg=4.422417960148236,\nhits=(0=2.931063457109318,\n 3=0.13401266545903745,\n 4=0.35736710789076653,\n 5=0.35736710789076653,\n 6=0.13401266545903745))) }
class EffectiveStrength { 110 }
class EquipmentIds { (HEAD=-1,\n SHIELD=-1,\n BOOTS=-1,\n RING=-1,\n WEAPON=13652,\n GLOVES=-1,\n AMMO=-1,\n BODY=-1,\n LEGS=-1,\n AMULET=-1,\n CAPE=-1) }
class EquipmentStats { ItemStats(itemId=13652,\n name=null,\n accuracyStab=41,\n accuracySlash=57,\n accuracyCrush=-4,\n accuracyMagic=0,\n accuracyRanged=0,\n strengthMelee=56,\n strengthRanged=0,\n strengthMagic=0,\n prayer=0,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED) }
class MaxHit { 21 }
class MeleeCombatStyleBonus { GB(acc=+8,\nstr=+11) }
class MeleeMaxHit { 21 }
class VoidEquipmentCheck { NONE }
class Weapon { ItemStats(itemId=13652,\n name=null,\n accuracyStab=41,\n accuracySlash=57,\n accuracyCrush=-4,\n accuracyMagic=0,\n accuracyRanged=0,\n strengthMelee=56,\n strengthRanged=0,\n strengthMagic=0,\n prayer=0,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED) }
```