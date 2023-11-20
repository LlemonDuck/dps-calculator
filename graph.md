# Calculation took 23ms
```mermaid
classDiagram
Accuracy --|> StandardAccuracy
Accuracy --|> Weapon
AttackDist --|> AttackStyle
AttackDist --|> DefenderAttributes
AttackDist --|> DistMaxHit
AttackDist --|> KerisDistribution
AttackDist --|> Weapon
AttackRoll --|> AttackStyle
AttackRoll --|> DefenderAttributes
AttackRoll --|> EffectiveAccuracyLevel
AttackRoll --|> EquipmentIds
AttackRoll --|> EquipmentStats
AttackRoll --|> Keris
AttackRoll --|> Weapon
CombatStyleBonus --|> AttackStyle
DefenceRoll --|> StandardDefenceRoll
DefenderStats --|> StandardDefenderStats
Dps --|> Dpt
Dpt --|> AttackDist
Dpt --|> Weapon
EffectiveAccuracyLevel --|> AttackStyle
EffectiveAccuracyLevel --|> AttackerPrayers
EffectiveAccuracyLevel --|> AttackerSkills
EffectiveAccuracyLevel --|> CombatStyleBonus
EffectiveAccuracyLevel --|> PrayerModAccuracy
EffectiveAccuracyLevel --|> PrayerModStrength
EffectiveAccuracyLevel --|> VoidEquipmentCheck
EffectiveStrengthLevel --|> AttackStyle
EffectiveStrengthLevel --|> AttackerPrayers
EffectiveStrengthLevel --|> AttackerSkills
EffectiveStrengthLevel --|> CombatStyleBonus
EffectiveStrengthLevel --|> PrayerModAccuracy
EffectiveStrengthLevel --|> PrayerModStrength
EffectiveStrengthLevel --|> VoidEquipmentCheck
EquipmentIds --|> AttackerEquipment
EquipmentStats --|> StandardEquipmentStats
Keris --|> Weapon
KerisDistribution --|> StandardHitDistribution
MaxHit --|> AttackStyle
MaxHit --|> StandardMaxHit
MaxHit --|> Weapon
StandardAccuracy --|> AttackRoll
StandardAccuracy --|> DefenceRoll
StandardDefenceRoll --|> AttackStyle
StandardDefenceRoll --|> DefenderBonuses
StandardDefenceRoll --|> DefenderStats
StandardDefenderStats --|> DefenderBonuses
StandardEquipmentStats --|> AttackerEquipment
StandardHitDistribution --|> Accuracy
StandardHitDistribution --|> MaxHit
StandardMaxHit --|> AttackStyle
StandardMaxHit --|> DefenderAttributes
StandardMaxHit --|> EffectiveStrengthLevel
StandardMaxHit --|> EquipmentIds
StandardMaxHit --|> EquipmentStats
StandardMaxHit --|> Keris
StandardMaxHit --|> Weapon
VoidEquipmentCheck --|> EquipmentIds
Weapon --|> AttackerEquipment
class Accuracy { 0.4495415448377665 }
class AttackDist { AttackDistribution(hitDists=Dist(expected=11.445680313173328,\n sumProb=1.0,\n outcomes=102)) }
class AttackRoll { 31672 }
class AttackStyle { Lunge (Stab/Aggressive) }
class AttackerEquipment { Map(HEAD=ItemStats(itemId=26382,\n name=Torva full helm,\n accuracyStab=0,\n accuracySlash=0,\n accuracyCrush=0,\n accuracyMagic=-5,\n accuracyRanged=-5,\n strengthMelee=8,\n strengthRanged=0,\n strengthMagic=0,\n prayer=1,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED),\n CAPE=ItemStats(itemId=21295,\n name=Infernal cape,\n accuracyStab=4,\n accuracySlash=4,\n accuracyCrush=4,\n accuracyMagic=1,\n accuracyRanged=1,\n strengthMelee=8,\n strengthRanged=0,\n strengthMagic=0,\n prayer=2,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED),\n AMULET=ItemStats(itemId=19553,\n name=Amulet of torture,\n accuracyStab=15,\n accuracySlash=15,\n accuracyCrush=15,\n accuracyMagic=0,\n accuracyRanged=0,\n strengthMelee=10,\n strengthRanged=0,\n strengthMagic=0,\n prayer=2,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED),\n AMMO=ItemStats(itemId=22947,\n name=Rada's blessing 4,\n accuracyStab=0,\n accuracySlash=0,\n accuracyCrush=0,\n accuracyMagic=0,\n accuracyRanged=0,\n strengthMelee=0,\n strengthRanged=0,\n strengthMagic=0,\n prayer=2,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED),\n BODY=ItemStats(itemId=26384,\n name=Torva platebody,\n accuracyStab=0,\n accuracySlash=0,\n accuracyCrush=0,\n accuracyMagic=-18,\n accuracyRanged=-14,\n strengthMelee=6,\n strengthRanged=0,\n strengthMagic=0,\n prayer=1,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED),\n LEGS=ItemStats(itemId=26386,\n name=Torva platelegs,\n accuracyStab=0,\n accuracySlash=0,\n accuracyCrush=0,\n accuracyMagic=-24,\n accuracyRanged=-11,\n strengthMelee=4,\n strengthRanged=0,\n strengthMagic=0,\n prayer=1,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED),\n GLOVES=ItemStats(itemId=22981,\n name=Ferocious gloves,\n accuracyStab=16,\n accuracySlash=16,\n accuracyCrush=16,\n accuracyMagic=-16,\n accuracyRanged=-16,\n strengthMelee=14,\n strengthRanged=0,\n strengthMagic=0,\n prayer=0,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED),\n BOOTS=ItemStats(itemId=13239,\n name=Primordial boots,\n accuracyStab=2,\n accuracySlash=2,\n accuracyCrush=2,\n accuracyMagic=-4,\n accuracyRanged=-1,\n strengthMelee=5,\n strengthRanged=0,\n strengthMagic=0,\n prayer=0,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED),\n RING=ItemStats(itemId=25485,\n name=Ultor ring,\n accuracyStab=0,\n accuracySlash=0,\n accuracyCrush=0,\n accuracyMagic=0,\n accuracyRanged=0,\n strengthMelee=12,\n strengthRanged=0,\n strengthMagic=0,\n prayer=0,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED),\n WEAPON=ItemStats(itemId=25981,\n name=Keris partisan of breaching,\n accuracyStab=58,\n accuracySlash=-2,\n accuracyCrush=57,\n accuracyMagic=2,\n accuracyRanged=0,\n strengthMelee=45,\n strengthRanged=0,\n strengthMagic=0,\n prayer=3,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=PARTISAN),\n SHIELD=ItemStats(itemId=22322,\n name=Avernic defender,\n accuracyStab=30,\n accuracySlash=29,\n accuracyCrush=28,\n accuracyMagic=-5,\n accuracyRanged=-4,\n strengthMelee=8,\n strengthRanged=0,\n strengthMagic=0,\n prayer=0,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED)) }
class AttackerPrayers { Set() }
class AttackerSkills { Skills(levels=(ATTACK=99,\n STRENGTH=99),\n boosts=(ATTACK=19,\n STRENGTH=19)) }
class CombatStyleBonus { GB(acc=+8,\nstr=+11) }
class DefenceRoll { 35226 }
class DefenderAttributes { DefenderAttributes(npcId=963,\n name=Kalphite Queen,\n isDemon=false,\n isDragon=false,\n isKalphite=true,\n isLeafy=false,\n isUndead=false,\n isVampyre1=false,\n isVampyre2=false,\n isVampyre3=false,\n isGolem=false,\n size=1,\n inToa=false) }
class DefenderBonuses { DefensiveStats(levelDefence=300,\n levelMagic=150,\n defenceStab=50,\n defenceSlash=50,\n defenceCrush=10,\n defenceRanged=100,\n defenceMagic=100,\n accuracyMagic=0) }
class DefenderStats { DefensiveStats(levelDefence=300,\n levelMagic=150,\n defenceStab=50,\n defenceSlash=50,\n defenceCrush=10,\n defenceRanged=100,\n defenceMagic=100,\n accuracyMagic=0) }
class DistMaxHit { 147 }
class Dps { 4.76903346382222 }
class Dpt { 2.861420078293332 }
class EffectiveAccuracyLevel { 126 }
class EffectiveStrengthLevel { 129 }
class EquipmentIds { Map(BODY=26384,\n AMULET=19553,\n GLOVES=22981,\n SHIELD=22322,\n LEGS=26386,\n RING=25485,\n HEAD=26382,\n WEAPON=25981,\n BOOTS=13239,\n CAPE=21295,\n AMMO=22947) }
class EquipmentStats { ItemStats(itemId=-1,\n name=null,\n accuracyStab=125,\n accuracySlash=64,\n accuracyCrush=122,\n accuracyMagic=-69,\n accuracyRanged=-50,\n strengthMelee=120,\n strengthRanged=0,\n strengthMagic=0,\n prayer=10,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED) }
class Keris { GB(acc=*133/100,\nstr=*133/100) }
class KerisDistribution { AttackDistribution(hitDists=Dist(expected=11.445680313173328,\n sumProb=1.0,\n outcomes=102)) }
class MaxHit { 49 }
class PrayerModAccuracy { null }
class PrayerModStrength { null }
class StandardAccuracy { 0.4495415448377665 }
class StandardDefenceRoll { 35226 }
class StandardDefenderStats { DefensiveStats(levelDefence=300,\n levelMagic=150,\n defenceStab=50,\n defenceSlash=50,\n defenceCrush=10,\n defenceRanged=100,\n defenceMagic=100,\n accuracyMagic=0) }
class StandardEquipmentStats { ItemStats(itemId=-1,\n name=null,\n accuracyStab=125,\n accuracySlash=64,\n accuracyCrush=122,\n accuracyMagic=-69,\n accuracyRanged=-50,\n strengthMelee=120,\n strengthRanged=0,\n strengthMagic=0,\n prayer=10,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=UNARMED) }
class StandardHitDistribution { Dist(expected=11.013767848525278,\n sumProb=1.0,\n outcomes=51) }
class StandardMaxHit { 49 }
class VoidEquipmentCheck { NONE }
class Weapon { ItemStats(itemId=25981,\n name=Keris partisan of breaching,\n accuracyStab=58,\n accuracySlash=-2,\n accuracyCrush=57,\n accuracyMagic=2,\n accuracyRanged=0,\n strengthMelee=45,\n strengthRanged=0,\n strengthMagic=0,\n prayer=3,\n speed=4,\n slot=-1,\n is2h=false,\n weaponCategory=PARTISAN) }
```