package g.sig.utils

import java.math.MathContext
import java.math.RoundingMode

val Float.formatTo3Decimals: String
    get() = "${(this * 100).toBigDecimal(MathContext(3, RoundingMode.HALF_EVEN))}%"