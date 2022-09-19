package g.sig.feature_overview

import android.icu.util.Currency
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import g.sig.core_data.models.transaction.Month
import g.sig.core_data.models.transaction.MonthCategories
import g.sig.core_data.utils.getTotal
import g.sig.core_ui.NoNavIconAppBar
import g.sig.core_ui.R
import g.sig.core_ui.surfaces.PrimaryGradientColumn
import g.sig.core_ui.surfaces.SecondaryGradientColumn
import java.time.Instant
import java.util.*

@Composable
fun OverviewRoute() {
    val viewModel: OverviewViewModel = viewModel()
    val overviewState by viewModel.overviewStateFlow.collectAsState()
    val currency by viewModel.savedCurrency.collectAsState()
    OverviewScreen(overviewState, currency = currency.currencyCode)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    overviewState: OverviewState = OverviewState.OverviewLoading,
    currency: String = Currency.getInstance(Locale.getDefault()).currencyCode,
) {
    Scaffold(
        topBar = { NoNavIconAppBar(stringResource(R.string.overview)) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            when (overviewState) {
                is OverviewState.OverviewLoading -> {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                is OverviewState.OverviewSuccess -> {
                    Text(
                        text = "$currency ${overviewState.monthCategories?.month?.getTotal() ?: 0}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        BudgetBox(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .fillMaxWidth()
                                .weight(1f),
                            amount = overviewState.monthCategories?.month?.budget,
                            currency = currency
                        )
                        ExpensesBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            amount = overviewState.monthCategories?.month?.expenses,
                            currency = currency
                        )
                    }

                    Divider(modifier = Modifier.fillMaxWidth())
                }
                is OverviewState.OverviewError -> {

                }
            }
        }

    }
}

@Composable
fun BudgetBox(modifier: Modifier, amount: Double?, currency: String) {
    PrimaryGradientColumn(modifier = modifier) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.budget),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            text = "${amount ?: 0f} $currency"
        )
    }
}

@Composable
fun ExpensesBox(modifier: Modifier = Modifier, amount: Double?, currency: String) {
    SecondaryGradientColumn(modifier = modifier) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.expenses),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
        Text(
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp),
            text = "${amount ?: 0f} $currency",
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
@Preview
fun PreviewOverview() {
    OverviewScreen(
        OverviewState.OverviewSuccess(
            MonthCategories(
                Month(
                    monthId = 0,
                    month = 0,
                    year = 0
                ),
                listOf()
            )
        )
    )
}