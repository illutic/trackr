package g.sig.core_data.database_impl.models.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import g.sig.core_data.models.user.Currency

@Entity
data class CurrencyDb(
    @PrimaryKey override val currencyId: Int,
    override val currencyName: String
) : Currency