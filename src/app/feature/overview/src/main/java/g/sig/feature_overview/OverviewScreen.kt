package g.sig.feature_overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import g.sig.core_ui.NoNavIconAppBar
import g.sig.core_ui.R

@Composable
fun OverviewRoute() {
    val viewModel: OverviewViewModel = viewModel()
    val overviewState by viewModel.overviewStateFlow.collectAsState()
    OverviewScreen(overviewState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(overviewState: OverviewState) {
    Scaffold(
        topBar = { NoNavIconAppBar(stringResource(R.string.overview)) }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            when (overviewState) {
                is OverviewState.OverviewLoading -> {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                is OverviewState.OverviewSuccess -> {

                }
                is OverviewState.OverviewError -> {

                }
            }
        }

    }
}

@Composable
@Preview
fun PreviewOverview() {
    OverviewRoute()
}