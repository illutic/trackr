package g.sig.transactions

import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import g.sig.core_navigation.Routes

fun NavGraphBuilder.transactionsGraph(deepLinks: List<NavDeepLink> = listOf()) {
    composable(Routes.Transactions.destination, deepLinks = deepLinks) {
        TransactionsScreen()
    }
}