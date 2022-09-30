package g.sig.core_ui.menus

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import g.sig.constants.DateConstants
import g.sig.core_ui.AppIcons
import g.sig.core_ui.R
import g.sig.core_ui.input.AppTextButton
import g.sig.core_ui.theme.shape24
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@Composable
@Preview
fun MonthPickerDialog(
    modifier: Modifier = Modifier,
    initialDate: LocalDate = LocalDate.now(),
    onMonthChanged: (LocalDate) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    var date by remember { mutableStateOf(initialDate) }
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            AppTextButton(
                onClick = { onMonthChanged(date) },
                text = stringResource(id = R.string.confirm)
            )
        },
        dismissButton = {
            AppTextButton(onClick = onDismiss, text = stringResource(id = R.string.dismiss))
        },
        icon = { Icon(imageVector = AppIcons.outlinedDateRange, contentDescription = "month") },
        title = { Text(stringResource(id = R.string.choose_a_month)) },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
                verticalArrangement = Arrangement.Center
            ) {
                items(DateConstants.Months) { month ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                date = date.withMonth(month.value)
                            }
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        if (date.month == month) {
                            Surface(
                                shape = shape24,
                                color = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = month.getDisplayName(
                                        TextStyle.FULL,
                                        Locale.getDefault()
                                    )
                                )
                            }
                        } else {
                            Text(
                                text = month.getDisplayName(
                                    TextStyle.FULL,
                                    Locale.getDefault()
                                )
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
@Preview
fun YearPickerDialog(
    modifier: Modifier = Modifier,
    initialDate: LocalDate = LocalDate.now(),
    onYearChanged: (LocalDate) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    var date by remember { mutableStateOf(initialDate) }
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            AppTextButton(
                onClick = { onYearChanged(date) },
                text = stringResource(id = R.string.confirm)
            )
        },
        dismissButton = {
            AppTextButton(onClick = onDismiss, text = stringResource(id = R.string.dismiss))
        },
        icon = { Icon(imageVector = AppIcons.outlinedDateRange, contentDescription = "year") },
        title = { Text(stringResource(id = R.string.choose_a_year)) },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(48.dp),
                verticalArrangement = Arrangement.Center
            ) {
                items(DateConstants.Years) { year ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                date = date.withYear(year)
                            }
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        if (date.year == year) {
                            Surface(
                                shape = shape24,
                                color = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ) {
                                Text(
                                    modifier = modifier.padding(8.dp),
                                    text = year.toString()
                                )
                            }
                        } else {
                            Text(
                                text = year.toString()
                            )
                        }
                    }
                }
            }
        }
    )
}