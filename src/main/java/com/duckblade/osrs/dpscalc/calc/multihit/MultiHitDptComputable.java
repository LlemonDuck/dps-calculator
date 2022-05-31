package com.duckblade.osrs.dpscalc.calc.multihit;

import com.duckblade.osrs.dpscalc.calc.compute.OptionalComputable;

/**
 * Models a DPS calculation that is computed by multiple distinct hit types,
 * not necessarily multiple hits per swing
 * (e.g. Keris applies a regular hit 50/51 hits, and triple damage 1/51 hits).
 * Only one MultiHitDptComputable should be applicable at a time,
 * and if it is applicable, it will replace the resultant DPS calculation.
 */
public interface MultiHitDptComputable extends OptionalComputable<Double>
{
}
