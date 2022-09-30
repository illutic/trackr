package g.sig.core_data.utils

import g.sig.core_data.models.transaction.CategoryTransactions

fun CategoryTransactions.getPercentage(totalAmount: Double) =
    loggedTransactions.sumOf { it.amount } / totalAmount
