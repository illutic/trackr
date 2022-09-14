package g.sig.transactions

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import g.sig.core_navigation.Routes

fun NavGraphBuilder.transactionsGraph() {
    composable(Routes.Transactions.destination) {
        TransactionsScreen()
    }
}