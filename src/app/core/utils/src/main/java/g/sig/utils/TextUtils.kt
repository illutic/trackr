package g.sig.utils

import android.text.TextUtils.isDigitsOnly
import java.math.MathContext
import java.math.RoundingMode

val Float.formatTo3Decimals: String
    get() = "${(this * 100).toBigDecimal(MathContext(3, RoundingMode.HALF_EVEN))}%"

val String.isDouble: Boolean get() = toDoubleOrNull() != null

val String.isProperString get() = isNotBlank() or !isDigitsOnly(this)