package g.sig.trackr.core

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import g.sig.core_ui.theme.AppTheme
import g.sig.trackr.modals.AddCategoryModal
import g.sig.trackr.modals.AddExpenseModal
import g.sig.trackr.navigation.AppNavHost
import g.sig.trackr.navigation.TrackRBottomAppBar
import g.sig.trackr.states.CategoryState

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun App(appState: AppState = rememberAppState(), viewModel: AppViewModel = viewModel()) {
    val categoryState by viewModel.categoryState.collectAsState()
    val transactionState by viewModel.transactionState.collectAsState()

    LaunchedEffect(categoryState) {
        if (categoryState is CategoryState.Success) {
            viewModel.fetchCategories()
        }
    }

    AddCategoryModal(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 24.dp),
        state = appState.addCategoryModalBottomSheetState,
        categoryState = categoryState,
        onCategorySaved = viewModel::setCategory
    ) {
        AddExpenseModal(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 24.dp),
            state = appState.addExpenseModalBottomSheetState,
            addCategoryState = appState.addCategoryModalBottomSheetState,
            onTransactionLogged = { transaction, category ->
                viewModel.addTransaction(appState.currentMonth, transaction, category)
            },
            transactionState = transactionState
        ) {
            Scaffold(
                bottomBar = {
                    if (appState.topLevelDestinations.map { it.destination }
                            .contains(appState.currentDestination?.route))
                        TrackRBottomAppBar(
                            destinations = appState.topLevelDestinations,
                            onNavigate = appState::navigate,
                            currentDestination = appState.currentDestination
                        )
                }
            ) { padding ->
                AppNavHost(
                    modifier = Modifier
                        .padding(padding)
                        .consumedWindowInsets(padding),
                    navController = appState.navController,
                    addExpenseBottomSheetState = appState.addExpenseModalBottomSheetState,
                    onDateChanged = { appState.currentMonth = it },
                    initialMonth = appState.currentMonth
                )
            }
        }
    }
}

@Composable
@Preview
fun AppPreview() {
    AppTheme {
        App()
    }
}