package g.sig.settings

import android.icu.util.Currency
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import g.sig.core_ui.menus.OutlinedCurrencyDropDown
import g.sig.core_ui.surfaces.GradientLogo
import g.sig.core_ui.surfaces.PrimarySwitchSurface
import java.util.*

@Composable
fun SettingsRoute() {
    val viewModel: SettingsViewModel = viewModel()
    val currency = viewModel.savedCurrency.collectAsState().value

    SettingsScreen(
        currencies = viewModel.getCurrencies().map { it.currencyCode },
        selectedCurrency = currency,
        isMaterialYou = viewModel.materialYou,
        onMaterialYouToggled = viewModel::toggleMaterialYou,
        onCurrencySelected = { viewModel.setCurrency(Currency.getInstance(it)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun SettingsScreen(
    currencies: List<String> = listOf(),
    isMaterialYou: Boolean = false,
    selectedCurrency: Currency = Currency.getInstance(Locale.getDefault()),
    onMaterialYouToggled: (Boolean) -> Unit = {},
    onCurrencySelected: (String) -> Unit = {}
) {
    Scaffold(topBar = { NoNavIconAppBar(stringResource(R.string.settings)) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            GradientLogo(
                materialYou = isMaterialYou,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )

            OutlinedCurrencyDropDown(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                label = stringResource(id = R.string.currency),
                selectedItem = selectedCurrency.currencyCode,
                items = currencies,
                onItemSelected = onCurrencySelected
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                PrimarySwitchSurface(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    selected = isMaterialYou,
                    title = stringResource(R.string.material_you_title),
                    message = stringResource(R.string.material_you_message),
                    onChanged = onMaterialYouToggled
                )
        }
    }
}

@Composable
@Preview
fun PreviewOverview() {
    SettingsRoute()
}