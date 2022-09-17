package g.sig.trackr.core

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import g.sig.core_ui.theme.AppTheme
import g.sig.trackr.navigation.AppNavHost
import g.sig.trackr.navigation.TrackRBottomAppBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun App(appState: AppState = rememberAppState(), appViewModel: AppViewModel = viewModel()) {
    LaunchedEffect( Unit ) {
        appViewModel.getUser()
    }
    Scaffold(
        bottomBar = {
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
            navController = appState.navController
        )
    }
}

@Composable
@Preview
fun AppPreview() {
    AppTheme {
        App()
    }
}