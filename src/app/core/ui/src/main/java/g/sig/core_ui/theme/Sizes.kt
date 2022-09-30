package g.sig.core_ui.theme

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

val largeSize = DpSize(182.dp, 182.dp)
val mediumSize = largeSize.copy(largeSize.width, largeSize.height * .75f)
val smallSize = mediumSize.copy(largeSize.width * .25f, largeSize.height * .5f)