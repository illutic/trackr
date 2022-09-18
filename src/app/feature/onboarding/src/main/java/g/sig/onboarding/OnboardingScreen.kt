package g.sig.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import g.sig.core_data.shared_prefs.SharedKeys
import g.sig.core_data.shared_prefs.isMaterialYou
import g.sig.core_data.shared_prefs.sharedPrefsUserId
import g.sig.core_ui.R
import g.sig.core_ui.icons.TrackRLogo

@Composable
fun OnboardingRoute(onOnboardingComplete: () -> Unit) {
    val context = LocalContext.current
    val viewModel: OnboardingViewModel = viewModel()
    val overviewState by viewModel.onboardingStateFlow.collectAsState()

    if (context.sharedPrefsUserId != SharedKeys.NoUserId) {
        onOnboardingComplete()
    } else {
        OnboardingScreen(
            overviewState,
            isMaterialYou = context.isMaterialYou,
            onOnboardingStart = viewModel::registerUser,
            onOnboardingComplete = onOnboardingComplete
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun OnboardingScreen(
    onboardingState: OnboardingState = OnboardingState.OnboardingIdle,
    isMaterialYou: Boolean = false,
    onOnboardingComplete: () -> Unit = {},
    onOnboardingStart: () -> Unit = {}
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TrackRLogo(
                modifier = Modifier.size(124.dp),
                isMaterialYou = isMaterialYou,
                showText = true
            )
            when (onboardingState) {
                is OnboardingState.OnboardingError -> TODO()
                OnboardingState.OnboardingIdle -> {
                    FilledTonalButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        onClick = onOnboardingStart
                    ) {
                        Text(text = stringResource(id = R.string.start))
                    }
                }
                OnboardingState.OnboardingLoading -> CircularProgressIndicator()
                is OnboardingState.OnboardingSuccess -> onOnboardingComplete()
            }
        }

    }
}