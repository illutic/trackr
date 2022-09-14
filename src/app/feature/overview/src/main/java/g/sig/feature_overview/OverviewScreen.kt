package g.sig.feature_overview

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import g.sig.core_ui.NoNavIconAppBar
import g.sig.core_ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen() {
    Scaffold(topBar = { NoNavIconAppBar(stringResource(R.string.overview)) }) {
        Text(modifier = Modifier.padding(it), text = "Hellow")
    }
}

@Composable
@Preview
fun PreviewOverview() {
    OverviewScreen()
}