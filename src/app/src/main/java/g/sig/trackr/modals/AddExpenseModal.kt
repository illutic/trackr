package g.sig.trackr.modals

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import g.sig.core_data.models.transaction.Category
import g.sig.core_data.models.transaction.LoggedTransaction
import g.sig.core_ui.AppIcons
import g.sig.core_ui.R
import g.sig.core_ui.input.AppTextButton
import g.sig.core_ui.input.AppTonalButton
import g.sig.core_ui.theme.shape16
import g.sig.trackr.states.CategoryState
import g.sig.trackr.states.TransactionState
import g.sig.utils.isDouble
import g.sig.utils.isProperString
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseModal(
    modifier: Modifier = Modifier,
    state: ModalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded),
    addCategoryState: ModalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
    transactionState: TransactionState,
    onTransactionLogged: (LoggedTransaction, Category?) -> Unit = { _, _ -> },
    content: @Composable () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    var expense by remember { mutableStateOf("0.0") }
    var description by remember { mutableStateOf("") }
    val expenseTextStyle = if (expense.isDouble) MaterialTheme.typography.headlineLarge.copy(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    ) else MaterialTheme.typography.headlineLarge.copy(
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
    var selectedCategory: Category? by remember {
        mutableStateOf(null)
    }
    val chipColors = FilterChipDefaults.filterChipColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    )

    ModalBottomSheetLayout(
        sheetState = state,
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        sheetContent = {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.add_expense),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                when (transactionState) {
                    is TransactionState.Idle -> {
                        BasicTextField(
                            modifier = Modifier.padding(16.dp),
                            value = expense,
                            onValueChange = { expense = it },
                            textStyle = expenseTextStyle,
                            maxLines = 1
                        )

                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(transactionState.categories) {
                                FilterChip(
                                    modifier = Modifier.padding(horizontal = 4.dp),
                                    label = {
                                        Text(it.name)
                                    },
                                    onClick = { selectedCategory = it },
                                    selected = selectedCategory == it
                                )
                            }
                            item {
                                FilterChip(
                                    selected = false,
                                    leadingIcon = {
                                        Icon(
                                            imageVector = AppIcons.add,
                                            contentDescription = "add category"
                                        )
                                    },
                                    colors = chipColors,
                                    onClick = { coroutineScope.launch { addCategoryState.show() } },
                                    label = {
                                        Text(stringResource(id = R.string.add_category))
                                    })
                            }
                        }

                        OutlinedTextField(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth(),
                            shape = shape16,
                            value = description,
                            onValueChange = { description = it },
                            singleLine = true,
                            label = { Text(stringResource(R.string.add_a_name)) }
                        )

                        /** TODO - ADD DATE FIELD */

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AppTextButton(
                                onClick = {
                                    coroutineScope.launch { state.hide() }
                                    expense = "0.0"
                                },
                                text = stringResource(R.string.cancel),
                            )
                            AppTonalButton(
                                onClick = {
                                    if (expense.isDouble) {
                                        onTransactionLogged(
                                            LoggedTransaction(amount = expense.toDouble()),
                                            selectedCategory
                                        )
                                    }
                                },
                                text = stringResource(R.string.save),
                            )
                        }
                    }
                    is TransactionState.Success -> {
                        LaunchedEffect(Unit) {
                            coroutineScope.launch {
                                state.hide()
                            }
                        }
                    }
                    is TransactionState.Error -> {

                    }
                    is TransactionState.Loading -> {
                        androidx.compose.material3.CircularProgressIndicator()
                    }
                }
            }
        },
        content = content
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryModal(
    modifier: Modifier = Modifier,
    state: ModalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Expanded),
    onCategorySaved: (Category) -> Unit = {},
    categoryState: CategoryState,
    content: @Composable () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    var categoryName by remember { mutableStateOf("") }

    ModalBottomSheetLayout(
        sheetState = state,
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        sheetContent = {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.add_expense),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                when (categoryState) {
                    is CategoryState.Success -> {
                        LaunchedEffect(categoryState) {
                            state.hide()
                        }
                    }
                    is CategoryState.Idle -> {
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .fillMaxWidth(),
                            shape = shape16,
                            value = categoryName,
                            onValueChange = { categoryName = it },
                            isError = !categoryName.isProperString,
                            singleLine = true,
                            label = { Text(stringResource(R.string.add_a_name)) },

                            )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AppTextButton(
                                onClick = {
                                    coroutineScope.launch { state.hide() }
                                    categoryName = ""
                                },
                                text = stringResource(R.string.cancel),
                            )
                            AppTonalButton(
                                onClick = {
                                    onCategorySaved(Category(name = categoryName))
                                },
                                text = stringResource(R.string.save),
                            )
                        }
                    }
                    is CategoryState.Error -> {}
                    CategoryState.Loading ->
                        androidx.compose.material3.CircularProgressIndicator()
                }
            }
        },
        content = content
    )
}