package g.sig.core_navigation

sealed interface Routes {
    object Overview : Navigable, Routes {
        override val route: String = "overview_route"
        override val destination: String = "overview_dest"
    }

    object Transactions : Navigable, Routes {
        override val route: String = "transactions_route"
        override val destination: String = "transactions_dest"
    }

    object Settings : Navigable, Routes {
        override val route: String = "settings_route"
        override val destination: String = "settings_dest"
    }
}