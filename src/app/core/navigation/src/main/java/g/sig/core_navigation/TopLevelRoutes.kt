package g.sig.core_navigation

import g.sig.core_ui.*
import g.sig.core_ui.R

sealed interface TopLevelRoute : Navigable {
    val titleRes: Int
    val selectedIcon: AppIcon
    val unselectedIcon: AppIcon

    class Overview : TopLevelRoute {
        override val titleRes = R.string.overview
        override val selectedIcon = ImageVectorIcon(AppIcons.filledDateRange)
        override val unselectedIcon = ImageVectorIcon(AppIcons.outlinedDateRange)
        override val route: String = "overview_route"
        override val destination: String = "overview_dest"
    }

    class Transactions : TopLevelRoute {
        override val titleRes = R.string.transactions
        override val selectedIcon = DrawableResIcon(AppIcons.filledPaid)
        override val unselectedIcon = DrawableResIcon(AppIcons.outlinedPaid)
        override val route: String = "transactions_route"
        override val destination: String = "transactions_dest"
    }

    class Settings : TopLevelRoute {
        override val titleRes = R.string.settings
        override val selectedIcon = ImageVectorIcon(AppIcons.filledSettings)
        override val unselectedIcon = ImageVectorIcon(AppIcons.outlinedSettings)
        override val route: String = "settings_route"
        override val destination: String = "settings_dest"
    }
}