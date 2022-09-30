package g.sig.feature_overview

import android.icu.util.Currency
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import g.sig.constants.AppButtonType
import g.sig.core_data.models.transaction.Month
import g.sig.core_data.models.transaction.MonthCategories
import g.sig.core_data.utils.*
import g.sig.core_ui.AppIcons
import g.sig.core_ui.NoNavIconAppBar
import g.sig.core_ui.R
import g.sig.core_ui.input.AppTonalButton
import g.sig.core_ui.layouts.PercentageGridLayout
import g.sig.core_ui.menus.MonthPickerDialog
import g.sig.core_ui.menus.YearPickerDialog
import g.sig.core_ui.surfaces.PrimaryGradientColumn
import g.sig.core_ui.surfaces.SecondaryGradientColumn
import g.sig.core_ui.theme.shape24to4
import g.sig.core_ui.theme.shape4to24
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

@Composable
fun OverviewRoute() {
    val viewModel: OverviewViewModel = viewModel()
    val overviewState by viewModel.overviewStateFlow.collectAsState()
    val currency by viewModel.savedCurrency.collectAsState()
    OverviewScreen(overviewState, currency = currency.currencyCode, viewModel::changeMonth)
}

@Composable
fun OverviewScreen(
    overviewState: OverviewState = OverviewState.OverviewLoading,
    currency: String = Currency.getInstance(Locale.getDefault()).currencyCode,
    onDateChange: (LocalDate) -> Unit = {}
) {
    when (overviewState) {
        is OverviewState.OverviewLoading -> {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        is OverviewState.OverviewSuccess ->
            OverviewSuccessContent(
                currency = currency,
                overviewState = overviewState,
                onDateChange = onDateChange
            )
        is OverviewState.OverviewError -> {

        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OverviewSuccessContent(
    currency: String,
    overviewState: OverviewState.OverviewSuccess,
    onDateChange: (LocalDate) -> Unit
) {
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    AddExpenseModal(bottomSheetState) {
        SuccessContent(
            currency = currency,
            overviewState = overviewState,
            onDateChange = onDateChange,
            onAddExpense = { coroutineScope.launch { bottomSheetState.show() } }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessContent(
    currency: String,
    overviewState: OverviewState.OverviewSuccess,
    onDateChange: (LocalDate) -> Unit,
    onAddExpense: () -> Unit
) {

    var showMonthPicker by remember { mutableStateOf(false) }
    var showYearPicker by remember { mutableStateOf(false) }

    if (showMonthPicker) {
        MonthPickerDialog(
            initialDate = overviewState.monthCategories?.month?.toLocalDate() ?: LocalDate.now(),
            onMonthChanged = { onDateChange(it); showMonthPicker = false },
            onDismiss = { showMonthPicker = false }
        )
    }

    if (showYearPicker) {
        YearPickerDialog(
            initialDate = overviewState.monthCategories?.month?.toLocalDate() ?: LocalDate.now(),
            onYearChanged = { onDateChange(it); showYearPicker = false },
            onDismiss = { showYearPicker = false }
        )
    }

    Scaffold(
        topBar = {
            NoNavIconAppBar(stringResource(R.string.overview)) {
                AppTonalButton(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = overviewState.monthCategories?.month?.toDisplayName() ?: LocalDate.now()
                        .toMonthDisplayName(),
                    onClick = { showMonthPicker = true },
                    type = AppButtonType.Primary,
                    shape = shape24to4
                )
                AppTonalButton(
                    modifier = Modifier.padding(end = 16.dp),
                    text = overviewState.monthCategories?.month?.year?.toString() ?: LocalDate.now()
                        .toYearDisplayName(),
                    onClick = { showYearPicker = true },
                    type = AppButtonType.Secondary,
                    shape = shape4to24
                )
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
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

            val categories = overviewState.monthCategories?.categories ?: listOf()

            if (categories.isEmpty()) {
                EmptyCategories()
            } else {
                PercentageGridLayout(itemList = categories)
            }


            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                ExtendedFloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    onClick = { onAddExpense() }
                ) {
                    Icon(imageVector = AppIcons.add, contentDescription = "Add icon")
                    Text(stringResource(id = R.string.add_expense))
                }
            }
        }
    }
}

@Composable
fun EmptyCategories() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .size(95.dp)
                .padding(vertical = 16.dp),
            painter = painterResource(AppIcons.receipt),
            contentDescription = "empty categories"
        )
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.no_transactions)
        )
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