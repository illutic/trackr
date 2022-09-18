package g.sig.core_navigation

sealed interface Routes {
    object Overview : Navigable, Routes, DeepLink {
        override val route: String = "overview_route"
        override val destination: String = "overview_dest"
        override val deepLink: String = "overview_link"
    }

    object Transactions : Navigable, Routes, DeepLink {
        override val route: String = "transactions_route"
        override val destination: String = "transactions_dest"
        override val deepLink: String = "transactions_link"
    }

    object Settings : Navigable, Routes, DeepLink {
        override val route: String = "settings_route"
        override val destination: String = "settings_dest"
        override val deepLink: String = "settings_link"
    }
}