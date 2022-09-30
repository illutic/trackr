package g.sig.core_ui.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import g.sig.constants.CategoryPercentages
import g.sig.core_data.models.transaction.Category
import g.sig.core_data.models.transaction.CategoryTransactions
import g.sig.core_data.models.transaction.LoggedTransaction
import g.sig.core_data.utils.getPercentage
import g.sig.core_ui.theme.largeSize
import g.sig.core_ui.theme.mediumSize
import g.sig.core_ui.theme.shape24
import g.sig.core_ui.theme.smallSize
import g.sig.utils.formatTo3Decimals
import java.time.Instant

@Composable
fun PercentageGridLayout(
    itemList: List<CategoryTransactions> = listOf(),
    onItemClick: (CategoryTransactions) -> Unit = {}
) {
    val totalAmount =
        itemList.sumOf { category -> category.loggedTransactions.sumOf { it.amount } }
    val highPercentageCategories =
        itemList.sortedBy { it.getPercentage(totalAmount) }
            .filter { it.getPercentage(totalAmount) >= CategoryPercentages.High }
    val mediumPercentageCategories =
        itemList.sortedBy { it.getPercentage(totalAmount) }
            .filter {
                val percentage = it.getPercentage(totalAmount)
                percentage < CategoryPercentages.High
                        && percentage >= CategoryPercentages.Medium
            }
    val lowPercentageCategories =
        itemList.sortedBy { it.getPercentage(totalAmount) }
            .filter { it.getPercentage(totalAmount) < CategoryPercentages.Medium }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        userScrollEnabled = true,
    ) {
        items(highPercentageCategories) { categories ->
            CategoryCard(
                category = categories.category,
                percentage = CategoryPercentages.High,
                onItemClick = { onItemClick(categories) }
            )
        }
        items(mediumPercentageCategories) { categories ->
            CategoryCard(
                category = categories.category,
                percentage = CategoryPercentages.Medium,
                onItemClick = { onItemClick(categories) }
            )
        }
        items(lowPercentageCategories) { categories ->
            CategoryCard(
                category = categories.category,
                percentage = CategoryPercentages.Low,
                onItemClick = { onItemClick(categories) }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryCard(category: Category, percentage: Float, onItemClick: (Category) -> Unit) {
    val size =
        when (percentage) {
            CategoryPercentages.High -> largeSize
            CategoryPercentages.Medium -> mediumSize
            else -> smallSize
        }
    val color =
        when (percentage) {
            CategoryPercentages.High -> MaterialTheme.colorScheme.primaryContainer
            CategoryPercentages.Medium -> MaterialTheme.colorScheme.tertiaryContainer
            else -> MaterialTheme.colorScheme.secondaryContainer
        }
    val onColor =
        when (percentage) {
            CategoryPercentages.High -> MaterialTheme.colorScheme.onPrimaryContainer
            CategoryPercentages.Medium -> MaterialTheme.colorScheme.onTertiaryContainer
            else -> MaterialTheme.colorScheme.onSecondaryContainer
        }

    Surface(
        modifier = Modifier
            .size(size)
            .padding(8.dp),
        shape = shape24,
        onClick = { onItemClick(category) },
        color = color,
        contentColor = onColor
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "${category.name}: ${percentage.formatTo3Decimals}"
            )
        }
    }
}

@Composable
@Preview
fun PreviewPercentageGridLayout() {
    val categoryList = listOf(
        CategoryTransactions(
            Category(0, "Category 1"),
            listOf(
                LoggedTransaction(0, 0.0, Instant.now()),
                LoggedTransaction(1, 0.0, Instant.now()),
                LoggedTransaction(2, 10.0, Instant.now())
            )
        ),
        CategoryTransactions(
            Category(1, "Category 2"),
            listOf(
                LoggedTransaction(3, 10.0, Instant.now()),
                LoggedTransaction(4, 20.0, Instant.now()),
                LoggedTransaction(5, 40.0, Instant.now())
            )
        ),
        CategoryTransactions(
            Category(3, "Category 4"),
            listOf(
                LoggedTransaction(3, 10.0, Instant.now()),
                LoggedTransaction(4, 20.0, Instant.now()),
                LoggedTransaction(5, 10.0, Instant.now())
            )
        ),
        CategoryTransactions(
            Category(4, "Category 5"),
            listOf(
                LoggedTransaction(0, 0.0, Instant.now()),
                LoggedTransaction(1, 0.0, Instant.now()),
                LoggedTransaction(2, 10.0, Instant.now())
            )
        ),
        CategoryTransactions(
            Category(5, "Category 6"),
            listOf(
                LoggedTransaction(3, 10.0, Instant.now()),
                LoggedTransaction(4, 20.0, Instant.now()),
                LoggedTransaction(5, 400.0, Instant.now())
            )
        ),
        CategoryTransactions(
            Category(6, "Category 7"),
            listOf(
                LoggedTransaction(3, 10.0, Instant.now()),
                LoggedTransaction(4, 200.0, Instant.now()),
                LoggedTransaction(5, 10.0, Instant.now())
            )
        )
    )

    PercentageGridLayout(
        itemList = categoryList
    )
}