package g.sig.settings

import android.os.Build
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import g.sig.core_ui.NoNavIconAppBar
import g.sig.core_ui.R
import g.sig.core_ui.surfaces.GradientLogo
import g.sig.core_ui.surfaces.PrimarySwitchSurface

@Composable
fun SettingsRoute() {
    val viewModel: SettingsViewModel = viewModel()
    val state by viewModel.settingsState.collectAsState()
    SettingsScreen(state, onMaterialYouToggled = viewModel::toggleMaterialYou)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(settingsState: SettingsState, onMaterialYouToggled: (Boolean) -> Unit) {
    Scaffold(topBar = { NoNavIconAppBar(stringResource(R.string.settings)) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (settingsState is SettingsState.SettingsLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            GradientLogo(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )

            when (settingsState) {
                is SettingsState.SettingsLoading -> {}
                is SettingsState.SettingsSuccess -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                        PrimarySwitchSurface(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            selected = settingsState.isMaterialYouEnabled,
                            title = stringResource(R.string.material_you_title),
                            message = stringResource(R.string.material_you_message),
                            onChanged = onMaterialYouToggled
                        )
                }
                is SettingsState.SettingsError -> {

                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewOverview() {
    SettingsRoute()
}