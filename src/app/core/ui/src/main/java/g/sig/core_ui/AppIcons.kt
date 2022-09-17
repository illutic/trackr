package g.sig.core_ui

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

object AppIcons {
    val filledDateRange = Icons.Filled.DateRange
    val outlinedDateRange = Icons.Outlined.DateRange
    val filledPaid = R.drawable.paid_24
    val outlinedPaid = R.drawable.outline_paid_24
    val filledSettings = Icons.Filled.Settings
    val outlinedSettings = Icons.Outlined.Settings
    val logo = R.drawable.ic_logo
    val logo_mono = R.drawable.ic_logo_mono
    val chevron_down = Icons.Filled.ArrowDropDown
}

sealed interface AppIcon

data class ImageVectorIcon(val vector: ImageVector) : AppIcon
data class DrawableResIcon(@DrawableRes val id: Int) : AppIcon