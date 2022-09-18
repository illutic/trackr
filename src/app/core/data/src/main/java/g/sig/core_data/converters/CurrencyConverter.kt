package g.sig.core_data.converters

import android.icu.util.Currency
import androidx.room.TypeConverter

class CurrencyConverter {
    @TypeConverter
    fun fromCurrencyToString(currency: Currency?): String? =
        currency?.currencyCode

    @TypeConverter
    fun fromStringToCurrency(currencyCode: String?): Currency? =
        Currency.getInstance(currencyCode)
}